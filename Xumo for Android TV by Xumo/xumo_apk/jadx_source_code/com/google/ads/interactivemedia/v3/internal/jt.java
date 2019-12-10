package com.google.ads.interactivemedia.v3.internal;

import com.google.ads.interactivemedia.v3.api.Ad;

/* compiled from: IMASDK */
public class jt {
    public static jt a;
    public boolean b = false;
    public int[] c = new int[]{-2013265920, -2013265920};
    public int d = -1728053248;
    public int e = 1;
    public int f = 1728053247;
    public int g = 1;
    public String h = "Advertisement";
    public int i = -3355444;
    public String j = "Arial";
    public int k = 12;
    public int l = 4;
    public boolean m = true;
    public String n = "···";
    public String o;
    public int p;
    public float q;
    public int r;
    public int s;
    public int t;

    public jt() {
        String str = "Learn More ";
        String valueOf = String.valueOf(this.n);
        this.o = valueOf.length() != 0 ? str.concat(valueOf) : new String(str);
        this.p = -3355444;
        this.q = 16.0f;
        this.r = 15;
        this.s = 25;
        this.t = 8;
    }

    static void a(com.google.ads.interactivemedia.v3.internal.jt r1, com.google.ads.interactivemedia.v3.api.Ad r2) {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.google.ads.interactivemedia.v3.internal.jt.a(com.google.ads.interactivemedia.v3.internal.jt, com.google.ads.interactivemedia.v3.api.Ad):void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: java.lang.NullPointerException
	at jadx.core.dex.nodes.MethodNode.initTryCatches(MethodNode.java:305)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:105)
	... 7 more
*/
        /*
        r4 = (com.google.ads.interactivemedia.v3.impl.data.b) r4;
        r4 = r4.getClickThruUrl();
        r0 = com.google.ads.interactivemedia.v3.internal.jx.a(r4);
        if (r0 != 0) goto L_0x0012;
        r0 = new java.net.URI;
        r0.<init>(r4);
        goto L_0x0037;
        r0 = new java.lang.Exception;
        r0.<init>();
        throw r0;
        r0 = "IMASDK";
        r1 = "Malformed clickthrough URL: ";
        r4 = java.lang.String.valueOf(r4);
        r2 = r4.length();
        if (r2 == 0) goto L_0x002c;
        r4 = r1.concat(r4);
        goto L_0x0031;
        r4 = new java.lang.String;
        r4.<init>(r1);
        android.util.Log.w(r0, r4);
        r4 = 0;
        r3.m = r4;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.jt.a(com.google.ads.interactivemedia.v3.internal.jt, com.google.ads.interactivemedia.v3.api.Ad):void");
    }

    public static jt a(Ad ad) {
        if (a != null) {
            return a;
        }
        jt jtVar = new jt();
        jtVar.b = ad.isSkippable();
        a(jtVar, ad);
        return jtVar;
    }
}
