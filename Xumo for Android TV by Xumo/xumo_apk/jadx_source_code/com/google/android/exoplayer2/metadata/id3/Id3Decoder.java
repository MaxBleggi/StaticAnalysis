package com.google.android.exoplayer2.metadata.id3;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataDecoder;
import com.google.android.exoplayer2.metadata.MetadataInputBuffer;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public final class Id3Decoder implements MetadataDecoder {
    private static final int FRAME_FLAG_V3_HAS_GROUP_IDENTIFIER = 32;
    private static final int FRAME_FLAG_V3_IS_COMPRESSED = 128;
    private static final int FRAME_FLAG_V3_IS_ENCRYPTED = 64;
    private static final int FRAME_FLAG_V4_HAS_DATA_LENGTH = 1;
    private static final int FRAME_FLAG_V4_HAS_GROUP_IDENTIFIER = 64;
    private static final int FRAME_FLAG_V4_IS_COMPRESSED = 8;
    private static final int FRAME_FLAG_V4_IS_ENCRYPTED = 4;
    private static final int FRAME_FLAG_V4_IS_UNSYNCHRONIZED = 2;
    public static final int ID3_HEADER_LENGTH = 10;
    public static final int ID3_TAG = Util.getIntegerCodeForString("ID3");
    private static final int ID3_TEXT_ENCODING_ISO_8859_1 = 0;
    private static final int ID3_TEXT_ENCODING_UTF_16 = 1;
    private static final int ID3_TEXT_ENCODING_UTF_16BE = 2;
    private static final int ID3_TEXT_ENCODING_UTF_8 = 3;
    public static final FramePredicate NO_FRAMES_PREDICATE = -$$Lambda$Id3Decoder$7M0gB-IGKaTbyTVX-WCb62bIHyc.INSTANCE;
    private static final String TAG = "Id3Decoder";
    @Nullable
    private final FramePredicate framePredicate;

    public interface FramePredicate {
        boolean evaluate(int i, int i2, int i3, int i4, int i5);
    }

    private static final class Id3Header {
        private final int framesSize;
        private final boolean isUnsynchronized;
        private final int majorVersion;

        public Id3Header(int i, boolean z, int i2) {
            this.majorVersion = i;
            this.isUnsynchronized = z;
            this.framesSize = i2;
        }
    }

    private static int delimiterLength(int i) {
        if (i != 0) {
            if (i != 3) {
                return 2;
            }
        }
        return 1;
    }

    private static String getCharsetName(int i) {
        switch (i) {
            case 1:
                return C.UTF16_NAME;
            case 2:
                return "UTF-16BE";
            case 3:
                return "UTF-8";
            default:
                return "ISO-8859-1";
        }
    }

    public Id3Decoder() {
        this(null);
    }

    public Id3Decoder(@Nullable FramePredicate framePredicate) {
        this.framePredicate = framePredicate;
    }

    @Nullable
    public Metadata decode(MetadataInputBuffer metadataInputBuffer) {
        metadataInputBuffer = metadataInputBuffer.data;
        return decode(metadataInputBuffer.array(), metadataInputBuffer.limit());
    }

    @Nullable
    public Metadata decode(byte[] bArr, int i) {
        List arrayList = new ArrayList();
        ParsableByteArray parsableByteArray = new ParsableByteArray(bArr, i);
        bArr = decodeHeader(parsableByteArray);
        if (bArr == null) {
            return null;
        }
        int position = parsableByteArray.getPosition();
        int i2 = bArr.majorVersion == 2 ? 6 : 10;
        int access$100 = bArr.framesSize;
        if (bArr.isUnsynchronized) {
            access$100 = removeUnsynchronization(parsableByteArray, bArr.framesSize);
        }
        parsableByteArray.setLimit(position + access$100);
        boolean z = false;
        if (!validateFrames(parsableByteArray, bArr.majorVersion, i2, false)) {
            if (bArr.majorVersion == 4 && validateFrames(parsableByteArray, 4, i2, true)) {
                z = true;
            } else {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to validate ID3 tag with majorVersion=");
                stringBuilder.append(bArr.majorVersion);
                Log.w(str, stringBuilder.toString());
                return null;
            }
        }
        while (parsableByteArray.bytesLeft() >= i2) {
            i = decodeFrame(bArr.majorVersion, parsableByteArray, z, i2, this.framePredicate);
            if (i != 0) {
                arrayList.add(i);
            }
        }
        return new Metadata(arrayList);
    }

    @Nullable
    private static Id3Header decodeHeader(ParsableByteArray parsableByteArray) {
        if (parsableByteArray.bytesLeft() < 10) {
            Log.w(TAG, "Data too short to be an ID3 tag");
            return null;
        }
        int readUnsignedInt24 = parsableByteArray.readUnsignedInt24();
        if (readUnsignedInt24 != ID3_TAG) {
            parsableByteArray = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected first three bytes of ID3 tag header: ");
            stringBuilder.append(readUnsignedInt24);
            Log.w(parsableByteArray, stringBuilder.toString());
            return null;
        }
        readUnsignedInt24 = parsableByteArray.readUnsignedByte();
        boolean z = true;
        parsableByteArray.skipBytes(1);
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        int readSynchSafeInt = parsableByteArray.readSynchSafeInt();
        if (readUnsignedInt24 == 2) {
            if (((readUnsignedByte & 64) != null ? true : null) != null) {
                Log.w(TAG, "Skipped ID3 tag with majorVersion=2 and undefined compression scheme");
                return null;
            }
        } else if (readUnsignedInt24 == 3) {
            if (((readUnsignedByte & 64) != 0 ? 1 : null) != null) {
                r1 = parsableByteArray.readInt();
                parsableByteArray.skipBytes(r1);
                readSynchSafeInt -= r1 + 4;
            }
        } else if (readUnsignedInt24 == 4) {
            if (((readUnsignedByte & 64) != 0 ? 1 : null) != null) {
                r1 = parsableByteArray.readSynchSafeInt();
                parsableByteArray.skipBytes(r1 - 4);
                readSynchSafeInt -= r1;
            }
            if (((readUnsignedByte & 16) != null ? true : null) != null) {
                readSynchSafeInt -= 10;
            }
        } else {
            parsableByteArray = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Skipped ID3 tag with unsupported majorVersion=");
            stringBuilder.append(readUnsignedInt24);
            Log.w(parsableByteArray, stringBuilder.toString());
            return null;
        }
        if (readUnsignedInt24 >= 4 || (readUnsignedByte & 128) == null) {
            z = false;
        }
        return new Id3Header(readUnsignedInt24, z, readSynchSafeInt);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean validateFrames(com.google.android.exoplayer2.util.ParsableByteArray r18, int r19, int r20, boolean r21) {
        /*
        r1 = r18;
        r0 = r19;
        r2 = r18.getPosition();
    L_0x0008:
        r3 = r18.bytesLeft();	 Catch:{ all -> 0x00b2 }
        r4 = 1;
        r5 = r20;
        if (r3 < r5) goto L_0x00ae;
    L_0x0011:
        r3 = 3;
        r6 = 0;
        if (r0 < r3) goto L_0x0022;
    L_0x0015:
        r7 = r18.readInt();	 Catch:{ all -> 0x00b2 }
        r8 = r18.readUnsignedInt();	 Catch:{ all -> 0x00b2 }
        r10 = r18.readUnsignedShort();	 Catch:{ all -> 0x00b2 }
        goto L_0x002c;
    L_0x0022:
        r7 = r18.readUnsignedInt24();	 Catch:{ all -> 0x00b2 }
        r8 = r18.readUnsignedInt24();	 Catch:{ all -> 0x00b2 }
        r8 = (long) r8;
        r10 = 0;
    L_0x002c:
        r11 = 0;
        if (r7 != 0) goto L_0x003a;
    L_0x0030:
        r7 = (r8 > r11 ? 1 : (r8 == r11 ? 0 : -1));
        if (r7 != 0) goto L_0x003a;
    L_0x0034:
        if (r10 != 0) goto L_0x003a;
    L_0x0036:
        r1.setPosition(r2);
        return r4;
    L_0x003a:
        r7 = 4;
        if (r0 != r7) goto L_0x006b;
    L_0x003d:
        if (r21 != 0) goto L_0x006b;
    L_0x003f:
        r13 = 8421504; // 0x808080 float:1.180104E-38 double:4.160776E-317;
        r13 = r13 & r8;
        r15 = (r13 > r11 ? 1 : (r13 == r11 ? 0 : -1));
        if (r15 == 0) goto L_0x004b;
    L_0x0047:
        r1.setPosition(r2);
        return r6;
    L_0x004b:
        r11 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        r13 = r8 & r11;
        r15 = 8;
        r15 = r8 >> r15;
        r15 = r15 & r11;
        r17 = 7;
        r15 = r15 << r17;
        r13 = r13 | r15;
        r15 = 16;
        r15 = r8 >> r15;
        r15 = r15 & r11;
        r17 = 14;
        r15 = r15 << r17;
        r13 = r13 | r15;
        r15 = 24;
        r8 = r8 >> r15;
        r8 = r8 & r11;
        r11 = 21;
        r8 = r8 << r11;
        r8 = r8 | r13;
    L_0x006b:
        if (r0 != r7) goto L_0x007a;
    L_0x006d:
        r3 = r10 & 64;
        if (r3 == 0) goto L_0x0073;
    L_0x0071:
        r3 = 1;
        goto L_0x0074;
    L_0x0073:
        r3 = 0;
    L_0x0074:
        r7 = r10 & 1;
        if (r7 == 0) goto L_0x0089;
    L_0x0078:
        r7 = 1;
        goto L_0x008a;
    L_0x007a:
        if (r0 != r3) goto L_0x0088;
    L_0x007c:
        r3 = r10 & 32;
        if (r3 == 0) goto L_0x0082;
    L_0x0080:
        r3 = 1;
        goto L_0x0083;
    L_0x0082:
        r3 = 0;
    L_0x0083:
        r7 = r10 & 128;
        if (r7 == 0) goto L_0x0089;
    L_0x0087:
        goto L_0x0078;
    L_0x0088:
        r3 = 0;
    L_0x0089:
        r7 = 0;
    L_0x008a:
        if (r3 == 0) goto L_0x008d;
    L_0x008c:
        goto L_0x008e;
    L_0x008d:
        r4 = 0;
    L_0x008e:
        if (r7 == 0) goto L_0x0092;
    L_0x0090:
        r4 = r4 + 4;
    L_0x0092:
        r3 = (long) r4;
        r7 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1));
        if (r7 >= 0) goto L_0x009b;
    L_0x0097:
        r1.setPosition(r2);
        return r6;
    L_0x009b:
        r3 = r18.bytesLeft();	 Catch:{ all -> 0x00b2 }
        r3 = (long) r3;
        r7 = (r3 > r8 ? 1 : (r3 == r8 ? 0 : -1));
        if (r7 >= 0) goto L_0x00a8;
    L_0x00a4:
        r1.setPosition(r2);
        return r6;
    L_0x00a8:
        r3 = (int) r8;
        r1.skipBytes(r3);	 Catch:{ all -> 0x00b2 }
        goto L_0x0008;
    L_0x00ae:
        r1.setPosition(r2);
        return r4;
    L_0x00b2:
        r0 = move-exception;
        r1.setPosition(r2);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.metadata.id3.Id3Decoder.validateFrames(com.google.android.exoplayer2.util.ParsableByteArray, int, int, boolean):boolean");
    }

    @androidx.annotation.Nullable
    private static com.google.android.exoplayer2.metadata.id3.Id3Frame decodeFrame(int r19, com.google.android.exoplayer2.util.ParsableByteArray r20, boolean r21, int r22, @androidx.annotation.Nullable com.google.android.exoplayer2.metadata.id3.Id3Decoder.FramePredicate r23) {
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
        r0 = r19;
        r7 = r20;
        r8 = r20.readUnsignedByte();
        r9 = r20.readUnsignedByte();
        r10 = r20.readUnsignedByte();
        r11 = 3;
        if (r0 < r11) goto L_0x0019;
    L_0x0013:
        r1 = r20.readUnsignedByte();
        r13 = r1;
        goto L_0x001a;
    L_0x0019:
        r13 = 0;
    L_0x001a:
        r14 = 4;
        if (r0 != r14) goto L_0x003c;
    L_0x001d:
        r1 = r20.readUnsignedIntToInt();
        if (r21 != 0) goto L_0x003a;
    L_0x0023:
        r2 = r1 & 255;
        r3 = r1 >> 8;
        r3 = r3 & 255;
        r3 = r3 << 7;
        r2 = r2 | r3;
        r3 = r1 >> 16;
        r3 = r3 & 255;
        r3 = r3 << 14;
        r2 = r2 | r3;
        r1 = r1 >> 24;
        r1 = r1 & 255;
        r1 = r1 << 21;
        r1 = r1 | r2;
    L_0x003a:
        r15 = r1;
        goto L_0x0048;
    L_0x003c:
        if (r0 != r11) goto L_0x0043;
    L_0x003e:
        r1 = r20.readUnsignedIntToInt();
        goto L_0x003a;
    L_0x0043:
        r1 = r20.readUnsignedInt24();
        goto L_0x003a;
    L_0x0048:
        if (r0 < r11) goto L_0x0050;
    L_0x004a:
        r1 = r20.readUnsignedShort();
        r6 = r1;
        goto L_0x0051;
    L_0x0050:
        r6 = 0;
    L_0x0051:
        r16 = 0;
        if (r8 != 0) goto L_0x0067;
    L_0x0055:
        if (r9 != 0) goto L_0x0067;
    L_0x0057:
        if (r10 != 0) goto L_0x0067;
    L_0x0059:
        if (r13 != 0) goto L_0x0067;
    L_0x005b:
        if (r15 != 0) goto L_0x0067;
    L_0x005d:
        if (r6 != 0) goto L_0x0067;
    L_0x005f:
        r0 = r20.limit();
        r7.setPosition(r0);
        return r16;
    L_0x0067:
        r1 = r20.getPosition();
        r5 = r1 + r15;
        r1 = r20.limit();
        if (r5 <= r1) goto L_0x0082;
    L_0x0073:
        r0 = "Id3Decoder";
        r1 = "Frame size exceeds remaining tag data";
        com.google.android.exoplayer2.util.Log.w(r0, r1);
        r0 = r20.limit();
        r7.setPosition(r0);
        return r16;
    L_0x0082:
        if (r23 == 0) goto L_0x0098;
    L_0x0084:
        r1 = r23;
        r2 = r19;
        r3 = r8;
        r4 = r9;
        r12 = r5;
        r5 = r10;
        r14 = r6;
        r6 = r13;
        r1 = r1.evaluate(r2, r3, r4, r5, r6);
        if (r1 != 0) goto L_0x009a;
    L_0x0094:
        r7.setPosition(r12);
        return r16;
    L_0x0098:
        r12 = r5;
        r14 = r6;
    L_0x009a:
        r1 = 1;
        if (r0 != r11) goto L_0x00b7;
    L_0x009d:
        r2 = r14 & 128;
        if (r2 == 0) goto L_0x00a3;
    L_0x00a1:
        r2 = 1;
        goto L_0x00a4;
    L_0x00a3:
        r2 = 0;
    L_0x00a4:
        r3 = r14 & 64;
        if (r3 == 0) goto L_0x00aa;
    L_0x00a8:
        r3 = 1;
        goto L_0x00ab;
    L_0x00aa:
        r3 = 0;
    L_0x00ab:
        r4 = r14 & 32;
        if (r4 == 0) goto L_0x00b1;
    L_0x00af:
        r4 = 1;
        goto L_0x00b2;
    L_0x00b1:
        r4 = 0;
    L_0x00b2:
        r17 = r3;
        r5 = 0;
        r3 = r2;
        goto L_0x00ee;
    L_0x00b7:
        r2 = 4;
        if (r0 != r2) goto L_0x00e8;
    L_0x00ba:
        r2 = r14 & 64;
        if (r2 == 0) goto L_0x00c0;
    L_0x00be:
        r2 = 1;
        goto L_0x00c1;
    L_0x00c0:
        r2 = 0;
    L_0x00c1:
        r3 = r14 & 8;
        if (r3 == 0) goto L_0x00c7;
    L_0x00c5:
        r3 = 1;
        goto L_0x00c8;
    L_0x00c7:
        r3 = 0;
    L_0x00c8:
        r4 = r14 & 4;
        if (r4 == 0) goto L_0x00ce;
    L_0x00cc:
        r4 = 1;
        goto L_0x00cf;
    L_0x00ce:
        r4 = 0;
    L_0x00cf:
        r5 = r14 & 2;
        if (r5 == 0) goto L_0x00d5;
    L_0x00d3:
        r5 = 1;
        goto L_0x00d6;
    L_0x00d5:
        r5 = 0;
    L_0x00d6:
        r6 = r14 & 1;
        if (r6 == 0) goto L_0x00dd;
    L_0x00da:
        r17 = 1;
        goto L_0x00df;
    L_0x00dd:
        r17 = 0;
    L_0x00df:
        r18 = r4;
        r4 = r2;
        r2 = r3;
        r3 = r17;
        r17 = r18;
        goto L_0x00ee;
    L_0x00e8:
        r2 = 0;
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r17 = 0;
    L_0x00ee:
        if (r2 != 0) goto L_0x021d;
    L_0x00f0:
        if (r17 == 0) goto L_0x00f4;
    L_0x00f2:
        goto L_0x021d;
    L_0x00f4:
        if (r4 == 0) goto L_0x00fb;
    L_0x00f6:
        r15 = r15 + -1;
        r7.skipBytes(r1);
    L_0x00fb:
        if (r3 == 0) goto L_0x0103;
    L_0x00fd:
        r15 = r15 + -4;
        r1 = 4;
        r7.skipBytes(r1);
    L_0x0103:
        r1 = r15;
        if (r5 == 0) goto L_0x010a;
    L_0x0106:
        r1 = removeUnsynchronization(r7, r1);
    L_0x010a:
        r11 = r1;
        r1 = 84;
        r2 = 88;
        r3 = 2;
        if (r8 != r1) goto L_0x0120;
    L_0x0112:
        if (r9 != r2) goto L_0x0120;
    L_0x0114:
        if (r10 != r2) goto L_0x0120;
    L_0x0116:
        if (r0 == r3) goto L_0x011a;
    L_0x0118:
        if (r13 != r2) goto L_0x0120;
    L_0x011a:
        r1 = decodeTxxxFrame(r7, r11);	 Catch:{ UnsupportedEncodingException -> 0x020e }
        goto L_0x01e6;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x0120:
        if (r8 != r1) goto L_0x012f;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x0122:
        r1 = getFrameId(r0, r8, r9, r10, r13);	 Catch:{ UnsupportedEncodingException -> 0x020e }
        r1 = decodeTextInformationFrame(r7, r11, r1);	 Catch:{ UnsupportedEncodingException -> 0x020e }
        goto L_0x01e6;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x012c:
        r0 = move-exception;	 Catch:{ UnsupportedEncodingException -> 0x020e }
        goto L_0x0219;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x012f:
        r4 = 87;	 Catch:{ UnsupportedEncodingException -> 0x020e }
        if (r8 != r4) goto L_0x0141;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x0133:
        if (r9 != r2) goto L_0x0141;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x0135:
        if (r10 != r2) goto L_0x0141;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x0137:
        if (r0 == r3) goto L_0x013b;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x0139:
        if (r13 != r2) goto L_0x0141;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x013b:
        r1 = decodeWxxxFrame(r7, r11);	 Catch:{ UnsupportedEncodingException -> 0x020e }
        goto L_0x01e6;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x0141:
        r2 = 87;	 Catch:{ UnsupportedEncodingException -> 0x020e }
        if (r8 != r2) goto L_0x014f;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x0145:
        r1 = getFrameId(r0, r8, r9, r10, r13);	 Catch:{ UnsupportedEncodingException -> 0x020e }
        r1 = decodeUrlLinkFrame(r7, r11, r1);	 Catch:{ UnsupportedEncodingException -> 0x020e }
        goto L_0x01e6;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x014f:
        r2 = 73;	 Catch:{ UnsupportedEncodingException -> 0x020e }
        r4 = 80;	 Catch:{ UnsupportedEncodingException -> 0x020e }
        if (r8 != r4) goto L_0x0165;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x0155:
        r5 = 82;	 Catch:{ UnsupportedEncodingException -> 0x020e }
        if (r9 != r5) goto L_0x0165;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x0159:
        if (r10 != r2) goto L_0x0165;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x015b:
        r5 = 86;	 Catch:{ UnsupportedEncodingException -> 0x020e }
        if (r13 != r5) goto L_0x0165;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x015f:
        r1 = decodePrivFrame(r7, r11);	 Catch:{ UnsupportedEncodingException -> 0x020e }
        goto L_0x01e6;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x0165:
        r5 = 71;	 Catch:{ UnsupportedEncodingException -> 0x020e }
        r6 = 79;	 Catch:{ UnsupportedEncodingException -> 0x020e }
        if (r8 != r5) goto L_0x017d;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x016b:
        r5 = 69;	 Catch:{ UnsupportedEncodingException -> 0x020e }
        if (r9 != r5) goto L_0x017d;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x016f:
        if (r10 != r6) goto L_0x017d;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x0171:
        r5 = 66;	 Catch:{ UnsupportedEncodingException -> 0x020e }
        if (r13 == r5) goto L_0x0177;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x0175:
        if (r0 != r3) goto L_0x017d;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x0177:
        r1 = decodeGeobFrame(r7, r11);	 Catch:{ UnsupportedEncodingException -> 0x020e }
        goto L_0x01e6;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x017d:
        r5 = 67;	 Catch:{ UnsupportedEncodingException -> 0x020e }
        if (r0 != r3) goto L_0x0188;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x0181:
        if (r8 != r4) goto L_0x0197;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x0183:
        if (r9 != r2) goto L_0x0197;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x0185:
        if (r10 != r5) goto L_0x0197;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x0187:
        goto L_0x0192;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x0188:
        r14 = 65;	 Catch:{ UnsupportedEncodingException -> 0x020e }
        if (r8 != r14) goto L_0x0197;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x018c:
        if (r9 != r4) goto L_0x0197;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x018e:
        if (r10 != r2) goto L_0x0197;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x0190:
        if (r13 != r5) goto L_0x0197;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x0192:
        r1 = decodeApicFrame(r7, r11, r0);	 Catch:{ UnsupportedEncodingException -> 0x020e }
        goto L_0x01e6;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x0197:
        if (r8 != r5) goto L_0x01aa;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x0199:
        if (r9 != r6) goto L_0x01aa;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x019b:
        r2 = 77;	 Catch:{ UnsupportedEncodingException -> 0x020e }
        if (r10 != r2) goto L_0x01aa;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x019f:
        r2 = 77;	 Catch:{ UnsupportedEncodingException -> 0x020e }
        if (r13 == r2) goto L_0x01a5;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x01a3:
        if (r0 != r3) goto L_0x01aa;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x01a5:
        r1 = decodeCommentFrame(r7, r11);	 Catch:{ UnsupportedEncodingException -> 0x020e }
        goto L_0x01e6;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x01aa:
        if (r8 != r5) goto L_0x01c6;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x01ac:
        r2 = 72;	 Catch:{ UnsupportedEncodingException -> 0x020e }
        if (r9 != r2) goto L_0x01c6;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x01b0:
        r2 = 65;	 Catch:{ UnsupportedEncodingException -> 0x020e }
        if (r10 != r2) goto L_0x01c6;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x01b4:
        if (r13 != r4) goto L_0x01c6;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x01b6:
        r1 = r20;	 Catch:{ UnsupportedEncodingException -> 0x020e }
        r2 = r11;	 Catch:{ UnsupportedEncodingException -> 0x020e }
        r3 = r19;	 Catch:{ UnsupportedEncodingException -> 0x020e }
        r4 = r21;	 Catch:{ UnsupportedEncodingException -> 0x020e }
        r5 = r22;	 Catch:{ UnsupportedEncodingException -> 0x020e }
        r6 = r23;	 Catch:{ UnsupportedEncodingException -> 0x020e }
        r1 = decodeChapterFrame(r1, r2, r3, r4, r5, r6);	 Catch:{ UnsupportedEncodingException -> 0x020e }
        goto L_0x01e6;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x01c6:
        if (r8 != r5) goto L_0x01de;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x01c8:
        if (r9 != r1) goto L_0x01de;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x01ca:
        if (r10 != r6) goto L_0x01de;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x01cc:
        if (r13 != r5) goto L_0x01de;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x01ce:
        r1 = r20;	 Catch:{ UnsupportedEncodingException -> 0x020e }
        r2 = r11;	 Catch:{ UnsupportedEncodingException -> 0x020e }
        r3 = r19;	 Catch:{ UnsupportedEncodingException -> 0x020e }
        r4 = r21;	 Catch:{ UnsupportedEncodingException -> 0x020e }
        r5 = r22;	 Catch:{ UnsupportedEncodingException -> 0x020e }
        r6 = r23;	 Catch:{ UnsupportedEncodingException -> 0x020e }
        r1 = decodeChapterTOCFrame(r1, r2, r3, r4, r5, r6);	 Catch:{ UnsupportedEncodingException -> 0x020e }
        goto L_0x01e6;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x01de:
        r1 = getFrameId(r0, r8, r9, r10, r13);	 Catch:{ UnsupportedEncodingException -> 0x020e }
        r1 = decodeBinaryFrame(r7, r11, r1);	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x01e6:
        if (r1 != 0) goto L_0x020a;	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x01e8:
        r2 = "Id3Decoder";	 Catch:{ UnsupportedEncodingException -> 0x020e }
        r3 = new java.lang.StringBuilder;	 Catch:{ UnsupportedEncodingException -> 0x020e }
        r3.<init>();	 Catch:{ UnsupportedEncodingException -> 0x020e }
        r4 = "Failed to decode frame: id=";	 Catch:{ UnsupportedEncodingException -> 0x020e }
        r3.append(r4);	 Catch:{ UnsupportedEncodingException -> 0x020e }
        r0 = getFrameId(r0, r8, r9, r10, r13);	 Catch:{ UnsupportedEncodingException -> 0x020e }
        r3.append(r0);	 Catch:{ UnsupportedEncodingException -> 0x020e }
        r0 = ", frameSize=";	 Catch:{ UnsupportedEncodingException -> 0x020e }
        r3.append(r0);	 Catch:{ UnsupportedEncodingException -> 0x020e }
        r3.append(r11);	 Catch:{ UnsupportedEncodingException -> 0x020e }
        r0 = r3.toString();	 Catch:{ UnsupportedEncodingException -> 0x020e }
        com.google.android.exoplayer2.util.Log.w(r2, r0);	 Catch:{ UnsupportedEncodingException -> 0x020e }
    L_0x020a:
        r7.setPosition(r12);
        return r1;
    L_0x020e:
        r0 = "Id3Decoder";	 Catch:{ all -> 0x012c }
        r1 = "Unsupported character encoding";	 Catch:{ all -> 0x012c }
        com.google.android.exoplayer2.util.Log.w(r0, r1);	 Catch:{ all -> 0x012c }
        r7.setPosition(r12);
        return r16;
    L_0x0219:
        r7.setPosition(r12);
        throw r0;
    L_0x021d:
        r0 = "Id3Decoder";
        r1 = "Skipping unsupported compressed or encrypted frame";
        com.google.android.exoplayer2.util.Log.w(r0, r1);
        r7.setPosition(r12);
        return r16;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.metadata.id3.Id3Decoder.decodeFrame(int, com.google.android.exoplayer2.util.ParsableByteArray, boolean, int, com.google.android.exoplayer2.metadata.id3.Id3Decoder$FramePredicate):com.google.android.exoplayer2.metadata.id3.Id3Frame");
    }

    @Nullable
    private static TextInformationFrame decodeTxxxFrame(ParsableByteArray parsableByteArray, int i) throws UnsupportedEncodingException {
        if (i < 1) {
            return null;
        }
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        String charsetName = getCharsetName(readUnsignedByte);
        i--;
        byte[] bArr = new byte[i];
        parsableByteArray.readBytes(bArr, 0, i);
        parsableByteArray = indexOfEos(bArr, 0, readUnsignedByte);
        i = new String(bArr, 0, parsableByteArray, charsetName);
        parsableByteArray += delimiterLength(readUnsignedByte);
        return new TextInformationFrame("TXXX", i, decodeStringIfValid(bArr, parsableByteArray, indexOfEos(bArr, parsableByteArray, readUnsignedByte), charsetName));
    }

    @Nullable
    private static TextInformationFrame decodeTextInformationFrame(ParsableByteArray parsableByteArray, int i, String str) throws UnsupportedEncodingException {
        if (i < 1) {
            return null;
        }
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        String charsetName = getCharsetName(readUnsignedByte);
        i--;
        byte[] bArr = new byte[i];
        parsableByteArray.readBytes(bArr, 0, i);
        return new TextInformationFrame(str, null, new String(bArr, 0, indexOfEos(bArr, 0, readUnsignedByte), charsetName));
    }

    @Nullable
    private static UrlLinkFrame decodeWxxxFrame(ParsableByteArray parsableByteArray, int i) throws UnsupportedEncodingException {
        if (i < 1) {
            return null;
        }
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        String charsetName = getCharsetName(readUnsignedByte);
        i--;
        byte[] bArr = new byte[i];
        parsableByteArray.readBytes(bArr, 0, i);
        parsableByteArray = indexOfEos(bArr, 0, readUnsignedByte);
        i = new String(bArr, 0, parsableByteArray, charsetName);
        parsableByteArray += delimiterLength(readUnsignedByte);
        return new UrlLinkFrame("WXXX", i, decodeStringIfValid(bArr, parsableByteArray, indexOfZeroByte(bArr, parsableByteArray), "ISO-8859-1"));
    }

    private static UrlLinkFrame decodeUrlLinkFrame(ParsableByteArray parsableByteArray, int i, String str) throws UnsupportedEncodingException {
        byte[] bArr = new byte[i];
        parsableByteArray.readBytes(bArr, 0, i);
        return new UrlLinkFrame(str, null, new String(bArr, 0, indexOfZeroByte(bArr, 0), "ISO-8859-1"));
    }

    private static PrivFrame decodePrivFrame(ParsableByteArray parsableByteArray, int i) throws UnsupportedEncodingException {
        byte[] bArr = new byte[i];
        parsableByteArray.readBytes(bArr, 0, i);
        parsableByteArray = indexOfZeroByte(bArr, 0);
        return new PrivFrame(new String(bArr, 0, parsableByteArray, "ISO-8859-1"), copyOfRangeIfValid(bArr, parsableByteArray + 1, bArr.length));
    }

    private static GeobFrame decodeGeobFrame(ParsableByteArray parsableByteArray, int i) throws UnsupportedEncodingException {
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        String charsetName = getCharsetName(readUnsignedByte);
        i--;
        byte[] bArr = new byte[i];
        parsableByteArray.readBytes(bArr, 0, i);
        parsableByteArray = indexOfZeroByte(bArr, 0);
        i = new String(bArr, 0, parsableByteArray, "ISO-8859-1");
        parsableByteArray++;
        int indexOfEos = indexOfEos(bArr, parsableByteArray, readUnsignedByte);
        parsableByteArray = decodeStringIfValid(bArr, parsableByteArray, indexOfEos, charsetName);
        indexOfEos += delimiterLength(readUnsignedByte);
        int indexOfEos2 = indexOfEos(bArr, indexOfEos, readUnsignedByte);
        return new GeobFrame(i, parsableByteArray, decodeStringIfValid(bArr, indexOfEos, indexOfEos2, charsetName), copyOfRangeIfValid(bArr, indexOfEos2 + delimiterLength(readUnsignedByte), bArr.length));
    }

    private static ApicFrame decodeApicFrame(ParsableByteArray parsableByteArray, int i, int i2) throws UnsupportedEncodingException {
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        String charsetName = getCharsetName(readUnsignedByte);
        i--;
        byte[] bArr = new byte[i];
        parsableByteArray.readBytes(bArr, 0, i);
        if (i2 == 2) {
            i = new StringBuilder();
            i.append("image/");
            i.append(Util.toLowerInvariant(new String(bArr, 0, 3, "ISO-8859-1")));
            i = i.toString();
            if ("image/jpg".equals(i) != 0) {
                i = "image/jpeg";
            }
            i2 = i;
            i = 2;
        } else {
            i = indexOfZeroByte(bArr, 0);
            i2 = Util.toLowerInvariant(new String(bArr, 0, i, "ISO-8859-1"));
            if (i2.indexOf(47) == -1) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("image/");
                stringBuilder.append(i2);
                i2 = stringBuilder.toString();
            }
        }
        int i3 = bArr[i + 1] & 255;
        i += 2;
        parsableByteArray = indexOfEos(bArr, i, readUnsignedByte);
        return new ApicFrame(i2, new String(bArr, i, parsableByteArray - i, charsetName), i3, copyOfRangeIfValid(bArr, parsableByteArray + delimiterLength(readUnsignedByte), bArr.length));
    }

    @Nullable
    private static CommentFrame decodeCommentFrame(ParsableByteArray parsableByteArray, int i) throws UnsupportedEncodingException {
        if (i < 4) {
            return null;
        }
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        String charsetName = getCharsetName(readUnsignedByte);
        byte[] bArr = new byte[3];
        parsableByteArray.readBytes(bArr, 0, 3);
        String str = new String(bArr, 0, 3);
        i -= 4;
        byte[] bArr2 = new byte[i];
        parsableByteArray.readBytes(bArr2, 0, i);
        parsableByteArray = indexOfEos(bArr2, 0, readUnsignedByte);
        i = new String(bArr2, 0, parsableByteArray, charsetName);
        parsableByteArray += delimiterLength(readUnsignedByte);
        return new CommentFrame(str, i, decodeStringIfValid(bArr2, parsableByteArray, indexOfEos(bArr2, parsableByteArray, readUnsignedByte), charsetName));
    }

    private static ChapterFrame decodeChapterFrame(ParsableByteArray parsableByteArray, int i, int i2, boolean z, int i3, @Nullable FramePredicate framePredicate) throws UnsupportedEncodingException {
        ParsableByteArray parsableByteArray2 = parsableByteArray;
        int position = parsableByteArray.getPosition();
        int indexOfZeroByte = indexOfZeroByte(parsableByteArray2.data, position);
        String str = new String(parsableByteArray2.data, position, indexOfZeroByte - position, "ISO-8859-1");
        parsableByteArray.setPosition(indexOfZeroByte + 1);
        int readInt = parsableByteArray.readInt();
        int readInt2 = parsableByteArray.readInt();
        long readUnsignedInt = parsableByteArray.readUnsignedInt();
        long j = readUnsignedInt == 4294967295L ? -1 : readUnsignedInt;
        readUnsignedInt = parsableByteArray.readUnsignedInt();
        long j2 = readUnsignedInt == 4294967295L ? -1 : readUnsignedInt;
        ArrayList arrayList = new ArrayList();
        position += i;
        while (parsableByteArray.getPosition() < position) {
            Id3Frame decodeFrame = decodeFrame(i2, parsableByteArray, z, i3, framePredicate);
            if (decodeFrame != null) {
                arrayList.add(decodeFrame);
            }
        }
        Id3Frame[] id3FrameArr = new Id3Frame[arrayList.size()];
        arrayList.toArray(id3FrameArr);
        return new ChapterFrame(str, readInt, readInt2, j, j2, id3FrameArr);
    }

    private static ChapterTocFrame decodeChapterTOCFrame(ParsableByteArray parsableByteArray, int i, int i2, boolean z, int i3, @Nullable FramePredicate framePredicate) throws UnsupportedEncodingException {
        ParsableByteArray parsableByteArray2 = parsableByteArray;
        int position = parsableByteArray.getPosition();
        int indexOfZeroByte = indexOfZeroByte(parsableByteArray2.data, position);
        String str = new String(parsableByteArray2.data, position, indexOfZeroByte - position, "ISO-8859-1");
        parsableByteArray.setPosition(indexOfZeroByte + 1);
        indexOfZeroByte = parsableByteArray.readUnsignedByte();
        int i4 = (indexOfZeroByte & 2) != 0 ? 1 : 0;
        boolean z2 = (indexOfZeroByte & 1) != 0;
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        String[] strArr = new String[readUnsignedByte];
        for (int i5 = 0; i5 < readUnsignedByte; i5++) {
            int position2 = parsableByteArray.getPosition();
            int indexOfZeroByte2 = indexOfZeroByte(parsableByteArray2.data, position2);
            strArr[i5] = new String(parsableByteArray2.data, position2, indexOfZeroByte2 - position2, "ISO-8859-1");
            parsableByteArray.setPosition(indexOfZeroByte2 + 1);
        }
        ArrayList arrayList = new ArrayList();
        position += i;
        while (parsableByteArray.getPosition() < position) {
            Id3Frame decodeFrame = decodeFrame(i2, parsableByteArray, z, i3, framePredicate);
            if (decodeFrame != null) {
                arrayList.add(decodeFrame);
            }
        }
        Id3Frame[] id3FrameArr = new Id3Frame[arrayList.size()];
        arrayList.toArray(id3FrameArr);
        return new ChapterTocFrame(str, i4, z2, strArr, id3FrameArr);
    }

    private static BinaryFrame decodeBinaryFrame(ParsableByteArray parsableByteArray, int i, String str) {
        byte[] bArr = new byte[i];
        parsableByteArray.readBytes(bArr, 0, i);
        return new BinaryFrame(str, bArr);
    }

    private static int removeUnsynchronization(ParsableByteArray parsableByteArray, int i) {
        Object obj = parsableByteArray.data;
        parsableByteArray = parsableByteArray.getPosition();
        while (true) {
            int i2 = parsableByteArray + 1;
            if (i2 >= i) {
                return i;
            }
            if ((obj[parsableByteArray] & 255) == 255 && obj[i2] == (byte) 0) {
                System.arraycopy(obj, parsableByteArray + 2, obj, i2, (i - parsableByteArray) - 2);
                i--;
            }
            parsableByteArray = i2;
        }
    }

    private static String getFrameId(int i, int i2, int i3, int i4, int i5) {
        if (i == 2) {
            return String.format(Locale.US, "%c%c%c", new Object[]{Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4)});
        }
        return String.format(Locale.US, "%c%c%c%c", new Object[]{Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5)});
    }

    private static int indexOfEos(byte[] bArr, int i, int i2) {
        i = indexOfZeroByte(bArr, i);
        if (i2 != 0) {
            if (i2 != 3) {
                while (i < bArr.length - 1) {
                    if (i % 2 == 0 && bArr[i + 1] == 0) {
                        return i;
                    }
                    i = indexOfZeroByte(bArr, i + 1);
                }
                return bArr.length;
            }
        }
        return i;
    }

    private static int indexOfZeroByte(byte[] bArr, int i) {
        while (i < bArr.length) {
            if (bArr[i] == (byte) 0) {
                return i;
            }
            i++;
        }
        return bArr.length;
    }

    private static byte[] copyOfRangeIfValid(byte[] bArr, int i, int i2) {
        if (i2 <= i) {
            return Util.EMPTY_BYTE_ARRAY;
        }
        return Arrays.copyOfRange(bArr, i, i2);
    }

    private static String decodeStringIfValid(byte[] bArr, int i, int i2, String str) throws UnsupportedEncodingException {
        if (i2 > i) {
            if (i2 <= bArr.length) {
                return new String(bArr, i, i2 - i, str);
            }
        }
        return "";
    }
}
