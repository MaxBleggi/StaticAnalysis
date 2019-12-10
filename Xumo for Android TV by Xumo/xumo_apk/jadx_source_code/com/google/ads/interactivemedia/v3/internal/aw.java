package com.google.ads.interactivemedia.v3.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.Arrays;

/* compiled from: IMASDK */
public final class aw implements Parcelable {
    public static final Creator<aw> e = new Creator<aw>() {
        public aw a(Parcel parcel) {
            return new aw(parcel);
        }

        public aw[] a(int i) {
            return new aw[0];
        }

        public /* synthetic */ Object[] newArray(int i) {
            return a(i);
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }
    };
    public final int a;
    public final int b;
    public final int c;
    public final byte[] d;
    private int f;

    public aw(int i, int i2, int i3, byte[] bArr) {
        this.a = i;
        this.b = i2;
        this.c = i3;
        this.d = bArr;
    }

    public int describeContents() {
        return 0;
    }

    aw(Parcel parcel) {
        this.a = parcel.readInt();
        this.b = parcel.readInt();
        this.c = parcel.readInt();
        this.d = (parcel.readInt() != 0 ? 1 : null) != null ? parcel.createByteArray() : null;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null) {
            if (getClass() == obj.getClass()) {
                aw awVar = (aw) obj;
                if (this.a == awVar.a && this.b == awVar.b && this.c == awVar.c) {
                    if (Arrays.equals(this.d, awVar.d) != null) {
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    public String toString() {
        int i = this.a;
        int i2 = this.b;
        int i3 = this.c;
        boolean z = this.d != null;
        StringBuilder stringBuilder = new StringBuilder(55);
        stringBuilder.append("ColorInfo(");
        stringBuilder.append(i);
        stringBuilder.append(", ");
        stringBuilder.append(i2);
        stringBuilder.append(", ");
        stringBuilder.append(i3);
        stringBuilder.append(", ");
        stringBuilder.append(z);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public int hashCode() {
        if (this.f == 0) {
            this.f = ((((((527 + this.a) * 31) + this.b) * 31) + this.c) * 31) + Arrays.hashCode(this.d);
        }
        return this.f;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.a);
        parcel.writeInt(this.b);
        parcel.writeInt(this.c);
        parcel.writeInt(this.d != 0 ? 1 : 0);
        if (this.d != 0) {
            parcel.writeByteArray(this.d);
        }
    }
}
