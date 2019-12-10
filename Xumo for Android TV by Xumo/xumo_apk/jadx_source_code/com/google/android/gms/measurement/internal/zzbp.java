package com.google.android.gms.measurement.internal;

import android.content.Context;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public final class zzbp extends zzcq {
    private static final AtomicLong zzapk = new AtomicLong(Long.MIN_VALUE);
    private ExecutorService zzaea;
    private zzbt zzapb;
    private zzbt zzapc;
    private final PriorityBlockingQueue<zzbs<?>> zzapd = new PriorityBlockingQueue();
    private final BlockingQueue<zzbs<?>> zzape = new LinkedBlockingQueue();
    private final UncaughtExceptionHandler zzapf = new zzbr(this, "Thread death: Uncaught exception on worker thread");
    private final UncaughtExceptionHandler zzapg = new zzbr(this, "Thread death: Uncaught exception on network thread");
    private final Object zzaph = new Object();
    private final Semaphore zzapi = new Semaphore(2);
    private volatile boolean zzapj;

    zzbp(zzbu com_google_android_gms_measurement_internal_zzbu) {
        super(com_google_android_gms_measurement_internal_zzbu);
    }

    protected final boolean zzgy() {
        return false;
    }

    public final void zzaf() {
        if (Thread.currentThread() != this.zzapb) {
            throw new IllegalStateException("Call expected from worker thread");
        }
    }

    public final void zzgh() {
        if (Thread.currentThread() != this.zzapc) {
            throw new IllegalStateException("Call expected from network thread");
        }
    }

    public final boolean zzkf() {
        return Thread.currentThread() == this.zzapb;
    }

    public final <V> Future<V> zzb(Callable<V> callable) throws IllegalStateException {
        zzcl();
        Preconditions.checkNotNull(callable);
        zzbs com_google_android_gms_measurement_internal_zzbs = new zzbs(this, (Callable) callable, false, "Task exception on worker thread");
        if (Thread.currentThread() == this.zzapb) {
            if (this.zzapd.isEmpty() == null) {
                zzgt().zzjj().zzca("Callable skipped the worker queue.");
            }
            com_google_android_gms_measurement_internal_zzbs.run();
        } else {
            zza(com_google_android_gms_measurement_internal_zzbs);
        }
        return com_google_android_gms_measurement_internal_zzbs;
    }

    public final <V> Future<V> zzc(Callable<V> callable) throws IllegalStateException {
        zzcl();
        Preconditions.checkNotNull(callable);
        zzbs com_google_android_gms_measurement_internal_zzbs = new zzbs(this, (Callable) callable, true, "Task exception on worker thread");
        if (Thread.currentThread() == this.zzapb) {
            com_google_android_gms_measurement_internal_zzbs.run();
        } else {
            zza(com_google_android_gms_measurement_internal_zzbs);
        }
        return com_google_android_gms_measurement_internal_zzbs;
    }

    public final void zzc(Runnable runnable) throws IllegalStateException {
        zzcl();
        Preconditions.checkNotNull(runnable);
        zza(new zzbs(this, runnable, false, "Task exception on worker thread"));
    }

    final <T> T zza(java.util.concurrent.atomic.AtomicReference<T> r1, long r2, java.lang.String r4, java.lang.Runnable r5) {
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
        r0 = this;
        monitor-enter(r1);
        r2 = r0.zzgs();	 Catch:{ all -> 0x005c }
        r2.zzc(r5);	 Catch:{ all -> 0x005c }
        r2 = 15000; // 0x3a98 float:2.102E-41 double:7.411E-320;
        r1.wait(r2);	 Catch:{ InterruptedException -> 0x0037 }
        monitor-exit(r1);	 Catch:{ all -> 0x005c }
        r1 = r1.get();
        if (r1 != 0) goto L_0x0036;
    L_0x0014:
        r2 = r0.zzgt();
        r2 = r2.zzjj();
        r3 = "Timed out waiting for ";
        r4 = java.lang.String.valueOf(r4);
        r5 = r4.length();
        if (r5 == 0) goto L_0x002d;
    L_0x0028:
        r3 = r3.concat(r4);
        goto L_0x0033;
    L_0x002d:
        r4 = new java.lang.String;
        r4.<init>(r3);
        r3 = r4;
    L_0x0033:
        r2.zzca(r3);
    L_0x0036:
        return r1;
    L_0x0037:
        r2 = r0.zzgt();	 Catch:{ all -> 0x005c }
        r2 = r2.zzjj();	 Catch:{ all -> 0x005c }
        r3 = "Interrupted waiting for ";	 Catch:{ all -> 0x005c }
        r4 = java.lang.String.valueOf(r4);	 Catch:{ all -> 0x005c }
        r5 = r4.length();	 Catch:{ all -> 0x005c }
        if (r5 == 0) goto L_0x0050;	 Catch:{ all -> 0x005c }
    L_0x004b:
        r3 = r3.concat(r4);	 Catch:{ all -> 0x005c }
        goto L_0x0056;	 Catch:{ all -> 0x005c }
    L_0x0050:
        r4 = new java.lang.String;	 Catch:{ all -> 0x005c }
        r4.<init>(r3);	 Catch:{ all -> 0x005c }
        r3 = r4;	 Catch:{ all -> 0x005c }
    L_0x0056:
        r2.zzca(r3);	 Catch:{ all -> 0x005c }
        r2 = 0;	 Catch:{ all -> 0x005c }
        monitor-exit(r1);	 Catch:{ all -> 0x005c }
        return r2;	 Catch:{ all -> 0x005c }
    L_0x005c:
        r2 = move-exception;	 Catch:{ all -> 0x005c }
        monitor-exit(r1);	 Catch:{ all -> 0x005c }
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzbp.zza(java.util.concurrent.atomic.AtomicReference, long, java.lang.String, java.lang.Runnable):T");
    }

    private final void zza(zzbs<?> com_google_android_gms_measurement_internal_zzbs_) {
        synchronized (this.zzaph) {
            this.zzapd.add(com_google_android_gms_measurement_internal_zzbs_);
            if (this.zzapb == null) {
                this.zzapb = new zzbt(this, "Measurement Worker", this.zzapd);
                this.zzapb.setUncaughtExceptionHandler(this.zzapf);
                this.zzapb.start();
            } else {
                this.zzapb.zzki();
            }
        }
    }

    public final void zzd(Runnable runnable) throws IllegalStateException {
        zzcl();
        Preconditions.checkNotNull(runnable);
        zzbs com_google_android_gms_measurement_internal_zzbs = new zzbs(this, runnable, false, "Task exception on network thread");
        synchronized (this.zzaph) {
            this.zzape.add(com_google_android_gms_measurement_internal_zzbs);
            if (this.zzapc == null) {
                this.zzapc = new zzbt(this, "Measurement Network", this.zzape);
                this.zzapc.setUncaughtExceptionHandler(this.zzapg);
                this.zzapc.start();
            } else {
                this.zzapc.zzki();
            }
        }
    }

    public final ExecutorService zzkg() {
        ExecutorService executorService;
        synchronized (this.zzaph) {
            if (this.zzaea == null) {
                this.zzaea = new ThreadPoolExecutor(0, 1, 30, TimeUnit.SECONDS, new ArrayBlockingQueue(100));
            }
            executorService = this.zzaea;
        }
        return executorService;
    }

    public final /* bridge */ /* synthetic */ void zzgf() {
        super.zzgf();
    }

    public final /* bridge */ /* synthetic */ void zzgg() {
        super.zzgg();
    }

    public final /* bridge */ /* synthetic */ zzy zzgp() {
        return super.zzgp();
    }

    public final /* bridge */ /* synthetic */ Clock zzbx() {
        return super.zzbx();
    }

    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final /* bridge */ /* synthetic */ zzao zzgq() {
        return super.zzgq();
    }

    public final /* bridge */ /* synthetic */ zzfu zzgr() {
        return super.zzgr();
    }

    public final /* bridge */ /* synthetic */ zzbp zzgs() {
        return super.zzgs();
    }

    public final /* bridge */ /* synthetic */ zzaq zzgt() {
        return super.zzgt();
    }

    public final /* bridge */ /* synthetic */ zzbb zzgu() {
        return super.zzgu();
    }

    public final /* bridge */ /* synthetic */ zzo zzgv() {
        return super.zzgv();
    }

    public final /* bridge */ /* synthetic */ zzl zzgw() {
        return super.zzgw();
    }
}
