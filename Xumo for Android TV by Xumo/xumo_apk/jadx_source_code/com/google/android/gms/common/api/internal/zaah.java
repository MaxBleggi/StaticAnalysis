package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl;

public final class zaah implements zabd {
    private final zabe zafs;
    private boolean zaft = false;

    public zaah(zabe com_google_android_gms_common_api_internal_zabe) {
        this.zafs = com_google_android_gms_common_api_internal_zabe;
    }

    public final void begin() {
    }

    public final void onConnected(Bundle bundle) {
    }

    public final void zaa(ConnectionResult connectionResult, Api<?> api, boolean z) {
    }

    public final <A extends AnyClient, R extends Result, T extends ApiMethodImpl<R, A>> T enqueue(T t) {
        return execute(t);
    }

    public final <A extends com.google.android.gms.common.api.Api.AnyClient, T extends com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl<? extends com.google.android.gms.common.api.Result, A>> T execute(T r4) {
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
        r0 = r3.zafs;	 Catch:{ DeadObjectException -> 0x004b }
        r0 = r0.zaed;	 Catch:{ DeadObjectException -> 0x004b }
        r0 = r0.zahe;	 Catch:{ DeadObjectException -> 0x004b }
        r0.zab(r4);	 Catch:{ DeadObjectException -> 0x004b }
        r0 = r3.zafs;	 Catch:{ DeadObjectException -> 0x004b }
        r0 = r0.zaed;	 Catch:{ DeadObjectException -> 0x004b }
        r1 = r4.getClientKey();	 Catch:{ DeadObjectException -> 0x004b }
        r0 = r0.zagy;	 Catch:{ DeadObjectException -> 0x004b }
        r0 = r0.get(r1);	 Catch:{ DeadObjectException -> 0x004b }
        r0 = (com.google.android.gms.common.api.Api.Client) r0;	 Catch:{ DeadObjectException -> 0x004b }
        r1 = "Appropriate Api was not requested.";	 Catch:{ DeadObjectException -> 0x004b }
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r0, r1);	 Catch:{ DeadObjectException -> 0x004b }
        r1 = r0.isConnected();	 Catch:{ DeadObjectException -> 0x004b }
        if (r1 != 0) goto L_0x003d;	 Catch:{ DeadObjectException -> 0x004b }
    L_0x0024:
        r1 = r3.zafs;	 Catch:{ DeadObjectException -> 0x004b }
        r1 = r1.zaho;	 Catch:{ DeadObjectException -> 0x004b }
        r2 = r4.getClientKey();	 Catch:{ DeadObjectException -> 0x004b }
        r1 = r1.containsKey(r2);	 Catch:{ DeadObjectException -> 0x004b }
        if (r1 == 0) goto L_0x003d;	 Catch:{ DeadObjectException -> 0x004b }
    L_0x0032:
        r0 = new com.google.android.gms.common.api.Status;	 Catch:{ DeadObjectException -> 0x004b }
        r1 = 17;	 Catch:{ DeadObjectException -> 0x004b }
        r0.<init>(r1);	 Catch:{ DeadObjectException -> 0x004b }
        r4.setFailedResult(r0);	 Catch:{ DeadObjectException -> 0x004b }
        goto L_0x0055;	 Catch:{ DeadObjectException -> 0x004b }
    L_0x003d:
        r1 = r0 instanceof com.google.android.gms.common.internal.SimpleClientAdapter;	 Catch:{ DeadObjectException -> 0x004b }
        if (r1 == 0) goto L_0x0047;	 Catch:{ DeadObjectException -> 0x004b }
    L_0x0041:
        r0 = (com.google.android.gms.common.internal.SimpleClientAdapter) r0;	 Catch:{ DeadObjectException -> 0x004b }
        r0 = r0.getClient();	 Catch:{ DeadObjectException -> 0x004b }
    L_0x0047:
        r4.run(r0);	 Catch:{ DeadObjectException -> 0x004b }
        goto L_0x0055;
    L_0x004b:
        r0 = r3.zafs;
        r1 = new com.google.android.gms.common.api.internal.zaai;
        r1.<init>(r3, r3);
        r0.zaa(r1);
    L_0x0055:
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zaah.execute(com.google.android.gms.common.api.internal.BaseImplementation$ApiMethodImpl):T");
    }

    public final boolean disconnect() {
        if (this.zaft) {
            return false;
        }
        if (this.zafs.zaed.zaax()) {
            this.zaft = true;
            for (zacm zabv : this.zafs.zaed.zahd) {
                zabv.zabv();
            }
            return false;
        }
        this.zafs.zaf(null);
        return true;
    }

    public final void connect() {
        if (this.zaft) {
            this.zaft = false;
            this.zafs.zaa(new zaaj(this, this));
        }
    }

    public final void onConnectionSuspended(int i) {
        this.zafs.zaf(null);
        this.zafs.zahs.zab(i, this.zaft);
    }

    final void zaam() {
        if (this.zaft) {
            this.zaft = false;
            this.zafs.zaed.zahe.release();
            disconnect();
        }
    }
}
