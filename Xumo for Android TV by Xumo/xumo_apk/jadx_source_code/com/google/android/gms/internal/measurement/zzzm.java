package com.google.android.gms.internal.measurement;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public final class zzzm<M extends zzzl<M>, T> {
    public final int tag;
    private final int type;
    private final zzvx<?, ?> zzbzk;
    protected final Class<T> zzcfy;
    protected final boolean zzcfz;

    public static <M extends zzzl<M>, T extends zzzr> zzzm<M, T> zza(int i, Class<T> cls, long j) {
        return new zzzm(11, cls, 810, false);
    }

    private zzzm(int i, Class<T> cls, int i2, boolean z) {
        this(11, cls, null, 810, false);
    }

    private zzzm(int i, Class<T> cls, zzvx<?, ?> com_google_android_gms_internal_measurement_zzvx___, int i2, boolean z) {
        this.type = i;
        this.zzcfy = cls;
        this.tag = i2;
        this.zzcfz = false;
        this.zzbzk = 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzzm)) {
            return false;
        }
        zzzm com_google_android_gms_internal_measurement_zzzm = (zzzm) obj;
        return this.type == com_google_android_gms_internal_measurement_zzzm.type && this.zzcfy == com_google_android_gms_internal_measurement_zzzm.zzcfy && this.tag == com_google_android_gms_internal_measurement_zzzm.tag && this.zzcfz == com_google_android_gms_internal_measurement_zzzm.zzcfz;
    }

    public final int hashCode() {
        return ((((((this.type + 1147) * 31) + this.zzcfy.hashCode()) * 31) + this.tag) * 31) + this.zzcfz;
    }

    final T zzah(List<zzzt> list) {
        if (list == null) {
            return null;
        }
        if (this.zzcfz) {
            List arrayList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                zzzt com_google_android_gms_internal_measurement_zzzt = (zzzt) list.get(i);
                if (com_google_android_gms_internal_measurement_zzzt.zzbvb.length != 0) {
                    arrayList.add(zze(zzzi.zzn(com_google_android_gms_internal_measurement_zzzt.zzbvb)));
                }
            }
            list = arrayList.size();
            if (list == null) {
                return null;
            }
            T cast = this.zzcfy.cast(Array.newInstance(this.zzcfy.getComponentType(), list));
            for (int i2 = 0; i2 < list; i2++) {
                Array.set(cast, i2, arrayList.get(i2));
            }
            return cast;
        } else if (list.isEmpty()) {
            return null;
        } else {
            return this.zzcfy.cast(zze(zzzi.zzn(((zzzt) list.get(list.size() - 1)).zzbvb)));
        }
    }

    private final Object zze(zzzi com_google_android_gms_internal_measurement_zzzi) {
        StringBuilder stringBuilder;
        String valueOf;
        Class componentType = this.zzcfz ? this.zzcfy.getComponentType() : this.zzcfy;
        try {
            zzzr com_google_android_gms_internal_measurement_zzzr;
            switch (this.type) {
                case 10:
                    com_google_android_gms_internal_measurement_zzzr = (zzzr) componentType.newInstance();
                    com_google_android_gms_internal_measurement_zzzi.zza(com_google_android_gms_internal_measurement_zzzr, this.tag >>> 3);
                    return com_google_android_gms_internal_measurement_zzzr;
                case 11:
                    com_google_android_gms_internal_measurement_zzzr = (zzzr) componentType.newInstance();
                    com_google_android_gms_internal_measurement_zzzi.zza(com_google_android_gms_internal_measurement_zzzr);
                    return com_google_android_gms_internal_measurement_zzzr;
                default:
                    int i = this.type;
                    stringBuilder = new StringBuilder(24);
                    stringBuilder.append("Unknown type ");
                    stringBuilder.append(i);
                    throw new IllegalArgumentException(stringBuilder.toString());
            }
        } catch (zzzi com_google_android_gms_internal_measurement_zzzi2) {
            valueOf = String.valueOf(componentType);
            stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 33);
            stringBuilder.append("Error creating instance of class ");
            stringBuilder.append(valueOf);
            throw new IllegalArgumentException(stringBuilder.toString(), com_google_android_gms_internal_measurement_zzzi2);
        } catch (zzzi com_google_android_gms_internal_measurement_zzzi22) {
            valueOf = String.valueOf(componentType);
            stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 33);
            stringBuilder.append("Error creating instance of class ");
            stringBuilder.append(valueOf);
            throw new IllegalArgumentException(stringBuilder.toString(), com_google_android_gms_internal_measurement_zzzi22);
        } catch (zzzi com_google_android_gms_internal_measurement_zzzi222) {
            throw new IllegalArgumentException("Error reading extension field", com_google_android_gms_internal_measurement_zzzi222);
        }
    }

    protected final void zza(Object obj, zzzj com_google_android_gms_internal_measurement_zzzj) {
        try {
            com_google_android_gms_internal_measurement_zzzj.zzcc(this.tag);
            switch (this.type) {
                case 10:
                    int i = this.tag >>> 3;
                    ((zzzr) obj).zza(com_google_android_gms_internal_measurement_zzzj);
                    com_google_android_gms_internal_measurement_zzzj.zzc(i, 4);
                    return;
                case 11:
                    com_google_android_gms_internal_measurement_zzzj.zzb((zzzr) obj);
                    return;
                default:
                    com_google_android_gms_internal_measurement_zzzj = this.type;
                    StringBuilder stringBuilder = new StringBuilder(24);
                    stringBuilder.append("Unknown type ");
                    stringBuilder.append(com_google_android_gms_internal_measurement_zzzj);
                    throw new IllegalArgumentException(stringBuilder.toString());
            }
        } catch (Object obj2) {
            throw new IllegalStateException(obj2);
        }
    }

    protected final int zzao(Object obj) {
        int i = this.tag >>> 3;
        switch (this.type) {
            case 10:
                return (zzzj.zzbc(i) << 1) + ((zzzr) obj).zzwe();
            case 11:
                return zzzj.zzb(i, (zzzr) obj);
            default:
                i = this.type;
                StringBuilder stringBuilder = new StringBuilder(24);
                stringBuilder.append("Unknown type ");
                stringBuilder.append(i);
                throw new IllegalArgumentException(stringBuilder.toString());
        }
    }
}
