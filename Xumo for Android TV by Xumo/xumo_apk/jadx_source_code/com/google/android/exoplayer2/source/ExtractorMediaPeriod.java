package com.google.android.exoplayer2.source;

import android.net.Uri;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.extractor.DefaultExtractorInput;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekMap.SeekPoints;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.source.MediaSourceEventListener.EventDispatcher;
import com.google.android.exoplayer2.source.SampleQueue.UpstreamFormatChangedListener;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.upstream.Loader.Callback;
import com.google.android.exoplayer2.upstream.Loader.LoadErrorAction;
import com.google.android.exoplayer2.upstream.Loader.Loadable;
import com.google.android.exoplayer2.upstream.Loader.ReleaseCallback;
import com.google.android.exoplayer2.upstream.StatsDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ConditionVariable;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.Arrays;

final class ExtractorMediaPeriod implements MediaPeriod, ExtractorOutput, Callback<ExtractingLoadable>, ReleaseCallback, UpstreamFormatChangedListener {
    private static final long DEFAULT_LAST_SAMPLE_DURATION_US = 10000;
    private final Allocator allocator;
    @Nullable
    private MediaPeriod.Callback callback;
    private final long continueLoadingCheckIntervalBytes;
    @Nullable
    private final String customCacheKey;
    private final DataSource dataSource;
    private int dataType;
    private long durationUs;
    private int enabledTrackCount;
    private final EventDispatcher eventDispatcher;
    private int extractedSamplesCountAtStartOfLoad;
    private final ExtractorHolder extractorHolder;
    private final Handler handler;
    private boolean haveAudioVideoTracks;
    private long lastSeekPositionUs;
    private long length;
    private final Listener listener;
    private final ConditionVariable loadCondition;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private final Loader loader = new Loader("Loader:ExtractorMediaPeriod");
    private boolean loadingFinished;
    private final Runnable maybeFinishPrepareRunnable;
    private boolean notifiedReadingStarted;
    private boolean notifyDiscontinuity;
    private final Runnable onContinueLoadingRequestedRunnable;
    private boolean pendingDeferredRetry;
    private long pendingResetPositionUs;
    private boolean prepared;
    @Nullable
    private PreparedState preparedState;
    private boolean released;
    private int[] sampleQueueTrackIds;
    private SampleQueue[] sampleQueues;
    private boolean sampleQueuesBuilt;
    @Nullable
    private SeekMap seekMap;
    private boolean seenFirstTrackSelection;
    private final Uri uri;

    private static final class ExtractorHolder {
        @Nullable
        private Extractor extractor;
        private final Extractor[] extractors;

        public ExtractorHolder(Extractor[] extractorArr) {
            this.extractors = extractorArr;
        }

        public com.google.android.exoplayer2.extractor.Extractor selectExtractor(com.google.android.exoplayer2.extractor.ExtractorInput r6, com.google.android.exoplayer2.extractor.ExtractorOutput r7, android.net.Uri r8) throws java.io.IOException, java.lang.InterruptedException {
            /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r5 = this;
            r0 = r5.extractor;
            if (r0 == 0) goto L_0x0007;
        L_0x0004:
            r6 = r5.extractor;
            return r6;
        L_0x0007:
            r0 = r5.extractors;
            r1 = r0.length;
            r2 = 0;
        L_0x000b:
            if (r2 >= r1) goto L_0x0026;
        L_0x000d:
            r3 = r0[r2];
            r4 = r3.sniff(r6);	 Catch:{ EOFException -> 0x0020, all -> 0x001b }
            if (r4 == 0) goto L_0x0020;	 Catch:{ EOFException -> 0x0020, all -> 0x001b }
        L_0x0015:
            r5.extractor = r3;	 Catch:{ EOFException -> 0x0020, all -> 0x001b }
            r6.resetPeekPosition();
            goto L_0x0026;
        L_0x001b:
            r7 = move-exception;
            r6.resetPeekPosition();
            throw r7;
        L_0x0020:
            r6.resetPeekPosition();
            r2 = r2 + 1;
            goto L_0x000b;
        L_0x0026:
            r6 = r5.extractor;
            if (r6 == 0) goto L_0x0032;
        L_0x002a:
            r6 = r5.extractor;
            r6.init(r7);
            r6 = r5.extractor;
            return r6;
        L_0x0032:
            r6 = new com.google.android.exoplayer2.source.UnrecognizedInputFormatException;
            r7 = new java.lang.StringBuilder;
            r7.<init>();
            r0 = "None of the available extractors (";
            r7.append(r0);
            r0 = r5.extractors;
            r0 = com.google.android.exoplayer2.util.Util.getCommaDelimitedSimpleClassNames(r0);
            r7.append(r0);
            r0 = ") could read the stream.";
            r7.append(r0);
            r7 = r7.toString();
            r6.<init>(r7, r8);
            throw r6;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.ExtractorMediaPeriod.ExtractorHolder.selectExtractor(com.google.android.exoplayer2.extractor.ExtractorInput, com.google.android.exoplayer2.extractor.ExtractorOutput, android.net.Uri):com.google.android.exoplayer2.extractor.Extractor");
        }

