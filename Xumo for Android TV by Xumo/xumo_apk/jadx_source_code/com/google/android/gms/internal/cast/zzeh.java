package com.google.android.gms.internal.cast;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public final class zzeh extends zza implements zzeg {
    zzeh(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.cast.remote_display.ICastRemoteDisplayService");
    }

    public final void disconnect() throws RemoteException {
        transactOneway(3, obtainAndWriteInterfaceToken());
    }

    public final void zza(zzee com_google_android_gms_internal_cast_zzee, zzei com_google_android_gms_internal_cast_zzei, String str, String str2, Bundle bundle) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) com_google_android_gms_internal_cast_zzee);
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) com_google_android_gms_internal_cast_zzei);
        obtainAndWriteInterfaceToken.writeString(str);
        obtainAndWriteInterfaceToken.writeString(str2);
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) bundle);
        transactOneway(7, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzee com_google_android_gms_internal_cast_zzee, PendingIntent pendingIntent, String str, String str2, Bundle bundle) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) com_google_android_gms_internal_cast_zzee);
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) pendingIntent);
        obtainAndWriteInterfaceToken.writeString(str);
        obtainAndWriteInterfaceToken.writeString(str2);
        zzc.zza(obtainAndWriteInterfaceToken, (Parcelable) bundle);
        transactOneway(8, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzee com_google_android_gms_internal_cast_zzee, int i) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) com_google_android_gms_internal_cast_zzee);
        obtainAndWriteInterfaceToken.writeInt(i);
        transactOneway(5, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzee com_google_android_gms_internal_cast_zzee) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzc.zza(obtainAndWriteInterfaceToken, (IInterface) com_google_android_gms_internal_cast_zzee);
        transactOneway(6, obtainAndWriteInterfaceToken);
    }
}
