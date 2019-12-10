package com.google.ads.interactivemedia.v3.internal;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/* compiled from: IMASDK */
public abstract class kd extends kc {
    static boolean d = false;
    private static Method e;
    private static Method f;
    private static Method g;
    private static Method h;
    private static Method i;
    private static Method j;
    private static Method k;
    private static Method l;
    private static Method m;
    private static Method n;
    private static Method o;
    private static Method p;
    private static Method q;
    private static String r;
    private static String s;
    private static String t;
    private static long u;
    private static kj v;

    /* compiled from: IMASDK */
    static class a extends Exception {
        public a(Throwable th) {
            super(th);
        }
    }

    protected static synchronized void a(java.lang.String r3, android.content.Context r4, com.google.ads.interactivemedia.v3.internal.kh r5) {
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
        r0 = com.google.ads.interactivemedia.v3.internal.kd.class;
        monitor-enter(r0);
        r1 = d;	 Catch:{ all -> 0x0023 }
        if (r1 != 0) goto L_0x0021;
    L_0x0007:
        r1 = new com.google.ads.interactivemedia.v3.internal.kj;	 Catch:{ a -> 0x0021, a -> 0x0021 }
        r2 = 0;	 Catch:{ a -> 0x0021, a -> 0x0021 }
        r1.<init>(r5, r2);	 Catch:{ a -> 0x0021, a -> 0x0021 }
        v = r1;	 Catch:{ a -> 0x0021, a -> 0x0021 }
        r = r3;	 Catch:{ a -> 0x0021, a -> 0x0021 }
        k(r4);	 Catch:{ a -> 0x0021, a -> 0x0021 }
        r3 = b();	 Catch:{ a -> 0x0021, a -> 0x0021 }
        r3 = r3.longValue();	 Catch:{ a -> 0x0021, a -> 0x0021 }
        u = r3;	 Catch:{ a -> 0x0021, a -> 0x0021 }
        r3 = 1;	 Catch:{ a -> 0x0021, a -> 0x0021 }
        d = r3;	 Catch:{ a -> 0x0021, a -> 0x0021 }
    L_0x0021:
        monitor-exit(r0);
        return;
    L_0x0023:
        r3 = move-exception;
        monitor-exit(r0);
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.kd.a(java.lang.String, android.content.Context, com.google.ads.interactivemedia.v3.internal.kh):void");
    }

    protected kd(Context context, kh khVar, ki kiVar) {
        super(context, khVar, kiVar);
    }

