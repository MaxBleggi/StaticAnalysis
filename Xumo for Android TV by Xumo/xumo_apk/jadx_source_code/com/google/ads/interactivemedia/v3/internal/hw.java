package com.google.ads.interactivemedia.v3.internal;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/* compiled from: IMASDK */
public class hw<T> {
    final Class<? super T> a;
    final Type b;
    final int c;

    protected hw() {
        this.b = a(getClass());
        this.a = gx.e(this.b);
        this.c = this.b.hashCode();
    }

    hw(Type type) {
        this.b = gx.d((Type) gw.a((Object) type));
        this.a = gx.e(this.b);
        this.c = this.b.hashCode();
    }

    static Type a(Class<?> cls) {
        cls = cls.getGenericSuperclass();
        if (!(cls instanceof Class)) {
            return gx.d(((ParameterizedType) cls).getActualTypeArguments()[0]);
        }
        throw new RuntimeException("Missing type parameter.");
    }

    public final Class<? super T> a() {
        return this.a;
    }

    public final Type b() {
        return this.b;
    }

    public final int hashCode() {
        return this.c;
    }

    public final boolean equals(Object obj) {
        return (!(obj instanceof hw) || gx.a(this.b, ((hw) obj).b) == null) ? null : true;
    }

    public final String toString() {
        return gx.f(this.b);
    }

    public static hw<?> a(Type type) {
        return new hw(type);
    }

    public static <T> hw<T> b(Class<T> cls) {
        return new hw(cls);
    }
}
