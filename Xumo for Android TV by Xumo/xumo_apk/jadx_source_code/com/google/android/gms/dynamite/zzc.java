package com.google.android.gms.dynamite;

import android.content.Context;
import com.google.android.gms.dynamite.DynamiteModule.LoadingException;
import com.google.android.gms.dynamite.DynamiteModule.VersionPolicy;
import com.google.android.gms.dynamite.DynamiteModule.VersionPolicy.zza;
import com.google.android.gms.dynamite.DynamiteModule.VersionPolicy.zzb;

final class zzc implements VersionPolicy {
    zzc() {
    }

    public final zzb zza(Context context, String str, zza com_google_android_gms_dynamite_DynamiteModule_VersionPolicy_zza) throws LoadingException {
        zzb com_google_android_gms_dynamite_DynamiteModule_VersionPolicy_zzb = new zzb();
        com_google_android_gms_dynamite_DynamiteModule_VersionPolicy_zzb.zziq = com_google_android_gms_dynamite_DynamiteModule_VersionPolicy_zza.getLocalVersion(context, str);
        if (com_google_android_gms_dynamite_DynamiteModule_VersionPolicy_zzb.zziq != 0) {
            com_google_android_gms_dynamite_DynamiteModule_VersionPolicy_zzb.zzis = -1;
        } else {
            com_google_android_gms_dynamite_DynamiteModule_VersionPolicy_zzb.zzir = com_google_android_gms_dynamite_DynamiteModule_VersionPolicy_zza.zza(context, str, true);
            if (com_google_android_gms_dynamite_DynamiteModule_VersionPolicy_zzb.zzir != null) {
                com_google_android_gms_dynamite_DynamiteModule_VersionPolicy_zzb.zzis = 1;
            }
        }
        return com_google_android_gms_dynamite_DynamiteModule_VersionPolicy_zzb;
    }
}
