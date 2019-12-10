package com.google.android.gms.internal.cast;

import android.widget.SeekBar;
import com.google.android.gms.cast.MediaStatus;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.cast.framework.media.RemoteMediaClient.ProgressListener;
import com.google.android.gms.cast.framework.media.uicontroller.UIController;

public final class zzbd extends UIController implements ProgressListener {
    private boolean zzmq = true;
    private final SeekBar zzrq;
    private final long zzsf;

    public zzbd(SeekBar seekBar, long j) {
        this.zzrq = seekBar;
        this.zzsf = j;
        this.zzrq.setEnabled(0);
    }

    public final void onSendingRemoteMediaRequest() {
    }

    public final void onSessionConnected(CastSession castSession) {
        super.onSessionConnected(castSession);
        castSession = getRemoteMediaClient();
        if (castSession != null) {
            castSession.addProgressListener(this, this.zzsf);
            if (castSession.hasMediaSession()) {
                this.zzrq.setMax((int) castSession.getStreamDuration());
                this.zzrq.setProgress((int) castSession.getApproximateStreamPosition());
                this.zzrq.setEnabled(true);
                return;
            }
        }
        this.zzrq.setMax(1);
        this.zzrq.setProgress(0);
        this.zzrq.setEnabled(false);
    }

    public final void onSessionEnded() {
        if (getRemoteMediaClient() != null) {
            getRemoteMediaClient().removeProgressListener(this);
        }
        this.zzrq.setMax(1);
        this.zzrq.setProgress(0);
        this.zzrq.setEnabled(false);
        super.onSessionEnded();
    }

    public final void onProgressUpdated(long j, long j2) {
        if (this.zzmq) {
            MediaStatus mediaStatus;
            RemoteMediaClient remoteMediaClient = getRemoteMediaClient();
            if (remoteMediaClient == null) {
                mediaStatus = null;
            } else {
                mediaStatus = remoteMediaClient.getMediaStatus();
            }
            Object obj = (mediaStatus == null || !mediaStatus.isPlayingAd()) ? null : 1;
            if (obj != null) {
                this.zzrq.setEnabled(false);
            } else {
                this.zzrq.setProgress((int) j);
                this.zzrq.setEnabled(true);
            }
            this.zzrq.setMax((int) j2);
        }
    }

    public final void zzk(boolean z) {
        this.zzmq = z;
    }
}
