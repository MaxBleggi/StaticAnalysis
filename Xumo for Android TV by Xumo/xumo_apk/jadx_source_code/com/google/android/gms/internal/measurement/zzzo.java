package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

final class zzzo implements Cloneable {
    private Object value;
    private zzzm<?, ?> zzcge;
    private List<zzzt> zzcgf = new ArrayList();

    zzzo() {
    }

    final void zza(zzzt com_google_android_gms_internal_measurement_zzzt) throws IOException {
        if (this.zzcgf != null) {
            this.zzcgf.add(com_google_android_gms_internal_measurement_zzzt);
            return;
        }
        if (this.value instanceof zzzr) {
            com_google_android_gms_internal_measurement_zzzt = com_google_android_gms_internal_measurement_zzzt.zzbvb;
            zzzi zzj = zzzi.zzj(com_google_android_gms_internal_measurement_zzzt, 0, com_google_android_gms_internal_measurement_zzzt.length);
            int zzvi = zzj.zzvi();
            if (zzvi == com_google_android_gms_internal_measurement_zzzt.length - zzzj.zzbd(zzvi)) {
                com_google_android_gms_internal_measurement_zzzt = ((zzzr) this.value).zza(zzj);
            } else {
                throw zzzq.zzzk();
            }
        }
        zzzt com_google_android_gms_internal_measurement_zzzt2;
        if (this.value instanceof zzzr[]) {
            zzzr[] com_google_android_gms_internal_measurement_zzzrArr = (zzzr[]) this.zzcge.zzah(Collections.singletonList(com_google_android_gms_internal_measurement_zzzt));
            zzzr[] com_google_android_gms_internal_measurement_zzzrArr2 = (zzzr[]) this.value;
            com_google_android_gms_internal_measurement_zzzt2 = (zzzr[]) Arrays.copyOf(com_google_android_gms_internal_measurement_zzzrArr2, com_google_android_gms_internal_measurement_zzzrArr2.length + com_google_android_gms_internal_measurement_zzzrArr.length);
            System.arraycopy(com_google_android_gms_internal_measurement_zzzrArr, 0, com_google_android_gms_internal_measurement_zzzt2, com_google_android_gms_internal_measurement_zzzrArr2.length, com_google_android_gms_internal_measurement_zzzrArr.length);
        } else if (this.value instanceof zzxe) {
            com_google_android_gms_internal_measurement_zzzt = ((zzxe) this.value).zzwo().zza((zzxe) this.zzcge.zzah(Collections.singletonList(com_google_android_gms_internal_measurement_zzzt))).zzwv();
        } else if (this.value instanceof zzxe[]) {
            zzxe[] com_google_android_gms_internal_measurement_zzxeArr = (zzxe[]) this.zzcge.zzah(Collections.singletonList(com_google_android_gms_internal_measurement_zzzt));
            zzxe[] com_google_android_gms_internal_measurement_zzxeArr2 = (zzxe[]) this.value;
            zzxe[] com_google_android_gms_internal_measurement_zzxeArr3 = (zzxe[]) Arrays.copyOf(com_google_android_gms_internal_measurement_zzxeArr2, com_google_android_gms_internal_measurement_zzxeArr2.length + com_google_android_gms_internal_measurement_zzxeArr.length);
            System.arraycopy(com_google_android_gms_internal_measurement_zzxeArr, 0, com_google_android_gms_internal_measurement_zzxeArr3, com_google_android_gms_internal_measurement_zzxeArr2.length, com_google_android_gms_internal_measurement_zzxeArr.length);
        } else {
            com_google_android_gms_internal_measurement_zzzt = this.zzcge.zzah(Collections.singletonList(com_google_android_gms_internal_measurement_zzzt));
        }
        com_google_android_gms_internal_measurement_zzzt = com_google_android_gms_internal_measurement_zzzt2;
        this.zzcge = this.zzcge;
        this.value = com_google_android_gms_internal_measurement_zzzt;
        this.zzcgf = null;
    }

