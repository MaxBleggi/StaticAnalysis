package com.google.android.gms.internal.cast;

import android.widget.ProgressBar;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.cast.framework.media.RemoteMediaClient.ProgressListener;
import com.google.android.gms.cast.framework.media.uicontroller.UIController;

public final class zzbc extends UIController implements ProgressListener {
    private final ProgressBar zzse;
    private final long zzsf;

    public zzbc(ProgressBar progressBar, long j) {
        this.zzse = progressBar;
        this.zzsf = j;
    }

    public final void onSessionConnected(CastSession castSession) {
        super.onSessionConnected(castSession);
        castSession = getRemoteMediaClient();
        if (castSession != null) {
            castSession.addProgressListener(this, this.zzsf);
            if (castSession.hasMediaSession()) {
                this.zzse.setMax((int) castSession.getStreamDuration());
                this.zzse.setProgress((int) castSession.getApproximateStreamPosition());
                return;
            }
            this.zzse.setMax(1);
            this.zzse.setProgress(0);
        }
    }

    public final void onSessionEnded() {
        if (getRemoteMediaClient() != null) {
            getRemoteMediaClient().removeProgressListener(this);
        }
        this.zzse.setMax(1);
        this.zzse.setProgress(0);
        super.onSessionEnded();
    }

    public final void onMediaStatusUpdated() {
        RemoteMediaClient remoteMediaClient = getRemoteMediaClient();
        if (remoteMediaClient == null || !remoteMediaClient.hasMediaSession()) {
            this.zzse.setMax(1);
            this.zzse.setProgress(0);
        }
    }

    public final void onProgressUpdated(long j, long j2) {
        this.zzse.setMax((int) j2);
        this.zzse.setProgress((int) j);
    }
}
