package com.google.android.exoplayer2.extractor.mp4;

import android.util.Pair;
import android.util.SparseArray;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmInitData.SchemeData;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekMap.Unseekable;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.TrackOutput.CryptoData;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.exoplayer2.text.cea.CeaUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public final class FragmentedMp4Extractor implements Extractor {
    private static final Format EMSG_FORMAT = Format.createSampleFormat(null, MimeTypes.APPLICATION_EMSG, Long.MAX_VALUE);
    public static final ExtractorsFactory FACTORY = -$$Lambda$FragmentedMp4Extractor$i0zfpH_PcF0vytkdatCL0xeWFhQ.INSTANCE;
    public static final int FLAG_ENABLE_EMSG_TRACK = 4;
    private static final int FLAG_SIDELOADED = 8;
    public static final int FLAG_WORKAROUND_EVERY_VIDEO_FRAME_IS_SYNC_FRAME = 1;
    public static final int FLAG_WORKAROUND_IGNORE_EDIT_LISTS = 16;
    public static final int FLAG_WORKAROUND_IGNORE_TFDT_BOX = 2;
    private static final byte[] PIFF_SAMPLE_ENCRYPTION_BOX_EXTENDED_TYPE = new byte[]{(byte) -94, (byte) 57, (byte) 79, (byte) 82, (byte) 90, (byte) -101, (byte) 79, (byte) 20, (byte) -94, (byte) 68, (byte) 108, (byte) 66, (byte) 124, (byte) 100, (byte) -115, (byte) -12};
    private static final int SAMPLE_GROUP_TYPE_seig = Util.getIntegerCodeForString("seig");
    private static final int STATE_READING_ATOM_HEADER = 0;
    private static final int STATE_READING_ATOM_PAYLOAD = 1;
    private static final int STATE_READING_ENCRYPTION_DATA = 2;
    private static final int STATE_READING_SAMPLE_CONTINUE = 4;
    private static final int STATE_READING_SAMPLE_START = 3;
    private static final String TAG = "FragmentedMp4Extractor";
    @Nullable
    private final TrackOutput additionalEmsgTrackOutput;
    private ParsableByteArray atomData;
    private final ParsableByteArray atomHeader;
    private int atomHeaderBytesRead;
    private long atomSize;
    private int atomType;
    private TrackOutput[] cea608TrackOutputs;
    private final List<Format> closedCaptionFormats;
    private final ArrayDeque<ContainerAtom> containerAtoms;
    private TrackBundle currentTrackBundle;
    private long durationUs;
    private TrackOutput[] emsgTrackOutputs;
    private long endOfMdatPosition;
    private final byte[] extendedTypeScratch;
    private ExtractorOutput extractorOutput;
    private final int flags;
    private boolean haveOutputSeekMap;
    private final ParsableByteArray nalBuffer;
    private final ParsableByteArray nalPrefix;
    private final ParsableByteArray nalStartCode;
    private int parserState;
    private int pendingMetadataSampleBytes;
    private final ArrayDeque<MetadataSampleInfo> pendingMetadataSampleInfos;
    private long pendingSeekTimeUs;
    private boolean processSeiNalUnitPayload;
    private int sampleBytesWritten;
    private int sampleCurrentNalBytesRemaining;
    private int sampleSize;
    private long segmentIndexEarliestPresentationTimeUs;
    @Nullable
    private final DrmInitData sideloadedDrmInitData;
    @Nullable
    private final Track sideloadedTrack;
    @Nullable
    private final TimestampAdjuster timestampAdjuster;
    private final SparseArray<TrackBundle> trackBundles;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    private static final class MetadataSampleInfo {
        public final long presentationTimeDeltaUs;
        public final int size;

        public MetadataSampleInfo(long j, int i) {
            this.presentationTimeDeltaUs = j;
            this.size = i;
        }
    }

    private static final class TrackBundle {
        public int currentSampleInTrackRun;
        public int currentSampleIndex;
        public int currentTrackRunIndex;
        private final ParsableByteArray defaultInitializationVector = new ParsableByteArray();
        public DefaultSampleValues defaultSampleValues;
        private final ParsableByteArray encryptionSignalByte = new ParsableByteArray(1);
        public int firstSampleToOutputIndex;
        public final TrackFragment fragment = new TrackFragment();
        public final TrackOutput output;
        public Track track;

        public TrackBundle(TrackOutput trackOutput) {
            this.output = trackOutput;
        }

        public void init(Track track, DefaultSampleValues defaultSampleValues) {
            this.track = (Track) Assertions.checkNotNull(track);
            this.defaultSampleValues = (DefaultSampleValues) Assertions.checkNotNull(defaultSampleValues);
            this.output.format(track.format);
            reset();
        }

        public void updateDrmInitData(DrmInitData drmInitData) {
            TrackEncryptionBox sampleDescriptionEncryptionBox = this.track.getSampleDescriptionEncryptionBox(this.fragment.header.sampleDescriptionIndex);
            this.output.format(this.track.format.copyWithDrmInitData(drmInitData.copyWithSchemeType(sampleDescriptionEncryptionBox != null ? sampleDescriptionEncryptionBox.schemeType : null)));
        }

        public void reset() {
            this.fragment.reset();
            this.currentSampleIndex = 0;
            this.currentTrackRunIndex = 0;
            this.currentSampleInTrackRun = 0;
            this.firstSampleToOutputIndex = 0;
        }

        public void seek(long j) {
            j = C.usToMs(j);
            int i = this.currentSampleIndex;
            while (i < this.fragment.sampleCount && this.fragment.getSamplePresentationTime(i) < j) {
                if (this.fragment.sampleIsSyncFrameTable[i]) {
                    this.firstSampleToOutputIndex = i;
                }
                i++;
            }
        }

        public boolean next() {
            this.currentSampleIndex++;
            this.currentSampleInTrackRun++;
            if (this.currentSampleInTrackRun != this.fragment.trunLength[this.currentTrackRunIndex]) {
                return true;
            }
            this.currentTrackRunIndex++;
            this.currentSampleInTrackRun = 0;
            return false;
        }

        public int outputSampleEncryptionData() {
            TrackEncryptionBox encryptionBoxIfEncrypted = getEncryptionBoxIfEncrypted();
            if (encryptionBoxIfEncrypted == null) {
                return 0;
            }
            ParsableByteArray parsableByteArray;
            int i;
            if (encryptionBoxIfEncrypted.perSampleIvSize != 0) {
                parsableByteArray = this.fragment.sampleEncryptionData;
                i = encryptionBoxIfEncrypted.perSampleIvSize;
            } else {
                byte[] bArr = encryptionBoxIfEncrypted.defaultInitializationVector;
                this.defaultInitializationVector.reset(bArr, bArr.length);
                parsableByteArray = this.defaultInitializationVector;
                i = bArr.length;
            }
            boolean sampleHasSubsampleEncryptionTable = this.fragment.sampleHasSubsampleEncryptionTable(this.currentSampleIndex);
            this.encryptionSignalByte.data[0] = (byte) ((sampleHasSubsampleEncryptionTable ? 128 : 0) | i);
            this.encryptionSignalByte.setPosition(0);
            this.output.sampleData(this.encryptionSignalByte, 1);
            this.output.sampleData(parsableByteArray, i);
            if (!sampleHasSubsampleEncryptionTable) {
                return i + 1;
            }
            ParsableByteArray parsableByteArray2 = this.fragment.sampleEncryptionData;
            int readUnsignedShort = parsableByteArray2.readUnsignedShort();
            parsableByteArray2.skipBytes(-2);
            readUnsignedShort = (readUnsignedShort * 6) + 2;
            this.output.sampleData(parsableByteArray2, readUnsignedShort);
            return (i + 1) + readUnsignedShort;
        }

        private void skipSampleEncryptionData() {
            TrackEncryptionBox encryptionBoxIfEncrypted = getEncryptionBoxIfEncrypted();
            if (encryptionBoxIfEncrypted != null) {
                ParsableByteArray parsableByteArray = this.fragment.sampleEncryptionData;
                if (encryptionBoxIfEncrypted.perSampleIvSize != 0) {
                    parsableByteArray.skipBytes(encryptionBoxIfEncrypted.perSampleIvSize);
                }
                if (this.fragment.sampleHasSubsampleEncryptionTable(this.currentSampleIndex)) {
                    parsableByteArray.skipBytes(parsableByteArray.readUnsignedShort() * 6);
                }
            }
        }

        private TrackEncryptionBox getEncryptionBoxIfEncrypted() {
            TrackEncryptionBox trackEncryptionBox;
            int i = this.fragment.header.sampleDescriptionIndex;
            if (this.fragment.trackEncryptionBox != null) {
                trackEncryptionBox = this.fragment.trackEncryptionBox;
            } else {
                trackEncryptionBox = this.track.getSampleDescriptionEncryptionBox(i);
            }
            return (trackEncryptionBox == null || !trackEncryptionBox.isEncrypted) ? null : trackEncryptionBox;
        }
    }

    public void release() {
    }

    public FragmentedMp4Extractor() {
        this(0);
    }

    public FragmentedMp4Extractor(int i) {
        this(i, null);
    }

    public FragmentedMp4Extractor(int i, @Nullable TimestampAdjuster timestampAdjuster) {
        this(i, timestampAdjuster, null, null);
    }

    public FragmentedMp4Extractor(int i, @Nullable TimestampAdjuster timestampAdjuster, @Nullable Track track, @Nullable DrmInitData drmInitData) {
        this(i, timestampAdjuster, track, drmInitData, Collections.emptyList());
    }

    public FragmentedMp4Extractor(int i, @Nullable TimestampAdjuster timestampAdjuster, @Nullable Track track, @Nullable DrmInitData drmInitData, List<Format> list) {
        this(i, timestampAdjuster, track, drmInitData, list, null);
    }

    public FragmentedMp4Extractor(int i, @Nullable TimestampAdjuster timestampAdjuster, @Nullable Track track, @Nullable DrmInitData drmInitData, List<Format> list, @Nullable TrackOutput trackOutput) {
        this.flags = i | (track != null ? 8 : 0);
        this.timestampAdjuster = timestampAdjuster;
        this.sideloadedTrack = track;
        this.sideloadedDrmInitData = drmInitData;
        this.closedCaptionFormats = Collections.unmodifiableList(list);
        this.additionalEmsgTrackOutput = trackOutput;
        this.atomHeader = new ParsableByteArray(16);
        this.nalStartCode = new ParsableByteArray(NalUnitUtil.NAL_START_CODE);
        this.nalPrefix = new ParsableByteArray(5);
        this.nalBuffer = new ParsableByteArray();
        this.extendedTypeScratch = new byte[16];
        this.containerAtoms = new ArrayDeque();
        this.pendingMetadataSampleInfos = new ArrayDeque();
        this.trackBundles = new SparseArray();
        this.durationUs = C.TIME_UNSET;
        this.pendingSeekTimeUs = C.TIME_UNSET;
        this.segmentIndexEarliestPresentationTimeUs = C.TIME_UNSET;
        enterReadingAtomHeaderState();
    }

    public boolean sniff(ExtractorInput extractorInput) throws IOException, InterruptedException {
        return Sniffer.sniffFragmented(extractorInput);
    }

    public void init(ExtractorOutput extractorOutput) {
        this.extractorOutput = extractorOutput;
        if (this.sideloadedTrack != null) {
            TrackBundle trackBundle = new TrackBundle(extractorOutput.track(0, this.sideloadedTrack.type));
            trackBundle.init(this.sideloadedTrack, new DefaultSampleValues(0, 0, 0, 0));
            this.trackBundles.put(0, trackBundle);
            maybeInitExtraTracks();
            this.extractorOutput.endTracks();
        }
    }

    public void seek(long j, long j2) {
        j = this.trackBundles.size();
        for (int i = 0; i < j; i++) {
            ((TrackBundle) this.trackBundles.valueAt(i)).reset();
        }
        this.pendingMetadataSampleInfos.clear();
        this.pendingMetadataSampleBytes = 0;
        this.pendingSeekTimeUs = j2;
        this.containerAtoms.clear();
        enterReadingAtomHeaderState();
    }

    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException, InterruptedException {
        while (true) {
            switch (this.parserState) {
                case null:
                    if (readAtomHeader(extractorInput) != null) {
                        break;
                    }
                    return -1;
                case 1:
                    readAtomPayload(extractorInput);
                    break;
                case 2:
                    readEncryptionData(extractorInput);
                    break;
                default:
                    if (readSample(extractorInput) == null) {
                        break;
                    }
                    return null;
            }
        }
    }

    private void enterReadingAtomHeaderState() {
        this.parserState = 0;
        this.atomHeaderBytesRead = 0;
    }

    private boolean readAtomHeader(ExtractorInput extractorInput) throws IOException, InterruptedException {
        long length;
        if (this.atomHeaderBytesRead == 0) {
            if (!extractorInput.readFully(this.atomHeader.data, 0, 8, true)) {
                return false;
            }
            this.atomHeaderBytesRead = 8;
            this.atomHeader.setPosition(0);
            this.atomSize = this.atomHeader.readUnsignedInt();
            this.atomType = this.atomHeader.readInt();
        }
        if (this.atomSize == 1) {
            extractorInput.readFully(this.atomHeader.data, 8, 8);
            this.atomHeaderBytesRead += 8;
            this.atomSize = this.atomHeader.readUnsignedLongToLong();
        } else if (this.atomSize == 0) {
            length = extractorInput.getLength();
            if (length == -1 && !this.containerAtoms.isEmpty()) {
                length = ((ContainerAtom) this.containerAtoms.peek()).endPosition;
            }
            if (length != -1) {
                this.atomSize = (length - extractorInput.getPosition()) + ((long) this.atomHeaderBytesRead);
            }
        }
        if (this.atomSize >= ((long) this.atomHeaderBytesRead)) {
            length = extractorInput.getPosition() - ((long) this.atomHeaderBytesRead);
            if (this.atomType == Atom.TYPE_moof) {
                int size = this.trackBundles.size();
                for (int i = 0; i < size; i++) {
                    TrackFragment trackFragment = ((TrackBundle) this.trackBundles.valueAt(i)).fragment;
                    trackFragment.atomPosition = length;
                    trackFragment.auxiliaryDataPosition = length;
                    trackFragment.dataPosition = length;
                }
            }
            if (this.atomType == Atom.TYPE_mdat) {
                this.currentTrackBundle = null;
                this.endOfMdatPosition = this.atomSize + length;
                if (this.haveOutputSeekMap == null) {
                    this.extractorOutput.seekMap(new Unseekable(this.durationUs, length));
                    this.haveOutputSeekMap = true;
                }
                this.parserState = 2;
                return true;
            }
            if (shouldParseContainerAtom(this.atomType)) {
                long position = (extractorInput.getPosition() + this.atomSize) - 8;
                this.containerAtoms.push(new ContainerAtom(this.atomType, position));
                if (this.atomSize == ((long) this.atomHeaderBytesRead)) {
                    processAtomEnded(position);
                } else {
                    enterReadingAtomHeaderState();
                }
            } else if (shouldParseLeafAtom(this.atomType) != null) {
                if (this.atomHeaderBytesRead != 8) {
                    throw new ParserException("Leaf atom defines extended atom size (unsupported).");
                } else if (this.atomSize <= 2147483647L) {
                    this.atomData = new ParsableByteArray((int) this.atomSize);
                    System.arraycopy(this.atomHeader.data, 0, this.atomData.data, 0, 8);
                    this.parserState = 1;
                } else {
                    throw new ParserException("Leaf atom with length > 2147483647 (unsupported).");
                }
            } else if (this.atomSize <= 2147483647L) {
                this.atomData = null;
                this.parserState = 1;
            } else {
                throw new ParserException("Skipping atom with length > 2147483647 (unsupported).");
            }
            return true;
        }
        throw new ParserException("Atom size less than header length (unsupported).");
    }

    private void readAtomPayload(ExtractorInput extractorInput) throws IOException, InterruptedException {
        int i = ((int) this.atomSize) - this.atomHeaderBytesRead;
        if (this.atomData != null) {
            extractorInput.readFully(this.atomData.data, 8, i);
            onLeafAtomRead(new LeafAtom(this.atomType, this.atomData), extractorInput.getPosition());
        } else {
            extractorInput.skipFully(i);
        }
        processAtomEnded(extractorInput.getPosition());
    }

    private void processAtomEnded(long j) throws ParserException {
        while (!this.containerAtoms.isEmpty() && ((ContainerAtom) this.containerAtoms.peek()).endPosition == j) {
            onContainerAtomRead((ContainerAtom) this.containerAtoms.pop());
        }
        enterReadingAtomHeaderState();
    }

    private void onLeafAtomRead(LeafAtom leafAtom, long j) throws ParserException {
        if (!this.containerAtoms.isEmpty()) {
            ((ContainerAtom) this.containerAtoms.peek()).add(leafAtom);
        } else if (leafAtom.type == Atom.TYPE_sidx) {
            leafAtom = parseSidx(leafAtom.data, j);
            this.segmentIndexEarliestPresentationTimeUs = ((Long) leafAtom.first).longValue();
            this.extractorOutput.seekMap((SeekMap) leafAtom.second);
            this.haveOutputSeekMap = true;
        } else if (leafAtom.type == Atom.TYPE_emsg) {
            onEmsgLeafAtomRead(leafAtom.data);
        }
    }

    private void onContainerAtomRead(ContainerAtom containerAtom) throws ParserException {
        if (containerAtom.type == Atom.TYPE_moov) {
            onMoovContainerAtomRead(containerAtom);
        } else if (containerAtom.type == Atom.TYPE_moof) {
            onMoofContainerAtomRead(containerAtom);
        } else if (!this.containerAtoms.isEmpty()) {
            ((ContainerAtom) this.containerAtoms.peek()).add(containerAtom);
        }
    }

    private void onMoovContainerAtomRead(ContainerAtom containerAtom) throws ParserException {
        DrmInitData drmInitData;
        ContainerAtom containerAtom2 = containerAtom;
        int i = 0;
        boolean z = true;
        Assertions.checkState(this.sideloadedTrack == null, "Unexpected moov box.");
        if (r0.sideloadedDrmInitData != null) {
            drmInitData = r0.sideloadedDrmInitData;
        } else {
            drmInitData = getDrmInitDataFromAtoms(containerAtom2.leafChildren);
        }
        ContainerAtom containerAtomOfType = containerAtom2.getContainerAtomOfType(Atom.TYPE_mvex);
        SparseArray sparseArray = new SparseArray();
        int size = containerAtomOfType.leafChildren.size();
        long j = C.TIME_UNSET;
        for (int i2 = 0; i2 < size; i2++) {
            LeafAtom leafAtom = (LeafAtom) containerAtomOfType.leafChildren.get(i2);
            if (leafAtom.type == Atom.TYPE_trex) {
                Pair parseTrex = parseTrex(leafAtom.data);
                sparseArray.put(((Integer) parseTrex.first).intValue(), parseTrex.second);
            } else if (leafAtom.type == Atom.TYPE_mehd) {
                j = parseMehd(leafAtom.data);
            }
        }
        SparseArray sparseArray2 = new SparseArray();
        int size2 = containerAtom2.containerChildren.size();
        int i3 = 0;
        while (i3 < size2) {
            int i4;
            int i5;
            containerAtomOfType = (ContainerAtom) containerAtom2.containerChildren.get(i3);
            if (containerAtomOfType.type == Atom.TYPE_trak) {
                i4 = i3;
                i5 = size2;
                Track parseTrak = AtomParsers.parseTrak(containerAtomOfType, containerAtom2.getLeafAtomOfType(Atom.TYPE_mvhd), j, drmInitData, (r0.flags & 16) != 0, false);
                if (parseTrak != null) {
                    sparseArray2.put(parseTrak.id, parseTrak);
                }
            } else {
                i4 = i3;
                i5 = size2;
            }
            i3 = i4 + 1;
            size2 = i5;
        }
        int size3 = sparseArray2.size();
        if (r0.trackBundles.size() == 0) {
            while (i < size3) {
                Track track = (Track) sparseArray2.valueAt(i);
                TrackBundle trackBundle = new TrackBundle(r0.extractorOutput.track(i, track.type));
                trackBundle.init(track, getDefaultSampleValues(sparseArray, track.id));
                r0.trackBundles.put(track.id, trackBundle);
                r0.durationUs = Math.max(r0.durationUs, track.durationUs);
                i++;
            }
            maybeInitExtraTracks();
            r0.extractorOutput.endTracks();
            return;
        }
        if (r0.trackBundles.size() != size3) {
            z = false;
        }
        Assertions.checkState(z);
        while (i < size3) {
            track = (Track) sparseArray2.valueAt(i);
            ((TrackBundle) r0.trackBundles.get(track.id)).init(track, getDefaultSampleValues(sparseArray, track.id));
            i++;
        }
    }

    private DefaultSampleValues getDefaultSampleValues(SparseArray<DefaultSampleValues> sparseArray, int i) {
        if (sparseArray.size() == 1) {
            return (DefaultSampleValues) sparseArray.valueAt(0);
        }
        return (DefaultSampleValues) Assertions.checkNotNull(sparseArray.get(i));
    }

    private void onMoofContainerAtomRead(ContainerAtom containerAtom) throws ParserException {
        parseMoof(containerAtom, this.trackBundles, this.flags, this.extendedTypeScratch);
        if (this.sideloadedDrmInitData != null) {
            containerAtom = null;
        } else {
            containerAtom = getDrmInitDataFromAtoms(containerAtom.leafChildren);
        }
        if (containerAtom != null) {
            int size = this.trackBundles.size();
            for (int i = 0; i < size; i++) {
                ((TrackBundle) this.trackBundles.valueAt(i)).updateDrmInitData(containerAtom);
            }
        }
        if (this.pendingSeekTimeUs != C.TIME_UNSET) {
            containerAtom = this.trackBundles.size();
            for (int i2 = 0; i2 < containerAtom; i2++) {
                ((TrackBundle) this.trackBundles.valueAt(i2)).seek(this.pendingSeekTimeUs);
            }
            this.pendingSeekTimeUs = C.TIME_UNSET;
        }
    }

    private void maybeInitExtraTracks() {
        int i = 0;
        if (this.emsgTrackOutputs == null) {
            int i2;
            this.emsgTrackOutputs = new TrackOutput[2];
            if (this.additionalEmsgTrackOutput != null) {
                this.emsgTrackOutputs[0] = this.additionalEmsgTrackOutput;
                i2 = 1;
            } else {
                i2 = 0;
            }
            if ((this.flags & 4) != 0) {
                int i3 = i2 + 1;
                this.emsgTrackOutputs[i2] = this.extractorOutput.track(this.trackBundles.size(), 4);
                i2 = i3;
            }
            this.emsgTrackOutputs = (TrackOutput[]) Arrays.copyOf(this.emsgTrackOutputs, i2);
            for (TrackOutput format : this.emsgTrackOutputs) {
                format.format(EMSG_FORMAT);
            }
        }
        if (this.cea608TrackOutputs == null) {
            this.cea608TrackOutputs = new TrackOutput[this.closedCaptionFormats.size()];
            while (i < this.cea608TrackOutputs.length) {
                TrackOutput track = this.extractorOutput.track((this.trackBundles.size() + 1) + i, 3);
                track.format((Format) this.closedCaptionFormats.get(i));
                this.cea608TrackOutputs[i] = track;
                i++;
            }
        }
    }

    private void onEmsgLeafAtomRead(ParsableByteArray parsableByteArray) {
        if (this.emsgTrackOutputs != null) {
            if (this.emsgTrackOutputs.length != 0) {
                parsableByteArray.setPosition(12);
                int bytesLeft = parsableByteArray.bytesLeft();
                parsableByteArray.readNullTerminatedString();
                parsableByteArray.readNullTerminatedString();
                long scaleLargeTimestamp = Util.scaleLargeTimestamp(parsableByteArray.readUnsignedInt(), 1000000, parsableByteArray.readUnsignedInt());
                for (TrackOutput trackOutput : this.emsgTrackOutputs) {
                    parsableByteArray.setPosition(12);
                    trackOutput.sampleData(parsableByteArray, bytesLeft);
                }
                if (this.segmentIndexEarliestPresentationTimeUs != C.TIME_UNSET) {
                    long j = this.segmentIndexEarliestPresentationTimeUs + scaleLargeTimestamp;
                    long adjustSampleTimestamp = this.timestampAdjuster != null ? this.timestampAdjuster.adjustSampleTimestamp(j) : j;
                    for (TrackOutput sampleMetadata : this.emsgTrackOutputs) {
                        sampleMetadata.sampleMetadata(adjustSampleTimestamp, 1, bytesLeft, 0, null);
                    }
                } else {
                    this.pendingMetadataSampleInfos.addLast(new MetadataSampleInfo(scaleLargeTimestamp, bytesLeft));
                    this.pendingMetadataSampleBytes += bytesLeft;
                }
            }
        }
    }

    private static Pair<Integer, DefaultSampleValues> parseTrex(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(12);
        return Pair.create(Integer.valueOf(parsableByteArray.readInt()), new DefaultSampleValues(parsableByteArray.readUnsignedIntToInt() - 1, parsableByteArray.readUnsignedIntToInt(), parsableByteArray.readUnsignedIntToInt(), parsableByteArray.readInt()));
    }

    private static long parseMehd(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(8);
        return Atom.parseFullAtomVersion(parsableByteArray.readInt()) == 0 ? parsableByteArray.readUnsignedInt() : parsableByteArray.readUnsignedLongToLong();
    }

    private static void parseMoof(ContainerAtom containerAtom, SparseArray<TrackBundle> sparseArray, int i, byte[] bArr) throws ParserException {
        int size = containerAtom.containerChildren.size();
        for (int i2 = 0; i2 < size; i2++) {
            ContainerAtom containerAtom2 = (ContainerAtom) containerAtom.containerChildren.get(i2);
            if (containerAtom2.type == Atom.TYPE_traf) {
                parseTraf(containerAtom2, sparseArray, i, bArr);
            }
        }
    }

    private static void parseTraf(ContainerAtom containerAtom, SparseArray<TrackBundle> sparseArray, int i, byte[] bArr) throws ParserException {
        sparseArray = parseTfhd(containerAtom.getLeafAtomOfType(Atom.TYPE_tfhd).data, sparseArray);
        if (sparseArray != null) {
            TrackFragment trackFragment = sparseArray.fragment;
            long j = trackFragment.nextFragmentDecodeTime;
            sparseArray.reset();
            if (containerAtom.getLeafAtomOfType(Atom.TYPE_tfdt) != null && (i & 2) == 0) {
                j = parseTfdt(containerAtom.getLeafAtomOfType(Atom.TYPE_tfdt).data);
            }
            parseTruns(containerAtom, sparseArray, j, i);
            sparseArray = sparseArray.track.getSampleDescriptionEncryptionBox(trackFragment.header.sampleDescriptionIndex);
            i = containerAtom.getLeafAtomOfType(Atom.TYPE_saiz);
            if (i != 0) {
                parseSaiz(sparseArray, i.data, trackFragment);
            }
            i = containerAtom.getLeafAtomOfType(Atom.TYPE_saio);
            if (i != 0) {
                parseSaio(i.data, trackFragment);
            }
            i = containerAtom.getLeafAtomOfType(Atom.TYPE_senc);
            if (i != 0) {
                parseSenc(i.data, trackFragment);
            }
            i = containerAtom.getLeafAtomOfType(Atom.TYPE_sbgp);
            LeafAtom leafAtomOfType = containerAtom.getLeafAtomOfType(Atom.TYPE_sgpd);
            if (!(i == 0 || leafAtomOfType == null)) {
                parseSgpd(i.data, leafAtomOfType.data, sparseArray != null ? sparseArray.schemeType : null, trackFragment);
            }
            sparseArray = containerAtom.leafChildren.size();
            for (i = 0; i < sparseArray; i++) {
                leafAtomOfType = (LeafAtom) containerAtom.leafChildren.get(i);
                if (leafAtomOfType.type == Atom.TYPE_uuid) {
                    parseUuid(leafAtomOfType.data, trackFragment, bArr);
                }
            }
        }
    }

    private static void parseTruns(ContainerAtom containerAtom, TrackBundle trackBundle, long j, int i) {
        containerAtom = containerAtom.leafChildren;
        int size = containerAtom.size();
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < size; i5++) {
            LeafAtom leafAtom = (LeafAtom) containerAtom.get(i5);
            if (leafAtom.type == Atom.TYPE_trun) {
                ParsableByteArray parsableByteArray = leafAtom.data;
                parsableByteArray.setPosition(12);
                int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
                if (readUnsignedIntToInt > 0) {
                    i4 += readUnsignedIntToInt;
                    i3++;
                }
            }
        }
        trackBundle.currentTrackRunIndex = 0;
        trackBundle.currentSampleInTrackRun = 0;
        trackBundle.currentSampleIndex = 0;
        trackBundle.fragment.initTables(i3, i4);
        i3 = 0;
        int i6 = 0;
        while (i2 < size) {
            LeafAtom leafAtom2 = (LeafAtom) containerAtom.get(i2);
            if (leafAtom2.type == Atom.TYPE_trun) {
                int i7 = i3 + 1;
                i6 = parseTrun(trackBundle, i3, j, i, leafAtom2.data, i6);
                i3 = i7;
            }
            i2++;
        }
    }

    private static void parseSaiz(TrackEncryptionBox trackEncryptionBox, ParsableByteArray parsableByteArray, TrackFragment trackFragment) throws ParserException {
        trackEncryptionBox = trackEncryptionBox.perSampleIvSize;
        parsableByteArray.setPosition(8);
        boolean z = true;
        if ((Atom.parseFullAtomFlags(parsableByteArray.readInt()) & 1) == 1) {
            parsableByteArray.skipBytes(8);
        }
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
        if (readUnsignedIntToInt == trackFragment.sampleCount) {
            int i;
            if (readUnsignedByte == 0) {
                boolean[] zArr = trackFragment.sampleHasSubsampleEncryptionTable;
                i = 0;
                for (int i2 = 0; i2 < readUnsignedIntToInt; i2++) {
                    int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
                    i += readUnsignedByte2;
                    zArr[i2] = readUnsignedByte2 > trackEncryptionBox;
                }
            } else {
                if (readUnsignedByte <= trackEncryptionBox) {
                    z = false;
                }
                i = (readUnsignedByte * readUnsignedIntToInt) + 0;
                Arrays.fill(trackFragment.sampleHasSubsampleEncryptionTable, 0, readUnsignedIntToInt, z);
            }
            trackFragment.initEncryptionData(i);
            return;
        }
        parsableByteArray = new StringBuilder();
        parsableByteArray.append("Length mismatch: ");
        parsableByteArray.append(readUnsignedIntToInt);
        parsableByteArray.append(", ");
        parsableByteArray.append(trackFragment.sampleCount);
        throw new ParserException(parsableByteArray.toString());
    }

    private static void parseSaio(ParsableByteArray parsableByteArray, TrackFragment trackFragment) throws ParserException {
        parsableByteArray.setPosition(8);
        int readInt = parsableByteArray.readInt();
        if ((Atom.parseFullAtomFlags(readInt) & 1) == 1) {
            parsableByteArray.skipBytes(8);
        }
        int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
        if (readUnsignedIntToInt == 1) {
            long readUnsignedInt;
            readUnsignedIntToInt = Atom.parseFullAtomVersion(readInt);
            long j = trackFragment.auxiliaryDataPosition;
            if (readUnsignedIntToInt == 0) {
                readUnsignedInt = parsableByteArray.readUnsignedInt();
            } else {
                readUnsignedInt = parsableByteArray.readUnsignedLongToLong();
            }
            trackFragment.auxiliaryDataPosition = j + readUnsignedInt;
            return;
        }
        trackFragment = new StringBuilder();
        trackFragment.append("Unexpected saio entry count: ");
        trackFragment.append(readUnsignedIntToInt);
        throw new ParserException(trackFragment.toString());
    }

    private static TrackBundle parseTfhd(ParsableByteArray parsableByteArray, SparseArray<TrackBundle> sparseArray) {
        parsableByteArray.setPosition(8);
        int parseFullAtomFlags = Atom.parseFullAtomFlags(parsableByteArray.readInt());
        sparseArray = getTrackBundle(sparseArray, parsableByteArray.readInt());
        if (sparseArray == null) {
            return null;
        }
        if ((parseFullAtomFlags & 1) != 0) {
            long readUnsignedLongToLong = parsableByteArray.readUnsignedLongToLong();
            sparseArray.fragment.dataPosition = readUnsignedLongToLong;
            sparseArray.fragment.auxiliaryDataPosition = readUnsignedLongToLong;
        }
        DefaultSampleValues defaultSampleValues = sparseArray.defaultSampleValues;
        sparseArray.fragment.header = new DefaultSampleValues((parseFullAtomFlags & 2) != 0 ? parsableByteArray.readUnsignedIntToInt() - 1 : defaultSampleValues.sampleDescriptionIndex, (parseFullAtomFlags & 8) != 0 ? parsableByteArray.readUnsignedIntToInt() : defaultSampleValues.duration, (parseFullAtomFlags & 16) != 0 ? parsableByteArray.readUnsignedIntToInt() : defaultSampleValues.size, (parseFullAtomFlags & 32) != 0 ? parsableByteArray.readUnsignedIntToInt() : defaultSampleValues.flags);
        return sparseArray;
    }

    @Nullable
    private static TrackBundle getTrackBundle(SparseArray<TrackBundle> sparseArray, int i) {
        if (sparseArray.size() == 1) {
            return (TrackBundle) sparseArray.valueAt(0);
        }
        return (TrackBundle) sparseArray.get(i);
    }

    private static long parseTfdt(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(8);
        return Atom.parseFullAtomVersion(parsableByteArray.readInt()) == 1 ? parsableByteArray.readUnsignedLongToLong() : parsableByteArray.readUnsignedInt();
    }

    private static int parseTrun(TrackBundle trackBundle, int i, long j, int i2, ParsableByteArray parsableByteArray, int i3) {
        long[] jArr;
        long j2;
        long j3;
        TrackBundle trackBundle2 = trackBundle;
        parsableByteArray.setPosition(8);
        int parseFullAtomFlags = Atom.parseFullAtomFlags(parsableByteArray.readInt());
        Track track = trackBundle2.track;
        TrackFragment trackFragment = trackBundle2.fragment;
        DefaultSampleValues defaultSampleValues = trackFragment.header;
        trackFragment.trunLength[i] = parsableByteArray.readUnsignedIntToInt();
        trackFragment.trunDataPosition[i] = trackFragment.dataPosition;
        if ((parseFullAtomFlags & 1) != 0) {
            long[] jArr2 = trackFragment.trunDataPosition;
            jArr2[i] = jArr2[i] + ((long) parsableByteArray.readInt());
        }
        Object obj = (parseFullAtomFlags & 4) != 0 ? 1 : null;
        int i4 = defaultSampleValues.flags;
        if (obj != null) {
            i4 = parsableByteArray.readUnsignedIntToInt();
        }
        Object obj2 = (parseFullAtomFlags & 256) != 0 ? 1 : null;
        Object obj3 = (parseFullAtomFlags & 512) != 0 ? 1 : null;
        Object obj4 = (parseFullAtomFlags & 1024) != 0 ? 1 : null;
        Object obj5 = (parseFullAtomFlags & 2048) != 0 ? 1 : null;
        long j4 = 0;
        if (track.editListDurations != null && track.editListDurations.length == 1 && track.editListDurations[0] == 0) {
            j4 = Util.scaleLargeTimestamp(track.editListMediaTimes[0], 1000, track.timescale);
        }
        int[] iArr = trackFragment.sampleSizeTable;
        int[] iArr2 = trackFragment.sampleCompositionTimeOffsetTable;
        long[] jArr3 = trackFragment.sampleDecodingTimeTable;
        int i5 = i4;
        boolean[] zArr = trackFragment.sampleIsSyncFrameTable;
        Object obj6 = (track.type != 2 || (i2 & 1) == 0) ? null : 1;
        i4 = i3 + trackFragment.trunLength[i];
        Object obj7 = obj6;
        long j5 = track.timescale;
        if (i > 0) {
            jArr = jArr3;
            j2 = j4;
            j3 = trackFragment.nextFragmentDecodeTime;
        } else {
            jArr = jArr3;
            j2 = j4;
            j3 = j;
        }
        int i6 = i3;
        while (i6 < i4) {
            Object obj8;
            int readUnsignedIntToInt;
            Object obj9;
            int i7;
            DefaultSampleValues defaultSampleValues2;
            int readUnsignedIntToInt2 = obj2 != null ? parsableByteArray.readUnsignedIntToInt() : defaultSampleValues.duration;
            if (obj3 != null) {
                obj8 = obj2;
                readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
            } else {
                obj8 = obj2;
                readUnsignedIntToInt = defaultSampleValues.size;
            }
            if (i6 == 0 && obj != null) {
                obj9 = obj;
                i7 = i5;
            } else if (obj4 != null) {
                i7 = parsableByteArray.readInt();
                obj9 = obj;
            } else {
                obj9 = obj;
                i7 = defaultSampleValues.flags;
            }
            if (obj5 != null) {
                defaultSampleValues2 = defaultSampleValues;
                iArr2[i6] = (int) ((((long) parsableByteArray.readInt()) * 1000) / j5);
            } else {
                defaultSampleValues2 = defaultSampleValues;
                iArr2[i6] = 0;
            }
            jArr[i6] = Util.scaleLargeTimestamp(j3, 1000, j5) - j2;
            iArr[i6] = readUnsignedIntToInt;
            boolean z = ((i7 >> 16) & 1) == 0 && (obj7 == null || i6 == 0);
            zArr[i6] = z;
            j3 += (long) readUnsignedIntToInt2;
            i6++;
            obj2 = obj8;
            obj = obj9;
            defaultSampleValues = defaultSampleValues2;
        }
        trackFragment.nextFragmentDecodeTime = j3;
        return i4;
    }

    private static void parseUuid(ParsableByteArray parsableByteArray, TrackFragment trackFragment, byte[] bArr) throws ParserException {
        parsableByteArray.setPosition(8);
        parsableByteArray.readBytes(bArr, 0, 16);
        if (Arrays.equals(bArr, PIFF_SAMPLE_ENCRYPTION_BOX_EXTENDED_TYPE) != null) {
            parseSenc(parsableByteArray, 16, trackFragment);
        }
    }

    private static void parseSenc(ParsableByteArray parsableByteArray, TrackFragment trackFragment) throws ParserException {
        parseSenc(parsableByteArray, 0, trackFragment);
    }

    private static void parseSenc(ParsableByteArray parsableByteArray, int i, TrackFragment trackFragment) throws ParserException {
        parsableByteArray.setPosition(i + 8);
        i = Atom.parseFullAtomFlags(parsableByteArray.readInt());
        if ((i & 1) == 0) {
            i = (i & 2) != 0 ? 1 : 0;
            int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
            if (readUnsignedIntToInt == trackFragment.sampleCount) {
                Arrays.fill(trackFragment.sampleHasSubsampleEncryptionTable, 0, readUnsignedIntToInt, i);
                trackFragment.initEncryptionData(parsableByteArray.bytesLeft());
                trackFragment.fillEncryptionData(parsableByteArray);
                return;
            }
            i = new StringBuilder();
            i.append("Length mismatch: ");
            i.append(readUnsignedIntToInt);
            i.append(", ");
            i.append(trackFragment.sampleCount);
            throw new ParserException(i.toString());
        }
        throw new ParserException("Overriding TrackEncryptionBox parameters is unsupported.");
    }

    private static void parseSgpd(ParsableByteArray parsableByteArray, ParsableByteArray parsableByteArray2, String str, TrackFragment trackFragment) throws ParserException {
        parsableByteArray.setPosition(8);
        int readInt = parsableByteArray.readInt();
        if (parsableByteArray.readInt() == SAMPLE_GROUP_TYPE_seig) {
            if (Atom.parseFullAtomVersion(readInt) == 1) {
                parsableByteArray.skipBytes(4);
            }
            if (parsableByteArray.readInt() == 1) {
                parsableByteArray2.setPosition(8);
                parsableByteArray = parsableByteArray2.readInt();
                if (parsableByteArray2.readInt() == SAMPLE_GROUP_TYPE_seig) {
                    parsableByteArray = Atom.parseFullAtomVersion(parsableByteArray);
                    if (parsableByteArray == 1) {
                        if (parsableByteArray2.readUnsignedInt() == 0) {
                            throw new ParserException("Variable length description in sgpd found (unsupported)");
                        }
                    } else if (parsableByteArray >= 2) {
                        parsableByteArray2.skipBytes(4);
                    }
                    if (parsableByteArray2.readUnsignedInt() == 1) {
                        parsableByteArray2.skipBytes(1);
                        parsableByteArray = parsableByteArray2.readUnsignedByte();
                        int i = (parsableByteArray & PsExtractor.VIDEO_STREAM_MASK) >> 4;
                        int i2 = parsableByteArray & 15;
                        boolean z = parsableByteArray2.readUnsignedByte() == 1;
                        if (z) {
                            byte[] bArr;
                            int readUnsignedByte = parsableByteArray2.readUnsignedByte();
                            byte[] bArr2 = new byte[16];
                            parsableByteArray2.readBytes(bArr2, 0, bArr2.length);
                            if (z && readUnsignedByte == 0) {
                                parsableByteArray = parsableByteArray2.readUnsignedByte();
                                byte[] bArr3 = new byte[parsableByteArray];
                                parsableByteArray2.readBytes(bArr3, 0, parsableByteArray);
                                bArr = bArr3;
                            } else {
                                bArr = null;
                            }
                            trackFragment.definesEncryptionData = true;
                            trackFragment.trackEncryptionBox = new TrackEncryptionBox(z, str, readUnsignedByte, bArr2, i, i2, bArr);
                            return;
                        }
                        return;
                    }
                    throw new ParserException("Entry count in sgpd != 1 (unsupported).");
                }
                return;
            }
            throw new ParserException("Entry count in sbgp != 1 (unsupported).");
        }
    }

    private static Pair<Long, ChunkIndex> parseSidx(ParsableByteArray parsableByteArray, long j) throws ParserException {
        long readUnsignedInt;
        long readUnsignedInt2;
        ParsableByteArray parsableByteArray2 = parsableByteArray;
        parsableByteArray2.setPosition(8);
        int parseFullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
        parsableByteArray2.skipBytes(4);
        long readUnsignedInt3 = parsableByteArray.readUnsignedInt();
        if (parseFullAtomVersion == 0) {
            readUnsignedInt = parsableByteArray.readUnsignedInt();
            readUnsignedInt2 = j + parsableByteArray.readUnsignedInt();
        } else {
            readUnsignedInt = parsableByteArray.readUnsignedLongToLong();
            readUnsignedInt2 = j + parsableByteArray.readUnsignedLongToLong();
        }
        long j2 = readUnsignedInt2;
        readUnsignedInt2 = readUnsignedInt;
        long scaleLargeTimestamp = Util.scaleLargeTimestamp(readUnsignedInt2, 1000000, readUnsignedInt3);
        parsableByteArray2.skipBytes(2);
        parseFullAtomVersion = parsableByteArray.readUnsignedShort();
        int[] iArr = new int[parseFullAtomVersion];
        long[] jArr = new long[parseFullAtomVersion];
        long[] jArr2 = new long[parseFullAtomVersion];
        long[] jArr3 = new long[parseFullAtomVersion];
        long j3 = readUnsignedInt2;
        readUnsignedInt = scaleLargeTimestamp;
        int i = 0;
        while (i < parseFullAtomVersion) {
            int readInt = parsableByteArray.readInt();
            if ((readInt & Integer.MIN_VALUE) == 0) {
                long readUnsignedInt4 = parsableByteArray.readUnsignedInt();
                iArr[i] = readInt & Integer.MAX_VALUE;
                jArr[i] = j2;
                jArr3[i] = readUnsignedInt;
                j3 += readUnsignedInt4;
                long[] jArr4 = jArr2;
                long[] jArr5 = jArr3;
                int i2 = parseFullAtomVersion;
                int[] iArr2 = iArr;
                long[] jArr6 = jArr;
                readUnsignedInt = Util.scaleLargeTimestamp(j3, 1000000, readUnsignedInt3);
                jArr4[i] = readUnsignedInt - jArr5[i];
                parsableByteArray2.skipBytes(4);
                j2 += (long) iArr2[i];
                i++;
                jArr2 = jArr4;
                iArr = iArr2;
                jArr3 = jArr5;
                parseFullAtomVersion = i2;
                jArr = jArr6;
            } else {
                throw new ParserException("Unhandled indirect reference");
            }
        }
        return Pair.create(Long.valueOf(scaleLargeTimestamp), new ChunkIndex(iArr, jArr, jArr2, jArr3));
    }

    private void readEncryptionData(ExtractorInput extractorInput) throws IOException, InterruptedException {
        int size = this.trackBundles.size();
        TrackBundle trackBundle = null;
        long j = Long.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            TrackFragment trackFragment = ((TrackBundle) this.trackBundles.valueAt(i)).fragment;
            if (trackFragment.sampleEncryptionDataNeedsFill && trackFragment.auxiliaryDataPosition < j) {
                long j2 = trackFragment.auxiliaryDataPosition;
                trackBundle = (TrackBundle) this.trackBundles.valueAt(i);
                j = j2;
            }
        }
        if (trackBundle == null) {
            this.parserState = 3;
            return;
        }
        size = (int) (j - extractorInput.getPosition());
        if (size >= 0) {
            extractorInput.skipFully(size);
            trackBundle.fragment.fillEncryptionData(extractorInput);
            return;
        }
        throw new ParserException("Offset to encryption data was negative.");
    }

    private boolean readSample(ExtractorInput extractorInput) throws IOException, InterruptedException {
        int i;
        CryptoData cryptoData;
        ExtractorInput extractorInput2 = extractorInput;
        int i2 = 4;
        int i3 = 1;
        int i4 = 0;
        if (this.parserState == 3) {
            if (r0.currentTrackBundle == null) {
                TrackBundle nextFragmentRun = getNextFragmentRun(r0.trackBundles);
                if (nextFragmentRun == null) {
                    int position = (int) (r0.endOfMdatPosition - extractorInput.getPosition());
                    if (position >= 0) {
                        extractorInput2.skipFully(position);
                        enterReadingAtomHeaderState();
                        return false;
                    }
                    throw new ParserException("Offset to end of mdat was negative.");
                }
                int position2 = (int) (nextFragmentRun.fragment.trunDataPosition[nextFragmentRun.currentTrackRunIndex] - extractorInput.getPosition());
                if (position2 < 0) {
                    Log.w(TAG, "Ignoring negative offset to sample data.");
                    position2 = 0;
                }
                extractorInput2.skipFully(position2);
                r0.currentTrackBundle = nextFragmentRun;
            }
            r0.sampleSize = r0.currentTrackBundle.fragment.sampleSizeTable[r0.currentTrackBundle.currentSampleIndex];
            if (r0.currentTrackBundle.currentSampleIndex < r0.currentTrackBundle.firstSampleToOutputIndex) {
                extractorInput2.skipFully(r0.sampleSize);
                r0.currentTrackBundle.skipSampleEncryptionData();
                if (!r0.currentTrackBundle.next()) {
                    r0.currentTrackBundle = null;
                }
                r0.parserState = 3;
                return true;
            }
            if (r0.currentTrackBundle.track.sampleTransformation == 1) {
                r0.sampleSize -= 8;
                extractorInput2.skipFully(8);
            }
            r0.sampleBytesWritten = r0.currentTrackBundle.outputSampleEncryptionData();
            r0.sampleSize += r0.sampleBytesWritten;
            r0.parserState = 4;
            r0.sampleCurrentNalBytesRemaining = 0;
        }
        TrackFragment trackFragment = r0.currentTrackBundle.fragment;
        Track track = r0.currentTrackBundle.track;
        TrackOutput trackOutput = r0.currentTrackBundle.output;
        int i5 = r0.currentTrackBundle.currentSampleIndex;
        long samplePresentationTime = trackFragment.getSamplePresentationTime(i5) * 1000;
        if (r0.timestampAdjuster != null) {
            samplePresentationTime = r0.timestampAdjuster.adjustSampleTimestamp(samplePresentationTime);
        }
        long j = samplePresentationTime;
        if (track.nalUnitLengthFieldLength != 0) {
            byte[] bArr = r0.nalPrefix.data;
            bArr[0] = (byte) 0;
            bArr[1] = (byte) 0;
            bArr[2] = (byte) 0;
            int i6 = track.nalUnitLengthFieldLength + 1;
            i = 4 - track.nalUnitLengthFieldLength;
            while (r0.sampleBytesWritten < r0.sampleSize) {
                if (r0.sampleCurrentNalBytesRemaining == 0) {
                    extractorInput2.readFully(bArr, i, i6);
                    r0.nalPrefix.setPosition(i4);
                    r0.sampleCurrentNalBytesRemaining = r0.nalPrefix.readUnsignedIntToInt() - i3;
                    r0.nalStartCode.setPosition(i4);
                    trackOutput.sampleData(r0.nalStartCode, i2);
                    trackOutput.sampleData(r0.nalPrefix, i3);
                    boolean z = r0.cea608TrackOutputs.length > 0 && NalUnitUtil.isNalUnitSei(track.format.sampleMimeType, bArr[i2]);
                    r0.processSeiNalUnitPayload = z;
                    r0.sampleBytesWritten += 5;
                    r0.sampleSize += i;
                } else {
                    int i7;
                    if (r0.processSeiNalUnitPayload) {
                        r0.nalBuffer.reset(r0.sampleCurrentNalBytesRemaining);
                        extractorInput2.readFully(r0.nalBuffer.data, i4, r0.sampleCurrentNalBytesRemaining);
                        trackOutput.sampleData(r0.nalBuffer, r0.sampleCurrentNalBytesRemaining);
                        i7 = r0.sampleCurrentNalBytesRemaining;
                        int unescapeStream = NalUnitUtil.unescapeStream(r0.nalBuffer.data, r0.nalBuffer.limit());
                        r0.nalBuffer.setPosition(MimeTypes.VIDEO_H265.equals(track.format.sampleMimeType));
                        r0.nalBuffer.setLimit(unescapeStream);
                        CeaUtil.consume(j, r0.nalBuffer, r0.cea608TrackOutputs);
                    } else {
                        i7 = trackOutput.sampleData(extractorInput2, r0.sampleCurrentNalBytesRemaining, false);
                    }
                    r0.sampleBytesWritten += i7;
                    r0.sampleCurrentNalBytesRemaining -= i7;
                    i2 = 4;
                    i3 = 1;
                    i4 = 0;
                }
            }
        } else {
            while (r0.sampleBytesWritten < r0.sampleSize) {
                r0.sampleBytesWritten += trackOutput.sampleData(extractorInput2, r0.sampleSize - r0.sampleBytesWritten, false);
            }
        }
        boolean z2 = trackFragment.sampleIsSyncFrameTable[i5];
        TrackEncryptionBox access$100 = r0.currentTrackBundle.getEncryptionBoxIfEncrypted();
        if (access$100 != null) {
            i = z2 | 1073741824;
            cryptoData = access$100.cryptoData;
        } else {
            i = z2;
            cryptoData = null;
        }
        long j2 = j;
        trackOutput.sampleMetadata(j, i, r0.sampleSize, 0, cryptoData);
        outputPendingMetadataSamples(j2);
        if (!r0.currentTrackBundle.next()) {
            r0.currentTrackBundle = null;
        }
        r0.parserState = 3;
        return true;
    }

    private void outputPendingMetadataSamples(long j) {
        while (!this.pendingMetadataSampleInfos.isEmpty()) {
            MetadataSampleInfo metadataSampleInfo = (MetadataSampleInfo) this.pendingMetadataSampleInfos.removeFirst();
            this.pendingMetadataSampleBytes -= metadataSampleInfo.size;
            long j2 = metadataSampleInfo.presentationTimeDeltaUs + j;
            if (this.timestampAdjuster != null) {
                j2 = this.timestampAdjuster.adjustSampleTimestamp(j2);
            }
            for (TrackOutput sampleMetadata : this.emsgTrackOutputs) {
                sampleMetadata.sampleMetadata(j2, 1, metadataSampleInfo.size, this.pendingMetadataSampleBytes, null);
            }
        }
    }

    private static TrackBundle getNextFragmentRun(SparseArray<TrackBundle> sparseArray) {
        int size = sparseArray.size();
        TrackBundle trackBundle = null;
        long j = Long.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            TrackBundle trackBundle2 = (TrackBundle) sparseArray.valueAt(i);
            if (trackBundle2.currentTrackRunIndex != trackBundle2.fragment.trunCount) {
                long j2 = trackBundle2.fragment.trunDataPosition[trackBundle2.currentTrackRunIndex];
                if (j2 < j) {
                    trackBundle = trackBundle2;
                    j = j2;
                }
            }
        }
        return trackBundle;
    }

    private static DrmInitData getDrmInitDataFromAtoms(List<LeafAtom> list) {
        int size = list.size();
        List list2 = null;
        for (int i = 0; i < size; i++) {
            LeafAtom leafAtom = (LeafAtom) list.get(i);
            if (leafAtom.type == Atom.TYPE_pssh) {
                if (list2 == null) {
                    list2 = new ArrayList();
                }
                byte[] bArr = leafAtom.data.data;
                UUID parseUuid = PsshAtomUtil.parseUuid(bArr);
                if (parseUuid == null) {
                    Log.w(TAG, "Skipped pssh atom (failed to extract uuid)");
                } else {
                    list2.add(new SchemeData(parseUuid, MimeTypes.VIDEO_MP4, bArr));
                }
            }
        }
        if (list2 == null) {
            return null;
        }
        return new DrmInitData(list2);
    }

    private static boolean shouldParseLeafAtom(int i) {
        if (!(i == Atom.TYPE_hdlr || i == Atom.TYPE_mdhd || i == Atom.TYPE_mvhd || i == Atom.TYPE_sidx || i == Atom.TYPE_stsd || i == Atom.TYPE_tfdt || i == Atom.TYPE_tfhd || i == Atom.TYPE_tkhd || i == Atom.TYPE_trex || i == Atom.TYPE_trun || i == Atom.TYPE_pssh || i == Atom.TYPE_saiz || i == Atom.TYPE_saio || i == Atom.TYPE_senc || i == Atom.TYPE_uuid || i == Atom.TYPE_sbgp || i == Atom.TYPE_sgpd || i == Atom.TYPE_elst || i == Atom.TYPE_mehd)) {
            if (i != Atom.TYPE_emsg) {
                return false;
            }
        }
        return true;
    }

    private static boolean shouldParseContainerAtom(int i) {
        if (!(i == Atom.TYPE_moov || i == Atom.TYPE_trak || i == Atom.TYPE_mdia || i == Atom.TYPE_minf || i == Atom.TYPE_stbl || i == Atom.TYPE_moof || i == Atom.TYPE_traf || i == Atom.TYPE_mvex)) {
            if (i != Atom.TYPE_edts) {
                return false;
            }
        }
        return true;
    }
}
