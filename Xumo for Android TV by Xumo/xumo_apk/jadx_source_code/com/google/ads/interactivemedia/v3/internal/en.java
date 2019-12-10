package com.google.ads.interactivemedia.v3.internal;

import android.util.SparseArray;
import androidx.recyclerview.widget.ItemTouchHelper.Callback;
import com.google.ads.interactivemedia.v3.internal.bu.c;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.firebase.FirebaseError;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;

/* compiled from: IMASDK */
public final class en implements cc {
    private static final byte[] a = new byte[]{(byte) 49, (byte) 10, (byte) 48, (byte) 48, (byte) 58, (byte) 48, (byte) 48, (byte) 58, (byte) 48, (byte) 48, (byte) 44, (byte) 48, (byte) 48, (byte) 48, (byte) 32, (byte) 45, (byte) 45, (byte) 62, (byte) 32, (byte) 48, (byte) 48, (byte) 58, (byte) 48, (byte) 48, (byte) 58, (byte) 48, (byte) 48, (byte) 44, (byte) 48, (byte) 48, (byte) 48, (byte) 10};
    private static final byte[] b = new byte[]{(byte) 32, (byte) 32, (byte) 32, (byte) 32, (byte) 32, (byte) 32, (byte) 32, (byte) 32, (byte) 32, (byte) 32, (byte) 32, (byte) 32};
    private static final UUID c = new UUID(72057594037932032L, -9223371306706625679L);
    private long A;
    private boolean B;
    private long C;
    private long D;
    private long E;
    private fk F;
    private fk G;
    private boolean H;
    private int I;
    private long J;
    private long K;
    private int L;
    private int M;
    private int[] N;
    private int O;
    private int P;
    private int Q;
    private int R;
    private boolean S;
    private boolean T;
    private boolean U;
    private boolean V;
    private byte W;
    private int X;
    private int Y;
    private int Z;
    private boolean aa;
    private boolean ab;
    private ce ac;
    private final ej d;
    private final em e;
    private final SparseArray<b> f;
    private final boolean g;
    private final fp h;
    private final fp i;
    private final fp j;
    private final fp k;
    private final fp l;
    private final fp m;
    private final fp n;
    private final fp o;
    private final fp p;
    private ByteBuffer q;
    private long r;
    private long s;
    private long t;
    private long u;
    private long v;
    private b w;
    private boolean x;
    private boolean y;
    private int z;

    /* compiled from: IMASDK */
    private static final class b {
        public float A;
        public float B;
        public float C;
        public float D;
        public float E;
        public int F;
        public int G;
        public int H;
        public long I;
        public long J;
        public ck K;
        public int L;
        private String M;
        public String a;
        public int b;
        public int c;
        public int d;
        public boolean e;
        public byte[] f;
        public byte[] g;
        public byte[] h;
        public int i;
        public int j;
        public int k;
        public int l;
        public int m;
        public byte[] n;
        public int o;
        public boolean p;
        public int q;
        public int r;
        public int s;
        public int t;
        public int u;
        public float v;
        public float w;
        public float x;
        public float y;
        public float z;

