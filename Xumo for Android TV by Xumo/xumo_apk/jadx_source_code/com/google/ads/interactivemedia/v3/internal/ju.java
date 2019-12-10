package com.google.ads.interactivemedia.v3.internal;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.google.ads.interactivemedia.v3.api.Ad;
import com.google.ads.interactivemedia.v3.api.player.VideoProgressUpdate;
import com.google.ads.interactivemedia.v3.internal.jc.c;
import com.google.ads.interactivemedia.v3.internal.jd.e;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: IMASDK */
public class ju extends RelativeLayout implements OnClickListener, e, com.google.ads.interactivemedia.v3.internal.ji.b {
    private FrameLayout a;
    private List<a> b;
    private final float c;
    private final String d;
    private jd e;
    private boolean f;
    private float g;
    private String h;
    private b i;
    private jr j;
    private jt k;
    private jq l;
    private int m;
    private int n;

    /* compiled from: IMASDK */
    private enum b {
        NOT_SKIPPABLE,
        WAITING_TO_SKIP,
        SKIPPABLE
    }

    /* compiled from: IMASDK */
    public interface a extends com.google.ads.interactivemedia.v3.internal.jq.a {
        void a();

        void b();
    }

    public ju(Context context, jt jtVar, jd jdVar, String str) {
        this(context, jtVar, jdVar, str, null, null);
    }

    public View a() {
        return this;
    }

    ju(Context context, jt jtVar, jd jdVar, String str, jq jqVar, jr jrVar) {
        super(context);
        this.b = new ArrayList();
        this.f = false;
        this.e = jdVar;
        this.d = str;
        this.k = jtVar;
        this.l = jqVar;
        this.j = jrVar;
        this.c = getResources().getDisplayMetrics().density;
        d(context);
        if (jtVar.b != null) {
            c(context);
        }
        a(this.f);
    }

    public void a(c cVar, String str) {
        switch (cVar) {
            case adRemainingTime:
                a(str);
                return;
            case learnMore:
                b(str);
                return;
            case preSkipButton:
                this.j.a(str);
                return;
            case skipButton:
                this.j.a(str);
                this.i = b.SKIPPABLE;
                for (a b : this.b) {
                    b.b();
                }
                return;
            default:
                return;
        }
    }

    private void c(Context context) {
        a(context);
        this.a = new FrameLayout(context);
        this.a.addView(this.j, new LayoutParams(-2, -2));
        int a = js.a(this.k.r, this.c);
        this.a.setPadding(a, a, 0, a);
        this.a.setOnClickListener(this);
        View frameLayout = new FrameLayout(context);
        frameLayout.addView(this.a, new LayoutParams(-2, -2));
        frameLayout.setPadding(0, 0, 0, js.a(this.k.s, this.c));
        context = new LayoutParams(-2, -2);
        context.addRule(12);
        context.addRule(11);
        frameLayout.setLayoutParams(context);
        addView(frameLayout);
    }

    private void d(Context context) {
        b(context);
        context = new LayoutParams(-1, -2);
        context.addRule(10);
        addView(this.l, context);
        this.l.a(new com.google.ads.interactivemedia.v3.internal.jq.a(this) {
            final /* synthetic */ ju a;

            {
                this.a = r1;
            }

            public void c() {
                for (a c : this.a.b) {
                    c.c();
                }
            }
        });
    }

    private void a(String str) {
        if (!this.f) {
            this.l.a(str);
        } else if (TextUtils.isEmpty(this.h)) {
            this.l.a(str);
        } else {
            jq jqVar = this.l;
            String str2 = this.h;
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 3) + String.valueOf(str2).length());
            stringBuilder.append(str);
            stringBuilder.append(": ");
            stringBuilder.append(str2);
            stringBuilder.append("»");
            jqVar.a(stringBuilder.toString());
        }
    }

    private void b(String str) {
        this.l.b(str);
    }

    public void a(a aVar) {
        this.b.add(aVar);
    }

    public void a(Ad ad) {
        this.m = ad.getAdPodInfo().getAdPosition();
        this.n = ad.getAdPodInfo().getTotalAds();
        a("");
        if (this.k.m) {
            b(this.k.n);
            this.e.b(new jc(com.google.ads.interactivemedia.v3.internal.jc.b.i18n, c.learnMore, this.d));
        }
        if (ad.isSkippable() != null) {
            this.i = b.WAITING_TO_SKIP;
            this.a.setVisibility(0);
            ad = new HashMap(1);
            ad.put("seconds", Integer.valueOf(5));
            this.e.b(new jc(com.google.ads.interactivemedia.v3.internal.jc.b.i18n, c.preSkipButton, this.d, ad));
        } else {
            this.i = b.NOT_SKIPPABLE;
            if (this.a != null) {
                this.a.setVisibility(4);
            }
        }
        setVisibility(0);
    }

    public void a(VideoProgressUpdate videoProgressUpdate) {
        if (videoProgressUpdate != null) {
            if (videoProgressUpdate.getDuration() >= 0.0f) {
                float duration = videoProgressUpdate.getDuration() - videoProgressUpdate.getCurrentTime();
                Object obj = Math.floor((double) duration) != Math.floor((double) this.g) ? 1 : null;
                if (obj != null) {
                    Map hashMap = new HashMap(4);
                    int i = (int) duration;
                    hashMap.put("minutes", Integer.valueOf(i / 60));
                    hashMap.put("seconds", Integer.valueOf(i % 60));
                    hashMap.put("adPosition", Integer.valueOf(this.m));
                    hashMap.put("totalAds", Integer.valueOf(this.n));
                    this.e.b(new jc(com.google.ads.interactivemedia.v3.internal.jc.b.i18n, c.adRemainingTime, this.d, hashMap));
                }
                this.g = duration;
                if (this.i == b.WAITING_TO_SKIP) {
                    duration = 5.0f - videoProgressUpdate.getCurrentTime();
                    if (duration <= 0.0f) {
                        this.e.b(new jc(com.google.ads.interactivemedia.v3.internal.jc.b.i18n, c.skipButton, this.d));
                    } else if (obj != null) {
                        videoProgressUpdate = new HashMap(1);
                        videoProgressUpdate.put("seconds", Float.valueOf(duration));
                        this.e.b(new jc(com.google.ads.interactivemedia.v3.internal.jc.b.i18n, c.preSkipButton, this.d, videoProgressUpdate));
                    }
                }
            }
        }
    }

    public void a(boolean z) {
        this.f = z;
    }

    public void b() {
        setVisibility(4);
    }

    public void onClick(View view) {
        if (view == this.a && this.i == b.SKIPPABLE) {
            for (a a : this.b) {
                a.a();
            }
        }
    }

    protected void a(Context context) {
        this.j = new jr(context, this.k);
    }

    protected void b(Context context) {
        this.l = new jq(context, this.k);
    }
}
