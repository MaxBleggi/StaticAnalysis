package com.google.android.gms.internal.cast;

import android.graphics.drawable.ColorDrawable;
import android.widget.SeekBar;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.cast.framework.media.uicontroller.UIController;
import com.google.android.gms.common.util.PlatformVersion;

public final class zzau extends UIController {
    private final SeekBar zzrp;
    private final SeekBar zzrq;

    public zzau(SeekBar seekBar, SeekBar seekBar2) {
        this.zzrp = seekBar;
        this.zzrq = seekBar2;
        this.zzrp.setClickable(false);
        if (PlatformVersion.isAtLeastKitKat() != null) {
            this.zzrp.setThumb(null);
        } else {
            this.zzrp.setThumb(new ColorDrawable(0));
        }
        this.zzrp.setMax(1);
        this.zzrp.setProgress(1);
        this.zzrp.setOnTouchListener(new zzav(this));
    }

    public final void onSessionConnected(CastSession castSession) {
        super.onSessionConnected(castSession);
        zzcr();
    }

    public final void onMediaStatusUpdated() {
        zzcr();
    }

    private final void zzcr() {
        RemoteMediaClient remoteMediaClient = getRemoteMediaClient();
        if (remoteMediaClient != null && remoteMediaClient.hasMediaSession()) {
            boolean isLiveStream = remoteMediaClient.isLiveStream();
            int i = 4;
            this.zzrp.setVisibility(isLiveStream ? 0 : 4);
            SeekBar seekBar = this.zzrq;
            if (!isLiveStream) {
                i = 0;
            }
            seekBar.setVisibility(i);
        }
    }
}
