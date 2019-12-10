package com.google.android.gms.cast.framework;

import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.cast.zzb;
import com.google.android.gms.internal.cast.zzc;

public abstract class zzg extends zzb implements zzf {
    public zzg() {
        super("com.google.android.gms.cast.framework.IAppVisibilityListener");
    }

    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        switch (i) {
            case 1:
                IInterface zzn = zzn();
                parcel2.writeNoException();
                zzc.zza(parcel2, zzn);
                break;
            case 2:
                onAppEnteredForeground();
                parcel2.writeNoException();
                break;
            case 3:
                onAppEnteredBackground();
                parcel2.writeNoException();
                break;
            case 4:
                zzm();
                parcel2.writeNoException();
                parcel2.writeInt(12451009);
                break;
            default:
                return false;
        }
        return true;
    }
}
