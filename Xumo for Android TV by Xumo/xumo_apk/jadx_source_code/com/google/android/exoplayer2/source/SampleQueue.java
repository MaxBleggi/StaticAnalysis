package com.google.android.exoplayer2.source;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.TrackOutput.CryptoData;
import com.google.android.exoplayer2.source.SampleMetadataQueue.SampleExtrasHolder;
import com.google.android.exoplayer2.upstream.Allocation;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;

public final class SampleQueue implements TrackOutput {
    public static final int ADVANCE_FAILED = -1;
    private static final int INITIAL_SCRATCH_SIZE = 32;
    private final int allocationLength;
    private final Allocator allocator;
    private Format downstreamFormat;
    private final SampleExtrasHolder extrasHolder = new SampleExtrasHolder();
    private AllocationNode firstAllocationNode = new AllocationNode(0, this.allocationLength);
    private Format lastUnadjustedFormat;
    private final SampleMetadataQueue metadataQueue = new SampleMetadataQueue();
    private boolean pendingFormatAdjustment;
    private boolean pendingSplice;
    private AllocationNode readAllocationNode = this.firstAllocationNode;
    private long sampleOffsetUs;
    private final ParsableByteArray scratch = new ParsableByteArray(32);
    private long totalBytesWritten;
    private UpstreamFormatChangedListener upstreamFormatChangeListener;
    private AllocationNode writeAllocationNode = this.firstAllocationNode;

    private static final class AllocationNode {
        @Nullable
        public Allocation allocation;
        public final long endPosition;
        @Nullable
        public AllocationNode next;
        public final long startPosition;
        public boolean wasInitialized;

        public AllocationNode(long j, int i) {
            this.startPosition = j;
            this.endPosition = j + ((long) i);
        }

        public void initialize(Allocation allocation, AllocationNode allocationNode) {
            this.allocation = allocation;
            this.next = allocationNode;
            this.wasInitialized = true;
        }

        public int translateOffset(long j) {
            return ((int) (j - this.startPosition)) + this.allocation.offset;
        }

        public AllocationNode clear() {
            this.allocation = null;
            AllocationNode allocationNode = this.next;
            this.next = null;
            return allocationNode;
        }
    }

    public interface UpstreamFormatChangedListener {
        void onUpstreamFormatChanged(Format format);
    }

    public SampleQueue(Allocator allocator) {
        this.allocator = allocator;
        this.allocationLength = allocator.getIndividualAllocationLength();
    }

    public void reset() {
        reset(false);
    }

    public void reset(boolean z) {
        this.metadataQueue.reset(z);
        clearAllocationNodes(this.firstAllocationNode);
        this.firstAllocationNode = new AllocationNode(0, this.allocationLength);
        this.readAllocationNode = this.firstAllocationNode;
        this.writeAllocationNode = this.firstAllocationNode;
        this.totalBytesWritten = 0;
        this.allocator.trim();
    }

    public void sourceId(int i) {
        this.metadataQueue.sourceId(i);
    }

    public void splice() {
        this.pendingSplice = true;
    }

    public int getWriteIndex() {
        return this.metadataQueue.getWriteIndex();
    }

    public void discardUpstreamSamples(int i) {
        this.totalBytesWritten = this.metadataQueue.discardUpstreamSamples(i);
        if (this.totalBytesWritten != 0) {
            if (this.totalBytesWritten != this.firstAllocationNode.startPosition) {
                i = this.firstAllocationNode;
                while (this.totalBytesWritten > i.endPosition) {
                    i = i.next;
                }
                AllocationNode allocationNode = i.next;
                clearAllocationNodes(allocationNode);
                i.next = new AllocationNode(i.endPosition, this.allocationLength);
                this.writeAllocationNode = this.totalBytesWritten == i.endPosition ? i.next : i;
                if (this.readAllocationNode == allocationNode) {
                    this.readAllocationNode = i.next;
                    return;
                }
                return;
            }
        }
        clearAllocationNodes(this.firstAllocationNode);
        this.firstAllocationNode = new AllocationNode(this.totalBytesWritten, this.allocationLength);
        this.readAllocationNode = this.firstAllocationNode;
        this.writeAllocationNode = this.firstAllocationNode;
    }

    public boolean hasNextSample() {
        return this.metadataQueue.hasNextSample();
    }

    public int getFirstIndex() {
        return this.metadataQueue.getFirstIndex();
    }

    public int getReadIndex() {
        return this.metadataQueue.getReadIndex();
    }

    public int peekSourceId() {
        return this.metadataQueue.peekSourceId();
    }

    public Format getUpstreamFormat() {
        return this.metadataQueue.getUpstreamFormat();
    }

    public long getLargestQueuedTimestampUs() {
        return this.metadataQueue.getLargestQueuedTimestampUs();
    }

