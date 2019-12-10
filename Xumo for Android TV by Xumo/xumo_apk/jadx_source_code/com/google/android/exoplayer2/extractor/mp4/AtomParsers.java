package com.google.android.exoplayer2.extractor.mp4;

import android.util.Pair;
import androidx.core.internal.view.SupportMenu;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.Ac3Util;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.GaplessInfoHolder;
import com.google.android.exoplayer2.extractor.mp4.FixedSampleSizeRechunker.Results;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.Metadata.Entry;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.AvcConfig;
import com.google.android.exoplayer2.video.HevcConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

final class AtomParsers {
    private static final int MAX_GAPLESS_TRIM_SIZE_SAMPLES = 3;
    private static final String TAG = "AtomParsers";
    private static final int TYPE_clcp = Util.getIntegerCodeForString("clcp");
    private static final int TYPE_meta = Util.getIntegerCodeForString("meta");
    private static final int TYPE_sbtl = Util.getIntegerCodeForString("sbtl");
    private static final int TYPE_soun = Util.getIntegerCodeForString("soun");
    private static final int TYPE_subt = Util.getIntegerCodeForString("subt");
    private static final int TYPE_text = Util.getIntegerCodeForString(MimeTypes.BASE_TYPE_TEXT);
    private static final int TYPE_vide = Util.getIntegerCodeForString("vide");

    private static final class ChunkIterator {
        private final ParsableByteArray chunkOffsets;
        private final boolean chunkOffsetsAreLongs;
        public int index;
        public final int length;
        private int nextSamplesPerChunkChangeIndex;
        public int numSamples;
        public long offset;
        private int remainingSamplesPerChunkChanges;
        private final ParsableByteArray stsc;

        public ChunkIterator(ParsableByteArray parsableByteArray, ParsableByteArray parsableByteArray2, boolean z) {
            this.stsc = parsableByteArray;
            this.chunkOffsets = parsableByteArray2;
            this.chunkOffsetsAreLongs = z;
            parsableByteArray2.setPosition(12);
            this.length = parsableByteArray2.readUnsignedIntToInt();
            parsableByteArray.setPosition(12);
            this.remainingSamplesPerChunkChanges = parsableByteArray.readUnsignedIntToInt();
            parsableByteArray2 = true;
            if (parsableByteArray.readInt() != 1) {
                parsableByteArray2 = null;
            }
            Assertions.checkState(parsableByteArray2, "first_chunk must be 1");
            this.index = -1;
        }

        public boolean moveNext() {
            int i = this.index + 1;
            this.index = i;
            if (i == this.length) {
                return false;
            }
            long readUnsignedLongToLong;
            if (this.chunkOffsetsAreLongs) {
                readUnsignedLongToLong = this.chunkOffsets.readUnsignedLongToLong();
            } else {
                readUnsignedLongToLong = this.chunkOffsets.readUnsignedInt();
            }
            this.offset = readUnsignedLongToLong;
            if (this.index == this.nextSamplesPerChunkChangeIndex) {
                this.numSamples = this.stsc.readUnsignedIntToInt();
                this.stsc.skipBytes(4);
                i = this.remainingSamplesPerChunkChanges - 1;
                this.remainingSamplesPerChunkChanges = i;
                this.nextSamplesPerChunkChangeIndex = i > 0 ? this.stsc.readUnsignedIntToInt() - 1 : -1;
            }
            return true;
        }
    }

    private interface SampleSizeBox {
        int getSampleCount();

        boolean isFixedSampleSize();

        int readNextSampleSize();
    }

    private static final class StsdData {
        public static final int STSD_HEADER_SIZE = 8;
        public Format format;
        public int nalUnitLengthFieldLength;
        public int requiredSampleTransformation = 0;
        public final TrackEncryptionBox[] trackEncryptionBoxes;

        public StsdData(int i) {
            this.trackEncryptionBoxes = new TrackEncryptionBox[i];
        }
    }

    private static final class TkhdData {
        private final long duration;
        private final int id;
        private final int rotationDegrees;

        public TkhdData(int i, long j, int i2) {
            this.id = i;
            this.duration = j;
            this.rotationDegrees = i2;
        }
    }

    static final class StszSampleSizeBox implements SampleSizeBox {
        private final ParsableByteArray data;
        private final int fixedSampleSize = this.data.readUnsignedIntToInt();
        private final int sampleCount = this.data.readUnsignedIntToInt();

        public StszSampleSizeBox(LeafAtom leafAtom) {
            this.data = leafAtom.data;
            this.data.setPosition(12);
        }

        public int getSampleCount() {
            return this.sampleCount;
        }

        public int readNextSampleSize() {
            return this.fixedSampleSize == 0 ? this.data.readUnsignedIntToInt() : this.fixedSampleSize;
        }

        public boolean isFixedSampleSize() {
            return this.fixedSampleSize != 0;
        }
    }

    static final class Stz2SampleSizeBox implements SampleSizeBox {
        private int currentByte;
        private final ParsableByteArray data;
        private final int fieldSize = (this.data.readUnsignedIntToInt() & 255);
        private final int sampleCount = this.data.readUnsignedIntToInt();
        private int sampleIndex;

        public boolean isFixedSampleSize() {
            return false;
        }

        public Stz2SampleSizeBox(LeafAtom leafAtom) {
            this.data = leafAtom.data;
            this.data.setPosition(12);
        }

        public int getSampleCount() {
            return this.sampleCount;
        }

        public int readNextSampleSize() {
            if (this.fieldSize == 8) {
                return this.data.readUnsignedByte();
            }
            if (this.fieldSize == 16) {
                return this.data.readUnsignedShort();
            }
            int i = this.sampleIndex;
            this.sampleIndex = i + 1;
            if (i % 2 != 0) {
                return this.currentByte & 15;
            }
            this.currentByte = this.data.readUnsignedByte();
            return (this.currentByte & PsExtractor.VIDEO_STREAM_MASK) >> 4;
        }
    }

    public static Track parseTrak(ContainerAtom containerAtom, LeafAtom leafAtom, long j, DrmInitData drmInitData, boolean z, boolean z2) throws ParserException {
        ContainerAtom containerAtom2 = containerAtom;
        ContainerAtom containerAtomOfType = containerAtom2.getContainerAtomOfType(Atom.TYPE_mdia);
        int parseHdlr = parseHdlr(containerAtomOfType.getLeafAtomOfType(Atom.TYPE_hdlr).data);
        if (parseHdlr == -1) {
            return null;
        }
        long access$000;
        LeafAtom leafAtom2;
        long[] jArr;
        long[] jArr2;
        Track track;
        TkhdData parseTkhd = parseTkhd(containerAtom2.getLeafAtomOfType(Atom.TYPE_tkhd).data);
        long j2 = C.TIME_UNSET;
        if (j == C.TIME_UNSET) {
            access$000 = parseTkhd.duration;
            leafAtom2 = leafAtom;
        } else {
            leafAtom2 = leafAtom;
            access$000 = j;
        }
        long parseMvhd = parseMvhd(leafAtom2.data);
        if (access$000 != C.TIME_UNSET) {
            j2 = Util.scaleLargeTimestamp(access$000, 1000000, parseMvhd);
        }
        long j3 = j2;
        ContainerAtom containerAtomOfType2 = containerAtomOfType.getContainerAtomOfType(Atom.TYPE_minf).getContainerAtomOfType(Atom.TYPE_stbl);
        Pair parseMdhd = parseMdhd(containerAtomOfType.getLeafAtomOfType(Atom.TYPE_mdhd).data);
        StsdData parseStsd = parseStsd(containerAtomOfType2.getLeafAtomOfType(Atom.TYPE_stsd).data, parseTkhd.id, parseTkhd.rotationDegrees, (String) parseMdhd.second, drmInitData, z2);
        if (z) {
            jArr = null;
            jArr2 = jArr;
        } else {
            Pair parseEdts = parseEdts(containerAtom2.getContainerAtomOfType(Atom.TYPE_edts));
            jArr2 = (long[]) parseEdts.second;
            jArr = (long[]) parseEdts.first;
        }
        if (parseStsd.format == null) {
            track = null;
        } else {
            int access$100 = parseTkhd.id;
            j2 = ((Long) parseMdhd.first).longValue();
            Format format = parseStsd.format;
            int i = parseStsd.requiredSampleTransformation;
            TrackEncryptionBox[] trackEncryptionBoxArr = parseStsd.trackEncryptionBoxes;
            int i2 = parseStsd.nalUnitLengthFieldLength;
            Track track2 = new Track(access$100, parseHdlr, j2, parseMvhd, j3, format, i, trackEncryptionBoxArr, i2, jArr, jArr2);
        }
        return track;
    }

