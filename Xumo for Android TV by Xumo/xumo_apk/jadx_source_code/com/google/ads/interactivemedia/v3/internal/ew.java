package com.google.ads.interactivemedia.v3.internal;

import android.util.Log;
import androidx.recyclerview.widget.ItemTouchHelper.Callback;
import com.google.ads.interactivemedia.v3.internal.ez.a;
import com.google.ads.interactivemedia.v3.internal.ez.b;
import com.google.ads.interactivemedia.v3.internal.ez.c;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.HttpURLConnection;
import java.net.NoRouteToHostException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

/* compiled from: IMASDK */
public class ew implements ez {
    private static final Pattern b = Pattern.compile("^bytes (\\d+)-(\\d+)/(\\d+)$");
    private static final AtomicReference<byte[]> c = new AtomicReference();
    private final boolean d;
    private final int e;
    private final int f;
    private final String g;
    private final fq<String> h;
    private final HashMap<String, String> i = new HashMap();
    private final fb j;
    private eu k;
    private HttpURLConnection l;
    private InputStream m;
    private boolean n;
    private long o;
    private long p;
    private long q;
    private long r;

    public ew(String str, fq<String> fqVar, fb fbVar, int i, int i2, boolean z) {
        this.g = fe.a(str);
        this.h = fqVar;
        this.j = fbVar;
        this.e = i;
        this.f = i2;
        this.d = z;
    }

    public long a(eu euVar) throws a {
        String str;
        String valueOf;
        this.k = euVar;
        long j = 0;
        this.r = 0;
        this.q = 0;
        try {
            this.l = b(euVar);
            try {
                int responseCode = this.l.getResponseCode();
                if (responseCode < Callback.DEFAULT_DRAG_ANIMATION_DURATION || responseCode > 299) {
                    Map headerFields = this.l.getHeaderFields();
                    d();
                    throw new c(responseCode, headerFields, euVar);
                }
                String contentType = this.l.getContentType();
                if (this.h != null) {
                    if (!this.h.a(contentType)) {
                        d();
                        throw new b(contentType, euVar);
                    }
                }
                if (responseCode == Callback.DEFAULT_DRAG_ANIMATION_DURATION && euVar.d != 0) {
                    j = euVar.d;
                }
                this.o = j;
                if ((euVar.g & 1) == 0) {
                    j = a(this.l);
                    long j2 = -1;
                    if (euVar.e != -1) {
                        j2 = euVar.e;
                    } else if (j != -1) {
                        j2 = j - this.o;
                    }
                    this.p = j2;
                } else {
                    this.p = euVar.e;
                }
                try {
                    this.m = this.l.getInputStream();
                    this.n = true;
                    if (this.j != null) {
                        this.j.a();
                    }
                    return this.p;
                } catch (IOException e) {
                    d();
                    throw new a(e, euVar, 1);
                }
            } catch (IOException e2) {
                d();
                str = "Unable to connect to ";
                valueOf = String.valueOf(euVar.a.toString());
                throw new a(valueOf.length() != 0 ? str.concat(valueOf) : new String(str), e2, euVar, 1);
            }
        } catch (IOException e22) {
            str = "Unable to connect to ";
            valueOf = String.valueOf(euVar.a.toString());
            throw new a(valueOf.length() != 0 ? str.concat(valueOf) : new String(str), e22, euVar, 1);
        }
    }

    public int a(byte[] bArr, int i, int i2) throws a {
        try {
            c();
            return b(bArr, i, i2);
        } catch (IOException e) {
            throw new a(e, this.k, 2);
        }
    }

    public void a() throws a {
        try {
            if (this.m != null) {
                ft.a(this.l, b());
                this.m.close();
            }
            this.m = null;
            d();
            if (this.n) {
                this.n = false;
                if (this.j != null) {
                    this.j.b();
                }
            }
        } catch (IOException e) {
            throw new a(e, this.k, 3);
        } catch (Throwable th) {
            this.m = null;
            d();
            if (this.n) {
                this.n = false;
                if (this.j != null) {
                    this.j.b();
                }
            }
        }
    }

    protected final long b() {
        return this.p == -1 ? this.p : this.p - this.r;
    }

