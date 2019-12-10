package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.measurement.zzea;

public final class zzez extends zzf {
    private Handler handler;
    @VisibleForTesting
    private long zzatl = zzbx().elapsedRealtime();
    @VisibleForTesting
    private long zzatm = this.zzatl;
    private final zzw zzatn = new zzfa(this, this.zzadp);
    private final zzw zzato = new zzfb(this, this.zzadp);
    private final zzw zzatp = new zzfc(this, this.zzadp);

    zzez(zzbu com_google_android_gms_measurement_internal_zzbu) {
        super(com_google_android_gms_measurement_internal_zzbu);
    }

    protected final boolean zzgy() {
        return false;
    }

    private final void zzlm() {
        synchronized (this) {
            if (this.handler == null) {
                this.handler = new zzea(Looper.getMainLooper());
            }
        }
    }

    @WorkerThread
    final void zzln() {
        zzaf();
        this.zzatn.cancel();
        this.zzato.cancel();
        this.zzatl = 0;
        this.zzatm = this.zzatl;
    }

    @WorkerThread
    private final void zzam(long j) {
        zzaf();
        zzlm();
        zzgt().zzjo().zzg("Activity resumed, time", Long.valueOf(j));
        this.zzatl = j;
        this.zzatm = this.zzatl;
        if (zzgv().zzbk(zzgk().zzal()) != null) {
            zzan(zzbx().currentTimeMillis());
            return;
        }
        this.zzatn.cancel();
        this.zzato.cancel();
        if (!(zzgv().zzbi(zzgk().zzal()) == null && zzgv().zzbj(zzgk().zzal()) == null)) {
            this.zzatp.cancel();
        }
        if (zzgu().zzaj(zzbx().currentTimeMillis()) != null) {
            zzgu().zzany.set(true);
            zzgu().zzaoa.set(0);
        }
        if (zzgu().zzany.get() != null) {
            this.zzatn.zzh(Math.max(0, zzgu().zzanw.get() - zzgu().zzaoa.get()));
        } else {
            this.zzato.zzh(Math.max(0, 3600000 - zzgu().zzaoa.get()));
        }
    }

    @WorkerThread
    final void zzan(long j) {
        zzaf();
        zzlm();
        zza(j, false);
    }

    @WorkerThread
    final void zza(long j, boolean z) {
        zzaf();
        zzlm();
        this.zzatn.cancel();
        this.zzato.cancel();
        if (zzgv().zzbi(zzgk().zzal()) || zzgv().zzbj(zzgk().zzal())) {
            this.zzatp.cancel();
        }
        if (zzgu().zzaj(j)) {
            zzgu().zzany.set(true);
            zzgu().zzaoa.set(0);
        }
        if (zzgu().zzany.get()) {
            zzap(j);
            return;
        }
        this.zzato.zzh(Math.max(0, 3600000 - zzgu().zzaoa.get()));
        if (z && zzgv().zzbl(zzgk().zzal())) {
            zzgu().zzanz.set(j);
            if (!(zzgv().zzbi(zzgk().zzal()) == null && zzgv().zzbj(zzgk().zzal()) == null)) {
                this.zzatp.cancel();
                this.zzatp.zzh(zzgu().zzanx.get());
            }
        }
    }

    @WorkerThread
    private final void zzao(long j) {
        zzaf();
        zzlm();
        this.zzatn.cancel();
        this.zzato.cancel();
        if (zzgv().zzbi(zzgk().zzal()) || zzgv().zzbj(zzgk().zzal())) {
            this.zzatp.cancel();
            this.zzatp.zzh(zzgu().zzanx.get());
        }
        zzgt().zzjo().zzg("Activity paused, time", Long.valueOf(j));
        if (this.zzatl != 0) {
            zzgu().zzaoa.set(zzgu().zzaoa.get() + (j - this.zzatl));
        }
    }

    @WorkerThread
    private final void zzap(long j) {
        zzaf();
        zzgt().zzjo().zzg("Session started, time", Long.valueOf(zzbx().elapsedRealtime()));
        Long l = null;
        Long valueOf = zzgv().zzbi(zzgk().zzal()) ? Long.valueOf(j / 1000) : null;
        if (zzgv().zzbj(zzgk().zzal())) {
            l = Long.valueOf(-1);
        }
        long j2 = j;
        zzgj().zza("auto", "_sid", (Object) valueOf, j2);
        zzgj().zza("auto", "_sno", (Object) l, j2);
        zzgu().zzany.set(false);
        Bundle bundle = new Bundle();
        if (zzgv().zzbi(zzgk().zzal())) {
            bundle.putLong("_sid", valueOf.longValue());
        }
        zzgj().zza("auto", "_s", j, bundle);
        zzgu().zzanz.set(j);
    }

