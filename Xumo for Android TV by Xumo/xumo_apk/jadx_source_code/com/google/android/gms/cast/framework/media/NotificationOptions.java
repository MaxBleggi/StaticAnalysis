package com.google.android.gms.cast.framework.media;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import androidx.annotation.NonNull;
import com.google.android.gms.cast.framework.R;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Class(creator = "NotificationOptionsCreator")
@Reserved({1})
public class NotificationOptions extends AbstractSafeParcelable {
    public static final Creator<NotificationOptions> CREATOR = new zzq();
    public static final long SKIP_STEP_TEN_SECONDS_IN_MS = 10000;
    public static final long SKIP_STEP_THIRTY_SECONDS_IN_MS = 30000;
    private static final List<String> zznp = Arrays.asList(new String[]{MediaIntentReceiver.ACTION_TOGGLE_PLAYBACK, MediaIntentReceiver.ACTION_STOP_CASTING});
    private static final int[] zznq = new int[]{0, 1};
    @Field(getter = "getSkipStepMs", id = 4)
    private final long zzlx;
    @Field(getter = "getActions", id = 2)
    private final List<String> zznr;
    @Field(getter = "getCompatActionIndices", id = 3)
    private final int[] zzns;
    @Field(getter = "getTargetActivityClassName", id = 5)
    private final String zznt;
    @Field(getter = "getSmallIconDrawableResId", id = 6)
    private final int zznu;
    @Field(getter = "getStopLiveStreamDrawableResId", id = 7)
    private final int zznv;
    @Field(getter = "getPauseDrawableResId", id = 8)
    private final int zznw;
    @Field(getter = "getPlayDrawableResId", id = 9)
    private final int zznx;
    @Field(getter = "getSkipNextDrawableResId", id = 10)
    private final int zzny;
    @Field(getter = "getSkipPrevDrawableResId", id = 11)
    private final int zznz;
    @Field(getter = "getForwardDrawableResId", id = 12)
    private final int zzoa;
    @Field(getter = "getForward10DrawableResId", id = 13)
    private final int zzob;
    @Field(getter = "getForward30DrawableResId", id = 14)
    private final int zzoc;
    @Field(getter = "getRewindDrawableResId", id = 15)
    private final int zzod;
    @Field(getter = "getRewind10DrawableResId", id = 16)
    private final int zzoe;
    @Field(getter = "getRewind30DrawableResId", id = 17)
    private final int zzof;
    @Field(getter = "getDisconnectDrawableResId", id = 18)
    private final int zzog;
    @Field(getter = "getImageSizeDimenResId", id = 19)
    private final int zzoh;
    @Field(getter = "getCastingToDeviceStringResId", id = 20)
    private final int zzoi;
    @Field(getter = "getStopLiveStreamTitleResId", id = 21)
    private final int zzoj;
    @Field(getter = "getPauseTitleResId", id = 22)
    private final int zzok;
    @Field(getter = "getPlayTitleResId", id = 23)
    private final int zzol;
    @Field(getter = "getSkipNextTitleResId", id = 24)
    private final int zzom;
    @Field(getter = "getSkipPrevTitleResId", id = 25)
    private final int zzon;
    @Field(getter = "getForwardTitleResId", id = 26)
    private final int zzoo;
    @Field(getter = "getForward10TitleResId", id = 27)
    private final int zzop;
    @Field(getter = "getForward30TitleResId", id = 28)
    private final int zzoq;
    @Field(getter = "getRewindTitleResId", id = 29)
    private final int zzor;
    @Field(getter = "getRewind10TitleResId", id = 30)
    private final int zzos;
    @Field(getter = "getRewind30TitleResId", id = 31)
    private final int zzot;
    @Field(getter = "getDisconnectTitleResId", id = 32)
    private final int zzou;
    @Field(getter = "getNotificationActionsProviderAsBinder", id = 33, type = "android.os.IBinder")
    private final zzd zzov;

