package com.google.android.gms.measurement.internal;

import com.google.android.gms.measurement.AppMeasurement.UserProperty;

public class zzcu {
    public static final String[] zzarh = new String[]{"firebase_last_notification", "first_open_time", "first_visit_time", "last_deep_link_referrer", "user_id", "first_open_after_install", "lifetime_user_engagement", "google_allow_ad_personalization_signals", "session_number", "session_id"};
    public static final String[] zzari = new String[]{UserProperty.FIREBASE_LAST_NOTIFICATION, "_fot", "_fvt", "_ldl", "_id", "_fi", "_lte", "_ap", "_sno", "_sid"};

    protected zzcu() {
    }

    public static String zzcq(String str) {
        return zzdu.zza(str, zzarh, zzari);
    }
}
