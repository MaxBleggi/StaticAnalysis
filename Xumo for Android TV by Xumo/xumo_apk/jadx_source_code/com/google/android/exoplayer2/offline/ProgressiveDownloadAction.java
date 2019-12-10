package com.google.android.exoplayer2.offline;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.offline.DownloadAction.Deserializer;
import com.google.android.exoplayer2.upstream.cache.CacheUtil;
import com.google.android.exoplayer2.util.Util;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ProgressiveDownloadAction extends DownloadAction {
    public static final Deserializer DESERIALIZER = new Deserializer(TYPE, 0) {
        public ProgressiveDownloadAction readFromStream(int i, DataInputStream dataInputStream) throws IOException {
            i = Uri.parse(dataInputStream.readUTF());
            boolean readBoolean = dataInputStream.readBoolean();
            byte[] bArr = new byte[dataInputStream.readInt()];
            dataInputStream.readFully(bArr);
            return new ProgressiveDownloadAction(i, readBoolean, bArr, dataInputStream.readBoolean() ? dataInputStream.readUTF() : null);
        }
    };
    private static final String TYPE = "progressive";
    private static final int VERSION = 0;
    @Nullable
    private final String customCacheKey;

    public static ProgressiveDownloadAction createDownloadAction(Uri uri, @Nullable byte[] bArr, @Nullable String str) {
        return new ProgressiveDownloadAction(uri, false, bArr, str);
    }

    public static ProgressiveDownloadAction createRemoveAction(Uri uri, @Nullable byte[] bArr, @Nullable String str) {
        return new ProgressiveDownloadAction(uri, true, bArr, str);
    }

    @Deprecated
    public ProgressiveDownloadAction(Uri uri, boolean z, @Nullable byte[] bArr, @Nullable String str) {
        super(TYPE, 0, uri, z, bArr);
        this.customCacheKey = str;
    }

    public ProgressiveDownloader createDownloader(DownloaderConstructorHelper downloaderConstructorHelper) {
        return new ProgressiveDownloader(this.uri, this.customCacheKey, downloaderConstructorHelper);
    }

    protected void writeToStream(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeUTF(this.uri.toString());
        dataOutputStream.writeBoolean(this.isRemoveAction);
        dataOutputStream.writeInt(this.data.length);
        dataOutputStream.write(this.data);
        boolean z = this.customCacheKey != null;
        dataOutputStream.writeBoolean(z);
        if (z) {
            dataOutputStream.writeUTF(this.customCacheKey);
        }
    }

    public boolean isSameMedia(DownloadAction downloadAction) {
        return (!(downloadAction instanceof ProgressiveDownloadAction) || getCacheKey().equals(((ProgressiveDownloadAction) downloadAction).getCacheKey()) == null) ? null : true;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return null;
        }
        return Util.areEqual(this.customCacheKey, ((ProgressiveDownloadAction) obj).customCacheKey);
    }

    public int hashCode() {
        return (super.hashCode() * 31) + (this.customCacheKey != null ? this.customCacheKey.hashCode() : 0);
    }

    private String getCacheKey() {
        return this.customCacheKey != null ? this.customCacheKey : CacheUtil.generateKey(this.uri);
    }
}
