package com.google.android.gms.cast;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.cast.zzcv;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Class(creator = "CastDeviceCreator")
@Reserved({1})
public class CastDevice extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final int CAPABILITY_AUDIO_IN = 8;
    public static final int CAPABILITY_AUDIO_OUT = 4;
    public static final int CAPABILITY_MULTIZONE_GROUP = 32;
    public static final int CAPABILITY_VIDEO_IN = 2;
    public static final int CAPABILITY_VIDEO_OUT = 1;
    public static final Creator<CastDevice> CREATOR = new zzn();
    @Field(defaultValue = "-1", getter = "getStatus", id = 10)
    private int status;
    @Field(getter = "getDeviceIdRaw", id = 2)
    private String zzam;
    @Field(id = 3)
    private String zzan;
    private InetAddress zzao;
    @Field(getter = "getFriendlyName", id = 4)
    private String zzap;
    @Field(getter = "getModelName", id = 5)
    private String zzaq;
    @Field(getter = "getDeviceVersion", id = 6)
    private String zzar;
    @Field(getter = "getServicePort", id = 7)
    private int zzas;
    @Field(getter = "getIcons", id = 8)
    private List<WebImage> zzat;
    @Field(getter = "getCapabilities", id = 9)
    private int zzau;
    @Field(getter = "getServiceInstanceName", id = 11)
    private String zzav;
    @Field(getter = "getReceiverMetricsId", id = 12)
    private String zzaw;
    @Field(getter = "getRcnEnabledStatus", id = 13)
    private int zzax;
    @Field(getter = "getHotspotBssid", id = 14)
    private String zzay;
    @Field(getter = "getIpLowestTwoBytes", id = 15)
    private byte[] zzaz;
    @Field(getter = "getCloudDeviceId", id = 16)
    private String zzba;

    @Constructor
    CastDevice(@Param(id = 2) String str, @Param(id = 3) String str2, @Param(id = 4) String str3, @Param(id = 5) String str4, @Param(id = 6) String str5, @Param(id = 7) int i, @Param(id = 8) List<WebImage> list, @Param(id = 9) int i2, @Param(id = 10) int i3, @Param(id = 11) String str6, @Param(id = 12) String str7, @Param(id = 13) int i4, @Param(id = 14) String str8, @Param(id = 15) byte[] bArr, @Param(id = 16) String str9) {
        List list2;
        this.zzam = zza(str);
        this.zzan = zza(str2);
        if (!TextUtils.isEmpty(this.zzan)) {
            try {
                r1.zzao = InetAddress.getByName(r1.zzan);
            } catch (UnknownHostException e) {
                String str10 = r1.zzan;
                String message = e.getMessage();
                StringBuilder stringBuilder = new StringBuilder((String.valueOf(str10).length() + 48) + String.valueOf(message).length());
                stringBuilder.append("Unable to convert host address (");
                stringBuilder.append(str10);
                stringBuilder.append(") to ipaddress: ");
                stringBuilder.append(message);
                Log.i("CastDevice", stringBuilder.toString());
            }
        }
        r1.zzap = zza(str3);
        r1.zzaq = zza(str4);
        r1.zzar = zza(str5);
        r1.zzas = i;
        if (list != null) {
            list2 = list;
        } else {
            list2 = new ArrayList();
        }
        r1.zzat = list2;
        r1.zzau = i2;
        r1.status = i3;
        r1.zzav = zza(str6);
        r1.zzaw = str7;
        r1.zzax = i4;
        r1.zzay = str8;
        r1.zzaz = bArr;
        r1.zzba = str9;
    }

    private static String zza(String str) {
        return str == null ? "" : str;
    }

    public String getDeviceId() {
        if (this.zzam.startsWith("__cast_nearby__")) {
            return this.zzam.substring(16);
        }
        return this.zzam;
    }

    public boolean hasIPv4Address() {
        return getInetAddress() != null && (getInetAddress() instanceof Inet4Address);
    }

    public boolean hasIPv6Address() {
        return getInetAddress() != null && (getInetAddress() instanceof Inet6Address);
    }

    @Deprecated
    public Inet4Address getIpAddress() {
        return hasIPv4Address() ? (Inet4Address) this.zzao : null;
    }

    public InetAddress getInetAddress() {
        return this.zzao;
    }

    public String getFriendlyName() {
        return this.zzap;
    }

    public String getModelName() {
        return this.zzaq;
    }

    public String getDeviceVersion() {
        return this.zzar;
    }

    public int getServicePort() {
        return this.zzas;
    }

    public List<WebImage> getIcons() {
        return Collections.unmodifiableList(this.zzat);
    }

    public WebImage getIcon(int i, int i2) {
        WebImage webImage = null;
        if (this.zzat.isEmpty()) {
            return null;
        }
        if (i > 0) {
            if (i2 > 0) {
                WebImage webImage2 = null;
                for (WebImage webImage3 : this.zzat) {
                    int width = webImage3.getWidth();
                    int height = webImage3.getHeight();
                    if (width < i || height < i2) {
                        if (width < i && height < i2) {
                            if (webImage2 == null || (webImage2.getWidth() < width && webImage2.getHeight() < height)) {
                                webImage2 = webImage3;
                            }
                        }
                    } else if (webImage == null || (webImage.getWidth() > width && webImage.getHeight() > height)) {
                        webImage = webImage3;
                    }
                }
                if (webImage == null) {
                    if (webImage2 != null) {
                        webImage = webImage2;
                    } else {
                        webImage = (WebImage) this.zzat.get(0);
                    }
                }
                return webImage;
            }
        }
        return (WebImage) this.zzat.get(0);
    }

    @VisibleForTesting
    public boolean hasIcons() {
        return !this.zzat.isEmpty();
    }

    public boolean hasCapability(int i) {
        return (this.zzau & i) == i;
    }

    public boolean hasCapabilities(int[] iArr) {
        if (iArr == null) {
            return false;
        }
        for (int hasCapability : iArr) {
            if (!hasCapability(hasCapability)) {
                return false;
            }
        }
        return 1;
    }

    public String toString() {
        return String.format("\"%s\" (%s)", new Object[]{this.zzap, this.zzam});
    }

    public void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzam, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzan, false);
        SafeParcelWriter.writeString(parcel, 4, getFriendlyName(), false);
        SafeParcelWriter.writeString(parcel, 5, getModelName(), false);
        SafeParcelWriter.writeString(parcel, 6, getDeviceVersion(), false);
        SafeParcelWriter.writeInt(parcel, 7, getServicePort());
        SafeParcelWriter.writeTypedList(parcel, 8, getIcons(), false);
        SafeParcelWriter.writeInt(parcel, 9, this.zzau);
        SafeParcelWriter.writeInt(parcel, 10, this.status);
        SafeParcelWriter.writeString(parcel, 11, this.zzav, false);
        SafeParcelWriter.writeString(parcel, 12, this.zzaw, false);
        SafeParcelWriter.writeInt(parcel, 13, this.zzax);
        SafeParcelWriter.writeString(parcel, 14, this.zzay, false);
        SafeParcelWriter.writeByteArray(parcel, 15, this.zzaz, false);
        SafeParcelWriter.writeString(parcel, 16, this.zzba, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CastDevice)) {
            return false;
        }
        CastDevice castDevice = (CastDevice) obj;
        return this.zzam == null ? castDevice.zzam == null : zzcv.zza(this.zzam, castDevice.zzam) && zzcv.zza(this.zzao, castDevice.zzao) && zzcv.zza(this.zzaq, castDevice.zzaq) && zzcv.zza(this.zzap, castDevice.zzap) && zzcv.zza(this.zzar, castDevice.zzar) && this.zzas == castDevice.zzas && zzcv.zza(this.zzat, castDevice.zzat) && this.zzau == castDevice.zzau && this.status == castDevice.status && zzcv.zza(this.zzav, castDevice.zzav) && zzcv.zza(Integer.valueOf(this.zzax), Integer.valueOf(castDevice.zzax)) && zzcv.zza(this.zzay, castDevice.zzay) && zzcv.zza(this.zzaw, castDevice.zzaw) && zzcv.zza(this.zzar, castDevice.getDeviceVersion()) && this.zzas == castDevice.getServicePort() && (((this.zzaz == null && castDevice.zzaz == null) || Arrays.equals(this.zzaz, castDevice.zzaz)) && zzcv.zza(this.zzba, castDevice.zzba) != null);
    }

    @VisibleForTesting
    public boolean isSameDevice(CastDevice castDevice) {
        if (castDevice == null) {
            return false;
        }
        if (!TextUtils.isEmpty(getDeviceId()) && !getDeviceId().startsWith("__cast_ble__") && !TextUtils.isEmpty(castDevice.getDeviceId()) && !castDevice.getDeviceId().startsWith("__cast_ble__")) {
            return zzcv.zza(getDeviceId(), castDevice.getDeviceId());
        }
        if (TextUtils.isEmpty(this.zzay) || TextUtils.isEmpty(castDevice.zzay)) {
            return false;
        }
        return zzcv.zza(this.zzay, castDevice.zzay);
    }

    public int hashCode() {
        return this.zzam == null ? 0 : this.zzam.hashCode();
    }

    public void putInBundle(Bundle bundle) {
        if (bundle != null) {
            bundle.putParcelable("com.google.android.gms.cast.EXTRA_CAST_DEVICE", this);
        }
    }

    public static CastDevice getFromBundle(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        bundle.setClassLoader(CastDevice.class.getClassLoader());
        return (CastDevice) bundle.getParcelable("com.google.android.gms.cast.EXTRA_CAST_DEVICE");
    }

    public boolean isOnLocalNetwork() {
        return !this.zzam.startsWith("__cast_nearby__");
    }
}
