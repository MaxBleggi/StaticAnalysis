package com.google.ads.interactivemedia.v3.internal;

import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

/* compiled from: IMASDK */
public final class hu {
    public static final gp<String> A = new gp<String>() {
        public String a(hx hxVar) throws IOException {
            hy f = hxVar.f();
            if (f == hy.NULL) {
                hxVar.j();
                return null;
            } else if (f == hy.BOOLEAN) {
                return Boolean.toString(hxVar.i());
            } else {
                return hxVar.h();
            }
        }

        public void a(hz hzVar, String str) throws IOException {
            hzVar.b(str);
        }

        public /* synthetic */ Object read(hx hxVar) throws IOException {
            return a(hxVar);
        }

        public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
            a(hzVar, (String) obj);
        }
    };
    public static final gp<BigDecimal> B = new gp<BigDecimal>() {
        public BigDecimal a(hx hxVar) throws IOException {
            if (hxVar.f() == hy.NULL) {
                hxVar.j();
                return null;
            }
            try {
                return new BigDecimal(hxVar.h());
            } catch (Throwable e) {
                throw new gn(e);
            }
        }

        public void a(hz hzVar, BigDecimal bigDecimal) throws IOException {
            hzVar.a((Number) bigDecimal);
        }

        public /* synthetic */ Object read(hx hxVar) throws IOException {
            return a(hxVar);
        }

        public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
            a(hzVar, (BigDecimal) obj);
        }
    };
    public static final gp<BigInteger> C = new gp<BigInteger>() {
        public BigInteger a(hx hxVar) throws IOException {
            if (hxVar.f() == hy.NULL) {
                hxVar.j();
                return null;
            }
            try {
                return new BigInteger(hxVar.h());
            } catch (Throwable e) {
                throw new gn(e);
            }
        }

        public void a(hz hzVar, BigInteger bigInteger) throws IOException {
            hzVar.a((Number) bigInteger);
        }

        public /* synthetic */ Object read(hx hxVar) throws IOException {
            return a(hxVar);
        }

        public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
            a(hzVar, (BigInteger) obj);
        }
    };
    public static final gq D = a(String.class, A);
    public static final gp<StringBuilder> E = new gp<StringBuilder>() {
        public StringBuilder a(hx hxVar) throws IOException {
            if (hxVar.f() != hy.NULL) {
                return new StringBuilder(hxVar.h());
            }
            hxVar.j();
            return null;
        }

        public void a(hz hzVar, StringBuilder stringBuilder) throws IOException {
            hzVar.b(stringBuilder == null ? null : stringBuilder.toString());
        }

        public /* synthetic */ Object read(hx hxVar) throws IOException {
            return a(hxVar);
        }

        public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
            a(hzVar, (StringBuilder) obj);
        }
    };
    public static final gq F = a(StringBuilder.class, E);
    public static final gp<StringBuffer> G = new gp<StringBuffer>() {
        public StringBuffer a(hx hxVar) throws IOException {
            if (hxVar.f() != hy.NULL) {
                return new StringBuffer(hxVar.h());
            }
            hxVar.j();
            return null;
        }

        public void a(hz hzVar, StringBuffer stringBuffer) throws IOException {
            hzVar.b(stringBuffer == null ? null : stringBuffer.toString());
        }

        public /* synthetic */ Object read(hx hxVar) throws IOException {
            return a(hxVar);
        }

        public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
            a(hzVar, (StringBuffer) obj);
        }
    };
    public static final gq H = a(StringBuffer.class, G);
    public static final gp<URL> I = new gp<URL>() {
        public URL a(hx hxVar) throws IOException {
            URL url = null;
            if (hxVar.f() == hy.NULL) {
                hxVar.j();
                return null;
            }
            hxVar = hxVar.h();
            if (!"null".equals(hxVar)) {
                url = new URL(hxVar);
            }
            return url;
        }

        public void a(hz hzVar, URL url) throws IOException {
            hzVar.b(url == null ? null : url.toExternalForm());
        }

        public /* synthetic */ Object read(hx hxVar) throws IOException {
            return a(hxVar);
        }

        public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
            a(hzVar, (URL) obj);
        }
    };
    public static final gq J = a(URL.class, I);
    public static final gp<URI> K = new gp<URI>() {
        public URI a(hx hxVar) throws IOException {
            URI uri = null;
            if (hxVar.f() == hy.NULL) {
                hxVar.j();
                return null;
            }
            try {
                hxVar = hxVar.h();
                if (!"null".equals(hxVar)) {
                    uri = new URI(hxVar);
                }
                return uri;
            } catch (Throwable e) {
                throw new gg(e);
            }
        }

        public void a(hz hzVar, URI uri) throws IOException {
            hzVar.b(uri == null ? null : uri.toASCIIString());
        }

        public /* synthetic */ Object read(hx hxVar) throws IOException {
            return a(hxVar);
        }

        public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
            a(hzVar, (URI) obj);
        }
    };
    public static final gq L = a(URI.class, K);
    public static final gp<InetAddress> M = new gp<InetAddress>() {
        public InetAddress a(hx hxVar) throws IOException {
            if (hxVar.f() != hy.NULL) {
                return InetAddress.getByName(hxVar.h());
            }
            hxVar.j();
            return null;
        }

        public void a(hz hzVar, InetAddress inetAddress) throws IOException {
            hzVar.b(inetAddress == null ? null : inetAddress.getHostAddress());
        }

        public /* synthetic */ Object read(hx hxVar) throws IOException {
            return a(hxVar);
        }

        public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
            a(hzVar, (InetAddress) obj);
        }
    };
    public static final gq N = b(InetAddress.class, M);
    public static final gp<UUID> O = new gp<UUID>() {
        public UUID a(hx hxVar) throws IOException {
            if (hxVar.f() != hy.NULL) {
                return UUID.fromString(hxVar.h());
            }
            hxVar.j();
            return null;
        }

        public void a(hz hzVar, UUID uuid) throws IOException {
            hzVar.b(uuid == null ? null : uuid.toString());
        }

        public /* synthetic */ Object read(hx hxVar) throws IOException {
            return a(hxVar);
        }

        public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
            a(hzVar, (UUID) obj);
        }
    };
    public static final gq P = a(UUID.class, O);
    public static final gp<Currency> Q = new gp<Currency>() {
        public Currency a(hx hxVar) throws IOException {
            return Currency.getInstance(hxVar.h());
        }

        public void a(hz hzVar, Currency currency) throws IOException {
            hzVar.b(currency.getCurrencyCode());
        }

        public /* synthetic */ Object read(hx hxVar) throws IOException {
            return a(hxVar);
        }

        public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
            a(hzVar, (Currency) obj);
        }
    }.nullSafe();
    public static final gq R = a(Currency.class, Q);
    public static final gq S = new gq() {
        public <T> gp<T> a(fz fzVar, hw<T> hwVar) {
            if (hwVar.a() != Timestamp.class) {
                return null;
            }
            fzVar = fzVar.a(Date.class);
            return new gp<Timestamp>(this) {
                final /* synthetic */ AnonymousClass19 b;

                public Timestamp a(hx hxVar) throws IOException {
                    Date date = (Date) fzVar.read(hxVar);
                    return date != null ? new Timestamp(date.getTime()) : null;
                }

                public void a(hz hzVar, Timestamp timestamp) throws IOException {
                    fzVar.write(hzVar, timestamp);
                }

                public /* synthetic */ Object read(hx hxVar) throws IOException {
                    return a(hxVar);
                }

                public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
                    a(hzVar, (Timestamp) obj);
                }
            };
        }
    };
    public static final gp<Calendar> T = new gp<Calendar>() {
        public Calendar a(hx hxVar) throws IOException {
            if (hxVar.f() == hy.NULL) {
                hxVar.j();
                return null;
            }
            hxVar.c();
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            int i6 = 0;
            while (hxVar.f() != hy.END_OBJECT) {
                String g = hxVar.g();
                int m = hxVar.m();
                if ("year".equals(g)) {
                    i = m;
                } else if ("month".equals(g)) {
                    i2 = m;
                } else if ("dayOfMonth".equals(g)) {
                    i3 = m;
                } else if ("hourOfDay".equals(g)) {
                    i4 = m;
                } else if ("minute".equals(g)) {
                    i5 = m;
                } else if ("second".equals(g)) {
                    i6 = m;
                }
            }
            hxVar.d();
            return new GregorianCalendar(i, i2, i3, i4, i5, i6);
        }

        public void a(hz hzVar, Calendar calendar) throws IOException {
            if (calendar == null) {
                hzVar.f();
                return;
            }
            hzVar.d();
            hzVar.a("year");
            hzVar.a((long) calendar.get(1));
            hzVar.a("month");
            hzVar.a((long) calendar.get(2));
            hzVar.a("dayOfMonth");
            hzVar.a((long) calendar.get(5));
            hzVar.a("hourOfDay");
            hzVar.a((long) calendar.get(11));
            hzVar.a("minute");
            hzVar.a((long) calendar.get(12));
            hzVar.a("second");
            hzVar.a((long) calendar.get(13));
            hzVar.e();
        }

        public /* synthetic */ Object read(hx hxVar) throws IOException {
            return a(hxVar);
        }

        public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
            a(hzVar, (Calendar) obj);
        }
    };
    public static final gq U = b(Calendar.class, GregorianCalendar.class, T);
    public static final gp<Locale> V = new gp<Locale>() {
        public Locale a(hx hxVar) throws IOException {
            String str = null;
            if (hxVar.f() == hy.NULL) {
                hxVar.j();
                return null;
            }
            StringTokenizer stringTokenizer = new StringTokenizer(hxVar.h(), EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
            hxVar = stringTokenizer.hasMoreElements() != null ? stringTokenizer.nextToken() : null;
            String nextToken = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
            if (stringTokenizer.hasMoreElements()) {
                str = stringTokenizer.nextToken();
            }
            if (nextToken == null && str == null) {
                return new Locale(hxVar);
            }
            if (str == null) {
                return new Locale(hxVar, nextToken);
            }
            return new Locale(hxVar, nextToken, str);
        }

        public void a(hz hzVar, Locale locale) throws IOException {
            hzVar.b(locale == null ? null : locale.toString());
        }

        public /* synthetic */ Object read(hx hxVar) throws IOException {
            return a(hxVar);
        }

        public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
            a(hzVar, (Locale) obj);
        }
    };
    public static final gq W = a(Locale.class, V);
    public static final gp<gf> X = new gp<gf>() {
        public gf a(hx hxVar) throws IOException {
            gf gcVar;
            switch (hxVar.f()) {
                case NUMBER:
                    return new gk(new hb(hxVar.h()));
                case BOOLEAN:
                    return new gk(Boolean.valueOf(hxVar.i()));
                case STRING:
                    return new gk(hxVar.h());
                case NULL:
                    hxVar.j();
                    return gh.a;
                case BEGIN_ARRAY:
                    gcVar = new gc();
                    hxVar.a();
                    while (hxVar.e()) {
                        gcVar.a(a(hxVar));
                    }
                    hxVar.b();
                    return gcVar;
                case BEGIN_OBJECT:
                    gcVar = new gi();
                    hxVar.c();
                    while (hxVar.e()) {
                        gcVar.a(hxVar.g(), a(hxVar));
                    }
                    hxVar.d();
                    return gcVar;
                default:
                    throw new IllegalArgumentException();
            }
        }

        public void a(hz hzVar, gf gfVar) throws IOException {
            if (gfVar != null) {
                if (!gfVar.j()) {
                    if (gfVar.i()) {
                        gfVar = gfVar.m();
                        if (gfVar.p()) {
                            hzVar.a(gfVar.a());
                            return;
                        } else if (gfVar.o()) {
                            hzVar.a(gfVar.f());
                            return;
                        } else {
                            hzVar.b(gfVar.b());
                            return;
                        }
                    } else if (gfVar.g()) {
                        hzVar.b();
                        gfVar = gfVar.l().iterator();
                        while (gfVar.hasNext()) {
                            a(hzVar, (gf) gfVar.next());
                        }
                        hzVar.c();
                        return;
                    } else if (gfVar.h()) {
                        hzVar.d();
                        for (Entry entry : gfVar.k().o()) {
                            hzVar.a((String) entry.getKey());
                            a(hzVar, (gf) entry.getValue());
                        }
                        hzVar.e();
                        return;
                    } else {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Couldn't write ");
                        stringBuilder.append(gfVar.getClass());
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                }
            }
            hzVar.f();
        }

        public /* synthetic */ Object read(hx hxVar) throws IOException {
            return a(hxVar);
        }

        public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
            a(hzVar, (gf) obj);
        }
    };
    public static final gq Y = b(gf.class, X);
    public static final gq Z = new gq() {
        public <T> gp<T> a(fz fzVar, hw<T> hwVar) {
            fzVar = hwVar.a();
            if (Enum.class.isAssignableFrom(fzVar) != null) {
                if (fzVar != Enum.class) {
                    if (fzVar.isEnum() == null) {
                        fzVar = fzVar.getSuperclass();
                    }
                    return new a(fzVar);
                }
            }
            return null;
        }
    };
    public static final gp<Class> a = new gp<Class>() {
        public void a(hz hzVar, Class cls) throws IOException {
            if (cls == null) {
                hzVar.f();
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Attempted to serialize java.lang.Class: ");
            stringBuilder.append(cls.getName());
            stringBuilder.append(". Forgot to register a type adapter?");
            throw new UnsupportedOperationException(stringBuilder.toString());
        }

        public Class a(hx hxVar) throws IOException {
            if (hxVar.f() == hy.NULL) {
                hxVar.j();
                return null;
            }
            throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
        }

        public /* synthetic */ Object read(hx hxVar) throws IOException {
            return a(hxVar);
        }

        public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
            a(hzVar, (Class) obj);
        }
    };
    public static final gq b = a(Class.class, a);
    public static final gp<BitSet> c = new gp<BitSet>() {
        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.util.BitSet a(com.google.ads.interactivemedia.v3.internal.hx r7) throws java.io.IOException {
            /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r6 = this;
            r0 = r7.f();
            r1 = com.google.ads.interactivemedia.v3.internal.hy.NULL;
            if (r0 != r1) goto L_0x000d;
        L_0x0008:
            r7.j();
            r7 = 0;
            return r7;
        L_0x000d:
            r0 = new java.util.BitSet;
            r0.<init>();
            r7.a();
            r1 = r7.f();
            r2 = 0;
            r3 = 0;
        L_0x001b:
            r4 = com.google.ads.interactivemedia.v3.internal.hy.END_ARRAY;
            if (r1 == r4) goto L_0x007d;
        L_0x001f:
            r4 = com.google.ads.interactivemedia.v3.internal.hu.AnonymousClass30.a;
            r5 = r1.ordinal();
            r4 = r4[r5];
            r5 = 1;
            switch(r4) {
                case 1: goto L_0x006b;
                case 2: goto L_0x0066;
                case 3: goto L_0x0042;
                default: goto L_0x002b;
            };
        L_0x002b:
            r7 = new com.google.ads.interactivemedia.v3.internal.gn;
            r0 = new java.lang.StringBuilder;
            r0.<init>();
            r2 = "Invalid bitset value type: ";
            r0.append(r2);
            r0.append(r1);
            r0 = r0.toString();
            r7.<init>(r0);
            throw r7;
        L_0x0042:
            r1 = r7.h();
            r4 = java.lang.Integer.parseInt(r1);	 Catch:{ NumberFormatException -> 0x004f }
            if (r4 == 0) goto L_0x004d;
        L_0x004c:
            goto L_0x0071;
        L_0x004d:
            r5 = 0;
            goto L_0x0071;
        L_0x004f:
            r7 = new com.google.ads.interactivemedia.v3.internal.gn;
            r0 = new java.lang.StringBuilder;
            r0.<init>();
            r2 = "Error: Expecting: bitset number value (1, 0), Found: ";
            r0.append(r2);
            r0.append(r1);
            r0 = r0.toString();
            r7.<init>(r0);
            throw r7;
        L_0x0066:
            r5 = r7.i();
            goto L_0x0071;
        L_0x006b:
            r1 = r7.m();
            if (r1 == 0) goto L_0x004d;
        L_0x0071:
            if (r5 == 0) goto L_0x0076;
        L_0x0073:
            r0.set(r3);
        L_0x0076:
            r3 = r3 + 1;
            r1 = r7.f();
            goto L_0x001b;
        L_0x007d:
            r7.b();
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.hu.12.a(com.google.ads.interactivemedia.v3.internal.hx):java.util.BitSet");
        }

        public void a(hz hzVar, BitSet bitSet) throws IOException {
            if (bitSet == null) {
                hzVar.f();
                return;
            }
            hzVar.b();
            for (int i = 0; i < bitSet.length(); i++) {
                hzVar.a((long) bitSet.get(i));
            }
            hzVar.c();
        }

        public /* synthetic */ Object read(hx hxVar) throws IOException {
            return a(hxVar);
        }

        public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
            a(hzVar, (BitSet) obj);
        }
    };
    public static final gq d = a(BitSet.class, c);
    public static final gp<Boolean> e = new gp<Boolean>() {
        public Boolean a(hx hxVar) throws IOException {
            if (hxVar.f() == hy.NULL) {
                hxVar.j();
                return null;
            } else if (hxVar.f() == hy.STRING) {
                return Boolean.valueOf(Boolean.parseBoolean(hxVar.h()));
            } else {
                return Boolean.valueOf(hxVar.i());
            }
        }

        public void a(hz hzVar, Boolean bool) throws IOException {
            hzVar.a(bool);
        }

        public /* synthetic */ Object read(hx hxVar) throws IOException {
            return a(hxVar);
        }

        public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
            a(hzVar, (Boolean) obj);
        }
    };
    public static final gp<Boolean> f = new gp<Boolean>() {
        public Boolean a(hx hxVar) throws IOException {
            if (hxVar.f() != hy.NULL) {
                return Boolean.valueOf(hxVar.h());
            }
            hxVar.j();
            return null;
        }

        public void a(hz hzVar, Boolean bool) throws IOException {
            hzVar.b(bool == null ? "null" : bool.toString());
        }

        public /* synthetic */ Object read(hx hxVar) throws IOException {
            return a(hxVar);
        }

        public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
            a(hzVar, (Boolean) obj);
        }
    };
    public static final gq g = a(Boolean.TYPE, Boolean.class, e);
    public static final gp<Number> h = new gp<Number>() {
        public Number a(hx hxVar) throws IOException {
            if (hxVar.f() == hy.NULL) {
                hxVar.j();
                return null;
            }
            try {
                return Byte.valueOf((byte) hxVar.m());
            } catch (Throwable e) {
                throw new gn(e);
            }
        }

        public void a(hz hzVar, Number number) throws IOException {
            hzVar.a(number);
        }

        public /* synthetic */ Object read(hx hxVar) throws IOException {
            return a(hxVar);
        }

        public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
            a(hzVar, (Number) obj);
        }
    };
    public static final gq i = a(Byte.TYPE, Byte.class, h);
    public static final gp<Number> j = new gp<Number>() {
        public Number a(hx hxVar) throws IOException {
            if (hxVar.f() == hy.NULL) {
                hxVar.j();
                return null;
            }
            try {
                return Short.valueOf((short) hxVar.m());
            } catch (Throwable e) {
                throw new gn(e);
            }
        }

        public void a(hz hzVar, Number number) throws IOException {
            hzVar.a(number);
        }

        public /* synthetic */ Object read(hx hxVar) throws IOException {
            return a(hxVar);
        }

        public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
            a(hzVar, (Number) obj);
        }
    };
    public static final gq k = a(Short.TYPE, Short.class, j);
    public static final gp<Number> l = new gp<Number>() {
        public Number a(hx hxVar) throws IOException {
            if (hxVar.f() == hy.NULL) {
                hxVar.j();
                return null;
            }
            try {
                return Integer.valueOf(hxVar.m());
            } catch (Throwable e) {
                throw new gn(e);
            }
        }

        public void a(hz hzVar, Number number) throws IOException {
            hzVar.a(number);
        }

        public /* synthetic */ Object read(hx hxVar) throws IOException {
            return a(hxVar);
        }

        public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
            a(hzVar, (Number) obj);
        }
    };
    public static final gq m = a(Integer.TYPE, Integer.class, l);
    public static final gp<AtomicInteger> n = new gp<AtomicInteger>() {
        public AtomicInteger a(hx hxVar) throws IOException {
            try {
                return new AtomicInteger(hxVar.m());
            } catch (Throwable e) {
                throw new gn(e);
            }
        }

        public void a(hz hzVar, AtomicInteger atomicInteger) throws IOException {
            hzVar.a((long) atomicInteger.get());
        }

        public /* synthetic */ Object read(hx hxVar) throws IOException {
            return a(hxVar);
        }

        public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
            a(hzVar, (AtomicInteger) obj);
        }
    }.nullSafe();
    public static final gq o = a(AtomicInteger.class, n);
    public static final gp<AtomicBoolean> p = new gp<AtomicBoolean>() {
        public AtomicBoolean a(hx hxVar) throws IOException {
            return new AtomicBoolean(hxVar.i());
        }

        public void a(hz hzVar, AtomicBoolean atomicBoolean) throws IOException {
            hzVar.a(atomicBoolean.get());
        }

        public /* synthetic */ Object read(hx hxVar) throws IOException {
            return a(hxVar);
        }

        public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
            a(hzVar, (AtomicBoolean) obj);
        }
    }.nullSafe();
    public static final gq q = a(AtomicBoolean.class, p);
    public static final gp<AtomicIntegerArray> r = new gp<AtomicIntegerArray>() {
        public AtomicIntegerArray a(hx hxVar) throws IOException {
            List arrayList = new ArrayList();
            hxVar.a();
            while (hxVar.e()) {
                try {
                    arrayList.add(Integer.valueOf(hxVar.m()));
                } catch (Throwable e) {
                    throw new gn(e);
                }
            }
            hxVar.b();
            hxVar = arrayList.size();
            AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(hxVar);
            for (int i = 0; i < hxVar; i++) {
                atomicIntegerArray.set(i, ((Integer) arrayList.get(i)).intValue());
            }
            return atomicIntegerArray;
        }

        public void a(hz hzVar, AtomicIntegerArray atomicIntegerArray) throws IOException {
            hzVar.b();
            int length = atomicIntegerArray.length();
            for (int i = 0; i < length; i++) {
                hzVar.a((long) atomicIntegerArray.get(i));
            }
            hzVar.c();
        }

        public /* synthetic */ Object read(hx hxVar) throws IOException {
            return a(hxVar);
        }

        public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
            a(hzVar, (AtomicIntegerArray) obj);
        }
    }.nullSafe();
    public static final gq s = a(AtomicIntegerArray.class, r);
    public static final gp<Number> t = new gp<Number>() {
        public Number a(hx hxVar) throws IOException {
            if (hxVar.f() == hy.NULL) {
                hxVar.j();
                return null;
            }
            try {
                return Long.valueOf(hxVar.l());
            } catch (Throwable e) {
                throw new gn(e);
            }
        }

        public void a(hz hzVar, Number number) throws IOException {
            hzVar.a(number);
        }

        public /* synthetic */ Object read(hx hxVar) throws IOException {
            return a(hxVar);
        }

        public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
            a(hzVar, (Number) obj);
        }
    };
    public static final gp<Number> u = new gp<Number>() {
        public Number a(hx hxVar) throws IOException {
            if (hxVar.f() != hy.NULL) {
                return Float.valueOf((float) hxVar.k());
            }
            hxVar.j();
            return null;
        }

        public void a(hz hzVar, Number number) throws IOException {
            hzVar.a(number);
        }

        public /* synthetic */ Object read(hx hxVar) throws IOException {
            return a(hxVar);
        }

        public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
            a(hzVar, (Number) obj);
        }
    };
    public static final gp<Number> v = new gp<Number>() {
        public Number a(hx hxVar) throws IOException {
            if (hxVar.f() != hy.NULL) {
                return Double.valueOf(hxVar.k());
            }
            hxVar.j();
            return null;
        }

        public void a(hz hzVar, Number number) throws IOException {
            hzVar.a(number);
        }

        public /* synthetic */ Object read(hx hxVar) throws IOException {
            return a(hxVar);
        }

        public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
            a(hzVar, (Number) obj);
        }
    };
    public static final gp<Number> w = new gp<Number>() {
        public Number a(hx hxVar) throws IOException {
            hy f = hxVar.f();
            int i = AnonymousClass30.a[f.ordinal()];
            if (i == 1) {
                return new hb(hxVar.h());
            }
            if (i == 4) {
                hxVar.j();
                return null;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Expecting number, got: ");
            stringBuilder.append(f);
            throw new gn(stringBuilder.toString());
        }

        public void a(hz hzVar, Number number) throws IOException {
            hzVar.a(number);
        }

        public /* synthetic */ Object read(hx hxVar) throws IOException {
            return a(hxVar);
        }

        public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
            a(hzVar, (Number) obj);
        }
    };
    public static final gq x = a(Number.class, w);
    public static final gp<Character> y = new gp<Character>() {
        public Character a(hx hxVar) throws IOException {
            if (hxVar.f() == hy.NULL) {
                hxVar.j();
                return null;
            }
            hxVar = hxVar.h();
            if (hxVar.length() == 1) {
                return Character.valueOf(hxVar.charAt(0));
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Expecting character, got: ");
            stringBuilder.append(hxVar);
            throw new gn(stringBuilder.toString());
        }

        public void a(hz hzVar, Character ch) throws IOException {
            hzVar.b(ch == null ? null : String.valueOf(ch));
        }

        public /* synthetic */ Object read(hx hxVar) throws IOException {
            return a(hxVar);
        }

        public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
            a(hzVar, (Character) obj);
        }
    };
    public static final gq z = a(Character.TYPE, Character.class, y);

    /* compiled from: IMASDK */
    private static final class a<T extends Enum<T>> extends gp<T> {
        private final Map<String, T> a = new HashMap();
        private final Map<T, String> b = new HashMap();

        public a(Class<T> cls) {
            try {
                for (Enum enumR : (Enum[]) cls.getEnumConstants()) {
                    Object name = enumR.name();
                    gt gtVar = (gt) cls.getField(name).getAnnotation(gt.class);
                    if (gtVar != null) {
                        name = gtVar.a();
                        for (Object put : gtVar.b()) {
                            this.a.put(put, enumR);
                        }
                    }
                    this.a.put(name, enumR);
                    this.b.put(enumR, name);
                }
            } catch (Class<T> cls2) {
                throw new AssertionError(cls2);
            }
        }

        public T a(hx hxVar) throws IOException {
            if (hxVar.f() != hy.NULL) {
                return (Enum) this.a.get(hxVar.h());
            }
            hxVar.j();
            return null;
        }

        public void a(hz hzVar, T t) throws IOException {
            hzVar.b(t == null ? null : (String) this.b.get(t));
        }

        public /* synthetic */ Object read(hx hxVar) throws IOException {
            return a(hxVar);
        }

        public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
            a(hzVar, (Enum) obj);
        }
    }

    public static <TT> gq a(final hw<TT> hwVar, final gp<TT> gpVar) {
        return new gq() {
            public <T> gp<T> a(fz fzVar, hw<T> hwVar) {
                return hwVar.equals(hwVar) != null ? gpVar : null;
            }
        };
    }

    public static <TT> gq a(final Class<TT> cls, final gp<TT> gpVar) {
        return new gq() {
            public <T> gp<T> a(fz fzVar, hw<T> hwVar) {
                return hwVar.a() == cls ? gpVar : null;
            }

            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Factory[type=");
                stringBuilder.append(cls.getName());
                stringBuilder.append(",adapter=");
                stringBuilder.append(gpVar);
                stringBuilder.append("]");
                return stringBuilder.toString();
            }
        };
    }

    public static <TT> gq a(final Class<TT> cls, final Class<TT> cls2, final gp<? super TT> gpVar) {
        return new gq() {
            public <T> gp<T> a(fz fzVar, hw<T> hwVar) {
                fzVar = hwVar.a();
                if (fzVar != cls) {
                    if (fzVar != cls2) {
                        return null;
                    }
                }
                return gpVar;
            }

            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Factory[type=");
                stringBuilder.append(cls2.getName());
                stringBuilder.append("+");
                stringBuilder.append(cls.getName());
                stringBuilder.append(",adapter=");
                stringBuilder.append(gpVar);
                stringBuilder.append("]");
                return stringBuilder.toString();
            }
        };
    }

    public static <TT> gq b(final Class<TT> cls, final Class<? extends TT> cls2, final gp<? super TT> gpVar) {
        return new gq() {
            public <T> gp<T> a(fz fzVar, hw<T> hwVar) {
                fzVar = hwVar.a();
                if (fzVar != cls) {
                    if (fzVar != cls2) {
                        return null;
                    }
                }
                return gpVar;
            }

            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Factory[type=");
                stringBuilder.append(cls.getName());
                stringBuilder.append("+");
                stringBuilder.append(cls2.getName());
                stringBuilder.append(",adapter=");
                stringBuilder.append(gpVar);
                stringBuilder.append("]");
                return stringBuilder.toString();
            }
        };
    }

    public static <T1> gq b(final Class<T1> cls, final gp<T1> gpVar) {
        return new gq() {
            public <T2> gp<T2> a(fz fzVar, hw<T2> hwVar) {
                fzVar = hwVar.a();
                if (cls.isAssignableFrom(fzVar) == null) {
                    return null;
                }
                return new gp<T1>(this) {
                    final /* synthetic */ AnonymousClass29 b;

                    public void write(hz hzVar, T1 t1) throws IOException {
                        gpVar.write(hzVar, t1);
                    }

                    public T1 read(hx hxVar) throws IOException {
                        hxVar = gpVar.read(hxVar);
                        if (hxVar != null) {
                            if (!fzVar.isInstance(hxVar)) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Expected a ");
                                stringBuilder.append(fzVar.getName());
                                stringBuilder.append(" but was ");
                                stringBuilder.append(hxVar.getClass().getName());
                                throw new gn(stringBuilder.toString());
                            }
                        }
                        return hxVar;
                    }
                };
            }

            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Factory[typeHierarchy=");
                stringBuilder.append(cls.getName());
                stringBuilder.append(",adapter=");
                stringBuilder.append(gpVar);
                stringBuilder.append("]");
                return stringBuilder.toString();
            }
        };
    }
}
