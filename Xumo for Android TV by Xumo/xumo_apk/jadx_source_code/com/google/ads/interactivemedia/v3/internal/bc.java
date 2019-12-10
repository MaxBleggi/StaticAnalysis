package com.google.ads.interactivemedia.v3.internal;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;
import com.google.ads.interactivemedia.v3.internal.ba.a;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: IMASDK */
final class bc implements Callback {
    private final Handler a;
    private final HandlerThread b;
    private final Handler c;
    private final bp d;
    private final AtomicInteger e;
    private final List<bq> f;
    private final bj[][] g;
    private final int[] h;
    private final long i;
    private final long j;
    private bq[] k;
    private bq l;
    private bd m;
    private boolean n;
    private boolean o;
    private boolean p;
    private int q;
    private int r = 0;
    private int s = 0;
    private long t;
    private long u;
    private volatile long v;
    private volatile long w;
    private volatile long x;

    public bc(Handler handler, boolean z, int[] iArr, int i, int i2) {
        this.c = handler;
        this.o = z;
        this.i = ((long) i) * 1000;
        this.j = ((long) i2) * 1000;
        this.h = Arrays.copyOf(iArr, iArr.length);
        this.q = 1;
        this.v = -1;
        this.x = -1;
        this.d = new bp();
        this.e = new AtomicInteger();
        this.f = new ArrayList(iArr.length);
        this.g = new bj[iArr.length][];
        this.b = new fr("ExoPlayerImplInternal:Handler", -16);
        this.b.start();
        this.a = new Handler(this.b.getLooper(), this);
    }

    public long a() {
        return this.e.get() > 0 ? this.t : this.w / 1000;
    }

    public long b() {
        if (this.v == -1) {
            return -1;
        }
        return this.v / 1000;
    }

    public void a(bq... bqVarArr) {
        this.a.obtainMessage(1, bqVarArr).sendToTarget();
    }

    public void a(boolean z) {
        this.a.obtainMessage(3, z, 0).sendToTarget();
    }

    public void a(long j) {
        this.t = j;
        this.e.incrementAndGet();
        this.a.obtainMessage(6, ft.a(j), ft.b(j)).sendToTarget();
    }

    public void c() {
        this.a.sendEmptyMessage(4);
    }

    public void a(a aVar, int i, Object obj) {
        this.r++;
        this.a.obtainMessage(9, i, 0, Pair.create(aVar, obj)).sendToTarget();
    }

    public synchronized void b(com.google.ads.interactivemedia.v3.internal.ba.a r5, int r6, java.lang.Object r7) {
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
        r4 = this;
        monitor-enter(r4);
        r0 = r4.n;	 Catch:{ all -> 0x004c }
        if (r0 == 0) goto L_0x0024;	 Catch:{ all -> 0x004c }
    L_0x0005:
        r5 = "ExoPlayerImplInternal";	 Catch:{ all -> 0x004c }
        r7 = 57;	 Catch:{ all -> 0x004c }
        r0 = new java.lang.StringBuilder;	 Catch:{ all -> 0x004c }
        r0.<init>(r7);	 Catch:{ all -> 0x004c }
        r7 = "Sent message(";	 Catch:{ all -> 0x004c }
        r0.append(r7);	 Catch:{ all -> 0x004c }
        r0.append(r6);	 Catch:{ all -> 0x004c }
        r6 = ") after release. Message ignored.";	 Catch:{ all -> 0x004c }
        r0.append(r6);	 Catch:{ all -> 0x004c }
        r6 = r0.toString();	 Catch:{ all -> 0x004c }
        android.util.Log.w(r5, r6);	 Catch:{ all -> 0x004c }
        monitor-exit(r4);
        return;
    L_0x0024:
        r0 = r4.r;	 Catch:{ all -> 0x004c }
        r1 = r0 + 1;	 Catch:{ all -> 0x004c }
        r4.r = r1;	 Catch:{ all -> 0x004c }
        r1 = r4.a;	 Catch:{ all -> 0x004c }
        r2 = 9;	 Catch:{ all -> 0x004c }
        r3 = 0;	 Catch:{ all -> 0x004c }
        r5 = android.util.Pair.create(r5, r7);	 Catch:{ all -> 0x004c }
        r5 = r1.obtainMessage(r2, r6, r3, r5);	 Catch:{ all -> 0x004c }
        r5.sendToTarget();	 Catch:{ all -> 0x004c }
    L_0x003a:
        r5 = r4.s;	 Catch:{ all -> 0x004c }
        if (r5 > r0) goto L_0x004a;
    L_0x003e:
        r4.wait();	 Catch:{ InterruptedException -> 0x0042 }
        goto L_0x003a;
    L_0x0042:
        r5 = java.lang.Thread.currentThread();	 Catch:{ all -> 0x004c }
        r5.interrupt();	 Catch:{ all -> 0x004c }
        goto L_0x003a;
    L_0x004a:
        monitor-exit(r4);
        return;
    L_0x004c:
        r5 = move-exception;
        monitor-exit(r4);
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.bc.b(com.google.ads.interactivemedia.v3.internal.ba$a, int, java.lang.Object):void");
    }

