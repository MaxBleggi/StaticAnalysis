package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.util.List;
import java.util.RandomAccess;

final class zzxw {
    private static final Class<?> zzccr = zzyh();
    private static final zzym<?, ?> zzccs = zzv(false);
    private static final zzym<?, ?> zzcct = zzv(true);
    private static final zzym<?, ?> zzccu = new zzyo();

    public static void zzj(Class<?> cls) {
        if (!zzvx.class.isAssignableFrom(cls) && zzccr != null) {
            if (zzccr.isAssignableFrom(cls) == null) {
                throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
            }
        }
    }

    public static void zza(int i, List<Double> list, zzzh com_google_android_gms_internal_measurement_zzzh, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            com_google_android_gms_internal_measurement_zzzh.zzg(i, list, z);
        }
    }

    public static void zzb(int i, List<Float> list, zzzh com_google_android_gms_internal_measurement_zzzh, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            com_google_android_gms_internal_measurement_zzzh.zzf(i, list, z);
        }
    }

    public static void zzc(int i, List<Long> list, zzzh com_google_android_gms_internal_measurement_zzzh, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            com_google_android_gms_internal_measurement_zzzh.zzc(i, list, z);
        }
    }

    public static void zzd(int i, List<Long> list, zzzh com_google_android_gms_internal_measurement_zzzh, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            com_google_android_gms_internal_measurement_zzzh.zzd(i, list, z);
        }
    }

    public static void zze(int i, List<Long> list, zzzh com_google_android_gms_internal_measurement_zzzh, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            com_google_android_gms_internal_measurement_zzzh.zzn(i, list, z);
        }
    }

    public static void zzf(int i, List<Long> list, zzzh com_google_android_gms_internal_measurement_zzzh, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            com_google_android_gms_internal_measurement_zzzh.zze(i, list, z);
        }
    }

    public static void zzg(int i, List<Long> list, zzzh com_google_android_gms_internal_measurement_zzzh, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            com_google_android_gms_internal_measurement_zzzh.zzl(i, list, z);
        }
    }

    public static void zzh(int i, List<Integer> list, zzzh com_google_android_gms_internal_measurement_zzzh, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            com_google_android_gms_internal_measurement_zzzh.zza(i, (List) list, z);
        }
    }

    public static void zzi(int i, List<Integer> list, zzzh com_google_android_gms_internal_measurement_zzzh, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            com_google_android_gms_internal_measurement_zzzh.zzj(i, list, z);
        }
    }

    public static void zzj(int i, List<Integer> list, zzzh com_google_android_gms_internal_measurement_zzzh, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            com_google_android_gms_internal_measurement_zzzh.zzm(i, list, z);
        }
    }

    public static void zzk(int i, List<Integer> list, zzzh com_google_android_gms_internal_measurement_zzzh, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            com_google_android_gms_internal_measurement_zzzh.zzb(i, (List) list, z);
        }
    }

    public static void zzl(int i, List<Integer> list, zzzh com_google_android_gms_internal_measurement_zzzh, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            com_google_android_gms_internal_measurement_zzzh.zzk(i, list, z);
        }
    }

    public static void zzm(int i, List<Integer> list, zzzh com_google_android_gms_internal_measurement_zzzh, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            com_google_android_gms_internal_measurement_zzzh.zzh(i, list, z);
        }
    }

    public static void zzn(int i, List<Boolean> list, zzzh com_google_android_gms_internal_measurement_zzzh, boolean z) throws IOException {
        if (list != null && !list.isEmpty()) {
            com_google_android_gms_internal_measurement_zzzh.zzi(i, list, z);
        }
    }

    public static void zza(int i, List<String> list, zzzh com_google_android_gms_internal_measurement_zzzh) throws IOException {
        if (list != null && !list.isEmpty()) {
            com_google_android_gms_internal_measurement_zzzh.zza(i, (List) list);
        }
    }

    public static void zzb(int i, List<zzun> list, zzzh com_google_android_gms_internal_measurement_zzzh) throws IOException {
        if (list != null && !list.isEmpty()) {
            com_google_android_gms_internal_measurement_zzzh.zzb(i, (List) list);
        }
    }

    public static void zza(int i, List<?> list, zzzh com_google_android_gms_internal_measurement_zzzh, zzxu com_google_android_gms_internal_measurement_zzxu) throws IOException {
        if (list != null && !list.isEmpty()) {
            com_google_android_gms_internal_measurement_zzzh.zza(i, (List) list, com_google_android_gms_internal_measurement_zzxu);
        }
    }

    public static void zzb(int i, List<?> list, zzzh com_google_android_gms_internal_measurement_zzzh, zzxu com_google_android_gms_internal_measurement_zzxu) throws IOException {
        if (list != null && !list.isEmpty()) {
            com_google_android_gms_internal_measurement_zzzh.zzb(i, (List) list, com_google_android_gms_internal_measurement_zzxu);
        }
    }

    static int zzx(List<Long> list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        int i2;
        if (list instanceof zzws) {
            zzws com_google_android_gms_internal_measurement_zzws = (zzws) list;
            i2 = 0;
            while (i < size) {
                i2 += zzve.zzbb(com_google_android_gms_internal_measurement_zzws.getLong(i));
                i++;
            }
        } else {
            i2 = 0;
            while (i < size) {
                i2 += zzve.zzbb(((Long) list.get(i)).longValue());
                i++;
            }
        }
        return i2;
    }

    static int zzo(int i, List<Long> list, boolean z) {
        if (list.size()) {
            return zzx(list) + (list.size() * zzve.zzbc(i));
        }
        return 0;
    }

    static int zzy(List<Long> list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        int i2;
        if (list instanceof zzws) {
            zzws com_google_android_gms_internal_measurement_zzws = (zzws) list;
            i2 = 0;
            while (i < size) {
                i2 += zzve.zzbc(com_google_android_gms_internal_measurement_zzws.getLong(i));
                i++;
            }
        } else {
            i2 = 0;
            while (i < size) {
                i2 += zzve.zzbc(((Long) list.get(i)).longValue());
                i++;
            }
        }
        return i2;
    }

    static int zzp(int i, List<Long> list, boolean z) {
        z = list.size();
        if (z) {
            return zzy(list) + (z * zzve.zzbc(i));
        }
        return 0;
    }

    static int zzz(List<Long> list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        int i2;
        if (list instanceof zzws) {
            zzws com_google_android_gms_internal_measurement_zzws = (zzws) list;
            i2 = 0;
            while (i < size) {
                i2 += zzve.zzbd(com_google_android_gms_internal_measurement_zzws.getLong(i));
                i++;
            }
        } else {
            i2 = 0;
            while (i < size) {
                i2 += zzve.zzbd(((Long) list.get(i)).longValue());
                i++;
            }
        }
        return i2;
    }

    static int zzq(int i, List<Long> list, boolean z) {
        z = list.size();
        if (z) {
            return zzz(list) + (z * zzve.zzbc(i));
        }
        return 0;
    }

    static int zzaa(List<Integer> list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        int i2;
        if (list instanceof zzvy) {
            zzvy com_google_android_gms_internal_measurement_zzvy = (zzvy) list;
            i2 = 0;
            while (i < size) {
                i2 += zzve.zzbi(com_google_android_gms_internal_measurement_zzvy.getInt(i));
                i++;
            }
        } else {
            i2 = 0;
            while (i < size) {
                i2 += zzve.zzbi(((Integer) list.get(i)).intValue());
                i++;
            }
        }
        return i2;
    }

    static int zzr(int i, List<Integer> list, boolean z) {
        z = list.size();
        if (z) {
            return zzaa(list) + (z * zzve.zzbc(i));
        }
        return 0;
    }

    static int zzab(List<Integer> list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        int i2;
        if (list instanceof zzvy) {
            zzvy com_google_android_gms_internal_measurement_zzvy = (zzvy) list;
            i2 = 0;
            while (i < size) {
                i2 += zzve.zzbd(com_google_android_gms_internal_measurement_zzvy.getInt(i));
                i++;
            }
        } else {
            i2 = 0;
            while (i < size) {
                i2 += zzve.zzbd(((Integer) list.get(i)).intValue());
                i++;
            }
        }
        return i2;
    }

    static int zzs(int i, List<Integer> list, boolean z) {
        z = list.size();
        if (z) {
            return zzab(list) + (z * zzve.zzbc(i));
        }
        return 0;
    }

    static int zzac(List<Integer> list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        int i2;
        if (list instanceof zzvy) {
            zzvy com_google_android_gms_internal_measurement_zzvy = (zzvy) list;
            i2 = 0;
            while (i < size) {
                i2 += zzve.zzbe(com_google_android_gms_internal_measurement_zzvy.getInt(i));
                i++;
            }
        } else {
            i2 = 0;
            while (i < size) {
                i2 += zzve.zzbe(((Integer) list.get(i)).intValue());
                i++;
            }
        }
        return i2;
    }

    static int zzt(int i, List<Integer> list, boolean z) {
        z = list.size();
        if (z) {
            return zzac(list) + (z * zzve.zzbc(i));
        }
        return 0;
    }

    static int zzad(List<Integer> list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        int i2;
        if (list instanceof zzvy) {
            zzvy com_google_android_gms_internal_measurement_zzvy = (zzvy) list;
            i2 = 0;
            while (i < size) {
                i2 += zzve.zzbf(com_google_android_gms_internal_measurement_zzvy.getInt(i));
                i++;
            }
        } else {
            i2 = 0;
            while (i < size) {
                i2 += zzve.zzbf(((Integer) list.get(i)).intValue());
                i++;
            }
        }
        return i2;
    }

    static int zzu(int i, List<Integer> list, boolean z) {
        z = list.size();
        if (z) {
            return zzad(list) + (z * zzve.zzbc(i));
        }
        return 0;
    }

    static int zzae(List<?> list) {
        return list.size() << 2;
    }

    static int zzv(int i, List<?> list, boolean z) {
        list = list.size();
        if (list == null) {
            return 0;
        }
        return list * zzve.zzk(i, 0);
    }

    static int zzaf(List<?> list) {
        return list.size() << 3;
    }

    static int zzw(int i, List<?> list, boolean z) {
        list = list.size();
        if (list == null) {
            return 0;
        }
        return list * zzve.zzg(i, 0);
    }

    static int zzag(List<?> list) {
        return list.size();
    }

    static int zzx(int i, List<?> list, boolean z) {
        list = list.size();
        if (list == null) {
            return 0;
        }
        return list * zzve.zzc(i, true);
    }

    static int zzc(int i, List<?> list) {
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        i = zzve.zzbc(i) * size;
        Object zzbo;
        if (list instanceof zzwn) {
            zzwn com_google_android_gms_internal_measurement_zzwn = (zzwn) list;
            while (i2 < size) {
                zzbo = com_google_android_gms_internal_measurement_zzwn.zzbo(i2);
                if (zzbo instanceof zzun) {
                    i += zzve.zzb((zzun) zzbo);
                } else {
                    i += zzve.zzge((String) zzbo);
                }
                i2++;
            }
        } else {
            while (i2 < size) {
                zzbo = list.get(i2);
                if (zzbo instanceof zzun) {
                    i += zzve.zzb((zzun) zzbo);
                } else {
                    i += zzve.zzge((String) zzbo);
                }
                i2++;
            }
        }
        return i;
    }

    static int zzc(int i, Object obj, zzxu com_google_android_gms_internal_measurement_zzxu) {
        if (obj instanceof zzwl) {
            return zzve.zza(i, (zzwl) obj);
        }
        return zzve.zzb(i, (zzxe) obj, com_google_android_gms_internal_measurement_zzxu);
    }

    static int zzc(int i, List<?> list, zzxu com_google_android_gms_internal_measurement_zzxu) {
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        i = zzve.zzbc(i) * size;
        while (i2 < size) {
            Object obj = list.get(i2);
            if (obj instanceof zzwl) {
                i += zzve.zza((zzwl) obj);
            } else {
                i += zzve.zzb((zzxe) obj, com_google_android_gms_internal_measurement_zzxu);
            }
            i2++;
        }
        return i;
    }

    static int zzd(int i, List<zzun> list) {
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        size *= zzve.zzbc(i);
        while (i2 < list.size()) {
            size += zzve.zzb((zzun) list.get(i2));
            i2++;
        }
        return size;
    }

    static int zzd(int i, List<zzxe> list, zzxu com_google_android_gms_internal_measurement_zzxu) {
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        int i3 = 0;
        while (i2 < size) {
            i3 += zzve.zzc(i, (zzxe) list.get(i2), com_google_android_gms_internal_measurement_zzxu);
            i2++;
        }
        return i3;
    }

    public static zzym<?, ?> zzye() {
        return zzccs;
    }

    public static zzym<?, ?> zzyf() {
        return zzcct;
    }

    public static zzym<?, ?> zzyg() {
        return zzccu;
    }

    private static com.google.android.gms.internal.measurement.zzym<?, ?> zzv(boolean r6) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r0 = 0;
        r1 = zzyi();	 Catch:{ Throwable -> 0x0023 }
        if (r1 != 0) goto L_0x0008;	 Catch:{ Throwable -> 0x0023 }
    L_0x0007:
        return r0;	 Catch:{ Throwable -> 0x0023 }
    L_0x0008:
        r2 = 1;	 Catch:{ Throwable -> 0x0023 }
        r3 = new java.lang.Class[r2];	 Catch:{ Throwable -> 0x0023 }
        r4 = java.lang.Boolean.TYPE;	 Catch:{ Throwable -> 0x0023 }
        r5 = 0;	 Catch:{ Throwable -> 0x0023 }
        r3[r5] = r4;	 Catch:{ Throwable -> 0x0023 }
        r1 = r1.getConstructor(r3);	 Catch:{ Throwable -> 0x0023 }
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x0023 }
        r6 = java.lang.Boolean.valueOf(r6);	 Catch:{ Throwable -> 0x0023 }
        r2[r5] = r6;	 Catch:{ Throwable -> 0x0023 }
        r6 = r1.newInstance(r2);	 Catch:{ Throwable -> 0x0023 }
        r6 = (com.google.android.gms.internal.measurement.zzym) r6;	 Catch:{ Throwable -> 0x0023 }
        return r6;
    L_0x0023:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzxw.zzv(boolean):com.google.android.gms.internal.measurement.zzym<?, ?>");
    }

    private static java.lang.Class<?> zzyh() {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r0 = "com.google.protobuf.GeneratedMessage";	 Catch:{ Throwable -> 0x0007 }
        r0 = java.lang.Class.forName(r0);	 Catch:{ Throwable -> 0x0007 }
        return r0;
    L_0x0007:
        r0 = 0;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzxw.zzyh():java.lang.Class<?>");
    }

    private static java.lang.Class<?> zzyi() {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r0 = "com.google.protobuf.UnknownFieldSetSchema";	 Catch:{ Throwable -> 0x0007 }
        r0 = java.lang.Class.forName(r0);	 Catch:{ Throwable -> 0x0007 }
        return r0;
    L_0x0007:
        r0 = 0;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzxw.zzyi():java.lang.Class<?>");
    }

    static boolean zze(Object obj, Object obj2) {
        if (obj != obj2) {
            if (obj == null || obj.equals(obj2) == null) {
                return null;
            }
        }
        return true;
    }

    static <T> void zza(zzwz com_google_android_gms_internal_measurement_zzwz, T t, T t2, long j) {
        zzys.zza((Object) t, j, com_google_android_gms_internal_measurement_zzwz.zzc(zzys.zzp(t, j), zzys.zzp(t2, j)));
    }

    static <T, FT extends zzvq<FT>> void zza(zzvl<FT> com_google_android_gms_internal_measurement_zzvl_FT, T t, T t2) {
        zzvo zzw = com_google_android_gms_internal_measurement_zzvl_FT.zzw(t2);
        if (!zzw.isEmpty()) {
            com_google_android_gms_internal_measurement_zzvl_FT.zzx(t).zza(zzw);
        }
    }

    static <T, UT, UB> void zza(zzym<UT, UB> com_google_android_gms_internal_measurement_zzym_UT__UB, T t, T t2) {
        com_google_android_gms_internal_measurement_zzym_UT__UB.zzf(t, com_google_android_gms_internal_measurement_zzym_UT__UB.zzh(com_google_android_gms_internal_measurement_zzym_UT__UB.zzal(t), com_google_android_gms_internal_measurement_zzym_UT__UB.zzal(t2)));
    }

    static <UT, UB> UB zza(int i, List<Integer> list, zzwc com_google_android_gms_internal_measurement_zzwc, UB ub, zzym<UT, UB> com_google_android_gms_internal_measurement_zzym_UT__UB) {
        if (com_google_android_gms_internal_measurement_zzwc == null) {
            return ub;
        }
        UB ub2;
        if (!(list instanceof RandomAccess)) {
            list = list.iterator();
            loop1:
            while (true) {
                ub2 = ub;
                while (list.hasNext() != null) {
                    int intValue = ((Integer) list.next()).intValue();
                    if (!com_google_android_gms_internal_measurement_zzwc.zzb(intValue)) {
                        ub = zza(i, intValue, (Object) ub2, (zzym) com_google_android_gms_internal_measurement_zzym_UT__UB);
                        list.remove();
                    }
                }
                break loop1;
            }
        }
        UB size = list.size();
        ub2 = ub;
        ub = null;
        for (UB ub3 = null; ub3 < size; ub3++) {
            int intValue2 = ((Integer) list.get(ub3)).intValue();
            if (com_google_android_gms_internal_measurement_zzwc.zzb(intValue2)) {
                if (ub3 != ub) {
                    list.set(ub, Integer.valueOf(intValue2));
                }
                ub++;
            } else {
                ub2 = zza(i, intValue2, (Object) ub2, (zzym) com_google_android_gms_internal_measurement_zzym_UT__UB);
            }
        }
        if (ub != size) {
            list.subList(ub, size).clear();
        }
        return ub2;
    }

    static <UT, UB> UB zza(int i, int i2, UB ub, zzym<UT, UB> com_google_android_gms_internal_measurement_zzym_UT__UB) {
        Object zzyr;
        if (ub == null) {
            zzyr = com_google_android_gms_internal_measurement_zzym_UT__UB.zzyr();
        }
        com_google_android_gms_internal_measurement_zzym_UT__UB.zza(zzyr, i, (long) i2);
        return zzyr;
    }
}
