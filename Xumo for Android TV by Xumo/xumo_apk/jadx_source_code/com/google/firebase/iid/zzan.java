package com.google.firebase.iid;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.firebase.FirebaseApp;
import java.util.List;
import javax.annotation.concurrent.GuardedBy;

public final class zzan {
    @GuardedBy("this")
    private String zzci;
    @GuardedBy("this")
    private String zzcj;
    @GuardedBy("this")
    private int zzck;
    @GuardedBy("this")
    private int zzcl = 0;
    private final Context zzx;

    public zzan(Context context) {
        this.zzx = context;
    }

    public final synchronized int zzac() {
        if (this.zzcl != 0) {
            return this.zzcl;
        }
        PackageManager packageManager = this.zzx.getPackageManager();
        if (packageManager.checkPermission("com.google.android.c2dm.permission.SEND", "com.google.android.gms") == -1) {
            Log.e("FirebaseInstanceId", "Google Play services missing or without correct permission.");
            return 0;
        }
        Intent intent;
        if (!PlatformVersion.isAtLeastO()) {
            intent = new Intent("com.google.android.c2dm.intent.REGISTER");
            intent.setPackage("com.google.android.gms");
            List queryIntentServices = packageManager.queryIntentServices(intent, 0);
            if (queryIntentServices != null && queryIntentServices.size() > 0) {
                this.zzcl = 1;
                return this.zzcl;
            }
        }
        intent = new Intent("com.google.iid.TOKEN_REQUEST");
        intent.setPackage("com.google.android.gms");
        List queryBroadcastReceivers = packageManager.queryBroadcastReceivers(intent, 0);
        if (queryBroadcastReceivers == null || queryBroadcastReceivers.size() <= 0) {
            Log.w("FirebaseInstanceId", "Failed to resolve IID implementation package, falling back");
            if (PlatformVersion.isAtLeastO()) {
                this.zzcl = 2;
            } else {
                this.zzcl = 1;
            }
            return this.zzcl;
        }
        this.zzcl = 2;
        return this.zzcl;
    }

    public static String zza(FirebaseApp firebaseApp) {
        String gcmSenderId = firebaseApp.getOptions().getGcmSenderId();
        if (gcmSenderId != null) {
            return gcmSenderId;
        }
        firebaseApp = firebaseApp.getOptions().getApplicationId();
        if (!firebaseApp.startsWith("1:")) {
            return firebaseApp;
        }
        firebaseApp = firebaseApp.split(":");
        if (firebaseApp.length < 2) {
            return null;
        }
        firebaseApp = firebaseApp[1];
        if (firebaseApp.isEmpty()) {
            return null;
        }
        return firebaseApp;
    }

    public static java.lang.String zza(java.security.KeyPair r3) {
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
        r3 = r3.getPublic();
        r3 = r3.getEncoded();
        r0 = "SHA1";	 Catch:{ NoSuchAlgorithmException -> 0x0025 }
        r0 = java.security.MessageDigest.getInstance(r0);	 Catch:{ NoSuchAlgorithmException -> 0x0025 }
        r3 = r0.digest(r3);	 Catch:{ NoSuchAlgorithmException -> 0x0025 }
        r0 = 0;	 Catch:{ NoSuchAlgorithmException -> 0x0025 }
        r1 = r3[r0];	 Catch:{ NoSuchAlgorithmException -> 0x0025 }
        r1 = r1 & 15;	 Catch:{ NoSuchAlgorithmException -> 0x0025 }
        r1 = r1 + 112;	 Catch:{ NoSuchAlgorithmException -> 0x0025 }
        r1 = (byte) r1;	 Catch:{ NoSuchAlgorithmException -> 0x0025 }
        r3[r0] = r1;	 Catch:{ NoSuchAlgorithmException -> 0x0025 }
        r1 = 8;	 Catch:{ NoSuchAlgorithmException -> 0x0025 }
        r2 = 11;	 Catch:{ NoSuchAlgorithmException -> 0x0025 }
        r3 = android.util.Base64.encodeToString(r3, r0, r1, r2);	 Catch:{ NoSuchAlgorithmException -> 0x0025 }
        return r3;
    L_0x0025:
        r3 = "FirebaseInstanceId";
        r0 = "Unexpected error, device missing required algorithms";
        android.util.Log.w(r3, r0);
        r3 = 0;
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzan.zza(java.security.KeyPair):java.lang.String");
    }

    public final synchronized String zzad() {
        if (this.zzci == null) {
            zzag();
        }
        return this.zzci;
    }

    public final synchronized String zzae() {
        if (this.zzcj == null) {
            zzag();
        }
        return this.zzcj;
    }

    public final synchronized int zzaf() {
        if (this.zzck == 0) {
            PackageInfo zze = zze("com.google.android.gms");
            if (zze != null) {
                this.zzck = zze.versionCode;
            }
        }
        return this.zzck;
    }

    private final synchronized void zzag() {
        PackageInfo zze = zze(this.zzx.getPackageName());
        if (zze != null) {
            this.zzci = Integer.toString(zze.versionCode);
            this.zzcj = zze.versionName;
        }
    }

    private final PackageInfo zze(String str) {
        try {
            return this.zzx.getPackageManager().getPackageInfo(str, 0);
        } catch (String str2) {
            str2 = String.valueOf(str2);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str2).length() + 23);
            stringBuilder.append("Failed to find package ");
            stringBuilder.append(str2);
            Log.w("FirebaseInstanceId", stringBuilder.toString());
            return null;
        }
    }
}