    public synchronized void d() {
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
        monitor-enter(r2);
        r0 = r2.n;	 Catch:{ all -> 0x0024 }
        if (r0 == 0) goto L_0x0007;
    L_0x0005:
        monitor-exit(r2);
        return;
    L_0x0007:
        r0 = r2.a;	 Catch:{ all -> 0x0024 }
        r1 = 5;	 Catch:{ all -> 0x0024 }
        r0.sendEmptyMessage(r1);	 Catch:{ all -> 0x0024 }
    L_0x000d:
        r0 = r2.n;	 Catch:{ all -> 0x0024 }
        if (r0 != 0) goto L_0x001d;
    L_0x0011:
        r2.wait();	 Catch:{ InterruptedException -> 0x0015 }
        goto L_0x000d;
    L_0x0015:
        r0 = java.lang.Thread.currentThread();	 Catch:{ all -> 0x0024 }
        r0.interrupt();	 Catch:{ all -> 0x0024 }
        goto L_0x000d;	 Catch:{ all -> 0x0024 }
    L_0x001d:
        r0 = r2.b;	 Catch:{ all -> 0x0024 }
        r0.quit();	 Catch:{ all -> 0x0024 }
        monitor-exit(r2);
        return;
    L_0x0024:
        r0 = move-exception;
        monitor-exit(r2);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.bc.d():void");
    }

    public boolean handleMessage(Message message) {
        try {
            boolean z = false;
            switch (message.what) {
                case 1:
                    b((bq[]) message.obj);
                    return true;
                case 2:
                    e();
                    return true;
                case 3:
                    if (message.arg1 != null) {
                        z = true;
                    }
                    b(z);
                    return true;
                case 4:
                    j();
                    return true;
                case 5:
                    k();
                    return true;
                case 6:
                    b(ft.b(message.arg1, message.arg2));
                    return true;
                case 7:
                    i();
                    return true;
                case 8:
                    a(message.arg1, message.arg2);
                    return true;
                case 9:
                    a(message.arg1, message.obj);
                    return true;
                default:
                    return false;
            }
        } catch (Message message2) {
            Log.e("ExoPlayerImplInternal", "Internal track renderer error.", message2);
            this.c.obtainMessage(4, message2).sendToTarget();
            j();
            return true;
        } catch (Message message22) {
            Log.e("ExoPlayerImplInternal", "Internal runtime error.", message22);
            this.c.obtainMessage(4, new az(message22, true)).sendToTarget();
            j();
            return true;
        }
    }

    private void a(int i) {
        if (this.q != i) {
            this.q = i;
            this.c.obtainMessage(2, i, 0).sendToTarget();
        }
    }

    private void b(bq[] bqVarArr) throws az {
        l();
        this.k = bqVarArr;
        Arrays.fill(this.g, null);
        a((int) 2);
        e();
    }

    private void e() throws az {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        Object obj = 1;
        for (bq bqVar : this.k) {
            if (bqVar.v() == 0 && bqVar.f(this.w) == 0) {
                bqVar.s();
                obj = null;
            }
        }
        if (obj == null) {
            a(2, elapsedRealtime, 10);
            return;
        }
        long j = 0;
        obj = 1;
        Object obj2 = 1;
        for (int i = 0; i < this.k.length; i++) {
            bq bqVar2 = this.k[i];
            int u = bqVar2.u();
            bj[] bjVarArr = new bj[u];
            for (int i2 = 0; i2 < u; i2++) {
                bjVarArr[i2] = bqVar2.b(i2);
            }
            this.g[i] = bjVarArr;
            if (u > 0) {
                if (j != -1) {
                    long r = bqVar2.r();
                    if (r == -1) {
                        j = -1;
                    } else if (r != -2) {
                        j = Math.max(j, r);
                    }
                }
                int i3 = this.h[i];
                if (i3 >= 0 && i3 < bjVarArr.length) {
                    a(bqVar2, i3, false);
                    obj = (obj == null || !bqVar2.e()) ? null : 1;
                    obj2 = (obj2 == null || !a(bqVar2)) ? null : 1;
                }
            }
        }
        this.v = j;
        if (obj == null || (j != -1 && j > this.w)) {
            this.q = obj2 != null ? 4 : 3;
        } else {
            this.q = 5;
        }
        this.c.obtainMessage(1, this.q, 0, this.g).sendToTarget();
        if (this.o && this.q == 4) {
            f();
        }
        this.a.sendEmptyMessage(7);
    }

