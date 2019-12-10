package com.google.android.exoplayer2.util;

import com.android.volley.toolbox.ImageRequest;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import java.nio.ByteBuffer;
import java.util.Arrays;

public final class NalUnitUtil {
    public static final float[] ASPECT_RATIO_IDC_VALUES = new float[]{1.0f, 1.0f, 1.0909091f, 0.90909094f, 1.4545455f, 1.2121212f, 2.1818182f, 1.8181819f, 2.909091f, 2.4242425f, 1.6363636f, 1.3636364f, 1.939394f, 1.6161616f, 1.3333334f, 1.5f, ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT};
    public static final int EXTENDED_SAR = 255;
    private static final int H264_NAL_UNIT_TYPE_SEI = 6;
    private static final int H264_NAL_UNIT_TYPE_SPS = 7;
    private static final int H265_NAL_UNIT_TYPE_PREFIX_SEI = 39;
    public static final byte[] NAL_START_CODE = new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 1};
    private static final String TAG = "NalUnitUtil";
    private static int[] scratchEscapePositions = new int[10];
    private static final Object scratchEscapePositionsLock = new Object();

    public static final class PpsData {
        public final boolean bottomFieldPicOrderInFramePresentFlag;
        public final int picParameterSetId;
        public final int seqParameterSetId;

        public PpsData(int i, int i2, boolean z) {
            this.picParameterSetId = i;
            this.seqParameterSetId = i2;
            this.bottomFieldPicOrderInFramePresentFlag = z;
        }
    }

    public static final class SpsData {
        public final int constraintsFlagsAndReservedZero2Bits;
        public final boolean deltaPicOrderAlwaysZeroFlag;
        public final boolean frameMbsOnlyFlag;
        public final int frameNumLength;
        public final int height;
        public final int levelIdc;
        public final int picOrderCntLsbLength;
        public final int picOrderCountType;
        public final float pixelWidthAspectRatio;
        public final int profileIdc;
        public final boolean separateColorPlaneFlag;
        public final int seqParameterSetId;
        public final int width;

        public SpsData(int i, int i2, int i3, int i4, int i5, int i6, float f, boolean z, boolean z2, int i7, int i8, int i9, boolean z3) {
            this.profileIdc = i;
            this.constraintsFlagsAndReservedZero2Bits = i2;
            this.levelIdc = i3;
            this.seqParameterSetId = i4;
            this.width = i5;
            this.height = i6;
            this.pixelWidthAspectRatio = f;
            this.separateColorPlaneFlag = z;
            this.frameMbsOnlyFlag = z2;
            this.frameNumLength = i7;
            this.picOrderCountType = i8;
            this.picOrderCntLsbLength = i9;
            this.deltaPicOrderAlwaysZeroFlag = z3;
        }
    }

    public static int unescapeStream(byte[] bArr, int i) {
        synchronized (scratchEscapePositionsLock) {
            int i2;
            int i3 = 0;
            int i4 = 0;
            while (i3 < i) {
                i3 = findNextUnescapeIndex(bArr, i3, i);
                if (i3 < i) {
                    if (scratchEscapePositions.length <= i4) {
                        scratchEscapePositions = Arrays.copyOf(scratchEscapePositions, scratchEscapePositions.length * 2);
                    }
                    i2 = i4 + 1;
                    scratchEscapePositions[i4] = i3;
                    i3 += 3;
                    i4 = i2;
                }
            }
            i -= i4;
            int i5 = 0;
            i2 = 0;
            for (i3 = 0; i3 < i4; i3++) {
                int i6 = scratchEscapePositions[i3] - i2;
                System.arraycopy(bArr, i2, bArr, i5, i6);
                i5 += i6;
                int i7 = i5 + 1;
                bArr[i5] = (byte) 0;
                i5 = i7 + 1;
                bArr[i7] = (byte) 0;
                i2 += i6 + 3;
            }
            System.arraycopy(bArr, i2, bArr, i5, i - i5);
        }
        return i;
    }

    public static void discardToSps(ByteBuffer byteBuffer) {
        int position = byteBuffer.position();
        int i = 0;
        int i2 = 0;
        while (true) {
            int i3 = i + 1;
            if (i3 < position) {
                int i4 = byteBuffer.get(i) & 255;
                if (i2 == 3) {
                    if (i4 == 1 && (byteBuffer.get(i3) & 31) == 7) {
                        ByteBuffer duplicate = byteBuffer.duplicate();
                        duplicate.position(i - 3);
                        duplicate.limit(position);
                        byteBuffer.position(0);
                        byteBuffer.put(duplicate);
                        return;
                    }
                } else if (i4 == 0) {
                    i2++;
                }
                if (i4 != 0) {
                    i2 = 0;
                }
                i = i3;
            } else {
                byteBuffer.clear();
                return;
            }
        }
    }

    public static boolean isNalUnitSei(String str, byte b) {
        if (MimeTypes.VIDEO_H264.equals(str) && (b & 31) == 6) {
            return true;
        }
        if (MimeTypes.VIDEO_H265.equals(str) == null || ((b & 126) >> 1) != (byte) 39) {
            return false;
        }
        return true;
    }

    public static int getNalUnitType(byte[] bArr, int i) {
        return bArr[i + 3] & 31;
    }

    public static int getH265NalUnitType(byte[] bArr, int i) {
        return (bArr[i + 3] & 126) >> 1;
    }

    public static SpsData parseSpsNalUnit(byte[] bArr, int i, int i2) {
        int i3;
        boolean z;
        int readUnsignedExpGolombCodedInt;
        int readUnsignedExpGolombCodedInt2;
        int readUnsignedExpGolombCodedInt3;
        long readUnsignedExpGolombCodedInt4;
        int i4;
        boolean z2;
        int readUnsignedExpGolombCodedInt5;
        int readUnsignedExpGolombCodedInt6;
        boolean readBit;
        int i5;
        int readUnsignedExpGolombCodedInt7;
        int readUnsignedExpGolombCodedInt8;
        int readUnsignedExpGolombCodedInt9;
        int i6;
        float f;
        int readBits;
        float f2;
        String str;
        StringBuilder stringBuilder;
        ParsableNalUnitBitArray parsableNalUnitBitArray = new ParsableNalUnitBitArray(bArr, i, i2);
        parsableNalUnitBitArray.skipBits(8);
        int readBits2 = parsableNalUnitBitArray.readBits(8);
        int readBits3 = parsableNalUnitBitArray.readBits(8);
        int readBits4 = parsableNalUnitBitArray.readBits(8);
        int readUnsignedExpGolombCodedInt10 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        int i7 = 1;
        if (!(readBits2 == 100 || readBits2 == 110 || readBits2 == 122 || readBits2 == 244 || readBits2 == 44 || readBits2 == 83 || readBits2 == 86 || readBits2 == 118 || readBits2 == 128)) {
            if (readBits2 != TsExtractor.TS_STREAM_TYPE_DTS) {
                i3 = 1;
                z = false;
                readUnsignedExpGolombCodedInt = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt() + 4;
                readUnsignedExpGolombCodedInt2 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                if (readUnsignedExpGolombCodedInt2 == 0) {
                    readUnsignedExpGolombCodedInt3 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt() + 4;
                } else if (readUnsignedExpGolombCodedInt2 != 1) {
                    boolean readBit2 = parsableNalUnitBitArray.readBit();
                    parsableNalUnitBitArray.readSignedExpGolombCodedInt();
                    parsableNalUnitBitArray.readSignedExpGolombCodedInt();
                    readUnsignedExpGolombCodedInt4 = (long) parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                    for (i4 = 0; ((long) i4) < readUnsignedExpGolombCodedInt4; i4++) {
                        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                    }
                    z2 = readBit2;
                    readUnsignedExpGolombCodedInt3 = 0;
                    parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                    parsableNalUnitBitArray.skipBit();
                    readUnsignedExpGolombCodedInt5 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt() + 1;
                    readUnsignedExpGolombCodedInt6 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt() + 1;
                    readBit = parsableNalUnitBitArray.readBit();
                    i5 = (2 - readBit) * readUnsignedExpGolombCodedInt6;
                    if (!readBit) {
                        parsableNalUnitBitArray.skipBit();
                    }
                    parsableNalUnitBitArray.skipBit();
                    readUnsignedExpGolombCodedInt5 *= 16;
                    i5 *= 16;
                    if (parsableNalUnitBitArray.readBit()) {
                        readUnsignedExpGolombCodedInt6 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                        readUnsignedExpGolombCodedInt7 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                        readUnsignedExpGolombCodedInt8 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                        readUnsignedExpGolombCodedInt9 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                        if (i3 != 0) {
                            i7 = 2 - readBit;
                            i6 = 1;
                        } else {
                            i6 = i3 != 3 ? 1 : 2;
                            if (i3 == 1) {
                                i7 = 2;
                            }
                            i7 *= 2 - readBit;
                        }
                        readUnsignedExpGolombCodedInt5 -= (readUnsignedExpGolombCodedInt6 + readUnsignedExpGolombCodedInt7) * i6;
                        i5 -= (readUnsignedExpGolombCodedInt8 + readUnsignedExpGolombCodedInt9) * i7;
                    }
                    i6 = readUnsignedExpGolombCodedInt5;
                    f = 1.0f;
                    if (parsableNalUnitBitArray.readBit() && parsableNalUnitBitArray.readBit()) {
                        readUnsignedExpGolombCodedInt6 = parsableNalUnitBitArray.readBits(8);
                        if (readUnsignedExpGolombCodedInt6 != 255) {
                            readUnsignedExpGolombCodedInt6 = parsableNalUnitBitArray.readBits(16);
                            readBits = parsableNalUnitBitArray.readBits(16);
                            if (!(readUnsignedExpGolombCodedInt6 == 0 || readBits == 0)) {
                                f = ((float) readUnsignedExpGolombCodedInt6) / ((float) readBits);
                            }
                            f2 = f;
                        } else if (readUnsignedExpGolombCodedInt6 >= ASPECT_RATIO_IDC_VALUES.length) {
                            f2 = ASPECT_RATIO_IDC_VALUES[readUnsignedExpGolombCodedInt6];
                        } else {
                            str = TAG;
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("Unexpected aspect_ratio_idc value: ");
                            stringBuilder.append(readUnsignedExpGolombCodedInt6);
                            Log.w(str, stringBuilder.toString());
                        }
                        return new SpsData(readBits2, readBits3, readBits4, readUnsignedExpGolombCodedInt10, i6, i5, f2, z, readBit, readUnsignedExpGolombCodedInt, readUnsignedExpGolombCodedInt2, readUnsignedExpGolombCodedInt3, z2);
                    }
                    f2 = 1.0f;
                    return new SpsData(readBits2, readBits3, readBits4, readUnsignedExpGolombCodedInt10, i6, i5, f2, z, readBit, readUnsignedExpGolombCodedInt, readUnsignedExpGolombCodedInt2, readUnsignedExpGolombCodedInt3, z2);
                } else {
                    readUnsignedExpGolombCodedInt3 = 0;
                }
                z2 = false;
                parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                parsableNalUnitBitArray.skipBit();
                readUnsignedExpGolombCodedInt5 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt() + 1;
                readUnsignedExpGolombCodedInt6 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt() + 1;
                readBit = parsableNalUnitBitArray.readBit();
                i5 = (2 - readBit) * readUnsignedExpGolombCodedInt6;
                if (readBit) {
                    parsableNalUnitBitArray.skipBit();
                }
                parsableNalUnitBitArray.skipBit();
                readUnsignedExpGolombCodedInt5 *= 16;
                i5 *= 16;
                if (parsableNalUnitBitArray.readBit()) {
                    readUnsignedExpGolombCodedInt6 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                    readUnsignedExpGolombCodedInt7 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                    readUnsignedExpGolombCodedInt8 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                    readUnsignedExpGolombCodedInt9 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                    if (i3 != 0) {
                        if (i3 != 3) {
                        }
                        if (i3 == 1) {
                            i7 = 2;
                        }
                        i7 *= 2 - readBit;
                    } else {
                        i7 = 2 - readBit;
                        i6 = 1;
                    }
                    readUnsignedExpGolombCodedInt5 -= (readUnsignedExpGolombCodedInt6 + readUnsignedExpGolombCodedInt7) * i6;
                    i5 -= (readUnsignedExpGolombCodedInt8 + readUnsignedExpGolombCodedInt9) * i7;
                }
                i6 = readUnsignedExpGolombCodedInt5;
                f = 1.0f;
                readUnsignedExpGolombCodedInt6 = parsableNalUnitBitArray.readBits(8);
                if (readUnsignedExpGolombCodedInt6 != 255) {
                    readUnsignedExpGolombCodedInt6 = parsableNalUnitBitArray.readBits(16);
                    readBits = parsableNalUnitBitArray.readBits(16);
                    f = ((float) readUnsignedExpGolombCodedInt6) / ((float) readBits);
                    f2 = f;
                } else if (readUnsignedExpGolombCodedInt6 >= ASPECT_RATIO_IDC_VALUES.length) {
                    str = TAG;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Unexpected aspect_ratio_idc value: ");
                    stringBuilder.append(readUnsignedExpGolombCodedInt6);
                    Log.w(str, stringBuilder.toString());
                    f2 = 1.0f;
                } else {
                    f2 = ASPECT_RATIO_IDC_VALUES[readUnsignedExpGolombCodedInt6];
                }
                return new SpsData(readBits2, readBits3, readBits4, readUnsignedExpGolombCodedInt10, i6, i5, f2, z, readBit, readUnsignedExpGolombCodedInt, readUnsignedExpGolombCodedInt2, readUnsignedExpGolombCodedInt3, z2);
            }
        }
        i3 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        z = i3 == 3 ? parsableNalUnitBitArray.readBit() : false;
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.skipBit();
        if (parsableNalUnitBitArray.readBit()) {
            readUnsignedExpGolombCodedInt = i3 != 3 ? 8 : 12;
            readUnsignedExpGolombCodedInt2 = 0;
            while (readUnsignedExpGolombCodedInt2 < readUnsignedExpGolombCodedInt) {
                if (parsableNalUnitBitArray.readBit()) {
                    skipScalingList(parsableNalUnitBitArray, readUnsignedExpGolombCodedInt2 < 6 ? 16 : 64);
                }
                readUnsignedExpGolombCodedInt2++;
            }
        }
        readUnsignedExpGolombCodedInt = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt() + 4;
        readUnsignedExpGolombCodedInt2 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        if (readUnsignedExpGolombCodedInt2 == 0) {
            readUnsignedExpGolombCodedInt3 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt() + 4;
        } else if (readUnsignedExpGolombCodedInt2 != 1) {
            readUnsignedExpGolombCodedInt3 = 0;
        } else {
            boolean readBit22 = parsableNalUnitBitArray.readBit();
            parsableNalUnitBitArray.readSignedExpGolombCodedInt();
            parsableNalUnitBitArray.readSignedExpGolombCodedInt();
            readUnsignedExpGolombCodedInt4 = (long) parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            for (i4 = 0; ((long) i4) < readUnsignedExpGolombCodedInt4; i4++) {
                parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            }
            z2 = readBit22;
            readUnsignedExpGolombCodedInt3 = 0;
            parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            parsableNalUnitBitArray.skipBit();
            readUnsignedExpGolombCodedInt5 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt() + 1;
            readUnsignedExpGolombCodedInt6 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt() + 1;
            readBit = parsableNalUnitBitArray.readBit();
            i5 = (2 - readBit) * readUnsignedExpGolombCodedInt6;
            if (readBit) {
                parsableNalUnitBitArray.skipBit();
            }
            parsableNalUnitBitArray.skipBit();
            readUnsignedExpGolombCodedInt5 *= 16;
            i5 *= 16;
            if (parsableNalUnitBitArray.readBit()) {
                readUnsignedExpGolombCodedInt6 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                readUnsignedExpGolombCodedInt7 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                readUnsignedExpGolombCodedInt8 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                readUnsignedExpGolombCodedInt9 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
                if (i3 != 0) {
                    i7 = 2 - readBit;
                    i6 = 1;
                } else {
                    if (i3 != 3) {
                    }
                    if (i3 == 1) {
                        i7 = 2;
                    }
                    i7 *= 2 - readBit;
                }
                readUnsignedExpGolombCodedInt5 -= (readUnsignedExpGolombCodedInt6 + readUnsignedExpGolombCodedInt7) * i6;
                i5 -= (readUnsignedExpGolombCodedInt8 + readUnsignedExpGolombCodedInt9) * i7;
            }
            i6 = readUnsignedExpGolombCodedInt5;
            f = 1.0f;
            readUnsignedExpGolombCodedInt6 = parsableNalUnitBitArray.readBits(8);
            if (readUnsignedExpGolombCodedInt6 != 255) {
                readUnsignedExpGolombCodedInt6 = parsableNalUnitBitArray.readBits(16);
                readBits = parsableNalUnitBitArray.readBits(16);
                f = ((float) readUnsignedExpGolombCodedInt6) / ((float) readBits);
                f2 = f;
            } else if (readUnsignedExpGolombCodedInt6 >= ASPECT_RATIO_IDC_VALUES.length) {
                f2 = ASPECT_RATIO_IDC_VALUES[readUnsignedExpGolombCodedInt6];
            } else {
                str = TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Unexpected aspect_ratio_idc value: ");
                stringBuilder.append(readUnsignedExpGolombCodedInt6);
                Log.w(str, stringBuilder.toString());
                f2 = 1.0f;
            }
            return new SpsData(readBits2, readBits3, readBits4, readUnsignedExpGolombCodedInt10, i6, i5, f2, z, readBit, readUnsignedExpGolombCodedInt, readUnsignedExpGolombCodedInt2, readUnsignedExpGolombCodedInt3, z2);
        }
        z2 = false;
        parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.skipBit();
        readUnsignedExpGolombCodedInt5 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt() + 1;
        readUnsignedExpGolombCodedInt6 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt() + 1;
        readBit = parsableNalUnitBitArray.readBit();
        i5 = (2 - readBit) * readUnsignedExpGolombCodedInt6;
        if (readBit) {
            parsableNalUnitBitArray.skipBit();
        }
        parsableNalUnitBitArray.skipBit();
        readUnsignedExpGolombCodedInt5 *= 16;
        i5 *= 16;
        if (parsableNalUnitBitArray.readBit()) {
            readUnsignedExpGolombCodedInt6 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            readUnsignedExpGolombCodedInt7 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            readUnsignedExpGolombCodedInt8 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            readUnsignedExpGolombCodedInt9 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
            if (i3 != 0) {
                if (i3 != 3) {
                }
                if (i3 == 1) {
                    i7 = 2;
                }
                i7 *= 2 - readBit;
            } else {
                i7 = 2 - readBit;
                i6 = 1;
            }
            readUnsignedExpGolombCodedInt5 -= (readUnsignedExpGolombCodedInt6 + readUnsignedExpGolombCodedInt7) * i6;
            i5 -= (readUnsignedExpGolombCodedInt8 + readUnsignedExpGolombCodedInt9) * i7;
        }
        i6 = readUnsignedExpGolombCodedInt5;
        f = 1.0f;
        readUnsignedExpGolombCodedInt6 = parsableNalUnitBitArray.readBits(8);
        if (readUnsignedExpGolombCodedInt6 != 255) {
            readUnsignedExpGolombCodedInt6 = parsableNalUnitBitArray.readBits(16);
            readBits = parsableNalUnitBitArray.readBits(16);
            f = ((float) readUnsignedExpGolombCodedInt6) / ((float) readBits);
            f2 = f;
        } else if (readUnsignedExpGolombCodedInt6 >= ASPECT_RATIO_IDC_VALUES.length) {
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected aspect_ratio_idc value: ");
            stringBuilder.append(readUnsignedExpGolombCodedInt6);
            Log.w(str, stringBuilder.toString());
            f2 = 1.0f;
        } else {
            f2 = ASPECT_RATIO_IDC_VALUES[readUnsignedExpGolombCodedInt6];
        }
        return new SpsData(readBits2, readBits3, readBits4, readUnsignedExpGolombCodedInt10, i6, i5, f2, z, readBit, readUnsignedExpGolombCodedInt, readUnsignedExpGolombCodedInt2, readUnsignedExpGolombCodedInt3, z2);
    }

    public static PpsData parsePpsNalUnit(byte[] bArr, int i, int i2) {
        ParsableNalUnitBitArray parsableNalUnitBitArray = new ParsableNalUnitBitArray(bArr, i, i2);
        parsableNalUnitBitArray.skipBits(8);
        bArr = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        i = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.skipBit();
        return new PpsData(bArr, i, parsableNalUnitBitArray.readBit());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int findNalUnit(byte[] r7, int r8, int r9, boolean[] r10) {
        /*
        r0 = r9 - r8;
        r1 = 0;
        r2 = 1;
        if (r0 < 0) goto L_0x0008;
    L_0x0006:
        r3 = 1;
        goto L_0x0009;
    L_0x0008:
        r3 = 0;
    L_0x0009:
        com.google.android.exoplayer2.util.Assertions.checkState(r3);
        if (r0 != 0) goto L_0x000f;
    L_0x000e:
        return r9;
    L_0x000f:
        r3 = 2;
        if (r10 == 0) goto L_0x0040;
    L_0x0012:
        r4 = r10[r1];
        if (r4 == 0) goto L_0x001c;
    L_0x0016:
        clearPrefixFlags(r10);
        r8 = r8 + -3;
        return r8;
    L_0x001c:
        if (r0 <= r2) goto L_0x002b;
    L_0x001e:
        r4 = r10[r2];
        if (r4 == 0) goto L_0x002b;
    L_0x0022:
        r4 = r7[r8];
        if (r4 != r2) goto L_0x002b;
    L_0x0026:
        clearPrefixFlags(r10);
        r8 = r8 - r3;
        return r8;
    L_0x002b:
        if (r0 <= r3) goto L_0x0040;
    L_0x002d:
        r4 = r10[r3];
        if (r4 == 0) goto L_0x0040;
    L_0x0031:
        r4 = r7[r8];
        if (r4 != 0) goto L_0x0040;
    L_0x0035:
        r4 = r8 + 1;
        r4 = r7[r4];
        if (r4 != r2) goto L_0x0040;
    L_0x003b:
        clearPrefixFlags(r10);
        r8 = r8 - r2;
        return r8;
    L_0x0040:
        r4 = r9 + -1;
        r8 = r8 + r3;
    L_0x0043:
        if (r8 >= r4) goto L_0x0067;
    L_0x0045:
        r5 = r7[r8];
        r5 = r5 & 254;
        if (r5 == 0) goto L_0x004c;
    L_0x004b:
        goto L_0x0064;
    L_0x004c:
        r5 = r8 + -2;
        r6 = r7[r5];
        if (r6 != 0) goto L_0x0062;
    L_0x0052:
        r6 = r8 + -1;
        r6 = r7[r6];
        if (r6 != 0) goto L_0x0062;
    L_0x0058:
        r6 = r7[r8];
        if (r6 != r2) goto L_0x0062;
    L_0x005c:
        if (r10 == 0) goto L_0x0061;
    L_0x005e:
        clearPrefixFlags(r10);
    L_0x0061:
        return r5;
    L_0x0062:
        r8 = r8 + -2;
    L_0x0064:
        r8 = r8 + 3;
        goto L_0x0043;
    L_0x0067:
        if (r10 == 0) goto L_0x00bd;
    L_0x0069:
        if (r0 <= r3) goto L_0x007f;
    L_0x006b:
        r8 = r9 + -3;
        r8 = r7[r8];
        if (r8 != 0) goto L_0x007d;
    L_0x0071:
        r8 = r9 + -2;
        r8 = r7[r8];
        if (r8 != 0) goto L_0x007d;
    L_0x0077:
        r8 = r7[r4];
        if (r8 != r2) goto L_0x007d;
    L_0x007b:
        r8 = 1;
        goto L_0x0099;
    L_0x007d:
        r8 = 0;
        goto L_0x0099;
    L_0x007f:
        if (r0 != r3) goto L_0x0090;
    L_0x0081:
        r8 = r10[r3];
        if (r8 == 0) goto L_0x007d;
    L_0x0085:
        r8 = r9 + -2;
        r8 = r7[r8];
        if (r8 != 0) goto L_0x007d;
    L_0x008b:
        r8 = r7[r4];
        if (r8 != r2) goto L_0x007d;
    L_0x008f:
        goto L_0x007b;
    L_0x0090:
        r8 = r10[r2];
        if (r8 == 0) goto L_0x007d;
    L_0x0094:
        r8 = r7[r4];
        if (r8 != r2) goto L_0x007d;
    L_0x0098:
        goto L_0x007b;
    L_0x0099:
        r10[r1] = r8;
        if (r0 <= r2) goto L_0x00ab;
    L_0x009d:
        r8 = r9 + -2;
        r8 = r7[r8];
        if (r8 != 0) goto L_0x00a9;
    L_0x00a3:
        r8 = r7[r4];
        if (r8 != 0) goto L_0x00a9;
    L_0x00a7:
        r8 = 1;
        goto L_0x00b4;
    L_0x00a9:
        r8 = 0;
        goto L_0x00b4;
    L_0x00ab:
        r8 = r10[r3];
        if (r8 == 0) goto L_0x00a9;
    L_0x00af:
        r8 = r7[r4];
        if (r8 != 0) goto L_0x00a9;
    L_0x00b3:
        goto L_0x00a7;
    L_0x00b4:
        r10[r2] = r8;
        r7 = r7[r4];
        if (r7 != 0) goto L_0x00bb;
    L_0x00ba:
        r1 = 1;
    L_0x00bb:
        r10[r3] = r1;
    L_0x00bd:
        return r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.util.NalUnitUtil.findNalUnit(byte[], int, int, boolean[]):int");
    }

    public static void clearPrefixFlags(boolean[] zArr) {
        zArr[0] = false;
        zArr[1] = false;
        zArr[2] = false;
    }

    private static int findNextUnescapeIndex(byte[] bArr, int i, int i2) {
        while (i < i2 - 2) {
            if (bArr[i] == (byte) 0 && bArr[i + 1] == (byte) 0 && bArr[i + 2] == (byte) 3) {
                return i;
            }
            i++;
        }
        return i2;
    }

    private static void skipScalingList(ParsableNalUnitBitArray parsableNalUnitBitArray, int i) {
        int i2 = 8;
        int i3 = 8;
        for (int i4 = 0; i4 < i; i4++) {
            if (i2 != 0) {
                i2 = ((parsableNalUnitBitArray.readSignedExpGolombCodedInt() + i3) + 256) % 256;
            }
            if (i2 != 0) {
                i3 = i2;
            }
        }
    }

    private NalUnitUtil() {
    }
}
