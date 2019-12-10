package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public final class zzub extends zzq implements zzua {
    zzub(IBinder iBinder) {
        super(iBinder, "com.google.firebase.dynamiclinks.internal.IDynamicLinksService");
    }

    public final void zza(zzty com_google_android_gms_internal_measurement_zzty, String str) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzs.zza(obtainAndWriteInterfaceToken, (IInterface) com_google_android_gms_internal_measurement_zzty);
        obtainAndWriteInterfaceToken.writeString(str);
        zza(1, obtainAndWriteInterfaceToken);
    }

    public final void zza(zzty com_google_android_gms_internal_measurement_zzty, Bundle bundle) throws RemoteException {
        Parcel obtainAndWriteInterfaceToken = obtainAndWriteInterfaceToken();
        zzs.zza(obtainAndWriteInterfaceToken, (IInterface) com_google_android_gms_internal_measurement_zzty);
        zzs.zza(obtainAndWriteInterfaceToken, (Parcelable) bundle);
        zza(2, obtainAndWriteInterfaceToken);
    }
}
