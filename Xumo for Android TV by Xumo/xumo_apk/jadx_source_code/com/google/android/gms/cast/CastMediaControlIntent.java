package com.google.android.gms.cast;

import android.text.TextUtils;
import com.google.android.gms.internal.cast.zzcv;
import java.util.Collection;
import java.util.Locale;

public final class CastMediaControlIntent {
    public static final String ACTION_SYNC_STATUS = "com.google.android.gms.cast.ACTION_SYNC_STATUS";
    public static final String DEFAULT_MEDIA_RECEIVER_APPLICATION_ID = "CC1AD845";
    public static final int ERROR_CODE_REQUEST_FAILED = 1;
    public static final int ERROR_CODE_SESSION_START_FAILED = 2;
    public static final int ERROR_CODE_TEMPORARILY_DISCONNECTED = 3;
    public static final String EXTRA_CAST_APPLICATION_ID = "com.google.android.gms.cast.EXTRA_CAST_APPLICATION_ID";
    public static final String EXTRA_CAST_LANGUAGE_CODE = "com.google.android.gms.cast.EXTRA_CAST_LANGUAGE_CODE";
    public static final String EXTRA_CAST_RELAUNCH_APPLICATION = "com.google.android.gms.cast.EXTRA_CAST_RELAUNCH_APPLICATION";
    public static final String EXTRA_CAST_STOP_APPLICATION_WHEN_SESSION_ENDS = "com.google.android.gms.cast.EXTRA_CAST_STOP_APPLICATION_WHEN_SESSION_ENDS";
    public static final String EXTRA_CUSTOM_DATA = "com.google.android.gms.cast.EXTRA_CUSTOM_DATA";
    public static final String EXTRA_DEBUG_LOGGING_ENABLED = "com.google.android.gms.cast.EXTRA_DEBUG_LOGGING_ENABLED";
    public static final String EXTRA_ERROR_CODE = "com.google.android.gms.cast.EXTRA_ERROR_CODE";

    public static String categoryForRemotePlayback(String str) throws IllegalArgumentException {
        if (!TextUtils.isEmpty(str)) {
            return zza("com.google.android.gms.cast.CATEGORY_CAST_REMOTE_PLAYBACK", str, null, false, true);
        }
        throw new IllegalArgumentException("applicationId cannot be null or empty");
    }

    public static String categoryForRemotePlayback() {
        return zza("com.google.android.gms.cast.CATEGORY_CAST_REMOTE_PLAYBACK", null, null, false, true);
    }

    public static String categoryForCast(String str) throws IllegalArgumentException {
        if (str != null) {
            return zza("com.google.android.gms.cast.CATEGORY_CAST", str, null, false, true);
        }
        throw new IllegalArgumentException("applicationId cannot be null");
    }

    public static String categoryForCast(Collection<String> collection) throws IllegalArgumentException {
        if (collection != null) {
            return zza("com.google.android.gms.cast.CATEGORY_CAST", null, collection, false, true);
        }
        throw new IllegalArgumentException("namespaces cannot be null");
    }

    public static String categoryForCast(String str, Collection<String> collection) {
        if (str == null) {
            throw new IllegalArgumentException("applicationId cannot be null");
        } else if (collection != null) {
            return zza("com.google.android.gms.cast.CATEGORY_CAST", str, collection, false, true);
        } else {
            throw new IllegalArgumentException("namespaces cannot be null");
        }
    }

    private static String zza(String str, String str2, Collection<String> collection, boolean z, boolean z2) throws IllegalArgumentException {
        z = new StringBuilder(str);
        if (str2 != null) {
            str = str2.toUpperCase();
            if (str.matches("[A-F0-9]+")) {
                z.append("/");
                z.append(str);
            } else {
                collection = "Invalid application ID: ";
                str2 = String.valueOf(str2);
                throw new IllegalArgumentException(str2.length() ? collection.concat(str2) : new String(collection));
            }
        }
        if (collection != null) {
            if (collection.isEmpty() == null) {
                if (str2 == null) {
                    z.append("/");
                }
                z.append("/");
                str = true;
                for (String str3 : collection) {
                    zzcv.zzo(str3);
                    if (str != null) {
                        str = null;
                    } else {
                        z.append(",");
                    }
                    z.append(zzcv.zzq(str3));
                }
            } else {
                throw new IllegalArgumentException("Must specify at least one namespace");
            }
        }
        if (str2 == null && collection == null) {
            z.append("/");
        }
        if (collection == null) {
            z.append("/");
        }
        z.append("/");
        z.append("/");
        z.append("ALLOW_IPV6");
        return z.toString();
    }

    public static String languageTagForLocale(Locale locale) {
        return zzcv.zza(locale);
    }

    private CastMediaControlIntent() {
    }
}
