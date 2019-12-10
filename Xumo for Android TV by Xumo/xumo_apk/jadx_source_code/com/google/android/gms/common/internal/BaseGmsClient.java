package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import androidx.annotation.BinderThread;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.util.VisibleForTesting;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.concurrent.GuardedBy;

@KeepForSdk
public abstract class BaseGmsClient<T extends IInterface> {
    @KeepForSdk
    public static final int CONNECT_STATE_CONNECTED = 4;
    @KeepForSdk
    public static final int CONNECT_STATE_DISCONNECTED = 1;
    @KeepForSdk
    public static final int CONNECT_STATE_DISCONNECTING = 5;
    @KeepForSdk
    public static final String DEFAULT_ACCOUNT = "<<default account>>";
    @KeepForSdk
    public static final String[] GOOGLE_PLUS_REQUIRED_FEATURES = new String[]{"service_esmobile", "service_googleme"};
    @KeepForSdk
    public static final String KEY_PENDING_INTENT = "pendingIntent";
    private static final Feature[] zzbs = new Feature[0];
    private final Context mContext;
    final Handler mHandler;
    private final Object mLock;
    private int zzbt;
    private long zzbu;
    private long zzbv;
    private int zzbw;
    private long zzbx;
    @VisibleForTesting
    private zzh zzby;
    private final Looper zzbz;
    private final GmsClientSupervisor zzca;
    private final GoogleApiAvailabilityLight zzcb;
    private final Object zzcc;
    @GuardedBy("mServiceBrokerLock")
    private IGmsServiceBroker zzcd;
    @VisibleForTesting
    protected ConnectionProgressReportCallbacks zzce;
    @GuardedBy("mLock")
    private T zzcf;
    private final ArrayList<zzc<?>> zzcg;
    @GuardedBy("mLock")
    private zze zzch;
    @GuardedBy("mLock")
    private int zzci;
    private final BaseConnectionCallbacks zzcj;
    private final BaseOnConnectionFailedListener zzck;
    private final int zzcl;
    private final String zzcm;
    private ConnectionResult zzcn;
    private boolean zzco;
    private volatile zzb zzcp;
    @VisibleForTesting
    protected AtomicInteger zzcq;

    @KeepForSdk
    public interface BaseConnectionCallbacks {
        @KeepForSdk
        void onConnected(@Nullable Bundle bundle);

        @KeepForSdk
        void onConnectionSuspended(int i);
    }

    @KeepForSdk
    public interface BaseOnConnectionFailedListener {
        void onConnectionFailed(@NonNull ConnectionResult connectionResult);
    }

    @KeepForSdk
    public interface ConnectionProgressReportCallbacks {
        @KeepForSdk
        void onReportServiceBinding(@NonNull ConnectionResult connectionResult);
    }

    @KeepForSdk
    public interface SignOutCallbacks {
        @KeepForSdk
        void onSignOutComplete();
    }

    protected abstract class zzc<TListener> {
        private final /* synthetic */ BaseGmsClient zzcs;
        private TListener zzct;
        private boolean zzcu = null;

        public zzc(BaseGmsClient baseGmsClient, TListener tListener) {
            this.zzcs = baseGmsClient;
            this.zzct = tListener;
        }

        protected abstract void zza(TListener tListener);

        protected abstract void zzn();

        public final void zzo() {
            synchronized (this) {
                Object obj = this.zzct;
                if (this.zzcu) {
                    String valueOf = String.valueOf(this);
                    StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 47);
                    stringBuilder.append("Callback proxy ");
                    stringBuilder.append(valueOf);
                    stringBuilder.append(" being reused. This is not safe.");
                    Log.w("GmsClient", stringBuilder.toString());
                }
            }
            if (obj != null) {
                try {
                    zza(obj);
                } catch (RuntimeException e) {
                    zzn();
                    throw e;
                }
            }
            zzn();
            synchronized (this) {
                this.zzcu = true;
            }
            unregister();
        }

        public final void unregister() {
            removeListener();
            synchronized (this.zzcs.zzcg) {
                this.zzcs.zzcg.remove(this);
            }
        }

