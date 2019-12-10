package com.google.android.gms.cast.framework.media;

import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.cast.zzb;
import com.google.android.gms.internal.cast.zzc;
import java.util.List;

public interface zzd extends IInterface {

    public static abstract class zza extends zzb implements zzd {
        public zza() {
            super("com.google.android.gms.cast.framework.media.INotificationActionsProvider");
        }

        protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            switch (i) {
                case 1:
                    i = zzm();
                    parcel2.writeNoException();
                    parcel2.writeInt(i);
                    break;
                case 2:
                    IInterface zzax = zzax();
                    parcel2.writeNoException();
                    zzc.zza(parcel2, zzax);
                    break;
                case 3:
                    i = getNotificationActions();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(i);
                    break;
                case 4:
                    i = getCompactViewActionIndices();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(i);
                    break;
                default:
                    return false;
            }
            return true;
        }
    }

    int[] getCompactViewActionIndices() throws RemoteException;

    List<NotificationAction> getNotificationActions() throws RemoteException;

    IObjectWrapper zzax() throws RemoteException;

    int zzm() throws RemoteException;
}
