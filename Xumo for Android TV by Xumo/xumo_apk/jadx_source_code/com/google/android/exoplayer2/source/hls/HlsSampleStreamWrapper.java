package com.google.android.exoplayer2.source.hls;

import android.os.Handler;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.extractor.DummyTrackOutput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.source.MediaSourceEventListener.EventDispatcher;
import com.google.android.exoplayer2.source.SampleQueue;
import com.google.android.exoplayer2.source.SampleQueue.UpstreamFormatChangedListener;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.SequenceableLoader;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.hls.HlsChunkSource.HlsChunkHolder;
import com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist.HlsUrl;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.upstream.Loader.LoadErrorAction;
import com.google.android.exoplayer2.upstream.Loader.ReleaseCallback;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

final class HlsSampleStreamWrapper implements com.google.android.exoplayer2.upstream.Loader.Callback<Chunk>, ReleaseCallback, SequenceableLoader, ExtractorOutput, UpstreamFormatChangedListener {
    public static final int SAMPLE_QUEUE_INDEX_NO_MAPPING_FATAL = -2;
    public static final int SAMPLE_QUEUE_INDEX_NO_MAPPING_NON_FATAL = -3;
    public static final int SAMPLE_QUEUE_INDEX_PENDING = -1;
    private static final String TAG = "HlsSampleStreamWrapper";
    private final Allocator allocator;
    private int audioSampleQueueIndex = -1;
    private boolean audioSampleQueueMappingDone;
    private final Callback callback;
    private final HlsChunkSource chunkSource;
    private int chunkUid;
    private Format downstreamTrackFormat;
    private int enabledTrackGroupCount;
    private final EventDispatcher eventDispatcher;
    private final Handler handler = new Handler();
    private boolean haveAudioVideoSampleQueues;
    private final ArrayList<HlsSampleStream> hlsSampleStreams = new ArrayList();
    private long lastSeekPositionUs;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private final Loader loader = new Loader("Loader:HlsSampleStreamWrapper");
    private boolean loadingFinished;
    private final Runnable maybeFinishPrepareRunnable = new -$$Lambda$HlsSampleStreamWrapper$8JyeEr0irIOShv9LlAxAmgzl5vY();
    private final ArrayList<HlsMediaChunk> mediaChunks = new ArrayList();
    private final Format muxedAudioFormat;
    private final HlsChunkHolder nextChunkHolder = new HlsChunkHolder();
    private final Runnable onTracksEndedRunnable = new -$$Lambda$HlsSampleStreamWrapper$afhkI3tagC_-MAOTh7FzBWzQsno();
    private TrackGroupArray optionalTrackGroups;
    private long pendingResetPositionUs;
    private boolean pendingResetUpstreamFormats;
    private boolean prepared;
    private int primarySampleQueueIndex;
    private int primarySampleQueueType;
    private int primaryTrackGroupIndex;
    private final List<HlsMediaChunk> readOnlyMediaChunks = Collections.unmodifiableList(this.mediaChunks);
    private boolean released;
    private long sampleOffsetUs;
    private boolean[] sampleQueueIsAudioVideoFlags = new boolean[0];
    private int[] sampleQueueTrackIds = new int[0];
    private SampleQueue[] sampleQueues = new SampleQueue[0];
    private boolean sampleQueuesBuilt;
    private boolean[] sampleQueuesEnabledStates = new boolean[0];
    private boolean seenFirstTrackSelection;
    private int[] trackGroupToSampleQueueIndex;
    private TrackGroupArray trackGroups;
    private final int trackType;
    private boolean tracksEnded;
    private Format upstreamTrackFormat;
    private int videoSampleQueueIndex = -1;
    private boolean videoSampleQueueMappingDone;

    public interface Callback extends com.google.android.exoplayer2.source.SequenceableLoader.Callback<HlsSampleStreamWrapper> {
        void onPlaylistRefreshRequired(HlsUrl hlsUrl);

        void onPrepared();
    }

    private static int getTrackTypeScore(int i) {
        switch (i) {
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 1;
            default:
                return 0;
        }
    }

    public void reevaluateBuffer(long j) {
    }

    public void seekMap(SeekMap seekMap) {
    }

    public HlsSampleStreamWrapper(int i, Callback callback, HlsChunkSource hlsChunkSource, Allocator allocator, long j, Format format, LoadErrorHandlingPolicy loadErrorHandlingPolicy, EventDispatcher eventDispatcher) {
        this.trackType = i;
        this.callback = callback;
        this.chunkSource = hlsChunkSource;
        this.allocator = allocator;
        this.muxedAudioFormat = format;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
        this.eventDispatcher = eventDispatcher;
        this.lastSeekPositionUs = j;
        this.pendingResetPositionUs = j;
    }

    public void continuePreparing() {
        if (!this.prepared) {
            continueLoading(this.lastSeekPositionUs);
        }
    }

