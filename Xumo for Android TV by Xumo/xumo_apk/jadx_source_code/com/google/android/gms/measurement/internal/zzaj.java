package com.google.android.gms.measurement.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.internal.measurement.zzq;
import com.google.android.gms.internal.measurement.zzs;
import java.util.List;

public final class zzaj extends zzq implements zzah {
    zzaj(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.measurement.internal.IMeasurementService");
    }

    public final void zza(zzae com_google_android_gms_measurement_internal_zzae, zzi com_google_android_gms_measurement_internal_zzi) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzs.zza(obtainAndWriteInterfaceToken, (Parcelable) com_google_android_gms_measurement_internal_zzae);
        zzs.zza(obtainAndWriteInterfaceToken, (Parcelable) com_google_android_gms_measurement_internal_zzi);
        zza(1, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzfr com_google_android_gms_measurement_internal_zzfr, zzi com_google_android_gms_measurement_internal_zzi) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzs.zza(obtainAndWriteInterfaceToken, (Parcelable) com_google_android_gms_measurement_internal_zzfr);
        zzs.zza(obtainAndWriteInterfaceToken, (Parcelable) com_google_android_gms_measurement_internal_zzi);
        zza(2, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzi com_google_android_gms_measurement_internal_zzi) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzs.zza(obtainAndWriteInterfaceToken, (Parcelable) com_google_android_gms_measurement_internal_zzi);
        zza(4, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzae com_google_android_gms_measurement_internal_zzae, String str, String str2) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzs.zza(obtainAndWriteInterfaceToken, (Parcelable) com_google_android_gms_measurement_internal_zzae);
        obtainAndWriteInterfaceToken.writeString(str);
        obtainAndWriteInterfaceToken.writeString(str2);
        zza(5, obtainAndWriteInterfaceToken);
    }

    public final void zzb(zzi com_google_android_gms_measurement_internal_zzi) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzs.zza(obtainAndWriteInterfaceToken, (Parcelable) com_google_android_gms_measurement_internal_zzi);
        zza(6, obtainAndWriteInterfaceToken);
    }

    public final List<zzfr> zza(zzi com_google_android_gms_measurement_internal_zzi, boolean z) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzs.zza(obtainAndWriteInterfaceToken, (Parcelable) com_google_android_gms_measurement_internal_zzi);
        zzs.writeBoolean(obtainAndWriteInterfaceToken, z);
        com_google_android_gms_measurement_internal_zzi = transactAndReadException(7, obtainAndWriteInterfaceToken);
        z = com_google_android_gms_measurement_internal_zzi.createTypedArrayList(zzfr.CREATOR);
        com_google_android_gms_measurement_internal_zzi.recycle();
        return z;
    }

    public final byte[] zza(zzae com_google_android_gms_measurement_internal_zzae, String str) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzs.zza(obtainAndWriteInterfaceToken, (Parcelable) com_google_android_gms_measurement_internal_zzae);
        obtainAndWriteInterfaceToken.writeString(str);
        com_google_android_gms_measurement_internal_zzae = transactAndReadException(9, obtainAndWriteInterfaceToken);
        str = com_google_android_gms_measurement_internal_zzae.createByteArray();
        com_google_android_gms_measurement_internal_zzae.recycle();
        return str;
    }

    public final void zza(long j, String str, String str2, String str3) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeLong(j);
        obtainAndWriteInterfaceToken.writeString(str);
        obtainAndWriteInterfaceToken.writeString(str2);
        obtainAndWriteInterfaceToken.writeString(str3);
        zza(10, obtainAndWriteInterfaceToken);
    }

    public final String zzc(zzi com_google_android_gms_measurement_internal_zzi) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzs.zza(obtainAndWriteInterfaceToken, (Parcelable) com_google_android_gms_measurement_internal_zzi);
        com_google_android_gms_measurement_internal_zzi = transactAndReadException(11, obtainAndWriteInterfaceToken);
        String readString = com_google_android_gms_measurement_internal_zzi.readString();
        com_google_android_gms_measurement_internal_zzi.recycle();
        return readString;
    }

    public final void zza(zzm com_google_android_gms_measurement_internal_zzm, zzi com_google_android_gms_measurement_internal_zzi) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzs.zza(obtainAndWriteInterfaceToken, (Parcelable) com_google_android_gms_measurement_internal_zzm);
        zzs.zza(obtainAndWriteInterfaceToken, (Parcelable) com_google_android_gms_measurement_internal_zzi);
        zza(12, obtainAndWriteInterfaceToken);
    }

    public final void zzb(zzm com_google_android_gms_measurement_internal_zzm) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzs.zza(obtainAndWriteInterfaceToken, (Parcelable) com_google_android_gms_measurement_internal_zzm);
        zza(13, obtainAndWriteInterfaceToken);
    }

    public final List<zzfr> zza(String str, String str2, boolean z, zzi com_google_android_gms_measurement_internal_zzi) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeString(str);
        obtainAndWriteInterfaceToken.writeString(str2);
        zzs.writeBoolean(obtainAndWriteInterfaceToken, z);
        zzs.zza(obtainAndWriteInterfaceToken, (Parcelable) com_google_android_gms_measurement_internal_zzi);
        str = transactAndReadException(14, obtainAndWriteInterfaceToken);
        str2 = str.createTypedArrayList(zzfr.CREATOR);
        str.recycle();
        return str2;
    }

    public final List<zzfr> zza(String str, String str2, String str3, boolean z) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeString(str);
        obtainAndWriteInterfaceToken.writeString(str2);
        obtainAndWriteInterfaceToken.writeString(str3);
        zzs.writeBoolean(obtainAndWriteInterfaceToken, z);
        str = transactAndReadException(15, obtainAndWriteInterfaceToken);
        str2 = str.createTypedArrayList(zzfr.CREATOR);
        str.recycle();
        return str2;
    }

    public final List<zzm> zza(String str, String str2, zzi com_google_android_gms_measurement_internal_zzi) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeString(str);
        obtainAndWriteInterfaceToken.writeString(str2);
        zzs.zza(obtainAndWriteInterfaceToken, (Parcelable) com_google_android_gms_measurement_internal_zzi);
        str = transactAndReadException(16, obtainAndWriteInterfaceToken);
        str2 = str.createTypedArrayList(zzm.CREATOR);
        str.recycle();
        return str2;
    }

    public final List<zzm> zze(String str, String str2, String str3) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        obtainAndWriteInterfaceToken.writeString(str);
        obtainAndWriteInterfaceToken.writeString(str2);
        obtainAndWriteInterfaceToken.writeString(str3);
        str = transactAndReadException(17, obtainAndWriteInterfaceToken);
        str2 = str.createTypedArrayList(zzm.CREATOR);
        str.recycle();
        return str2;
    }

    public final void zzd(zzi com_google_android_gms_measurement_internal_zzi) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzs.zza(obtainAndWriteInterfaceToken, (Parcelable) com_google_android_gms_measurement_internal_zzi);
        zza(18, obtainAndWriteInterfaceToken);
    }
}