    public long getFirstTimestampUs() {
        return this.metadataQueue.getFirstTimestampUs();
    }

    public void rewind() {
        this.metadataQueue.rewind();
        this.readAllocationNode = this.firstAllocationNode;
    }

    public void discardTo(long j, boolean z, boolean z2) {
        discardDownstreamTo(this.metadataQueue.discardTo(j, z, z2));
    }

    public void discardToRead() {
        discardDownstreamTo(this.metadataQueue.discardToRead());
    }

    public void discardToEnd() {
        discardDownstreamTo(this.metadataQueue.discardToEnd());
    }

    public int advanceToEnd() {
        return this.metadataQueue.advanceToEnd();
    }

    public int advanceTo(long j, boolean z, boolean z2) {
        return this.metadataQueue.advanceTo(j, z, z2);
    }

    public boolean setReadPosition(int i) {
        return this.metadataQueue.setReadPosition(i);
    }

    public int read(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, boolean z, boolean z2, long j) {
        switch (this.metadataQueue.read(formatHolder, decoderInputBuffer, z, z2, this.downstreamFormat, this.extrasHolder)) {
            case C.RESULT_FORMAT_READ /*-5*/:
                this.downstreamFormat = formatHolder.format;
                return -5;
            case true:
                if (decoderInputBuffer.isEndOfStream() == null) {
                    if (decoderInputBuffer.timeUs < j) {
                        decoderInputBuffer.addFlag(Integer.MIN_VALUE);
                    }
                    if (decoderInputBuffer.isEncrypted() != null) {
                        readEncryptionData(decoderInputBuffer, this.extrasHolder);
                    }
                    decoderInputBuffer.ensureSpaceForWrite(this.extrasHolder.size);
                    readData(this.extrasHolder.offset, decoderInputBuffer.data, this.extrasHolder.size);
                }
                return -4;
            case true:
                return -3;
            default:
                throw new IllegalStateException();
        }
    }