        public void release() {
            if (this.extractor != null) {
                this.extractor.release();
                this.extractor = null;
            }
        }
    }

    interface Listener {
        void onSourceInfoRefreshed(long j, boolean z);
    }

    private static final class PreparedState {
        public final SeekMap seekMap;
        public final boolean[] trackEnabledStates;
        public final boolean[] trackIsAudioVideoFlags;
        public final boolean[] trackNotifiedDownstreamFormats;
        public final TrackGroupArray tracks;

        public PreparedState(SeekMap seekMap, TrackGroupArray trackGroupArray, boolean[] zArr) {
            this.seekMap = seekMap;
            this.tracks = trackGroupArray;
            this.trackIsAudioVideoFlags = zArr;
            this.trackEnabledStates = new boolean[trackGroupArray.length];
            this.trackNotifiedDownstreamFormats = new boolean[trackGroupArray.length];
        }
    }

    final class ExtractingLoadable implements Loadable {
        private final StatsDataSource dataSource;
        private DataSpec dataSpec;
        private final ExtractorHolder extractorHolder;
        private final ExtractorOutput extractorOutput;
        private long length = -1;
        private volatile boolean loadCanceled;
        private final ConditionVariable loadCondition;
        private boolean pendingExtractorSeek = true;
        private final PositionHolder positionHolder = new PositionHolder();
        private long seekTimeUs;
        private final Uri uri;

        public ExtractingLoadable(Uri uri, DataSource dataSource, ExtractorHolder extractorHolder, ExtractorOutput extractorOutput, ConditionVariable conditionVariable) {
            this.uri = uri;
            this.dataSource = new StatsDataSource(dataSource);
            this.extractorHolder = extractorHolder;
            this.extractorOutput = extractorOutput;
            this.loadCondition = conditionVariable;
            this.dataSpec = new DataSpec(uri, this.positionHolder.position, -1, ExtractorMediaPeriod.this.customCacheKey);
        }

        public void cancelLoad() {
            this.loadCanceled = true;
        }

        public void load() throws IOException, InterruptedException {
            Throwable th;
            int i = 0;
            while (i == 0 && !this.loadCanceled) {
                ExtractorInput defaultExtractorInput;
                ExtractorInput extractorInput = null;
                try {
                    long j = this.positionHolder.position;
                    this.dataSpec = new DataSpec(this.uri, j, -1, ExtractorMediaPeriod.this.customCacheKey);
                    this.length = this.dataSource.open(this.dataSpec);
                    if (this.length != -1) {
                        this.length += j;
                    }
                    Uri uri = (Uri) Assertions.checkNotNull(this.dataSource.getUri());
                    defaultExtractorInput = new DefaultExtractorInput(this.dataSource, j, this.length);
                    try {
                        Extractor selectExtractor = this.extractorHolder.selectExtractor(defaultExtractorInput, this.extractorOutput, uri);
                        if (this.pendingExtractorSeek) {
                            selectExtractor.seek(j, this.seekTimeUs);
                            this.pendingExtractorSeek = false;
                        }
                        while (i == 0 && !this.loadCanceled) {
                            this.loadCondition.block();
                            int read = selectExtractor.read(defaultExtractorInput, this.positionHolder);
                            try {
                                if (defaultExtractorInput.getPosition() > ExtractorMediaPeriod.this.continueLoadingCheckIntervalBytes + j) {
                                    j = defaultExtractorInput.getPosition();
                                    this.loadCondition.close();
                                    ExtractorMediaPeriod.this.handler.post(ExtractorMediaPeriod.this.onContinueLoadingRequestedRunnable);
                                }
                                i = read;
                            } catch (Throwable th2) {
                                th = th2;
                                i = read;
                            }
                        }
                        if (i == 1) {
                            i = 0;
                        } else {
                            this.positionHolder.position = defaultExtractorInput.getPosition();
                        }
                        Util.closeQuietly(this.dataSource);
                    } catch (Throwable th3) {
                        th = th3;
                    }
                } catch (Throwable th4) {
                    th = th4;
                }
            }
            return;
            extractorInput = defaultExtractorInput;
            if (!(i == 1 || extractorInput == null)) {
                this.positionHolder.position = extractorInput.getPosition();
            }
            Util.closeQuietly(this.dataSource);
            throw th;
            this.positionHolder.position = extractorInput.getPosition();
            Util.closeQuietly(this.dataSource);
            throw th;
        }

