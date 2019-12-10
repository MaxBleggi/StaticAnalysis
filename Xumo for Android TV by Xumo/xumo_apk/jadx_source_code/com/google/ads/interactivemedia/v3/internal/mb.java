package com.google.ads.interactivemedia.v3.internal;

import java.util.HashSet;
import java.util.Set;

/* compiled from: IMASDK */
public class mb {
    private static final ThreadLocal<Set<mh<mf, mf>>> a = new ThreadLocal();
    private boolean b = true;
    private boolean c = false;
    private boolean d = false;
    private Class<?> e = null;
    private String[] f = null;

    static Set<mh<mf, mf>> a() {
        return (Set) a.get();
    }

    static mh<mf, mf> a(Object obj, Object obj2) {
        return mh.b(new mf(obj), new mf(obj2));
    }

    static boolean b(Object obj, Object obj2) {
        Set a = a();
        obj = a(obj, obj2);
        return (a == null || (a.contains(obj) == null && a.contains(mh.b(obj.a(), obj.b())) == null)) ? null : true;
    }

    private static void e(Object obj, Object obj2) {
        Set a = a();
        if (a == null) {
            a = new HashSet();
            a.set(a);
        }
        a.add(a(obj, obj2));
    }

    private static void f(Object obj, Object obj2) {
        Set a = a();
        if (a != null) {
            a.remove(a(obj, obj2));
            if (a.isEmpty() != null) {
                a.remove();
            }
        }
    }

    public mb a(boolean z) {
        this.c = z;
        return this;
    }

    public mb b(boolean z) {
        this.d = z;
        return this;
    }

    public mb a(Class<?> cls) {
        this.e = cls;
        return this;
    }

    public mb a(String... strArr) {
        this.f = strArr;
        return this;
    }

    public static boolean a(Object obj, Object obj2, String... strArr) {
        return a(obj, obj2, false, null, strArr);
    }

    public static boolean a(Object obj, Object obj2, boolean z, Class<?> cls, String... strArr) {
        return a(obj, obj2, z, cls, false, strArr);
    }

    public static boolean a(Object obj, Object obj2, boolean z, Class<?> cls, boolean z2, String... strArr) {
        if (obj == obj2) {
            return true;
        }
        if (obj != null) {
            if (obj2 != null) {
                return new mb().a(strArr).a((Class) cls).a(z).b(z2).c(obj, obj2).b();
            }
        }
        return null;
    }

    public com.google.ads.interactivemedia.v3.internal.mb c(java.lang.Object r5, java.lang.Object r6) {
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
        r4 = this;
        r0 = r4.b;
        if (r0 != 0) goto L_0x0005;
    L_0x0004:
        return r4;
    L_0x0005:
        if (r5 != r6) goto L_0x0008;
    L_0x0007:
        return r4;
    L_0x0008:
        r0 = 0;
        if (r5 == 0) goto L_0x0057;
    L_0x000b:
        if (r6 != 0) goto L_0x000e;
    L_0x000d:
        goto L_0x0057;
    L_0x000e:
        r1 = r5.getClass();
        r2 = r6.getClass();
        r3 = r1.isInstance(r6);
        if (r3 == 0) goto L_0x0023;
    L_0x001c:
        r3 = r2.isInstance(r5);
        if (r3 != 0) goto L_0x0031;
    L_0x0022:
        goto L_0x0030;
    L_0x0023:
        r3 = r2.isInstance(r5);
        if (r3 == 0) goto L_0x0054;
    L_0x0029:
        r3 = r1.isInstance(r6);
        if (r3 != 0) goto L_0x0030;
    L_0x002f:
        goto L_0x0031;
    L_0x0030:
        r1 = r2;
    L_0x0031:
        r2 = r1.isArray();	 Catch:{ IllegalArgumentException -> 0x0051 }
        if (r2 == 0) goto L_0x003b;	 Catch:{ IllegalArgumentException -> 0x0051 }
    L_0x0037:
        r4.d(r5, r6);	 Catch:{ IllegalArgumentException -> 0x0051 }
        goto L_0x0050;	 Catch:{ IllegalArgumentException -> 0x0051 }
    L_0x003b:
        r4.a(r5, r6, r1);	 Catch:{ IllegalArgumentException -> 0x0051 }
    L_0x003e:
        r2 = r1.getSuperclass();	 Catch:{ IllegalArgumentException -> 0x0051 }
        if (r2 == 0) goto L_0x0050;	 Catch:{ IllegalArgumentException -> 0x0051 }
    L_0x0044:
        r2 = r4.e;	 Catch:{ IllegalArgumentException -> 0x0051 }
        if (r1 == r2) goto L_0x0050;	 Catch:{ IllegalArgumentException -> 0x0051 }
    L_0x0048:
        r1 = r1.getSuperclass();	 Catch:{ IllegalArgumentException -> 0x0051 }
        r4.a(r5, r6, r1);	 Catch:{ IllegalArgumentException -> 0x0051 }
        goto L_0x003e;
    L_0x0050:
        return r4;
    L_0x0051:
        r4.b = r0;
        return r4;
    L_0x0054:
        r4.b = r0;
        return r4;
    L_0x0057:
        r4.b = r0;
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.mb.c(java.lang.Object, java.lang.Object):com.google.ads.interactivemedia.v3.internal.mb");
    }

