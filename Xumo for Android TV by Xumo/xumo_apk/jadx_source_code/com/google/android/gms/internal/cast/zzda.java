package com.google.android.gms.internal.cast;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.cast.LaunchOptions;
import com.google.android.gms.cast.zzag;

public interface zzda extends IInterface {
    void disconnect() throws RemoteException;

    void requestStatus() throws RemoteException;

    void zza(double d, double d2, boolean z) throws RemoteException;

    void zza(String str, String str2, long j) throws RemoteException;

    void zza(String str, String str2, zzag com_google_android_gms_cast_zzag) throws RemoteException;

    void zza(boolean z, double d, boolean z2) throws RemoteException;

    void zzb(String str, LaunchOptions launchOptions) throws RemoteException;

    void zzdn() throws RemoteException;

    void zzi(String str) throws RemoteException;

    void zzr(String str) throws RemoteException;

    void zzs(String str) throws RemoteException;
}
