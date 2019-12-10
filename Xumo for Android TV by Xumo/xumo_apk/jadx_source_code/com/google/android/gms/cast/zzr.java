package com.google.android.gms.cast;

import android.os.RemoteException;
import android.view.Display;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.internal.cast.zzeb;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzr extends zza {
    private final /* synthetic */ TaskCompletionSource zzbk;
    private final /* synthetic */ zzeb zzbl;
    private final /* synthetic */ zzq zzbm;

    zzr(zzq com_google_android_gms_cast_zzq, TaskCompletionSource taskCompletionSource, zzeb com_google_android_gms_internal_cast_zzeb) {
        this.zzbm = com_google_android_gms_cast_zzq;
        this.zzbk = taskCompletionSource;
        this.zzbl = com_google_android_gms_internal_cast_zzeb;
        super();
    }

    public final void zza(int r11, int r12, android.view.Surface r13) throws android.os.RemoteException {
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
        r10 = this;
        r0 = r10.zzbm;
        r0 = r0.zzbj;
        r0 = r0.zzbe;
        r1 = "onConnected";
        r2 = 0;
        r3 = new java.lang.Object[r2];
        r0.d(r1, r3);
        r0 = r10.zzbm;
        r0 = r0.zzbj;
        r0 = r0.getApplicationContext();
        r1 = "display";
        r0 = r0.getSystemService(r1);
        r3 = r0;
        r3 = (android.hardware.display.DisplayManager) r3;
        r0 = 0;
        if (r3 != 0) goto L_0x003b;
    L_0x0024:
        r11 = r10.zzbm;
        r11 = r11.zzbj;
        r11 = r11.zzbe;
        r12 = "Unable to get the display manager";
        r13 = new java.lang.Object[r2];
        r11.e(r12, r13);
        r11 = com.google.android.gms.common.api.Status.RESULT_INTERNAL_ERROR;
        r12 = r10.zzbk;
        com.google.android.gms.common.api.internal.TaskUtil.setResultOrApiException(r11, r0, r12);
        return;
    L_0x003b:
        r1 = r10.zzbm;
        r1 = r1.zzbj;
        r1.zzb();
        r1 = r10.zzbm;
        r1 = r1.zzbj;
        r7 = com.google.android.gms.cast.CastRemoteDisplayClient.zza(r11, r12);
        r1 = r10.zzbm;
        r1 = r1.zzbj;
        r4 = "private_display";
        r9 = 2;
        r5 = r11;
        r6 = r12;
        r8 = r13;
        r11 = r3.createVirtualDisplay(r4, r5, r6, r7, r8, r9);
        r1.zzbf = r11;
        r11 = r10.zzbm;
        r11 = r11.zzbj;
        r11 = r11.zzbf;
        if (r11 != 0) goto L_0x007c;
    L_0x0065:
        r11 = r10.zzbm;
        r11 = r11.zzbj;
        r11 = r11.zzbe;
        r12 = "Unable to create virtual display";
        r13 = new java.lang.Object[r2];
        r11.e(r12, r13);
        r11 = com.google.android.gms.common.api.Status.RESULT_INTERNAL_ERROR;
        r12 = r10.zzbk;
        com.google.android.gms.common.api.internal.TaskUtil.setResultOrApiException(r11, r0, r12);
        return;
    L_0x007c:
        r11 = r10.zzbm;
        r11 = r11.zzbj;
        r11 = r11.zzbf;
        r11 = r11.getDisplay();
        if (r11 != 0) goto L_0x00a1;
    L_0x008a:
        r11 = r10.zzbm;
        r11 = r11.zzbj;
        r11 = r11.zzbe;
        r12 = "Virtual display does not have a display";
        r13 = new java.lang.Object[r2];
        r11.e(r12, r13);
        r11 = com.google.android.gms.common.api.Status.RESULT_INTERNAL_ERROR;
        r12 = r10.zzbk;
        com.google.android.gms.common.api.internal.TaskUtil.setResultOrApiException(r11, r0, r12);
        return;
    L_0x00a1:
        r12 = r10.zzbl;	 Catch:{ RemoteException -> 0x00b1, RemoteException -> 0x00b1 }
        r12 = r12.getService();	 Catch:{ RemoteException -> 0x00b1, RemoteException -> 0x00b1 }
        r12 = (com.google.android.gms.internal.cast.zzeg) r12;	 Catch:{ RemoteException -> 0x00b1, RemoteException -> 0x00b1 }
        r11 = r11.getDisplayId();	 Catch:{ RemoteException -> 0x00b1, RemoteException -> 0x00b1 }
        r12.zza(r10, r11);	 Catch:{ RemoteException -> 0x00b1, RemoteException -> 0x00b1 }
        return;
    L_0x00b1:
        r11 = r10.zzbm;
        r11 = r11.zzbj;
        r11 = r11.zzbe;
        r12 = "Unable to provision the route's new virtual Display";
        r13 = new java.lang.Object[r2];
        r11.e(r12, r13);
        r11 = com.google.android.gms.common.api.Status.RESULT_INTERNAL_ERROR;
        r12 = r10.zzbk;
        com.google.android.gms.common.api.internal.TaskUtil.setResultOrApiException(r11, r0, r12);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.zzr.zza(int, int, android.view.Surface):void");
    }

    public final void zzc() {
        this.zzbm.zzbj.zzbe.d("onConnectedWithDisplay", new Object[0]);
        if (this.zzbm.zzbj.zzbf == null) {
            this.zzbm.zzbj.zzbe.e("There is no virtual display", new Object[0]);
            TaskUtil.setResultOrApiException(Status.RESULT_INTERNAL_ERROR, null, this.zzbk);
            return;
        }
        Display display = this.zzbm.zzbj.zzbf.getDisplay();
        if (display != null) {
            TaskUtil.setResultOrApiException(Status.RESULT_SUCCESS, display, this.zzbk);
            return;
        }
        this.zzbm.zzbj.zzbe.e("Virtual display no longer has a display", new Object[0]);
        TaskUtil.setResultOrApiException(Status.RESULT_INTERNAL_ERROR, null, this.zzbk);
    }

    public final void onError(int i) throws RemoteException {
        this.zzbm.zzbj.zzbe.d("onError: %d", Integer.valueOf(i));
        this.zzbm.zzbj.zzb();
        TaskUtil.setResultOrApiException(Status.RESULT_INTERNAL_ERROR, null, this.zzbk);
    }
}
