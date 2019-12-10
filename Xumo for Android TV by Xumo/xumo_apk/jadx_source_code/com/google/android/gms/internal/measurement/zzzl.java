package com.google.android.gms.internal.measurement;

import java.io.IOException;

public abstract class zzzl<M extends zzzl<M>> extends zzzr {
    protected zzzn zzcfx;

    protected int zzf() {
        if (this.zzcfx == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < this.zzcfx.size(); i2++) {
            i += this.zzcfx.zzce(i2).zzf();
        }
        return i;
    }

    public void zza(zzzj com_google_android_gms_internal_measurement_zzzj) throws IOException {
        if (this.zzcfx != null) {
            for (int i = 0; i < this.zzcfx.size(); i++) {
                this.zzcfx.zzce(i).zza(com_google_android_gms_internal_measurement_zzzj);
            }
        }
    }

    public final <T> T zza(zzzm<M, T> com_google_android_gms_internal_measurement_zzzm_M__T) {
        if (this.zzcfx == null) {
            return null;
        }
        zzzo zzcd = this.zzcfx.zzcd(com_google_android_gms_internal_measurement_zzzm_M__T.tag >>> 3);
        if (zzcd == null) {
            return null;
        }
        return zzcd.zzb(com_google_android_gms_internal_measurement_zzzm_M__T);
    }

    protected final boolean zza(zzzi com_google_android_gms_internal_measurement_zzzi, int i) throws IOException {
        int position = com_google_android_gms_internal_measurement_zzzi.getPosition();
        if (!com_google_android_gms_internal_measurement_zzzi.zzap(i)) {
            return null;
        }
        int i2 = i >>> 3;
        zzzt com_google_android_gms_internal_measurement_zzzt = new zzzt(i, com_google_android_gms_internal_measurement_zzzi.zzs(position, com_google_android_gms_internal_measurement_zzzi.getPosition() - position));
        com_google_android_gms_internal_measurement_zzzi = null;
        if (this.zzcfx == 0) {
            this.zzcfx = new zzzn();
        } else {
            com_google_android_gms_internal_measurement_zzzi = this.zzcfx.zzcd(i2);
        }
        if (com_google_android_gms_internal_measurement_zzzi == null) {
            com_google_android_gms_internal_measurement_zzzi = new zzzo();
            this.zzcfx.zza(i2, com_google_android_gms_internal_measurement_zzzi);
        }
        com_google_android_gms_internal_measurement_zzzi.zza(com_google_android_gms_internal_measurement_zzzt);
        return true;
    }

    public final /* synthetic */ zzzr zzzi() throws CloneNotSupportedException {
        return (zzzl) clone();
    }

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzzl com_google_android_gms_internal_measurement_zzzl = (zzzl) super.zzzi();
        zzzp.zza(this, com_google_android_gms_internal_measurement_zzzl);
        return com_google_android_gms_internal_measurement_zzzl;
    }
}
