package com.google.android.exoplayer2.extractor.amr;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ConstantBitrateSeekMap;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekMap.Unseekable;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

public final class AmrExtractor implements Extractor {
    public static final ExtractorsFactory FACTORY = -$$Lambda$AmrExtractor$lVuGuaAcylUV-_XE4-hSR1hBylI.INSTANCE;
    public static final int FLAG_ENABLE_CONSTANT_BITRATE_SEEKING = 1;
    private static final int MAX_FRAME_SIZE_BYTES = frameSizeBytesByTypeWb[8];
    private static final int NUM_SAME_SIZE_CONSTANT_BIT_RATE_THRESHOLD = 20;
    private static final int SAMPLE_RATE_NB = 8000;
    private static final int SAMPLE_RATE_WB = 16000;
    private static final int SAMPLE_TIME_PER_FRAME_US = 20000;
    private static final byte[] amrSignatureNb = Util.getUtf8Bytes("#!AMR\n");
    private static final byte[] amrSignatureWb = Util.getUtf8Bytes("#!AMR-WB\n");
    private static final int[] frameSizeBytesByTypeNb = new int[]{13, 14, 16, 18, 20, 21, 27, 32, 6, 7, 6, 6, 1, 1, 1, 1};
    private static final int[] frameSizeBytesByTypeWb = new int[]{18, 24, 33, 37, 41, 47, 51, 59, 61, 6, 1, 1, 1, 1, 1, 1};
    private int currentSampleBytesRemaining;
    private int currentSampleSize;
    private long currentSampleTimeUs;
    private ExtractorOutput extractorOutput;
    private long firstSamplePosition;
    private int firstSampleSize;
    private final int flags;
    private boolean hasOutputFormat;
    private boolean hasOutputSeekMap;
    private boolean isWideBand;
    private int numSamplesWithSameSize;
    private final byte[] scratch;
    @Nullable
    private SeekMap seekMap;
    private long timeOffsetUs;
    private TrackOutput trackOutput;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public void release() {
    }

    public AmrExtractor() {
        this(0);
    }

    public AmrExtractor(int i) {
        this.flags = i;
        this.scratch = new byte[1];
        this.firstSampleSize = -1;
    }

    public boolean sniff(ExtractorInput extractorInput) throws IOException, InterruptedException {
        return readAmrHeader(extractorInput);
    }

    public void init(ExtractorOutput extractorOutput) {
        this.extractorOutput = extractorOutput;
        this.trackOutput = extractorOutput.track(0, 1);
        extractorOutput.endTracks();
    }

    public int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException, InterruptedException {
        if (extractorInput.getPosition() == 0) {
            if (readAmrHeader(extractorInput) == null) {
                throw new ParserException("Could not find AMR header.");
            }
        }
        maybeOutputFormat();
        positionHolder = readSample(extractorInput);
        maybeOutputSeekMap(extractorInput.getLength(), positionHolder);
        return positionHolder;
    }

    public void seek(long j, long j2) {
        this.currentSampleTimeUs = 0;
        this.currentSampleSize = 0;
        this.currentSampleBytesRemaining = 0;
        if (j == 0 || !(this.seekMap instanceof ConstantBitrateSeekMap)) {
            this.timeOffsetUs = 0;
        } else {
            this.timeOffsetUs = ((ConstantBitrateSeekMap) this.seekMap).getTimeUsAtPosition(j);
        }
    }

    static int frameSizeBytesByTypeNb(int i) {
        return frameSizeBytesByTypeNb[i];
    }

    static int frameSizeBytesByTypeWb(int i) {
        return frameSizeBytesByTypeWb[i];
    }

    static byte[] amrSignatureNb() {
        return Arrays.copyOf(amrSignatureNb, amrSignatureNb.length);
    }

    static byte[] amrSignatureWb() {
        return Arrays.copyOf(amrSignatureWb, amrSignatureWb.length);
    }

    private boolean readAmrHeader(ExtractorInput extractorInput) throws IOException, InterruptedException {
        if (peekAmrSignature(extractorInput, amrSignatureNb)) {
            this.isWideBand = false;
            extractorInput.skipFully(amrSignatureNb.length);
            return true;
        } else if (!peekAmrSignature(extractorInput, amrSignatureWb)) {
            return false;
        } else {
            this.isWideBand = true;
            extractorInput.skipFully(amrSignatureWb.length);
            return true;
        }
    }

    private boolean peekAmrSignature(ExtractorInput extractorInput, byte[] bArr) throws IOException, InterruptedException {
        extractorInput.resetPeekPosition();
        byte[] bArr2 = new byte[bArr.length];
        extractorInput.peekFully(bArr2, 0, bArr.length);
        return Arrays.equals(bArr2, bArr);
    }

    private void maybeOutputFormat() {
        if (!this.hasOutputFormat) {
            this.hasOutputFormat = true;
            this.trackOutput.format(Format.createAudioSampleFormat(null, this.isWideBand ? MimeTypes.AUDIO_AMR_WB : MimeTypes.AUDIO_AMR_NB, null, -1, MAX_FRAME_SIZE_BYTES, 1, this.isWideBand ? SAMPLE_RATE_WB : 8000, -1, null, null, 0, null));
        }
    }

