package com.google.ads.interactivemedia.v3.internal;

import java.io.Serializable;
import java.util.Map.Entry;
import java.util.Objects;

/* compiled from: IMASDK */
public abstract class mh<L, R> implements Serializable, Comparable<mh<L, R>>, Entry<L, R> {
    public abstract L a();

    public abstract R b();

    public static <L, R> mh<L, R> b(L l, R r) {
        return new mg(l, r);
    }

    public final L getKey() {
        return a();
    }

    public R getValue() {
        return b();
    }

    public int a(mh<L, R> mhVar) {
        return new ma().a(a(), mhVar.a()).a(b(), mhVar.b()).a();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Entry)) {
            return false;
        }
        Entry entry = (Entry) obj;
        if (!Objects.equals(getKey(), entry.getKey()) || Objects.equals(getValue(), entry.getValue()) == null) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = getKey() == null ? 0 : getKey().hashCode();
        if (getValue() != null) {
            i = getValue().hashCode();
        }
        return hashCode ^ i;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('(');
        stringBuilder.append(a());
        stringBuilder.append(',');
        stringBuilder.append(b());
        stringBuilder.append(')');
        return stringBuilder.toString();
    }

    public /* synthetic */ int compareTo(Object obj) {
        return a((mh) obj);
    }
}