    private void a(java.lang.Object r5, java.lang.Object r6, java.lang.Class<?> r7) {
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
        r4 = this;
        r0 = b(r5, r6);
        if (r0 == 0) goto L_0x0007;
    L_0x0006:
        return;
    L_0x0007:
        e(r5, r6);	 Catch:{ all -> 0x006f }
        r7 = r7.getDeclaredFields();	 Catch:{ all -> 0x006f }
        r0 = 1;	 Catch:{ all -> 0x006f }
        java.lang.reflect.AccessibleObject.setAccessible(r7, r0);	 Catch:{ all -> 0x006f }
        r0 = 0;	 Catch:{ all -> 0x006f }
    L_0x0013:
        r1 = r7.length;	 Catch:{ all -> 0x006f }
        if (r0 >= r1) goto L_0x006b;	 Catch:{ all -> 0x006f }
    L_0x0016:
        r1 = r4.b;	 Catch:{ all -> 0x006f }
        if (r1 == 0) goto L_0x006b;	 Catch:{ all -> 0x006f }
    L_0x001a:
        r1 = r7[r0];	 Catch:{ all -> 0x006f }
        r2 = r4.f;	 Catch:{ all -> 0x006f }
        r3 = r1.getName();	 Catch:{ all -> 0x006f }
        r2 = com.google.ads.interactivemedia.v3.internal.lx.b(r2, r3);	 Catch:{ all -> 0x006f }
        if (r2 != 0) goto L_0x0068;	 Catch:{ all -> 0x006f }
    L_0x0028:
        r2 = r1.getName();	 Catch:{ all -> 0x006f }
        r3 = "$";	 Catch:{ all -> 0x006f }
        r2 = r2.contains(r3);	 Catch:{ all -> 0x006f }
        if (r2 != 0) goto L_0x0068;	 Catch:{ all -> 0x006f }
    L_0x0034:
        r2 = r4.c;	 Catch:{ all -> 0x006f }
        if (r2 != 0) goto L_0x0042;	 Catch:{ all -> 0x006f }
    L_0x0038:
        r2 = r1.getModifiers();	 Catch:{ all -> 0x006f }
        r2 = java.lang.reflect.Modifier.isTransient(r2);	 Catch:{ all -> 0x006f }
        if (r2 != 0) goto L_0x0068;	 Catch:{ all -> 0x006f }
    L_0x0042:
        r2 = r1.getModifiers();	 Catch:{ all -> 0x006f }
        r2 = java.lang.reflect.Modifier.isStatic(r2);	 Catch:{ all -> 0x006f }
        if (r2 != 0) goto L_0x0068;	 Catch:{ all -> 0x006f }
    L_0x004c:
        r2 = com.google.ads.interactivemedia.v3.internal.mc.class;	 Catch:{ all -> 0x006f }
        r2 = r1.isAnnotationPresent(r2);	 Catch:{ all -> 0x006f }
        if (r2 != 0) goto L_0x0068;
    L_0x0054:
        r2 = r1.get(r5);	 Catch:{ IllegalAccessException -> 0x0060 }
        r1 = r1.get(r6);	 Catch:{ IllegalAccessException -> 0x0060 }
        r4.d(r2, r1);	 Catch:{ IllegalAccessException -> 0x0060 }
        goto L_0x0068;
    L_0x0060:
        r7 = new java.lang.InternalError;	 Catch:{ all -> 0x006f }
        r0 = "Unexpected IllegalAccessException";	 Catch:{ all -> 0x006f }
        r7.<init>(r0);	 Catch:{ all -> 0x006f }
        throw r7;	 Catch:{ all -> 0x006f }
    L_0x0068:
        r0 = r0 + 1;
        goto L_0x0013;
    L_0x006b:
        f(r5, r6);
        return;
    L_0x006f:
        r7 = move-exception;
        f(r5, r6);
        throw r7;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.mb.a(java.lang.Object, java.lang.Object, java.lang.Class):void");
    }

