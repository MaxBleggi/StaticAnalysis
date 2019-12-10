package com.google.ads.interactivemedia.v3.internal;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.IOException;

/* compiled from: IMASDK */
final class dc {
    private static final int[] a = new int[]{ft.c("isom"), ft.c("iso2"), ft.c("iso3"), ft.c("iso4"), ft.c("iso5"), ft.c("iso6"), ft.c("avc1"), ft.c("hvc1"), ft.c("hev1"), ft.c("mp41"), ft.c("mp42"), ft.c("3g2a"), ft.c("3g2b"), ft.c("3gr6"), ft.c("3gs6"), ft.c("3ge6"), ft.c("3gg6"), ft.c("M4V "), ft.c("M4A "), ft.c("f4v "), ft.c("kddi"), ft.c("M4VP"), ft.c("qt  "), ft.c("MSNV")};

    public static boolean a(cd cdVar) throws IOException, InterruptedException {
        return a(cdVar, true);
    }

    public static boolean b(cd cdVar) throws IOException, InterruptedException {
        return a(cdVar, false);
    }

    private static boolean a(cd cdVar, boolean z) throws IOException, InterruptedException {
        boolean z2;
        cd cdVar2 = cdVar;
        long d = cdVar.d();
        if (d == -1 || d > PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM) {
            d = PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
        }
        int i = (int) d;
        fp fpVar = new fp(64);
        boolean z3 = false;
        int i2 = 0;
        Object obj = null;
        while (i2 < i) {
            int i3;
            cdVar2.c(fpVar.a, 0, 8);
            fpVar.c(0);
            long k = fpVar.k();
            int m = fpVar.m();
            if (k == 1) {
                cdVar2.c(fpVar.a, 8, 8);
                k = fpVar.u();
                i3 = 16;
            } else {
                i3 = 8;
            }
            long j = (long) i3;
            if (k >= j) {
                i2 += i3;
                if (m != cv.B) {
                    if (m != cv.K) {
                        if (m != cv.M) {
                            int i4 = m;
                            if ((((long) i2) + k) - j >= ((long) i)) {
                                break;
                            }
                            int i5 = (int) (k - j);
                            i2 += i5;
                            if (i4 == cv.a) {
                                if (i5 < 8) {
                                    return false;
                                }
                                if (fpVar.e() < i5) {
                                    fpVar.a(new byte[i5], i5);
                                }
                                cdVar2.c(fpVar.a, 0, i5);
                                i5 /= 4;
                                for (m = 0; m < i5; m++) {
                                    if (m == 1) {
                                        fpVar.d(4);
                                    } else if (a(fpVar.m())) {
                                        obj = 1;
                                        break;
                                    }
                                }
                                if (obj == null) {
                                    return false;
                                }
                            } else if (i5 != 0) {
                                cdVar2.c(i5);
                            }
                        }
                    }
                    z2 = true;
                    break;
                }
            } else {
                return false;
            }
        }
        z2 = false;
        if (obj != null && z == r0) {
            z3 = true;
        }
        return z3;
    }

    private static boolean a(int i) {
        if ((i >>> 8) == ft.c("3gp")) {
            return true;
        }
        for (int i2 : a) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }
}
