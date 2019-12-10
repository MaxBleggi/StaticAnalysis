package com.google.android.gms.cast.framework.media.uicontroller;

import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

final class zzf implements OnSeekBarChangeListener {
    private final /* synthetic */ UIMediaController zzrg;

    zzf(UIMediaController uIMediaController) {
        this.zzrg = uIMediaController;
    }

    public final void onStopTrackingTouch(SeekBar seekBar) {
        this.zzrg.onSeekBarStopTrackingTouch(seekBar);
    }

    public final void onStartTrackingTouch(SeekBar seekBar) {
        this.zzrg.onSeekBarStartTrackingTouch(seekBar);
    }

    public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        this.zzrg.onSeekBarProgressChanged(seekBar, i, z);
    }
}
