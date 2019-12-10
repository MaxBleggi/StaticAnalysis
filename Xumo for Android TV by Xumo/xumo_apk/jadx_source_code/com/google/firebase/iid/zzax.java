package com.google.firebase.iid;

import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.Nullable;
import com.google.android.gms.measurement.AppMeasurement.Param;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

final class zzax {
    private static final long zzdf = TimeUnit.DAYS.toMillis(7);
    private final long timestamp;
    final String zzbq;
    private final String zzdg;

    private zzax(String str, String str2, long j) {
        this.zzbq = str;
        this.zzdg = str2;
        this.timestamp = j;
    }

    static zzax zzi(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (!str.startsWith("{")) {
            return new zzax(str, null, 0);
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            return new zzax(jSONObject.getString("token"), jSONObject.getString("appVersion"), jSONObject.getLong(Param.TIMESTAMP));
        } catch (String str2) {
            str2 = String.valueOf(str2);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str2).length() + 23);
            stringBuilder.append("Failed to parse token: ");
            stringBuilder.append(str2);
            Log.w("FirebaseInstanceId", stringBuilder.toString());
            return null;
        }
    }

    static String zza(String str, String str2, long j) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("token", str);
            jSONObject.put("appVersion", str2);
            jSONObject.put(Param.TIMESTAMP, j);
            return jSONObject.toString();
        } catch (String str3) {
            str3 = String.valueOf(str3);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str3).length() + 24);
            stringBuilder.append("Failed to encode token: ");
            stringBuilder.append(str3);
            Log.w("FirebaseInstanceId", stringBuilder.toString());
            return null;
        }
    }

    static String zza(@Nullable zzax com_google_firebase_iid_zzax) {
        return com_google_firebase_iid_zzax == null ? null : com_google_firebase_iid_zzax.zzbq;
    }

    final boolean zzj(String str) {
        if (System.currentTimeMillis() <= this.timestamp + zzdf) {
            if (str.equals(this.zzdg) != null) {
                return null;
            }
        }
        return true;
    }
}
