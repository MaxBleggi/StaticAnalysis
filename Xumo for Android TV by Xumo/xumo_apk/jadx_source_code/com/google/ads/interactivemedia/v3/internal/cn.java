package com.google.ads.interactivemedia.v3.internal;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/* compiled from: IMASDK */
final class cn extends co {
    public cn(ck ckVar) {
        super(ckVar);
    }

    protected boolean a(fp fpVar) {
        return true;
    }

    protected void a(fp fpVar, long j) throws bl {
        if (b(fpVar) == 2) {
            if ("onMetaData".equals(e(fpVar)) != null) {
                if (b(fpVar) == 8) {
                    fpVar = h(fpVar);
                    if (fpVar.containsKey("duration") != null) {
                        fpVar = ((Double) fpVar.get("duration")).doubleValue();
                        if (fpVar > 0.0d) {
                            a((long) (long) (fpVar * 1000000.0d));
                        }
                    }
                    return;
                }
                throw new bl();
            }
            return;
        }
        throw new bl();
    }

    private static int b(fp fpVar) {
        return fpVar.f();
    }

    private static Boolean c(fp fpVar) {
        boolean z = true;
        if (fpVar.f() != 1) {
            z = false;
        }
        return Boolean.valueOf(z);
    }

    private static Double d(fp fpVar) {
        return Double.valueOf(Double.longBitsToDouble(fpVar.o()));
    }

    private static String e(fp fpVar) {
        int g = fpVar.g();
        int d = fpVar.d();
        fpVar.d(g);
        return new String(fpVar.a, d, g);
    }

    private static ArrayList<Object> f(fp fpVar) {
        int s = fpVar.s();
        ArrayList<Object> arrayList = new ArrayList(s);
        for (int i = 0; i < s; i++) {
            arrayList.add(a(fpVar, b(fpVar)));
        }
        return arrayList;
    }

    private static HashMap<String, Object> g(fp fpVar) {
        HashMap<String, Object> hashMap = new HashMap();
        while (true) {
            String e = e(fpVar);
            int b = b(fpVar);
            if (b == 9) {
                return hashMap;
            }
            hashMap.put(e, a(fpVar, b));
        }
    }

    private static HashMap<String, Object> h(fp fpVar) {
        int s = fpVar.s();
        HashMap<String, Object> hashMap = new HashMap(s);
        for (int i = 0; i < s; i++) {
            hashMap.put(e(fpVar), a(fpVar, b(fpVar)));
        }
        return hashMap;
    }

    private static Date i(fp fpVar) {
        Date date = new Date((long) d(fpVar).doubleValue());
        fpVar.d(2);
        return date;
    }

    private static Object a(fp fpVar, int i) {
        if (i == 8) {
            return h(fpVar);
        }
        switch (i) {
            case 0:
                return d(fpVar);
            case 1:
                return c(fpVar);
            case 2:
                return e(fpVar);
            case 3:
                return g(fpVar);
            default:
                switch (i) {
                    case 10:
                        return f(fpVar);
                    case 11:
                        return i(fpVar);
                    default:
                        return null;
                }
        }
    }
}
