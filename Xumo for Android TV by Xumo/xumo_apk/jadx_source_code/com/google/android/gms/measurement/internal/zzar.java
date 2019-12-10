package com.google.android.gms.measurement.internal;

final class zzar implements Runnable {
    private final /* synthetic */ int zzamo;
    private final /* synthetic */ String zzamp;
    private final /* synthetic */ Object zzamq;
    private final /* synthetic */ Object zzamr;
    private final /* synthetic */ Object zzams;
    private final /* synthetic */ zzaq zzamt;

    zzar(zzaq com_google_android_gms_measurement_internal_zzaq, int i, String str, Object obj, Object obj2, Object obj3) {
        this.zzamt = com_google_android_gms_measurement_internal_zzaq;
        this.zzamo = i;
        this.zzamp = str;
        this.zzamq = obj;
        this.zzamr = obj2;
        this.zzams = obj3;
    }

    public final void run() {
        zzcq zzgu = this.zzamt.zzadp.zzgu();
        if (zzgu.isInitialized()) {
            if (this.zzamt.zzamd == '\u0000') {
                zzaq com_google_android_gms_measurement_internal_zzaq;
                if (this.zzamt.zzgv().zzdw()) {
                    com_google_android_gms_measurement_internal_zzaq = this.zzamt;
                    this.zzamt.zzgw();
                    com_google_android_gms_measurement_internal_zzaq.zzamd = 'C';
                } else {
                    com_google_android_gms_measurement_internal_zzaq = this.zzamt;
                    this.zzamt.zzgw();
                    com_google_android_gms_measurement_internal_zzaq.zzamd = 'c';
                }
            }
            if (this.zzamt.zzadt < 0) {
                this.zzamt.zzadt = this.zzamt.zzgv().zzhh();
            }
            char charAt = "01VDIWEA?".charAt(this.zzamo);
            char zza = this.zzamt.zzamd;
            long zzb = this.zzamt.zzadt;
            String zza2 = zzaq.zza(true, this.zzamp, this.zzamq, this.zzamr, this.zzams);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(zza2).length() + 24);
            stringBuilder.append("2");
            stringBuilder.append(charAt);
            stringBuilder.append(zza);
            stringBuilder.append(zzb);
            stringBuilder.append(":");
            stringBuilder.append(zza2);
            String stringBuilder2 = stringBuilder.toString();
            if (stringBuilder2.length() > 1024) {
                stringBuilder2 = this.zzamp.substring(0, 1024);
            }
            zzgu.zzank.zzc(stringBuilder2, 1);
            return;
        }
        this.zzamt.zza(6, "Persisted config not initialized. Not logging error/warn");
    }
}
