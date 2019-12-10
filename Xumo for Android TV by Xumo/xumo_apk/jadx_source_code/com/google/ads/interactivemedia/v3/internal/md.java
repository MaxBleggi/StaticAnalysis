package com.google.ads.interactivemedia.v3.internal;

import java.util.HashSet;
import java.util.Set;

/* compiled from: IMASDK */
public class md {
    private static final ThreadLocal<Set<mf>> a = new ThreadLocal();
    private final int b;
    private int c;

    static Set<mf> a() {
        return (Set) a.get();
    }

    static boolean a(Object obj) {
        Set a = a();
        return (a == null || a.contains(new mf(obj)) == null) ? null : true;
    }

    private static void a(java.lang.Object r5, java.lang.Class<?> r6, com.google.ads.interactivemedia.v3.internal.md r7, boolean r8, java.lang.String[] r9) {
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
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r0 = a(r5);
        if (r0 == 0) goto L_0x0007;
    L_0x0006:
        return;
    L_0x0007:
        c(r5);	 Catch:{ all -> 0x0063 }
        r6 = r6.getDeclaredFields();	 Catch:{ all -> 0x0063 }
        r0 = 1;	 Catch:{ all -> 0x0063 }
        java.lang.reflect.AccessibleObject.setAccessible(r6, r0);	 Catch:{ all -> 0x0063 }
        r0 = r6.length;	 Catch:{ all -> 0x0063 }
        r1 = 0;	 Catch:{ all -> 0x0063 }
    L_0x0014:
        if (r1 >= r0) goto L_0x005f;	 Catch:{ all -> 0x0063 }
    L_0x0016:
        r2 = r6[r1];	 Catch:{ all -> 0x0063 }
        r3 = r2.getName();	 Catch:{ all -> 0x0063 }
        r3 = com.google.ads.interactivemedia.v3.internal.lx.b(r9, r3);	 Catch:{ all -> 0x0063 }
        if (r3 != 0) goto L_0x005c;	 Catch:{ all -> 0x0063 }
    L_0x0022:
        r3 = r2.getName();	 Catch:{ all -> 0x0063 }
        r4 = "$";	 Catch:{ all -> 0x0063 }
        r3 = r3.contains(r4);	 Catch:{ all -> 0x0063 }
        if (r3 != 0) goto L_0x005c;	 Catch:{ all -> 0x0063 }
    L_0x002e:
        if (r8 != 0) goto L_0x003a;	 Catch:{ all -> 0x0063 }
    L_0x0030:
        r3 = r2.getModifiers();	 Catch:{ all -> 0x0063 }
        r3 = java.lang.reflect.Modifier.isTransient(r3);	 Catch:{ all -> 0x0063 }
        if (r3 != 0) goto L_0x005c;	 Catch:{ all -> 0x0063 }
    L_0x003a:
        r3 = r2.getModifiers();	 Catch:{ all -> 0x0063 }
        r3 = java.lang.reflect.Modifier.isStatic(r3);	 Catch:{ all -> 0x0063 }
        if (r3 != 0) goto L_0x005c;	 Catch:{ all -> 0x0063 }
    L_0x0044:
        r3 = com.google.ads.interactivemedia.v3.internal.me.class;	 Catch:{ all -> 0x0063 }
        r3 = r2.isAnnotationPresent(r3);	 Catch:{ all -> 0x0063 }
        if (r3 != 0) goto L_0x005c;
    L_0x004c:
        r2 = r2.get(r5);	 Catch:{ IllegalAccessException -> 0x0054 }
        r7.b(r2);	 Catch:{ IllegalAccessException -> 0x0054 }
        goto L_0x005c;
    L_0x0054:
        r6 = new java.lang.InternalError;	 Catch:{ all -> 0x0063 }
        r7 = "Unexpected IllegalAccessException";	 Catch:{ all -> 0x0063 }
        r6.<init>(r7);	 Catch:{ all -> 0x0063 }
        throw r6;	 Catch:{ all -> 0x0063 }
    L_0x005c:
        r1 = r1 + 1;
        goto L_0x0014;
    L_0x005f:
        d(r5);
        return;
    L_0x0063:
        r6 = move-exception;
        d(r5);
        throw r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.md.a(java.lang.Object, java.lang.Class, com.google.ads.interactivemedia.v3.internal.md, boolean, java.lang.String[]):void");
    }

    public static <T> int a(int i, int i2, T t, boolean z, Class<? super T> cls, String... strArr) {
        lz.a(t != null, "The object to build a hash code for must not be null", new Object[0]);
        md mdVar = new md(i, i2);
        i = t.getClass();
        a(t, i, mdVar, z, strArr);
        while (i.getSuperclass() != 0 && i != cls) {
            i = i.getSuperclass();
            a(t, i, mdVar, z, strArr);
        }
        return mdVar.b();
    }