    public mb d(Object obj, Object obj2) {
        if (!this.b || obj == obj2) {
            return this;
        }
        if (obj != null) {
            if (obj2 != null) {
                Class cls = obj.getClass();
                if (cls.isArray()) {
                    g(obj, obj2);
                } else if (!this.d || ly.a(cls)) {
                    this.b = obj.equals(obj2);
                } else {
                    c(obj, obj2);
                }
                return this;
            }
        }
        c(null);
        return this;
    }

    private void g(Object obj, Object obj2) {
        if (obj.getClass() != obj2.getClass()) {
            c(null);
        } else if (obj instanceof long[]) {
            a((long[]) obj, (long[]) obj2);
        } else if (obj instanceof int[]) {
            a((int[]) obj, (int[]) obj2);
        } else if (obj instanceof short[]) {
            a((short[]) obj, (short[]) obj2);
        } else if (obj instanceof char[]) {
            a((char[]) obj, (char[]) obj2);
        } else if (obj instanceof byte[]) {
            a((byte[]) obj, (byte[]) obj2);
        } else if (obj instanceof double[]) {
            a((double[]) obj, (double[]) obj2);
        } else if (obj instanceof float[]) {
            a((float[]) obj, (float[]) obj2);
        } else if (obj instanceof boolean[]) {
            a((boolean[]) obj, (boolean[]) obj2);
        } else {
            a((Object[]) obj, (Object[]) obj2);
        }
    }

    public mb a(long j, long j2) {
        if (!this.b) {
            return this;
        }
        this.b = j == j2 ? 1 : null;
        return this;
    }

    public mb a(int i, int i2) {
        if (!this.b) {
            return this;
        }
        this.b = i == i2 ? 1 : 0;
        return this;
    }

    public mb a(short s, short s2) {
        if (!this.b) {
            return this;
        }
        this.b = s == s2 ? (short) 1 : (short) 0;
        return this;
    }

    public mb a(char c, char c2) {
        if (!this.b) {
            return this;
        }
        this.b = c == c2 ? '\u0001' : '\u0000';
        return this;
    }

    public mb a(byte b, byte b2) {
        if (!this.b) {
            return this;
        }
        this.b = b == b2 ? (byte) 1 : (byte) 0;
        return this;
    }

    public mb a(double d, double d2) {
        if (this.b) {
            return a(Double.doubleToLongBits(d), Double.doubleToLongBits(d2));
        }
        return this;
    }

    public mb a(float f, float f2) {
        if (this.b) {
            return a(Float.floatToIntBits(f), Float.floatToIntBits(f2));
        }
        return this;
    }

    public mb a(boolean z, boolean z2) {
        if (!this.b) {
            return this;
        }
        this.b = z == z2;
        return this;
    }

    public mb a(Object[] objArr, Object[] objArr2) {
        if (!this.b || objArr == objArr2) {
            return this;
        }
        int i = 0;
        if (objArr != null) {
            if (objArr2 != null) {
                if (objArr.length != objArr2.length) {
                    c(false);
                    return this;
                }
                while (i < objArr.length && this.b) {
                    d(objArr[i], objArr2[i]);
                    i++;
                }
                return this;
            }
        }
        c(false);
        return this;
    }