    final <T> T zzb(zzzm<?, T> com_google_android_gms_internal_measurement_zzzm___T) {
        if (this.value == null) {
            this.zzcge = com_google_android_gms_internal_measurement_zzzm___T;
            this.value = com_google_android_gms_internal_measurement_zzzm___T.zzah(this.zzcgf);
            this.zzcgf = null;
        } else if (this.zzcge.equals(com_google_android_gms_internal_measurement_zzzm___T) == null) {
            throw new IllegalStateException("Tried to getExtension with a different Extension.");
        }
        return this.value;
    }

    final int zzf() {
        int i = 0;
        int i2;
        if (this.value != null) {
            zzzm com_google_android_gms_internal_measurement_zzzm = this.zzcge;
            Object obj = this.value;
            if (!com_google_android_gms_internal_measurement_zzzm.zzcfz) {
                return com_google_android_gms_internal_measurement_zzzm.zzao(obj);
            }
            int length = Array.getLength(obj);
            i2 = 0;
            while (i < length) {
                Object obj2 = Array.get(obj, i);
                if (obj2 != null) {
                    i2 += com_google_android_gms_internal_measurement_zzzm.zzao(obj2);
                }
                i++;
            }
            return i2;
        }
        i2 = 0;
        for (zzzt com_google_android_gms_internal_measurement_zzzt : this.zzcgf) {
            i2 += (zzzj.zzbk(com_google_android_gms_internal_measurement_zzzt.tag) + 0) + com_google_android_gms_internal_measurement_zzzt.zzbvb.length;
        }
        return i2;
    }