    public static int a(Object obj, String... strArr) {
        return a(17, 37, obj, false, null, strArr);
    }

    private static void c(Object obj) {
        Set a = a();
        if (a == null) {
            a = new HashSet();
            a.set(a);
        }
        a.add(new mf(obj));
    }

    private static void d(Object obj) {
        Set a = a();
        if (a != null) {
            a.remove(new mf(obj));
            if (a.isEmpty() != null) {
                a.remove();
            }
        }
    }

    public md() {
        this.c = 0;
        this.b = 37;
        this.c = 17;
    }

    public md(int i, int i2) {
        this.c = 0;
        boolean z = true;
        lz.a(i % 2 != 0, "HashCodeBuilder requires an odd initial value", new Object[0]);
        if (i2 % 2 == 0) {
            z = false;
        }
        lz.a(z, "HashCodeBuilder requires an odd multiplier", new Object[0]);
        this.b = i2;
        this.c = i;
    }

    public md a(boolean z) {
        this.c = (this.c * this.b) + (z ^ 1);
        return this;
    }

    public md a(boolean[] zArr) {
        if (zArr == null) {
            this.c *= this.b;
        } else {
            for (boolean a : zArr) {
                a(a);
            }
        }
        return this;
    }

    public md a(byte b) {
        this.c = (this.c * this.b) + b;
        return this;
    }

    public md a(byte[] bArr) {
        if (bArr == null) {
            this.c *= this.b;
        } else {
            for (byte a : bArr) {
                a(a);
            }
        }
        return this;
    }

    public md a(char c) {
        this.c = (this.c * this.b) + c;
        return this;
    }

    public md a(char[] cArr) {
        if (cArr == null) {
            this.c *= this.b;
        } else {
            for (char a : cArr) {
                a(a);
            }
        }
        return this;
    }

    public md a(double d) {
        return a(Double.doubleToLongBits(d));
    }

    public md a(double[] dArr) {
        if (dArr == null) {
            this.c *= this.b;
        } else {
            for (double a : dArr) {
                a(a);
            }
        }
        return this;
    }

    public md a(float f) {
        this.c = (this.c * this.b) + Float.floatToIntBits(f);
        return this;
    }

    public md a(float[] fArr) {
        if (fArr == null) {
            this.c *= this.b;
        } else {
            for (float a : fArr) {
                a(a);
            }
        }
        return this;
    }

    public md a(int i) {
        this.c = (this.c * this.b) + i;
        return this;
    }

    public md a(int[] iArr) {
        if (iArr == null) {
            this.c *= this.b;
        } else {
            for (int a : iArr) {
                a(a);
            }
        }
        return this;
    }

    public md a(long j) {
        this.c = (this.c * this.b) + ((int) (j ^ (j >> 32)));
        return this;
    }

    public md a(long[] jArr) {
        if (jArr == null) {
            this.c *= this.b;
        } else {
            for (long a : jArr) {
                a(a);
            }
        }
        return this;
    }

    public md b(Object obj) {
        if (obj == null) {
            this.c *= this.b;
        } else if (obj.getClass().isArray()) {
            e(obj);
        } else {
            this.c = (this.c * this.b) + obj.hashCode();
        }
        return this;
    }

    private void e(Object obj) {
        if (obj instanceof long[]) {
            a((long[]) obj);
        } else if (obj instanceof int[]) {
            a((int[]) obj);
        } else if (obj instanceof short[]) {
            a((short[]) obj);
        } else if (obj instanceof char[]) {
            a((char[]) obj);
        } else if (obj instanceof byte[]) {
            a((byte[]) obj);
        } else if (obj instanceof double[]) {
            a((double[]) obj);
        } else if (obj instanceof float[]) {
            a((float[]) obj);
        } else if (obj instanceof boolean[]) {
            a((boolean[]) obj);
        } else {
            a((Object[]) obj);
        }
    }

    public md a(Object[] objArr) {
        if (objArr == null) {
            this.c *= this.b;
        } else {
            for (Object b : objArr) {
                b(b);
            }
        }
        return this;
    }

    public md a(short s) {
        this.c = (this.c * this.b) + s;
        return this;
    }

    public md a(short[] sArr) {
        if (sArr == null) {
            this.c *= this.b;
        } else {
            for (short a : sArr) {
                a(a);
            }
        }
        return this;
    }

    public int b() {
        return this.c;
    }

    public int hashCode() {
        return b();
    }
}
