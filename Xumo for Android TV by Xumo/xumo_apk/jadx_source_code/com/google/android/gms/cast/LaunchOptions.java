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
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.cast.zzcv;
import java.util.Locale;

@Class(creator = "LaunchOptionsCreator")
@Reserved({1})
public class LaunchOptions extends AbstractSafeParcelable {
    public static final Creator<LaunchOptions> CREATOR = new zzai();
    @Field(getter = "getRelaunchIfRunning", id = 2)
    private boolean zzda;
    @Field(getter = "getLanguage", id = 3)
    private String zzdb;

    @VisibleForTesting
    public static final class Builder {
        private LaunchOptions zzdc = new LaunchOptions();

        public final Builder setRelaunchIfRunning(boolean z) {
            this.zzdc.setRelaunchIfRunning(z);
            return this;
        }

        public final Builder setLocale(Locale locale) {
            this.zzdc.setLanguage(zzcv.zza(locale));
            return this;
        }

        public final LaunchOptions build() {
            return this.zzdc;
        }
    }

    @Constructor
    LaunchOptions(@Param(id = 2) boolean z, @Param(id = 3) String str) {
        this.zzda = z;
        this.zzdb = str;
    }

    public LaunchOptions() {
        this(false, zzcv.zza(Locale.getDefault()));
    }

    public void setRelaunchIfRunning(boolean z) {
        this.zzda = z;
    }

    public boolean getRelaunchIfRunning() {
        return this.zzda;
    }

    public void setLanguage(String str) {
        this.zzdb = str;
    }

    public String getLanguage() {
        return this.zzdb;
    }

    public String toString() {
        return String.format("LaunchOptions(relaunchIfRunning=%b, language=%s)", new Object[]{Boolean.valueOf(this.zzda), this.zzdb});
    }

    public void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBoolean(parcel, 2, getRelaunchIfRunning());
        SafeParcelWriter.writeString(parcel, 3, getLanguage(), false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LaunchOptions)) {
            return false;
        }
        LaunchOptions launchOptions = (LaunchOptions) obj;
        return this.zzda == launchOptions.zzda && zzcv.zza(this.zzdb, launchOptions.zzdb) != null;
    }

    public int hashCode() {
        return Objects.hashCode(Boolean.valueOf(this.zzda), this.zzdb);
    }
}
