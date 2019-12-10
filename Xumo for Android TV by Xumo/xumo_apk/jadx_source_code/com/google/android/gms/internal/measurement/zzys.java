package com.google.android.gms.internal.measurement;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.logging.Level;
import java.util.logging.Logger;
import libcore.io.Memory;
import sun.misc.Unsafe;

final class zzys {
    private static final Logger logger = Logger.getLogger(zzys.class.getName());
    private static final Class<?> zzbuq = zzuk.zzuj();
    private static final boolean zzbvq = zzyy();
    private static final Unsafe zzcbt = zzyx();
    private static final boolean zzcdq = zzn(Long.TYPE);
    private static final boolean zzcdr = zzn(Integer.TYPE);
    private static final zzd zzcds;
    private static final boolean zzcdt = zzyz();
    private static final long zzcdu = ((long) zzl(byte[].class));
    private static final long zzcdv = ((long) zzl(boolean[].class));
    private static final long zzcdw = ((long) zzm(boolean[].class));
    private static final long zzcdx = ((long) zzl(int[].class));
    private static final long zzcdy = ((long) zzm(int[].class));
    private static final long zzcdz = ((long) zzl(long[].class));
    private static final long zzcea = ((long) zzm(long[].class));
    private static final long zzceb = ((long) zzl(float[].class));
    private static final long zzcec = ((long) zzm(float[].class));
    private static final long zzced = ((long) zzl(double[].class));
    private static final long zzcee = ((long) zzm(double[].class));
    private static final long zzcef = ((long) zzl(Object[].class));
    private static final long zzceg = ((long) zzm(Object[].class));
    private static final long zzceh;
    private static final boolean zzcei = (ByteOrder.nativeOrder() != ByteOrder.BIG_ENDIAN);

    static abstract class zzd {
        Unsafe zzcej;

        zzd(Unsafe unsafe) {
            this.zzcej = unsafe;
        }

        public abstract void zza(long j, byte b);

        public abstract void zza(Object obj, long j, double d);

        public abstract void zza(Object obj, long j, float f);

        public abstract void zza(Object obj, long j, boolean z);

        public abstract void zza(byte[] bArr, long j, long j2, long j3);

        public abstract void zze(Object obj, long j, byte b);

        public abstract boolean zzm(Object obj, long j);

        public abstract float zzn(Object obj, long j);

        public abstract double zzo(Object obj, long j);

        public abstract byte zzy(Object obj, long j);

        public final int zzk(Object obj, long j) {
            return this.zzcej.getInt(obj, j);
        }

        public final void zzb(Object obj, long j, int i) {
            this.zzcej.putInt(obj, j, i);
        }

        public final long zzl(Object obj, long j) {
            return this.zzcej.getLong(obj, j);
        }

        public final void zza(Object obj, long j, long j2) {
            this.zzcej.putLong(obj, j, j2);
        }
    }

    static final class zza extends zzd {
        zza(Unsafe unsafe) {
            super(unsafe);
        }

        public final void zza(long j, byte b) {
            Memory.pokeByte((int) (j & -1), b);
        }

        public final byte zzy(Object obj, long j) {
            if (zzys.zzcei) {
                return zzys.zzq(obj, j);
            }
            return zzys.zzr(obj, j);
        }

        public final void zze(Object obj, long j, byte b) {
            if (zzys.zzcei) {
                zzys.zza(obj, j, b);
            } else {
                zzys.zzb(obj, j, b);
            }
        }

        public final boolean zzm(Object obj, long j) {
            if (zzys.zzcei) {
                return zzys.zzs(obj, j);
            }
            return zzys.zzt(obj, j);
        }

        public final void zza(Object obj, long j, boolean z) {
            if (zzys.zzcei) {
                zzys.zzb(obj, j, z);
            } else {
                zzys.zzc(obj, j, z);
            }
        }

        public final float zzn(Object obj, long j) {
            return Float.intBitsToFloat(zzk(obj, j));
        }

        public final void zza(Object obj, long j, float f) {
            zzb(obj, j, Float.floatToIntBits(f));
        }

        public final double zzo(Object obj, long j) {
            return Double.longBitsToDouble(zzl(obj, j));
        }

        public final void zza(Object obj, long j, double d) {
            zza(obj, j, Double.doubleToLongBits(d));
        }

        public final void zza(byte[] bArr, long j, long j2, long j3) {
            Memory.pokeByteArray((int) (j2 & -1), bArr, (int) j, (int) j3);
        }
    }

    static final class zzb extends zzd {
        zzb(Unsafe unsafe) {
            super(unsafe);
        }

