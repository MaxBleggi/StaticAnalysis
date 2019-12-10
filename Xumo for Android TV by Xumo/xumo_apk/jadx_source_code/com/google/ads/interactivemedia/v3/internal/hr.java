package com.google.ads.interactivemedia.v3.internal;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/* compiled from: IMASDK */
public final class hr extends gp<Time> {
    public static final gq a = new gq() {
        public <T> gp<T> a(fz fzVar, hw<T> hwVar) {
            return hwVar.a() == Time.class ? new hr() : null;
        }
    };
    private final DateFormat b = new SimpleDateFormat("hh:mm:ss a");

    public synchronized Time a(hx hxVar) throws IOException {
        if (hxVar.f() == hy.NULL) {
            hxVar.j();
            return null;
        }
        try {
            return new Time(this.b.parse(hxVar.h()).getTime());
        } catch (Throwable e) {
            throw new gn(e);
        }
    }

    public synchronized void a(hz hzVar, Time time) throws IOException {
        hzVar.b(time == null ? null : this.b.format(time));
    }

    public /* synthetic */ Object read(hx hxVar) throws IOException {
        return a(hxVar);
    }

    public /* synthetic */ void write(hz hzVar, Object obj) throws IOException {
        a(hzVar, (Time) obj);
    }
}
