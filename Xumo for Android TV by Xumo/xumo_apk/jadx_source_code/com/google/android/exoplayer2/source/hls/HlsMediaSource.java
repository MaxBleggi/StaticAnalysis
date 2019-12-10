package com.google.android.exoplayer2.source.hls;

import android.net.Uri;
import android.os.Handler;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.BaseMediaSource;
import com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.DefaultCompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource.MediaPeriodId;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.SinglePeriodTimeline;
import com.google.android.exoplayer2.source.ads.AdsMediaSource.MediaSourceFactory;
import com.google.android.exoplayer2.source.hls.playlist.DefaultHlsPlaylistParserFactory;
import com.google.android.exoplayer2.source.hls.playlist.DefaultHlsPlaylistTracker;
import com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist.Segment;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistParser;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistParserFactory;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker.PrimaryPlaylistListener;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.ParsingLoadable.Parser;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.util.List;

public final class HlsMediaSource extends BaseMediaSource implements PrimaryPlaylistListener {
    private final boolean allowChunklessPreparation;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private final HlsDataSourceFactory dataSourceFactory;
    private final HlsExtractorFactory extractorFactory;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private final Uri manifestUri;
    @Nullable
    private TransferListener mediaTransferListener;
    private final HlsPlaylistTracker playlistTracker;
    @Nullable
    private final Object tag;

    public static final class Factory implements MediaSourceFactory {
        private boolean allowChunklessPreparation;
        private CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
        private HlsExtractorFactory extractorFactory;
        private final HlsDataSourceFactory hlsDataSourceFactory;
        private boolean isCreateCalled;
        private LoadErrorHandlingPolicy loadErrorHandlingPolicy;
        private HlsPlaylistParserFactory playlistParserFactory;
        private com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker.Factory playlistTrackerFactory;
        @Nullable
        private Object tag;

        public Factory(com.google.android.exoplayer2.upstream.DataSource.Factory factory) {
            this(new DefaultHlsDataSourceFactory(factory));
        }

        public Factory(HlsDataSourceFactory hlsDataSourceFactory) {
            this.hlsDataSourceFactory = (HlsDataSourceFactory) Assertions.checkNotNull(hlsDataSourceFactory);
            this.playlistParserFactory = new DefaultHlsPlaylistParserFactory();
            this.playlistTrackerFactory = DefaultHlsPlaylistTracker.FACTORY;
            this.extractorFactory = HlsExtractorFactory.DEFAULT;
            this.loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy();
            this.compositeSequenceableLoaderFactory = new DefaultCompositeSequenceableLoaderFactory();
        }

        public Factory setTag(Object obj) {
            Assertions.checkState(this.isCreateCalled ^ 1);
            this.tag = obj;
            return this;
        }

        public Factory setExtractorFactory(HlsExtractorFactory hlsExtractorFactory) {
            Assertions.checkState(this.isCreateCalled ^ 1);
            this.extractorFactory = (HlsExtractorFactory) Assertions.checkNotNull(hlsExtractorFactory);
            return this;
        }

        public Factory setLoadErrorHandlingPolicy(LoadErrorHandlingPolicy loadErrorHandlingPolicy) {
            Assertions.checkState(this.isCreateCalled ^ 1);
            this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
            return this;
        }

        @Deprecated
        public Factory setMinLoadableRetryCount(int i) {
            Assertions.checkState(this.isCreateCalled ^ 1);
            this.loadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy(i);
            return this;
        }

        public Factory setPlaylistParserFactory(HlsPlaylistParserFactory hlsPlaylistParserFactory) {
            Assertions.checkState(this.isCreateCalled ^ 1);
            this.playlistParserFactory = (HlsPlaylistParserFactory) Assertions.checkNotNull(hlsPlaylistParserFactory);
            return this;
        }

        public Factory setPlaylistTrackerFactory(com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker.Factory factory) {
            Assertions.checkState(this.isCreateCalled ^ 1);
            this.playlistTrackerFactory = (com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker.Factory) Assertions.checkNotNull(factory);
            return this;
        }

        public Factory setCompositeSequenceableLoaderFactory(CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory) {
            Assertions.checkState(this.isCreateCalled ^ 1);
            this.compositeSequenceableLoaderFactory = (CompositeSequenceableLoaderFactory) Assertions.checkNotNull(compositeSequenceableLoaderFactory);
            return this;
        }

