package com.google.android.gms.measurement.internal;

import android.content.BroadcastReceiver.PendingResult;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.MainThread;
import com.google.android.gms.common.internal.Preconditions;

public final class zzbk {
    private final zzbn zzaoo;

    public zzbk(zzbn com_google_android_gms_measurement_internal_zzbn) {
        Preconditions.checkNotNull(com_google_android_gms_measurement_internal_zzbn);
        this.zzaoo = com_google_android_gms_measurement_internal_zzbn;
    }

    public static boolean zza(android.content.Context r4) {
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
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r4);
        r0 = 0;
        r1 = r4.getPackageManager();	 Catch:{ NameNotFoundException -> 0x001e }
        if (r1 != 0) goto L_0x000b;	 Catch:{ NameNotFoundException -> 0x001e }
    L_0x000a:
        return r0;	 Catch:{ NameNotFoundException -> 0x001e }
    L_0x000b:
        r2 = new android.content.ComponentName;	 Catch:{ NameNotFoundException -> 0x001e }
        r3 = "com.google.android.gms.measurement.AppMeasurementReceiver";	 Catch:{ NameNotFoundException -> 0x001e }
        r2.<init>(r4, r3);	 Catch:{ NameNotFoundException -> 0x001e }
        r4 = r1.getReceiverInfo(r2, r0);	 Catch:{ NameNotFoundException -> 0x001e }
        if (r4 == 0) goto L_0x001e;	 Catch:{ NameNotFoundException -> 0x001e }
    L_0x0018:
        r4 = r4.enabled;	 Catch:{ NameNotFoundException -> 0x001e }
        if (r4 == 0) goto L_0x001e;
    L_0x001c:
        r4 = 1;
        return r4;
    L_0x001e:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzbk.zza(android.content.Context):boolean");
    }

    @MainThread
    public final void onReceive(Context context, Intent intent) {
        zzbu zza = zzbu.zza(context, null);
        zzaq zzgt = zza.zzgt();
        if (intent == null) {
            zzgt.zzjj().zzca("Receiver called with null intent");
            return;
        }
        zza.zzgw();
        String action = intent.getAction();
        zzgt.zzjo().zzg("Local receiver got", action);
        if ("com.google.android.gms.measurement.UPLOAD".equals(action)) {
            intent = new Intent().setClassName(context, "com.google.android.gms.measurement.AppMeasurementService");
            intent.setAction("com.google.android.gms.measurement.UPLOAD");
            zzgt.zzjo().zzca("Starting wakeful intent.");
            this.zzaoo.doStartService(context, intent);
            return;
        }
        if ("com.android.vending.INSTALL_REFERRER".equals(action)) {
            try {
                zza.zzgs().zzc(new zzbl(this, zza, zzgt));
            } catch (Exception e) {
                zzgt.zzjj().zzg("Install Referrer Reporter encountered a problem", e);
            }
            PendingResult doGoAsync = this.zzaoo.doGoAsync();
            action = intent.getStringExtra("referrer");
            if (action == null) {
                zzgt.zzjo().zzca("Install referrer extras are null");
                if (doGoAsync != null) {
                    doGoAsync.finish();
                }
                return;
            }
            zzgt.zzjm().zzg("Install referrer extras are", action);
            if (!action.contains("?")) {
                String str = "?";
                action = String.valueOf(action);
                action = action.length() != 0 ? str.concat(action) : new String(str);
            }
            Bundle zza2 = zza.zzgr().zza(Uri.parse(action));
            if (zza2 == null) {
                zzgt.zzjo().zzca("No campaign defined in install referrer broadcast");
                if (doGoAsync != null) {
                    doGoAsync.finish();
                    return;
                }
            }
            long longExtra = intent.getLongExtra("referrer_timestamp_seconds", 0) * 1000;
            if (longExtra == 0) {
                zzgt.zzjj().zzca("Install referrer is missing timestamp");
            }
            zza.zzgs().zzc(new zzbm(this, zza, longExtra, zza2, context, zzgt, doGoAsync));
        }
    }
}