        private void setLoadPosition(long j, long j2) {
            this.positionHolder.position = j;
            this.seekTimeUs = j2;
            this.pendingExtractorSeek = 1;
        }
    }

    private final class SampleStreamImpl implements SampleStream {
        private final int track;

        public SampleStreamImpl(int i) {
            this.track = i;
        }

        public boolean isReady() {
            return ExtractorMediaPeriod.this.isReady(this.track);
        }

        public void maybeThrowError() throws IOException {
            ExtractorMediaPeriod.this.maybeThrowError();
        }

        public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, boolean z) {
            return ExtractorMediaPeriod.this.readData(this.track, formatHolder, decoderInputBuffer, z);
        }

        public int skipData(long j) {
            return ExtractorMediaPeriod.this.skipData(this.track, j);
        }
    }

    public void reevaluateBuffer(long j) {
    }

    public ExtractorMediaPeriod(Uri uri, DataSource dataSource, Extractor[] extractorArr, LoadErrorHandlingPolicy loadErrorHandlingPolicy, EventDispatcher eventDispatcher, Listener listener, Allocator allocator, @Nullable String str, int i) {
        this.uri = uri;
        this.dataSource = dataSource;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
        this.eventDispatcher = eventDispatcher;
        this.listener = listener;
        this.allocator = allocator;
        this.customCacheKey = str;
        this.continueLoadingCheckIntervalBytes = (long) i;
        this.extractorHolder = new ExtractorHolder(extractorArr);
        this.loadCondition = new ConditionVariable();
        this.maybeFinishPrepareRunnable = new -$$Lambda$ExtractorMediaPeriod$Ll7lI30pD07GZk92Lo8XgkQMAAY();
        this.onContinueLoadingRequestedRunnable = new -$$Lambda$ExtractorMediaPeriod$Hd-sBytb6cpkhM49l8dYCND3wmk();
        this.handler = new Handler();
        this.sampleQueueTrackIds = new int[0];
        this.sampleQueues = new SampleQueue[0];
        this.pendingResetPositionUs = C.TIME_UNSET;
        this.length = -1;
        this.durationUs = C.TIME_UNSET;
        this.dataType = 1;
        eventDispatcher.mediaPeriodCreated();
    }

    public static /* synthetic */ void lambda$new$0(ExtractorMediaPeriod extractorMediaPeriod) {
        if (!extractorMediaPeriod.released) {
            ((MediaPeriod.Callback) Assertions.checkNotNull(extractorMediaPeriod.callback)).onContinueLoadingRequested(extractorMediaPeriod);
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
        this.callback = null;
        this.released = true;
        this.eventDispatcher.mediaPeriodReleased();
    }

    public void onLoaderReleased() {
        for (SampleQueue reset : this.sampleQueues) {
            reset.reset();
        }
        this.extractorHolder.release();
    }

    public void prepare(MediaPeriod.Callback callback, long j) {
        this.callback = callback;
        this.loadCondition.open();
        startLoading();
    }

    public void maybeThrowPrepareError() throws IOException {
        maybeThrowError();
    }

    public TrackGroupArray getTrackGroups() {
        return getPreparedState().tracks;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long selectTracks(com.google.android.exoplayer2.trackselection.TrackSelection[] r9, boolean[] r10, com.google.android.exoplayer2.source.SampleStream[] r11, boolean[] r12, long r13) {
        /*
        r8 = this;
        r0 = r8.getPreparedState();
        r1 = r0.tracks;
        r0 = r0.trackEnabledStates;
        r2 = r8.enabledTrackCount;
        r3 = 0;
        r4 = 0;
    L_0x000c:
        r5 = r9.length;
        r6 = 1;
        if (r4 >= r5) goto L_0x0036;
    L_0x0010:
        r5 = r11[r4];
        if (r5 == 0) goto L_0x0033;
    L_0x0014:
        r5 = r9[r4];
        if (r5 == 0) goto L_0x001c;
    L_0x0018:
        r5 = r10[r4];
        if (r5 != 0) goto L_0x0033;
    L_0x001c:
        r5 = r11[r4];
        r5 = (com.google.android.exoplayer2.source.ExtractorMediaPeriod.SampleStreamImpl) r5;
        r5 = r5.track;
        r7 = r0[r5];
        com.google.android.exoplayer2.util.Assertions.checkState(r7);
        r7 = r8.enabledTrackCount;
        r7 = r7 - r6;
        r8.enabledTrackCount = r7;
        r0[r5] = r3;
        r5 = 0;
        r11[r4] = r5;
    L_0x0033:
        r4 = r4 + 1;
        goto L_0x000c;
    L_0x0036:
        r10 = r8.seenFirstTrackSelection;
        if (r10 == 0) goto L_0x0040;
    L_0x003a:
        if (r2 != 0) goto L_0x003e;
    L_0x003c:
        r10 = 1;
        goto L_0x0047;
    L_0x003e:
        r10 = 0;
        goto L_0x0047;
    L_0x0040:
        r4 = 0;
        r10 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1));
        if (r10 == 0) goto L_0x003e;
    L_0x0046:
        goto L_0x003c;
    L_0x0047:
        r2 = r10;
        r10 = 0;
    L_0x0049:
        r4 = r9.length;
        if (r10 >= r4) goto L_0x00a8;
    L_0x004c:
        r4 = r11[r10];
        if (r4 != 0) goto L_0x00a5;
    L_0x0050:
        r4 = r9[r10];
        if (r4 == 0) goto L_0x00a5;
    L_0x0054:
        r4 = r9[r10];
        r5 = r4.length();
        if (r5 != r6) goto L_0x005e;
    L_0x005c:
        r5 = 1;
        goto L_0x005f;
    L_0x005e:
        r5 = 0;
    L_0x005f:
        com.google.android.exoplayer2.util.Assertions.checkState(r5);
        r5 = r4.getIndexInTrackGroup(r3);
        if (r5 != 0) goto L_0x006a;
    L_0x0068:
        r5 = 1;
        goto L_0x006b;
    L_0x006a:
        r5 = 0;
    L_0x006b:
        com.google.android.exoplayer2.util.Assertions.checkState(r5);
        r4 = r4.getTrackGroup();
        r4 = r1.indexOf(r4);
        r5 = r0[r4];
        r5 = r5 ^ r6;
        com.google.android.exoplayer2.util.Assertions.checkState(r5);
        r5 = r8.enabledTrackCount;
        r5 = r5 + r6;
        r8.enabledTrackCount = r5;
        r0[r4] = r6;
        r5 = new com.google.android.exoplayer2.source.ExtractorMediaPeriod$SampleStreamImpl;
        r5.<init>(r4);
        r11[r10] = r5;
        r12[r10] = r6;
        if (r2 != 0) goto L_0x00a5;
    L_0x008e:
        r2 = r8.sampleQueues;
        r2 = r2[r4];
        r2.rewind();
        r4 = r2.advanceTo(r13, r6, r6);
        r5 = -1;
        if (r4 != r5) goto L_0x00a4;
    L_0x009c:
        r2 = r2.getReadIndex();
        if (r2 == 0) goto L_0x00a4;
    L_0x00a2:
        r2 = 1;
        goto L_0x00a5;
    L_0x00a4:
        r2 = 0;
    L_0x00a5:
        r10 = r10 + 1;
        goto L_0x0049;
    L_0x00a8:
        r9 = r8.enabledTrackCount;
        if (r9 != 0) goto L_0x00d8;
    L_0x00ac:
        r8.pendingDeferredRetry = r3;
        r8.notifyDiscontinuity = r3;
        r9 = r8.loader;
        r9 = r9.isLoading();
        if (r9 == 0) goto L_0x00cb;
    L_0x00b8:
        r9 = r8.sampleQueues;
        r10 = r9.length;
    L_0x00bb:
        if (r3 >= r10) goto L_0x00c5;
    L_0x00bd:
        r11 = r9[r3];
        r11.discardToEnd();
        r3 = r3 + 1;
        goto L_0x00bb;
    L_0x00c5:
        r9 = r8.loader;
        r9.cancelLoading();
        goto L_0x00ea;
    L_0x00cb:
        r9 = r8.sampleQueues;
        r10 = r9.length;
    L_0x00ce:
        if (r3 >= r10) goto L_0x00ea;
    L_0x00d0:
        r11 = r9[r3];
        r11.reset();
        r3 = r3 + 1;
        goto L_0x00ce;
    L_0x00d8:
        if (r2 == 0) goto L_0x00ea;
    L_0x00da:
        r13 = r8.seekToUs(r13);
    L_0x00de:
        r9 = r11.length;
        if (r3 >= r9) goto L_0x00ea;
    L_0x00e1:
        r9 = r11[r3];
        if (r9 == 0) goto L_0x00e7;
    L_0x00e5:
        r12[r3] = r6;
    L_0x00e7:
        r3 = r3 + 1;
        goto L_0x00de;
    L_0x00ea:
        r8.seenFirstTrackSelection = r6;
        return r13;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.ExtractorMediaPeriod.selectTracks(com.google.android.exoplayer2.trackselection.TrackSelection[], boolean[], com.google.android.exoplayer2.source.SampleStream[], boolean[], long):long");
    }

    public void discardBuffer(long j, boolean z) {
        if (!isPendingReset()) {
            boolean[] zArr = getPreparedState().trackEnabledStates;
            int length = this.sampleQueues.length;
            for (int i = 0; i < length; i++) {
                this.sampleQueues[i].discardTo(j, z, zArr[i]);
            }
        }
    }

    public boolean continueLoading(long j) {
        if (this.loadingFinished == null && this.pendingDeferredRetry == null) {
            if (this.prepared == null || this.enabledTrackCount != null) {
                j = this.loadCondition.open();
                if (!this.loader.isLoading()) {
                    startLoading();
                    j = 1;
                }
                return j;
            }
        }
        return 0;
    }

    public long getNextLoadPositionUs() {
        return this.enabledTrackCount == 0 ? Long.MIN_VALUE : getBufferedPositionUs();
    }

    public long readDiscontinuity() {
        if (!this.notifiedReadingStarted) {
            this.eventDispatcher.readingStarted();
            this.notifiedReadingStarted = true;
        }
        if (!this.notifyDiscontinuity || (!this.loadingFinished && getExtractedSamplesCount() <= this.extractedSamplesCountAtStartOfLoad)) {
            return C.TIME_UNSET;
        }
        this.notifyDiscontinuity = false;
        return this.lastSeekPositionUs;
    }

    public long getBufferedPositionUs() {
        boolean[] zArr = getPreparedState().trackIsAudioVideoFlags;
        if (this.loadingFinished) {
            return Long.MIN_VALUE;
        }
        if (isPendingReset()) {
            return this.pendingResetPositionUs;
        }
        long j;
        if (this.haveAudioVideoTracks) {
            j = Long.MAX_VALUE;
            int length = this.sampleQueues.length;
            for (int i = 0; i < length; i++) {
                if (zArr[i]) {
                    j = Math.min(j, this.sampleQueues[i].getLargestQueuedTimestampUs());
                }
            }
        } else {
            j = getLargestQueuedTimestampUs();
        }
        if (j == Long.MIN_VALUE) {
            j = this.lastSeekPositionUs;
        }
        return j;
    }

    public long seekToUs(long j) {
        PreparedState preparedState = getPreparedState();
        SeekMap seekMap = preparedState.seekMap;
        boolean[] zArr = preparedState.trackIsAudioVideoFlags;
        if (!seekMap.isSeekable()) {
            j = 0;
        }
        int i = 0;
        this.notifyDiscontinuity = false;
        this.lastSeekPositionUs = j;
        if (isPendingReset()) {
            this.pendingResetPositionUs = j;
            return j;
        } else if (this.dataType != 7 && seekInsideBufferUs(zArr, j)) {
            return j;
        } else {
            this.pendingDeferredRetry = false;
            this.pendingResetPositionUs = j;
            this.loadingFinished = false;
            if (this.loader.isLoading()) {
                this.loader.cancelLoading();
            } else {
                SampleQueue[] sampleQueueArr = this.sampleQueues;
                int length = sampleQueueArr.length;
                while (i < length) {
                    sampleQueueArr[i].reset();
                    i++;
                }
            }
            return j;
        }
    }

    public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
        SeekMap seekMap = getPreparedState().seekMap;
        if (!seekMap.isSeekable()) {
            return 0;
        }
        SeekPoints seekPoints = seekMap.getSeekPoints(j);
        return Util.resolveSeekPositionUs(j, seekParameters, seekPoints.first.timeUs, seekPoints.second.timeUs);
    }

    boolean isReady(int i) {
        return !suppressRead() && (this.loadingFinished || this.sampleQueues[i].hasNextSample() != 0);
    }

    void maybeThrowError() throws IOException {
        this.loader.maybeThrowError(this.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(this.dataType));
    }

    int readData(int i, FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, boolean z) {
        if (suppressRead()) {
            return -3;
        }
        maybeNotifyDownstreamFormat(i);
        formatHolder = this.sampleQueues[i].read(formatHolder, decoderInputBuffer, z, this.loadingFinished, this.lastSeekPositionUs);
        if (formatHolder == -3) {
            maybeStartDeferredRetry(i);
        }
        return formatHolder;
    }

    int skipData(int i, long j) {
        int i2 = 0;
        if (suppressRead()) {
            return 0;
        }
        maybeNotifyDownstreamFormat(i);
        SampleQueue sampleQueue = this.sampleQueues[i];
        if (!this.loadingFinished || j <= sampleQueue.getLargestQueuedTimestampUs()) {
            j = sampleQueue.advanceTo(j, true, true);
            if (j != -1) {
                i2 = j;
            }
        } else {
            i2 = sampleQueue.advanceToEnd();
        }
        if (i2 == 0) {
            maybeStartDeferredRetry(i);
        }
        return i2;
    }

    private void maybeNotifyDownstreamFormat(int i) {
        PreparedState preparedState = getPreparedState();
        boolean[] zArr = preparedState.trackNotifiedDownstreamFormats;
        if (!zArr[i]) {
            Format format = preparedState.tracks.get(i).getFormat(0);
            this.eventDispatcher.downstreamFormatChanged(MimeTypes.getTrackType(format.sampleMimeType), format, 0, null, this.lastSeekPositionUs);
            zArr[i] = true;
        }
    }

    private void maybeStartDeferredRetry(int i) {
        boolean[] zArr = getPreparedState().trackIsAudioVideoFlags;
        if (this.pendingDeferredRetry && zArr[i]) {
            if (this.sampleQueues[i].hasNextSample() == 0) {
                this.pendingResetPositionUs = 0;
                i = 0;
                this.pendingDeferredRetry = false;
                this.notifyDiscontinuity = true;
                this.lastSeekPositionUs = 0;
                this.extractedSamplesCountAtStartOfLoad = 0;
                SampleQueue[] sampleQueueArr = this.sampleQueues;
                int length = sampleQueueArr.length;
                while (i < length) {
                    sampleQueueArr[i].reset();
                    i++;
                }
                ((MediaPeriod.Callback) Assertions.checkNotNull(this.callback)).onContinueLoadingRequested(this);
            }
        }
    }

    private boolean suppressRead() {
        if (!this.notifyDiscontinuity) {
            if (!isPendingReset()) {
                return false;
            }
        }
        return true;
    }

    public void onLoadCompleted(ExtractingLoadable extractingLoadable, long j, long j2) {
        if (this.durationUs == C.TIME_UNSET) {
            SeekMap seekMap = (SeekMap) Assertions.checkNotNull(r0.seekMap);
            long largestQueuedTimestampUs = getLargestQueuedTimestampUs();
            r0.durationUs = largestQueuedTimestampUs == Long.MIN_VALUE ? 0 : largestQueuedTimestampUs + 10000;
            r0.listener.onSourceInfoRefreshed(r0.durationUs, seekMap.isSeekable());
        }
        r0.eventDispatcher.loadCompleted(extractingLoadable.dataSpec, extractingLoadable.dataSource.getLastOpenedUri(), extractingLoadable.dataSource.getLastResponseHeaders(), 1, -1, null, 0, null, extractingLoadable.seekTimeUs, r0.durationUs, j, j2, extractingLoadable.dataSource.getBytesRead());
        copyLengthFromLoader(extractingLoadable);
        r0.loadingFinished = true;
        ((MediaPeriod.Callback) Assertions.checkNotNull(r0.callback)).onContinueLoadingRequested(r0);
    }

    public void onLoadCanceled(ExtractingLoadable extractingLoadable, long j, long j2, boolean z) {
        this.eventDispatcher.loadCanceled(extractingLoadable.dataSpec, extractingLoadable.dataSource.getLastOpenedUri(), extractingLoadable.dataSource.getLastResponseHeaders(), 1, -1, null, 0, null, extractingLoadable.seekTimeUs, this.durationUs, j, j2, extractingLoadable.dataSource.getBytesRead());
        if (!z) {
            copyLengthFromLoader(extractingLoadable);
            for (SampleQueue reset : r0.sampleQueues) {
                reset.reset();
            }
            if (r0.enabledTrackCount > 0) {
                ((MediaPeriod.Callback) Assertions.checkNotNull(r0.callback)).onContinueLoadingRequested(r0);
            }
        }
    }

    public LoadErrorAction onLoadError(ExtractingLoadable extractingLoadable, long j, long j2, IOException iOException, int i) {
        LoadErrorAction loadErrorAction;
        copyLengthFromLoader(extractingLoadable);
        long retryDelayMsFor = this.loadErrorHandlingPolicy.getRetryDelayMsFor(this.dataType, this.durationUs, iOException, i);
        ExtractingLoadable extractingLoadable2;
        if (retryDelayMsFor == C.TIME_UNSET) {
            loadErrorAction = Loader.DONT_RETRY_FATAL;
            extractingLoadable2 = extractingLoadable;
        } else {
            boolean z;
            int extractedSamplesCount = getExtractedSamplesCount();
            if (extractedSamplesCount > r0.extractedSamplesCountAtStartOfLoad) {
                extractingLoadable2 = extractingLoadable;
                z = true;
            } else {
                extractingLoadable2 = extractingLoadable;
                z = false;
            }
            loadErrorAction = configureRetry(extractingLoadable2, extractedSamplesCount) ? Loader.createRetryAction(z, retryDelayMsFor) : Loader.DONT_RETRY;
        }
        r0.eventDispatcher.loadError(extractingLoadable.dataSpec, extractingLoadable.dataSource.getLastOpenedUri(), extractingLoadable.dataSource.getLastResponseHeaders(), 1, -1, null, 0, null, extractingLoadable.seekTimeUs, r0.durationUs, j, j2, extractingLoadable.dataSource.getBytesRead(), iOException, loadErrorAction.isRetry() ^ 1);
        return loadErrorAction;
    }

    public TrackOutput track(int i, int i2) {
        i2 = this.sampleQueues.length;
        for (int i3 = 0; i3 < i2; i3++) {
            if (this.sampleQueueTrackIds[i3] == i) {
                return this.sampleQueues[i3];
            }
        }
        TrackOutput sampleQueue = new SampleQueue(this.allocator);
        sampleQueue.setUpstreamFormatChangeListener(this);
        int i4 = i2 + 1;
        this.sampleQueueTrackIds = Arrays.copyOf(this.sampleQueueTrackIds, i4);
        this.sampleQueueTrackIds[i2] = i;
        SampleQueue[] sampleQueueArr = (SampleQueue[]) Arrays.copyOf(this.sampleQueues, i4);
        sampleQueueArr[i2] = sampleQueue;
        this.sampleQueues = (SampleQueue[]) Util.castNonNullTypeArray(sampleQueueArr);
        return sampleQueue;
    }

    public void endTracks() {
        this.sampleQueuesBuilt = true;
        this.handler.post(this.maybeFinishPrepareRunnable);
    }

    public void seekMap(SeekMap seekMap) {
        this.seekMap = seekMap;
        this.handler.post(this.maybeFinishPrepareRunnable);
    }

    public void onUpstreamFormatChanged(Format format) {
        this.handler.post(this.maybeFinishPrepareRunnable);
    }

    private void maybeFinishPrepare() {
        SeekMap seekMap = this.seekMap;
        if (!(this.released || this.prepared || !this.sampleQueuesBuilt)) {
            if (seekMap != null) {
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
                this.loadCondition.close();
                int length2 = this.sampleQueues.length;
                TrackGroup[] trackGroupArr = new TrackGroup[length2];
                boolean[] zArr = new boolean[length2];
                this.durationUs = seekMap.getDurationUs();
                int i2 = 0;
                while (true) {
                    boolean z = true;
                    if (i2 >= length2) {
                        break;
                    }
                    trackGroupArr[i2] = new TrackGroup(this.sampleQueues[i2].getUpstreamFormat());
                    String str = r7.sampleMimeType;
                    if (!MimeTypes.isVideo(str)) {
                        if (!MimeTypes.isAudio(str)) {
                            z = false;
                        }
                    }
                    zArr[i2] = z;
                    this.haveAudioVideoTracks = z | this.haveAudioVideoTracks;
                    i2++;
                }
                length2 = (this.length == -1 && seekMap.getDurationUs() == C.TIME_UNSET) ? 7 : 1;
                this.dataType = length2;
                this.preparedState = new PreparedState(seekMap, new TrackGroupArray(trackGroupArr), zArr);
                this.prepared = true;
                this.listener.onSourceInfoRefreshed(this.durationUs, seekMap.isSeekable());
                ((MediaPeriod.Callback) Assertions.checkNotNull(this.callback)).onPrepared(this);
            }
        }
    }

    private PreparedState getPreparedState() {
        return (PreparedState) Assertions.checkNotNull(this.preparedState);
    }

    private void copyLengthFromLoader(ExtractingLoadable extractingLoadable) {
        if (this.length == -1) {
            this.length = extractingLoadable.length;
        }
    }

    private void startLoading() {
        ExtractingLoadable extractingLoadable = new ExtractingLoadable(this.uri, this.dataSource, this.extractorHolder, this, this.loadCondition);
        if (this.prepared) {
            SeekMap seekMap = getPreparedState().seekMap;
            Assertions.checkState(isPendingReset());
            if (r7.durationUs == C.TIME_UNSET || r7.pendingResetPositionUs < r7.durationUs) {
                extractingLoadable.setLoadPosition(seekMap.getSeekPoints(r7.pendingResetPositionUs).first.position, r7.pendingResetPositionUs);
                r7.pendingResetPositionUs = C.TIME_UNSET;
            } else {
                r7.loadingFinished = true;
                r7.pendingResetPositionUs = C.TIME_UNSET;
                return;
            }
        }
        r7.extractedSamplesCountAtStartOfLoad = getExtractedSamplesCount();
        r7.eventDispatcher.loadStarted(extractingLoadable.dataSpec, 1, -1, null, 0, null, extractingLoadable.seekTimeUs, r7.durationUs, r7.loader.startLoading(extractingLoadable, r7, r7.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(r7.dataType)));
    }

    private boolean configureRetry(ExtractingLoadable extractingLoadable, int i) {
        if (this.length == -1) {
            if (this.seekMap == null || this.seekMap.getDurationUs() == C.TIME_UNSET) {
                int i2 = 0;
                if (this.prepared == 0 || suppressRead() != 0) {
                    this.notifyDiscontinuity = this.prepared;
                    this.lastSeekPositionUs = 0;
                    this.extractedSamplesCountAtStartOfLoad = 0;
                    i = this.sampleQueues;
                    int length = i.length;
                    while (i2 < length) {
                        i[i2].reset();
                        i2++;
                    }
                    extractingLoadable.setLoadPosition(0, 0);
                    return true;
                }
                this.pendingDeferredRetry = true;
                return false;
            }
        }
        this.extractedSamplesCountAtStartOfLoad = i;
        return true;
    }

    private boolean seekInsideBufferUs(boolean[] zArr, long j) {
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
            if (z || (!zArr[i] && this.haveAudioVideoTracks)) {
                i++;
            }
        }
        return false;
    }

    private int getExtractedSamplesCount() {
        int i = 0;
        for (SampleQueue writeIndex : this.sampleQueues) {
            i += writeIndex.getWriteIndex();
        }
        return i;
    }

    private long getLargestQueuedTimestampUs() {
        long j = Long.MIN_VALUE;
        for (SampleQueue largestQueuedTimestampUs : this.sampleQueues) {
            j = Math.max(j, largestQueuedTimestampUs.getLargestQueuedTimestampUs());
        }
        return j;
    }

    private boolean isPendingReset() {
        return this.pendingResetPositionUs != C.TIME_UNSET;
    }
}
