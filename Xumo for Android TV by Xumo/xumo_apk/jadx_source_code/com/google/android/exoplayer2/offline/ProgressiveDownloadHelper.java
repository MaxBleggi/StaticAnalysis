package com.google.android.exoplayer2.offline;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.source.TrackGroupArray;
import java.util.List;

public final class ProgressiveDownloadHelper extends DownloadHelper {
    @Nullable
    private final String customCacheKey;
    private final Uri uri;

    public int getPeriodCount() {
        return 1;
    }

    protected void prepareInternal() {
    }

    public ProgressiveDownloadHelper(Uri uri) {
        this(uri, null);
    }

    public ProgressiveDownloadHelper(Uri uri, @Nullable String str) {
        this.uri = uri;
        this.customCacheKey = str;
    }

    public TrackGroupArray getTrackGroups(int i) {
        return TrackGroupArray.EMPTY;
    }

    public ProgressiveDownloadAction getDownloadAction(@Nullable byte[] bArr, List<TrackKey> list) {
        return ProgressiveDownloadAction.createDownloadAction(this.uri, bArr, this.customCacheKey);
    }

    public ProgressiveDownloadAction getRemoveAction(@Nullable byte[] bArr) {
        return ProgressiveDownloadAction.createRemoveAction(this.uri, bArr, this.customCacheKey);
    }
}
