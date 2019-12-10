package com.google.ads.interactivemedia.v3.internal;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;

/* compiled from: IMASDK */
public class hx implements Closeable {
    private static final char[] b = ")]}'\n".toCharArray();
    int a = 0;
    private final Reader c;
    private boolean d = false;
    private final char[] e = new char[1024];
    private int f = 0;
    private int g = 0;
    private int h = 0;
    private int i = 0;
    private long j;
    private int k;
    private String l;
    private int[] m = new int[32];
    private int n = 0;
    private String[] o;
    private int[] p;

    public hx(Reader reader) {
        int[] iArr = this.m;
        int i = this.n;
        this.n = i + 1;
        iArr[i] = 6;
        this.o = new String[32];
        this.p = new int[32];
        if (reader != null) {
            this.c = reader;
            return;
        }
        throw new NullPointerException("in == null");
    }

    public final void a(boolean z) {
        this.d = z;
    }

    public final boolean q() {
        return this.d;
    }

    public void a() throws IOException {
        int i = this.a;
        if (i == 0) {
            i = r();
        }
        if (i == 3) {
            a(1);
            this.p[this.n - 1] = 0;
            this.a = 0;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected BEGIN_ARRAY but was ");
        stringBuilder.append(f());
        stringBuilder.append(x());
        throw new IllegalStateException(stringBuilder.toString());
    }

    public void b() throws IOException {
        int i = this.a;
        if (i == 0) {
            i = r();
        }
        if (i == 4) {
            this.n--;
            int[] iArr = this.p;
            int i2 = this.n - 1;
            iArr[i2] = iArr[i2] + 1;
            this.a = 0;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected END_ARRAY but was ");
        stringBuilder.append(f());
        stringBuilder.append(x());
        throw new IllegalStateException(stringBuilder.toString());
    }

    public void c() throws IOException {
        int i = this.a;
        if (i == 0) {
            i = r();
        }
        if (i == 1) {
            a(3);
            this.a = 0;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected BEGIN_OBJECT but was ");
        stringBuilder.append(f());
        stringBuilder.append(x());
        throw new IllegalStateException(stringBuilder.toString());
    }

    public void d() throws IOException {
        int i = this.a;
        if (i == 0) {
            i = r();
        }
        if (i == 2) {
            this.n--;
            this.o[this.n] = null;
            int[] iArr = this.p;
            int i2 = this.n - 1;
            iArr[i2] = iArr[i2] + 1;
            this.a = 0;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected END_OBJECT but was ");
        stringBuilder.append(f());
        stringBuilder.append(x());
        throw new IllegalStateException(stringBuilder.toString());
    }

    public boolean e() throws IOException {
        int i = this.a;
        if (i == 0) {
            i = r();
        }
        return (i == 2 || i == 4) ? false : true;
    }

    public hy f() throws IOException {
        int i = this.a;
        if (i == 0) {
            i = r();
        }
        switch (i) {
            case 1:
                return hy.BEGIN_OBJECT;
            case 2:
                return hy.END_OBJECT;
            case 3:
                return hy.BEGIN_ARRAY;
            case 4:
                return hy.END_ARRAY;
            case 5:
            case 6:
                return hy.BOOLEAN;
            case 7:
                return hy.NULL;
            case 8:
            case 9:
            case 10:
            case 11:
                return hy.STRING;
            case 12:
            case 13:
            case 14:
                return hy.NAME;
            case 15:
            case 16:
                return hy.NUMBER;
            case 17:
                return hy.END_DOCUMENT;
            default:
                throw new AssertionError();
        }
    }

    int r() throws IOException {
        int b;
        int i = this.m[this.n - 1];
        if (i == 1) {
            this.m[this.n - 1] = 2;
        } else if (i == 2) {
            b = b(true);
            if (b != 44) {
                if (b == 59) {
                    v();
                } else if (b == 93) {
                    this.a = 4;
                    return 4;
                } else {
                    throw b("Unterminated array");
                }
            }
        } else {
            int b2;
            if (i != 3) {
                if (i != 5) {
                    if (i == 4) {
                        this.m[this.n - 1] = 5;
                        b = b(true);
                        if (b != 58) {
                            if (b == 61) {
                                v();
                                if ((this.f < this.g || b(1)) && this.e[this.f] == '>') {
                                    this.f++;
                                }
                            } else {
                                throw b("Expected ':'");
                            }
                        }
                    } else if (i == 6) {
                        if (this.d) {
                            z();
                        }
                        this.m[this.n - 1] = 7;
                    } else if (i == 7) {
                        if (b(false) == -1) {
                            this.a = 17;
                            return 17;
                        }
                        v();
                        this.f--;
                    } else if (i == 8) {
                        throw new IllegalStateException("JsonReader is closed");
                    }
                }
            }
            this.m[this.n - 1] = 4;
            if (i == 5) {
                b2 = b(true);
                if (b2 != 44) {
                    if (b2 == 59) {
                        v();
                    } else if (b2 == 125) {
                        this.a = 2;
                        return 2;
                    } else {
                        throw b("Unterminated object");
                    }
                }
            }
            b2 = b(true);
            if (b2 == 34) {
                this.a = 13;
                return 13;
            } else if (b2 == 39) {
                v();
                this.a = 12;
                return 12;
            } else if (b2 != 125) {
                v();
                this.f--;
                if (a((char) b2)) {
                    this.a = 14;
                    return 14;
                }
                throw b("Expected name");
            } else if (i != 5) {
                this.a = 2;
                return 2;
            } else {
                throw b("Expected name");
            }
        }
        b = b(true);
        if (b == 34) {
            this.a = 9;
            return 9;
        } else if (b != 39) {
            if (!(b == 44 || b == 59)) {
                if (b == 91) {
                    this.a = 3;
                    return 3;
                } else if (b != 93) {
                    if (b != 123) {
                        this.f--;
                        i = o();
                        if (i != 0) {
                            return i;
                        }
                        i = s();
                        if (i != 0) {
                            return i;
                        }
                        if (a(this.e[this.f])) {
                            v();
                            this.a = 10;
                            return 10;
                        }
                        throw b("Expected value");
                    }
                    this.a = 1;
                    return 1;
                } else if (i == 1) {
                    this.a = 4;
                    return 4;
                }
            }
            if (i != 1) {
                if (i != 2) {
                    throw b("Unexpected value");
                }
            }
            v();
            this.f--;
            this.a = 7;
            return 7;
        } else {
            v();
            this.a = 8;
            return 8;
        }
    }

    private int o() throws IOException {
        String str;
        String str2;
        int i;
        int length;
        int i2;
        char c;
        char c2 = this.e[this.f];
        if (c2 != 't') {
            if (c2 != 'T') {
                if (c2 != 'f') {
                    if (c2 != 'F') {
                        if (c2 != 'n') {
                            if (c2 != 'N') {
                                return 0;
                            }
                        }
                        str = "null";
                        str2 = "NULL";
                        i = 7;
                        length = str.length();
                        i2 = 1;
                        while (i2 < length) {
                            if (this.f + i2 < this.g && !b(i2 + 1)) {
                                return 0;
                            }
                            c = this.e[this.f + i2];
                            if (c != str.charAt(i2) && c != r2.charAt(i2)) {
                                return 0;
                            }
                            i2++;
                        }
                        if ((this.f + length >= this.g || b(length + 1)) && a(this.e[this.f + length])) {
                            return 0;
                        }
                        this.f += length;
                        this.a = i;
                        return i;
                    }
                }
                str = "false";
                str2 = "FALSE";
                i = 6;
                length = str.length();
                i2 = 1;
                while (i2 < length) {
                    if (this.f + i2 < this.g) {
                    }
                    c = this.e[this.f + i2];
                    if (c != str.charAt(i2)) {
                    }
                    i2++;
                }
                if (this.f + length >= this.g) {
                }
                return 0;
            }
        }
        str = "true";
        str2 = "TRUE";
        i = 5;
        length = str.length();
        i2 = 1;
        while (i2 < length) {
            if (this.f + i2 < this.g) {
            }
            c = this.e[this.f + i2];
            if (c != str.charAt(i2)) {
            }
            i2++;
        }
        if (this.f + length >= this.g) {
        }
        return 0;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int s() throws java.io.IOException {
        /*
        r18 = this;
        r0 = r18;
        r1 = r0.e;
        r2 = r0.f;
        r3 = r0.g;
        r6 = 1;
        r7 = 0;
        r8 = r3;
        r3 = 0;
        r9 = 0;
        r10 = 1;
        r11 = 0;
        r13 = 0;
    L_0x0011:
        r14 = r2 + r3;
        r15 = 2;
        if (r14 != r8) goto L_0x0028;
    L_0x0016:
        r2 = r1.length;
        if (r3 != r2) goto L_0x001a;
    L_0x0019:
        return r7;
    L_0x001a:
        r2 = r3 + 1;
        r2 = r0.b(r2);
        if (r2 != 0) goto L_0x0024;
    L_0x0022:
        goto L_0x009b;
    L_0x0024:
        r2 = r0.f;
        r8 = r0.g;
    L_0x0028:
        r14 = r2 + r3;
        r14 = r1[r14];
        r7 = 43;
        r4 = 3;
        r5 = 5;
        if (r14 == r7) goto L_0x00ec;
    L_0x0032:
        r7 = 69;
        if (r14 == r7) goto L_0x00e0;
    L_0x0036:
        r7 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        if (r14 == r7) goto L_0x00e0;
    L_0x003a:
        switch(r14) {
            case 45: goto L_0x00d3;
            case 46: goto L_0x00cb;
            default: goto L_0x003d;
        };
    L_0x003d:
        r7 = 48;
        if (r14 < r7) goto L_0x0095;
    L_0x0041:
        r7 = 57;
        if (r14 <= r7) goto L_0x0046;
    L_0x0045:
        goto L_0x0095;
    L_0x0046:
        if (r9 == r6) goto L_0x008b;
    L_0x0048:
        if (r9 != 0) goto L_0x004b;
    L_0x004a:
        goto L_0x008b;
    L_0x004b:
        if (r9 != r15) goto L_0x0076;
    L_0x004d:
        r16 = 0;
        r4 = (r11 > r16 ? 1 : (r11 == r16 ? 0 : -1));
        if (r4 != 0) goto L_0x0055;
    L_0x0053:
        r4 = 0;
        return r4;
    L_0x0055:
        r4 = 10;
        r4 = r4 * r11;
        r14 = r14 + -48;
        r14 = (long) r14;
        r4 = r4 - r14;
        r14 = -922337203685477580; // 0xf333333333333334 float:4.1723254E-8 double:-8.390303882365713E246;
        r7 = (r11 > r14 ? 1 : (r11 == r14 ? 0 : -1));
        if (r7 > 0) goto L_0x0071;
    L_0x0066:
        r7 = (r11 > r14 ? 1 : (r11 == r14 ? 0 : -1));
        if (r7 != 0) goto L_0x006f;
    L_0x006a:
        r7 = (r4 > r11 ? 1 : (r4 == r11 ? 0 : -1));
        if (r7 >= 0) goto L_0x006f;
    L_0x006e:
        goto L_0x0071;
    L_0x006f:
        r7 = 0;
        goto L_0x0072;
    L_0x0071:
        r7 = 1;
    L_0x0072:
        r7 = r7 & r10;
        r11 = r4;
        r10 = r7;
        goto L_0x0084;
    L_0x0076:
        r16 = 0;
        if (r9 != r4) goto L_0x007e;
    L_0x007a:
        r7 = 0;
        r9 = 4;
        goto L_0x00f3;
    L_0x007e:
        if (r9 == r5) goto L_0x0087;
    L_0x0080:
        r4 = 6;
        if (r9 != r4) goto L_0x0084;
    L_0x0083:
        goto L_0x0087;
    L_0x0084:
        r7 = 0;
        goto L_0x00f3;
    L_0x0087:
        r7 = 0;
        r9 = 7;
        goto L_0x00f3;
    L_0x008b:
        r16 = 0;
        r14 = r14 + -48;
        r4 = -r14;
        r4 = (long) r4;
        r11 = r4;
        r7 = 0;
        r9 = 2;
        goto L_0x00f3;
    L_0x0095:
        r1 = r0.a(r14);
        if (r1 != 0) goto L_0x00c9;
    L_0x009b:
        if (r9 != r15) goto L_0x00b7;
    L_0x009d:
        if (r10 == 0) goto L_0x00b7;
    L_0x009f:
        r1 = -9223372036854775808;
        r4 = (r11 > r1 ? 1 : (r11 == r1 ? 0 : -1));
        if (r4 != 0) goto L_0x00a7;
    L_0x00a5:
        if (r13 == 0) goto L_0x00b7;
    L_0x00a7:
        if (r13 == 0) goto L_0x00aa;
    L_0x00a9:
        goto L_0x00ab;
    L_0x00aa:
        r11 = -r11;
    L_0x00ab:
        r0.j = r11;
        r1 = r0.f;
        r1 = r1 + r3;
        r0.f = r1;
        r1 = 15;
        r0.a = r1;
        return r1;
    L_0x00b7:
        if (r9 == r15) goto L_0x00c2;
    L_0x00b9:
        r1 = 4;
        if (r9 == r1) goto L_0x00c2;
    L_0x00bc:
        r1 = 7;
        if (r9 != r1) goto L_0x00c0;
    L_0x00bf:
        goto L_0x00c2;
    L_0x00c0:
        r7 = 0;
        return r7;
    L_0x00c2:
        r0.k = r3;
        r1 = 16;
        r0.a = r1;
        return r1;
    L_0x00c9:
        r7 = 0;
        return r7;
    L_0x00cb:
        r7 = 0;
        r16 = 0;
        if (r9 != r15) goto L_0x00d2;
    L_0x00d0:
        r9 = 3;
        goto L_0x00f3;
    L_0x00d2:
        return r7;
    L_0x00d3:
        r4 = 6;
        r7 = 0;
        r16 = 0;
        if (r9 != 0) goto L_0x00dc;
    L_0x00d9:
        r9 = 1;
        r13 = 1;
        goto L_0x00f3;
    L_0x00dc:
        if (r9 != r5) goto L_0x00df;
    L_0x00de:
        goto L_0x00f2;
    L_0x00df:
        return r7;
    L_0x00e0:
        r7 = 0;
        r16 = 0;
        if (r9 == r15) goto L_0x00ea;
    L_0x00e5:
        r4 = 4;
        if (r9 != r4) goto L_0x00e9;
    L_0x00e8:
        goto L_0x00ea;
    L_0x00e9:
        return r7;
    L_0x00ea:
        r9 = 5;
        goto L_0x00f3;
    L_0x00ec:
        r4 = 6;
        r7 = 0;
        r16 = 0;
        if (r9 != r5) goto L_0x00f7;
    L_0x00f2:
        r9 = 6;
    L_0x00f3:
        r3 = r3 + 1;
        goto L_0x0011;
    L_0x00f7:
        return r7;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.hx.s():int");
    }

    private boolean a(char c) throws IOException {
        switch (c) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
            case ',':
            case ':':
            case '[':
            case ']':
            case '{':
            case '}':
                break;
            case '#':
            case '/':
            case ';':
            case '=':
            case '\\':
                v();
                break;
            default:
                return true;
        }
        return false;
    }

    public String g() throws IOException {
        String t;
        int i = this.a;
        if (i == 0) {
            i = r();
        }
        if (i == 14) {
            t = t();
        } else if (i == 12) {
            t = b('\'');
        } else if (i == 13) {
            t = b('\"');
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Expected a name but was ");
            stringBuilder.append(f());
            stringBuilder.append(x());
            throw new IllegalStateException(stringBuilder.toString());
        }
        this.a = 0;
        this.o[this.n - 1] = t;
        return t;
    }

    public String h() throws IOException {
        String t;
        int i = this.a;
        if (i == 0) {
            i = r();
        }
        if (i == 10) {
            t = t();
        } else if (i == 8) {
            t = b('\'');
        } else if (i == 9) {
            t = b('\"');
        } else if (i == 11) {
            t = this.l;
            this.l = null;
        } else if (i == 15) {
            t = Long.toString(this.j);
        } else if (i == 16) {
            t = new String(this.e, this.f, this.k);
            this.f += this.k;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Expected a string but was ");
            stringBuilder.append(f());
            stringBuilder.append(x());
            throw new IllegalStateException(stringBuilder.toString());
        }
        this.a = 0;
        int[] iArr = this.p;
        int i2 = this.n - 1;
        iArr[i2] = iArr[i2] + 1;
        return t;
    }

    public boolean i() throws IOException {
        int i = this.a;
        if (i == 0) {
            i = r();
        }
        int[] iArr;
        int i2;
        if (i == 5) {
            this.a = 0;
            iArr = this.p;
            i2 = this.n - 1;
            iArr[i2] = iArr[i2] + 1;
            return true;
        } else if (i == 6) {
            this.a = 0;
            iArr = this.p;
            i2 = this.n - 1;
            iArr[i2] = iArr[i2] + 1;
            return false;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Expected a boolean but was ");
            stringBuilder.append(f());
            stringBuilder.append(x());
            throw new IllegalStateException(stringBuilder.toString());
        }
    }

    public void j() throws IOException {
        int i = this.a;
        if (i == 0) {
            i = r();
        }
        if (i == 7) {
            this.a = 0;
            int[] iArr = this.p;
            int i2 = this.n - 1;
            iArr[i2] = iArr[i2] + 1;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected null but was ");
        stringBuilder.append(f());
        stringBuilder.append(x());
        throw new IllegalStateException(stringBuilder.toString());
    }

    public double k() throws IOException {
        int i = this.a;
        if (i == 0) {
            i = r();
        }
        if (i == 15) {
            this.a = 0;
            int[] iArr = this.p;
            int i2 = this.n - 1;
            iArr[i2] = iArr[i2] + 1;
            return (double) this.j;
        }
        if (i == 16) {
            this.l = new String(this.e, this.f, this.k);
            this.f += this.k;
        } else {
            if (i != 8) {
                if (i != 9) {
                    if (i == 10) {
                        this.l = t();
                    } else if (i != 11) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Expected a double but was ");
                        stringBuilder.append(f());
                        stringBuilder.append(x());
                        throw new IllegalStateException(stringBuilder.toString());
                    }
                }
            }
            this.l = b(i == 8 ? '\'' : '\"');
        }
        this.a = 11;
        double parseDouble = Double.parseDouble(this.l);
        if (!this.d) {
            if (Double.isNaN(parseDouble) || Double.isInfinite(parseDouble)) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("JSON forbids NaN and infinities: ");
                stringBuilder2.append(parseDouble);
                stringBuilder2.append(x());
                throw new ia(stringBuilder2.toString());
            }
        }
        this.l = null;
        this.a = 0;
        int[] iArr2 = this.p;
        int i3 = this.n - 1;
        iArr2[i3] = iArr2[i3] + 1;
        return parseDouble;
    }

    public long l() throws java.io.IOException {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r8 = this;
        r0 = r8.a;
        if (r0 != 0) goto L_0x0008;
    L_0x0004:
        r0 = r8.r();
    L_0x0008:
        r1 = 15;
        r2 = 0;
        if (r0 != r1) goto L_0x001e;
    L_0x000d:
        r8.a = r2;
        r0 = r8.p;
        r1 = r8.n;
        r1 = r1 + -1;
        r2 = r0[r1];
        r2 = r2 + 1;
        r0[r1] = r2;
        r0 = r8.j;
        return r0;
    L_0x001e:
        r1 = 16;
        if (r0 != r1) goto L_0x0037;
    L_0x0022:
        r0 = new java.lang.String;
        r1 = r8.e;
        r3 = r8.f;
        r4 = r8.k;
        r0.<init>(r1, r3, r4);
        r8.l = r0;
        r0 = r8.f;
        r1 = r8.k;
        r0 = r0 + r1;
        r8.f = r0;
        goto L_0x0091;
    L_0x0037:
        r1 = 10;
        r3 = 8;
        if (r0 == r3) goto L_0x0066;
    L_0x003d:
        r4 = 9;
        if (r0 == r4) goto L_0x0066;
    L_0x0041:
        if (r0 != r1) goto L_0x0044;
    L_0x0043:
        goto L_0x0066;
    L_0x0044:
        r0 = new java.lang.IllegalStateException;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "Expected a long but was ";
        r1.append(r2);
        r2 = r8.f();
        r1.append(r2);
        r2 = r8.x();
        r1.append(r2);
        r1 = r1.toString();
        r0.<init>(r1);
        throw r0;
    L_0x0066:
        if (r0 != r1) goto L_0x006f;
    L_0x0068:
        r0 = r8.t();
        r8.l = r0;
        goto L_0x007c;
    L_0x006f:
        if (r0 != r3) goto L_0x0074;
    L_0x0071:
        r0 = 39;
        goto L_0x0076;
    L_0x0074:
        r0 = 34;
    L_0x0076:
        r0 = r8.b(r0);
        r8.l = r0;
    L_0x007c:
        r0 = r8.l;	 Catch:{ NumberFormatException -> 0x0091 }
        r0 = java.lang.Long.parseLong(r0);	 Catch:{ NumberFormatException -> 0x0091 }
        r8.a = r2;	 Catch:{ NumberFormatException -> 0x0091 }
        r3 = r8.p;	 Catch:{ NumberFormatException -> 0x0091 }
        r4 = r8.n;	 Catch:{ NumberFormatException -> 0x0091 }
        r4 = r4 + -1;	 Catch:{ NumberFormatException -> 0x0091 }
        r5 = r3[r4];	 Catch:{ NumberFormatException -> 0x0091 }
        r5 = r5 + 1;	 Catch:{ NumberFormatException -> 0x0091 }
        r3[r4] = r5;	 Catch:{ NumberFormatException -> 0x0091 }
        return r0;
    L_0x0091:
        r0 = 11;
        r8.a = r0;
        r0 = r8.l;
        r0 = java.lang.Double.parseDouble(r0);
        r3 = (long) r0;
        r5 = (double) r3;
        r7 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1));
        if (r7 != 0) goto L_0x00b3;
    L_0x00a1:
        r0 = 0;
        r8.l = r0;
        r8.a = r2;
        r0 = r8.p;
        r1 = r8.n;
        r1 = r1 + -1;
        r2 = r0[r1];
        r2 = r2 + 1;
        r0[r1] = r2;
        return r3;
    L_0x00b3:
        r0 = new java.lang.NumberFormatException;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "Expected a long but was ";
        r1.append(r2);
        r2 = r8.l;
        r1.append(r2);
        r2 = r8.x();
        r1.append(r2);
        r1 = r1.toString();
        r0.<init>(r1);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.hx.l():long");
    }

    private String b(char c) throws IOException {
        char[] cArr = this.e;
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            int i;
            int i2 = this.f;
            int i3 = this.g;
            while (true) {
                i = i2;
                while (i2 < i3) {
                    int i4 = i2 + 1;
                    char c2 = cArr[i2];
                    if (c2 == c) {
                        this.f = i4;
                        stringBuilder.append(cArr, i, (i4 - i) - 1);
                        return stringBuilder.toString();
                    } else if (c2 == '\\') {
                        this.f = i4;
                        stringBuilder.append(cArr, i, (i4 - i) - 1);
                        stringBuilder.append(y());
                        i2 = this.f;
                        i3 = this.g;
                    } else {
                        if (c2 == '\n') {
                            this.h++;
                            this.i = i4;
                        }
                        i2 = i4;
                    }
                }
                break;
            }
            stringBuilder.append(cArr, i, i2 - i);
            this.f = i2;
            if (!b(1)) {
                throw b("Unterminated string");
            }
        }
    }

    private String t() throws IOException {
        String str;
        int i = 0;
        StringBuilder stringBuilder = null;
        do {
            int i2 = 0;
            while (true) {
                if (this.f + i2 < this.g) {
                    switch (this.e[this.f + i2]) {
                        case '\t':
                        case '\n':
                        case '\f':
                        case '\r':
                        case ' ':
                        case ',':
                        case ':':
                        case '[':
                        case ']':
                        case '{':
                        case '}':
                            break;
                        case '#':
                        case '/':
                        case ';':
                        case '=':
                        case '\\':
                            v();
                            break;
                        default:
                            i2++;
                            break;
                    }
                } else if (i2 >= this.e.length) {
                    if (stringBuilder == null) {
                        stringBuilder = new StringBuilder();
                    }
                    stringBuilder.append(this.e, this.f, i2);
                    this.f += i2;
                } else if (b(i2 + 1)) {
                }
                i = i2;
                if (stringBuilder != null) {
                    str = new String(this.e, this.f, i);
                } else {
                    stringBuilder.append(this.e, this.f, i);
                    str = stringBuilder.toString();
                }
                this.f += i;
                return str;
            }
        } while (b(1));
        if (stringBuilder != null) {
            stringBuilder.append(this.e, this.f, i);
            str = stringBuilder.toString();
        } else {
            str = new String(this.e, this.f, i);
        }
        this.f += i;
        return str;
    }

    private void c(char c) throws IOException {
        char[] cArr = this.e;
        while (true) {
            int i = this.f;
            int i2 = this.g;
            while (i < i2) {
                int i3 = i + 1;
                char c2 = cArr[i];
                if (c2 == c) {
                    this.f = i3;
                    return;
                } else if (c2 == '\\') {
                    this.f = i3;
                    y();
                    i = this.f;
                    i2 = this.g;
                } else {
                    if (c2 == '\n') {
                        this.h++;
                        this.i = i3;
                    }
                    i = i3;
                }
            }
            this.f = i;
            if (!b(1)) {
                throw b("Unterminated string");
            }
        }
    }

    private void u() throws IOException {
        do {
            int i = 0;
            while (this.f + i < this.g) {
                switch (this.e[this.f + i]) {
                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                    case ' ':
                    case ',':
                    case ':':
                    case '[':
                    case ']':
                    case '{':
                    case '}':
                        break;
                    case '#':
                    case '/':
                    case ';':
                    case '=':
                    case '\\':
                        v();
                        break;
                    default:
                        i++;
                }
                this.f += i;
                return;
            }
            this.f += i;
        } while (b(1));
    }

    public int m() throws java.io.IOException {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r7 = this;
        r0 = r7.a;
        if (r0 != 0) goto L_0x0008;
    L_0x0004:
        r0 = r7.r();
    L_0x0008:
        r1 = 15;
        r2 = 0;
        if (r0 != r1) goto L_0x0046;
    L_0x000d:
        r0 = r7.j;
        r0 = (int) r0;
        r3 = r7.j;
        r5 = (long) r0;
        r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1));
        if (r1 != 0) goto L_0x0026;
    L_0x0017:
        r7.a = r2;
        r1 = r7.p;
        r2 = r7.n;
        r2 = r2 + -1;
        r3 = r1[r2];
        r3 = r3 + 1;
        r1[r2] = r3;
        return r0;
    L_0x0026:
        r0 = new java.lang.NumberFormatException;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "Expected an int but was ";
        r1.append(r2);
        r2 = r7.j;
        r1.append(r2);
        r2 = r7.x();
        r1.append(r2);
        r1 = r1.toString();
        r0.<init>(r1);
        throw r0;
    L_0x0046:
        r1 = 16;
        if (r0 != r1) goto L_0x005f;
    L_0x004a:
        r0 = new java.lang.String;
        r1 = r7.e;
        r3 = r7.f;
        r4 = r7.k;
        r0.<init>(r1, r3, r4);
        r7.l = r0;
        r0 = r7.f;
        r1 = r7.k;
        r0 = r0 + r1;
        r7.f = r0;
        goto L_0x00b9;
    L_0x005f:
        r1 = 10;
        r3 = 8;
        if (r0 == r3) goto L_0x008e;
    L_0x0065:
        r4 = 9;
        if (r0 == r4) goto L_0x008e;
    L_0x0069:
        if (r0 != r1) goto L_0x006c;
    L_0x006b:
        goto L_0x008e;
    L_0x006c:
        r0 = new java.lang.IllegalStateException;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "Expected an int but was ";
        r1.append(r2);
        r2 = r7.f();
        r1.append(r2);
        r2 = r7.x();
        r1.append(r2);
        r1 = r1.toString();
        r0.<init>(r1);
        throw r0;
    L_0x008e:
        if (r0 != r1) goto L_0x0097;
    L_0x0090:
        r0 = r7.t();
        r7.l = r0;
        goto L_0x00a4;
    L_0x0097:
        if (r0 != r3) goto L_0x009c;
    L_0x0099:
        r0 = 39;
        goto L_0x009e;
    L_0x009c:
        r0 = 34;
    L_0x009e:
        r0 = r7.b(r0);
        r7.l = r0;
    L_0x00a4:
        r0 = r7.l;	 Catch:{ NumberFormatException -> 0x00b9 }
        r0 = java.lang.Integer.parseInt(r0);	 Catch:{ NumberFormatException -> 0x00b9 }
        r7.a = r2;	 Catch:{ NumberFormatException -> 0x00b9 }
        r1 = r7.p;	 Catch:{ NumberFormatException -> 0x00b9 }
        r3 = r7.n;	 Catch:{ NumberFormatException -> 0x00b9 }
        r3 = r3 + -1;	 Catch:{ NumberFormatException -> 0x00b9 }
        r4 = r1[r3];	 Catch:{ NumberFormatException -> 0x00b9 }
        r4 = r4 + 1;	 Catch:{ NumberFormatException -> 0x00b9 }
        r1[r3] = r4;	 Catch:{ NumberFormatException -> 0x00b9 }
        return r0;
    L_0x00b9:
        r0 = 11;
        r7.a = r0;
        r0 = r7.l;
        r0 = java.lang.Double.parseDouble(r0);
        r3 = (int) r0;
        r4 = (double) r3;
        r6 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1));
        if (r6 != 0) goto L_0x00db;
    L_0x00c9:
        r0 = 0;
        r7.l = r0;
        r7.a = r2;
        r0 = r7.p;
        r1 = r7.n;
        r1 = r1 + -1;
        r2 = r0[r1];
        r2 = r2 + 1;
        r0[r1] = r2;
        return r3;
    L_0x00db:
        r0 = new java.lang.NumberFormatException;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "Expected an int but was ";
        r1.append(r2);
        r2 = r7.l;
        r1.append(r2);
        r2 = r7.x();
        r1.append(r2);
        r1 = r1.toString();
        r0.<init>(r1);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.hx.m():int");
    }

    public void close() throws IOException {
        this.a = 0;
        this.m[0] = 8;
        this.n = 1;
        this.c.close();
    }

    public void n() throws IOException {
        int i = 0;
        do {
            int i2 = this.a;
            if (i2 == 0) {
                i2 = r();
            }
            if (i2 == 3) {
                a(1);
                i++;
            } else if (i2 == 1) {
                a(3);
                i++;
            } else if (i2 == 4) {
                this.n--;
                i--;
            } else if (i2 == 2) {
                this.n--;
                i--;
            } else {
                if (i2 != 14) {
                    if (i2 != 10) {
                        if (i2 != 8) {
                            if (i2 != 12) {
                                if (i2 != 9) {
                                    if (i2 != 13) {
                                        if (i2 == 16) {
                                            this.f += this.k;
                                        }
                                    }
                                }
                                c('\"');
                            }
                        }
                        c('\'');
                    }
                }
                u();
            }
            this.a = 0;
        } while (i != 0);
        int[] iArr = this.p;
        i = this.n - 1;
        iArr[i] = iArr[i] + 1;
        this.o[this.n - 1] = "null";
    }

    private void a(int i) {
        if (this.n == this.m.length) {
            Object obj = new int[(this.n * 2)];
            Object obj2 = new int[(this.n * 2)];
            Object obj3 = new String[(this.n * 2)];
            System.arraycopy(this.m, 0, obj, 0, this.n);
            System.arraycopy(this.p, 0, obj2, 0, this.n);
            System.arraycopy(this.o, 0, obj3, 0, this.n);
            this.m = obj;
            this.p = obj2;
            this.o = obj3;
        }
        int[] iArr = this.m;
        int i2 = this.n;
        this.n = i2 + 1;
        iArr[i2] = i;
    }

    private boolean b(int i) throws IOException {
        Object obj = this.e;
        this.i -= this.f;
        if (this.g != this.f) {
            this.g -= this.f;
            System.arraycopy(obj, this.f, obj, 0, this.g);
        } else {
            this.g = 0;
        }
        this.f = 0;
        do {
            int read = this.c.read(obj, this.g, obj.length - this.g);
            if (read == -1) {
                return false;
            }
            this.g += read;
            if (this.h == 0 && this.i == 0 && this.g > 0 && obj[0] == '﻿') {
                this.f++;
                this.i++;
                i++;
            }
        } while (this.g < i);
        return true;
    }

    private int b(boolean z) throws IOException {
        char[] cArr = this.e;
        int i = this.f;
        int i2 = this.g;
        while (true) {
            if (i == i2) {
                this.f = i;
                if (!b(1)) {
                    break;
                }
                i = this.f;
                i2 = this.g;
            }
            int i3 = i + 1;
            char c = cArr[i];
            if (c == '\n') {
                this.h++;
                this.i = i3;
            } else if (!(c == ' ' || c == '\r')) {
                if (c != '\t') {
                    if (c == '/') {
                        this.f = i3;
                        if (i3 == i2) {
                            this.f--;
                            boolean b = b(2);
                            this.f++;
                            if (!b) {
                                return c;
                            }
                        }
                        v();
                        char c2 = cArr[this.f];
                        if (c2 == '*') {
                            this.f++;
                            if (a("*/")) {
                                i = this.f + 2;
                                i2 = this.g;
                            } else {
                                throw b("Unterminated comment");
                            }
                        } else if (c2 != '/') {
                            return c;
                        } else {
                            this.f++;
                            w();
                            i = this.f;
                            i2 = this.g;
                        }
                    } else if (c == '#') {
                        this.f = i3;
                        v();
                        w();
                        i = this.f;
                        i2 = this.g;
                    } else {
                        this.f = i3;
                        return c;
                    }
                }
            }
            i = i3;
        }
        if (!z) {
            return true;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("End of input");
        stringBuilder.append(x());
        throw new EOFException(stringBuilder.toString());
    }

