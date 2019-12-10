package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;

@Class(creator = "JoinOptionsCreator")
@Reserved({1})
public final class zzag extends AbstractSafeParcelable {
    public static final Creator<zzag> CREATOR = new zzah();
    @Field(getter = "getConnectionType", id = 2)
    private int zzcz;

    @Constructor
    zzag(@Param(id = 2) int i) {
        this.zzcz = i;
    }

    public zzag() {
        this(0);
    }

    public final String toString() {
        int i = this.zzcz;
        String str = i != 0 ? i != 2 ? "UNKNOWN" : "INVISIBLE" : "STRONG";
        return String.format("joinOptions(connectionType=%s)", new Object[]{str});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 2, this.zzcz);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzag)) {
            return false;
        }
        return this.zzcz == ((zzag) obj).zzcz;
    }

    public final int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.zzcz));
    }
}
