package com.google.android.exoplayer2.source.dash;

import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.SparseArray;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.Timeline.Window;
import com.google.android.exoplayer2.source.BaseMediaSource;
import com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.DefaultCompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource.MediaPeriodId;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.MediaSourceEventListener.EventDispatcher;
import com.google.android.exoplayer2.source.ads.AdsMediaSource.MediaSourceFactory;
import com.google.android.exoplayer2.source.dash.PlayerEmsgHandler.PlayerEmsgCallback;
import com.google.android.exoplayer2.source.dash.manifest.AdaptationSet;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.manifest.DashManifestParser;
import com.google.android.exoplayer2.source.dash.manifest.Period;
import com.google.android.exoplayer2.source.dash.manifest.Representation;
import com.google.android.exoplayer2.source.dash.manifest.UtcTimingElement;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.upstream.Loader.Callback;
import com.google.android.exoplayer2.upstream.Loader.LoadErrorAction;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower.Dummy;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.upstream.ParsingLoadable.Parser;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;

public final class DashMediaSource extends BaseMediaSource {
    @Deprecated
    public static final long DEFAULT_LIVE_PRESENTATION_DELAY_FIXED_MS = 30000;
    public static final long DEFAULT_LIVE_PRESENTATION_DELAY_MS = 30000;
    @Deprecated
    public static final long DEFAULT_LIVE_PRESENTATION_DELAY_PREFER_MANIFEST_MS = -1;
    private static final long MIN_LIVE_DEFAULT_START_POSITION_US = 5000000;
    private static final int NOTIFY_MANIFEST_INTERVAL_MS = 5000;
    private static final String TAG = "DashMediaSource";
    private final com.google.android.exoplayer2.source.dash.DashChunkSource.Factory chunkSourceFactory;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private DataSource dataSource;
    private boolean dynamicMediaPresentationEnded;
    private long elapsedRealtimeOffsetMs;
    private long expiredManifestPublishTimeUs;
    private int firstPeriodId;
    private Handler handler;
    private Uri initialManifestUri;
    private final long livePresentationDelayMs;
    private final boolean livePresentationDelayOverridesManifest;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private Loader loader;
    private DashManifest manifest;
    private final ManifestCallback manifestCallback;
    private final com.google.android.exoplayer2.upstream.DataSource.Factory manifestDataSourceFactory;
    private final EventDispatcher manifestEventDispatcher;
    private IOException manifestFatalError;
    private long manifestLoadEndTimestampMs;
    private final LoaderErrorThrower manifestLoadErrorThrower;
    private boolean manifestLoadPending;
    private long manifestLoadStartTimestampMs;
    private final Parser<? extends DashManifest> manifestParser;
    private Uri manifestUri;
    private final Object manifestUriLock;
    @Nullable
    private TransferListener mediaTransferListener;
    private final SparseArray<DashMediaPeriod> periodsById;
    private final PlayerEmsgCallback playerEmsgCallback;
    private final Runnable refreshManifestRunnable;
    private final boolean sideloadedManifest;
    private final Runnable simulateManifestRefreshRunnable;
    private int staleManifestReloadAttempt;
    @Nullable
    private final Object tag;

    private static final class PeriodSeekInfo {
        public final long availableEndTimeUs;
        public final long availableStartTimeUs;
        public final boolean isIndexExplicit;

        public static PeriodSeekInfo createPeriodSeekInfo(Period period, long j) {
            Object obj;
            Period period2 = period;
            long j2 = j;
            int size = period2.adaptationSets.size();
            int i = 0;
            int i2 = 0;
            while (i2 < size) {
                int i3 = ((AdaptationSet) period2.adaptationSets.get(i2)).type;
                if (i3 != 1) {
                    if (i3 != 2) {
                        i2++;
                    }
                }
                obj = 1;
            }
            obj = null;
            long j3 = Long.MAX_VALUE;
            int i4 = 0;
            Object obj2 = null;
            boolean z = false;
            long j4 = 0;
            while (i4 < size) {
                int i5;
                Object obj3;
                AdaptationSet adaptationSet = (AdaptationSet) period2.adaptationSets.get(i4);
                if (obj == null || adaptationSet.type != 3) {
                    DashSegmentIndex index = ((Representation) adaptationSet.representations.get(i)).getIndex();
                    if (index == null) {
                        return new PeriodSeekInfo(true, 0, j);
                    }
                    boolean isExplicit = index.isExplicit() | z;
                    int segmentCount = index.getSegmentCount(j2);
                    if (segmentCount == 0) {
                        i5 = size;
                        obj3 = obj;
                        z = isExplicit;
                        obj2 = 1;
                        j4 = 0;
                        j3 = 0;
                    } else {
                        if (obj2 == null) {
                            obj3 = obj;
                            long firstSegmentNum = index.getFirstSegmentNum();
                            i5 = size;
                            long max = Math.max(j4, index.getTimeUs(firstSegmentNum));
                            if (segmentCount != -1) {
                                firstSegmentNum = (firstSegmentNum + ((long) segmentCount)) - 1;
                                j4 = max;
                                j3 = Math.min(j3, index.getTimeUs(firstSegmentNum) + index.getDurationUs(firstSegmentNum, j2));
                            } else {
                                j4 = max;
                            }
                        } else {
                            i5 = size;
                            obj3 = obj;
                        }
                        z = isExplicit;
                    }
                } else {
                    i5 = size;
                    obj3 = obj;
                }
                i4++;
                obj = obj3;
                size = i5;
                period2 = period;
                i = 0;
            }
            return new PeriodSeekInfo(z, j4, j3);
        }