    private void a(bq bqVar, int i, boolean z) throws az {
        bqVar.b(i, this.w, z);
        this.f.add(bqVar);
        i = bqVar.b();
        if (i != 0) {
            fe.b(!this.m);
            this.m = i;
            this.l = bqVar;
        }
    }

    private boolean a(bq bqVar) {
        boolean z = true;
        if (bqVar.e()) {
            return true;
        }
        if (!bqVar.f()) {
            return false;
        }
        if (this.q == 4) {
            return true;
        }
        long r = bqVar.r();
        long q = bqVar.q();
        long j = this.p != null ? this.j : this.i;
        if (j > 0 && q != -1 && q != -3 && q < this.w + j) {
            if (r == -1 || r == -2 || q < r) {
                z = false;
            }
        }
        return z;
    }

    private void b(boolean z) throws az {
        try {
            this.p = false;
            this.o = z;
            if (!z) {
                g();
                h();
            } else if (this.q) {
                f();
                this.a.sendEmptyMessage(7);
            } else if (this.q) {
                this.a.sendEmptyMessage(7);
            }
            this.c.obtainMessage(3).sendToTarget();
        } catch (Throwable th) {
            this.c.obtainMessage(3).sendToTarget();
        }
    }

    private void f() throws az {
        int i = 0;
        this.p = false;
        this.d.b();
        while (i < this.f.size()) {
            ((bq) this.f.get(i)).w();
            i++;
        }
    }

    private void g() throws az {
        this.d.c();
        for (int i = 0; i < this.f.size(); i++) {
            d((bq) this.f.get(i));
        }
    }

    private void h() {
        if (this.m == null || !this.f.contains(this.l) || this.l.e()) {
            this.w = this.d.a();
        } else {
            this.w = this.m.a();
            this.d.a(this.w);
        }
        this.u = SystemClock.elapsedRealtime() * 1000;
    }

    private void i() throws az {
        fs.a("doSomeWork");
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j = this.v != -1 ? r6.v : Long.MAX_VALUE;
        h();
        long j2 = j;
        Object obj = 1;
        Object obj2 = 1;
        for (int i = 0; i < r6.f.size(); i++) {
            bq bqVar = (bq) r6.f.get(i);
            bqVar.b(r6.w, r6.u);
            obj = (obj == null || !bqVar.e()) ? null : 1;
            boolean a = a(bqVar);
            if (!a) {
                bqVar.s();
            }
            obj2 = (obj2 == null || !a) ? null : 1;
            if (j2 != -1) {
                long r = bqVar.r();
                long q = bqVar.q();
                if (q == -1) {
                    j2 = -1;
                } else if (q != -3) {
                    if (r == -1 || r == -2 || q < r) {
                        j2 = Math.min(j2, q);
                    }
                }
            }
        }
        r6.x = j2;
        if (obj != null && (r6.v == -1 || r6.v <= r6.w)) {
            a(5);
            g();
        } else if (r6.q == 3 && obj2 != null) {
            a(4);
            if (r6.o) {
                f();
            }
        } else if (r6.q == 4 && obj2 == null) {
            r6.p = r6.o;
            a(3);
            g();
        }
        r6.a.removeMessages(7);
        if ((r6.o && r6.q == 4) || r6.q == 3) {
            a(7, elapsedRealtime, 10);
        } else if (!r6.f.isEmpty()) {
            a(7, elapsedRealtime, 1000);
        }
        fs.a();
    }

    private void a(int i, long j, long j2) {
        j = (j + j2) - SystemClock.elapsedRealtime();
        if (j <= 0) {
            this.a.sendEmptyMessage(i);
        } else {
            this.a.sendEmptyMessageDelayed(i, j);
        }
    }