    public void prepareWithMasterPlaylistInfo(TrackGroupArray trackGroupArray, int i, TrackGroupArray trackGroupArray2) {
        this.prepared = true;
        this.trackGroups = trackGroupArray;
        this.optionalTrackGroups = trackGroupArray2;
        this.primaryTrackGroupIndex = i;
        this.callback.onPrepared();
    }

    public void maybeThrowPrepareError() throws IOException {
        maybeThrowError();
    }

    public TrackGroupArray getTrackGroups() {
        return this.trackGroups;
    }

    public int bindSampleQueueToSampleStream(int i) {
        int i2 = this.trackGroupToSampleQueueIndex[i];
        int i3 = -2;
        if (i2 == -1) {
            if (this.optionalTrackGroups.indexOf(this.trackGroups.get(i)) != -1) {
                i3 = -3;
            }
            return i3;
        } else if (this.sampleQueuesEnabledStates[i2] != 0) {
            return -2;
        } else {
            this.sampleQueuesEnabledStates[i2] = true;
            return i2;
        }
    }

    public void unbindSampleQueue(int i) {
        i = this.trackGroupToSampleQueueIndex[i];
        Assertions.checkState(this.sampleQueuesEnabledStates[i]);
        this.sampleQueuesEnabledStates[i] = false;
    }

