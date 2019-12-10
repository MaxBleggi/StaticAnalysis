package com.google.android.gms.cast.framework;

import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

public interface zzv extends IInterface {
    int getCastState() throws RemoteException;

    void zza(zzn com_google_android_gms_cast_framework_zzn) throws RemoteException;

    void zza(zzx com_google_android_gms_cast_framework_zzx) throws RemoteException;

    void zza(boolean z, boolean z2) throws RemoteException;

    IObjectWrapper zzaa() throws RemoteException;

    void zzb(zzn com_google_android_gms_cast_framework_zzn) throws RemoteException;

    void zzb(zzx com_google_android_gms_cast_framework_zzx) throws RemoteException;

    void zzc(Bundle bundle) throws RemoteException;

    IObjectWrapper zzy() throws RemoteException;
}
