package com.google.ads.interactivemedia.v3.internal;

import java.io.EOFException;
import java.io.IOException;

/* compiled from: IMASDK */
final class dl {
    private static final int a = ft.c("OggS");

    /* compiled from: IMASDK */
    public static class a {
        public int a;
        public int b;
    }

    /* compiled from: IMASDK */
    public static final class b {
        public int a;
        public int b;
        public long c;
        public long d;
        public long e;
        public long f;
        public int g;
        public int h;
        public int i;
        public final int[] j = new int[255];

        public void a() {
            this.a = 0;
            this.b = 0;
            this.c = 0;
            this.d = 0;
            this.e = 0;
            this.f = 0;
            this.g = 0;
            this.h = 0;
            this.i = 0;
        }
    }

    public static int a(byte b, int i, int i2) {
        return (b >> i2) & (255 >>> (8 - i));
    }

    public static void a(cd cdVar) throws IOException, InterruptedException {
        byte[] bArr = new byte[2048];
        int length = bArr.length;
        while (true) {
            int i;
            if (cdVar.d() != -1 && cdVar.c() + ((long) r1) > cdVar.d()) {
                length = (int) (cdVar.d() - cdVar.c());
                if (length < 4) {
                    throw new EOFException();
                }
            }
            int i2 = 0;
            cdVar.b(bArr, 0, length, false);
            while (true) {
                i = length - 3;
                if (i2 >= i) {
                    break;
                } else if (bArr[i2] == (byte) 79 && bArr[i2 + 1] == (byte) 103 && bArr[i2 + 2] == (byte) 103 && bArr[i2 + 3] == (byte) 83) {
                    cdVar.b(i2);
                    return;
                } else {
                    i2++;
                }
            }
            cdVar.b(i);
        }
    }

    public static boolean a(cd cdVar, b bVar, fp fpVar, boolean z) throws IOException, InterruptedException {
        Object obj;
        fpVar.a();
        bVar.a();
        int i = 0;
        if (cdVar.d() != -1) {
            if (cdVar.d() - cdVar.b() < 27) {
                obj = null;
                if (obj != null) {
                    if (!cdVar.b(fpVar.a, 0, 27, true)) {
                        if (fpVar.k() != ((long) a)) {
                            bVar.a = fpVar.f();
                            if (bVar.a != 0) {
                                bVar.b = fpVar.f();
                                bVar.c = fpVar.p();
                                bVar.d = fpVar.l();
                                bVar.e = fpVar.l();
                                bVar.f = fpVar.l();
                                bVar.g = fpVar.f();
                                fpVar.a();
                                bVar.h = bVar.g + true;
                                cdVar.c(fpVar.a, 0, bVar.g);
                                while (i < bVar.g) {
                                    bVar.j[i] = fpVar.f();
                                    bVar.i += bVar.j[i];
                                    i++;
                                }
                                return true;
                            } else if (z) {
                                return false;
                            } else {
                                throw new bl("unsupported bit stream revision");
                            }
                        } else if (z) {
                            return false;
                        } else {
                            throw new bl("expected OggS capture pattern at begin of page");
                        }
                    }
                }
                if (z) {
                    return false;
                }
                throw new EOFException();
            }
        }
        obj = 1;
        if (obj != null) {
            if (!cdVar.b(fpVar.a, 0, 27, true)) {
                if (fpVar.k() != ((long) a)) {
                    bVar.a = fpVar.f();
                    if (bVar.a != 0) {
                        bVar.b = fpVar.f();
                        bVar.c = fpVar.p();
                        bVar.d = fpVar.l();
                        bVar.e = fpVar.l();
                        bVar.f = fpVar.l();
                        bVar.g = fpVar.f();
                        fpVar.a();
                        bVar.h = bVar.g + true;
                        cdVar.c(fpVar.a, 0, bVar.g);
                        while (i < bVar.g) {
                            bVar.j[i] = fpVar.f();
                            bVar.i += bVar.j[i];
                            i++;
                        }
                        return true;
                    } else if (z) {
                        return false;
                    } else {
                        throw new bl("unsupported bit stream revision");
                    }
                } else if (z) {
                    return false;
                } else {
                    throw new bl("expected OggS capture pattern at begin of page");
                }
            }
        }
        if (z) {
            return false;
        }
        throw new EOFException();
    }

    public static void a(b bVar, int i, a aVar) {
        aVar.b = 0;
        aVar.a = 0;
        while (aVar.b + i < bVar.g) {
            int[] iArr = bVar.j;
            int i2 = aVar.b;
            aVar.b = i2 + 1;
            int i3 = iArr[i2 + i];
            aVar.a += i3;
            if (i3 != 255) {
                return;
            }
        }
    }
}