    protected void b(android.content.Context r9) {
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
        r8 = this;
        r0 = 1;
        r1 = c();	 Catch:{ a -> 0x0008 }
        r8.a(r0, r1);	 Catch:{ a -> 0x0008 }
    L_0x0008:
        r1 = 2;
        r2 = a();	 Catch:{ a -> 0x0010 }
        r8.a(r1, r2);	 Catch:{ a -> 0x0010 }
    L_0x0010:
        r1 = b();	 Catch:{ a -> 0x0035 }
        r1 = r1.longValue();	 Catch:{ a -> 0x0035 }
        r3 = 25;	 Catch:{ a -> 0x0035 }
        r8.a(r3, r1);	 Catch:{ a -> 0x0035 }
        r3 = u;	 Catch:{ a -> 0x0035 }
        r5 = 0;	 Catch:{ a -> 0x0035 }
        r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1));	 Catch:{ a -> 0x0035 }
        if (r7 == 0) goto L_0x0035;	 Catch:{ a -> 0x0035 }
    L_0x0025:
        r3 = 17;	 Catch:{ a -> 0x0035 }
        r4 = u;	 Catch:{ a -> 0x0035 }
        r6 = 0;	 Catch:{ a -> 0x0035 }
        r1 = r1 - r4;	 Catch:{ a -> 0x0035 }
        r8.a(r3, r1);	 Catch:{ a -> 0x0035 }
        r1 = 23;	 Catch:{ a -> 0x0035 }
        r2 = u;	 Catch:{ a -> 0x0035 }
        r8.a(r1, r2);	 Catch:{ a -> 0x0035 }
    L_0x0035:
        r1 = 0;
        r2 = g(r9);	 Catch:{ a -> 0x0058 }
        r3 = 31;	 Catch:{ a -> 0x0058 }
        r4 = r2.get(r1);	 Catch:{ a -> 0x0058 }
        r4 = (java.lang.Long) r4;	 Catch:{ a -> 0x0058 }
        r4 = r4.longValue();	 Catch:{ a -> 0x0058 }
        r8.a(r3, r4);	 Catch:{ a -> 0x0058 }
        r3 = 32;	 Catch:{ a -> 0x0058 }
        r2 = r2.get(r0);	 Catch:{ a -> 0x0058 }
        r2 = (java.lang.Long) r2;	 Catch:{ a -> 0x0058 }
        r4 = r2.longValue();	 Catch:{ a -> 0x0058 }
        r8.a(r3, r4);	 Catch:{ a -> 0x0058 }
    L_0x0058:
        r2 = 33;
        r3 = d();	 Catch:{ a -> 0x0065 }
        r3 = r3.longValue();	 Catch:{ a -> 0x0065 }
        r8.a(r2, r3);	 Catch:{ a -> 0x0065 }
    L_0x0065:
        r2 = 27;
        r3 = r8.c;	 Catch:{ a -> 0x0070 }
        r3 = a(r9, r3);	 Catch:{ a -> 0x0070 }
        r8.a(r2, r3);	 Catch:{ a -> 0x0070 }
    L_0x0070:
        r2 = 29;
        r3 = r8.c;	 Catch:{ a -> 0x007b }
        r3 = b(r9, r3);	 Catch:{ a -> 0x007b }
        r8.a(r2, r3);	 Catch:{ a -> 0x007b }
    L_0x007b:
        r2 = h(r9);	 Catch:{ a -> 0x008d }
        r3 = 5;	 Catch:{ a -> 0x008d }
        r1 = r2[r1];	 Catch:{ a -> 0x008d }
        r4 = (long) r1;	 Catch:{ a -> 0x008d }
        r8.a(r3, r4);	 Catch:{ a -> 0x008d }
        r1 = 6;	 Catch:{ a -> 0x008d }
        r0 = r2[r0];	 Catch:{ a -> 0x008d }
        r2 = (long) r0;	 Catch:{ a -> 0x008d }
        r8.a(r1, r2);	 Catch:{ a -> 0x008d }
    L_0x008d:
        r0 = i(r9);	 Catch:{ a -> 0x0097 }
        r1 = 12;	 Catch:{ a -> 0x0097 }
        r2 = (long) r0;	 Catch:{ a -> 0x0097 }
        r8.a(r1, r2);	 Catch:{ a -> 0x0097 }
    L_0x0097:
        r0 = j(r9);	 Catch:{ a -> 0x00a0 }
        r1 = 3;	 Catch:{ a -> 0x00a0 }
        r2 = (long) r0;	 Catch:{ a -> 0x00a0 }
        r8.a(r1, r2);	 Catch:{ a -> 0x00a0 }
    L_0x00a0:
        r0 = 34;
        r1 = e(r9);	 Catch:{ a -> 0x00a9 }
        r8.a(r0, r1);	 Catch:{ a -> 0x00a9 }
    L_0x00a9:
        r0 = 35;
        r9 = f(r9);	 Catch:{ IOException -> 0x00b6, IOException -> 0x00b6 }
        r1 = r9.longValue();	 Catch:{ IOException -> 0x00b6, IOException -> 0x00b6 }
        r8.a(r0, r1);	 Catch:{ IOException -> 0x00b6, IOException -> 0x00b6 }
    L_0x00b6:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.kd.b(android.content.Context):void");
    }

    protected void c(android.content.Context r7) {
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
        r0 = 2;
        r1 = a();	 Catch:{ a -> 0x0008 }
        r6.a(r0, r1);	 Catch:{ a -> 0x0008 }
    L_0x0008:
        r1 = 1;
        r2 = c();	 Catch:{ a -> 0x0010 }
        r6.a(r1, r2);	 Catch:{ a -> 0x0010 }
    L_0x0010:
        r2 = 25;
        r3 = b();	 Catch:{ a -> 0x001d }
        r3 = r3.longValue();	 Catch:{ a -> 0x001d }
        r6.a(r2, r3);	 Catch:{ a -> 0x001d }
    L_0x001d:
        r2 = r6.a;	 Catch:{ a -> 0x005a }
        r3 = r6.b;	 Catch:{ a -> 0x005a }
        r2 = a(r2, r3);	 Catch:{ a -> 0x005a }
        r3 = 14;	 Catch:{ a -> 0x005a }
        r4 = 0;	 Catch:{ a -> 0x005a }
        r4 = r2.get(r4);	 Catch:{ a -> 0x005a }
        r4 = (java.lang.Long) r4;	 Catch:{ a -> 0x005a }
        r4 = r4.longValue();	 Catch:{ a -> 0x005a }
        r6.a(r3, r4);	 Catch:{ a -> 0x005a }
        r3 = 15;	 Catch:{ a -> 0x005a }
        r1 = r2.get(r1);	 Catch:{ a -> 0x005a }
        r1 = (java.lang.Long) r1;	 Catch:{ a -> 0x005a }
        r4 = r1.longValue();	 Catch:{ a -> 0x005a }
        r6.a(r3, r4);	 Catch:{ a -> 0x005a }
        r1 = r2.size();	 Catch:{ a -> 0x005a }
        r3 = 3;	 Catch:{ a -> 0x005a }
        if (r1 < r3) goto L_0x005a;	 Catch:{ a -> 0x005a }
    L_0x004b:
        r1 = 16;	 Catch:{ a -> 0x005a }
        r0 = r2.get(r0);	 Catch:{ a -> 0x005a }
        r0 = (java.lang.Long) r0;	 Catch:{ a -> 0x005a }
        r2 = r0.longValue();	 Catch:{ a -> 0x005a }
        r6.a(r1, r2);	 Catch:{ a -> 0x005a }
    L_0x005a:
        r0 = 34;
        r1 = e(r7);	 Catch:{ a -> 0x0063 }
        r6.a(r0, r1);	 Catch:{ a -> 0x0063 }
    L_0x0063:
        r0 = 35;
        r7 = f(r7);	 Catch:{ IOException -> 0x0070, IOException -> 0x0070 }
        r1 = r7.longValue();	 Catch:{ IOException -> 0x0070, IOException -> 0x0070 }
        r6.a(r0, r1);	 Catch:{ IOException -> 0x0070, IOException -> 0x0070 }
    L_0x0070:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.kd.c(android.content.Context):void");
    }

    static String a() throws a {
        if (r != null) {
            return r;
        }
        throw new a();
    }

    static Long b() throws a {
        if (e != null) {
            try {
                return (Long) e.invoke(null, new Object[0]);
            } catch (Throwable e) {
                throw new a(e);
            } catch (Throwable e2) {
                throw new a(e2);
            }
        }
        throw new a();
    }

    static String d(Context context) throws a {
        if (j != null) {
            try {
                String str = (String) j.invoke(null, new Object[]{context});
                if (str != null) {
                    return str;
                }
                throw new a();
            } catch (Context context2) {
                throw new a(context2);
            } catch (Context context22) {
                throw new a(context22);
            }
        }
        throw new a();
    }

    static String c() throws a {
        if (g != null) {
            try {
                return (String) g.invoke(null, new Object[0]);
            } catch (Throwable e) {
                throw new a(e);
            } catch (Throwable e2) {
                throw new a(e2);
            }
        }
        throw new a();
    }

    static ArrayList<Long> a(MotionEvent motionEvent, DisplayMetrics displayMetrics) throws a {
        if (i == null || motionEvent == null) {
            throw new a();
        }
        try {
            return (ArrayList) i.invoke(null, new Object[]{motionEvent, displayMetrics});
        } catch (MotionEvent motionEvent2) {
            throw new a(motionEvent2);
        } catch (MotionEvent motionEvent22) {
            throw new a(motionEvent22);
        }
    }

    static String e(Context context) throws a {
        if (n != null) {
            try {
                return (String) n.invoke(null, new Object[]{context});
            } catch (Context context2) {
                throw new a(context2);
            } catch (Context context22) {
                throw new a(context22);
            }
        }
        throw new a();
    }

    static Long f(Context context) throws a {
        if (o != null) {
            try {
                return (Long) o.invoke(null, new Object[]{context});
            } catch (Context context2) {
                throw new a(context2);
            } catch (Context context22) {
                throw new a(context22);
            }
        }
        throw new a();
    }

    static String a(Context context, kh khVar) throws a {
        if (s != null) {
            return s;
        }
        if (h != null) {
            try {
                ByteBuffer byteBuffer = (ByteBuffer) h.invoke(null, new Object[]{context});
                if (byteBuffer != null) {
                    s = khVar.a(byteBuffer.array(), true);
                    return s;
                }
                throw new a();
            } catch (Context context2) {
                throw new a(context2);
            } catch (Context context22) {
                throw new a(context22);
            }
        }
        throw new a();
    }

    static ArrayList<Long> g(Context context) throws a {
        if (l != null) {
            try {
                ArrayList arrayList = (ArrayList) l.invoke(null, new Object[]{context});
                if (arrayList != null && arrayList.size() == 2) {
                    return arrayList;
                }
                throw new a();
            } catch (Context context2) {
                throw new a(context2);
            } catch (Context context22) {
                throw new a(context22);
            }
        }
        throw new a();
    }

    static String b(Context context, kh khVar) throws a {
        if (t != null) {
            return t;
        }
        if (k != null) {
            try {
                ByteBuffer byteBuffer = (ByteBuffer) k.invoke(null, new Object[]{context});
                if (byteBuffer != null) {
                    t = khVar.a(byteBuffer.array(), true);
                    return t;
                }
                throw new a();
            } catch (Context context2) {
                throw new a(context2);
            } catch (Context context22) {
                throw new a(context22);
            }
        }
        throw new a();
    }

    static Long d() throws a {
        if (f != null) {
            try {
                return (Long) f.invoke(null, new Object[0]);
            } catch (Throwable e) {
                throw new a(e);
            } catch (Throwable e2) {
                throw new a(e2);
            }
        }
        throw new a();
    }

    static int[] h(Context context) throws a {
        if (m != null) {
            try {
                return (int[]) m.invoke(null, new Object[]{context});
            } catch (Context context2) {
                throw new a(context2);
            } catch (Context context22) {
                throw new a(context22);
            }
        }
        throw new a();
    }

    static int i(Context context) throws a {
        if (p != null) {
            try {
                return ((Integer) p.invoke(null, new Object[]{context})).intValue();
            } catch (Context context2) {
                throw new a(context2);
            } catch (Context context22) {
                throw new a(context22);
            }
        }
        throw new a();
    }

    static int j(Context context) throws a {
        if (q != null) {
            try {
                return ((Integer) q.invoke(null, new Object[]{context})).intValue();
            } catch (Context context2) {
                throw new a(context2);
            } catch (Context context22) {
                throw new a(context22);
            }
        }
        throw new a();
    }

    private static String b(byte[] bArr, String str) throws a {
        try {
            return new String(v.a(bArr, str), "UTF-8");
        } catch (byte[] bArr2) {
            throw new a(bArr2);
        } catch (byte[] bArr22) {
            throw new a(bArr22);
        }
    }

    private static void k(Context context) throws a {
        File file;
        Throwable th;
        String name;
        try {
            byte[] a = v.a(kl.a());
            byte[] a2 = v.a(a, kl.b());
            File cacheDir = context.getCacheDir();
            if (cacheDir == null) {
                cacheDir = context.getDir("dex", 0);
                if (cacheDir == null) {
                    throw new a();
                }
            } else {
                Context context2 = context;
            }
            File createTempFile = File.createTempFile("ads", ".jar", cacheDir);
            FileOutputStream fileOutputStream = new FileOutputStream(createTempFile);
            fileOutputStream.write(a2, 0, a2.length);
            fileOutputStream.close();
            try {
                DexClassLoader dexClassLoader = new DexClassLoader(createTempFile.getAbsolutePath(), cacheDir.getAbsolutePath(), null, context.getClassLoader());
                Class loadClass = dexClassLoader.loadClass(b(a, kl.k()));
                Class loadClass2 = dexClassLoader.loadClass(b(a, kl.y()));
                Class loadClass3 = dexClassLoader.loadClass(b(a, kl.s()));
                Class loadClass4 = dexClassLoader.loadClass(b(a, kl.o()));
                Class loadClass5 = dexClassLoader.loadClass(b(a, kl.A()));
                Class loadClass6 = dexClassLoader.loadClass(b(a, kl.m()));
                Class loadClass7 = dexClassLoader.loadClass(b(a, kl.w()));
                Class loadClass8 = dexClassLoader.loadClass(b(a, kl.u()));
                Class loadClass9 = dexClassLoader.loadClass(b(a, kl.i()));
                Class loadClass10 = dexClassLoader.loadClass(b(a, kl.g()));
                Class loadClass11 = dexClassLoader.loadClass(b(a, kl.e()));
                Class loadClass12 = dexClassLoader.loadClass(b(a, kl.q()));
                File file2 = cacheDir;
                try {
                    Class loadClass13 = dexClassLoader.loadClass(b(a, kl.c()));
                    file = createTempFile;
                    try {
                        e = loadClass.getMethod(b(a, kl.l()), new Class[0]);
                        f = loadClass2.getMethod(b(a, kl.z()), new Class[0]);
                        g = loadClass3.getMethod(b(a, kl.t()), new Class[0]);
                        h = loadClass4.getMethod(b(a, kl.p()), new Class[]{Context.class});
                        i = loadClass5.getMethod(b(a, kl.B()), new Class[]{MotionEvent.class, DisplayMetrics.class});
                        j = loadClass6.getMethod(b(a, kl.n()), new Class[]{Context.class});
                        k = loadClass7.getMethod(b(a, kl.x()), new Class[]{Context.class});
                        l = loadClass8.getMethod(b(a, kl.v()), new Class[]{Context.class});
                        m = loadClass9.getMethod(b(a, kl.j()), new Class[]{Context.class});
                        n = loadClass10.getMethod(b(a, kl.h()), new Class[]{Context.class});
                        o = loadClass11.getMethod(b(a, kl.f()), new Class[]{Context.class});
                        p = loadClass12.getMethod(b(a, kl.r()), new Class[]{Context.class});
                        q = loadClass13.getMethod(b(a, kl.d()), new Class[]{Context.class});
                        String name2 = file.getName();
                        file.delete();
                        new File(file2, name2.replace(".jar", ".dex")).delete();
                    } catch (Throwable th2) {
                        th = th2;
                        cacheDir = file2;
                        name = file.getName();
                        file.delete();
                        new File(cacheDir, name.replace(".jar", ".dex")).delete();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    file = createTempFile;
                    cacheDir = file2;
                    name = file.getName();
                    file.delete();
                    new File(cacheDir, name.replace(".jar", ".dex")).delete();
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                file = createTempFile;
                name = file.getName();
                file.delete();
                new File(cacheDir, name.replace(".jar", ".dex")).delete();
                throw th;
            }
        } catch (Throwable th5) {
            throw new a(th5);
        } catch (Throwable th52) {
            throw new a(th52);
        } catch (Throwable th522) {
            throw new a(th522);
        } catch (Throwable th5222) {
            throw new a(th5222);
        } catch (Throwable th52222) {
            throw new a(th52222);
        } catch (Throwable th522222) {
            throw new a(th522222);
        }
    }
}