    private HttpURLConnection b(eu euVar) throws IOException {
        eu euVar2 = euVar;
        URL url = new URL(euVar2.a.toString());
        byte[] bArr = euVar2.b;
        long j = euVar2.d;
        long j2 = euVar2.e;
        int i = 0;
        boolean z = (euVar2.g & 1) != 0;
        if (!this.d) {
            return a(url, bArr, j, j2, z, true);
        }
        HttpURLConnection a;
        while (true) {
            int i2 = i + 1;
            if (i <= 20) {
                a = a(url, bArr, j, j2, z, false);
                i = a.getResponseCode();
                if (!(i == 300 || i == 301 || i == 302 || i == 303)) {
                    if (bArr == null) {
                        if (i != 307) {
                            if (i != 308) {
                                break;
                            }
                        } else {
                            continue;
                        }
                    } else {
                        break;
                    }
                }
                bArr = null;
                String headerField = a.getHeaderField(HttpRequest.HEADER_LOCATION);
                a.disconnect();
                url = a(url, headerField);
                ew ewVar = this;
                i = i2;
            } else {
                StringBuilder stringBuilder = new StringBuilder(31);
                stringBuilder.append("Too many redirects: ");
                stringBuilder.append(i2);
                throw new NoRouteToHostException(stringBuilder.toString());
            }
        }
        return a;
    }