    public static final class Builder {
        private long zzlx = NotificationOptions.SKIP_STEP_TEN_SECONDS_IN_MS;
        private List<String> zznr = NotificationOptions.zznp;
        private int[] zzns = NotificationOptions.zznq;
        private String zznt;
        private int zznu = R.drawable.cast_ic_notification_small_icon;
        private int zznv = R.drawable.cast_ic_notification_stop_live_stream;
        private int zznw = R.drawable.cast_ic_notification_pause;
        private int zznx = R.drawable.cast_ic_notification_play;
        private int zzny = R.drawable.cast_ic_notification_skip_next;
        private int zznz = R.drawable.cast_ic_notification_skip_prev;
        private int zzoa = R.drawable.cast_ic_notification_forward;
        private int zzob = R.drawable.cast_ic_notification_forward10;
        private int zzoc = R.drawable.cast_ic_notification_forward30;
        private int zzod = R.drawable.cast_ic_notification_rewind;
        private int zzoe = R.drawable.cast_ic_notification_rewind10;
        private int zzof = R.drawable.cast_ic_notification_rewind30;
        private int zzog = R.drawable.cast_ic_notification_disconnect;
        private NotificationActionsProvider zzow;

        public final Builder setActions(List<String> list, int[] iArr) {
            if (list == null) {
                if (iArr != null) {
                    throw new IllegalArgumentException("When setting actions to null, you must also set compatActionIndices to null.");
                }
            }
            if (list != null) {
                if (iArr == null) {
                    throw new IllegalArgumentException("When setting compatActionIndices to null, you must also set actions to null.");
                }
            }
            if (list == null || iArr == null) {
                this.zznr = NotificationOptions.zznp;
                this.zzns = NotificationOptions.zznq;
            } else {
                int size = list.size();
                if (iArr.length <= size) {
                    for (int i : iArr) {
                        if (i < 0 || i >= size) {
                            throw new IllegalArgumentException(String.format(Locale.ROOT, "Index %d in compatActionIndices out of range: [0, %d]", new Object[]{Integer.valueOf(i), Integer.valueOf(size - 1)}));
                        }
                    }
                    this.zznr = new ArrayList(list);
                    this.zzns = Arrays.copyOf(iArr, iArr.length);
                } else {
                    throw new IllegalArgumentException(String.format(Locale.ROOT, "Invalid number of compat actions: %d > %d.", new Object[]{Integer.valueOf(iArr.length), Integer.valueOf(size)}));
                }
            }
            return this;
        }

        public final Builder setNotificationActionsProvider(@NonNull NotificationActionsProvider notificationActionsProvider) {
            if (notificationActionsProvider != null) {
                this.zzow = notificationActionsProvider;
                return this;
            }
            throw new IllegalArgumentException("notificationActionsProvider cannot be null.");
        }

        public final Builder setSkipStepMs(long j) {
            Preconditions.checkArgument(j > 0, "skipStepMs must be positive.");
            this.zzlx = j;
            return this;
        }

        public final Builder setTargetActivityClassName(String str) {
            this.zznt = str;
            return this;
        }

        public final Builder setSmallIconDrawableResId(int i) {
            this.zznu = i;
            return this;
        }

        public final Builder setStopLiveStreamDrawableResId(int i) {
            this.zznv = i;
            return this;
        }

        public final Builder setPauseDrawableResId(int i) {
            this.zznw = i;
            return this;
        }

        public final Builder setPlayDrawableResId(int i) {
            this.zznx = i;
            return this;
        }

        public final Builder setDisconnectDrawableResId(int i) {
            this.zzog = i;
            return this;
        }

        public final Builder setSkipNextDrawableResId(int i) {
            this.zzny = i;
            return this;
        }

        public final Builder setSkipPrevDrawableResId(int i) {
            this.zznz = i;
            return this;
        }

        public final Builder setForwardDrawableResId(int i) {
            this.zzoa = i;
            return this;
        }

        public final Builder setForward10DrawableResId(int i) {
            this.zzob = i;
            return this;
        }

        public final Builder setForward30DrawableResId(int i) {
            this.zzoc = i;
            return this;
        }

        public final Builder setRewindDrawableResId(int i) {
            this.zzod = i;
            return this;
        }

        public final Builder setRewind10DrawableResId(int i) {
            this.zzoe = i;
            return this;
        }

        public final Builder setRewind30DrawableResId(int i) {
            this.zzof = i;
            return this;
        }

