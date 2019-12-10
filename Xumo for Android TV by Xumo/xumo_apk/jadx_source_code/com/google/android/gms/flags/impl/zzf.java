package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import android.util.Log;
import com.google.android.gms.internal.flags.zze;

public final class zzf extends zza<Long> {
    public static Long zza(SharedPreferences sharedPreferences, String str, Long l) {
        try {
            return (Long) zze.zza(new zzg(sharedPreferences, str, l));
        } catch (SharedPreferences sharedPreferences2) {
            str = "FlagDataUtils";
            String str2 = "Flag value not available, returning default: ";
            sharedPreferences2 = String.valueOf(sharedPreferences2.getMessage());
            Log.w(str, sharedPreferences2.length() != 0 ? str2.concat(sharedPreferences2) : new String(str2));
            return l;
        }
    }
}