        public final void removeListener() {
            synchronized (this) {
                this.zzct = null;
            }
        }
    }

    @VisibleForTesting
    public final class zze implements ServiceConnection {
        private final /* synthetic */ BaseGmsClient zzcs;
        private final int zzcw;

        public zze(BaseGmsClient baseGmsClient, int i) {
            this.zzcs = baseGmsClient;
            this.zzcw = i;
        }

        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (iBinder == null) {
                this.zzcs.zzb(16);
                return;
            }
            synchronized (this.zzcs.zzcc) {
                IGmsServiceBroker iGmsServiceBroker;
                BaseGmsClient baseGmsClient = this.zzcs;
                if (iBinder == null) {
                    iGmsServiceBroker = null;
                } else {
                    IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                    if (queryLocalInterface == null || !(queryLocalInterface instanceof IGmsServiceBroker)) {
                        iGmsServiceBroker = new zza(iBinder);
                    } else {
                        iGmsServiceBroker = (IGmsServiceBroker) queryLocalInterface;
                    }
                }
                baseGmsClient.zzcd = iGmsServiceBroker;
            }
            this.zzcs.zza(0, null, this.zzcw);
        }

        public final void onServiceDisconnected(ComponentName componentName) {
            synchronized (this.zzcs.zzcc) {
                this.zzcs.zzcd = null;
            }
            this.zzcs.mHandler.sendMessage(this.zzcs.mHandler.obtainMessage(6, this.zzcw, 1));
        }
    }

    protected class LegacyClientCallbackAdapter implements ConnectionProgressReportCallbacks {
        private final /* synthetic */ BaseGmsClient zzcs;

        @KeepForSdk
        public LegacyClientCallbackAdapter(BaseGmsClient baseGmsClient) {
            this.zzcs = baseGmsClient;
        }

        public void onReportServiceBinding(@NonNull ConnectionResult connectionResult) {
            if (connectionResult.isSuccess()) {
                this.zzcs.getRemoteService(null, this.zzcs.getScopes());
                return;
            }
            if (this.zzcs.zzck != null) {
                this.zzcs.zzck.onConnectionFailed(connectionResult);
            }
        }
    }

    private abstract class zza extends zzc<Boolean> {
        private final int statusCode;
        private final Bundle zzcr;
        private final /* synthetic */ BaseGmsClient zzcs;

        @BinderThread
        protected zza(BaseGmsClient baseGmsClient, int i, Bundle bundle) {
            this.zzcs = baseGmsClient;
            super(baseGmsClient, Boolean.valueOf(true));
            this.statusCode = i;
            this.zzcr = bundle;
        }

        protected abstract void zza(ConnectionResult connectionResult);

        protected abstract boolean zzm();

        protected final void zzn() {
        }

        protected final /* synthetic */ void zza(Object obj) {
            PendingIntent pendingIntent = null;
            if (((Boolean) obj) == null) {
                this.zzcs.zza(1, null);
                return;
            }
            obj = this.statusCode;
            if (obj != null) {
                if (obj != 10) {
                    this.zzcs.zza(1, null);
                    if (this.zzcr != null) {
                        pendingIntent = (PendingIntent) this.zzcr.getParcelable(BaseGmsClient.KEY_PENDING_INTENT);
                    }
                    zza(new ConnectionResult(this.statusCode, pendingIntent));
                } else {
                    this.zzcs.zza(1, null);
                    throw new IllegalStateException(String.format("A fatal developer error has occurred. Class name: %s. Start service action: %s. Service Descriptor: %s. ", new Object[]{getClass().getSimpleName(), this.zzcs.getStartServiceAction(), this.zzcs.getServiceDescriptor()}));
                }
            } else if (zzm() == null) {
                this.zzcs.zza(1, null);
                zza(new ConnectionResult(8, null));
            }
        }
    }

    final class zzb extends com.google.android.gms.internal.common.zze {
        private final /* synthetic */ BaseGmsClient zzcs;

        public zzb(BaseGmsClient baseGmsClient, Looper looper) {
            this.zzcs = baseGmsClient;
            super(looper);
        }

        public final void handleMessage(Message message) {
            if (this.zzcs.zzcq.get() != message.arg1) {
                if (zzb(message)) {
                    zza(message);
                }
            } else if ((message.what == 1 || message.what == 7 || message.what == 4 || message.what == 5) && !this.zzcs.isConnecting()) {
                zza(message);
            } else {
                PendingIntent pendingIntent = null;
                if (message.what == 4) {
                    this.zzcs.zzcn = new ConnectionResult(message.arg2);
                    if (this.zzcs.zzl() == null || this.zzcs.zzco != null) {
                        if (this.zzcs.zzcn != null) {
                            message = this.zzcs.zzcn;
                        } else {
                            message = new ConnectionResult(8);
                        }
                        this.zzcs.zzce.onReportServiceBinding(message);
                        this.zzcs.onConnectionFailed(message);
                        return;
                    }
                    this.zzcs.zza(3, null);
                } else if (message.what == 5) {
                    if (this.zzcs.zzcn != null) {
                        message = this.zzcs.zzcn;
                    } else {
                        message = new ConnectionResult(8);
                    }
                    this.zzcs.zzce.onReportServiceBinding(message);
                    this.zzcs.onConnectionFailed(message);
                } else if (message.what == 3) {
                    if (message.obj instanceof PendingIntent) {
                        pendingIntent = (PendingIntent) message.obj;
                    }
                    ConnectionResult connectionResult = new ConnectionResult(message.arg2, pendingIntent);
                    this.zzcs.zzce.onReportServiceBinding(connectionResult);
                    this.zzcs.onConnectionFailed(connectionResult);
                } else if (message.what == 6) {
                    this.zzcs.zza(5, null);
                    if (this.zzcs.zzcj != null) {
                        this.zzcs.zzcj.onConnectionSuspended(message.arg2);
                    }
                    this.zzcs.onConnectionSuspended(message.arg2);
                    this.zzcs.zza(5, 1, null);
                } else if (message.what == 2 && !this.zzcs.isConnected()) {
                    zza(message);
                } else if (zzb(message)) {
                    ((zzc) message.obj).zzo();
                } else {
                    message = message.what;
                    StringBuilder stringBuilder = new StringBuilder(45);
                    stringBuilder.append("Don't know how to handle message: ");
                    stringBuilder.append(message);
                    Log.wtf("GmsClient", stringBuilder.toString(), new Exception());
                }
            }
        }

        private static void zza(Message message) {
            zzc com_google_android_gms_common_internal_BaseGmsClient_zzc = (zzc) message.obj;
            com_google_android_gms_common_internal_BaseGmsClient_zzc.zzn();
            com_google_android_gms_common_internal_BaseGmsClient_zzc.unregister();
        }

        private static boolean zzb(Message message) {
            if (!(message.what == 2 || message.what == 1)) {
                if (message.what != 7) {
                    return null;
                }
            }
            return true;
        }
    }

    @VisibleForTesting
    public static final class zzd extends com.google.android.gms.common.internal.IGmsCallbacks.zza {
        private BaseGmsClient zzcv;
        private final int zzcw;

        public zzd(@NonNull BaseGmsClient baseGmsClient, int i) {
            this.zzcv = baseGmsClient;
            this.zzcw = i;
        }

        @BinderThread
        public final void zza(int i, @Nullable Bundle bundle) {
            Log.wtf("GmsClient", "received deprecated onAccountValidationComplete callback, ignoring", new Exception());
        }

        @BinderThread
        public final void onPostInitComplete(int i, @NonNull IBinder iBinder, @Nullable Bundle bundle) {
            Preconditions.checkNotNull(this.zzcv, "onPostInitComplete can be called only once per call to getRemoteService");
            this.zzcv.onPostInitHandler(i, iBinder, bundle, this.zzcw);
            this.zzcv = 0;
        }

        @BinderThread
        public final void zza(int i, @NonNull IBinder iBinder, @NonNull zzb com_google_android_gms_common_internal_zzb) {
            Preconditions.checkNotNull(this.zzcv, "onPostInitCompleteWithConnectionInfo can be called only once per call togetRemoteService");
            Preconditions.checkNotNull(com_google_android_gms_common_internal_zzb);
            this.zzcv.zza(com_google_android_gms_common_internal_zzb);
            onPostInitComplete(i, iBinder, com_google_android_gms_common_internal_zzb.zzcz);
        }
    }

    protected final class zzf extends zza {
        private final /* synthetic */ BaseGmsClient zzcs;
        private final IBinder zzcx;

        @BinderThread
        public zzf(BaseGmsClient baseGmsClient, int i, IBinder iBinder, Bundle bundle) {
            this.zzcs = baseGmsClient;
            super(baseGmsClient, i, bundle);
            this.zzcx = iBinder;
        }

        protected final void zza(ConnectionResult connectionResult) {
            if (this.zzcs.zzck != null) {
                this.zzcs.zzck.onConnectionFailed(connectionResult);
            }
            this.zzcs.onConnectionFailed(connectionResult);
        }

        protected final boolean zzm() {
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
            r6 = this;
            r0 = 0;
            r1 = r6.zzcx;	 Catch:{ RemoteException -> 0x0088 }
            r1 = r1.getInterfaceDescriptor();	 Catch:{ RemoteException -> 0x0088 }
            r2 = r6.zzcs;
            r2 = r2.getServiceDescriptor();
            r2 = r2.equals(r1);
            if (r2 != 0) goto L_0x004b;
        L_0x0013:
            r2 = "GmsClient";
            r3 = r6.zzcs;
            r3 = r3.getServiceDescriptor();
            r4 = java.lang.String.valueOf(r3);
            r4 = r4.length();
            r4 = r4 + 34;
            r5 = java.lang.String.valueOf(r1);
            r5 = r5.length();
            r4 = r4 + r5;
            r5 = new java.lang.StringBuilder;
            r5.<init>(r4);
            r4 = "service descriptor mismatch: ";
            r5.append(r4);
            r5.append(r3);
            r3 = " vs. ";
            r5.append(r3);
            r5.append(r1);
            r1 = r5.toString();
            android.util.Log.e(r2, r1);
            return r0;
        L_0x004b:
            r1 = r6.zzcs;
            r2 = r6.zzcx;
            r1 = r1.createServiceInterface(r2);
            if (r1 == 0) goto L_0x0087;
        L_0x0055:
            r2 = r6.zzcs;
            r3 = 2;
            r4 = 4;
            r2 = r2.zza(r3, r4, r1);
            if (r2 != 0) goto L_0x0068;
        L_0x005f:
            r2 = r6.zzcs;
            r3 = 3;
            r1 = r2.zza(r3, r4, r1);
            if (r1 == 0) goto L_0x0087;
        L_0x0068:
            r0 = r6.zzcs;
            r1 = 0;
            r0.zzcn = r1;
            r0 = r6.zzcs;
            r0 = r0.getConnectionHint();
            r1 = r6.zzcs;
            r1 = r1.zzcj;
            if (r1 == 0) goto L_0x0085;
        L_0x007c:
            r1 = r6.zzcs;
            r1 = r1.zzcj;
            r1.onConnected(r0);
        L_0x0085:
            r0 = 1;
            return r0;
        L_0x0087:
            return r0;
        L_0x0088:
            r1 = "GmsClient";
            r2 = "service probably died";
            android.util.Log.w(r1, r2);
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.BaseGmsClient.zzf.zzm():boolean");
        }
    }

    protected final class zzg extends zza {
        private final /* synthetic */ BaseGmsClient zzcs;

        @BinderThread
        public zzg(BaseGmsClient baseGmsClient, @Nullable int i, Bundle bundle) {
            this.zzcs = baseGmsClient;
            super(baseGmsClient, i, null);
        }

        protected final void zza(ConnectionResult connectionResult) {
            this.zzcs.zzce.onReportServiceBinding(connectionResult);
            this.zzcs.onConnectionFailed(connectionResult);
        }

        protected final boolean zzm() {
            this.zzcs.zzce.onReportServiceBinding(ConnectionResult.RESULT_SUCCESS);
            return true;
        }
    }

    @KeepForSdk
    @Nullable
    protected abstract T createServiceInterface(IBinder iBinder);

    @KeepForSdk
    public Account getAccount() {
        return null;
    }

    @KeepForSdk
    public Bundle getConnectionHint() {
        return null;
    }

    @KeepForSdk
    @Nullable
    protected String getLocalStartServiceAction() {
        return null;
    }

    @NonNull
    @KeepForSdk
    protected abstract String getServiceDescriptor();

    @NonNull
    @KeepForSdk
    protected abstract String getStartServiceAction();

    @KeepForSdk
    protected String getStartServicePackage() {
        return "com.google.android.gms";
    }

    @KeepForSdk
    void onSetConnectState(int i, T t) {
    }

    @KeepForSdk
    public boolean providesSignIn() {
        return false;
    }

    @KeepForSdk
    public boolean requiresAccount() {
        return false;
    }

    @KeepForSdk
    public boolean requiresGooglePlayServices() {
        return true;
    }

    @KeepForSdk
    public boolean requiresSignIn() {
        return false;
    }

    @KeepForSdk
    protected BaseGmsClient(Context context, Looper looper, int i, BaseConnectionCallbacks baseConnectionCallbacks, BaseOnConnectionFailedListener baseOnConnectionFailedListener, String str) {
        this(context, looper, GmsClientSupervisor.getInstance(context), GoogleApiAvailabilityLight.getInstance(), i, (BaseConnectionCallbacks) Preconditions.checkNotNull(baseConnectionCallbacks), (BaseOnConnectionFailedListener) Preconditions.checkNotNull(baseOnConnectionFailedListener), str);
    }

    @KeepForSdk
    @VisibleForTesting
    protected BaseGmsClient(Context context, Looper looper, GmsClientSupervisor gmsClientSupervisor, GoogleApiAvailabilityLight googleApiAvailabilityLight, int i, BaseConnectionCallbacks baseConnectionCallbacks, BaseOnConnectionFailedListener baseOnConnectionFailedListener, String str) {
        this.mLock = new Object();
        this.zzcc = new Object();
        this.zzcg = new ArrayList();
        this.zzci = 1;
        this.zzcn = null;
        this.zzco = false;
        this.zzcp = null;
        this.zzcq = new AtomicInteger(0);
        this.mContext = (Context) Preconditions.checkNotNull(context, "Context must not be null");
        this.zzbz = (Looper) Preconditions.checkNotNull(looper, "Looper must not be null");
        this.zzca = (GmsClientSupervisor) Preconditions.checkNotNull(gmsClientSupervisor, "Supervisor must not be null");
        this.zzcb = (GoogleApiAvailabilityLight) Preconditions.checkNotNull(googleApiAvailabilityLight, "API availability must not be null");
        this.mHandler = new zzb(this, looper);
        this.zzcl = i;
        this.zzcj = baseConnectionCallbacks;
        this.zzck = baseOnConnectionFailedListener;
        this.zzcm = str;
    }

    @KeepForSdk
    @VisibleForTesting
    protected BaseGmsClient(Context context, Handler handler, GmsClientSupervisor gmsClientSupervisor, GoogleApiAvailabilityLight googleApiAvailabilityLight, int i, BaseConnectionCallbacks baseConnectionCallbacks, BaseOnConnectionFailedListener baseOnConnectionFailedListener) {
        this.mLock = new Object();
        this.zzcc = new Object();
        this.zzcg = new ArrayList();
        this.zzci = 1;
        this.zzcn = null;
        this.zzco = false;
        this.zzcp = null;
        this.zzcq = new AtomicInteger(0);
        this.mContext = (Context) Preconditions.checkNotNull(context, "Context must not be null");
        this.mHandler = (Handler) Preconditions.checkNotNull(handler, "Handler must not be null");
        this.zzbz = handler.getLooper();
        this.zzca = (GmsClientSupervisor) Preconditions.checkNotNull(gmsClientSupervisor, "Supervisor must not be null");
        this.zzcb = (GoogleApiAvailabilityLight) Preconditions.checkNotNull(googleApiAvailabilityLight, "API availability must not be null");
        this.zzcl = i;
        this.zzcj = baseConnectionCallbacks;
        this.zzck = baseOnConnectionFailedListener;
        this.zzcm = null;
    }

    @Nullable
    private final String zzj() {
        return this.zzcm == null ? this.mContext.getClass().getName() : this.zzcm;
    }

    private final void zza(zzb com_google_android_gms_common_internal_zzb) {
        this.zzcp = com_google_android_gms_common_internal_zzb;
    }

    @KeepForSdk
    @Nullable
    public final Feature[] getAvailableFeatures() {
        zzb com_google_android_gms_common_internal_zzb = this.zzcp;
        if (com_google_android_gms_common_internal_zzb == null) {
            return null;
        }
        return com_google_android_gms_common_internal_zzb.zzda;
    }

    @CallSuper
    @KeepForSdk
    protected void onConnectedLocked(@NonNull T t) {
        this.zzbv = System.currentTimeMillis();
    }

    @CallSuper
    @KeepForSdk
    protected void onConnectionSuspended(int i) {
        this.zzbt = i;
        this.zzbu = System.currentTimeMillis();
    }

    @CallSuper
    @KeepForSdk
    protected void onConnectionFailed(ConnectionResult connectionResult) {
        this.zzbw = connectionResult.getErrorCode();
        this.zzbx = System.currentTimeMillis();
    }

    private final void zza(int i, T t) {
        Preconditions.checkArgument((i == 4 ? 1 : null) == (t != null ? 1 : null));
        synchronized (this.mLock) {
            this.zzci = i;
            this.zzcf = t;
            onSetConnectState(i, t);
            switch (i) {
                case 1:
                    if (this.zzch != 0) {
                        this.zzca.zza(getStartServiceAction(), getStartServicePackage(), TsExtractor.TS_STREAM_TYPE_AC3, this.zzch, zzj());
                        this.zzch = null;
                        break;
                    }
                    break;
                case 2:
                case 3:
                    if (!(this.zzch == 0 || this.zzby == 0)) {
                        t = this.zzby.zzt();
                        String packageName = this.zzby.getPackageName();
                        StringBuilder stringBuilder = new StringBuilder((String.valueOf(t).length() + 70) + String.valueOf(packageName).length());
                        stringBuilder.append("Calling connect() while still connected, missing disconnect() for ");
                        stringBuilder.append(t);
                        stringBuilder.append(" on ");
                        stringBuilder.append(packageName);
                        Log.e("GmsClient", stringBuilder.toString());
                        this.zzca.zza(this.zzby.zzt(), this.zzby.getPackageName(), this.zzby.zzq(), this.zzch, zzj());
                        this.zzcq.incrementAndGet();
                    }
                    this.zzch = new zze(this, this.zzcq.get());
                    if (this.zzci != 3 || getLocalStartServiceAction() == 0) {
                        i = new zzh(getStartServicePackage(), getStartServiceAction(), false, TsExtractor.TS_STREAM_TYPE_AC3);
                    } else {
                        i = new zzh(getContext().getPackageName(), getLocalStartServiceAction(), true, TsExtractor.TS_STREAM_TYPE_AC3);
                    }
                    this.zzby = i;
                    if (this.zzca.zza(new zza(this.zzby.zzt(), this.zzby.getPackageName(), this.zzby.zzq()), this.zzch, zzj()) == 0) {
                        t = this.zzby.zzt();
                        String packageName2 = this.zzby.getPackageName();
                        StringBuilder stringBuilder2 = new StringBuilder((String.valueOf(t).length() + 34) + String.valueOf(packageName2).length());
                        stringBuilder2.append("unable to connect to service: ");
                        stringBuilder2.append(t);
                        stringBuilder2.append(" on ");
                        stringBuilder2.append(packageName2);
                        Log.e("GmsClient", stringBuilder2.toString());
                        zza(16, null, this.zzcq.get());
                        break;
                    }
                    break;
                case 4:
                    onConnectedLocked(t);
                    break;
                default:
                    break;
            }
        }
    }

    private final boolean zza(int i, int i2, T t) {
        synchronized (this.mLock) {
            if (this.zzci != i) {
                return false;
            }
            zza(i2, (IInterface) t);
            return true;
        }
    }

    @KeepForSdk
    public void checkAvailabilityAndConnect() {
        int isGooglePlayServicesAvailable = this.zzcb.isGooglePlayServicesAvailable(this.mContext, getMinApkVersion());
        if (isGooglePlayServicesAvailable != 0) {
            zza(1, null);
            triggerNotAvailable(new LegacyClientCallbackAdapter(this), isGooglePlayServicesAvailable, null);
            return;
        }
        connect(new LegacyClientCallbackAdapter(this));
    }

    @KeepForSdk
    public void connect(@NonNull ConnectionProgressReportCallbacks connectionProgressReportCallbacks) {
        this.zzce = (ConnectionProgressReportCallbacks) Preconditions.checkNotNull(connectionProgressReportCallbacks, "Connection progress callbacks cannot be null.");
        zza(2, null);
    }

    @KeepForSdk
    public boolean isConnected() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzci == 4;
        }
        return z;
    }

    @KeepForSdk
    public boolean isConnecting() {
        boolean z;
        synchronized (this.mLock) {
            if (this.zzci != 2) {
                if (this.zzci != 3) {
                    z = false;
                }
            }
            z = true;
        }
        return z;
    }

    private final boolean zzk() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzci == 3;
        }
        return z;
    }

    @KeepForSdk
    public void disconnect() {
        this.zzcq.incrementAndGet();
        synchronized (this.zzcg) {
            int size = this.zzcg.size();
            for (int i = 0; i < size; i++) {
                ((zzc) this.zzcg.get(i)).removeListener();
            }
            this.zzcg.clear();
        }
        synchronized (this.zzcc) {
            this.zzcd = null;
        }
        zza(1, null);
    }

    @KeepForSdk
    public void triggerConnectionSuspended(int i) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(6, this.zzcq.get(), i));
    }

    private final void zzb(int i) {
        if (zzk() != 0) {
            i = 5;
            this.zzco = true;
        } else {
            i = 4;
        }
        this.mHandler.sendMessage(this.mHandler.obtainMessage(i, this.zzcq.get(), 16));
    }

    @KeepForSdk
    @VisibleForTesting
    protected void triggerNotAvailable(@NonNull ConnectionProgressReportCallbacks connectionProgressReportCallbacks, int i, @Nullable PendingIntent pendingIntent) {
        this.zzce = (ConnectionProgressReportCallbacks) Preconditions.checkNotNull(connectionProgressReportCallbacks, "Connection progress callbacks cannot be null.");
        this.mHandler.sendMessage(this.mHandler.obtainMessage(3, this.zzcq.get(), i, pendingIntent));
    }

    @KeepForSdk
    public final Context getContext() {
        return this.mContext;
    }

    @KeepForSdk
    public final Looper getLooper() {
        return this.zzbz;
    }

    @KeepForSdk
    public Feature[] getApiFeatures() {
        return zzbs;
    }

    @KeepForSdk
    protected Bundle getGetServiceRequestExtraArgs() {
        return new Bundle();
    }

    @KeepForSdk
    protected void onPostInitHandler(int i, IBinder iBinder, Bundle bundle, int i2) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1, i2, -1, new zzf(this, i, iBinder, bundle)));
    }

    protected final void zza(int i, @Nullable Bundle bundle, int i2) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(7, i2, -1, new zzg(this, i, null)));
    }

    @KeepForSdk
    protected final void checkConnected() {
        if (!isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    @KeepForSdk
    public final T getService() throws DeadObjectException {
        T t;
        synchronized (this.mLock) {
            if (this.zzci != 5) {
                checkConnected();
                Preconditions.checkState(this.zzcf != null, "Client is connected but service is null");
                t = this.zzcf;
            } else {
                throw new DeadObjectException();
            }
        }
        return t;
    }

    @WorkerThread
    @KeepForSdk
    public void getRemoteService(IAccountAccessor iAccountAccessor, Set<Scope> set) {
        Bundle getServiceRequestExtraArgs = getGetServiceRequestExtraArgs();
        GetServiceRequest getServiceRequest = new GetServiceRequest(this.zzcl);
        getServiceRequest.zzdh = this.mContext.getPackageName();
        getServiceRequest.zzdk = getServiceRequestExtraArgs;
        if (set != null) {
            getServiceRequest.zzdj = (Scope[]) set.toArray(new Scope[set.size()]);
        }
        if (requiresSignIn() != null) {
            getServiceRequest.zzdl = getAccount() != null ? getAccount() : new Account("<<default account>>", AccountType.GOOGLE);
            if (iAccountAccessor != null) {
                getServiceRequest.zzdi = iAccountAccessor.asBinder();
            }
        } else if (requiresAccount() != null) {
            getServiceRequest.zzdl = getAccount();
        }
        getServiceRequest.zzdm = zzbs;
        getServiceRequest.zzdn = getApiFeatures();
        try {
            synchronized (this.zzcc) {
                if (this.zzcd != null) {
                    this.zzcd.getService(new zzd(this, this.zzcq.get()), getServiceRequest);
                } else {
                    Log.w("GmsClient", "mServiceBroker is null, client disconnected");
                }
            }
        } catch (IAccountAccessor iAccountAccessor2) {
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", iAccountAccessor2);
            triggerConnectionSuspended(1);
        } catch (IAccountAccessor iAccountAccessor22) {
            throw iAccountAccessor22;
        } catch (IAccountAccessor iAccountAccessor222) {
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", iAccountAccessor222);
            onPostInitHandler(8, null, null, this.zzcq.get());
        }
    }

    @KeepForSdk
    public void onUserSignOut(@NonNull SignOutCallbacks signOutCallbacks) {
        signOutCallbacks.onSignOutComplete();
    }

    @KeepForSdk
    public Intent getSignInIntent() {
        throw new UnsupportedOperationException("Not a sign in API");
    }

    @KeepForSdk
    protected Set<Scope> getScopes() {
        return Collections.EMPTY_SET;
    }

    @KeepForSdk
    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        synchronized (this.mLock) {
            strArr = this.zzci;
            IInterface iInterface = this.zzcf;
        }
        synchronized (this.zzcc) {
            fileDescriptor = this.zzcd;
        }
        printWriter.append(str).append("mConnectState=");
        switch (strArr) {
            case 1:
                printWriter.print("DISCONNECTED");
                break;
            case 2:
                printWriter.print("REMOTE_CONNECTING");
                break;
            case 3:
                printWriter.print("LOCAL_CONNECTING");
                break;
            case 4:
                printWriter.print("CONNECTED");
                break;
            case 5:
                printWriter.print("DISCONNECTING");
                break;
            default:
                printWriter.print("UNKNOWN");
                break;
        }
        printWriter.append(" mService=");
        if (iInterface == null) {
            printWriter.append("null");
        } else {
            printWriter.append(getServiceDescriptor()).append("@").append(Integer.toHexString(System.identityHashCode(iInterface.asBinder())));
        }
        printWriter.append(" mServiceBroker=");
        if (fileDescriptor == null) {
            printWriter.println("null");
        } else {
            printWriter.append("IGmsServiceBroker@").println(Integer.toHexString(System.identityHashCode(fileDescriptor.asBinder())));
        }
        fileDescriptor = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
        if (this.zzbv > 0) {
            strArr = printWriter.append(str).append("lastConnectedTime=");
            long j = this.zzbv;
            String format = fileDescriptor.format(new Date(this.zzbv));
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(format).length() + 21);
            stringBuilder.append(j);
            stringBuilder.append(" ");
            stringBuilder.append(format);
            strArr.println(stringBuilder.toString());
        }
        if (this.zzbu > 0) {
            printWriter.append(str).append("lastSuspendedCause=");
            switch (this.zzbt) {
                case 1:
                    printWriter.append("CAUSE_SERVICE_DISCONNECTED");
                    break;
                case 2:
                    printWriter.append("CAUSE_NETWORK_LOST");
                    break;
                default:
                    printWriter.append(String.valueOf(this.zzbt));
                    break;
            }
            strArr = printWriter.append(" lastSuspendedTime=");
            j = this.zzbu;
            format = fileDescriptor.format(new Date(this.zzbu));
            stringBuilder = new StringBuilder(String.valueOf(format).length() + 21);
            stringBuilder.append(j);
            stringBuilder.append(" ");
            stringBuilder.append(format);
            strArr.println(stringBuilder.toString());
        }
        if (this.zzbx > 0) {
            printWriter.append(str).append("lastFailedStatus=").append(CommonStatusCodes.getStatusCodeString(this.zzbw));
            str = printWriter.append(" lastFailedTime=");
            printWriter = this.zzbx;
            fileDescriptor = fileDescriptor.format(new Date(this.zzbx));
            StringBuilder stringBuilder2 = new StringBuilder(String.valueOf(fileDescriptor).length() + 21);
            stringBuilder2.append(printWriter);
            stringBuilder2.append(" ");
            stringBuilder2.append(fileDescriptor);
            str.println(stringBuilder2.toString());
        }
    }

    @KeepForSdk
    @Nullable
    public IBinder getServiceBrokerBinder() {
        synchronized (this.zzcc) {
            if (this.zzcd == null) {
                return null;
            }
            IBinder asBinder = this.zzcd.asBinder();
            return asBinder;
        }
    }

    private final boolean zzl() {
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
        r0 = r2.zzco;
        r1 = 0;
        if (r0 == 0) goto L_0x0006;
    L_0x0005:
        return r1;
    L_0x0006:
        r0 = r2.getServiceDescriptor();
        r0 = android.text.TextUtils.isEmpty(r0);
        if (r0 == 0) goto L_0x0011;
    L_0x0010:
        return r1;
    L_0x0011:
        r0 = r2.getLocalStartServiceAction();
        r0 = android.text.TextUtils.isEmpty(r0);
        if (r0 == 0) goto L_0x001c;
    L_0x001b:
        return r1;
    L_0x001c:
        r0 = r2.getServiceDescriptor();	 Catch:{ ClassNotFoundException -> 0x0025 }
        java.lang.Class.forName(r0);	 Catch:{ ClassNotFoundException -> 0x0025 }
        r0 = 1;
        return r0;
    L_0x0025:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.BaseGmsClient.zzl():boolean");
    }

    @KeepForSdk
    public String getEndpointPackageName() {
        if (isConnected() && this.zzby != null) {
            return this.zzby.getPackageName();
        }
        throw new RuntimeException("Failed to connect when checking package");
    }

    @KeepForSdk
    public int getMinApkVersion() {
        return GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    }
}
