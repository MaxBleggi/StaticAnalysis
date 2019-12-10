package com.google.firebase.iid;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class zzm implements Creator<zzl> {
    zzm() {
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzl[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        parcel = parcel.readStrongBinder();
        return parcel != null ? new zzl(parcel) : null;
    }
}