    private void v() throws IOException {
        if (!this.d) {
            throw b("Use JsonReader.setLenient(true) to accept malformed JSON");
        }
    }

    private void w() throws IOException {
        char c;
        do {
            if (this.f < this.g || b(1)) {
                char[] cArr = this.e;
                int i = this.f;
                this.f = i + 1;
                c = cArr[i];
                if (c == '\n') {
                    this.h++;
                    this.i = this.f;
                    return;
                }
            } else {
                return;
            }
        } while (c != '\r');
    }

    private boolean a(String str) throws IOException {
        while (true) {
            int i = 0;
            if (this.f + str.length() > this.g) {
                if (!b(str.length())) {
                    return false;
                }
            }
            if (this.e[this.f] == '\n') {
                this.h++;
                this.i = this.f + 1;
            } else {
                while (i < str.length()) {
                    if (this.e[this.f + i] == str.charAt(i)) {
                        i++;
                    }
                }
                return true;
            }
            this.f++;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClass().getSimpleName());
        stringBuilder.append(x());
        return stringBuilder.toString();
    }

    private String x() {
        int i = this.h + 1;
        int i2 = (this.f - this.i) + 1;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" at line ");
        stringBuilder.append(i);
        stringBuilder.append(" column ");
        stringBuilder.append(i2);
        stringBuilder.append(" path ");
        stringBuilder.append(p());
        return stringBuilder.toString();
    }

    public String p() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('$');
        int i = this.n;
        for (int i2 = 0; i2 < i; i2++) {
            switch (this.m[i2]) {
                case 1:
                case 2:
                    stringBuilder.append('[');
                    stringBuilder.append(this.p[i2]);
                    stringBuilder.append(']');
                    break;
                case 3:
                case 4:
                case 5:
                    stringBuilder.append('.');
                    if (this.o[i2] == null) {
                        break;
                    }
                    stringBuilder.append(this.o[i2]);
                    break;
                default:
                    break;
            }
        }
        return stringBuilder.toString();
    }

    private char y() throws IOException {
        if (this.f == this.g) {
            if (!b(1)) {
                throw b("Unterminated escape sequence");
            }
        }
        char[] cArr = this.e;
        int i = this.f;
        this.f = i + 1;
        char c = cArr[i];
        if (c == '\n') {
            this.h++;
            this.i = this.f;
        } else if (!(c == '\"' || c == '\'' || c == '/' || c == '\\')) {
            if (c == 'b') {
                return '\b';
            }
            if (c == 'f') {
                return '\f';
            }
            if (c == 'n') {
                return '\n';
            }
            if (c == 'r') {
                return '\r';
            }
            switch (c) {
                case 't':
                    return '\t';
                case 'u':
                    if (this.f + 4 > this.g) {
                        if (!b(4)) {
                            throw b("Unterminated escape sequence");
                        }
                    }
                    c = '\u0000';
                    int i2 = this.f;
                    int i3 = i2 + 4;
                    while (i2 < i3) {
                        char c2 = this.e[i2];
                        c = (char) (c << 4);
                        if (c2 >= '0' && c2 <= '9') {
                            c = (char) (c + (c2 - 48));
                        } else if (c2 >= 'a' && c2 <= 'f') {
                            c = (char) (c + ((c2 - 97) + 10));
                        } else if (c2 < 'A' || c2 > 'F') {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("\\u");
                            stringBuilder.append(new String(this.e, this.f, 4));
                            throw new NumberFormatException(stringBuilder.toString());
                        } else {
                            c = (char) (c + ((c2 - 65) + 10));
                        }
                        i2++;
                    }
                    this.f += 4;
                    return c;
                default:
                    throw b("Invalid escape sequence");
            }
        }
        return c;
    }

    private IOException b(String str) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append(x());
        throw new ia(stringBuilder.toString());
    }

    private void z() throws IOException {
        b(true);
        this.f--;
        if (this.f + b.length <= this.g || b(b.length)) {
            int i = 0;
            while (i < b.length) {
                if (this.e[this.f + i] == b[i]) {
                    i++;
                } else {
                    return;
                }
            }
            this.f += b.length;
        }
    }

    static {
        ha.a = new ha() {
            public void a(hx hxVar) throws IOException {
                if (hxVar instanceof hl) {
                    ((hl) hxVar).o();
                    return;
                }
                int i = hxVar.a;
                if (i == 0) {
                    i = hxVar.r();
                }
                if (i == 13) {
                    hxVar.a = 9;
                } else if (i == 12) {
                    hxVar.a = 8;
                } else if (i == 14) {
                    hxVar.a = 10;
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Expected a name but was ");
                    stringBuilder.append(hxVar.f());
                    stringBuilder.append(hxVar.x());
                    throw new IllegalStateException(stringBuilder.toString());
                }
            }
        };
    }
}
