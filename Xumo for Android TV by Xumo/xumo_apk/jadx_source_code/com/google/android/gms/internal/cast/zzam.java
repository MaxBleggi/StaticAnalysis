package com.google.android.gms.internal.cast;

import android.content.Intent;
import android.support.v4.media.session.MediaSessionCompat.Callback;
import android.view.KeyEvent;

final class zzam extends Callback {
    private final /* synthetic */ zzai zzqz;

    zzam(zzai com_google_android_gms_internal_cast_zzai) {
        this.zzqz = com_google_android_gms_internal_cast_zzai;
    }

    public final boolean onMediaButtonEvent(Intent intent) {
        KeyEvent keyEvent = (KeyEvent) intent.getParcelableExtra("android.intent.extra.KEY_EVENT");
        if (keyEvent != null && (keyEvent.getKeyCode() == 127 || keyEvent.getKeyCode() == 126)) {
            this.zzqz.zzig.togglePlayback();
        }
        return true;
    }

    public final void onPlay() {
        this.zzqz.zzig.togglePlayback();
    }

    public final void onPause() {
        this.zzqz.zzig.togglePlayback();
    }

    public final void onStop() {
        if (this.zzqz.zzig.isLiveStream()) {
            this.zzqz.zzig.togglePlayback();
        }
    }
}
