package com.google.android.gms.cast.framework.media;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;

@Class(creator = "NotificationActionCreator")
@Reserved({1})
public class NotificationAction extends AbstractSafeParcelable {
    public static final Creator<NotificationAction> CREATOR = new zzo();
    @Field(getter = "getAction", id = 2)
    private final String zznk;
    @Field(getter = "getIconResId", id = 3)
    private final int zznl;
    @Field(getter = "getContentDescription", id = 4)
    private final String zznm;

    public static final class Builder {
        private String zznk;
        private int zznl;
        private String zznm;

        public final Builder setAction(@NonNull String str) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("action cannot be null or an empty string.");
            }
            this.zznk = str;
            return this;
        }

        public final Builder setIconResId(int i) {
            this.zznl = i;
            return this;
        }

        public final Builder setContentDescription(@NonNull String str) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("contentDescription cannot be null  or an empty string.");
            }
            this.zznm = str;
            return this;
        }

        public final NotificationAction build() {
            return new NotificationAction(this.zznk, this.zznl, this.zznm);
        }
    }

    @Constructor
    NotificationAction(@Param(id = 2) String str, @Param(id = 3) int i, @Param(id = 4) String str2) {
        this.zznk = str;
        this.zznl = i;
        this.zznm = str2;
    }

    public String getAction() {
        return this.zznk;
    }

    public int getIconResId() {
        return this.zznl;
    }

    public String getContentDescription() {
        return this.zznm;
    }

    public void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, getAction(), false);
        SafeParcelWriter.writeInt(parcel, 3, getIconResId());
        SafeParcelWriter.writeString(parcel, 4, getContentDescription(), false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
