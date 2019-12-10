package com.google.ads.interactivemedia.v3.internal;

/* compiled from: IMASDK */
final class mf {
    private final Object a;
    private final int b;

    mf(Object obj) {
        this.b = System.identityHashCode(obj);
        this.a = obj;
    }

    public int hashCode() {
        return this.b;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof mf)) {
            return false;
        }
        mf mfVar = (mf) obj;
        if (this.b != mfVar.b) {
            return false;
        }
        if (this.a == mfVar.a) {
            z = true;
        }
        return z;
    }
}
