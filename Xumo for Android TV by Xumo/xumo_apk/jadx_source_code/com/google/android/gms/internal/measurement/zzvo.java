package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

final class zzvo<FieldDescriptorType extends zzvq<FieldDescriptorType>> {
    private static final zzvo zzbwn = new zzvo(true);
    private boolean zzbqj;
    private final zzxx<FieldDescriptorType, Object> zzbwl = zzxx.zzbv(16);
    private boolean zzbwm = false;

    private zzvo() {
    }

    private zzvo(boolean z) {
        zzsw();
    }

    public static <T extends zzvq<T>> zzvo<T> zzwd() {
        return zzbwn;
    }

    final boolean isEmpty() {
        return this.zzbwl.isEmpty();
    }

    public final void zzsw() {
        if (!this.zzbqj) {
            this.zzbwl.zzsw();
            this.zzbqj = true;
        }
    }

    public final boolean isImmutable() {
        return this.zzbqj;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzvo)) {
            return null;
        }
        return this.zzbwl.equals(((zzvo) obj).zzbwl);
    }

    public final int hashCode() {
        return this.zzbwl.hashCode();
    }

    public final Iterator<Entry<FieldDescriptorType, Object>> iterator() {
        if (this.zzbwm) {
            return new zzwk(this.zzbwl.entrySet().iterator());
        }
        return this.zzbwl.entrySet().iterator();
    }

    final Iterator<Entry<FieldDescriptorType, Object>> descendingIterator() {
        if (this.zzbwm) {
            return new zzwk(this.zzbwl.zzyl().iterator());
        }
        return this.zzbwl.zzyl().iterator();
    }

    private final Object zza(FieldDescriptorType fieldDescriptorType) {
        fieldDescriptorType = this.zzbwl.get(fieldDescriptorType);
        return fieldDescriptorType instanceof zzwh ? zzwh.zzxg() : fieldDescriptorType;
    }

    private final void zza(FieldDescriptorType fieldDescriptorType, Object obj) {
        if (!fieldDescriptorType.zzwi()) {
            zza(fieldDescriptorType.zzwg(), obj);
        } else if (obj instanceof List) {
            List arrayList = new ArrayList();
            arrayList.addAll((List) obj);
            ArrayList arrayList2 = (ArrayList) arrayList;
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList2.get(i);
                i++;
                zza(fieldDescriptorType.zzwg(), obj2);
            }
            obj = arrayList;
        } else {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
        if (obj instanceof zzwh) {
            this.zzbwm = true;
        }
        this.zzbwl.zza((Comparable) fieldDescriptorType, obj);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void zza(com.google.android.gms.internal.measurement.zzzb r2, java.lang.Object r3) {
        /*
        com.google.android.gms.internal.measurement.zzvz.checkNotNull(r3);
        r0 = com.google.android.gms.internal.measurement.zzvp.zzbwo;
        r2 = r2.zzzc();
        r2 = r2.ordinal();
        r2 = r0[r2];
        r0 = 1;
        r1 = 0;
        switch(r2) {
            case 1: goto L_0x0040;
            case 2: goto L_0x003d;
            case 3: goto L_0x003a;
            case 4: goto L_0x0037;
            case 5: goto L_0x0034;
            case 6: goto L_0x0031;
            case 7: goto L_0x0028;
            case 8: goto L_0x001e;
            case 9: goto L_0x0015;
            default: goto L_0x0014;
        };
    L_0x0014:
        goto L_0x0043;
    L_0x0015:
        r2 = r3 instanceof com.google.android.gms.internal.measurement.zzxe;
        if (r2 != 0) goto L_0x0026;
    L_0x0019:
        r2 = r3 instanceof com.google.android.gms.internal.measurement.zzwh;
        if (r2 == 0) goto L_0x0043;
    L_0x001d:
        goto L_0x0026;
    L_0x001e:
        r2 = r3 instanceof java.lang.Integer;
        if (r2 != 0) goto L_0x0026;
    L_0x0022:
        r2 = r3 instanceof com.google.android.gms.internal.measurement.zzwa;
        if (r2 == 0) goto L_0x0043;
    L_0x0026:
        r1 = 1;
        goto L_0x0043;
    L_0x0028:
        r2 = r3 instanceof com.google.android.gms.internal.measurement.zzun;
        if (r2 != 0) goto L_0x0026;
    L_0x002c:
        r2 = r3 instanceof byte[];
        if (r2 == 0) goto L_0x0043;
    L_0x0030:
        goto L_0x0026;
    L_0x0031:
        r0 = r3 instanceof java.lang.String;
        goto L_0x0042;
    L_0x0034:
        r0 = r3 instanceof java.lang.Boolean;
        goto L_0x0042;
    L_0x0037:
        r0 = r3 instanceof java.lang.Double;
        goto L_0x0042;
    L_0x003a:
        r0 = r3 instanceof java.lang.Float;
        goto L_0x0042;
    L_0x003d:
        r0 = r3 instanceof java.lang.Long;
        goto L_0x0042;
    L_0x0040:
        r0 = r3 instanceof java.lang.Integer;
    L_0x0042:
        r1 = r0;
    L_0x0043:
        if (r1 == 0) goto L_0x0046;
    L_0x0045:
        return;
    L_0x0046:
        r2 = new java.lang.IllegalArgumentException;
        r3 = "Wrong object type used with protocol message reflection.";
        r2.<init>(r3);
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzvo.zza(com.google.android.gms.internal.measurement.zzzb, java.lang.Object):void");
    }

    public final boolean isInitialized() {
        for (int i = 0; i < this.zzbwl.zzyj(); i++) {
            if (!zzc(this.zzbwl.zzbw(i))) {
                return false;
            }
        }
        for (Entry zzc : this.zzbwl.zzyk()) {
            if (!zzc(zzc)) {
                return false;
            }
        }
        return true;
    }

    private static boolean zzc(Entry<FieldDescriptorType, Object> entry) {
        zzvq com_google_android_gms_internal_measurement_zzvq = (zzvq) entry.getKey();
        if (com_google_android_gms_internal_measurement_zzvq.zzwh() == zzzg.MESSAGE) {
            if (com_google_android_gms_internal_measurement_zzvq.zzwi()) {
                for (zzxe isInitialized : (List) entry.getValue()) {
                    if (!isInitialized.isInitialized()) {
                        return false;
                    }
                }
            }
            entry = entry.getValue();
            if (entry instanceof zzxe) {
                if (((zzxe) entry).isInitialized() == null) {
                    return false;
                }
            } else if ((entry instanceof zzwh) != null) {
                return true;
            } else {
                throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
        }
        return true;
    }

    public final void zza(zzvo<FieldDescriptorType> com_google_android_gms_internal_measurement_zzvo_FieldDescriptorType) {
        for (int i = 0; i < com_google_android_gms_internal_measurement_zzvo_FieldDescriptorType.zzbwl.zzyj(); i++) {
            zzd(com_google_android_gms_internal_measurement_zzvo_FieldDescriptorType.zzbwl.zzbw(i));
        }
        for (FieldDescriptorType zzd : com_google_android_gms_internal_measurement_zzvo_FieldDescriptorType.zzbwl.zzyk()) {
            zzd(zzd);
        }
    }

    private static Object zzz(Object obj) {
        if (obj instanceof zzxk) {
            return ((zzxk) obj).zzxw();
        }
        if (!(obj instanceof byte[])) {
            return obj;
        }
        byte[] bArr = (byte[]) obj;
        Object obj2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, obj2, 0, bArr.length);
        return obj2;
    }

    private final void zzd(Entry<FieldDescriptorType, Object> entry) {
        Comparable comparable = (zzvq) entry.getKey();
        entry = entry.getValue();
        if (entry instanceof zzwh) {
            entry = zzwh.zzxg();
        }
        Object zza;
        if (comparable.zzwi()) {
            zza = zza((zzvq) comparable);
            if (zza == null) {
                zza = new ArrayList();
            }
            for (Object zzz : (List) entry) {
                ((List) zza).add(zzz(zzz));
            }
            this.zzbwl.zza(comparable, zza);
        } else if (comparable.zzwh() == zzzg.MESSAGE) {
            zza = zza((zzvq) comparable);
            if (zza == null) {
                this.zzbwl.zza(comparable, zzz(entry));
                return;
            }
            Object zza2;
            if (zza instanceof zzxk) {
                zza2 = comparable.zza((zzxk) zza, (zzxk) entry);
            } else {
                zza2 = comparable.zza(((zzxe) zza).zzwo(), (zzxe) entry).zzwv();
            }
            this.zzbwl.zza(comparable, zza2);
        } else {
            this.zzbwl.zza(comparable, zzz(entry));
        }
    }

    static void zza(zzve com_google_android_gms_internal_measurement_zzve, zzzb com_google_android_gms_internal_measurement_zzzb, int i, Object obj) throws IOException {
        if (com_google_android_gms_internal_measurement_zzzb == zzzb.GROUP) {
            zzxe com_google_android_gms_internal_measurement_zzxe = (zzxe) obj;
            zzvz.zzf(com_google_android_gms_internal_measurement_zzxe);
            com_google_android_gms_internal_measurement_zzve.zzc(i, 3);
            com_google_android_gms_internal_measurement_zzxe.zzb(com_google_android_gms_internal_measurement_zzve);
            com_google_android_gms_internal_measurement_zzve.zzc(i, 4);
            return;
        }
        com_google_android_gms_internal_measurement_zzve.zzc(i, com_google_android_gms_internal_measurement_zzzb.zzzd());
        switch (zzvp.zzbvp[com_google_android_gms_internal_measurement_zzzb.ordinal()]) {
            case 1:
                com_google_android_gms_internal_measurement_zzve.zzb(((Double) obj).doubleValue());
                return;
            case 2:
                com_google_android_gms_internal_measurement_zzve.zza(((Float) obj).floatValue());
                return;
            case 3:
                com_google_android_gms_internal_measurement_zzve.zzay(((Long) obj).longValue());
                return;
            case 4:
                com_google_android_gms_internal_measurement_zzve.zzay(((Long) obj).longValue());
                return;
            case 5:
                com_google_android_gms_internal_measurement_zzve.zzay(((Integer) obj).intValue());
                return;
            case 6:
                com_google_android_gms_internal_measurement_zzve.zzba(((Long) obj).longValue());
                return;
            case 7:
                com_google_android_gms_internal_measurement_zzve.zzbb(((Integer) obj).intValue());
                return;
            case 8:
                com_google_android_gms_internal_measurement_zzve.zzs(((Boolean) obj).booleanValue());
                return;
            case 9:
                ((zzxe) obj).zzb(com_google_android_gms_internal_measurement_zzve);
                return;
            case 10:
                com_google_android_gms_internal_measurement_zzve.zzb((zzxe) obj);
                return;
            case 11:
                if ((obj instanceof zzun) != null) {
                    com_google_android_gms_internal_measurement_zzve.zza((zzun) obj);
                    return;
                } else {
                    com_google_android_gms_internal_measurement_zzve.zzgd((String) obj);
                    return;
                }
            case 12:
                if ((obj instanceof zzun) != null) {
                    com_google_android_gms_internal_measurement_zzve.zza((zzun) obj);
                    return;
                }
                byte[] bArr = (byte[]) obj;
                com_google_android_gms_internal_measurement_zzve.zze(bArr, null, bArr.length);
                return;
            case 13:
                com_google_android_gms_internal_measurement_zzve.zzaz(((Integer) obj).intValue());
                return;
            case 14:
                com_google_android_gms_internal_measurement_zzve.zzbb(((Integer) obj).intValue());
                return;
            case 15:
                com_google_android_gms_internal_measurement_zzve.zzba(((Long) obj).longValue());
                return;
            case 16:
                com_google_android_gms_internal_measurement_zzve.zzba(((Integer) obj).intValue());
                return;
            case 17:
                com_google_android_gms_internal_measurement_zzve.zzaz(((Long) obj).longValue());
                return;
            case 18:
                if ((obj instanceof zzwa) == null) {
                    com_google_android_gms_internal_measurement_zzve.zzay(((Integer) obj).intValue());
                    break;
                } else {
                    com_google_android_gms_internal_measurement_zzve.zzay(((zzwa) obj).zzc());
                    return;
                }
            default:
                break;
        }
    }

    public final int zzwe() {
        int i = 0;
        for (int i2 = 0; i2 < this.zzbwl.zzyj(); i2++) {
            Entry zzbw = this.zzbwl.zzbw(i2);
            i += zzb((zzvq) zzbw.getKey(), zzbw.getValue());
        }
        for (Entry zzbw2 : this.zzbwl.zzyk()) {
            i += zzb((zzvq) zzbw2.getKey(), zzbw2.getValue());
        }
        return i;
    }

    public final int zzwf() {
        int i = 0;
        for (int i2 = 0; i2 < this.zzbwl.zzyj(); i2++) {
            i += zze(this.zzbwl.zzbw(i2));
        }
        for (Entry zze : this.zzbwl.zzyk()) {
            i += zze(zze);
        }
        return i;
    }

    private static int zze(Entry<FieldDescriptorType, Object> entry) {
        zzvq com_google_android_gms_internal_measurement_zzvq = (zzvq) entry.getKey();
        Object value = entry.getValue();
        if (com_google_android_gms_internal_measurement_zzvq.zzwh() != zzzg.MESSAGE || com_google_android_gms_internal_measurement_zzvq.zzwi() || com_google_android_gms_internal_measurement_zzvq.zzwj()) {
            return zzb(com_google_android_gms_internal_measurement_zzvq, value);
        }
        if (value instanceof zzwh) {
            return zzve.zzb(((zzvq) entry.getKey()).zzc(), (zzwh) value);
        }
        return zzve.zzd(((zzvq) entry.getKey()).zzc(), (zzxe) value);
    }

    static int zza(zzzb com_google_android_gms_internal_measurement_zzzb, int i, Object obj) {
        i = zzve.zzbc(i);
        if (com_google_android_gms_internal_measurement_zzzb == zzzb.GROUP) {
            zzvz.zzf((zzxe) obj);
            i <<= 1;
        }
        return i + zzb(com_google_android_gms_internal_measurement_zzzb, obj);
    }

    private static int zzb(zzzb com_google_android_gms_internal_measurement_zzzb, Object obj) {
        switch (zzvp.zzbvp[com_google_android_gms_internal_measurement_zzzb.ordinal()]) {
            case 1:
                return zzve.zzc(((Double) obj).doubleValue());
            case 2:
                return zzve.zzb(((Float) obj).floatValue());
            case 3:
                return zzve.zzbb(((Long) obj).longValue());
            case 4:
                return zzve.zzbc(((Long) obj).longValue());
            case 5:
                return zzve.zzbd(((Integer) obj).intValue());
            case 6:
                return zzve.zzbe(((Long) obj).longValue());
            case 7:
                return zzve.zzbg(((Integer) obj).intValue());
            case 8:
                return zzve.zzt(((Boolean) obj).booleanValue());
            case 9:
                return zzve.zzd((zzxe) obj);
            case 10:
                if ((obj instanceof zzwh) != null) {
                    return zzve.zza((zzwh) obj);
                }
                return zzve.zzc((zzxe) obj);
            case 11:
                if ((obj instanceof zzun) != null) {
                    return zzve.zzb((zzun) obj);
                }
                return zzve.zzge((String) obj);
            case 12:
                if ((obj instanceof zzun) != null) {
                    return zzve.zzb((zzun) obj);
                }
                return zzve.zzk((byte[]) obj);
            case 13:
                return zzve.zzbe(((Integer) obj).intValue());
            case 14:
                return zzve.zzbh(((Integer) obj).intValue());
            case 15:
                return zzve.zzbf(((Long) obj).longValue());
            case 16:
                return zzve.zzbf(((Integer) obj).intValue());
            case 17:
                return zzve.zzbd(((Long) obj).longValue());
            case 18:
                if ((obj instanceof zzwa) != null) {
                    return zzve.zzbi(((zzwa) obj).zzc());
                }
                return zzve.zzbi(((Integer) obj).intValue());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    private static int zzb(zzvq<?> com_google_android_gms_internal_measurement_zzvq_, Object obj) {
        zzzb zzwg = com_google_android_gms_internal_measurement_zzvq_.zzwg();
        int zzc = com_google_android_gms_internal_measurement_zzvq_.zzc();
        if (!com_google_android_gms_internal_measurement_zzvq_.zzwi()) {
            return zza(zzwg, zzc, obj);
        }
        int i = 0;
        if (com_google_android_gms_internal_measurement_zzvq_.zzwj() != null) {
            for (Object obj2 : (List) obj2) {
                i += zzb(zzwg, obj2);
            }
            return (zzve.zzbc(zzc) + i) + zzve.zzbk(i);
        }
        for (Object obj22 : (List) obj22) {
            i += zza(zzwg, zzc, obj22);
        }
        return i;
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzvo com_google_android_gms_internal_measurement_zzvo = new zzvo();
        for (int i = 0; i < this.zzbwl.zzyj(); i++) {
            Entry zzbw = this.zzbwl.zzbw(i);
            com_google_android_gms_internal_measurement_zzvo.zza((zzvq) zzbw.getKey(), zzbw.getValue());
        }
        for (Entry zzbw2 : this.zzbwl.zzyk()) {
            com_google_android_gms_internal_measurement_zzvo.zza((zzvq) zzbw2.getKey(), zzbw2.getValue());
        }
        com_google_android_gms_internal_measurement_zzvo.zzbwm = this.zzbwm;
        return com_google_android_gms_internal_measurement_zzvo;
    }
}
