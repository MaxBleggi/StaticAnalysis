package com.google.ads.interactivemedia.v3.internal;

import android.content.Context;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.io.IOException;

/* compiled from: IMASDK */
public final class ex implements fc {
    private final fc a;
    private final fc b;
    private final fc c;
    private final fc d;
    private fc e;

    public ex(Context context, fb fbVar, String str, boolean z) {
        this(context, fbVar, new ew(str, null, fbVar, 8000, 8000, z));
    }

    public ex(Context context, fb fbVar, fc fcVar) {
        this.a = (fc) fe.a((Object) fcVar);
        this.b = new ey(fbVar);
        this.c = new er(context, fbVar);
        this.d = new es(context, fbVar);
    }

    public long a(eu euVar) throws IOException {
        fe.b(this.e == null);
        String scheme = euVar.a.getScheme();
        if (ft.a(euVar.a)) {
            if (euVar.a.getPath().startsWith("/android_asset/")) {
                this.e = this.c;
            } else {
                this.e = this.b;
            }
        } else if ("asset".equals(scheme)) {
            this.e = this.c;
        } else if (Param.CONTENT.equals(scheme)) {
            this.e = this.d;
        } else {
            this.e = this.a;
        }
        return this.e.a(euVar);
    }

    public int a(byte[] bArr, int i, int i2) throws IOException {
        return this.e.a(bArr, i, i2);
    }

    public void a() throws IOException {
        if (this.e != null) {
            try {
                this.e.a();
            } finally {
                this.e = null;
            }
        }
    }
}
