package com.google.android.gms.cast.framework;

import android.os.RemoteException;
import androidx.annotation.NonNull;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;

public final class zzae<T extends Session> extends zzy {
    private final SessionManagerListener<T> zzjb;
    private final Class<T> zzjc;

    public zzae(@NonNull SessionManagerListener<T> sessionManagerListener, @NonNull Class<T> cls) {
        this.zzjb = sessionManagerListener;
        this.zzjc = cls;
    }

    public final int zzm() {
        return 12451009;
    }

    public final IObjectWrapper zzn() {
        return ObjectWrapper.wrap(this.zzjb);
    }

    public final void zza(@NonNull IObjectWrapper iObjectWrapper) throws RemoteException {
        Session session = (Session) ObjectWrapper.unwrap(iObjectWrapper);
        if (this.zzjc.isInstance(session) && this.zzjb != null) {
            this.zzjb.onSessionStarting((Session) this.zzjc.cast(session));
        }
    }

    public final void zza(@NonNull IObjectWrapper iObjectWrapper, String str) throws RemoteException {
        Session session = (Session) ObjectWrapper.unwrap(iObjectWrapper);
        if (this.zzjc.isInstance(session) && this.zzjb != null) {
            this.zzjb.onSessionStarted((Session) this.zzjc.cast(session), str);
        }
    }

    public final void zza(@NonNull IObjectWrapper iObjectWrapper, int i) throws RemoteException {
        Session session = (Session) ObjectWrapper.unwrap(iObjectWrapper);
        if (this.zzjc.isInstance(session) && this.zzjb != null) {
            this.zzjb.onSessionStartFailed((Session) this.zzjc.cast(session), i);
        }
    }

    public final void zzb(@NonNull IObjectWrapper iObjectWrapper) throws RemoteException {
        Session session = (Session) ObjectWrapper.unwrap(iObjectWrapper);
        if (this.zzjc.isInstance(session) && this.zzjb != null) {
            this.zzjb.onSessionEnding((Session) this.zzjc.cast(session));
        }
    }

    public final void zzb(@NonNull IObjectWrapper iObjectWrapper, int i) throws RemoteException {
        Session session = (Session) ObjectWrapper.unwrap(iObjectWrapper);
        if (this.zzjc.isInstance(session) && this.zzjb != null) {
            this.zzjb.onSessionEnded((Session) this.zzjc.cast(session), i);
        }
    }

    public final void zzb(@NonNull IObjectWrapper iObjectWrapper, String str) throws RemoteException {
        Session session = (Session) ObjectWrapper.unwrap(iObjectWrapper);
        if (this.zzjc.isInstance(session) && this.zzjb != null) {
            this.zzjb.onSessionResuming((Session) this.zzjc.cast(session), str);
        }
    }

    public final void zza(@NonNull IObjectWrapper iObjectWrapper, boolean z) throws RemoteException {
        Session session = (Session) ObjectWrapper.unwrap(iObjectWrapper);
        if (this.zzjc.isInstance(session) && this.zzjb != null) {
            this.zzjb.onSessionResumed((Session) this.zzjc.cast(session), z);
        }
    }

    public final void zzc(@NonNull IObjectWrapper iObjectWrapper, int i) throws RemoteException {
        Session session = (Session) ObjectWrapper.unwrap(iObjectWrapper);
        if (this.zzjc.isInstance(session) && this.zzjb != null) {
            this.zzjb.onSessionResumeFailed((Session) this.zzjc.cast(session), i);
        }
    }

    public final void zzd(@NonNull IObjectWrapper iObjectWrapper, int i) throws RemoteException {
        Session session = (Session) ObjectWrapper.unwrap(iObjectWrapper);
        if (this.zzjc.isInstance(session) && this.zzjb != null) {
            this.zzjb.onSessionSuspended((Session) this.zzjc.cast(session), i);
        }
    }
}