    private void b(long j) throws az {
        try {
            if (j != this.w / 1000) {
                int i = 0;
                this.p = false;
                this.w = j * 1000;
                this.d.c();
                this.d.a(this.w);
                if (this.q != 1) {
                    if (this.q != 2) {
                        while (i < this.f.size()) {
                            bq bqVar = (bq) this.f.get(i);
                            d(bqVar);
                            bqVar.d(this.w);
                            i++;
                        }
                        a((int) 3);
                        this.a.sendEmptyMessage(7);
                        this.e.decrementAndGet();
                        return;
                    }
                }
                this.e.decrementAndGet();
            }
        } finally {
            this.e.decrementAndGet();
        }
    }

    private void j() {
        l();
        a(1);
    }

    private void k() {
        l();
        a(1);
        synchronized (this) {
            this.n = true;
            notifyAll();
        }
    }

    private void l() {
        this.a.removeMessages(7);
        this.a.removeMessages(2);
        int i = 0;
        this.p = false;
        this.d.c();
        if (this.k != null) {
            while (i < this.k.length) {
                bq bqVar = this.k[i];
                b(bqVar);
                c(bqVar);
                i++;
            }
            this.k = null;
            this.m = null;
            this.l = null;
            this.f.clear();
        }
    }

    private void b(bq bqVar) {
        try {
            e(bqVar);
        } catch (bq bqVar2) {
            Log.e("ExoPlayerImplInternal", "Stop failed.", bqVar2);
        } catch (bq bqVar22) {
            Log.e("ExoPlayerImplInternal", "Stop failed.", bqVar22);
        }
    }

    private void c(bq bqVar) {
        try {
            bqVar.z();
        } catch (bq bqVar2) {
            Log.e("ExoPlayerImplInternal", "Release failed.", bqVar2);
        } catch (bq bqVar22) {
            Log.e("ExoPlayerImplInternal", "Release failed.", bqVar22);
        }
    }

    private <T> void a(int i, Object obj) throws az {
        try {
            Pair pair = (Pair) obj;
            ((a) pair.first).a(i, pair.second);
            if (!(this.q == 1 || this.q == 2)) {
                this.a.sendEmptyMessage(7);
            }
            synchronized (this) {
                this.s++;
                notifyAll();
            }
        } catch (Throwable th) {
            synchronized (this) {
                this.s += 1;
                notifyAll();
            }
        }
    }

    private void a(int i, int i2) throws az {
        if (this.h[i] != i2) {
            this.h[i] = i2;
            boolean z = true;
            if (this.q != 1) {
                if (this.q != 2) {
                    bq bqVar = this.k[i];
                    int v = bqVar.v();
                    if (!(v == 0 || v == -1)) {
                        if (bqVar.u() != 0) {
                            Object obj;
                            if (v != 2) {
                                if (v != 3) {
                                    obj = null;
                                    i = (i2 >= 0 || i2 >= this.g[i].length) ? 0 : 1;
                                    if (obj != null) {
                                        if (i == 0 && bqVar == this.l) {
                                            this.d.a(this.m.a());
                                        }
                                        e(bqVar);
                                        this.f.remove(bqVar);
                                    }
                                    if (i != 0) {
                                        i = (this.o == 0 && this.q == 4) ? 1 : 0;
                                        if (obj == null || i == 0) {
                                            z = false;
                                        }
                                        a(bqVar, i2, z);
                                        if (i != 0) {
                                            bqVar.w();
                                        }
                                        this.a.sendEmptyMessage(7);
                                    }
                                }
                            }
                            obj = 1;
                            if (i2 >= 0) {
                            }
                            if (obj != null) {
                                this.d.a(this.m.a());
                                e(bqVar);
                                this.f.remove(bqVar);
                            }
                            if (i != 0) {
                                if (this.o == 0) {
                                }
                                if (obj == null) {
                                }
                                z = false;
                                a(bqVar, i2, z);
                                if (i != 0) {
                                    bqVar.w();
                                }
                                this.a.sendEmptyMessage(7);
                            }
                        }
                    }
                }
            }
        }
    }

    private void d(bq bqVar) throws az {
        if (bqVar.v() == 3) {
            bqVar.x();
        }
    }

    private void e(bq bqVar) throws az {
        d(bqVar);
        if (bqVar.v() == 2) {
            bqVar.y();
            if (bqVar == this.l) {
                this.m = null;
                this.l = null;
            }
        }
    }
}
