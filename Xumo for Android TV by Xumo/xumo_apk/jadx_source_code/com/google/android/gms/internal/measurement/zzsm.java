package com.google.android.gms.internal.measurement;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.GuardedBy;
import androidx.collection.ArrayMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class zzsm implements zzsq {
    @GuardedBy("ConfigurationContentLoader.class")
    static final Map<Uri, zzsm> zzbrm = new ArrayMap();
    private static final String[] zzbrr = new String[]{"key", "value"};
    private final Uri uri;
    private final ContentResolver zzbrn;
    private final Object zzbro = new Object();
    private volatile Map<String, String> zzbrp;
    @GuardedBy("this")
    private final List<zzsp> zzbrq = new ArrayList();

    private zzsm(ContentResolver contentResolver, Uri uri) {
        this.zzbrn = contentResolver;
        this.uri = uri;
        this.zzbrn.registerContentObserver(uri, false, new zzso(this, null));
    }

    public static com.google.android.gms.internal.measurement.zzsm zza(android.content.ContentResolver r3, android.net.Uri r4) {
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
        r0 = com.google.android.gms.internal.measurement.zzsm.class;
        monitor-enter(r0);
        r1 = zzbrm;	 Catch:{ all -> 0x001a }
        r1 = r1.get(r4);	 Catch:{ all -> 0x001a }
        r1 = (com.google.android.gms.internal.measurement.zzsm) r1;	 Catch:{ all -> 0x001a }
        if (r1 != 0) goto L_0x0018;
    L_0x000d:
        r2 = new com.google.android.gms.internal.measurement.zzsm;	 Catch:{ SecurityException -> 0x0018 }
        r2.<init>(r3, r4);	 Catch:{ SecurityException -> 0x0018 }
        r3 = zzbrm;	 Catch:{ SecurityException -> 0x0017 }
        r3.put(r4, r2);	 Catch:{ SecurityException -> 0x0017 }
    L_0x0017:
        r1 = r2;
    L_0x0018:
        monitor-exit(r0);	 Catch:{ all -> 0x001a }
        return r1;	 Catch:{ all -> 0x001a }
    L_0x001a:
        r3 = move-exception;	 Catch:{ all -> 0x001a }
        monitor-exit(r0);	 Catch:{ all -> 0x001a }
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzsm.zza(android.content.ContentResolver, android.net.Uri):com.google.android.gms.internal.measurement.zzsm");
    }

    public final Map<String, String> zztk() {
        Map<String, String> map = this.zzbrp;
        if (map == null) {
            synchronized (this.zzbro) {
                map = this.zzbrp;
                if (map == null) {
                    map = zztm();
                    this.zzbrp = map;
                }
            }
        }
        if (map != null) {
            return map;
        }
        return Collections.emptyMap();
    }

    public final void zztl() {
        synchronized (this.zzbro) {
            this.zzbrp = null;
            zzsx.zztq();
        }
        synchronized (this) {
            for (zzsp zztp : this.zzbrq) {
                zztp.zztp();
            }
        }
    }

    private final java.util.Map<java.lang.String, java.lang.String> zztm() {
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
        r2 = this;
        r0 = new com.google.android.gms.internal.measurement.zzsn;	 Catch:{ SecurityException -> 0x000c, SecurityException -> 0x000c }
        r0.<init>(r2);	 Catch:{ SecurityException -> 0x000c, SecurityException -> 0x000c }
        r0 = com.google.android.gms.internal.measurement.zzsr.zza(r0);	 Catch:{ SecurityException -> 0x000c, SecurityException -> 0x000c }
        r0 = (java.util.Map) r0;	 Catch:{ SecurityException -> 0x000c, SecurityException -> 0x000c }
        return r0;
    L_0x000c:
        r0 = "ConfigurationContentLoader";
        r1 = "PhenotypeFlag unable to load ContentProvider, using default values";
        android.util.Log.e(r0, r1);
        r0 = 0;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzsm.zztm():java.util.Map<java.lang.String, java.lang.String>");
    }

    public final /* synthetic */ Object zzfp(String str) {
        return (String) zztk().get(str);
    }

    final /* synthetic */ Map zztn() {
        Cursor query = this.zzbrn.query(this.uri, zzbrr, null, null, null);
        if (query == null) {
            return Collections.emptyMap();
        }
        try {
            int count = query.getCount();
            if (count == 0) {
                Map emptyMap = Collections.emptyMap();
                return emptyMap;
            }
            Map arrayMap;
            if (count <= 256) {
                arrayMap = new ArrayMap(count);
            } else {
                arrayMap = new HashMap(count, 1.0f);
            }
            while (query.moveToNext()) {
                arrayMap.put(query.getString(0), query.getString(1));
            }
            query.close();
            return arrayMap;
        } finally {
            query.close();
        }
    }
}
