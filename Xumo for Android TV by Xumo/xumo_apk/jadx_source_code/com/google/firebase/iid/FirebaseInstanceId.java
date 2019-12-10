package com.google.firebase.iid;

import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.common.util.concurrent.NamedThreadFactory;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.DataCollectionDefaultChange;
import com.google.firebase.FirebaseApp;
import com.google.firebase.events.EventHandler;
import com.google.firebase.events.Subscriber;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.annotation.concurrent.GuardedBy;

public class FirebaseInstanceId {
    private static final long zzai = TimeUnit.HOURS.toSeconds(8);
    private static zzaw zzaj;
    @GuardedBy("FirebaseInstanceId.class")
    @VisibleForTesting
    private static ScheduledThreadPoolExecutor zzak;
    private final Executor zzal;
    private final FirebaseApp zzam;
    private final zzan zzan;
    private MessagingChannel zzao;
    private final zzaq zzap;
    private final zzba zzaq;
    @GuardedBy("this")
    private boolean zzar;
    private final zza zzas;

    private class zza {
        private final boolean zzaz = zzu();
        private final Subscriber zzba;
        @GuardedBy("this")
        @Nullable
        private EventHandler<DataCollectionDefaultChange> zzbb;
        @GuardedBy("this")
        @Nullable
        private Boolean zzbc = zzt();
        final /* synthetic */ FirebaseInstanceId zzbd;

        zza(FirebaseInstanceId firebaseInstanceId, Subscriber subscriber) {
            this.zzbd = firebaseInstanceId;
            this.zzba = subscriber;
            if (this.zzbc == null && this.zzaz != null) {
                this.zzbb = new zzq(this);
                subscriber.subscribe(DataCollectionDefaultChange.class, this.zzbb);
            }
        }

        final synchronized boolean isEnabled() {
            if (this.zzbc == null) {
                return this.zzaz && this.zzbd.zzam.isDataCollectionDefaultEnabled();
            } else {
                return this.zzbc.booleanValue();
            }
        }

        final synchronized void setEnabled(boolean z) {
            if (this.zzbb != null) {
                this.zzba.unsubscribe(DataCollectionDefaultChange.class, this.zzbb);
                this.zzbb = null;
            }
            Editor edit = this.zzbd.zzam.getApplicationContext().getSharedPreferences("com.google.firebase.messaging", 0).edit();
            edit.putBoolean("auto_init", z);
            edit.apply();
            if (z) {
                this.zzbd.zzg();
            }
            this.zzbc = Boolean.valueOf(z);
        }

        @androidx.annotation.Nullable
        private final java.lang.Boolean zzt() {
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
            r0 = r4.zzbd;
            r0 = r0.zzam;
            r0 = r0.getApplicationContext();
            r1 = "com.google.firebase.messaging";
            r2 = 0;
            r1 = r0.getSharedPreferences(r1, r2);
            r3 = "auto_init";
            r3 = r1.contains(r3);
            if (r3 == 0) goto L_0x0024;
        L_0x0019:
            r0 = "auto_init";
            r0 = r1.getBoolean(r0, r2);
            r0 = java.lang.Boolean.valueOf(r0);
            return r0;
        L_0x0024:
            r1 = r0.getPackageManager();	 Catch:{ NameNotFoundException -> 0x0051 }
            if (r1 == 0) goto L_0x0051;	 Catch:{ NameNotFoundException -> 0x0051 }
        L_0x002a:
            r0 = r0.getPackageName();	 Catch:{ NameNotFoundException -> 0x0051 }
            r2 = 128; // 0x80 float:1.794E-43 double:6.32E-322;	 Catch:{ NameNotFoundException -> 0x0051 }
            r0 = r1.getApplicationInfo(r0, r2);	 Catch:{ NameNotFoundException -> 0x0051 }
            if (r0 == 0) goto L_0x0051;	 Catch:{ NameNotFoundException -> 0x0051 }
        L_0x0036:
            r1 = r0.metaData;	 Catch:{ NameNotFoundException -> 0x0051 }
            if (r1 == 0) goto L_0x0051;	 Catch:{ NameNotFoundException -> 0x0051 }
        L_0x003a:
            r1 = r0.metaData;	 Catch:{ NameNotFoundException -> 0x0051 }
            r2 = "firebase_messaging_auto_init_enabled";	 Catch:{ NameNotFoundException -> 0x0051 }
            r1 = r1.containsKey(r2);	 Catch:{ NameNotFoundException -> 0x0051 }
            if (r1 == 0) goto L_0x0051;	 Catch:{ NameNotFoundException -> 0x0051 }
        L_0x0044:
            r0 = r0.metaData;	 Catch:{ NameNotFoundException -> 0x0051 }
            r1 = "firebase_messaging_auto_init_enabled";	 Catch:{ NameNotFoundException -> 0x0051 }
            r0 = r0.getBoolean(r1);	 Catch:{ NameNotFoundException -> 0x0051 }
            r0 = java.lang.Boolean.valueOf(r0);	 Catch:{ NameNotFoundException -> 0x0051 }
            return r0;
        L_0x0051:
            r0 = 0;
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.FirebaseInstanceId.zza.zzt():java.lang.Boolean");
        }

