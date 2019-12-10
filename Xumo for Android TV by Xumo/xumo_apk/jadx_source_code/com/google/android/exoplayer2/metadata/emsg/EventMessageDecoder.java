package com.google.android.exoplayer2.metadata.emsg;

import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataDecoder;
import com.google.android.exoplayer2.metadata.MetadataInputBuffer;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;

public final class EventMessageDecoder implements MetadataDecoder {
    public Metadata decode(MetadataInputBuffer metadataInputBuffer) {
        metadataInputBuffer = metadataInputBuffer.data;
        byte[] array = metadataInputBuffer.array();
        metadataInputBuffer = metadataInputBuffer.limit();
        ParsableByteArray parsableByteArray = new ParsableByteArray(array, metadataInputBuffer);
        String str = (String) Assertions.checkNotNull(parsableByteArray.readNullTerminatedString());
        String str2 = (String) Assertions.checkNotNull(parsableByteArray.readNullTerminatedString());
        long readUnsignedInt = parsableByteArray.readUnsignedInt();
        long scaleLargeTimestamp = Util.scaleLargeTimestamp(parsableByteArray.readUnsignedInt(), 1000000, readUnsignedInt);
        long scaleLargeTimestamp2 = Util.scaleLargeTimestamp(parsableByteArray.readUnsignedInt(), 1000, readUnsignedInt);
        long readUnsignedInt2 = parsableByteArray.readUnsignedInt();
        byte[] copyOfRange = Arrays.copyOfRange(array, parsableByteArray.getPosition(), metadataInputBuffer);
        return new Metadata(new EventMessage(str, str2, scaleLargeTimestamp2, readUnsignedInt2, copyOfRange, scaleLargeTimestamp));
    }
}
