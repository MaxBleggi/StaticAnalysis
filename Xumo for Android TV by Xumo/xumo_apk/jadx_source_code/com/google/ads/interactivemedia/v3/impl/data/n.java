package com.google.ads.interactivemedia.v3.impl.data;

import com.google.ads.interactivemedia.v3.api.UiElement;
import com.google.ads.interactivemedia.v3.internal.gp;
import com.google.ads.interactivemedia.v3.internal.hx;
import com.google.ads.interactivemedia.v3.internal.hy;
import com.google.ads.interactivemedia.v3.internal.hz;
import com.google.ads.interactivemedia.v3.internal.ks;
import java.io.IOException;

/* compiled from: IMASDK */
public class n implements UiElement {
    public static final gp<n> GSON_TYPE_ADAPTER = new gp<n>() {
        public void write(hz hzVar, n nVar) throws IOException {
            if (nVar == null) {
                hzVar.f();
            } else {
                hzVar.b(nVar.getName());
            }
        }

        public n read(hx hxVar) throws IOException {
            if (hxVar.f() != hy.NULL) {
                return new n(hxVar.h());
            }
            hxVar.j();
            return null;
        }
    };
    private final String name;

    public n(String str) {
        this.name = str;
    }

    public String getName() {
        return this.name;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof n)) {
            return false;
        }
        return this.name.equals(((n) obj).name);
    }

    public int hashCode() {
        return ks.a(this.name);
    }

    public String toString() {
        String str = this.name;
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 20);
        stringBuilder.append("UiElementImpl[name=");
        stringBuilder.append(str);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
