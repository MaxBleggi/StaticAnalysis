package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.ParsableByteArray;

public final class TsUtil {
    public static int findSyncBytePosition(byte[] bArr, int i, int i2) {
        while (i < i2 && bArr[i] != (byte) 71) {
            i++;
        }
        return i;
    }

    public static long readPcrFromPacket(ParsableByteArray parsableByteArray, int i, int i2) {
        parsableByteArray.setPosition(i);
        if (parsableByteArray.bytesLeft() < 5) {
            return C.TIME_UNSET;
        }
        i = parsableByteArray.readInt();
        if ((8388608 & i) != 0 || ((2096896 & i) >> 8) != i2) {
            return C.TIME_UNSET;
        }
        i2 = 1;
        if (((i & 32) != 0 ? 1 : 0) != 0 && parsableByteArray.readUnsignedByte() >= 7 && parsableByteArray.bytesLeft() >= 7) {
            if ((parsableByteArray.readUnsignedByte() & 16) != 16) {
                i2 = 0;
            }
            if (i2 != 0) {
                i = new byte[6];
                parsableByteArray.readBytes(i, 0, i.length);
                return readPcrValueFromPcrBytes(i);
            }
        }
        return C.TIME_UNSET;
    }

    private static long readPcrValueFromPcrBytes(byte[] bArr) {
        return (((((((long) bArr[0]) & 255) << 25) | ((((long) bArr[1]) & 255) << 17)) | ((((long) bArr[2]) & 255) << 9)) | ((((long) bArr[3]) & 255) << 1)) | ((255 & ((long) bArr[4])) >> 7);
    }

    private TsUtil() {
    }
}