    @WorkerThread
    @VisibleForTesting
    protected final void zzlo() {
        zzaf();
        zzap(zzbx().currentTimeMillis());
    }

    @WorkerThread
    @VisibleForTesting
    final long zzlp() {
        long elapsedRealtime = zzbx().elapsedRealtime();
        long j = elapsedRealtime - this.zzatm;
        this.zzatm = elapsedRealtime;
        return j;
    }

    @WorkerThread
    public final boolean zza(boolean z, boolean z2) {
        zzaf();
        zzcl();
        long elapsedRealtime = zzbx().elapsedRealtime();
        zzgu().zzanz.set(zzbx().currentTimeMillis());
        long j = elapsedRealtime - this.zzatl;
        if (z || j >= 1000) {
            zzgu().zzaoa.set(j);
            zzgt().zzjo().zzg("Recording user engagement, ms", Long.valueOf(j));
            Bundle bundle = new Bundle();
            bundle.putLong("_et", j);
            zzdw.zza(zzgm().zzle(), bundle, true);
            if (zzgv().zzbm(zzgk().zzal())) {
                if (zzgv().zze(zzgk().zzal(), zzag.zzalm)) {
                    if (!z2) {
                        zzlp();
                    }
                } else if (z2) {
                    bundle.putLong("_fr", 1);
                } else {
                    zzlp();
                }
            }
            if (!(zzgv().zze(zzgk().zzal(), zzag.zzalm) && z2)) {
                zzgj().logEvent("auto", "_e", bundle);
            }
            this.zzatl = elapsedRealtime;
            this.zzato.cancel();
            this.zzato.zzh(Math.max(0, 3600000 - zzgu().zzaoa.get()));
            return true;
        }
        zzgt().zzjo().zzg("Screen exposed for less than 1000 ms. Event not sent. time", Long.valueOf(j));
        return false;
    }

    @WorkerThread
    private final void zzlq() {
        zzaf();
        zza(false, false);
        zzgi().zzq(zzbx().elapsedRealtime());
    }

    public final /* bridge */ /* synthetic */ void zzgf() {
        super.zzgf();
    }

    public final /* bridge */ /* synthetic */ void zzgg() {
        super.zzgg();
    }

    public final /* bridge */ /* synthetic */ void zzgh() {
        super.zzgh();
    }

    public final /* bridge */ /* synthetic */ void zzaf() {
        super.zzaf();
    }

    public final /* bridge */ /* synthetic */ zza zzgi() {
        return super.zzgi();
    }

    public final /* bridge */ /* synthetic */ zzcy zzgj() {
        return super.zzgj();
    }

    public final /* bridge */ /* synthetic */ zzak zzgk() {
        return super.zzgk();
    }

    public final /* bridge */ /* synthetic */ zzdz zzgl() {
        return super.zzgl();
    }

    public final /* bridge */ /* synthetic */ zzdw zzgm() {
        return super.zzgm();
    }

    public final /* bridge */ /* synthetic */ zzam zzgn() {
        return super.zzgn();
    }

    public final /* bridge */ /* synthetic */ zzez zzgo() {
        return super.zzgo();
    }

    public final /* bridge */ /* synthetic */ zzy zzgp() {
        return super.zzgp();
    }

    public final /* bridge */ /* synthetic */ Clock zzbx() {
        return super.zzbx();
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final /* bridge */ /* synthetic */ zzao zzgq() {
        return super.zzgq();
    }

    public final /* bridge */ /* synthetic */ zzfu zzgr() {
        return super.zzgr();
    }

    public final /* bridge */ /* synthetic */ zzbp zzgs() {
        return super.zzgs();
    }

    public final /* bridge */ /* synthetic */ zzaq zzgt() {
        return super.zzgt();
    }

    public final /* bridge */ /* synthetic */ zzbb zzgu() {
        return super.zzgu();
    }

    public final /* bridge */ /* synthetic */ zzo zzgv() {
        return super.zzgv();
    }

    public final /* bridge */ /* synthetic */ zzl zzgw() {
        return super.zzgw();
    }
}
