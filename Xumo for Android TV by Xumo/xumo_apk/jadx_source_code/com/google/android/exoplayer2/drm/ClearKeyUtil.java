package com.google.android.exoplayer2.drm;

import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import org.json.JSONArray;
import org.json.JSONObject;

final class ClearKeyUtil {
    private static final String TAG = "ClearKeyUtil";

    private ClearKeyUtil() {
    }

    public static byte[] adjustRequestData(byte[] bArr) {
        if (Util.SDK_INT >= 27) {
            return bArr;
        }
        return Util.getUtf8Bytes(base64ToBase64Url(Util.fromUtf8Bytes(bArr)));
    }

    public static byte[] adjustResponseData(byte[] bArr) {
        if (Util.SDK_INT >= 27) {
            return bArr;
        }
        try {
            JSONObject jSONObject = new JSONObject(Util.fromUtf8Bytes(bArr));
            StringBuilder stringBuilder = new StringBuilder("{\"keys\":[");
            JSONArray jSONArray = jSONObject.getJSONArray("keys");
            for (int i = 0; i < jSONArray.length(); i++) {
                if (i != 0) {
                    stringBuilder.append(",");
                }
                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                stringBuilder.append("{\"k\":\"");
                stringBuilder.append(base64UrlToBase64(jSONObject2.getString("k")));
                stringBuilder.append("\",\"kid\":\"");
                stringBuilder.append(base64UrlToBase64(jSONObject2.getString("kid")));
                stringBuilder.append("\",\"kty\":\"");
                stringBuilder.append(jSONObject2.getString("kty"));
                stringBuilder.append("\"}");
            }
            stringBuilder.append("]}");
            return Util.getUtf8Bytes(stringBuilder.toString());
        } catch (Throwable e) {
            String str = TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Failed to adjust response data: ");
            stringBuilder2.append(Util.fromUtf8Bytes(bArr));
            Log.e(str, stringBuilder2.toString(), e);
            return bArr;
        }
    }

    private static String base64ToBase64Url(String str) {
        return str.replace('+', '-').replace('/', '_');
    }

    private static String base64UrlToBase64(String str) {
        return str.replace('-', '+').replace('_', '/');
    }
}
