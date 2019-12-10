package com.google.ads.interactivemedia.v3.internal;

import android.os.Build;
import android.os.Build.VERSION;
import com.google.android.libraries.cast.companionlibrary.utils.CustomData.DEVICETYPE;
import com.google.android.libraries.cast.companionlibrary.utils.CustomData.KEY;
import org.json.JSONObject;

/* compiled from: IMASDK */
public final class ab {
    public static String a() {
        String str = Build.MANUFACTURER;
        String str2 = Build.MODEL;
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 2) + String.valueOf(str2).length());
        stringBuilder.append(str);
        stringBuilder.append("; ");
        stringBuilder.append(str2);
        return stringBuilder.toString();
    }

    public static String c() {
        return DEVICETYPE.ANDROID;
    }

    public static String b() {
        return Integer.toString(VERSION.SDK_INT);
    }

    public static JSONObject d() {
        JSONObject jSONObject = new JSONObject();
        ac.a(jSONObject, KEY.DEVICETYPE, a());
        ac.a(jSONObject, "osVersion", b());
        ac.a(jSONObject, "os", c());
        return jSONObject;
    }
}
