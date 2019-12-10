package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import android.util.Log;
import com.google.android.gms.internal.flags.zze;

public final class zzd extends zza<Integer> {
    public static Integer zza(SharedPreferences sharedPreferences, String str, Integer num) {
        try {
            return (Integer) zze.zza(new zze(sharedPreferences, str, num));
        } catch (SharedPreferences sharedPreferences2) {
            str = "FlagDataUtils";
            String str2 = "Flag value not available, returning default: ";
            sharedPreferences2 = String.valueOf(sharedPreferences2.getMessage());
            Log.w(str, sharedPreferences2.length() != 0 ? str2.concat(sharedPreferences2) : new String(str2));
            return num;
        }
    }
}
