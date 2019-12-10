package com.google.android.gms.common.api.internal;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;
import com.google.android.gms.cast.framework.media.NotificationOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.UnsupportedApiCallException;
import com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl;
import com.google.android.gms.common.api.internal.ListenerHolder.ListenerKey;
import com.google.android.gms.common.internal.BaseGmsClient.ConnectionProgressReportCallbacks;
import com.google.android.gms.common.internal.GoogleApiAvailabilityCache;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.SimpleClientAdapter;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.internal.base.zal;
import com.google.android.gms.signin.zad;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.concurrent.GuardedBy;

@KeepForSdk
public class GoogleApiManager implements Callback {
    private static final Object lock = new Object();
    public static final Status zahw = new Status(4, "Sign-out occurred while this API call was in progress.");
    private static final Status zahx = new Status(4, "The user must be signed in to make this API call.");
    @GuardedBy("lock")
    private static GoogleApiManager zaib;
    private final Handler handler;
    private long zahy = 5000;
    private long zahz = 120000;
    private long zaia = NotificationOptions.SKIP_STEP_TEN_SECONDS_IN_MS;
    private final Context zaic;
    private final GoogleApiAvailability zaid;
    private final GoogleApiAvailabilityCache zaie;
    private final AtomicInteger zaif = new AtomicInteger(1);
    private final AtomicInteger zaig = new AtomicInteger(0);
    private final Map<zai<?>, zaa<?>> zaih = new ConcurrentHashMap(5, 0.75f, 1);
    @GuardedBy("lock")
    private zaae zaii = null;
    @GuardedBy("lock")
    private final Set<zai<?>> zaij = new ArraySet();
    private final Set<zai<?>> zaik = new ArraySet();

    private static class zab {
        private final zai<?> zaja;
        private final Feature zajb;

        private zab(zai<?> com_google_android_gms_common_api_internal_zai_, Feature feature) {
            this.zaja = com_google_android_gms_common_api_internal_zai_;
            this.zajb = feature;
        }

        public final boolean equals(Object obj) {
            if (obj == null || !(obj instanceof zab)) {
                return false;
            }
            zab com_google_android_gms_common_api_internal_GoogleApiManager_zab = (zab) obj;
            if (!Objects.equal(this.zaja, com_google_android_gms_common_api_internal_GoogleApiManager_zab.zaja) || Objects.equal(this.zajb, com_google_android_gms_common_api_internal_GoogleApiManager_zab.zajb) == null) {
                return false;
            }
            return true;
        }

        public final int hashCode() {
            return Objects.hashCode(this.zaja, this.zajb);
        }

        public final String toString() {
            return Objects.toStringHelper(this).add("key", this.zaja).add("feature", this.zajb).toString();
        }
    }

    private class zac implements zach, ConnectionProgressReportCallbacks {
        private final zai<?> zafp;
        final /* synthetic */ GoogleApiManager zail;
        private final Client zain;
        private IAccountAccessor zajc = null;
        private Set<Scope> zajd = null;
        private boolean zaje = null;

        public zac(GoogleApiManager googleApiManager, Client client, zai<?> com_google_android_gms_common_api_internal_zai_) {
            this.zail = googleApiManager;
            this.zain = client;
            this.zafp = com_google_android_gms_common_api_internal_zai_;
        }

        public final void onReportServiceBinding(@NonNull ConnectionResult connectionResult) {
            this.zail.handler.post(new zabo(this, connectionResult));
        }

        @WorkerThread
        public final void zag(ConnectionResult connectionResult) {
            ((zaa) this.zail.zaih.get(this.zafp)).zag(connectionResult);
        }

        @WorkerThread
        public final void zaa(IAccountAccessor iAccountAccessor, Set<Scope> set) {
            if (iAccountAccessor != null) {
                if (set != null) {
                    this.zajc = iAccountAccessor;
                    this.zajd = set;
                    zabr();
                    return;
                }
            }
            Log.wtf("GoogleApiManager", "Received null response from onSignInSuccess", new Exception());
            zag(new ConnectionResult(4));
        }

        @WorkerThread
        private final void zabr() {
            if (this.zaje && this.zajc != null) {
                this.zain.getRemoteService(this.zajc, this.zajd);
            }
        }
    }

    public class zaa<O extends ApiOptions> implements ConnectionCallbacks, OnConnectionFailedListener, zar {
        private final zai<O> zafp;
        final /* synthetic */ GoogleApiManager zail;
        private final Queue<zab> zaim = new LinkedList();
        private final Client zain;
        private final AnyClient zaio;
        private final zaab zaip;
        private final Set<zak> zaiq = new HashSet();
        private final Map<ListenerKey<?>, zabw> zair = new HashMap();
        private final int zais;
        private final zace zait;
        private boolean zaiu;
        private final List<zab> zaiv = new ArrayList();
        private ConnectionResult zaiw = null;

