package com.google.android.gms.internal.cast;

import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.cast.framework.media.RemoteMediaClient.ProgressListener;
import com.google.android.gms.cast.framework.media.uicontroller.UIController;

public final class zzbi extends UIController implements ProgressListener {
    private final TextView zzsi;
    private final String zzsj;
    private final View zzsk;

    public zzbi(TextView textView, String str, View view) {
        this.zzsi = textView;
        this.zzsj = str;
        this.zzsk = view;
    }

    public final void onSessionConnected(CastSession castSession) {
        super.onSessionConnected(castSession);
        if (getRemoteMediaClient() != null) {
            getRemoteMediaClient().addProgressListener(this, 1000);
        }
        zza(-1, true);
    }

    public final void onSessionEnded() {
        this.zzsi.setText(this.zzsj);
        if (getRemoteMediaClient() != null) {
            getRemoteMediaClient().removeProgressListener(this);
        }
        super.onSessionEnded();
    }

    public final void onMediaStatusUpdated() {
        zza(-1, true);
    }

    public final void onProgressUpdated(long j, long j2) {
        zza(j2, 0);
    }

    private final void zza(long j, boolean z) {
        RemoteMediaClient remoteMediaClient = getRemoteMediaClient();
        if (remoteMediaClient == null || !remoteMediaClient.hasMediaSession()) {
            this.zzsi.setVisibility(0);
            this.zzsi.setText(this.zzsj);
            if (this.zzsk != null) {
                this.zzsk.setVisibility(4);
            }
        } else if (remoteMediaClient.isLiveStream()) {
            this.zzsi.setText(this.zzsj);
            if (this.zzsk != null) {
                this.zzsi.setVisibility(4);
                this.zzsk.setVisibility(0);
            }
        } else {
            if (z) {
                j = remoteMediaClient.getStreamDuration();
            }
            this.zzsi.setVisibility(0);
            this.zzsi.setText(DateUtils.formatElapsedTime(j / 1000));
            if (this.zzsk != null) {
                this.zzsk.setVisibility(4);
            }
        }
    }
}
