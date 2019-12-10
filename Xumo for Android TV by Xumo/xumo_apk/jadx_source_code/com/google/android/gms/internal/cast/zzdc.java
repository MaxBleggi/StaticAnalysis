package com.google.android.gms.internal.cast;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.cast.ApplicationMetadata;

public interface zzdc extends IInterface {
    void onApplicationDisconnected(int i) throws RemoteException;

    void zza(ApplicationMetadata applicationMetadata, String str, String str2, boolean z) throws RemoteException;

    void zza(String str, double d, boolean z) throws RemoteException;

    void zza(String str, long j) throws RemoteException;

    void zza(String str, long j, int i) throws RemoteException;

    void zza(String str, byte[] bArr) throws RemoteException;

    void zzb(zzce com_google_android_gms_internal_cast_zzce) throws RemoteException;

    void zzb(zzcw com_google_android_gms_internal_cast_zzcw) throws RemoteException;

    void zzb(String str, String str2) throws RemoteException;

    void zzf(int i) throws RemoteException;

    void zzn(int i) throws RemoteException;

    void zzo(int i) throws RemoteException;

    void zzp(int i) throws RemoteException;
}
