package com.google.ads.interactivemedia.v3.internal;

import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.regex.Pattern;

/* compiled from: IMASDK */
public final class ft {
    public static final int a;
    public static final String b = Build.DEVICE;
    public static final String c = Build.MANUFACTURER;
    public static final String d = Build.MODEL;
    private static final Pattern e = Pattern.compile("(\\d\\d\\d\\d)\\-(\\d\\d)\\-(\\d\\d)[Tt](\\d\\d):(\\d\\d):(\\d\\d)(\\.(\\d+))?([Zz]|((\\+|\\-)(\\d\\d):?(\\d\\d)))?");
    private static final Pattern f = Pattern.compile("^(-)?P(([0-9]*)Y)?(([0-9]*)M)?(([0-9]*)D)?(T(([0-9]*)H)?(([0-9]*)M)?(([0-9.]*)S)?)?$");
    private static final Pattern g = Pattern.compile("%([A-Fa-f0-9]{2})");
    private static final int[] h = new int[]{0, 79764919, 159529838, 222504665, 319059676, 398814059, 445009330, 507990021, 638119352, 583659535, 797628118, 726387553, 890018660, 835552979, 1015980042, 944750013, 1276238704, 1221641927, 1167319070, 1095957929, 1595256236, 1540665371, 1452775106, 1381403509, 1780037320, 1859660671, 1671105958, 1733955601, 2031960084, 2111593891, 1889500026, 1952343757, -1742489888, -1662866601, -1851683442, -1788833735, -1960329156, -1880695413, -2103051438, -2040207643, -1104454824, -1159051537, -1213636554, -1284997759, -1389417084, -1444007885, -1532160278, -1603531939, -734892656, -789352409, -575645954, -646886583, -952755380, -1007220997, -827056094, -898286187, -231047128, -151282273, -71779514, -8804623, -515967244, -436212925, -390279782, -327299027, 881225847, 809987520, 1023691545, 969234094, 662832811, 591600412, 771767749, 717299826, 311336399, 374308984, 453813921, 533576470, 25881363, 88864420, 134795389, 214552010, 2023205639, 2086057648, 1897238633, 1976864222, 1804852699, 1867694188, 1645340341, 1724971778, 1587496639, 1516133128, 1461550545, 1406951526, 1302016099, 1230646740, 1142491917, 1087903418, -1398421865, -1469785312, -1524105735, -1578704818, -1079922613, -1151291908, -1239184603, -1293773166, -1968362705, -1905510760, -2094067647, -2014441994, -1716953613, -1654112188, -1876203875, -1796572374, -525066777, -462094256, -382327159, -302564546, -206542021, -143559028, -97365931, -17609246, -960696225, -1031934488, -817968335, -872425850, -709327229, -780559564, -600130067, -654598054, 1762451694, 1842216281, 1619975040, 1682949687, 2047383090, 2127137669, 1938468188, 2001449195, 1325665622, 1271206113, 1183200824, 1111960463, 1543535498, 1489069629, 1434599652, 1363369299, 622672798, 568075817, 748617968, 677256519, 907627842, 853037301, 1067152940, 995781531, 51762726, 131386257, 177728840, 240578815, 269590778, 349224269, 429104020, 491947555, -248556018, -168932423, -122852000, -60002089, -500490030, -420856475, -341238852, -278395381, -685261898, -739858943, -559578920, -630940305, -1004286614, -1058877219, -845023740, -916395085, -1119974018, -1174433591, -1262701040, -1333941337, -1371866206, -1426332139, -1481064244, -1552294533, -1690935098, -1611170447, -1833673816, -1770699233, -2009983462, -1930228819, -2119160460, -2056179517, 1569362073, 1498123566, 1409854455, 1355396672, 1317987909, 1246755826, 1192025387, 1137557660, 2072149281, 2135122070, 1912620623, 1992383480, 1753615357, 1816598090, 1627664531, 1707420964, 295390185, 358241886, 404320391, 483945776, 43990325, 106832002, 186451547, 266083308, 932423249, 861060070, 1041341759, 986742920, 613929101, 542559546, 756411363, 701822548, -978770311, -1050133554, -869589737, -924188512, -693284699, -764654318, -550540341, -605129092, -475935807, -413084042, -366743377, -287118056, -257573603, -194731862, -114850189, -35218492, -1984365303, -1921392450, -2143631769, -2063868976, -1698919467, -1635936670, -1824608069, -1744851700, -1347415887, -1418654458, -1506661409, -1561119128, -1129027987, -1200260134, -1254728445, -1309196108};

    /* compiled from: IMASDK */
    class AnonymousClass1 implements ThreadFactory {
        final /* synthetic */ String a;

