package com.google.android.exoplayer2.analytics;

import android.view.Surface;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.MediaSource.MediaPeriodId;
import com.google.android.exoplayer2.source.MediaSourceEventListener.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaSourceEventListener.MediaLoadData;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import java.io.IOException;

public interface AnalyticsListener {

    public final /* synthetic */ class -CC {
        public static void $default$onAudioAttributesChanged(AnalyticsListener analyticsListener, EventTime eventTime, AudioAttributes audioAttributes) {
        }

        public static void $default$onAudioSessionId(AnalyticsListener analyticsListener, EventTime eventTime, int i) {
        }

        public static void $default$onAudioUnderrun(AnalyticsListener analyticsListener, EventTime eventTime, int i, long j, long j2) {
        }

        public static void $default$onBandwidthEstimate(AnalyticsListener analyticsListener, EventTime eventTime, int i, long j, long j2) {
        }

        public static void $default$onDecoderDisabled(AnalyticsListener analyticsListener, EventTime eventTime, int i, DecoderCounters decoderCounters) {
        }

        public static void $default$onDecoderEnabled(AnalyticsListener analyticsListener, EventTime eventTime, int i, DecoderCounters decoderCounters) {
        }

        public static void $default$onDecoderInitialized(AnalyticsListener analyticsListener, EventTime eventTime, int i, String str, long j) {
        }

        public static void $default$onDecoderInputFormatChanged(AnalyticsListener analyticsListener, EventTime eventTime, int i, Format format) {
        }

        public static void $default$onDownstreamFormatChanged(AnalyticsListener analyticsListener, EventTime eventTime, MediaLoadData mediaLoadData) {
        }

        public static void $default$onDrmKeysLoaded(AnalyticsListener analyticsListener, EventTime eventTime) {
        }

        public static void $default$onDrmKeysRemoved(AnalyticsListener analyticsListener, EventTime eventTime) {
        }

        public static void $default$onDrmKeysRestored(AnalyticsListener analyticsListener, EventTime eventTime) {
        }

        public static void $default$onDrmSessionAcquired(AnalyticsListener analyticsListener, EventTime eventTime) {
        }

        public static void $default$onDrmSessionManagerError(AnalyticsListener analyticsListener, EventTime eventTime, Exception exception) {
        }

        public static void $default$onDrmSessionReleased(AnalyticsListener analyticsListener, EventTime eventTime) {
        }

        public static void $default$onDroppedVideoFrames(AnalyticsListener analyticsListener, EventTime eventTime, int i, long j) {
        }

        public static void $default$onLoadCanceled(AnalyticsListener analyticsListener, EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        }

        public static void $default$onLoadCompleted(AnalyticsListener analyticsListener, EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        }

        public static void $default$onLoadError(AnalyticsListener analyticsListener, EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z) {
        }

        public static void $default$onLoadStarted(AnalyticsListener analyticsListener, EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        }

        public static void $default$onLoadingChanged(AnalyticsListener analyticsListener, EventTime eventTime, boolean z) {
        }

        public static void $default$onMediaPeriodCreated(AnalyticsListener analyticsListener, EventTime eventTime) {
        }

        public static void $default$onMediaPeriodReleased(AnalyticsListener analyticsListener, EventTime eventTime) {
        }

        public static void $default$onMetadata(AnalyticsListener analyticsListener, EventTime eventTime, Metadata metadata) {
        }

        public static void $default$onPlaybackParametersChanged(AnalyticsListener analyticsListener, EventTime eventTime, PlaybackParameters playbackParameters) {
        }

        public static void $default$onPlayerError(AnalyticsListener analyticsListener, EventTime eventTime, ExoPlaybackException exoPlaybackException) {
        }

        public static void $default$onPlayerStateChanged(AnalyticsListener analyticsListener, EventTime eventTime, boolean z, int i) {
        }

        public static void $default$onPositionDiscontinuity(AnalyticsListener analyticsListener, EventTime eventTime, int i) {
        }

        public static void $default$onReadingStarted(AnalyticsListener analyticsListener, EventTime eventTime) {
        }

        public static void $default$onRenderedFirstFrame(AnalyticsListener analyticsListener, @Nullable EventTime eventTime, Surface surface) {
        }

        public static void $default$onRepeatModeChanged(AnalyticsListener analyticsListener, EventTime eventTime, int i) {
        }

        public static void $default$onSeekProcessed(AnalyticsListener analyticsListener, EventTime eventTime) {
        }

        public static void $default$onSeekStarted(AnalyticsListener analyticsListener, EventTime eventTime) {
        }

        public static void $default$onShuffleModeChanged(AnalyticsListener analyticsListener, EventTime eventTime, boolean z) {
        }

        public static void $default$onSurfaceSizeChanged(AnalyticsListener analyticsListener, EventTime eventTime, int i, int i2) {
        }

        public static void $default$onTimelineChanged(AnalyticsListener analyticsListener, EventTime eventTime, int i) {
        }

        public static void $default$onTracksChanged(AnalyticsListener analyticsListener, EventTime eventTime, TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
        }

        public static void $default$onUpstreamDiscarded(AnalyticsListener analyticsListener, EventTime eventTime, MediaLoadData mediaLoadData) {
        }

        public static void $default$onVideoSizeChanged(AnalyticsListener analyticsListener, EventTime eventTime, int i, int i2, int i3, float f) {
        }

        public static void $default$onVolumeChanged(AnalyticsListener analyticsListener, EventTime eventTime, float f) {
        }
    }