    public static TrackSampleTable parseStbl(Track track, ContainerAtom containerAtom, GaplessInfoHolder gaplessInfoHolder) throws ParserException {
        SampleSizeBox stszSampleSizeBox;
        Track track2 = track;
        ContainerAtom containerAtom2 = containerAtom;
        GaplessInfoHolder gaplessInfoHolder2 = gaplessInfoHolder;
        LeafAtom leafAtomOfType = containerAtom2.getLeafAtomOfType(Atom.TYPE_stsz);
        if (leafAtomOfType != null) {
            stszSampleSizeBox = new StszSampleSizeBox(leafAtomOfType);
        } else {
            leafAtomOfType = containerAtom2.getLeafAtomOfType(Atom.TYPE_stz2);
            if (leafAtomOfType != null) {
                stszSampleSizeBox = new Stz2SampleSizeBox(leafAtomOfType);
            } else {
                throw new ParserException("Track has no sample table size information");
            }
        }
        int sampleCount = stszSampleSizeBox.getSampleCount();
        if (sampleCount == 0) {
            return new TrackSampleTable(track, new long[0], new int[0], 0, new long[0], new int[0], C.TIME_UNSET);
        }
        boolean z;
        int readUnsignedIntToInt;
        int readUnsignedIntToInt2;
        int i;
        int i2;
        long j;
        int i3;
        long j2;
        Object obj;
        int i4;
        Object obj2;
        Object obj3;
        Object obj4;
        long[] jArr;
        int i5;
        Object obj5;
        Object obj6;
        LeafAtom leafAtomOfType2 = containerAtom2.getLeafAtomOfType(Atom.TYPE_stco);
        if (leafAtomOfType2 == null) {
            leafAtomOfType2 = containerAtom2.getLeafAtomOfType(Atom.TYPE_co64);
            z = true;
        } else {
            z = false;
        }
        ParsableByteArray parsableByteArray = leafAtomOfType2.data;
        ParsableByteArray parsableByteArray2 = containerAtom2.getLeafAtomOfType(Atom.TYPE_stsc).data;
        ParsableByteArray parsableByteArray3 = containerAtom2.getLeafAtomOfType(Atom.TYPE_stts).data;
        LeafAtom leafAtomOfType3 = containerAtom2.getLeafAtomOfType(Atom.TYPE_stss);
        ParsableByteArray parsableByteArray4 = leafAtomOfType3 != null ? leafAtomOfType3.data : null;
        LeafAtom leafAtomOfType4 = containerAtom2.getLeafAtomOfType(Atom.TYPE_ctts);
        ParsableByteArray parsableByteArray5 = leafAtomOfType4 != null ? leafAtomOfType4.data : null;
        ChunkIterator chunkIterator = new ChunkIterator(parsableByteArray2, parsableByteArray, z);
        parsableByteArray3.setPosition(12);
        int readUnsignedIntToInt3 = parsableByteArray3.readUnsignedIntToInt() - 1;
        int readUnsignedIntToInt4 = parsableByteArray3.readUnsignedIntToInt();
        int readUnsignedIntToInt5 = parsableByteArray3.readUnsignedIntToInt();
        if (parsableByteArray5 != null) {
            parsableByteArray5.setPosition(12);
            readUnsignedIntToInt = parsableByteArray5.readUnsignedIntToInt();
        } else {
            readUnsignedIntToInt = 0;
        }
        int i6 = -1;
        if (parsableByteArray4 != null) {
            parsableByteArray4.setPosition(12);
            readUnsignedIntToInt2 = parsableByteArray4.readUnsignedIntToInt();
            if (readUnsignedIntToInt2 > 0) {
                i6 = parsableByteArray4.readUnsignedIntToInt() - 1;
            } else {
                parsableByteArray4 = null;
            }
        } else {
            readUnsignedIntToInt2 = 0;
        }
        Object obj7 = (stszSampleSizeBox.isFixedSampleSize() && MimeTypes.AUDIO_RAW.equals(track2.format.sampleMimeType) && readUnsignedIntToInt3 == 0 && readUnsignedIntToInt == 0 && readUnsignedIntToInt2 == 0) ? 1 : null;
        long j3 = 0;
        if (obj7 == null) {
            int i7;
            int i8;
            long[] jArr2;
            obj7 = new long[sampleCount];
            Object obj8 = new int[sampleCount];
            long[] jArr3 = new long[sampleCount];
            int i9 = readUnsignedIntToInt2;
            Object obj9 = new int[sampleCount];
            int i10 = readUnsignedIntToInt3;
            int i11 = readUnsignedIntToInt4;
            ParsableByteArray parsableByteArray6 = parsableByteArray3;
            i = readUnsignedIntToInt5;
            int i12 = readUnsignedIntToInt;
            i2 = i6;
            j = 0;
            readUnsignedIntToInt4 = i9;
            int i13 = 0;
            readUnsignedIntToInt3 = 0;
            i6 = 0;
            int i14 = 0;
            i3 = 0;
            j2 = j;
            while (readUnsignedIntToInt3 < sampleCount) {
                int i15;
                int i16;
                int i17;
                while (i14 == 0) {
                    Assertions.checkState(chunkIterator.moveNext());
                    i7 = readUnsignedIntToInt4;
                    i15 = i;
                    long j4 = chunkIterator.offset;
                    i14 = chunkIterator.numSamples;
                    readUnsignedIntToInt4 = i7;
                    i = i15;
                    j2 = j4;
                }
                i7 = readUnsignedIntToInt4;
                i15 = i;
                if (parsableByteArray5 != null) {
                    while (i6 == 0 && i12 > 0) {
                        i6 = parsableByteArray5.readUnsignedIntToInt();
                        i3 = parsableByteArray5.readInt();
                        i12--;
                    }
                    i6--;
                }
                readUnsignedIntToInt4 = i3;
                obj7[readUnsignedIntToInt3] = j2;
                obj8[readUnsignedIntToInt3] = stszSampleSizeBox.readNextSampleSize();
                if (obj8[readUnsignedIntToInt3] > i13) {
                    i13 = obj8[readUnsignedIntToInt3];
                }
                SampleSizeBox sampleSizeBox = stszSampleSizeBox;
                obj = obj7;
                jArr3[readUnsignedIntToInt3] = ((long) readUnsignedIntToInt4) + j;
                obj9[readUnsignedIntToInt3] = parsableByteArray4 == null ? 1 : 0;
                if (readUnsignedIntToInt3 == i2) {
                    obj9[readUnsignedIntToInt3] = 1;
                    i4 = i7 - 1;
                    if (i4 > 0) {
                        i2 = parsableByteArray4.readUnsignedIntToInt() - 1;
                    }
                    i16 = i2;
                    i17 = i4;
                } else {
                    i16 = i2;
                    i17 = i7;
                }
                i2 = i15;
                j += (long) i2;
                i11--;
                if (i11 == 0) {
                    i8 = i10;
                    if (i8 > 0) {
                        i10 = i8 - 1;
                        i11 = parsableByteArray6.readUnsignedIntToInt();
                        i = parsableByteArray6.readInt();
                        j2 += (long) obj8[readUnsignedIntToInt3];
                        i14--;
                        readUnsignedIntToInt3++;
                        i3 = readUnsignedIntToInt4;
                        stszSampleSizeBox = sampleSizeBox;
                        obj7 = obj;
                        i2 = i16;
                        readUnsignedIntToInt4 = i17;
                    }
                } else {
                    i8 = i10;
                }
                i = i2;
                i10 = i8;
                j2 += (long) obj8[readUnsignedIntToInt3];
                i14--;
                readUnsignedIntToInt3++;
                i3 = readUnsignedIntToInt4;
                stszSampleSizeBox = sampleSizeBox;
                obj7 = obj;
                i2 = i16;
                readUnsignedIntToInt4 = i17;
            }
            obj = obj7;
            i7 = readUnsignedIntToInt4;
            i8 = i10;
            j += (long) i3;
            Assertions.checkArgument(i6 == 0);
            while (i12 > 0) {
                Assertions.checkArgument(parsableByteArray5.readUnsignedIntToInt() == 0);
                parsableByteArray5.readInt();
                i12--;
            }
            if (i7 == 0 && i11 == 0 && i14 == 0) {
                if (i8 == 0) {
                    i4 = i13;
                    track2 = track;
                    obj2 = obj8;
                    obj3 = obj;
                    jArr2 = jArr3;
                    obj4 = obj9;
                    jArr = jArr2;
                }
            }
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Inconsistent stbl box for track ");
            i4 = i13;
            track2 = track;
            stringBuilder.append(track2.id);
            stringBuilder.append(": remainingSynchronizationSamples ");
            stringBuilder.append(i7);
            stringBuilder.append(", remainingSamplesAtTimestampDelta ");
            stringBuilder.append(i11);
            stringBuilder.append(", remainingSamplesInChunk ");
            stringBuilder.append(i14);
            stringBuilder.append(", remainingTimestampDeltaChanges ");
            stringBuilder.append(i8);
            Log.w(str, stringBuilder.toString());
            obj2 = obj8;
            obj3 = obj;
            jArr2 = jArr3;
            obj4 = obj9;
            jArr = jArr2;
        } else {
            long[] jArr4 = new long[chunkIterator.length];
            int[] iArr = new int[chunkIterator.length];
            while (chunkIterator.moveNext()) {
                jArr4[chunkIterator.index] = chunkIterator.offset;
                iArr[chunkIterator.index] = chunkIterator.numSamples;
            }
            Results rechunk = FixedSampleSizeRechunker.rechunk(Util.getPcmFrameSize(track2.format.pcmEncoding, track2.format.channelCount), jArr4, iArr, (long) readUnsignedIntToInt5);
            obj3 = rechunk.offsets;
            obj2 = rechunk.sizes;
            i4 = rechunk.maximumSize;
            jArr = rechunk.timestamps;
            obj4 = rechunk.flags;
            j = rechunk.duration;
        }
        long scaleLargeTimestamp = Util.scaleLargeTimestamp(j, 1000000, track2.timescale);
        if (track2.editListDurations != null) {
            if (!gaplessInfoHolder.hasGaplessInfo()) {
                if (track2.editListDurations.length == 1 && track2.type == 1 && jArr.length >= 2) {
                    long j5 = track2.editListMediaTimes[0];
                    scaleLargeTimestamp = Util.scaleLargeTimestamp(track2.editListDurations[0], track2.timescale, track2.movieTimescale) + j5;
                    if (canApplyEditWithGaplessInfo(jArr, j, j5, scaleLargeTimestamp)) {
                        j2 = j - scaleLargeTimestamp;
                        scaleLargeTimestamp = Util.scaleLargeTimestamp(j5 - jArr[0], (long) track2.format.sampleRate, track2.timescale);
                        long scaleLargeTimestamp2 = Util.scaleLargeTimestamp(j2, (long) track2.format.sampleRate, track2.timescale);
                        if (!(scaleLargeTimestamp == 0 && scaleLargeTimestamp2 == 0) && scaleLargeTimestamp <= 2147483647L && scaleLargeTimestamp2 <= 2147483647L) {
                            GaplessInfoHolder gaplessInfoHolder3 = gaplessInfoHolder;
                            gaplessInfoHolder3.encoderDelay = (int) scaleLargeTimestamp;
                            gaplessInfoHolder3.encoderPadding = (int) scaleLargeTimestamp2;
                            Util.scaleLargeTimestampsInPlace(jArr, 1000000, track2.timescale);
                            return new TrackSampleTable(track, obj3, obj2, i4, jArr, obj4, Util.scaleLargeTimestamp(track2.editListDurations[0], 1000000, track2.movieTimescale));
                        }
                    }
                }
                int i18;
                if (track2.editListDurations.length == 1 && track2.editListDurations[0] == 0) {
                    long j6 = track2.editListMediaTimes[0];
                    for (i18 = 0; i18 < jArr.length; i18++) {
                        jArr[i18] = Util.scaleLargeTimestamp(jArr[i18] - j6, 1000000, track2.timescale);
                    }
                    return new TrackSampleTable(track, obj3, obj2, i4, jArr, obj4, Util.scaleLargeTimestamp(j - j6, 1000000, track2.timescale));
                }
                Object obj10;
                Object obj11;
                boolean z2 = track2.type == 1;
                int[] iArr2 = new int[track2.editListDurations.length];
                int[] iArr3 = new int[track2.editListDurations.length];
                i = 0;
                int i19 = 0;
                int i20 = 0;
                int i21 = 0;
                while (i < track2.editListDurations.length) {
                    int i22;
                    obj10 = obj2;
                    i5 = i4;
                    long j7 = track2.editListMediaTimes[i];
                    if (j7 != -1) {
                        obj11 = obj3;
                        i22 = sampleCount;
                        long scaleLargeTimestamp3 = Util.scaleLargeTimestamp(track2.editListDurations[i], track2.timescale, track2.movieTimescale);
                        iArr2[i] = Util.binarySearchCeil(jArr, j7, true, true);
                        iArr3[i] = Util.binarySearchCeil(jArr, j7 + scaleLargeTimestamp3, z2, false);
                        while (iArr2[i] < iArr3[i] && (obj4[iArr2[i]] & 1) == 0) {
                            iArr2[i] = iArr2[i] + 1;
                        }
                        i20 += iArr3[i] - iArr2[i];
                        i19 = (i21 != iArr2[i] ? 1 : 0) | i19;
                        i21 = iArr3[i];
                    } else {
                        obj11 = obj3;
                        i22 = sampleCount;
                    }
                    i++;
                    obj2 = obj10;
                    i4 = i5;
                    obj3 = obj11;
                    sampleCount = i22;
                }
                obj11 = obj3;
                obj10 = obj2;
                i5 = i4;
                i2 = 0;
                readUnsignedIntToInt5 = 1;
                if (i20 == sampleCount) {
                    readUnsignedIntToInt5 = 0;
                }
                i18 = i19 | readUnsignedIntToInt5;
                Object obj12 = i18 != 0 ? new long[i20] : obj11;
                obj2 = i18 != 0 ? new int[i20] : obj10;
                if (i18 != 0) {
                    i5 = 0;
                }
                Object obj13 = i18 != 0 ? new int[i20] : obj4;
                long[] jArr5 = new long[i20];
                i19 = 0;
                while (i2 < track2.editListDurations.length) {
                    Object obj14;
                    Object obj15;
                    int[] iArr4;
                    long j8 = track2.editListMediaTimes[i2];
                    i20 = iArr2[i2];
                    readUnsignedIntToInt = iArr3[i2];
                    if (i18 != 0) {
                        i21 = readUnsignedIntToInt - i20;
                        obj14 = obj11;
                        System.arraycopy(obj14, i20, obj12, i19, i21);
                        obj15 = obj14;
                        obj14 = obj10;
                        System.arraycopy(obj14, i20, obj2, i19, i21);
                        System.arraycopy(obj4, i20, obj13, i19, i21);
                    } else {
                        obj14 = obj10;
                        obj15 = obj11;
                    }
                    int i23 = i19;
                    i21 = i20;
                    i19 = i5;
                    while (i21 < readUnsignedIntToInt) {
                        int[] iArr5 = iArr2;
                        iArr4 = iArr3;
                        obj5 = obj4;
                        int i24 = i19;
                        i3 = i21;
                        obj6 = obj14;
                        obj = obj15;
                        int i25 = readUnsignedIntToInt;
                        jArr5[i23] = Util.scaleLargeTimestamp(j3, 1000000, track2.movieTimescale) + Util.scaleLargeTimestamp(jArr[i3] - j8, 1000000, track2.timescale);
                        if (i18 != 0 && obj2[i23] > i24) {
                            i24 = obj6[i3];
                        }
                        i19 = i24;
                        i23++;
                        i21 = i3 + 1;
                        readUnsignedIntToInt = i25;
                        obj14 = obj6;
                        obj15 = obj;
                        iArr2 = iArr5;
                        iArr3 = iArr4;
                        obj4 = obj5;
                    }
                    iArr4 = iArr3;
                    j3 += track2.editListDurations[i2];
                    i2++;
                    i5 = i19;
                    i19 = i23;
                    obj10 = obj14;
                    obj11 = obj15;
                    iArr2 = iArr2;
                    obj4 = obj4;
                }
                return new TrackSampleTable(track, obj12, obj2, i5, jArr5, obj13, Util.scaleLargeTimestamp(j3, 1000000, track2.movieTimescale));
            }
        }
        obj = obj3;
        obj6 = obj2;
        i5 = i4;
        obj5 = obj4;
        Util.scaleLargeTimestampsInPlace(jArr, 1000000, track2.timescale);
        return new TrackSampleTable(track, obj, obj6, i5, jArr, obj5, scaleLargeTimestamp);
    }

