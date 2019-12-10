package com.google.ads.interactivemedia.v3.internal;

import java.io.Serializable;

/* compiled from: IMASDK */
final class ky<F, T> extends ll<F> implements Serializable {
    final kr<F, ? extends T> a;
    final ll<T> b;

    ky(kr<F, ? extends T> krVar, ll<T> llVar) {
        this.a = (kr) kv.a(krVar);
        this.b = (ll) kv.a(llVar);
    }

    public int compare(F f, F f2) {
        return this.b.compare(this.a.a(f), this.a.a(f2));
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ky)) {
            return false;
        }
        ky kyVar = (ky) obj;
        if (!this.a.equals(kyVar.a) || this.b.equals(kyVar.b) == null) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ks.a(this.a, this.b);
    }

    public String toString() {
        String valueOf = String.valueOf(this.b);
        String valueOf2 = String.valueOf(this.a);
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(valueOf).length() + 13) + String.valueOf(valueOf2).length());
        stringBuilder.append(valueOf);
        stringBuilder.append(".onResultOf(");
        stringBuilder.append(valueOf2);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
