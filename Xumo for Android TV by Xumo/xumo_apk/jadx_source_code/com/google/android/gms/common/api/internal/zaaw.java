package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.cast.framework.media.NotificationOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.AbstractClientBuilder;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.Api.AnyClientKey;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClientEventManager;
import com.google.android.gms.common.internal.GmsClientEventManager.GmsClientEventState;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.common.util.ClientLibraryUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.signin.zad;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

public final class zaaw extends GoogleApiClient implements zabt {
    private final Context mContext;
    private final Looper zabj;
    private final int zaca;
    private final GoogleApiAvailability zacc;
    private final AbstractClientBuilder<? extends zad, SignInOptions> zacd;
    private boolean zacg;
    private final Lock zaen;
    private final ClientSettings zaes;
    private final Map<Api<?>, Boolean> zaev;
    @VisibleForTesting
    final Queue<ApiMethodImpl<?, ?>> zafb = new LinkedList();
    private final GmsClientEventManager zagr;
    private zabs zags = null;
    private volatile boolean zagt;
    private long zagu;
    private long zagv;
    private final zabb zagw;
    @VisibleForTesting
    private zabq zagx;
    final Map<AnyClientKey<?>, Client> zagy;
    Set<Scope> zagz;
    private final ListenerHolders zaha;
    private final ArrayList<zaq> zahb;
    private Integer zahc;
    Set<zacm> zahd;
    final zacp zahe;
    private final GmsClientEventState zahf;

    public zaaw(Context context, Lock lock, Looper looper, ClientSettings clientSettings, GoogleApiAvailability googleApiAvailability, AbstractClientBuilder<? extends zad, SignInOptions> abstractClientBuilder, Map<Api<?>, Boolean> map, List<ConnectionCallbacks> list, List<OnConnectionFailedListener> list2, Map<AnyClientKey<?>, Client> map2, int i, int i2, ArrayList<zaq> arrayList, boolean z) {
        Looper looper2 = looper;
        r0.zagu = ClientLibraryUtils.isPackageSide() ? NotificationOptions.SKIP_STEP_TEN_SECONDS_IN_MS : 120000;
        r0.zagv = 5000;
        r0.zagz = new HashSet();
        r0.zaha = new ListenerHolders();
        r0.zahc = null;
        r0.zahd = null;
        r0.zahf = new zaax(this);
        r0.mContext = context;
        r0.zaen = lock;
        r0.zacg = false;
        r0.zagr = new GmsClientEventManager(looper, r0.zahf);
        r0.zabj = looper2;
        r0.zagw = new zabb(this, looper);
        r0.zacc = googleApiAvailability;
        r0.zaca = i;
        if (r0.zaca >= 0) {
            r0.zahc = Integer.valueOf(i2);
        }
        r0.zaev = map;
        r0.zagy = map2;
        r0.zahb = arrayList;
        r0.zahe = new zacp(r0.zagy);
        for (ConnectionCallbacks registerConnectionCallbacks : list) {
            r0.zagr.registerConnectionCallbacks(registerConnectionCallbacks);
        }
        for (OnConnectionFailedListener registerConnectionFailedListener : list2) {
            r0.zagr.registerConnectionFailedListener(registerConnectionFailedListener);
        }
        r0.zaes = clientSettings;
        r0.zacd = abstractClientBuilder;
    }

    private static String zaf(int i) {
        switch (i) {
            case 1:
                return "SIGN_IN_MODE_REQUIRED";
            case 2:
                return "SIGN_IN_MODE_OPTIONAL";
            case 3:
                return "SIGN_IN_MODE_NONE";
            default:
                return "UNKNOWN";
        }
    }

