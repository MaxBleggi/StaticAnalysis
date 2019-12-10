package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzvx.zze;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

final class zzvg implements zzzh {
    private final zzve zzbva;

    public static zzvg zza(zzve com_google_android_gms_internal_measurement_zzve) {
        if (com_google_android_gms_internal_measurement_zzve.zzbvr != null) {
            return com_google_android_gms_internal_measurement_zzve.zzbvr;
        }
        return new zzvg(com_google_android_gms_internal_measurement_zzve);
    }

    private zzvg(zzve com_google_android_gms_internal_measurement_zzve) {
        this.zzbva = (zzve) zzvz.zza(com_google_android_gms_internal_measurement_zzve, "output");
    }

    public final int zzvt() {
        return zze.zzbzz;
    }

    public final void zzn(int i, int i2) throws IOException {
        this.zzbva.zzg(i, i2);
    }

    public final void zzi(int i, long j) throws IOException {
        this.zzbva.zza(i, j);
    }

    public final void zzj(int i, long j) throws IOException {
        this.zzbva.zzc(i, j);
    }

    public final void zza(int i, float f) throws IOException {
        this.zzbva.zza(i, f);
    }

    public final void zza(int i, double d) throws IOException {
        this.zzbva.zza(i, d);
    }

    public final void zzo(int i, int i2) throws IOException {
        this.zzbva.zzd(i, i2);
    }

    public final void zza(int i, long j) throws IOException {
        this.zzbva.zza(i, j);
    }

    public final void zzd(int i, int i2) throws IOException {
        this.zzbva.zzd(i, i2);
    }

    public final void zzc(int i, long j) throws IOException {
        this.zzbva.zzc(i, j);
    }

    public final void zzg(int i, int i2) throws IOException {
        this.zzbva.zzg(i, i2);
    }

    public final void zzb(int i, boolean z) throws IOException {
        this.zzbva.zzb(i, z);
    }

    public final void zzb(int i, String str) throws IOException {
        this.zzbva.zzb(i, str);
    }

    public final void zza(int i, zzun com_google_android_gms_internal_measurement_zzun) throws IOException {
        this.zzbva.zza(i, com_google_android_gms_internal_measurement_zzun);
    }

    public final void zze(int i, int i2) throws IOException {
        this.zzbva.zze(i, i2);
    }

    public final void zzf(int i, int i2) throws IOException {
        this.zzbva.zzf(i, i2);
    }

    public final void zzb(int i, long j) throws IOException {
        this.zzbva.zzb(i, j);
    }

    public final void zza(int i, Object obj, zzxu com_google_android_gms_internal_measurement_zzxu) throws IOException {
        this.zzbva.zza(i, (zzxe) obj, com_google_android_gms_internal_measurement_zzxu);
    }

    public final void zzb(int i, Object obj, zzxu com_google_android_gms_internal_measurement_zzxu) throws IOException {
        zzve com_google_android_gms_internal_measurement_zzve = this.zzbva;
        zzxe com_google_android_gms_internal_measurement_zzxe = (zzxe) obj;
        com_google_android_gms_internal_measurement_zzve.zzc(i, 3);
        com_google_android_gms_internal_measurement_zzxu.zza(com_google_android_gms_internal_measurement_zzxe, com_google_android_gms_internal_measurement_zzve.zzbvr);
        com_google_android_gms_internal_measurement_zzve.zzc(i, 4);
    }

    public final void zzbl(int i) throws IOException {
        this.zzbva.zzc(i, 3);
    }

    public final void zzbm(int i) throws IOException {
        this.zzbva.zzc(i, 4);
    }

    public final void zza(int i, Object obj) throws IOException {
        if (obj instanceof zzun) {
            this.zzbva.zzb(i, (zzun) obj);
        } else {
            this.zzbva.zzb(i, (zzxe) obj);
        }
    }

    public final void zza(int i, List<Integer> list, boolean z) throws IOException {
        boolean z2 = false;
        if (z) {
            this.zzbva.zzc(i, 2);
            int i2 = false;
            for (i = 0; i < list.size(); i++) {
                i2 += zzve.zzbd(((Integer) list.get(i)).intValue());
            }
            this.zzbva.zzaz(i2);
            int i3;
            while (i3 < list.size()) {
                this.zzbva.zzay(((Integer) list.get(i3)).intValue());
                i3++;
            }
            return;
        }
        while (z2 < list.size()) {
            this.zzbva.zzd(i, ((Integer) list.get(z2)).intValue());
            z2++;
        }
    }

    public final void zzb(int i, List<Integer> list, boolean z) throws IOException {
        boolean z2 = false;
        if (z) {
            this.zzbva.zzc(i, 2);
            int i2 = false;
            for (i = 0; i < list.size(); i++) {
                i2 += zzve.zzbg(((Integer) list.get(i)).intValue());
            }
            this.zzbva.zzaz(i2);
            int i3;
            while (i3 < list.size()) {
                this.zzbva.zzbb(((Integer) list.get(i3)).intValue());
                i3++;
            }
            return;
        }
        while (z2 < list.size()) {
            this.zzbva.zzg(i, ((Integer) list.get(z2)).intValue());
            z2++;
        }
    }

