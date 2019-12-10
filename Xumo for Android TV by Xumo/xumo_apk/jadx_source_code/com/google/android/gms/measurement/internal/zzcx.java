package com.google.android.gms.measurement.internal;

import android.content.Context;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;

@VisibleForTesting
public final class zzcx {
    boolean zzadv = true;
    String zzadx;
    String zzaps;
    String zzapt;
    Boolean zzaqm;
    zzal zzarj;
    final Context zzri;

    @VisibleForTesting
    public zzcx(Context context, zzal com_google_android_gms_measurement_internal_zzal) {
        Preconditions.checkNotNull(context);
        context = context.getApplicationContext();
        Preconditions.checkNotNull(context);
        this.zzri = context;
        if (com_google_android_gms_measurement_internal_zzal != null) {
            this.zzarj = com_google_android_gms_measurement_internal_zzal;
            this.zzadx = com_google_android_gms_measurement_internal_zzal.zzadx;
            this.zzaps = com_google_android_gms_measurement_internal_zzal.origin;
            this.zzapt = com_google_android_gms_measurement_internal_zzal.zzadw;
            this.zzadv = com_google_android_gms_measurement_internal_zzal.zzadv;
            if (com_google_android_gms_measurement_internal_zzal.zzady != null) {
                this.zzaqm = Boolean.valueOf(com_google_android_gms_measurement_internal_zzal.zzady.getBoolean("dataCollectionDefaultEnabled", true));
            }
        }
    }
}
