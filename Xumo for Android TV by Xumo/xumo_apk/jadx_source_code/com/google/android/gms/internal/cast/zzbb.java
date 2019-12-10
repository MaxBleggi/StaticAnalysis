package com.google.android.gms.internal.cast;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.R;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.cast.framework.media.uicontroller.UIController;

public final class zzbb extends UIController {
    private final ImageView zzrj;
    private final View zzrw;
    private final boolean zzrx;
    private final Drawable zzry;
    private final String zzrz;
    private final Drawable zzsa;
    private final String zzsb;
    private final Drawable zzsc;
    private final String zzsd;

    public zzbb(@NonNull ImageView imageView, Context context, @NonNull Drawable drawable, @NonNull Drawable drawable2, Drawable drawable3, View view, boolean z) {
        this.zzrj = imageView;
        this.zzry = drawable;
        this.zzsa = drawable2;
        if (drawable3 != null) {
            drawable2 = drawable3;
        }
        this.zzsc = drawable2;
        this.zzrz = context.getString(R.string.cast_play);
        this.zzsb = context.getString(R.string.cast_pause);
        this.zzsd = context.getString(R.string.cast_stop);
        this.zzrw = view;
        this.zzrx = z;
        this.zzrj.setEnabled(null);
    }

    public final void onSessionConnected(CastSession castSession) {
        super.onSessionConnected(castSession);
        zzcp();
    }

    public final void onSessionEnded() {
        this.zzrj.setEnabled(false);
        super.onSessionEnded();
    }

    public final void onMediaStatusUpdated() {
        zzcp();
    }

    public final void onSendingRemoteMediaRequest() {
        zzj(true);
    }

    private final void zzcp() {
        RemoteMediaClient remoteMediaClient = getRemoteMediaClient();
        if (remoteMediaClient != null) {
            if (remoteMediaClient.hasMediaSession()) {
                if (remoteMediaClient.isPaused()) {
                    zza(this.zzry, this.zzrz);
                    return;
                } else if (remoteMediaClient.isPlaying()) {
                    if (remoteMediaClient.isLiveStream()) {
                        zza(this.zzsc, this.zzsd);
                        return;
                    } else {
                        zza(this.zzsa, this.zzsb);
                        return;
                    }
                } else if (remoteMediaClient.isBuffering()) {
                    zzj(false);
                    return;
                } else {
                    if (remoteMediaClient.isLoadingNextItem()) {
                        zzj(true);
                    }
                    return;
                }
            }
        }
        this.zzrj.setEnabled(false);
    }

    private final void zza(Drawable drawable, String str) {
        this.zzrj.setImageDrawable(drawable);
        this.zzrj.setContentDescription(str);
        this.zzrj.setVisibility(null);
        this.zzrj.setEnabled(true);
        if (this.zzrw != null) {
            this.zzrw.setVisibility(8);
        }
    }

    private final void zzj(boolean z) {
        int i = 0;
        if (this.zzrw != null) {
            this.zzrw.setVisibility(0);
        }
        ImageView imageView = this.zzrj;
        if (this.zzrx) {
            i = 4;
        }
        imageView.setVisibility(i);
        this.zzrj.setEnabled(z ^ 1);
    }
}
