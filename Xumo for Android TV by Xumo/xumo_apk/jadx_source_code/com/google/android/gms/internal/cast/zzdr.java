package com.google.android.gms.internal.cast;

import android.text.TextUtils;
import com.google.android.gms.common.images.WebImage;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.json.JSONArray;

public final class zzdr {
    private static final zzdh zzbe = new zzdh("MetadataUtils");
    private static final String[] zzzc = new String[]{"Z", "+hh", "+hhmm", "+hh:mm"};
    private static final String zzzd;

    public static void zza(java.util.List<com.google.android.gms.common.images.WebImage> r3, org.json.JSONArray r4) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r3.clear();	 Catch:{ JSONException -> 0x001a }
        r0 = 0;	 Catch:{ JSONException -> 0x001a }
    L_0x0004:
        r1 = r4.length();	 Catch:{ JSONException -> 0x001a }
        if (r0 >= r1) goto L_0x0019;	 Catch:{ JSONException -> 0x001a }
    L_0x000a:
        r1 = r4.getJSONObject(r0);	 Catch:{ JSONException -> 0x001a }
        r2 = new com.google.android.gms.common.images.WebImage;	 Catch:{ IllegalArgumentException -> 0x0016 }
        r2.<init>(r1);	 Catch:{ IllegalArgumentException -> 0x0016 }
        r3.add(r2);	 Catch:{ IllegalArgumentException -> 0x0016 }
    L_0x0016:
        r0 = r0 + 1;
        goto L_0x0004;
    L_0x0019:
        return;
    L_0x001a:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzdr.zza(java.util.List, org.json.JSONArray):void");
    }

    public static JSONArray zzd(List<WebImage> list) {
        if (list == null && list.isEmpty()) {
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        for (WebImage toJson : list) {
            jSONArray.put(toJson.toJson());
        }
        return jSONArray;
    }

    public static String zza(Calendar calendar) {
        if (calendar == null) {
            zzbe.d("Calendar object cannot be null", new Object[0]);
            return null;
        }
        String str = zzzd;
        if (calendar.get(11) == 0 && calendar.get(12) == 0 && calendar.get(13) == 0) {
            str = "yyyyMMdd";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str);
        simpleDateFormat.setTimeZone(calendar.getTimeZone());
        calendar = simpleDateFormat.format(calendar.getTime());
        if (calendar.endsWith("+0000")) {
            calendar = calendar.replace("+0000", zzzc[0]);
        }
        return calendar;
    }

    public static Calendar zzv(String str) {
        if (TextUtils.isEmpty(str)) {
            zzbe.d("Input string is empty or null", new Object[0]);
            return null;
        }
        String zzw = zzw(str);
        if (TextUtils.isEmpty(zzw)) {
            zzbe.d("Invalid date format", new Object[0]);
            return null;
        }
        str = zzx(str);
        String str2 = "yyyyMMdd";
        if (!TextUtils.isEmpty(str)) {
            zzw = String.valueOf(zzw);
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(zzw).length() + 1) + String.valueOf(str).length());
            stringBuilder.append(zzw);
            stringBuilder.append("T");
            stringBuilder.append(str);
            zzw = stringBuilder.toString();
            if (str.length() == 6) {
                str2 = "yyyyMMdd'T'HHmmss";
            } else {
                str2 = zzzd;
            }
        }
        str = GregorianCalendar.getInstance();
        try {
            str.setTime(new SimpleDateFormat(str2).parse(zzw));
            return str;
        } catch (String str3) {
            zzbe.d("Error parsing string: %s", str3.getMessage());
            return null;
        }
    }

    private static String zzw(String str) {
        if (TextUtils.isEmpty(str)) {
            zzbe.d("Input string is empty or null", new Object[0]);
            return null;
        }
        try {
            return str.substring(0, 8);
        } catch (String str2) {
            zzbe.i("Error extracting the date: %s", str2.getMessage());
            return null;
        }
    }

    private static String zzx(String str) {
        if (TextUtils.isEmpty(str)) {
            zzbe.d("string is empty or null", new Object[0]);
            return null;
        }
        int indexOf = str.indexOf(84);
        int i = indexOf + 1;
        if (indexOf != 8) {
            zzbe.d("T delimeter is not found", new Object[0]);
            return null;
        }
        indexOf = 1;
        try {
            str = str.substring(i);
            if (str.length() == 6) {
                return str;
            }
            char charAt = str.charAt(6);
            if (charAt == '+' || charAt == '-') {
                i = str.length();
                if (!(i == zzzc[1].length() + 6 || i == zzzc[2].length() + 6)) {
                    if (i != zzzc[3].length() + 6) {
                        indexOf = 0;
                    }
                }
                if (indexOf != 0) {
                    return str.replaceAll("([\\+\\-]\\d\\d):(\\d\\d)", "$1$2");
                }
            } else if (charAt == 'Z') {
                if (str.length() != zzzc[0].length() + 6) {
                    return null;
                }
                str = String.valueOf(str.substring(0, str.length() - 1));
                String valueOf = String.valueOf("+0000");
                return valueOf.length() != 0 ? str.concat(valueOf) : new String(str);
            }
            return null;
        } catch (String str2) {
            zzbe.d("Error extracting the time substring: %s", str2.getMessage());
            return null;
        }
    }

    static {
        String valueOf = String.valueOf("yyyyMMdd'T'HHmmss");
        String valueOf2 = String.valueOf(zzzc[0]);
        zzzd = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }
}
