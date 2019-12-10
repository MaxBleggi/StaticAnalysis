package com.google.android.gms.cast.framework;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.cast.LaunchOptions;
import com.google.android.gms.cast.MediaLoadOptions;
import com.google.android.gms.cast.framework.media.CastMediaOptions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Class(creator = "CastOptionsCreator")
@Reserved({1})
public class CastOptions extends AbstractSafeParcelable {
    public static final Creator<CastOptions> CREATOR = new zzb();
    @Field(getter = "getLaunchOptions", id = 5)
    private final LaunchOptions zzdc;
    @Field(getter = "getReceiverApplicationId", id = 2)
    private String zzhr;
    @Field(getter = "getSupportedNamespaces", id = 3)
    private final List<String> zzhs;
    @Field(getter = "getStopReceiverApplicationWhenEndingSession", id = 4)
    private final boolean zzht;
    @Field(getter = "getResumeSavedSession", id = 6)
    private final boolean zzhu;
    @Field(getter = "getCastMediaOptions", id = 7)
    private final CastMediaOptions zzhv;
    @Field(getter = "getEnableReconnectionService", id = 8)
    private final boolean zzhw;
    @Field(getter = "getVolumeDeltaBeforeIceCreamSandwich", id = 9)
    private final double zzhx;
    @Field(getter = "getEnableIpv6Support", id = 10)
    private final boolean zzhy;

    @VisibleForTesting
    public static final class Builder {
        private LaunchOptions zzdc = new LaunchOptions();
        private String zzhr;
        private List<String> zzhs = new ArrayList();
        private boolean zzht;
        private boolean zzhu = true;
        private CastMediaOptions zzhv = new com.google.android.gms.cast.framework.media.CastMediaOptions.Builder().build();
        private boolean zzhw = true;
        private double zzhx = 0.05000000074505806d;
        private boolean zzhz = false;

        public final Builder setReceiverApplicationId(String str) {
            this.zzhr = str;
            return this;
        }

        public final Builder setSupportedNamespaces(List<String> list) {
            this.zzhs = list;
            return this;
        }

        public final Builder setStopReceiverApplicationWhenEndingSession(boolean z) {
            this.zzht = z;
            return this;
        }

        public final Builder setLaunchOptions(LaunchOptions launchOptions) {
            this.zzdc = launchOptions;
            return this;
        }

        public final Builder setResumeSavedSession(boolean z) {
            this.zzhu = z;
            return this;
        }

        public final Builder setCastMediaOptions(CastMediaOptions castMediaOptions) {
            this.zzhv = castMediaOptions;
            return this;
        }

        public final Builder setEnableReconnectionService(boolean z) {
            this.zzhw = z;
            return this;
        }

        public final Builder setVolumeDeltaBeforeIceCreamSandwich(double d) throws IllegalArgumentException {
            if (d <= 0.0d || d > MediaLoadOptions.PLAYBACK_RATE_MIN) {
                throw new IllegalArgumentException("volumeDelta must be greater than 0 and less or equal to 0.5");
            }
            this.zzhx = d;
            return this;
        }

        public final CastOptions build() {
            return new CastOptions(this.zzhr, this.zzhs, this.zzht, this.zzdc, this.zzhu, this.zzhv, this.zzhw, this.zzhx, false);
        }
    }

    @Constructor
    CastOptions(@Param(id = 2) String str, @Param(id = 3) List<String> list, @Param(id = 4) boolean z, @Param(id = 5) LaunchOptions launchOptions, @Param(id = 6) boolean z2, @Param(id = 7) CastMediaOptions castMediaOptions, @Param(id = 8) boolean z3, @Param(id = 9) double d, @Param(id = 10) boolean z4) {
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        this.zzhr = str;
        if (list == null) {
            str = null;
        } else {
            str = list.size();
        }
        this.zzhs = new ArrayList(str);
        if (str > null) {
            this.zzhs.addAll(list);
        }
        this.zzht = z;
        if (launchOptions == null) {
            launchOptions = new LaunchOptions();
        }
        this.zzdc = launchOptions;
        this.zzhu = z2;
        this.zzhv = castMediaOptions;
        this.zzhw = z3;
        this.zzhx = d;
        this.zzhy = z4;
    }

    public String getReceiverApplicationId() {
        return this.zzhr;
    }

    public final void setReceiverApplicationId(String str) {
        this.zzhr = str;
    }

    public List<String> getSupportedNamespaces() {
        return Collections.unmodifiableList(this.zzhs);
    }

    public boolean getStopReceiverApplicationWhenEndingSession() {
        return this.zzht;
    }

    public LaunchOptions getLaunchOptions() {
        return this.zzdc;
    }

    public boolean getResumeSavedSession() {
        return this.zzhu;
    }

    public CastMediaOptions getCastMediaOptions() {
        return this.zzhv;
    }

    public boolean getEnableReconnectionService() {
        return this.zzhw;
    }

    public double getVolumeDeltaBeforeIceCreamSandwich() {
        return this.zzhx;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, getReceiverApplicationId(), false);
        SafeParcelWriter.writeStringList(parcel, 3, getSupportedNamespaces(), false);
        SafeParcelWriter.writeBoolean(parcel, 4, getStopReceiverApplicationWhenEndingSession());
        SafeParcelWriter.writeParcelable(parcel, 5, getLaunchOptions(), i, false);
        SafeParcelWriter.writeBoolean(parcel, 6, getResumeSavedSession());
        SafeParcelWriter.writeParcelable(parcel, 7, getCastMediaOptions(), i, false);
        SafeParcelWriter.writeBoolean(parcel, 8, getEnableReconnectionService());
        SafeParcelWriter.writeDouble(parcel, 9, getVolumeDeltaBeforeIceCreamSandwich());
        SafeParcelWriter.writeBoolean(parcel, 10, this.zzhy);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
