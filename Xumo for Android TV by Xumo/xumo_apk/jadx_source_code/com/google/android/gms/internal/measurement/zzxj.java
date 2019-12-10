package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

final class zzxj<T> implements zzxu<T> {
    private final zzxe zzcby;
    private final boolean zzcbz;
    private final zzym<?, ?> zzcci;
    private final zzvl<?> zzccj;

    private zzxj(zzym<?, ?> com_google_android_gms_internal_measurement_zzym___, zzvl<?> com_google_android_gms_internal_measurement_zzvl_, zzxe com_google_android_gms_internal_measurement_zzxe) {
        this.zzcci = com_google_android_gms_internal_measurement_zzym___;
        this.zzcbz = com_google_android_gms_internal_measurement_zzvl_.zze(com_google_android_gms_internal_measurement_zzxe);
        this.zzccj = com_google_android_gms_internal_measurement_zzvl_;
        this.zzcby = com_google_android_gms_internal_measurement_zzxe;
    }

    static <T> zzxj<T> zza(zzym<?, ?> com_google_android_gms_internal_measurement_zzym___, zzvl<?> com_google_android_gms_internal_measurement_zzvl_, zzxe com_google_android_gms_internal_measurement_zzxe) {
        return new zzxj(com_google_android_gms_internal_measurement_zzym___, com_google_android_gms_internal_measurement_zzvl_, com_google_android_gms_internal_measurement_zzxe);
    }

    public final T newInstance() {
        return this.zzcby.zzwp().zzwu();
    }

    public final boolean equals(T t, T t2) {
        if (this.zzcci.zzal(t).equals(this.zzcci.zzal(t2))) {
            return this.zzcbz ? this.zzccj.zzw(t).equals(this.zzccj.zzw(t2)) : true;
        } else {
            return null;
        }
    }

    public final int hashCode(T t) {
        int hashCode = this.zzcci.zzal(t).hashCode();
        return this.zzcbz ? (hashCode * 53) + this.zzccj.zzw(t).hashCode() : hashCode;
    }

    public final void zzd(T t, T t2) {
        zzxw.zza(this.zzcci, (Object) t, (Object) t2);
        if (this.zzcbz) {
            zzxw.zza(this.zzccj, (Object) t, (Object) t2);
        }
    }

    public final void zza(T t, zzzh com_google_android_gms_internal_measurement_zzzh) throws IOException {
        Iterator it = this.zzccj.zzw(t).iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            zzvq com_google_android_gms_internal_measurement_zzvq = (zzvq) entry.getKey();
            if (com_google_android_gms_internal_measurement_zzvq.zzwh() != zzzg.MESSAGE || com_google_android_gms_internal_measurement_zzvq.zzwi() || com_google_android_gms_internal_measurement_zzvq.zzwj()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            } else if (entry instanceof zzwj) {
                com_google_android_gms_internal_measurement_zzzh.zza(com_google_android_gms_internal_measurement_zzvq.zzc(), ((zzwj) entry).zzxh().zzud());
            } else {
                com_google_android_gms_internal_measurement_zzzh.zza(com_google_android_gms_internal_measurement_zzvq.zzc(), entry.getValue());
            }
        }
        zzym com_google_android_gms_internal_measurement_zzym = this.zzcci;
        com_google_android_gms_internal_measurement_zzym.zzc(com_google_android_gms_internal_measurement_zzym.zzal(t), com_google_android_gms_internal_measurement_zzzh);
    }

    public final void zza(T t, zzxt com_google_android_gms_internal_measurement_zzxt, zzvk com_google_android_gms_internal_measurement_zzvk) throws IOException {
        zzym com_google_android_gms_internal_measurement_zzym = this.zzcci;
        zzvl com_google_android_gms_internal_measurement_zzvl = this.zzccj;
        Object zzam = com_google_android_gms_internal_measurement_zzym.zzam(t);
        zzvo zzx = com_google_android_gms_internal_measurement_zzvl.zzx(t);
        while (com_google_android_gms_internal_measurement_zzxt.zzvo() != Integer.MAX_VALUE) {
            try {
                boolean zza;
                int tag = com_google_android_gms_internal_measurement_zzxt.getTag();
                if (tag != 11) {
                    if ((tag & 7) == 2) {
                        Object zza2 = com_google_android_gms_internal_measurement_zzvl.zza(com_google_android_gms_internal_measurement_zzvk, this.zzcby, tag >>> 3);
                        if (zza2 != null) {
                            com_google_android_gms_internal_measurement_zzvl.zza(com_google_android_gms_internal_measurement_zzxt, zza2, com_google_android_gms_internal_measurement_zzvk, zzx);
                        } else {
                            zza = com_google_android_gms_internal_measurement_zzym.zza(zzam, com_google_android_gms_internal_measurement_zzxt);
                            continue;
                        }
                    } else {
                        zza = com_google_android_gms_internal_measurement_zzxt.zzvp();
                        continue;
                    }
                    if (!zza) {
                        return;
                    }
                }
                Object obj = null;
                zzun com_google_android_gms_internal_measurement_zzun = null;
                int i = 0;
                while (com_google_android_gms_internal_measurement_zzxt.zzvo() != Integer.MAX_VALUE) {
                    int tag2 = com_google_android_gms_internal_measurement_zzxt.getTag();
                    if (tag2 == 16) {
                        i = com_google_android_gms_internal_measurement_zzxt.zzuz();
                        obj = com_google_android_gms_internal_measurement_zzvl.zza(com_google_android_gms_internal_measurement_zzvk, this.zzcby, i);
                    } else if (tag2 == 26) {
                        if (obj != null) {
                            com_google_android_gms_internal_measurement_zzvl.zza(com_google_android_gms_internal_measurement_zzxt, obj, com_google_android_gms_internal_measurement_zzvk, zzx);
                        } else {
                            com_google_android_gms_internal_measurement_zzun = com_google_android_gms_internal_measurement_zzxt.zzuy();
                        }
                    } else if (!com_google_android_gms_internal_measurement_zzxt.zzvp()) {
                        break;
                    }
                }
                if (com_google_android_gms_internal_measurement_zzxt.getTag() != 12) {
                    throw zzwe.zzxa();
                } else if (com_google_android_gms_internal_measurement_zzun != null) {
                    if (obj != null) {
                        com_google_android_gms_internal_measurement_zzvl.zza(com_google_android_gms_internal_measurement_zzun, obj, com_google_android_gms_internal_measurement_zzvk, zzx);
                    } else {
                        com_google_android_gms_internal_measurement_zzym.zza(zzam, i, com_google_android_gms_internal_measurement_zzun);
                    }
                }
                zza = true;
                continue;
                if (zza) {
                    return;
                }
            } finally {
                com_google_android_gms_internal_measurement_zzym.zzg(t, zzam);
            }
        }
        com_google_android_gms_internal_measurement_zzym.zzg(t, zzam);
    }

    public final void zzy(T t) {
        this.zzcci.zzy(t);
        this.zzccj.zzy(t);
    }

    public final boolean zzaj(T t) {
        return this.zzccj.zzw(t).isInitialized();
    }

    public final int zzai(T t) {
        zzym com_google_android_gms_internal_measurement_zzym = this.zzcci;
        int zzan = com_google_android_gms_internal_measurement_zzym.zzan(com_google_android_gms_internal_measurement_zzym.zzal(t)) + 0;
        return this.zzcbz ? zzan + this.zzccj.zzw(t).zzwf() : zzan;
    }
}
