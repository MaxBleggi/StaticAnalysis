package com.google.android.gms.internal.cast;

import android.animation.Animator;
import androidx.annotation.Nullable;

public final class zzen extends zzem {
    protected final Animator animator;
    private final Runnable zzzq;
    private final int zzzr;
    private int zzzs;
    private zzer zzzt = new zzeo(this);

    public static void zza(Animator animator, int i, @Nullable Runnable runnable) {
        animator.addListener(new zzen(animator, -1, null));
    }

    private zzen(Animator animator, int i, @Nullable Runnable runnable) {
        this.animator = animator;
        this.zzzr = -1;
        this.zzzq = null;
    }

    public final void onAnimationEnd(Animator animator) {
        if (zzb(animator) == null) {
            zzep.zzdu().zza(this.zzzt);
        }
    }

    private final boolean zzdt() {
        if (this.zzzr != -1 && this.zzzs >= this.zzzr) {
            return true;
        }
        return false;
    }
}
