package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.zzk;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.common.zza;
import com.google.android.gms.internal.common.zzc;

public final class zzo extends zza implements zzm {
    zzo(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.IGoogleCertificatesApi");
    }

    public final boolean zza(zzk com_google_android_gms_common_zzk, IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel zza = zza();
        zzc.zza(zza, (Parcelable) com_google_android_gms_common_zzk);
        zzc.zza(zza, (IInterface) iObjectWrapper);
        com_google_android_gms_common_zzk = zza(5, zza);
        iObjectWrapper = zzc.zza(com_google_android_gms_common_zzk);
        com_google_android_gms_common_zzk.recycle();
        return iObjectWrapper;
    }
}
