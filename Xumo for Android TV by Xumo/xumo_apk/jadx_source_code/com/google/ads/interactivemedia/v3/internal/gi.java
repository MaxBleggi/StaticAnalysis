package com.google.ads.interactivemedia.v3.internal;

import java.util.Map.Entry;
import java.util.Set;

/* compiled from: IMASDK */
public final class gi extends gf {
    private final hc<String, gf> a = new hc();

    public void a(String str, gf gfVar) {
        if (gfVar == null) {
            gfVar = gh.a;
        }
        this.a.put(str, gfVar);
    }

    public Set<Entry<String, gf>> o() {
        return this.a.entrySet();
    }

    public boolean equals(Object obj) {
        if (obj != this) {
            if (!(obj instanceof gi) || ((gi) obj).a.equals(this.a) == null) {
                return null;
            }
        }
        return true;
    }

    public int hashCode() {
        return this.a.hashCode();
    }
}
