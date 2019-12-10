package com.google.ads.interactivemedia.v3.internal;

import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/* compiled from: IMASDK */
public class q {
    @SuppressLint({"StaticFieldLeak"})
    private static q a = new q();
    private Context b;
    private BroadcastReceiver c;
    private boolean d;
    private boolean e;
    private a f;

    /* compiled from: IMASDK */
    public interface a {
        void a(boolean z);
    }

    private q() {
    }

    public static q a() {
        return a;
    }

    public void a(Context context) {
        this.b = context.getApplicationContext();
    }

    public void b() {
        e();
        this.d = true;
        g();
    }

    public void c() {
        f();
        this.d = false;
        this.e = false;
        this.f = null;
    }

    public boolean d() {
        return this.e ^ 1;
    }

    public void a(a aVar) {
        this.f = aVar;
    }

    private void a(boolean z) {
        if (this.e != z) {
            this.e = z;
            if (this.d) {
                g();
                if (this.f) {
                    this.f.a(d());
                }
            }
        }
    }

    private void e() {
        this.c = new BroadcastReceiver(this) {
            final /* synthetic */ q a;

            {
                this.a = r1;
            }

            public void onReceive(Context context, Intent intent) {
                if (intent != null) {
                    if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                        this.a.a(true);
                    } else if ("android.intent.action.USER_PRESENT".equals(intent.getAction())) {
                        this.a.a(false);
                    } else if ("android.intent.action.SCREEN_ON".equals(intent.getAction()) != null) {
                        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService("keyguard");
                        if (keyguardManager != null && keyguardManager.inKeyguardRestrictedInputMode() == null) {
                            this.a.a(false);
                        }
                    }
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        this.b.registerReceiver(this.c, intentFilter);
    }

    private void f() {
        if (this.b != null && this.c != null) {
            this.b.unregisterReceiver(this.c);
            this.c = null;
        }
    }

    private void g() {
        boolean z = this.e ^ 1;
        for (g e : p.a().b()) {
            e.e().a(z);
        }
    }
}
