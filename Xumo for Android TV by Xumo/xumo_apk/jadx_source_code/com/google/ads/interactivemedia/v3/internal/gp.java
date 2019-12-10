package com.google.ads.interactivemedia.v3.internal;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

/* compiled from: IMASDK */
public abstract class gp<T> {
    public abstract T read(hx hxVar) throws IOException;

    public abstract void write(hz hzVar, T t) throws IOException;

    public final void toJson(Writer writer, T t) throws IOException {
        write(new hz(writer), t);
    }

    public final gp<T> nullSafe() {
        return new gp<T>(this) {
            final /* synthetic */ gp a;

            {
                this.a = r1;
            }

            public void write(hz hzVar, T t) throws IOException {
                if (t == null) {
                    hzVar.f();
                } else {
                    this.a.write(hzVar, t);
                }
            }

            public T read(hx hxVar) throws IOException {
                if (hxVar.f() != hy.NULL) {
                    return this.a.read(hxVar);
                }
                hxVar.j();
                return null;
            }
        };
    }

    public final String toJson(T t) {
        Writer stringWriter = new StringWriter();
        try {
            toJson(stringWriter, t);
            return stringWriter.toString();
        } catch (T t2) {
            throw new AssertionError(t2);
        }
    }

    public final gf toJsonTree(T t) {
        try {
            hz hmVar = new hm();
            write(hmVar, t);
            return hmVar.a();
        } catch (Throwable e) {
            throw new gg(e);
        }
    }

    public final T fromJson(Reader reader) throws IOException {
        return read(new hx(reader));
    }

    public final T fromJson(String str) throws IOException {
        return fromJson(new StringReader(str));
    }

    public final T fromJsonTree(gf gfVar) {
        try {
            return read(new hl(gfVar));
        } catch (Throwable e) {
            throw new gg(e);
        }
    }
}
