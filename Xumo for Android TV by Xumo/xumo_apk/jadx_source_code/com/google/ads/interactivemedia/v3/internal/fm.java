package com.google.ads.interactivemedia.v3.internal;

import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.exoplayer2.util.MimeTypes;

/* compiled from: IMASDK */
public final class fm {
    private static final String[] h = new String[]{MimeTypes.AUDIO_MPEG_L1, MimeTypes.AUDIO_MPEG_L2, MimeTypes.AUDIO_MPEG};
    private static final int[] i = new int[]{44100, 48000, 32000};
    private static final int[] j = new int[]{32, 64, 96, 128, 160, PsExtractor.AUDIO_STREAM, 224, 256, 288, 320, 352, 384, 416, 448};
    private static final int[] k = new int[]{32, 48, 56, 64, 80, 96, 112, 128, 144, 160, 176, PsExtractor.AUDIO_STREAM, 224, 256};
    private static final int[] l = new int[]{32, 48, 56, 64, 80, 96, 112, 128, 160, PsExtractor.AUDIO_STREAM, 224, 256, 320, 384};
    private static final int[] m = new int[]{32, 40, 48, 56, 64, 80, 96, 112, 128, 160, PsExtractor.AUDIO_STREAM, 224, 256, 320};
    private static final int[] n = new int[]{8, 16, 24, 32, 40, 48, 56, 64, 80, 96, 112, 128, 144, 160};
    public int a;
    public String b;
    public int c;
    public int d;
    public int e;
    public int f;
    public int g;

    public static int a(int i) {
        if ((i & -2097152) != -2097152) {
            return -1;
        }
        int i2 = (i >>> 19) & 3;
        if (i2 == 1) {
            return -1;
        }
        int i3 = (i >>> 17) & 3;
        if (i3 == 0) {
            return -1;
        }
        int i4 = (i >>> 12) & 15;
        if (i4 != 0) {
            if (i4 != 15) {
                int i5 = (i >>> 10) & 3;
                if (i5 == 3) {
                    return -1;
                }
                int i6 = i[i5];
                if (i2 == 2) {
                    i6 /= 2;
                } else if (i2 == 0) {
                    i6 /= 4;
                }
                i = (i >>> 9) & 1;
                if (i3 == 3) {
                    return ((((i2 == 3 ? j[i4 - 1] : k[i4 - 1]) * 12000) / i6) + i) * 4;
                }
                i4 = i2 == 3 ? i3 == 2 ? l[i4 - 1] : m[i4 - 1] : n[i4 - 1];
                i5 = 144000;
                if (i2 == 3) {
                    return ((i4 * 144000) / i6) + i;
                }
                if (i3 == 1) {
                    i5 = DefaultOggSeeker.MATCH_RANGE;
                }
                return ((i5 * i4) / i6) + i;
            }
        }
        return -1;
    }

    public static boolean a(int i, fm fmVar) {
        if ((i & -2097152) != -2097152) {
            return false;
        }
        int i2 = (i >>> 19) & 3;
        if (i2 == 1) {
            return false;
        }
        int i3 = (i >>> 17) & 3;
        if (i3 == 0) {
            return false;
        }
        int i4 = (i >>> 12) & 15;
        if (i4 != 0) {
            if (i4 != 15) {
                int i5 = (i >>> 10) & 3;
                if (i5 == 3) {
                    return false;
                }
                int i6;
                int i7;
                int i8 = i[i5];
                if (i2 == 2) {
                    i8 /= 2;
                } else if (i2 == 0) {
                    i8 /= 4;
                }
                int i9 = i8;
                i8 = (i >>> 9) & 1;
                int i10 = 1152;
                if (i3 == 3) {
                    i4 = i2 == 3 ? j[i4 - 1] : k[i4 - 1];
                    i6 = (((i4 * 12000) / i9) + i8) * 4;
                    i7 = 384;
                } else {
                    i6 = 144000;
                    if (i2 == 3) {
                        i4 = i3 == 2 ? l[i4 - 1] : m[i4 - 1];
                        i6 = ((144000 * i4) / i9) + i8;
                        i7 = 1152;
                    } else {
                        i4 = n[i4 - 1];
                        if (i3 == 1) {
                            i10 = 576;
                        }
                        if (i3 == 1) {
                            i6 = DefaultOggSeeker.MATCH_RANGE;
                        }
                        i6 = ((i6 * i4) / i9) + i8;
                        i7 = i10;
                    }
                }
                fmVar.a(i2, h[3 - i3], i6, i9, ((i >> 6) & 3) == 3 ? 1 : 2, i4 * 1000, i7);
                return true;
            }
        }
        return false;
    }

    private void a(int i, String str, int i2, int i3, int i4, int i5, int i6) {
        this.a = i;
        this.b = str;
        this.c = i2;
        this.d = i3;
        this.e = i4;
        this.f = i5;
        this.g = i6;
    }
}