        public Factory setAllowChunklessPreparation(boolean z) {
            Assertions.checkState(this.isCreateCalled ^ 1);
            this.allowChunklessPreparation = z;
            return this;
        }

        public HlsMediaSource createMediaSource(Uri uri) {
            this.isCreateCalled = true;
            return new HlsMediaSource(uri, this.hlsDataSourceFactory, this.extractorFactory, this.compositeSequenceableLoaderFactory, this.loadErrorHandlingPolicy, this.playlistTrackerFactory.createTracker(this.hlsDataSourceFactory, this.loadErrorHandlingPolicy, this.playlistParserFactory), this.allowChunklessPreparation, this.tag);
        }

        @Deprecated
        public HlsMediaSource createMediaSource(Uri uri, @Nullable Handler handler, @Nullable MediaSourceEventListener mediaSourceEventListener) {
            uri = createMediaSource(uri);
            if (!(handler == null || mediaSourceEventListener == null)) {
                uri.addEventListener(handler, mediaSourceEventListener);
            }
            return uri;
        }

        public int[] getSupportedTypes() {
            return new int[]{2};
        }
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.hls");
    }

    @Deprecated
    public HlsMediaSource(Uri uri, com.google.android.exoplayer2.upstream.DataSource.Factory factory, Handler handler, MediaSourceEventListener mediaSourceEventListener) {
        this(uri, factory, 3, handler, mediaSourceEventListener);
    }

    @Deprecated
    public HlsMediaSource(Uri uri, com.google.android.exoplayer2.upstream.DataSource.Factory factory, int i, Handler handler, MediaSourceEventListener mediaSourceEventListener) {
        this(uri, new DefaultHlsDataSourceFactory(factory), HlsExtractorFactory.DEFAULT, i, handler, mediaSourceEventListener, new HlsPlaylistParser());
    }

    @Deprecated
    public HlsMediaSource(Uri uri, HlsDataSourceFactory hlsDataSourceFactory, HlsExtractorFactory hlsExtractorFactory, int i, Handler handler, MediaSourceEventListener mediaSourceEventListener, Parser<HlsPlaylist> parser) {
        int i2 = i;
        Handler handler2 = handler;
        MediaSourceEventListener mediaSourceEventListener2 = mediaSourceEventListener;
        CompositeSequenceableLoaderFactory defaultCompositeSequenceableLoaderFactory = new DefaultCompositeSequenceableLoaderFactory();
        LoadErrorHandlingPolicy defaultLoadErrorHandlingPolicy = new DefaultLoadErrorHandlingPolicy(i2);
        LoadErrorHandlingPolicy defaultLoadErrorHandlingPolicy2 = new DefaultLoadErrorHandlingPolicy(i2);
        HlsDataSourceFactory hlsDataSourceFactory2 = hlsDataSourceFactory;
        this(uri, hlsDataSourceFactory, hlsExtractorFactory, defaultCompositeSequenceableLoaderFactory, defaultLoadErrorHandlingPolicy, new DefaultHlsPlaylistTracker(hlsDataSourceFactory, defaultLoadErrorHandlingPolicy2, (Parser) parser), false, null);
        if (handler2 == null || mediaSourceEventListener2 == null) {
            HlsMediaSource hlsMediaSource = this;
            return;
        }
        hlsMediaSource = this;
        addEventListener(handler2, mediaSourceEventListener2);
    }

    private HlsMediaSource(Uri uri, HlsDataSourceFactory hlsDataSourceFactory, HlsExtractorFactory hlsExtractorFactory, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, LoadErrorHandlingPolicy loadErrorHandlingPolicy, HlsPlaylistTracker hlsPlaylistTracker, boolean z, @Nullable Object obj) {
        this.manifestUri = uri;
        this.dataSourceFactory = hlsDataSourceFactory;
        this.extractorFactory = hlsExtractorFactory;
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy;
        this.playlistTracker = hlsPlaylistTracker;
        this.allowChunklessPreparation = z;
        this.tag = obj;
    }

    public void prepareSourceInternal(ExoPlayer exoPlayer, boolean z, @Nullable TransferListener transferListener) {
        this.mediaTransferListener = transferListener;
        this.playlistTracker.start(this.manifestUri, createEventDispatcher(null), this);
    }

    public void maybeThrowSourceInfoRefreshError() throws IOException {
        this.playlistTracker.maybeThrowPrimaryPlaylistRefreshError();
    }

