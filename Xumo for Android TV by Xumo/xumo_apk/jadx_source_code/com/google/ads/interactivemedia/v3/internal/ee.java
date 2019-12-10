package com.google.ads.interactivemedia.v3.internal;

import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import java.io.IOException;

/* compiled from: IMASDK */
public final class ee implements cc {
    private static final long d = ((long) ft.c("AC-3"));
    private static final long e = ((long) ft.c("EAC3"));
    private static final long f = ((long) ft.c("HEVC"));
    final SparseArray<d> a;
    final SparseBooleanArray b;
    dy c;
    private final ec g;
    private final int h;
    private final fp i;
    private final fo j;
    private final SparseIntArray k;
    private ce l;
    private boolean m;
    private int n;

    /* compiled from: IMASDK */
    private static abstract class d {
        private d() {
        }

        public abstract void a();

        public abstract void a(fp fpVar, boolean z, ce ceVar);
    }

    /* compiled from: IMASDK */
    private class a extends d {
        final /* synthetic */ ee a;
        private final fp b = new fp();
        private final fo c = new fo(new byte[4]);
        private int d;
        private int e;
        private int f;

        public a(ee eeVar) {
            this.a = eeVar;
            super();
        }

        public void a() {
        }

        public void a(fp fpVar, boolean z, ce ceVar) {
            int i = 0;
            if (z) {
                fpVar.d(fpVar.f());
                fpVar.a(this.c, 3);
                this.c.b(12);
                this.d = this.c.c(12);
                this.e = 0;
                this.f = ft.a(this.c.a, 0, 3, -1);
                this.b.a(this.d);
            }
            z = Math.min(fpVar.b(), this.d - this.e);
            fpVar.a(this.b.a, this.e, z);
            this.e += z;
            if (this.e >= this.d && ft.a(this.b.a, 0, this.d, this.f) == null) {
                this.b.d(true);
                fpVar = (this.d - 9) / 4;
                while (i < fpVar) {
                    this.b.a(this.c, 4);
                    int c = this.c.c(16);
                    this.c.b(3);
                    if (c == 0) {
                        this.c.b(13);
                    } else {
                        c = this.c.c(13);
                        this.a.a.put(c, new c(this.a, c));
                    }
                    i++;
                }
            }
        }
    }

    /* compiled from: IMASDK */
    private static final class b extends d {
        private final du a;
        private final ec b;
        private final fo c = new fo(new byte[10]);
        private int d = null;
        private int e;
        private boolean f;
        private boolean g;
        private boolean h;
        private int i;
        private int j;
        private boolean k;
        private long l;

        public b(du duVar, ec ecVar) {
            super();
            this.a = duVar;
            this.b = ecVar;
        }

        public void a() {
            this.d = 0;
            this.e = 0;
            this.h = false;
            this.a.a();
        }

        public void a(fp fpVar, boolean z, ce ceVar) {
            if (z) {
                switch (this.d) {
                    case false:
                    case true:
                        break;
                    case true:
                        Log.w("TsExtractor", "Unexpected start indicator reading extended header");
                        break;
                    case true:
                        if (!this.j) {
                            int i = this.j;
                            StringBuilder stringBuilder = new StringBuilder(59);
                            stringBuilder.append("Unexpected start indicator: expected ");
                            stringBuilder.append(i);
                            stringBuilder.append(" more bytes");
                            Log.w("TsExtractor", stringBuilder.toString());
                        }
                        this.a.b();
                        break;
                    default:
                        break;
                }
                a(1);
            }
            while (fpVar.b() <= false) {
                i = 0;
                switch (this.d) {
                    case false:
                        fpVar.d(fpVar.b());
                        break;
                    case true:
                        if (!a(fpVar, this.c.a, 9)) {
                            break;
                        }
                        if (b()) {
                            i = 2;
                        }
                        a(i);
                        break;
                    case true:
                        if (a(fpVar, this.c.a, Math.min(true, this.i)) && a(fpVar, (byte[]) false, this.i)) {
                            c();
                            this.a.a(this.l, this.k);
                            a(true);
                            break;
                        }
                    case true:
                        z = fpVar.b();
                        if (this.j != -1) {
                            i = z - this.j;
                        }
                        if (i > 0) {
                            z -= i;
                            fpVar.b(fpVar.d() + z);
                        }
                        this.a.a(fpVar);
                        if (this.j == -1) {
                            break;
                        }
                        this.j -= z;
                        if (!this.j) {
                            this.a.b();
                            a(1);
                            break;
                        }
                        break;
                    default:
                        break;
                }
            }
        }

