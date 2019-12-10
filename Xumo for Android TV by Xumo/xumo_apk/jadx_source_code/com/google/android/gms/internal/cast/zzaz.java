package com.google.android.gms.internal.cast;

import android.content.Context;
import android.widget.ImageView;
import com.google.android.gms.cast.Cast.Listener;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.R;
import com.google.android.gms.cast.framework.Session;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.cast.framework.media.uicontroller.UIController;

public final class zzaz extends UIController {
    private Listener zzaj;
    private final Context zzhh;
    private final ImageView zzrj;
    private final String zzrt = this.zzhh.getString(R.string.cast_mute);
    private final String zzru = this.zzhh.getString(R.string.cast_unmute);

    public zzaz(ImageView imageView, Context context) {
        this.zzrj = imageView;
        this.zzhh = context.getApplicationContext();
        this.zzrj.setEnabled(null);
        this.zzaj = null;
    }

    public final void onSessionConnected(CastSession castSession) {
        if (this.zzaj == null) {
            this.zzaj = new zzba(this);
        }
        super.onSessionConnected(castSession);
        castSession.addCastListener(this.zzaj);
        zzcp();
    }

    public final void onSessionEnded() {
        this.zzrj.setEnabled(false);
        CastSession currentCastSession = CastContext.getSharedInstance(this.zzhh).getSessionManager().getCurrentCastSession();
        if (!(currentCastSession == null || this.zzaj == null)) {
            currentCastSession.removeCastListener(this.zzaj);
        }
        super.onSessionEnded();
    }

    public final void onMediaStatusUpdated() {
        zzcp();
    }

    public final void onSendingRemoteMediaRequest() {
        this.zzrj.setEnabled(false);
    }

    private final void zzi(boolean z) {
        this.zzrj.setSelected(z);
        this.zzrj.setContentDescription(z ? this.zzrt : this.zzru);
    }

    protected final void zzcp() {
        Session currentCastSession = CastContext.getSharedInstance(this.zzhh).getSessionManager().getCurrentCastSession();
        if (currentCastSession == null || !currentCastSession.isConnected()) {
            this.zzrj.setEnabled(false);
            return;
        }
        RemoteMediaClient remoteMediaClient = getRemoteMediaClient();
        if (remoteMediaClient != null) {
            if (remoteMediaClient.hasMediaSession()) {
                this.zzrj.setEnabled(true);
                if (currentCastSession.isMute()) {
                    zzi(false);
                } else {
                    zzi(true);
                }
            }
        }
        this.zzrj.setEnabled(false);
        if (currentCastSession.isMute()) {
            zzi(false);
        } else {
            zzi(true);
        }
    }
}