    public MediaPeriod createPeriod(MediaPeriodId mediaPeriodId, Allocator allocator) {
        return new HlsMediaPeriod(this.extractorFactory, this.playlistTracker, this.dataSourceFactory, this.mediaTransferListener, this.loadErrorHandlingPolicy, createEventDispatcher(mediaPeriodId), allocator, this.compositeSequenceableLoaderFactory, this.allowChunklessPreparation);
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        ((HlsMediaPeriod) mediaPeriod).release();
    }

    public void releaseSourceInternal() {
        this.playlistTracker.stop();
    }

    public void onPrimaryPlaylistRefreshed(HlsMediaPlaylist hlsMediaPlaylist) {
        long j;
        long j2;
        Timeline singlePeriodTimeline;
        List list;
        long j3;
        long j4;
        HlsMediaSource hlsMediaSource = this;
        HlsMediaPlaylist hlsMediaPlaylist2 = hlsMediaPlaylist;
        long usToMs = hlsMediaPlaylist2.hasProgramDateTime ? C.usToMs(hlsMediaPlaylist2.startTimeUs) : C.TIME_UNSET;
        if (hlsMediaPlaylist2.playlistType != 2) {
            if (hlsMediaPlaylist2.playlistType != 1) {
                j = C.TIME_UNSET;
                j2 = hlsMediaPlaylist2.startOffsetUs;
                if (hlsMediaSource.playlistTracker.isLive()) {
                    singlePeriodTimeline = new SinglePeriodTimeline(j, usToMs, hlsMediaPlaylist2.durationUs, hlsMediaPlaylist2.durationUs, 0, j2 != C.TIME_UNSET ? 0 : j2, true, false, hlsMediaSource.tag);
                } else {
                    long initialStartTimeUs = hlsMediaPlaylist2.startTimeUs - hlsMediaSource.playlistTracker.getInitialStartTimeUs();
                    long j5 = hlsMediaPlaylist2.hasEndTag ? initialStartTimeUs + hlsMediaPlaylist2.durationUs : C.TIME_UNSET;
                    list = hlsMediaPlaylist2.segments;
                    if (j2 != C.TIME_UNSET) {
                        if (list.isEmpty()) {
                            j3 = ((Segment) list.get(Math.max(0, list.size() - 3))).relativeStartTimeUs;
                        } else {
                            j3 = 0;
                        }
                        j4 = j3;
                    } else {
                        j4 = j2;
                    }
                    singlePeriodTimeline = new SinglePeriodTimeline(j, usToMs, j5, hlsMediaPlaylist2.durationUs, initialStartTimeUs, j4, true, hlsMediaPlaylist2.hasEndTag ^ 1, hlsMediaSource.tag);
                }
                refreshSourceInfo(r2, new HlsManifest(hlsMediaSource.playlistTracker.getMasterPlaylist(), hlsMediaPlaylist2));
            }
        }
        j = usToMs;
        j2 = hlsMediaPlaylist2.startOffsetUs;
        if (hlsMediaSource.playlistTracker.isLive()) {
            if (j2 != C.TIME_UNSET) {
            }
            singlePeriodTimeline = new SinglePeriodTimeline(j, usToMs, hlsMediaPlaylist2.durationUs, hlsMediaPlaylist2.durationUs, 0, j2 != C.TIME_UNSET ? 0 : j2, true, false, hlsMediaSource.tag);
        } else {
            long initialStartTimeUs2 = hlsMediaPlaylist2.startTimeUs - hlsMediaSource.playlistTracker.getInitialStartTimeUs();
            if (hlsMediaPlaylist2.hasEndTag) {
            }
            list = hlsMediaPlaylist2.segments;
            if (j2 != C.TIME_UNSET) {
                j4 = j2;
            } else {
                if (list.isEmpty()) {
                    j3 = ((Segment) list.get(Math.max(0, list.size() - 3))).relativeStartTimeUs;
                } else {
                    j3 = 0;
                }
                j4 = j3;
            }
            singlePeriodTimeline = new SinglePeriodTimeline(j, usToMs, j5, hlsMediaPlaylist2.durationUs, initialStartTimeUs2, j4, true, hlsMediaPlaylist2.hasEndTag ^ 1, hlsMediaSource.tag);
        }
        refreshSourceInfo(r2, new HlsManifest(hlsMediaSource.playlistTracker.getMasterPlaylist(), hlsMediaPlaylist2));
    }
}
