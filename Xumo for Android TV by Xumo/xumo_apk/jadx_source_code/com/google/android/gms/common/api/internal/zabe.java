package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AbstractClientBuilder;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.Api.AnyClientKey;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.signin.zad;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

public final class zabe implements zabs, zar {
    private final Context mContext;
    private final AbstractClientBuilder<? extends zad, SignInOptions> zacd;
    final zaaw zaed;
    private final Lock zaen;
    private final ClientSettings zaes;
    private final Map<Api<?>, Boolean> zaev;
    private final GoogleApiAvailabilityLight zaex;
    final Map<AnyClientKey<?>, Client> zagy;
    private final Condition zahm;
    private final zabg zahn;
    final Map<AnyClientKey<?>, ConnectionResult> zaho = new HashMap();
    private volatile zabd zahp;
    private ConnectionResult zahq = null;
    int zahr;
    final zabt zahs;

    public zabe(Context context, zaaw com_google_android_gms_common_api_internal_zaaw, Lock lock, Looper looper, GoogleApiAvailabilityLight googleApiAvailabilityLight, Map<AnyClientKey<?>, Client> map, ClientSettings clientSettings, Map<Api<?>, Boolean> map2, AbstractClientBuilder<? extends zad, SignInOptions> abstractClientBuilder, ArrayList<zaq> arrayList, zabt com_google_android_gms_common_api_internal_zabt) {
        this.mContext = context;
        this.zaen = lock;
        this.zaex = googleApiAvailabilityLight;
        this.zagy = map;
        this.zaes = clientSettings;
        this.zaev = map2;
        this.zacd = abstractClientBuilder;
        this.zaed = com_google_android_gms_common_api_internal_zaaw;
        this.zahs = com_google_android_gms_common_api_internal_zabt;
        arrayList = arrayList;
        zaaw size = arrayList.size();
        com_google_android_gms_common_api_internal_zaaw = null;
        while (com_google_android_gms_common_api_internal_zaaw < size) {
            googleApiAvailabilityLight = arrayList.get(com_google_android_gms_common_api_internal_zaaw);
            com_google_android_gms_common_api_internal_zaaw++;
            ((zaq) googleApiAvailabilityLight).zaa(this);
        }
        this.zahn = new zabg(this, looper);
        this.zahm = lock.newCondition();
        this.zahp = new zaav(this);
    }

    public final boolean maybeSignIn(SignInConnectionListener signInConnectionListener) {
        return false;
    }

    public final void maybeSignOut() {
    }

    @GuardedBy("mLock")
    public final <A extends AnyClient, R extends Result, T extends ApiMethodImpl<R, A>> T enqueue(@NonNull T t) {
        t.zau();
        return this.zahp.enqueue(t);
    }

    @GuardedBy("mLock")
    public final <A extends AnyClient, T extends ApiMethodImpl<? extends Result, A>> T execute(@NonNull T t) {
        t.zau();
        return this.zahp.execute(t);
    }

    @GuardedBy("mLock")
    public final void connect() {
        this.zahp.connect();
    }

    @javax.annotation.concurrent.GuardedBy("mLock")
    public final com.google.android.gms.common.ConnectionResult blockingConnect() {
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
        r3 = this;
        r3.connect();
    L_0x0003:
        r0 = r3.isConnecting();
        r1 = 0;
        if (r0 == 0) goto L_0x001f;
    L_0x000a:
        r0 = r3.zahm;	 Catch:{ InterruptedException -> 0x0010 }
        r0.await();	 Catch:{ InterruptedException -> 0x0010 }
        goto L_0x0003;
    L_0x0010:
        r0 = java.lang.Thread.currentThread();
        r0.interrupt();
        r0 = new com.google.android.gms.common.ConnectionResult;
        r2 = 15;
        r0.<init>(r2, r1);
        return r0;
    L_0x001f:
        r0 = r3.isConnected();
        if (r0 == 0) goto L_0x0028;
    L_0x0025:
        r0 = com.google.android.gms.common.ConnectionResult.RESULT_SUCCESS;
        return r0;
    L_0x0028:
        r0 = r3.zahq;
        if (r0 == 0) goto L_0x002f;
    L_0x002c:
        r0 = r3.zahq;
        return r0;
    L_0x002f:
        r0 = new com.google.android.gms.common.ConnectionResult;
        r2 = 13;
        r0.<init>(r2, r1);
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zabe.blockingConnect():com.google.android.gms.common.ConnectionResult");
    }

