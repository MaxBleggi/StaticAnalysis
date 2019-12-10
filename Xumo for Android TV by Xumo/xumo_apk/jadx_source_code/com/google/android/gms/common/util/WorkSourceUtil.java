package com.google.android.gms.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Process;
import android.os.WorkSource;
import android.util.Log;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.wrappers.Wrappers;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@KeepForSdk
public class WorkSourceUtil {
    private static final int zzhh = Process.myUid();
    private static final Method zzhi = zzw();
    private static final Method zzhj = zzx();
    private static final Method zzhk = zzy();
    private static final Method zzhl = zzz();
    private static final Method zzhm = zzaa();
    private static final Method zzhn = zzab();
    private static final Method zzho = zzac();

    private WorkSourceUtil() {
    }

    private static WorkSource zza(int i, String str) {
        WorkSource workSource = new WorkSource();
        zza(workSource, i, str);
        return workSource;
    }

    @com.google.android.gms.common.annotation.KeepForSdk
    @androidx.annotation.Nullable
    public static android.os.WorkSource fromPackage(android.content.Context r3, @androidx.annotation.Nullable java.lang.String r4) {
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
        if (r3 == 0) goto L_0x0056;
    L_0x0003:
        r1 = r3.getPackageManager();
        if (r1 == 0) goto L_0x0056;
    L_0x0009:
        if (r4 != 0) goto L_0x000c;
    L_0x000b:
        goto L_0x0056;
    L_0x000c:
        r3 = com.google.android.gms.common.wrappers.Wrappers.packageManager(r3);	 Catch:{ NameNotFoundException -> 0x003a }
        r1 = 0;	 Catch:{ NameNotFoundException -> 0x003a }
        r3 = r3.getApplicationInfo(r4, r1);	 Catch:{ NameNotFoundException -> 0x003a }
        if (r3 != 0) goto L_0x0033;
    L_0x0017:
        r3 = "WorkSourceUtil";
        r1 = "Could not get applicationInfo from package: ";
        r4 = java.lang.String.valueOf(r4);
        r2 = r4.length();
        if (r2 == 0) goto L_0x002a;
    L_0x0025:
        r4 = r1.concat(r4);
        goto L_0x002f;
    L_0x002a:
        r4 = new java.lang.String;
        r4.<init>(r1);
    L_0x002f:
        android.util.Log.e(r3, r4);
        return r0;
    L_0x0033:
        r3 = r3.uid;
        r3 = zza(r3, r4);
        return r3;
    L_0x003a:
        r3 = "WorkSourceUtil";
        r1 = "Could not find package: ";
        r4 = java.lang.String.valueOf(r4);
        r2 = r4.length();
        if (r2 == 0) goto L_0x004d;
    L_0x0048:
        r4 = r1.concat(r4);
        goto L_0x0052;
    L_0x004d:
        r4 = new java.lang.String;
        r4.<init>(r1);
    L_0x0052:
        android.util.Log.e(r3, r4);
        return r0;
    L_0x0056:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.util.WorkSourceUtil.fromPackage(android.content.Context, java.lang.String):android.os.WorkSource");
    }

    private static void zza(WorkSource workSource, int i, @Nullable String str) {
        if (zzhj != null) {
            if (str == null) {
                str = "";
            }
            try {
                zzhj.invoke(workSource, new Object[]{Integer.valueOf(i), str});
                return;
            } catch (WorkSource workSource2) {
                Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", workSource2);
                return;
            }
        }
        if (zzhi != null) {
            try {
                zzhi.invoke(workSource2, new Object[]{Integer.valueOf(i)});
            } catch (WorkSource workSource22) {
                Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", workSource22);
            }
        }
    }

    @KeepForSdk
    public static WorkSource fromPackageAndModuleExperimentalPi(Context context, String str, String str2) {
        if (!(context == null || context.getPackageManager() == null || str2 == null)) {
            if (str != null) {
                context = zzd(context, str);
                if (context < null) {
                    return null;
                }
                WorkSource workSource = new WorkSource();
                if (zzhn != null) {
                    if (zzho != null) {
                        try {
                            Object invoke = zzhn.invoke(workSource, new Object[0]);
                            if (context != zzhh) {
                                zzho.invoke(invoke, new Object[]{Integer.valueOf(context), str});
                            }
                            zzho.invoke(invoke, new Object[]{Integer.valueOf(zzhh), str2});
                        } catch (Context context2) {
                            Log.w("WorkSourceUtil", "Unable to assign chained blame through WorkSource", context2);
                        }
                        return workSource;
                    }
                }
                zza(workSource, context2, str);
                return workSource;
            }
        }
        Log.w("WorkSourceUtil", "Unexpected null arguments");
        return null;
    }

