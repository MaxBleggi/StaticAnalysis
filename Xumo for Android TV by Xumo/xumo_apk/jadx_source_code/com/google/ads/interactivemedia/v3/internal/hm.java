package com.google.ads.interactivemedia.v3.internal;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/* compiled from: IMASDK */
public final class hm extends hz {
    private static final Writer a = new Writer() {
        public void write(char[] cArr, int i, int i2) {
            throw new AssertionError();
        }

        public void flush() throws IOException {
            throw new AssertionError();
        }

        public void close() throws IOException {
            throw new AssertionError();
        }
    };
    private static final gk b = new gk("closed");
    private final List<gf> c = new ArrayList();
    private String d;
    private gf e = gh.a;

    public hm() {
        super(a);
    }

    public void flush() throws IOException {
    }

    public gf a() {
        if (this.c.isEmpty()) {
            return this.e;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected one JSON element but was ");
        stringBuilder.append(this.c);
        throw new IllegalStateException(stringBuilder.toString());
    }

    private gf j() {
        return (gf) this.c.get(this.c.size() - 1);
    }

    private void a(gf gfVar) {
        if (this.d != null) {
            if (!gfVar.j() || i()) {
                ((gi) j()).a(this.d, gfVar);
            }
            this.d = null;
        } else if (this.c.isEmpty()) {
            this.e = gfVar;
        } else {
            gf j = j();
            if (j instanceof gc) {
                ((gc) j).a(gfVar);
                return;
            }
            throw new IllegalStateException();
        }
    }

    public hz b() throws IOException {
        gf gcVar = new gc();
        a(gcVar);
        this.c.add(gcVar);
        return this;
    }

    public hz c() throws IOException {
        if (this.c.isEmpty() || this.d != null) {
            throw new IllegalStateException();
        } else if (j() instanceof gc) {
            this.c.remove(this.c.size() - 1);
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public hz d() throws IOException {
        gf giVar = new gi();
        a(giVar);
        this.c.add(giVar);
        return this;
    }

    public hz e() throws IOException {
        if (this.c.isEmpty() || this.d != null) {
            throw new IllegalStateException();
        } else if (j() instanceof gi) {
            this.c.remove(this.c.size() - 1);
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public hz a(String str) throws IOException {
        if (this.c.isEmpty() || this.d != null) {
            throw new IllegalStateException();
        } else if (j() instanceof gi) {
            this.d = str;
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public hz b(String str) throws IOException {
        if (str == null) {
            return f();
        }
        a(new gk(str));
        return this;
    }

    public hz f() throws IOException {
        a(gh.a);
        return this;
    }

    public hz a(boolean z) throws IOException {
        a(new gk(Boolean.valueOf(z)));
        return this;
    }

    public hz a(Boolean bool) throws IOException {
        if (bool == null) {
            return f();
        }
        a(new gk(bool));
        return this;
    }

    public hz a(long j) throws IOException {
        a(new gk(Long.valueOf(j)));
        return this;
    }

    public hz a(Number number) throws IOException {
        if (number == null) {
            return f();
        }
        if (!g()) {
            double doubleValue = number.doubleValue();
            if (Double.isNaN(doubleValue) || Double.isInfinite(doubleValue)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("JSON forbids NaN and infinities: ");
                stringBuilder.append(number);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }
        a(new gk(number));
        return this;
    }

    public void close() throws IOException {
        if (this.c.isEmpty()) {
            this.c.add(b);
            return;
        }
        throw new IOException("Incomplete document");
    }
}
