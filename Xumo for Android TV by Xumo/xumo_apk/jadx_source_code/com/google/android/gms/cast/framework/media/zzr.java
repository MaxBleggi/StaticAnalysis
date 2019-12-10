package com.google.android.gms.cast.framework.media;

import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.MediaStatus;
import com.google.android.gms.cast.framework.media.RemoteMediaClient.Callback;
import com.google.android.gms.cast.framework.media.RemoteMediaClient.Listener;
import com.google.android.gms.internal.cast.zzdk;
import java.util.List;

final class zzr implements zzdk {
    private final /* synthetic */ RemoteMediaClient zzpe;

    zzr(RemoteMediaClient remoteMediaClient) {
        this.zzpe = remoteMediaClient;
    }

    public final void onStatusUpdated() {
        zzcf();
        this.zzpe.zzce();
        for (Listener onStatusUpdated : this.zzpe.zzoz) {
            onStatusUpdated.onStatusUpdated();
        }
        for (Callback onStatusUpdated2 : this.zzpe.zzpa) {
            onStatusUpdated2.onStatusUpdated();
        }
    }

    public final void onMetadataUpdated() {
        zzcf();
        for (Listener onMetadataUpdated : this.zzpe.zzoz) {
            onMetadataUpdated.onMetadataUpdated();
        }
        for (Callback onMetadataUpdated2 : this.zzpe.zzpa) {
            onMetadataUpdated2.onMetadataUpdated();
        }
    }

    public final void onQueueStatusUpdated() {
        for (Listener onQueueStatusUpdated : this.zzpe.zzoz) {
            onQueueStatusUpdated.onQueueStatusUpdated();
        }
        for (Callback onQueueStatusUpdated2 : this.zzpe.zzpa) {
            onQueueStatusUpdated2.onQueueStatusUpdated();
        }
    }

    public final void onPreloadStatusUpdated() {
        for (Listener onPreloadStatusUpdated : this.zzpe.zzoz) {
            onPreloadStatusUpdated.onPreloadStatusUpdated();
        }
        for (Callback onPreloadStatusUpdated2 : this.zzpe.zzpa) {
            onPreloadStatusUpdated2.onPreloadStatusUpdated();
        }
    }

    public final void onAdBreakStatusUpdated() {
        for (Listener onAdBreakStatusUpdated : this.zzpe.zzoz) {
            onAdBreakStatusUpdated.onAdBreakStatusUpdated();
        }
        for (Callback onAdBreakStatusUpdated2 : this.zzpe.zzpa) {
            onAdBreakStatusUpdated2.onAdBreakStatusUpdated();
        }
    }

    public final void zza(int[] iArr) {
        for (Callback zza : this.zzpe.zzpa) {
            zza.zza(iArr);
        }
    }

    public final void zza(int[] iArr, int i) {
        for (Callback zza : this.zzpe.zzpa) {
            zza.zza(iArr, i);
        }
    }

    public final void zzb(int[] iArr) {
        for (Callback zzb : this.zzpe.zzpa) {
            zzb.zzb(iArr);
        }
    }

    public final void zzc(int[] iArr) {
        for (Callback zzc : this.zzpe.zzpa) {
            zzc.zzc(iArr);
        }
    }

    public final void zzb(MediaQueueItem[] mediaQueueItemArr) {
        for (Callback zzb : this.zzpe.zzpa) {
            zzb.zzb(mediaQueueItemArr);
        }
    }

    private final void zzcf() {
        if (this.zzpe.zzpd != null) {
            MediaStatus mediaStatus = this.zzpe.getMediaStatus();
            if (mediaStatus != null) {
                mediaStatus.zzf(this.zzpe.zzpd.parseIsPlayingAdFromMediaStatus(mediaStatus));
                List parseAdBreaksFromMediaStatus = this.zzpe.zzpd.parseAdBreaksFromMediaStatus(mediaStatus);
                MediaInfo mediaInfo = this.zzpe.getMediaInfo();
                if (mediaInfo != null) {
                    mediaInfo.zzb(parseAdBreaksFromMediaStatus);
                }
            }
        }
    }
}