        private void a(int i) {
            this.d = i;
            this.e = 0;
        }

        private boolean a(fp fpVar, byte[] bArr, int i) {
            int min = Math.min(fpVar.b(), i - this.e);
            boolean z = true;
            if (min <= 0) {
                return true;
            }
            if (bArr == null) {
                fpVar.d(min);
            } else {
                fpVar.a(bArr, this.e, min);
            }
            this.e += min;
            if (this.e != i) {
                z = false;
            }
            return z;
        }

        private boolean b() {
            this.c.a(0);
            int c = this.c.c(24);
            if (c != 1) {
                StringBuilder stringBuilder = new StringBuilder(41);
                stringBuilder.append("Unexpected start code prefix: ");
                stringBuilder.append(c);
                Log.w("TsExtractor", stringBuilder.toString());
                this.j = -1;
                return false;
            }
            this.c.b(8);
            c = this.c.c(16);
            this.c.b(5);
            this.k = this.c.b();
            this.c.b(2);
            this.f = this.c.b();
            this.g = this.c.b();
            this.c.b(6);
            this.i = this.c.c(8);
            if (c == 0) {
                this.j = -1;
            } else {
                this.j = ((c + 6) - 9) - this.i;
            }
            return true;
        }

        private void c() {
            this.c.a(0);
            this.l = -1;
            if (this.f) {
                this.c.b(4);
                long c = ((long) this.c.c(3)) << 30;
                this.c.b(1);
                c |= (long) (this.c.c(15) << 15);
                this.c.b(1);
                c |= (long) this.c.c(15);
                this.c.b(1);
                if (!this.h && this.g) {
                    this.c.b(4);
                    long c2 = ((long) this.c.c(3)) << 30;
                    this.c.b(1);
                    c2 |= (long) (this.c.c(15) << 15);
                    this.c.b(1);
                    c2 |= (long) this.c.c(15);
                    this.c.b(1);
                    this.b.a(c2);
                    this.h = true;
                }
                this.l = this.b.a(c);
            }
        }
    }

    /* compiled from: IMASDK */
    private class c extends d {
        final /* synthetic */ ee a;
        private final fo b = new fo(new byte[5]);
        private final fp c = new fp();
        private final int d;
        private int e;
        private int f;
        private int g;

        public c(ee eeVar, int i) {
            this.a = eeVar;
            super();
            this.d = i;
        }

        public void a() {
        }

