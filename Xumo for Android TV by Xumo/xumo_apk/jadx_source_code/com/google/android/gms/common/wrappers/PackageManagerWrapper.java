package com.google.android.gms.common.wrappers;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Binder;
import android.os.Process;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.util.PlatformVersion;

@KeepForSdk
public class PackageManagerWrapper {
    private final Context zzhv;

    public PackageManagerWrapper(Context context) {
        this.zzhv = context;
    }

    @KeepForSdk
    public ApplicationInfo getApplicationInfo(String str, int i) throws NameNotFoundException {
        return this.zzhv.getPackageManager().getApplicationInfo(str, i);
    }

    @KeepForSdk
    public PackageInfo getPackageInfo(String str, int i) throws NameNotFoundException {
        return this.zzhv.getPackageManager().getPackageInfo(str, i);
    }

    public final PackageInfo zza(String str, int i, int i2) throws NameNotFoundException {
        return this.zzhv.getPackageManager().getPackageInfo(str, 64);
    }

    public final String[] getPackagesForUid(int i) {
        return this.zzhv.getPackageManager().getPackagesForUid(i);
    }

    @android.annotation.TargetApi(19)
    public final boolean zzb(int r5, java.lang.String r6) {
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
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r4 = this;
        r0 = com.google.android.gms.common.util.PlatformVersion.isAtLeastKitKat();
        r1 = 1;
        r2 = 0;
        if (r0 == 0) goto L_0x0017;
    L_0x0008:
        r0 = r4.zzhv;	 Catch:{ SecurityException -> 0x0016 }
        r3 = "appops";	 Catch:{ SecurityException -> 0x0016 }
        r0 = r0.getSystemService(r3);	 Catch:{ SecurityException -> 0x0016 }
        r0 = (android.app.AppOpsManager) r0;	 Catch:{ SecurityException -> 0x0016 }
        r0.checkPackage(r5, r6);	 Catch:{ SecurityException -> 0x0016 }
        return r1;
    L_0x0016:
        return r2;
    L_0x0017:
        r0 = r4.zzhv;
        r0 = r0.getPackageManager();
        r5 = r0.getPackagesForUid(r5);
        if (r6 == 0) goto L_0x0035;
    L_0x0023:
        if (r5 == 0) goto L_0x0035;
    L_0x0025:
        r0 = 0;
    L_0x0026:
        r3 = r5.length;
        if (r0 >= r3) goto L_0x0035;
    L_0x0029:
        r3 = r5[r0];
        r3 = r6.equals(r3);
        if (r3 == 0) goto L_0x0032;
    L_0x0031:
        return r1;
    L_0x0032:
        r0 = r0 + 1;
        goto L_0x0026;
    L_0x0035:
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.wrappers.PackageManagerWrapper.zzb(int, java.lang.String):boolean");
    }

    @KeepForSdk
    public int checkCallingOrSelfPermission(String str) {
        return this.zzhv.checkCallingOrSelfPermission(str);
    }

    @KeepForSdk
    public int checkPermission(String str, String str2) {
        return this.zzhv.getPackageManager().checkPermission(str, str2);
    }

    @KeepForSdk
    public CharSequence getApplicationLabel(String str) throws NameNotFoundException {
        return this.zzhv.getPackageManager().getApplicationLabel(this.zzhv.getPackageManager().getApplicationInfo(str, 0));
    }

    @KeepForSdk
    public boolean isCallerInstantApp() {
        if (Binder.getCallingUid() == Process.myUid()) {
            return InstantApps.isInstantApp(this.zzhv);
        }
        if (PlatformVersion.isAtLeastO()) {
            String nameForUid = this.zzhv.getPackageManager().getNameForUid(Binder.getCallingUid());
            if (nameForUid != null) {
                return this.zzhv.getPackageManager().isInstantApp(nameForUid);
            }
        }
        return false;
    }
}
