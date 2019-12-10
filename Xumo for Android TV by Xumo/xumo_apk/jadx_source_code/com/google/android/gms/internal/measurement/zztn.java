package com.google.android.gms.internal.measurement;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;

@Class(creator = "DynamicLinkDataCreator")
public final class zztn extends AbstractSafeParcelable {
    public static final Creator<zztn> CREATOR = new zzto();
    @Field(getter = "getDynamicLink", id = 1)
    private String zzbua;
    @Field(getter = "getDeepLink", id = 2)
    private String zzbub;
    @Field(getter = "getMinVersion", id = 3)
    private int zzbuc;
    @Field(getter = "getClickTimestamp", id = 4)
    private long zzbud = 0;
    @Field(getter = "getExtensionBundle", id = 5)
    private Bundle zzbue = null;
    @Field(getter = "getRedirectUrl", id = 6)
    private Uri zzbuf;

    public final String zztz() {
        return this.zzbub;
    }

    public final int zzua() {
        return this.zzbuc;
    }

    public final long getClickTimestamp() {
        return this.zzbud;
    }

    public final void zzax(long j) {
        this.zzbud = j;
    }

    public final Bundle zzub() {
        return this.zzbue == null ? new Bundle() : this.zzbue;
    }

    public final Uri zzty() {
        return this.zzbuf;
    }

    @Constructor
    public zztn(@Param(id = 1) String str, @Param(id = 2) String str2, @Param(id = 3) int i, @Param(id = 4) long j, @Param(id = 5) Bundle bundle, @Param(id = 6) Uri uri) {
        this.zzbua = str;
        this.zzbub = str2;
        this.zzbuc = i;
        this.zzbud = j;
        this.zzbue = bundle;
        this.zzbuf = uri;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzbua, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzbub, false);
        SafeParcelWriter.writeInt(parcel, 3, this.zzbuc);
        SafeParcelWriter.writeLong(parcel, 4, this.zzbud);
        SafeParcelWriter.writeBundle(parcel, 5, zzub(), false);
        SafeParcelWriter.writeParcelable(parcel, 6, this.zzbuf, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