    public final void zzc(int i, List<Long> list, boolean z) throws IOException {
        boolean z2 = false;
        if (z) {
            this.zzbva.zzc(i, 2);
            int i2 = false;
            for (i = 0; i < list.size(); i++) {
                i2 += zzve.zzbb(((Long) list.get(i)).longValue());
            }
            this.zzbva.zzaz(i2);
            int i3;
            while (i3 < list.size()) {
                this.zzbva.zzay(((Long) list.get(i3)).longValue());
                i3++;
            }
            return;
        }
        while (z2 < list.size()) {
            this.zzbva.zza(i, ((Long) list.get(z2)).longValue());
            z2++;
        }
    }

    public final void zzd(int i, List<Long> list, boolean z) throws IOException {
        boolean z2 = false;
        if (z) {
            this.zzbva.zzc(i, 2);
            int i2 = false;
            for (i = 0; i < list.size(); i++) {
                i2 += zzve.zzbc(((Long) list.get(i)).longValue());
            }
            this.zzbva.zzaz(i2);
            int i3;
            while (i3 < list.size()) {
                this.zzbva.zzay(((Long) list.get(i3)).longValue());
                i3++;
            }
            return;
        }
        while (z2 < list.size()) {
            this.zzbva.zza(i, ((Long) list.get(z2)).longValue());
            z2++;
        }
    }

    public final void zze(int i, List<Long> list, boolean z) throws IOException {
        boolean z2 = false;
        if (z) {
            this.zzbva.zzc(i, 2);
            int i2 = false;
            for (i = 0; i < list.size(); i++) {
                i2 += zzve.zzbe(((Long) list.get(i)).longValue());
            }
            this.zzbva.zzaz(i2);
            int i3;
            while (i3 < list.size()) {
                this.zzbva.zzba(((Long) list.get(i3)).longValue());
                i3++;
            }
            return;
        }
        while (z2 < list.size()) {
            this.zzbva.zzc(i, ((Long) list.get(z2)).longValue());
            z2++;
        }
    }

    public final void zzf(int i, List<Float> list, boolean z) throws IOException {
        boolean z2 = false;
        if (z) {
            this.zzbva.zzc(i, 2);
            int i2 = false;
            for (i = 0; i < list.size(); i++) {
                i2 += zzve.zzb(((Float) list.get(i)).floatValue());
            }
            this.zzbva.zzaz(i2);
            int i3;
            while (i3 < list.size()) {
                this.zzbva.zza(((Float) list.get(i3)).floatValue());
                i3++;
            }
            return;
        }
        while (z2 < list.size()) {
            this.zzbva.zza(i, ((Float) list.get(z2)).floatValue());
            z2++;
        }
    }

    public final void zzg(int i, List<Double> list, boolean z) throws IOException {
        boolean z2 = false;
        if (z) {
            this.zzbva.zzc(i, 2);
            int i2 = false;
            for (i = 0; i < list.size(); i++) {
                i2 += zzve.zzc(((Double) list.get(i)).doubleValue());
            }
            this.zzbva.zzaz(i2);
            int i3;
            while (i3 < list.size()) {
                this.zzbva.zzb(((Double) list.get(i3)).doubleValue());
                i3++;
            }
            return;
        }
        while (z2 < list.size()) {
            this.zzbva.zza(i, ((Double) list.get(z2)).doubleValue());
            z2++;
        }
    }

    public final void zzh(int i, List<Integer> list, boolean z) throws IOException {
        boolean z2 = false;
        if (z) {
            this.zzbva.zzc(i, 2);
            int i2 = false;
            for (i = 0; i < list.size(); i++) {
                i2 += zzve.zzbi(((Integer) list.get(i)).intValue());
            }
            this.zzbva.zzaz(i2);
            int i3;
            while (i3 < list.size()) {
                this.zzbva.zzay(((Integer) list.get(i3)).intValue());
                i3++;
            }
            return;
        }
        while (z2 < list.size()) {
            this.zzbva.zzd(i, ((Integer) list.get(z2)).intValue());
            z2++;
        }
    }

    public final void zzi(int i, List<Boolean> list, boolean z) throws IOException {
        boolean z2 = false;
        if (z) {
            this.zzbva.zzc(i, 2);
            int i2 = false;
            for (i = 0; i < list.size(); i++) {
                i2 += zzve.zzt(((Boolean) list.get(i)).booleanValue());
            }
            this.zzbva.zzaz(i2);
            int i3;
            while (i3 < list.size()) {
                this.zzbva.zzs(((Boolean) list.get(i3)).booleanValue());
                i3++;
            }
            return;
        }
        while (z2 < list.size()) {
            this.zzbva.zzb(i, ((Boolean) list.get(z2)).booleanValue());
            z2++;
        }
    }

