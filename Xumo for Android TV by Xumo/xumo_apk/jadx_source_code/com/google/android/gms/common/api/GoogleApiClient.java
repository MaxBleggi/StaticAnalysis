package com.google.android.gms.common.api;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api.AbstractClientBuilder;
import com.google.android.gms.common.api.Api.AnyClient;
import com.google.android.gms.common.api.Api.AnyClientKey;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.api.Api.ApiOptions.NotRequiredOptions;
import com.google.android.gms.common.api.Api.BaseClientBuilder;
import com.google.android.gms.common.api.Api.Client;
import com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl;
import com.google.android.gms.common.api.internal.LifecycleActivity;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.SignInConnectionListener;
import com.google.android.gms.common.api.internal.zaaw;
import com.google.android.gms.common.api.internal.zacm;
import com.google.android.gms.common.api.internal.zaj;
import com.google.android.gms.common.api.internal.zaq;
import com.google.android.gms.common.internal.AccountType;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.ClientSettings.OptionalApiSettings;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.signin.zaa;
import com.google.android.gms.signin.zad;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.concurrent.GuardedBy;

@KeepForSdk
public abstract class GoogleApiClient {
    @KeepForSdk
    public static final String DEFAULT_ACCOUNT = "<<default account>>";
    public static final int SIGN_IN_MODE_OPTIONAL = 2;
    public static final int SIGN_IN_MODE_REQUIRED = 1;
    @GuardedBy("sAllClients")
    private static final Set<GoogleApiClient> zabq = Collections.newSetFromMap(new WeakHashMap());

    @KeepForSdk
    public static final class Builder {
        private final Context mContext;
        private Looper zabj;
        private final Set<Scope> zabr;
        private final Set<Scope> zabs;
        private int zabt;
        private View zabu;
        private String zabv;
        private String zabw;
        private final Map<Api<?>, OptionalApiSettings> zabx;
        private final Map<Api<?>, ApiOptions> zaby;
        private LifecycleActivity zabz;
        private int zaca;
        private OnConnectionFailedListener zacb;
        private GoogleApiAvailability zacc;
        private AbstractClientBuilder<? extends zad, SignInOptions> zacd;
        private final ArrayList<ConnectionCallbacks> zace;
        private final ArrayList<OnConnectionFailedListener> zacf;
        private boolean zacg;
        private Account zax;

        @KeepForSdk
        public Builder(@NonNull Context context) {
            this.zabr = new HashSet();
            this.zabs = new HashSet();
            this.zabx = new ArrayMap();
            this.zaby = new ArrayMap();
            this.zaca = -1;
            this.zacc = GoogleApiAvailability.getInstance();
            this.zacd = zaa.zapg;
            this.zace = new ArrayList();
            this.zacf = new ArrayList();
            this.zacg = false;
            this.mContext = context;
            this.zabj = context.getMainLooper();
            this.zabv = context.getPackageName();
            this.zabw = context.getClass().getName();
        }

        @KeepForSdk
        public Builder(@NonNull Context context, @NonNull ConnectionCallbacks connectionCallbacks, @NonNull OnConnectionFailedListener onConnectionFailedListener) {
            this(context);
            Preconditions.checkNotNull(connectionCallbacks, "Must provide a connected listener");
            this.zace.add(connectionCallbacks);
            Preconditions.checkNotNull(onConnectionFailedListener, "Must provide a connection failed listener");
            this.zacf.add(onConnectionFailedListener);
        }

        public final Builder setHandler(@NonNull Handler handler) {
            Preconditions.checkNotNull(handler, "Handler must not be null");
            this.zabj = handler.getLooper();
            return this;
        }

        public final Builder addConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) {
            Preconditions.checkNotNull(connectionCallbacks, "Listener must not be null");
            this.zace.add(connectionCallbacks);
            return this;
        }

