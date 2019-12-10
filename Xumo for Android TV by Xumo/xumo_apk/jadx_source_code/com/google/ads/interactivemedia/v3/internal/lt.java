package com.google.ads.interactivemedia.v3.internal;

import java.io.PrintStream;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: IMASDK */
public final class lt {
    static final a a;
    static final int b;

    /* compiled from: IMASDK */
    static abstract class a {
        protected static final Throwable[] a = new Throwable[0];

        a() {
        }

        public abstract void a(Throwable th);
    }

    /* compiled from: IMASDK */
    static final class b {
        private final ConcurrentHashMap<a, List<Throwable>> a = new ConcurrentHashMap(16, 0.75f, 10);
        private final ReferenceQueue<Throwable> b = new ReferenceQueue();

        /* compiled from: IMASDK */
        private static final class a extends WeakReference<Throwable> {
            private final int a;

            public a(Throwable th, ReferenceQueue<Throwable> referenceQueue) {
                super(th, referenceQueue);
                if (th != null) {
                    this.a = System.identityHashCode(th);
                    return;
                }
                throw new NullPointerException("The referent cannot be null");
            }

            public int hashCode() {
                return this.a;
            }

            public boolean equals(Object obj) {
                boolean z = false;
                if (obj != null) {
                    if (obj.getClass() == getClass()) {
                        if (this == obj) {
                            return true;
                        }
                        a aVar = (a) obj;
                        if (this.a == aVar.a && get() == aVar.get()) {
                            z = true;
                        }
                        return z;
                    }
                }
                return false;
            }
        }

        b() {
        }

        public List<Throwable> a(Throwable th, boolean z) {
            a();
            List<Throwable> list = (List) this.a.get(new a(th, null));
            if (!z || list != null) {
                return list;
            }
            z = new Vector(2);
            th = (List) this.a.putIfAbsent(new a(th, this.b), z);
            if (th == null) {
                th = z;
            }
            return th;
        }

        void a() {
            Object poll = this.b.poll();
            while (poll != null) {
                this.a.remove(poll);
                poll = this.b.poll();
            }
        }
    }

    /* compiled from: IMASDK */
    static final class c extends a {
        private final b b = new b();

        c() {
        }

        public void a(Throwable th) {
            th.printStackTrace();
            Throwable<Throwable> a = this.b.a(th, false);
            if (a != null) {
                synchronized (a) {
                    for (Throwable th2 : a) {
                        System.err.print("Suppressed: ");
                        th2.printStackTrace();
                    }
                }
            }
        }
    }

    /* compiled from: IMASDK */
    static final class d extends a {
        d() {
        }

        public void a(Throwable th) {
            th.printStackTrace();
        }
    }

    /* compiled from: IMASDK */
    static final class e extends a {
        e() {
        }

        public void a(Throwable th) {
            th.printStackTrace();
        }
    }

    public static void a(Throwable th) {
        a.a(th);
    }

    private static boolean a() {
        return Boolean.getBoolean("com.google.devtools.build.android.desugar.runtime.twr_disable_mimic") ^ 1;
    }

    private static Integer b() {
        try {
            return (Integer) Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
        } catch (Exception e) {
            System.err.println("Failed to retrieve value from android.os.Build$VERSION.SDK_INT due to the following exception.");
            e.printStackTrace(System.err);
            return null;
        }
    }

    static {
        Integer b;
        a eVar;
        int i;
        Throwable th;
        PrintStream printStream;
        String name;
        StringBuilder stringBuilder;
        try {
            b = b();
            if (b != null) {
                try {
                    if (b.intValue() >= 19) {
                        eVar = new e();
                        a = eVar;
                        if (b != null) {
                            i = 1;
                        } else {
                            i = b.intValue();
                        }
                        b = i;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    printStream = System.err;
                    name = d.class.getName();
                    stringBuilder = new StringBuilder(String.valueOf(name).length() + 132);
                    stringBuilder.append("An error has occured when initializing the try-with-resources desuguring strategy. The default strategy ");
                    stringBuilder.append(name);
                    stringBuilder.append("will be used. The error is: ");
                    printStream.println(stringBuilder.toString());
                    th.printStackTrace(System.err);
                    eVar = new d();
                    a = eVar;
                    if (b != null) {
                        i = 1;
                    } else {
                        i = b.intValue();
                    }
                    b = i;
                }
            }
            if (a()) {
                eVar = new c();
            } else {
                eVar = new d();
            }
        } catch (Throwable th3) {
            th = th3;
            b = null;
            printStream = System.err;
            name = d.class.getName();
            stringBuilder = new StringBuilder(String.valueOf(name).length() + 132);
            stringBuilder.append("An error has occured when initializing the try-with-resources desuguring strategy. The default strategy ");
            stringBuilder.append(name);
            stringBuilder.append("will be used. The error is: ");
            printStream.println(stringBuilder.toString());
            th.printStackTrace(System.err);
            eVar = new d();
            a = eVar;
            if (b != null) {
                i = b.intValue();
            } else {
                i = 1;
            }
            b = i;
        }
        a = eVar;
        if (b != null) {
            i = b.intValue();
        } else {
            i = 1;
        }
        b = i;
    }
}
