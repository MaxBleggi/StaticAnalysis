package com.google.ads.interactivemedia.v3.internal;

import java.util.regex.Pattern;

/* compiled from: IMASDK */
public final class cg {
    private static final Pattern c = Pattern.compile("^ [0-9a-fA-F]{8} ([0-9a-fA-F]{8}) ([0-9a-fA-F]{8})");
    public final int a;
    public final int b;

    public static com.google.ads.interactivemedia.v3.internal.cg a(java.lang.String r3, java.lang.String r4) {
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
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r0 = "iTunSMPB";
        r3 = r0.equals(r3);
        r0 = 0;
        if (r3 != 0) goto L_0x000a;
    L_0x0009:
        return r0;
    L_0x000a:
        r3 = c;
        r3 = r3.matcher(r4);
        r4 = r3.find();
        if (r4 == 0) goto L_0x0036;
    L_0x0016:
        r4 = 1;
        r4 = r3.group(r4);	 Catch:{ NumberFormatException -> 0x0036 }
        r1 = 16;	 Catch:{ NumberFormatException -> 0x0036 }
        r4 = java.lang.Integer.parseInt(r4, r1);	 Catch:{ NumberFormatException -> 0x0036 }
        r2 = 2;	 Catch:{ NumberFormatException -> 0x0036 }
        r3 = r3.group(r2);	 Catch:{ NumberFormatException -> 0x0036 }
        r3 = java.lang.Integer.parseInt(r3, r1);	 Catch:{ NumberFormatException -> 0x0036 }
        if (r4 != 0) goto L_0x002f;	 Catch:{ NumberFormatException -> 0x0036 }
    L_0x002c:
        if (r3 != 0) goto L_0x002f;	 Catch:{ NumberFormatException -> 0x0036 }
    L_0x002e:
        goto L_0x0035;	 Catch:{ NumberFormatException -> 0x0036 }
    L_0x002f:
        r1 = new com.google.ads.interactivemedia.v3.internal.cg;	 Catch:{ NumberFormatException -> 0x0036 }
        r1.<init>(r4, r3);	 Catch:{ NumberFormatException -> 0x0036 }
        r0 = r1;
    L_0x0035:
        return r0;
    L_0x0036:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.cg.a(java.lang.String, java.lang.String):com.google.ads.interactivemedia.v3.internal.cg");
    }

    public static cg a(int i) {
        int i2 = i >> 12;
        i &= 4095;
        return (i2 == 0 && i == 0) ? 0 : new cg(i2, i);
    }

    private cg(int i, int i2) {
        this.a = i;
        this.b = i2;
    }
}