        private PeriodSeekInfo(boolean z, long j, long j2) {
            this.isIndexExplicit = z;
            this.availableStartTimeUs = j;
            this.availableEndTimeUs = j2;
        }
    }

    private static final class DashTimeline extends Timeline {
        private final int firstPeriodId;
        private final DashManifest manifest;
        private final long offsetInFirstPeriodUs;
        private final long presentationStartTimeMs;
        private final long windowDefaultStartPositionUs;
        private final long windowDurationUs;
        private final long windowStartTimeMs;
        @Nullable
        private final Object windowTag;

        public int getWindowCount() {
            return 1;
        }

        public DashTimeline(long j, long j2, int i, long j3, long j4, long j5, DashManifest dashManifest, @Nullable Object obj) {
            this.presentationStartTimeMs = j;
            this.windowStartTimeMs = j2;
            this.firstPeriodId = i;
            this.offsetInFirstPeriodUs = j3;
            this.windowDurationUs = j4;
            this.windowDefaultStartPositionUs = j5;
            this.manifest = dashManifest;
            this.windowTag = obj;
        }

        public int getPeriodCount() {
            return this.manifest.getPeriodCount();
        }

        public Timeline.Period getPeriod(int i, Timeline.Period period, boolean z) {
            Assertions.checkIndex(i, 0, getPeriodCount());
            Integer num = null;
            Object obj = z ? this.manifest.getPeriod(i).id : null;
            if (z) {
                num = Integer.valueOf(this.firstPeriodId + i);
            }
            return period.set(obj, num, 0, this.manifest.getPeriodDurationUs(i), C.msToUs(this.manifest.getPeriod(i).startMs - this.manifest.getPeriod(0).startMs) - this.offsetInFirstPeriodUs);
        }

        public Window getWindow(int i, Window window, boolean z, long j) {
            Assertions.checkIndex(i, 0, 1);
            return window.set(z ? r0.windowTag : null, r0.presentationStartTimeMs, r0.windowStartTimeMs, true, r0.manifest.dynamic, getAdjustedWindowDefaultStartPositionUs(j), r0.windowDurationUs, 0, getPeriodCount() - 1, r0.offsetInFirstPeriodUs);
        }

        public int getIndexOfPeriod(Object obj) {
            if (!(obj instanceof Integer)) {
                return -1;
            }
            obj = ((Integer) obj).intValue() - this.firstPeriodId;
            if (obj < null || obj >= getPeriodCount()) {
                obj = -1;
            }
            return obj;
        }

        private long getAdjustedWindowDefaultStartPositionUs(long j) {
            long j2 = this.windowDefaultStartPositionUs;
            if (!this.manifest.dynamic) {
                return j2;
            }
            if (j > 0) {
                j2 += j;
                if (j2 > this.windowDurationUs) {
                    return C.TIME_UNSET;
                }
            }
            j = this.offsetInFirstPeriodUs + j2;
            long periodDurationUs = this.manifest.getPeriodDurationUs(0);
            long j3 = j;
            j = null;
            while (j < this.manifest.getPeriodCount() - 1 && j3 >= periodDurationUs) {
                j3 -= periodDurationUs;
                j++;
                periodDurationUs = this.manifest.getPeriodDurationUs(j);
            }
            j = this.manifest.getPeriod(j);
            int adaptationSetIndex = j.getAdaptationSetIndex(2);
            if (adaptationSetIndex == -1) {
                return j2;
            }
            j = ((Representation) ((AdaptationSet) j.adaptationSets.get(adaptationSetIndex)).representations.get(0)).getIndex();
            if (j != null) {
                if (j.getSegmentCount(periodDurationUs) != 0) {
                    return (j2 + j.getTimeUs(j.getSegmentNum(j3, periodDurationUs))) - j3;
                }
            }
            return j2;
        }

        public Object getUidOfPeriod(int i) {
            Assertions.checkIndex(i, 0, getPeriodCount());
            return Integer.valueOf(this.firstPeriodId + i);
        }
    }

    private final class DefaultPlayerEmsgCallback implements PlayerEmsgCallback {
        private DefaultPlayerEmsgCallback() {
        }

        public void onDashManifestRefreshRequested() {
            DashMediaSource.this.onDashManifestRefreshRequested();
        }

        public void onDashManifestPublishTimeExpired(long j) {
            DashMediaSource.this.onDashManifestPublishTimeExpired(j);
        }

        public void onDashLiveMediaPresentationEndSignalEncountered() {
            DashMediaSource.this.onDashLiveMediaPresentationEndSignalEncountered();
        }
    }

