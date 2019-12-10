package com.google.android.gms.measurement.internal;

import android.os.Bundle;

final class zzdx implements Runnable {
    private final /* synthetic */ boolean zzasj;
    private final /* synthetic */ zzdv zzask;
    private final /* synthetic */ zzdv zzasl;
    private final /* synthetic */ zzdw zzasm;

    zzdx(zzdw com_google_android_gms_measurement_internal_zzdw, boolean z, zzdv com_google_android_gms_measurement_internal_zzdv, zzdv com_google_android_gms_measurement_internal_zzdv2) {
        this.zzasm = com_google_android_gms_measurement_internal_zzdw;
        this.zzasj = z;
        this.zzask = com_google_android_gms_measurement_internal_zzdv;
        this.zzasl = com_google_android_gms_measurement_internal_zzdv2;
    }

    public final void run() {
        Object obj = null;
        Object obj2;
        if (this.zzasm.zzgv().zzbm(this.zzasm.zzgk().zzal())) {
            obj2 = (!this.zzasj || this.zzasm.zzasd == null) ? null : 1;
            if (obj2 != null) {
                this.zzasm.zza(this.zzasm.zzasd, true);
            }
        } else {
            if (this.zzasj && this.zzasm.zzasd != null) {
                this.zzasm.zza(this.zzasm.zzasd, true);
            }
            obj2 = null;
        }
        if (!(this.zzask != null && this.zzask.zzasb == this.zzasl.zzasb && zzfu.zzv(this.zzask.zzasa, this.zzasl.zzasa) && zzfu.zzv(this.zzask.zzuw, this.zzasl.zzuw))) {
            obj = 1;
        }
        if (obj != null) {
            Bundle bundle = new Bundle();
            zzdw.zza(this.zzasl, bundle, true);
            if (this.zzask != null) {
                if (this.zzask.zzuw != null) {
                    bundle.putString("_pn", this.zzask.zzuw);
                }
                bundle.putString("_pc", this.zzask.zzasa);
                bundle.putLong("_pi", this.zzask.zzasb);
            }
            if (this.zzasm.zzgv().zzbm(this.zzasm.zzgk().zzal()) && r0 != null) {
                long zzlp = this.zzasm.zzgo().zzlp();
                if (zzlp > 0) {
                    this.zzasm.zzgr().zza(bundle, zzlp);
                }
            }
            this.zzasm.zzgj().zza("auto", "_vs", bundle);
        }
        this.zzasm.zzasd = this.zzasl;
        this.zzasm.zzgl().zza(this.zzasl);
    }
}
