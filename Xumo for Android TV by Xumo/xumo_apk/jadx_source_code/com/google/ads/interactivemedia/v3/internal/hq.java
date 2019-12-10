package com.google.ads.interactivemedia.v3.internal;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/* compiled from: IMASDK */
public final class hq extends gp<Date> {
    public static final gq a = new gq() {
        public <T> gp<T> a(fz fzVar, hw<T> hwVar) {
            return hwVar.a() == Date.class ? new hq() : null;
        }
    };
    private final DateFormat b = new SimpleDateFormat("MMM d, yyyy");

    public synchronized Date a(hx hxVar) throws IOException {
        if (hxVar.f() == hy.NULL) {
            hxVar.j();
            return null;
        }
        try {
            return new Date(this.b.parse(hxVar.h()).getTime());
        } catch (Throwable e) {
            throw new gn(e);
        }
    }

    public synchronized void a(hz hzVar, Date date) throws IOException {
        hzVar.b(date == null ? null : this.b.format(date));
    }

    public /* synthetic */ Object read(hx hxVar) throws IOException {
        return a(hxVar);
    }

    public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
        a(hzVar, (Date) obj);
    }
}
