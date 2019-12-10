package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import java.util.Iterator;

@Class(creator = "EventParamsCreator")
@Reserved({1})
public final class zzab extends AbstractSafeParcelable implements Iterable<String> {
    public static final Creator<zzab> CREATOR = new zzad();
    @Field(getter = "z", id = 2)
    private final Bundle zzaip;

    @Constructor
    zzab(@Param(id = 2) Bundle bundle) {
        this.zzaip = bundle;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBundle(parcel, 2, zziy(), false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }

    final Object get(String str) {
        return this.zzaip.get(str);
    }

    final Long getLong(String str) {
        return Long.valueOf(this.zzaip.getLong(str));
    }

    final Double zzbt(String str) {
        return Double.valueOf(this.zzaip.getDouble(str));
    }

    final String getString(String str) {
        return this.zzaip.getString(str);
    }

    public final int size() {
        return this.zzaip.size();
    }

    public final String toString() {
        return this.zzaip.toString();
    }

    public final Bundle zziy() {
        return new Bundle(this.zzaip);
    }

    public final Iterator<String> iterator() {
        return new zzac(this);
    }
}