        public void a(fp fpVar, boolean z, ce ceVar) {
            c cVar = this;
            fp fpVar2 = fpVar;
            ce ceVar2 = ceVar;
            int i = 3;
            int i2 = 12;
            if (z) {
                fpVar2.d(fpVar.f());
                fpVar2.a(cVar.b, 3);
                cVar.b.b(12);
                cVar.e = cVar.b.c(12);
                cVar.f = 0;
                cVar.g = ft.a(cVar.b.a, 0, 3, -1);
                cVar.c.a(cVar.e);
            }
            int min = Math.min(fpVar.b(), cVar.e - cVar.f);
            fpVar2.a(cVar.c.a, cVar.f, min);
            cVar.f += min;
            if (cVar.f >= cVar.e && ft.a(cVar.c.a, 0, cVar.e, cVar.g) == 0) {
                cVar.c.d(7);
                cVar.c.a(cVar.b, 2);
                cVar.b.b(4);
                int c = cVar.b.c(12);
                cVar.c.d(c);
                if ((cVar.a.h & 16) != 0 && cVar.a.c == null) {
                    cVar.a.c = new dy(ceVar2.d(21));
                }
                int i3 = ((cVar.e - 9) - c) - 4;
                while (i3 > 0) {
                    cVar.c.a(cVar.b, 5);
                    int c2 = cVar.b.c(8);
                    cVar.b.b(i);
                    int c3 = cVar.b.c(13);
                    cVar.b.b(4);
                    int c4 = cVar.b.c(i2);
                    if (c2 == 6) {
                        c2 = a(cVar.c, c4);
                    } else {
                        cVar.c.d(c4);
                    }
                    i3 -= c4 + 5;
                    c4 = (cVar.a.h & 16) != 0 ? c2 : c3;
                    if (!cVar.a.b.get(c4)) {
                        du duVar = null;
                        if (c2 != 15) {
                            if (c2 == 21) {
                                duVar = (cVar.a.h & 16) != 0 ? cVar.a.c : new dy(ceVar2.d(cVar.a.n = cVar.a.n + 1));
                            } else if (c2 != 27) {
                                if (c2 == 36) {
                                    duVar = new dx(ceVar2.d(c4), new ed(ceVar2.d(cVar.a.n = cVar.a.n + 1)));
                                } else if (c2 != TsExtractor.TS_STREAM_TYPE_E_AC3) {
                                    if (c2 != TsExtractor.TS_STREAM_TYPE_DTS) {
                                        switch (c2) {
                                            case 2:
                                                duVar = new dv(ceVar2.d(c4));
                                                break;
                                            case 3:
                                                duVar = new dz(ceVar2.d(c4));
                                                break;
                                            case 4:
                                                duVar = new dz(ceVar2.d(c4));
                                                break;
                                            default:
                                                switch (c2) {
                                                    case TsExtractor.TS_STREAM_TYPE_AC3 /*129*/:
                                                        duVar = new dq(ceVar2.d(c4), false);
                                                        break;
                                                    case TsExtractor.TS_STREAM_TYPE_HDMV_DTS /*130*/:
                                                        break;
                                                    default:
                                                        break;
                                                }
                                        }
                                    }
                                    duVar = new dt(ceVar2.d(c4));
                                } else {
                                    duVar = new dq(ceVar2.d(c4), true);
                                }
                            } else if ((cVar.a.h & 4) == 0) {
                                duVar = new dw(ceVar2.d(c4), new ed(ceVar2.d(cVar.a.n = cVar.a.n + 1)), (cVar.a.h & 1) != 0, (cVar.a.h & 8) != 0);
                            }
                        } else if ((cVar.a.h & 2) == 0) {
                            duVar = new ds(ceVar2.d(c4), new cb());
                        }
                        if (duVar != null) {
                            cVar.a.b.put(c4, true);
                            cVar.a.a.put(c3, new b(duVar, cVar.a.g));
                        }
                    }
                    i = 3;
                    i2 = 12;
                }
                if ((cVar.a.h & 16) == 0) {
                    cVar.a.a.remove(0);
                    cVar.a.a.remove(cVar.d);
                    ceVar.f();
                } else if (!cVar.a.m) {
                    ceVar.f();
                }
                cVar.a.m = true;
            }
        }

        private int a(fp fpVar, int i) {
            int d = fpVar.d() + i;
            i = -1;
            while (fpVar.d() < d) {
                int f = fpVar.f();
                int f2 = fpVar.f();
                if (f == 5) {
                    long k = fpVar.k();
                    if (k == ee.d) {
                        i = TsExtractor.TS_STREAM_TYPE_AC3;
                    } else if (k == ee.e) {
                        i = TsExtractor.TS_STREAM_TYPE_E_AC3;
                    } else if (k == ee.f) {
                        i = 36;
                    }
                    fpVar.c(d);
                    return i;
                }
                if (f == 106) {
                    i = TsExtractor.TS_STREAM_TYPE_AC3;
                } else if (f == 122) {
                    i = TsExtractor.TS_STREAM_TYPE_E_AC3;
                } else if (f == 123) {
                    i = TsExtractor.TS_STREAM_TYPE_DTS;
                }
                fpVar.d(f2);
            }
            fpVar.c(d);
            return i;
        }
    }

