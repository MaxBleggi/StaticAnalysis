package com.google.android.gms.internal.measurement;

import java.util.Arrays;

final class zzzt {
    final int tag;
    final byte[] zzbvb;

    zzzt(int i, byte[] bArr) {
        this.tag = i;
        this.zzbvb = bArr;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzzt)) {
            return false;
        }
        zzzt com_google_android_gms_internal_measurement_zzzt = (zzzt) obj;
        return this.tag == com_google_android_gms_internal_measurement_zzzt.tag && Arrays.equals(this.zzbvb, com_google_android_gms_internal_measurement_zzzt.zzbvb) != null;
    }

    public final int hashCode() {
        return ((this.tag + 527) * 31) + Arrays.hashCode(this.zzbvb);
    }
}
