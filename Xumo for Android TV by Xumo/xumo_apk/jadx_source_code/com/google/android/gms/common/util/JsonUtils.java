package com.google.android.gms.common.util;

import android.text.TextUtils;
import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@KeepForSdk
@VisibleForTesting
public final class JsonUtils {
    private static final Pattern zzhb = Pattern.compile("\\\\.");
    private static final Pattern zzhc = Pattern.compile("[\\\\\"/\b\f\n\r\t]");

    private JsonUtils() {
    }

    @KeepForSdk
    public static String unescapeString(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        str = zzd.unescape(str);
        Matcher matcher = zzhb.matcher(str);
        StringBuffer stringBuffer = null;
        while (matcher.find()) {
            if (stringBuffer == null) {
                stringBuffer = new StringBuffer();
            }
            char charAt = matcher.group().charAt(1);
            if (charAt == '\"') {
                matcher.appendReplacement(stringBuffer, "\"");
            } else if (charAt == '/') {
                matcher.appendReplacement(stringBuffer, "/");
            } else if (charAt == '\\') {
                matcher.appendReplacement(stringBuffer, "\\\\");
            } else if (charAt == 'b') {
                matcher.appendReplacement(stringBuffer, "\b");
            } else if (charAt == 'f') {
                matcher.appendReplacement(stringBuffer, "\f");
            } else if (charAt == 'n') {
                matcher.appendReplacement(stringBuffer, "\n");
            } else if (charAt == 'r') {
                matcher.appendReplacement(stringBuffer, "\r");
            } else if (charAt == 't') {
                matcher.appendReplacement(stringBuffer, "\t");
            } else {
                throw new IllegalStateException("Found an escaped character that should never be.");
            }
        }
        if (stringBuffer == null) {
            return str;
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    @KeepForSdk
    public static String escapeString(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        Matcher matcher = zzhc.matcher(str);
        StringBuffer stringBuffer = null;
        while (matcher.find()) {
            if (stringBuffer == null) {
                stringBuffer = new StringBuffer();
            }
            char charAt = matcher.group().charAt(0);
            if (charAt == '\"') {
                matcher.appendReplacement(stringBuffer, "\\\\\\\"");
            } else if (charAt == '/') {
                matcher.appendReplacement(stringBuffer, "\\\\/");
            } else if (charAt != '\\') {
                switch (charAt) {
                    case '\b':
                        matcher.appendReplacement(stringBuffer, "\\\\b");
                        break;
                    case '\t':
                        matcher.appendReplacement(stringBuffer, "\\\\t");
                        break;
                    case '\n':
                        matcher.appendReplacement(stringBuffer, "\\\\n");
                        break;
                    default:
                        switch (charAt) {
                            case '\f':
                                matcher.appendReplacement(stringBuffer, "\\\\f");
                                break;
                            case '\r':
                                matcher.appendReplacement(stringBuffer, "\\\\r");
                                break;
                            default:
                                break;
                        }
                }
            } else {
                matcher.appendReplacement(stringBuffer, "\\\\\\\\");
            }
        }
        if (stringBuffer == null) {
            return str;
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    @com.google.android.gms.common.annotation.KeepForSdk
    public static boolean areJsonValuesEquivalent(java.lang.Object r5, java.lang.Object r6) {
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
        r0 = 1;
        if (r5 != 0) goto L_0x0006;
    L_0x0003:
        if (r6 != 0) goto L_0x0006;
    L_0x0005:
        return r0;
    L_0x0006:
        r1 = 0;
        if (r5 == 0) goto L_0x0083;
    L_0x0009:
        if (r6 != 0) goto L_0x000d;
    L_0x000b:
        goto L_0x0083;
    L_0x000d:
        r2 = r5 instanceof org.json.JSONObject;
        if (r2 == 0) goto L_0x004c;
    L_0x0011:
        r2 = r6 instanceof org.json.JSONObject;
        if (r2 == 0) goto L_0x004c;
    L_0x0015:
        r5 = (org.json.JSONObject) r5;
        r6 = (org.json.JSONObject) r6;
        r2 = r5.length();
        r3 = r6.length();
        if (r2 == r3) goto L_0x0024;
    L_0x0023:
        return r1;
    L_0x0024:
        r2 = r5.keys();
    L_0x0028:
        r3 = r2.hasNext();
        if (r3 == 0) goto L_0x004b;
    L_0x002e:
        r3 = r2.next();
        r3 = (java.lang.String) r3;
        r4 = r6.has(r3);
        if (r4 != 0) goto L_0x003b;
    L_0x003a:
        return r1;
    L_0x003b:
        r4 = r5.get(r3);	 Catch:{ JSONException -> 0x004a }
        r3 = r6.get(r3);	 Catch:{ JSONException -> 0x004a }
        r3 = areJsonValuesEquivalent(r4, r3);	 Catch:{ JSONException -> 0x004a }
        if (r3 != 0) goto L_0x0028;
    L_0x0049:
        return r1;
    L_0x004a:
        return r1;
    L_0x004b:
        return r0;
    L_0x004c:
        r2 = r5 instanceof org.json.JSONArray;
        if (r2 == 0) goto L_0x007e;
    L_0x0050:
        r2 = r6 instanceof org.json.JSONArray;
        if (r2 == 0) goto L_0x007e;
    L_0x0054:
        r5 = (org.json.JSONArray) r5;
        r6 = (org.json.JSONArray) r6;
        r2 = r5.length();
        r3 = r6.length();
        if (r2 == r3) goto L_0x0063;
    L_0x0062:
        return r1;
    L_0x0063:
        r2 = 0;
    L_0x0064:
        r3 = r5.length();
        if (r2 >= r3) goto L_0x007d;
    L_0x006a:
        r3 = r5.get(r2);	 Catch:{ JSONException -> 0x007c }
        r4 = r6.get(r2);	 Catch:{ JSONException -> 0x007c }
        r3 = areJsonValuesEquivalent(r3, r4);	 Catch:{ JSONException -> 0x007c }
        if (r3 != 0) goto L_0x0079;
    L_0x0078:
        return r1;
    L_0x0079:
        r2 = r2 + 1;
        goto L_0x0064;
    L_0x007c:
        return r1;
    L_0x007d:
        return r0;
    L_0x007e:
        r5 = r5.equals(r6);
        return r5;
    L_0x0083:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.util.JsonUtils.areJsonValuesEquivalent(java.lang.Object, java.lang.Object):boolean");
    }
}
