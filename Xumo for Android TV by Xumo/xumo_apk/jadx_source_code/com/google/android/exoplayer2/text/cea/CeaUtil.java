package com.google.android.exoplayer2.text.cea;

import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;

public final class CeaUtil {
    private static final int COUNTRY_CODE = 181;
    private static final int PAYLOAD_TYPE_CC = 4;
    private static final int PROVIDER_CODE_ATSC = 49;
    private static final int PROVIDER_CODE_DIRECTV = 47;
    private static final String TAG = "CeaUtil";
    public static final int USER_DATA_IDENTIFIER_GA94 = Util.getIntegerCodeForString("GA94");
    public static final int USER_DATA_TYPE_CODE_MPEG_CC = 3;

    public static void consume(long j, ParsableByteArray parsableByteArray, TrackOutput[] trackOutputArr) {
        while (true) {
            int i = 1;
            if (parsableByteArray.bytesLeft() > 1) {
                int readNon255TerminatedValue = readNon255TerminatedValue(parsableByteArray);
                int readNon255TerminatedValue2 = readNon255TerminatedValue(parsableByteArray);
                int position = parsableByteArray.getPosition() + readNon255TerminatedValue2;
                if (readNon255TerminatedValue2 != -1) {
                    if (readNon255TerminatedValue2 <= parsableByteArray.bytesLeft()) {
                        if (readNon255TerminatedValue == 4 && readNon255TerminatedValue2 >= 8) {
                            readNon255TerminatedValue = parsableByteArray.readUnsignedByte();
                            readNon255TerminatedValue2 = parsableByteArray.readUnsignedShort();
                            int readInt = readNon255TerminatedValue2 == 49 ? parsableByteArray.readInt() : 0;
                            int readUnsignedByte = parsableByteArray.readUnsignedByte();
                            if (readNon255TerminatedValue2 == 47) {
                                parsableByteArray.skipBytes(1);
                            }
                            readNon255TerminatedValue = (readNon255TerminatedValue == COUNTRY_CODE && ((readNon255TerminatedValue2 == 49 || readNon255TerminatedValue2 == 47) && readUnsignedByte == 3)) ? 1 : 0;
                            if (readNon255TerminatedValue2 == 49) {
                                if (readInt != USER_DATA_IDENTIFIER_GA94) {
                                    i = 0;
                                }
                                readNon255TerminatedValue &= i;
                            }
                            if (readNon255TerminatedValue != 0) {
                                consumeCcData(j, parsableByteArray, trackOutputArr);
                            }
                        }
                        parsableByteArray.setPosition(position);
                    }
                }
                Log.w(TAG, "Skipping remainder of malformed SEI NAL unit.");
                position = parsableByteArray.limit();
                parsableByteArray.setPosition(position);
            } else {
                return;
            }
        }
    }

    public static void consumeCcData(long j, ParsableByteArray parsableByteArray, TrackOutput[] trackOutputArr) {
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        if (((readUnsignedByte & 64) != 0 ? 1 : null) != null) {
            readUnsignedByte &= 31;
            parsableByteArray.skipBytes(1);
            readUnsignedByte *= 3;
            int position = parsableByteArray.getPosition();
            for (TrackOutput trackOutput : trackOutputArr) {
                parsableByteArray.setPosition(position);
                trackOutput.sampleData(parsableByteArray, readUnsignedByte);
                trackOutput.sampleMetadata(j, 1, readUnsignedByte, 0, null);
            }
        }
    }

    private static int readNon255TerminatedValue(ParsableByteArray parsableByteArray) {
        int i = 0;
        while (parsableByteArray.bytesLeft() != 0) {
            int readUnsignedByte = parsableByteArray.readUnsignedByte();
            i += readUnsignedByte;
            if (readUnsignedByte != 255) {
                return i;
            }
        }
        return -1;
    }

    private CeaUtil() {
    }
}
