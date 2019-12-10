package com.google.android.exoplayer2.source;

import androidx.annotation.Nullable;
import androidx.core.os.EnvironmentCompat;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.Timeline.Period;
import com.google.android.exoplayer2.Timeline.Window;
import com.google.android.exoplayer2.source.MediaSource.MediaPeriodId;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

public final class ClippingMediaSource extends CompositeMediaSource<Void> {
    private final boolean allowDynamicClippingUpdates;
    private IllegalClippingException clippingError;
    private ClippingTimeline clippingTimeline;
    private final boolean enableInitialDiscontinuity;
    private final long endUs;
    @Nullable
    private Object manifest;
    private final ArrayList<ClippingMediaPeriod> mediaPeriods;
    private final MediaSource mediaSource;
    private long periodEndUs;
    private long periodStartUs;
    private final boolean relativeToDefaultPosition;
    private final long startUs;
    private final Window window;

    public static final class IllegalClippingException extends IOException {
        public static final int REASON_INVALID_PERIOD_COUNT = 0;
        public static final int REASON_NOT_SEEKABLE_TO_START = 1;
        public static final int REASON_START_EXCEEDS_END = 2;
        public final int reason;

        @Retention(RetentionPolicy.SOURCE)
        public @interface Reason {
        }

        private static String getReasonDescription(int i) {
            switch (i) {
                case 0:
                    return "invalid period count";
                case 1:
                    return "not seekable to start";
                case 2:
                    return "start exceeds end";
                default:
                    return EnvironmentCompat.MEDIA_UNKNOWN;
            }
        }