    private int readSample(com.google.android.exoplayer2.extractor.ExtractorInput r9) throws java.io.IOException, java.lang.InterruptedException {
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
        r0 = r8.currentSampleBytesRemaining;
        r1 = 1;
        r2 = -1;
        if (r0 != 0) goto L_0x002b;
    L_0x0006:
        r0 = r8.peekNextSampleSize(r9);	 Catch:{ EOFException -> 0x002a }
        r8.currentSampleSize = r0;	 Catch:{ EOFException -> 0x002a }
        r0 = r8.currentSampleSize;
        r8.currentSampleBytesRemaining = r0;
        r0 = r8.firstSampleSize;
        if (r0 != r2) goto L_0x001e;
    L_0x0014:
        r3 = r9.getPosition();
        r8.firstSamplePosition = r3;
        r0 = r8.currentSampleSize;
        r8.firstSampleSize = r0;
    L_0x001e:
        r0 = r8.firstSampleSize;
        r3 = r8.currentSampleSize;
        if (r0 != r3) goto L_0x002b;
    L_0x0024:
        r0 = r8.numSamplesWithSameSize;
        r0 = r0 + r1;
        r8.numSamplesWithSameSize = r0;
        goto L_0x002b;
    L_0x002a:
        return r2;
    L_0x002b:
        r0 = r8.trackOutput;
        r3 = r8.currentSampleBytesRemaining;
        r9 = r0.sampleData(r9, r3, r1);
        if (r9 != r2) goto L_0x0036;
    L_0x0035:
        return r2;
    L_0x0036:
        r0 = r8.currentSampleBytesRemaining;
        r0 = r0 - r9;
        r8.currentSampleBytesRemaining = r0;
        r9 = r8.currentSampleBytesRemaining;
        r0 = 0;
        if (r9 <= 0) goto L_0x0041;
    L_0x0040:
        return r0;
    L_0x0041:
        r1 = r8.trackOutput;
        r2 = r8.timeOffsetUs;
        r4 = r8.currentSampleTimeUs;
        r2 = r2 + r4;
        r4 = 1;
        r5 = r8.currentSampleSize;
        r6 = 0;
        r7 = 0;
        r1.sampleMetadata(r2, r4, r5, r6, r7);
        r1 = r8.currentSampleTimeUs;
        r3 = 20000; // 0x4e20 float:2.8026E-41 double:9.8813E-320;
        r1 = r1 + r3;
        r8.currentSampleTimeUs = r1;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.amr.AmrExtractor.readSample(com.google.android.exoplayer2.extractor.ExtractorInput):int");
    }

    private int peekNextSampleSize(ExtractorInput extractorInput) throws IOException, InterruptedException {
        extractorInput.resetPeekPosition();
        extractorInput.peekFully(this.scratch, 0, 1);
        extractorInput = this.scratch[0];
        if ((extractorInput & 131) <= 0) {
            return getFrameSizeInBytes((extractorInput >> 3) & 15);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid padding bits for frame header ");
        stringBuilder.append(extractorInput);
        throw new ParserException(stringBuilder.toString());
    }

    private int getFrameSizeInBytes(int i) throws ParserException {
        if (isValidFrameType(i)) {
            return this.isWideBand ? frameSizeBytesByTypeWb[i] : frameSizeBytesByTypeNb[i];
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Illegal AMR ");
            stringBuilder.append(this.isWideBand ? "WB" : "NB");
            stringBuilder.append(" frame type ");
            stringBuilder.append(i);
            throw new ParserException(stringBuilder.toString());
        }
    }

    private boolean isValidFrameType(int i) {
        return i >= 0 && i <= 15 && (isWideBandValidFrameType(i) || isNarrowBandValidFrameType(i) != 0);
    }

    private boolean isWideBandValidFrameType(int i) {
        return this.isWideBand && (i < 10 || i > 13);
    }

    private boolean isNarrowBandValidFrameType(int i) {
        return !this.isWideBand && (i < 12 || i > 14);
    }

    private void maybeOutputSeekMap(long j, int i) {
        if (!this.hasOutputSeekMap) {
            if (!((this.flags & 1) == 0 || j == -1)) {
                if (this.firstSampleSize == -1 || this.firstSampleSize == this.currentSampleSize) {
                    if (this.numSamplesWithSameSize >= 20 || i == -1) {
                        this.seekMap = getConstantBitrateSeekMap(j);
                        this.extractorOutput.seekMap(this.seekMap);
                        this.hasOutputSeekMap = true;
                    }
                }
            }
            this.seekMap = new Unseekable(C.TIME_UNSET);
            this.extractorOutput.seekMap(this.seekMap);
            this.hasOutputSeekMap = true;
        }
    }

    private SeekMap getConstantBitrateSeekMap(long j) {
        return new ConstantBitrateSeekMap(j, this.firstSamplePosition, getBitrateFromFrameSize(this.firstSampleSize, 20000), this.firstSampleSize);
    }

    private static int getBitrateFromFrameSize(int i, long j) {
        return (int) ((((long) (i * 8)) * 1000000) / j);
    }
}
