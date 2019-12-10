package com.google.ads.interactivemedia.v3.internal;

import androidx.core.view.ViewCompat;
import com.google.android.exoplayer2.metadata.id3.InternalFrame;
import com.google.android.exoplayer2.source.hls.DefaultHlsExtractorFactory;
import com.google.android.exoplayer2.upstream.DataSchemeDataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: IMASDK */
abstract class cv {
    public static final int A = ft.c("sidx");
    public static final int B = ft.c("moov");
    public static final int C = ft.c("mvhd");
    public static final int D = ft.c("trak");
    public static final int E = ft.c("mdia");
    public static final int F = ft.c("minf");
    public static final int G = ft.c("stbl");
    public static final int H = ft.c("avcC");
    public static final int I = ft.c("hvcC");
    public static final int J = ft.c("esds");
    public static final int K = ft.c("moof");
    public static final int L = ft.c("traf");
    public static final int M = ft.c("mvex");
    public static final int N = ft.c("mehd");
    public static final int O = ft.c("tkhd");
    public static final int P = ft.c("edts");
    public static final int Q = ft.c("elst");
    public static final int R = ft.c("mdhd");
    public static final int S = ft.c("hdlr");
    public static final int T = ft.c("stsd");
    public static final int U = ft.c("pssh");
    public static final int V = ft.c("sinf");
    public static final int W = ft.c("schm");
    public static final int X = ft.c("schi");
    public static final int Y = ft.c("tenc");
    public static final int Z = ft.c("encv");
    public static final int a = ft.c("ftyp");
    public static final int aA = ft.c("meta");
    public static final int aB = ft.c("ilst");
    public static final int aC = ft.c("mean");
    public static final int aD = ft.c(JsonKey.NAME);
    public static final int aE = ft.c(DataSchemeDataSource.SCHEME_DATA);
    public static final int aF = ft.c("emsg");
    public static final int aG = ft.c("st3d");
    public static final int aH = ft.c("sv3d");
    public static final int aI = ft.c("proj");
    public static final int aJ = ft.c("vp08");
    public static final int aK = ft.c("vp09");
    public static final int aL = ft.c("vpcC");
    public static final int aM = ft.c("camm");
    public static final int aN = ft.c(InternalFrame.ID);
    public static final int aa = ft.c("enca");
    public static final int ab = ft.c("frma");
    public static final int ac = ft.c("saiz");
    public static final int ad = ft.c("saio");
    public static final int ae = ft.c("sbgp");
    public static final int af = ft.c("sgpd");
    public static final int ag = ft.c("uuid");
    public static final int ah = ft.c("senc");
    public static final int ai = ft.c("pasp");
    public static final int aj = ft.c("TTML");
    public static final int ak = ft.c("vmhd");
    public static final int al = ft.c("mp4v");
    public static final int am = ft.c("stts");
    public static final int an = ft.c("stss");
    public static final int ao = ft.c("ctts");
    public static final int ap = ft.c("stsc");
    public static final int aq = ft.c("stsz");
    public static final int ar = ft.c("stz2");
    public static final int as = ft.c("stco");
    public static final int at = ft.c("co64");
    public static final int au = ft.c("tx3g");
    public static final int av = ft.c("wvtt");
    public static final int aw = ft.c("stpp");
    public static final int ax = ft.c("samr");
    public static final int ay = ft.c("sawb");
    public static final int az = ft.c("udta");
    public static final int b = ft.c("avc1");
    public static final int c = ft.c("avc3");
    public static final int d = ft.c("hvc1");
    public static final int e = ft.c("hev1");
    public static final int f = ft.c("s263");
    public static final int g = ft.c("d263");
    public static final int h = ft.c("mdat");
    public static final int i = ft.c("mp4a");
    public static final int j = ft.c(DefaultHlsExtractorFactory.MP3_FILE_EXTENSION);
    public static final int k = ft.c("wave");
    public static final int l = ft.c("lpcm");
    public static final int m = ft.c("sowt");
    public static final int n = ft.c("ac-3");
    public static final int o = ft.c("dac3");
    public static final int p = ft.c("ec-3");
    public static final int q = ft.c("dec3");
    public static final int r = ft.c("dtsc");
    public static final int s = ft.c("dtsh");
    public static final int t = ft.c("dtsl");
    public static final int u = ft.c("dtse");
    public static final int v = ft.c("ddts");
    public static final int w = ft.c("tfdt");
    public static final int x = ft.c("tfhd");
    public static final int y = ft.c("trex");
    public static final int z = ft.c("trun");
    public final int aO;

    /* compiled from: IMASDK */
    static final class a extends cv {
        public final long aP;
        public final List<b> aQ = new ArrayList();
        public final List<a> aR = new ArrayList();

        public a(int i, long j) {
            super(i);
            this.aP = j;
        }

        public void a(b bVar) {
            this.aQ.add(bVar);
        }

        public void a(a aVar) {
            this.aR.add(aVar);
        }

        public b d(int i) {
            int size = this.aQ.size();
            for (int i2 = 0; i2 < size; i2++) {
                b bVar = (b) this.aQ.get(i2);
                if (bVar.aO == i) {
                    return bVar;
                }
            }
            return 0;
        }

        public a e(int i) {
            int size = this.aR.size();
            for (int i2 = 0; i2 < size; i2++) {
                a aVar = (a) this.aR.get(i2);
                if (aVar.aO == i) {
                    return aVar;
                }
            }
            return 0;
        }

        public int f(int i) {
            int size = this.aQ.size();
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                if (((b) this.aQ.get(i3)).aO == i) {
                    i2++;
                }
            }
            size = this.aR.size();
            for (int i4 = 0; i4 < size; i4++) {
                if (((a) this.aR.get(i4)).aO == i) {
                    i2++;
                }
            }
            return i2;
        }

        public String toString() {
            String c = cv.c(this.aO);
            String arrays = Arrays.toString(this.aQ.toArray(new b[0]));
            String arrays2 = Arrays.toString(this.aR.toArray(new a[0]));
            StringBuilder stringBuilder = new StringBuilder(((String.valueOf(c).length() + 22) + String.valueOf(arrays).length()) + String.valueOf(arrays2).length());
            stringBuilder.append(c);
            stringBuilder.append(" leaves: ");
            stringBuilder.append(arrays);
            stringBuilder.append(" containers: ");
            stringBuilder.append(arrays2);
            return stringBuilder.toString();
        }
    }

    /* compiled from: IMASDK */
    static final class b extends cv {
        public final fp aP;

        public b(int i, fp fpVar) {
            super(i);
            this.aP = fpVar;
        }
    }

    public cv(int i) {
        this.aO = i;
    }

    public static int a(int i) {
        return (i >> 24) & 255;
    }

    public static int b(int i) {
        return i & ViewCompat.MEASURED_SIZE_MASK;
    }

    public String toString() {
        return c(this.aO);
    }

    public static String c(int i) {
        char c = (char) (i >> 24);
        char c2 = (char) ((i >> 16) & 255);
        char c3 = (char) ((i >> 8) & 255);
        i = (char) (i & 255);
        StringBuilder stringBuilder = new StringBuilder(4);
        stringBuilder.append(c);
        stringBuilder.append(c2);
        stringBuilder.append(c3);
        stringBuilder.append(i);
        return stringBuilder.toString();
    }
}
