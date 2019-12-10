package com.xumo.xumo.util;

import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.xumo.xumo.model.LiveAsset;
import com.xumo.xumo.repository.UserPreferences;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.net.URL;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import javax.net.ssl.HttpsURLConnection;

public class XumoUtil {
    public static final String SHARING_ASSET_URL = "https://www.xumo.tv/video/%s/%s?utm_source=android";
    public static final String SHARING_CHANNEL_URL = "https://www.xumo.tv/channel/%s/%s?utm_source=android";
    public static final String SHARING_SPEC_CHANNEL_ASSET_URL = "https://www.xumo.tv/channel/%s/%s?v=%s&utm_source=android";
    static final SecureRandom numberGenerator = new SecureRandom();
    private static long startTime = System.currentTimeMillis();
    public static String urlGeo = "https://android-tv-app.xumo.com/geo-check/index.html";

    public static String getLiveTimeString(long j, long j2) {
        Date date = new Date(j);
        j = new Date(j2);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm", Locale.getDefault());
        j2 = simpleDateFormat.format(date);
        simpleDateFormat.applyPattern("hh:mm a");
        j = simpleDateFormat.format(j).toLowerCase();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(j2);
        stringBuilder.append(" - ");
        stringBuilder.append(j);
        return stringBuilder.toString();
    }

    public static String getProperTime(long j) {
        StringBuilder stringBuilder;
        String str = "";
        long j2 = j % 3600;
        long j3 = j2 % 60;
        j2 /= 60;
        j /= 3600;
        if (j > 0) {
            StringBuilder stringBuilder2;
            if (j > 0 && j < 10) {
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(str);
                stringBuilder2.append("0");
                stringBuilder2.append(String.valueOf(j));
                j = stringBuilder2.toString();
            } else if (j > 0) {
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(str);
                stringBuilder2.append(String.valueOf(j));
                j = stringBuilder2.toString();
            } else {
                j = new StringBuilder();
                j.append(str);
                j.append("00");
                j = j.toString();
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(j);
            stringBuilder.append(":");
            str = stringBuilder.toString();
        }
        if (j2 > 0 && j2 < 10) {
            j = new StringBuilder();
            j.append(str);
            j.append("0");
            j.append(String.valueOf(j2));
            j = j.toString();
        } else if (j2 > 0) {
            j = new StringBuilder();
            j.append(str);
            j.append(String.valueOf(j2));
            j = j.toString();
        } else {
            j = new StringBuilder();
            j.append(str);
            j.append("00");
            j = j.toString();
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(j);
        stringBuilder.append(":");
        j = stringBuilder.toString();
        if (j3 > 0 && j3 < 10) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(j);
            stringBuilder.append("0");
            stringBuilder.append(String.valueOf(j3));
            return stringBuilder.toString();
        } else if (j3 > 0) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(j);
            stringBuilder.append(String.valueOf(j3));
            return stringBuilder.toString();
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append(j);
            stringBuilder.append("00");
            return stringBuilder.toString();
        }
    }

    public static long getTimeMillisUTC(Date date) {
        if (date == null) {
            return 0;
        }
        Calendar instance = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        instance.setTime(date);
        return instance.getTimeInMillis();
    }

    public static long getCurrentTimeMillisUTC() {
        Calendar instance = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        instance.setTime(new Date(System.currentTimeMillis()));
        return instance.getTimeInMillis();
    }