    private HttpURLConnection a(URL url, byte[] bArr, long j, long j2, boolean z, boolean z2) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout(this.e);
        httpURLConnection.setReadTimeout(this.f);
        synchronized (this.i) {
            for (Entry entry : this.i.entrySet()) {
                httpURLConnection.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
            }
        }
        if (!(j == 0 && j2 == -1)) {
            StringBuilder stringBuilder = new StringBuilder(27);
            stringBuilder.append("bytes=");
            stringBuilder.append(j);
            stringBuilder.append("-");
            String stringBuilder2 = stringBuilder.toString();
            if (j2 != -1) {
                stringBuilder2 = String.valueOf(stringBuilder2);
                j = (j + j2) - 1;
                StringBuilder stringBuilder3 = new StringBuilder(String.valueOf(stringBuilder2).length() + 20);
                stringBuilder3.append(stringBuilder2);
                stringBuilder3.append(j);
                stringBuilder2 = stringBuilder3.toString();
            }
            httpURLConnection.setRequestProperty("Range", stringBuilder2);
        }
        httpURLConnection.setRequestProperty("User-Agent", this.g);
        if (!z) {
            httpURLConnection.setRequestProperty(HttpRequest.HEADER_ACCEPT_ENCODING, "identity");
        }
        httpURLConnection.setInstanceFollowRedirects(z2);
        httpURLConnection.setDoOutput(bArr != null ? 1 : null);
        if (bArr != null) {
            httpURLConnection.setRequestMethod(HttpRequest.METHOD_POST);
            if (bArr.length == null) {
                httpURLConnection.connect();
            } else {
                httpURLConnection.setFixedLengthStreamingMode(bArr.length);
                httpURLConnection.connect();
                j = httpURLConnection.getOutputStream();
                j.write(bArr);
                j.close();
            }
        } else {
            httpURLConnection.connect();
        }
        return httpURLConnection;
    }

    private static URL a(URL url, String str) throws IOException {
        if (str != null) {
            URL url2 = new URL(url, str);
            url = url2.getProtocol();
            if ("https".equals(url) != null || "http".equals(url) != null) {
                return url2;
            }
            String str2 = "Unsupported protocol redirect: ";
            url = String.valueOf(url);
            throw new ProtocolException(url.length() != 0 ? str2.concat(url) : new String(str2));
        }
        throw new ProtocolException("Null location redirect");
    }

    private static long a(java.net.HttpURLConnection r8) {
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
        if (r1 != 0) goto L_0x0036;
    L_0x000c:
        r1 = java.lang.Long.parseLong(r0);	 Catch:{ NumberFormatException -> 0x0011 }
        goto L_0x0038;
    L_0x0011:
        r1 = "DefaultHttpDataSource";
        r2 = java.lang.String.valueOf(r0);
        r2 = r2.length();
        r2 = r2 + 28;
        r3 = new java.lang.StringBuilder;
        r3.<init>(r2);
        r2 = "Unexpected Content-Length [";
        r3.append(r2);
        r3.append(r0);
        r2 = "]";
        r3.append(r2);
        r2 = r3.toString();
        android.util.Log.e(r1, r2);
    L_0x0036:
        r1 = -1;
    L_0x0038:
        r3 = "Content-Range";
        r8 = r8.getHeaderField(r3);
        r3 = android.text.TextUtils.isEmpty(r8);
        if (r3 != 0) goto L_0x00d4;
    L_0x0044:
        r3 = b;
        r3 = r3.matcher(r8);
        r4 = r3.find();
        if (r4 == 0) goto L_0x00d4;
    L_0x0050:
        r4 = 2;
        r4 = r3.group(r4);	 Catch:{ NumberFormatException -> 0x00af }
        r4 = java.lang.Long.parseLong(r4);	 Catch:{ NumberFormatException -> 0x00af }
        r6 = 1;	 Catch:{ NumberFormatException -> 0x00af }
        r3 = r3.group(r6);	 Catch:{ NumberFormatException -> 0x00af }
        r6 = java.lang.Long.parseLong(r3);	 Catch:{ NumberFormatException -> 0x00af }
        r3 = 0;	 Catch:{ NumberFormatException -> 0x00af }
        r4 = r4 - r6;	 Catch:{ NumberFormatException -> 0x00af }
        r6 = 1;	 Catch:{ NumberFormatException -> 0x00af }
        r4 = r4 + r6;	 Catch:{ NumberFormatException -> 0x00af }
        r6 = 0;	 Catch:{ NumberFormatException -> 0x00af }
        r3 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1));	 Catch:{ NumberFormatException -> 0x00af }
        if (r3 >= 0) goto L_0x006f;	 Catch:{ NumberFormatException -> 0x00af }
    L_0x006d:
        r1 = r4;	 Catch:{ NumberFormatException -> 0x00af }
        goto L_0x00d4;	 Catch:{ NumberFormatException -> 0x00af }
    L_0x006f:
        r3 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1));	 Catch:{ NumberFormatException -> 0x00af }
        if (r3 == 0) goto L_0x00d4;	 Catch:{ NumberFormatException -> 0x00af }
    L_0x0073:
        r3 = "DefaultHttpDataSource";	 Catch:{ NumberFormatException -> 0x00af }
        r6 = java.lang.String.valueOf(r0);	 Catch:{ NumberFormatException -> 0x00af }
        r6 = r6.length();	 Catch:{ NumberFormatException -> 0x00af }
        r6 = r6 + 26;	 Catch:{ NumberFormatException -> 0x00af }
        r7 = java.lang.String.valueOf(r8);	 Catch:{ NumberFormatException -> 0x00af }
        r7 = r7.length();	 Catch:{ NumberFormatException -> 0x00af }
        r6 = r6 + r7;	 Catch:{ NumberFormatException -> 0x00af }
        r7 = new java.lang.StringBuilder;	 Catch:{ NumberFormatException -> 0x00af }
        r7.<init>(r6);	 Catch:{ NumberFormatException -> 0x00af }
        r6 = "Inconsistent headers [";	 Catch:{ NumberFormatException -> 0x00af }
        r7.append(r6);	 Catch:{ NumberFormatException -> 0x00af }
        r7.append(r0);	 Catch:{ NumberFormatException -> 0x00af }
        r0 = "] [";	 Catch:{ NumberFormatException -> 0x00af }
        r7.append(r0);	 Catch:{ NumberFormatException -> 0x00af }
        r7.append(r8);	 Catch:{ NumberFormatException -> 0x00af }
        r0 = "]";	 Catch:{ NumberFormatException -> 0x00af }
        r7.append(r0);	 Catch:{ NumberFormatException -> 0x00af }
        r0 = r7.toString();	 Catch:{ NumberFormatException -> 0x00af }
        android.util.Log.w(r3, r0);	 Catch:{ NumberFormatException -> 0x00af }
        r3 = java.lang.Math.max(r1, r4);	 Catch:{ NumberFormatException -> 0x00af }
        r1 = r3;
        goto L_0x00d4;
    L_0x00af:
        r0 = "DefaultHttpDataSource";
        r3 = java.lang.String.valueOf(r8);
        r3 = r3.length();
        r3 = r3 + 27;
        r4 = new java.lang.StringBuilder;
        r4.<init>(r3);
        r3 = "Unexpected Content-Range [";
        r4.append(r3);
        r4.append(r8);
        r8 = "]";
        r4.append(r8);
        r8 = r4.toString();
        android.util.Log.e(r0, r8);
    L_0x00d4:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.ew.a(java.net.HttpURLConnection):long");
    }

    private void c() throws IOException {
        if (this.q != this.o) {
            Object obj = (byte[]) c.getAndSet(null);
            if (obj == null) {
                obj = new byte[4096];
            }
            while (this.q != this.o) {
                int read = this.m.read(obj, 0, (int) Math.min(this.o - this.q, (long) obj.length));
                if (Thread.interrupted()) {
                    throw new InterruptedIOException();
                } else if (read != -1) {
                    this.q += (long) read;
                    if (this.j != null) {
                        this.j.a(read);
                    }
                } else {
                    throw new EOFException();
                }
            }
            c.set(obj);
        }
    }

    private int b(byte[] bArr, int i, int i2) throws IOException {
        if (i2 == 0) {
            return null;
        }
        bArr = this.m.read(bArr, i, i2);
        if (bArr == -1) {
            if (this.p != -1) {
                if (this.p != this.r) {
                    throw new EOFException();
                }
            }
            return -1;
        }
        this.r += (long) bArr;
        if (this.j != 0) {
            this.j.a(bArr);
        }
        return bArr;
    }

    private void d() {
        if (this.l != null) {
            try {
                this.l.disconnect();
            } catch (Throwable e) {
                Log.e("DefaultHttpDataSource", "Unexpected error while disconnecting", e);
            }
            this.l = null;
        }
    }
}
