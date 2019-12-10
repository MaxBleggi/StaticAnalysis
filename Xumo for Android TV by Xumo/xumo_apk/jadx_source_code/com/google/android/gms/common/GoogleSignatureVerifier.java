package com.google.android.gms.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.wrappers.Wrappers;
import javax.annotation.CheckReturnValue;

@ShowFirstParty
@CheckReturnValue
@KeepForSdk
public class GoogleSignatureVerifier {
    private static GoogleSignatureVerifier zzal;
    private final Context mContext;
    private volatile String zzam;

    private GoogleSignatureVerifier(Context context) {
        this.mContext = context.getApplicationContext();
    }

    private final com.google.android.gms.common.zzm zza(java.lang.String r1, int r2) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.google.android.gms.common.GoogleSignatureVerifier.zza(java.lang.String, int):com.google.android.gms.common.zzm
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: java.lang.NullPointerException
	at jadx.core.dex.nodes.MethodNode.initTryCatches(MethodNode.java:305)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:105)
	... 5 more
*/
        /*
        r0 = this;
        r0 = r2.mContext;
        r0 = com.google.android.gms.common.wrappers.Wrappers.packageManager(r0);
        r1 = 64;
        r4 = r0.zza(r3, r1, r4);
        r4 = r2.zza(r4);
        return r4;
        r4 = "no pkg ";
        r3 = java.lang.String.valueOf(r3);
        r0 = r3.length();
        if (r0 == 0) goto L_0x0023;
        r3 = r4.concat(r3);
        goto L_0x0028;
        r3 = new java.lang.String;
        r3.<init>(r4);
        r3 = com.google.android.gms.common.zzm.zzb(r3);
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.GoogleSignatureVerifier.zza(java.lang.String, int):com.google.android.gms.common.zzm");
    }

    private final com.google.android.gms.common.zzm zzc(java.lang.String r1) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.google.android.gms.common.GoogleSignatureVerifier.zzc(java.lang.String):com.google.android.gms.common.zzm
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: java.lang.NullPointerException
	at jadx.core.dex.nodes.MethodNode.initTryCatches(MethodNode.java:305)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:105)
	... 5 more
*/
        /*
        r0 = this;
        if (r3 != 0) goto L_0x0009;
        r3 = "null pkg";
        r3 = com.google.android.gms.common.zzm.zzb(r3);
        return r3;
        r0 = r2.zzam;
        r0 = r3.equals(r0);
        if (r0 == 0) goto L_0x0016;
        r3 = com.google.android.gms.common.zzm.zze();
        return r3;
        r0 = r2.mContext;
        r0 = com.google.android.gms.common.wrappers.Wrappers.packageManager(r0);
        r1 = 64;
        r0 = r0.getPackageInfo(r3, r1);
        r0 = r2.zza(r0);
        r1 = r0.zzac;
        if (r1 == 0) goto L_0x002c;
        r2.zzam = r3;
        return r0;
        r0 = "no pkg ";
        r3 = java.lang.String.valueOf(r3);
        r1 = r3.length();
        if (r1 == 0) goto L_0x003f;
        r3 = r0.concat(r3);
        goto L_0x0044;
        r3 = new java.lang.String;
        r3.<init>(r0);
        r3 = com.google.android.gms.common.zzm.zzb(r3);
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.GoogleSignatureVerifier.zzc(java.lang.String):com.google.android.gms.common.zzm");
    }

    @KeepForSdk
    public static GoogleSignatureVerifier getInstance(Context context) {
        Preconditions.checkNotNull(context);
        synchronized (GoogleSignatureVerifier.class) {
            if (zzal == null) {
                zzc.zza(context);
                zzal = new GoogleSignatureVerifier(context);
            }
        }
        return zzal;
    }

    @ShowFirstParty
    @KeepForSdk
    public boolean isUidGoogleSigned(int i) {
        zzm com_google_android_gms_common_zzm;
        String[] packagesForUid = Wrappers.packageManager(this.mContext).getPackagesForUid(i);
        if (packagesForUid != null) {
            if (packagesForUid.length != 0) {
                com_google_android_gms_common_zzm = null;
                for (String zza : packagesForUid) {
                    com_google_android_gms_common_zzm = zza(zza, i);
                    if (com_google_android_gms_common_zzm.zzac) {
                        break;
                    }
                }
                com_google_android_gms_common_zzm.zzf();
                return com_google_android_gms_common_zzm.zzac;
            }
        }
        com_google_android_gms_common_zzm = zzm.zzb("no pkgs");
        com_google_android_gms_common_zzm.zzf();
        return com_google_android_gms_common_zzm.zzac;
    }

    @ShowFirstParty
    @KeepForSdk
    public boolean isPackageGoogleSigned(String str) {
        str = zzc(str);
        str.zzf();
        return str.zzac;
    }

    public static boolean zza(PackageInfo packageInfo, boolean z) {
        if (!(packageInfo == null || packageInfo.signatures == null)) {
            if (z) {
                packageInfo = zza(packageInfo, zzh.zzx);
            } else {
                packageInfo = zza(packageInfo, zzh.zzx[0]);
            }
            if (packageInfo != null) {
                return true;
            }
        }
        return false;
    }

    @KeepForSdk
    public boolean isGooglePublicSignedPackage(PackageInfo packageInfo) {
        if (packageInfo == null) {
            return false;
        }
        if (zza(packageInfo, false)) {
            return true;
        }
        if (zza(packageInfo, true) != null) {
            if (GooglePlayServicesUtilLight.honorsDebugCertificates(this.mContext) != null) {
                return true;
            }
            Log.w("GoogleSignatureVerifier", "Test-keys aren't accepted on this build.");
        }
        return false;
    }

    private final zzm zza(PackageInfo packageInfo) {
        boolean honorsDebugCertificates = GooglePlayServicesUtilLight.honorsDebugCertificates(this.mContext);
        if (packageInfo == null) {
            return zzm.zzb("null pkg");
        }
        if (packageInfo.signatures.length != 1) {
            return zzm.zzb("single cert required");
        }
        zze com_google_android_gms_common_zzf = new zzf(packageInfo.signatures[0].toByteArray());
        String str = packageInfo.packageName;
        zzm zza = zzc.zza(str, com_google_android_gms_common_zzf, honorsDebugCertificates);
        return (!zza.zzac || packageInfo.applicationInfo == null || (packageInfo.applicationInfo.flags & 2) == null || (honorsDebugCertificates && zzc.zza(str, com_google_android_gms_common_zzf, false).zzac == null)) ? zza : zzm.zzb("debuggable release cert app rejected");
    }

    private static zze zza(PackageInfo packageInfo, zze... com_google_android_gms_common_zzeArr) {
        if (packageInfo.signatures == null) {
            return null;
        }
        if (packageInfo.signatures.length != 1) {
            Log.w("GoogleSignatureVerifier", "Package has more than one signature.");
            return null;
        }
        int i = 0;
        zzf com_google_android_gms_common_zzf = new zzf(packageInfo.signatures[0].toByteArray());
        while (i < com_google_android_gms_common_zzeArr.length) {
            if (com_google_android_gms_common_zzeArr[i].equals(com_google_android_gms_common_zzf) != null) {
                return com_google_android_gms_common_zzeArr[i];
            }
            i++;
        }
        return null;
    }
}