    public ee() {
        this(new ec(0));
    }

    public void c() {
    }

    public ee(ec ecVar) {
        this(ecVar, 0);
    }

    public ee(ec ecVar, int i) {
        this.g = ecVar;
        this.h = i;
        this.i = new fp(940);
        this.j = new fo(new byte[3]);
        this.a = new SparseArray();
        this.b = new SparseBooleanArray();
        this.k = new SparseIntArray();
        f();
    }

    public boolean a(cd cdVar) throws IOException, InterruptedException {
        byte[] bArr = this.i.a;
        cdVar.c(bArr, 0, 940);
        int i = 0;
        while (i < TsExtractor.TS_PACKET_SIZE) {
            int i2 = 0;
            while (i2 != 5) {
                if (bArr[(i2 * TsExtractor.TS_PACKET_SIZE) + i] != (byte) 71) {
                    i++;
                } else {
                    i2++;
                }
            }
            cdVar.b(i);
            return true;
        }
        return false;
    }

    public void a(ce ceVar) {
        this.l = ceVar;
        ceVar.a(cj.f);
    }

    public void b() {
        this.g.a();
        this.i.a();
        this.k.clear();
        f();
    }

    public int a(cd cdVar, ch chVar) throws IOException, InterruptedException {
        int b;
        byte[] bArr = this.i.a;
        if (940 - this.i.d() < TsExtractor.TS_PACKET_SIZE) {
            b = this.i.b();
            if (b > 0) {
                System.arraycopy(bArr, this.i.d(), bArr, 0, b);
            }
            this.i.a(bArr, b);
        }
        while (this.i.b() < TsExtractor.TS_PACKET_SIZE) {
            b = this.i.c();
            int a = cdVar.a(bArr, b, 940 - b);
            if (a == -1) {
                return -1;
            }
            this.i.b(b + a);
        }
        cdVar = this.i.c();
        b = this.i.d();
        while (b < cdVar && bArr[b] != (byte) 71) {
            b++;
        }
        this.i.c(b);
        b += TsExtractor.TS_PACKET_SIZE;
        if (b > cdVar) {
            return 0;
        }
        boolean z = true;
        this.i.d(1);
        this.i.a(this.j, 3);
        if (this.j.b() != null) {
            this.i.c(b);
            return 0;
        }
        Object obj;
        d dVar;
        chVar = this.j.b();
        this.j.b(1);
        a = this.j.c(13);
        this.j.b(2);
        boolean b2 = this.j.b();
        boolean b3 = this.j.b();
        int c = this.j.c(4);
        if ((this.h & 16) == 0) {
            int i = this.k.get(a, c - 1);
            this.k.put(a, c);
            if (i == c) {
                if (b3) {
                    this.i.c(b);
                    return 0;
                }
            } else if (c != (i + 1) % 16) {
                obj = 1;
                if (b2) {
                    this.i.d(this.i.f());
                }
                if (b3) {
                    dVar = (d) this.a.get(a);
                    if (dVar != null) {
                        if (obj != null) {
                            dVar.a();
                        }
                        this.i.b(b);
                        dVar.a(this.i, chVar, this.l);
                        if (this.i.d() <= b) {
                            z = false;
                        }
                        fe.b(z);
                        this.i.b(cdVar);
                    }
                }
                this.i.c(b);
                return 0;
            }
        }
        obj = null;
        if (b2) {
            this.i.d(this.i.f());
        }
        if (b3) {
            dVar = (d) this.a.get(a);
            if (dVar != null) {
                if (obj != null) {
                    dVar.a();
                }
                this.i.b(b);
                dVar.a(this.i, chVar, this.l);
                if (this.i.d() <= b) {
                    z = false;
                }
                fe.b(z);
                this.i.b(cdVar);
            }
        }
        this.i.c(b);
        return 0;
    }

    private void f() {
        this.b.clear();
        this.a.clear();
        this.a.put(0, new a(this));
        this.c = null;
        this.n = 8192;
    }
}
