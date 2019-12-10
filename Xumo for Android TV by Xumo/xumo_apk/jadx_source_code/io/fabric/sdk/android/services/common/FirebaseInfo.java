package io.fabric.sdk.android.services.common;

import android.content.Context;
import android.text.TextUtils;
import io.fabric.sdk.android.Fabric;

public class FirebaseInfo {
    static final String AUTO_INITIALIZE = "io.fabric.auto_initialize";
    static final String FIREBASE_FEATURE_SWITCH = "com.crashlytics.useFirebaseAppId";
    static final String GOOGLE_APP_ID = "google_app_id";

    String getApiKeyFromFirebaseAppId(Context context) {
        int resourcesIdentifier = CommonUtils.getResourcesIdentifier(context, GOOGLE_APP_ID, "string");
        if (resourcesIdentifier == 0) {
            return null;
        }
        Fabric.getLogger().d(Fabric.TAG, "Generating Crashlytics ApiKey from google_app_id in Strings");
        return createApiKeyFromFirebaseAppId(context.getResources().getString(resourcesIdentifier));
    }

    String createApiKeyFromFirebaseAppId(String str) {
        return CommonUtils.sha256(str).substring(0, 40);
    }

    public boolean isFirebaseCrashlyticsEnabled(Context context) {
        boolean z = false;
        if (CommonUtils.getBooleanResourceValue(context, FIREBASE_FEATURE_SWITCH, false)) {
            return true;
        }
        if (hasGoogleAppId(context) && hasApiKey(context) == null) {
            z = true;
        }
        return z;
    }

    boolean hasApiKey(Context context) {
        if (TextUtils.isEmpty(new ApiKey().getApiKeyFromManifest(context))) {
            return TextUtils.isEmpty(new ApiKey().getApiKeyFromStrings(context)) ^ 1;
        }
        return true;
    }

    public boolean isAutoInitializeFlagEnabled(Context context) {
        int resourcesIdentifier = CommonUtils.getResourcesIdentifier(context, AUTO_INITIALIZE, "bool");
        if (resourcesIdentifier == 0) {
            return null;
        }
        context = context.getResources().getBoolean(resourcesIdentifier);
        if (context != null) {
            Fabric.getLogger().d(Fabric.TAG, "Found Fabric auto-initialization flag for joint Firebase/Fabric customers");
        }
        return context;
    }

    boolean hasGoogleAppId(Context context) {
        int resourcesIdentifier = CommonUtils.getResourcesIdentifier(context, GOOGLE_APP_ID, "string");
        if (resourcesIdentifier == 0) {
            return null;
        }
        return TextUtils.isEmpty(context.getResources().getString(resourcesIdentifier)) ^ 1;
    }

    public boolean isDataCollectionDefaultEnabled(Context context) {
        context = FirebaseAppImpl.getInstance(context);
        if (context == null) {
            return true;
        }
        return context.isDataCollectionDefaultEnabled();
    }
}