    public mb a(long[] jArr, long[] jArr2) {
        if (!this.b || jArr == jArr2) {
            return this;
        }
        int i = 0;
        if (jArr != null) {
            if (jArr2 != null) {
                if (jArr.length != jArr2.length) {
                    c(false);
                    return this;
                }
                while (i < jArr.length && this.b) {
                    a(jArr[i], jArr2[i]);
                    i++;
                }
                return this;
            }
        }
        c(false);
        return this;
    }

    public mb a(int[] iArr, int[] iArr2) {
        if (!this.b || iArr == iArr2) {
            return this;
        }
        int i = 0;
        if (iArr != null) {
            if (iArr2 != null) {
                if (iArr.length != iArr2.length) {
                    c(false);
                    return this;
                }
                while (i < iArr.length && this.b) {
                    a(iArr[i], iArr2[i]);
                    i++;
                }
                return this;
            }
        }
        c(false);
        return this;
    }

    public mb a(short[] sArr, short[] sArr2) {
        if (!this.b || sArr == sArr2) {
            return this;
        }
        int i = 0;
        if (sArr != null) {
            if (sArr2 != null) {
                if (sArr.length != sArr2.length) {
                    c(false);
                    return this;
                }
                while (i < sArr.length && this.b) {
                    a(sArr[i], sArr2[i]);
                    i++;
                }
                return this;
            }
        }
        c(false);
        return this;
    }

    public mb a(char[] cArr, char[] cArr2) {
        if (!this.b || cArr == cArr2) {
            return this;
        }
        int i = 0;
        if (cArr != null) {
            if (cArr2 != null) {
                if (cArr.length != cArr2.length) {
                    c(false);
                    return this;
                }
                while (i < cArr.length && this.b) {
                    a(cArr[i], cArr2[i]);
                    i++;
                }
                return this;
            }
        }
        c(false);
        return this;
    }

    public mb a(byte[] bArr, byte[] bArr2) {
        if (!this.b || bArr == bArr2) {
            return this;
        }
        int i = 0;
        if (bArr != null) {
            if (bArr2 != null) {
                if (bArr.length != bArr2.length) {
                    c(false);
                    return this;
                }
                while (i < bArr.length && this.b) {
                    a(bArr[i], bArr2[i]);
                    i++;
                }
                return this;
            }
        }
        c(false);
        return this;
    }

    public mb a(double[] dArr, double[] dArr2) {
        if (!this.b || dArr == dArr2) {
            return this;
        }
        int i = 0;
        if (dArr != null) {
            if (dArr2 != null) {
                if (dArr.length != dArr2.length) {
                    c(false);
                    return this;
                }
                while (i < dArr.length && this.b) {
                    a(dArr[i], dArr2[i]);
                    i++;
                }
                return this;
            }
        }
        c(false);
        return this;
    }

    public mb a(float[] fArr, float[] fArr2) {
        if (!this.b || fArr == fArr2) {
            return this;
        }
        int i = 0;
        if (fArr != null) {
            if (fArr2 != null) {
                if (fArr.length != fArr2.length) {
                    c(false);
                    return this;
                }
                while (i < fArr.length && this.b) {
                    a(fArr[i], fArr2[i]);
                    i++;
                }
                return this;
            }
        }
        c(false);
        return this;
    }

    public mb a(boolean[] zArr, boolean[] zArr2) {
        if (!this.b || zArr == zArr2) {
            return this;
        }
        int i = 0;
        if (zArr != null) {
            if (zArr2 != null) {
                if (zArr.length != zArr2.length) {
                    c(false);
                    return this;
                }
                while (i < zArr.length && this.b) {
                    a(zArr[i], zArr2[i]);
                    i++;
                }
                return this;
            }
        }
        c(false);
        return this;
    }

    public boolean b() {
        return this.b;
    }

    protected void c(boolean z) {
        this.b = z;
    }
}
