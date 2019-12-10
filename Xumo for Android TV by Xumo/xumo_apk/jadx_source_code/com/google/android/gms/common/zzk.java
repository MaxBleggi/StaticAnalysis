package com.google.android.gms.common;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.dynamic.ObjectWrapper;
import javax.annotation.Nullable;

@Class(creator = "GoogleCertificatesQueryCreator")
public final class zzk extends AbstractSafeParcelable {
    public static final Creator<zzk> CREATOR = new zzl();
    @Field(getter = "getAllowTestKeys", id = 3)
    private final boolean zzaa;
    @Field(getter = "getCallingPackage", id = 1)
    private final String zzy;
    @Field(getter = "getCallingCertificateBinder", id = 2, type = "android.os.IBinder")
    @Nullable
    private final zze zzz;

    @Constructor
    zzk(@Param(id = 1) String str, @Param(id = 2) @Nullable IBinder iBinder, @Param(id = 3) boolean z) {
        this.zzy = str;
        this.zzz = zza(iBinder);
        this.zzaa = z;
    }

    zzk(String str, @Nullable zze com_google_android_gms_common_zze, boolean z) {
        this.zzy = str;
        this.zzz = com_google_android_gms_common_zze;
        this.zzaa = z;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        IBinder iBinder;
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzy, false);
        if (this.zzz == null) {
            Log.w("GoogleCertificatesQuery", "certificate binder is null");
            iBinder = null;
        } else {
            iBinder = this.zzz.asBinder();
        }
        SafeParcelWriter.writeIBinder(parcel, 2, iBinder, false);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzaa);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }

    @Nullable
    private static zze zza(@Nullable IBinder iBinder) {
        zze com_google_android_gms_common_zze = null;
        if (iBinder == null) {
            return null;
        }
        try {
            iBinder = zzj.zzb(iBinder).zzb();
            if (iBinder == null) {
                iBinder = null;
            } else {
                iBinder = (byte[]) ObjectWrapper.unwrap(iBinder);
            }
            if (iBinder != null) {
                com_google_android_gms_common_zze = new zzf(iBinder);
            } else {
                Log.e("GoogleCertificatesQuery", "Could not unwrap certificate");
            }
            return com_google_android_gms_common_zze;
        } catch (IBinder iBinder2) {
            Log.e("GoogleCertificatesQuery", "Could not unwrap certificate", iBinder2);
            return null;
        }
    }
}