        public IllegalClippingException(int i) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Illegal clipping: ");
            stringBuilder.append(getReasonDescription(i));
            super(stringBuilder.toString());
            this.reason = i;
        }
    }

    private static final class ClippingTimeline extends ForwardingTimeline {
        private final long durationUs;
        private final long endUs;
        private final boolean isDynamic;
        private final long startUs;

        public ClippingTimeline(Timeline timeline, long j, long j2) throws IllegalClippingException {
            super(timeline);
            boolean z = true;
            if (timeline.getPeriodCount() == 1) {
                timeline = timeline.getWindow(0, new Window(), false);
                j = Math.max(0, j);
                j2 = j2 == Long.MIN_VALUE ? timeline.durationUs : Math.max(0, j2);
                if (timeline.durationUs != C.TIME_UNSET) {
                    if (j2 > timeline.durationUs) {
                        j2 = timeline.durationUs;
                    }
                    if (j != 0) {
                        if (!timeline.isSeekable) {
                            throw new IllegalClippingException(1);
                        }
                    }
                    if (j > j2) {
                        throw new IllegalClippingException(2);
                    }
                }
                this.startUs = j;
                this.endUs = j2;
                this.durationUs = j2 == C.TIME_UNSET ? C.TIME_UNSET : j2 - j;
                if (timeline.isDynamic != null) {
                    if (j2 != C.TIME_UNSET) {
                        if (timeline.durationUs != C.TIME_UNSET && j2 == timeline.durationUs) {
                        }
                    }
                    this.isDynamic = z;
                    return;
                }
                z = false;
                this.isDynamic = z;
                return;
            }
            throw new IllegalClippingException(0);
        }

        public Window getWindow(int i, Window window, boolean z, long j) {
            this.timeline.getWindow(0, window, z, 0);
            window.positionInFirstPeriodUs += this.startUs;
            window.durationUs = this.durationUs;
            window.isDynamic = this.isDynamic;
            if (!window.defaultPositionUs) {
                window.defaultPositionUs = Math.max(window.defaultPositionUs, this.startUs);
                if (this.endUs) {
                    z = window.defaultPositionUs;
                } else {
                    z = Math.min(window.defaultPositionUs, this.endUs);
                }
                window.defaultPositionUs = z;
                window.defaultPositionUs -= this.startUs;
            }
            z = C.usToMs(this.startUs);
            if (window.presentationStartTimeMs != C.TIME_UNSET) {
                window.presentationStartTimeMs += z;
            }
            if (window.windowStartTimeMs != C.TIME_UNSET) {
                window.windowStartTimeMs += z;
            }
            return window;
        }

        public Period getPeriod(int i, Period period, boolean z) {
            this.timeline.getPeriod(0, period, z);
            long positionInWindowUs = period.getPositionInWindowUs() - this.startUs;
            return period.set(period.id, period.uid, 0, this.durationUs == C.TIME_UNSET ? C.TIME_UNSET : this.durationUs - positionInWindowUs, positionInWindowUs);
        }
    }

    public ClippingMediaSource(MediaSource mediaSource, long j, long j2) {
        this(mediaSource, j, j2, true, false, false);
    }

    @Deprecated
    public ClippingMediaSource(MediaSource mediaSource, long j, long j2, boolean z) {
        this(mediaSource, j, j2, z, false, false);
    }

    public ClippingMediaSource(MediaSource mediaSource, long j) {
        this(mediaSource, 0, j, true, false, true);
    }

    public ClippingMediaSource(MediaSource mediaSource, long j, long j2, boolean z, boolean z2, boolean z3) {
        Assertions.checkArgument(j >= 0);
        this.mediaSource = (MediaSource) Assertions.checkNotNull(mediaSource);
        this.startUs = j;
        this.endUs = j2;
        this.enableInitialDiscontinuity = z;
        this.allowDynamicClippingUpdates = z2;
        this.relativeToDefaultPosition = z3;
        this.mediaPeriods = new ArrayList();
        this.window = new Window();
    }

    public void prepareSourceInternal(ExoPlayer exoPlayer, boolean z, @Nullable TransferListener transferListener) {
        super.prepareSourceInternal(exoPlayer, z, transferListener);
        prepareChildSource(false, this.mediaSource);
    }

    public void maybeThrowSourceInfoRefreshError() throws IOException {
        if (this.clippingError == null) {
            super.maybeThrowSourceInfoRefreshError();
            return;
        }
        throw this.clippingError;
    }

    public MediaPeriod createPeriod(MediaPeriodId mediaPeriodId, Allocator allocator) {
        MediaPeriod clippingMediaPeriod = new ClippingMediaPeriod(this.mediaSource.createPeriod(mediaPeriodId, allocator), this.enableInitialDiscontinuity, this.periodStartUs, this.periodEndUs);
        this.mediaPeriods.add(clippingMediaPeriod);
        return clippingMediaPeriod;
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        Assertions.checkState(this.mediaPeriods.remove(mediaPeriod));
        this.mediaSource.releasePeriod(((ClippingMediaPeriod) mediaPeriod).mediaPeriod);
        if (this.mediaPeriods.isEmpty() != null && this.allowDynamicClippingUpdates == null) {
            refreshClippedTimeline(this.clippingTimeline.timeline);
        }
    }

    public void releaseSourceInternal() {
        super.releaseSourceInternal();
        this.clippingError = null;
        this.clippingTimeline = null;
    }

    protected void onChildSourceInfoRefreshed(Void voidR, MediaSource mediaSource, Timeline timeline, @Nullable Object obj) {
        if (this.clippingError == null) {
            this.manifest = obj;
            refreshClippedTimeline(timeline);
        }
    }

    private void refreshClippedTimeline(Timeline timeline) {
        int i = 0;
        timeline.getWindow(0, this.window);
        long positionInFirstPeriodUs = this.window.getPositionInFirstPeriodUs();
        long j = Long.MIN_VALUE;
        if (!(this.clippingTimeline == null || r1.mediaPeriods.isEmpty())) {
            if (!r1.allowDynamicClippingUpdates) {
                long j2 = r1.periodStartUs - positionInFirstPeriodUs;
                if (r1.endUs != Long.MIN_VALUE) {
                    j = r1.periodEndUs - positionInFirstPeriodUs;
                }
                positionInFirstPeriodUs = j2;
                r1.clippingTimeline = new ClippingTimeline(timeline, positionInFirstPeriodUs, j);
                refreshSourceInfo(r1.clippingTimeline, r1.manifest);
            }
        }
        long j3 = r1.startUs;
        long j4 = r1.endUs;
        if (r1.relativeToDefaultPosition) {
            long defaultPositionUs = r1.window.getDefaultPositionUs();
            j3 += defaultPositionUs;
            j4 += defaultPositionUs;
        }
        r1.periodStartUs = positionInFirstPeriodUs + j3;
        if (r1.endUs != Long.MIN_VALUE) {
            j = positionInFirstPeriodUs + j4;
        }
        r1.periodEndUs = j;
        int size = r1.mediaPeriods.size();
        while (i < size) {
            ((ClippingMediaPeriod) r1.mediaPeriods.get(i)).updateClipping(r1.periodStartUs, r1.periodEndUs);
            i++;
        }
        positionInFirstPeriodUs = j3;
        j = j4;
        try {
            r1.clippingTimeline = new ClippingTimeline(timeline, positionInFirstPeriodUs, j);
            refreshSourceInfo(r1.clippingTimeline, r1.manifest);
        } catch (IllegalClippingException e) {
            r1.clippingError = e;
        }
    }

    protected long getMediaTimeForChildMediaTime(Void voidR, long j) {
        if (j == C.TIME_UNSET) {
            return C.TIME_UNSET;
        }
        long usToMs = C.usToMs(this.startUs);
        voidR = Math.max(0, j - usToMs);
        if (this.endUs != Long.MIN_VALUE) {
            voidR = Math.min(C.usToMs(this.endUs) - usToMs, voidR);
        }
        return voidR;
    }
}
