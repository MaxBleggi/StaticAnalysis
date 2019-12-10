package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;

public final class zzz {
    final String name;
    private final String origin;
    final long timestamp;
    final long zzaif;
    final zzab zzaig;
    final String zztt;

    private zzz(zzbu com_google_android_gms_measurement_internal_zzbu, String str, String str2, String str3, long j, long j2, zzab com_google_android_gms_measurement_internal_zzab) {
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkNotEmpty(str3);
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzab);
        this.zztt = str2;
        this.name = str3;
        if (TextUtils.isEmpty(str)) {
            str = null;
        }
        this.origin = str;
        this.timestamp = j;
        this.zzaif = j2;
        if (this.zzaif != 0 && this.zzaif > this.timestamp) {
            com_google_android_gms_measurement_internal_zzbu.zzgt().zzjj().zze("Event created with reverse previous/current timestamps. appId, name", zzaq.zzby(str2), zzaq.zzby(str3));
        }
        this.zzaig = com_google_android_gms_measurement_internal_zzab;
    }

    zzz(zzbu com_google_android_gms_measurement_internal_zzbu, String str, String str2, String str3, long j, long j2, Bundle bundle) {
        Preconditions.checkNotEmpty(str2);
        Preconditions.checkNotEmpty(str3);
        this.zztt = str2;
        this.name = str3;
        if (TextUtils.isEmpty(str) != null) {
            str = null;
        }
        this.origin = str;
        this.timestamp = j;
        this.zzaif = j2;
        if (this.zzaif != 0 && this.zzaif > this.timestamp) {
            com_google_android_gms_measurement_internal_zzbu.zzgt().zzjj().zzg("Event created with reverse previous/current timestamps. appId", zzaq.zzby(str2));
        }
        if (bundle == null || bundle.isEmpty() != null) {
            com_google_android_gms_measurement_internal_zzbu = new zzab(new Bundle());
        } else {
            Bundle bundle2 = new Bundle(bundle);
            str2 = bundle2.keySet().iterator();
            while (str2.hasNext() != null) {
                str3 = (String) str2.next();
                if (str3 == null) {
                    com_google_android_gms_measurement_internal_zzbu.zzgt().zzjg().zzca("Param name can't be null");
                    str2.remove();
                } else {
                    Object zzh = com_google_android_gms_measurement_internal_zzbu.zzgr().zzh(str3, bundle2.get(str3));
                    if (zzh == null) {
                        com_google_android_gms_measurement_internal_zzbu.zzgt().zzjj().zzg("Param value can't be null", com_google_android_gms_measurement_internal_zzbu.zzgq().zzbw(str3));
                        str2.remove();
                    } else {
                        com_google_android_gms_measurement_internal_zzbu.zzgr().zza(bundle2, str3, zzh);
                    }
                }
            }
            com_google_android_gms_measurement_internal_zzbu = new zzab(bundle2);
        }
        this.zzaig = com_google_android_gms_measurement_internal_zzbu;
    }

    final zzz zza(zzbu com_google_android_gms_measurement_internal_zzbu, long j) {
        return new zzz(com_google_android_gms_measurement_internal_zzbu, this.origin, this.zztt, this.name, this.timestamp, j, this.zzaig);
    }

    public final String toString() {
        String str = this.zztt;
        String str2 = this.name;
        String valueOf = String.valueOf(this.zzaig);
        StringBuilder stringBuilder = new StringBuilder(((String.valueOf(str).length() + 33) + String.valueOf(str2).length()) + String.valueOf(valueOf).length());
        stringBuilder.append("Event{appId='");
        stringBuilder.append(str);
        stringBuilder.append("', name='");
        stringBuilder.append(str2);
        stringBuilder.append("', params=");
        stringBuilder.append(valueOf);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
