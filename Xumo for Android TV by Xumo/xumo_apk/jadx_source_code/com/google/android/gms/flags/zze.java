package com.google.android.gms.flags;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.flags.zza;
import com.google.android.gms.internal.flags.zzc;

public final class zze extends zza implements zzc {
    zze(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.flags.IFlagProvider");
    }

    public final void init(IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel zza = zza();
        zzc.zza(zza, iObjectWrapper);
        zzb(1, zza);
    }

    public final boolean getBooleanFlagValue(String str, boolean z, int i) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zzc.writeBoolean(zza, z);
        zza.writeInt(i);
        str = zza(2, zza);
        z = zzc.zza(str);
        str.recycle();
        return z;
    }

    public final int getIntFlagValue(String str, int i, int i2) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zza.writeInt(i);
        zza.writeInt(i2);
        str = zza(3, zza);
        i = str.readInt();
        str.recycle();
        return i;
    }

    public final long getLongFlagValue(String str, long j, int i) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zza.writeLong(j);
        zza.writeInt(i);
        str = zza(4, zza);
        j = str.readLong();
        str.recycle();
        return j;
    }

    public final String getStringFlagValue(String str, String str2, int i) throws RemoteException {
        Parcel zza = zza();
        zza.writeString(str);
        zza.writeString(str2);
        zza.writeInt(i);
        str = zza(5, zza);
        str2 = str.readString();
        str.recycle();
        return str2;
    }
}