    public static final class EventTime {
        public final long currentPlaybackPositionMs;
        public final long eventPlaybackPositionMs;
        @Nullable
        public final MediaPeriodId mediaPeriodId;
        public final long realtimeMs;
        public final Timeline timeline;
        public final long totalBufferedDurationMs;
        public final int windowIndex;

        public EventTime(long j, Timeline timeline, int i, @Nullable MediaPeriodId mediaPeriodId, long j2, long j3, long j4) {
            this.realtimeMs = j;
            this.timeline = timeline;
            this.windowIndex = i;
            this.mediaPeriodId = mediaPeriodId;
            this.eventPlaybackPositionMs = j2;
            this.currentPlaybackPositionMs = j3;
            this.totalBufferedDurationMs = j4;
        }
    }

    void onAudioAttributesChanged(EventTime eventTime, AudioAttributes audioAttributes);

    void onAudioSessionId(EventTime eventTime, int i);

    void onAudioUnderrun(EventTime eventTime, int i, long j, long j2);

    void onBandwidthEstimate(EventTime eventTime, int i, long j, long j2);

    void onDecoderDisabled(EventTime eventTime, int i, DecoderCounters decoderCounters);

    void onDecoderEnabled(EventTime eventTime, int i, DecoderCounters decoderCounters);

    void onDecoderInitialized(EventTime eventTime, int i, String str, long j);

    void onDecoderInputFormatChanged(EventTime eventTime, int i, Format format);

    void onDownstreamFormatChanged(EventTime eventTime, MediaLoadData mediaLoadData);

    void onDrmKeysLoaded(EventTime eventTime);

    void onDrmKeysRemoved(EventTime eventTime);

    void onDrmKeysRestored(EventTime eventTime);

    void onDrmSessionAcquired(EventTime eventTime);

    void onDrmSessionManagerError(EventTime eventTime, Exception exception);

    void onDrmSessionReleased(EventTime eventTime);

    void onDroppedVideoFrames(EventTime eventTime, int i, long j);

    void onLoadCanceled(EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData);

    void onLoadCompleted(EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData);

    void onLoadError(EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z);

    void onLoadStarted(EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData);

    void onLoadingChanged(EventTime eventTime, boolean z);

    void onMediaPeriodCreated(EventTime eventTime);

    void onMediaPeriodReleased(EventTime eventTime);

    void onMetadata(EventTime eventTime, Metadata metadata);

    void onPlaybackParametersChanged(EventTime eventTime, PlaybackParameters playbackParameters);

    void onPlayerError(EventTime eventTime, ExoPlaybackException exoPlaybackException);

    void onPlayerStateChanged(EventTime eventTime, boolean z, int i);

    void onPositionDiscontinuity(EventTime eventTime, int i);

    void onReadingStarted(EventTime eventTime);

    void onRenderedFirstFrame(EventTime eventTime, @Nullable Surface surface);

    void onRepeatModeChanged(EventTime eventTime, int i);

    void onSeekProcessed(EventTime eventTime);

    void onSeekStarted(EventTime eventTime);

    void onShuffleModeChanged(EventTime eventTime, boolean z);

    void onSurfaceSizeChanged(EventTime eventTime, int i, int i2);

    void onTimelineChanged(EventTime eventTime, int i);

    void onTracksChanged(EventTime eventTime, TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray);

    void onUpstreamDiscarded(EventTime eventTime, MediaLoadData mediaLoadData);

    void onVideoSizeChanged(EventTime eventTime, int i, int i2, int i3, float f);

    void onVolumeChanged(EventTime eventTime, float f);
}