    public static Metadata parseUdta(LeafAtom leafAtom, boolean z) {
        if (z) {
            return null;
        }
        leafAtom = leafAtom.data;
        leafAtom.setPosition(8);
        while (leafAtom.bytesLeft() >= 8) {
            int position = leafAtom.getPosition();
            int readInt = leafAtom.readInt();
            if (leafAtom.readInt() == Atom.TYPE_meta) {
                leafAtom.setPosition(position);
                return parseMetaAtom(leafAtom, position + readInt);
            }
            leafAtom.skipBytes(readInt - 8);
        }
        return null;
    }

    private static Metadata parseMetaAtom(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.skipBytes(12);
        while (parsableByteArray.getPosition() < i) {
            int position = parsableByteArray.getPosition();
            int readInt = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == Atom.TYPE_ilst) {
                parsableByteArray.setPosition(position);
                return parseIlst(parsableByteArray, position + readInt);
            }
            parsableByteArray.skipBytes(readInt - 8);
        }
        return null;
    }

    private static Metadata parseIlst(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.skipBytes(8);
        List arrayList = new ArrayList();
        while (parsableByteArray.getPosition() < i) {
            Entry parseIlstElement = MetadataUtil.parseIlstElement(parsableByteArray);
            if (parseIlstElement != null) {
                arrayList.add(parseIlstElement);
            }
        }
        return arrayList.isEmpty() != null ? null : new Metadata(arrayList);
    }

    private static long parseMvhd(ParsableByteArray parsableByteArray) {
        int i = 8;
        parsableByteArray.setPosition(8);
        if (Atom.parseFullAtomVersion(parsableByteArray.readInt()) != 0) {
            i = 16;
        }
        parsableByteArray.skipBytes(i);
        return parsableByteArray.readUnsignedInt();
    }

    private static TkhdData parseTkhd(ParsableByteArray parsableByteArray) {
        Object obj;
        int i = 8;
        parsableByteArray.setPosition(8);
        int parseFullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
        parsableByteArray.skipBytes(parseFullAtomVersion == 0 ? 8 : 16);
        int readInt = parsableByteArray.readInt();
        parsableByteArray.skipBytes(4);
        int position = parsableByteArray.getPosition();
        if (parseFullAtomVersion == 0) {
            i = 4;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            if (parsableByteArray.data[position + i3] != (byte) -1) {
                obj = null;
                break;
            }
        }
        obj = 1;
        long j = C.TIME_UNSET;
        if (obj != null) {
            parsableByteArray.skipBytes(i);
        } else {
            long readUnsignedInt = parseFullAtomVersion == 0 ? parsableByteArray.readUnsignedInt() : parsableByteArray.readUnsignedLongToLong();
            if (readUnsignedInt != 0) {
                j = readUnsignedInt;
            }
        }
        parsableByteArray.skipBytes(16);
        i = parsableByteArray.readInt();
        parseFullAtomVersion = parsableByteArray.readInt();
        parsableByteArray.skipBytes(4);
        int readInt2 = parsableByteArray.readInt();
        parsableByteArray = parsableByteArray.readInt();
        if (i == 0 && parseFullAtomVersion == 65536 && readInt2 == SupportMenu.CATEGORY_MASK && parsableByteArray == null) {
            i2 = 90;
        } else if (i == 0 && parseFullAtomVersion == SupportMenu.CATEGORY_MASK && readInt2 == 65536 && parsableByteArray == null) {
            i2 = 270;
        } else if (i == SupportMenu.CATEGORY_MASK && parseFullAtomVersion == 0 && readInt2 == 0 && parsableByteArray == -65536) {
            i2 = 180;
        }
        return new TkhdData(readInt, j, i2);
    }

    private static int parseHdlr(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(16);
        parsableByteArray = parsableByteArray.readInt();
        if (parsableByteArray == TYPE_soun) {
            return 1;
        }
        if (parsableByteArray == TYPE_vide) {
            return 2;
        }
        if (!(parsableByteArray == TYPE_text || parsableByteArray == TYPE_sbtl || parsableByteArray == TYPE_subt)) {
            if (parsableByteArray != TYPE_clcp) {
                return parsableByteArray == TYPE_meta ? 4 : -1;
            }
        }
        return 3;
    }

    private static Pair<Long, String> parseMdhd(ParsableByteArray parsableByteArray) {
        int i = 8;
        parsableByteArray.setPosition(8);
        int parseFullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
        parsableByteArray.skipBytes(parseFullAtomVersion == 0 ? 8 : 16);
        long readUnsignedInt = parsableByteArray.readUnsignedInt();
        if (parseFullAtomVersion == 0) {
            i = 4;
        }
        parsableByteArray.skipBytes(i);
        parsableByteArray = parsableByteArray.readUnsignedShort();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append((char) (((parsableByteArray >> 10) & 31) + 96));
        stringBuilder.append((char) (((parsableByteArray >> 5) & 31) + 96));
        stringBuilder.append((char) ((parsableByteArray & 31) + 96));
        return Pair.create(Long.valueOf(readUnsignedInt), stringBuilder.toString());
    }

    private static StsdData parseStsd(ParsableByteArray parsableByteArray, int i, int i2, String str, DrmInitData drmInitData, boolean z) throws ParserException {
        ParsableByteArray parsableByteArray2 = parsableByteArray;
        parsableByteArray2.setPosition(12);
        int readInt = parsableByteArray.readInt();
        StsdData stsdData = new StsdData(readInt);
        for (int i3 = 0; i3 < readInt; i3++) {
            int position = parsableByteArray.getPosition();
            int readInt2 = parsableByteArray.readInt();
            Assertions.checkArgument(readInt2 > 0, "childAtomSize should be positive");
            int readInt3 = parsableByteArray.readInt();
            if (!(readInt3 == Atom.TYPE_avc1 || readInt3 == Atom.TYPE_avc3 || readInt3 == Atom.TYPE_encv || readInt3 == Atom.TYPE_mp4v || readInt3 == Atom.TYPE_hvc1 || readInt3 == Atom.TYPE_hev1 || readInt3 == Atom.TYPE_s263 || readInt3 == Atom.TYPE_vp08)) {
                if (readInt3 != Atom.TYPE_vp09) {
                    if (!(readInt3 == Atom.TYPE_mp4a || readInt3 == Atom.TYPE_enca || readInt3 == Atom.TYPE_ac_3 || readInt3 == Atom.TYPE_ec_3 || readInt3 == Atom.TYPE_dtsc || readInt3 == Atom.TYPE_dtse || readInt3 == Atom.TYPE_dtsh || readInt3 == Atom.TYPE_dtsl || readInt3 == Atom.TYPE_samr || readInt3 == Atom.TYPE_sawb || readInt3 == Atom.TYPE_lpcm || readInt3 == Atom.TYPE_sowt || readInt3 == Atom.TYPE__mp3 || readInt3 == Atom.TYPE_alac || readInt3 == Atom.TYPE_alaw)) {
                        if (readInt3 != Atom.TYPE_ulaw) {
                            if (!(readInt3 == Atom.TYPE_TTML || readInt3 == Atom.TYPE_tx3g || readInt3 == Atom.TYPE_wvtt || readInt3 == Atom.TYPE_stpp)) {
                                if (readInt3 != Atom.TYPE_c608) {
                                    if (readInt3 == Atom.TYPE_camm) {
                                        stsdData.format = Format.createSampleFormat(Integer.toString(i), MimeTypes.APPLICATION_CAMERA_MOTION, null, -1, null);
                                    }
                                    parsableByteArray2.setPosition(position + readInt2);
                                }
                            }
                            parseTextSampleEntry(parsableByteArray, readInt3, position, readInt2, i, str, stsdData);
                            parsableByteArray2.setPosition(position + readInt2);
                        }
                    }
                    parseAudioSampleEntry(parsableByteArray, readInt3, position, readInt2, i, str, z, drmInitData, stsdData, i3);
                    parsableByteArray2.setPosition(position + readInt2);
                }
            }
            parseVideoSampleEntry(parsableByteArray, readInt3, position, readInt2, i, i2, drmInitData, stsdData, i3);
            parsableByteArray2.setPosition(position + readInt2);
        }
        return stsdData;
    }

    private static void parseTextSampleEntry(ParsableByteArray parsableByteArray, int i, int i2, int i3, int i4, String str, StsdData stsdData) throws ParserException {
        String str2;
        String str3;
        ParsableByteArray parsableByteArray2 = parsableByteArray;
        int i5 = i;
        StsdData stsdData2 = stsdData;
        parsableByteArray2.setPosition((i2 + 8) + 8);
        List list = null;
        long j = Long.MAX_VALUE;
        if (i5 == Atom.TYPE_TTML) {
            str2 = MimeTypes.APPLICATION_TTML;
        } else if (i5 == Atom.TYPE_tx3g) {
            String str4 = MimeTypes.APPLICATION_TX3G;
            int i6 = (i3 - 8) - 8;
            Object obj = new byte[i6];
            parsableByteArray2.readBytes(obj, 0, i6);
            list = Collections.singletonList(obj);
            str3 = str4;
            stsdData2.format = Format.createTextSampleFormat(Integer.toString(i4), str3, null, -1, 0, str, -1, null, j, list);
        } else if (i5 == Atom.TYPE_wvtt) {
            str2 = MimeTypes.APPLICATION_MP4VTT;
        } else if (i5 == Atom.TYPE_stpp) {
            str2 = MimeTypes.APPLICATION_TTML;
            j = 0;
        } else if (i5 == Atom.TYPE_c608) {
            str2 = MimeTypes.APPLICATION_MP4CEA608;
            stsdData2.requiredSampleTransformation = 1;
        } else {
            throw new IllegalStateException();
        }
        str3 = str2;
        stsdData2.format = Format.createTextSampleFormat(Integer.toString(i4), str3, null, -1, 0, str, -1, null, j, list);
    }

    private static void parseVideoSampleEntry(ParsableByteArray parsableByteArray, int i, int i2, int i3, int i4, int i5, DrmInitData drmInitData, StsdData stsdData, int i6) throws ParserException {
        Pair parseSampleEntryEncryptionData;
        ParsableByteArray parsableByteArray2 = parsableByteArray;
        int i7 = i2;
        int i8 = i3;
        DrmInitData drmInitData2 = drmInitData;
        StsdData stsdData2 = stsdData;
        parsableByteArray2.setPosition((i7 + 8) + 8);
        parsableByteArray2.skipBytes(16);
        int readUnsignedShort = parsableByteArray.readUnsignedShort();
        int readUnsignedShort2 = parsableByteArray.readUnsignedShort();
        parsableByteArray2.skipBytes(50);
        int position = parsableByteArray.getPosition();
        String str = null;
        int i9 = i;
        if (i9 == Atom.TYPE_encv) {
            parseSampleEntryEncryptionData = parseSampleEntryEncryptionData(parsableByteArray2, i7, i8);
            if (parseSampleEntryEncryptionData != null) {
                i9 = ((Integer) parseSampleEntryEncryptionData.first).intValue();
                if (drmInitData2 == null) {
                    drmInitData2 = null;
                } else {
                    drmInitData2 = drmInitData2.copyWithSchemeType(((TrackEncryptionBox) parseSampleEntryEncryptionData.second).schemeType);
                }
                stsdData2.trackEncryptionBoxes[i6] = (TrackEncryptionBox) parseSampleEntryEncryptionData.second;
            }
            parsableByteArray2.setPosition(position);
        }
        DrmInitData drmInitData3 = drmInitData2;
        List list = null;
        byte[] bArr = list;
        Object obj = null;
        float f = 1.0f;
        int i10 = -1;
        while (position - i7 < i8) {
            parsableByteArray2.setPosition(position);
            int position2 = parsableByteArray.getPosition();
            int readInt = parsableByteArray.readInt();
            if (readInt != 0 || parsableByteArray.getPosition() - i7 != i8) {
                Assertions.checkArgument(readInt > 0, "childAtomSize should be positive");
                int readInt2 = parsableByteArray.readInt();
                if (readInt2 == Atom.TYPE_avcC) {
                    Assertions.checkState(str == null);
                    str = MimeTypes.VIDEO_H264;
                    parsableByteArray2.setPosition(position2 + 8);
                    AvcConfig parse = AvcConfig.parse(parsableByteArray);
                    list = parse.initializationData;
                    stsdData2.nalUnitLengthFieldLength = parse.nalUnitLengthFieldLength;
                    if (obj == null) {
                        f = parse.pixelWidthAspectRatio;
                    }
                } else if (readInt2 == Atom.TYPE_hvcC) {
                    Assertions.checkState(str == null);
                    str = MimeTypes.VIDEO_H265;
                    parsableByteArray2.setPosition(position2 + 8);
                    HevcConfig parse2 = HevcConfig.parse(parsableByteArray);
                    list = parse2.initializationData;
                    stsdData2.nalUnitLengthFieldLength = parse2.nalUnitLengthFieldLength;
                } else if (readInt2 == Atom.TYPE_vpcC) {
                    Assertions.checkState(str == null);
                    str = i9 == Atom.TYPE_vp08 ? MimeTypes.VIDEO_VP8 : MimeTypes.VIDEO_VP9;
                } else if (readInt2 == Atom.TYPE_d263) {
                    Assertions.checkState(str == null);
                    str = MimeTypes.VIDEO_H263;
                } else if (readInt2 == Atom.TYPE_esds) {
                    Assertions.checkState(str == null);
                    parseSampleEntryEncryptionData = parseEsdsFromParent(parsableByteArray2, position2);
                    str = (String) parseSampleEntryEncryptionData.first;
                    list = Collections.singletonList(parseSampleEntryEncryptionData.second);
                } else if (readInt2 == Atom.TYPE_pasp) {
                    f = parsePaspFromParent(parsableByteArray2, position2);
                    obj = 1;
                } else if (readInt2 == Atom.TYPE_sv3d) {
                    bArr = parseProjFromParent(parsableByteArray2, position2, readInt);
                } else if (readInt2 == Atom.TYPE_st3d) {
                    readInt2 = parsableByteArray.readUnsignedByte();
                    parsableByteArray2.skipBytes(3);
                    if (readInt2 == 0) {
                        switch (parsableByteArray.readUnsignedByte()) {
                            case 0:
                                i10 = 0;
                                break;
                            case 1:
                                i10 = 1;
                                break;
                            case 2:
                                i10 = 2;
                                break;
                            case 3:
                                i10 = 3;
                                break;
                            default:
                                break;
                        }
                    }
                }
                position += readInt;
            } else if (str == null) {
                stsdData2.format = Format.createVideoSampleFormat(Integer.toString(i4), str, null, -1, -1, readUnsignedShort, readUnsignedShort2, -1.0f, list, i5, f, bArr, i10, null, drmInitData3);
            }
        }
        if (str == null) {
            stsdData2.format = Format.createVideoSampleFormat(Integer.toString(i4), str, null, -1, -1, readUnsignedShort, readUnsignedShort2, -1.0f, list, i5, f, bArr, i10, null, drmInitData3);
        }
    }

    private static Pair<long[], long[]> parseEdts(ContainerAtom containerAtom) {
        if (containerAtom != null) {
            containerAtom = containerAtom.getLeafAtomOfType(Atom.TYPE_elst);
            if (containerAtom != null) {
                containerAtom = containerAtom.data;
                containerAtom.setPosition(8);
                int parseFullAtomVersion = Atom.parseFullAtomVersion(containerAtom.readInt());
                int readUnsignedIntToInt = containerAtom.readUnsignedIntToInt();
                Object obj = new long[readUnsignedIntToInt];
                Object obj2 = new long[readUnsignedIntToInt];
                int i = 0;
                while (i < readUnsignedIntToInt) {
                    obj[i] = parseFullAtomVersion == 1 ? containerAtom.readUnsignedLongToLong() : containerAtom.readUnsignedInt();
                    obj2[i] = parseFullAtomVersion == 1 ? containerAtom.readLong() : (long) containerAtom.readInt();
                    if (containerAtom.readShort() == (short) 1) {
                        containerAtom.skipBytes(2);
                        i++;
                    } else {
                        throw new IllegalArgumentException("Unsupported media rate.");
                    }
                }
                return Pair.create(obj, obj2);
            }
        }
        return Pair.create(null, null);
    }

    private static float parsePaspFromParent(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.setPosition(i + 8);
        return ((float) parsableByteArray.readUnsignedIntToInt()) / ((float) parsableByteArray.readUnsignedIntToInt());
    }

    private static void parseAudioSampleEntry(ParsableByteArray parsableByteArray, int i, int i2, int i3, int i4, String str, boolean z, DrmInitData drmInitData, StsdData stsdData, int i5) throws ParserException {
        int readUnsignedShort;
        int round;
        int i6;
        int i7;
        Pair parseSampleEntryEncryptionData;
        DrmInitData drmInitData2;
        String str2;
        int i8;
        int i9;
        Object obj;
        String str3;
        int readInt;
        int readInt2;
        int i10;
        String str4;
        int i11;
        DrmInitData drmInitData3;
        StsdData stsdData2;
        Pair parseEsdsFromParent;
        String str5;
        List list;
        ParsableByteArray parsableByteArray2 = parsableByteArray;
        int i12 = i2;
        int i13 = i3;
        String str6 = str;
        DrmInitData drmInitData4 = drmInitData;
        StsdData stsdData3 = stsdData;
        parsableByteArray2.setPosition((i12 + 8) + 8);
        if (z) {
            readUnsignedShort = parsableByteArray.readUnsignedShort();
            parsableByteArray2.skipBytes(6);
        } else {
            parsableByteArray2.skipBytes(8);
            readUnsignedShort = 0;
        }
        if (readUnsignedShort != 0) {
            if (readUnsignedShort != 1) {
                if (readUnsignedShort == 2) {
                    parsableByteArray2.skipBytes(16);
                    round = (int) Math.round(parsableByteArray.readDouble());
                    readUnsignedShort = parsableByteArray.readUnsignedIntToInt();
                    parsableByteArray2.skipBytes(20);
                    i6 = readUnsignedShort;
                    readUnsignedShort = parsableByteArray.getPosition();
                    i7 = i;
                    if (i7 == Atom.TYPE_enca) {
                        parseSampleEntryEncryptionData = parseSampleEntryEncryptionData(parsableByteArray2, i12, i13);
                        if (parseSampleEntryEncryptionData != null) {
                            i7 = ((Integer) parseSampleEntryEncryptionData.first).intValue();
                            if (drmInitData4 != null) {
                                drmInitData4 = null;
                            } else {
                                drmInitData4 = drmInitData4.copyWithSchemeType(((TrackEncryptionBox) parseSampleEntryEncryptionData.second).schemeType);
                            }
                            stsdData3.trackEncryptionBoxes[i5] = (TrackEncryptionBox) parseSampleEntryEncryptionData.second;
                        }
                        parsableByteArray2.setPosition(readUnsignedShort);
                    }
                    drmInitData2 = drmInitData4;
                    if (i7 == Atom.TYPE_ac_3) {
                        str2 = MimeTypes.AUDIO_AC3;
                    } else if (i7 == Atom.TYPE_ec_3) {
                        str2 = MimeTypes.AUDIO_E_AC3;
                    } else if (i7 != Atom.TYPE_dtsc) {
                        str2 = MimeTypes.AUDIO_DTS;
                    } else {
                        if (i7 != Atom.TYPE_dtsh) {
                            if (i7 == Atom.TYPE_dtsl) {
                                if (i7 == Atom.TYPE_dtse) {
                                    str2 = MimeTypes.AUDIO_DTS_EXPRESS;
                                } else if (i7 == Atom.TYPE_samr) {
                                    str2 = MimeTypes.AUDIO_AMR_NB;
                                } else if (i7 != Atom.TYPE_sawb) {
                                    str2 = MimeTypes.AUDIO_AMR_WB;
                                } else {
                                    if (i7 != Atom.TYPE_lpcm) {
                                        if (i7 == Atom.TYPE_sowt) {
                                            str2 = i7 != Atom.TYPE__mp3 ? MimeTypes.AUDIO_MPEG : i7 != Atom.TYPE_alac ? MimeTypes.AUDIO_ALAC : i7 != Atom.TYPE_alaw ? MimeTypes.AUDIO_ALAW : i7 != Atom.TYPE_ulaw ? MimeTypes.AUDIO_MLAW : null;
                                        }
                                    }
                                    str2 = MimeTypes.AUDIO_RAW;
                                }
                            }
                        }
                        str2 = MimeTypes.AUDIO_DTS_HD;
                    }
                    i8 = round;
                    i7 = readUnsignedShort;
                    i9 = i6;
                    obj = null;
                    str3 = str2;
                    while (i7 - i12 < i13) {
                        parsableByteArray2.setPosition(i7);
                        readInt = parsableByteArray.readInt();
                        Assertions.checkArgument(readInt <= 0, "childAtomSize should be positive");
                        readInt2 = parsableByteArray.readInt();
                        if (readInt2 != Atom.TYPE_esds) {
                            if (z || readInt2 != Atom.TYPE_wave) {
                                if (readInt2 == Atom.TYPE_dac3) {
                                    parsableByteArray2.setPosition(i7 + 8);
                                    stsdData3.format = Ac3Util.parseAc3AnnexFFormat(parsableByteArray2, Integer.toString(i4), str6, drmInitData2);
                                } else if (readInt2 != Atom.TYPE_dec3) {
                                    parsableByteArray2.setPosition(i7 + 8);
                                    stsdData3.format = Ac3Util.parseEAc3AnnexFFormat(parsableByteArray2, Integer.toString(i4), str6, drmInitData2);
                                } else {
                                    if (readInt2 != Atom.TYPE_ddts) {
                                        i10 = readInt;
                                        str4 = str3;
                                        i11 = i7;
                                        drmInitData3 = drmInitData2;
                                        stsdData2 = stsdData3;
                                        stsdData2.format = Format.createAudioSampleFormat(Integer.toString(i4), str3, null, -1, -1, i9, i8, null, drmInitData3, 0, str);
                                    } else {
                                        i10 = readInt;
                                        str4 = str3;
                                        i11 = i7;
                                        drmInitData3 = drmInitData2;
                                        stsdData2 = stsdData3;
                                        if (readInt2 == Atom.TYPE_alac) {
                                            readUnsignedShort = i10;
                                            Object obj2 = new byte[readUnsignedShort];
                                            readInt = i11;
                                            parsableByteArray2.setPosition(readInt);
                                            parsableByteArray2.readBytes(obj2, 0, readUnsignedShort);
                                            obj = obj2;
                                            i7 = readInt + readUnsignedShort;
                                            stsdData3 = stsdData2;
                                            drmInitData2 = drmInitData3;
                                            str3 = str4;
                                            i13 = i3;
                                        }
                                    }
                                    readUnsignedShort = i10;
                                    readInt = i11;
                                    i7 = readInt + readUnsignedShort;
                                    stsdData3 = stsdData2;
                                    drmInitData2 = drmInitData3;
                                    str3 = str4;
                                    i13 = i3;
                                }
                                readUnsignedShort = readInt;
                                str4 = str3;
                                readInt = i7;
                                drmInitData3 = drmInitData2;
                                stsdData2 = stsdData3;
                                i7 = readInt + readUnsignedShort;
                                stsdData3 = stsdData2;
                                drmInitData2 = drmInitData3;
                                str3 = str4;
                                i13 = i3;
                            }
                        }
                        readUnsignedShort = readInt;
                        str4 = str3;
                        readInt = i7;
                        drmInitData3 = drmInitData2;
                        stsdData2 = stsdData3;
                        if (readInt2 != Atom.TYPE_esds) {
                            i7 = readInt;
                        } else {
                            i7 = findEsdsPosition(parsableByteArray2, readInt, readUnsignedShort);
                        }
                        if (i7 == -1) {
                            parseEsdsFromParent = parseEsdsFromParent(parsableByteArray2, i7);
                            str5 = (String) parseEsdsFromParent.first;
                            obj = (byte[]) parseEsdsFromParent.second;
                            if (MimeTypes.AUDIO_AAC.equals(str5)) {
                                parseEsdsFromParent = CodecSpecificDataUtil.parseAacAudioSpecificConfig(obj);
                                i8 = ((Integer) parseEsdsFromParent.first).intValue();
                                i9 = ((Integer) parseEsdsFromParent.second).intValue();
                            }
                        } else {
                            str5 = str4;
                        }
                        str4 = str5;
                        i7 = readInt + readUnsignedShort;
                        stsdData3 = stsdData2;
                        drmInitData2 = drmInitData3;
                        str3 = str4;
                        i13 = i3;
                    }
                    str4 = str3;
                    drmInitData3 = drmInitData2;
                    stsdData2 = stsdData3;
                    if (stsdData2.format == null) {
                        str2 = str4;
                        if (str2 != null) {
                            i6 = MimeTypes.AUDIO_RAW.equals(str2) ? 2 : -1;
                            String num = Integer.toString(i4);
                            if (obj != null) {
                                list = null;
                            } else {
                                list = Collections.singletonList(obj);
                            }
                            stsdData2.format = Format.createAudioSampleFormat(num, str2, null, -1, -1, i9, i8, i6, list, drmInitData3, 0, str);
                        }
                    }
                }
                return;
            }
        }
        i6 = parsableByteArray.readUnsignedShort();
        parsableByteArray2.skipBytes(6);
        round = parsableByteArray.readUnsignedFixedPoint1616();
        if (readUnsignedShort == 1) {
            parsableByteArray2.skipBytes(16);
        }
        readUnsignedShort = parsableByteArray.getPosition();
        i7 = i;
        if (i7 == Atom.TYPE_enca) {
            parseSampleEntryEncryptionData = parseSampleEntryEncryptionData(parsableByteArray2, i12, i13);
            if (parseSampleEntryEncryptionData != null) {
                i7 = ((Integer) parseSampleEntryEncryptionData.first).intValue();
                if (drmInitData4 != null) {
                    drmInitData4 = drmInitData4.copyWithSchemeType(((TrackEncryptionBox) parseSampleEntryEncryptionData.second).schemeType);
                } else {
                    drmInitData4 = null;
                }
                stsdData3.trackEncryptionBoxes[i5] = (TrackEncryptionBox) parseSampleEntryEncryptionData.second;
            }
            parsableByteArray2.setPosition(readUnsignedShort);
        }
        drmInitData2 = drmInitData4;
        if (i7 == Atom.TYPE_ac_3) {
            str2 = MimeTypes.AUDIO_AC3;
        } else if (i7 == Atom.TYPE_ec_3) {
            str2 = MimeTypes.AUDIO_E_AC3;
        } else if (i7 != Atom.TYPE_dtsc) {
            if (i7 != Atom.TYPE_dtsh) {
                if (i7 == Atom.TYPE_dtsl) {
                    if (i7 == Atom.TYPE_dtse) {
                        str2 = MimeTypes.AUDIO_DTS_EXPRESS;
                    } else if (i7 == Atom.TYPE_samr) {
                        str2 = MimeTypes.AUDIO_AMR_NB;
                    } else if (i7 != Atom.TYPE_sawb) {
                        if (i7 != Atom.TYPE_lpcm) {
                            if (i7 == Atom.TYPE_sowt) {
                                if (i7 != Atom.TYPE__mp3) {
                                    if (i7 != Atom.TYPE_alac) {
                                        if (i7 != Atom.TYPE_alaw) {
                                            if (i7 != Atom.TYPE_ulaw) {
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        str2 = MimeTypes.AUDIO_RAW;
                    } else {
                        str2 = MimeTypes.AUDIO_AMR_WB;
                    }
                }
            }
            str2 = MimeTypes.AUDIO_DTS_HD;
        } else {
            str2 = MimeTypes.AUDIO_DTS;
        }
        i8 = round;
        i7 = readUnsignedShort;
        i9 = i6;
        obj = null;
        str3 = str2;
        while (i7 - i12 < i13) {
            parsableByteArray2.setPosition(i7);
            readInt = parsableByteArray.readInt();
            if (readInt <= 0) {
            }
            Assertions.checkArgument(readInt <= 0, "childAtomSize should be positive");
            readInt2 = parsableByteArray.readInt();
            if (readInt2 != Atom.TYPE_esds) {
                if (z) {
                }
                if (readInt2 == Atom.TYPE_dac3) {
                    parsableByteArray2.setPosition(i7 + 8);
                    stsdData3.format = Ac3Util.parseAc3AnnexFFormat(parsableByteArray2, Integer.toString(i4), str6, drmInitData2);
                } else if (readInt2 != Atom.TYPE_dec3) {
                    if (readInt2 != Atom.TYPE_ddts) {
                        i10 = readInt;
                        str4 = str3;
                        i11 = i7;
                        drmInitData3 = drmInitData2;
                        stsdData2 = stsdData3;
                        if (readInt2 == Atom.TYPE_alac) {
                            readUnsignedShort = i10;
                            Object obj22 = new byte[readUnsignedShort];
                            readInt = i11;
                            parsableByteArray2.setPosition(readInt);
                            parsableByteArray2.readBytes(obj22, 0, readUnsignedShort);
                            obj = obj22;
                            i7 = readInt + readUnsignedShort;
                            stsdData3 = stsdData2;
                            drmInitData2 = drmInitData3;
                            str3 = str4;
                            i13 = i3;
                        }
                    } else {
                        i10 = readInt;
                        str4 = str3;
                        i11 = i7;
                        drmInitData3 = drmInitData2;
                        stsdData2 = stsdData3;
                        stsdData2.format = Format.createAudioSampleFormat(Integer.toString(i4), str3, null, -1, -1, i9, i8, null, drmInitData3, 0, str);
                    }
                    readUnsignedShort = i10;
                    readInt = i11;
                    i7 = readInt + readUnsignedShort;
                    stsdData3 = stsdData2;
                    drmInitData2 = drmInitData3;
                    str3 = str4;
                    i13 = i3;
                } else {
                    parsableByteArray2.setPosition(i7 + 8);
                    stsdData3.format = Ac3Util.parseEAc3AnnexFFormat(parsableByteArray2, Integer.toString(i4), str6, drmInitData2);
                }
                readUnsignedShort = readInt;
                str4 = str3;
                readInt = i7;
                drmInitData3 = drmInitData2;
                stsdData2 = stsdData3;
                i7 = readInt + readUnsignedShort;
                stsdData3 = stsdData2;
                drmInitData2 = drmInitData3;
                str3 = str4;
                i13 = i3;
            }
            readUnsignedShort = readInt;
            str4 = str3;
            readInt = i7;
            drmInitData3 = drmInitData2;
            stsdData2 = stsdData3;
            if (readInt2 != Atom.TYPE_esds) {
                i7 = findEsdsPosition(parsableByteArray2, readInt, readUnsignedShort);
            } else {
                i7 = readInt;
            }
            if (i7 == -1) {
                str5 = str4;
            } else {
                parseEsdsFromParent = parseEsdsFromParent(parsableByteArray2, i7);
                str5 = (String) parseEsdsFromParent.first;
                obj = (byte[]) parseEsdsFromParent.second;
                if (MimeTypes.AUDIO_AAC.equals(str5)) {
                    parseEsdsFromParent = CodecSpecificDataUtil.parseAacAudioSpecificConfig(obj);
                    i8 = ((Integer) parseEsdsFromParent.first).intValue();
                    i9 = ((Integer) parseEsdsFromParent.second).intValue();
                }
            }
            str4 = str5;
            i7 = readInt + readUnsignedShort;
            stsdData3 = stsdData2;
            drmInitData2 = drmInitData3;
            str3 = str4;
            i13 = i3;
        }
        str4 = str3;
        drmInitData3 = drmInitData2;
        stsdData2 = stsdData3;
        if (stsdData2.format == null) {
            str2 = str4;
            if (str2 != null) {
                if (MimeTypes.AUDIO_RAW.equals(str2)) {
                }
                String num2 = Integer.toString(i4);
                if (obj != null) {
                    list = Collections.singletonList(obj);
                } else {
                    list = null;
                }
                stsdData2.format = Format.createAudioSampleFormat(num2, str2, null, -1, -1, i9, i8, i6, list, drmInitData3, 0, str);
            }
        }
    }

    private static int findEsdsPosition(ParsableByteArray parsableByteArray, int i, int i2) {
        int position = parsableByteArray.getPosition();
        while (position - i < i2) {
            parsableByteArray.setPosition(position);
            int readInt = parsableByteArray.readInt();
            Assertions.checkArgument(readInt > 0, "childAtomSize should be positive");
            if (parsableByteArray.readInt() == Atom.TYPE_esds) {
                return position;
            }
            position += readInt;
        }
        return -1;
    }

    private static Pair<String, byte[]> parseEsdsFromParent(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.setPosition((i + 8) + 4);
        parsableByteArray.skipBytes(1);
        parseExpandableClassSize(parsableByteArray);
        parsableByteArray.skipBytes(2);
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        if ((readUnsignedByte & 128) != 0) {
            parsableByteArray.skipBytes(2);
        }
        if ((readUnsignedByte & 64) != 0) {
            parsableByteArray.skipBytes(parsableByteArray.readUnsignedShort());
        }
        if ((readUnsignedByte & 32) != 0) {
            parsableByteArray.skipBytes(2);
        }
        parsableByteArray.skipBytes(1);
        parseExpandableClassSize(parsableByteArray);
        String mimeTypeFromMp4ObjectType = MimeTypes.getMimeTypeFromMp4ObjectType(parsableByteArray.readUnsignedByte());
        if (!(MimeTypes.AUDIO_MPEG.equals(mimeTypeFromMp4ObjectType) || MimeTypes.AUDIO_DTS.equals(mimeTypeFromMp4ObjectType))) {
            if (!MimeTypes.AUDIO_DTS_HD.equals(mimeTypeFromMp4ObjectType)) {
                parsableByteArray.skipBytes(12);
                parsableByteArray.skipBytes(1);
                i = parseExpandableClassSize(parsableByteArray);
                Object obj = new byte[i];
                parsableByteArray.readBytes(obj, 0, i);
                return Pair.create(mimeTypeFromMp4ObjectType, obj);
            }
        }
        return Pair.create(mimeTypeFromMp4ObjectType, null);
    }

    private static Pair<Integer, TrackEncryptionBox> parseSampleEntryEncryptionData(ParsableByteArray parsableByteArray, int i, int i2) {
        int position = parsableByteArray.getPosition();
        while (position - i < i2) {
            parsableByteArray.setPosition(position);
            int readInt = parsableByteArray.readInt();
            Assertions.checkArgument(readInt > 0, "childAtomSize should be positive");
            if (parsableByteArray.readInt() == Atom.TYPE_sinf) {
                Pair<Integer, TrackEncryptionBox> parseCommonEncryptionSinfFromParent = parseCommonEncryptionSinfFromParent(parsableByteArray, position, readInt);
                if (parseCommonEncryptionSinfFromParent != null) {
                    return parseCommonEncryptionSinfFromParent;
                }
            }
            position += readInt;
        }
        return null;
    }

    static Pair<Integer, TrackEncryptionBox> parseCommonEncryptionSinfFromParent(ParsableByteArray parsableByteArray, int i, int i2) {
        int i3 = i + 8;
        String str = null;
        Object obj = str;
        int i4 = -1;
        int i5 = 0;
        while (i3 - i < i2) {
            parsableByteArray.setPosition(i3);
            int readInt = parsableByteArray.readInt();
            int readInt2 = parsableByteArray.readInt();
            if (readInt2 == Atom.TYPE_frma) {
                obj = Integer.valueOf(parsableByteArray.readInt());
            } else if (readInt2 == Atom.TYPE_schm) {
                parsableByteArray.skipBytes(4);
                str = parsableByteArray.readString(4);
            } else if (readInt2 == Atom.TYPE_schi) {
                i4 = i3;
                i5 = readInt;
            }
            i3 += readInt;
        }
        if (C.CENC_TYPE_cenc.equals(str) == 0 && C.CENC_TYPE_cbc1.equals(str) == 0 && C.CENC_TYPE_cens.equals(str) == 0) {
            if (C.CENC_TYPE_cbcs.equals(str) == 0) {
                return null;
            }
        }
        i = 1;
        Assertions.checkArgument(obj != null ? 1 : 0, "frma atom is mandatory");
        Assertions.checkArgument(i4 != -1 ? 1 : 0, "schi atom is mandatory");
        parsableByteArray = parseSchiFromParent(parsableByteArray, i4, i5, str);
        if (parsableByteArray == null) {
            i = 0;
        }
        Assertions.checkArgument(i, "tenc atom is mandatory");
        return Pair.create(obj, parsableByteArray);
    }

    private static TrackEncryptionBox parseSchiFromParent(ParsableByteArray parsableByteArray, int i, int i2, String str) {
        TrackEncryptionBox trackEncryptionBox;
        int i3;
        int i4;
        int i5 = i + 8;
        while (true) {
            trackEncryptionBox = null;
            if (i5 - i >= i2) {
                return null;
            }
            parsableByteArray.setPosition(i5);
            int readInt = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == Atom.TYPE_tenc) {
                break;
            }
            i5 += readInt;
        }
        i = Atom.parseFullAtomVersion(parsableByteArray.readInt());
        parsableByteArray.skipBytes(1);
        if (i == 0) {
            parsableByteArray.skipBytes(1);
            i3 = 0;
            i4 = 0;
        } else {
            i = parsableByteArray.readUnsignedByte();
            i4 = i & 15;
            i3 = (i & PsExtractor.VIDEO_STREAM_MASK) >> 4;
        }
        boolean z = parsableByteArray.readUnsignedByte() == 1;
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        byte[] bArr = new byte[16];
        parsableByteArray.readBytes(bArr, 0, bArr.length);
        if (z && readUnsignedByte == 0) {
            i = parsableByteArray.readUnsignedByte();
            trackEncryptionBox = new byte[i];
            parsableByteArray.readBytes(trackEncryptionBox, 0, i);
        }
        return new TrackEncryptionBox(z, str, readUnsignedByte, bArr, i3, i4, trackEncryptionBox);
    }

    private static byte[] parseProjFromParent(ParsableByteArray parsableByteArray, int i, int i2) {
        int i3 = i + 8;
        while (i3 - i < i2) {
            parsableByteArray.setPosition(i3);
            int readInt = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == Atom.TYPE_proj) {
                return Arrays.copyOfRange(parsableByteArray.data, i3, readInt + i3);
            }
            i3 += readInt;
        }
        return null;
    }

    private static int parseExpandableClassSize(ParsableByteArray parsableByteArray) {
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        int i = readUnsignedByte & 127;
        while ((readUnsignedByte & 128) == 128) {
            readUnsignedByte = parsableByteArray.readUnsignedByte();
            i = (i << 7) | (readUnsignedByte & 127);
        }
        return i;
    }

    private static boolean canApplyEditWithGaplessInfo(long[] jArr, long j, long j2, long j3) {
        int length = jArr.length - 1;
        int constrainValue = Util.constrainValue(3, 0, length);
        length = Util.constrainValue(jArr.length - 3, 0, length);
        if (jArr[0] > j2 || j2 >= jArr[constrainValue] || jArr[length] >= j3 || j3 > j) {
            return false;
        }
        return true;
    }

    private AtomParsers() {
    }
}