        @WorkerThread
        public zaa(GoogleApiManager googleApiManager, GoogleApi<O> googleApi) {
            this.zail = googleApiManager;
            this.zain = googleApi.zaa(googleApiManager.handler.getLooper(), this);
            if (this.zain instanceof SimpleClientAdapter) {
                this.zaio = ((SimpleClientAdapter) this.zain).getClient();
            } else {
                this.zaio = this.zain;
            }
            this.zafp = googleApi.zak();
            this.zaip = new zaab();
            this.zais = googleApi.getInstanceId();
            if (this.zain.requiresSignIn()) {
                this.zait = googleApi.zaa(googleApiManager.zaic, googleApiManager.handler);
            } else {
                this.zait = null;
            }
        }

        public final void onConnected(@Nullable Bundle bundle) {
            if (Looper.myLooper() == this.zail.handler.getLooper()) {
                zabg();
            } else {
                this.zail.handler.post(new zabj(this));
            }
        }

        @androidx.annotation.WorkerThread
        private final void zabg() {
            /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r4 = this;
            r4.zabl();
            r0 = com.google.android.gms.common.ConnectionResult.RESULT_SUCCESS;
            r4.zai(r0);
            r4.zabn();
            r0 = r4.zair;
            r0 = r0.values();
            r0 = r0.iterator();
        L_0x0015:
            r1 = r0.hasNext();
            if (r1 == 0) goto L_0x004b;
        L_0x001b:
            r1 = r0.next();
            r1 = (com.google.android.gms.common.api.internal.zabw) r1;
            r2 = r1.zajw;
            r2 = r2.getRequiredFeatures();
            r2 = r4.zaa(r2);
            if (r2 == 0) goto L_0x0031;
        L_0x002d:
            r0.remove();
            goto L_0x0015;
        L_0x0031:
            r1 = r1.zajw;	 Catch:{ DeadObjectException -> 0x0042, RemoteException -> 0x003e }
            r2 = r4.zaio;	 Catch:{ DeadObjectException -> 0x0042, RemoteException -> 0x003e }
            r3 = new com.google.android.gms.tasks.TaskCompletionSource;	 Catch:{ DeadObjectException -> 0x0042, RemoteException -> 0x003e }
            r3.<init>();	 Catch:{ DeadObjectException -> 0x0042, RemoteException -> 0x003e }
            r1.registerListener(r2, r3);	 Catch:{ DeadObjectException -> 0x0042, RemoteException -> 0x003e }
            goto L_0x0015;
        L_0x003e:
            r0.remove();
            goto L_0x0015;
        L_0x0042:
            r0 = 1;
            r4.onConnectionSuspended(r0);
            r0 = r4.zain;
            r0.disconnect();
        L_0x004b:
            r4.zabi();
            r4.zabo();
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.GoogleApiManager.zaa.zabg():void");
        }

        public final void onConnectionSuspended(int i) {
            if (Looper.myLooper() == this.zail.handler.getLooper()) {
                zabh();
            } else {
                this.zail.handler.post(new zabk(this));
            }
        }

        @WorkerThread
        private final void zabh() {
            zabl();
            this.zaiu = true;
            this.zaip.zaai();
            this.zail.handler.sendMessageDelayed(Message.obtain(this.zail.handler, 9, this.zafp), this.zail.zahy);
            this.zail.handler.sendMessageDelayed(Message.obtain(this.zail.handler, 11, this.zafp), this.zail.zahz);
            this.zail.zaie.flush();
        }

        @WorkerThread
        public final void zag(@NonNull ConnectionResult connectionResult) {
            Preconditions.checkHandlerThread(this.zail.handler);
            this.zain.disconnect();
            onConnectionFailed(connectionResult);
        }

        @WorkerThread
        private final boolean zah(@NonNull ConnectionResult connectionResult) {
            synchronized (GoogleApiManager.lock) {
                if (this.zail.zaii == null || !this.zail.zaij.contains(this.zafp)) {
                    return null;
                }
                this.zail.zaii.zab(connectionResult, this.zais);
                return true;
            }
        }

        public final void zaa(ConnectionResult connectionResult, Api<?> api, boolean z) {
            if (Looper.myLooper() == this.zail.handler.getLooper()) {
                onConnectionFailed(connectionResult);
            } else {
                this.zail.handler.post(new zabl(this, connectionResult));
            }
        }