        public final NotificationOptions build() {
            IBinder iBinder;
            if (this.zzow == null) {
                iBinder = null;
            } else {
                iBinder = r0.zzow.zzbl().asBinder();
            }
            NotificationOptions notificationOptions = r2;
            NotificationOptions notificationOptions2 = new NotificationOptions(r0.zznr, r0.zzns, r0.zzlx, r0.zznt, r0.zznu, r0.zznv, r0.zznw, r0.zznx, r0.zzny, r0.zznz, r0.zzoa, r0.zzob, r0.zzoc, r0.zzod, r0.zzoe, r0.zzof, r0.zzog, R.dimen.cast_notification_image_size, R.string.cast_casting_to_device, R.string.cast_stop_live_stream, R.string.cast_pause, R.string.cast_play, R.string.cast_skip_next, R.string.cast_skip_prev, R.string.cast_forward, R.string.cast_forward_10, R.string.cast_forward_30, R.string.cast_rewind, R.string.cast_rewind_10, R.string.cast_rewind_30, R.string.cast_disconnect, iBinder);
            return notificationOptions;
        }
    }

    @Constructor
    public NotificationOptions(@Param(id = 2) List<String> list, @Param(id = 3) int[] iArr, @Param(id = 4) long j, @Param(id = 5) String str, @Param(id = 6) int i, @Param(id = 7) int i2, @Param(id = 8) int i3, @Param(id = 9) int i4, @Param(id = 10) int i5, @Param(id = 11) int i6, @Param(id = 12) int i7, @Param(id = 13) int i8, @Param(id = 14) int i9, @Param(id = 15) int i10, @Param(id = 16) int i11, @Param(id = 17) int i12, @Param(id = 18) int i13, @Param(id = 19) int i14, @Param(id = 20) int i15, @Param(id = 21) int i16, @Param(id = 22) int i17, @Param(id = 23) int i18, @Param(id = 24) int i19, @Param(id = 25) int i20, @Param(id = 26) int i21, @Param(id = 27) int i22, @Param(id = 28) int i23, @Param(id = 29) int i24, @Param(id = 30) int i25, @Param(id = 31) int i26, @Param(id = 32) int i27, @Param(id = 33) IBinder iBinder) {
        NotificationOptions notificationOptions = this;
        int[] iArr2 = iArr;
        IBinder iBinder2 = iBinder;
        zzd com_google_android_gms_cast_framework_media_zzd = null;
        if (list != null) {
            notificationOptions.zznr = new ArrayList(list);
        } else {
            notificationOptions.zznr = null;
        }
        if (iArr2 != null) {
            notificationOptions.zzns = Arrays.copyOf(iArr, iArr2.length);
        } else {
            notificationOptions.zzns = null;
        }
        notificationOptions.zzlx = j;
        notificationOptions.zznt = str;
        notificationOptions.zznu = i;
        notificationOptions.zznv = i2;
        notificationOptions.zznw = i3;
        notificationOptions.zznx = i4;
        notificationOptions.zzny = i5;
        notificationOptions.zznz = i6;
        notificationOptions.zzoa = i7;
        notificationOptions.zzob = i8;
        notificationOptions.zzoc = i9;
        notificationOptions.zzod = i10;
        notificationOptions.zzoe = i11;
        notificationOptions.zzof = i12;
        notificationOptions.zzog = i13;
        notificationOptions.zzoh = i14;
        notificationOptions.zzoi = i15;
        notificationOptions.zzoj = i16;
        notificationOptions.zzok = i17;
        notificationOptions.zzol = i18;
        notificationOptions.zzom = i19;
        notificationOptions.zzon = i20;
        notificationOptions.zzoo = i21;
        notificationOptions.zzop = i22;
        notificationOptions.zzoq = i23;
        notificationOptions.zzor = i24;
        notificationOptions.zzos = i25;
        notificationOptions.zzot = i26;
        notificationOptions.zzou = i27;
        if (iBinder2 != null) {
            IInterface queryLocalInterface = iBinder2.queryLocalInterface("com.google.android.gms.cast.framework.media.INotificationActionsProvider");
            if (queryLocalInterface instanceof zzd) {
                com_google_android_gms_cast_framework_media_zzd = (zzd) queryLocalInterface;
            } else {
                com_google_android_gms_cast_framework_media_zzd = new zze(iBinder2);
            }
        }
        notificationOptions.zzov = com_google_android_gms_cast_framework_media_zzd;
    }

    public List<String> getActions() {
        return this.zznr;
    }

    public int[] getCompatActionIndices() {
        return Arrays.copyOf(this.zzns, this.zzns.length);
    }

    public long getSkipStepMs() {
        return this.zzlx;
    }

    public String getTargetActivityClassName() {
        return this.zznt;
    }

    public int getSmallIconDrawableResId() {
        return this.zznu;
    }

    public int getStopLiveStreamDrawableResId() {
        return this.zznv;
    }

    public int getPauseDrawableResId() {
        return this.zznw;
    }

