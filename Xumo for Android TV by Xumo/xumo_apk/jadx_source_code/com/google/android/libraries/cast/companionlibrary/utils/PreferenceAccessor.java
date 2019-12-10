package com.google.android.libraries.cast.companionlibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.android.exoplayer2.text.Cue;

public class PreferenceAccessor {
    private final SharedPreferences mSharedPreference;

    public PreferenceAccessor(Context context) {
        this.mSharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void saveStringToPreference(String str, String str2) {
        if (str2 == null) {
            this.mSharedPreference.edit().remove(str).apply();
        } else {
            this.mSharedPreference.edit().putString(str, str2).apply();
        }
    }

    public void saveFloatToPreference(String str, Float f) {
        if (f == null) {
            this.mSharedPreference.edit().remove(str).apply();
        } else {
            this.mSharedPreference.edit().putFloat(str, f.floatValue()).apply();
        }
    }

    public void saveIntToPreference(String str, Integer num) {
        if (num == null) {
            this.mSharedPreference.edit().remove(str).apply();
        } else {
            this.mSharedPreference.edit().putInt(str, num.intValue()).apply();
        }
    }

    public void saveLongToPreference(String str, Long l) {
        if (l == null) {
            this.mSharedPreference.edit().remove(str).apply();
        } else {
            this.mSharedPreference.edit().putLong(str, l.longValue()).apply();
        }
    }

    public void saveBooleanToPreference(String str, Boolean bool) {
        if (bool == null) {
            this.mSharedPreference.edit().remove(str).apply();
        } else {
            this.mSharedPreference.edit().putBoolean(str, bool.booleanValue()).apply();
        }
    }

    public String getStringFromPreference(String str) {
        return getStringFromPreference(str, null);
    }

    public String getStringFromPreference(String str, String str2) {
        return this.mSharedPreference.getString(str, str2);
    }

    public float getFloatFromPreference(String str) {
        return this.mSharedPreference.getFloat(str, Cue.DIMEN_UNSET);
    }

    public int getIntFromPreference(String str) {
        return this.mSharedPreference.getInt(str, Integer.MIN_VALUE);
    }

    public int getIntFromPreference(String str, int i) {
        return this.mSharedPreference.getInt(str, i);
    }

    public long getLongFromPreference(String str, long j) {
        return this.mSharedPreference.getLong(str, j);
    }

    public boolean getBooleanFromPreference(String str, boolean z) {
        return this.mSharedPreference.getBoolean(str, z);
    }
}
