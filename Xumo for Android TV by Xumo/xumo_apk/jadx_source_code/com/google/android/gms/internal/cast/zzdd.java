package com.google.android.gms.internal.cast;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.cast.ApplicationMetadata;

public abstract class zzdd extends zzb implements zzdc {
    public zzdd() {
        super("com.google.android.gms.cast.internal.ICastDeviceControllerListener");
    }

    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        switch (i) {
            case 1:
                zzn(parcel.readInt());
                break;
            case 2:
                zza((ApplicationMetadata) zzc.zza(parcel, ApplicationMetadata.CREATOR), parcel.readString(), parcel.readString(), zzc.zza(parcel));
                break;
            case 3:
                zzf(parcel.readInt());
                break;
            case 4:
                zza((String) parcel.readString(), (double) parcel.readDouble(), (boolean) zzc.zza(parcel));
                break;
            case 5:
                zzb(parcel.readString(), parcel.readString());
                break;
            case 6:
                zza((String) parcel.readString(), (byte[]) parcel.createByteArray());
                break;
            case 7:
                zzp(parcel.readInt());
                break;
            case 8:
                zzo(parcel.readInt());
                break;
            case 9:
                onApplicationDisconnected(parcel.readInt());
                break;
            case 10:
                zza((String) parcel.readString(), (long) parcel.readLong(), (int) parcel.readInt());
                break;
            case 11:
                zza((String) parcel.readString(), (long) parcel.readLong());
                break;
            case 12:
                zzb((zzce) zzc.zza(parcel, zzce.CREATOR));
                break;
            case 13:
                zzb((zzcw) zzc.zza(parcel, zzcw.CREATOR));
                break;
            default:
                return false;
        }
        return true;
    }
}
