package com.google.ads.interactivemedia.v3.internal;

import java.util.Iterator;
import java.util.Set;

/* compiled from: IMASDK */
public abstract class lg<E> extends ld<E> implements Set<E> {
    private transient le<E> a;

    static int a(int i) {
        i = Math.max(i, 2);
        boolean z = true;
        if (i < 751619276) {
            int highestOneBit = Integer.highestOneBit(i - 1) << 1;
            while (true) {
                double d = (double) highestOneBit;
                Double.isNaN(d);
                if (d * 0.7d >= ((double) i)) {
                    return highestOneBit;
                }
                highestOneBit <<= 1;
            }
        } else {
            if (i >= 1073741824) {
                z = false;
            }
            kv.a(z, (Object) "collection too large");
            return 1073741824;
        }
    }

    public abstract lr<E> a();

    boolean d() {
        return false;
    }

    lg() {
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if ((obj instanceof lg) && d() && ((lg) obj).d() && hashCode() != obj.hashCode()) {
            return null;
        }
        return lp.a(this, obj);
    }

    public int hashCode() {
        return lp.a(this);
    }

    public le<E> b() {
        le<E> leVar = this.a;
        if (leVar != null) {
            return leVar;
        }
        leVar = e();
        this.a = leVar;
        return leVar;
    }

    le<E> e() {
        return le.a(toArray());
    }

    public /* synthetic */ Iterator iterator() {
        return a();
    }
}
