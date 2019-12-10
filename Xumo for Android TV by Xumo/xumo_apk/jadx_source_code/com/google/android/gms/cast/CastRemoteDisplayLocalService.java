package com.google.android.gms.cast;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.Display;
import androidx.annotation.VisibleForTesting;
import androidx.core.app.NotificationCompat.Builder;
import androidx.mediarouter.media.MediaRouter;
import androidx.mediarouter.media.MediaRouter.Callback;
import com.google.android.gms.cast.CastRemoteDisplay.Configuration;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.internal.cast.zzdh;
import com.google.android.gms.internal.cast.zzek;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;

@TargetApi(19)
public abstract class CastRemoteDisplayLocalService extends Service {
    private static final zzdh zzbe = new zzdh("CastRemoteDisplayLocalService");
    private static final int zzbo = R.id.cast_notification_id;
    private static final Object zzbp = new Object();
    private static AtomicBoolean zzbq = new AtomicBoolean(false);
    private static CastRemoteDisplayLocalService zzcf;
    private Handler handler;
    private WeakReference<Callbacks> zzbr;
    private zzb zzbs;
    private NotificationSettings zzbt;
    private Notification zzbu;
    private boolean zzbv;
    private PendingIntent zzbw;
    private CastDevice zzbx;
    private Display zzby;
    private Context zzbz;
    private ServiceConnection zzca;
    private MediaRouter zzcb;
    private boolean zzcc = false;
    private CastRemoteDisplayClient zzcd;
    private final Callback zzce = new zzu(this);
    private final IBinder zzcg = new zza(this);
    private String zzy;

    public interface Callbacks {
        void onRemoteDisplaySessionEnded(CastRemoteDisplayLocalService castRemoteDisplayLocalService);

        void onRemoteDisplaySessionError(Status status);

        void onRemoteDisplaySessionStarted(CastRemoteDisplayLocalService castRemoteDisplayLocalService);

        void onServiceCreated(CastRemoteDisplayLocalService castRemoteDisplayLocalService);
    }

    public static final class NotificationSettings {
        private Notification zzbu;
        private PendingIntent zzco;
        private String zzcp;
        private String zzcq;

        public static final class Builder {
            private NotificationSettings zzcr = new NotificationSettings();

            public final Builder setNotification(Notification notification) {
                this.zzcr.zzbu = notification;
                return this;
            }

            public final Builder setNotificationPendingIntent(PendingIntent pendingIntent) {
                this.zzcr.zzco = pendingIntent;
                return this;
            }

            public final Builder setNotificationTitle(String str) {
                this.zzcr.zzcp = str;
                return this;
            }

            public final Builder setNotificationText(String str) {
                this.zzcr.zzcq = str;
                return this;
            }

            public final NotificationSettings build() {
                if (this.zzcr.zzbu != null) {
                    if (!TextUtils.isEmpty(this.zzcr.zzcp)) {
                        throw new IllegalArgumentException("notificationTitle requires using the default notification");
                    } else if (!TextUtils.isEmpty(this.zzcr.zzcq)) {
                        throw new IllegalArgumentException("notificationText requires using the default notification");
                    } else if (this.zzcr.zzco != null) {
                        throw new IllegalArgumentException("notificationPendingIntent requires using the default notification");
                    }
                } else if (TextUtils.isEmpty(this.zzcr.zzcp) && TextUtils.isEmpty(this.zzcr.zzcq)) {
                    if (this.zzcr.zzco == null) {
                        throw new IllegalArgumentException("At least an argument must be provided");
                    }
                }
                return this.zzcr;
            }
        }

        private NotificationSettings() {
        }

        private NotificationSettings(NotificationSettings notificationSettings) {
            this.zzbu = notificationSettings.zzbu;
            this.zzco = notificationSettings.zzco;
            this.zzcp = notificationSettings.zzcp;
            this.zzcq = notificationSettings.zzcq;
        }
    }

    public static class Options {
        @Configuration
        private int zzbc = 2;

        public void setConfigPreset(@Configuration int i) {
            this.zzbc = i;
        }

        @Configuration
        public int getConfigPreset() {
            return this.zzbc;
        }
    }

    @VisibleForTesting
    class zza extends Binder {
        final /* synthetic */ CastRemoteDisplayLocalService zzch;

        zza(CastRemoteDisplayLocalService castRemoteDisplayLocalService) {
            this.zzch = castRemoteDisplayLocalService;
        }
    }

