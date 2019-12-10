package com.google.ads.interactivemedia.v3.internal;

import com.google.ads.interactivemedia.v3.internal.hp.a;
import java.io.IOException;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/* compiled from: IMASDK */
final class ht<T> extends gp<T> {
    private final fz a;
    private final gp<T> b;
    private final Type c;

    ht(fz fzVar, gp<T> gpVar, Type type) {
        this.a = fzVar;
        this.b = gpVar;
        this.c = type;
    }

    public T read(hx hxVar) throws IOException {
        return this.b.read(hxVar);
    }

    public void write(hz hzVar, T t) throws IOException {
        gp gpVar = this.b;
        Type a = a(this.c, t);
        if (a != this.c) {
            gpVar = this.a.a(hw.a(a));
            if (gpVar instanceof a) {
                if (!(this.b instanceof a)) {
                    gpVar = this.b;
                }
            }
        }
        gpVar.write(hzVar, t);
    }

    private Type a(Type type, Object obj) {
        if (obj != null) {
            return (type == Object.class || (type instanceof TypeVariable) || (type instanceof Class)) ? obj.getClass() : type;
        } else {
            return type;
        }
    }
}