        private final boolean zzu() {
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
            r0 = 1;
            r1 = "com.google.firebase.messaging.FirebaseMessaging";	 Catch:{ ClassNotFoundException -> 0x0007 }
            java.lang.Class.forName(r1);	 Catch:{ ClassNotFoundException -> 0x0007 }
            return r0;
        L_0x0007:
            r1 = r4.zzbd;
            r1 = r1.zzam;
            r1 = r1.getApplicationContext();
            r2 = new android.content.Intent;
            r3 = "com.google.firebase.MESSAGING_EVENT";
            r2.<init>(r3);
            r3 = r1.getPackageName();
            r2.setPackage(r3);
            r1 = r1.getPackageManager();
            r3 = 0;
            r1 = r1.resolveService(r2, r3);
            if (r1 == 0) goto L_0x002f;
        L_0x002a:
            r1 = r1.serviceInfo;
            if (r1 == 0) goto L_0x002f;
        L_0x002e:
            return r0;
        L_0x002f:
            return r3;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.FirebaseInstanceId.zza.zzu():boolean");
        }
    }

    public static FirebaseInstanceId getInstance() {
        return getInstance(FirebaseApp.getInstance());
    }

    @Keep
    public static FirebaseInstanceId getInstance(@NonNull FirebaseApp firebaseApp) {
        return (FirebaseInstanceId) firebaseApp.get(FirebaseInstanceId.class);
    }

    FirebaseInstanceId(FirebaseApp firebaseApp, Subscriber subscriber) {
        this(firebaseApp, new zzan(firebaseApp.getApplicationContext()), zzi.zzf(), zzi.zzf(), subscriber);
    }

    private FirebaseInstanceId(FirebaseApp firebaseApp, zzan com_google_firebase_iid_zzan, Executor executor, Executor executor2, Subscriber subscriber) {
        this.zzar = false;
        if (zzan.zza(firebaseApp) != null) {
            synchronized (FirebaseInstanceId.class) {
                if (zzaj == null) {
                    zzaj = new zzaw(firebaseApp.getApplicationContext());
                }
            }
            this.zzam = firebaseApp;
            this.zzan = com_google_firebase_iid_zzan;
            if (this.zzao == null) {
                MessagingChannel messagingChannel = (MessagingChannel) firebaseApp.get(MessagingChannel.class);
                if (messagingChannel == null || !messagingChannel.isAvailable()) {
                    this.zzao = new zzr(firebaseApp, com_google_firebase_iid_zzan, executor);
                } else {
                    this.zzao = messagingChannel;
                }
            }
            this.zzao = this.zzao;
            this.zzal = executor2;
            this.zzaq = new zzba(zzaj);
            this.zzas = new zza(this, subscriber);
            this.zzap = new zzaq(executor);
            if (this.zzas.isEnabled() != null) {
                zzg();
                return;
            }
            return;
        }
        throw new IllegalStateException("FirebaseInstanceId failed to initialize, FirebaseApp is missing project ID");
    }

