package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import android.util.Base64;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.net.URLDecoder;

public final class DataSchemeDataSource extends BaseDataSource {
    public static final String SCHEME_DATA = "data";
    private int bytesRead;
    @Nullable
    private byte[] data;
    @Nullable
    private DataSpec dataSpec;

    public DataSchemeDataSource() {
        super(false);
    }

    public long open(DataSpec dataSpec) throws IOException {
        transferInitializing(dataSpec);
        this.dataSpec = dataSpec;
        Uri uri = dataSpec.uri;
        String scheme = uri.getScheme();
        if (SCHEME_DATA.equals(scheme)) {
            String[] split = Util.split(uri.getSchemeSpecificPart(), ",");
            if (split.length == 2) {
                String str = split[1];
                if (split[0].contains(";base64")) {
                    try {
                        this.data = Base64.decode(str, 0);
                    } catch (DataSpec dataSpec2) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Error while parsing Base64 encoded string: ");
                        stringBuilder.append(str);
                        throw new ParserException(stringBuilder.toString(), dataSpec2);
                    }
                }
                this.data = Util.getUtf8Bytes(URLDecoder.decode(str, C.ASCII_NAME));
                transferStarted(dataSpec2);
                return (long) this.data.length;
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Unexpected URI format: ");
            stringBuilder2.append(uri);
            throw new ParserException(stringBuilder2.toString());
        }
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("Unsupported scheme: ");
        stringBuilder3.append(scheme);
        throw new ParserException(stringBuilder3.toString());
    }

    public int read(byte[] bArr, int i, int i2) {
        if (i2 == 0) {
            return null;
        }
        int length = this.data.length - this.bytesRead;
        if (length == 0) {
            return -1;
        }
        i2 = Math.min(i2, length);
        System.arraycopy(this.data, this.bytesRead, bArr, i, i2);
        this.bytesRead += i2;
        bytesTransferred(i2);
        return i2;
    }

    @Nullable
    public Uri getUri() {
        return this.dataSpec != null ? this.dataSpec.uri : null;
    }

    public void close() throws IOException {
        if (this.data != null) {
            this.data = null;
            transferEnded();
        }
        this.dataSpec = null;
    }
}
