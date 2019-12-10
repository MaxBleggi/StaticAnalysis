package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import android.text.TextUtils;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

final class zzek implements Runnable {
    private final /* synthetic */ String zzaeh;
    private final /* synthetic */ String zzaeo;
    private final /* synthetic */ zzi zzaqv;
    private final /* synthetic */ String zzaqy;
    private final /* synthetic */ zzdz zzasv;
    private final /* synthetic */ AtomicReference zzasw;

    zzek(zzdz com_google_android_gms_measurement_internal_zzdz, AtomicReference atomicReference, String str, String str2, String str3, zzi com_google_android_gms_measurement_internal_zzi) {
        this.zzasv = com_google_android_gms_measurement_internal_zzdz;
        this.zzasw = atomicReference;
        this.zzaqy = str;
        this.zzaeh = str2;
        this.zzaeo = str3;
        this.zzaqv = com_google_android_gms_measurement_internal_zzi;
    }

    public final void run() {
        synchronized (this.zzasw) {
            try {
                zzah zzd = this.zzasv.zzasp;
                if (zzd == null) {
                    this.zzasv.zzgt().zzjg().zzd("Failed to get conditional properties", zzaq.zzby(this.zzaqy), this.zzaeh, this.zzaeo);
                    this.zzasw.set(Collections.emptyList());
                    this.zzasw.notify();
                    return;
                }
                if (TextUtils.isEmpty(this.zzaqy)) {
                    this.zzasw.set(zzd.zza(this.zzaeh, this.zzaeo, this.zzaqv));
                } else {
                    this.zzasw.set(zzd.zze(this.zzaqy, this.zzaeh, this.zzaeo));
                }
                this.zzasv.zzcy();
                this.zzasw.notify();
            } catch (RemoteException e) {
                try {
                    this.zzasv.zzgt().zzjg().zzd("Failed to get conditional properties", zzaq.zzby(this.zzaqy), this.zzaeh, e);
                    this.zzasw.set(Collections.emptyList());
                    this.zzasw.notify();
                } catch (Throwable th) {
                    this.zzasw.notify();
                }
            }
        }
    }
}
