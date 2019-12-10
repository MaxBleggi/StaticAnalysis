package com.google.android.gms.internal.measurement;

import android.content.ContentResolver;
import android.net.Uri;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

public class zzsj {
    public static final Uri CONTENT_URI = Uri.parse("content://com.google.android.gsf.gservices");
    private static final Uri zzbqy = Uri.parse("content://com.google.android.gsf.gservices/prefix");
    public static final Pattern zzbqz = Pattern.compile("^(1|true|t|on|yes|y)$", 2);
    public static final Pattern zzbra = Pattern.compile("^(0|false|f|off|no|n)$", 2);
    private static final AtomicBoolean zzbrb = new AtomicBoolean();
    private static HashMap<String, String> zzbrc;
    private static final HashMap<String, Boolean> zzbrd = new HashMap();
    private static final HashMap<String, Integer> zzbre = new HashMap();
    private static final HashMap<String, Long> zzbrf = new HashMap();
    private static final HashMap<String, Float> zzbrg = new HashMap();
    private static Object zzbrh;
    private static boolean zzbri;
    private static String[] zzbrj = new String[0];

    private static void zza(ContentResolver contentResolver) {
        if (zzbrc == null) {
            zzbrb.set(false);
            zzbrc = new HashMap();
            zzbrh = new Object();
            zzbri = false;
            contentResolver.registerContentObserver(CONTENT_URI, true, new zzsk(null));
            return;
        }
        if (zzbrb.getAndSet(false) != null) {
            zzbrc.clear();
            zzbrd.clear();
            zzbre.clear();
            zzbrf.clear();
            zzbrg.clear();
            zzbrh = new Object();
            zzbri = false;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String zza(android.content.ContentResolver r13, java.lang.String r14, java.lang.String r15) {
        /*
        r15 = com.google.android.gms.internal.measurement.zzsj.class;
        monitor-enter(r15);
        zza(r13);	 Catch:{ all -> 0x00aa }
        r0 = zzbrh;	 Catch:{ all -> 0x00aa }
        r1 = zzbrc;	 Catch:{ all -> 0x00aa }
        r1 = r1.containsKey(r14);	 Catch:{ all -> 0x00aa }
        r2 = 0;
        if (r1 == 0) goto L_0x001f;
    L_0x0011:
        r13 = zzbrc;	 Catch:{ all -> 0x00aa }
        r13 = r13.get(r14);	 Catch:{ all -> 0x00aa }
        r13 = (java.lang.String) r13;	 Catch:{ all -> 0x00aa }
        if (r13 == 0) goto L_0x001c;
    L_0x001b:
        goto L_0x001d;
    L_0x001c:
        r13 = r2;
    L_0x001d:
        monitor-exit(r15);	 Catch:{ all -> 0x00aa }
        return r13;
    L_0x001f:
        r1 = zzbrj;	 Catch:{ all -> 0x00aa }
        r3 = r1.length;	 Catch:{ all -> 0x00aa }
        r4 = 0;
        r5 = 0;
    L_0x0024:
        r6 = 1;
        if (r5 >= r3) goto L_0x0063;
    L_0x0027:
        r7 = r1[r5];	 Catch:{ all -> 0x00aa }
        r7 = r14.startsWith(r7);	 Catch:{ all -> 0x00aa }
        if (r7 == 0) goto L_0x0060;
    L_0x002f:
        r0 = zzbri;	 Catch:{ all -> 0x00aa }
        if (r0 == 0) goto L_0x003b;
    L_0x0033:
        r0 = zzbrc;	 Catch:{ all -> 0x00aa }
        r0 = r0.isEmpty();	 Catch:{ all -> 0x00aa }
        if (r0 == 0) goto L_0x005e;
    L_0x003b:
        r0 = zzbrj;	 Catch:{ all -> 0x00aa }
        r1 = zzbrc;	 Catch:{ all -> 0x00aa }
        r13 = zza(r13, r0);	 Catch:{ all -> 0x00aa }
        r1.putAll(r13);	 Catch:{ all -> 0x00aa }
        zzbri = r6;	 Catch:{ all -> 0x00aa }
        r13 = zzbrc;	 Catch:{ all -> 0x00aa }
        r13 = r13.containsKey(r14);	 Catch:{ all -> 0x00aa }
        if (r13 == 0) goto L_0x005e;
    L_0x0050:
        r13 = zzbrc;	 Catch:{ all -> 0x00aa }
        r13 = r13.get(r14);	 Catch:{ all -> 0x00aa }
        r13 = (java.lang.String) r13;	 Catch:{ all -> 0x00aa }
        if (r13 == 0) goto L_0x005b;
    L_0x005a:
        goto L_0x005c;
    L_0x005b:
        r13 = r2;
    L_0x005c:
        monitor-exit(r15);	 Catch:{ all -> 0x00aa }
        return r13;
    L_0x005e:
        monitor-exit(r15);	 Catch:{ all -> 0x00aa }
        return r2;
    L_0x0060:
        r5 = r5 + 1;
        goto L_0x0024;
    L_0x0063:
        monitor-exit(r15);	 Catch:{ all -> 0x00aa }
        r8 = CONTENT_URI;
        r9 = 0;
        r10 = 0;
        r11 = new java.lang.String[r6];
        r11[r4] = r14;
        r12 = 0;
        r7 = r13;
        r13 = r7.query(r8, r9, r10, r11, r12);
        if (r13 != 0) goto L_0x007a;
    L_0x0074:
        if (r13 == 0) goto L_0x0079;
    L_0x0076:
        r13.close();
    L_0x0079:
        return r2;
    L_0x007a:
        r15 = r13.moveToFirst();	 Catch:{ all -> 0x00a3 }
        if (r15 != 0) goto L_0x0089;
    L_0x0080:
        zza(r0, r14, r2);	 Catch:{ all -> 0x00a3 }
        if (r13 == 0) goto L_0x0088;
    L_0x0085:
        r13.close();
    L_0x0088:
        return r2;
    L_0x0089:
        r15 = r13.getString(r6);	 Catch:{ all -> 0x00a3 }
        if (r15 == 0) goto L_0x0096;
    L_0x008f:
        r1 = r15.equals(r2);	 Catch:{ all -> 0x00a3 }
        if (r1 == 0) goto L_0x0096;
    L_0x0095:
        r15 = r2;
    L_0x0096:
        zza(r0, r14, r15);	 Catch:{ all -> 0x00a3 }
        if (r15 == 0) goto L_0x009c;
    L_0x009b:
        goto L_0x009d;
    L_0x009c:
        r15 = r2;
    L_0x009d:
        if (r13 == 0) goto L_0x00a2;
    L_0x009f:
        r13.close();
    L_0x00a2:
        return r15;
    L_0x00a3:
        r14 = move-exception;
        if (r13 == 0) goto L_0x00a9;
    L_0x00a6:
        r13.close();
    L_0x00a9:
        throw r14;
    L_0x00aa:
        r13 = move-exception;
        monitor-exit(r15);	 Catch:{ all -> 0x00aa }
        throw r13;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzsj.zza(android.content.ContentResolver, java.lang.String, java.lang.String):java.lang.String");
    }

    private static void zza(Object obj, String str, String str2) {
        synchronized (zzsj.class) {
            if (obj == zzbrh) {
                zzbrc.put(str, str2);
            }
        }
    }

    private static Map<String, String> zza(ContentResolver contentResolver, String... strArr) {
        contentResolver = contentResolver.query(zzbqy, null, null, strArr, null);
        strArr = new TreeMap();
        if (contentResolver == null) {
            return strArr;
        }
        while (contentResolver.moveToNext()) {
            try {
                strArr.put(contentResolver.getString(0), contentResolver.getString(1));
            } finally {
                contentResolver.close();
            }
        }
        return strArr;
    }
}
