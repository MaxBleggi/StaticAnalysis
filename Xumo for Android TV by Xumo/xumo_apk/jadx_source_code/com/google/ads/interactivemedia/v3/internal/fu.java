package com.google.ads.interactivemedia.v3.internal;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* compiled from: IMASDK */
final class fu implements ge<Date>, gm<Date> {
    private final DateFormat a;
    private final DateFormat b;

    fu() {
        this(DateFormat.getDateTimeInstance(2, 2, Locale.US), DateFormat.getDateTimeInstance(2, 2));
    }

    fu(String str) {
        this(new SimpleDateFormat(str, Locale.US), new SimpleDateFormat(str));
    }

    public fu(int i, int i2) {
        this(DateFormat.getDateTimeInstance(i, i2, Locale.US), DateFormat.getDateTimeInstance(i, i2));
    }

    fu(DateFormat dateFormat, DateFormat dateFormat2) {
        this.a = dateFormat;
        this.b = dateFormat2;
    }

    public gf a(Date date, Type type, gl glVar) {
        synchronized (this.b) {
            glVar = new gk(this.a.format(date));
        }
        return glVar;
    }

    public Date a(gf gfVar, Type type, gd gdVar) throws gj {
        if ((gfVar instanceof gk) != null) {
            gfVar = a(gfVar);
            if (type == Date.class) {
                return gfVar;
            }
            if (type == Timestamp.class) {
                return new Timestamp(gfVar.getTime());
            }
            if (type == java.sql.Date.class) {
                return new java.sql.Date(gfVar.getTime());
            }
            gdVar = new StringBuilder();
            gdVar.append(getClass());
            gdVar.append(" cannot deserialize to ");
            gdVar.append(type);
            throw new IllegalArgumentException(gdVar.toString());
        }
        throw new gj("The date should be a string value");
    }

    private java.util.Date a(com.google.ads.interactivemedia.v3.internal.gf r5) {
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
        r4 = this;
        r0 = r4.b;
        monitor-enter(r0);
        r1 = r4.b;	 Catch:{ ParseException -> 0x0011 }
        r2 = r5.b();	 Catch:{ ParseException -> 0x0011 }
        r1 = r1.parse(r2);	 Catch:{ ParseException -> 0x0011 }
        monitor-exit(r0);	 Catch:{ all -> 0x000f }
        return r1;
    L_0x000f:
        r5 = move-exception;
        goto L_0x0038;
    L_0x0011:
        r1 = r4.a;	 Catch:{ ParseException -> 0x001d }
        r2 = r5.b();	 Catch:{ ParseException -> 0x001d }
        r1 = r1.parse(r2);	 Catch:{ ParseException -> 0x001d }
        monitor-exit(r0);	 Catch:{ all -> 0x000f }
        return r1;
    L_0x001d:
        r1 = r5.b();	 Catch:{ ParseException -> 0x002d }
        r2 = new java.text.ParsePosition;	 Catch:{ ParseException -> 0x002d }
        r3 = 0;	 Catch:{ ParseException -> 0x002d }
        r2.<init>(r3);	 Catch:{ ParseException -> 0x002d }
        r1 = com.google.ads.interactivemedia.v3.internal.hv.a(r1, r2);	 Catch:{ ParseException -> 0x002d }
        monitor-exit(r0);	 Catch:{ all -> 0x000f }
        return r1;	 Catch:{ all -> 0x000f }
    L_0x002d:
        r1 = move-exception;	 Catch:{ all -> 0x000f }
        r2 = new com.google.ads.interactivemedia.v3.internal.gn;	 Catch:{ all -> 0x000f }
        r5 = r5.b();	 Catch:{ all -> 0x000f }
        r2.<init>(r5, r1);	 Catch:{ all -> 0x000f }
        throw r2;	 Catch:{ all -> 0x000f }
    L_0x0038:
        monitor-exit(r0);	 Catch:{ all -> 0x000f }
        throw r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.fu.a(com.google.ads.interactivemedia.v3.internal.gf):java.util.Date");
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(fu.class.getSimpleName());
        stringBuilder.append('(');
        stringBuilder.append(this.b.getClass().getSimpleName());
        stringBuilder.append(')');
        return stringBuilder.toString();
    }

    public /* synthetic */ Object b(gf gfVar, Type type, gd gdVar) throws gj {
        return a(gfVar, type, gdVar);
    }
}