    private void readEncryptionData(DecoderInputBuffer decoderInputBuffer, SampleExtrasHolder sampleExtrasHolder) {
        int readUnsignedShort;
        int[] iArr;
        int[] iArr2;
        CryptoData cryptoData;
        int i;
        DecoderInputBuffer decoderInputBuffer2 = decoderInputBuffer;
        SampleExtrasHolder sampleExtrasHolder2 = sampleExtrasHolder;
        long j = sampleExtrasHolder2.offset;
        this.scratch.reset(1);
        readData(j, this.scratch.data, 1);
        j++;
        int i2 = 0;
        byte b = this.scratch.data[0];
        Object obj = (b & 128) != 0 ? 1 : null;
        int i3 = b & 127;
        if (decoderInputBuffer2.cryptoInfo.iv == null) {
            decoderInputBuffer2.cryptoInfo.iv = new byte[16];
        }
        readData(j, decoderInputBuffer2.cryptoInfo.iv, i3);
        j += (long) i3;
        if (obj != null) {
            r0.scratch.reset(2);
            readData(j, r0.scratch.data, 2);
            j += 2;
            readUnsignedShort = r0.scratch.readUnsignedShort();
        } else {
            readUnsignedShort = 1;
        }
        int[] iArr3 = decoderInputBuffer2.cryptoInfo.numBytesOfClearData;
        if (iArr3 != null) {
            if (iArr3.length < readUnsignedShort) {
            }
            iArr = iArr3;
            iArr3 = decoderInputBuffer2.cryptoInfo.numBytesOfEncryptedData;
            if (iArr3 != null) {
                if (iArr3.length < readUnsignedShort) {
                }
                iArr2 = iArr3;
                if (obj == null) {
                    i3 = readUnsignedShort * 6;
                    r0.scratch.reset(i3);
                    readData(j, r0.scratch.data, i3);
                    j += (long) i3;
                    r0.scratch.setPosition(0);
                    while (i2 < readUnsignedShort) {
                        iArr[i2] = r0.scratch.readUnsignedShort();
                        iArr2[i2] = r0.scratch.readUnsignedIntToInt();
                        i2++;
                    }
                } else {
                    iArr[0] = 0;
                    iArr2[0] = sampleExtrasHolder2.size - ((int) (j - sampleExtrasHolder2.offset));
                }
                cryptoData = sampleExtrasHolder2.cryptoData;
                decoderInputBuffer2.cryptoInfo.set(readUnsignedShort, iArr, iArr2, cryptoData.encryptionKey, decoderInputBuffer2.cryptoInfo.iv, cryptoData.cryptoMode, cryptoData.encryptedBlocks, cryptoData.clearBlocks);
                i = (int) (j - sampleExtrasHolder2.offset);
                sampleExtrasHolder2.offset += (long) i;
                sampleExtrasHolder2.size -= i;
            }
            iArr3 = new int[readUnsignedShort];
            iArr2 = iArr3;
            if (obj == null) {
                iArr[0] = 0;
                iArr2[0] = sampleExtrasHolder2.size - ((int) (j - sampleExtrasHolder2.offset));
            } else {
                i3 = readUnsignedShort * 6;
                r0.scratch.reset(i3);
                readData(j, r0.scratch.data, i3);
                j += (long) i3;
                r0.scratch.setPosition(0);
                while (i2 < readUnsignedShort) {
                    iArr[i2] = r0.scratch.readUnsignedShort();
                    iArr2[i2] = r0.scratch.readUnsignedIntToInt();
                    i2++;
                }
            }
            cryptoData = sampleExtrasHolder2.cryptoData;
            decoderInputBuffer2.cryptoInfo.set(readUnsignedShort, iArr, iArr2, cryptoData.encryptionKey, decoderInputBuffer2.cryptoInfo.iv, cryptoData.cryptoMode, cryptoData.encryptedBlocks, cryptoData.clearBlocks);
            i = (int) (j - sampleExtrasHolder2.offset);
            sampleExtrasHolder2.offset += (long) i;
            sampleExtrasHolder2.size -= i;
        }
        iArr3 = new int[readUnsignedShort];
        iArr = iArr3;
        iArr3 = decoderInputBuffer2.cryptoInfo.numBytesOfEncryptedData;
        if (iArr3 != null) {
            if (iArr3.length < readUnsignedShort) {
            }
            iArr2 = iArr3;
            if (obj == null) {
                i3 = readUnsignedShort * 6;
                r0.scratch.reset(i3);
                readData(j, r0.scratch.data, i3);
                j += (long) i3;
                r0.scratch.setPosition(0);
                while (i2 < readUnsignedShort) {
                    iArr[i2] = r0.scratch.readUnsignedShort();
                    iArr2[i2] = r0.scratch.readUnsignedIntToInt();
                    i2++;
                }
            } else {
                iArr[0] = 0;
                iArr2[0] = sampleExtrasHolder2.size - ((int) (j - sampleExtrasHolder2.offset));
            }
            cryptoData = sampleExtrasHolder2.cryptoData;
            decoderInputBuffer2.cryptoInfo.set(readUnsignedShort, iArr, iArr2, cryptoData.encryptionKey, decoderInputBuffer2.cryptoInfo.iv, cryptoData.cryptoMode, cryptoData.encryptedBlocks, cryptoData.clearBlocks);
            i = (int) (j - sampleExtrasHolder2.offset);
            sampleExtrasHolder2.offset += (long) i;
            sampleExtrasHolder2.size -= i;
        }
        iArr3 = new int[readUnsignedShort];
        iArr2 = iArr3;
        if (obj == null) {
            iArr[0] = 0;
            iArr2[0] = sampleExtrasHolder2.size - ((int) (j - sampleExtrasHolder2.offset));
        } else {
            i3 = readUnsignedShort * 6;
            r0.scratch.reset(i3);
            readData(j, r0.scratch.data, i3);
            j += (long) i3;
            r0.scratch.setPosition(0);
            while (i2 < readUnsignedShort) {
                iArr[i2] = r0.scratch.readUnsignedShort();
                iArr2[i2] = r0.scratch.readUnsignedIntToInt();
                i2++;
            }
        }
        cryptoData = sampleExtrasHolder2.cryptoData;
        decoderInputBuffer2.cryptoInfo.set(readUnsignedShort, iArr, iArr2, cryptoData.encryptionKey, decoderInputBuffer2.cryptoInfo.iv, cryptoData.cryptoMode, cryptoData.encryptedBlocks, cryptoData.clearBlocks);
        i = (int) (j - sampleExtrasHolder2.offset);
        sampleExtrasHolder2.offset += (long) i;
        sampleExtrasHolder2.size -= i;
    }

    private void readData(long j, ByteBuffer byteBuffer, int i) {
        advanceReadTo(j);
        while (i > 0) {
            int min = Math.min(i, (int) (this.readAllocationNode.endPosition - j));
            byteBuffer.put(this.readAllocationNode.allocation.data, this.readAllocationNode.translateOffset(j), min);
            i -= min;
            j += (long) min;
            if (j == this.readAllocationNode.endPosition) {
                this.readAllocationNode = this.readAllocationNode.next;
            }
        }
    }

