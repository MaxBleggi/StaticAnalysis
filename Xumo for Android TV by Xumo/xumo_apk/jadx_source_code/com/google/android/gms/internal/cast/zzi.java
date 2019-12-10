package com.google.android.gms.internal.cast;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.cast.framework.CastOptions;
import com.google.android.gms.cast.framework.zzab;
import com.google.android.gms.cast.framework.zzh;
import com.google.android.gms.cast.framework.zzj;
import com.google.android.gms.cast.framework.zzj.zza;
import com.google.android.gms.cast.framework.zzl;
import com.google.android.gms.cast.framework.zzr;
import com.google.android.gms.cast.framework.zzt;
import com.google.android.gms.dynamic.IObjectWrapper;
import java.util.Map;

public final class zzi extends zza implements zzh {
    zzi(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.cast.framework.internal.ICastDynamiteModule");
    }

    public final zzj zza(IObjectWrapper iObjectWrapper, CastOptions castOptions, zzj com_google_android_gms_internal_cast_zzj, Map map) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) castOptions);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) com_google_android_gms_internal_cast_zzj);
        obtainAndWriteInterfaceToken.writeMap(map);
        iObjectWrapper = transactAndReadException(1, obtainAndWriteInterfaceToken);
        castOptions = zza.zza(iObjectWrapper.readStrongBinder());
        iObjectWrapper.recycle();
        return castOptions;
    }

    public final zzt zza(String str, String str2, zzab com_google_android_gms_cast_framework_zzab) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeString(str);
        obtainAndWriteInterfaceToken.writeString(str2);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) com_google_android_gms_cast_framework_zzab);
        str = transactAndReadException(2, obtainAndWriteInterfaceToken);
        str2 = zzt.zza.zzd(str.readStrongBinder());
        str.recycle();
        return str2;
    }

    public final zzl zza(CastOptions castOptions, IObjectWrapper iObjectWrapper, zzh com_google_android_gms_cast_framework_zzh) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) castOptions);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) com_google_android_gms_cast_framework_zzh);
        castOptions = transactAndReadException(3, obtainAndWriteInterfaceToken);
        iObjectWrapper = zzl.zza.zzb(castOptions.readStrongBinder());
        castOptions.recycle();
        return iObjectWrapper;
    }

    public final zzr zza(IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2, IObjectWrapper iObjectWrapper3) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper2);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper3);
        iObjectWrapper = transactAndReadException(5, obtainAndWriteInterfaceToken);
        iObjectWrapper2 = zzr.zza.zzc(iObjectWrapper.readStrongBinder());
        iObjectWrapper.recycle();
        return iObjectWrapper2;
    }

    public final zzae zza(IObjectWrapper iObjectWrapper, zzag com_google_android_gms_internal_cast_zzag, int i, int i2, boolean z, long j, int i3, int i4, int i5) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) iObjectWrapper);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) com_google_android_gms_internal_cast_zzag);
        obtainAndWriteInterfaceToken.writeInt(i);
        obtainAndWriteInterfaceToken.writeInt(i2);
        zzc.writeBoolean(obtainAndWriteInterfaceToken, z);
        obtainAndWriteInterfaceToken.writeLong(j);
        obtainAndWriteInterfaceToken.writeInt(i3);
        obtainAndWriteInterfaceToken.writeInt(i4);
        obtainAndWriteInterfaceToken.writeInt(i5);
        iObjectWrapper = transactAndReadException(6, obtainAndWriteInterfaceToken);
        com_google_android_gms_internal_cast_zzag = zzae.zza.zze(iObjectWrapper.readStrongBinder());
        iObjectWrapper.recycle();
        return com_google_android_gms_internal_cast_zzag;
    }
}
