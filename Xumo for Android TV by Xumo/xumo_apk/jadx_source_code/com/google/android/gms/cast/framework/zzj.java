package com.google.android.gms.cast.framework;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.internal.cast.zzb;
import java.util.Map;

public interface zzj extends IInterface {

    public static abstract class zza extends zzb implements zzj {
        public static zzj zza(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.cast.framework.ICastContext");
            if (queryLocalInterface instanceof zzj) {
                return (zzj) queryLocalInterface;
            }
            return new zzk(iBinder);
        }
    }

    boolean isAppVisible() throws RemoteException;

    void zza(zzf com_google_android_gms_cast_framework_zzf) throws RemoteException;

    void zza(String str, Map map) throws RemoteException;

    void zzb(zzf com_google_android_gms_cast_framework_zzf) throws RemoteException;

    boolean zzq() throws RemoteException;

    Bundle zzv() throws RemoteException;

    zzv zzw() throws RemoteException;

    zzp zzx() throws RemoteException;
}
