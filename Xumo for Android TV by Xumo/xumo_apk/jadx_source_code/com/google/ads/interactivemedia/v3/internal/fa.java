package com.google.ads.interactivemedia.v3.internal;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

/* compiled from: IMASDK */
public final class fa {
    private final ExecutorService a;
    private b b;
    private boolean c;

    /* compiled from: IMASDK */
    public interface a {
        void a(c cVar);

        void a(c cVar, IOException iOException);

        void b(c cVar);
    }

    @SuppressLint({"HandlerLeak"})
    /* compiled from: IMASDK */
    private final class b extends Handler implements Runnable {
        final /* synthetic */ fa a;
        private final c b;
        private final a c;
        private volatile Thread d;

        public b(fa faVar, Looper looper, c cVar, a aVar) {
            this.a = faVar;
            super(looper);
            this.b = cVar;
            this.c = aVar;
        }

        public void a() {
            this.b.a();
            if (this.d != null) {
                this.d.interrupt();
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
            r4 = this;
            r0 = 0;
            r1 = 1;
            r2 = java.lang.Thread.currentThread();	 Catch:{ IOException -> 0x0066, InterruptedException -> 0x0059, Exception -> 0x0044, Error -> 0x0033 }
            r4.d = r2;	 Catch:{ IOException -> 0x0066, InterruptedException -> 0x0059, Exception -> 0x0044, Error -> 0x0033 }
            r2 = r4.b;	 Catch:{ IOException -> 0x0066, InterruptedException -> 0x0059, Exception -> 0x0044, Error -> 0x0033 }
            r2 = r2.b();	 Catch:{ IOException -> 0x0066, InterruptedException -> 0x0059, Exception -> 0x0044, Error -> 0x0033 }
            if (r2 != 0) goto L_0x002f;	 Catch:{ IOException -> 0x0066, InterruptedException -> 0x0059, Exception -> 0x0044, Error -> 0x0033 }
        L_0x0010:
            r2 = r4.b;	 Catch:{ IOException -> 0x0066, InterruptedException -> 0x0059, Exception -> 0x0044, Error -> 0x0033 }
            r2 = r2.getClass();	 Catch:{ IOException -> 0x0066, InterruptedException -> 0x0059, Exception -> 0x0044, Error -> 0x0033 }
            r2 = r2.getSimpleName();	 Catch:{ IOException -> 0x0066, InterruptedException -> 0x0059, Exception -> 0x0044, Error -> 0x0033 }
            r2 = java.lang.String.valueOf(r2);	 Catch:{ IOException -> 0x0066, InterruptedException -> 0x0059, Exception -> 0x0044, Error -> 0x0033 }
            r3 = ".load()";	 Catch:{ IOException -> 0x0066, InterruptedException -> 0x0059, Exception -> 0x0044, Error -> 0x0033 }
            r2 = r2.concat(r3);	 Catch:{ IOException -> 0x0066, InterruptedException -> 0x0059, Exception -> 0x0044, Error -> 0x0033 }
            com.google.ads.interactivemedia.v3.internal.fs.a(r2);	 Catch:{ IOException -> 0x0066, InterruptedException -> 0x0059, Exception -> 0x0044, Error -> 0x0033 }
            r2 = r4.b;	 Catch:{ IOException -> 0x0066, InterruptedException -> 0x0059, Exception -> 0x0044, Error -> 0x0033 }
            r2.c();	 Catch:{ IOException -> 0x0066, InterruptedException -> 0x0059, Exception -> 0x0044, Error -> 0x0033 }
            com.google.ads.interactivemedia.v3.internal.fs.a();	 Catch:{ IOException -> 0x0066, InterruptedException -> 0x0059, Exception -> 0x0044, Error -> 0x0033 }
        L_0x002f:
            r4.sendEmptyMessage(r0);	 Catch:{ IOException -> 0x0066, InterruptedException -> 0x0059, Exception -> 0x0044, Error -> 0x0033 }
            goto L_0x006e;
        L_0x0033:
            r0 = move-exception;
            r1 = "LoadTask";
            r2 = "Unexpected error loading stream";
            android.util.Log.e(r1, r2, r0);
            r1 = 2;
            r1 = r4.obtainMessage(r1, r0);
            r1.sendToTarget();
            throw r0;
        L_0x0044:
            r0 = move-exception;
            r2 = "LoadTask";
            r3 = "Unexpected exception loading stream";
            android.util.Log.e(r2, r3, r0);
            r2 = new com.google.ads.interactivemedia.v3.internal.fa$d;
            r2.<init>(r0);
            r0 = r4.obtainMessage(r1, r2);
            r0.sendToTarget();
            goto L_0x006e;
        L_0x0059:
            r1 = r4.b;
            r1 = r1.b();
            com.google.ads.interactivemedia.v3.internal.fe.b(r1);
            r4.sendEmptyMessage(r0);
            goto L_0x006e;
        L_0x0066:
            r0 = move-exception;
            r0 = r4.obtainMessage(r1, r0);
            r0.sendToTarget();
        L_0x006e:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.fa.b.run():void");
        }

        public void handleMessage(Message message) {
            if (message.what != 2) {
                b();
                if (this.b.b()) {
                    this.c.b(this.b);
                    return;
                }
                switch (message.what) {
                    case 0:
                        this.c.a(this.b);
                        break;
                    case 1:
                        this.c.a(this.b, (IOException) message.obj);
                        break;
                    default:
                        break;
                }
                return;
            }
            throw ((Error) message.obj);
        }

        private void b() {
            this.a.c = false;
            this.a.b = null;
        }
    }

    /* compiled from: IMASDK */
    public interface c {
        void a();

        boolean b();

        void c() throws IOException, InterruptedException;
    }

    /* compiled from: IMASDK */
    public static final class d extends IOException {
        public d(Exception exception) {
            String simpleName = exception.getClass().getSimpleName();
            String message = exception.getMessage();
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(simpleName).length() + 13) + String.valueOf(message).length());
            stringBuilder.append("Unexpected ");
            stringBuilder.append(simpleName);
            stringBuilder.append(": ");
            stringBuilder.append(message);
            super(stringBuilder.toString(), exception);
        }
    }

    public fa(String str) {
        this.a = ft.a(str);
    }

    public void a(c cVar, a aVar) {
        Looper myLooper = Looper.myLooper();
        fe.b(myLooper != null);
        a(myLooper, cVar, aVar);
    }

    public void a(Looper looper, c cVar, a aVar) {
        fe.b(this.c ^ true);
        this.c = true;
        this.b = new b(this, looper, cVar, aVar);
        this.a.submit(this.b);
    }

    public boolean a() {
        return this.c;
    }

    public void b() {
        fe.b(this.c);
        this.b.a();
    }

    public void a(Runnable runnable) {
        if (this.c) {
            b();
        }
        if (runnable != null) {
            this.a.submit(runnable);
        }
        this.a.shutdown();
    }
}
