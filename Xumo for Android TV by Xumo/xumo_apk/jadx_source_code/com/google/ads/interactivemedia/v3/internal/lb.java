package com.google.ads.interactivemedia.v3.internal;

import java.io.Serializable;
import java.util.Comparator;

/* compiled from: IMASDK */
final class lb<T> extends ll<T> implements Serializable {
    final Comparator<T> a;

    lb(Comparator<T> comparator) {
        this.a = (Comparator) kv.a(comparator);
    }

    public int compare(T t, T t2) {
        return this.a.compare(t, t2);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof lb)) {
            return null;
        }
        return this.a.equals(((lb) obj).a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    public String toString() {
        return this.a.toString();
    }
}
