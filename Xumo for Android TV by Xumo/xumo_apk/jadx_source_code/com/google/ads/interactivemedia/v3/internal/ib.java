package com.google.ads.interactivemedia.v3.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import androidx.core.view.ViewCompat;
import com.google.android.exoplayer2.util.MimeTypes;

/* compiled from: IMASDK */
public class ib implements com.google.ads.interactivemedia.v3.internal.jd.a {
    private final jd a;
    private String b;
    private View c;
    private b d;
    private a e;
    private Activity f;
    private boolean g;

    @TargetApi(14)
    /* compiled from: IMASDK */
    protected class a implements ActivityLifecycleCallbacks {
        final /* synthetic */ ib a;

        protected a(ib ibVar) {
            this.a = ibVar;
        }

        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        public void onActivityStarted(Activity activity) {
        }

        public void onActivityStopped(Activity activity) {
        }

        public void onActivityResumed(Activity activity) {
            if (this.a.f == activity) {
                this.a.a.b(new jc(com.google.ads.interactivemedia.v3.internal.jc.b.activityMonitor, com.google.ads.interactivemedia.v3.internal.jc.c.appStateChanged, this.a.b, this.a.a("", "", "", "active")));
            }
        }

        public void onActivityPaused(Activity activity) {
            if (this.a.f == null || this.a.f == activity) {
                this.a.f = activity;
                this.a.a.b(new jc(com.google.ads.interactivemedia.v3.internal.jc.b.activityMonitor, com.google.ads.interactivemedia.v3.internal.jc.c.appStateChanged, this.a.b, this.a.a("", "", "", "inactive")));
            }
        }

        public void onActivityDestroyed(Activity activity) {
            if (this.a.f == activity) {
                this.a.f = null;
                activity = this.a.j();
                if (activity != null) {
                    activity.unregisterActivityLifecycleCallbacks(this.a.e);
                }
            }
        }
    }

    /* compiled from: IMASDK */
    public interface b {
        long a();
    }

    /* compiled from: IMASDK */
    private static class c implements b {
        private c() {
        }

        public long a() {
            return System.currentTimeMillis();
        }
    }

    private static int a(int i, float f) {
        return (int) Math.ceil((double) (((float) i) / f));
    }

    private static com.google.ads.interactivemedia.v3.impl.data.a.a a(com.google.ads.interactivemedia.v3.impl.data.a.a aVar, float f) {
        return com.google.ads.interactivemedia.v3.impl.data.a.a.create(a(aVar.left(), f), a(aVar.top(), f), a(aVar.height(), f), a(aVar.width(), f));
    }

    private DisplayMetrics i() {
        return this.c.getContext().getResources().getDisplayMetrics();
    }

    public ib(String str, jd jdVar, View view) {
        this(str, jdVar, view, new c());
    }

    protected ib(String str, jd jdVar, View view, b bVar) {
        this.b = str;
        this.a = jdVar;
        this.c = view;
        this.d = bVar;
        this.f = null;
        this.e = null;
        this.g = null;
    }

    protected void a(boolean z) {
        this.g = z;
    }

    private Application j() {
        Context applicationContext = this.c.getContext().getApplicationContext();
        return applicationContext instanceof Application ? (Application) applicationContext : null;
    }

    public void a() {
        this.a.a((com.google.ads.interactivemedia.v3.internal.jd.a) this, this.b);
    }

    public void b() {
        this.a.b(this.b);
    }

    @TargetApi(14)
    public void c() {
        if (VERSION.SDK_INT >= 14 && this.g) {
            Application j = j();
            if (j != null) {
                this.e = new a(this);
                j.registerActivityLifecycleCallbacks(this.e);
            }
        }
    }

    @TargetApi(14)
    public void d() {
        if (VERSION.SDK_INT >= 14) {
            Application j = j();
            if (j != null && this.e != null) {
                j.unregisterActivityLifecycleCallbacks(this.e);
            }
        }
    }

    public double e() {
        AudioManager audioManager = (AudioManager) this.c.getContext().getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        if (audioManager == null) {
            return 0.0d;
        }
        double streamVolume = (double) audioManager.getStreamVolume(3);
        double streamMaxVolume = (double) audioManager.getStreamMaxVolume(3);
        Double.isNaN(streamVolume);
        Double.isNaN(streamMaxVolume);
        return streamVolume / streamMaxVolume;
    }

    public boolean f() {
        if (this.c.getGlobalVisibleRect(new Rect())) {
            if (this.c.isShown()) {
                return false;
            }
        }
        return true;
    }

    public com.google.ads.interactivemedia.v3.impl.data.a.a g() {
        return a(com.google.ads.interactivemedia.v3.impl.data.a.a.createFromLocationOnScreen(this.c), i().density);
    }

    public com.google.ads.interactivemedia.v3.impl.data.a.a h() {
        Rect rect = new Rect();
        boolean globalVisibleRect = this.c.getGlobalVisibleRect(rect);
        Object obj = this.c.getWindowToken() != null ? 1 : null;
        if (!(globalVisibleRect && obj != null && this.c.isShown())) {
            rect.set(0, 0, 0, 0);
        }
        return a(com.google.ads.interactivemedia.v3.impl.data.a.a.create(rect.left, rect.top, rect.height(), rect.width()), i().density);
    }

    public com.google.ads.interactivemedia.v3.impl.data.a a(String str, String str2, String str3, String str4) {
        com.google.ads.interactivemedia.v3.impl.data.a.a g = g();
        com.google.ads.interactivemedia.v3.impl.data.a.a h = h();
        boolean isAttachedToWindow = ViewCompat.isAttachedToWindow(this.c);
        return com.google.ads.interactivemedia.v3.impl.data.a.builder().queryId(str).eventId(str2).vastEvent(str3).appState(str4).nativeTime(this.d.a()).nativeVolume(e()).nativeViewAttached(isAttachedToWindow).nativeViewHidden(f()).nativeViewBounds(g).nativeViewVisibleBounds(h).build();
    }

    public void a(String str, String str2) {
        this.a.b(new jc(com.google.ads.interactivemedia.v3.internal.jc.b.activityMonitor, com.google.ads.interactivemedia.v3.internal.jc.c.viewability, this.b, a(str, str2, "", "")));
    }

    public void a(String str, String str2, String str3) {
        this.a.b(new jc(com.google.ads.interactivemedia.v3.internal.jc.b.activityMonitor, com.google.ads.interactivemedia.v3.internal.jc.c.viewability, this.b, a(str, str2, str3, "")));
    }
}
