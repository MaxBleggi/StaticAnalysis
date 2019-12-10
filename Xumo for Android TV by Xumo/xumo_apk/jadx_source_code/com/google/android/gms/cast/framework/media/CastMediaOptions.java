package com.google.android.gms.cast.framework.media;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.internal.cast.zzdh;

@Class(creator = "CastMediaOptionsCreator")
@Reserved({1})
public class CastMediaOptions extends AbstractSafeParcelable {
    public static final Creator<CastMediaOptions> CREATOR = new zza();
    private static final zzdh zzbe = new zzdh("CastMediaOptions");
    @Field(getter = "getMediaIntentReceiverClassName", id = 2)
    private final String zzli;
    @Field(getter = "getExpandedControllerActivityClassName", id = 3)
    private final String zzlj;
    @Field(getter = "getImagePickerAsBinder", id = 4, type = "android.os.IBinder")
    private final zzb zzlk;
    @Field(getter = "getNotificationOptions", id = 5)
    private final NotificationOptions zzll;
    @Field(getter = "getDisableRemoteControlNotification", id = 6)
    private final boolean zzlm;

    public static final class Builder {
        private String zzli = MediaIntentReceiver.class.getName();
        private String zzlj;
        private NotificationOptions zzll = new com.google.android.gms.cast.framework.media.NotificationOptions.Builder().build();
        private ImagePicker zzln;

        public final Builder setMediaIntentReceiverClassName(String str) {
            this.zzli = str;
            return this;
        }

        public final Builder setExpandedControllerActivityClassName(String str) {
            this.zzlj = str;
            return this;
        }

        public final Builder setImagePicker(ImagePicker imagePicker) {
            this.zzln = imagePicker;
            return this;
        }

        public final Builder setNotificationOptions(NotificationOptions notificationOptions) {
            this.zzll = notificationOptions;
            return this;
        }

        public final CastMediaOptions build() {
            return new CastMediaOptions(this.zzli, this.zzlj, this.zzln == null ? null : this.zzln.zzay().asBinder(), this.zzll, false);
        }
    }

    @Constructor
    CastMediaOptions(@Param(id = 2) String str, @Param(id = 3) String str2, @Param(id = 4) IBinder iBinder, @Param(id = 5) NotificationOptions notificationOptions, @Param(id = 6) boolean z) {
        this.zzli = str;
        this.zzlj = str2;
        if (iBinder == null) {
            str = null;
        } else {
            str = iBinder.queryLocalInterface("com.google.android.gms.cast.framework.media.IImagePicker");
            if ((str instanceof zzb) != null) {
                str = (zzb) str;
            } else {
                str = new zzc(iBinder);
            }
        }
        this.zzlk = str;
        this.zzll = notificationOptions;
        this.zzlm = z;
    }

    public String getMediaIntentReceiverClassName() {
        return this.zzli;
    }

    public NotificationOptions getNotificationOptions() {
        return this.zzll;
    }

    public final boolean zzaw() {
        return this.zzlm;
    }

    public String getExpandedControllerActivityClassName() {
        return this.zzlj;
    }

    public ImagePicker getImagePicker() {
        if (this.zzlk != null) {
            try {
                return (ImagePicker) ObjectWrapper.unwrap(this.zzlk.zzax());
            } catch (Throwable e) {
                zzbe.zza(e, "Unable to call %s on %s.", "getWrappedClientObject", zzb.class.getSimpleName());
            }
        }
        return null;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, getMediaIntentReceiverClassName(), false);
        SafeParcelWriter.writeString(parcel, 3, getExpandedControllerActivityClassName(), false);
        SafeParcelWriter.writeIBinder(parcel, 4, this.zzlk == null ? null : this.zzlk.asBinder(), false);
        SafeParcelWriter.writeParcelable(parcel, 5, getNotificationOptions(), i, false);
        SafeParcelWriter.writeBoolean(parcel, 6, this.zzlm);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