        private b() {
            this.i = -1;
            this.j = -1;
            this.k = -1;
            this.l = -1;
            this.m = 0;
            this.n = null;
            this.o = -1;
            this.p = false;
            this.q = -1;
            this.r = -1;
            this.s = -1;
            this.t = 1000;
            this.u = Callback.DEFAULT_DRAG_ANIMATION_DURATION;
            this.v = -1.0f;
            this.w = -1.0f;
            this.x = -1.0f;
            this.y = -1.0f;
            this.z = -1.0f;
            this.A = -1.0f;
            this.B = -1.0f;
            this.C = -1.0f;
            this.D = -1.0f;
            this.E = -1.0f;
            this.F = 1;
            this.G = -1;
            this.H = 8000;
            this.I = 0;
            this.J = 0;
            this.M = "eng";
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void a(com.google.ads.interactivemedia.v3.internal.ce r22, int r23, long r24) throws com.google.ads.interactivemedia.v3.internal.bl {
            /*
            r21 = this;
            r0 = r21;
            r1 = r0.a;
            r2 = r1.hashCode();
            r3 = 3;
            r4 = 8;
            r5 = -1;
            switch(r2) {
                case -2095576542: goto L_0x0121;
                case -2095575984: goto L_0x0117;
                case -1985379776: goto L_0x010c;
                case -1784763192: goto L_0x0101;
                case -1730367663: goto L_0x00f6;
                case -1482641357: goto L_0x00eb;
                case -1373388978: goto L_0x00e0;
                case -538363189: goto L_0x00d6;
                case -538363109: goto L_0x00cc;
                case -425012669: goto L_0x00c1;
                case -356037306: goto L_0x00b5;
                case 62923557: goto L_0x00a9;
                case 62923603: goto L_0x009d;
                case 62927045: goto L_0x0091;
                case 82338133: goto L_0x0086;
                case 82338134: goto L_0x007b;
                case 99146302: goto L_0x006f;
                case 542569478: goto L_0x0063;
                case 725957860: goto L_0x0057;
                case 855502857: goto L_0x004c;
                case 1422270023: goto L_0x0040;
                case 1809237540: goto L_0x0035;
                case 1950749482: goto L_0x0029;
                case 1950789798: goto L_0x001d;
                case 1951062397: goto L_0x0011;
                default: goto L_0x000f;
            };
        L_0x000f:
            goto L_0x012b;
        L_0x0011:
            r2 = "A_OPUS";
            r1 = r1.equals(r2);
            if (r1 == 0) goto L_0x012b;
        L_0x0019:
            r1 = 10;
            goto L_0x012c;
        L_0x001d:
            r2 = "A_FLAC";
            r1 = r1.equals(r2);
            if (r1 == 0) goto L_0x012b;
        L_0x0025:
            r1 = 19;
            goto L_0x012c;
        L_0x0029:
            r2 = "A_EAC3";
            r1 = r1.equals(r2);
            if (r1 == 0) goto L_0x012b;
        L_0x0031:
            r1 = 14;
            goto L_0x012c;
        L_0x0035:
            r2 = "V_MPEG2";
            r1 = r1.equals(r2);
            if (r1 == 0) goto L_0x012b;
        L_0x003d:
            r1 = 2;
            goto L_0x012c;
        L_0x0040:
            r2 = "S_TEXT/UTF8";
            r1 = r1.equals(r2);
            if (r1 == 0) goto L_0x012b;
        L_0x0048:
            r1 = 22;
            goto L_0x012c;
        L_0x004c:
            r2 = "V_MPEGH/ISO/HEVC";
            r1 = r1.equals(r2);
            if (r1 == 0) goto L_0x012b;
        L_0x0054:
            r1 = 7;
            goto L_0x012c;
        L_0x0057:
            r2 = "A_PCM/INT/LIT";
            r1 = r1.equals(r2);
            if (r1 == 0) goto L_0x012b;
        L_0x005f:
            r1 = 21;
            goto L_0x012c;
        L_0x0063:
            r2 = "A_DTS/EXPRESS";
            r1 = r1.equals(r2);
            if (r1 == 0) goto L_0x012b;
        L_0x006b:
            r1 = 17;
            goto L_0x012c;
        L_0x006f:
            r2 = "S_HDMV/PGS";
            r1 = r1.equals(r2);
            if (r1 == 0) goto L_0x012b;
        L_0x0077:
            r1 = 24;
            goto L_0x012c;
        L_0x007b:
            r2 = "V_VP9";
            r1 = r1.equals(r2);
            if (r1 == 0) goto L_0x012b;
        L_0x0083:
            r1 = 1;
            goto L_0x012c;
        L_0x0086:
            r2 = "V_VP8";
            r1 = r1.equals(r2);
            if (r1 == 0) goto L_0x012b;
        L_0x008e:
            r1 = 0;
            goto L_0x012c;
        L_0x0091:
            r2 = "A_DTS";
            r1 = r1.equals(r2);
            if (r1 == 0) goto L_0x012b;
        L_0x0099:
            r1 = 16;
            goto L_0x012c;
        L_0x009d:
            r2 = "A_AC3";
            r1 = r1.equals(r2);
            if (r1 == 0) goto L_0x012b;
        L_0x00a5:
            r1 = 13;
            goto L_0x012c;
        L_0x00a9:
            r2 = "A_AAC";
            r1 = r1.equals(r2);
            if (r1 == 0) goto L_0x012b;
        L_0x00b1:
            r1 = 11;
            goto L_0x012c;
        L_0x00b5:
            r2 = "A_DTS/LOSSLESS";
            r1 = r1.equals(r2);
            if (r1 == 0) goto L_0x012b;
        L_0x00bd:
            r1 = 18;
            goto L_0x012c;
        L_0x00c1:
            r2 = "S_VOBSUB";
            r1 = r1.equals(r2);
            if (r1 == 0) goto L_0x012b;
        L_0x00c9:
            r1 = 23;
            goto L_0x012c;
        L_0x00cc:
            r2 = "V_MPEG4/ISO/AVC";
            r1 = r1.equals(r2);
            if (r1 == 0) goto L_0x012b;
        L_0x00d4:
            r1 = 6;
            goto L_0x012c;
        L_0x00d6:
            r2 = "V_MPEG4/ISO/ASP";
            r1 = r1.equals(r2);
            if (r1 == 0) goto L_0x012b;
        L_0x00de:
            r1 = 4;
            goto L_0x012c;
        L_0x00e0:
            r2 = "V_MS/VFW/FOURCC";
            r1 = r1.equals(r2);
            if (r1 == 0) goto L_0x012b;
        L_0x00e8:
            r1 = 8;
            goto L_0x012c;
        L_0x00eb:
            r2 = "A_MPEG/L3";
            r1 = r1.equals(r2);
            if (r1 == 0) goto L_0x012b;
        L_0x00f3:
            r1 = 12;
            goto L_0x012c;
        L_0x00f6:
            r2 = "A_VORBIS";
            r1 = r1.equals(r2);
            if (r1 == 0) goto L_0x012b;
        L_0x00fe:
            r1 = 9;
            goto L_0x012c;
        L_0x0101:
            r2 = "A_TRUEHD";
            r1 = r1.equals(r2);
            if (r1 == 0) goto L_0x012b;
        L_0x0109:
            r1 = 15;
            goto L_0x012c;
        L_0x010c:
            r2 = "A_MS/ACM";
            r1 = r1.equals(r2);
            if (r1 == 0) goto L_0x012b;
        L_0x0114:
            r1 = 20;
            goto L_0x012c;
        L_0x0117:
            r2 = "V_MPEG4/ISO/SP";
            r1 = r1.equals(r2);
            if (r1 == 0) goto L_0x012b;
        L_0x011f:
            r1 = 3;
            goto L_0x012c;
        L_0x0121:
            r2 = "V_MPEG4/ISO/AP";
            r1 = r1.equals(r2);
            if (r1 == 0) goto L_0x012b;
        L_0x0129:
            r1 = 5;
            goto L_0x012c;
        L_0x012b:
            r1 = -1;
        L_0x012c:
            r2 = 38;
            r6 = 0;
            switch(r1) {
                case 0: goto L_0x0294;
                case 1: goto L_0x0291;
                case 2: goto L_0x028e;
                case 3: goto L_0x027f;
                case 4: goto L_0x027f;
                case 5: goto L_0x027f;
                case 6: goto L_0x0261;
                case 7: goto L_0x0245;
                case 8: goto L_0x0236;
                case 9: goto L_0x0226;
                case 10: goto L_0x01e0;
                case 11: goto L_0x01d7;
                case 12: goto L_0x01cd;
                case 13: goto L_0x01c9;
                case 14: goto L_0x01c5;
                case 15: goto L_0x01c1;
                case 16: goto L_0x01bd;
                case 17: goto L_0x01bd;
                case 18: goto L_0x01b9;
                case 19: goto L_0x01af;
                case 20: goto L_0x0176;
                case 21: goto L_0x014c;
                case 22: goto L_0x0148;
                case 23: goto L_0x013e;
                case 24: goto L_0x013a;
                default: goto L_0x0132;
            };
        L_0x0132:
            r1 = new com.google.ads.interactivemedia.v3.internal.bl;
            r2 = "Unrecognized codec identifier.";
            r1.<init>(r2);
            throw r1;
        L_0x013a:
            r1 = "application/pgs";
            goto L_0x0296;
        L_0x013e:
            r1 = "application/vobsub";
            r2 = r0.h;
            r2 = java.util.Collections.singletonList(r2);
            goto L_0x0243;
        L_0x0148:
            r1 = "application/x-subrip";
            goto L_0x0296;
        L_0x014c:
            r1 = "audio/raw";
            r3 = r0.G;
            r3 = com.google.ads.interactivemedia.v3.internal.ft.a(r3);
            if (r3 == 0) goto L_0x015d;
        L_0x0156:
            r8 = r1;
            r17 = r3;
            r2 = r6;
            r10 = -1;
            goto L_0x029b;
        L_0x015d:
            r1 = new com.google.ads.interactivemedia.v3.internal.bl;
            r3 = r0.G;
            r4 = new java.lang.StringBuilder;
            r4.<init>(r2);
            r2 = "Unsupported PCM bit depth: ";
            r4.append(r2);
            r4.append(r3);
            r2 = r4.toString();
            r1.<init>(r2);
            throw r1;
        L_0x0176:
            r1 = "audio/raw";
            r3 = new com.google.ads.interactivemedia.v3.internal.fp;
            r4 = r0.h;
            r3.<init>(r4);
            r3 = d(r3);
            if (r3 == 0) goto L_0x01a7;
        L_0x0185:
            r3 = r0.G;
            r3 = com.google.ads.interactivemedia.v3.internal.ft.a(r3);
            if (r3 == 0) goto L_0x018e;
        L_0x018d:
            goto L_0x0156;
        L_0x018e:
            r1 = new com.google.ads.interactivemedia.v3.internal.bl;
            r3 = r0.G;
            r4 = new java.lang.StringBuilder;
            r4.<init>(r2);
            r2 = "Unsupported PCM bit depth: ";
            r4.append(r2);
            r4.append(r3);
            r2 = r4.toString();
            r1.<init>(r2);
            throw r1;
        L_0x01a7:
            r1 = new com.google.ads.interactivemedia.v3.internal.bl;
            r2 = "Non-PCM MS/ACM is unsupported";
            r1.<init>(r2);
            throw r1;
        L_0x01af:
            r1 = "audio/x-flac";
            r2 = r0.h;
            r2 = java.util.Collections.singletonList(r2);
            goto L_0x0243;
        L_0x01b9:
            r1 = "audio/vnd.dts.hd";
            goto L_0x0296;
        L_0x01bd:
            r1 = "audio/vnd.dts";
            goto L_0x0296;
        L_0x01c1:
            r1 = "audio/true-hd";
            goto L_0x0296;
        L_0x01c5:
            r1 = "audio/eac3";
            goto L_0x0296;
        L_0x01c9:
            r1 = "audio/ac3";
            goto L_0x0296;
        L_0x01cd:
            r1 = "audio/mpeg";
            r2 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
            r8 = r1;
            r2 = r6;
            r10 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
            goto L_0x0299;
        L_0x01d7:
            r1 = "audio/mp4a-latm";
            r2 = r0.h;
            r2 = java.util.Collections.singletonList(r2);
            goto L_0x0243;
        L_0x01e0:
            r1 = "audio/opus";
            r2 = 5760; // 0x1680 float:8.071E-42 double:2.846E-320;
            r7 = new java.util.ArrayList;
            r7.<init>(r3);
            r3 = r0.h;
            r7.add(r3);
            r3 = java.nio.ByteBuffer.allocate(r4);
            r8 = java.nio.ByteOrder.nativeOrder();
            r3 = r3.order(r8);
            r8 = r0.I;
            r3 = r3.putLong(r8);
            r3 = r3.array();
            r7.add(r3);
            r3 = java.nio.ByteBuffer.allocate(r4);
            r4 = java.nio.ByteOrder.nativeOrder();
            r3 = r3.order(r4);
            r8 = r0.J;
            r3 = r3.putLong(r8);
            r3 = r3.array();
            r7.add(r3);
            r8 = r1;
            r2 = r7;
            r10 = 5760; // 0x1680 float:8.071E-42 double:2.846E-320;
            goto L_0x0299;
        L_0x0226:
            r1 = "audio/vorbis";
            r2 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
            r3 = r0.h;
            r3 = a(r3);
            r8 = r1;
            r2 = r3;
            r10 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
            goto L_0x0299;
        L_0x0236:
            r1 = "video/wvc1";
            r2 = new com.google.ads.interactivemedia.v3.internal.fp;
            r3 = r0.h;
            r2.<init>(r3);
            r2 = a(r2);
        L_0x0243:
            r8 = r1;
            goto L_0x0298;
        L_0x0245:
            r1 = "video/hevc";
            r2 = new com.google.ads.interactivemedia.v3.internal.fp;
            r3 = r0.h;
            r2.<init>(r3);
            r2 = c(r2);
            r3 = r2.first;
            r3 = (java.util.List) r3;
            r2 = r2.second;
            r2 = (java.lang.Integer) r2;
            r2 = r2.intValue();
            r0.L = r2;
            goto L_0x027c;
        L_0x0261:
            r1 = "video/avc";
            r2 = new com.google.ads.interactivemedia.v3.internal.fp;
            r3 = r0.h;
            r2.<init>(r3);
            r2 = b(r2);
            r3 = r2.first;
            r3 = (java.util.List) r3;
            r2 = r2.second;
            r2 = (java.lang.Integer) r2;
            r2 = r2.intValue();
            r0.L = r2;
        L_0x027c:
            r8 = r1;
            r2 = r3;
            goto L_0x0298;
        L_0x027f:
            r1 = "video/mp4v-es";
            r2 = r0.h;
            if (r2 != 0) goto L_0x0287;
        L_0x0285:
            r2 = r6;
            goto L_0x0243;
        L_0x0287:
            r2 = r0.h;
            r2 = java.util.Collections.singletonList(r2);
            goto L_0x0243;
        L_0x028e:
            r1 = "video/mpeg2";
            goto L_0x0296;
        L_0x0291:
            r1 = "video/x-vnd.on2.vp9";
            goto L_0x0296;
        L_0x0294:
            r1 = "video/x-vnd.on2.vp8";
        L_0x0296:
            r8 = r1;
            r2 = r6;
        L_0x0298:
            r10 = -1;
        L_0x0299:
            r17 = -1;
        L_0x029b:
            r1 = com.google.ads.interactivemedia.v3.internal.fl.a(r8);
            if (r1 == 0) goto L_0x02b7;
        L_0x02a1:
            r7 = java.lang.Integer.toString(r23);
            r9 = -1;
            r13 = r0.F;
            r14 = r0.H;
            r1 = r0.M;
            r11 = r24;
            r15 = r2;
            r16 = r1;
            r1 = com.google.ads.interactivemedia.v3.internal.bj.a(r7, r8, r9, r10, r11, r13, r14, r15, r16, r17);
            goto L_0x0362;
        L_0x02b7:
            r1 = com.google.ads.interactivemedia.v3.internal.fl.b(r8);
            if (r1 == 0) goto L_0x0325;
        L_0x02bd:
            r1 = r0.m;
            if (r1 != 0) goto L_0x02d7;
        L_0x02c1:
            r1 = r0.k;
            if (r1 != r5) goto L_0x02c8;
        L_0x02c5:
            r1 = r0.i;
            goto L_0x02ca;
        L_0x02c8:
            r1 = r0.k;
        L_0x02ca:
            r0.k = r1;
            r1 = r0.l;
            if (r1 != r5) goto L_0x02d3;
        L_0x02d0:
            r1 = r0.j;
            goto L_0x02d5;
        L_0x02d3:
            r1 = r0.l;
        L_0x02d5:
            r0.l = r1;
        L_0x02d7:
            r1 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
            r3 = r0.k;
            if (r3 == r5) goto L_0x02f3;
        L_0x02dd:
            r3 = r0.l;
            if (r3 == r5) goto L_0x02f3;
        L_0x02e1:
            r1 = r0.j;
            r3 = r0.k;
            r1 = r1 * r3;
            r1 = (float) r1;
            r3 = r0.i;
            r4 = r0.l;
            r3 = r3 * r4;
            r3 = (float) r3;
            r1 = r1 / r3;
            r17 = r1;
            goto L_0x02f5;
        L_0x02f3:
            r17 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        L_0x02f5:
            r1 = r0.p;
            if (r1 == 0) goto L_0x0308;
        L_0x02f9:
            r1 = r21.a();
            r6 = new com.google.ads.interactivemedia.v3.internal.aw;
            r3 = r0.q;
            r4 = r0.s;
            r5 = r0.r;
            r6.<init>(r3, r4, r5, r1);
        L_0x0308:
            r20 = r6;
            r7 = java.lang.Integer.toString(r23);
            r9 = -1;
            r13 = r0.i;
            r14 = r0.j;
            r16 = -1;
            r1 = r0.n;
            r3 = r0.o;
            r11 = r24;
            r15 = r2;
            r18 = r1;
            r19 = r3;
            r1 = com.google.ads.interactivemedia.v3.internal.bj.a(r7, r8, r9, r10, r11, r13, r14, r15, r16, r17, r18, r19, r20);
            goto L_0x0362;
        L_0x0325:
            r1 = "application/x-subrip";
            r1 = r1.equals(r8);
            if (r1 == 0) goto L_0x033b;
        L_0x032d:
            r7 = java.lang.Integer.toString(r23);
            r9 = -1;
            r12 = r0.M;
            r10 = r24;
            r1 = com.google.ads.interactivemedia.v3.internal.bj.a(r7, r8, r9, r10, r12);
            goto L_0x0362;
        L_0x033b:
            r1 = "application/vobsub";
            r1 = r1.equals(r8);
            if (r1 != 0) goto L_0x0354;
        L_0x0343:
            r1 = "application/pgs";
            r1 = r1.equals(r8);
            if (r1 == 0) goto L_0x034c;
        L_0x034b:
            goto L_0x0354;
        L_0x034c:
            r1 = new com.google.ads.interactivemedia.v3.internal.bl;
            r2 = "Unexpected MIME type.";
            r1.<init>(r2);
            throw r1;
        L_0x0354:
            r7 = java.lang.Integer.toString(r23);
            r9 = -1;
            r13 = r0.M;
            r10 = r24;
            r12 = r2;
            r1 = com.google.ads.interactivemedia.v3.internal.bj.a(r7, r8, r9, r10, r12, r13);
        L_0x0362:
            r2 = r0.b;
            r3 = r22;
            r2 = r3.d(r2);
            r0.K = r2;
            r2 = r0.K;
            r2.a(r1);
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.en.b.a(com.google.ads.interactivemedia.v3.internal.ce, int, long):void");
        }

        private byte[] a() {
            if (!(this.v == -1.0f || this.w == -1.0f || this.x == -1.0f || this.y == -1.0f || this.z == -1.0f || this.A == -1.0f || this.B == -1.0f || this.C == -1.0f || this.D == -1.0f)) {
                if (this.E != -1.0f) {
                    byte[] bArr = new byte[25];
                    ByteBuffer wrap = ByteBuffer.wrap(bArr);
                    wrap.put((byte) 0);
                    wrap.putShort((short) ((int) ((this.v * 50000.0f) + 0.5f)));
                    wrap.putShort((short) ((int) ((this.w * 50000.0f) + 0.5f)));
                    wrap.putShort((short) ((int) ((this.x * 50000.0f) + 0.5f)));
                    wrap.putShort((short) ((int) ((this.y * 50000.0f) + 0.5f)));
                    wrap.putShort((short) ((int) ((this.z * 50000.0f) + 0.5f)));
                    wrap.putShort((short) ((int) ((this.A * 50000.0f) + 0.5f)));
                    wrap.putShort((short) ((int) ((this.B * 50000.0f) + 0.5f)));
                    wrap.putShort((short) ((int) ((this.C * 50000.0f) + 0.5f)));
                    wrap.putShort((short) ((int) (this.D + 0.5f)));
                    wrap.putShort((short) ((int) (this.E + 0.5f)));
                    wrap.putShort((short) this.t);
                    wrap.putShort((short) this.u);
                    return bArr;
                }
            }
            return null;
        }

        private static java.util.List<byte[]> a(com.google.ads.interactivemedia.v3.internal.fp r5) throws com.google.ads.interactivemedia.v3.internal.bl {
            /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r0 = 16;
            r5.d(r0);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            r0 = r5.l();	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            r2 = 826496599; // 0x31435657 float:2.8425313E-9 double:4.08343576E-315;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            if (r4 != 0) goto L_0x004b;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
        L_0x0010:
            r0 = r5.d();	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            r0 = r0 + 20;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            r5 = r5.a;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
        L_0x0018:
            r1 = r5.length;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            r1 = r1 + -4;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            if (r0 >= r1) goto L_0x0043;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
        L_0x001d:
            r1 = r5[r0];	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            if (r1 != 0) goto L_0x0040;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
        L_0x0021:
            r1 = r0 + 1;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            r1 = r5[r1];	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            if (r1 != 0) goto L_0x0040;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
        L_0x0027:
            r1 = r0 + 2;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            r1 = r5[r1];	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            r2 = 1;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            if (r1 != r2) goto L_0x0040;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
        L_0x002e:
            r1 = r0 + 3;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            r1 = r5[r1];	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            r2 = 15;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            if (r1 != r2) goto L_0x0040;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
        L_0x0036:
            r1 = r5.length;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            r5 = java.util.Arrays.copyOfRange(r5, r0, r1);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            r5 = java.util.Collections.singletonList(r5);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            return r5;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
        L_0x0040:
            r0 = r0 + 1;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            goto L_0x0018;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
        L_0x0043:
            r5 = new com.google.ads.interactivemedia.v3.internal.bl;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            r0 = "Failed to find FourCC VC1 initialization data";	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            r5.<init>(r0);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            throw r5;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
        L_0x004b:
            r5 = new com.google.ads.interactivemedia.v3.internal.bl;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            r2 = 57;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            r3 = new java.lang.StringBuilder;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            r3.<init>(r2);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            r2 = "Unsupported FourCC compression type: ";	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            r3.append(r2);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            r3.append(r0);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            r0 = r3.toString();	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            r5.<init>(r0);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
            throw r5;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0064 }
        L_0x0064:
            r5 = new com.google.ads.interactivemedia.v3.internal.bl;
            r0 = "Error parsing FourCC VC1 codec private";
            r5.<init>(r0);
            throw r5;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.en.b.a(com.google.ads.interactivemedia.v3.internal.fp):java.util.List<byte[]>");
        }

        private static android.util.Pair<java.util.List<byte[]>, java.lang.Integer> b(com.google.ads.interactivemedia.v3.internal.fp r6) throws com.google.ads.interactivemedia.v3.internal.bl {
            /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r0 = 4;
            r6.c(r0);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0046 }
            r0 = r6.f();	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0046 }
            r1 = 3;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0046 }
            r0 = r0 & r1;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0046 }
            r0 = r0 + 1;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0046 }
            if (r0 == r1) goto L_0x0040;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0046 }
        L_0x000e:
            r1 = new java.util.ArrayList;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0046 }
            r1.<init>();	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0046 }
            r2 = r6.f();	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0046 }
            r2 = r2 & 31;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0046 }
            r3 = 0;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0046 }
            r4 = 0;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0046 }
        L_0x001b:
            if (r4 >= r2) goto L_0x0027;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0046 }
        L_0x001d:
            r5 = com.google.ads.interactivemedia.v3.internal.fn.a(r6);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0046 }
            r1.add(r5);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0046 }
            r4 = r4 + 1;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0046 }
            goto L_0x001b;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0046 }
        L_0x0027:
            r2 = r6.f();	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0046 }
        L_0x002b:
            if (r3 >= r2) goto L_0x0037;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0046 }
        L_0x002d:
            r4 = com.google.ads.interactivemedia.v3.internal.fn.a(r6);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0046 }
            r1.add(r4);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0046 }
            r3 = r3 + 1;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0046 }
            goto L_0x002b;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0046 }
        L_0x0037:
            r6 = java.lang.Integer.valueOf(r0);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0046 }
            r6 = android.util.Pair.create(r1, r6);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0046 }
            return r6;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0046 }
        L_0x0040:
            r6 = new com.google.ads.interactivemedia.v3.internal.bl;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0046 }
            r6.<init>();	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0046 }
            throw r6;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0046 }
        L_0x0046:
            r6 = new com.google.ads.interactivemedia.v3.internal.bl;
            r0 = "Error parsing AVC codec private";
            r6.<init>(r0);
            throw r6;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.en.b.b(com.google.ads.interactivemedia.v3.internal.fp):android.util.Pair<java.util.List<byte[]>, java.lang.Integer>");
        }

        private static android.util.Pair<java.util.List<byte[]>, java.lang.Integer> c(com.google.ads.interactivemedia.v3.internal.fp r13) throws com.google.ads.interactivemedia.v3.internal.bl {
            /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r0 = 21;
            r13.c(r0);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r0 = r13.f();	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r0 = r0 & 3;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r1 = r13.f();	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r2 = r13.d();	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r3 = 0;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r4 = 0;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r5 = 0;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
        L_0x0016:
            r6 = 1;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            if (r4 >= r1) goto L_0x0035;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
        L_0x0019:
            r13.d(r6);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r6 = r13.g();	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r7 = r5;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r5 = 0;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
        L_0x0022:
            if (r5 >= r6) goto L_0x0031;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
        L_0x0024:
            r8 = r13.g();	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r9 = r8 + 4;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r7 = r7 + r9;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r13.d(r8);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r5 = r5 + 1;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            goto L_0x0022;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
        L_0x0031:
            r4 = r4 + 1;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r5 = r7;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            goto L_0x0016;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
        L_0x0035:
            r13.c(r2);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r2 = new byte[r5];	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r4 = 0;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r7 = 0;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
        L_0x003c:
            if (r4 >= r1) goto L_0x006d;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
        L_0x003e:
            r13.d(r6);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r8 = r13.g();	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r9 = r7;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r7 = 0;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
        L_0x0047:
            if (r7 >= r8) goto L_0x0069;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
        L_0x0049:
            r10 = r13.g();	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r11 = com.google.ads.interactivemedia.v3.internal.fn.a;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r12 = com.google.ads.interactivemedia.v3.internal.fn.a;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r12 = r12.length;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            java.lang.System.arraycopy(r11, r3, r2, r9, r12);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r11 = com.google.ads.interactivemedia.v3.internal.fn.a;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r11 = r11.length;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r9 = r9 + r11;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r11 = r13.a;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r12 = r13.d();	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            java.lang.System.arraycopy(r11, r12, r2, r9, r10);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r9 = r9 + r10;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r13.d(r10);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r7 = r7 + 1;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            goto L_0x0047;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
        L_0x0069:
            r4 = r4 + 1;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r7 = r9;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            goto L_0x003c;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
        L_0x006d:
            if (r5 != 0) goto L_0x0071;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
        L_0x006f:
            r13 = 0;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            goto L_0x0075;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
        L_0x0071:
            r13 = java.util.Collections.singletonList(r2);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
        L_0x0075:
            r0 = r0 + r6;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r0 = java.lang.Integer.valueOf(r0);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            r13 = android.util.Pair.create(r13, r0);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x007f }
            return r13;
        L_0x007f:
            r13 = new com.google.ads.interactivemedia.v3.internal.bl;
            r0 = "Error parsing HEVC codec private";
            r13.<init>(r0);
            throw r13;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.en.b.c(com.google.ads.interactivemedia.v3.internal.fp):android.util.Pair<java.util.List<byte[]>, java.lang.Integer>");
        }

        private static java.util.List<byte[]> a(byte[] r8) throws com.google.ads.interactivemedia.v3.internal.bl {
            /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r0 = 0;
            r1 = r8[r0];	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r2 = 2;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            if (r1 != r2) goto L_0x0069;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
        L_0x0006:
            r1 = 1;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r3 = 1;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r4 = 0;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
        L_0x0009:
            r5 = r8[r3];	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r6 = -1;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            if (r5 != r6) goto L_0x0013;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
        L_0x000e:
            r4 = r4 + 255;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r3 = r3 + 1;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            goto L_0x0009;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
        L_0x0013:
            r5 = r3 + 1;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r3 = r8[r3];	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r4 = r4 + r3;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r3 = 0;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
        L_0x0019:
            r7 = r8[r5];	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            if (r7 != r6) goto L_0x0022;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
        L_0x001d:
            r3 = r3 + 255;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r5 = r5 + 1;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            goto L_0x0019;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
        L_0x0022:
            r6 = r5 + 1;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r5 = r8[r5];	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r3 = r3 + r5;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r5 = r8[r6];	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            if (r5 != r1) goto L_0x0061;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
        L_0x002b:
            r1 = new byte[r4];	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            java.lang.System.arraycopy(r8, r6, r1, r0, r4);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r6 = r6 + r4;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r4 = r8[r6];	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r5 = 3;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            if (r4 != r5) goto L_0x0059;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
        L_0x0036:
            r6 = r6 + r3;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r3 = r8[r6];	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r4 = 5;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            if (r3 != r4) goto L_0x0051;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
        L_0x003c:
            r3 = r8.length;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r3 = r3 - r6;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r3 = new byte[r3];	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r4 = r8.length;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r4 = r4 - r6;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            java.lang.System.arraycopy(r8, r6, r3, r0, r4);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r8 = new java.util.ArrayList;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r8.<init>(r2);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r8.add(r1);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r8.add(r3);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            return r8;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
        L_0x0051:
            r8 = new com.google.ads.interactivemedia.v3.internal.bl;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r0 = "Error parsing vorbis codec private";	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r8.<init>(r0);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            throw r8;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
        L_0x0059:
            r8 = new com.google.ads.interactivemedia.v3.internal.bl;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r0 = "Error parsing vorbis codec private";	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r8.<init>(r0);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            throw r8;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
        L_0x0061:
            r8 = new com.google.ads.interactivemedia.v3.internal.bl;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r0 = "Error parsing vorbis codec private";	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r8.<init>(r0);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            throw r8;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
        L_0x0069:
            r8 = new com.google.ads.interactivemedia.v3.internal.bl;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r0 = "Error parsing vorbis codec private";	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            r8.<init>(r0);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
            throw r8;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0071 }
        L_0x0071:
            r8 = new com.google.ads.interactivemedia.v3.internal.bl;
            r0 = "Error parsing vorbis codec private";
            r8.<init>(r0);
            throw r8;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.en.b.a(byte[]):java.util.List<byte[]>");
        }

        private static boolean d(com.google.ads.interactivemedia.v3.internal.fp r8) throws com.google.ads.interactivemedia.v3.internal.bl {
            /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r0 = r8.h();	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0037 }
            r1 = 1;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0037 }
            if (r0 != r1) goto L_0x0008;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0037 }
        L_0x0007:
            return r1;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0037 }
        L_0x0008:
            r2 = 65534; // 0xfffe float:9.1833E-41 double:3.2378E-319;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0037 }
            r3 = 0;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0037 }
            if (r0 != r2) goto L_0x0036;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0037 }
        L_0x000e:
            r0 = 24;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0037 }
            r8.c(r0);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0037 }
            r4 = r8.o();	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0037 }
            r0 = com.google.ads.interactivemedia.v3.internal.en.c;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0037 }
            r6 = r0.getMostSignificantBits();	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0037 }
            r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0037 }
            if (r0 != 0) goto L_0x0034;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0037 }
        L_0x0023:
            r4 = r8.o();	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0037 }
            r8 = com.google.ads.interactivemedia.v3.internal.en.c;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0037 }
            r6 = r8.getLeastSignificantBits();	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0037 }
            r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
            if (r8 != 0) goto L_0x0034;
        L_0x0033:
            goto L_0x0035;
        L_0x0034:
            r1 = 0;
        L_0x0035:
            return r1;
        L_0x0036:
            return r3;
        L_0x0037:
            r8 = new com.google.ads.interactivemedia.v3.internal.bl;
            r0 = "Error parsing MS/ACM codec private";
            r8.<init>(r0);
            throw r8;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.en.b.d(com.google.ads.interactivemedia.v3.internal.fp):boolean");
        }
    }

    /* compiled from: IMASDK */
    private final class a implements ek {
        final /* synthetic */ en a;

        private a(en enVar) {
            this.a = enVar;
        }

        public int a(int i) {
            return this.a.a(i);
        }

        public boolean b(int i) {
            return this.a.b(i);
        }

        public void a(int i, long j, long j2) throws bl {
            this.a.a(i, j, j2);
        }

        public void c(int i) throws bl {
            this.a.c(i);
        }

        public void a(int i, long j) throws bl {
            this.a.a(i, j);
        }

        public void a(int i, double d) throws bl {
            this.a.a(i, d);
        }

        public void a(int i, String str) throws bl {
            this.a.a(i, str);
        }

        public void a(int i, int i2, cd cdVar) throws IOException, InterruptedException {
            this.a.a(i, i2, cdVar);
        }
    }

    public en() {
        this(new ei(), 0);
    }

    int a(int i) {
        switch (i) {
            case 131:
            case 155:
            case 159:
            case 176:
            case 179:
            case 186:
            case 215:
            case 231:
            case 241:
            case 251:
            case 16980:
            case 17029:
            case 17143:
            case 18401:
            case 18408:
            case 20529:
            case 20530:
            case 21420:
            case 21432:
            case 21680:
            case 21682:
            case 21690:
            case 21945:
            case 21946:
            case 21947:
            case 21948:
            case 21949:
            case 22186:
            case 22203:
            case 25188:
            case 2352003:
            case 2807729:
                return 2;
            case TsExtractor.TS_STREAM_TYPE_SPLICE_INFO /*134*/:
            case FirebaseError.ERROR_WEAK_PASSWORD /*17026*/:
            case 2274716:
                return 3;
            case 160:
            case 174:
            case 183:
            case 187:
            case 224:
            case 225:
            case 18407:
            case 19899:
            case 20532:
            case 20533:
            case 21936:
            case 21968:
            case 25152:
            case 28032:
            case 30320:
            case 290298740:
            case 357149030:
            case 374648427:
            case 408125543:
            case 440786851:
            case 475249515:
            case 524531317:
                return 1;
            case 161:
            case 163:
            case 16981:
            case 18402:
            case 21419:
            case 25506:
            case 30322:
                return 4;
            case 181:
            case 17545:
            case 21969:
            case 21970:
            case 21971:
            case 21972:
            case 21973:
            case 21974:
            case 21975:
            case 21976:
            case 21977:
            case 21978:
                return 5;
            default:
                return 0;
        }
    }

    boolean b(int i) {
        if (!(i == 357149030 || i == 524531317 || i == 475249515)) {
            if (i != 374648427) {
                return false;
            }
        }
        return true;
    }

    public void c() {
    }

    en(ej ejVar, int i) {
        this.r = -1;
        this.s = -1;
        this.t = -1;
        this.u = -1;
        this.v = -1;
        this.C = -1;
        this.D = -1;
        this.E = -1;
        this.d = ejVar;
        this.d.a(new a());
        ejVar = true;
        if ((i & 1) != 0) {
            ejVar = null;
        }
        this.g = ejVar;
        this.e = new em();
        this.f = new SparseArray();
        this.j = new fp(4);
        this.k = new fp(ByteBuffer.allocate(4).putInt(-1).array());
        this.l = new fp(4);
        this.h = new fp(fn.a);
        this.i = new fp(4);
        this.m = new fp();
        this.n = new fp();
        this.o = new fp(8);
        this.p = new fp();
    }

    public boolean a(cd cdVar) throws IOException, InterruptedException {
        return new el().a(cdVar);
    }

    public void a(ce ceVar) {
        this.ac = ceVar;
    }

    public void b() {
        this.E = -1;
        this.I = 0;
        this.d.a();
        this.e.a();
        d();
    }

    public int a(cd cdVar, ch chVar) throws IOException, InterruptedException {
        int i = 0;
        this.aa = false;
        boolean z = true;
        while (z && !this.aa) {
            z = this.d.a(cdVar);
            if (z && a(chVar, cdVar.c())) {
                return 1;
            }
        }
        if (!z) {
            i = -1;
        }
        return i;
    }

    void a(int i, long j, long j2) throws bl {
        if (i == 160) {
            this.ab = false;
        } else if (i == 174) {
            this.w = new b();
        } else if (i == 187) {
            this.H = false;
        } else if (i == 19899) {
            this.z = -1;
            this.A = -1;
        } else if (i == 20533) {
            this.w.e = true;
        } else if (i == 21968) {
            this.w.p = true;
        } else if (i == 25152) {
        } else {
            if (i == 408125543) {
                if (this.r != -1) {
                    if (this.r != j) {
                        throw new bl("Multiple Segment elements not supported");
                    }
                }
                this.r = j;
                this.s = j2;
            } else if (i != 475249515) {
                if (i == 524531317 && this.y == 0) {
                    if (this.g == 0 || this.C == -1) {
                        this.ac.a(cj.f);
                        this.y = true;
                    } else {
                        this.B = true;
                    }
                }
            } else {
                this.F = new fk();
                this.G = new fk();
            }
        }
    }

    void c(int i) throws bl {
        if (i != 160) {
            if (i == 174) {
                if (a(this.w.a) != 0) {
                    this.w.a(this.ac, this.w.b, this.v);
                    this.f.put(this.w.b, this.w);
                }
                this.w = 0;
            } else if (i != 19899) {
                if (i == 25152) {
                    if (this.w.e != 0) {
                        if (this.w.g == 0) {
                            throw new bl("Encrypted Track found but ContentEncKeyID was not found");
                        } else if (this.x == 0) {
                            this.ac.a(new c(new com.google.ads.interactivemedia.v3.internal.bu.b(MimeTypes.VIDEO_WEBM, this.w.g)));
                            this.x = true;
                        }
                    }
                } else if (i == 28032) {
                    if (this.w.e != 0) {
                        if (this.w.f != 0) {
                            throw new bl("Combining encryption and compression is not supported");
                        }
                    }
                } else if (i == 357149030) {
                    if (this.t == -1) {
                        this.t = 1000000;
                    }
                    if (this.u != -1) {
                        this.v = a(this.u);
                    }
                } else if (i != 374648427) {
                    if (i == 475249515 && this.y == 0) {
                        this.ac.a(e());
                        this.y = true;
                    }
                } else if (this.f.size() != 0) {
                    this.ac.f();
                } else {
                    throw new bl("No valid tracks were found");
                }
            } else if (this.z == -1 || this.A == -1) {
                throw new bl("Mandatory element SeekID or SeekPosition not found");
            } else {
                if (this.z == 475249515) {
                    this.C = this.A;
                }
            }
        } else if (this.I == 2) {
            if (this.ab == 0) {
                this.Q |= 1;
            }
            a((b) this.f.get(this.O), this.J);
            this.I = 0;
        }
    }

    void a(int i, long j) throws bl {
        StringBuilder stringBuilder;
        StringBuilder stringBuilder2;
        switch (i) {
            case 131:
                this.w.c = (int) j;
                return;
            case 155:
                this.K = a(j);
                return;
            case 159:
                this.w.F = (int) j;
                return;
            case 176:
                this.w.i = (int) j;
                return;
            case 179:
                this.F.a(a(j));
                return;
            case 186:
                this.w.j = (int) j;
                return;
            case 215:
                this.w.b = (int) j;
                return;
            case 231:
                this.E = a(j);
                return;
            case 241:
                if (this.H == 0) {
                    this.G.a(j);
                    this.H = true;
                }
                return;
            case 251:
                this.ab = true;
                return;
            case 16980:
                if (j != 3) {
                    stringBuilder = new StringBuilder(50);
                    stringBuilder.append("ContentCompAlgo ");
                    stringBuilder.append(j);
                    stringBuilder.append(" not supported");
                    throw new bl(stringBuilder.toString());
                }
                return;
            case 17029:
                if (j < 1 || j > 2) {
                    stringBuilder2 = new StringBuilder(53);
                    stringBuilder2.append("DocTypeReadVersion ");
                    stringBuilder2.append(j);
                    stringBuilder2.append(" not supported");
                    throw new bl(stringBuilder2.toString());
                }
                return;
            case 17143:
                if (j != 1) {
                    stringBuilder = new StringBuilder(50);
                    stringBuilder.append("EBMLReadVersion ");
                    stringBuilder.append(j);
                    stringBuilder.append(" not supported");
                    throw new bl(stringBuilder.toString());
                }
                return;
            case 18401:
                if (j != 5) {
                    stringBuilder2 = new StringBuilder(49);
                    stringBuilder2.append("ContentEncAlgo ");
                    stringBuilder2.append(j);
                    stringBuilder2.append(" not supported");
                    throw new bl(stringBuilder2.toString());
                }
                return;
            case 18408:
                if (j != 1) {
                    stringBuilder2 = new StringBuilder(56);
                    stringBuilder2.append("AESSettingsCipherMode ");
                    stringBuilder2.append(j);
                    stringBuilder2.append(" not supported");
                    throw new bl(stringBuilder2.toString());
                }
                return;
            case 20529:
                if (j != 0) {
                    stringBuilder = new StringBuilder(55);
                    stringBuilder.append("ContentEncodingOrder ");
                    stringBuilder.append(j);
                    stringBuilder.append(" not supported");
                    throw new bl(stringBuilder.toString());
                }
                return;
            case 20530:
                if (j != 1) {
                    stringBuilder = new StringBuilder(55);
                    stringBuilder.append("ContentEncodingScope ");
                    stringBuilder.append(j);
                    stringBuilder.append(" not supported");
                    throw new bl(stringBuilder.toString());
                }
                return;
            case 21420:
                this.A = j + this.r;
                return;
            case 21432:
                i = (int) j;
                if (i == 3) {
                    this.w.o = 1;
                } else if (i != 15) {
                    switch (i) {
                        case 0:
                            this.w.o = 0;
                            break;
                        case 1:
                            this.w.o = 2;
                            break;
                        default:
                            break;
                    }
                } else {
                    this.w.o = 3;
                }
                return;
            case 21680:
                this.w.k = (int) j;
                return;
            case 21682:
                this.w.m = (int) j;
                return;
            case 21690:
                this.w.l = (int) j;
                return;
            case 21945:
                switch ((int) j) {
                    case 1:
                        this.w.s = 2;
                        break;
                    case 2:
                        this.w.s = 1;
                        break;
                    default:
                        break;
                }
            case 21946:
                i = (int) j;
                if (i != 1) {
                    if (i != 16) {
                        if (i == 18) {
                            this.w.r = 7;
                            break;
                        }
                        switch (i) {
                            case 6:
                            case 7:
                                break;
                            default:
                                break;
                        }
                    }
                    this.w.r = 6;
                    break;
                }
                this.w.r = 3;
                break;
            case 21947:
                this.w.p = true;
                i = (int) j;
                if (i != 1) {
                    if (i == 9) {
                        this.w.q = 6;
                        break;
                    }
                    switch (i) {
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                            this.w.q = 2;
                            break;
                        default:
                            break;
                    }
                }
                this.w.q = 1;
                break;
            case 21948:
                this.w.t = (int) j;
                break;
            case 21949:
                this.w.u = (int) j;
                break;
            case 22186:
                this.w.I = j;
                return;
            case 22203:
                this.w.J = j;
                return;
            case 25188:
                this.w.G = (int) j;
                return;
            case 2352003:
                this.w.d = (int) j;
                return;
            case 2807729:
                this.t = j;
                return;
            default:
                return;
        }
    }

    void a(int i, double d) {
        if (i == 181) {
            this.w.H = (int) d;
        } else if (i != 17545) {
            switch (i) {
                case 21969:
                    this.w.v = (float) d;
                    break;
                case 21970:
                    this.w.w = (float) d;
                    break;
                case 21971:
                    this.w.x = (float) d;
                    break;
                case 21972:
                    this.w.y = (float) d;
                    break;
                case 21973:
                    this.w.z = (float) d;
                    break;
                case 21974:
                    this.w.A = (float) d;
                    break;
                case 21975:
                    this.w.B = (float) d;
                    break;
                case 21976:
                    this.w.C = (float) d;
                    break;
                case 21977:
                    this.w.D = (float) d;
                    break;
                case 21978:
                    this.w.E = (float) d;
                    break;
                default:
                    return;
            }
        } else {
            this.u = (long) d;
        }
    }

    void a(int i, String str) throws bl {
        if (i == TsExtractor.TS_STREAM_TYPE_SPLICE_INFO) {
            this.w.a = str;
        } else if (i == FirebaseError.ERROR_WEAK_PASSWORD) {
            if ("webm".equals(str) == 0) {
                if ("matroska".equals(str) == 0) {
                    StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 22);
                    stringBuilder.append("DocType ");
                    stringBuilder.append(str);
                    stringBuilder.append(" not supported");
                    throw new bl(stringBuilder.toString());
                }
            }
        } else if (i == 2274716) {
            this.w.M = str;
        }
    }

    void a(int i, int i2, cd cdVar) throws IOException, InterruptedException {
        en enVar = this;
        int i3 = i;
        int i4 = i2;
        cd cdVar2 = cdVar;
        boolean z = false;
        if (i3 == 161 || i3 == 163) {
            long j = 8;
            if (enVar.I == 0) {
                enVar.O = (int) enVar.e.a(cdVar2, false, true, 8);
                enVar.P = enVar.e.b();
                enVar.K = -1;
                enVar.I = 1;
                enVar.j.a();
            }
            b bVar = (b) enVar.f.get(enVar.O);
            if (bVar == null) {
                cdVar2.b(i4 - enVar.P);
                enVar.I = 0;
                return;
            }
            if (enVar.I == 1) {
                int i5;
                a(cdVar2, 3);
                int i6 = (enVar.j.a[2] & 6) >> 1;
                int i7 = 255;
                if (i6 == 0) {
                    enVar.M = 1;
                    enVar.N = a(enVar.N, 1);
                    enVar.N[0] = (i4 - enVar.P) - 3;
                } else if (i3 == 163) {
                    a(cdVar2, 4);
                    enVar.M = (enVar.j.a[3] & 255) + 1;
                    enVar.N = a(enVar.N, enVar.M);
                    if (i6 == 2) {
                        Arrays.fill(enVar.N, 0, enVar.M, ((i4 - enVar.P) - 4) / enVar.M);
                    } else if (i6 == 1) {
                        r10 = 4;
                        i6 = 0;
                        for (r6 = 0; r6 < enVar.M - 1; r6++) {
                            enVar.N[r6] = 0;
                            do {
                                r10++;
                                a(cdVar2, r10);
                                r14 = enVar.j.a[r10 - 1] & 255;
                                int[] iArr = enVar.N;
                                iArr[r6] = iArr[r6] + r14;
                            } while (r14 == 255);
                            i6 += enVar.N[r6];
                        }
                        enVar.N[enVar.M - 1] = ((i4 - enVar.P) - r10) - i6;
                    } else if (i6 == 3) {
                        r6 = 0;
                        r10 = 4;
                        i6 = 0;
                        while (r6 < enVar.M - 1) {
                            enVar.N[r6] = z;
                            r10++;
                            a(cdVar2, r10);
                            int i8 = r10 - 1;
                            if (enVar.j.a[i8] != (byte) 0) {
                                long j2;
                                long j3 = 0;
                                r14 = 0;
                                while (r14 < j) {
                                    int i9 = 1 << (7 - r14);
                                    if ((enVar.j.a[i8] & i9) != 0) {
                                        r10 += r14;
                                        a(cdVar2, r10);
                                        i5 = i8 + 1;
                                        j3 = (long) ((enVar.j.a[i8] & i7) & (i9 ^ -1));
                                        while (i5 < r10) {
                                            i5++;
                                            j3 = ((long) (enVar.j.a[i5] & 255)) | (j3 << j);
                                            j = 8;
                                        }
                                        if (r6 > 0) {
                                            j3 -= (1 << ((r14 * 7) + 6)) - 1;
                                        }
                                        j2 = j3;
                                        if (j2 >= -2147483648L || j2 > 2147483647L) {
                                            throw new bl("EBML lacing sample size out of range.");
                                        }
                                        i5 = (int) j2;
                                        int[] iArr2 = enVar.N;
                                        if (r6 != 0) {
                                            i5 += enVar.N[r6 - 1];
                                        }
                                        iArr2[r6] = i5;
                                        i6 += enVar.N[r6];
                                        r6++;
                                        z = false;
                                        j = 8;
                                        i7 = 255;
                                    } else {
                                        r14++;
                                        j = 8;
                                        i7 = 255;
                                    }
                                }
                                j2 = j3;
                                if (j2 >= -2147483648L) {
                                }
                                throw new bl("EBML lacing sample size out of range.");
                            }
                            throw new bl("No valid varint length mask found");
                        }
                        enVar.N[enVar.M - 1] = ((i4 - enVar.P) - r10) - i6;
                    } else {
                        StringBuilder stringBuilder = new StringBuilder(36);
                        stringBuilder.append("Unexpected lacing value: ");
                        stringBuilder.append(i6);
                        throw new bl(stringBuilder.toString());
                    }
                } else {
                    throw new bl("Lacing only supported in SimpleBlocks.");
                }
                enVar.J = enVar.E + a((long) ((enVar.j.a[0] << 8) | (enVar.j.a[1] & 255)));
                Object obj = (enVar.j.a[2] & 8) == 8 ? 1 : null;
                if (bVar.c != 2) {
                    if (i3 != 163 || (enVar.j.a[2] & 128) != 128) {
                        i5 = 0;
                        enVar.Q = i5 | (obj == null ? 134217728 : 0);
                        enVar.I = 2;
                        enVar.L = 0;
                    }
                }
                i5 = 1;
                if (obj == null) {
                }
                enVar.Q = i5 | (obj == null ? 134217728 : 0);
                enVar.I = 2;
                enVar.L = 0;
            }
            if (i3 == 163) {
                while (enVar.L < enVar.M) {
                    a(cdVar2, bVar, enVar.N[enVar.L]);
                    a(bVar, enVar.J + ((long) ((enVar.L * bVar.d) / 1000)));
                    enVar.L++;
                }
                enVar.I = 0;
            } else {
                a(cdVar2, bVar, enVar.N[0]);
            }
        } else if (i3 == 16981) {
            enVar.w.f = new byte[i4];
            cdVar2.b(enVar.w.f, 0, i4);
        } else if (i3 == 18402) {
            enVar.w.g = new byte[i4];
            cdVar2.b(enVar.w.g, 0, i4);
        } else if (i3 == 21419) {
            Arrays.fill(enVar.l.a, (byte) 0);
            cdVar2.b(enVar.l.a, 4 - i4, i4);
            enVar.l.c(0);
            enVar.z = (int) enVar.l.k();
        } else if (i3 == 25506) {
            enVar.w.h = new byte[i4];
            cdVar2.b(enVar.w.h, 0, i4);
        } else if (i3 == 30322) {
            enVar.w.n = new byte[i4];
            cdVar2.b(enVar.w.n, 0, i4);
        } else {
            StringBuilder stringBuilder2 = new StringBuilder(26);
            stringBuilder2.append("Unexpected id: ");
            stringBuilder2.append(i3);
            throw new bl(stringBuilder2.toString());
        }
    }

    private void a(b bVar, long j) {
        if ("S_TEXT/UTF8".equals(bVar.a)) {
            a(bVar);
        }
        bVar.K.a(j, this.Q, this.Z, 0, bVar.g);
        this.aa = true;
        d();
    }

    private void d() {
        this.R = 0;
        this.Z = 0;
        this.Y = 0;
        this.S = false;
        this.T = false;
        this.V = false;
        this.X = 0;
        this.W = (byte) 0;
        this.U = false;
        this.m.a();
    }

    private void a(cd cdVar, int i) throws IOException, InterruptedException {
        if (this.j.c() < i) {
            if (this.j.e() < i) {
                this.j.a(Arrays.copyOf(this.j.a, Math.max(this.j.a.length * 2, i)), this.j.c());
            }
            cdVar.b(this.j.a, this.j.c(), i - this.j.c());
            this.j.b(i);
        }
    }

    private void a(cd cdVar, b bVar, int i) throws IOException, InterruptedException {
        if ("S_TEXT/UTF8".equals(bVar.a)) {
            bVar = a.length + i;
            if (this.n.e() < bVar) {
                this.n.a = Arrays.copyOf(a, bVar + i);
            }
            cdVar.b(this.n.a, a.length, i);
            this.n.c(0);
            this.n.b(bVar);
            return;
        }
        ck ckVar = bVar.K;
        if (!this.S) {
            if (bVar.e) {
                this.Q &= -3;
                int i2 = 128;
                if (!this.T) {
                    cdVar.b(this.j.a, 0, 1);
                    this.R++;
                    if ((this.j.a[0] & 128) != 128) {
                        this.W = this.j.a[0];
                        this.T = true;
                    } else {
                        throw new bl("Extension bit is set in signal byte");
                    }
                }
                if (((this.W & 1) == 1 ? 1 : null) != null) {
                    Object obj = (this.W & 2) == 2 ? 1 : null;
                    this.Q |= 2;
                    if (!this.U) {
                        cdVar.b(this.o.a, 0, 8);
                        this.R += 8;
                        this.U = true;
                        byte[] bArr = this.j.a;
                        if (obj == null) {
                            i2 = 0;
                        }
                        bArr[0] = (byte) (i2 | 8);
                        this.j.c(0);
                        ckVar.a(this.j, 1);
                        this.Z++;
                        this.o.c(0);
                        ckVar.a(this.o, 8);
                        this.Z += 8;
                    }
                    if (obj != null) {
                        if (!this.V) {
                            cdVar.b(this.j.a, 0, 1);
                            this.R++;
                            this.j.c(0);
                            this.X = this.j.f();
                            this.V = true;
                        }
                        int i3 = this.X * 4;
                        if (this.j.c() < i3) {
                            this.j.a(new byte[i3], i3);
                        }
                        cdVar.b(this.j.a, 0, i3);
                        this.R += i3;
                        this.j.c(0);
                        this.j.b(i3);
                        short s = (short) ((this.X / 2) + 1);
                        i2 = (s * 6) + 2;
                        if (this.q == null || this.q.capacity() < i2) {
                            this.q = ByteBuffer.allocate(i2);
                        }
                        this.q.position(0);
                        this.q.putShort(s);
                        i3 = 0;
                        int i4 = 0;
                        while (i3 < this.X) {
                            int s2 = this.j.s();
                            if (i3 % 2 == 0) {
                                this.q.putShort((short) (s2 - i4));
                            } else {
                                this.q.putInt(s2 - i4);
                            }
                            i3++;
                            i4 = s2;
                        }
                        i3 = (i - this.R) - i4;
                        if (this.X % 2 == 1) {
                            this.q.putInt(i3);
                        } else {
                            this.q.putShort((short) i3);
                            this.q.putInt(0);
                        }
                        this.p.a(this.q.array(), i2);
                        ckVar.a(this.p, i2);
                        this.Z += i2;
                    }
                }
            } else if (bVar.f != null) {
                this.m.a(bVar.f, bVar.f.length);
            }
            this.S = true;
        }
        i += this.m.c();
        if (!"V_MPEG4/ISO/AVC".equals(bVar.a)) {
            if (!"V_MPEGH/ISO/HEVC".equals(bVar.a)) {
                while (this.R < i) {
                    a(cdVar, ckVar, i - this.R);
                }
                if ("A_VORBIS".equals(bVar.a) != null) {
                    this.k.c(0);
                    ckVar.a(this.k, 4);
                    this.Z += 4;
                }
            }
        }
        byte[] bArr2 = this.i.a;
        bArr2[0] = (byte) 0;
        bArr2[1] = (byte) 0;
        bArr2[2] = (byte) 0;
        int i5 = bVar.L;
        int i6 = 4 - bVar.L;
        while (this.R < i) {
            if (this.Y == 0) {
                a(cdVar, bArr2, i6, i5);
                this.i.c(0);
                this.Y = this.i.s();
                this.h.c(0);
                ckVar.a(this.h, 4);
                this.Z += 4;
            } else {
                this.Y -= a(cdVar, ckVar, this.Y);
            }
        }
        if ("A_VORBIS".equals(bVar.a) != null) {
            this.k.c(0);
            ckVar.a(this.k, 4);
            this.Z += 4;
        }
    }

    private void a(b bVar) {
        a(this.n.a, this.K);
        bVar.K.a(this.n, this.n.c());
        this.Z += this.n.c();
    }

    private static void a(byte[] bArr, long j) {
        if (j == -1) {
            j = b;
        } else {
            j -= ((long) ((int) (j / 3600000000L))) * 3600000000L;
            j -= (long) (60000000 * ((int) (j / 60000000)));
            j = (int) ((j - ((long) (1000000 * ((int) (j / 1000000))))) / 1000);
            j = String.format(Locale.US, "%02d:%02d:%02d,%03d", new Object[]{Integer.valueOf(r3), Integer.valueOf(r1), Integer.valueOf(r2), Integer.valueOf(j)}).getBytes();
        }
        System.arraycopy(j, 0, bArr, 19, 12);
    }

    private void a(cd cdVar, byte[] bArr, int i, int i2) throws IOException, InterruptedException {
        int min = Math.min(i2, this.m.b());
        cdVar.b(bArr, i + min, i2 - min);
        if (min > 0) {
            this.m.a(bArr, i, min);
        }
        this.R += i2;
    }

    private int a(cd cdVar, ck ckVar, int i) throws IOException, InterruptedException {
        int b = this.m.b();
        if (b > 0) {
            cdVar = Math.min(i, b);
            ckVar.a(this.m, cdVar);
        } else {
            cdVar = ckVar.a(cdVar, i, false);
        }
        this.R += cdVar;
        this.Z += cdVar;
        return cdVar;
    }

    private cj e() {
        if (!(this.r == -1 || this.v == -1 || this.F == null || this.F.a() == 0 || this.G == null)) {
            if (this.G.a() == this.F.a()) {
                int i;
                int a = this.F.a();
                int[] iArr = new int[a];
                long[] jArr = new long[a];
                long[] jArr2 = new long[a];
                long[] jArr3 = new long[a];
                int i2 = 0;
                for (i = 0; i < a; i++) {
                    jArr3[i] = this.F.a(i);
                    jArr[i] = this.r + this.G.a(i);
                }
                while (true) {
                    i = a - 1;
                    if (i2 < i) {
                        i = i2 + 1;
                        iArr[i2] = (int) (jArr[i] - jArr[i2]);
                        jArr2[i2] = jArr3[i] - jArr3[i2];
                        i2 = i;
                    } else {
                        iArr[i] = (int) ((this.r + this.s) - jArr[i]);
                        jArr2[i] = this.v - jArr3[i];
                        this.F = null;
                        this.G = null;
                        return new by(iArr, jArr, jArr2, jArr3);
                    }
                }
            }
        }
        this.F = null;
        this.G = null;
        return cj.f;
    }

    private boolean a(ch chVar, long j) {
        if (this.B) {
            this.D = j;
            chVar.a = this.C;
            this.B = false;
            return true;
        } else if (this.y == null || this.D == -1) {
            return false;
        } else {
            chVar.a = this.D;
            this.D = -1;
            return true;
        }
    }

    private long a(long j) throws bl {
        if (this.t != -1) {
            return ft.a(j, this.t, 1000);
        }
        throw new bl("Can't scale timecode prior to timecodeScale being set.");
    }

    private static boolean a(String str) {
        if (!("V_VP8".equals(str) || "V_VP9".equals(str) || "V_MPEG2".equals(str) || "V_MPEG4/ISO/SP".equals(str) || "V_MPEG4/ISO/ASP".equals(str) || "V_MPEG4/ISO/AP".equals(str) || "V_MPEG4/ISO/AVC".equals(str) || "V_MPEGH/ISO/HEVC".equals(str) || "V_MS/VFW/FOURCC".equals(str) || "A_OPUS".equals(str) || "A_VORBIS".equals(str) || "A_AAC".equals(str) || "A_MPEG/L3".equals(str) || "A_AC3".equals(str) || "A_EAC3".equals(str) || "A_TRUEHD".equals(str) || "A_DTS".equals(str) || "A_DTS/EXPRESS".equals(str) || "A_DTS/LOSSLESS".equals(str) || "A_FLAC".equals(str) || "A_MS/ACM".equals(str) || "A_PCM/INT/LIT".equals(str) || "S_TEXT/UTF8".equals(str) || "S_VOBSUB".equals(str))) {
            if ("S_HDMV/PGS".equals(str) == null) {
                return null;
            }
        }
        return true;
    }

    private static int[] a(int[] iArr, int i) {
        if (iArr == null) {
            return new int[i];
        }
        if (iArr.length >= i) {
            return iArr;
        }
        return new int[Math.max(iArr.length * 2, i)];
    }
}
