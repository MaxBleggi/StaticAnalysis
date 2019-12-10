package com.google.firebase.analytics.connector.internal;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.android.gms.measurement.AppMeasurement.UserProperty;
import com.google.android.gms.measurement.internal.zzcs;
import com.google.android.gms.measurement.internal.zzdu;
import com.google.firebase.analytics.FirebaseAnalytics.Event;
import com.google.firebase.analytics.connector.AnalyticsConnector.ConditionalUserProperty;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class zzc {
    private static final Set<String> zzbth = new HashSet(Arrays.asList(new String[]{"_in", "_xa", "_xu", "_aq", "_aa", "_ai", "_ac", Event.CAMPAIGN_DETAILS, "_ug", "_iapx", "_exp_set", "_exp_clear", "_exp_activate", "_exp_timeout", "_exp_expire"}));
    private static final List<String> zzbti = Arrays.asList(new String[]{"_e", "_f", "_iap", "_s", "_au", "_ui", "_cd", Event.APP_OPEN});
    private static final List<String> zzbtj = Arrays.asList(new String[]{"auto", SettingsJsonConstants.APP_KEY, "am"});
    private static final List<String> zzbtk = Arrays.asList(new String[]{"_r", "_dbg"});
    private static final List<String> zzbtl = Arrays.asList((String[]) ArrayUtils.concat(UserProperty.zzarh, UserProperty.zzari));
    private static final List<String> zzbtm = Arrays.asList(new String[]{"^_ltv_[A-Z]{3}$", "^_cc[1-5]{1}$"});

    public static boolean zzfv(@NonNull String str) {
        return zzbtj.contains(str) == null ? true : null;
    }

    public static boolean zza(@NonNull String str, @Nullable Bundle bundle) {
        if (zzbti.contains(str) != null) {
            return false;
        }
        if (bundle != null) {
            for (String containsKey : zzbtk) {
                if (bundle.containsKey(containsKey)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean zzfw(@NonNull String str) {
        return zzbth.contains(str) == null ? true : null;
    }

    public static boolean zzz(@NonNull String str, @NonNull String str2) {
        if (!"_ce1".equals(str2)) {
            if (!"_ce2".equals(str2)) {
                if (UserProperty.FIREBASE_LAST_NOTIFICATION.equals(str2)) {
                    if (str.equals(AppMeasurement.FCM_ORIGIN) == null) {
                        if (str.equals(AppMeasurement.FIAM_ORIGIN) == null) {
                            return false;
                        }
                    }
                    return true;
                } else if (zzbtl.contains(str2) != null) {
                    return false;
                } else {
                    for (String matches : zzbtm) {
                        if (str2.matches(matches)) {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        if (str.equals(AppMeasurement.FCM_ORIGIN) == null) {
            if (str.equals("frc") == null) {
                return false;
            }
        }
        return true;
    }

    public static boolean zza(ConditionalUserProperty conditionalUserProperty) {
        if (conditionalUserProperty == null) {
            return false;
        }
        String str = conditionalUserProperty.origin;
        if (str != null) {
            if (!str.isEmpty()) {
                if ((conditionalUserProperty.value != null && zzdu.zze(conditionalUserProperty.value) == null) || !zzfv(str) || !zzz(str, conditionalUserProperty.name)) {
                    return false;
                }
                if (conditionalUserProperty.expiredEventName != null && (!zza(conditionalUserProperty.expiredEventName, conditionalUserProperty.expiredEventParams) || !zzb(str, conditionalUserProperty.expiredEventName, conditionalUserProperty.expiredEventParams))) {
                    return false;
                }
                if (conditionalUserProperty.triggeredEventName != null && (!zza(conditionalUserProperty.triggeredEventName, conditionalUserProperty.triggeredEventParams) || !zzb(str, conditionalUserProperty.triggeredEventName, conditionalUserProperty.triggeredEventParams))) {
                    return false;
                }
                if (conditionalUserProperty.timedOutEventName == null || (zza(conditionalUserProperty.timedOutEventName, conditionalUserProperty.timedOutEventParams) && zzb(str, conditionalUserProperty.timedOutEventName, conditionalUserProperty.timedOutEventParams) != null)) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public static boolean zzb(@NonNull String str, @NonNull String str2, @Nullable Bundle bundle) {
        if ("_cmp".equals(str2) == null) {
            return true;
        }
        if (zzfv(str) == null || bundle == null) {
            return false;
        }
        for (String containsKey : zzbtk) {
            if (bundle.containsKey(containsKey)) {
                return false;
            }
        }
        int hashCode = str.hashCode();
        if (hashCode != 101200) {
            if (hashCode != 101230) {
                if (hashCode == 3142703) {
                    if (str.equals(AppMeasurement.FIAM_ORIGIN) != null) {
                        str = 2;
                        switch (str) {
                            case null:
                                bundle.putString("_cis", "fcm_integration");
                                return true;
                            case 1:
                                bundle.putString("_cis", "fdl_integration");
                                return true;
                            case 2:
                                bundle.putString("_cis", "fiam_integration");
                                return true;
                            default:
                                return false;
                        }
                    }
                }
            } else if (str.equals("fdl") != null) {
                str = true;
                switch (str) {
                    case null:
                        bundle.putString("_cis", "fcm_integration");
                        return true;
                    case 1:
                        bundle.putString("_cis", "fdl_integration");
                        return true;
                    case 2:
                        bundle.putString("_cis", "fiam_integration");
                        return true;
                    default:
                        return false;
                }
            }
        } else if (str.equals(AppMeasurement.FCM_ORIGIN) != null) {
            str = null;
            switch (str) {
                case null:
                    bundle.putString("_cis", "fcm_integration");
                    return true;
                case 1:
                    bundle.putString("_cis", "fdl_integration");
                    return true;
                case 2:
                    bundle.putString("_cis", "fiam_integration");
                    return true;
                default:
                    return false;
            }
        }
        str = -1;
        switch (str) {
            case null:
                bundle.putString("_cis", "fcm_integration");
                return true;
            case 1:
                bundle.putString("_cis", "fdl_integration");
                return true;
            case 2:
                bundle.putString("_cis", "fiam_integration");
                return true;
            default:
                return false;
        }
    }

    public static AppMeasurement.ConditionalUserProperty zzb(ConditionalUserProperty conditionalUserProperty) {
        AppMeasurement.ConditionalUserProperty conditionalUserProperty2 = new AppMeasurement.ConditionalUserProperty();
        conditionalUserProperty2.mOrigin = conditionalUserProperty.origin;
        conditionalUserProperty2.mActive = conditionalUserProperty.active;
        conditionalUserProperty2.mCreationTimestamp = conditionalUserProperty.creationTimestamp;
        conditionalUserProperty2.mExpiredEventName = conditionalUserProperty.expiredEventName;
        if (conditionalUserProperty.expiredEventParams != null) {
            conditionalUserProperty2.mExpiredEventParams = new Bundle(conditionalUserProperty.expiredEventParams);
        }
        conditionalUserProperty2.mName = conditionalUserProperty.name;
        conditionalUserProperty2.mTimedOutEventName = conditionalUserProperty.timedOutEventName;
        if (conditionalUserProperty.timedOutEventParams != null) {
            conditionalUserProperty2.mTimedOutEventParams = new Bundle(conditionalUserProperty.timedOutEventParams);
        }
        conditionalUserProperty2.mTimeToLive = conditionalUserProperty.timeToLive;
        conditionalUserProperty2.mTriggeredEventName = conditionalUserProperty.triggeredEventName;
        if (conditionalUserProperty.triggeredEventParams != null) {
            conditionalUserProperty2.mTriggeredEventParams = new Bundle(conditionalUserProperty.triggeredEventParams);
        }
        conditionalUserProperty2.mTriggeredTimestamp = conditionalUserProperty.triggeredTimestamp;
        conditionalUserProperty2.mTriggerEventName = conditionalUserProperty.triggerEventName;
        conditionalUserProperty2.mTriggerTimeout = conditionalUserProperty.triggerTimeout;
        if (conditionalUserProperty.value != null) {
            conditionalUserProperty2.mValue = zzdu.zze(conditionalUserProperty.value);
        }
        return conditionalUserProperty2;
    }

    public static ConditionalUserProperty zzd(AppMeasurement.ConditionalUserProperty conditionalUserProperty) {
        ConditionalUserProperty conditionalUserProperty2 = new ConditionalUserProperty();
        conditionalUserProperty2.origin = conditionalUserProperty.mOrigin;
        conditionalUserProperty2.active = conditionalUserProperty.mActive;
        conditionalUserProperty2.creationTimestamp = conditionalUserProperty.mCreationTimestamp;
        conditionalUserProperty2.expiredEventName = conditionalUserProperty.mExpiredEventName;
        if (conditionalUserProperty.mExpiredEventParams != null) {
            conditionalUserProperty2.expiredEventParams = new Bundle(conditionalUserProperty.mExpiredEventParams);
        }
        conditionalUserProperty2.name = conditionalUserProperty.mName;
        conditionalUserProperty2.timedOutEventName = conditionalUserProperty.mTimedOutEventName;
        if (conditionalUserProperty.mTimedOutEventParams != null) {
            conditionalUserProperty2.timedOutEventParams = new Bundle(conditionalUserProperty.mTimedOutEventParams);
        }
        conditionalUserProperty2.timeToLive = conditionalUserProperty.mTimeToLive;
        conditionalUserProperty2.triggeredEventName = conditionalUserProperty.mTriggeredEventName;
        if (conditionalUserProperty.mTriggeredEventParams != null) {
            conditionalUserProperty2.triggeredEventParams = new Bundle(conditionalUserProperty.mTriggeredEventParams);
        }
        conditionalUserProperty2.triggeredTimestamp = conditionalUserProperty.mTriggeredTimestamp;
        conditionalUserProperty2.triggerEventName = conditionalUserProperty.mTriggerEventName;
        conditionalUserProperty2.triggerTimeout = conditionalUserProperty.mTriggerTimeout;
        if (conditionalUserProperty.mValue != null) {
            conditionalUserProperty2.value = zzdu.zze(conditionalUserProperty.mValue);
        }
        return conditionalUserProperty2;
    }

    public static boolean zzfx(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        int codePointAt = str.codePointAt(0);
        if (!Character.isLetter(codePointAt)) {
            return false;
        }
        int length = str.length();
        codePointAt = Character.charCount(codePointAt);
        while (codePointAt < length) {
            int codePointAt2 = str.codePointAt(codePointAt);
            if (codePointAt2 != 95 && !Character.isLetterOrDigit(codePointAt2)) {
                return false;
            }
            codePointAt += Character.charCount(codePointAt2);
        }
        return true;
    }

    public static boolean zzfy(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        int codePointAt = str.codePointAt(0);
        if (!Character.isLetter(codePointAt) && codePointAt != 95) {
            return false;
        }
        int length = str.length();
        codePointAt = Character.charCount(codePointAt);
        while (codePointAt < length) {
            int codePointAt2 = str.codePointAt(codePointAt);
            if (codePointAt2 != 95 && !Character.isLetterOrDigit(codePointAt2)) {
                return false;
            }
            codePointAt += Character.charCount(codePointAt2);
        }
        return true;
    }

    public static String zzfz(String str) {
        String zzcp = zzcs.zzcp(str);
        return zzcp != null ? zzcp : str;
    }

    public static String zzga(String str) {
        String zzcq = zzcs.zzcq(str);
        return zzcq != null ? zzcq : str;
    }
}
