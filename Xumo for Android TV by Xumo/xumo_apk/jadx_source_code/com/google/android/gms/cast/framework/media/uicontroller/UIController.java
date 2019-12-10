package com.google.android.gms.cast.framework.media.uicontroller;

import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;

public class UIController {
    private RemoteMediaClient zzig;

    public void onMediaStatusUpdated() {
    }

    public void onSendingRemoteMediaRequest() {
    }

    protected RemoteMediaClient getRemoteMediaClient() {
        return this.zzig;
    }

    public void onSessionConnected(CastSession castSession) {
        if (castSession != null) {
            this.zzig = castSession.getRemoteMediaClient();
        } else {
            this.zzig = null;
        }
    }

    public void onSessionEnded() {
        this.zzig = null;
    }
}
