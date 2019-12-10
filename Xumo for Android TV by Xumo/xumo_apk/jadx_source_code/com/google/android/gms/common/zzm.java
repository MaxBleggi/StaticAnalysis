package com.google.android.gms.common;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.common.util.AndroidUtilsLight;
import com.google.android.gms.common.util.Hex;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.util.concurrent.Callable;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@CheckReturnValue
class zzm {
    private static final zzm zzab = new zzm(true, null, null);
    private final Throwable cause;
    final boolean zzac;
    private final String zzad;

    zzm(boolean z, @Nullable String str, @Nullable Throwable th) {
        this.zzac = z;
        this.zzad = str;
        this.cause = th;
    }

    static zzm zze() {
        return zzab;
    }

    static zzm zza(Callable<String> callable) {
        return new zzo(callable, null);
    }

    static zzm zzb(@NonNull String str) {
        return new zzm(false, str, null);
    }

    static zzm zza(@NonNull String str, @NonNull Throwable th) {
        return new zzm(false, str, th);
    }

    @Nullable
    String getErrorMessage() {
        return this.zzad;
    }

    final void zzf() {
        if (!this.zzac && Log.isLoggable("GoogleCertificatesRslt", 3)) {
            if (this.cause != null) {
                Log.d("GoogleCertificatesRslt", getErrorMessage(), this.cause);
                return;
            }
            Log.d("GoogleCertificatesRslt", getErrorMessage());
        }
    }

    static String zza(String str, zze com_google_android_gms_common_zze, boolean z, boolean z2) {
        z2 = z2 ? "debug cert rejected" : "not whitelisted";
        return String.format("%s: pkg=%s, sha1=%s, atk=%s, ver=%s", new Object[]{z2, str, Hex.zza(AndroidUtilsLight.zzi(CommonUtils.SHA1_INSTANCE).digest(com_google_android_gms_common_zze.getBytes())), Boolean.valueOf(z), "12451009.false"});
    }
}
