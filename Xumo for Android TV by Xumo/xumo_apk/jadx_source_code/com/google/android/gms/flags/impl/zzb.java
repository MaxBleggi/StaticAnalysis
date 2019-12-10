package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import android.util.Log;
import com.google.android.gms.internal.flags.zze;

public final class zzb extends zza<Boolean> {
    public static Boolean zza(SharedPreferences sharedPreferences, String str, Boolean bool) {
        try {
            return (Boolean) zze.zza(new zzc(sharedPreferences, str, bool));
        } catch (SharedPreferences sharedPreferences2) {
            str = "FlagDataUtils";
            String str2 = "Flag value not available, returning default: ";
            sharedPreferences2 = String.valueOf(sharedPreferences2.getMessage());
            Log.w(str, sharedPreferences2.length() != 0 ? str2.concat(sharedPreferences2) : new String(str2));
            return bool;
        }
    }
}
