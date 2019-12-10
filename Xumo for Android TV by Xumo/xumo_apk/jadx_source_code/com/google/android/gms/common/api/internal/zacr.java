package com.google.android.gms.common.api.internal;

import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import com.google.android.gms.common.api.zac;
import java.lang.ref.WeakReference;

final class zacr implements DeathRecipient, zacs {
    private final WeakReference<BasePendingResult<?>> zalb;
    private final WeakReference<zac> zalc;
    private final WeakReference<IBinder> zald;

    private zacr(BasePendingResult<?> basePendingResult, zac com_google_android_gms_common_api_zac, IBinder iBinder) {
        this.zalc = new WeakReference(com_google_android_gms_common_api_zac);
        this.zalb = new WeakReference(basePendingResult);
        this.zald = new WeakReference(iBinder);
    }

    public final void zac(BasePendingResult<?> basePendingResult) {
        zaby();
    }

    public final void binderDied() {
        zaby();
    }

    private final void zaby() {
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
        r2 = this;
        r0 = r2.zalb;
        r0 = r0.get();
        r0 = (com.google.android.gms.common.api.internal.BasePendingResult) r0;
        r1 = r2.zalc;
        r1 = r1.get();
        r1 = (com.google.android.gms.common.api.zac) r1;
        if (r1 == 0) goto L_0x001f;
    L_0x0012:
        if (r0 == 0) goto L_0x001f;
    L_0x0014:
        r0 = r0.zam();
        r0 = r0.intValue();
        r1.remove(r0);
    L_0x001f:
        r0 = r2.zald;
        r0 = r0.get();
        r0 = (android.os.IBinder) r0;
        if (r0 == 0) goto L_0x002e;
    L_0x0029:
        r1 = 0;
        r0.unlinkToDeath(r2, r1);	 Catch:{ NoSuchElementException -> 0x002e }
        return;
    L_0x002e:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zacr.zaby():void");
    }
}
