package com.google.android.gms.internal.cast;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.RelativeLayout;
import com.google.android.gms.cast.framework.IntroductoryOverlay;
import com.google.android.gms.cast.framework.IntroductoryOverlay.Builder;
import com.google.android.gms.cast.framework.IntroductoryOverlay.OnOverlayDismissedListener;
import com.google.android.gms.cast.framework.R;
import com.google.android.gms.cast.framework.internal.featurehighlight.zza;
import com.google.android.gms.cast.framework.internal.featurehighlight.zzi;

public final class zzn extends RelativeLayout implements IntroductoryOverlay {
    private int color;
    private Activity zzim;
    private View zzin;
    private String zzip;
    private OnOverlayDismissedListener zziq;
    private final boolean zzjh;
    private zza zzji;
    private boolean zzjj;

    @TargetApi(15)
    public zzn(Builder builder) {
        super(builder.getActivity());
        this.zzim = builder.getActivity();
        this.zzjh = builder.zzaf();
        this.zziq = builder.zzad();
        this.zzin = builder.zzac();
        this.zzip = builder.zzag();
        this.color = builder.zzae();
    }

    private final void reset() {
        removeAllViews();
        this.zzim = null;
        this.zziq = null;
        this.zzin = null;
        this.zzji = null;
        this.zzip = null;
        this.color = 0;
        this.zzjj = false;
    }

    public final void show() {
        if (!(this.zzim == null || this.zzin == null)) {
            if (!this.zzjj) {
                if (!zzg(this.zzim)) {
                    if (this.zzjh && IntroductoryOverlay.zza.zze(this.zzim)) {
                        reset();
                        return;
                    }
                    this.zzji = new zza(this.zzim);
                    if (this.color != 0) {
                        this.zzji.zzg(this.color);
                    }
                    addView(this.zzji);
                    zzi com_google_android_gms_cast_framework_internal_featurehighlight_zzi = (zzi) this.zzim.getLayoutInflater().inflate(R.layout.cast_help_text, this.zzji, false);
                    com_google_android_gms_cast_framework_internal_featurehighlight_zzi.setText(this.zzip, null);
                    this.zzji.zza(com_google_android_gms_cast_framework_internal_featurehighlight_zzi);
                    this.zzji.zza(this.zzin, null, true, new zzo(this));
                    this.zzjj = true;
                    ((ViewGroup) this.zzim.getWindow().getDecorView()).addView(this);
                    this.zzji.zza(null);
                }
            }
        }
    }

    public final void remove() {
        if (this.zzjj) {
            ((ViewGroup) this.zzim.getWindow().getDecorView()).removeView(this);
            reset();
        }
    }

    static boolean zzg(Context context) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        return (accessibilityManager == null || !accessibilityManager.isEnabled() || accessibilityManager.isTouchExplorationEnabled() == null) ? null : true;
    }
}