        public final void zza(long j, byte b) {
            Memory.pokeByte(j, b);
        }

        public final byte zzy(Object obj, long j) {
            if (zzys.zzcei) {
                return zzys.zzq(obj, j);
            }
            return zzys.zzr(obj, j);
        }

        public final void zze(Object obj, long j, byte b) {
            if (zzys.zzcei) {
                zzys.zza(obj, j, b);
            } else {
                zzys.zzb(obj, j, b);
            }
        }

        public final boolean zzm(Object obj, long j) {
            if (zzys.zzcei) {
                return zzys.zzs(obj, j);
            }
            return zzys.zzt(obj, j);
        }

        public final void zza(Object obj, long j, boolean z) {
            if (zzys.zzcei) {
                zzys.zzb(obj, j, z);
            } else {
                zzys.zzc(obj, j, z);
            }
        }

        public final float zzn(Object obj, long j) {
            return Float.intBitsToFloat(zzk(obj, j));
        }

        public final void zza(Object obj, long j, float f) {
            zzb(obj, j, Float.floatToIntBits(f));
        }

        public final double zzo(Object obj, long j) {
            return Double.longBitsToDouble(zzl(obj, j));
        }

        public final void zza(Object obj, long j, double d) {
            zza(obj, j, Double.doubleToLongBits(d));
        }

        public final void zza(byte[] bArr, long j, long j2, long j3) {
            Memory.pokeByteArray(j2, bArr, (int) j, (int) j3);
        }
    }

    static final class zzc extends zzd {
        zzc(Unsafe unsafe) {
            super(unsafe);
        }

        public final void zza(long j, byte b) {
            this.zzcej.putByte(j, b);
        }

        public final byte zzy(Object obj, long j) {
            return this.zzcej.getByte(obj, j);
        }

        public final void zze(Object obj, long j, byte b) {
            this.zzcej.putByte(obj, j, b);
        }

        public final boolean zzm(Object obj, long j) {
            return this.zzcej.getBoolean(obj, j);
        }

        public final void zza(Object obj, long j, boolean z) {
            this.zzcej.putBoolean(obj, j, z);
        }

        public final float zzn(Object obj, long j) {
            return this.zzcej.getFloat(obj, j);
        }

        public final void zza(Object obj, long j, float f) {
            this.zzcej.putFloat(obj, j, f);
        }

        public final double zzo(Object obj, long j) {
            return this.zzcej.getDouble(obj, j);
        }

        public final void zza(Object obj, long j, double d) {
            this.zzcej.putDouble(obj, j, d);
        }

        public final void zza(byte[] bArr, long j, long j2, long j3) {
            this.zzcej.copyMemory(bArr, zzys.zzcdu + j, null, j2, j3);
        }
    }

    private zzys() {
    }

    static boolean zzyv() {
        return zzbvq;
    }

    static boolean zzyw() {
        return zzcdt;
    }

    static <T> T zzk(Class<T> cls) {
        try {
            return zzcbt.allocateInstance(cls);
        } catch (Class<T> cls2) {
            throw new IllegalStateException(cls2);
        }
    }

    private static int zzl(Class<?> cls) {
        return zzbvq ? zzcds.zzcej.arrayBaseOffset(cls) : -1;
    }

    private static int zzm(Class<?> cls) {
        return zzbvq ? zzcds.zzcej.arrayIndexScale(cls) : -1;
    }

    static int zzk(Object obj, long j) {
        return zzcds.zzk(obj, j);
    }

    static void zzb(Object obj, long j, int i) {
        zzcds.zzb(obj, j, i);
    }

    static long zzl(Object obj, long j) {
        return zzcds.zzl(obj, j);
    }

    static void zza(Object obj, long j, long j2) {
        zzcds.zza(obj, j, j2);
    }

    static boolean zzm(Object obj, long j) {
        return zzcds.zzm(obj, j);
    }

    static void zza(Object obj, long j, boolean z) {
        zzcds.zza(obj, j, z);
    }

    static float zzn(Object obj, long j) {
        return zzcds.zzn(obj, j);
    }

    static void zza(Object obj, long j, float f) {
        zzcds.zza(obj, j, f);
    }

    static double zzo(Object obj, long j) {
        return zzcds.zzo(obj, j);
    }

    static void zza(Object obj, long j, double d) {
        zzcds.zza(obj, j, d);
    }

    static Object zzp(Object obj, long j) {
        return zzcds.zzcej.getObject(obj, j);
    }

    static void zza(Object obj, long j, Object obj2) {
        zzcds.zzcej.putObject(obj, j, obj2);
    }

