package com.google.ads.interactivemedia.v3.internal;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.LinkedBlockingDeque;

/* compiled from: IMASDK */
final class ci {
    private final eq a;
    private final int b;
    private final a c = new a();
    private final LinkedBlockingDeque<ep> d = new LinkedBlockingDeque();
    private final b e = new b();
    private final fp f = new fp(32);
    private long g;
    private long h;
    private ep i;
    private int j = this.b;

    /* compiled from: IMASDK */
    private static final class a {
        private int a = 1000;
        private long[] b = new long[this.a];
        private int[] c = new int[this.a];
        private int[] d = new int[this.a];
        private long[] e = new long[this.a];
        private byte[][] f = new byte[this.a][];
        private int g;
        private int h;
        private int i;
        private int j;

        public void a() {
            this.h = 0;
            this.i = 0;
            this.j = 0;
            this.g = 0;
        }

        public synchronized boolean a(bm bmVar, b bVar) {
            if (this.g == 0) {
                return null;
            }
            bmVar.e = this.e[this.i];
            bmVar.c = this.c[this.i];
            bmVar.d = this.d[this.i];
            bVar.a = this.b[this.i];
            bVar.b = this.f[this.i];
            return true;
        }

        public synchronized long b() {
            long j;
            this.g--;
            int i = this.i;
            this.i = i + 1;
            this.h++;
            if (this.i == this.a) {
                this.i = 0;
            }
            if (this.g > 0) {
                j = this.b[this.i];
            } else {
                j = ((long) this.c[i]) + this.b[i];
            }
            return j;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public synchronized long a(long r10) {
            /*
            r9 = this;
            monitor-enter(r9);
            r0 = r9.g;	 Catch:{ all -> 0x006b }
            r1 = -1;
            if (r0 == 0) goto L_0x0069;
        L_0x0007:
            r0 = r9.e;	 Catch:{ all -> 0x006b }
            r3 = r9.i;	 Catch:{ all -> 0x006b }
            r3 = r0[r3];	 Catch:{ all -> 0x006b }
            r0 = (r10 > r3 ? 1 : (r10 == r3 ? 0 : -1));
            if (r0 >= 0) goto L_0x0012;
        L_0x0011:
            goto L_0x0069;
        L_0x0012:
            r0 = r9.j;	 Catch:{ all -> 0x006b }
            if (r0 != 0) goto L_0x0019;
        L_0x0016:
            r0 = r9.a;	 Catch:{ all -> 0x006b }
            goto L_0x001b;
        L_0x0019:
            r0 = r9.j;	 Catch:{ all -> 0x006b }
        L_0x001b:
            r0 = r0 + -1;
            r3 = r9.e;	 Catch:{ all -> 0x006b }
            r4 = r3[r0];	 Catch:{ all -> 0x006b }
            r0 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1));
            if (r0 <= 0) goto L_0x0027;
        L_0x0025:
            monitor-exit(r9);
            return r1;
        L_0x0027:
            r0 = 0;
            r3 = r9.i;	 Catch:{ all -> 0x006b }
            r4 = -1;
            r0 = -1;
            r5 = 0;
        L_0x002d:
            r6 = r9.j;	 Catch:{ all -> 0x006b }
            if (r3 == r6) goto L_0x004b;
        L_0x0031:
            r6 = r9.e;	 Catch:{ all -> 0x006b }
            r7 = r6[r3];	 Catch:{ all -> 0x006b }
            r6 = (r7 > r10 ? 1 : (r7 == r10 ? 0 : -1));
            if (r6 <= 0) goto L_0x003a;
        L_0x0039:
            goto L_0x004b;
        L_0x003a:
            r6 = r9.d;	 Catch:{ all -> 0x006b }
            r6 = r6[r3];	 Catch:{ all -> 0x006b }
            r6 = r6 & 1;
            if (r6 == 0) goto L_0x0043;
        L_0x0042:
            r0 = r5;
        L_0x0043:
            r3 = r3 + 1;
            r6 = r9.a;	 Catch:{ all -> 0x006b }
            r3 = r3 % r6;
            r5 = r5 + 1;
            goto L_0x002d;
        L_0x004b:
            if (r0 != r4) goto L_0x004f;
        L_0x004d:
            monitor-exit(r9);
            return r1;
        L_0x004f:
            r10 = r9.g;	 Catch:{ all -> 0x006b }
            r10 = r10 - r0;
            r9.g = r10;	 Catch:{ all -> 0x006b }
            r10 = r9.i;	 Catch:{ all -> 0x006b }
            r10 = r10 + r0;
            r11 = r9.a;	 Catch:{ all -> 0x006b }
            r10 = r10 % r11;
            r9.i = r10;	 Catch:{ all -> 0x006b }
            r10 = r9.h;	 Catch:{ all -> 0x006b }
            r10 = r10 + r0;
            r9.h = r10;	 Catch:{ all -> 0x006b }
            r10 = r9.b;	 Catch:{ all -> 0x006b }
            r11 = r9.i;	 Catch:{ all -> 0x006b }
            r0 = r10[r11];	 Catch:{ all -> 0x006b }
            monitor-exit(r9);
            return r0;
        L_0x0069:
            monitor-exit(r9);
            return r1;
        L_0x006b:
            r10 = move-exception;
            monitor-exit(r9);
            throw r10;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.ci.a.a(long):long");
        }

        public synchronized void a(long j, int i, long j2, int i2, byte[] bArr) {
            this.e[this.j] = j;
            this.b[this.j] = j2;
            this.c[this.j] = i2;
            this.d[this.j] = i;
            this.f[this.j] = bArr;
            this.g++;
            if (this.g == this.a) {
                j = this.a + 1000;
                Object obj = new long[j];
                j2 = new long[j];
                Object obj2 = new int[j];
                i2 = new int[j];
                bArr = new byte[j][];
                int i3 = this.a - this.i;
                System.arraycopy(this.b, this.i, obj, 0, i3);
                System.arraycopy(this.e, this.i, j2, 0, i3);
                System.arraycopy(this.d, this.i, obj2, 0, i3);
                System.arraycopy(this.c, this.i, i2, 0, i3);
                System.arraycopy(this.f, this.i, bArr, 0, i3);
                int i4 = this.i;
                System.arraycopy(this.b, 0, obj, i3, i4);
                System.arraycopy(this.e, 0, j2, i3, i4);
                System.arraycopy(this.d, 0, obj2, i3, i4);
                System.arraycopy(this.c, 0, i2, i3, i4);
                System.arraycopy(this.f, 0, bArr, i3, i4);
                this.b = obj;
                this.e = j2;
                this.d = obj2;
                this.c = i2;
                this.f = bArr;
                this.i = 0;
                this.j = this.a;
                this.g = this.a;
                this.a = j;
            } else {
                this.j++;
                if (this.j == this.a) {
                    this.j = 0;
                }
            }
        }
    }

    /* compiled from: IMASDK */
    private static final class b {
        public long a;
        public byte[] b;

        private b() {
        }
    }

    public ci(eq eqVar) {
        this.a = eqVar;
        this.b = eqVar.b();
    }

    public void a() {
        this.c.a();
        this.a.a((ep[]) this.d.toArray(new ep[this.d.size()]));
        this.d.clear();
        this.g = 0;
        this.h = 0;
        this.i = null;
        this.j = this.b;
    }

    public boolean a(bm bmVar) {
        return this.c.a(bmVar, this.e);
    }

    public void b() {
        b(this.c.b());
    }

    public boolean a(long j) {
        j = this.c.a(j);
        if (j == -1) {
            return 0;
        }
        b(j);
        return 1;
    }

    public boolean b(bm bmVar) {
        if (!this.c.a(bmVar, this.e)) {
            return null;
        }
        if (bmVar.a()) {
            a(bmVar, this.e);
        }
        bmVar.a(bmVar.c);
        a(this.e.a, bmVar.b, bmVar.c);
        b(this.c.b());
        return true;
    }

    private void a(bm bmVar, b bVar) {
        int g;
        int[] iArr;
        int[] iArr2;
        int i;
        long j = bVar.a;
        a(j, this.f.a, 1);
        j++;
        int i2 = 0;
        byte b = this.f.a[0];
        Object obj = (b & 128) != 0 ? 1 : null;
        int i3 = b & 127;
        if (bmVar.a.a == null) {
            bmVar.a.a = new byte[16];
        }
        a(j, bmVar.a.a, i3);
        j += (long) i3;
        if (obj != null) {
            a(j, this.f.a, 2);
            j += 2;
            this.f.c(0);
            g = this.f.g();
        } else {
            g = 1;
        }
        int[] iArr3 = bmVar.a.d;
        if (iArr3 != null) {
            if (iArr3.length < g) {
            }
            iArr = iArr3;
            iArr3 = bmVar.a.e;
            if (iArr3 != null) {
                if (iArr3.length < g) {
                }
                iArr2 = iArr3;
                if (obj == null) {
                    i3 = g * 6;
                    b(this.f, i3);
                    a(j, this.f.a, i3);
                    j += (long) i3;
                    this.f.c(0);
                    while (i2 < g) {
                        iArr[i2] = this.f.g();
                        iArr2[i2] = this.f.s();
                        i2++;
                    }
                } else {
                    iArr[0] = 0;
                    iArr2[0] = bmVar.c - ((int) (j - bVar.a));
                }
                bmVar.a.a(g, iArr, iArr2, bVar.b, bmVar.a.a, 1);
                i = (int) (j - bVar.a);
                bVar.a += (long) i;
                bmVar.c -= i;
            }
            iArr3 = new int[g];
            iArr2 = iArr3;
            if (obj == null) {
                iArr[0] = 0;
                iArr2[0] = bmVar.c - ((int) (j - bVar.a));
            } else {
                i3 = g * 6;
                b(this.f, i3);
                a(j, this.f.a, i3);
                j += (long) i3;
                this.f.c(0);
                while (i2 < g) {
                    iArr[i2] = this.f.g();
                    iArr2[i2] = this.f.s();
                    i2++;
                }
            }
            bmVar.a.a(g, iArr, iArr2, bVar.b, bmVar.a.a, 1);
            i = (int) (j - bVar.a);
            bVar.a += (long) i;
            bmVar.c -= i;
        }
        iArr3 = new int[g];
        iArr = iArr3;
        iArr3 = bmVar.a.e;
        if (iArr3 != null) {
            if (iArr3.length < g) {
            }
            iArr2 = iArr3;
            if (obj == null) {
                i3 = g * 6;
                b(this.f, i3);
                a(j, this.f.a, i3);
                j += (long) i3;
                this.f.c(0);
                while (i2 < g) {
                    iArr[i2] = this.f.g();
                    iArr2[i2] = this.f.s();
                    i2++;
                }
            } else {
                iArr[0] = 0;
                iArr2[0] = bmVar.c - ((int) (j - bVar.a));
            }
            bmVar.a.a(g, iArr, iArr2, bVar.b, bmVar.a.a, 1);
            i = (int) (j - bVar.a);
            bVar.a += (long) i;
            bmVar.c -= i;
        }
        iArr3 = new int[g];
        iArr2 = iArr3;
        if (obj == null) {
            iArr[0] = 0;
            iArr2[0] = bmVar.c - ((int) (j - bVar.a));
        } else {
            i3 = g * 6;
            b(this.f, i3);
            a(j, this.f.a, i3);
            j += (long) i3;
            this.f.c(0);
            while (i2 < g) {
                iArr[i2] = this.f.g();
                iArr2[i2] = this.f.s();
                i2++;
            }
        }
        bmVar.a.a(g, iArr, iArr2, bVar.b, bmVar.a.a, 1);
        i = (int) (j - bVar.a);
        bVar.a += (long) i;
        bmVar.c -= i;
    }

    private void a(long j, ByteBuffer byteBuffer, int i) {
        while (i > 0) {
            b(j);
            int i2 = (int) (j - this.g);
            int min = Math.min(i, this.b - i2);
            ep epVar = (ep) this.d.peek();
            byteBuffer.put(epVar.a, epVar.a(i2), min);
            j += (long) min;
            i -= min;
        }
    }

    private void a(long j, byte[] bArr, int i) {
        int i2 = 0;
        while (i2 < i) {
            b(j);
            int i3 = (int) (j - this.g);
            int min = Math.min(i - i2, this.b - i3);
            ep epVar = (ep) this.d.peek();
            System.arraycopy(epVar.a, epVar.a(i3), bArr, i2, min);
            j += (long) min;
            i2 += min;
        }
    }

    private void b(long j) {
        j = ((int) (j - this.g)) / this.b;
        for (int i = 0; i < j; i++) {
            this.a.a((ep) this.d.remove());
            this.g += (long) this.b;
        }
    }

    private static void b(fp fpVar, int i) {
        if (fpVar.c() < i) {
            fpVar.a(new byte[i], i);
        }
    }

    public long c() {
        return this.h;
    }

    public int a(cd cdVar, int i, boolean z) throws IOException, InterruptedException {
        cdVar = cdVar.a(this.i.a, this.i.a(this.j), a(i));
        if (cdVar != -1) {
            this.j += cdVar;
            this.h += (long) cdVar;
            return cdVar;
        } else if (z) {
            return -1;
        } else {
            throw new EOFException();
        }
    }

    public void a(fp fpVar, int i) {
        while (i > 0) {
            int a = a(i);
            fpVar.a(this.i.a, this.i.a(this.j), a);
            this.j += a;
            this.h += (long) a;
            i -= a;
        }
    }

    public void a(long j, int i, long j2, int i2, byte[] bArr) {
        this.c.a(j, i, j2, i2, bArr);
    }

    private int a(int i) {
        if (this.j == this.b) {
            this.j = 0;
            this.i = this.a.a();
            this.d.add(this.i);
        }
        return Math.min(i, this.b - this.j);
    }
}
