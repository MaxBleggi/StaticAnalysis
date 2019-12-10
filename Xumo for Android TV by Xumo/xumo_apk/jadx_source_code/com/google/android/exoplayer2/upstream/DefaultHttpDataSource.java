package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper.Callback;
import com.google.android.exoplayer2.upstream.HttpDataSource.HttpDataSourceException;
import com.google.android.exoplayer2.upstream.HttpDataSource.InvalidContentTypeException;
import com.google.android.exoplayer2.upstream.HttpDataSource.InvalidResponseCodeException;
import com.google.android.exoplayer2.upstream.HttpDataSource.RequestProperties;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Predicate;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.HttpURLConnection;
import java.net.NoRouteToHostException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class DefaultHttpDataSource extends BaseDataSource implements HttpDataSource {
    private static final Pattern CONTENT_RANGE_HEADER = Pattern.compile("^bytes (\\d+)-(\\d+)/(\\d+)$");
    public static final int DEFAULT_CONNECT_TIMEOUT_MILLIS = 8000;
    public static final int DEFAULT_READ_TIMEOUT_MILLIS = 8000;
    private static final int HTTP_STATUS_PERMANENT_REDIRECT = 308;
    private static final int HTTP_STATUS_TEMPORARY_REDIRECT = 307;
    private static final long MAX_BYTES_TO_DRAIN = 2048;
    private static final int MAX_REDIRECTS = 20;
    private static final String TAG = "DefaultHttpDataSource";
    private static final AtomicReference<byte[]> skipBufferReference = new AtomicReference();
    private final boolean allowCrossProtocolRedirects;
    private long bytesRead;
    private long bytesSkipped;
    private long bytesToRead;
    private long bytesToSkip;
    private final int connectTimeoutMillis;
    @Nullable
    private HttpURLConnection connection;
    @Nullable
    private final Predicate<String> contentTypePredicate;
    @Nullable
    private DataSpec dataSpec;
    @Nullable
    private final RequestProperties defaultRequestProperties;
    @Nullable
    private InputStream inputStream;
    private boolean opened;
    private final int readTimeoutMillis;
    private final RequestProperties requestProperties;
    private final String userAgent;

    public DefaultHttpDataSource(String str, @Nullable Predicate<String> predicate) {
        this(str, predicate, 8000, 8000);
    }

    public DefaultHttpDataSource(String str, @Nullable Predicate<String> predicate, int i, int i2) {
        this(str, predicate, i, i2, false, null);
    }

    public DefaultHttpDataSource(String str, @Nullable Predicate<String> predicate, int i, int i2, boolean z, @Nullable RequestProperties requestProperties) {
        super(true);
        this.userAgent = Assertions.checkNotEmpty(str);
        this.contentTypePredicate = predicate;
        this.requestProperties = new RequestProperties();
        this.connectTimeoutMillis = i;
        this.readTimeoutMillis = i2;
        this.allowCrossProtocolRedirects = z;
        this.defaultRequestProperties = requestProperties;
    }

    @Deprecated
    public DefaultHttpDataSource(String str, @Nullable Predicate<String> predicate, @Nullable TransferListener transferListener) {
        this(str, predicate, transferListener, 8000, 8000);
    }

    @Deprecated
    public DefaultHttpDataSource(String str, @Nullable Predicate<String> predicate, @Nullable TransferListener transferListener, int i, int i2) {
        this(str, predicate, transferListener, i, i2, false, null);
    }

    @Deprecated
    public DefaultHttpDataSource(String str, @Nullable Predicate<String> predicate, @Nullable TransferListener transferListener, int i, int i2, boolean z, @Nullable RequestProperties requestProperties) {
        this(str, predicate, i, i2, z, requestProperties);
        if (transferListener != null) {
            addTransferListener(transferListener);
        }
    }

    @Nullable
    public Uri getUri() {
        return this.connection == null ? null : Uri.parse(this.connection.getURL().toString());
    }

    public Map<String, List<String>> getResponseHeaders() {
        return this.connection == null ? Collections.emptyMap() : this.connection.getHeaderFields();
    }

    public void setRequestProperty(String str, String str2) {
        Assertions.checkNotNull(str);
        Assertions.checkNotNull(str2);
        this.requestProperties.set(str, str2);
    }

    public void clearRequestProperty(String str) {
        Assertions.checkNotNull(str);
        this.requestProperties.remove(str);
    }

    public void clearAllRequestProperties() {
        this.requestProperties.clear();
    }

    public long open(DataSpec dataSpec) throws HttpDataSourceException {
        StringBuilder stringBuilder;
        this.dataSpec = dataSpec;
        long j = 0;
        this.bytesRead = 0;
        this.bytesSkipped = 0;
        transferInitializing(dataSpec);
        try {
            this.connection = makeConnection(dataSpec);
            try {
                int responseCode = this.connection.getResponseCode();
                if (responseCode >= Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                    if (responseCode <= 299) {
                        String contentType = this.connection.getContentType();
                        if (this.contentTypePredicate != null) {
                            if (!this.contentTypePredicate.evaluate(contentType)) {
                                closeConnectionQuietly();
                                throw new InvalidContentTypeException(contentType, dataSpec);
                            }
                        }
                        if (responseCode == Callback.DEFAULT_DRAG_ANIMATION_DURATION && dataSpec.position != 0) {
                            j = dataSpec.position;
                        }
                        this.bytesToSkip = j;
                        if (dataSpec.isFlagSet(1)) {
                            this.bytesToRead = dataSpec.length;
                        } else {
                            long j2 = -1;
                            if (dataSpec.length != -1) {
                                this.bytesToRead = dataSpec.length;
                            } else {
                                j = getContentLength(this.connection);
                                if (j != -1) {
                                    j2 = j - this.bytesToSkip;
                                }
                                this.bytesToRead = j2;
                            }
                        }
                        try {
                            this.inputStream = this.connection.getInputStream();
                            this.opened = true;
                            transferStarted(dataSpec);
                            return this.bytesToRead;
                        } catch (IOException e) {
                            closeConnectionQuietly();
                            throw new HttpDataSourceException(e, dataSpec, 1);
                        }
                    }
                }
                Map headerFields = this.connection.getHeaderFields();
                closeConnectionQuietly();
                InvalidResponseCodeException invalidResponseCodeException = new InvalidResponseCodeException(responseCode, headerFields, dataSpec);
                if (responseCode == 416) {
                    invalidResponseCodeException.initCause(new DataSourceException(0));
                }
                throw invalidResponseCodeException;
            } catch (IOException e2) {
                closeConnectionQuietly();
                stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to connect to ");
                stringBuilder.append(dataSpec.uri.toString());
                throw new HttpDataSourceException(stringBuilder.toString(), e2, dataSpec, 1);
            }
        } catch (IOException e22) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to connect to ");
            stringBuilder.append(dataSpec.uri.toString());
            throw new HttpDataSourceException(stringBuilder.toString(), e22, dataSpec, 1);
        }
    }

    public int read(byte[] bArr, int i, int i2) throws HttpDataSourceException {
        try {
            skipInternal();
            return readInternal(bArr, i, i2);
        } catch (IOException e) {
            throw new HttpDataSourceException(e, this.dataSpec, 2);
        }
    }

    public void close() throws HttpDataSourceException {
        try {
            if (this.inputStream != null) {
                maybeTerminateInputStream(this.connection, bytesRemaining());
                this.inputStream.close();
            }
            this.inputStream = null;
            closeConnectionQuietly();
            if (this.opened) {
                this.opened = false;
                transferEnded();
            }
        } catch (IOException e) {
            throw new HttpDataSourceException(e, this.dataSpec, 3);
        } catch (Throwable th) {
            this.inputStream = null;
            closeConnectionQuietly();
            if (this.opened) {
                this.opened = false;
                transferEnded();
            }
        }
    }

    @Nullable
    protected final HttpURLConnection getConnection() {
        return this.connection;
    }

    protected final long bytesSkipped() {
        return this.bytesSkipped;
    }

    protected final long bytesRead() {
        return this.bytesRead;
    }

    protected final long bytesRemaining() {
        return this.bytesToRead == -1 ? this.bytesToRead : this.bytesToRead - this.bytesRead;
    }

    private HttpURLConnection makeConnection(DataSpec dataSpec) throws IOException {
        DataSpec dataSpec2 = dataSpec;
        URL url = new URL(dataSpec2.uri.toString());
        int i = dataSpec2.httpMethod;
        byte[] bArr = dataSpec2.httpBody;
        long j = dataSpec2.position;
        long j2 = dataSpec2.length;
        boolean isFlagSet = dataSpec2.isFlagSet(1);
        if (!this.allowCrossProtocolRedirects) {
            return makeConnection(url, i, bArr, j, j2, isFlagSet, true);
        }
        HttpURLConnection makeConnection;
        int i2 = 0;
        while (true) {
            int i3 = i2 + 1;
            int i4;
            if (i2 <= 20) {
                DefaultHttpDataSource defaultHttpDataSource;
                i4 = i3;
                long j3 = j2;
                makeConnection = makeConnection(url, i, bArr, j, j2, isFlagSet, false);
                int responseCode = makeConnection.getResponseCode();
                String headerField = makeConnection.getHeaderField(HttpRequest.HEADER_LOCATION);
                if (i == 1 || i == 3) {
                    if (!(responseCode == 300 || responseCode == 301 || responseCode == 302 || responseCode == 303 || responseCode == HTTP_STATUS_TEMPORARY_REDIRECT)) {
                        if (responseCode == HTTP_STATUS_PERMANENT_REDIRECT) {
                        }
                    }
                    makeConnection.disconnect();
                    url = handleRedirect(url, headerField);
                    defaultHttpDataSource = this;
                    i2 = i4;
                    j2 = j3;
                }
                if (i != 2 || (responseCode != 300 && responseCode != 301 && responseCode != 302 && responseCode != 303)) {
                    return makeConnection;
                }
                makeConnection.disconnect();
                url = handleRedirect(url, headerField);
                bArr = null;
                i = 1;
                defaultHttpDataSource = this;
                i2 = i4;
                j2 = j3;
            } else {
                i4 = i3;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Too many redirects: ");
                stringBuilder.append(i4);
                throw new NoRouteToHostException(stringBuilder.toString());
            }
        }
        return makeConnection;
    }

    private HttpURLConnection makeConnection(URL url, int i, byte[] bArr, long j, long j2, boolean z, boolean z2) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout(this.connectTimeoutMillis);
        httpURLConnection.setReadTimeout(this.readTimeoutMillis);
        if (this.defaultRequestProperties != null) {
            for (Entry entry : this.defaultRequestProperties.getSnapshot().entrySet()) {
                httpURLConnection.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
            }
        }
        for (Entry entry2 : this.requestProperties.getSnapshot().entrySet()) {
            httpURLConnection.setRequestProperty((String) entry2.getKey(), (String) entry2.getValue());
        }
        if (!(j == 0 && j2 == -1)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("bytes=");
            stringBuilder.append(j);
            stringBuilder.append("-");
            String stringBuilder2 = stringBuilder.toString();
            if (j2 != -1) {
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append(stringBuilder2);
                stringBuilder3.append((j + j2) - 1);
                stringBuilder2 = stringBuilder3.toString();
            }
            httpURLConnection.setRequestProperty("Range", stringBuilder2);
        }
        httpURLConnection.setRequestProperty("User-Agent", this.userAgent);
        if (!z) {
            httpURLConnection.setRequestProperty(HttpRequest.HEADER_ACCEPT_ENCODING, "identity");
        }
        httpURLConnection.setInstanceFollowRedirects(z2);
        httpURLConnection.setDoOutput(bArr != null ? 1 : null);
        httpURLConnection.setRequestMethod(DataSpec.getStringForHttpMethod(i));
        if (bArr != null) {
            httpURLConnection.setFixedLengthStreamingMode(bArr.length);
            httpURLConnection.connect();
            i = httpURLConnection.getOutputStream();
            i.write(bArr);
            i.close();
        } else {
            httpURLConnection.connect();
        }
        return httpURLConnection;
    }

    private static URL handleRedirect(URL url, String str) throws IOException {
        if (str != null) {
            URL url2 = new URL(url, str);
            url = url2.getProtocol();
            if ("https".equals(url) == null) {
                if ("http".equals(url) == null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unsupported protocol redirect: ");
                    stringBuilder.append(url);
                    throw new ProtocolException(stringBuilder.toString());
                }
            }
            return url2;
        }
        throw new ProtocolException("Null location redirect");
    }

    private static long getContentLength(java.net.HttpURLConnection r8) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r0 = "Content-Length";
        r0 = r8.getHeaderField(r0);
        r1 = android.text.TextUtils.isEmpty(r0);
        if (r1 != 0) goto L_0x002c;
    L_0x000c:
        r1 = java.lang.Long.parseLong(r0);	 Catch:{ NumberFormatException -> 0x0011 }
        goto L_0x002e;
    L_0x0011:
        r1 = "DefaultHttpDataSource";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "Unexpected Content-Length [";
        r2.append(r3);
        r2.append(r0);
        r3 = "]";
        r2.append(r3);
        r2 = r2.toString();
        com.google.android.exoplayer2.util.Log.e(r1, r2);
    L_0x002c:
        r1 = -1;
    L_0x002e:
        r3 = "Content-Range";
        r8 = r8.getHeaderField(r3);
        r3 = android.text.TextUtils.isEmpty(r8);
        if (r3 != 0) goto L_0x00ad;
    L_0x003a:
        r3 = CONTENT_RANGE_HEADER;
        r3 = r3.matcher(r8);
        r4 = r3.find();
        if (r4 == 0) goto L_0x00ad;
    L_0x0046:
        r4 = 2;
        r4 = r3.group(r4);	 Catch:{ NumberFormatException -> 0x0092 }
        r4 = java.lang.Long.parseLong(r4);	 Catch:{ NumberFormatException -> 0x0092 }
        r6 = 1;	 Catch:{ NumberFormatException -> 0x0092 }
        r3 = r3.group(r6);	 Catch:{ NumberFormatException -> 0x0092 }
        r6 = java.lang.Long.parseLong(r3);	 Catch:{ NumberFormatException -> 0x0092 }
        r3 = 0;	 Catch:{ NumberFormatException -> 0x0092 }
        r4 = r4 - r6;	 Catch:{ NumberFormatException -> 0x0092 }
        r6 = 1;	 Catch:{ NumberFormatException -> 0x0092 }
        r4 = r4 + r6;	 Catch:{ NumberFormatException -> 0x0092 }
        r6 = 0;	 Catch:{ NumberFormatException -> 0x0092 }
        r3 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1));	 Catch:{ NumberFormatException -> 0x0092 }
        if (r3 >= 0) goto L_0x0065;	 Catch:{ NumberFormatException -> 0x0092 }
    L_0x0063:
        r1 = r4;	 Catch:{ NumberFormatException -> 0x0092 }
        goto L_0x00ad;	 Catch:{ NumberFormatException -> 0x0092 }
    L_0x0065:
        r3 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1));	 Catch:{ NumberFormatException -> 0x0092 }
        if (r3 == 0) goto L_0x00ad;	 Catch:{ NumberFormatException -> 0x0092 }
    L_0x0069:
        r3 = "DefaultHttpDataSource";	 Catch:{ NumberFormatException -> 0x0092 }
        r6 = new java.lang.StringBuilder;	 Catch:{ NumberFormatException -> 0x0092 }
        r6.<init>();	 Catch:{ NumberFormatException -> 0x0092 }
        r7 = "Inconsistent headers [";	 Catch:{ NumberFormatException -> 0x0092 }
        r6.append(r7);	 Catch:{ NumberFormatException -> 0x0092 }
        r6.append(r0);	 Catch:{ NumberFormatException -> 0x0092 }
        r0 = "] [";	 Catch:{ NumberFormatException -> 0x0092 }
        r6.append(r0);	 Catch:{ NumberFormatException -> 0x0092 }
        r6.append(r8);	 Catch:{ NumberFormatException -> 0x0092 }
        r0 = "]";	 Catch:{ NumberFormatException -> 0x0092 }
        r6.append(r0);	 Catch:{ NumberFormatException -> 0x0092 }
        r0 = r6.toString();	 Catch:{ NumberFormatException -> 0x0092 }
        com.google.android.exoplayer2.util.Log.w(r3, r0);	 Catch:{ NumberFormatException -> 0x0092 }
        r3 = java.lang.Math.max(r1, r4);	 Catch:{ NumberFormatException -> 0x0092 }
        r1 = r3;
        goto L_0x00ad;
    L_0x0092:
        r0 = "DefaultHttpDataSource";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "Unexpected Content-Range [";
        r3.append(r4);
        r3.append(r8);
        r8 = "]";
        r3.append(r8);
        r8 = r3.toString();
        com.google.android.exoplayer2.util.Log.e(r0, r8);
    L_0x00ad:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.DefaultHttpDataSource.getContentLength(java.net.HttpURLConnection):long");
    }

    private void skipInternal() throws IOException {
        if (this.bytesSkipped != this.bytesToSkip) {
            Object obj = (byte[]) skipBufferReference.getAndSet(null);
            if (obj == null) {
                obj = new byte[4096];
            }
            while (this.bytesSkipped != this.bytesToSkip) {
                int read = this.inputStream.read(obj, 0, (int) Math.min(this.bytesToSkip - this.bytesSkipped, (long) obj.length));
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedIOException();
                } else if (read != -1) {
                    this.bytesSkipped += (long) read;
                    bytesTransferred(read);
                } else {
                    throw new EOFException();
                }
            }
            skipBufferReference.set(obj);
        }
    }

    private int readInternal(byte[] bArr, int i, int i2) throws IOException {
        if (i2 == 0) {
            return null;
        }
        if (this.bytesToRead != -1) {
            long j = this.bytesToRead - this.bytesRead;
            if (j == 0) {
                return -1;
            }
            i2 = (int) Math.min((long) i2, j);
        }
        bArr = this.inputStream.read(bArr, i, i2);
        if (bArr != -1) {
            this.bytesRead += (long) bArr;
            bytesTransferred(bArr);
            return bArr;
        } else if (this.bytesToRead == -1) {
            return -1;
        } else {
            throw new EOFException();
        }
    }

    private static void maybeTerminateInputStream(java.net.HttpURLConnection r3, long r4) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r0 = com.google.android.exoplayer2.util.Util.SDK_INT;
        r1 = 19;
        if (r0 == r1) goto L_0x000d;
    L_0x0006:
        r0 = com.google.android.exoplayer2.util.Util.SDK_INT;
        r1 = 20;
        if (r0 == r1) goto L_0x000d;
    L_0x000c:
        return;
    L_0x000d:
        r3 = r3.getInputStream();	 Catch:{ Exception -> 0x0058 }
        r0 = -1;	 Catch:{ Exception -> 0x0058 }
        r2 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1));	 Catch:{ Exception -> 0x0058 }
        if (r2 != 0) goto L_0x001f;	 Catch:{ Exception -> 0x0058 }
    L_0x0017:
        r4 = r3.read();	 Catch:{ Exception -> 0x0058 }
        r5 = -1;	 Catch:{ Exception -> 0x0058 }
        if (r4 != r5) goto L_0x0026;	 Catch:{ Exception -> 0x0058 }
    L_0x001e:
        return;	 Catch:{ Exception -> 0x0058 }
    L_0x001f:
        r0 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;	 Catch:{ Exception -> 0x0058 }
        r2 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1));	 Catch:{ Exception -> 0x0058 }
        if (r2 > 0) goto L_0x0026;	 Catch:{ Exception -> 0x0058 }
    L_0x0025:
        return;	 Catch:{ Exception -> 0x0058 }
    L_0x0026:
        r4 = r3.getClass();	 Catch:{ Exception -> 0x0058 }
        r4 = r4.getName();	 Catch:{ Exception -> 0x0058 }
        r5 = "com.android.okhttp.internal.http.HttpTransport$ChunkedInputStream";	 Catch:{ Exception -> 0x0058 }
        r5 = r5.equals(r4);	 Catch:{ Exception -> 0x0058 }
        if (r5 != 0) goto L_0x003e;	 Catch:{ Exception -> 0x0058 }
    L_0x0036:
        r5 = "com.android.okhttp.internal.http.HttpTransport$FixedLengthInputStream";	 Catch:{ Exception -> 0x0058 }
        r4 = r5.equals(r4);	 Catch:{ Exception -> 0x0058 }
        if (r4 == 0) goto L_0x0058;	 Catch:{ Exception -> 0x0058 }
    L_0x003e:
        r4 = r3.getClass();	 Catch:{ Exception -> 0x0058 }
        r4 = r4.getSuperclass();	 Catch:{ Exception -> 0x0058 }
        r5 = "unexpectedEndOfInput";	 Catch:{ Exception -> 0x0058 }
        r0 = 0;	 Catch:{ Exception -> 0x0058 }
        r1 = new java.lang.Class[r0];	 Catch:{ Exception -> 0x0058 }
        r4 = r4.getDeclaredMethod(r5, r1);	 Catch:{ Exception -> 0x0058 }
        r5 = 1;	 Catch:{ Exception -> 0x0058 }
        r4.setAccessible(r5);	 Catch:{ Exception -> 0x0058 }
        r5 = new java.lang.Object[r0];	 Catch:{ Exception -> 0x0058 }
        r4.invoke(r3, r5);	 Catch:{ Exception -> 0x0058 }
    L_0x0058:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.DefaultHttpDataSource.maybeTerminateInputStream(java.net.HttpURLConnection, long):void");
    }

    private void closeConnectionQuietly() {
        if (this.connection != null) {
            try {
                this.connection.disconnect();
            } catch (Throwable e) {
                Log.e(TAG, "Unexpected error while disconnecting", e);
            }
            this.connection = null;
        }
    }
}
