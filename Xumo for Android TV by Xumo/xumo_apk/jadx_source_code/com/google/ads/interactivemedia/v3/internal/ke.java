package com.google.ads.interactivemedia.v3.internal;

import android.content.Context;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import java.util.concurrent.CountDownLatch;

/* compiled from: IMASDK */
public class ke extends kd {
    private static AdvertisingIdClient e;
    private static CountDownLatch f = new CountDownLatch(1);
    private static volatile boolean g;
    private boolean h;

    /* compiled from: IMASDK */
    class a {
        final /* synthetic */ ke a;
        private String b;
        private boolean c;

        public a(ke keVar, String str, boolean z) {
            this.a = keVar;
            this.b = str;
            this.c = z;
        }

        public String a() {
            return this.b;
        }

        public boolean b() {
            return this.c;
        }
    }

    /* compiled from: IMASDK */
    private static final class b implements Runnable {
        private Context a;

        public b(Context context) {
            this.a = context.getApplicationContext();
            if (this.a == null) {
                this.a = context;
            }
        }

        public void run() {
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
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r3 = this;
            r0 = com.google.ads.interactivemedia.v3.internal.ke.class;
            monitor-enter(r0);
            r1 = com.google.ads.interactivemedia.v3.internal.ke.e;	 Catch:{ GooglePlayServicesNotAvailableException -> 0x0032, IOException -> 0x0028, GooglePlayServicesRepairableException -> 0x0020 }
            if (r1 != 0) goto L_0x0016;	 Catch:{ GooglePlayServicesNotAvailableException -> 0x0032, IOException -> 0x0028, GooglePlayServicesRepairableException -> 0x0020 }
        L_0x0009:
            r1 = new com.google.android.gms.ads.identifier.AdvertisingIdClient;	 Catch:{ GooglePlayServicesNotAvailableException -> 0x0032, IOException -> 0x0028, GooglePlayServicesRepairableException -> 0x0020 }
            r2 = r3.a;	 Catch:{ GooglePlayServicesNotAvailableException -> 0x0032, IOException -> 0x0028, GooglePlayServicesRepairableException -> 0x0020 }
            r1.<init>(r2);	 Catch:{ GooglePlayServicesNotAvailableException -> 0x0032, IOException -> 0x0028, GooglePlayServicesRepairableException -> 0x0020 }
            r1.start();	 Catch:{ GooglePlayServicesNotAvailableException -> 0x0032, IOException -> 0x0028, GooglePlayServicesRepairableException -> 0x0020 }
            com.google.ads.interactivemedia.v3.internal.ke.e = r1;	 Catch:{ GooglePlayServicesNotAvailableException -> 0x0032, IOException -> 0x0028, GooglePlayServicesRepairableException -> 0x0020 }
        L_0x0016:
            r1 = com.google.ads.interactivemedia.v3.internal.ke.f;	 Catch:{ all -> 0x0030 }
            r1.countDown();	 Catch:{ all -> 0x0030 }
            goto L_0x003d;	 Catch:{ all -> 0x0030 }
        L_0x001e:
            r1 = move-exception;	 Catch:{ all -> 0x0030 }
            goto L_0x003f;	 Catch:{ all -> 0x0030 }
        L_0x0020:
            r1 = com.google.ads.interactivemedia.v3.internal.ke.f;	 Catch:{ all -> 0x0030 }
            r1.countDown();	 Catch:{ all -> 0x0030 }
            goto L_0x003d;	 Catch:{ all -> 0x0030 }
        L_0x0028:
            r1 = com.google.ads.interactivemedia.v3.internal.ke.f;	 Catch:{ all -> 0x0030 }
            r1.countDown();	 Catch:{ all -> 0x0030 }
            goto L_0x003d;
        L_0x0030:
            r1 = move-exception;
            goto L_0x0047;
        L_0x0032:
            r1 = 1;
            com.google.ads.interactivemedia.v3.internal.ke.g = r1;	 Catch:{ all -> 0x001e }
            r1 = com.google.ads.interactivemedia.v3.internal.ke.f;	 Catch:{ all -> 0x0030 }
            r1.countDown();	 Catch:{ all -> 0x0030 }
        L_0x003d:
            monitor-exit(r0);	 Catch:{ all -> 0x0030 }
            return;	 Catch:{ all -> 0x0030 }
        L_0x003f:
            r2 = com.google.ads.interactivemedia.v3.internal.ke.f;	 Catch:{ all -> 0x0030 }
            r2.countDown();	 Catch:{ all -> 0x0030 }
            throw r1;	 Catch:{ all -> 0x0030 }
        L_0x0047:
            monitor-exit(r0);	 Catch:{ all -> 0x0030 }
            throw r1;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.ke.b.run():void");
        }
    }

    public static ke a(String str, Context context) {
        return a(str, context, true);
    }

    public static ke a(String str, Context context, boolean z) {
        kh jzVar = new jz();
        kd.a(str, context, jzVar);
        if (z) {
            synchronized (ke.class) {
                if (e == null) {
                    new Thread(new b(context)).start();
                }
            }
        }
        return new ke(context, jzVar, new kk(239), z);
    }

    protected ke(Context context, kh khVar, ki kiVar, boolean z) {
        super(context, khVar, kiVar);
        this.h = z;
    }

