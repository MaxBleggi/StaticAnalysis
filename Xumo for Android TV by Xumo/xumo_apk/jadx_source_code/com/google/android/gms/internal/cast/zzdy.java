package com.google.android.gms.internal.cast;

import android.annotation.TargetApi;
import android.os.RemoteException;
import android.view.Display;
import com.google.android.gms.common.api.Status;

@TargetApi(19)
public final class zzdy extends zzdw {
    private final zzec zzzi;
    private final /* synthetic */ zzdx zzzj;

    public zzdy(zzdx com_google_android_gms_internal_cast_zzdx, zzec com_google_android_gms_internal_cast_zzec) {
        this.zzzj = com_google_android_gms_internal_cast_zzdx;
        this.zzzi = com_google_android_gms_internal_cast_zzec;
    }

    public final void zza(int r11, int r12, android.view.Surface r13) {
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
        r0 = com.google.android.gms.internal.cast.zzds.zzbe;
        r1 = "onConnected";
        r2 = 0;
        r3 = new java.lang.Object[r2];
        r0.d(r1, r3);
        r0 = r10.zzzi;
        r0 = r0.getContext();
        r1 = "display";
        r0 = r0.getSystemService(r1);
        r3 = r0;
        r3 = (android.hardware.display.DisplayManager) r3;
        if (r3 != 0) goto L_0x0035;
    L_0x001d:
        r11 = com.google.android.gms.internal.cast.zzds.zzbe;
        r12 = "Unable to get the display manager";
        r13 = new java.lang.Object[r2];
        r11.e(r12, r13);
        r11 = r10.zzzj;
        r12 = new com.google.android.gms.internal.cast.zzea;
        r13 = com.google.android.gms.common.api.Status.RESULT_INTERNAL_ERROR;
        r12.<init>(r13);
        r11.setResult(r12);
        return;
    L_0x0035:
        r0 = r10.zzzj;
        r0 = r0.zzzg;
        r0.zzb();
        if (r11 >= r12) goto L_0x0040;
    L_0x003e:
        r0 = r11;
        goto L_0x0041;
    L_0x0040:
        r0 = r12;
    L_0x0041:
        r0 = r0 * 320;
        r7 = r0 / 1080;
        r0 = r10.zzzj;
        r0 = r0.zzzg;
        r4 = "private_display";
        r9 = 2;
        r5 = r11;
        r6 = r12;
        r8 = r13;
        r11 = r3.createVirtualDisplay(r4, r5, r6, r7, r8, r9);
        r0.zzbf = r11;
        r11 = r10.zzzj;
        r11 = r11.zzzg;
        r11 = r11.zzbf;
        if (r11 != 0) goto L_0x0078;
    L_0x0060:
        r11 = com.google.android.gms.internal.cast.zzds.zzbe;
        r12 = "Unable to create virtual display";
        r13 = new java.lang.Object[r2];
        r11.e(r12, r13);
        r11 = r10.zzzj;
        r12 = new com.google.android.gms.internal.cast.zzea;
        r13 = com.google.android.gms.common.api.Status.RESULT_INTERNAL_ERROR;
        r12.<init>(r13);
        r11.setResult(r12);
        return;
    L_0x0078:
        r11 = r10.zzzj;
        r11 = r11.zzzg;
        r11 = r11.zzbf;
        r11 = r11.getDisplay();
        if (r11 != 0) goto L_0x009e;
    L_0x0086:
        r11 = com.google.android.gms.internal.cast.zzds.zzbe;
        r12 = "Virtual display does not have a display";
        r13 = new java.lang.Object[r2];
        r11.e(r12, r13);
        r11 = r10.zzzj;
        r12 = new com.google.android.gms.internal.cast.zzea;
        r13 = com.google.android.gms.common.api.Status.RESULT_INTERNAL_ERROR;
        r12.<init>(r13);
        r11.setResult(r12);
        return;
    L_0x009e:
        r11 = r10.zzzi;	 Catch:{ RemoteException -> 0x00ba, RemoteException -> 0x00ba }
        r12 = r10.zzzj;	 Catch:{ RemoteException -> 0x00ba, RemoteException -> 0x00ba }
        r12 = r12.zzzg;	 Catch:{ RemoteException -> 0x00ba, RemoteException -> 0x00ba }
        r12 = r12.zzbf;	 Catch:{ RemoteException -> 0x00ba, RemoteException -> 0x00ba }
        r12 = r12.getDisplay();	 Catch:{ RemoteException -> 0x00ba, RemoteException -> 0x00ba }
        r12 = r12.getDisplayId();	 Catch:{ RemoteException -> 0x00ba, RemoteException -> 0x00ba }
        r11 = r11.getService();	 Catch:{ RemoteException -> 0x00ba, RemoteException -> 0x00ba }
        r11 = (com.google.android.gms.internal.cast.zzeg) r11;	 Catch:{ RemoteException -> 0x00ba, RemoteException -> 0x00ba }
        r11.zza(r10, r12);	 Catch:{ RemoteException -> 0x00ba, RemoteException -> 0x00ba }
        return;
    L_0x00ba:
        r11 = com.google.android.gms.internal.cast.zzds.zzbe;
        r12 = "Unable to provision the route's new virtual Display";
        r13 = new java.lang.Object[r2];
        r11.e(r12, r13);
        r11 = r10.zzzj;
        r12 = new com.google.android.gms.internal.cast.zzea;
        r13 = com.google.android.gms.common.api.Status.RESULT_INTERNAL_ERROR;
        r12.<init>(r13);
        r11.setResult(r12);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzdy.zza(int, int, android.view.Surface):void");
    }

    public final void zzc() {
        zzds.zzbe.d("onConnectedWithDisplay", new Object[0]);
        if (this.zzzj.zzzg.zzbf == null) {
            zzds.zzbe.e("There is no virtual display", new Object[0]);
            this.zzzj.setResult(new zzea(Status.RESULT_INTERNAL_ERROR));
            return;
        }
        Display display = this.zzzj.zzzg.zzbf.getDisplay();
        if (display != null) {
            this.zzzj.setResult(new zzea(display));
            return;
        }
        zzds.zzbe.e("Virtual display no longer has a display", new Object[0]);
        this.zzzj.setResult(new zzea(Status.RESULT_INTERNAL_ERROR));
    }

    public final void onError(int i) throws RemoteException {
        zzds.zzbe.d("onError: %d", Integer.valueOf(i));
        this.zzzj.zzzg.zzb();
        this.zzzj.setResult(new zzea(Status.RESULT_INTERNAL_ERROR));
    }
}