        @WorkerThread
        public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            Preconditions.checkHandlerThread(this.zail.handler);
            if (this.zait != null) {
                this.zait.zabs();
            }
            zabl();
            this.zail.zaie.flush();
            zai(connectionResult);
            if (connectionResult.getErrorCode() == 4) {
                zac(GoogleApiManager.zahx);
            } else if (this.zaim.isEmpty()) {
                this.zaiw = connectionResult;
            } else {
                if (!(zah(connectionResult) || this.zail.zac(connectionResult, this.zais))) {
                    if (connectionResult.getErrorCode() == 18) {
                        this.zaiu = true;
                    }
                    if (this.zaiu != null) {
                        this.zail.handler.sendMessageDelayed(Message.obtain(this.zail.handler, 9, this.zafp), this.zail.zahy);
                        return;
                    }
                    String zan = this.zafp.zan();
                    StringBuilder stringBuilder = new StringBuilder(String.valueOf(zan).length() + 38);
                    stringBuilder.append("API: ");
                    stringBuilder.append(zan);
                    stringBuilder.append(" is not available on this device.");
                    zac(new Status(17, stringBuilder.toString()));
                }
            }
        }

        @WorkerThread
        private final void zabi() {
            ArrayList arrayList = new ArrayList(this.zaim);
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                zab com_google_android_gms_common_api_internal_zab = (zab) obj;
                if (!this.zain.isConnected()) {
                    return;
                }
                if (zab(com_google_android_gms_common_api_internal_zab)) {
                    this.zaim.remove(com_google_android_gms_common_api_internal_zab);
                }
            }
        }

        @WorkerThread
        public final void zaa(zab com_google_android_gms_common_api_internal_zab) {
            Preconditions.checkHandlerThread(this.zail.handler);
            if (!this.zain.isConnected()) {
                this.zaim.add(com_google_android_gms_common_api_internal_zab);
                if (this.zaiw == null || this.zaiw.hasResolution() == null) {
                    connect();
                } else {
                    onConnectionFailed(this.zaiw);
                }
            } else if (zab(com_google_android_gms_common_api_internal_zab)) {
                zabo();
            } else {
                this.zaim.add(com_google_android_gms_common_api_internal_zab);
            }
        }

        @WorkerThread
        public final void zabj() {
            Preconditions.checkHandlerThread(this.zail.handler);
            zac(GoogleApiManager.zahw);
            this.zaip.zaah();
            for (ListenerKey com_google_android_gms_common_api_internal_zah : (ListenerKey[]) this.zair.keySet().toArray(new ListenerKey[this.zair.size()])) {
                zaa(new zah(com_google_android_gms_common_api_internal_zah, new TaskCompletionSource()));
            }
            zai(new ConnectionResult(4));
            if (this.zain.isConnected()) {
                this.zain.onUserSignOut(new zabm(this));
            }
        }

        public final Client zaab() {
            return this.zain;
        }

        public final Map<ListenerKey<?>, zabw> zabk() {
            return this.zair;
        }

        @WorkerThread
        public final void zabl() {
            Preconditions.checkHandlerThread(this.zail.handler);
            this.zaiw = null;
        }

        @WorkerThread
        public final ConnectionResult zabm() {
            Preconditions.checkHandlerThread(this.zail.handler);
            return this.zaiw;
        }

        @WorkerThread
        private final boolean zab(zab com_google_android_gms_common_api_internal_zab) {
            if (com_google_android_gms_common_api_internal_zab instanceof zac) {
                zac com_google_android_gms_common_api_internal_zac = (zac) com_google_android_gms_common_api_internal_zab;
                Feature zaa = zaa(com_google_android_gms_common_api_internal_zac.zab(this));
                if (zaa == null) {
                    zac(com_google_android_gms_common_api_internal_zab);
                    return true;
                }
                if (com_google_android_gms_common_api_internal_zac.zac(this) != null) {
                    com_google_android_gms_common_api_internal_zab = new zab(this.zafp, zaa);
                    int indexOf = this.zaiv.indexOf(com_google_android_gms_common_api_internal_zab);
                    if (indexOf >= 0) {
                        zab com_google_android_gms_common_api_internal_GoogleApiManager_zab = (zab) this.zaiv.get(indexOf);
                        this.zail.handler.removeMessages(15, com_google_android_gms_common_api_internal_GoogleApiManager_zab);
                        this.zail.handler.sendMessageDelayed(Message.obtain(this.zail.handler, 15, com_google_android_gms_common_api_internal_GoogleApiManager_zab), this.zail.zahy);
                    } else {
                        this.zaiv.add(com_google_android_gms_common_api_internal_zab);
                        this.zail.handler.sendMessageDelayed(Message.obtain(this.zail.handler, 15, com_google_android_gms_common_api_internal_zab), this.zail.zahy);
                        this.zail.handler.sendMessageDelayed(Message.obtain(this.zail.handler, 16, com_google_android_gms_common_api_internal_zab), this.zail.zahz);
                        com_google_android_gms_common_api_internal_zab = new ConnectionResult(2, null);
                        if (!zah(com_google_android_gms_common_api_internal_zab)) {
                            this.zail.zac(com_google_android_gms_common_api_internal_zab, this.zais);
                        }
                    }
                } else {
                    com_google_android_gms_common_api_internal_zac.zaa(new UnsupportedApiCallException(zaa));
                }
                return null;
            }
            zac(com_google_android_gms_common_api_internal_zab);
            return true;
        }

        @androidx.annotation.WorkerThread
        private final void zac(com.google.android.gms.common.api.internal.zab r3) {
            /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r2 = this;
            r0 = r2.zaip;
            r1 = r2.requiresSignIn();
            r3.zaa(r0, r1);
            r3.zaa(r2);	 Catch:{ DeadObjectException -> 0x000d }
            return;
        L_0x000d:
            r3 = 1;
            r2.onConnectionSuspended(r3);
            r3 = r2.zain;
            r3.disconnect();
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.GoogleApiManager.zaa.zac(com.google.android.gms.common.api.internal.zab):void");
        }

        @WorkerThread
        public final void zac(Status status) {
            Preconditions.checkHandlerThread(this.zail.handler);
            for (zab zaa : this.zaim) {
                zaa.zaa(status);
            }
            this.zaim.clear();
        }

        @WorkerThread
        public final void resume() {
            Preconditions.checkHandlerThread(this.zail.handler);
            if (this.zaiu) {
                connect();
            }
        }

        @WorkerThread
        private final void zabn() {
            if (this.zaiu) {
                this.zail.handler.removeMessages(11, this.zafp);
                this.zail.handler.removeMessages(9, this.zafp);
                this.zaiu = false;
            }
        }

        @WorkerThread
        public final void zaav() {
            Preconditions.checkHandlerThread(this.zail.handler);
            if (this.zaiu) {
                Status status;
                zabn();
                if (this.zail.zaid.isGooglePlayServicesAvailable(this.zail.zaic) == 18) {
                    status = new Status(8, "Connection timed out while waiting for Google Play services update to complete.");
                } else {
                    status = new Status(8, "API failed to connect while resuming due to an unknown error.");
                }
                zac(status);
                this.zain.disconnect();
            }
        }

        private final void zabo() {
            this.zail.handler.removeMessages(12, this.zafp);
            this.zail.handler.sendMessageDelayed(this.zail.handler.obtainMessage(12, this.zafp), this.zail.zaia);
        }

        @WorkerThread
        public final boolean zabp() {
            return zac(true);
        }

        @WorkerThread
        private final boolean zac(boolean z) {
            Preconditions.checkHandlerThread(this.zail.handler);
            if (!this.zain.isConnected() || this.zair.size() != 0) {
                return false;
            }
            if (this.zaip.zaag()) {
                if (z) {
                    zabo();
                }
                return false;
            }
            this.zain.disconnect();
            return true;
        }

        @WorkerThread
        public final void connect() {
            Preconditions.checkHandlerThread(this.zail.handler);
            if (!this.zain.isConnected()) {
                if (!this.zain.isConnecting()) {
                    int clientAvailability = this.zail.zaie.getClientAvailability(this.zail.zaic, this.zain);
                    if (clientAvailability != 0) {
                        onConnectionFailed(new ConnectionResult(clientAvailability, null));
                        return;
                    }
                    zach com_google_android_gms_common_api_internal_GoogleApiManager_zac = new zac(this.zail, this.zain, this.zafp);
                    if (this.zain.requiresSignIn()) {
                        this.zait.zaa(com_google_android_gms_common_api_internal_GoogleApiManager_zac);
                    }
                    this.zain.connect(com_google_android_gms_common_api_internal_GoogleApiManager_zac);
                }
            }
        }

        @WorkerThread
        public final void zaa(zak com_google_android_gms_common_api_internal_zak) {
            Preconditions.checkHandlerThread(this.zail.handler);
            this.zaiq.add(com_google_android_gms_common_api_internal_zak);
        }

        @WorkerThread
        private final void zai(ConnectionResult connectionResult) {
            for (zak com_google_android_gms_common_api_internal_zak : this.zaiq) {
                String str = null;
                if (Objects.equal(connectionResult, ConnectionResult.RESULT_SUCCESS)) {
                    str = this.zain.getEndpointPackageName();
                }
                com_google_android_gms_common_api_internal_zak.zaa(this.zafp, connectionResult, str);
            }
            this.zaiq.clear();
        }

        final boolean isConnected() {
            return this.zain.isConnected();
        }

        public final boolean requiresSignIn() {
            return this.zain.requiresSignIn();
        }

        public final int getInstanceId() {
            return this.zais;
        }

        final zad zabq() {
            return this.zait == null ? null : this.zait.zabq();
        }

        @WorkerThread
        @Nullable
        private final Feature zaa(@Nullable Feature[] featureArr) {
            if (featureArr != null) {
                if (featureArr.length != 0) {
                    Feature[] availableFeatures = this.zain.getAvailableFeatures();
                    int i = 0;
                    if (availableFeatures == null) {
                        availableFeatures = new Feature[0];
                    }
                    Map arrayMap = new ArrayMap(availableFeatures.length);
                    for (Feature feature : availableFeatures) {
                        arrayMap.put(feature.getName(), Long.valueOf(feature.getVersion()));
                    }
                    int length = featureArr.length;
                    while (i < length) {
                        Feature feature2 = featureArr[i];
                        if (arrayMap.containsKey(feature2.getName())) {
                            if (((Long) arrayMap.get(feature2.getName())).longValue() >= feature2.getVersion()) {
                                i++;
                            }
                        }
                        return feature2;
                    }
                    return null;
                }
            }
            return null;
        }

        @WorkerThread
        private final void zaa(zab com_google_android_gms_common_api_internal_GoogleApiManager_zab) {
            if (this.zaiv.contains(com_google_android_gms_common_api_internal_GoogleApiManager_zab) != null && this.zaiu == null) {
                if (this.zain.isConnected() == null) {
                    connect();
                    return;
                }
                zabi();
            }
        }

        @WorkerThread
        private final void zab(zab com_google_android_gms_common_api_internal_GoogleApiManager_zab) {
            if (this.zaiv.remove(com_google_android_gms_common_api_internal_GoogleApiManager_zab)) {
                this.zail.handler.removeMessages(15, com_google_android_gms_common_api_internal_GoogleApiManager_zab);
                this.zail.handler.removeMessages(16, com_google_android_gms_common_api_internal_GoogleApiManager_zab);
                Object zad = com_google_android_gms_common_api_internal_GoogleApiManager_zab.zajb;
                ArrayList arrayList = new ArrayList(this.zaim.size());
                for (zab com_google_android_gms_common_api_internal_zab : this.zaim) {
                    if (com_google_android_gms_common_api_internal_zab instanceof zac) {
                        Object[] zab = ((zac) com_google_android_gms_common_api_internal_zab).zab(this);
                        if (zab != null && ArrayUtils.contains(zab, zad)) {
                            arrayList.add(com_google_android_gms_common_api_internal_zab);
                        }
                    }
                }
                arrayList = arrayList;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    zab com_google_android_gms_common_api_internal_zab2 = (zab) obj;
                    this.zaim.remove(com_google_android_gms_common_api_internal_zab2);
                    com_google_android_gms_common_api_internal_zab2.zaa(new UnsupportedApiCallException(zad));
                }
            }
        }
    }

    public static GoogleApiManager zab(Context context) {
        synchronized (lock) {
            if (zaib == null) {
                HandlerThread handlerThread = new HandlerThread("GoogleApiHandler", 9);
                handlerThread.start();
                zaib = new GoogleApiManager(context.getApplicationContext(), handlerThread.getLooper(), GoogleApiAvailability.getInstance());
            }
            context = zaib;
        }
        return context;
    }

    public static GoogleApiManager zabc() {
        GoogleApiManager googleApiManager;
        synchronized (lock) {
            Preconditions.checkNotNull(zaib, "Must guarantee manager is non-null before using getInstance");
            googleApiManager = zaib;
        }
        return googleApiManager;
    }

    @KeepForSdk
    public static void reportSignOut() {
        synchronized (lock) {
            if (zaib != null) {
                GoogleApiManager googleApiManager = zaib;
                googleApiManager.zaig.incrementAndGet();
                googleApiManager.handler.sendMessageAtFrontOfQueue(googleApiManager.handler.obtainMessage(10));
            }
        }
    }

    @KeepForSdk
    private GoogleApiManager(Context context, Looper looper, GoogleApiAvailability googleApiAvailability) {
        this.zaic = context;
        this.handler = new zal(looper, this);
        this.zaid = googleApiAvailability;
        this.zaie = new GoogleApiAvailabilityCache(googleApiAvailability);
        this.handler.sendMessage(this.handler.obtainMessage(6));
    }

    public final int zabd() {
        return this.zaif.getAndIncrement();
    }

    public final void zaa(GoogleApi<?> googleApi) {
        this.handler.sendMessage(this.handler.obtainMessage(7, googleApi));
    }

    @WorkerThread
    private final void zab(GoogleApi<?> googleApi) {
        zai zak = googleApi.zak();
        zaa com_google_android_gms_common_api_internal_GoogleApiManager_zaa = (zaa) this.zaih.get(zak);
        if (com_google_android_gms_common_api_internal_GoogleApiManager_zaa == null) {
            com_google_android_gms_common_api_internal_GoogleApiManager_zaa = new zaa(this, googleApi);
            this.zaih.put(zak, com_google_android_gms_common_api_internal_GoogleApiManager_zaa);
        }
        if (com_google_android_gms_common_api_internal_GoogleApiManager_zaa.requiresSignIn() != null) {
            this.zaik.add(zak);
        }
        com_google_android_gms_common_api_internal_GoogleApiManager_zaa.connect();
    }

    public final void zaa(@NonNull zaae com_google_android_gms_common_api_internal_zaae) {
        synchronized (lock) {
            if (this.zaii != com_google_android_gms_common_api_internal_zaae) {
                this.zaii = com_google_android_gms_common_api_internal_zaae;
                this.zaij.clear();
            }
            this.zaij.addAll(com_google_android_gms_common_api_internal_zaae.zaaj());
        }
    }

    final void zab(@NonNull zaae com_google_android_gms_common_api_internal_zaae) {
        synchronized (lock) {
            if (this.zaii == com_google_android_gms_common_api_internal_zaae) {
                this.zaii = null;
                this.zaij.clear();
            }
        }
    }

    public final Task<Map<zai<?>, String>> zaa(Iterable<? extends GoogleApi<?>> iterable) {
        zak com_google_android_gms_common_api_internal_zak = new zak(iterable);
        this.handler.sendMessage(this.handler.obtainMessage(2, com_google_android_gms_common_api_internal_zak));
        return com_google_android_gms_common_api_internal_zak.getTask();
    }

    public final void zao() {
        this.handler.sendMessage(this.handler.obtainMessage(3));
    }

    final void maybeSignOut() {
        this.zaig.incrementAndGet();
        this.handler.sendMessage(this.handler.obtainMessage(10));
    }

    public final Task<Boolean> zac(GoogleApi<?> googleApi) {
        zaaf com_google_android_gms_common_api_internal_zaaf = new zaaf(googleApi.zak());
        this.handler.sendMessage(this.handler.obtainMessage(14, com_google_android_gms_common_api_internal_zaaf));
        return com_google_android_gms_common_api_internal_zaaf.zaal().getTask();
    }

    public final <O extends ApiOptions> void zaa(GoogleApi<O> googleApi, int i, ApiMethodImpl<? extends Result, AnyClient> apiMethodImpl) {
        this.handler.sendMessage(this.handler.obtainMessage(4, new zabv(new zae(i, apiMethodImpl), this.zaig.get(), googleApi)));
    }

    public final <O extends ApiOptions, ResultT> void zaa(GoogleApi<O> googleApi, int i, TaskApiCall<AnyClient, ResultT> taskApiCall, TaskCompletionSource<ResultT> taskCompletionSource, StatusExceptionMapper statusExceptionMapper) {
        this.handler.sendMessage(this.handler.obtainMessage(4, new zabv(new zag(i, taskApiCall, taskCompletionSource, statusExceptionMapper), this.zaig.get(), googleApi)));
    }

    public final <O extends ApiOptions> Task<Void> zaa(@NonNull GoogleApi<O> googleApi, @NonNull RegisterListenerMethod<AnyClient, ?> registerListenerMethod, @NonNull UnregisterListenerMethod<AnyClient, ?> unregisterListenerMethod) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.handler.sendMessage(this.handler.obtainMessage(8, new zabv(new zaf(new zabw(registerListenerMethod, unregisterListenerMethod), taskCompletionSource), this.zaig.get(), googleApi)));
        return taskCompletionSource.getTask();
    }

    public final <O extends ApiOptions> Task<Boolean> zaa(@NonNull GoogleApi<O> googleApi, @NonNull ListenerKey<?> listenerKey) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.handler.sendMessage(this.handler.obtainMessage(13, new zabv(new zah(listenerKey, taskCompletionSource), this.zaig.get(), googleApi)));
        return taskCompletionSource.getTask();
    }

    @WorkerThread
    public boolean handleMessage(Message message) {
        long j = 300000;
        zaa com_google_android_gms_common_api_internal_GoogleApiManager_zaa;
        zaa com_google_android_gms_common_api_internal_GoogleApiManager_zaa2;
        zai zak;
        zab com_google_android_gms_common_api_internal_GoogleApiManager_zab;
        switch (message.what) {
            case 1:
                if (((Boolean) message.obj).booleanValue() != null) {
                    j = NotificationOptions.SKIP_STEP_TEN_SECONDS_IN_MS;
                }
                this.zaia = j;
                this.handler.removeMessages(12);
                for (zai obtainMessage : this.zaih.keySet()) {
                    this.handler.sendMessageDelayed(this.handler.obtainMessage(12, obtainMessage), this.zaia);
                }
                break;
            case 2:
                zak com_google_android_gms_common_api_internal_zak = (zak) message.obj;
                for (zai obtainMessage2 : com_google_android_gms_common_api_internal_zak.zap()) {
                    com_google_android_gms_common_api_internal_GoogleApiManager_zaa = (zaa) this.zaih.get(obtainMessage2);
                    if (com_google_android_gms_common_api_internal_GoogleApiManager_zaa == null) {
                        com_google_android_gms_common_api_internal_zak.zaa(obtainMessage2, new ConnectionResult(13), null);
                        break;
                    } else if (com_google_android_gms_common_api_internal_GoogleApiManager_zaa.isConnected()) {
                        com_google_android_gms_common_api_internal_zak.zaa(obtainMessage2, ConnectionResult.RESULT_SUCCESS, com_google_android_gms_common_api_internal_GoogleApiManager_zaa.zaab().getEndpointPackageName());
                    } else if (com_google_android_gms_common_api_internal_GoogleApiManager_zaa.zabm() != null) {
                        com_google_android_gms_common_api_internal_zak.zaa(obtainMessage2, com_google_android_gms_common_api_internal_GoogleApiManager_zaa.zabm(), null);
                    } else {
                        com_google_android_gms_common_api_internal_GoogleApiManager_zaa.zaa(com_google_android_gms_common_api_internal_zak);
                        com_google_android_gms_common_api_internal_GoogleApiManager_zaa.connect();
                    }
                }
                break;
            case 3:
                for (zaa com_google_android_gms_common_api_internal_GoogleApiManager_zaa22 : this.zaih.values()) {
                    com_google_android_gms_common_api_internal_GoogleApiManager_zaa22.zabl();
                    com_google_android_gms_common_api_internal_GoogleApiManager_zaa22.connect();
                }
                break;
            case 4:
            case 8:
            case 13:
                zabv com_google_android_gms_common_api_internal_zabv = (zabv) message.obj;
                com_google_android_gms_common_api_internal_GoogleApiManager_zaa22 = (zaa) this.zaih.get(com_google_android_gms_common_api_internal_zabv.zajs.zak());
                if (com_google_android_gms_common_api_internal_GoogleApiManager_zaa22 == null) {
                    zab(com_google_android_gms_common_api_internal_zabv.zajs);
                    com_google_android_gms_common_api_internal_GoogleApiManager_zaa22 = (zaa) this.zaih.get(com_google_android_gms_common_api_internal_zabv.zajs.zak());
                }
                if (com_google_android_gms_common_api_internal_GoogleApiManager_zaa22.requiresSignIn() && this.zaig.get() != com_google_android_gms_common_api_internal_zabv.zajr) {
                    com_google_android_gms_common_api_internal_zabv.zajq.zaa(zahw);
                    com_google_android_gms_common_api_internal_GoogleApiManager_zaa22.zabj();
                    break;
                }
                com_google_android_gms_common_api_internal_GoogleApiManager_zaa22.zaa(com_google_android_gms_common_api_internal_zabv.zajq);
                break;
                break;
            case 5:
                StringBuilder stringBuilder;
                String errorString;
                StringBuilder stringBuilder2;
                int i = message.arg1;
                ConnectionResult connectionResult = (ConnectionResult) message.obj;
                for (zaa com_google_android_gms_common_api_internal_GoogleApiManager_zaa3 : this.zaih.values()) {
                    if (com_google_android_gms_common_api_internal_GoogleApiManager_zaa3.getInstanceId() == i) {
                        if (com_google_android_gms_common_api_internal_GoogleApiManager_zaa3 != null) {
                            stringBuilder = new StringBuilder(76);
                            stringBuilder.append("Could not find API instance ");
                            stringBuilder.append(i);
                            stringBuilder.append(" while trying to fail enqueued calls.");
                            Log.wtf("GoogleApiManager", stringBuilder.toString(), new Exception());
                            break;
                        }
                        errorString = this.zaid.getErrorString(connectionResult.getErrorCode());
                        message = connectionResult.getErrorMessage();
                        stringBuilder2 = new StringBuilder((String.valueOf(errorString).length() + 69) + String.valueOf(message).length());
                        stringBuilder2.append("Error resolution was canceled by the user, original error message: ");
                        stringBuilder2.append(errorString);
                        stringBuilder2.append(": ");
                        stringBuilder2.append(message);
                        com_google_android_gms_common_api_internal_GoogleApiManager_zaa3.zac(new Status(17, stringBuilder2.toString()));
                        break;
                    }
                }
                com_google_android_gms_common_api_internal_GoogleApiManager_zaa3 = null;
                if (com_google_android_gms_common_api_internal_GoogleApiManager_zaa3 != null) {
                    stringBuilder = new StringBuilder(76);
                    stringBuilder.append("Could not find API instance ");
                    stringBuilder.append(i);
                    stringBuilder.append(" while trying to fail enqueued calls.");
                    Log.wtf("GoogleApiManager", stringBuilder.toString(), new Exception());
                } else {
                    errorString = this.zaid.getErrorString(connectionResult.getErrorCode());
                    message = connectionResult.getErrorMessage();
                    stringBuilder2 = new StringBuilder((String.valueOf(errorString).length() + 69) + String.valueOf(message).length());
                    stringBuilder2.append("Error resolution was canceled by the user, original error message: ");
                    stringBuilder2.append(errorString);
                    stringBuilder2.append(": ");
                    stringBuilder2.append(message);
                    com_google_android_gms_common_api_internal_GoogleApiManager_zaa3.zac(new Status(17, stringBuilder2.toString()));
                }
            case 6:
                if (!(PlatformVersion.isAtLeastIceCreamSandwich() == null || (this.zaic.getApplicationContext() instanceof Application) == null)) {
                    BackgroundDetector.initialize((Application) this.zaic.getApplicationContext());
                    BackgroundDetector.getInstance().addListener(new zabi(this));
                    if (BackgroundDetector.getInstance().readCurrentStateIfPossible(true) == null) {
                        this.zaia = 300000;
                        break;
                    }
                }
                break;
            case 7:
                zab((GoogleApi) message.obj);
                break;
            case 9:
                if (this.zaih.containsKey(message.obj)) {
                    ((zaa) this.zaih.get(message.obj)).resume();
                    break;
                }
                break;
            case 10:
                for (zai zak2 : this.zaik) {
                    ((zaa) this.zaih.remove(zak2)).zabj();
                }
                this.zaik.clear();
                break;
            case 11:
                if (this.zaih.containsKey(message.obj)) {
                    ((zaa) this.zaih.get(message.obj)).zaav();
                    break;
                }
                break;
            case 12:
                if (this.zaih.containsKey(message.obj)) {
                    ((zaa) this.zaih.get(message.obj)).zabp();
                    break;
                }
                break;
            case 14:
                zaaf com_google_android_gms_common_api_internal_zaaf = (zaaf) message.obj;
                zak2 = com_google_android_gms_common_api_internal_zaaf.zak();
                if (!this.zaih.containsKey(zak2)) {
                    com_google_android_gms_common_api_internal_zaaf.zaal().setResult(Boolean.valueOf(false));
                    break;
                }
                com_google_android_gms_common_api_internal_zaaf.zaal().setResult(Boolean.valueOf(((zaa) this.zaih.get(zak2)).zac(false)));
                break;
            case 15:
                com_google_android_gms_common_api_internal_GoogleApiManager_zab = (zab) message.obj;
                if (this.zaih.containsKey(com_google_android_gms_common_api_internal_GoogleApiManager_zab.zaja)) {
                    ((zaa) this.zaih.get(com_google_android_gms_common_api_internal_GoogleApiManager_zab.zaja)).zaa(com_google_android_gms_common_api_internal_GoogleApiManager_zab);
                    break;
                }
                break;
            case 16:
                com_google_android_gms_common_api_internal_GoogleApiManager_zab = (zab) message.obj;
                if (this.zaih.containsKey(com_google_android_gms_common_api_internal_GoogleApiManager_zab.zaja)) {
                    ((zaa) this.zaih.get(com_google_android_gms_common_api_internal_GoogleApiManager_zab.zaja)).zab(com_google_android_gms_common_api_internal_GoogleApiManager_zab);
                    break;
                }
                break;
            default:
                message = message.what;
                StringBuilder stringBuilder3 = new StringBuilder(31);
                stringBuilder3.append("Unknown message id: ");
                stringBuilder3.append(message);
                Log.w("GoogleApiManager", stringBuilder3.toString());
                return false;
        }
        return true;
    }

    final PendingIntent zaa(zai<?> com_google_android_gms_common_api_internal_zai_, int i) {
        zaa com_google_android_gms_common_api_internal_GoogleApiManager_zaa = (zaa) this.zaih.get(com_google_android_gms_common_api_internal_zai_);
        if (com_google_android_gms_common_api_internal_GoogleApiManager_zaa == null) {
            return null;
        }
        com_google_android_gms_common_api_internal_zai_ = com_google_android_gms_common_api_internal_GoogleApiManager_zaa.zabq();
        if (com_google_android_gms_common_api_internal_zai_ == null) {
            return null;
        }
        return PendingIntent.getActivity(this.zaic, i, com_google_android_gms_common_api_internal_zai_.getSignInIntent(), 134217728);
    }

    final boolean zac(ConnectionResult connectionResult, int i) {
        return this.zaid.zaa(this.zaic, connectionResult, i);
    }

    public final void zaa(ConnectionResult connectionResult, int i) {
        if (!zac(connectionResult, i)) {
            this.handler.sendMessage(this.handler.obtainMessage(5, i, 0, connectionResult));
        }
    }
}
