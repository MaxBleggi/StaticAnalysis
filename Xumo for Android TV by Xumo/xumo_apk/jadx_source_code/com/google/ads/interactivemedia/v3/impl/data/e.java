package com.google.ads.interactivemedia.v3.impl.data;

import com.google.ads.interactivemedia.v3.impl.data.a.a;

/* compiled from: IMASDK */
final class e extends a {
    private final int height;
    private final int left;
    private final int top;
    private final int width;

    e(int i, int i2, int i3, int i4) {
        this.left = i;
        this.top = i2;
        this.height = i3;
        this.width = i4;
    }

    public int left() {
        return this.left;
    }

    public int top() {
        return this.top;
    }

    public int height() {
        return this.height;
    }

    public int width() {
        return this.width;
    }

    public String toString() {
        int i = this.left;
        int i2 = this.top;
        int i3 = this.height;
        int i4 = this.width;
        StringBuilder stringBuilder = new StringBuilder(86);
        stringBuilder.append("BoundingRect{left=");
        stringBuilder.append(i);
        stringBuilder.append(", top=");
        stringBuilder.append(i2);
        stringBuilder.append(", height=");
        stringBuilder.append(i3);
        stringBuilder.append(", width=");
        stringBuilder.append(i4);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof a)) {
            return false;
        }
        a aVar = (a) obj;
        if (this.left != aVar.left() || this.top != aVar.top() || this.height != aVar.height() || this.width != aVar.width()) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((((((this.left ^ 1000003) * 1000003) ^ this.top) * 1000003) ^ this.height) * 1000003) ^ this.width;
    }
}
