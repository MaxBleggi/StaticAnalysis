package com.google.android.gms.cast.framework;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.internal.cast.zzdh;

public class SessionManager {
    private static final zzdh zzbe = new zzdh("SessionManager");
    private final zzv zziz;
    private final Context zzja;

    public SessionManager(zzv com_google_android_gms_cast_framework_zzv, Context context) {
        this.zziz = com_google_android_gms_cast_framework_zzv;
        this.zzja = context;
    }

    public Session getCurrentSession() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        try {
            return (Session) ObjectWrapper.unwrap(this.zziz.zzaa());
        } catch (Throwable e) {
            zzbe.zza(e, "Unable to call %s on %s.", "getWrappedCurrentSession", zzv.class.getSimpleName());
            return null;
        }
    }

    public CastSession getCurrentCastSession() {
        Preconditions.checkMainThread("Must be called from the main thread.");
        Session currentSession = getCurrentSession();
        return (currentSession == null || !(currentSession instanceof CastSession)) ? null : (CastSession) currentSession;
    }

    public void endCurrentSession(boolean z) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        try {
            this.zziz.zza(true, z);
        } catch (boolean z2) {
            zzbe.zza(z2, "Unable to call %s on %s.", "endCurrentSession", zzv.class.getSimpleName());
        }
    }

    public void addSessionManagerListener(SessionManagerListener<Session> sessionManagerListener) throws NullPointerException {
        Preconditions.checkMainThread("Must be called from the main thread.");
        addSessionManagerListener(sessionManagerListener, Session.class);
    }

    public <T extends Session> void addSessionManagerListener(SessionManagerListener<T> sessionManagerListener, Class<T> cls) throws NullPointerException {
        Preconditions.checkNotNull(sessionManagerListener);
        Preconditions.checkNotNull(cls);
        Preconditions.checkMainThread("Must be called from the main thread.");
        try {
            this.zziz.zza(new zzae(sessionManagerListener, cls));
        } catch (SessionManagerListener<T> sessionManagerListener2) {
            zzbe.zza(sessionManagerListener2, "Unable to call %s on %s.", "addSessionManagerListener", zzv.class.getSimpleName());
        }
    }

    public void removeSessionManagerListener(SessionManagerListener<Session> sessionManagerListener) {
        Preconditions.checkMainThread("Must be called from the main thread.");
        removeSessionManagerListener(sessionManagerListener, Session.class);
    }

    public <T extends Session> void removeSessionManagerListener(SessionManagerListener<T> sessionManagerListener, Class cls) {
        Preconditions.checkNotNull(cls);
        Preconditions.checkMainThread("Must be called from the main thread.");
        if (sessionManagerListener != null) {
            try {
                this.zziz.zzb(new zzae(sessionManagerListener, cls));
            } catch (SessionManagerListener<T> sessionManagerListener2) {
                zzbe.zza(sessionManagerListener2, "Unable to call %s on %s.", "removeSessionManagerListener", zzv.class.getSimpleName());
            }
        }
    }

    public void startSession(Intent intent) {
        try {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                if (extras.getString("CAST_INTENT_TO_CAST_ROUTE_ID_KEY") != null) {
                    String string = extras.getString("CAST_INTENT_TO_CAST_DEVICE_NAME_KEY");
                    if (!extras.getBoolean("CAST_INTENT_TO_CAST_NO_TOAST_KEY")) {
                        Toast.makeText(this.zzja, this.zzja.getString(R.string.cast_connecting_to_device, new Object[]{string}), 0).show();
                    }
                    this.zziz.zzc(new Bundle(extras));
                    intent.removeExtra("CAST_INTENT_TO_CAST_ROUTE_ID_KEY");
                }
            }
        } catch (Intent intent2) {
            zzbe.zza(intent2, "Unable to call %s on %s.", "startSession", zzv.class.getSimpleName());
        }
    }

    final int getCastState() {
        try {
            return this.zziz.getCastState();
        } catch (Throwable e) {
            zzbe.zza(e, "Unable to call %s on %s.", "addCastStateListener", zzv.class.getSimpleName());
            return 1;
        }
    }

    final void addCastStateListener(CastStateListener castStateListener) throws NullPointerException {
        Preconditions.checkNotNull(castStateListener);
        try {
            this.zziz.zza(new zzd(castStateListener));
        } catch (CastStateListener castStateListener2) {
            zzbe.zza(castStateListener2, "Unable to call %s on %s.", "addCastStateListener", zzv.class.getSimpleName());
        }
    }

    final void removeCastStateListener(CastStateListener castStateListener) {
        if (castStateListener != null) {
            try {
                this.zziz.zzb(new zzd(castStateListener));
            } catch (CastStateListener castStateListener2) {
                zzbe.zza(castStateListener2, "Unable to call %s on %s.", "removeCastStateListener", zzv.class.getSimpleName());
            }
        }
    }

    public final IObjectWrapper zzu() {
        try {
            return this.zziz.zzy();
        } catch (Throwable e) {
            zzbe.zza(e, "Unable to call %s on %s.", "getWrappedThis", zzv.class.getSimpleName());
            return null;
        }
    }
}
