package com.google.android.gms.internal.measurement;

import java.io.IOException;

public abstract class zzzr {
    protected volatile int zzcgh = -1;

    public abstract zzzr zza(zzzi com_google_android_gms_internal_measurement_zzzi) throws IOException;

    public void zza(zzzj com_google_android_gms_internal_measurement_zzzj) throws IOException {
    }

    protected int zzf() {
        return 0;
    }

    public final int zzzo() {
        if (this.zzcgh < 0) {
            zzwe();
        }
        return this.zzcgh;
    }

    public final int zzwe() {
        int zzf = zzf();
        this.zzcgh = zzf;
        return zzf;
    }

    public static final void zza(zzzr com_google_android_gms_internal_measurement_zzzr, byte[] bArr, int i, int i2) {
        try {
            zzzj zzk = zzzj.zzk(bArr, 0, i2);
            com_google_android_gms_internal_measurement_zzzr.zza(zzk);
            zzk.zzzh();
        } catch (zzzr com_google_android_gms_internal_measurement_zzzr2) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", com_google_android_gms_internal_measurement_zzzr2);
        }
    }

    public static final <T extends zzzr> T zza(T t, byte[] bArr) throws zzzq {
        return zzb(t, bArr, 0, bArr.length);
    }

    private static final <T extends zzzr> T zzb(T t, byte[] bArr, int i, int i2) throws zzzq {
        try {
            zzzi zzj = zzzi.zzj(bArr, 0, i2);
            t.zza(zzj);
            zzj.zzao(0);
            return t;
        } catch (T t2) {
            throw t2;
        } catch (T t22) {
            throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).", t22);
        }
    }

    public String toString() {
        return zzzs.zzc(this);
    }

    public zzzr zzzi() throws CloneNotSupportedException {
        return (zzzr) super.clone();
    }

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzzi();
    }
}
