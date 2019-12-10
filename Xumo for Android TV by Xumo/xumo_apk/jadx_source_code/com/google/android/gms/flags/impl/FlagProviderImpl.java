package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.flags.zzd;

@DynamiteApi
public class FlagProviderImpl extends zzd {
    private boolean zzu = false;
    private SharedPreferences zzv;

    public void init(com.google.android.gms.dynamic.IObjectWrapper r4) {
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
        r3 = this;
        r4 = com.google.android.gms.dynamic.ObjectWrapper.unwrap(r4);
        r4 = (android.content.Context) r4;
        r0 = r3.zzu;
        if (r0 == 0) goto L_0x000b;
    L_0x000a:
        return;
    L_0x000b:
        r0 = "com.google.android.gms";	 Catch:{ NameNotFoundException -> 0x003d, Exception -> 0x001c }
        r1 = 0;	 Catch:{ NameNotFoundException -> 0x003d, Exception -> 0x001c }
        r4 = r4.createPackageContext(r0, r1);	 Catch:{ NameNotFoundException -> 0x003d, Exception -> 0x001c }
        r4 = com.google.android.gms.flags.impl.zzj.zza(r4);	 Catch:{ NameNotFoundException -> 0x003d, Exception -> 0x001c }
        r3.zzv = r4;	 Catch:{ NameNotFoundException -> 0x003d, Exception -> 0x001c }
        r4 = 1;	 Catch:{ NameNotFoundException -> 0x003d, Exception -> 0x001c }
        r3.zzu = r4;	 Catch:{ NameNotFoundException -> 0x003d, Exception -> 0x001c }
        return;
    L_0x001c:
        r4 = move-exception;
        r0 = "FlagProviderImpl";
        r1 = "Could not retrieve sdk flags, continuing with defaults: ";
        r4 = r4.getMessage();
        r4 = java.lang.String.valueOf(r4);
        r2 = r4.length();
        if (r2 == 0) goto L_0x0034;
    L_0x002f:
        r4 = r1.concat(r4);
        goto L_0x0039;
    L_0x0034:
        r4 = new java.lang.String;
        r4.<init>(r1);
    L_0x0039:
        android.util.Log.w(r0, r4);
        return;
    L_0x003d:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.flags.impl.FlagProviderImpl.init(com.google.android.gms.dynamic.IObjectWrapper):void");
    }

    public boolean getBooleanFlagValue(String str, boolean z, int i) {
        if (this.zzu == 0) {
            return z;
        }
        return zzb.zza(this.zzv, str, Boolean.valueOf(z)).booleanValue();
    }

    public int getIntFlagValue(String str, int i, int i2) {
        if (this.zzu == 0) {
            return i;
        }
        return zzd.zza(this.zzv, str, Integer.valueOf(i)).intValue();
    }

    public long getLongFlagValue(String str, long j, int i) {
        if (this.zzu == 0) {
            return j;
        }
        return zzf.zza(this.zzv, str, Long.valueOf(j)).longValue();
    }

    public String getStringFlagValue(String str, String str2, int i) {
        if (this.zzu == 0) {
            return str2;
        }
        return zzh.zza(this.zzv, str, str2);
    }
}
