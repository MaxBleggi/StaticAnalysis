package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import android.util.Log;
import com.google.android.gms.internal.flags.zze;

public final class zzh extends zza<String> {
    public static String zza(SharedPreferences sharedPreferences, String str, String str2) {
        try {
            return (String) zze.zza(new zzi(sharedPreferences, str, str2));
        } catch (SharedPreferences sharedPreferences2) {
            str = "FlagDataUtils";
            String str3 = "Flag value not available, returning default: ";
            sharedPreferences2 = String.valueOf(sharedPreferences2.getMessage());
            Log.w(str, sharedPreferences2.length() != 0 ? str3.concat(sharedPreferences2) : new String(str3));
            return str2;
        }
    }
}