        AnonymousClass1(String str) {
            this.a = str;
        }

        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, this.a);
        }
    }

    public static int a(int i) {
        return i != 8 ? i != 16 ? i != 24 ? i != 32 ? 0 : 1073741824 : Integer.MIN_VALUE : 2 : 3;
    }

    public static int a(long j) {
        return (int) (j >>> 32);
    }

    public static boolean a(Uri uri) {
        uri = uri.getScheme();
        if (!TextUtils.isEmpty(uri)) {
            if (uri.equals("file") == null) {
                return null;
            }
        }
        return true;
    }

    public static int b(long j) {
        return (int) j;
    }

    public static long b(int i, int i2) {
        return (((long) i2) & 4294967295L) | (((long) i) << 32);
    }

    public static boolean a(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null ? true : null;
        } else {
            return obj.equals(obj2);
        }
    }

    public static ExecutorService a(String str) {
        return Executors.newSingleThreadExecutor(new AnonymousClass1(str));
    }

    public static void a(com.google.ads.interactivemedia.v3.internal.et r0) {
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
        if (r0 == 0) goto L_0x0005;
    L_0x0002:
        r0.a();	 Catch:{ IOException -> 0x0005 }
    L_0x0005:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.ft.a(com.google.ads.interactivemedia.v3.internal.et):void");
    }

    public static String b(String str) {
        return str == null ? null : str.toLowerCase(Locale.US);
    }

    public static int a(int i, int i2) {
        return ((i + i2) - 1) / i2;
    }

    public static int a(long[] jArr, long j, boolean z, boolean z2) {
        jArr = Arrays.binarySearch(jArr, j);
        if (jArr < null) {
            jArr = -(jArr + 2);
        } else if (!z) {
            jArr--;
        }
        return z2 ? Math.max(0, jArr) : jArr;
    }

    public static int b(long[] jArr, long j, boolean z, boolean z2) {
        j = Arrays.binarySearch(jArr, j);
        if (j < null) {
            j ^= -1;
        } else if (!z) {
            j++;
        }
        return z2 ? Math.min(jArr.length - 1, j) : j;
    }

    public static long a(long j, long j2, long j3) {
        if (j3 >= j2 && j3 % j2 == 0) {
            return j / (j3 / j2);
        }
        if (j3 < j2 && j2 % j3 == 0) {
            return j * (j2 / j3);
        }
        j2 = (double) j2;
        j3 = (double) j3;
        Double.isNaN(j2);
        Double.isNaN(j3);
        j2 /= j3;
        j = (double) j;
        Double.isNaN(j);
        return (long) (j * j2);
    }

    public static void a(long[] jArr, long j, long j2) {
        int i = 0;
        if (j2 >= j && j2 % j == 0) {
            j2 /= j;
            while (i < jArr.length) {
                jArr[i] = jArr[i] / j2;
                i++;
            }
        } else if (j2 >= j || j % j2 != 0) {
            j = (double) j;
            j2 = (double) j2;
            Double.isNaN(j);
            Double.isNaN(j2);
            j /= j2;
            while (i < jArr.length) {
                j2 = (double) jArr[i];
                Double.isNaN(j2);
                jArr[i] = (long) (j2 * j);
                i++;
            }
        } else {
            j /= j2;
            while (i < jArr.length) {
                jArr[i] = jArr[i] * j;
                i++;
            }
        }
    }

    public static void a(java.net.HttpURLConnection r3, long r4) {
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
        r0 = a;
        r1 = 19;
        if (r0 == r1) goto L_0x000d;
    L_0x0006:
        r0 = a;
        r1 = 20;
        if (r0 == r1) goto L_0x000d;
    L_0x000c:
        return;
    L_0x000d:
        r3 = r3.getInputStream();	 Catch:{ IOException -> 0x0058, IOException -> 0x0058 }
        r0 = -1;	 Catch:{ IOException -> 0x0058, IOException -> 0x0058 }
        r2 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1));	 Catch:{ IOException -> 0x0058, IOException -> 0x0058 }
        if (r2 != 0) goto L_0x001f;	 Catch:{ IOException -> 0x0058, IOException -> 0x0058 }
    L_0x0017:
        r4 = r3.read();	 Catch:{ IOException -> 0x0058, IOException -> 0x0058 }
        r5 = -1;	 Catch:{ IOException -> 0x0058, IOException -> 0x0058 }
        if (r4 != r5) goto L_0x0026;	 Catch:{ IOException -> 0x0058, IOException -> 0x0058 }
    L_0x001e:
        return;	 Catch:{ IOException -> 0x0058, IOException -> 0x0058 }
    L_0x001f:
        r0 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;	 Catch:{ IOException -> 0x0058, IOException -> 0x0058 }
        r2 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1));	 Catch:{ IOException -> 0x0058, IOException -> 0x0058 }
        if (r2 > 0) goto L_0x0026;	 Catch:{ IOException -> 0x0058, IOException -> 0x0058 }
    L_0x0025:
        return;	 Catch:{ IOException -> 0x0058, IOException -> 0x0058 }
    L_0x0026:
        r4 = r3.getClass();	 Catch:{ IOException -> 0x0058, IOException -> 0x0058 }
        r4 = r4.getName();	 Catch:{ IOException -> 0x0058, IOException -> 0x0058 }
        r5 = "com.android.okhttp.internal.http.HttpTransport$ChunkedInputStream";	 Catch:{ IOException -> 0x0058, IOException -> 0x0058 }
        r5 = r4.equals(r5);	 Catch:{ IOException -> 0x0058, IOException -> 0x0058 }
        if (r5 != 0) goto L_0x003e;	 Catch:{ IOException -> 0x0058, IOException -> 0x0058 }
    L_0x0036:
        r5 = "com.android.okhttp.internal.http.HttpTransport$FixedLengthInputStream";	 Catch:{ IOException -> 0x0058, IOException -> 0x0058 }
        r4 = r4.equals(r5);	 Catch:{ IOException -> 0x0058, IOException -> 0x0058 }
        if (r4 == 0) goto L_0x0058;	 Catch:{ IOException -> 0x0058, IOException -> 0x0058 }
    L_0x003e:
        r4 = r3.getClass();	 Catch:{ IOException -> 0x0058, IOException -> 0x0058 }
        r4 = r4.getSuperclass();	 Catch:{ IOException -> 0x0058, IOException -> 0x0058 }
        r5 = "unexpectedEndOfInput";	 Catch:{ IOException -> 0x0058, IOException -> 0x0058 }
        r0 = 0;	 Catch:{ IOException -> 0x0058, IOException -> 0x0058 }
        r1 = new java.lang.Class[r0];	 Catch:{ IOException -> 0x0058, IOException -> 0x0058 }
        r4 = r4.getDeclaredMethod(r5, r1);	 Catch:{ IOException -> 0x0058, IOException -> 0x0058 }
        r5 = 1;	 Catch:{ IOException -> 0x0058, IOException -> 0x0058 }
        r4.setAccessible(r5);	 Catch:{ IOException -> 0x0058, IOException -> 0x0058 }
        r5 = new java.lang.Object[r0];	 Catch:{ IOException -> 0x0058, IOException -> 0x0058 }
        r4.invoke(r3, r5);	 Catch:{ IOException -> 0x0058, IOException -> 0x0058 }
    L_0x0058:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.ft.a(java.net.HttpURLConnection, long):void");
    }

    public static int c(String str) {
        int length = str.length();
        fe.a(length <= 4);
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            i = (i << 8) | str.charAt(i2);
        }
        return i;
    }

    public static byte[] d(String str) {
        byte[] bArr = new byte[(str.length() / 2)];
        for (int i = 0; i < bArr.length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) ((Character.digit(str.charAt(i2), 16) << 4) + Character.digit(str.charAt(i2 + 1), 16));
        }
        return bArr;
    }

    public static <T> String a(T[] tArr) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < tArr.length; i++) {
            stringBuilder.append(tArr[i].getClass().getSimpleName());
            if (i < tArr.length - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    public static java.lang.String a(android.content.Context r3, java.lang.String r4) {
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
        r0 = r3.getPackageName();	 Catch:{ NameNotFoundException -> 0x0010 }
        r3 = r3.getPackageManager();	 Catch:{ NameNotFoundException -> 0x0010 }
        r1 = 0;	 Catch:{ NameNotFoundException -> 0x0010 }
        r3 = r3.getPackageInfo(r0, r1);	 Catch:{ NameNotFoundException -> 0x0010 }
        r3 = r3.versionName;	 Catch:{ NameNotFoundException -> 0x0010 }
        goto L_0x0012;
    L_0x0010:
        r3 = "?";
    L_0x0012:
        r0 = android.os.Build.VERSION.RELEASE;
        r1 = java.lang.String.valueOf(r4);
        r1 = r1.length();
        r1 = r1 + 38;
        r2 = java.lang.String.valueOf(r3);
        r2 = r2.length();
        r1 = r1 + r2;
        r2 = java.lang.String.valueOf(r0);
        r2 = r2.length();
        r1 = r1 + r2;
        r2 = new java.lang.StringBuilder;
        r2.<init>(r1);
        r2.append(r4);
        r4 = "/";
        r2.append(r4);
        r2.append(r3);
        r3 = " (Linux;Android ";
        r2.append(r3);
        r2.append(r0);
        r3 = ") ";
        r2.append(r3);
        r3 = "ExoPlayerLib/1.5.16";
        r2.append(r3);
        r3 = r2.toString();
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.ft.a(android.content.Context, java.lang.String):java.lang.String");
    }

    public static int a(byte[] bArr, int i, int i2, int i3) {
        while (i < i2) {
            i3 = h[((i3 >>> 24) ^ (bArr[i] & 255)) & 255] ^ (i3 << 8);
            i++;
        }
        return i3;
    }

    static {
        int i;
        if (VERSION.SDK_INT == 25 && VERSION.CODENAME.charAt(0) == 'O') {
            i = 26;
        } else {
            i = VERSION.SDK_INT;
        }
        a = i;
    }
}
