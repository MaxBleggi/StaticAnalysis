package com.google.android.gms.common.api.internal;

import android.app.Activity;
import androidx.annotation.MainThread;
import androidx.annotation.VisibleForTesting;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public final class zaa extends ActivityLifecycleObserver {
    private final WeakReference<zaa> zack;

    @VisibleForTesting(otherwise = 2)
    static class zaa extends LifecycleCallback {
        private List<Runnable> zacl = new ArrayList();

        private static zaa zaa(Activity activity) {
            zaa com_google_android_gms_common_api_internal_zaa_zaa;
            synchronized (activity) {
                LifecycleFragment fragment = LifecycleCallback.getFragment(activity);
                com_google_android_gms_common_api_internal_zaa_zaa = (zaa) fragment.getCallbackOrNull("LifecycleObserverOnStop", zaa.class);
                if (com_google_android_gms_common_api_internal_zaa_zaa == null) {
                    com_google_android_gms_common_api_internal_zaa_zaa = new zaa(fragment);
                }
            }
            return com_google_android_gms_common_api_internal_zaa_zaa;
        }

        private zaa(LifecycleFragment lifecycleFragment) {
            super(lifecycleFragment);
            this.mLifecycleFragment.addCallback("LifecycleObserverOnStop", this);
        }

        private final synchronized void zaa(Runnable runnable) {
            this.zacl.add(runnable);
        }

        @MainThread
        public void onStop() {
            synchronized (this) {
                List<Runnable> list = this.zacl;
                this.zacl = new ArrayList();
            }
            for (Runnable run : list) {
                run.run();
            }
        }
    }

    public zaa(Activity activity) {
        this(zaa.zaa(activity));
    }

    @VisibleForTesting(otherwise = 2)
    private zaa(zaa com_google_android_gms_common_api_internal_zaa_zaa) {
        this.zack = new WeakReference(com_google_android_gms_common_api_internal_zaa_zaa);
    }

    public final ActivityLifecycleObserver onStopCallOnce(Runnable runnable) {
        zaa com_google_android_gms_common_api_internal_zaa_zaa = (zaa) this.zack.get();
        if (com_google_android_gms_common_api_internal_zaa_zaa != null) {
            com_google_android_gms_common_api_internal_zaa_zaa.zaa(runnable);
            return this;
        }
        throw new IllegalStateException("The target activity has already been GC'd");
    }
}
