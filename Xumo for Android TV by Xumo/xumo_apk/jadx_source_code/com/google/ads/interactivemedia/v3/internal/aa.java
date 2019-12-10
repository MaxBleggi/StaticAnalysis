package com.google.ads.interactivemedia.v3.internal;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.webkit.WebView;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import java.util.List;

/* compiled from: IMASDK */
public class aa extends y {
    private WebView a;
    private List<j> b;
    private final String c;

    public aa(List<j> list, String str) {
        this.b = list;
        this.c = str;
    }

    public void a() {
        super.a();
        g();
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    void g() {
        this.a = new WebView(r.a().b());
        this.a.getSettings().setJavaScriptEnabled(true);
        a(this.a);
        s.a().a(this.a, this.c);
        for (j b : this.b) {
            s.a().b(this.a, b.b().toExternalForm());
        }
    }

    public void b() {
        super.b();
        new Handler().postDelayed(new Runnable(this) {
            final /* synthetic */ aa a;
            private WebView b = this.a.a;

            {
                this.a = r1;
            }

            public void run() {
                this.b.destroy();
            }
        }, AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS);
        this.a = null;
    }
}