    public static final class Factory implements MediaSourceFactory {
        private final com.google.android.exoplayer2.source.dash.DashChunkSource.Factory chunkSourceFactory;
        private CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
        private boolean isCreateCalled;
        private long livePresentationDelayMs;
        private boolean livePresentationDelayOverridesManifest;
        private LoadErrorHandlingPolicy loadErrorHandlingPolicy;
        @Nullable
        private final com.google.android.exoplayer2.upstream.DataSource.Factory manifestDataSourceFactory;
        @Nullable
        private Parser<? extends DashManifest> manifestParser;
        @Nullable
        private Object tag;

        public Factory(com.google.android.exoplayer2.upstream.DataSource.Factory factory) {
            this(new com.google.android.exoplayer2.source.dash.DefaultDashChunkSource.Factory(factory), factory);
        }

        public Factory(com.google.android.exoplayer2.source.dash.DashChunkSource.Factory factory, @Nullable com.google.android.exoplayer2.upstream.DataSource.Factory factory2) {
            this.chunkSourceFactory = (com.google.android.exoplayer2.source.dash.DashChunkSource.Factory) Assertions.checkNotNull(factory);
            this.manifestDataSourceFactory = factory2;
            this.loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
            this.livePresentationDelayMs = 30000;
            this.compositeSequenceableLoaderFactory = new DefaultCompositeSequenceableLoaderFactory();
        }

        public Factory setTag(Object obj) {
            Assertions.checkState(this.isCreateCalled ^ 1);
            this.tag = obj;
            return this;
        }

        @Deprecated
        public Factory setMinLoadableRetryCount(int i) {
            return setLoadErrorHandlingPolicy(new DefaultLoadErrorHandlingPolicy(i));
        }

        public Factory setLoadErrorHandlingPolicy(LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
            Assertions.checkState(this.isCreateCalled ^ 1);
            this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
            return this;
        }

        @Deprecated
        public Factory setLivePresentationDelayMs(long j) {
            if (j == -1) {
                return setLivePresentationDelayMs(30000, false);
            }
            return setLivePresentationDelayMs(j, true);
        }

        public Factory setLivePresentationDelayMs(long j, boolean z) {
            Assertions.checkState(this.isCreateCalled ^ 1);
            this.livePresentationDelayMs = j;
            this.livePresentationDelayOverridesManifest = z;
            return this;
        }

        public Factory setManifestParser(Parser<? extends DashManifest> parser) {
            Assertions.checkState(this.isCreateCalled ^ 1);
            this.manifestParser = (Parser) Assertions.checkNotNull(parser);
            return this;
        }

        public Factory setCompositeSequenceableLoaderFactory(CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory) {
            Assertions.checkState(this.isCreateCalled ^ 1);
            this.compositeSequenceableLoaderFactory = (CompositeSequenceableLoaderFactory) Assertions.checkNotNull(compositeSequenceableLoaderFactory);
            return this;
        }

        public DashMediaSource createMediaSource(DashManifest dashManifest) {
            Assertions.checkArgument(dashManifest.dynamic ^ true);
            this.isCreateCalled = true;
            return new DashMediaSource(dashManifest, null, null, null, this.chunkSourceFactory, this.compositeSequenceableLoaderFactory, this.loadErrorHandlingPolicy, this.livePresentationDelayMs, this.livePresentationDelayOverridesManifest, this.tag);
        }

        @Deprecated
        public DashMediaSource createMediaSource(DashManifest dashManifest, @Nullable Handler handler, @Nullable MediaSourceEventListener mediaSourceEventListener) {
            dashManifest = createMediaSource(dashManifest);
            if (!(handler == null || mediaSourceEventListener == null)) {
                dashManifest.addEventListener(handler, mediaSourceEventListener);
            }
            return dashManifest;
        }

        public DashMediaSource createMediaSource(Uri uri) {
            this.isCreateCalled = true;
            if (this.manifestParser == null) {
                this.manifestParser = new DashManifestParser();
            }
            return new DashMediaSource(null, (Uri) Assertions.checkNotNull(uri), this.manifestDataSourceFactory, this.manifestParser, this.chunkSourceFactory, this.compositeSequenceableLoaderFactory, this.loadErrorHandlingPolicy, this.livePresentationDelayMs, this.livePresentationDelayOverridesManifest, this.tag);
        }

        @Deprecated
        public DashMediaSource createMediaSource(Uri uri, @Nullable Handler handler, @Nullable MediaSourceEventListener mediaSourceEventListener) {
            uri = createMediaSource(uri);
            if (!(handler == null || mediaSourceEventListener == null)) {
                uri.addEventListener(handler, mediaSourceEventListener);
            }
            return uri;
        }

        public int[] getSupportedTypes() {
            return new int[]{0};
        }
    }

    static final class Iso8601Parser implements Parser<Long> {
        private static final Pattern TIMESTAMP_WITH_TIMEZONE_PATTERN = Pattern.compile("(.+?)(Z|((\\+|-|−)(\\d\\d)(:?(\\d\\d))?))");

