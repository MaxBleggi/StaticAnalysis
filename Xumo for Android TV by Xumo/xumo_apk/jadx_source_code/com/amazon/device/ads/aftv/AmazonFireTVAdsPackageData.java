package com.amazon.device.ads.aftv;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import org.json.JSONObject;

class AmazonFireTVAdsPackageData {
    private static final String LOGTAG = "AmazonFireTVAdsPackageData";

    AmazonFireTVAdsPackageData() {
    }

    static JSONObject getAppInfo(Context context) {
        JSONObject jSONObject = new JSONObject();
        if (context == null) {
            Log.e(LOGTAG, "APS: Context passed was null, ignoring app info retrieval");
            return jSONObject;
        }
        PackageInfo packageInfo;
        String num;
        String packageName = context.getPackageName();
        PackageManager packageManager = context.getPackageManager();
        String str = (String) packageManager.getApplicationLabel(context.getApplicationInfo());
        String str2 = null;
        try {
            packageInfo = packageManager.getPackageInfo(packageName, 0);
        } catch (NameNotFoundException e) {
            String str3 = LOGTAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("APS: Could not retrieve app info, reason ");
            stringBuilder.append(e.toString());
            Log.e(str3, stringBuilder.toString());
            packageInfo = null;
        }
        if (packageInfo != null) {
            str2 = packageInfo.versionName;
            num = Integer.toString(packageInfo.versionCode);
        } else {
            num = null;
        }
        getPackageJSON(jSONObject, packageName, str, str2, num);
        return jSONObject;
    }

    private static void getPackageJSON(JSONObject jSONObject, String str, String str2, String str3, String str4) {
        try {
            jSONObject.put("lbl", str2);
            jSONObject.put("pn", str);
            if (str4 != null) {
                jSONObject.put("v", str4);
            }
            if (str3 != null) {
                jSONObject.put("vn", str3);
            }
        } catch (JSONObject jSONObject2) {
            str = LOGTAG;
            str2 = new StringBuilder();
            str2.append("APS: jsonexception occured while serializing package json ");
            str2.append(jSONObject2.toString());
            Log.e(str, str2.toString());
        }
    }

    public static Bundle getAppBundle(Context context) {
        context = getAppInfo(context);
        Bundle bundle = new Bundle();
        try {
            bundle.putString("pn", context.has("pn") ? context.getString("pn") : "");
            bundle.putString("lbl", context.has("lbl") ? context.getString("pn") : "");
            bundle.putString("v", context.has("v") ? context.getString("v") : "");
            bundle.putString("vn", context.has("vn") ? context.getString("vn") : "");
        } catch (Context context2) {
            String str = LOGTAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("APS: jsonexception occured while serializing package json ");
            stringBuilder.append(context2.toString());
            Log.e(str, stringBuilder.toString());
        }
        return bundle;
    }
}
