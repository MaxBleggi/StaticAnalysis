package com.google.ads.interactivemedia.v3.internal;

import com.google.ads.interactivemedia.v3.internal.jc.a;

/* compiled from: IMASDK */
public class jf {
    private final long a;
    private final a b;

    jf(long j, a aVar) {
        this.a = j;
        this.b = aVar;
    }

    public long a() {
        return this.a;
    }

    public a b() {
        return this.b;
    }

    public int hashCode() {
        return (((int) this.a) * 31) + this.b.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        jf jfVar = (jf) obj;
        return this.a == jfVar.a && this.b == jfVar.b;
    }

    public String toString() {
        long j = this.a;
        String valueOf = String.valueOf(this.b);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 68);
        stringBuilder.append("NativeBridgeConfig [adTimeUpdateMs=");
        stringBuilder.append(j);
        stringBuilder.append(", adUiStyle=");
        stringBuilder.append(valueOf);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
