package com.google.android.gms.cast;

import com.google.android.gms.internal.cast.zzdk;

final class zzat implements zzdk {
    private final /* synthetic */ RemoteMediaPlayer zzfr;

    zzat(RemoteMediaPlayer remoteMediaPlayer) {
        this.zzfr = remoteMediaPlayer;
    }

    public final void onAdBreakStatusUpdated() {
    }

    public final void zza(int[] iArr) {
    }

    public final void zza(int[] iArr, int i) {
    }

    public final void zzb(int[] iArr) {
    }

    public final void zzb(MediaQueueItem[] mediaQueueItemArr) {
    }

    public final void zzc(int[] iArr) {
    }

    public final void onStatusUpdated() {
        this.zzfr.onStatusUpdated();
    }

    public final void onMetadataUpdated() {
        this.zzfr.onMetadataUpdated();
    }

    public final void onQueueStatusUpdated() {
        this.zzfr.onQueueStatusUpdated();
    }

    public final void onPreloadStatusUpdated() {
        this.zzfr.onPreloadStatusUpdated();
    }
}
