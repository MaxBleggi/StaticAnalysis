package com.google.firebase.dynamiclinks;

import android.net.Uri;
import android.os.Bundle;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.measurement.zztn;

public class PendingDynamicLinkData {
    private final zztn zzbtz;

    @VisibleForTesting
    public PendingDynamicLinkData(zztn com_google_android_gms_internal_measurement_zztn) {
        if (com_google_android_gms_internal_measurement_zztn == null) {
            this.zzbtz = null;
            return;
        }
        if (com_google_android_gms_internal_measurement_zztn.getClickTimestamp() == 0) {
            com_google_android_gms_internal_measurement_zztn.zzax(DefaultClock.getInstance().currentTimeMillis());
        }
        this.zzbtz = com_google_android_gms_internal_measurement_zztn;
    }

    protected PendingDynamicLinkData(String str, int i, long j, Uri uri) {
        this.zzbtz = new zztn(null, str, i, j, null, uri);
    }

    @ShowFirstParty
    public final Bundle zztx() {
        if (this.zzbtz == null) {
            return new Bundle();
        }
        return this.zzbtz.zzub();
    }

    public Uri getLink() {
        if (this.zzbtz == null) {
            return null;
        }
        String zztz = this.zzbtz.zztz();
        if (zztz != null) {
            return Uri.parse(zztz);
        }
        return null;
    }

    public int getMinimumAppVersion() {
        if (this.zzbtz == null) {
            return 0;
        }
        return this.zzbtz.zzua();
    }

    public long getClickTimestamp() {
        if (this.zzbtz == null) {
            return 0;
        }
        return this.zzbtz.getClickTimestamp();
    }

    @VisibleForTesting
    private final Uri zzty() {
        if (this.zzbtz == null) {
            return null;
        }
        return this.zzbtz.zzty();
    }

    public android.content.Intent getUpdateAppIntent(android.content.Context r4) {
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
        r0 = r3.getMinimumAppVersion();
        r1 = 0;
        if (r0 != 0) goto L_0x0008;
    L_0x0007:
        return r1;
    L_0x0008:
        r0 = r4.getPackageManager();	 Catch:{ NameNotFoundException -> 0x003e }
        r4 = r4.getApplicationContext();	 Catch:{ NameNotFoundException -> 0x003e }
        r4 = r4.getPackageName();	 Catch:{ NameNotFoundException -> 0x003e }
        r2 = 0;	 Catch:{ NameNotFoundException -> 0x003e }
        r4 = r0.getPackageInfo(r4, r2);	 Catch:{ NameNotFoundException -> 0x003e }
        r4 = r4.versionCode;	 Catch:{ NameNotFoundException -> 0x003e }
        r0 = r3.getMinimumAppVersion();
        if (r4 >= r0) goto L_0x003d;
    L_0x0021:
        r4 = r3.zzty();
        if (r4 == 0) goto L_0x003d;
    L_0x0027:
        r4 = new android.content.Intent;
        r0 = "android.intent.action.VIEW";
        r4.<init>(r0);
        r0 = r3.zzty();
        r4 = r4.setData(r0);
        r0 = "com.android.vending";
        r4 = r4.setPackage(r0);
        return r4;
    L_0x003d:
        return r1;
    L_0x003e:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.dynamiclinks.PendingDynamicLinkData.getUpdateAppIntent(android.content.Context):android.content.Intent");
    }
}
