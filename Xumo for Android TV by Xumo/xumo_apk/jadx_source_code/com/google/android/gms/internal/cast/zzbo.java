package com.google.android.gms.internal.cast;

import com.google.android.gms.cast.Cast.MessageReceivedCallback;
import com.google.android.gms.cast.CastDevice;

final class zzbo implements MessageReceivedCallback {
    private final /* synthetic */ zzbn zzuu;

    zzbo(zzbn com_google_android_gms_internal_cast_zzbn) {
        this.zzuu = com_google_android_gms_internal_cast_zzbn;
    }

    public final void onMessageReceived(CastDevice castDevice, String str, String str2) {
        this.zzuu.zzut.zzn(str2);
    }
}