        Iso8601Parser() {
        }

        public Long parse(Uri uri, InputStream inputStream) throws IOException {
            uri = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8"))).readLine();
            try {
                inputStream = TIMESTAMP_WITH_TIMEZONE_PATTERN.matcher(uri);
                if (inputStream.matches()) {
                    uri = inputStream.group(1);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    long time = simpleDateFormat.parse(uri).getTime();
                    if ("Z".equals(inputStream.group(2)) == null) {
                        long j = "+".equals(inputStream.group(4)) != null ? 1 : -1;
                        long parseLong = Long.parseLong(inputStream.group(5));
                        uri = inputStream.group(7);
                        time -= j * ((((parseLong * 60) + (TextUtils.isEmpty(uri) != null ? 0 : Long.parseLong(uri))) * 60) * 1000);
                    }
                    return Long.valueOf(time);
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Couldn't parse timestamp: ");
                stringBuilder.append(uri);
                throw new ParserException(stringBuilder.toString());
            } catch (Throwable e) {
                throw new ParserException(e);
            }
        }
    }

    private final class ManifestCallback implements Callback<ParsingLoadable<DashManifest>> {
        private ManifestCallback() {
        }

        public void onLoadCompleted(ParsingLoadable<DashManifest> parsingLoadable, long j, long j2) {
            DashMediaSource.this.onManifestLoadCompleted(parsingLoadable, j, j2);
        }

        public void onLoadCanceled(ParsingLoadable<DashManifest> parsingLoadable, long j, long j2, boolean z) {
            DashMediaSource.this.onLoadCanceled(parsingLoadable, j, j2);
        }

        public LoadErrorAction onLoadError(ParsingLoadable<DashManifest> parsingLoadable, long j, long j2, IOException iOException, int i) {
            return DashMediaSource.this.onManifestLoadError(parsingLoadable, j, j2, iOException);
        }
    }

    final class ManifestLoadErrorThrower implements LoaderErrorThrower {
        ManifestLoadErrorThrower() {
        }

        public void maybeThrowError() throws IOException {
            DashMediaSource.this.loader.maybeThrowError();
            maybeThrowManifestError();
        }

        public void maybeThrowError(int i) throws IOException {
            DashMediaSource.this.loader.maybeThrowError(i);
            maybeThrowManifestError();
        }

        private void maybeThrowManifestError() throws IOException {
            if (DashMediaSource.this.manifestFatalError != null) {
                throw DashMediaSource.this.manifestFatalError;
            }
        }
    }

    private final class UtcTimestampCallback implements Callback<ParsingLoadable<Long>> {
        private UtcTimestampCallback() {
        }

        public void onLoadCompleted(ParsingLoadable<Long> parsingLoadable, long j, long j2) {
            DashMediaSource.this.onUtcTimestampLoadCompleted(parsingLoadable, j, j2);
        }

        public void onLoadCanceled(ParsingLoadable<Long> parsingLoadable, long j, long j2, boolean z) {
            DashMediaSource.this.onLoadCanceled(parsingLoadable, j, j2);
        }

        public LoadErrorAction onLoadError(ParsingLoadable<Long> parsingLoadable, long j, long j2, IOException iOException, int i) {
            return DashMediaSource.this.onUtcTimestampLoadError(parsingLoadable, j, j2, iOException);
        }
    }

    private static final class XsDateTimeParser implements Parser<Long> {
        private XsDateTimeParser() {
        }

        public Long parse(Uri uri, InputStream inputStream) throws IOException {
            return Long.valueOf(Util.parseXsDateTime(new BufferedReader(new InputStreamReader(inputStream)).readLine()));
        }
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.dash");
    }

    @Deprecated
    public DashMediaSource(DashManifest dashManifest, com.google.android.exoplayer2.source.dash.DashChunkSource.Factory factory, Handler handler, MediaSourceEventListener mediaSourceEventListener) {
        this(dashManifest, factory, 3, handler, mediaSourceEventListener);
    }

    @Deprecated
    public DashMediaSource(DashManifest dashManifest, com.google.android.exoplayer2.source.dash.DashChunkSource.Factory factory, int i, Handler handler, MediaSourceEventListener mediaSourceEventListener) {
        Handler handler2 = handler;
        MediaSourceEventListener mediaSourceEventListener2 = mediaSourceEventListener;
        this(dashManifest, null, null, null, factory, new DefaultCompositeSequenceableLoaderFactory(), new DefaultLoadErrorHandlingPolicy(i), 30000, false, null);
        if (handler2 == null || mediaSourceEventListener2 == null) {
            DashMediaSource dashMediaSource = this;
            return;
        }
        dashMediaSource = this;
        addEventListener(handler2, mediaSourceEventListener2);
    }

    @Deprecated
    public DashMediaSource(Uri uri, com.google.android.exoplayer2.upstream.DataSource.Factory factory, com.google.android.exoplayer2.source.dash.DashChunkSource.Factory factory2, Handler handler, MediaSourceEventListener mediaSourceEventListener) {
        this(uri, factory, factory2, 3, -1, handler, mediaSourceEventListener);
    }

