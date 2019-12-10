package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader.TrackIdGenerator;
import com.google.android.exoplayer2.text.cea.CeaUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.List;

final class UserDataReader {
    private static final int USER_DATA_START_CODE = 434;
    private final List<Format> closedCaptionFormats;
    private final TrackOutput[] outputs;

    public UserDataReader(List<Format> list) {
        this.closedCaptionFormats = list;
        this.outputs = new TrackOutput[list.size()];
    }

    public void createTracks(ExtractorOutput extractorOutput, TrackIdGenerator trackIdGenerator) {
        UserDataReader userDataReader = this;
        for (int i = 0; i < userDataReader.outputs.length; i++) {
            boolean z;
            StringBuilder stringBuilder;
            trackIdGenerator.generateNewId();
            TrackOutput track = extractorOutput.track(trackIdGenerator.getTrackId(), 3);
            Format format = (Format) userDataReader.closedCaptionFormats.get(i);
            String str = format.sampleMimeType;
            if (!MimeTypes.APPLICATION_CEA608.equals(str)) {
                if (!MimeTypes.APPLICATION_CEA708.equals(str)) {
                    z = false;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid closed caption mime type provided: ");
                    stringBuilder.append(str);
                    Assertions.checkArgument(z, stringBuilder.toString());
                    track.format(Format.createTextSampleFormat(trackIdGenerator.getFormatId(), str, null, -1, format.selectionFlags, format.language, format.accessibilityChannel, null, Long.MAX_VALUE, format.initializationData));
                    userDataReader.outputs[i] = track;
                }
            }
            z = true;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid closed caption mime type provided: ");
            stringBuilder.append(str);
            Assertions.checkArgument(z, stringBuilder.toString());
            track.format(Format.createTextSampleFormat(trackIdGenerator.getFormatId(), str, null, -1, format.selectionFlags, format.language, format.accessibilityChannel, null, Long.MAX_VALUE, format.initializationData));
            userDataReader.outputs[i] = track;
        }
    }

    public void consume(long j, ParsableByteArray parsableByteArray) {
        if (parsableByteArray.bytesLeft() >= 9) {
            int readInt = parsableByteArray.readInt();
            int readInt2 = parsableByteArray.readInt();
            int readUnsignedByte = parsableByteArray.readUnsignedByte();
            if (readInt == USER_DATA_START_CODE && readInt2 == CeaUtil.USER_DATA_IDENTIFIER_GA94 && readUnsignedByte == 3) {
                CeaUtil.consumeCcData(j, parsableByteArray, this.outputs);
            }
        }
    }
}