    public boolean selectTracks(com.google.android.exoplayer2.trackselection.TrackSelection[] r21, boolean[] r22, com.google.android.exoplayer2.source.SampleStream[] r23, boolean[] r24, long r25, boolean r27) {
        /* JADX: method processing error */
/*
Error: java.lang.IndexOutOfBoundsException: bitIndex < 0: -1
	at java.util.BitSet.get(BitSet.java:623)
	at jadx.core.dex.visitors.CodeShrinker$ArgsInfo.usedArgAssign(CodeShrinker.java:138)
	at jadx.core.dex.visitors.CodeShrinker$ArgsInfo.access$300(CodeShrinker.java:43)
	at jadx.core.dex.visitors.CodeShrinker.canMoveBetweenBlocks(CodeShrinker.java:282)
	at jadx.core.dex.visitors.CodeShrinker.shrinkBlock(CodeShrinker.java:230)
	at jadx.core.dex.visitors.CodeShrinker.shrinkMethod(CodeShrinker.java:38)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.checkArrayForEach(LoopRegionVisitor.java:196)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.checkForIndexedLoop(LoopRegionVisitor.java:119)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.processLoopRegion(LoopRegionVisitor.java:65)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.enterRegion(LoopRegionVisitor.java:52)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:56)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:58)
	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:18)
	at jadx.core.dex.visitors.regions.LoopRegionVisitor.visit(LoopRegionVisitor.java:46)
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
        r20 = this;
        r0 = r20;
        r1 = r21;
        r2 = r23;
        r13 = r25;
        r4 = r0.prepared;
        com.google.android.exoplayer2.util.Assertions.checkState(r4);
        r4 = r0.enabledTrackGroupCount;
        r5 = 0;
    L_0x0010:
        r6 = r1.length;
        r7 = 0;
        r12 = 1;
        if (r5 >= r6) goto L_0x0032;
    L_0x0015:
        r6 = r2[r5];
        if (r6 == 0) goto L_0x002f;
    L_0x0019:
        r6 = r1[r5];
        if (r6 == 0) goto L_0x0021;
    L_0x001d:
        r8 = r22[r5];
        if (r8 != 0) goto L_0x002f;
    L_0x0021:
        r8 = r0.enabledTrackGroupCount;
        r8 = r8 - r12;
        r0.enabledTrackGroupCount = r8;
        r8 = r2[r5];
        r8 = (com.google.android.exoplayer2.source.hls.HlsSampleStream) r8;
        r8.unbindSampleQueue();
        r2[r5] = r7;
    L_0x002f:
        r5 = r5 + 1;
        goto L_0x0010;
    L_0x0032:
        if (r27 != 0) goto L_0x0044;
    L_0x0034:
        r5 = r0.seenFirstTrackSelection;
        if (r5 == 0) goto L_0x003b;
    L_0x0038:
        if (r4 != 0) goto L_0x0042;
    L_0x003a:
        goto L_0x0044;
    L_0x003b:
        r4 = r0.lastSeekPositionUs;
        r6 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1));
        if (r6 == 0) goto L_0x0042;
    L_0x0041:
        goto L_0x0044;
    L_0x0042:
        r4 = 0;
        goto L_0x0045;
    L_0x0044:
        r4 = 1;
    L_0x0045:
        r5 = r0.chunkSource;
        r5 = r5.getTrackSelection();
        r16 = r4;
        r11 = r5;
        r4 = 0;
    L_0x004f:
        r6 = r1.length;
        if (r4 >= r6) goto L_0x00af;
    L_0x0052:
        r6 = r2[r4];
        if (r6 != 0) goto L_0x00ac;
    L_0x0056:
        r6 = r1[r4];
        if (r6 == 0) goto L_0x00ac;
    L_0x005a:
        r6 = r0.enabledTrackGroupCount;
        r6 = r6 + r12;
        r0.enabledTrackGroupCount = r6;
        r6 = r1[r4];
        r8 = r0.trackGroups;
        r9 = r6.getTrackGroup();
        r8 = r8.indexOf(r9);
        r9 = r0.primaryTrackGroupIndex;
        if (r8 != r9) goto L_0x0075;
    L_0x006f:
        r9 = r0.chunkSource;
        r9.selectTracks(r6);
        r11 = r6;
    L_0x0075:
        r6 = new com.google.android.exoplayer2.source.hls.HlsSampleStream;
        r6.<init>(r0, r8);
        r2[r4] = r6;
        r24[r4] = r12;
        r6 = r0.trackGroupToSampleQueueIndex;
        if (r6 == 0) goto L_0x0089;
    L_0x0082:
        r6 = r2[r4];
        r6 = (com.google.android.exoplayer2.source.hls.HlsSampleStream) r6;
        r6.bindSampleQueue();
    L_0x0089:
        r6 = r0.sampleQueuesBuilt;
        if (r6 == 0) goto L_0x00ac;
    L_0x008d:
        if (r16 != 0) goto L_0x00ac;
    L_0x008f:
        r6 = r0.sampleQueues;
        r9 = r0.trackGroupToSampleQueueIndex;
        r8 = r9[r8];
        r6 = r6[r8];
        r6.rewind();
        r8 = r6.advanceTo(r13, r12, r12);
        r9 = -1;
        if (r8 != r9) goto L_0x00aa;
    L_0x00a1:
        r6 = r6.getReadIndex();
        if (r6 == 0) goto L_0x00aa;
    L_0x00a7:
        r16 = 1;
        goto L_0x00ac;
    L_0x00aa:
        r16 = 0;
    L_0x00ac:
        r4 = r4 + 1;
        goto L_0x004f;
    L_0x00af:
        r1 = r0.enabledTrackGroupCount;
        if (r1 != 0) goto L_0x00e5;
    L_0x00b3:
        r1 = r0.chunkSource;
        r1.reset();
        r0.downstreamTrackFormat = r7;
        r1 = r0.mediaChunks;
        r1.clear();
        r1 = r0.loader;
        r1 = r1.isLoading();
        if (r1 == 0) goto L_0x00df;
    L_0x00c7:
        r1 = r0.sampleQueuesBuilt;
        if (r1 == 0) goto L_0x00d9;
    L_0x00cb:
        r1 = r0.sampleQueues;
        r3 = r1.length;
        r15 = 0;
    L_0x00cf:
        if (r15 >= r3) goto L_0x00d9;
    L_0x00d1:
        r4 = r1[r15];
        r4.discardToEnd();
        r15 = r15 + 1;
        goto L_0x00cf;
    L_0x00d9:
        r1 = r0.loader;
        r1.cancelLoading();
        goto L_0x00e2;
    L_0x00df:
        r20.resetSampleQueues();
    L_0x00e2:
        r15 = 1;
        goto L_0x0153;
    L_0x00e5:
        r1 = r0.mediaChunks;
        r1 = r1.isEmpty();
        if (r1 != 0) goto L_0x013e;
    L_0x00ed:
        r1 = com.google.android.exoplayer2.util.Util.areEqual(r11, r5);
        if (r1 != 0) goto L_0x013e;
    L_0x00f3:
        r1 = r0.seenFirstTrackSelection;
        if (r1 != 0) goto L_0x0134;
    L_0x00f7:
        r4 = 0;
        r1 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1));
        if (r1 >= 0) goto L_0x00fe;
    L_0x00fd:
        r4 = -r13;
    L_0x00fe:
        r7 = r4;
        r1 = r20.getLastMediaChunk();
        r4 = r0.chunkSource;
        r17 = r4.createMediaChunkIterators(r1, r13);
        r9 = -9223372036854775807; // 0x8000000000000001 float:1.4E-45 double:-4.9E-324;
        r5 = r0.readOnlyMediaChunks;
        r4 = r11;
        r18 = r5;
        r5 = r25;
        r19 = r11;
        r11 = r18;
        r15 = 1;
        r12 = r17;
        r4.updateSelectedTrack(r5, r7, r9, r11, r12);
        r4 = r0.chunkSource;
        r4 = r4.getTrackGroup();
        r1 = r1.trackFormat;
        r1 = r4.indexOf(r1);
        r4 = r19.getSelectedIndexInTrackGroup();
        if (r4 == r1) goto L_0x0132;
    L_0x0131:
        goto L_0x0135;
    L_0x0132:
        r12 = 0;
        goto L_0x0136;
    L_0x0134:
        r15 = 1;
    L_0x0135:
        r12 = 1;
    L_0x0136:
        if (r12 == 0) goto L_0x013f;
    L_0x0138:
        r0.pendingResetUpstreamFormats = r15;
        r1 = 1;
        r16 = 1;
        goto L_0x0141;
    L_0x013e:
        r15 = 1;
    L_0x013f:
        r1 = r27;
    L_0x0141:
        if (r16 == 0) goto L_0x0153;
    L_0x0143:
        r0.seekToUs(r13, r1);
        r1 = 0;
    L_0x0147:
        r4 = r2.length;
        if (r1 >= r4) goto L_0x0153;
    L_0x014a:
        r4 = r2[r1];
        if (r4 == 0) goto L_0x0150;
    L_0x014e:
        r24[r1] = r15;
    L_0x0150:
        r1 = r1 + 1;
        goto L_0x0147;
    L_0x0153:
        r0.updateSampleStreams(r2);
        r0.seenFirstTrackSelection = r15;
        return r16;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper.selectTracks(com.google.android.exoplayer2.trackselection.TrackSelection[], boolean[], com.google.android.exoplayer2.source.SampleStream[], boolean[], long, boolean):boolean");
    }

    public void discardBuffer(long j, boolean z) {
        if (this.sampleQueuesBuilt) {
            if (!isPendingReset()) {
                int length = this.sampleQueues.length;
                for (int i = 0; i < length; i++) {
                    this.sampleQueues[i].discardTo(j, z, this.sampleQueuesEnabledStates[i]);
                }
            }
        }
    }

    public boolean seekToUs(long j, boolean z) {
        this.lastSeekPositionUs = j;
        if (isPendingReset()) {
            this.pendingResetPositionUs = j;
            return true;
        } else if (this.sampleQueuesBuilt && !z && seekInsideBufferUs(j)) {
            return false;
        } else {
            this.pendingResetPositionUs = j;
            this.loadingFinished = false;
            this.mediaChunks.clear();
            if (this.loader.isLoading() != null) {
                this.loader.cancelLoading();
            } else {
                resetSampleQueues();
            }
            return true;
        }
    }

    public void release() {
        if (this.prepared) {
            for (SampleQueue discardToEnd : this.sampleQueues) {
                discardToEnd.discardToEnd();
            }
        }
        this.loader.release(this);
        this.handler.removeCallbacksAndMessages(null);
        this.released = true;
        this.hlsSampleStreams.clear();
    }

    public void onLoaderReleased() {
        resetSampleQueues();
    }

    public void setIsTimestampMaster(boolean z) {
        this.chunkSource.setIsTimestampMaster(z);
    }

    public boolean onPlaylistError(HlsUrl hlsUrl, long j) {
        return this.chunkSource.onPlaylistError(hlsUrl, j);
    }

    public boolean isReady(int i) {
        if (!this.loadingFinished) {
            if (isPendingReset() || this.sampleQueues[i].hasNextSample() == 0) {
                return false;
            }
        }
        return true;
    }

    public void maybeThrowError() throws IOException {
        this.loader.maybeThrowError();
        this.chunkSource.maybeThrowError();
    }

    public int readData(int i, FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, boolean z) {
        if (isPendingReset()) {
            return -3;
        }
        boolean z2 = false;
        if (!this.mediaChunks.isEmpty()) {
            int i2 = 0;
            while (i2 < this.mediaChunks.size() - 1 && finishedReadingChunk((HlsMediaChunk) this.mediaChunks.get(i2))) {
                i2++;
            }
            Util.removeRange(this.mediaChunks, 0, i2);
            HlsMediaChunk hlsMediaChunk = (HlsMediaChunk) this.mediaChunks.get(0);
            Format format = hlsMediaChunk.trackFormat;
            if (!format.equals(this.downstreamTrackFormat)) {
                this.eventDispatcher.downstreamFormatChanged(this.trackType, format, hlsMediaChunk.trackSelectionReason, hlsMediaChunk.trackSelectionData, hlsMediaChunk.startTimeUs);
            }
            this.downstreamTrackFormat = format;
        }
        decoderInputBuffer = this.sampleQueues[i].read(formatHolder, decoderInputBuffer, z, this.loadingFinished, this.lastSeekPositionUs);
        if (decoderInputBuffer == true && i == this.primarySampleQueueIndex) {
            boolean peekSourceId = this.sampleQueues[i].peekSourceId();
            while (z2 < this.mediaChunks.size() && ((HlsMediaChunk) this.mediaChunks.get(z2)).uid != peekSourceId) {
                z2++;
            }
            formatHolder.format = formatHolder.format.copyWithManifestFormatInfo(z2 < this.mediaChunks.size() ? ((HlsMediaChunk) this.mediaChunks.get(z2)).trackFormat : this.upstreamTrackFormat);
        }
        return decoderInputBuffer;
    }

    public int skipData(int i, long j) {
        if (isPendingReset()) {
            return 0;
        }
        i = this.sampleQueues[i];
        if (this.loadingFinished && j > i.getLargestQueuedTimestampUs()) {
            return i.advanceToEnd();
        }
        i = i.advanceTo(j, true, true);
        if (i == -1) {
            i = 0;
        }
        return i;
    }

    public long getBufferedPositionUs() {
        if (this.loadingFinished) {
            return Long.MIN_VALUE;
        }
        if (isPendingReset()) {
            return this.pendingResetPositionUs;
        }
        long j = this.lastSeekPositionUs;
        HlsMediaChunk lastMediaChunk = getLastMediaChunk();
        if (!lastMediaChunk.isLoadCompleted()) {
            lastMediaChunk = this.mediaChunks.size() > 1 ? (HlsMediaChunk) this.mediaChunks.get(this.mediaChunks.size() - 2) : null;
        }
        if (lastMediaChunk != null) {
            j = Math.max(j, lastMediaChunk.endTimeUs);
        }
        if (this.sampleQueuesBuilt) {
            for (SampleQueue largestQueuedTimestampUs : this.sampleQueues) {
                j = Math.max(j, largestQueuedTimestampUs.getLargestQueuedTimestampUs());
            }
        }
        return j;
    }

    public long getNextLoadPositionUs() {
        if (isPendingReset()) {
            return this.pendingResetPositionUs;
        }
        return this.loadingFinished ? Long.MIN_VALUE : getLastMediaChunk().endTimeUs;
    }

    public boolean continueLoading(long j) {
        if (!this.loadingFinished) {
            if (!r0.loader.isLoading()) {
                List emptyList;
                long j2;
                if (isPendingReset()) {
                    emptyList = Collections.emptyList();
                    j2 = r0.pendingResetPositionUs;
                } else {
                    emptyList = r0.readOnlyMediaChunks;
                    HlsMediaChunk lastMediaChunk = getLastMediaChunk();
                    if (lastMediaChunk.isLoadCompleted()) {
                        j2 = lastMediaChunk.endTimeUs;
                    } else {
                        j2 = Math.max(r0.lastSeekPositionUs, lastMediaChunk.startTimeUs);
                    }
                }
                r0.chunkSource.getNextChunk(j, j2, emptyList, r0.nextChunkHolder);
                boolean z = r0.nextChunkHolder.endOfStream;
                Chunk chunk = r0.nextChunkHolder.chunk;
                HlsUrl hlsUrl = r0.nextChunkHolder.playlist;
                r0.nextChunkHolder.clear();
                if (z) {
                    r0.pendingResetPositionUs = C.TIME_UNSET;
                    r0.loadingFinished = true;
                    return true;
                } else if (chunk == null) {
                    if (hlsUrl != null) {
                        r0.callback.onPlaylistRefreshRequired(hlsUrl);
                    }
                    return false;
                } else {
                    if (isMediaChunk(chunk)) {
                        r0.pendingResetPositionUs = C.TIME_UNSET;
                        HlsMediaChunk hlsMediaChunk = (HlsMediaChunk) chunk;
                        hlsMediaChunk.init(r0);
                        r0.mediaChunks.add(hlsMediaChunk);
                        r0.upstreamTrackFormat = hlsMediaChunk.trackFormat;
                    }
                    long startLoading = r0.loader.startLoading(chunk, r0, r0.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(chunk.type));
                    r0.eventDispatcher.loadStarted(chunk.dataSpec, chunk.type, r0.trackType, chunk.trackFormat, chunk.trackSelectionReason, chunk.trackSelectionData, chunk.startTimeUs, chunk.endTimeUs, startLoading);
                    return true;
                }
            }
        }
        return false;
    }

    public void onLoadCompleted(Chunk chunk, long j, long j2) {
        Chunk chunk2 = chunk;
        long j3 = j;
        long j4 = j2;
        this.chunkSource.onChunkLoadCompleted(chunk2);
        this.eventDispatcher.loadCompleted(chunk2.dataSpec, chunk.getUri(), chunk.getResponseHeaders(), chunk2.type, this.trackType, chunk2.trackFormat, chunk2.trackSelectionReason, chunk2.trackSelectionData, chunk2.startTimeUs, chunk2.endTimeUs, j3, j4, chunk.bytesLoaded());
        if (this.prepared) {
            r0.callback.onContinueLoadingRequested(r0);
        } else {
            continueLoading(r0.lastSeekPositionUs);
        }
    }

    public void onLoadCanceled(Chunk chunk, long j, long j2, boolean z) {
        Chunk chunk2 = chunk;
        this.eventDispatcher.loadCanceled(chunk2.dataSpec, chunk.getUri(), chunk.getResponseHeaders(), chunk2.type, this.trackType, chunk2.trackFormat, chunk2.trackSelectionReason, chunk2.trackSelectionData, chunk2.startTimeUs, chunk2.endTimeUs, j, j2, chunk.bytesLoaded());
        if (!z) {
            resetSampleQueues();
            if (r0.enabledTrackGroupCount > 0) {
                r0.callback.onContinueLoadingRequested(r0);
            }
        }
    }

    public LoadErrorAction onLoadError(Chunk chunk, long j, long j2, IOException iOException, int i) {
        LoadErrorAction loadErrorAction;
        Chunk chunk2 = chunk;
        long bytesLoaded = chunk.bytesLoaded();
        boolean isMediaChunk = isMediaChunk(chunk);
        long blacklistDurationMsFor = this.loadErrorHandlingPolicy.getBlacklistDurationMsFor(chunk2.type, j2, iOException, i);
        boolean z = false;
        boolean maybeBlacklistTrack = blacklistDurationMsFor != C.TIME_UNSET ? r0.chunkSource.maybeBlacklistTrack(chunk2, blacklistDurationMsFor) : false;
        if (maybeBlacklistTrack) {
            if (isMediaChunk && bytesLoaded == 0) {
                if (((HlsMediaChunk) r0.mediaChunks.remove(r0.mediaChunks.size() - 1)) == chunk2) {
                    z = true;
                }
                Assertions.checkState(z);
                if (r0.mediaChunks.isEmpty()) {
                    r0.pendingResetPositionUs = r0.lastSeekPositionUs;
                }
            }
            loadErrorAction = Loader.DONT_RETRY;
        } else {
            long retryDelayMsFor = r0.loadErrorHandlingPolicy.getRetryDelayMsFor(chunk2.type, j2, iOException, i);
            loadErrorAction = retryDelayMsFor != C.TIME_UNSET ? Loader.createRetryAction(false, retryDelayMsFor) : Loader.DONT_RETRY_FATAL;
        }
        LoadErrorAction loadErrorAction2 = loadErrorAction;
        r0.eventDispatcher.loadError(chunk2.dataSpec, chunk.getUri(), chunk.getResponseHeaders(), chunk2.type, r0.trackType, chunk2.trackFormat, chunk2.trackSelectionReason, chunk2.trackSelectionData, chunk2.startTimeUs, chunk2.endTimeUs, j, j2, bytesLoaded, iOException, loadErrorAction2.isRetry() ^ 1);
        if (maybeBlacklistTrack) {
            if (r0.prepared) {
                r0.callback.onContinueLoadingRequested(r0);
            } else {
                continueLoading(r0.lastSeekPositionUs);
            }
        }
        return loadErrorAction2;
    }

    public void init(int i, boolean z, boolean z2) {
        boolean z3 = false;
        if (!z2) {
            this.audioSampleQueueMappingDone = false;
            this.videoSampleQueueMappingDone = false;
        }
        this.chunkUid = i;
        for (SampleQueue sourceId : this.sampleQueues) {
            sourceId.sourceId(i);
        }
        if (z) {
            i = this.sampleQueues;
            z = i.length;
            while (z3 < z) {
                i[z3].splice();
                z3++;
            }
        }
    }

    public TrackOutput track(int i, int i2) {
        int length = this.sampleQueues.length;
        boolean z = false;
        if (i2 == 1) {
            if (this.audioSampleQueueIndex != -1) {
                if (this.audioSampleQueueMappingDone) {
                    if (this.sampleQueueTrackIds[this.audioSampleQueueIndex] == i) {
                        i = this.sampleQueues[this.audioSampleQueueIndex];
                    } else {
                        i = createDummyTrackOutput(i, i2);
                    }
                    return i;
                }
                this.audioSampleQueueMappingDone = true;
                this.sampleQueueTrackIds[this.audioSampleQueueIndex] = i;
                return this.sampleQueues[this.audioSampleQueueIndex];
            } else if (this.tracksEnded) {
                return createDummyTrackOutput(i, i2);
            }
        } else if (i2 != 2) {
            for (int i3 = 0; i3 < length; i3++) {
                if (this.sampleQueueTrackIds[i3] == i) {
                    return this.sampleQueues[i3];
                }
            }
            if (this.tracksEnded) {
                return createDummyTrackOutput(i, i2);
            }
        } else if (this.videoSampleQueueIndex != -1) {
            if (this.videoSampleQueueMappingDone) {
                if (this.sampleQueueTrackIds[this.videoSampleQueueIndex] == i) {
                    i = this.sampleQueues[this.videoSampleQueueIndex];
                } else {
                    i = createDummyTrackOutput(i, i2);
                }
                return i;
            }
            this.videoSampleQueueMappingDone = true;
            this.sampleQueueTrackIds[this.videoSampleQueueIndex] = i;
            return this.sampleQueues[this.videoSampleQueueIndex];
        } else if (this.tracksEnded) {
            return createDummyTrackOutput(i, i2);
        }
        TrackOutput sampleQueue = new SampleQueue(this.allocator);
        sampleQueue.setSampleOffsetUs(this.sampleOffsetUs);
        sampleQueue.sourceId(this.chunkUid);
        sampleQueue.setUpstreamFormatChangeListener(this);
        int i4 = length + 1;
        this.sampleQueueTrackIds = Arrays.copyOf(this.sampleQueueTrackIds, i4);
        this.sampleQueueTrackIds[length] = i;
        this.sampleQueues = (SampleQueue[]) Arrays.copyOf(this.sampleQueues, i4);
        this.sampleQueues[length] = sampleQueue;
        this.sampleQueueIsAudioVideoFlags = Arrays.copyOf(this.sampleQueueIsAudioVideoFlags, i4);
        i = this.sampleQueueIsAudioVideoFlags;
        if (i2 == 1 || i2 == 2) {
            z = true;
        }
        i[length] = z;
        this.haveAudioVideoSampleQueues |= this.sampleQueueIsAudioVideoFlags[length];
        if (i2 == 1) {
            this.audioSampleQueueMappingDone = true;
            this.audioSampleQueueIndex = length;
        } else if (i2 == 2) {
            this.videoSampleQueueMappingDone = true;
            this.videoSampleQueueIndex = length;
        }
        if (getTrackTypeScore(i2) > getTrackTypeScore(this.primarySampleQueueType)) {
            this.primarySampleQueueIndex = length;
            this.primarySampleQueueType = i2;
        }
        this.sampleQueuesEnabledStates = Arrays.copyOf(this.sampleQueuesEnabledStates, i4);
        return sampleQueue;
    }

    public void endTracks() {
        this.tracksEnded = true;
        this.handler.post(this.onTracksEndedRunnable);
    }

    public void onUpstreamFormatChanged(Format format) {
        this.handler.post(this.maybeFinishPrepareRunnable);
    }

    public void setSampleOffsetUs(long j) {
        this.sampleOffsetUs = j;
        for (SampleQueue sampleOffsetUs : this.sampleQueues) {
            sampleOffsetUs.setSampleOffsetUs(j);
        }
    }

    private void updateSampleStreams(SampleStream[] sampleStreamArr) {
        this.hlsSampleStreams.clear();
        for (SampleStream sampleStream : sampleStreamArr) {
            if (sampleStream != null) {
                this.hlsSampleStreams.add((HlsSampleStream) sampleStream);
            }
        }
    }

    private boolean finishedReadingChunk(HlsMediaChunk hlsMediaChunk) {
        hlsMediaChunk = hlsMediaChunk.uid;
        int length = this.sampleQueues.length;
        int i = 0;
        while (i < length) {
            if (this.sampleQueuesEnabledStates[i] && this.sampleQueues[i].peekSourceId() == hlsMediaChunk) {
                return false;
            }
            i++;
        }
        return true;
    }

    private void resetSampleQueues() {
        for (SampleQueue reset : this.sampleQueues) {
            reset.reset(this.pendingResetUpstreamFormats);
        }
        this.pendingResetUpstreamFormats = false;
    }

    private void onTracksEnded() {
        this.sampleQueuesBuilt = true;
        maybeFinishPrepare();
    }

    private void maybeFinishPrepare() {
        if (!this.released && this.trackGroupToSampleQueueIndex == null) {
            if (this.sampleQueuesBuilt) {
                SampleQueue[] sampleQueueArr = this.sampleQueues;
                int length = sampleQueueArr.length;
                int i = 0;
                while (i < length) {
                    if (sampleQueueArr[i].getUpstreamFormat() != null) {
                        i++;
                    } else {
                        return;
                    }
                }
                if (this.trackGroups != null) {
                    mapSampleQueuesToMatchTrackGroups();
                } else {
                    buildTracksFromSampleStreams();
                    this.prepared = true;
                    this.callback.onPrepared();
                }
            }
        }
    }

    private void mapSampleQueuesToMatchTrackGroups() {
        int i = this.trackGroups.length;
        this.trackGroupToSampleQueueIndex = new int[i];
        Arrays.fill(this.trackGroupToSampleQueueIndex, -1);
        for (int i2 = 0; i2 < i; i2++) {
            for (int i3 = 0; i3 < this.sampleQueues.length; i3++) {
                if (formatsMatch(this.sampleQueues[i3].getUpstreamFormat(), this.trackGroups.get(i2).getFormat(0))) {
                    this.trackGroupToSampleQueueIndex[i2] = i3;
                    break;
                }
            }
        }
        Iterator it = this.hlsSampleStreams.iterator();
        while (it.hasNext()) {
            ((HlsSampleStream) it.next()).bindSampleQueue();
        }
    }

    private void buildTracksFromSampleStreams() {
        int length = this.sampleQueues.length;
        boolean z = false;
        int i = 0;
        int i2 = 6;
        int i3 = -1;
        while (true) {
            int i4 = 2;
            if (i >= length) {
                break;
            }
            String str = this.sampleQueues[i].getUpstreamFormat().sampleMimeType;
            if (!MimeTypes.isVideo(str)) {
                i4 = MimeTypes.isAudio(str) ? 1 : MimeTypes.isText(str) ? 3 : 6;
            }
            if (getTrackTypeScore(i4) > getTrackTypeScore(i2)) {
                i3 = i;
                i2 = i4;
            } else if (i4 == i2 && i3 != -1) {
                i3 = -1;
            }
            i++;
        }
        TrackGroup trackGroup = this.chunkSource.getTrackGroup();
        i = trackGroup.length;
        this.primaryTrackGroupIndex = -1;
        this.trackGroupToSampleQueueIndex = new int[length];
        for (int i5 = 0; i5 < length; i5++) {
            this.trackGroupToSampleQueueIndex[i5] = i5;
        }
        TrackGroup[] trackGroupArr = new TrackGroup[length];
        for (int i6 = 0; i6 < length; i6++) {
            Format upstreamFormat = this.sampleQueues[i6].getUpstreamFormat();
            if (i6 == i3) {
                Format[] formatArr = new Format[i];
                if (i == 1) {
                    formatArr[0] = upstreamFormat.copyWithManifestFormatInfo(trackGroup.getFormat(0));
                } else {
                    for (int i7 = 0; i7 < i; i7++) {
                        formatArr[i7] = deriveFormat(trackGroup.getFormat(i7), upstreamFormat, true);
                    }
                }
                trackGroupArr[i6] = new TrackGroup(formatArr);
                this.primaryTrackGroupIndex = i6;
            } else {
                Format format = (i2 == 2 && MimeTypes.isAudio(upstreamFormat.sampleMimeType)) ? this.muxedAudioFormat : null;
                trackGroupArr[i6] = new TrackGroup(deriveFormat(format, upstreamFormat, false));
            }
        }
        this.trackGroups = new TrackGroupArray(trackGroupArr);
        if (this.optionalTrackGroups == null) {
            z = true;
        }
        Assertions.checkState(z);
        this.optionalTrackGroups = TrackGroupArray.EMPTY;
    }

    private HlsMediaChunk getLastMediaChunk() {
        return (HlsMediaChunk) this.mediaChunks.get(this.mediaChunks.size() - 1);
    }

    private boolean isPendingReset() {
        return this.pendingResetPositionUs != C.TIME_UNSET;
    }

    private boolean seekInsideBufferUs(long j) {
        int length = this.sampleQueues.length;
        int i = 0;
        while (true) {
            boolean z = true;
            if (i >= length) {
                return true;
            }
            SampleQueue sampleQueue = this.sampleQueues[i];
            sampleQueue.rewind();
            if (sampleQueue.advanceTo(j, true, false) == -1) {
                z = false;
            }
            if (z || (!this.sampleQueueIsAudioVideoFlags[i] && this.haveAudioVideoSampleQueues)) {
                i++;
            }
        }
        return false;
    }

    private static Format deriveFormat(Format format, Format format2, boolean z) {
        if (format == null) {
            return format2;
        }
        int i = z ? format.bitrate : -1;
        String codecsOfType = Util.getCodecsOfType(format.codecs, MimeTypes.getTrackType(format2.sampleMimeType));
        z = MimeTypes.getMediaMimeType(codecsOfType);
        if (!z) {
            z = format2.sampleMimeType;
        }
        return format2.copyWithContainerInfo(format.id, format.label, z, codecsOfType, i, format.width, format.height, format.selectionFlags, format.language);
    }

    private static boolean isMediaChunk(Chunk chunk) {
        return chunk instanceof HlsMediaChunk;
    }

    private static boolean formatsMatch(Format format, Format format2) {
        String str = format.sampleMimeType;
        String str2 = format2.sampleMimeType;
        int trackType = MimeTypes.getTrackType(str);
        boolean z = false;
        if (trackType != 3) {
            if (trackType == MimeTypes.getTrackType(str2)) {
                z = true;
            }
            return z;
        } else if (!Util.areEqual(str, str2)) {
            return false;
        } else {
            if (!MimeTypes.APPLICATION_CEA608.equals(str)) {
                if (!MimeTypes.APPLICATION_CEA708.equals(str)) {
                    return true;
                }
            }
            if (format.accessibilityChannel == format2.accessibilityChannel) {
                z = true;
            }
            return z;
        }
    }

    private static DummyTrackOutput createDummyTrackOutput(int i, int i2) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unmapped track with id ");
        stringBuilder.append(i);
        stringBuilder.append(" of type ");
        stringBuilder.append(i2);
        Log.w(str, stringBuilder.toString());
        return new DummyTrackOutput();
    }
}
