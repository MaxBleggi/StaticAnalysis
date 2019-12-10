package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.SimpleArrayMap;
import com.google.android.gms.base.R;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.util.DeviceProperties;
import javax.annotation.concurrent.GuardedBy;

public final class ConnectionErrorMessages {
    @GuardedBy("sCache")
    private static final SimpleArrayMap<String, String> zaof = new SimpleArrayMap();

    public static java.lang.String getAppName(android.content.Context r1) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.google.android.gms.common.internal.ConnectionErrorMessages.getAppName(android.content.Context):java.lang.String
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: java.lang.NullPointerException
	at jadx.core.dex.nodes.MethodNode.initTryCatches(MethodNode.java:305)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:105)
	... 5 more
*/
        /*
        r0 = r2.getPackageName();
        r1 = com.google.android.gms.common.wrappers.Wrappers.packageManager(r2);
        r1 = r1.getApplicationLabel(r0);
        r1 = r1.toString();
        return r1;
        r2 = r2.getApplicationInfo();
        r2 = r2.name;
        r1 = android.text.TextUtils.isEmpty(r2);
        if (r1 == 0) goto L_0x001f;
        return r0;
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.ConnectionErrorMessages.getAppName(android.content.Context):java.lang.String");
    }

    @Nullable
    public static String getErrorTitle(Context context, int i) {
        Resources resources = context.getResources();
        if (i != 20) {
            switch (i) {
                case 1:
                    return resources.getString(R.string.common_google_play_services_install_title);
                case 2:
                    return resources.getString(R.string.common_google_play_services_update_title);
                case 3:
                    return resources.getString(R.string.common_google_play_services_enable_title);
                case 4:
                case 6:
                    break;
                case 5:
                    Log.e("GoogleApiAvailability", "An invalid account was specified when connecting. Please provide a valid account.");
                    return zaa(context, "common_google_play_services_invalid_account_title");
                case 7:
                    Log.e("GoogleApiAvailability", "Network error occurred. Please retry request later.");
                    return zaa(context, "common_google_play_services_network_error_title");
                case 8:
                    Log.e("GoogleApiAvailability", "Internal error occurred. Please see logs for detailed information");
                    return null;
                case 9:
                    Log.e("GoogleApiAvailability", "Google Play services is invalid. Cannot recover.");
                    return null;
                case 10:
                    Log.e("GoogleApiAvailability", "Developer error occurred. Please see logs for detailed information");
                    return null;
                case 11:
                    Log.e("GoogleApiAvailability", "The application is not licensed to the user.");
                    return null;
                default:
                    switch (i) {
                        case 16:
                            Log.e("GoogleApiAvailability", "One of the API components you attempted to connect to is not available.");
                            return null;
                        case 17:
                            Log.e("GoogleApiAvailability", "The specified account could not be signed in.");
                            return zaa(context, "common_google_play_services_sign_in_failed_title");
                        case 18:
                            break;
                        default:
                            StringBuilder stringBuilder = new StringBuilder(33);
                            stringBuilder.append("Unexpected error code ");
                            stringBuilder.append(i);
                            Log.e("GoogleApiAvailability", stringBuilder.toString());
                            return null;
                    }
            }
            return null;
        }
        Log.e("GoogleApiAvailability", "The current user profile is restricted and could not use authenticated features.");
        return zaa(context, "common_google_play_services_restricted_profile_title");
    }

    @NonNull
    public static String getErrorNotificationTitle(Context context, int i) {
        if (i == 6) {
            i = zaa(context, "common_google_play_services_resolution_required_title");
        } else {
            i = getErrorTitle(context, i);
        }
        return i == 0 ? context.getResources().getString(R.string.common_google_play_services_notification_ticker) : i;
    }

    @NonNull
    public static String getErrorMessage(Context context, int i) {
        Resources resources = context.getResources();
        String appName = getAppName(context);
        if (i == 5) {
            return zaa(context, "common_google_play_services_invalid_account_text", appName);
        }
        if (i == 7) {
            return zaa(context, "common_google_play_services_network_error_text", appName);
        }
        if (i == 9) {
            return resources.getString(R.string.common_google_play_services_unsupported_text, new Object[]{appName});
        } else if (i == 20) {
            return zaa(context, "common_google_play_services_restricted_profile_text", appName);
        } else {
            switch (i) {
                case 1:
                    return resources.getString(R.string.common_google_play_services_install_text, new Object[]{appName});
                case 2:
                    if (DeviceProperties.isWearableWithoutPlayStore(context) != null) {
                        return resources.getString(R.string.common_google_play_services_wear_update_text);
                    }
                    return resources.getString(R.string.common_google_play_services_update_text, new Object[]{appName});
                case 3:
                    return resources.getString(R.string.common_google_play_services_enable_text, new Object[]{appName});
                default:
                    switch (i) {
                        case 16:
                            return zaa(context, "common_google_play_services_api_unavailable_text", appName);
                        case 17:
                            return zaa(context, "common_google_play_services_sign_in_failed_text", appName);
                        case 18:
                            return resources.getString(R.string.common_google_play_services_updating_text, new Object[]{appName});
                        default:
                            return resources.getString(com.google.android.gms.common.R.string.common_google_play_services_unknown_issue, new Object[]{appName});
                    }
            }
        }
    }

    @NonNull
    public static String getErrorNotificationMessage(Context context, int i) {
        if (i == 6) {
            return zaa(context, "common_google_play_services_resolution_required_text", getAppName(context));
        }
        return getErrorMessage(context, i);
    }

    @NonNull
    public static String getErrorDialogButtonMessage(Context context, int i) {
        context = context.getResources();
        switch (i) {
            case 1:
                return context.getString(R.string.common_google_play_services_install_button);
            case 2:
                return context.getString(R.string.common_google_play_services_update_button);
            case 3:
                return context.getString(R.string.common_google_play_services_enable_button);
            default:
                return context.getString(17039370);
        }
    }

    private static String zaa(Context context, String str, String str2) {
        Resources resources = context.getResources();
        context = zaa(context, str);
        if (context == null) {
            context = resources.getString(com.google.android.gms.common.R.string.common_google_play_services_unknown_issue);
        }
        return String.format(resources.getConfiguration().locale, context, new Object[]{str2});
    }

    @Nullable
    private static String zaa(Context context, String str) {
        synchronized (zaof) {
            String str2 = (String) zaof.get(str);
            if (str2 != null) {
                return str2;
            }
            context = GooglePlayServicesUtil.getRemoteResource(context);
            if (context == null) {
                return null;
            }
            int identifier = context.getIdentifier(str, "string", "com.google.android.gms");
            if (identifier == 0) {
                context = "GoogleApiAvailability";
                String str3 = "Missing resource: ";
                str = String.valueOf(str);
                Log.w(context, str.length() != 0 ? str3.concat(str) : new String(str3));
                return null;
            }
            context = context.getString(identifier);
            if (TextUtils.isEmpty(context)) {
                context = "GoogleApiAvailability";
                str3 = "Got empty resource: ";
                str = String.valueOf(str);
                Log.w(context, str.length() != 0 ? str3.concat(str) : new String(str3));
                return null;
            }
            zaof.put(str, context);
            return context;
        }
    }

    public static String getDefaultNotificationChannelName(Context context) {
        return context.getResources().getString(R.string.common_google_play_services_notification_channel_name);
    }

    private ConnectionErrorMessages() {
    }
}