    static byte zza(byte[] bArr, long j) {
        return zzcds.zzy(bArr, zzcdu + j);
    }

    static void zza(byte[] bArr, long j, byte b) {
        zzcds.zze(bArr, zzcdu + j, b);
    }

    static void zza(byte[] bArr, long j, long j2, long j3) {
        zzcds.zza(bArr, j, j2, j3);
    }

    static void zza(long j, byte b) {
        zzcds.zza(j, b);
    }

    static long zzb(ByteBuffer byteBuffer) {
        return zzcds.zzl(byteBuffer, zzceh);
    }

    static sun.misc.Unsafe zzyx() {
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
        r0 = new com.google.android.gms.internal.measurement.zzyt;	 Catch:{ Throwable -> 0x000c }
        r0.<init>();	 Catch:{ Throwable -> 0x000c }
        r0 = java.security.AccessController.doPrivileged(r0);	 Catch:{ Throwable -> 0x000c }
        r0 = (sun.misc.Unsafe) r0;	 Catch:{ Throwable -> 0x000c }
        goto L_0x000d;
    L_0x000c:
        r0 = 0;
    L_0x000d:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzys.zzyx():sun.misc.Unsafe");
    }

    private static boolean zzyy() {
        if (zzcbt == null) {
            return false;
        }
        try {
            Class cls = zzcbt.getClass();
            cls.getMethod("objectFieldOffset", new Class[]{Field.class});
            cls.getMethod("arrayBaseOffset", new Class[]{Class.class});
            cls.getMethod("arrayIndexScale", new Class[]{Class.class});
            cls.getMethod("getInt", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putInt", new Class[]{Object.class, Long.TYPE, Integer.TYPE});
            cls.getMethod("getLong", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putLong", new Class[]{Object.class, Long.TYPE, Long.TYPE});
            cls.getMethod("getObject", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putObject", new Class[]{Object.class, Long.TYPE, Object.class});
            if (zzuk.zzui()) {
                return true;
            }
            cls.getMethod("getByte", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putByte", new Class[]{Object.class, Long.TYPE, Byte.TYPE});
            cls.getMethod("getBoolean", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putBoolean", new Class[]{Object.class, Long.TYPE, Boolean.TYPE});
            cls.getMethod("getFloat", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putFloat", new Class[]{Object.class, Long.TYPE, Float.TYPE});
            cls.getMethod("getDouble", new Class[]{Object.class, Long.TYPE});
            cls.getMethod("putDouble", new Class[]{Object.class, Long.TYPE, Double.TYPE});
            return true;
        } catch (Throwable th) {
            String valueOf = String.valueOf(th);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 71);
            stringBuilder.append("platform method missing - proto runtime falling back to safer methods: ");
            stringBuilder.append(valueOf);
            logger.logp(Level.WARNING, "com.google.protobuf.UnsafeUtil", "supportsUnsafeArrayOperations", stringBuilder.toString());
            return false;
        }
    }

    private static boolean zzyz() {
        if (zzcbt == null) {
            return false;
        }
        try {
            Class cls = zzcbt.getClass();
            cls.getMethod("objectFieldOffset", new Class[]{Field.class});
            cls.getMethod("getLong", new Class[]{Object.class, Long.TYPE});
            if (zzza() == null) {
                return false;
            }
            if (zzuk.zzui()) {
                return true;
            }
            cls.getMethod("getByte", new Class[]{Long.TYPE});
            cls.getMethod("putByte", new Class[]{Long.TYPE, Byte.TYPE});
            cls.getMethod("getInt", new Class[]{Long.TYPE});
            cls.getMethod("putInt", new Class[]{Long.TYPE, Integer.TYPE});
            cls.getMethod("getLong", new Class[]{Long.TYPE});
            cls.getMethod("putLong", new Class[]{Long.TYPE, Long.TYPE});
            cls.getMethod("copyMemory", new Class[]{Long.TYPE, Long.TYPE, Long.TYPE});
            cls.getMethod("copyMemory", new Class[]{Object.class, Long.TYPE, Object.class, Long.TYPE, Long.TYPE});
            return true;
        } catch (Throwable th) {
            String valueOf = String.valueOf(th);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 71);
            stringBuilder.append("platform method missing - proto runtime falling back to safer methods: ");
            stringBuilder.append(valueOf);
            logger.logp(Level.WARNING, "com.google.protobuf.UnsafeUtil", "supportsUnsafeByteBufferOperations", stringBuilder.toString());
            return false;
        }
    }

    private static boolean zzn(java.lang.Class<?> r9) {
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
        r0 = com.google.android.gms.internal.measurement.zzuk.zzui();
        r1 = 0;
        if (r0 != 0) goto L_0x0008;
    L_0x0007:
        return r1;
    L_0x0008:
        r0 = zzbuq;	 Catch:{ Throwable -> 0x008b }
        r2 = "peekLong";	 Catch:{ Throwable -> 0x008b }
        r3 = 2;	 Catch:{ Throwable -> 0x008b }
        r4 = new java.lang.Class[r3];	 Catch:{ Throwable -> 0x008b }
        r4[r1] = r9;	 Catch:{ Throwable -> 0x008b }
        r5 = java.lang.Boolean.TYPE;	 Catch:{ Throwable -> 0x008b }
        r6 = 1;	 Catch:{ Throwable -> 0x008b }
        r4[r6] = r5;	 Catch:{ Throwable -> 0x008b }
        r0.getMethod(r2, r4);	 Catch:{ Throwable -> 0x008b }
        r2 = "pokeLong";	 Catch:{ Throwable -> 0x008b }
        r4 = 3;	 Catch:{ Throwable -> 0x008b }
        r5 = new java.lang.Class[r4];	 Catch:{ Throwable -> 0x008b }
        r5[r1] = r9;	 Catch:{ Throwable -> 0x008b }
        r7 = java.lang.Long.TYPE;	 Catch:{ Throwable -> 0x008b }
        r5[r6] = r7;	 Catch:{ Throwable -> 0x008b }
        r7 = java.lang.Boolean.TYPE;	 Catch:{ Throwable -> 0x008b }
        r5[r3] = r7;	 Catch:{ Throwable -> 0x008b }
        r0.getMethod(r2, r5);	 Catch:{ Throwable -> 0x008b }
        r2 = "pokeInt";	 Catch:{ Throwable -> 0x008b }
        r5 = new java.lang.Class[r4];	 Catch:{ Throwable -> 0x008b }
        r5[r1] = r9;	 Catch:{ Throwable -> 0x008b }
        r7 = java.lang.Integer.TYPE;	 Catch:{ Throwable -> 0x008b }
        r5[r6] = r7;	 Catch:{ Throwable -> 0x008b }
        r7 = java.lang.Boolean.TYPE;	 Catch:{ Throwable -> 0x008b }
        r5[r3] = r7;	 Catch:{ Throwable -> 0x008b }
        r0.getMethod(r2, r5);	 Catch:{ Throwable -> 0x008b }
        r2 = "peekInt";	 Catch:{ Throwable -> 0x008b }
        r5 = new java.lang.Class[r3];	 Catch:{ Throwable -> 0x008b }
        r5[r1] = r9;	 Catch:{ Throwable -> 0x008b }
        r7 = java.lang.Boolean.TYPE;	 Catch:{ Throwable -> 0x008b }
        r5[r6] = r7;	 Catch:{ Throwable -> 0x008b }
        r0.getMethod(r2, r5);	 Catch:{ Throwable -> 0x008b }
        r2 = "pokeByte";	 Catch:{ Throwable -> 0x008b }
        r5 = new java.lang.Class[r3];	 Catch:{ Throwable -> 0x008b }
        r5[r1] = r9;	 Catch:{ Throwable -> 0x008b }
        r7 = java.lang.Byte.TYPE;	 Catch:{ Throwable -> 0x008b }
        r5[r6] = r7;	 Catch:{ Throwable -> 0x008b }
        r0.getMethod(r2, r5);	 Catch:{ Throwable -> 0x008b }
        r2 = "peekByte";	 Catch:{ Throwable -> 0x008b }
        r5 = new java.lang.Class[r6];	 Catch:{ Throwable -> 0x008b }
        r5[r1] = r9;	 Catch:{ Throwable -> 0x008b }
        r0.getMethod(r2, r5);	 Catch:{ Throwable -> 0x008b }
        r2 = "pokeByteArray";	 Catch:{ Throwable -> 0x008b }
        r5 = 4;	 Catch:{ Throwable -> 0x008b }
        r7 = new java.lang.Class[r5];	 Catch:{ Throwable -> 0x008b }
        r7[r1] = r9;	 Catch:{ Throwable -> 0x008b }
        r8 = byte[].class;	 Catch:{ Throwable -> 0x008b }
        r7[r6] = r8;	 Catch:{ Throwable -> 0x008b }
        r8 = java.lang.Integer.TYPE;	 Catch:{ Throwable -> 0x008b }
        r7[r3] = r8;	 Catch:{ Throwable -> 0x008b }
        r8 = java.lang.Integer.TYPE;	 Catch:{ Throwable -> 0x008b }
        r7[r4] = r8;	 Catch:{ Throwable -> 0x008b }
        r0.getMethod(r2, r7);	 Catch:{ Throwable -> 0x008b }
        r2 = "peekByteArray";	 Catch:{ Throwable -> 0x008b }
        r5 = new java.lang.Class[r5];	 Catch:{ Throwable -> 0x008b }
        r5[r1] = r9;	 Catch:{ Throwable -> 0x008b }
        r9 = byte[].class;	 Catch:{ Throwable -> 0x008b }
        r5[r6] = r9;	 Catch:{ Throwable -> 0x008b }
        r9 = java.lang.Integer.TYPE;	 Catch:{ Throwable -> 0x008b }
        r5[r3] = r9;	 Catch:{ Throwable -> 0x008b }
        r9 = java.lang.Integer.TYPE;	 Catch:{ Throwable -> 0x008b }
        r5[r4] = r9;	 Catch:{ Throwable -> 0x008b }
        r0.getMethod(r2, r5);	 Catch:{ Throwable -> 0x008b }
        return r6;
    L_0x008b:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzys.zzn(java.lang.Class):boolean");
    }

    private static Field zzza() {
        Field zzb;
        if (zzuk.zzui()) {
            zzb = zzb(Buffer.class, "effectiveDirectAddress");
            if (zzb != null) {
                return zzb;
            }
        }
        zzb = zzb(Buffer.class, "address");
        return (zzb == null || zzb.getType() != Long.TYPE) ? null : zzb;
    }

    private static java.lang.reflect.Field zzb(java.lang.Class<?> r0, java.lang.String r1) {
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
        r0 = r0.getDeclaredField(r1);	 Catch:{ Throwable -> 0x0009 }
        r1 = 1;	 Catch:{ Throwable -> 0x0009 }
        r0.setAccessible(r1);	 Catch:{ Throwable -> 0x0009 }
        goto L_0x000a;
    L_0x0009:
        r0 = 0;
    L_0x000a:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzys.zzb(java.lang.Class, java.lang.String):java.lang.reflect.Field");
    }

    private static byte zzq(Object obj, long j) {
        return (byte) (zzk(obj, -4 & j) >>> ((int) (((j ^ -1) & 3) << 3)));
    }

    private static byte zzr(Object obj, long j) {
        return (byte) (zzk(obj, -4 & j) >>> ((int) ((j & 3) << 3)));
    }

    private static void zza(Object obj, long j, byte b) {
        long j2 = -4 & j;
        j = ((((int) j) ^ -1) & 3) << 3;
        zzb(obj, j2, ((255 & b) << j) | (zzk(obj, j2) & ((255 << j) ^ -1)));
    }

    private static void zzb(Object obj, long j, byte b) {
        long j2 = -4 & j;
        j = (((int) j) & 3) << 3;
        zzb(obj, j2, ((255 & b) << j) | (zzk(obj, j2) & ((255 << j) ^ -1)));
    }

    private static boolean zzs(Object obj, long j) {
        return zzq(obj, j) != null ? true : null;
    }

    private static boolean zzt(Object obj, long j) {
        return zzr(obj, j) != null ? true : null;
    }

    private static void zzb(Object obj, long j, boolean z) {
        zza(obj, j, (byte) z);
    }

    private static void zzc(Object obj, long j, boolean z) {
        zzb(obj, j, (byte) z);
    }

    static {
        long objectFieldOffset;
        zzd com_google_android_gms_internal_measurement_zzys_zzd = null;
        if (zzcbt != null) {
            if (!zzuk.zzui()) {
                com_google_android_gms_internal_measurement_zzys_zzd = new zzc(zzcbt);
            } else if (zzcdq) {
                com_google_android_gms_internal_measurement_zzys_zzd = new zzb(zzcbt);
            } else if (zzcdr) {
                com_google_android_gms_internal_measurement_zzys_zzd = new zza(zzcbt);
            }
        }
        zzcds = com_google_android_gms_internal_measurement_zzys_zzd;
        Field zzza = zzza();
        if (zzza != null) {
            if (zzcds != null) {
                objectFieldOffset = zzcds.zzcej.objectFieldOffset(zzza);
                zzceh = objectFieldOffset;
            }
        }
        objectFieldOffset = -1;
        zzceh = objectFieldOffset;
        if (ByteOrder.nativeOrder() != ByteOrder.BIG_ENDIAN) {
        }
    }
}
