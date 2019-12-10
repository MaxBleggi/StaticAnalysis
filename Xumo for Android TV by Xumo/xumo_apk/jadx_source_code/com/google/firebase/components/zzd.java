package com.google.firebase.components;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.firebase:firebase-common@@16.0.2 */
final class zzd implements zze<Context> {
    private zzd() {
    }

    public final /* synthetic */ List zza(Object obj) {
        obj = zza((Context) obj);
        if (obj == null) {
            Log.w("ComponentDiscovery", "Could not retrieve metadata, returning empty list of registrars.");
            return Collections.emptyList();
        }
        List arrayList = new ArrayList();
        for (String str : obj.keySet()) {
            if ("com.google.firebase.components.ComponentRegistrar".equals(obj.get(str)) && str.startsWith("com.google.firebase.components:")) {
                arrayList.add(str.substring(31));
            }
        }
        return arrayList;
    }

    private static android.os.Bundle zza(android.content.Context r4) {
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
        r0 = 0;
        r1 = r4.getPackageManager();	 Catch:{ NameNotFoundException -> 0x0029 }
        if (r1 != 0) goto L_0x000f;	 Catch:{ NameNotFoundException -> 0x0029 }
    L_0x0007:
        r4 = "ComponentDiscovery";	 Catch:{ NameNotFoundException -> 0x0029 }
        r1 = "Context has no PackageManager.";	 Catch:{ NameNotFoundException -> 0x0029 }
        android.util.Log.w(r4, r1);	 Catch:{ NameNotFoundException -> 0x0029 }
        return r0;	 Catch:{ NameNotFoundException -> 0x0029 }
    L_0x000f:
        r2 = new android.content.ComponentName;	 Catch:{ NameNotFoundException -> 0x0029 }
        r3 = com.google.firebase.components.ComponentDiscoveryService.class;	 Catch:{ NameNotFoundException -> 0x0029 }
        r2.<init>(r4, r3);	 Catch:{ NameNotFoundException -> 0x0029 }
        r4 = 128; // 0x80 float:1.794E-43 double:6.32E-322;	 Catch:{ NameNotFoundException -> 0x0029 }
        r4 = r1.getServiceInfo(r2, r4);	 Catch:{ NameNotFoundException -> 0x0029 }
        if (r4 != 0) goto L_0x0026;	 Catch:{ NameNotFoundException -> 0x0029 }
    L_0x001e:
        r4 = "ComponentDiscovery";	 Catch:{ NameNotFoundException -> 0x0029 }
        r1 = "ComponentDiscoveryService has no service info.";	 Catch:{ NameNotFoundException -> 0x0029 }
        android.util.Log.w(r4, r1);	 Catch:{ NameNotFoundException -> 0x0029 }
        return r0;	 Catch:{ NameNotFoundException -> 0x0029 }
    L_0x0026:
        r4 = r4.metaData;	 Catch:{ NameNotFoundException -> 0x0029 }
        return r4;
    L_0x0029:
        r4 = "ComponentDiscovery";
        r1 = "Application info not found.";
        android.util.Log.w(r4, r1);
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.components.zzd.zza(android.content.Context):android.os.Bundle");
    }
}
