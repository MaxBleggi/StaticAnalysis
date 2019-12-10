package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;

public final class ByteArrayDataSource extends BaseDataSource {
    private int bytesRemaining;
    private final byte[] data;
    private boolean opened;
    private int readPosition;
    @Nullable
    private Uri uri;

    public ByteArrayDataSource(byte[] bArr) {
        boolean z = false;
        super(false);
        Assertions.checkNotNull(bArr);
        if (bArr.length > 0) {
            z = true;
        }
        Assertions.checkArgument(z);
        this.data = bArr;
    }

    public long open(DataSpec dataSpec) throws IOException {
        this.uri = dataSpec.uri;
        transferInitializing(dataSpec);
        this.readPosition = (int) dataSpec.position;
        this.bytesRemaining = (int) (dataSpec.length == -1 ? ((long) this.data.length) - dataSpec.position : dataSpec.length);
        if (this.bytesRemaining <= 0 || this.readPosition + this.bytesRemaining > this.data.length) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsatisfiable range: [");
            stringBuilder.append(this.readPosition);
            stringBuilder.append(", ");
            stringBuilder.append(dataSpec.length);
            stringBuilder.append("], length: ");
            stringBuilder.append(this.data.length);
            throw new IOException(stringBuilder.toString());
        }
        this.opened = true;
        transferStarted(dataSpec);
        return (long) this.bytesRemaining;
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (i2 == 0) {
            return null;
        }
        if (this.bytesRemaining == 0) {
            return -1;
        }
        i2 = Math.min(i2, this.bytesRemaining);
        System.arraycopy(this.data, this.readPosition, bArr, i, i2);
        this.readPosition += i2;
        this.bytesRemaining -= i2;
        bytesTransferred(i2);
        return i2;
    }

    @Nullable
    public Uri getUri() {
        return this.uri;
    }

    public void close() throws IOException {
        if (this.opened) {
            this.opened = false;
            transferEnded();
        }
        this.uri = null;
    }
}