    com.google.ads.interactivemedia.v3.internal.ke.a e() throws java.io.IOException {
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
        r6 = this;
        r0 = 0;
        r1 = 0;
        r2 = f;	 Catch:{ InterruptedException -> 0x003e }
        r3 = 2;	 Catch:{ InterruptedException -> 0x003e }
        r5 = java.util.concurrent.TimeUnit.SECONDS;	 Catch:{ InterruptedException -> 0x003e }
        r2 = r2.await(r3, r5);	 Catch:{ InterruptedException -> 0x003e }
        if (r2 != 0) goto L_0x0014;	 Catch:{ InterruptedException -> 0x003e }
    L_0x000e:
        r2 = new com.google.ads.interactivemedia.v3.internal.ke$a;	 Catch:{ InterruptedException -> 0x003e }
        r2.<init>(r6, r1, r0);	 Catch:{ InterruptedException -> 0x003e }
        return r2;
    L_0x0014:
        r2 = com.google.ads.interactivemedia.v3.internal.ke.class;
        monitor-enter(r2);
        r3 = e;	 Catch:{ all -> 0x003b }
        if (r3 != 0) goto L_0x0022;	 Catch:{ all -> 0x003b }
    L_0x001b:
        r3 = new com.google.ads.interactivemedia.v3.internal.ke$a;	 Catch:{ all -> 0x003b }
        r3.<init>(r6, r1, r0);	 Catch:{ all -> 0x003b }
        monitor-exit(r2);	 Catch:{ all -> 0x003b }
        return r3;	 Catch:{ all -> 0x003b }
    L_0x0022:
        r0 = e;	 Catch:{ all -> 0x003b }
        r0 = r0.getInfo();	 Catch:{ all -> 0x003b }
        monitor-exit(r2);	 Catch:{ all -> 0x003b }
        r1 = r0.getId();
        r1 = r6.a(r1);
        r2 = new com.google.ads.interactivemedia.v3.internal.ke$a;
        r0 = r0.isLimitAdTrackingEnabled();
        r2.<init>(r6, r1, r0);
        return r2;
    L_0x003b:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x003b }
        throw r0;
    L_0x003e:
        r2 = new com.google.ads.interactivemedia.v3.internal.ke$a;
        r2.<init>(r6, r1, r0);
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.ke.e():com.google.ads.interactivemedia.v3.internal.ke$a");
    }

    protected void b(android.content.Context r6) {
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
        r5 = this;
        super.b(r6);
        r0 = g;	 Catch:{ IOException -> 0x003c, a -> 0x003b }
        r1 = 24;	 Catch:{ IOException -> 0x003c, a -> 0x003b }
        if (r0 != 0) goto L_0x0033;	 Catch:{ IOException -> 0x003c, a -> 0x003b }
    L_0x0009:
        r0 = r5.h;	 Catch:{ IOException -> 0x003c, a -> 0x003b }
        if (r0 != 0) goto L_0x000e;	 Catch:{ IOException -> 0x003c, a -> 0x003b }
    L_0x000d:
        goto L_0x0033;	 Catch:{ IOException -> 0x003c, a -> 0x003b }
    L_0x000e:
        r6 = r5.e();	 Catch:{ IOException -> 0x003c, a -> 0x003b }
        r0 = r6.a();	 Catch:{ IOException -> 0x003c, a -> 0x003b }
        if (r0 == 0) goto L_0x003a;	 Catch:{ IOException -> 0x003c, a -> 0x003b }
    L_0x0018:
        r2 = 28;	 Catch:{ IOException -> 0x003c, a -> 0x003b }
        r6 = r6.b();	 Catch:{ IOException -> 0x003c, a -> 0x003b }
        if (r6 == 0) goto L_0x0023;	 Catch:{ IOException -> 0x003c, a -> 0x003b }
    L_0x0020:
        r3 = 1;	 Catch:{ IOException -> 0x003c, a -> 0x003b }
        goto L_0x0025;	 Catch:{ IOException -> 0x003c, a -> 0x003b }
    L_0x0023:
        r3 = 0;	 Catch:{ IOException -> 0x003c, a -> 0x003b }
    L_0x0025:
        r5.a(r2, r3);	 Catch:{ IOException -> 0x003c, a -> 0x003b }
        r6 = 26;	 Catch:{ IOException -> 0x003c, a -> 0x003b }
        r2 = 5;	 Catch:{ IOException -> 0x003c, a -> 0x003b }
        r5.a(r6, r2);	 Catch:{ IOException -> 0x003c, a -> 0x003b }
        r5.a(r1, r0);	 Catch:{ IOException -> 0x003c, a -> 0x003b }
        goto L_0x003a;	 Catch:{ IOException -> 0x003c, a -> 0x003b }
    L_0x0033:
        r6 = com.google.ads.interactivemedia.v3.internal.kd.d(r6);	 Catch:{ IOException -> 0x003c, a -> 0x003b }
        r5.a(r1, r6);	 Catch:{ IOException -> 0x003c, a -> 0x003b }
    L_0x003a:
        return;
    L_0x003b:
        return;
    L_0x003c:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.ke.b(android.content.Context):void");
    }
}
