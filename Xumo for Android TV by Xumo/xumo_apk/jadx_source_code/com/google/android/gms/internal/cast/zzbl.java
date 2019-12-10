package com.google.android.gms.internal.cast;

import android.view.View;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.cast.framework.media.uicontroller.UIController;

public final class zzbl extends UIController {
    private final View view;
    private final int zzsg;

    public zzbl(View view, int i) {
        this.view = view;
        this.zzsg = i;
    }

    public final void onSessionConnected(CastSession castSession) {
        super.onSessionConnected(castSession);
        zzcp();
    }

    public final void onSessionEnded() {
        this.view.setVisibility(this.zzsg);
        super.onSessionEnded();
    }

    public final void onMediaStatusUpdated() {
        zzcp();
    }

    private final void zzcp() {
        RemoteMediaClient remoteMediaClient = getRemoteMediaClient();
        if (remoteMediaClient != null) {
            if (remoteMediaClient.hasMediaSession()) {
                this.view.setVisibility(0);
                return;
            }
        }
        this.view.setVisibility(this.zzsg);
    }
}