    @Deprecated
    public DashMediaSource(Uri uri, com.google.android.exoplayer2.upstream.DataSource.Factory factory, com.google.android.exoplayer2.source.dash.DashChunkSource.Factory factory2, int i, long j, Handler handler, MediaSourceEventListener mediaSourceEventListener) {
        this(uri, factory, new DashManifestParser(), factory2, i, j, handler, mediaSourceEventListener);
    }

    @Deprecated
    public DashMediaSource(Uri uri, com.google.android.exoplayer2.upstream.DataSource.Factory factory, Parser<? extends DashManifest> parser, com.google.android.exoplayer2.source.dash.DashChunkSource.Factory factory2, int i, long j, Handler handler, MediaSourceEventListener mediaSourceEventListener) {
        Handler handler2 = handler;
        MediaSourceEventListener mediaSourceEventListener2 = mediaSourceEventListener;
        this(null, uri, factory, parser, factory2, new DefaultCompositeSequenceableLoaderFactory(), new DefaultLoadErrorHandlingPolicy(i), j == -1 ? 30000 : j, j != -1, null);
        if (handler2 == null || mediaSourceEventListener2 == null) {
            DashMediaSource dashMediaSource = this;
            return;
        }
        dashMediaSource = this;
        addEventListener(handler2, mediaSourceEventListener2);
    }

    private DashMediaSource(DashManifest dashManifest, Uri uri, com.google.android.exoplayer2.upstream.DataSource.Factory factory, Parser<? extends DashManifest> parser, com.google.android.exoplayer2.source.dash.DashChunkSource.Factory factory2, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, LoadErrorHandlingPolicy loadErrorHandlingPolicy, long j, boolean z, @Nullable Object obj) {
        this.initialManifestUri = uri;
        this.manifest = dashManifest;
        this.manifestUri = uri;
        this.manifestDataSourceFactory = factory;
        this.manifestParser = parser;
        this.chunkSourceFactory = factory2;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
        this.livePresentationDelayMs = j;
        this.livePresentationDelayOverridesManifest = z;
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory;
        this.tag = obj;
        this.sideloadedManifest = dashManifest != null ? true : null;
        this.manifestEventDispatcher = createEventDispatcher(null);
        this.manifestUriLock = new Object();
        this.periodsById = new SparseArray();
        this.playerEmsgCallback = new DefaultPlayerEmsgCallback();
        this.expiredManifestPublishTimeUs = 1;
        if (this.sideloadedManifest != null) {
            Assertions.checkState(dashManifest.dynamic ^ 1);
            this.manifestCallback = null;
            this.refreshManifestRunnable = null;
            this.simulateManifestRefreshRunnable = null;
            this.manifestLoadErrorThrower = new Dummy();
            return;
        }
        this.manifestCallback = new ManifestCallback();
        this.manifestLoadErrorThrower = new ManifestLoadErrorThrower();
        this.refreshManifestRunnable = new -$$Lambda$DashMediaSource$QbzYvqCY1TT8f0KClkalovG-Oxc();
        this.simulateManifestRefreshRunnable = new -$$Lambda$DashMediaSource$e1nzB-O4m3YSG1BkxQDKPaNvDa8();
    }

    public void replaceManifestUri(Uri uri) {
        synchronized (this.manifestUriLock) {
            this.manifestUri = uri;
            this.initialManifestUri = uri;
        }
    }

    public void prepareSourceInternal(ExoPlayer exoPlayer, boolean z, @Nullable TransferListener transferListener) {
        this.mediaTransferListener = transferListener;
        if (this.sideloadedManifest != null) {
            processManifest(null);
            return;
        }
        this.dataSource = this.manifestDataSourceFactory.createDataSource();
        this.loader = new Loader("Loader:DashMediaSource");
        this.handler = new Handler();
        startLoadingManifest();
    }

    public void maybeThrowSourceInfoRefreshError() throws IOException {
        this.manifestLoadErrorThrower.maybeThrowError();
    }

    public MediaPeriod createPeriod(MediaPeriodId mediaPeriodId, Allocator allocator) {
        MediaPeriodId mediaPeriodId2 = mediaPeriodId;
        int intValue = ((Integer) mediaPeriodId2.periodUid).intValue() - this.firstPeriodId;
        EventDispatcher createEventDispatcher = createEventDispatcher(mediaPeriodId2, this.manifest.getPeriod(intValue).startMs);
        MediaPeriod dashMediaPeriod = new DashMediaPeriod(this.firstPeriodId + intValue, this.manifest, intValue, this.chunkSourceFactory, this.mediaTransferListener, this.loadErrorHandlingPolicy, createEventDispatcher, this.elapsedRealtimeOffsetMs, this.manifestLoadErrorThrower, allocator, this.compositeSequenceableLoaderFactory, this.playerEmsgCallback);
        this.periodsById.put(dashMediaPeriod.id, dashMediaPeriod);
        return dashMediaPeriod;
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        DashMediaPeriod dashMediaPeriod = (DashMediaPeriod) mediaPeriod;
        dashMediaPeriod.release();
        this.periodsById.remove(dashMediaPeriod.id);
    }

