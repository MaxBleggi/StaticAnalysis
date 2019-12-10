package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.measurement.zzr;
import com.google.android.gms.internal.measurement.zzs;

public abstract class zzai extends zzr implements zzah {
    public zzai() {
        super("com.google.android.gms.measurement.internal.IMeasurementService");
    }

    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        switch (i) {
            case 1:
                zza((zzae) zzs.zza(parcel, zzae.CREATOR), (zzi) zzs.zza(parcel, zzi.CREATOR));
                parcel2.writeNoException();
                break;
            case 2:
                zza((zzfr) zzs.zza(parcel, zzfr.CREATOR), (zzi) zzs.zza(parcel, zzi.CREATOR));
                parcel2.writeNoException();
                break;
            case 4:
                zza((zzi) zzs.zza(parcel, zzi.CREATOR));
                parcel2.writeNoException();
                break;
            case 5:
                zza((zzae) zzs.zza(parcel, zzae.CREATOR), (String) parcel.readString(), (String) parcel.readString());
                parcel2.writeNoException();
                break;
            case 6:
                zzb((zzi) zzs.zza(parcel, zzi.CREATOR));
                parcel2.writeNoException();
                break;
            case 7:
                i = zza((zzi) zzs.zza(parcel, zzi.CREATOR), (boolean) zzs.zza(parcel));
                parcel2.writeNoException();
                parcel2.writeTypedList(i);
                break;
            case 9:
                i = zza((zzae) zzs.zza(parcel, zzae.CREATOR), (String) parcel.readString());
                parcel2.writeNoException();
                parcel2.writeByteArray(i);
                break;
            case 10:
                zza(parcel.readLong(), parcel.readString(), parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                break;
            case 11:
                i = zzc((zzi) zzs.zza(parcel, zzi.CREATOR));
                parcel2.writeNoException();
                parcel2.writeString(i);
                break;
            case 12:
                zza((zzm) zzs.zza(parcel, zzm.CREATOR), (zzi) zzs.zza(parcel, zzi.CREATOR));
                parcel2.writeNoException();
                break;
            case 13:
                zzb((zzm) zzs.zza(parcel, zzm.CREATOR));
                parcel2.writeNoException();
                break;
            case 14:
                i = zza((String) parcel.readString(), (String) parcel.readString(), zzs.zza(parcel), (zzi) zzs.zza(parcel, zzi.CREATOR));
                parcel2.writeNoException();
                parcel2.writeTypedList(i);
                break;
            case 15:
                i = zza((String) parcel.readString(), (String) parcel.readString(), parcel.readString(), (boolean) zzs.zza(parcel));
                parcel2.writeNoException();
                parcel2.writeTypedList(i);
                break;
            case 16:
                i = zza((String) parcel.readString(), (String) parcel.readString(), (zzi) zzs.zza(parcel, zzi.CREATOR));
                parcel2.writeNoException();
                parcel2.writeTypedList(i);
                break;
            case 17:
                i = zze(parcel.readString(), parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                parcel2.writeTypedList(i);
                break;
            case 18:
                zzd((zzi) zzs.zza(parcel, zzi.CREATOR));
                parcel2.writeNoException();
                break;
            default:
                return false;
        }
        return true;
    }
}