    final void zza(zzzj com_google_android_gms_internal_measurement_zzzj) throws IOException {
        if (this.value != null) {
            zzzm com_google_android_gms_internal_measurement_zzzm = this.zzcge;
            Object obj = this.value;
            if (com_google_android_gms_internal_measurement_zzzm.zzcfz) {
                int length = Array.getLength(obj);
                for (int i = 0; i < length; i++) {
                    Object obj2 = Array.get(obj, i);
                    if (obj2 != null) {
                        com_google_android_gms_internal_measurement_zzzm.zza(obj2, com_google_android_gms_internal_measurement_zzzj);
                    }
                }
                return;
            }
            com_google_android_gms_internal_measurement_zzzm.zza(obj, com_google_android_gms_internal_measurement_zzzj);
            return;
        }
        for (zzzt com_google_android_gms_internal_measurement_zzzt : this.zzcgf) {
            com_google_android_gms_internal_measurement_zzzj.zzcc(com_google_android_gms_internal_measurement_zzzt.tag);
            com_google_android_gms_internal_measurement_zzzj.zzp(com_google_android_gms_internal_measurement_zzzt.zzbvb);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzzo)) {
            return false;
        }
        zzzo com_google_android_gms_internal_measurement_zzzo = (zzzo) obj;
        if (this.value == null || com_google_android_gms_internal_measurement_zzzo.value == null) {
            if (this.zzcgf != null && com_google_android_gms_internal_measurement_zzzo.zzcgf != null) {
                return this.zzcgf.equals(com_google_android_gms_internal_measurement_zzzo.zzcgf);
            }
            try {
                return Arrays.equals(toByteArray(), com_google_android_gms_internal_measurement_zzzo.toByteArray());
            } catch (Object obj2) {
                throw new IllegalStateException(obj2);
            }
        } else if (this.zzcge != com_google_android_gms_internal_measurement_zzzo.zzcge) {
            return false;
        } else {
            if (!this.zzcge.zzcfy.isArray()) {
                return this.value.equals(com_google_android_gms_internal_measurement_zzzo.value);
            }
            if (this.value instanceof byte[]) {
                return Arrays.equals((byte[]) this.value, (byte[]) com_google_android_gms_internal_measurement_zzzo.value);
            }
            if (this.value instanceof int[]) {
                return Arrays.equals((int[]) this.value, (int[]) com_google_android_gms_internal_measurement_zzzo.value);
            }
            if (this.value instanceof long[]) {
                return Arrays.equals((long[]) this.value, (long[]) com_google_android_gms_internal_measurement_zzzo.value);
            }
            if (this.value instanceof float[]) {
                return Arrays.equals((float[]) this.value, (float[]) com_google_android_gms_internal_measurement_zzzo.value);
            }
            if (this.value instanceof double[]) {
                return Arrays.equals((double[]) this.value, (double[]) com_google_android_gms_internal_measurement_zzzo.value);
            }
            if (this.value instanceof boolean[]) {
                return Arrays.equals((boolean[]) this.value, (boolean[]) com_google_android_gms_internal_measurement_zzzo.value);
            }
            return Arrays.deepEquals((Object[]) this.value, (Object[]) com_google_android_gms_internal_measurement_zzzo.value);
        }
    }

    public final int hashCode() {
        try {
            return Arrays.hashCode(toByteArray()) + 527;
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    private final byte[] toByteArray() throws IOException {
        byte[] bArr = new byte[zzf()];
        zza(zzzj.zzo(bArr));
        return bArr;
    }

    private final zzzo zzzj() {
        zzzo com_google_android_gms_internal_measurement_zzzo = new zzzo();
        try {
            com_google_android_gms_internal_measurement_zzzo.zzcge = this.zzcge;
            if (this.zzcgf == null) {
                com_google_android_gms_internal_measurement_zzzo.zzcgf = null;
            } else {
                com_google_android_gms_internal_measurement_zzzo.zzcgf.addAll(this.zzcgf);
            }
            if (this.value != null) {
                if (this.value instanceof zzzr) {
                    com_google_android_gms_internal_measurement_zzzo.value = (zzzr) ((zzzr) this.value).clone();
                } else if (this.value instanceof byte[]) {
                    com_google_android_gms_internal_measurement_zzzo.value = ((byte[]) this.value).clone();
                } else {
                    int i = 0;
                    Object obj;
                    if (this.value instanceof byte[][]) {
                        byte[][] bArr = (byte[][]) this.value;
                        obj = new byte[bArr.length][];
                        com_google_android_gms_internal_measurement_zzzo.value = obj;
                        while (i < bArr.length) {
                            obj[i] = (byte[]) bArr[i].clone();
                            i++;
                        }
                    } else if (this.value instanceof boolean[]) {
                        com_google_android_gms_internal_measurement_zzzo.value = ((boolean[]) this.value).clone();
                    } else if (this.value instanceof int[]) {
                        com_google_android_gms_internal_measurement_zzzo.value = ((int[]) this.value).clone();
                    } else if (this.value instanceof long[]) {
                        com_google_android_gms_internal_measurement_zzzo.value = ((long[]) this.value).clone();
                    } else if (this.value instanceof float[]) {
                        com_google_android_gms_internal_measurement_zzzo.value = ((float[]) this.value).clone();
                    } else if (this.value instanceof double[]) {
                        com_google_android_gms_internal_measurement_zzzo.value = ((double[]) this.value).clone();
                    } else if (this.value instanceof zzzr[]) {
                        zzzr[] com_google_android_gms_internal_measurement_zzzrArr = (zzzr[]) this.value;
                        obj = new zzzr[com_google_android_gms_internal_measurement_zzzrArr.length];
                        com_google_android_gms_internal_measurement_zzzo.value = obj;
                        while (i < com_google_android_gms_internal_measurement_zzzrArr.length) {
                            obj[i] = (zzzr) com_google_android_gms_internal_measurement_zzzrArr[i].clone();
                            i++;
                        }
                    }
                }
            }
            return com_google_android_gms_internal_measurement_zzzo;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzzj();
    }
}
