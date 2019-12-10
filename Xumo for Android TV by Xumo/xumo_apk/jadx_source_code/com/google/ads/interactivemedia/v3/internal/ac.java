package com.google.ads.interactivemedia.v3.internal;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build.VERSION;
import android.view.WindowManager;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: IMASDK */
public class ac {
    static float a = Resources.getSystem().getDisplayMetrics().density;
    private static WindowManager b;
    private static String[] c = new String[]{"x", "y", "width", "height"};

    /* compiled from: IMASDK */
    private static class a {
        final float a;
        final float b;

        a(float f, float f2) {
            this.a = f;
            this.b = f2;
        }
    }

    public static void a(Context context) {
        if (context != null) {
            a = context.getResources().getDisplayMetrics().density;
            b = (WindowManager) context.getSystemService("window");
        }
    }

    public static JSONObject a(int i, int i2, int i3, int i4) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("x", (double) a(i));
            jSONObject.put("y", (double) a(i2));
            jSONObject.put("width", (double) a(i3));
            jSONObject.put("height", (double) a(i4));
        } catch (int i5) {
            ad.a("Error with creating viewStateObject", i5);
        }
        return jSONObject;
    }

    static float a(int i) {
        return ((float) i) / a;
    }

    public static void a(JSONObject jSONObject, String str, Object obj) {
        try {
            jSONObject.put(str, obj);
        } catch (JSONObject jSONObject2) {
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 47);
            stringBuilder.append("JSONException during JSONObject.put for name [");
            stringBuilder.append(str);
            stringBuilder.append("]");
            ad.a(stringBuilder.toString(), jSONObject2);
        }
    }

    public static void a(JSONObject jSONObject, String str) {
        try {
            jSONObject.put("adSessionId", str);
        } catch (JSONObject jSONObject2) {
            ad.a("Error with setting ad session id", jSONObject2);
        }
    }

    public static void a(JSONObject jSONObject, List<String> list) {
        JSONArray jSONArray = new JSONArray();
        for (String put : list) {
            jSONArray.put(put);
        }
        try {
            jSONObject.put("isFriendlyObstructionFor", jSONArray);
        } catch (JSONObject jSONObject2) {
            ad.a("Error with setting friendly obstruction", jSONObject2);
        }
    }

    public static void a(JSONObject jSONObject, JSONObject jSONObject2) {
        try {
            JSONArray optJSONArray = jSONObject.optJSONArray("childViews");
            if (optJSONArray == null) {
                optJSONArray = new JSONArray();
                jSONObject.put("childViews", optJSONArray);
            }
            optJSONArray.put(jSONObject2);
        } catch (JSONObject jSONObject3) {
            lt.a(jSONObject3);
        }
    }

    private static a b(JSONObject jSONObject) {
        float f = 0.0f;
        int i = 0;
        if (VERSION.SDK_INT < 17) {
            jSONObject = jSONObject.optJSONArray("childViews");
            if (jSONObject != null) {
                int length = jSONObject.length();
                float f2 = 0.0f;
                while (i < length) {
                    JSONObject optJSONObject = jSONObject.optJSONObject(i);
                    if (optJSONObject != null) {
                        double optDouble = optJSONObject.optDouble("x");
                        double optDouble2 = optJSONObject.optDouble("y");
                        double optDouble3 = optJSONObject.optDouble("width");
                        double optDouble4 = optJSONObject.optDouble("height");
                        f = Math.max(f, (float) (optDouble + optDouble3));
                        f2 = Math.max(f2, (float) (optDouble2 + optDouble4));
                    }
                    i++;
                }
                jSONObject = f2;
                return new a(f, jSONObject);
            }
        } else if (b != null) {
            jSONObject = new Point(0, 0);
            b.getDefaultDisplay().getRealSize(jSONObject);
            f = a(jSONObject.x);
            jSONObject = a(jSONObject.y);
            return new a(f, jSONObject);
        }
        jSONObject = null;
        return new a(f, jSONObject);
    }

    public static void a(JSONObject jSONObject) {
        a b = b(jSONObject);
        try {
            jSONObject.put("width", (double) b.a);
            jSONObject.put("height", (double) b.b);
        } catch (JSONObject jSONObject2) {
            lt.a(jSONObject2);
        }
    }

    public static boolean b(JSONObject jSONObject, JSONObject jSONObject2) {
        boolean z = true;
        if (jSONObject == null && jSONObject2 == null) {
            return true;
        }
        if (jSONObject != null) {
            if (jSONObject2 != null) {
                if (!c(jSONObject, jSONObject2) || !d(jSONObject, jSONObject2) || !e(jSONObject, jSONObject2) || f(jSONObject, jSONObject2) == null) {
                    z = false;
                }
                return z;
            }
        }
        return false;
    }

    private static boolean c(JSONObject jSONObject, JSONObject jSONObject2) {
        for (String str : c) {
            if (jSONObject.optDouble(str) != jSONObject2.optDouble(str)) {
                return false;
            }
        }
        return true;
    }

    private static boolean d(JSONObject jSONObject, JSONObject jSONObject2) {
        return jSONObject.optString("adSessionId", "").equals(jSONObject2.optString("adSessionId", ""));
    }

    private static boolean e(JSONObject jSONObject, JSONObject jSONObject2) {
        JSONArray optJSONArray = jSONObject.optJSONArray("isFriendlyObstructionFor");
        JSONArray optJSONArray2 = jSONObject2.optJSONArray("isFriendlyObstructionFor");
        if (optJSONArray == null && optJSONArray2 == null) {
            return true;
        }
        if (!a(optJSONArray, optJSONArray2)) {
            return false;
        }
        for (int i = 0; i < optJSONArray.length(); i++) {
            if (!optJSONArray.optString(i, "").equals(optJSONArray2.optString(i, ""))) {
                return false;
            }
        }
        return true;
    }

    private static boolean f(JSONObject jSONObject, JSONObject jSONObject2) {
        JSONArray optJSONArray = jSONObject.optJSONArray("childViews");
        JSONArray optJSONArray2 = jSONObject2.optJSONArray("childViews");
        if (optJSONArray == null && optJSONArray2 == null) {
            return true;
        }
        if (!a(optJSONArray, optJSONArray2)) {
            return false;
        }
        for (int i = 0; i < optJSONArray.length(); i++) {
            if (!b(optJSONArray.optJSONObject(i), optJSONArray2.optJSONObject(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean a(JSONArray jSONArray, JSONArray jSONArray2) {
        boolean z = true;
        if (jSONArray == null && jSONArray2 == null) {
            return true;
        }
        if (jSONArray != null) {
            if (jSONArray2 != null) {
                if (jSONArray.length() != jSONArray2.length()) {
                    z = false;
                }
                return z;
            }
        }
        return false;
    }
}
