package com.google.android.gms.internal.cast;

import android.text.format.DateUtils;
import android.widget.TextView;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.media.RemoteMediaClient.ProgressListener;
import com.google.android.gms.cast.framework.media.uicontroller.UIController;

public final class zzbj extends UIController implements ProgressListener {
    private boolean zzmq = true;
    private final long zzsf;
    private final TextView zzsi;
    private final String zzsl;

    public zzbj(TextView textView, long j, String str) {
        this.zzsi = textView;
        this.zzsf = j;
        this.zzsl = str;
    }

    public final void onSessionConnected(CastSession castSession) {
        super.onSessionConnected(castSession);
        castSession = getRemoteMediaClient();
        if (castSession != null) {
            castSession.addProgressListener(this, this.zzsf);
            if (castSession.hasMediaSession()) {
                this.zzsi.setText(DateUtils.formatElapsedTime(castSession.getApproximateStreamPosition() / 1000));
                return;
            }
            this.zzsi.setText(this.zzsl);
        }
    }

    public final void onSessionEnded() {
        this.zzsi.setText(this.zzsl);
        if (getRemoteMediaClient() != null) {
            getRemoteMediaClient().removeProgressListener(this);
        }
        super.onSessionEnded();
    }

    public final void onProgressUpdated(long j, long j2) {
        if (this.zzmq != null) {
            this.zzsi.setText(DateUtils.formatElapsedTime(j / 1000));
        }
    }

    public final void zzc(long j) {
        this.zzsi.setText(DateUtils.formatElapsedTime(j / 1000));
    }

    public final void zzk(boolean z) {
        this.zzmq = z;
    }
}