    public int getPlayDrawableResId() {
        return this.zznx;
    }

    public int getSkipNextDrawableResId() {
        return this.zzny;
    }

    public int getSkipPrevDrawableResId() {
        return this.zznz;
    }

    public int getForwardDrawableResId() {
        return this.zzoa;
    }

    public int getForward10DrawableResId() {
        return this.zzob;
    }

    public int getForward30DrawableResId() {
        return this.zzoc;
    }

    public int getRewindDrawableResId() {
        return this.zzod;
    }

    public int getRewind10DrawableResId() {
        return this.zzoe;
    }

    public int getRewind30DrawableResId() {
        return this.zzof;
    }

    public int getDisconnectDrawableResId() {
        return this.zzog;
    }

    public final int zzbm() {
        return this.zzoh;
    }

    public int getCastingToDeviceStringResId() {
        return this.zzoi;
    }

    public int getStopLiveStreamTitleResId() {
        return this.zzoj;
    }

    public final int zzbn() {
        return this.zzok;
    }

    public final int zzbo() {
        return this.zzol;
    }

    public final int zzbp() {
        return this.zzom;
    }

    public final int zzbq() {
        return this.zzon;
    }

    public final int zzbr() {
        return this.zzoo;
    }

    public final int zzbs() {
        return this.zzop;
    }

    public final int zzbt() {
        return this.zzoq;
    }

    public final int zzbu() {
        return this.zzor;
    }

    public final int zzbv() {
        return this.zzos;
    }

    public final int zzbw() {
        return this.zzot;
    }

    public final int zzbx() {
        return this.zzou;
    }

    public final zzd zzby() {
        return this.zzov;
    }

    public void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeStringList(parcel, 2, getActions(), false);
        SafeParcelWriter.writeIntArray(parcel, 3, getCompatActionIndices(), false);
        SafeParcelWriter.writeLong(parcel, 4, getSkipStepMs());
        SafeParcelWriter.writeString(parcel, 5, getTargetActivityClassName(), false);
        SafeParcelWriter.writeInt(parcel, 6, getSmallIconDrawableResId());
        SafeParcelWriter.writeInt(parcel, 7, getStopLiveStreamDrawableResId());
        SafeParcelWriter.writeInt(parcel, 8, getPauseDrawableResId());
        SafeParcelWriter.writeInt(parcel, 9, getPlayDrawableResId());
        SafeParcelWriter.writeInt(parcel, 10, getSkipNextDrawableResId());
        SafeParcelWriter.writeInt(parcel, 11, getSkipPrevDrawableResId());
        SafeParcelWriter.writeInt(parcel, 12, getForwardDrawableResId());
        SafeParcelWriter.writeInt(parcel, 13, getForward10DrawableResId());
        SafeParcelWriter.writeInt(parcel, 14, getForward30DrawableResId());
        SafeParcelWriter.writeInt(parcel, 15, getRewindDrawableResId());
        SafeParcelWriter.writeInt(parcel, 16, getRewind10DrawableResId());
        SafeParcelWriter.writeInt(parcel, 17, getRewind30DrawableResId());
        SafeParcelWriter.writeInt(parcel, 18, getDisconnectDrawableResId());
        SafeParcelWriter.writeInt(parcel, 19, this.zzoh);
        SafeParcelWriter.writeInt(parcel, 20, getCastingToDeviceStringResId());
        SafeParcelWriter.writeInt(parcel, 21, getStopLiveStreamTitleResId());
        SafeParcelWriter.writeInt(parcel, 22, this.zzok);
        SafeParcelWriter.writeInt(parcel, 23, this.zzol);
        SafeParcelWriter.writeInt(parcel, 24, this.zzom);
        SafeParcelWriter.writeInt(parcel, 25, this.zzon);
        SafeParcelWriter.writeInt(parcel, 26, this.zzoo);
        SafeParcelWriter.writeInt(parcel, 27, this.zzop);
        SafeParcelWriter.writeInt(parcel, 28, this.zzoq);
        SafeParcelWriter.writeInt(parcel, 29, this.zzor);
        SafeParcelWriter.writeInt(parcel, 30, this.zzos);
        SafeParcelWriter.writeInt(parcel, 31, this.zzot);
        SafeParcelWriter.writeInt(parcel, 32, this.zzou);
        SafeParcelWriter.writeIBinder(parcel, 33, this.zzov == null ? null : this.zzov.asBinder(), false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
