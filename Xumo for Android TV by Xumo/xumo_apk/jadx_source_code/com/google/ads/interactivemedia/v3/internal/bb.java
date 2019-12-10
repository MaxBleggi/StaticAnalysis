package com.google.ads.interactivemedia.v3.internal;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.google.ads.interactivemedia.v3.internal.ba.a;
import com.google.ads.interactivemedia.v3.internal.ba.c;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;

/* compiled from: IMASDK */
final class bb implements ba {
    private final Handler a;
    private final bc b;
    private final CopyOnWriteArraySet<c> c = new CopyOnWriteArraySet();
    private final bj[][] d;
    private final int[] e;
    private boolean f = false;
    private int g = 1;
    private int h;

    @SuppressLint({"HandlerLeak"})
    public bb(int i, int i2, int i3) {
        Log.i("ExoPlayerImpl", "Init ExoPlayerLib/1.5.16");
        this.d = new bj[i][];
        this.e = new int[i];
        this.a = new Handler(this) {
            final /* synthetic */ bb a;

            {
                this.a = r1;
            }

            public void handleMessage(Message message) {
                this.a.a(message);
            }
        };
        this.b = new bc(this.a, this.f, this.e, i2, i3);
    }

    public void a(c cVar) {
        this.c.add(cVar);
    }

    public void b(c cVar) {
        this.c.remove(cVar);
    }

    public int a() {
        return this.g;
    }

    public void a(bq... bqVarArr) {
        Arrays.fill(this.d, null);
        this.b.a(bqVarArr);
    }

    public void a(boolean z) {
        if (this.f != z) {
            this.f = z;
            this.h++;
            this.b.a(z);
            Iterator it = this.c.iterator();
            while (it.hasNext()) {
                ((c) it.next()).a(z, this.g);
            }
        }
    }

    public void a(long j) {
        this.b.a(j);
    }

    public void b() {
        this.b.c();
    }

    public void c() {
        this.b.d();
        this.a.removeCallbacksAndMessages(null);
    }

    public void a(a aVar, int i, Object obj) {
        this.b.a(aVar, i, obj);
    }

    public void b(a aVar, int i, Object obj) {
        this.b.b(aVar, i, obj);
    }

    public long d() {
        return this.b.b();
    }

    public long e() {
        return this.b.a();
    }

    void a(Message message) {
        switch (message.what) {
            case 1:
                System.arraycopy(message.obj, 0, this.d, 0, this.d.length);
                this.g = message.arg1;
                message = this.c.iterator();
                while (message.hasNext()) {
                    ((c) message.next()).a(this.f, this.g);
                }
                return;
            case 2:
                this.g = message.arg1;
                message = this.c.iterator();
                while (message.hasNext()) {
                    ((c) message.next()).a(this.f, this.g);
                }
                return;
            case 3:
                this.h--;
                if (this.h == null) {
                    message = this.c.iterator();
                    while (message.hasNext()) {
                        ((c) message.next()).a();
                    }
                    return;
                }
                return;
            case 4:
                az azVar = (az) message.obj;
                Iterator it = this.c.iterator();
                while (it.hasNext()) {
                    ((c) it.next()).a(azVar);
                }
                return;
            default:
                return;
        }
    }
}
