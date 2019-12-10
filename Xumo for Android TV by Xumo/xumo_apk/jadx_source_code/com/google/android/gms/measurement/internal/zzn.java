package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

public final class zzn implements Creator<zzm> {
    public final /* synthetic */ Object[] newArray(int i) {
        return new zzm[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        Parcel parcel2 = parcel;
        int validateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        long j = 0;
        long j2 = j;
        long j3 = j2;
        String str = null;
        String str2 = str;
        zzfr com_google_android_gms_measurement_internal_zzfr = str2;
        String str3 = com_google_android_gms_measurement_internal_zzfr;
        zzae com_google_android_gms_measurement_internal_zzae = str3;
        zzae com_google_android_gms_measurement_internal_zzae2 = com_google_android_gms_measurement_internal_zzae;
        zzae com_google_android_gms_measurement_internal_zzae3 = com_google_android_gms_measurement_internal_zzae2;
        boolean z = false;
        while (parcel.dataPosition() < validateObjectHeader) {
            int readHeader = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(readHeader)) {
                case 2:
                    str = SafeParcelReader.createString(parcel2, readHeader);
                    break;
                case 3:
                    str2 = SafeParcelReader.createString(parcel2, readHeader);
                    break;
                case 4:
                    com_google_android_gms_measurement_internal_zzfr = (zzfr) SafeParcelReader.createParcelable(parcel2, readHeader, zzfr.CREATOR);
                    break;
                case 5:
                    j = SafeParcelReader.readLong(parcel2, readHeader);
                    break;
                case 6:
                    z = SafeParcelReader.readBoolean(parcel2, readHeader);
                    break;
                case 7:
                    str3 = SafeParcelReader.createString(parcel2, readHeader);
                    break;
                case 8:
                    com_google_android_gms_measurement_internal_zzae = (zzae) SafeParcelReader.createParcelable(parcel2, readHeader, zzae.CREATOR);
                    break;
                case 9:
                    j2 = SafeParcelReader.readLong(parcel2, readHeader);
                    break;
                case 10:
                    com_google_android_gms_measurement_internal_zzae2 = (zzae) SafeParcelReader.createParcelable(parcel2, readHeader, zzae.CREATOR);
                    break;
                case 11:
                    j3 = SafeParcelReader.readLong(parcel2, readHeader);
                    break;
                case 12:
                    com_google_android_gms_measurement_internal_zzae3 = (zzae) SafeParcelReader.createParcelable(parcel2, readHeader, zzae.CREATOR);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel2, readHeader);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel2, validateObjectHeader);
        return new zzm(str, str2, com_google_android_gms_measurement_internal_zzfr, j, z, str3, com_google_android_gms_measurement_internal_zzae, j2, com_google_android_gms_measurement_internal_zzae2, j3, com_google_android_gms_measurement_internal_zzae3);
    }
}