    private static final class zzb extends BroadcastReceiver {
        private zzb() {
        }

        public final void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.google.android.gms.cast.remote_display.ACTION_NOTIFICATION_DISCONNECT") != null) {
                CastRemoteDisplayLocalService.stopService();
                return;
            }
            if (intent.getAction().equals("com.google.android.gms.cast.remote_display.ACTION_SESSION_ENDED") != null) {
                CastRemoteDisplayLocalService.zzb(false);
            }
        }
    }

    public abstract void onCreatePresentation(Display display);

    public abstract void onDismissPresentation();

    public IBinder onBind(Intent intent) {
        zzb("onBind");
        return this.zzcg;
    }

    public void onCreate() {
        zzb("onCreate");
        super.onCreate();
        this.handler = new zzek(getMainLooper());
        this.handler.postDelayed(new zzv(this), 100);
        if (this.zzcd == null) {
            this.zzcd = CastRemoteDisplay.getClient(this);
        }
        if (PlatformVersion.isAtLeastO()) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            NotificationChannel notificationChannel = new NotificationChannel("cast_remote_display_local_service", getString(R.string.cast_notification_default_channel_name), 2);
            notificationChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        zzb("onStartCommand");
        this.zzcc = true;
        return 2;
    }

    protected static void setDebugEnabled() {
        zzbe.zzl(true);
    }

    protected Display getDisplay() {
        return this.zzby;
    }

    public static CastRemoteDisplayLocalService getInstance() {
        CastRemoteDisplayLocalService castRemoteDisplayLocalService;
        synchronized (zzbp) {
            castRemoteDisplayLocalService = zzcf;
        }
        return castRemoteDisplayLocalService;
    }

    public static void startService(Context context, Class<? extends CastRemoteDisplayLocalService> cls, String str, CastDevice castDevice, NotificationSettings notificationSettings, Callbacks callbacks) {
        startServiceWithOptions(context, cls, str, castDevice, new Options(), notificationSettings, callbacks);
    }

    public static void startServiceWithOptions(@androidx.annotation.NonNull android.content.Context r8, @androidx.annotation.NonNull java.lang.Class<? extends com.google.android.gms.cast.CastRemoteDisplayLocalService> r9, @androidx.annotation.NonNull java.lang.String r10, @androidx.annotation.NonNull com.google.android.gms.cast.CastDevice r11, @androidx.annotation.NonNull com.google.android.gms.cast.CastRemoteDisplayLocalService.Options r12, @androidx.annotation.NonNull com.google.android.gms.cast.CastRemoteDisplayLocalService.NotificationSettings r13, @androidx.annotation.NonNull com.google.android.gms.cast.CastRemoteDisplayLocalService.Callbacks r14) {
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
        r0 = zzbe;
        r1 = "Starting Service";
        r2 = 0;
        r3 = new java.lang.Object[r2];
        r0.d(r1, r3);
        r0 = zzbp;
        monitor-enter(r0);
        r1 = zzcf;	 Catch:{ all -> 0x00a9 }
        r3 = 1;	 Catch:{ all -> 0x00a9 }
        if (r1 == 0) goto L_0x001e;	 Catch:{ all -> 0x00a9 }
    L_0x0012:
        r1 = zzbe;	 Catch:{ all -> 0x00a9 }
        r4 = "An existing service had not been stopped before starting one";	 Catch:{ all -> 0x00a9 }
        r5 = new java.lang.Object[r2];	 Catch:{ all -> 0x00a9 }
        r1.w(r4, r5);	 Catch:{ all -> 0x00a9 }
        zzb(r3);	 Catch:{ all -> 0x00a9 }
    L_0x001e:
        monitor-exit(r0);	 Catch:{ all -> 0x00a9 }
        r0 = new android.content.ComponentName;	 Catch:{ NameNotFoundException -> 0x00a1 }
        r0.<init>(r8, r9);	 Catch:{ NameNotFoundException -> 0x00a1 }
        r1 = r8.getPackageManager();	 Catch:{ NameNotFoundException -> 0x00a1 }
        r4 = 128; // 0x80 float:1.794E-43 double:6.32E-322;	 Catch:{ NameNotFoundException -> 0x00a1 }
        r0 = r1.getServiceInfo(r0, r4);	 Catch:{ NameNotFoundException -> 0x00a1 }
        if (r0 == 0) goto L_0x003d;	 Catch:{ NameNotFoundException -> 0x00a1 }
    L_0x0030:
        r0 = r0.exported;	 Catch:{ NameNotFoundException -> 0x00a1 }
        if (r0 != 0) goto L_0x0035;	 Catch:{ NameNotFoundException -> 0x00a1 }
    L_0x0034:
        goto L_0x003d;	 Catch:{ NameNotFoundException -> 0x00a1 }
    L_0x0035:
        r8 = new java.lang.IllegalStateException;	 Catch:{ NameNotFoundException -> 0x00a1 }
        r9 = "The service must not be exported, verify the manifest configuration";	 Catch:{ NameNotFoundException -> 0x00a1 }
        r8.<init>(r9);	 Catch:{ NameNotFoundException -> 0x00a1 }
        throw r8;	 Catch:{ NameNotFoundException -> 0x00a1 }
    L_0x003d:
        r0 = "activityContext is required.";
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r8, r0);
        r0 = "serviceClass is required.";
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r9, r0);
        r0 = "applicationId is required.";
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r10, r0);
        r0 = "device is required.";
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r11, r0);
        r0 = "options is required.";
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r12, r0);
        r0 = "notificationSettings is required.";
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r13, r0);
        r0 = "callbacks is required.";
        com.google.android.gms.common.internal.Preconditions.checkNotNull(r14, r0);
        r0 = r13.zzbu;
        if (r0 != 0) goto L_0x0075;
    L_0x0066:
        r0 = r13.zzco;
        if (r0 == 0) goto L_0x006d;
    L_0x006c:
        goto L_0x0075;
    L_0x006d:
        r8 = new java.lang.IllegalArgumentException;
        r9 = "notificationSettings: Either the notification or the notificationPendingIntent must be provided";
        r8.<init>(r9);
        throw r8;
    L_0x0075:
        r0 = zzbq;
        r0 = r0.getAndSet(r3);
        if (r0 == 0) goto L_0x0087;
    L_0x007d:
        r8 = zzbe;
        r9 = "Service is already being started, startService has been called twice";
        r10 = new java.lang.Object[r2];
        r8.e(r9, r10);
        return;
    L_0x0087:
        r0 = new android.content.Intent;
        r0.<init>(r8, r9);
        r8.startService(r0);
        r9 = new com.google.android.gms.cast.zzw;
        r1 = r9;
        r2 = r10;
        r3 = r11;
        r4 = r12;
        r5 = r13;
        r6 = r8;
        r7 = r14;
        r1.<init>(r2, r3, r4, r5, r6, r7);
        r10 = 64;
        r8.bindService(r0, r9, r10);
        return;
    L_0x00a1:
        r8 = new java.lang.IllegalStateException;
        r9 = "Service not found, did you forget to configure it in the manifest?";
        r8.<init>(r9);
        throw r8;
    L_0x00a9:
        r8 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x00a9 }
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.CastRemoteDisplayLocalService.startServiceWithOptions(android.content.Context, java.lang.Class, java.lang.String, com.google.android.gms.cast.CastDevice, com.google.android.gms.cast.CastRemoteDisplayLocalService$Options, com.google.android.gms.cast.CastRemoteDisplayLocalService$NotificationSettings, com.google.android.gms.cast.CastRemoteDisplayLocalService$Callbacks):void");
    }

    private final void zza(boolean r3) {
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
        r2 = this;
        r0 = "Stopping Service";
        r2.zzb(r0);
        r0 = "stopServiceInstanceInternal must be called on the main thread";
        com.google.android.gms.common.internal.Preconditions.checkMainThread(r0);
        if (r3 != 0) goto L_0x0020;
    L_0x000c:
        r3 = r2.zzcb;
        if (r3 == 0) goto L_0x0020;
    L_0x0010:
        r3 = "Setting default route";
        r2.zzb(r3);
        r3 = r2.zzcb;
        r0 = r2.zzcb;
        r0 = r0.getDefaultRoute();
        r3.selectRoute(r0);
    L_0x0020:
        r3 = r2.zzbs;
        if (r3 == 0) goto L_0x002e;
    L_0x0024:
        r3 = "Unregistering notification receiver";
        r2.zzb(r3);
        r3 = r2.zzbs;
        r2.unregisterReceiver(r3);
    L_0x002e:
        r3 = "stopRemoteDisplaySession";
        r2.zzb(r3);
        r3 = "stopRemoteDisplay";
        r2.zzb(r3);
        r3 = r2.zzcd;
        r3 = r3.stopRemoteDisplay();
        r0 = new com.google.android.gms.cast.zzaa;
        r0.<init>(r2);
        r3.addOnCompleteListener(r0);
        r3 = r2.zzbr;
        r3 = r3.get();
        if (r3 == 0) goto L_0x0059;
    L_0x004e:
        r3 = r2.zzbr;
        r3 = r3.get();
        r3 = (com.google.android.gms.cast.CastRemoteDisplayLocalService.Callbacks) r3;
        r3.onRemoteDisplaySessionEnded(r2);
    L_0x0059:
        r2.onDismissPresentation();
        r3 = "Stopping the remote display Service";
        r2.zzb(r3);
        r3 = 1;
        r2.stopForeground(r3);
        r2.stopSelf();
        r3 = r2.zzcb;
        if (r3 == 0) goto L_0x007d;
    L_0x006c:
        r3 = "CastRemoteDisplayLocalService calls must be done on the main thread";
        com.google.android.gms.common.internal.Preconditions.checkMainThread(r3);
        r3 = "removeMediaRouterCallback";
        r2.zzb(r3);
        r3 = r2.zzcb;
        r0 = r2.zzce;
        r3.removeCallback(r0);
    L_0x007d:
        r3 = r2.zzbz;
        r0 = 0;
        if (r3 == 0) goto L_0x0097;
    L_0x0082:
        r3 = r2.zzca;
        if (r3 == 0) goto L_0x0097;
    L_0x0086:
        r3 = r2.zzbz;	 Catch:{ IllegalArgumentException -> 0x008e }
        r1 = r2.zzca;	 Catch:{ IllegalArgumentException -> 0x008e }
        r3.unbindService(r1);	 Catch:{ IllegalArgumentException -> 0x008e }
        goto L_0x0093;
    L_0x008e:
        r3 = "No need to unbind service, already unbound";
        r2.zzb(r3);
    L_0x0093:
        r2.zzca = r0;
        r2.zzbz = r0;
    L_0x0097:
        r2.zzy = r0;
        r2.zzbu = r0;
        r2.zzby = r0;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.CastRemoteDisplayLocalService.zza(boolean):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void zzb(boolean r4) {
        /*
        r0 = zzbe;
        r1 = "Stopping Service";
        r2 = 0;
        r3 = new java.lang.Object[r2];
        r0.d(r1, r3);
        r0 = zzbq;
        r0.set(r2);
        r0 = zzbp;
        monitor-enter(r0);
        r1 = zzcf;	 Catch:{ all -> 0x0044 }
        if (r1 != 0) goto L_0x0021;
    L_0x0016:
        r4 = zzbe;	 Catch:{ all -> 0x0044 }
        r1 = "Service is already being stopped";
        r2 = new java.lang.Object[r2];	 Catch:{ all -> 0x0044 }
        r4.e(r1, r2);	 Catch:{ all -> 0x0044 }
        monitor-exit(r0);	 Catch:{ all -> 0x0044 }
        return;
    L_0x0021:
        r1 = zzcf;	 Catch:{ all -> 0x0044 }
        r2 = 0;
        zzcf = r2;	 Catch:{ all -> 0x0044 }
        monitor-exit(r0);	 Catch:{ all -> 0x0044 }
        r0 = r1.handler;
        if (r0 == 0) goto L_0x0043;
    L_0x002b:
        r0 = android.os.Looper.myLooper();
        r2 = android.os.Looper.getMainLooper();
        if (r0 == r2) goto L_0x0040;
    L_0x0035:
        r0 = r1.handler;
        r2 = new com.google.android.gms.cast.zzx;
        r2.<init>(r1, r4);
        r0.post(r2);
        return;
    L_0x0040:
        r1.zza(r4);
    L_0x0043:
        return;
    L_0x0044:
        r4 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x0044 }
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.CastRemoteDisplayLocalService.zzb(boolean):void");
    }

    public static void stopService() {
        zzb(false);
    }

    public void updateNotificationSettings(NotificationSettings notificationSettings) {
        Preconditions.checkNotNull(notificationSettings, "notificationSettings is required.");
        Preconditions.checkNotNull(this.handler, "Service is not ready yet.");
        this.handler.post(new zzy(this, notificationSettings));
    }

    private final void zza(NotificationSettings notificationSettings) {
        Preconditions.checkMainThread("updateNotificationSettingsInternal must be called on the main thread");
        if (this.zzbt != null) {
            if (!this.zzbv) {
                Preconditions.checkNotNull(notificationSettings.zzbu, "notification is required.");
                this.zzbu = notificationSettings.zzbu;
                this.zzbt.zzbu = this.zzbu;
            } else if (notificationSettings.zzbu == null) {
                if (notificationSettings.zzco != null) {
                    this.zzbt.zzco = notificationSettings.zzco;
                }
                if (!TextUtils.isEmpty(notificationSettings.zzcp)) {
                    this.zzbt.zzcp = notificationSettings.zzcp;
                }
                if (!TextUtils.isEmpty(notificationSettings.zzcq)) {
                    this.zzbt.zzcq = notificationSettings.zzcq;
                }
                this.zzbu = zzc(true);
            } else {
                throw new IllegalStateException("Current mode is default notification, notification attribute must not be provided");
            }
            startForeground(zzbo, this.zzbu);
            return;
        }
        throw new IllegalStateException("No current notification settings to update");
    }

    private final void zza(Display display) {
        this.zzby = display;
        if (this.zzbv != null) {
            this.zzbu = zzc(true);
            startForeground(zzbo, this.zzbu);
        }
        if (this.zzbr.get() != null) {
            ((Callbacks) this.zzbr.get()).onRemoteDisplaySessionStarted(this);
        }
        onCreatePresentation(this.zzby);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final boolean zza(java.lang.String r4, com.google.android.gms.cast.CastDevice r5, com.google.android.gms.cast.CastRemoteDisplayLocalService.Options r6, com.google.android.gms.cast.CastRemoteDisplayLocalService.NotificationSettings r7, android.content.Context r8, android.content.ServiceConnection r9, com.google.android.gms.cast.CastRemoteDisplayLocalService.Callbacks r10) {
        /*
        r3 = this;
        r0 = "startRemoteDisplaySession";
        r3.zzb(r0);
        r0 = "Starting the Cast Remote Display must be done on the main thread";
        com.google.android.gms.common.internal.Preconditions.checkMainThread(r0);
        r0 = zzbp;
        monitor-enter(r0);
        r1 = zzcf;	 Catch:{ all -> 0x00ea }
        r2 = 0;
        if (r1 == 0) goto L_0x001d;
    L_0x0012:
        r4 = zzbe;	 Catch:{ all -> 0x00ea }
        r5 = "An existing service had not been stopped before starting one";
        r6 = new java.lang.Object[r2];	 Catch:{ all -> 0x00ea }
        r4.w(r5, r6);	 Catch:{ all -> 0x00ea }
        monitor-exit(r0);	 Catch:{ all -> 0x00ea }
        return r2;
    L_0x001d:
        zzcf = r3;	 Catch:{ all -> 0x00ea }
        monitor-exit(r0);	 Catch:{ all -> 0x00ea }
        r0 = new java.lang.ref.WeakReference;
        r0.<init>(r10);
        r3.zzbr = r0;
        r3.zzy = r4;
        r3.zzbx = r5;
        r3.zzbz = r8;
        r3.zzca = r9;
        r4 = r3.zzcb;
        if (r4 != 0) goto L_0x003d;
    L_0x0033:
        r4 = r3.getApplicationContext();
        r4 = androidx.mediarouter.media.MediaRouter.getInstance(r4);
        r3.zzcb = r4;
    L_0x003d:
        r4 = new androidx.mediarouter.media.MediaRouteSelector$Builder;
        r4.<init>();
        r8 = r3.zzy;
        r8 = com.google.android.gms.cast.CastMediaControlIntent.categoryForCast(r8);
        r4 = r4.addControlCategory(r8);
        r4 = r4.build();
        r8 = "addMediaRouterCallback";
        r3.zzb(r8);
        r8 = r3.zzcb;
        r9 = r3.zzce;
        r10 = 4;
        r8.addCallback(r4, r9, r10);
        r4 = r7.zzbu;
        r3.zzbu = r4;
        r4 = new com.google.android.gms.cast.CastRemoteDisplayLocalService$zzb;
        r8 = 0;
        r4.<init>();
        r3.zzbs = r4;
        r4 = new android.content.IntentFilter;
        r4.<init>();
        r9 = "com.google.android.gms.cast.remote_display.ACTION_NOTIFICATION_DISCONNECT";
        r4.addAction(r9);
        r9 = "com.google.android.gms.cast.remote_display.ACTION_SESSION_ENDED";
        r4.addAction(r9);
        r9 = r3.zzbs;
        r3.registerReceiver(r9, r4);
        r4 = new com.google.android.gms.cast.CastRemoteDisplayLocalService$NotificationSettings;
        r4.<init>(r7);
        r3.zzbt = r4;
        r4 = r3.zzbt;
        r4 = r4.zzbu;
        r7 = 1;
        if (r4 != 0) goto L_0x0098;
    L_0x008f:
        r3.zzbv = r7;
        r4 = r3.zzc(r2);
        r3.zzbu = r4;
        goto L_0x00a2;
    L_0x0098:
        r3.zzbv = r2;
        r4 = r3.zzbt;
        r4 = r4.zzbu;
        r3.zzbu = r4;
    L_0x00a2:
        r4 = zzbo;
        r8 = r3.zzbu;
        r3.startForeground(r4, r8);
        r4 = "startRemoteDisplay";
        r3.zzb(r4);
        r4 = new android.content.Intent;
        r8 = "com.google.android.gms.cast.remote_display.ACTION_SESSION_ENDED";
        r4.<init>(r8);
        r8 = r3.zzbz;
        r8 = r8.getPackageName();
        r4.setPackage(r8);
        r4 = android.app.PendingIntent.getBroadcast(r3, r2, r4, r2);
        r8 = r3.zzcd;
        r9 = r3.zzy;
        r6 = r6.getConfigPreset();
        r4 = r8.startRemoteDisplay(r5, r9, r6, r4);
        r5 = new com.google.android.gms.cast.zzz;
        r5.<init>(r3);
        r4.addOnCompleteListener(r5);
        r4 = r3.zzbr;
        r4 = r4.get();
        if (r4 == 0) goto L_0x00e9;
    L_0x00de:
        r4 = r3.zzbr;
        r4 = r4.get();
        r4 = (com.google.android.gms.cast.CastRemoteDisplayLocalService.Callbacks) r4;
        r4.onServiceCreated(r3);
    L_0x00e9:
        return r7;
    L_0x00ea:
        r4 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x00ea }
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.CastRemoteDisplayLocalService.zza(java.lang.String, com.google.android.gms.cast.CastDevice, com.google.android.gms.cast.CastRemoteDisplayLocalService$Options, com.google.android.gms.cast.CastRemoteDisplayLocalService$NotificationSettings, android.content.Context, android.content.ServiceConnection, com.google.android.gms.cast.CastRemoteDisplayLocalService$Callbacks):boolean");
    }

    private final void zzd() {
        if (this.zzbr.get() != null) {
            ((Callbacks) this.zzbr.get()).onRemoteDisplaySessionError(new Status(CastStatusCodes.ERROR_SERVICE_CREATION_FAILED));
        }
        stopService();
    }

    private final Notification zzc(boolean z) {
        int i;
        zzb("createDefaultNotification");
        CharSequence zzc = this.zzbt.zzcp;
        CharSequence zzd = this.zzbt.zzcq;
        if (z) {
            z = R.string.cast_notification_connected_message;
            i = R.drawable.cast_ic_notification_on;
        } else {
            z = R.string.cast_notification_connecting_message;
            i = R.drawable.cast_ic_notification_connecting;
        }
        if (TextUtils.isEmpty(zzc)) {
            zzc = (String) getPackageManager().getApplicationLabel(getApplicationInfo());
        }
        if (TextUtils.isEmpty(zzd)) {
            zzd = getString(z, new Object[]{this.zzbx.getFriendlyName()});
        }
        z = new Builder(this, "cast_remote_display_local_service").setContentTitle(zzc).setContentText(zzd).setContentIntent(this.zzbt.zzco).setSmallIcon(i).setOngoing(true);
        zzd = getString(R.string.cast_notification_disconnect);
        if (this.zzbw == null) {
            Intent intent = new Intent("com.google.android.gms.cast.remote_display.ACTION_NOTIFICATION_DISCONNECT");
            intent.setPackage(this.zzbz.getPackageName());
            this.zzbw = PendingIntent.getBroadcast(this, 0, intent, 134217728);
        }
        return z.addAction(17301560, zzd, this.zzbw).build();
    }

    private final void zzb(String str) {
        zzbe.d("[Instance: %s] %s", this, str);
    }

    private final void zzc(String str) {
        zzbe.e("[Instance: %s] %s", this, str);
    }
}