    private final void zzg() {
        zzax zzj = zzj();
        if (!zzo() || zzj == null || zzj.zzj(this.zzan.zzad()) || this.zzaq.zzaq()) {
            startSync();
        }
    }

    final FirebaseApp zzh() {
        return this.zzam;
    }

    final synchronized void zza(boolean z) {
        this.zzar = z;
    }

    private final synchronized void startSync() {
        if (!this.zzar) {
            zza(0);
        }
    }

    final synchronized void zza(long j) {
        zza(new zzay(this, this.zzan, this.zzaq, Math.min(Math.max(30, j << 1), zzai)), j);
        this.zzar = true;
    }

    static void zza(Runnable runnable, long j) {
        synchronized (FirebaseInstanceId.class) {
            if (zzak == null) {
                zzak = new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("FirebaseInstanceId"));
            }
            zzak.schedule(runnable, j, TimeUnit.SECONDS);
        }
    }

    @WorkerThread
    public String getId() {
        zzg();
        return zzi();
    }

    private static String zzi() {
        return zzan.zza(zzaj.zzg("").getKeyPair());
    }

    public long getCreationTime() {
        return zzaj.zzg("").getCreationTime();
    }

    @NonNull
    public Task<InstanceIdResult> getInstanceId() {
        return zza(zzan.zza(this.zzam), "*");
    }

    private final Task<InstanceIdResult> zza(String str, String str2) {
        String zzd = zzd(str2);
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.zzal.execute(new zzn(this, str, str2, taskCompletionSource, zzd));
        return taskCompletionSource.getTask();
    }

    @WorkerThread
    public void deleteInstanceId() throws IOException {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            zza(this.zzao.deleteInstanceId(zzi()));
            zzm();
            return;
        }
        throw new IOException("MAIN_THREAD");
    }

    @Deprecated
    @Nullable
    public String getToken() {
        zzax zzj = zzj();
        if (zzj == null || zzj.zzj(this.zzan.zzad())) {
            startSync();
        }
        return zzj != null ? zzj.zzbq : null;
    }

    @Nullable
    final zzax zzj() {
        return zzb(zzan.zza(this.zzam), "*");
    }

    @Nullable
    @VisibleForTesting
    private static zzax zzb(String str, String str2) {
        return zzaj.zzb("", str, str2);
    }

    final String zzk() throws IOException {
        return getToken(zzan.zza(this.zzam), "*");
    }

    @WorkerThread
    public String getToken(String str, String str2) throws IOException {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            return ((InstanceIdResult) zza(zza(str, str2))).getToken();
        }
        throw new IOException("MAIN_THREAD");
    }

    private final <T> T zza(com.google.android.gms.tasks.Task<T> r4) throws java.io.IOException {
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
        r0 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r2 = java.util.concurrent.TimeUnit.MILLISECONDS;	 Catch:{ ExecutionException -> 0x0011, InterruptedException -> 0x0009, InterruptedException -> 0x0009 }
        r4 = com.google.android.gms.tasks.Tasks.await(r4, r0, r2);	 Catch:{ ExecutionException -> 0x0011, InterruptedException -> 0x0009, InterruptedException -> 0x0009 }
        return r4;
    L_0x0009:
        r4 = new java.io.IOException;
        r0 = "SERVICE_NOT_AVAILABLE";
        r4.<init>(r0);
        throw r4;
    L_0x0011:
        r4 = move-exception;
        r0 = r4.getCause();
        r1 = r0 instanceof java.io.IOException;
        if (r1 == 0) goto L_0x002c;
    L_0x001a:
        r4 = "INSTANCE_ID_RESET";
        r1 = r0.getMessage();
        r4 = r4.equals(r1);
        if (r4 == 0) goto L_0x0029;
    L_0x0026:
        r3.zzm();
    L_0x0029:
        r0 = (java.io.IOException) r0;
        throw r0;
    L_0x002c:
        r1 = r0 instanceof java.lang.RuntimeException;
        if (r1 == 0) goto L_0x0033;
    L_0x0030:
        r0 = (java.lang.RuntimeException) r0;
        throw r0;
    L_0x0033:
        r0 = new java.io.IOException;
        r0.<init>(r4);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.FirebaseInstanceId.zza(com.google.android.gms.tasks.Task):T");
    }

    @WorkerThread
    public void deleteToken(String str, String str2) throws IOException {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            str2 = zzd(str2);
            zza(this.zzao.deleteToken(zzi(), zzax.zza(zzb(str, str2)), str, str2));
            zzaj.zzc("", str, str2);
            return;
        }
        throw new IOException("MAIN_THREAD");
    }

    public final synchronized Task<Void> zza(String str) {
        str = this.zzaq.zza(str);
        startSync();
        return str;
    }

    final void zzb(String str) throws IOException {
        zzax zzj = zzj();
        if (zzj == null || zzj.zzj(this.zzan.zzad())) {
            throw new IOException("token not available");
        }
        zza(this.zzao.subscribeToTopic(zzi(), zzj.zzbq, str));
    }

    final void zzc(String str) throws IOException {
        zzax zzj = zzj();
        if (zzj == null || zzj.zzj(this.zzan.zzad())) {
            throw new IOException("token not available");
        }
        zza(this.zzao.unsubscribeFromTopic(zzi(), zzj.zzbq, str));
    }

    static boolean zzl() {
        if (!Log.isLoggable("FirebaseInstanceId", 3)) {
            if (VERSION.SDK_INT != 23 || !Log.isLoggable("FirebaseInstanceId", 3)) {
                return false;
            }
        }
        return true;
    }

    final synchronized void zzm() {
        zzaj.zzal();
        if (this.zzas.isEnabled()) {
            startSync();
        }
    }

    final boolean zzn() {
        return this.zzao.isAvailable();
    }

    final boolean zzo() {
        return this.zzao.isChannelBuilt();
    }

    final void zzp() throws IOException {
        zza(this.zzao.buildChannel(zzi(), zzax.zza(zzj())));
    }

    final void zzq() {
        zzaj.zzh("");
        startSync();
    }

    @VisibleForTesting
    public final boolean zzr() {
        return this.zzas.isEnabled();
    }

    @VisibleForTesting
    public final void zzb(boolean z) {
        this.zzas.setEnabled(z);
    }

    private static String zzd(String str) {
        if (!(str.isEmpty() || str.equalsIgnoreCase(AppMeasurement.FCM_ORIGIN))) {
            if (!str.equalsIgnoreCase("gcm")) {
                return str;
            }
        }
        return "*";
    }

    final /* synthetic */ void zza(String str, String str2, TaskCompletionSource taskCompletionSource, String str3) {
        String zzi = zzi();
        str2 = zzb(str, str2);
        if (str2 == null || str2.zzj(this.zzan.zzad())) {
            this.zzap.zza(str, str3, new zzo(this, zzi, zzax.zza(str2), str, str3)).addOnCompleteListener(this.zzal, new zzp(this, str, str3, taskCompletionSource, zzi));
            return;
        }
        taskCompletionSource.setResult(new zzx(zzi, str2.zzbq));
    }

    final /* synthetic */ void zza(String str, String str2, TaskCompletionSource taskCompletionSource, String str3, Task task) {
        if (task.isSuccessful()) {
            String str4 = (String) task.getResult();
            zzaj.zza("", str, str2, str4, this.zzan.zzad());
            taskCompletionSource.setResult(new zzx(str3, str4));
            return;
        }
        taskCompletionSource.setException(task.getException());
    }

    final /* synthetic */ Task zza(String str, String str2, String str3, String str4) {
        return this.zzao.getToken(str, str2, str3, str4);
    }
}