        public final Builder addOnConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
            Preconditions.checkNotNull(onConnectionFailedListener, "Listener must not be null");
            this.zacf.add(onConnectionFailedListener);
            return this;
        }

        public final Builder setViewForPopups(@NonNull View view) {
            Preconditions.checkNotNull(view, "View must not be null");
            this.zabu = view;
            return this;
        }

        public final Builder addScope(@NonNull Scope scope) {
            Preconditions.checkNotNull(scope, "Scope must not be null");
            this.zabr.add(scope);
            return this;
        }

        @KeepForSdk
        public final Builder addScopeNames(String[] strArr) {
            for (String scope : strArr) {
                this.zabr.add(new Scope(scope));
            }
            return this;
        }

        public final Builder addApi(@NonNull Api<? extends NotRequiredOptions> api) {
            Preconditions.checkNotNull(api, "Api must not be null");
            this.zaby.put(api, null);
            api = api.zah().getImpliedScopes(null);
            this.zabs.addAll(api);
            this.zabr.addAll(api);
            return this;
        }

        public final Builder addApiIfAvailable(@NonNull Api<? extends NotRequiredOptions> api, Scope... scopeArr) {
            Preconditions.checkNotNull(api, "Api must not be null");
            this.zaby.put(api, null);
            zaa(api, null, scopeArr);
            return this;
        }

        public final <O extends HasOptions> Builder addApi(@NonNull Api<O> api, @NonNull O o) {
            Preconditions.checkNotNull(api, "Api must not be null");
            Preconditions.checkNotNull(o, "Null options are not permitted for this Api");
            this.zaby.put(api, o);
            api = api.zah().getImpliedScopes(o);
            this.zabs.addAll(api);
            this.zabr.addAll(api);
            return this;
        }

        public final <O extends HasOptions> Builder addApiIfAvailable(@NonNull Api<O> api, @NonNull O o, Scope... scopeArr) {
            Preconditions.checkNotNull(api, "Api must not be null");
            Preconditions.checkNotNull(o, "Null options are not permitted for this Api");
            this.zaby.put(api, o);
            zaa(api, o, scopeArr);
            return this;
        }

        public final Builder setAccountName(String str) {
            this.zax = str == null ? null : new Account(str, AccountType.GOOGLE);
            return this;
        }

        public final Builder useDefaultAccount() {
            return setAccountName("<<default account>>");
        }

        public final Builder setGravityForPopups(int i) {
            this.zabt = i;
            return this;
        }

        public final Builder enableAutoManage(@NonNull FragmentActivity fragmentActivity, int i, @Nullable OnConnectionFailedListener onConnectionFailedListener) {
            LifecycleActivity lifecycleActivity = new LifecycleActivity((Activity) fragmentActivity);
            Preconditions.checkArgument(i >= 0 ? true : null, "clientId must be non-negative");
            this.zaca = i;
            this.zacb = onConnectionFailedListener;
            this.zabz = lifecycleActivity;
            return this;
        }

        public final Builder enableAutoManage(@NonNull FragmentActivity fragmentActivity, @Nullable OnConnectionFailedListener onConnectionFailedListener) {
            return enableAutoManage(fragmentActivity, 0, onConnectionFailedListener);
        }

        @KeepForSdk
        @VisibleForTesting
        public final ClientSettings buildClientSettings() {
            SignInOptions signInOptions = SignInOptions.DEFAULT;
            if (this.zaby.containsKey(zaa.API)) {
                signInOptions = (SignInOptions) this.zaby.get(zaa.API);
            }
            return new ClientSettings(this.zax, this.zabr, this.zabx, this.zabt, this.zabu, this.zabv, this.zabw, signInOptions);
        }

        public final GoogleApiClient build() {
            Preconditions.checkArgument(this.zaby.isEmpty() ^ true, "must call addApi() to add at least one API");
            ClientSettings buildClientSettings = buildClientSettings();
            Api api = null;
            Map optionalApiSettings = buildClientSettings.getOptionalApiSettings();
            Map arrayMap = new ArrayMap();
            Map arrayMap2 = new ArrayMap();
            ArrayList arrayList = new ArrayList();
            Object obj = null;
            for (Api api2 : this.zaby.keySet()) {
                Object obj2 = r1.zaby.get(api2);
                boolean z = optionalApiSettings.get(api2) != null;
                arrayMap.put(api2, Boolean.valueOf(z));
                ConnectionCallbacks com_google_android_gms_common_api_internal_zaq = new zaq(api2, z);
                arrayList.add(com_google_android_gms_common_api_internal_zaq);
                BaseClientBuilder zai = api2.zai();
                Api api3 = api2;
                Client buildClient = zai.buildClient(r1.mContext, r1.zabj, buildClientSettings, obj2, com_google_android_gms_common_api_internal_zaq, com_google_android_gms_common_api_internal_zaq);
                arrayMap2.put(api3.getClientKey(), buildClient);
                if (zai.getPriority() == 1) {
                    obj = obj2 != null ? 1 : null;
                }
                if (buildClient.providesSignIn()) {
                    if (api == null) {
                        api = api3;
                    } else {
                        String name = api3.getName();
                        String name2 = api.getName();
                        StringBuilder stringBuilder = new StringBuilder((String.valueOf(name).length() + 21) + String.valueOf(name2).length());
                        stringBuilder.append(name);
                        stringBuilder.append(" cannot be used with ");
                        stringBuilder.append(name2);
                        throw new IllegalStateException(stringBuilder.toString());
                    }
                }
            }
            if (api != null) {
                if (obj == null) {
                    Preconditions.checkState(r1.zax == null, "Must not set an account in GoogleApiClient.Builder when using %s. Set account in GoogleSignInOptions.Builder instead", api.getName());
                    Preconditions.checkState(r1.zabr.equals(r1.zabs), "Must not set scopes in GoogleApiClient.Builder when using %s. Set account in GoogleSignInOptions.Builder instead.", api.getName());
                } else {
                    name = api.getName();
                    StringBuilder stringBuilder2 = new StringBuilder(String.valueOf(name).length() + 82);
                    stringBuilder2.append("With using ");
                    stringBuilder2.append(name);
                    stringBuilder2.append(", GamesOptions can only be specified within GoogleSignInOptions.Builder");
                    throw new IllegalStateException(stringBuilder2.toString());
                }
            }
            GoogleApiClient com_google_android_gms_common_api_internal_zaaw = new zaaw(r1.mContext, new ReentrantLock(), r1.zabj, buildClientSettings, r1.zacc, r1.zacd, arrayMap, r1.zace, r1.zacf, arrayMap2, r1.zaca, zaaw.zaa(arrayMap2.values(), true), arrayList, false);
            synchronized (GoogleApiClient.zabq) {
                GoogleApiClient.zabq.add(com_google_android_gms_common_api_internal_zaaw);
            }
            if (r1.zaca >= 0) {
                zaj.zaa(r1.zabz).zaa(r1.zaca, com_google_android_gms_common_api_internal_zaaw, r1.zacb);
            }
            return com_google_android_gms_common_api_internal_zaaw;
        }

        private final <O extends ApiOptions> void zaa(Api<O> api, O o, Scope... scopeArr) {
            Set hashSet = new HashSet(api.zah().getImpliedScopes(o));
            for (Object add : scopeArr) {
                hashSet.add(add);
            }
            this.zabx.put(api, new OptionalApiSettings(hashSet));
        }
    }

    public interface ConnectionCallbacks {
        public static final int CAUSE_NETWORK_LOST = 2;
        public static final int CAUSE_SERVICE_DISCONNECTED = 1;

        void onConnected(@Nullable Bundle bundle);

        void onConnectionSuspended(int i);
    }

    public interface OnConnectionFailedListener {
        void onConnectionFailed(@NonNull ConnectionResult connectionResult);
    }

    public abstract ConnectionResult blockingConnect();

    public abstract ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit);

    public abstract PendingResult<Status> clearDefaultAccountAndReconnect();

    public abstract void connect();

    public abstract void disconnect();

    public abstract void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    @NonNull
    public abstract ConnectionResult getConnectionResult(@NonNull Api<?> api);

    public abstract boolean hasConnectedApi(@NonNull Api<?> api);

    public abstract boolean isConnected();

    public abstract boolean isConnecting();

    public abstract boolean isConnectionCallbacksRegistered(@NonNull ConnectionCallbacks connectionCallbacks);

    public abstract boolean isConnectionFailedListenerRegistered(@NonNull OnConnectionFailedListener onConnectionFailedListener);

    public abstract void reconnect();

    public abstract void registerConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks);

    public abstract void registerConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener);

    public abstract void stopAutoManage(@NonNull FragmentActivity fragmentActivity);

    public abstract void unregisterConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks);

    public abstract void unregisterConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener);

    public static void dumpAll(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        synchronized (zabq) {
            int i = 0;
            String concat = String.valueOf(str).concat("  ");
            for (GoogleApiClient googleApiClient : zabq) {
                int i2 = i + 1;
                printWriter.append(str).append("GoogleApiClient#").println(i);
                googleApiClient.dump(concat, fileDescriptor, printWriter, strArr);
                i = i2;
            }
        }
    }

    @KeepForSdk
    public static Set<GoogleApiClient> getAllClients() {
        Set<GoogleApiClient> set;
        synchronized (zabq) {
            set = zabq;
        }
        return set;
    }

    @KeepForSdk
    public <A extends AnyClient, R extends Result, T extends ApiMethodImpl<R, A>> T enqueue(@NonNull T t) {
        throw new UnsupportedOperationException();
    }

    @KeepForSdk
    public <A extends AnyClient, T extends ApiMethodImpl<? extends Result, A>> T execute(@NonNull T t) {
        throw new UnsupportedOperationException();
    }

    @KeepForSdk
    public <L> ListenerHolder<L> registerListener(@NonNull L l) {
        throw new UnsupportedOperationException();
    }

    @NonNull
    @KeepForSdk
    public <C extends Client> C getClient(@NonNull AnyClientKey<C> anyClientKey) {
        throw new UnsupportedOperationException();
    }

    @KeepForSdk
    public boolean hasApi(@NonNull Api<?> api) {
        throw new UnsupportedOperationException();
    }

    @KeepForSdk
    public Context getContext() {
        throw new UnsupportedOperationException();
    }

    @KeepForSdk
    public Looper getLooper() {
        throw new UnsupportedOperationException();
    }

    @KeepForSdk
    public boolean maybeSignIn(SignInConnectionListener signInConnectionListener) {
        throw new UnsupportedOperationException();
    }

    @KeepForSdk
    public void maybeSignOut() {
        throw new UnsupportedOperationException();
    }

    public void connect(int i) {
        throw new UnsupportedOperationException();
    }

    public void zaa(zacm com_google_android_gms_common_api_internal_zacm) {
        throw new UnsupportedOperationException();
    }

    public void zab(zacm com_google_android_gms_common_api_internal_zacm) {
        throw new UnsupportedOperationException();
    }
}