    private static int zza(WorkSource workSource) {
        if (zzhk != null) {
            try {
                return ((Integer) zzhk.invoke(workSource, new Object[0])).intValue();
            } catch (WorkSource workSource2) {
                Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", workSource2);
            }
        }
        return 0;
    }

    @Nullable
    private static String zza(WorkSource workSource, int i) {
        if (zzhm != null) {
            try {
                return (String) zzhm.invoke(workSource, new Object[]{Integer.valueOf(i)});
            } catch (WorkSource workSource2) {
                Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", workSource2);
            }
        }
        return null;
    }

    @KeepForSdk
    public static List<String> getNames(@Nullable WorkSource workSource) {
        int zza = workSource == null ? 0 : zza(workSource);
        if (zza == 0) {
            return Collections.emptyList();
        }
        List<String> arrayList = new ArrayList();
        for (int i = 0; i < zza; i++) {
            String zza2 = zza(workSource, i);
            if (!Strings.isEmptyOrWhitespace(zza2)) {
                arrayList.add(zza2);
            }
        }
        return arrayList;
    }

    @KeepForSdk
    public static boolean hasWorkSourcePermission(Context context) {
        if (context == null || context.getPackageManager() == null || Wrappers.packageManager(context).checkPermission("android.permission.UPDATE_DEVICE_STATS", context.getPackageName()) != null) {
            return false;
        }
        return true;
    }

    private static int zzd(android.content.Context r3, java.lang.String r4) {
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
        r0 = -1;
        r3 = com.google.android.gms.common.wrappers.Wrappers.packageManager(r3);	 Catch:{ NameNotFoundException -> 0x002b }
        r1 = 0;	 Catch:{ NameNotFoundException -> 0x002b }
        r3 = r3.getApplicationInfo(r4, r1);	 Catch:{ NameNotFoundException -> 0x002b }
        if (r3 != 0) goto L_0x0028;
    L_0x000c:
        r3 = "WorkSourceUtil";
        r1 = "Could not get applicationInfo from package: ";
        r4 = java.lang.String.valueOf(r4);
        r2 = r4.length();
        if (r2 == 0) goto L_0x001f;
    L_0x001a:
        r4 = r1.concat(r4);
        goto L_0x0024;
    L_0x001f:
        r4 = new java.lang.String;
        r4.<init>(r1);
    L_0x0024:
        android.util.Log.e(r3, r4);
        return r0;
    L_0x0028:
        r3 = r3.uid;
        return r3;
    L_0x002b:
        r3 = "WorkSourceUtil";
        r1 = "Could not find package: ";
        r4 = java.lang.String.valueOf(r4);
        r2 = r4.length();
        if (r2 == 0) goto L_0x003e;
    L_0x0039:
        r4 = r1.concat(r4);
        goto L_0x0043;
    L_0x003e:
        r4 = new java.lang.String;
        r4.<init>(r1);
    L_0x0043:
        android.util.Log.e(r3, r4);
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.util.WorkSourceUtil.zzd(android.content.Context, java.lang.String):int");
    }

    private static java.lang.reflect.Method zzw() {
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
        r0 = android.os.WorkSource.class;	 Catch:{ Exception -> 0x0011 }
        r1 = "add";	 Catch:{ Exception -> 0x0011 }
        r2 = 1;	 Catch:{ Exception -> 0x0011 }
        r2 = new java.lang.Class[r2];	 Catch:{ Exception -> 0x0011 }
        r3 = 0;	 Catch:{ Exception -> 0x0011 }
        r4 = java.lang.Integer.TYPE;	 Catch:{ Exception -> 0x0011 }
        r2[r3] = r4;	 Catch:{ Exception -> 0x0011 }
        r0 = r0.getMethod(r1, r2);	 Catch:{ Exception -> 0x0011 }
        goto L_0x0012;
    L_0x0011:
        r0 = 0;
    L_0x0012:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.util.WorkSourceUtil.zzw():java.lang.reflect.Method");
    }

