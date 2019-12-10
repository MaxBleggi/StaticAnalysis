package com.google.android.gms.flags;

import com.google.android.gms.common.annotation.KeepForSdk;

@KeepForSdk
@Deprecated
public abstract class Flag<T> {
    private final String mKey;
    private final int zze;
    private final T zzf;

    @Deprecated
    public static class BooleanFlag extends Flag<Boolean> {
        public BooleanFlag(int i, String str, Boolean bool) {
            super(i, str, bool);
        }

        private final java.lang.Boolean zzb(com.google.android.gms.flags.zzc r4) {
            /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r3 = this;
            r0 = r3.getKey();	 Catch:{ RemoteException -> 0x001b }
            r1 = r3.zzb();	 Catch:{ RemoteException -> 0x001b }
            r1 = (java.lang.Boolean) r1;	 Catch:{ RemoteException -> 0x001b }
            r1 = r1.booleanValue();	 Catch:{ RemoteException -> 0x001b }
            r2 = r3.getSource();	 Catch:{ RemoteException -> 0x001b }
            r4 = r4.getBooleanFlagValue(r0, r1, r2);	 Catch:{ RemoteException -> 0x001b }
            r4 = java.lang.Boolean.valueOf(r4);	 Catch:{ RemoteException -> 0x001b }
            return r4;
        L_0x001b:
            r4 = r3.zzb();
            r4 = (java.lang.Boolean) r4;
            return r4;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.flags.Flag.BooleanFlag.zzb(com.google.android.gms.flags.zzc):java.lang.Boolean");
        }

        public final /* synthetic */ Object zza(zzc com_google_android_gms_flags_zzc) {
            return zzb(com_google_android_gms_flags_zzc);
        }
    }

    @KeepForSdk
    @Deprecated
    public static class IntegerFlag extends Flag<Integer> {
        public IntegerFlag(int i, String str, Integer num) {
            super(i, str, num);
        }

        private final java.lang.Integer zzc(com.google.android.gms.flags.zzc r4) {
            /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r3 = this;
            r0 = r3.getKey();	 Catch:{ RemoteException -> 0x001b }
            r1 = r3.zzb();	 Catch:{ RemoteException -> 0x001b }
            r1 = (java.lang.Integer) r1;	 Catch:{ RemoteException -> 0x001b }
            r1 = r1.intValue();	 Catch:{ RemoteException -> 0x001b }
            r2 = r3.getSource();	 Catch:{ RemoteException -> 0x001b }
            r4 = r4.getIntFlagValue(r0, r1, r2);	 Catch:{ RemoteException -> 0x001b }
            r4 = java.lang.Integer.valueOf(r4);	 Catch:{ RemoteException -> 0x001b }
            return r4;
        L_0x001b:
            r4 = r3.zzb();
            r4 = (java.lang.Integer) r4;
            return r4;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.flags.Flag.IntegerFlag.zzc(com.google.android.gms.flags.zzc):java.lang.Integer");
        }

        public final /* synthetic */ Object zza(zzc com_google_android_gms_flags_zzc) {
            return zzc(com_google_android_gms_flags_zzc);
        }
    }

    @KeepForSdk
    @Deprecated
    public static class LongFlag extends Flag<Long> {
        public LongFlag(int i, String str, Long l) {
            super(i, str, l);
        }

        private final java.lang.Long zzd(com.google.android.gms.flags.zzc r5) {
            /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r4 = this;
            r0 = r4.getKey();	 Catch:{ RemoteException -> 0x001b }
            r1 = r4.zzb();	 Catch:{ RemoteException -> 0x001b }
            r1 = (java.lang.Long) r1;	 Catch:{ RemoteException -> 0x001b }
            r1 = r1.longValue();	 Catch:{ RemoteException -> 0x001b }
            r3 = r4.getSource();	 Catch:{ RemoteException -> 0x001b }
            r0 = r5.getLongFlagValue(r0, r1, r3);	 Catch:{ RemoteException -> 0x001b }
            r5 = java.lang.Long.valueOf(r0);	 Catch:{ RemoteException -> 0x001b }
            return r5;
        L_0x001b:
            r5 = r4.zzb();
            r5 = (java.lang.Long) r5;
            return r5;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.flags.Flag.LongFlag.zzd(com.google.android.gms.flags.zzc):java.lang.Long");
        }

        public final /* synthetic */ Object zza(zzc com_google_android_gms_flags_zzc) {
            return zzd(com_google_android_gms_flags_zzc);
        }
    }

    @KeepForSdk
    @Deprecated
    public static class StringFlag extends Flag<String> {
        public StringFlag(int i, String str, String str2) {
            super(i, str, str2);
        }

        private final java.lang.String zze(com.google.android.gms.flags.zzc r4) {
            /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r3 = this;
            r0 = r3.getKey();	 Catch:{ RemoteException -> 0x0013 }
            r1 = r3.zzb();	 Catch:{ RemoteException -> 0x0013 }
            r1 = (java.lang.String) r1;	 Catch:{ RemoteException -> 0x0013 }
            r2 = r3.getSource();	 Catch:{ RemoteException -> 0x0013 }
            r4 = r4.getStringFlagValue(r0, r1, r2);	 Catch:{ RemoteException -> 0x0013 }
            return r4;
        L_0x0013:
            r4 = r3.zzb();
            r4 = (java.lang.String) r4;
            return r4;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.flags.Flag.StringFlag.zze(com.google.android.gms.flags.zzc):java.lang.String");
        }

        public final /* synthetic */ Object zza(zzc com_google_android_gms_flags_zzc) {
            return zze(com_google_android_gms_flags_zzc);
        }
    }

    private Flag(int i, String str, T t) {
        this.zze = i;
        this.mKey = str;
        this.zzf = t;
        Singletons.flagRegistry().zza(this);
    }

    protected abstract T zza(zzc com_google_android_gms_flags_zzc);

    public final String getKey() {
        return this.mKey;
    }

    public final T zzb() {
        return this.zzf;
    }

    @KeepForSdk
    public T get() {
        return Singletons.zzd().zzb(this);
    }

    @KeepForSdk
    @Deprecated
    public static BooleanFlag define(int i, String str, Boolean bool) {
        return new BooleanFlag(i, str, bool);
    }

    @KeepForSdk
    @Deprecated
    public static IntegerFlag define(int i, String str, int i2) {
        return new IntegerFlag(i, str, Integer.valueOf(i2));
    }

    @KeepForSdk
    @Deprecated
    public static LongFlag define(int i, String str, long j) {
        return new LongFlag(i, str, Long.valueOf(j));
    }

    @KeepForSdk
    @Deprecated
    public static StringFlag define(int i, String str, String str2) {
        return new StringFlag(i, str, str2);
    }

    @Deprecated
    public final int getSource() {
        return this.zze;
    }
}
