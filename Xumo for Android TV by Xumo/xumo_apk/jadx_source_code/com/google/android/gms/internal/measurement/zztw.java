package com.google.android.gms.internal.measurement;

import android.content.Context;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

final class zztw extends zztt {
    private final TaskCompletionSource<PendingDynamicLinkData> zzbuh;
    private final Context zzwq;

    public zztw(Context context, TaskCompletionSource<PendingDynamicLinkData> taskCompletionSource) {
        this.zzwq = context;
        this.zzbuh = taskCompletionSource;
    }

    public final void zza(com.google.android.gms.common.api.Status r5, com.google.android.gms.internal.measurement.zztn r6) {
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
        r4 = this;
        if (r6 != 0) goto L_0x0004;
    L_0x0002:
        r0 = 0;
        goto L_0x0009;
    L_0x0004:
        r0 = new com.google.firebase.dynamiclinks.PendingDynamicLinkData;
        r0.<init>(r6);
    L_0x0009:
        r1 = r4.zzbuh;
        com.google.android.gms.common.api.internal.TaskUtil.setResultOrApiException(r5, r0, r1);
        if (r6 != 0) goto L_0x0011;
    L_0x0010:
        return;
    L_0x0011:
        r5 = r6.zzub();
        r6 = "scionData";
        r5 = r5.getBundle(r6);
        if (r5 == 0) goto L_0x004a;
    L_0x001d:
        r6 = r5.keySet();
        if (r6 != 0) goto L_0x0024;
    L_0x0023:
        goto L_0x004a;
    L_0x0024:
        r6 = r4.zzwq;	 Catch:{ NoClassDefFoundError -> 0x0049 }
        r6 = com.google.android.gms.measurement.AppMeasurement.getInstance(r6);	 Catch:{ NoClassDefFoundError -> 0x0049 }
        r0 = r5.keySet();	 Catch:{ NoClassDefFoundError -> 0x0049 }
        r0 = r0.iterator();	 Catch:{ NoClassDefFoundError -> 0x0049 }
    L_0x0032:
        r1 = r0.hasNext();	 Catch:{ NoClassDefFoundError -> 0x0049 }
        if (r1 == 0) goto L_0x0048;	 Catch:{ NoClassDefFoundError -> 0x0049 }
    L_0x0038:
        r1 = r0.next();	 Catch:{ NoClassDefFoundError -> 0x0049 }
        r1 = (java.lang.String) r1;	 Catch:{ NoClassDefFoundError -> 0x0049 }
        r2 = r5.getBundle(r1);	 Catch:{ NoClassDefFoundError -> 0x0049 }
        r3 = "fdl";	 Catch:{ NoClassDefFoundError -> 0x0049 }
        r6.logEventInternal(r3, r1, r2);	 Catch:{ NoClassDefFoundError -> 0x0049 }
        goto L_0x0032;
    L_0x0048:
        return;
    L_0x0049:
        return;
    L_0x004a:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zztw.zza(com.google.android.gms.common.api.Status, com.google.android.gms.internal.measurement.zztn):void");
    }
}
