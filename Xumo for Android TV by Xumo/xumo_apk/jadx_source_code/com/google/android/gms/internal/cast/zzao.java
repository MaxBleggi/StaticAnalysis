package com.google.android.gms.internal.cast;

import android.content.Context;
import android.view.View;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaTrack;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.R;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.cast.framework.media.uicontroller.UIController;
import java.util.List;

public final class zzao extends UIController {
    private final View view;
    private final String zznm;
    private final String zzri;

    public zzao(View view, Context context) {
        this.view = view;
        this.zznm = context.getString(R.string.cast_closed_captions);
        this.zzri = context.getString(R.string.cast_closed_captions_unavailable);
        this.view.setEnabled(null);
    }

    public final void onSessionConnected(CastSession castSession) {
        super.onSessionConnected(castSession);
        this.view.setEnabled(true);
        zzcp();
    }

    public final void onSessionEnded() {
        this.view.setEnabled(false);
        super.onSessionEnded();
    }

    public final void onMediaStatusUpdated() {
        zzcp();
    }

    public final void onSendingRemoteMediaRequest() {
        this.view.setEnabled(false);
    }

    private final void zzcp() {
        RemoteMediaClient remoteMediaClient = getRemoteMediaClient();
        if (remoteMediaClient != null && remoteMediaClient.hasMediaSession()) {
            Object obj;
            MediaInfo mediaInfo = remoteMediaClient.getMediaInfo();
            if (mediaInfo != null) {
                List<MediaTrack> mediaTracks = mediaInfo.getMediaTracks();
                if (mediaTracks != null) {
                    if (!mediaTracks.isEmpty()) {
                        int i = 0;
                        for (MediaTrack mediaTrack : mediaTracks) {
                            if (mediaTrack.getType() == 2) {
                                i++;
                                if (i > 1) {
                                }
                            } else if (mediaTrack.getType() == 1) {
                            }
                            obj = 1;
                        }
                    }
                }
            }
            obj = null;
            if (obj != null) {
                if (!remoteMediaClient.isPlayingAd()) {
                    this.view.setEnabled(true);
                    this.view.setContentDescription(this.zznm);
                    return;
                }
            }
        }
        this.view.setEnabled(false);
        this.view.setContentDescription(this.zzri);
    }
}
