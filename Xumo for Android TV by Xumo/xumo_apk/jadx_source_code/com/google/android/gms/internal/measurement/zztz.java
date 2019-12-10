package com.google.android.gms.internal.measurement;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;

public abstract class zztz extends zzr implements zzty {
    public zztz() {
        super("com.google.firebase.dynamiclinks.internal.IDynamicLinksCallbacks");
    }

    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        switch (i) {
            case 1:
                zza((Status) zzs.zza(parcel, Status.CREATOR), (zztn) zzs.zza(parcel, zztn.CREATOR));
                break;
            case 2:
                zza((Status) zzs.zza(parcel, Status.CREATOR), (zzuc) zzs.zza(parcel, zzuc.CREATOR));
                break;
            default:
                return false;
        }
        return true;
    }
}
