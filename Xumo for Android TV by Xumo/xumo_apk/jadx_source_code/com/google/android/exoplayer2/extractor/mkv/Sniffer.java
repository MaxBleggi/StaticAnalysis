package com.google.android.exoplayer2.extractor.mkv;

import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.IOException;

final class Sniffer {
    private static final int ID_EBML = 440786851;
    private static final int SEARCH_LENGTH = 1024;
    private int peekLength;
    private final ParsableByteArray scratch = new ParsableByteArray(8);

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean sniff(com.google.android.exoplayer2.extractor.ExtractorInput r19) throws java.io.IOException, java.lang.InterruptedException {
        /*
        r18 = this;
        r0 = r18;
        r1 = r19;
        r2 = r19.getLength();
        r4 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r6 = -1;
        r8 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
        if (r8 == 0) goto L_0x0016;
    L_0x0010:
        r8 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r8 <= 0) goto L_0x0015;
    L_0x0014:
        goto L_0x0016;
    L_0x0015:
        r4 = r2;
    L_0x0016:
        r4 = (int) r4;
        r5 = r0.scratch;
        r5 = r5.data;
        r8 = 4;
        r9 = 0;
        r1.peekFully(r5, r9, r8);
        r5 = r0.scratch;
        r10 = r5.readUnsignedInt();
        r0.peekLength = r8;
    L_0x0028:
        r12 = 440786851; // 0x1a45dfa3 float:4.0919297E-23 double:2.1777764E-315;
        r5 = 1;
        r8 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1));
        if (r8 == 0) goto L_0x0050;
    L_0x0030:
        r8 = r0.peekLength;
        r8 = r8 + r5;
        r0.peekLength = r8;
        if (r8 != r4) goto L_0x0038;
    L_0x0037:
        return r9;
    L_0x0038:
        r8 = r0.scratch;
        r8 = r8.data;
        r1.peekFully(r8, r9, r5);
        r5 = 8;
        r10 = r10 << r5;
        r12 = -256; // 0xffffffffffffff00 float:NaN double:NaN;
        r10 = r10 & r12;
        r5 = r0.scratch;
        r5 = r5.data;
        r5 = r5[r9];
        r5 = r5 & 255;
        r12 = (long) r5;
        r10 = r10 | r12;
        goto L_0x0028;
    L_0x0050:
        r10 = r18.readUint(r19);
        r4 = r0.peekLength;
        r12 = (long) r4;
        r14 = -9223372036854775808;
        r4 = (r10 > r14 ? 1 : (r10 == r14 ? 0 : -1));
        if (r4 == 0) goto L_0x00a5;
    L_0x005d:
        r4 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
        if (r4 == 0) goto L_0x0068;
    L_0x0061:
        r6 = r12 + r10;
        r4 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1));
        if (r4 < 0) goto L_0x0068;
    L_0x0067:
        goto L_0x00a5;
    L_0x0068:
        r2 = r0.peekLength;
        r2 = (long) r2;
        r6 = r12 + r10;
        r4 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
        if (r4 >= 0) goto L_0x009b;
    L_0x0071:
        r2 = r18.readUint(r19);
        r4 = (r2 > r14 ? 1 : (r2 == r14 ? 0 : -1));
        if (r4 != 0) goto L_0x007a;
    L_0x0079:
        return r9;
    L_0x007a:
        r2 = r18.readUint(r19);
        r6 = 0;
        r4 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
        if (r4 < 0) goto L_0x009a;
    L_0x0084:
        r16 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r4 = (r2 > r16 ? 1 : (r2 == r16 ? 0 : -1));
        if (r4 <= 0) goto L_0x008c;
    L_0x008b:
        goto L_0x009a;
    L_0x008c:
        r4 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
        if (r4 == 0) goto L_0x0068;
    L_0x0090:
        r2 = (int) r2;
        r1.advancePeekPosition(r2);
        r3 = r0.peekLength;
        r3 = r3 + r2;
        r0.peekLength = r3;
        goto L_0x0068;
    L_0x009a:
        return r9;
    L_0x009b:
        r1 = r0.peekLength;
        r1 = (long) r1;
        r3 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1));
        if (r3 != 0) goto L_0x00a3;
    L_0x00a2:
        goto L_0x00a4;
    L_0x00a3:
        r5 = 0;
    L_0x00a4:
        return r5;
    L_0x00a5:
        return r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mkv.Sniffer.sniff(com.google.android.exoplayer2.extractor.ExtractorInput):boolean");
    }

    private long readUint(ExtractorInput extractorInput) throws IOException, InterruptedException {
        int i = 0;
        extractorInput.peekFully(this.scratch.data, 0, 1);
        int i2 = this.scratch.data[0] & 255;
        if (i2 == 0) {
            return Long.MIN_VALUE;
        }
        int i3 = 128;
        int i4 = 0;
        while ((i2 & i3) == 0) {
            i3 >>= 1;
            i4++;
        }
        i2 &= i3 ^ -1;
        extractorInput.peekFully(this.scratch.data, 1, i4);
        while (i < i4) {
            i++;
            i2 = (this.scratch.data[i] & 255) + (i2 << 8);
        }
        this.peekLength += i4 + 1;
        return (long) i2;
    }
}
