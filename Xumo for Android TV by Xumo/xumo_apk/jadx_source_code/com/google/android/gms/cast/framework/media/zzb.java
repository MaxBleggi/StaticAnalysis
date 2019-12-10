package com.google.android.gms.cast.framework.media;

import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.cast.zzc;

public interface zzb extends IInterface {

    public static abstract class zza extends com.google.android.gms.internal.cast.zzb implements zzb {
        public zza() {
            super("com.google.android.gms.cast.framework.media.IImagePicker");
        }

        protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            switch (i) {
                case 1:
                    i = onPickImage((MediaMetadata) zzc.zza(parcel, MediaMetadata.CREATOR), parcel.readInt());
                    parcel2.writeNoException();
                    zzc.zzb(parcel2, i);
                    break;
                case 2:
                    IInterface zzax = zzax();
                    parcel2.writeNoException();
                    zzc.zza(parcel2, zzax);
                    break;
                case 3:
                    i = zzm();
                    parcel2.writeNoException();
                    parcel2.writeInt(i);
                    break;
                case 4:
                    i = zza((MediaMetadata) zzc.zza(parcel, MediaMetadata.CREATOR), (ImageHints) zzc.zza(parcel, ImageHints.CREATOR));
                    parcel2.writeNoException();
                    zzc.zzb(parcel2, i);
                    break;
                default:
                    return false;
            }
            return true;
        }
    }

    WebImage onPickImage(MediaMetadata mediaMetadata, int i) throws RemoteException;

    WebImage zza(MediaMetadata mediaMetadata, ImageHints imageHints) throws RemoteException;

    IObjectWrapper zzax() throws RemoteException;

    int zzm() throws RemoteException;
}
