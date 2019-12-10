package com.google.ads.interactivemedia.v3.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: IMASDK */
public final class gz implements gq, Cloneable {
    public static final gz a = new gz();
    private double b = -1.0d;
    private int c = 136;
    private boolean d = true;
    private boolean e;
    private List<fv> f = Collections.emptyList();
    private List<fv> g = Collections.emptyList();

    protected gz a() {
        try {
            return (gz) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public gz a(fv fvVar, boolean z, boolean z2) {
        gz a = a();
        if (z) {
            a.f = new ArrayList(this.f);
            a.f.add(fvVar);
        }
        if (z2) {
            a.g = new ArrayList(this.g);
            a.g.add(fvVar);
        }
        return a;
    }

    public <T> gp<T> a(fz fzVar, hw<T> hwVar) {
        Class a = hwVar.a();
        final boolean a2 = a(a, true);
        final boolean a3 = a(a, false);
        if (!a2 && !a3) {
            return null;
        }
        final fz fzVar2 = fzVar;
        final hw<T> hwVar2 = hwVar;
        return new gp<T>(this) {
            final /* synthetic */ gz e;
            private gp<T> f;

            public T read(hx hxVar) throws IOException {
                if (!a3) {
                    return a().read(hxVar);
                }
                hxVar.n();
                return null;
            }

            public void write(hz hzVar, T t) throws IOException {
                if (a2) {
                    hzVar.f();
                } else {
                    a().write(hzVar, t);
                }
            }

            private gp<T> a() {
                gp<T> gpVar = this.f;
                if (gpVar != null) {
                    return gpVar;
                }
                gpVar = fzVar2.a(this.e, hwVar2);
                this.f = gpVar;
                return gpVar;
            }
        };
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(java.lang.reflect.Field r7, boolean r8) {
        /* JADX: method processing error */
/*
Error: java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:410)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.fixIterableType(LoopRegionVisitor.java:308)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.checkIterableForEach(LoopRegionVisitor.java:249)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.processLoopRegion(LoopRegionVisitor.java:68)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.enterRegion(LoopRegionVisitor.java:52)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:56)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:18)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.visit(LoopRegionVisitor.java:46)
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
        r6 = this;
        r0 = r6.c;
        r1 = r7.getModifiers();
        r0 = r0 & r1;
        r1 = 1;
        if (r0 == 0) goto L_0x000b;
    L_0x000a:
        return r1;
    L_0x000b:
        r2 = r6.b;
        r4 = -4616189618054758400; // 0xbff0000000000000 float:0.0 double:-1.0;
        r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r0 == 0) goto L_0x002a;
    L_0x0013:
        r0 = com.google.ads.interactivemedia.v3.internal.gu.class;
        r0 = r7.getAnnotation(r0);
        r0 = (com.google.ads.interactivemedia.v3.internal.gu) r0;
        r2 = com.google.ads.interactivemedia.v3.internal.gv.class;
        r2 = r7.getAnnotation(r2);
        r2 = (com.google.ads.interactivemedia.v3.internal.gv) r2;
        r0 = r6.a(r0, r2);
        if (r0 != 0) goto L_0x002a;
    L_0x0029:
        return r1;
    L_0x002a:
        r0 = r7.isSynthetic();
        if (r0 == 0) goto L_0x0031;
    L_0x0030:
        return r1;
    L_0x0031:
        r0 = r6.e;
        if (r0 == 0) goto L_0x004f;
    L_0x0035:
        r0 = com.google.ads.interactivemedia.v3.internal.gr.class;
        r0 = r7.getAnnotation(r0);
        r0 = (com.google.ads.interactivemedia.v3.internal.gr) r0;
        if (r0 == 0) goto L_0x004e;
    L_0x003f:
        if (r8 == 0) goto L_0x0048;
    L_0x0041:
        r0 = r0.a();
        if (r0 != 0) goto L_0x004f;
    L_0x0047:
        goto L_0x004e;
    L_0x0048:
        r0 = r0.b();
        if (r0 != 0) goto L_0x004f;
    L_0x004e:
        return r1;
    L_0x004f:
        r0 = r6.d;
        if (r0 != 0) goto L_0x005e;
    L_0x0053:
        r0 = r7.getType();
        r0 = r6.b(r0);
        if (r0 == 0) goto L_0x005e;
    L_0x005d:
        return r1;
    L_0x005e:
        r0 = r7.getType();
        r0 = r6.a(r0);
        if (r0 == 0) goto L_0x0069;
    L_0x0068:
        return r1;
    L_0x0069:
        if (r8 == 0) goto L_0x006e;
    L_0x006b:
        r8 = r6.f;
        goto L_0x0070;
    L_0x006e:
        r8 = r6.g;
    L_0x0070:
        r0 = r8.isEmpty();
        if (r0 != 0) goto L_0x0092;
    L_0x0076:
        r0 = new com.google.ads.interactivemedia.v3.internal.fw;
        r0.<init>(r7);
        r7 = r8.iterator();
    L_0x007f:
        r8 = r7.hasNext();
        if (r8 == 0) goto L_0x0092;
    L_0x0085:
        r8 = r7.next();
        r8 = (com.google.ads.interactivemedia.v3.internal.fv) r8;
        r8 = r8.a(r0);
        if (r8 == 0) goto L_0x007f;
    L_0x0091:
        return r1;
    L_0x0092:
        r7 = 0;
        return r7;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.gz.a(java.lang.reflect.Field, boolean):boolean");
    }

    public boolean a(Class<?> cls, boolean z) {
        if (this.b != -1.0d && !a((gu) cls.getAnnotation(gu.class), (gv) cls.getAnnotation(gv.class))) {
            return true;
        }
        if ((!this.d && b(cls)) || a((Class) cls)) {
            return true;
        }
        for (fv a : z ? this.f : this.g) {
            if (a.a((Class) cls)) {
                return true;
            }
        }
        return null;
    }

    private boolean a(Class<?> cls) {
        return (Enum.class.isAssignableFrom(cls) || (!cls.isAnonymousClass() && cls.isLocalClass() == null)) ? null : true;
    }

    private boolean b(Class<?> cls) {
        return (cls.isMemberClass() && c(cls) == null) ? true : null;
    }

    private boolean c(Class<?> cls) {
        return (cls.getModifiers() & 8) != null ? true : null;
    }

    private boolean a(gu guVar, gv gvVar) {
        return (a(guVar) == null || a(gvVar) == null) ? null : true;
    }

    private boolean a(gu guVar) {
        return (guVar == null || guVar.a() <= this.b) ? true : null;
    }

    private boolean a(gv gvVar) {
        return (gvVar == null || gvVar.a() > this.b) ? true : null;
    }

    protected /* synthetic */ Object clone() throws CloneNotSupportedException {
        return a();
    }
}
