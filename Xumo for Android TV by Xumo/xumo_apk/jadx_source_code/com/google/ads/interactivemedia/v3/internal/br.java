package com.google.ads.interactivemedia.v3.internal;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.view.Choreographer;
import android.view.Choreographer.FrameCallback;
import android.view.WindowManager;

@TargetApi(16)
/* compiled from: IMASDK */
public final class br {
    private final a a;
    private final boolean b;
    private final long c;
    private final long d;
    private long e;
    private long f;
    private long g;
    private boolean h;
    private long i;
    private long j;
    private long k;

    /* compiled from: IMASDK */
    private static final class a implements Callback, FrameCallback {
        private static final a b = new a();
        public volatile long a;
        private final Handler c;
        private final HandlerThread d = new HandlerThread("ChoreographerOwner:Handler");
        private Choreographer e;
        private int f;

        public static a a() {
            return b;
        }

        private a() {
            this.d.start();
            this.c = new Handler(this.d.getLooper(), this);
            this.c.sendEmptyMessage(0);
        }

        public void b() {
            this.c.sendEmptyMessage(1);
        }

        public void c() {
            this.c.sendEmptyMessage(2);
        }

        public void doFrame(long j) {
            this.a = j;
            this.e.postFrameCallbackDelayed(this, 500);
        }

        public boolean handleMessage(Message message) {
            switch (message.what) {
                case null:
                    d();
                    return true;
                case 1:
                    e();
                    return true;
                case 2:
                    f();
                    return true;
                default:
                    return null;
            }
        }

        private void d() {
            this.e = Choreographer.getInstance();
        }

        private void e() {
            this.f++;
            if (this.f == 1) {
                this.e.postFrameCallback(this);
            }
        }

        private void f() {
            this.f--;
            if (this.f == 0) {
                this.e.removeFrameCallback(this);
                this.a = 0;
            }
        }
    }

    public br() {
        this(-1.0f, false);
    }

    protected void c() {
    }

    public br(Context context) {
        this(a(context), true);
    }

    private br(float f, boolean z) {
        this.b = z;
        if (z) {
            this.a = a.a();
            f = (double) f;
            Double.isNaN(f);
            this.c = (long) (1.0E9d / f);
            this.d = (this.c * 80) / 100;
            return;
        }
        this.a = 0.0f;
        this.c = -1;
        this.d = -1;
    }

    public void a() {
        this.h = false;
        if (this.b) {
            this.a.b();
        }
    }

    public void b() {
        if (this.b) {
            this.a.c();
        }
    }

    public long a(long j, long j2) {
        long j3;
        long j4;
        long j5 = j;
        long j6 = j2;
        long j7 = 1000 * j5;
        if (this.h) {
            if (j5 != r0.e) {
                r0.k++;
                r0.f = r0.g;
            }
            if (r0.k >= 6) {
                long j8;
                j3 = r0.f + ((j7 - r0.j) / r0.k);
                if (b(j3, j6)) {
                    r0.h = false;
                    j8 = j6;
                    j3 = j7;
                } else {
                    j8 = (r0.i + j3) - r0.j;
                }
                j4 = j8;
                if (!r0.h) {
                    r0.j = j7;
                    r0.i = j6;
                    r0.k = 0;
                    r0.h = true;
                    c();
                }
                r0.e = j5;
                r0.g = j3;
                if (r0.a != null) {
                    if (r0.a.a == 0) {
                        return a(j4, r0.a.a, r0.c) - r0.d;
                    }
                }
                return j4;
            } else if (b(j7, j6)) {
                r0.h = false;
            }
        }
        j4 = j6;
        j3 = j7;
        if (r0.h) {
            r0.j = j7;
            r0.i = j6;
            r0.k = 0;
            r0.h = true;
            c();
        }
        r0.e = j5;
        r0.g = j3;
        if (r0.a != null) {
            if (r0.a.a == 0) {
                return a(j4, r0.a.a, r0.c) - r0.d;
            }
        }
        return j4;
    }

    private boolean b(long j, long j2) {
        return Math.abs((j2 - this.i) - (j - this.j)) > 20000000 ? 1 : 0;
    }

    private static long a(long j, long j2, long j3) {
        j2 += ((j - j2) / j3) * j3;
        if (j <= j2) {
            j3 = j2 - j3;
        } else {
            long j4 = j2;
            j2 = j3 + j2;
            j3 = j4;
        }
        return j2 - j < j - j3 ? j2 : j3;
    }

    private static float a(Context context) {
        return ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRefreshRate();
    }
}