    public void releaseSourceInternal() {
        this.manifestLoadPending = false;
        this.dataSource = null;
        if (this.loader != null) {
            this.loader.release();
            this.loader = null;
        }
        this.manifestLoadStartTimestampMs = 0;
        this.manifestLoadEndTimestampMs = 0;
        this.manifest = this.sideloadedManifest ? this.manifest : null;
        this.manifestUri = this.initialManifestUri;
        this.manifestFatalError = null;
        if (this.handler != null) {
            this.handler.removeCallbacksAndMessages(null);
            this.handler = null;
        }
        this.elapsedRealtimeOffsetMs = 0;
        this.staleManifestReloadAttempt = 0;
        this.expiredManifestPublishTimeUs = C.TIME_UNSET;
        this.dynamicMediaPresentationEnded = false;
        this.firstPeriodId = 0;
        this.periodsById.clear();
    }

    void onDashManifestRefreshRequested() {
        this.handler.removeCallbacks(this.simulateManifestRefreshRunnable);
        startLoadingManifest();
    }

    void onDashLiveMediaPresentationEndSignalEncountered() {
        this.dynamicMediaPresentationEnded = true;
    }

    void onDashManifestPublishTimeExpired(long j) {
        if (this.expiredManifestPublishTimeUs == C.TIME_UNSET || this.expiredManifestPublishTimeUs < j) {
            this.expiredManifestPublishTimeUs = j;
        }
    }

    void onManifestLoadCompleted(ParsingLoadable<DashManifest> parsingLoadable, long j, long j2) {
        ParsingLoadable<DashManifest> parsingLoadable2 = parsingLoadable;
        long j3 = j;
        this.manifestEventDispatcher.loadCompleted(parsingLoadable2.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), parsingLoadable2.type, j, j2, parsingLoadable.bytesLoaded());
        DashManifest dashManifest = (DashManifest) parsingLoadable.getResult();
        int i = 0;
        int periodCount = this.manifest == null ? 0 : r1.manifest.getPeriodCount();
        long j4 = dashManifest.getPeriod(0).startMs;
        int i2 = 0;
        while (i2 < periodCount && r1.manifest.getPeriod(i2).startMs < j4) {
            i2++;
        }
        if (dashManifest.dynamic) {
            Object obj;
            int i3;
            if (periodCount - i2 > dashManifest.getPeriodCount()) {
                Log.w(TAG, "Loaded out of sync manifest");
            } else {
                if (!r1.dynamicMediaPresentationEnded) {
                    if (r1.expiredManifestPublishTimeUs == C.TIME_UNSET || dashManifest.publishTimeMs * 1000 > r1.expiredManifestPublishTimeUs) {
                        obj = null;
                        if (obj == null) {
                            i3 = r1.staleManifestReloadAttempt;
                            r1.staleManifestReloadAttempt = i3 + 1;
                            if (i3 >= r1.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(parsingLoadable2.type)) {
                                scheduleManifestRefresh(getManifestLoadRetryDelayMillis());
                            } else {
                                r1.manifestFatalError = new DashManifestStaleException();
                            }
                            return;
                        }
                        r1.staleManifestReloadAttempt = 0;
                    }
                }
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Loaded stale dynamic manifest: ");
                stringBuilder.append(dashManifest.publishTimeMs);
                stringBuilder.append(", ");
                stringBuilder.append(r1.dynamicMediaPresentationEnded);
                stringBuilder.append(", ");
                stringBuilder.append(r1.expiredManifestPublishTimeUs);
                Log.w(str, stringBuilder.toString());
            }
            obj = 1;
            if (obj == null) {
                r1.staleManifestReloadAttempt = 0;
            } else {
                i3 = r1.staleManifestReloadAttempt;
                r1.staleManifestReloadAttempt = i3 + 1;
                if (i3 >= r1.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(parsingLoadable2.type)) {
                    r1.manifestFatalError = new DashManifestStaleException();
                } else {
                    scheduleManifestRefresh(getManifestLoadRetryDelayMillis());
                }
                return;
            }
        }
        r1.manifest = dashManifest;
        r1.manifestLoadPending &= r1.manifest.dynamic;
        r1.manifestLoadStartTimestampMs = j3 - j2;
        r1.manifestLoadEndTimestampMs = j3;
        if (r1.manifest.location != null) {
            synchronized (r1.manifestUriLock) {
                if (parsingLoadable2.dataSpec.uri == r1.manifestUri) {
                    i = 1;
                }
                if (i != 0) {
                    r1.manifestUri = r1.manifest.location;
                }
            }
        }
        if (periodCount != 0) {
            r1.firstPeriodId += i2;
            processManifest(true);
        } else if (r1.manifest.utcTiming != null) {
            resolveUtcTimingElement(r1.manifest.utcTiming);
        } else {
            processManifest(true);
        }
    }

