package com.google.android.gms.cast.framework;

import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.internal.cast.zzdh;
import com.google.android.gms.internal.cast.zze;

public abstract class Session {
    private static final zzdh zzbe = new zzdh("Session");
    private final zzt zziw;
    private final zza zzix = new zza();

    private class zza extends zzac {
        private final /* synthetic */ Session zziy;

        private zza(Session session) {
            this.zziy = session;
        }

        public final int zzm() {
            return 12451009;
        }

        public final IObjectWrapper zzab() {
            return ObjectWrapper.wrap(this.zziy);
        }

        public final void onStarting(Bundle bundle) {
            this.zziy.onStarting(bundle);
        }

        public final void start(Bundle bundle) {
            this.zziy.start(bundle);
        }

        public final void onResuming(Bundle bundle) {
            this.zziy.onResuming(bundle);
        }

        public final void resume(Bundle bundle) {
            this.zziy.resume(bundle);
        }

        public final void end(boolean z) {
            this.zziy.end(z);
        }

        public final long getSessionRemainingTimeMs() {
            return this.zziy.getSessionRemainingTimeMs();
        }
    }

    protected Session(Context context, String str, String str2) {
        this.zziw = zze.zza(context, str, str2, this.zzix);
    }

    protected abstract void end(boolean z);

    protected void onResuming(Bundle bundle) {
    }

    protected void onStarting(Bundle bundle) {
    }

    protected abstract void resume(Bundle bundle);

    protected abstract void start(Bundle bundle);

    public long getSessionRemainingTimeMs() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        return 0;
    }

    public final String getCategory() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        try {
            return this.zziw.getCategory();
        } catch (Throwable e) {
            zzbe.zza(e, "Unable to call %s on %s.", "getCategory", zzt.class.getSimpleName());
            return null;
        }
    }

    public final String getSessionId() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        try {
            return this.zziw.getSessionId();
        } catch (Throwable e) {
            zzbe.zza(e, "Unable to call %s on %s.", "getSessionId", zzt.class.getSimpleName());
            return null;
        }
    }

    public boolean isConnected() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        try {
            return this.zziw.isConnected();
        } catch (Throwable e) {
            zzbe.zza(e, "Unable to call %s on %s.", "isConnected", zzt.class.getSimpleName());
            return false;
        }
    }

    public boolean isConnecting() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        try {
            return this.zziw.isConnecting();
        } catch (Throwable e) {
            zzbe.zza(e, "Unable to call %s on %s.", "isConnecting", zzt.class.getSimpleName());
            return false;
        }
    }

    public boolean isDisconnecting() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        try {
            return this.zziw.isDisconnecting();
        } catch (Throwable e) {
            zzbe.zza(e, "Unable to call %s on %s.", "isDisconnecting", zzt.class.getSimpleName());
            return false;
        }
    }

    public boolean isDisconnected() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        try {
            return this.zziw.isDisconnected();
        } catch (Throwable e) {
            zzbe.zza(e, "Unable to call %s on %s.", "isDisconnected", zzt.class.getSimpleName());
            return true;
        }
    }

    public boolean isResuming() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        try {
            return this.zziw.isResuming();
        } catch (Throwable e) {
            zzbe.zza(e, "Unable to call %s on %s.", "isResuming", zzt.class.getSimpleName());
            return false;
        }
    }

    public boolean isSuspended() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        try {
            return this.zziw.isSuspended();
        } catch (Throwable e) {
            zzbe.zza(e, "Unable to call %s on %s.", "isSuspended", zzt.class.getSimpleName());
            return false;
        }
    }

    protected final void notifySessionStarted(String str) {
        try {
            this.zziw.notifySessionStarted(str);
        } catch (String str2) {
            zzbe.zza(str2, "Unable to call %s on %s.", "notifySessionStarted", zzt.class.getSimpleName());
        }
    }

    protected final void notifyFailedToStartSession(int i) {
        try {
            this.zziw.notifyFailedToStartSession(i);
        } catch (int i2) {
            zzbe.zza(i2, "Unable to call %s on %s.", "notifyFailedToStartSession", zzt.class.getSimpleName());
        }
    }

    protected final void notifySessionEnded(int i) {
        try {
            this.zziw.notifySessionEnded(i);
        } catch (int i2) {
            zzbe.zza(i2, "Unable to call %s on %s.", "notifySessionEnded", zzt.class.getSimpleName());
        }
    }

    protected final void notifySessionResumed(boolean z) {
        try {
            this.zziw.notifySessionResumed(z);
        } catch (boolean z2) {
            zzbe.zza(z2, "Unable to call %s on %s.", "notifySessionResumed", zzt.class.getSimpleName());
        }
    }

    protected final void notifyFailedToResumeSession(int i) {
        try {
            this.zziw.notifyFailedToResumeSession(i);
        } catch (int i2) {
            zzbe.zza(i2, "Unable to call %s on %s.", "notifyFailedToResumeSession", zzt.class.getSimpleName());
        }
    }

    protected final void notifySessionSuspended(int i) {
        try {
            this.zziw.notifySessionSuspended(i);
        } catch (int i2) {
            zzbe.zza(i2, "Unable to call %s on %s.", "notifySessionSuspended", zzt.class.getSimpleName());
        }
    }

    public final IObjectWrapper zzz() {
        try {
            return this.zziw.zzz();
        } catch (Throwable e) {
            zzbe.zza(e, "Unable to call %s on %s.", "getWrappedObject", zzt.class.getSimpleName());
            return null;
        }
    }
}