    @javax.annotation.concurrent.GuardedBy("mLock")
    public final com.google.android.gms.common.ConnectionResult blockingConnect(long r4, java.util.concurrent.TimeUnit r6) {
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
        r3 = this;
        r3.connect();
        r4 = r6.toNanos(r4);
    L_0x0007:
        r6 = r3.isConnecting();
        r0 = 0;
        if (r6 == 0) goto L_0x0035;
    L_0x000e:
        r1 = 0;
        r6 = (r4 > r1 ? 1 : (r4 == r1 ? 0 : -1));
        if (r6 > 0) goto L_0x001f;
    L_0x0014:
        r3.disconnect();	 Catch:{ InterruptedException -> 0x0026 }
        r4 = new com.google.android.gms.common.ConnectionResult;	 Catch:{ InterruptedException -> 0x0026 }
        r5 = 14;	 Catch:{ InterruptedException -> 0x0026 }
        r4.<init>(r5, r0);	 Catch:{ InterruptedException -> 0x0026 }
        return r4;	 Catch:{ InterruptedException -> 0x0026 }
    L_0x001f:
        r6 = r3.zahm;	 Catch:{ InterruptedException -> 0x0026 }
        r4 = r6.awaitNanos(r4);	 Catch:{ InterruptedException -> 0x0026 }
        goto L_0x0007;
    L_0x0026:
        r4 = java.lang.Thread.currentThread();
        r4.interrupt();
        r4 = new com.google.android.gms.common.ConnectionResult;
        r5 = 15;
        r4.<init>(r5, r0);
        return r4;
    L_0x0035:
        r4 = r3.isConnected();
        if (r4 == 0) goto L_0x003e;
    L_0x003b:
        r4 = com.google.android.gms.common.ConnectionResult.RESULT_SUCCESS;
        return r4;
    L_0x003e:
        r4 = r3.zahq;
        if (r4 == 0) goto L_0x0045;
    L_0x0042:
        r4 = r3.zahq;
        return r4;
    L_0x0045:
        r4 = new com.google.android.gms.common.ConnectionResult;
        r5 = 13;
        r4.<init>(r5, r0);
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zabe.blockingConnect(long, java.util.concurrent.TimeUnit):com.google.android.gms.common.ConnectionResult");
    }

    @GuardedBy("mLock")
    public final void disconnect() {
        if (this.zahp.disconnect()) {
            this.zaho.clear();
        }
    }

    @GuardedBy("mLock")
    @Nullable
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        api = api.getClientKey();
        if (this.zagy.containsKey(api)) {
            if (((Client) this.zagy.get(api)).isConnected()) {
                return ConnectionResult.RESULT_SUCCESS;
            }
            if (this.zaho.containsKey(api)) {
                return (ConnectionResult) this.zaho.get(api);
            }
        }
        return null;
    }

    final void zaaz() {
        this.zaen.lock();
        try {
            this.zahp = new zaak(this, this.zaes, this.zaev, this.zaex, this.zacd, this.zaen, this.mContext);
            this.zahp.begin();
            this.zahm.signalAll();
        } finally {
            this.zaen.unlock();
        }
    }

    final void zaba() {
        this.zaen.lock();
        try {
            this.zaed.zaaw();
            this.zahp = new zaah(this);
            this.zahp.begin();
            this.zahm.signalAll();
        } finally {
            this.zaen.unlock();
        }
    }

    final void zaf(ConnectionResult connectionResult) {
        this.zaen.lock();
        try {
            this.zahq = connectionResult;
            this.zahp = new zaav(this);
            this.zahp.begin();
            this.zahm.signalAll();
        } finally {
            this.zaen.unlock();
        }
    }

    public final boolean isConnected() {
        return this.zahp instanceof zaah;
    }

    public final boolean isConnecting() {
        return this.zahp instanceof zaak;
    }

    @GuardedBy("mLock")
    public final void zaw() {
        if (isConnected()) {
            ((zaah) this.zahp).zaam();
        }
    }

    public final void zaa(@NonNull ConnectionResult connectionResult, @NonNull Api<?> api, boolean z) {
        this.zaen.lock();
        try {
            this.zahp.zaa(connectionResult, api, z);
        } finally {
            this.zaen.unlock();
        }
    }

    public final void onConnected(@Nullable Bundle bundle) {
        this.zaen.lock();
        try {
            this.zahp.onConnected(bundle);
        } finally {
            this.zaen.unlock();
        }
    }

    public final void onConnectionSuspended(int i) {
        this.zaen.lock();
        try {
            this.zahp.onConnectionSuspended(i);
        } finally {
            this.zaen.unlock();
        }
    }

    final void zaa(zabf com_google_android_gms_common_api_internal_zabf) {
        this.zahn.sendMessage(this.zahn.obtainMessage(1, com_google_android_gms_common_api_internal_zabf));
    }

    final void zab(RuntimeException runtimeException) {
        this.zahn.sendMessage(this.zahn.obtainMessage(2, runtimeException));
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String concat = String.valueOf(str).concat("  ");
        printWriter.append(str).append("mState=").println(this.zahp);
        for (Api api : this.zaev.keySet()) {
            printWriter.append(str).append(api.getName()).println(":");
            ((Client) this.zagy.get(api.getClientKey())).dump(concat, fileDescriptor, printWriter, strArr);
        }
    }
}
