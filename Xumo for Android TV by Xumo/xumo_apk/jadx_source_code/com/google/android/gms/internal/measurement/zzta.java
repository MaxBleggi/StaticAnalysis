package com.google.android.gms.internal.measurement;

import android.util.Log;

final class zzta extends zzsx<Boolean> {
    zzta(zztd com_google_android_gms_internal_measurement_zztd, String str, Boolean bool) {
        super(com_google_android_gms_internal_measurement_zztd, str, bool);
    }

    final /* synthetic */ Object zzs(Object obj) {
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (zzsj.zzbqz.matcher(str).matches()) {
                return Boolean.valueOf(true);
            }
            if (zzsj.zzbra.matcher(str).matches()) {
                return Boolean.valueOf(null);
            }
        }
        String zztr = super.zztr();
        obj = String.valueOf(obj);
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(zztr).length() + 28) + String.valueOf(obj).length());
        stringBuilder.append("Invalid boolean value for ");
        stringBuilder.append(zztr);
        stringBuilder.append(": ");
        stringBuilder.append(obj);
        Log.e("PhenotypeFlag", stringBuilder.toString());
        return null;
    }
}
