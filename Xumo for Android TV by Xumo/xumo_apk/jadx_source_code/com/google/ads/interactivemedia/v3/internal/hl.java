package com.google.ads.interactivemedia.v3.internal;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.Map.Entry;

/* compiled from: IMASDK */
public final class hl extends hx {
    private static final Reader b = new Reader() {
        public int read(char[] cArr, int i, int i2) throws IOException {
            throw new AssertionError();
        }

        public void close() throws IOException {
            throw new AssertionError();
        }
    };
    private static final Object c = new Object();
    private Object[] d = new Object[32];
    private int e = 0;
    private String[] f = new String[32];
    private int[] g = new int[32];

    public hl(gf gfVar) {
        super(b);
        a((Object) gfVar);
    }

    public void a() throws IOException {
        a(hy.BEGIN_ARRAY);
        a(((gc) s()).iterator());
        this.g[this.e - 1] = 0;
    }

    public void b() throws IOException {
        a(hy.END_ARRAY);
        t();
        t();
        if (this.e > 0) {
            int[] iArr = this.g;
            int i = this.e - 1;
            iArr[i] = iArr[i] + 1;
        }
    }

    public void c() throws IOException {
        a(hy.BEGIN_OBJECT);
        a(((gi) s()).o().iterator());
    }

    public void d() throws IOException {
        a(hy.END_OBJECT);
        t();
        t();
        if (this.e > 0) {
            int[] iArr = this.g;
            int i = this.e - 1;
            iArr[i] = iArr[i] + 1;
        }
    }

    public boolean e() throws IOException {
        hy f = f();
        return (f == hy.END_OBJECT || f == hy.END_ARRAY) ? false : true;
    }

    public hy f() throws IOException {
        if (this.e == 0) {
            return hy.END_DOCUMENT;
        }
        Object s = s();
        if (s instanceof Iterator) {
            boolean z = this.d[this.e - 2] instanceof gi;
            Iterator it = (Iterator) s;
            if (!it.hasNext()) {
                return z ? hy.END_OBJECT : hy.END_ARRAY;
            } else if (z) {
                return hy.NAME;
            } else {
                a(it.next());
                return f();
            }
        } else if (s instanceof gi) {
            return hy.BEGIN_OBJECT;
        } else {
            if (s instanceof gc) {
                return hy.BEGIN_ARRAY;
            }
            if (s instanceof gk) {
                gk gkVar = (gk) s;
                if (gkVar.q()) {
                    return hy.STRING;
                }
                if (gkVar.o()) {
                    return hy.BOOLEAN;
                }
                if (gkVar.p()) {
                    return hy.NUMBER;
                }
                throw new AssertionError();
            } else if (s instanceof gh) {
                return hy.NULL;
            } else {
                if (s == c) {
                    throw new IllegalStateException("JsonReader is closed");
                }
                throw new AssertionError();
            }
        }
    }

    private Object s() {
        return this.d[this.e - 1];
    }

    private Object t() {
        Object[] objArr = this.d;
        int i = this.e - 1;
        this.e = i;
        Object obj = objArr[i];
        this.d[this.e] = null;
        return obj;
    }

    private void a(hy hyVar) throws IOException {
        if (f() != hyVar) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Expected ");
            stringBuilder.append(hyVar);
            stringBuilder.append(" but was ");
            stringBuilder.append(f());
            stringBuilder.append(u());
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    public String g() throws IOException {
        a(hy.NAME);
        Entry entry = (Entry) ((Iterator) s()).next();
        String str = (String) entry.getKey();
        this.f[this.e - 1] = str;
        a(entry.getValue());
        return str;
    }

    public String h() throws IOException {
        hy f = f();
        if (f != hy.STRING) {
            if (f != hy.NUMBER) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Expected ");
                stringBuilder.append(hy.STRING);
                stringBuilder.append(" but was ");
                stringBuilder.append(f);
                stringBuilder.append(u());
                throw new IllegalStateException(stringBuilder.toString());
            }
        }
        String b = ((gk) t()).b();
        if (this.e > 0) {
            int[] iArr = this.g;
            int i = this.e - 1;
            iArr[i] = iArr[i] + 1;
        }
        return b;
    }

    public boolean i() throws IOException {
        a(hy.BOOLEAN);
        boolean f = ((gk) t()).f();
        if (this.e > 0) {
            int[] iArr = this.g;
            int i = this.e - 1;
            iArr[i] = iArr[i] + 1;
        }
        return f;
    }

