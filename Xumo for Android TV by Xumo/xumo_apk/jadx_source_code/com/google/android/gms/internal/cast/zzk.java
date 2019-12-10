package com.google.android.gms.internal.cast;

import android.os.Bundle;
import android.os.Parcel;
import android.os.RemoteException;

public abstract class zzk extends zzb implements zzj {
    public zzk() {
        super("com.google.android.gms.cast.framework.internal.IMediaRouter");
    }

    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        switch (i) {
            case 1:
                Bundle bundle = (Bundle) zzc.zza(parcel, Bundle.CREATOR);
                parcel = parcel.readStrongBinder();
                if (parcel == null) {
                    parcel = null;
                } else {
                    i2 = parcel.queryLocalInterface("com.google.android.gms.cast.framework.internal.IMediaRouterCallback");
                    if (i2 instanceof zzl) {
                        parcel = (zzl) i2;
                    } else {
                        parcel = new zzm(parcel);
                    }
                }
                zza(bundle, (zzl) parcel);
                parcel2.writeNoException();
                break;
            case 2:
                zza((Bundle) zzc.zza(parcel, Bundle.CREATOR), (int) parcel.readInt());
                parcel2.writeNoException();
                break;
            case 3:
                zzd((Bundle) zzc.zza(parcel, Bundle.CREATOR));
                parcel2.writeNoException();
                break;
            case 4:
                i = zzb((Bundle) zzc.zza(parcel, Bundle.CREATOR), parcel.readInt());
                parcel2.writeNoException();
                zzc.writeBoolean(parcel2, i);
                break;
            case 5:
                zzk(parcel.readString());
                parcel2.writeNoException();
                break;
            case 6:
                zzak();
                parcel2.writeNoException();
                break;
            case 7:
                i = zzal();
                parcel2.writeNoException();
                zzc.writeBoolean(parcel2, i);
                break;
            case 8:
                i = zzl(parcel.readString());
                parcel2.writeNoException();
                zzc.zzb(parcel2, i);
                break;
            case 9:
                i = zzam();
                parcel2.writeNoException();
                parcel2.writeString(i);
                break;
            case 10:
                zzm();
                parcel2.writeNoException();
                parcel2.writeInt(12451009);
                break;
            case 11:
                zzan();
                parcel2.writeNoException();
                break;
            default:
                return false;
        }
        return true;
    }
}