    static String joinStrings(String str, String[] strArr) {
        if (strArr != null) {
            if (strArr.length > 0) {
                String str2 = strArr[0];
                for (int i = 1; i < strArr.length; i++) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(str2);
                    stringBuilder.append(str);
                    stringBuilder.append(strArr[i]);
                    str2 = stringBuilder.toString();
                }
                return str2;
            }
        }
        return "";
    }

    public static String getRandomNumber(int i) {
        long random = (long) (Math.random() * 1.0E15d);
        int length = String.valueOf(random).length();
        String valueOf = String.valueOf(random);
        if (i > length) {
            i = length;
        }
        return valueOf.substring(0, i);
    }

    public static String generateRandomId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static long getStartTime() {
        return startTime;
    }

    public static int checkGeoBlock() {
        int i = 0;
        try {
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL(urlGeo).openConnection();
            httpsURLConnection.setRequestMethod(HttpRequest.METHOD_GET);
            httpsURLConnection.setRequestProperty(HttpRequest.HEADER_CACHE_CONTROL, "no-cache");
            httpsURLConnection.setInstanceFollowRedirects(false);
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setDoOutput(false);
            httpsURLConnection.connect();
            int responseCode = httpsURLConnection.getResponseCode();
            httpsURLConnection.disconnect();
            i = responseCode;
        } catch (Exception e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(e);
            LogUtil.d(stringBuilder.toString());
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("responseCode:");
        stringBuilder2.append(i);
        LogUtil.d(stringBuilder2.toString());
        return i;
    }

    public static long getStartTimeDiff(@NonNull Date date, @NonNull LiveAsset liveAsset) {
        long time = (date.getTime() / 1000) - liveAsset.getStart();
        if (time <= 0 || liveAsset.getRunTime() == 0) {
            return 0;
        }
        if (liveAsset.getRunTime() < time) {
            return 0;
        }
        return time * 1000;
    }

    public static int getPxToDp(Context context, int i) {
        return (int) (((float) i) / context.getResources().getDisplayMetrics().density);
    }

    public static long getTimeDiffToNowInSecs(long j) {
        return getTimeDiffToNow(j) / 1000;
    }

    public static long getTimeDiffToNow(long j) {
        return System.currentTimeMillis() - j;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.exoplayer2.text.CaptionStyleCompat getCaptionStyleCompat(android.content.Context r10) {
        /*
        r0 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r0 = r0.getTextOpacity();
        r1 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        r2 = 0;
        switch(r0) {
            case 1: goto L_0x009f;
            case 2: goto L_0x0073;
            case 3: goto L_0x0041;
            case 4: goto L_0x0010;
            default: goto L_0x000e;
        };
    L_0x000e:
        goto L_0x00cc;
    L_0x0010:
        r0 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r0 = r0.getTextColor();
        switch(r0) {
            case 1: goto L_0x003b;
            case 2: goto L_0x0035;
            case 3: goto L_0x002f;
            case 4: goto L_0x0029;
            case 5: goto L_0x0023;
            case 6: goto L_0x001d;
            default: goto L_0x001b;
        };
    L_0x001b:
        goto L_0x00cc;
    L_0x001d:
        r0 = android.graphics.Color.argb(r1, r2, r2, r1);
        goto L_0x00ca;
    L_0x0023:
        r0 = android.graphics.Color.argb(r1, r1, r2, r2);
        goto L_0x00ca;
    L_0x0029:
        r0 = android.graphics.Color.argb(r1, r2, r1, r2);
        goto L_0x00ca;
    L_0x002f:
        r0 = android.graphics.Color.argb(r1, r1, r1, r2);
        goto L_0x00ca;
    L_0x0035:
        r0 = android.graphics.Color.argb(r1, r2, r2, r2);
        goto L_0x00ca;
    L_0x003b:
        r0 = android.graphics.Color.argb(r1, r1, r1, r1);
        goto L_0x00ca;
    L_0x0041:
        r0 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r0 = r0.getTextColor();
        r3 = 204; // 0xcc float:2.86E-43 double:1.01E-321;
        switch(r0) {
            case 1: goto L_0x006e;
            case 2: goto L_0x0069;
            case 3: goto L_0x0063;
            case 4: goto L_0x005d;
            case 5: goto L_0x0057;
            case 6: goto L_0x0051;
            default: goto L_0x004e;
        };
    L_0x004e:
        r0 = 0;
        goto L_0x00ca;
    L_0x0051:
        r0 = android.graphics.Color.argb(r3, r2, r2, r1);
        goto L_0x00ca;
    L_0x0057:
        r0 = android.graphics.Color.argb(r3, r1, r2, r2);
        goto L_0x00ca;
    L_0x005d:
        r0 = android.graphics.Color.argb(r3, r2, r1, r2);
        goto L_0x00ca;
    L_0x0063:
        r0 = android.graphics.Color.argb(r3, r1, r1, r2);
        goto L_0x00ca;
    L_0x0069:
        r0 = android.graphics.Color.argb(r3, r2, r2, r2);
        goto L_0x00ca;
    L_0x006e:
        r0 = android.graphics.Color.argb(r3, r1, r1, r1);
        goto L_0x00ca;
    L_0x0073:
        r0 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r0 = r0.getTextColor();
        r3 = 153; // 0x99 float:2.14E-43 double:7.56E-322;
        switch(r0) {
            case 1: goto L_0x009a;
            case 2: goto L_0x0095;
            case 3: goto L_0x0090;
            case 4: goto L_0x008b;
            case 5: goto L_0x0086;
            case 6: goto L_0x0081;
            default: goto L_0x0080;
        };
    L_0x0080:
        goto L_0x004e;
    L_0x0081:
        r0 = android.graphics.Color.argb(r3, r2, r2, r1);
        goto L_0x00ca;
    L_0x0086:
        r0 = android.graphics.Color.argb(r3, r1, r2, r2);
        goto L_0x00ca;
    L_0x008b:
        r0 = android.graphics.Color.argb(r3, r2, r1, r2);
        goto L_0x00ca;
    L_0x0090:
        r0 = android.graphics.Color.argb(r3, r1, r1, r2);
        goto L_0x00ca;
    L_0x0095:
        r0 = android.graphics.Color.argb(r3, r2, r2, r2);
        goto L_0x00ca;
    L_0x009a:
        r0 = android.graphics.Color.argb(r3, r1, r1, r1);
        goto L_0x00ca;
    L_0x009f:
        r0 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r0 = r0.getTextColor();
        r3 = 102; // 0x66 float:1.43E-43 double:5.04E-322;
        switch(r0) {
            case 1: goto L_0x00c6;
            case 2: goto L_0x00c1;
            case 3: goto L_0x00bc;
            case 4: goto L_0x00b7;
            case 5: goto L_0x00b2;
            case 6: goto L_0x00ad;
            default: goto L_0x00ac;
        };
    L_0x00ac:
        goto L_0x004e;
    L_0x00ad:
        r0 = android.graphics.Color.argb(r3, r2, r2, r1);
        goto L_0x00ca;
    L_0x00b2:
        r0 = android.graphics.Color.argb(r3, r1, r2, r2);
        goto L_0x00ca;
    L_0x00b7:
        r0 = android.graphics.Color.argb(r3, r2, r1, r2);
        goto L_0x00ca;
    L_0x00bc:
        r0 = android.graphics.Color.argb(r3, r1, r1, r2);
        goto L_0x00ca;
    L_0x00c1:
        r0 = android.graphics.Color.argb(r3, r2, r2, r2);
        goto L_0x00ca;
    L_0x00c6:
        r0 = android.graphics.Color.argb(r3, r1, r1, r1);
    L_0x00ca:
        r4 = r0;
        goto L_0x00cd;
    L_0x00cc:
        r4 = 0;
    L_0x00cd:
        r0 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r0 = r0.getBackgroundOpacity();
        switch(r0) {
            case 1: goto L_0x0196;
            case 2: goto L_0x016a;
            case 3: goto L_0x013e;
            case 4: goto L_0x010b;
            case 5: goto L_0x00da;
            default: goto L_0x00d8;
        };
    L_0x00d8:
        goto L_0x019c;
    L_0x00da:
        r0 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r0 = r0.getBackgroundColor();
        switch(r0) {
            case 1: goto L_0x0105;
            case 2: goto L_0x00ff;
            case 3: goto L_0x00f9;
            case 4: goto L_0x00f3;
            case 5: goto L_0x00ed;
            case 6: goto L_0x00e7;
            default: goto L_0x00e5;
        };
    L_0x00e5:
        goto L_0x019c;
    L_0x00e7:
        r0 = android.graphics.Color.argb(r1, r2, r2, r1);
        goto L_0x019a;
    L_0x00ed:
        r0 = android.graphics.Color.argb(r1, r1, r2, r2);
        goto L_0x019a;
    L_0x00f3:
        r0 = android.graphics.Color.argb(r1, r2, r1, r2);
        goto L_0x019a;
    L_0x00f9:
        r0 = android.graphics.Color.argb(r1, r1, r1, r2);
        goto L_0x019a;
    L_0x00ff:
        r0 = android.graphics.Color.argb(r1, r2, r2, r2);
        goto L_0x019a;
    L_0x0105:
        r0 = android.graphics.Color.argb(r1, r1, r1, r1);
        goto L_0x019a;
    L_0x010b:
        r0 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r0 = r0.getBackgroundColor();
        r3 = 150; // 0x96 float:2.1E-43 double:7.4E-322;
        switch(r0) {
            case 1: goto L_0x0139;
            case 2: goto L_0x0133;
            case 3: goto L_0x012d;
            case 4: goto L_0x0127;
            case 5: goto L_0x0121;
            case 6: goto L_0x011b;
            default: goto L_0x0118;
        };
    L_0x0118:
        r0 = 0;
        goto L_0x019a;
    L_0x011b:
        r0 = android.graphics.Color.argb(r3, r2, r2, r1);
        goto L_0x019a;
    L_0x0121:
        r0 = android.graphics.Color.argb(r3, r1, r2, r2);
        goto L_0x019a;
    L_0x0127:
        r0 = android.graphics.Color.argb(r3, r2, r1, r2);
        goto L_0x019a;
    L_0x012d:
        r0 = android.graphics.Color.argb(r3, r1, r1, r2);
        goto L_0x019a;
    L_0x0133:
        r0 = android.graphics.Color.argb(r3, r2, r2, r2);
        goto L_0x019a;
    L_0x0139:
        r0 = android.graphics.Color.argb(r3, r1, r1, r1);
        goto L_0x019a;
    L_0x013e:
        r0 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r0 = r0.getBackgroundColor();
        r3 = 100;
        switch(r0) {
            case 1: goto L_0x0165;
            case 2: goto L_0x0160;
            case 3: goto L_0x015b;
            case 4: goto L_0x0156;
            case 5: goto L_0x0151;
            case 6: goto L_0x014c;
            default: goto L_0x014b;
        };
    L_0x014b:
        goto L_0x0118;
    L_0x014c:
        r0 = android.graphics.Color.argb(r3, r2, r2, r1);
        goto L_0x019a;
    L_0x0151:
        r0 = android.graphics.Color.argb(r3, r1, r2, r2);
        goto L_0x019a;
    L_0x0156:
        r0 = android.graphics.Color.argb(r3, r2, r1, r2);
        goto L_0x019a;
    L_0x015b:
        r0 = android.graphics.Color.argb(r3, r1, r1, r2);
        goto L_0x019a;
    L_0x0160:
        r0 = android.graphics.Color.argb(r3, r2, r2, r2);
        goto L_0x019a;
    L_0x0165:
        r0 = android.graphics.Color.argb(r3, r1, r1, r1);
        goto L_0x019a;
    L_0x016a:
        r0 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r0 = r0.getBackgroundColor();
        r3 = 50;
        switch(r0) {
            case 1: goto L_0x0191;
            case 2: goto L_0x018c;
            case 3: goto L_0x0187;
            case 4: goto L_0x0182;
            case 5: goto L_0x017d;
            case 6: goto L_0x0178;
            default: goto L_0x0177;
        };
    L_0x0177:
        goto L_0x0118;
    L_0x0178:
        r0 = android.graphics.Color.argb(r3, r2, r2, r1);
        goto L_0x019a;
    L_0x017d:
        r0 = android.graphics.Color.argb(r3, r1, r2, r2);
        goto L_0x019a;
    L_0x0182:
        r0 = android.graphics.Color.argb(r3, r2, r1, r2);
        goto L_0x019a;
    L_0x0187:
        r0 = android.graphics.Color.argb(r3, r1, r1, r2);
        goto L_0x019a;
    L_0x018c:
        r0 = android.graphics.Color.argb(r3, r2, r2, r2);
        goto L_0x019a;
    L_0x0191:
        r0 = android.graphics.Color.argb(r3, r1, r1, r1);
        goto L_0x019a;
    L_0x0196:
        r0 = android.graphics.Color.argb(r2, r1, r1, r1);
    L_0x019a:
        r5 = r0;
        goto L_0x019d;
    L_0x019c:
        r5 = 0;
    L_0x019d:
        r0 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r0 = r0.getEdgeType();
        switch(r0) {
            case 1: goto L_0x01a8;
            case 2: goto L_0x01ad;
            case 3: goto L_0x01aa;
            default: goto L_0x01a8;
        };
    L_0x01a8:
        r7 = 0;
        goto L_0x01af;
    L_0x01aa:
        r0 = 2;
        r7 = 2;
        goto L_0x01af;
    L_0x01ad:
        r0 = 1;
        r7 = 1;
    L_0x01af:
        r0 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r0 = r0.getEdgeColor();
        switch(r0) {
            case 1: goto L_0x01d9;
            case 2: goto L_0x01d4;
            case 3: goto L_0x01cf;
            case 4: goto L_0x01c8;
            case 5: goto L_0x01c3;
            case 6: goto L_0x01bc;
            default: goto L_0x01ba;
        };
    L_0x01ba:
        r8 = 0;
        goto L_0x01db;
    L_0x01bc:
        r2 = -16776961; // 0xffffffffff0000ff float:-1.7014636E38 double:NaN;
        r8 = -16776961; // 0xffffffffff0000ff float:-1.7014636E38 double:NaN;
        goto L_0x01db;
    L_0x01c3:
        r2 = -65536; // 0xffffffffffff0000 float:NaN double:NaN;
        r8 = -65536; // 0xffffffffffff0000 float:NaN double:NaN;
        goto L_0x01db;
    L_0x01c8:
        r2 = -16711936; // 0xffffffffff00ff00 float:-1.7146522E38 double:NaN;
        r8 = -16711936; // 0xffffffffff00ff00 float:-1.7146522E38 double:NaN;
        goto L_0x01db;
    L_0x01cf:
        r2 = -256; // 0xffffffffffffff00 float:NaN double:NaN;
        r8 = -256; // 0xffffffffffffff00 float:NaN double:NaN;
        goto L_0x01db;
    L_0x01d4:
        r2 = -16777216; // 0xffffffffff000000 float:-1.7014118E38 double:NaN;
        r8 = -16777216; // 0xffffffffff000000 float:-1.7014118E38 double:NaN;
        goto L_0x01db;
    L_0x01d9:
        r2 = -1;
        r8 = -1;
    L_0x01db:
        r0 = r10.getAssets();
        r1 = "fonts/OpenSans-Regular.ttf";
        r0 = android.graphics.Typeface.createFromAsset(r0, r1);
        r1 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r1 = r1.getTextStyle();
        switch(r1) {
            case 1: goto L_0x0208;
            case 2: goto L_0x01fd;
            case 3: goto L_0x01f2;
            default: goto L_0x01f0;
        };
    L_0x01f0:
        r9 = r0;
        goto L_0x0213;
    L_0x01f2:
        r10 = r10.getAssets();
        r0 = "fonts/OpenSans-Bold.ttf";
        r10 = android.graphics.Typeface.createFromAsset(r10, r0);
        goto L_0x0212;
    L_0x01fd:
        r10 = r10.getAssets();
        r0 = "fonts/Lato-Regular.ttf";
        r10 = android.graphics.Typeface.createFromAsset(r10, r0);
        goto L_0x0212;
    L_0x0208:
        r10 = r10.getAssets();
        r0 = "fonts/OpenSans-Regular.ttf";
        r10 = android.graphics.Typeface.createFromAsset(r10, r0);
    L_0x0212:
        r9 = r10;
    L_0x0213:
        r10 = new com.google.android.exoplayer2.text.CaptionStyleCompat;
        r6 = 0;
        r3 = r10;
        r3.<init>(r4, r5, r6, r7, r8, r9);
        return r10;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xumo.xumo.util.XumoUtil.getCaptionStyleCompat(android.content.Context):com.google.android.exoplayer2.text.CaptionStyleCompat");
    }

    public static int getTextSize() {
        switch (UserPreferences.getInstance().getTextSize()) {
            case 1:
                return 20;
            case 2:
                return 24;
            case 3:
                return 28;
            default:
                return 25;
        }
    }

    public static String formatMoviesTime(String str) {
        if (str == null || str == "") {
            return "";
        }
        str = str.split(":");
        StringBuilder stringBuilder;
        if (str.length == 3) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(Integer.parseInt(str[0]));
            stringBuilder.append("h ");
            stringBuilder.append(Integer.parseInt(str[1]));
            stringBuilder.append("m");
            return stringBuilder.toString();
        } else if (str.length != 2) {
            return "1m";
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append(Integer.parseInt(str[0]));
            stringBuilder.append("m");
            return stringBuilder.toString();
        }
    }

    public static String generateRandomShortId() {
        byte[] bArr = new byte[16];
        numberGenerator.nextBytes(bArr);
        return Long.toHexString(ByteBuffer.wrap(bArr).asLongBuffer().get());
    }

    public static String getKeyCodeText(int i) {
        if (i != 96) {
            switch (i) {
                case 19:
                    return "UP";
                case 20:
                    return "DOWN";
                case 21:
                    return "LEFT";
                case 22:
                    return "RIGHT";
                case 23:
                    break;
                default:
                    return Integer.toString(i);
            }
        }
        return "CENTER";
    }

    public static String generateTempDeviceId() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("temp-");
        String generateRandomShortId = generateRandomShortId();
        stringBuffer.append(generateRandomShortId.substring(0, 8));
        stringBuffer.append("-");
        stringBuffer.append(generateRandomShortId.substring(8, 12));
        stringBuffer.append("-");
        stringBuffer.append(generateRandomShortId.substring(12));
        stringBuffer.append("-");
        generateRandomShortId = generateRandomShortId();
        stringBuffer.append(generateRandomShortId.substring(0, 4));
        stringBuffer.append("-");
        stringBuffer.append(generateRandomShortId.substring(4));
        return stringBuffer.toString();
    }

    public static boolean isTempDeviceId(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getDeviceAuthorization - isTempDeviceId deviceId = ");
        stringBuilder.append(str);
        LogUtil.d(stringBuilder.toString());
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return str.replaceFirst("XumoDeviceId ", "").startsWith("temp-");
    }
}