    LoadErrorAction onManifestLoadError(ParsingLoadable<DashManifest> parsingLoadable, long j, long j2, IOException iOException) {
        ParsingLoadable<DashManifest> parsingLoadable2 = parsingLoadable;
        IOException iOException2 = iOException;
        boolean z = iOException2 instanceof ParserException;
        this.manifestEventDispatcher.loadError(parsingLoadable2.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), parsingLoadable2.type, j, j2, parsingLoadable.bytesLoaded(), iOException2, z);
        return z ? Loader.DONT_RETRY_FATAL : Loader.RETRY;
    }

    void onUtcTimestampLoadCompleted(ParsingLoadable<Long> parsingLoadable, long j, long j2) {
        ParsingLoadable<Long> parsingLoadable2 = parsingLoadable;
        this.manifestEventDispatcher.loadCompleted(parsingLoadable2.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), parsingLoadable2.type, j, j2, parsingLoadable.bytesLoaded());
        onUtcTimestampResolved(((Long) parsingLoadable.getResult()).longValue() - j);
    }

    LoadErrorAction onUtcTimestampLoadError(ParsingLoadable<Long> parsingLoadable, long j, long j2, IOException iOException) {
        ParsingLoadable<Long> parsingLoadable2 = parsingLoadable;
        EventDispatcher eventDispatcher = this.manifestEventDispatcher;
        DataSpec dataSpec = parsingLoadable2.dataSpec;
        Uri uri = parsingLoadable.getUri();
        Map responseHeaders = parsingLoadable.getResponseHeaders();
        int i = parsingLoadable2.type;
        eventDispatcher.loadError(dataSpec, uri, responseHeaders, i, j, j2, parsingLoadable.bytesLoaded(), iOException, true);
        onUtcTimestampResolutionError(iOException);
        return Loader.DONT_RETRY;
    }

    void onLoadCanceled(ParsingLoadable<?> parsingLoadable, long j, long j2) {
        ParsingLoadable<?> parsingLoadable2 = parsingLoadable;
        this.manifestEventDispatcher.loadCanceled(parsingLoadable2.dataSpec, parsingLoadable.getUri(), parsingLoadable.getResponseHeaders(), parsingLoadable2.type, j, j2, parsingLoadable.bytesLoaded());
    }

    private void resolveUtcTimingElement(UtcTimingElement utcTimingElement) {
        String str = utcTimingElement.schemeIdUri;
        if (!Util.areEqual(str, "urn:mpeg:dash:utc:direct:2014")) {
            if (!Util.areEqual(str, "urn:mpeg:dash:utc:direct:2012")) {
                if (!Util.areEqual(str, "urn:mpeg:dash:utc:http-iso:2014")) {
                    if (!Util.areEqual(str, "urn:mpeg:dash:utc:http-iso:2012")) {
                        if (!Util.areEqual(str, "urn:mpeg:dash:utc:http-xsdate:2014")) {
                            if (!Util.areEqual(str, "urn:mpeg:dash:utc:http-xsdate:2012")) {
                                onUtcTimestampResolutionError(new IOException("Unsupported UTC timing scheme"));
                                return;
                            }
                        }
                        resolveUtcTimingElementHttp(utcTimingElement, new XsDateTimeParser());
                        return;
                    }
                }
                resolveUtcTimingElementHttp(utcTimingElement, new Iso8601Parser());
                return;
            }
        }
        resolveUtcTimingElementDirect(utcTimingElement);
    }

    private void resolveUtcTimingElementDirect(UtcTimingElement utcTimingElement) {
        try {
            onUtcTimestampResolved(Util.parseXsDateTime(utcTimingElement.value) - this.manifestLoadEndTimestampMs);
        } catch (UtcTimingElement utcTimingElement2) {
            onUtcTimestampResolutionError(utcTimingElement2);
        }
    }

    private void resolveUtcTimingElementHttp(UtcTimingElement utcTimingElement, Parser<Long> parser) {
        startLoading(new ParsingLoadable(this.dataSource, Uri.parse(utcTimingElement.value), 5, (Parser) parser), new UtcTimestampCallback(), 1);
    }

    private void onUtcTimestampResolved(long j) {
        this.elapsedRealtimeOffsetMs = j;
        processManifest(1);
    }

    private void onUtcTimestampResolutionError(IOException iOException) {
        Log.e(TAG, "Failed to resolve UtcTiming element.", iOException);
        processManifest(true);
    }

    private void processManifest(boolean z) {
        int i;
        long j;
        Object obj;
        long j2;
        DashMediaSource dashMediaSource = this;
        for (i = 0; i < dashMediaSource.periodsById.size(); i++) {
            int keyAt = dashMediaSource.periodsById.keyAt(i);
            if (keyAt >= dashMediaSource.firstPeriodId) {
                ((DashMediaPeriod) dashMediaSource.periodsById.valueAt(i)).updateManifest(dashMediaSource.manifest, keyAt - dashMediaSource.firstPeriodId);
            }
        }
        i = dashMediaSource.manifest.getPeriodCount() - 1;
        PeriodSeekInfo createPeriodSeekInfo = PeriodSeekInfo.createPeriodSeekInfo(dashMediaSource.manifest.getPeriod(0), dashMediaSource.manifest.getPeriodDurationUs(0));
        PeriodSeekInfo createPeriodSeekInfo2 = PeriodSeekInfo.createPeriodSeekInfo(dashMediaSource.manifest.getPeriod(i), dashMediaSource.manifest.getPeriodDurationUs(i));
        long j3 = createPeriodSeekInfo.availableStartTimeUs;
        long j4 = createPeriodSeekInfo2.availableEndTimeUs;
        if (!dashMediaSource.manifest.dynamic || createPeriodSeekInfo2.isIndexExplicit) {
            j = j3;
            obj = null;
        } else {
            j4 = Math.min((getNowUnixTimeUs() - C.msToUs(dashMediaSource.manifest.availabilityStartTimeMs)) - C.msToUs(dashMediaSource.manifest.getPeriod(i).startMs), j4);
            if (dashMediaSource.manifest.timeShiftBufferDepthMs != C.TIME_UNSET) {
                long msToUs = j4 - C.msToUs(dashMediaSource.manifest.timeShiftBufferDepthMs);
                while (msToUs < 0 && i > 0) {
                    i--;
                    msToUs += dashMediaSource.manifest.getPeriodDurationUs(i);
                }
                if (i == 0) {
                    msToUs = Math.max(j3, msToUs);
                } else {
                    msToUs = dashMediaSource.manifest.getPeriodDurationUs(0);
                }
                j3 = msToUs;
            }
            j = j3;
            obj = 1;
        }
        long j5 = j4 - j;
        for (int i2 = 0; i2 < dashMediaSource.manifest.getPeriodCount() - 1; i2++) {
            j5 += dashMediaSource.manifest.getPeriodDurationUs(i2);
        }
        if (dashMediaSource.manifest.dynamic) {
            long j6 = dashMediaSource.livePresentationDelayMs;
            if (!(dashMediaSource.livePresentationDelayOverridesManifest || dashMediaSource.manifest.suggestedPresentationDelayMs == C.TIME_UNSET)) {
                j6 = dashMediaSource.manifest.suggestedPresentationDelayMs;
            }
            j6 = j5 - C.msToUs(j6);
            if (j6 < MIN_LIVE_DEFAULT_START_POSITION_US) {
                j6 = Math.min(MIN_LIVE_DEFAULT_START_POSITION_US, j5 / 2);
            }
            j2 = j6;
        } else {
            j2 = 0;
        }
        long usToMs = (dashMediaSource.manifest.availabilityStartTimeMs + dashMediaSource.manifest.getPeriod(0).startMs) + C.usToMs(j);
        refreshSourceInfo(new DashTimeline(dashMediaSource.manifest.availabilityStartTimeMs, usToMs, dashMediaSource.firstPeriodId, j, j5, j2, dashMediaSource.manifest, dashMediaSource.tag), dashMediaSource.manifest);
        if (!dashMediaSource.sideloadedManifest) {
            dashMediaSource.handler.removeCallbacks(dashMediaSource.simulateManifestRefreshRunnable);
            if (obj != null) {
                dashMediaSource.handler.postDelayed(dashMediaSource.simulateManifestRefreshRunnable, 5000);
            }
            if (dashMediaSource.manifestLoadPending) {
                startLoadingManifest();
            } else if (z && dashMediaSource.manifest.dynamic && dashMediaSource.manifest.minUpdatePeriodMs != C.TIME_UNSET) {
                long j7 = dashMediaSource.manifest.minUpdatePeriodMs;
                if (j7 == 0) {
                    j7 = 5000;
                }
                scheduleManifestRefresh(Math.max(0, (dashMediaSource.manifestLoadStartTimestampMs + j7) - SystemClock.elapsedRealtime()));
            }
        }
    }

    private void scheduleManifestRefresh(long j) {
        this.handler.postDelayed(this.refreshManifestRunnable, j);
    }

    private void startLoadingManifest() {
        this.handler.removeCallbacks(this.refreshManifestRunnable);
        if (this.loader.isLoading()) {
            this.manifestLoadPending = true;
            return;
        }
        Uri uri;
        synchronized (this.manifestUriLock) {
            uri = this.manifestUri;
        }
        this.manifestLoadPending = false;
        startLoading(new ParsingLoadable(this.dataSource, uri, 4, this.manifestParser), this.manifestCallback, this.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(4));
    }

    private long getManifestLoadRetryDelayMillis() {
        return (long) Math.min((this.staleManifestReloadAttempt - 1) * 1000, 5000);
    }

    private <T> void startLoading(ParsingLoadable<T> parsingLoadable, Callback<ParsingLoadable<T>> callback, int i) {
        this.manifestEventDispatcher.loadStarted(parsingLoadable.dataSpec, parsingLoadable.type, this.loader.startLoading(parsingLoadable, callback, i));
    }

    private long getNowUnixTimeUs() {
        if (this.elapsedRealtimeOffsetMs != 0) {
            return C.msToUs(SystemClock.elapsedRealtime() + this.elapsedRealtimeOffsetMs);
        }
        return C.msToUs(System.currentTimeMillis());
    }
}
