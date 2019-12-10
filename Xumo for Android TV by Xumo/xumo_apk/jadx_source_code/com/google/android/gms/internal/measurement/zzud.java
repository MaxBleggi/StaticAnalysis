package com.google.android.gms.internal.measurement;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.firebase.dynamiclinks.ShortDynamicLink.Warning;

@Class(creator = "WarningImplCreator")
public final class zzud extends AbstractSafeParcelable implements Warning {
    public static final Creator<zzud> CREATOR = new zzuf();
    @Field(getter = "getMessage", id = 2)
    @Reserved({1})
    private final String zzbul;

    @Constructor
    public zzud(@Param(id = 2) String str) {
        this.zzbul = str;
    }

    public final String getCode() {
        return null;
    }

    public final String getMessage() {
        return this.zzbul;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, getMessage(), false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