    public final void zza(int i, List<String> list) throws IOException {
        int i2 = 0;
        if (list instanceof zzwn) {
            zzwn com_google_android_gms_internal_measurement_zzwn = (zzwn) list;
            while (i2 < list.size()) {
                Object zzbo = com_google_android_gms_internal_measurement_zzwn.zzbo(i2);
                if (zzbo instanceof String) {
                    this.zzbva.zzb(i, (String) zzbo);
                } else {
                    this.zzbva.zza(i, (zzun) zzbo);
                }
                i2++;
            }
            return;
        }
        while (i2 < list.size()) {
            this.zzbva.zzb(i, (String) list.get(i2));
            i2++;
        }
    }

    public final void zzb(int i, List<zzun> list) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            this.zzbva.zza(i, (zzun) list.get(i2));
        }
    }

    public final void zzj(int i, List<Integer> list, boolean z) throws IOException {
        boolean z2 = false;
        if (z) {
            this.zzbva.zzc(i, 2);
            int i2 = false;
            for (i = 0; i < list.size(); i++) {
                i2 += zzve.zzbe(((Integer) list.get(i)).intValue());
            }
            this.zzbva.zzaz(i2);
            int i3;
            while (i3 < list.size()) {
                this.zzbva.zzaz(((Integer) list.get(i3)).intValue());
                i3++;
            }
            return;
        }
        while (z2 < list.size()) {
            this.zzbva.zze(i, ((Integer) list.get(z2)).intValue());
            z2++;
        }
    }

    public final void zzk(int i, List<Integer> list, boolean z) throws IOException {
        boolean z2 = false;
        if (z) {
            this.zzbva.zzc(i, 2);
            int i2 = false;
            for (i = 0; i < list.size(); i++) {
                i2 += zzve.zzbh(((Integer) list.get(i)).intValue());
            }
            this.zzbva.zzaz(i2);
            int i3;
            while (i3 < list.size()) {
                this.zzbva.zzbb(((Integer) list.get(i3)).intValue());
                i3++;
            }
            return;
        }
        while (z2 < list.size()) {
            this.zzbva.zzg(i, ((Integer) list.get(z2)).intValue());
            z2++;
        }
    }

    public final void zzl(int i, List<Long> list, boolean z) throws IOException {
        boolean z2 = false;
        if (z) {
            this.zzbva.zzc(i, 2);
            int i2 = false;
            for (i = 0; i < list.size(); i++) {
                i2 += zzve.zzbf(((Long) list.get(i)).longValue());
            }
            this.zzbva.zzaz(i2);
            int i3;
            while (i3 < list.size()) {
                this.zzbva.zzba(((Long) list.get(i3)).longValue());
                i3++;
            }
            return;
        }
        while (z2 < list.size()) {
            this.zzbva.zzc(i, ((Long) list.get(z2)).longValue());
            z2++;
        }
    }

    public final void zzm(int i, List<Integer> list, boolean z) throws IOException {
        boolean z2 = false;
        if (z) {
            this.zzbva.zzc(i, 2);
            int i2 = false;
            for (i = 0; i < list.size(); i++) {
                i2 += zzve.zzbf(((Integer) list.get(i)).intValue());
            }
            this.zzbva.zzaz(i2);
            int i3;
            while (i3 < list.size()) {
                this.zzbva.zzba(((Integer) list.get(i3)).intValue());
                i3++;
            }
            return;
        }
        while (z2 < list.size()) {
            this.zzbva.zzf(i, ((Integer) list.get(z2)).intValue());
            z2++;
        }
    }

    public final void zzn(int i, List<Long> list, boolean z) throws IOException {
        boolean z2 = false;
        if (z) {
            this.zzbva.zzc(i, 2);
            int i2 = false;
            for (i = 0; i < list.size(); i++) {
                i2 += zzve.zzbd(((Long) list.get(i)).longValue());
            }
            this.zzbva.zzaz(i2);
            int i3;
            while (i3 < list.size()) {
                this.zzbva.zzaz(((Long) list.get(i3)).longValue());
                i3++;
            }
            return;
        }
        while (z2 < list.size()) {
            this.zzbva.zzb(i, ((Long) list.get(z2)).longValue());
            z2++;
        }
    }

    public final void zza(int i, List<?> list, zzxu com_google_android_gms_internal_measurement_zzxu) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            zza(i, list.get(i2), com_google_android_gms_internal_measurement_zzxu);
        }
    }

    public final void zzb(int i, List<?> list, zzxu com_google_android_gms_internal_measurement_zzxu) throws IOException {
        for (int i2 = 0; i2 < list.size(); i2++) {
            zzb(i, list.get(i2), com_google_android_gms_internal_measurement_zzxu);
        }
    }

    public final <K, V> void zza(int i, zzwx<K, V> com_google_android_gms_internal_measurement_zzwx_K__V, Map<K, V> map) throws IOException {
        map = map.entrySet().iterator();
        while (map.hasNext()) {
            Entry entry = (Entry) map.next();
            this.zzbva.zzc(i, 2);
            this.zzbva.zzaz(zzww.zza(com_google_android_gms_internal_measurement_zzwx_K__V, entry.getKey(), entry.getValue()));
            zzww.zza(this.zzbva, com_google_android_gms_internal_measurement_zzwx_K__V, entry.getKey(), entry.getValue());
        }
    }
}
