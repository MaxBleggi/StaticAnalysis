package com.google.android.gms.internal.cast;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import androidx.collection.SimpleArrayMap;

public class zzem extends AnimatorListenerAdapter {
    private SimpleArrayMap<Animator, Boolean> zzzp = new SimpleArrayMap();

    public void onAnimationStart(Animator animator) {
        this.zzzp.put(animator, Boolean.valueOf(false));
    }

    public void onAnimationCancel(Animator animator) {
        this.zzzp.put(animator, Boolean.valueOf(true));
    }

    protected final boolean zzb(Animator animator) {
        return (!this.zzzp.containsKey(animator) || ((Boolean) this.zzzp.get(animator)).booleanValue() == null) ? null : true;
    }
}
