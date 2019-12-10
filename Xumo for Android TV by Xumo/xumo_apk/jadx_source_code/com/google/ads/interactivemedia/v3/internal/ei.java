package com.google.ads.interactivemedia.v3.internal;

import java.io.EOFException;
import java.io.IOException;
import java.util.Stack;

/* compiled from: IMASDK */
final class ei implements ej {
    private final byte[] a = new byte[8];
    private final Stack<a> b = new Stack();
    private final em c = new em();
    private ek d;
    private int e;
    private int f;
    private long g;

    /* compiled from: IMASDK */
    private static final class a {
        private final int a;
        private final long b;

        private a(int i, long j) {
            this.a = i;
            this.b = j;
        }
    }

    ei() {
    }

    public void a(ek ekVar) {
        this.d = ekVar;
    }

    public void a() {
        this.e = 0;
        this.b.clear();
        this.c.a();
    }

    public boolean a(cd cdVar) throws IOException, InterruptedException {
        fe.b(this.d != null);
        while (true) {
            if (this.b.isEmpty() || cdVar.c() < ((a) this.b.peek()).b) {
                if (this.e == 0) {
                    long a = this.c.a(cdVar, true, false, 4);
                    if (a == -2) {
                        a = b(cdVar);
                    }
                    if (a == -1) {
                        return false;
                    }
                    this.f = (int) a;
                    this.e = 1;
                }
                if (this.e == 1) {
                    this.g = this.c.a(cdVar, false, true, 8);
                    this.e = 2;
                }
                int a2 = this.d.a(this.f);
                long j;
                StringBuilder stringBuilder;
                switch (a2) {
                    case 0:
                        cdVar.b((int) this.g);
                        this.e = 0;
                    case 1:
                        long c = cdVar.c();
                        this.b.add(new a(this.f, this.g + c));
                        this.d.a(this.f, c, this.g);
                        this.e = 0;
                        return true;
                    case 2:
                        if (this.g <= 8) {
                            this.d.a(this.f, a(cdVar, (int) this.g));
                            this.e = 0;
                            return true;
                        }
                        j = this.g;
                        stringBuilder = new StringBuilder(42);
                        stringBuilder.append("Invalid integer size: ");
                        stringBuilder.append(j);
                        throw new bl(stringBuilder.toString());
                    case 3:
                        if (this.g <= 2147483647L) {
                            this.d.a(this.f, c(cdVar, (int) this.g));
                            this.e = 0;
                            return true;
                        }
                        j = this.g;
                        stringBuilder = new StringBuilder(41);
                        stringBuilder.append("String element size: ");
                        stringBuilder.append(j);
                        throw new bl(stringBuilder.toString());
                    case 4:
                        this.d.a(this.f, (int) this.g, cdVar);
                        this.e = 0;
                        return true;
                    case 5:
                        if (this.g != 4) {
                            if (this.g != 8) {
                                j = this.g;
                                stringBuilder = new StringBuilder(40);
                                stringBuilder.append("Invalid float size: ");
                                stringBuilder.append(j);
                                throw new bl(stringBuilder.toString());
                            }
                        }
                        this.d.a(this.f, b(cdVar, (int) this.g));
                        this.e = 0;
                        return true;
                    default:
                        StringBuilder stringBuilder2 = new StringBuilder(32);
                        stringBuilder2.append("Invalid element type ");
                        stringBuilder2.append(a2);
                        throw new bl(stringBuilder2.toString());
                }
            }
            this.d.c(((a) this.b.pop()).a);
            return true;
        }
    }

    private long b(cd cdVar) throws EOFException, IOException, InterruptedException {
        cdVar.a();
        while (true) {
            cdVar.c(this.a, 0, 4);
            int a = em.a(this.a[0]);
            if (a != -1 && a <= 4) {
                int a2 = (int) em.a(this.a, a, false);
                if (this.d.b(a2)) {
                    cdVar.b(a);
                    return (long) a2;
                }
            }
            cdVar.b(1);
        }
    }

    private long a(cd cdVar, int i) throws IOException, InterruptedException {
        int i2 = 0;
        cdVar.b(this.a, 0, i);
        long j = 0;
        while (i2 < i) {
            j = (j << 8) | ((long) (this.a[i2] & 255));
            i2++;
        }
        return j;
    }

    private double b(cd cdVar, int i) throws IOException, InterruptedException {
        long a = a(cdVar, i);
        if (i == 4) {
            return (double) Float.intBitsToFloat((int) a);
        }
        return Double.longBitsToDouble(a);
    }

    private String c(cd cdVar, int i) throws IOException, InterruptedException {
        if (i == 0) {
            return "";
        }
        byte[] bArr = new byte[i];
        cdVar.b(bArr, 0, i);
        return new String(bArr);
    }
}