    private void readData(long j, byte[] bArr, int i) {
        advanceReadTo(j);
        long j2 = j;
        j = i;
        while (j > null) {
            int min = Math.min(j, (int) (this.readAllocationNode.endPosition - j2));
            System.arraycopy(this.readAllocationNode.allocation.data, this.readAllocationNode.translateOffset(j2), bArr, i - j, min);
            j -= min;
            j2 += (long) min;
            if (j2 == this.readAllocationNode.endPosition) {
                this.readAllocationNode = this.readAllocationNode.next;
            }
        }
    }

    private void advanceReadTo(long j) {
        while (j >= this.readAllocationNode.endPosition) {
            this.readAllocationNode = this.readAllocationNode.next;
        }
    }

    private void discardDownstreamTo(long j) {
        if (j != -1) {
            while (j >= this.firstAllocationNode.endPosition) {
                this.allocator.release(this.firstAllocationNode.allocation);
                this.firstAllocationNode = this.firstAllocationNode.clear();
            }
            if (this.readAllocationNode.startPosition < this.firstAllocationNode.startPosition) {
                this.readAllocationNode = this.firstAllocationNode;
            }
        }
    }

    public void setUpstreamFormatChangeListener(UpstreamFormatChangedListener upstreamFormatChangedListener) {
        this.upstreamFormatChangeListener = upstreamFormatChangedListener;
    }

    public void setSampleOffsetUs(long j) {
        if (this.sampleOffsetUs != j) {
            this.sampleOffsetUs = j;
            this.pendingFormatAdjustment = 1;
        }
    }

    public void format(Format format) {
        Format adjustedSampleFormat = getAdjustedSampleFormat(format, this.sampleOffsetUs);
        boolean format2 = this.metadataQueue.format(adjustedSampleFormat);
        this.lastUnadjustedFormat = format;
        this.pendingFormatAdjustment = null;
        if (this.upstreamFormatChangeListener != null && format2) {
            this.upstreamFormatChangeListener.onUpstreamFormatChanged(adjustedSampleFormat);
        }
    }

    public int sampleData(ExtractorInput extractorInput, int i, boolean z) throws IOException, InterruptedException {
        extractorInput = extractorInput.read(this.writeAllocationNode.allocation.data, this.writeAllocationNode.translateOffset(this.totalBytesWritten), preAppend(i));
        if (extractorInput != -1) {
            postAppend(extractorInput);
            return extractorInput;
        } else if (z) {
            return -1;
        } else {
            throw new EOFException();
        }
    }

    public void sampleData(ParsableByteArray parsableByteArray, int i) {
        while (i > 0) {
            int preAppend = preAppend(i);
            parsableByteArray.readBytes(this.writeAllocationNode.allocation.data, this.writeAllocationNode.translateOffset(this.totalBytesWritten), preAppend);
            i -= preAppend;
            postAppend(preAppend);
        }
    }

    public void sampleMetadata(long j, int i, int i2, int i3, @Nullable CryptoData cryptoData) {
        if (this.pendingFormatAdjustment) {
            format(r0.lastUnadjustedFormat);
        }
        long j2 = r0.sampleOffsetUs + j;
        if (r0.pendingSplice) {
            if ((i & 1) != 0) {
                if (r0.metadataQueue.attemptSplice(j2)) {
                    r0.pendingSplice = false;
                }
            }
            return;
        }
        int i4 = i2;
        r0.metadataQueue.commitSample(j2, i, (r0.totalBytesWritten - ((long) i4)) - ((long) i3), i4, cryptoData);
    }

    private void clearAllocationNodes(AllocationNode allocationNode) {
        if (allocationNode.wasInitialized) {
            Allocation[] allocationArr = new Allocation[(this.writeAllocationNode.wasInitialized + (((int) (this.writeAllocationNode.startPosition - allocationNode.startPosition)) / this.allocationLength))];
            for (int i = 0; i < allocationArr.length; i++) {
                allocationArr[i] = allocationNode.allocation;
                allocationNode = allocationNode.clear();
            }
            this.allocator.release(allocationArr);
        }
    }

    private int preAppend(int i) {
        if (!this.writeAllocationNode.wasInitialized) {
            this.writeAllocationNode.initialize(this.allocator.allocate(), new AllocationNode(this.writeAllocationNode.endPosition, this.allocationLength));
        }
        return Math.min(i, (int) (this.writeAllocationNode.endPosition - this.totalBytesWritten));
    }

    private void postAppend(int i) {
        this.totalBytesWritten += (long) i;
        if (this.totalBytesWritten == this.writeAllocationNode.endPosition) {
            this.writeAllocationNode = this.writeAllocationNode.next;
        }
    }

    private static Format getAdjustedSampleFormat(Format format, long j) {
        if (format == null) {
            return null;
        }
        if (!(j == 0 || format.subsampleOffsetUs == Long.MAX_VALUE)) {
            format = format.copyWithSubsampleOffsetUs(format.subsampleOffsetUs + j);
        }
        return format;
    }
}
