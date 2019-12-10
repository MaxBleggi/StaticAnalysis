package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.WorkerThread;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import java.util.Map;

public final class zza extends zze {
    private final Map<String, Long> zzafq = new ArrayMap();
    private final Map<String, Integer> zzafr = new ArrayMap();
    private long zzafs;

    public zza(zzbu com_google_android_gms_measurement_internal_zzbu) {
        super(com_google_android_gms_measurement_internal_zzbu);
    }

    public final void beginAdUnitExposure(String str, long j) {
        if (str != null) {
            if (str.length() != 0) {
                zzgs().zzc(new zzb(this, str, j));
                return;
            }
        }
        zzgt().zzjg().zzca("Ad unit id must be a non-empty string");
    }

    @WorkerThread
    private final void zza(String str, long j) {
        zzgg();
        zzaf();
        Preconditions.checkNotEmpty(str);
        if (this.zzafr.isEmpty()) {
            this.zzafs = j;
        }
        Integer num = (Integer) this.zzafr.get(str);
        if (num != null) {
            this.zzafr.put(str, Integer.valueOf(num.intValue() + 1));
        } else if (this.zzafr.size() >= 100) {
            zzgt().zzjj().zzca("Too many ads visible");
        } else {
            this.zzafr.put(str, Integer.valueOf(1));
            this.zzafq.put(str, Long.valueOf(j));
        }
    }

    public final void endAdUnitExposure(String str, long j) {
        if (str != null) {
            if (str.length() != 0) {
                zzgs().zzc(new zzc(this, str, j));
                return;
            }
        }
        zzgt().zzjg().zzca("Ad unit id must be a non-empty string");
    }

    @WorkerThread
    private final void zzb(String str, long j) {
        zzgg();
        zzaf();
        Preconditions.checkNotEmpty(str);
        Integer num = (Integer) this.zzafr.get(str);
        if (num != null) {
            zzdv zzle = zzgm().zzle();
            int intValue = num.intValue() - 1;
            if (intValue == 0) {
                this.zzafr.remove(str);
                Long l = (Long) this.zzafq.get(str);
                if (l == null) {
                    zzgt().zzjg().zzca("First ad unit exposure time was never set");
                } else {
                    long longValue = j - l.longValue();
                    this.zzafq.remove(str);
                    zza(str, longValue, zzle);
                }
                if (this.zzafr.isEmpty() != null) {
                    if (this.zzafs == 0) {
                        zzgt().zzjg().zzca("First ad exposure time was never set");
                        return;
                    } else {
                        zza(j - this.zzafs, zzle);
                        this.zzafs = 0;
                    }
                }
                return;
            }
            this.zzafr.put(str, Integer.valueOf(intValue));
            return;
        }
        zzgt().zzjg().zzg("Call to endAdUnitExposure for unknown ad unit id", str);
    }

    @WorkerThread
    private final void zza(long j, zzdv com_google_android_gms_measurement_internal_zzdv) {
        if (com_google_android_gms_measurement_internal_zzdv == null) {
            zzgt().zzjo().zzca("Not logging ad exposure. No active activity");
        } else if (j < 1000) {
            zzgt().zzjo().zzg("Not logging ad exposure. Less than 1000 ms. exposure", Long.valueOf(j));
        } else {
            Bundle bundle = new Bundle();
            bundle.putLong("_xt", j);
            zzdw.zza(com_google_android_gms_measurement_internal_zzdv, bundle, (boolean) 1);
            zzgj().logEvent("am", "_xa", bundle);
        }
    }

    @WorkerThread
    private final void zza(String str, long j, zzdv com_google_android_gms_measurement_internal_zzdv) {
        if (com_google_android_gms_measurement_internal_zzdv == null) {
            zzgt().zzjo().zzca("Not logging ad unit exposure. No active activity");
        } else if (j < 1000) {
            zzgt().zzjo().zzg("Not logging ad unit exposure. Less than 1000 ms. exposure", Long.valueOf(j));
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("_ai", str);
            bundle.putLong("_xt", j);
            zzdw.zza(com_google_android_gms_measurement_internal_zzdv, bundle, true);
            zzgj().logEvent("am", "_xu", bundle);
        }
    }

    @WorkerThread
    public final void zzq(long j) {
        zzdv zzle = zzgm().zzle();
        for (String str : this.zzafq.keySet()) {
            zza(str, j - ((Long) this.zzafq.get(str)).longValue(), zzle);
        }
        if (!this.zzafq.isEmpty()) {
            zza(j - this.zzafs, zzle);
        }
        zzr(j);
    }

    @WorkerThread
    private final void zzr(long j) {
        for (String put : this.zzafq.keySet()) {
            this.zzafq.put(put, Long.valueOf(j));
        }
        if (!this.zzafq.isEmpty()) {
            this.zzafs = j;
        }
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
