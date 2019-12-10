package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

public final class DataSpec {
    public static final int FLAG_ALLOW_CACHING_UNKNOWN_LENGTH = 2;
    public static final int FLAG_ALLOW_GZIP = 1;
    public static final int HTTP_METHOD_GET = 1;
    public static final int HTTP_METHOD_HEAD = 3;
    public static final int HTTP_METHOD_POST = 2;
    public final long absoluteStreamPosition;
    public final int flags;
    @Nullable
    public final byte[] httpBody;
    public final int httpMethod;
    @Nullable
    public final String key;
    public final long length;
    public final long position;
    @Deprecated
    @Nullable
    public final byte[] postBody;
    public final Uri uri;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface HttpMethod {
    }

    public DataSpec(Uri uri) {
        this(uri, 0);
    }

    public DataSpec(Uri uri, int i) {
        this(uri, 0, -1, null, i);
    }

    public DataSpec(Uri uri, long j, long j2, @Nullable String str) {
        this(uri, j, j, j2, str, 0);
    }

    public DataSpec(Uri uri, long j, long j2, @Nullable String str, int i) {
        this(uri, j, j, j2, str, i);
    }

    public DataSpec(Uri uri, long j, long j2, long j3, @Nullable String str, int i) {
        this(uri, null, j, j2, j3, str, i);
    }

    public DataSpec(Uri uri, @Nullable byte[] bArr, long j, long j2, long j3, @Nullable String str, int i) {
        this(uri, bArr != null ? 2 : 1, bArr, j, j2, j3, str, i);
    }

    public DataSpec(Uri uri, int i, @Nullable byte[] bArr, long j, long j2, long j3, @Nullable String str, int i2) {
        DataSpec dataSpec = this;
        byte[] bArr2 = bArr;
        long j4 = j;
        long j5 = j2;
        long j6 = j3;
        boolean z = false;
        Assertions.checkArgument(j4 >= 0);
        Assertions.checkArgument(j5 >= 0);
        if (j6 > 0 || j6 == -1) {
            z = true;
        }
        Assertions.checkArgument(z);
        dataSpec.uri = uri;
        dataSpec.httpMethod = i;
        if (bArr2 == null || bArr2.length == 0) {
            bArr2 = null;
        }
        dataSpec.httpBody = bArr2;
        dataSpec.postBody = dataSpec.httpBody;
        dataSpec.absoluteStreamPosition = j4;
        dataSpec.position = j5;
        dataSpec.length = j6;
        dataSpec.key = str;
        dataSpec.flags = i2;
    }

    public boolean isFlagSet(int i) {
        return (this.flags & i) == i;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DataSpec[");
        stringBuilder.append(getHttpMethodString());
        stringBuilder.append(" ");
        stringBuilder.append(this.uri);
        stringBuilder.append(", ");
        stringBuilder.append(Arrays.toString(this.httpBody));
        stringBuilder.append(", ");
        stringBuilder.append(this.absoluteStreamPosition);
        stringBuilder.append(", ");
        stringBuilder.append(this.position);
        stringBuilder.append(", ");
        stringBuilder.append(this.length);
        stringBuilder.append(", ");
        stringBuilder.append(this.key);
        stringBuilder.append(", ");
        stringBuilder.append(this.flags);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public final String getHttpMethodString() {
        return getStringForHttpMethod(this.httpMethod);
    }

    public static String getStringForHttpMethod(int i) {
        switch (i) {
            case 1:
                return HttpRequest.METHOD_GET;
            case 2:
                return HttpRequest.METHOD_POST;
            case 3:
                return HttpRequest.METHOD_HEAD;
            default:
                throw new AssertionError(i);
        }
    }

    public DataSpec subrange(long j) {
        long j2 = -1;
        if (this.length != -1) {
            j2 = this.length - j;
        }
        return subrange(j, j2);
    }

    public DataSpec subrange(long j, long j2) {
        DataSpec dataSpec = this;
        if (j == 0 && dataSpec.length == j2) {
            return dataSpec;
        }
        return new DataSpec(dataSpec.uri, dataSpec.httpMethod, dataSpec.httpBody, dataSpec.absoluteStreamPosition + j, dataSpec.position + j, j2, dataSpec.key, dataSpec.flags);
    }

    public DataSpec withUri(Uri uri) {
        return new DataSpec(uri, this.httpMethod, this.httpBody, this.absoluteStreamPosition, this.position, this.length, this.key, this.flags);
    }
}
