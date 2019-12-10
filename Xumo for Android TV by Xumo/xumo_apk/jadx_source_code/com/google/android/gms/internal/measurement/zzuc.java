package com.google.android.gms.internal.measurement;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import java.util.List;

@Class(creator = "ShortDynamicLinkImplCreator")
public final class zzuc extends AbstractSafeParcelable implements ShortDynamicLink {
    public static final Creator<zzuc> CREATOR = new zzue();
    @Field(getter = "getShortLink", id = 1)
    private final Uri zzbui;
    @Field(getter = "getPreviewLink", id = 2)
    private final Uri zzbuj;
    @Field(getter = "getWarnings", id = 3)
    private final List<zzud> zzbuk;

    @Constructor
    public zzuc(@Param(id = 1) Uri uri, @Param(id = 2) Uri uri2, @Param(id = 3) List<zzud> list) {
        this.zzbui = uri;
        this.zzbuj = uri2;
        this.zzbuk = list;
    }

    public final Uri getShortLink() {
        return this.zzbui;
    }

    public final Uri getPreviewLink() {
        return this.zzbuj;
    }

    public final List<zzud> getWarnings() {
        return this.zzbuk;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, getShortLink(), i, false);
        SafeParcelWriter.writeParcelable(parcel, 2, getPreviewLink(), i, false);
        SafeParcelWriter.writeTypedList(parcel, 3, getWarnings(), false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