    private static java.lang.reflect.Method zzx() {
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
        r0 = com.google.android.gms.common.util.PlatformVersion.isAtLeastJellyBeanMR2();
        if (r0 == 0) goto L_0x001c;
    L_0x0006:
        r0 = android.os.WorkSource.class;	 Catch:{ Exception -> 0x001c }
        r1 = "add";	 Catch:{ Exception -> 0x001c }
        r2 = 2;	 Catch:{ Exception -> 0x001c }
        r2 = new java.lang.Class[r2];	 Catch:{ Exception -> 0x001c }
        r3 = 0;	 Catch:{ Exception -> 0x001c }
        r4 = java.lang.Integer.TYPE;	 Catch:{ Exception -> 0x001c }
        r2[r3] = r4;	 Catch:{ Exception -> 0x001c }
        r3 = 1;	 Catch:{ Exception -> 0x001c }
        r4 = java.lang.String.class;	 Catch:{ Exception -> 0x001c }
        r2[r3] = r4;	 Catch:{ Exception -> 0x001c }
        r0 = r0.getMethod(r1, r2);	 Catch:{ Exception -> 0x001c }
        goto L_0x001d;
    L_0x001c:
        r0 = 0;
    L_0x001d:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.util.WorkSourceUtil.zzx():java.lang.reflect.Method");
    }

    private static java.lang.reflect.Method zzy() {
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
        r0 = android.os.WorkSource.class;	 Catch:{ Exception -> 0x000c }
        r1 = "size";	 Catch:{ Exception -> 0x000c }
        r2 = 0;	 Catch:{ Exception -> 0x000c }
        r2 = new java.lang.Class[r2];	 Catch:{ Exception -> 0x000c }
        r0 = r0.getMethod(r1, r2);	 Catch:{ Exception -> 0x000c }
        goto L_0x000d;
    L_0x000c:
        r0 = 0;
    L_0x000d:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.util.WorkSourceUtil.zzy():java.lang.reflect.Method");
    }

    private static java.lang.reflect.Method zzz() {
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
        r0 = android.os.WorkSource.class;	 Catch:{ Exception -> 0x0011 }
        r1 = "get";	 Catch:{ Exception -> 0x0011 }
        r2 = 1;	 Catch:{ Exception -> 0x0011 }
        r2 = new java.lang.Class[r2];	 Catch:{ Exception -> 0x0011 }
        r3 = 0;	 Catch:{ Exception -> 0x0011 }
        r4 = java.lang.Integer.TYPE;	 Catch:{ Exception -> 0x0011 }
        r2[r3] = r4;	 Catch:{ Exception -> 0x0011 }
        r0 = r0.getMethod(r1, r2);	 Catch:{ Exception -> 0x0011 }
        goto L_0x0012;
    L_0x0011:
        r0 = 0;
    L_0x0012:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.util.WorkSourceUtil.zzz():java.lang.reflect.Method");
    }

    private static java.lang.reflect.Method zzaa() {
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
        r0 = com.google.android.gms.common.util.PlatformVersion.isAtLeastJellyBeanMR2();
        if (r0 == 0) goto L_0x0017;
    L_0x0006:
        r0 = android.os.WorkSource.class;	 Catch:{ Exception -> 0x0017 }
        r1 = "getName";	 Catch:{ Exception -> 0x0017 }
        r2 = 1;	 Catch:{ Exception -> 0x0017 }
        r2 = new java.lang.Class[r2];	 Catch:{ Exception -> 0x0017 }
        r3 = 0;	 Catch:{ Exception -> 0x0017 }
        r4 = java.lang.Integer.TYPE;	 Catch:{ Exception -> 0x0017 }
        r2[r3] = r4;	 Catch:{ Exception -> 0x0017 }
        r0 = r0.getMethod(r1, r2);	 Catch:{ Exception -> 0x0017 }
        goto L_0x0018;
    L_0x0017:
        r0 = 0;
    L_0x0018:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.util.WorkSourceUtil.zzaa():java.lang.reflect.Method");
    }

    private static final Method zzab() {
        if (PlatformVersion.isAtLeastP()) {
            try {
                return WorkSource.class.getMethod("createWorkChain", new Class[0]);
            } catch (Throwable e) {
                Log.w("WorkSourceUtil", "Missing WorkChain API createWorkChain", e);
            }
        }
        return null;
    }

    @SuppressLint({"PrivateApi"})
    private static final Method zzac() {
        if (PlatformVersion.isAtLeastP()) {
            try {
                return Class.forName("android.os.WorkSource$WorkChain").getMethod("addNode", new Class[]{Integer.TYPE, String.class});
            } catch (Throwable e) {
                Log.w("WorkSourceUtil", "Missing WorkChain class", e);
            }
        }
        return null;
    }
}