    public final <A extends AnyClient, R extends Result, T extends ApiMethodImpl<R, A>> T enqueue(@NonNull T t) {
        Preconditions.checkArgument(t.getClientKey() != null, "This task can not be enqueued (it's probably a Batch or malformed)");
        boolean containsKey = this.zagy.containsKey(t.getClientKey());
        String name = t.getApi() != null ? t.getApi().getName() : "the API";
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(name).length() + 65);
        stringBuilder.append("GoogleApiClient is not configured to use ");
        stringBuilder.append(name);
        stringBuilder.append(" required for this call.");
        Preconditions.checkArgument(containsKey, stringBuilder.toString());
        this.zaen.lock();
        try {
            if (this.zags == null) {
                this.zafb.add(t);
                return t;
            }
            t = this.zags.enqueue(t);
            this.zaen.unlock();
            return t;
        } finally {
            this.zaen.unlock();
        }
    }

    public final <A extends AnyClient, T extends ApiMethodImpl<? extends Result, A>> T execute(@NonNull T t) {
        Preconditions.checkArgument(t.getClientKey() != null, "This task can not be executed (it's probably a Batch or malformed)");
        boolean containsKey = this.zagy.containsKey(t.getClientKey());
        String name = t.getApi() != null ? t.getApi().getName() : "the API";
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(name).length() + 65);
        stringBuilder.append("GoogleApiClient is not configured to use ");
        stringBuilder.append(name);
        stringBuilder.append(" required for this call.");
        Preconditions.checkArgument(containsKey, stringBuilder.toString());
        this.zaen.lock();
        try {
            if (this.zags == null) {
                throw new IllegalStateException("GoogleApiClient is not connected yet.");
            } else if (this.zagt) {
                this.zafb.add(t);
                while (!this.zafb.isEmpty()) {
                    ApiMethodImpl apiMethodImpl = (ApiMethodImpl) this.zafb.remove();
                    this.zahe.zab(apiMethodImpl);
                    apiMethodImpl.setFailedResult(Status.RESULT_INTERNAL_ERROR);
                }
                return t;
            } else {
                t = this.zags.execute(t);
                this.zaen.unlock();
                return t;
            }
        } finally {
            this.zaen.unlock();
        }
    }

    public final <L> ListenerHolder<L> registerListener(@NonNull L l) {
        this.zaen.lock();
        try {
            l = this.zaha.zaa(l, this.zabj, "NO_TYPE");
            return l;
        } finally {
            this.zaen.unlock();
        }
    }

    @NonNull
    public final <C extends Client> C getClient(@NonNull AnyClientKey<C> anyClientKey) {
        Client client = (Client) this.zagy.get(anyClientKey);
        Preconditions.checkNotNull(client, "Appropriate Api was not requested.");
        return client;
    }

    public final boolean hasApi(@NonNull Api<?> api) {
        return this.zagy.containsKey(api.getClientKey());
    }

    public final boolean hasConnectedApi(@NonNull Api<?> api) {
        if (!isConnected()) {
            return false;
        }
        Client client = (Client) this.zagy.get(api.getClientKey());
        if (client == null || client.isConnected() == null) {
            return false;
        }
        return true;
    }

    @NonNull
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        this.zaen.lock();
        try {
            if (!isConnected()) {
                if (!this.zagt) {
                    throw new IllegalStateException("Cannot invoke getConnectionResult unless GoogleApiClient is connected");
                }
            }
            if (this.zagy.containsKey(api.getClientKey())) {
                ConnectionResult connectionResult = this.zags.getConnectionResult(api);
                if (connectionResult != null) {
                    this.zaen.unlock();
                    return connectionResult;
                } else if (this.zagt) {
                    api = ConnectionResult.RESULT_SUCCESS;
                    return api;
                } else {
                    Log.w("GoogleApiClientImpl", zaay());
                    Log.wtf("GoogleApiClientImpl", String.valueOf(api.getName()).concat(" requested in getConnectionResult is not connected but is not present in the failed  connections map"), new Exception());
                    api = new ConnectionResult(8, null);
                    this.zaen.unlock();
                    return api;
                }
            }
            throw new IllegalArgumentException(String.valueOf(api.getName()).concat(" was never registered with GoogleApiClient"));
        } finally {
            this.zaen.unlock();
        }
    }

    public final void connect() {
        this.zaen.lock();
        try {
            boolean z = false;
            if (this.zaca >= 0) {
                if (this.zahc != null) {
                    z = true;
                }
                Preconditions.checkState(z, "Sign-in mode should have been set explicitly by auto-manage.");
            } else if (this.zahc == null) {
                this.zahc = Integer.valueOf(zaa(this.zagy.values(), false));
            } else if (this.zahc.intValue() == 2) {
                throw new IllegalStateException("Cannot call connect() when SignInMode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            connect(this.zahc.intValue());
        } finally {
            this.zaen.unlock();
        }
    }

    public final void connect(int i) {
        this.zaen.lock();
        boolean z = true;
        if (!(i == 3 || i == 1)) {
            if (i != 2) {
                z = false;
            }
        }
        try {
            StringBuilder stringBuilder = new StringBuilder(33);
            stringBuilder.append("Illegal sign-in mode: ");
            stringBuilder.append(i);
            Preconditions.checkArgument(z, stringBuilder.toString());
            zae(i);
            zaau();
        } finally {
            this.zaen.unlock();
        }
    }

    public final ConnectionResult blockingConnect() {
        boolean z = true;
        Preconditions.checkState(Looper.myLooper() != Looper.getMainLooper(), "blockingConnect must not be called on the UI thread");
        this.zaen.lock();
        try {
            if (this.zaca >= 0) {
                if (this.zahc == null) {
                    z = false;
                }
                Preconditions.checkState(z, "Sign-in mode should have been set explicitly by auto-manage.");
            } else if (this.zahc == null) {
                this.zahc = Integer.valueOf(zaa(this.zagy.values(), false));
            } else if (this.zahc.intValue() == 2) {
                throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            zae(this.zahc.intValue());
            this.zagr.enableCallbacks();
            ConnectionResult blockingConnect = this.zags.blockingConnect();
            return blockingConnect;
        } finally {
            this.zaen.unlock();
        }
    }

    public final ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit) {
        Preconditions.checkState(Looper.myLooper() != Looper.getMainLooper(), "blockingConnect must not be called on the UI thread");
        Preconditions.checkNotNull(timeUnit, "TimeUnit must not be null");
        this.zaen.lock();
        try {
            if (this.zahc == null) {
                this.zahc = Integer.valueOf(zaa(this.zagy.values(), false));
            } else if (this.zahc.intValue() == 2) {
                throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            zae(this.zahc.intValue());
            this.zagr.enableCallbacks();
            j = this.zags.blockingConnect(j, timeUnit);
            return j;
        } finally {
            this.zaen.unlock();
        }
    }

    public final void disconnect() {
        this.zaen.lock();
        try {
            this.zahe.release();
            if (this.zags != null) {
                this.zags.disconnect();
            }
            this.zaha.release();
            for (ApiMethodImpl apiMethodImpl : this.zafb) {
                apiMethodImpl.zaa(null);
                apiMethodImpl.cancel();
            }
            this.zafb.clear();
            if (this.zags != null) {
                zaaw();
                this.zagr.disableCallbacks();
                this.zaen.unlock();
            }
        } finally {
            this.zaen.unlock();
        }
    }

    public final void reconnect() {
        disconnect();
        connect();
    }

    public final PendingResult<Status> clearDefaultAccountAndReconnect() {
        Preconditions.checkState(isConnected(), "GoogleApiClient is not connected yet.");
        Preconditions.checkState(this.zahc.intValue() != 2, "Cannot use clearDefaultAccountAndReconnect with GOOGLE_SIGN_IN_API");
        PendingResult statusPendingResult = new StatusPendingResult((GoogleApiClient) this);
        if (this.zagy.containsKey(Common.CLIENT_KEY)) {
            zaa(this, statusPendingResult, false);
        } else {
            AtomicReference atomicReference = new AtomicReference();
            GoogleApiClient build = new Builder(this.mContext).addApi(Common.API).addConnectionCallbacks(new zaay(this, atomicReference, statusPendingResult)).addOnConnectionFailedListener(new zaaz(this, statusPendingResult)).setHandler(this.zagw).build();
            atomicReference.set(build);
            build.connect();
        }
        return statusPendingResult;
    }

    private final void zaa(GoogleApiClient googleApiClient, StatusPendingResult statusPendingResult, boolean z) {
        Common.zaph.zaa(googleApiClient).setResultCallback(new zaba(this, statusPendingResult, z, googleApiClient));
    }

    public final void stopAutoManage(@NonNull FragmentActivity fragmentActivity) {
        LifecycleActivity lifecycleActivity = new LifecycleActivity((Activity) fragmentActivity);
        if (this.zaca >= null) {
            zaj.zaa(lifecycleActivity).zaa(this.zaca);
            return;
        }
        throw new IllegalStateException("Called stopAutoManage but automatic lifecycle management is not enabled.");
    }

    public final boolean isConnected() {
        return this.zags != null && this.zags.isConnected();
    }

    public final boolean isConnecting() {
        return this.zags != null && this.zags.isConnecting();
    }

    private final void zae(int i) {
        if (this.zahc == null) {
            this.zahc = Integer.valueOf(i);
        } else if (this.zahc.intValue() != i) {
            String zaf = zaf(i);
            String zaf2 = zaf(this.zahc.intValue());
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(zaf).length() + 51) + String.valueOf(zaf2).length());
            stringBuilder.append("Cannot use sign-in mode: ");
            stringBuilder.append(zaf);
            stringBuilder.append(". Mode was already set to ");
            stringBuilder.append(zaf2);
            throw new IllegalStateException(stringBuilder.toString());
        }
        if (this.zags == null) {
            Object obj = null;
            Object obj2 = null;
            for (Client client : this.zagy.values()) {
                if (client.requiresSignIn()) {
                    obj = 1;
                }
                if (client.providesSignIn()) {
                    obj2 = 1;
                }
            }
            switch (this.zahc.intValue()) {
                case 1:
                    if (obj == null) {
                        throw new IllegalStateException("SIGN_IN_MODE_REQUIRED cannot be used on a GoogleApiClient that does not contain any authenticated APIs. Use connect() instead.");
                    } else if (obj2 == null) {
                        break;
                    } else {
                        throw new IllegalStateException("Cannot use SIGN_IN_MODE_REQUIRED with GOOGLE_SIGN_IN_API. Use connect(SIGN_IN_MODE_OPTIONAL) instead.");
                    }
                case 2:
                    if (obj != null) {
                        if (this.zacg) {
                            this.zags = new zax(this.mContext, this.zaen, this.zabj, this.zacc, this.zagy, this.zaes, this.zaev, this.zacd, this.zahb, this, true);
                            return;
                        } else {
                            this.zags = zas.zaa(this.mContext, this, this.zaen, this.zabj, this.zacc, this.zagy, this.zaes, this.zaev, this.zacd, this.zahb);
                            return;
                        }
                    }
                    break;
                case 3:
                    break;
                default:
                    break;
            }
            if (this.zacg && obj2 == null) {
                this.zags = new zax(this.mContext, this.zaen, this.zabj, this.zacc, this.zagy, this.zaes, this.zaev, this.zacd, this.zahb, this, false);
            } else {
                this.zags = new zabe(this.mContext, this, this.zaen, this.zabj, this.zacc, this.zagy, this.zaes, this.zaev, this.zacd, this.zahb, this);
            }
        }
    }

    @GuardedBy("mLock")
    private final void zaau() {
        this.zagr.enableCallbacks();
        this.zags.connect();
    }

    private final void resume() {
        this.zaen.lock();
        try {
            if (this.zagt) {
                zaau();
            }
            this.zaen.unlock();
        } catch (Throwable th) {
            this.zaen.unlock();
        }
    }

    private final void zaav() {
        this.zaen.lock();
        try {
            if (zaaw()) {
                zaau();
            }
            this.zaen.unlock();
        } catch (Throwable th) {
            this.zaen.unlock();
        }
    }

    @GuardedBy("mLock")
    final boolean zaaw() {
        if (!this.zagt) {
            return false;
        }
        this.zagt = false;
        this.zagw.removeMessages(2);
        this.zagw.removeMessages(1);
        if (this.zagx != null) {
            this.zagx.unregister();
            this.zagx = null;
        }
        return true;
    }

    public final void registerConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) {
        this.zagr.registerConnectionCallbacks(connectionCallbacks);
    }

    public final boolean isConnectionCallbacksRegistered(@NonNull ConnectionCallbacks connectionCallbacks) {
        return this.zagr.isConnectionCallbacksRegistered(connectionCallbacks);
    }

    public final void unregisterConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) {
        this.zagr.unregisterConnectionCallbacks(connectionCallbacks);
    }

    public final void registerConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
        this.zagr.registerConnectionFailedListener(onConnectionFailedListener);
    }

    public final boolean isConnectionFailedListenerRegistered(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
        return this.zagr.isConnectionFailedListenerRegistered(onConnectionFailedListener);
    }

    public final void unregisterConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
        this.zagr.unregisterConnectionFailedListener(onConnectionFailedListener);
    }

    @GuardedBy("mLock")
    public final void zab(Bundle bundle) {
        while (!this.zafb.isEmpty()) {
            execute((ApiMethodImpl) this.zafb.remove());
        }
        this.zagr.onConnectionSuccess(bundle);
    }

    @GuardedBy("mLock")
    public final void zac(ConnectionResult connectionResult) {
        if (!this.zacc.isPlayServicesPossiblyUpdating(this.mContext, connectionResult.getErrorCode())) {
            zaaw();
        }
        if (!this.zagt) {
            this.zagr.onConnectionFailure(connectionResult);
            this.zagr.disableCallbacks();
        }
    }

    @GuardedBy("mLock")
    public final void zab(int i, boolean z) {
        if (!(i != 1 || z || this.zagt)) {
            this.zagt = true;
            if (!(this.zagx || ClientLibraryUtils.isPackageSide())) {
                this.zagx = this.zacc.zaa(this.mContext.getApplicationContext(), new zabc(this));
            }
            this.zagw.sendMessageDelayed(this.zagw.obtainMessage(1), this.zagu);
            this.zagw.sendMessageDelayed(this.zagw.obtainMessage(2), this.zagv);
        }
        this.zahe.zabx();
        this.zagr.onUnintentionalDisconnection(i);
        this.zagr.disableCallbacks();
        if (i == 2) {
            zaau();
        }
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final Looper getLooper() {
        return this.zabj;
    }

    public final boolean maybeSignIn(SignInConnectionListener signInConnectionListener) {
        return (this.zags == null || this.zags.maybeSignIn(signInConnectionListener) == null) ? null : true;
    }

    public final void maybeSignOut() {
        if (this.zags != null) {
            this.zags.maybeSignOut();
        }
    }

    public final void zaa(zacm com_google_android_gms_common_api_internal_zacm) {
        this.zaen.lock();
        try {
            if (this.zahd == null) {
                this.zahd = new HashSet();
            }
            this.zahd.add(com_google_android_gms_common_api_internal_zacm);
        } finally {
            this.zaen.unlock();
        }
    }

    public final void zab(zacm com_google_android_gms_common_api_internal_zacm) {
        this.zaen.lock();
        try {
            if (this.zahd == null) {
                Log.wtf("GoogleApiClientImpl", "Attempted to remove pending transform when no transforms are registered.", new Exception());
            } else if (this.zahd.remove(com_google_android_gms_common_api_internal_zacm) == null) {
                Log.wtf("GoogleApiClientImpl", "Failed to remove pending transform - this may lead to memory leaks!", new Exception());
            } else if (zaax() == null) {
                this.zags.zaw();
            }
            this.zaen.unlock();
        } catch (Throwable th) {
            this.zaen.unlock();
        }
    }

    final boolean zaax() {
        this.zaen.lock();
        try {
            if (this.zahd == null) {
                return false;
            }
            boolean isEmpty = this.zahd.isEmpty() ^ 1;
            this.zaen.unlock();
            return isEmpty;
        } finally {
            this.zaen.unlock();
        }
    }

    final String zaay() {
        Writer stringWriter = new StringWriter();
        dump("", null, new PrintWriter(stringWriter), null);
        return stringWriter.toString();
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.append(str).append("mContext=").println(this.mContext);
        printWriter.append(str).append("mResuming=").print(this.zagt);
        printWriter.append(" mWorkQueue.size()=").print(this.zafb.size());
        printWriter.append(" mUnconsumedApiCalls.size()=").println(this.zahe.zaky.size());
        if (this.zags != null) {
            this.zags.dump(str, fileDescriptor, printWriter, strArr);
        }
    }

    public static int zaa(Iterable<Client> iterable, boolean z) {
        Object obj = null;
        Object obj2 = null;
        for (Client client : iterable) {
            if (client.requiresSignIn()) {
                obj = 1;
            }
            if (client.providesSignIn()) {
                obj2 = 1;
            }
        }
        if (obj == null) {
            return 3;
        }
        if (obj2 == null || !z) {
            return 1;
        }
        return 2;
    }
}
