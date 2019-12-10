package com.google.ads.interactivemedia.v3.internal;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* compiled from: IMASDK */
public class aj implements com.google.ads.interactivemedia.v3.internal.u.a {
    private static aj a = new aj();
    private static Handler b = new Handler(Looper.getMainLooper());
    private static Handler c = null;
    private static final Runnable j = new Runnable() {
        public void run() {
            aj.a().i();
        }
    };
    private static final Runnable k = new Runnable() {
        public void run() {
            if (aj.c != null) {
                aj.c.post(aj.j);
                aj.c.postDelayed(aj.k, 200);
            }
        }
    };
    private List<a> d = new ArrayList();
    private int e;
    private v f = new v();
    private ah g = new ah();
    private ai h = new ai(new an());
    private double i;

    /* compiled from: IMASDK */
    public interface a {
        void a(int i, long j);
    }

    aj() {
    }

    public static aj a() {
        return a;
    }

    public void b() {
        l();
    }

    public void c() {
        d();
        this.d.clear();
        b.post(new Runnable(this) {
            final /* synthetic */ aj a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.h.a();
            }
        });
    }

    public void d() {
        m();
    }

    private void i() {
        j();
        e();
        k();
    }

    private void j() {
        this.e = 0;
        this.i = ae.a();
    }

    private void k() {
        a((long) (ae.a() - this.i));
    }

    void e() {
        this.g.c();
        double a = ae.a();
        u a2 = this.f.a();
        if (this.g.b().size() > 0) {
            this.h.b(a2.a(null), this.g.b(), a);
        }
        if (this.g.a().size() > 0) {
            JSONObject a3 = a2.a(null);
            a(null, a2, a3, ak.PARENT_VIEW);
            ac.a(a3);
            this.h.a(a3, this.g.a(), a);
        } else {
            this.h.a();
        }
        this.g.d();
    }

    public void a(View view, u uVar, JSONObject jSONObject) {
        if (ag.d(view)) {
            ak c = this.g.c(view);
            if (c != ak.UNDERLYING_VIEW) {
                JSONObject a = uVar.a(view);
                ac.a(jSONObject, a);
                if (a(view, a) == null) {
                    b(view, a);
                    a(view, uVar, a, c);
                }
                this.e++;
            }
        }
    }

    private void a(View view, u uVar, JSONObject jSONObject, ak akVar) {
        uVar.a(view, jSONObject, this, akVar == ak.PARENT_VIEW ? true : null);
    }

    private boolean a(View view, JSONObject jSONObject) {
        String a = this.g.a(view);
        if (a == null) {
            return null;
        }
        ac.a(jSONObject, a);
        this.g.e();
        return true;
    }

    private void b(View view, JSONObject jSONObject) {
        List b = this.g.b(view);
        if (b != null) {
            ac.a(jSONObject, b);
        }
    }

    private void a(long j) {
        if (this.d.size() > 0) {
            for (a a : this.d) {
                a.a(this.e, j);
            }
        }
    }

    private void l() {
        if (c == null) {
            c = new Handler(Looper.getMainLooper());
            c.post(j);
            c.postDelayed(k, 200);
        }
    }

    private void m() {
        if (c != null) {
            c.removeCallbacks(k);
            c = null;
        }
    }
}