    public void j() throws IOException {
        a(hy.NULL);
        t();
        if (this.e > 0) {
            int[] iArr = this.g;
            int i = this.e - 1;
            iArr[i] = iArr[i] + 1;
        }
    }

    public double k() throws IOException {
        hy f = f();
        if (f != hy.NUMBER) {
            if (f != hy.STRING) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Expected ");
                stringBuilder.append(hy.NUMBER);
                stringBuilder.append(" but was ");
                stringBuilder.append(f);
                stringBuilder.append(u());
                throw new IllegalStateException(stringBuilder.toString());
            }
        }
        double c = ((gk) s()).c();
        if (!q()) {
            if (Double.isNaN(c) || Double.isInfinite(c)) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("JSON forbids NaN and infinities: ");
                stringBuilder2.append(c);
                throw new NumberFormatException(stringBuilder2.toString());
            }
        }
        t();
        if (this.e > 0) {
            int[] iArr = this.g;
            int i = this.e - 1;
            iArr[i] = iArr[i] + 1;
        }
        return c;
    }

    public long l() throws IOException {
        hy f = f();
        if (f != hy.NUMBER) {
            if (f != hy.STRING) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Expected ");
                stringBuilder.append(hy.NUMBER);
                stringBuilder.append(" but was ");
                stringBuilder.append(f);
                stringBuilder.append(u());
                throw new IllegalStateException(stringBuilder.toString());
            }
        }
        long d = ((gk) s()).d();
        t();
        if (this.e > 0) {
            int[] iArr = this.g;
            int i = this.e - 1;
            iArr[i] = iArr[i] + 1;
        }
        return d;
    }

    public int m() throws IOException {
        hy f = f();
        if (f != hy.NUMBER) {
            if (f != hy.STRING) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Expected ");
                stringBuilder.append(hy.NUMBER);
                stringBuilder.append(" but was ");
                stringBuilder.append(f);
                stringBuilder.append(u());
                throw new IllegalStateException(stringBuilder.toString());
            }
        }
        int e = ((gk) s()).e();
        t();
        if (this.e > 0) {
            int[] iArr = this.g;
            int i = this.e - 1;
            iArr[i] = iArr[i] + 1;
        }
        return e;
    }

    public void close() throws IOException {
        this.d = new Object[]{c};
        this.e = 1;
    }

    public void n() throws IOException {
        if (f() == hy.NAME) {
            g();
            this.f[this.e - 2] = "null";
        } else {
            t();
            this.f[this.e - 1] = "null";
        }
        int[] iArr = this.g;
        int i = this.e - 1;
        iArr[i] = iArr[i] + 1;
    }

    public String toString() {
        return getClass().getSimpleName();
    }

    public void o() throws IOException {
        a(hy.NAME);
        Entry entry = (Entry) ((Iterator) s()).next();
        a(entry.getValue());
        a(new gk((String) entry.getKey()));
    }

    private void a(Object obj) {
        if (this.e == this.d.length) {
            Object obj2 = new Object[(this.e * 2)];
            Object obj3 = new int[(this.e * 2)];
            Object obj4 = new String[(this.e * 2)];
            System.arraycopy(this.d, 0, obj2, 0, this.e);
            System.arraycopy(this.g, 0, obj3, 0, this.e);
            System.arraycopy(this.f, 0, obj4, 0, this.e);
            this.d = obj2;
            this.g = obj3;
            this.f = obj4;
        }
        Object[] objArr = this.d;
        int i = this.e;
        this.e = i + 1;
        objArr[i] = obj;
    }

    public String p() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('$');
        int i = 0;
        while (i < this.e) {
            if (this.d[i] instanceof gc) {
                i++;
                if (this.d[i] instanceof Iterator) {
                    stringBuilder.append('[');
                    stringBuilder.append(this.g[i]);
                    stringBuilder.append(']');
                }
            } else if (this.d[i] instanceof gi) {
                i++;
                if (this.d[i] instanceof Iterator) {
                    stringBuilder.append('.');
                    if (this.f[i] != null) {
                        stringBuilder.append(this.f[i]);
                    }
                }
            }
            i++;
        }
        return stringBuilder.toString();
    }

    private String u() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" at path ");
        stringBuilder.append(p());
        return stringBuilder.toString();
    }
}
