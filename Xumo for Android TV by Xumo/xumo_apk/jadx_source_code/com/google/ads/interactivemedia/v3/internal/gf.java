package com.google.ads.interactivemedia.v3.internal;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/* compiled from: IMASDK */
public abstract class gf {
    public boolean g() {
        return this instanceof gc;
    }

    public boolean h() {
        return this instanceof gi;
    }

    public boolean i() {
        return this instanceof gk;
    }

    public boolean j() {
        return this instanceof gh;
    }

    public gi k() {
        if (h()) {
            return (gi) this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Not a JSON Object: ");
        stringBuilder.append(this);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public gc l() {
        if (g()) {
            return (gc) this;
        }
        throw new IllegalStateException("This is not a JSON Array.");
    }

    public gk m() {
        if (i()) {
            return (gk) this;
        }
        throw new IllegalStateException("This is not a JSON Primitive.");
    }

    public boolean f() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    Boolean n() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public Number a() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public String b() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public double c() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public long d() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public int e() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public String toString() {
        try {
            Writer stringWriter = new StringWriter();
            hz hzVar = new hz(stringWriter);
            hzVar.b(true);
            hf.a(this, hzVar);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
