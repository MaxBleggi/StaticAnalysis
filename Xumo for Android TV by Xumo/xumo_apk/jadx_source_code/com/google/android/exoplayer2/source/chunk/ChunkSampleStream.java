package com.google.android.exoplayer2.source.chunk;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.source.MediaSourceEventListener.EventDispatcher;
import com.google.android.exoplayer2.source.SampleQueue;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.SequenceableLoader;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.upstream.Loader.Callback;
import com.google.android.exoplayer2.upstream.Loader.LoadErrorAction;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChunkSampleStream<T extends ChunkSource> implements SampleStream, SequenceableLoader, Callback<Chunk>, com.google.android.exoplayer2.upstream.Loader.ReleaseCallback {
    private static final String TAG = "ChunkSampleStream";
    private final SequenceableLoader.Callback<ChunkSampleStream<T>> callback;
    private final T chunkSource;
    long decodeOnlyUntilPositionUs;
    private final SampleQueue[] embeddedSampleQueues;
    private final Format[] embeddedTrackFormats;
    private final int[] embeddedTrackTypes;
    private final boolean[] embeddedTracksSelected;
    private final EventDispatcher eventDispatcher;
    private long lastSeekPositionUs;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private final Loader loader;
    boolean loadingFinished;
    private final BaseMediaChunkOutput mediaChunkOutput;
    private final ArrayList<BaseMediaChunk> mediaChunks;
    private final ChunkHolder nextChunkHolder;
    private int nextNotifyPrimaryFormatMediaChunkIndex;
    private long pendingResetPositionUs;
    private Format primaryDownstreamTrackFormat;
    private final SampleQueue primarySampleQueue;
    public final int primaryTrackType;
    private final List<BaseMediaChunk> readOnlyMediaChunks;
    @Nullable
    private ReleaseCallback<T> releaseCallback;

    public interface ReleaseCallback<T extends ChunkSource> {
        void onSampleStreamReleased(ChunkSampleStream<T> chunkSampleStream);
    }

    public final class EmbeddedSampleStream implements SampleStream {
        private final int index;
        private boolean notifiedDownstreamFormat;
        public final ChunkSampleStream<T> parent;
        private final SampleQueue sampleQueue;

        public void maybeThrowError() throws IOException {
        }

        public EmbeddedSampleStream(ChunkSampleStream<T> chunkSampleStream, SampleQueue sampleQueue, int i) {
            this.parent = chunkSampleStream;
            this.sampleQueue = sampleQueue;
            this.index = i;
        }

        public boolean isReady() {
            if (!ChunkSampleStream.this.loadingFinished) {
                if (ChunkSampleStream.this.isPendingReset() || !this.sampleQueue.hasNextSample()) {
                    return false;
                }
            }
            return true;
        }

        public int skipData(long j) {
            int i = 0;
            if (ChunkSampleStream.this.isPendingReset()) {
                return 0;
            }
            maybeNotifyDownstreamFormat();
            if (!ChunkSampleStream.this.loadingFinished || j <= this.sampleQueue.getLargestQueuedTimestampUs()) {
                j = this.sampleQueue.advanceTo(j, true, true);
                if (j != -1) {
                    i = j;
                }
            } else {
                i = this.sampleQueue.advanceToEnd();
            }
            return i;
        }

        public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, boolean z) {
            if (ChunkSampleStream.this.isPendingReset()) {
                return -3;
            }
            maybeNotifyDownstreamFormat();
            return this.sampleQueue.read(formatHolder, decoderInputBuffer, z, ChunkSampleStream.this.loadingFinished, ChunkSampleStream.this.decodeOnlyUntilPositionUs);
        }

        public void release() {
            Assertions.checkState(ChunkSampleStream.this.embeddedTracksSelected[this.index]);
            ChunkSampleStream.this.embeddedTracksSelected[this.index] = false;
        }

        private void maybeNotifyDownstreamFormat() {
            if (!this.notifiedDownstreamFormat) {
                ChunkSampleStream.this.eventDispatcher.downstreamFormatChanged(ChunkSampleStream.this.embeddedTrackTypes[this.index], ChunkSampleStream.this.embeddedTrackFormats[this.index], 0, null, ChunkSampleStream.this.lastSeekPositionUs);
                this.notifiedDownstreamFormat = true;
            }
        }
    }

    @Deprecated
    public ChunkSampleStream(int i, int[] iArr, Format[] formatArr, T t, SequenceableLoader.Callback<ChunkSampleStream<T>> callback, Allocator allocator, long j, int i2, EventDispatcher eventDispatcher) {
        this(i, iArr, formatArr, (ChunkSource) t, (SequenceableLoader.Callback) callback, allocator, j, new DefaultLoadErrorHandlingPolicy(i2), eventDispatcher);
    }

    public ChunkSampleStream(int i, int[] iArr, Format[] formatArr, T t, SequenceableLoader.Callback<ChunkSampleStream<T>> callback, Allocator allocator, long j, LoadErrorHandlingPolicy loadErrorHandlingPolicy, EventDispatcher eventDispatcher) {
        this.primaryTrackType = i;
        this.embeddedTrackTypes = iArr;
        this.embeddedTrackFormats = formatArr;
        this.chunkSource = t;
        this.callback = callback;
        this.eventDispatcher = eventDispatcher;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
        this.loader = new Loader("Loader:ChunkSampleStream");
        this.nextChunkHolder = new ChunkHolder();
        this.mediaChunks = new ArrayList();
        this.readOnlyMediaChunks = Collections.unmodifiableList(this.mediaChunks);
        formatArr = null;
        if (iArr == null) {
            t = null;
        } else {
            t = iArr.length;
        }
        this.embeddedSampleQueues = new SampleQueue[t];
        this.embeddedTracksSelected = new boolean[t];
        callback = t + 1;
        loadErrorHandlingPolicy = new int[callback];
        callback = new SampleQueue[callback];
        this.primarySampleQueue = new SampleQueue(allocator);
        loadErrorHandlingPolicy[0] = i;
        callback[0] = this.primarySampleQueue;
        while (formatArr < t) {
            i = new SampleQueue(allocator);
            this.embeddedSampleQueues[formatArr] = i;
            eventDispatcher = formatArr + 1;
            callback[eventDispatcher] = i;
            loadErrorHandlingPolicy[eventDispatcher] = iArr[formatArr];
            formatArr = eventDispatcher;
        }
        this.mediaChunkOutput = new BaseMediaChunkOutput(loadErrorHandlingPolicy, callback);
        this.pendingResetPositionUs = j;
        this.lastSeekPositionUs = j;
    }

    public void discardBuffer(long j, boolean z) {
        if (!isPendingReset()) {
            int firstIndex = this.primarySampleQueue.getFirstIndex();
            this.primarySampleQueue.discardTo(j, z, true);
            j = this.primarySampleQueue.getFirstIndex();
            if (j > firstIndex) {
                long firstTimestampUs = this.primarySampleQueue.getFirstTimestampUs();
                for (int i = 0; i < this.embeddedSampleQueues.length; i++) {
                    this.embeddedSampleQueues[i].discardTo(firstTimestampUs, z, this.embeddedTracksSelected[i]);
                }
            }
            discardDownstreamMediaChunks(j);
        }
    }

    public EmbeddedSampleStream selectEmbeddedTrack(long j, int i) {
        for (int i2 = 0; i2 < this.embeddedSampleQueues.length; i2++) {
            if (this.embeddedTrackTypes[i2] == i) {
                Assertions.checkState(this.embeddedTracksSelected[i2] ^ 1);
                this.embeddedTracksSelected[i2] = 1;
                this.embeddedSampleQueues[i2].rewind();
                this.embeddedSampleQueues[i2].advanceTo(j, true, true);
                return new EmbeddedSampleStream(this, this.embeddedSampleQueues[i2], i2);
            }
        }
        throw new IllegalStateException();
    }

    public T getChunkSource() {
        return this.chunkSource;
    }

    public long getBufferedPositionUs() {
        if (this.loadingFinished) {
            return Long.MIN_VALUE;
        }
        if (isPendingReset()) {
            return this.pendingResetPositionUs;
        }
        long j = this.lastSeekPositionUs;
        BaseMediaChunk lastMediaChunk = getLastMediaChunk();
        if (!lastMediaChunk.isLoadCompleted()) {
            lastMediaChunk = this.mediaChunks.size() > 1 ? (BaseMediaChunk) this.mediaChunks.get(this.mediaChunks.size() - 2) : null;
        }
        if (lastMediaChunk != null) {
            j = Math.max(j, lastMediaChunk.endTimeUs);
        }
        return Math.max(j, this.primarySampleQueue.getLargestQueuedTimestampUs());
    }

    public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
        return this.chunkSource.getAdjustedSeekPositionUs(j, seekParameters);
    }

    public void seekToUs(long j) {
        this.lastSeekPositionUs = j;
        if (isPendingReset()) {
            this.pendingResetPositionUs = j;
            return;
        }
        boolean readPosition;
        BaseMediaChunk baseMediaChunk = null;
        int i = 0;
        int i2 = 0;
        while (i2 < this.mediaChunks.size()) {
            BaseMediaChunk baseMediaChunk2 = (BaseMediaChunk) this.mediaChunks.get(i2);
            long j2 = baseMediaChunk2.startTimeUs;
            if (j2 == j && baseMediaChunk2.clippedStartTimeUs == C.TIME_UNSET) {
                baseMediaChunk = baseMediaChunk2;
                break;
            } else if (j2 > j) {
                break;
            } else {
                i2++;
            }
        }
        this.primarySampleQueue.rewind();
        if (baseMediaChunk != null) {
            readPosition = this.primarySampleQueue.setReadPosition(baseMediaChunk.getFirstSampleIndex(0));
            this.decodeOnlyUntilPositionUs = Long.MIN_VALUE;
        } else {
            readPosition = this.primarySampleQueue.advanceTo(j, true, (j > getNextLoadPositionUs() ? 1 : (j == getNextLoadPositionUs() ? 0 : -1)) < 0) != -1;
            this.decodeOnlyUntilPositionUs = this.lastSeekPositionUs;
        }
        if (readPosition) {
            this.nextNotifyPrimaryFormatMediaChunkIndex = primarySampleIndexToMediaChunkIndex(this.primarySampleQueue.getReadIndex(), 0);
            for (SampleQueue sampleQueue : this.embeddedSampleQueues) {
                sampleQueue.rewind();
                sampleQueue.advanceTo(j, true, false);
            }
        } else {
            this.pendingResetPositionUs = j;
            this.loadingFinished = false;
            this.mediaChunks.clear();
            this.nextNotifyPrimaryFormatMediaChunkIndex = 0;
            if (this.loader.isLoading() != null) {
                this.loader.cancelLoading();
            } else {
                this.primarySampleQueue.reset();
                j = this.embeddedSampleQueues;
                int length = j.length;
                while (i < length) {
                    j[i].reset();
                    i++;
                }
            }
        }
    }

    public void release() {
        release(null);
    }

    public void release(@Nullable ReleaseCallback<T> releaseCallback) {
        this.releaseCallback = releaseCallback;
        this.primarySampleQueue.discardToEnd();
        for (SampleQueue discardToEnd : this.embeddedSampleQueues) {
            discardToEnd.discardToEnd();
        }
        this.loader.release(this);
    }

    public void onLoaderReleased() {
        this.primarySampleQueue.reset();
        for (SampleQueue reset : this.embeddedSampleQueues) {
            reset.reset();
        }
        if (this.releaseCallback != null) {
            this.releaseCallback.onSampleStreamReleased(this);
        }
    }

    public boolean isReady() {
        if (!this.loadingFinished) {
            if (isPendingReset() || !this.primarySampleQueue.hasNextSample()) {
                return false;
            }
        }
        return true;
    }

    public void maybeThrowError() throws IOException {
        this.loader.maybeThrowError();
        if (!this.loader.isLoading()) {
            this.chunkSource.maybeThrowError();
        }
    }

    public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, boolean z) {
        if (isPendingReset()) {
            return -3;
        }
        maybeNotifyPrimaryTrackFormatChanged();
        return this.primarySampleQueue.read(formatHolder, decoderInputBuffer, z, this.loadingFinished, this.decodeOnlyUntilPositionUs);
    }

    public int skipData(long j) {
        int i = 0;
        if (isPendingReset()) {
            return 0;
        }
        if (!this.loadingFinished || j <= this.primarySampleQueue.getLargestQueuedTimestampUs()) {
            j = this.primarySampleQueue.advanceTo(j, true, true);
            if (j != -1) {
                i = j;
            }
        } else {
            i = this.primarySampleQueue.advanceToEnd();
        }
        maybeNotifyPrimaryTrackFormatChanged();
        return i;
    }

    public void onLoadCompleted(Chunk chunk, long j, long j2) {
        Chunk chunk2 = chunk;
        long j3 = j;
        long j4 = j2;
        this.chunkSource.onChunkLoadCompleted(chunk2);
        this.eventDispatcher.loadCompleted(chunk2.dataSpec, chunk.getUri(), chunk.getResponseHeaders(), chunk2.type, this.primaryTrackType, chunk2.trackFormat, chunk2.trackSelectionReason, chunk2.trackSelectionData, chunk2.startTimeUs, chunk2.endTimeUs, j3, j4, chunk.bytesLoaded());
        this.callback.onContinueLoadingRequested(this);
    }

    public void onLoadCanceled(Chunk chunk, long j, long j2, boolean z) {
        Chunk chunk2 = chunk;
        this.eventDispatcher.loadCanceled(chunk2.dataSpec, chunk.getUri(), chunk.getResponseHeaders(), chunk2.type, this.primaryTrackType, chunk2.trackFormat, chunk2.trackSelectionReason, chunk2.trackSelectionData, chunk2.startTimeUs, chunk2.endTimeUs, j, j2, chunk.bytesLoaded());
        if (!z) {
            r0.primarySampleQueue.reset();
            for (SampleQueue reset : r0.embeddedSampleQueues) {
                reset.reset();
            }
            r0.callback.onContinueLoadingRequested(r0);
        }
    }

    public LoadErrorAction onLoadError(Chunk chunk, long j, long j2, IOException iOException, int i) {
        boolean z;
        LoadErrorAction loadErrorAction;
        LoadErrorAction loadErrorAction2;
        boolean isRetry;
        Chunk chunk2 = chunk;
        long bytesLoaded = chunk.bytesLoaded();
        boolean isMediaChunk = isMediaChunk(chunk);
        int size = this.mediaChunks.size() - 1;
        if (bytesLoaded != 0 && isMediaChunk) {
            if (haveReadFromMediaChunk(size)) {
                z = false;
                loadErrorAction = null;
                if (r0.chunkSource.onChunkLoadError(chunk, z, iOException, z ? r0.loadErrorHandlingPolicy.getBlacklistDurationMsFor(chunk2.type, j2, iOException, i) : C.TIME_UNSET)) {
                    if (z) {
                        Log.w(TAG, "Ignoring attempt to cancel non-cancelable load.");
                    } else {
                        loadErrorAction = Loader.DONT_RETRY;
                        if (isMediaChunk) {
                            Assertions.checkState(discardUpstreamMediaChunksFromIndex(size) != chunk2);
                            if (r0.mediaChunks.isEmpty()) {
                                r0.pendingResetPositionUs = r0.lastSeekPositionUs;
                            }
                        }
                    }
                }
                if (loadErrorAction == null) {
                    long retryDelayMsFor = r0.loadErrorHandlingPolicy.getRetryDelayMsFor(chunk2.type, j2, iOException, i);
                    loadErrorAction = retryDelayMsFor == C.TIME_UNSET ? Loader.createRetryAction(false, retryDelayMsFor) : Loader.DONT_RETRY_FATAL;
                }
                loadErrorAction2 = loadErrorAction;
                isRetry = loadErrorAction2.isRetry() ^ 1;
                r0.eventDispatcher.loadError(chunk2.dataSpec, chunk.getUri(), chunk.getResponseHeaders(), chunk2.type, r0.primaryTrackType, chunk2.trackFormat, chunk2.trackSelectionReason, chunk2.trackSelectionData, chunk2.startTimeUs, chunk2.endTimeUs, j, j2, bytesLoaded, iOException, isRetry);
                if (isRetry) {
                    r0.callback.onContinueLoadingRequested(r0);
                }
                return loadErrorAction2;
            }
        }
        z = true;
        if (z) {
        }
        loadErrorAction = null;
        if (r0.chunkSource.onChunkLoadError(chunk, z, iOException, z ? r0.loadErrorHandlingPolicy.getBlacklistDurationMsFor(chunk2.type, j2, iOException, i) : C.TIME_UNSET)) {
            if (z) {
                Log.w(TAG, "Ignoring attempt to cancel non-cancelable load.");
            } else {
                loadErrorAction = Loader.DONT_RETRY;
                if (isMediaChunk) {
                    if (discardUpstreamMediaChunksFromIndex(size) != chunk2) {
                    }
                    Assertions.checkState(discardUpstreamMediaChunksFromIndex(size) != chunk2);
                    if (r0.mediaChunks.isEmpty()) {
                        r0.pendingResetPositionUs = r0.lastSeekPositionUs;
                    }
                }
            }
        }
        if (loadErrorAction == null) {
            long retryDelayMsFor2 = r0.loadErrorHandlingPolicy.getRetryDelayMsFor(chunk2.type, j2, iOException, i);
            if (retryDelayMsFor2 == C.TIME_UNSET) {
            }
            loadErrorAction = retryDelayMsFor2 == C.TIME_UNSET ? Loader.createRetryAction(false, retryDelayMsFor2) : Loader.DONT_RETRY_FATAL;
        }
        loadErrorAction2 = loadErrorAction;
        isRetry = loadErrorAction2.isRetry() ^ 1;
        r0.eventDispatcher.loadError(chunk2.dataSpec, chunk.getUri(), chunk.getResponseHeaders(), chunk2.type, r0.primaryTrackType, chunk2.trackFormat, chunk2.trackSelectionReason, chunk2.trackSelectionData, chunk2.startTimeUs, chunk2.endTimeUs, j, j2, bytesLoaded, iOException, isRetry);
        if (isRetry) {
            r0.callback.onContinueLoadingRequested(r0);
        }
        return loadErrorAction2;
    }

    public boolean continueLoading(long j) {
        boolean z = false;
        if (!this.loadingFinished) {
            if (!r0.loader.isLoading()) {
                List emptyList;
                long j2;
                boolean isPendingReset = isPendingReset();
                if (isPendingReset) {
                    emptyList = Collections.emptyList();
                    j2 = r0.pendingResetPositionUs;
                } else {
                    emptyList = r0.readOnlyMediaChunks;
                    j2 = getLastMediaChunk().endTimeUs;
                }
                r0.chunkSource.getNextChunk(j, j2, emptyList, r0.nextChunkHolder);
                boolean z2 = r0.nextChunkHolder.endOfStream;
                Chunk chunk = r0.nextChunkHolder.chunk;
                r0.nextChunkHolder.clear();
                if (z2) {
                    r0.pendingResetPositionUs = C.TIME_UNSET;
                    r0.loadingFinished = true;
                    return true;
                } else if (chunk == null) {
                    return false;
                } else {
                    if (isMediaChunk(chunk)) {
                        BaseMediaChunk baseMediaChunk = (BaseMediaChunk) chunk;
                        if (isPendingReset) {
                            long j3;
                            if (baseMediaChunk.startTimeUs == r0.pendingResetPositionUs) {
                                z = true;
                            }
                            if (z) {
                                j3 = Long.MIN_VALUE;
                            } else {
                                j3 = r0.pendingResetPositionUs;
                            }
                            r0.decodeOnlyUntilPositionUs = j3;
                            r0.pendingResetPositionUs = C.TIME_UNSET;
                        }
                        baseMediaChunk.init(r0.mediaChunkOutput);
                        r0.mediaChunks.add(baseMediaChunk);
                    }
                    long startLoading = r0.loader.startLoading(chunk, r0, r0.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(chunk.type));
                    r0.eventDispatcher.loadStarted(chunk.dataSpec, chunk.type, r0.primaryTrackType, chunk.trackFormat, chunk.trackSelectionReason, chunk.trackSelectionData, chunk.startTimeUs, chunk.endTimeUs, startLoading);
                    return true;
                }
            }
        }
        return false;
    }

    public long getNextLoadPositionUs() {
        if (isPendingReset()) {
            return this.pendingResetPositionUs;
        }
        return this.loadingFinished ? Long.MIN_VALUE : getLastMediaChunk().endTimeUs;
    }

    public void reevaluateBuffer(long j) {
        if (!this.loader.isLoading()) {
            if (!isPendingReset()) {
                int size = this.mediaChunks.size();
                j = this.chunkSource.getPreferredQueueSize(j, this.readOnlyMediaChunks);
                if (size > j) {
                    while (j < size) {
                        if (!haveReadFromMediaChunk(j)) {
                            break;
                        }
                        j++;
                    }
                    j = size;
                    if (j != size) {
                        long j2 = getLastMediaChunk().endTimeUs;
                        j = discardUpstreamMediaChunksFromIndex(j);
                        if (this.mediaChunks.isEmpty()) {
                            this.pendingResetPositionUs = this.lastSeekPositionUs;
                        }
                        this.loadingFinished = false;
                        this.eventDispatcher.upstreamDiscarded(this.primaryTrackType, j.startTimeUs, j2);
                    }
                }
            }
        }
    }

    private boolean isMediaChunk(Chunk chunk) {
        return chunk instanceof BaseMediaChunk;
    }

    private boolean haveReadFromMediaChunk(int i) {
        BaseMediaChunk baseMediaChunk = (BaseMediaChunk) this.mediaChunks.get(i);
        if (this.primarySampleQueue.getReadIndex() > baseMediaChunk.getFirstSampleIndex(0)) {
            return true;
        }
        int i2 = 0;
        while (i2 < this.embeddedSampleQueues.length) {
            int readIndex = this.embeddedSampleQueues[i2].getReadIndex();
            i2++;
            if (readIndex > baseMediaChunk.getFirstSampleIndex(i2)) {
                return true;
            }
        }
        return false;
    }

    boolean isPendingReset() {
        return this.pendingResetPositionUs != C.TIME_UNSET;
    }

    private void discardDownstreamMediaChunks(int i) {
        i = Math.min(primarySampleIndexToMediaChunkIndex(i, 0), this.nextNotifyPrimaryFormatMediaChunkIndex);
        if (i > 0) {
            Util.removeRange(this.mediaChunks, 0, i);
            this.nextNotifyPrimaryFormatMediaChunkIndex -= i;
        }
    }

    private void maybeNotifyPrimaryTrackFormatChanged() {
        int primarySampleIndexToMediaChunkIndex = primarySampleIndexToMediaChunkIndex(this.primarySampleQueue.getReadIndex(), this.nextNotifyPrimaryFormatMediaChunkIndex - 1);
        while (this.nextNotifyPrimaryFormatMediaChunkIndex <= primarySampleIndexToMediaChunkIndex) {
            int i = this.nextNotifyPrimaryFormatMediaChunkIndex;
            this.nextNotifyPrimaryFormatMediaChunkIndex = i + 1;
            maybeNotifyPrimaryTrackFormatChanged(i);
        }
    }

    private void maybeNotifyPrimaryTrackFormatChanged(int i) {
        BaseMediaChunk baseMediaChunk = (BaseMediaChunk) this.mediaChunks.get(i);
        Format format = baseMediaChunk.trackFormat;
        if (!format.equals(this.primaryDownstreamTrackFormat)) {
            this.eventDispatcher.downstreamFormatChanged(this.primaryTrackType, format, baseMediaChunk.trackSelectionReason, baseMediaChunk.trackSelectionData, baseMediaChunk.startTimeUs);
        }
        this.primaryDownstreamTrackFormat = format;
    }

    private int primarySampleIndexToMediaChunkIndex(int i, int i2) {
        do {
            i2++;
            if (i2 >= this.mediaChunks.size()) {
                return this.mediaChunks.size() - 1;
            }
        } while (((BaseMediaChunk) this.mediaChunks.get(i2)).getFirstSampleIndex(0) <= i);
        return i2 - 1;
    }

    private BaseMediaChunk getLastMediaChunk() {
        return (BaseMediaChunk) this.mediaChunks.get(this.mediaChunks.size() - 1);
    }

    private BaseMediaChunk discardUpstreamMediaChunksFromIndex(int i) {
        BaseMediaChunk baseMediaChunk = (BaseMediaChunk) this.mediaChunks.get(i);
        Util.removeRange(this.mediaChunks, i, this.mediaChunks.size());
        this.nextNotifyPrimaryFormatMediaChunkIndex = Math.max(this.nextNotifyPrimaryFormatMediaChunkIndex, this.mediaChunks.size());
        int i2 = 0;
        this.primarySampleQueue.discardUpstreamSamples(baseMediaChunk.getFirstSampleIndex(0));
        while (i2 < this.embeddedSampleQueues.length) {
            i = this.embeddedSampleQueues[i2];
            i2++;
            i.discardUpstreamSamples(baseMediaChunk.getFirstSampleIndex(i2));
        }
        return baseMediaChunk;
    }
}
