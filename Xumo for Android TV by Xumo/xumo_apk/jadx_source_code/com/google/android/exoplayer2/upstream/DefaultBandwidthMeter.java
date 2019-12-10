package com.google.android.exoplayer2.upstream;

import android.os.Handler;
import android.support.v4.media.session.PlaybackStateCompat;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.upstream.BandwidthMeter.EventListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.EventDispatcher;
import com.google.android.exoplayer2.util.SlidingPercentile;

public final class DefaultBandwidthMeter implements BandwidthMeter, TransferListener {
    private static final int BYTES_TRANSFERRED_FOR_ESTIMATE = 524288;
    public static final long DEFAULT_INITIAL_BITRATE_ESTIMATE = 1000000;
    public static final int DEFAULT_SLIDING_WINDOW_MAX_WEIGHT = 2000;
    private static final int ELAPSED_MILLIS_FOR_ESTIMATE = 2000;
    private long bitrateEstimate;
    private final Clock clock;
    private final EventDispatcher<EventListener> eventDispatcher;
    private long sampleBytesTransferred;
    private long sampleStartTimeMs;
    private final SlidingPercentile slidingPercentile;
    private int streamCount;
    private long totalBytesTransferred;
    private long totalElapsedTimeMs;

    public static final class Builder {
        private Clock clock = Clock.DEFAULT;
        @Nullable
        private Handler eventHandler;
        @Nullable
        private EventListener eventListener;
        private long initialBitrateEstimate = 1000000;
        private int slidingWindowMaxWeight = 2000;

        public Builder setEventListener(Handler handler, EventListener eventListener) {
            boolean z = (handler == null || eventListener == null) ? false : true;
            Assertions.checkArgument(z);
            this.eventHandler = handler;
            this.eventListener = eventListener;
            return this;
        }

        public Builder setSlidingWindowMaxWeight(int i) {
            this.slidingWindowMaxWeight = i;
            return this;
        }

        public Builder setInitialBitrateEstimate(long j) {
            this.initialBitrateEstimate = j;
            return this;
        }

        public Builder setClock(Clock clock) {
            this.clock = clock;
            return this;
        }

        public DefaultBandwidthMeter build() {
            DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter(this.initialBitrateEstimate, this.slidingWindowMaxWeight, this.clock);
            if (!(this.eventHandler == null || this.eventListener == null)) {
                defaultBandwidthMeter.addEventListener(this.eventHandler, this.eventListener);
            }
            return defaultBandwidthMeter;
        }
    }

    @Nullable
    public TransferListener getTransferListener() {
        return this;
    }

    public void onTransferInitializing(DataSource dataSource, DataSpec dataSpec, boolean z) {
    }

    public DefaultBandwidthMeter() {
        this(1000000, 2000, Clock.DEFAULT);
    }

    @Deprecated
    public DefaultBandwidthMeter(Handler handler, EventListener eventListener) {
        this(1000000, 2000, Clock.DEFAULT);
        if (handler != null && eventListener != null) {
            addEventListener(handler, eventListener);
        }
    }

    @Deprecated
    public DefaultBandwidthMeter(Handler handler, EventListener eventListener, int i) {
        this(1000000, i, Clock.DEFAULT);
        if (handler != null && eventListener != null) {
            addEventListener(handler, eventListener);
        }
    }

    private DefaultBandwidthMeter(long j, int i, Clock clock) {
        this.eventDispatcher = new EventDispatcher();
        this.slidingPercentile = new SlidingPercentile(i);
        this.clock = clock;
        this.bitrateEstimate = j;
    }

    public synchronized long getBitrateEstimate() {
        return this.bitrateEstimate;
    }

    public void addEventListener(Handler handler, EventListener eventListener) {
        this.eventDispatcher.addListener(handler, eventListener);
    }

    public void removeEventListener(EventListener eventListener) {
        this.eventDispatcher.removeListener(eventListener);
    }

    public synchronized void onTransferStart(DataSource dataSource, DataSpec dataSpec, boolean z) {
        if (z) {
            if (this.streamCount == null) {
                this.sampleStartTimeMs = this.clock.elapsedRealtime();
            }
            this.streamCount++;
        }
    }

    public synchronized void onBytesTransferred(DataSource dataSource, DataSpec dataSpec, boolean z, int i) {
        if (z) {
            this.sampleBytesTransferred += (long) i;
        }
    }

    public synchronized void onTransferEnd(DataSource dataSource, DataSpec dataSpec, boolean z) {
        if (z) {
            Assertions.checkState(this.streamCount > null ? true : null);
            long elapsedRealtime = this.clock.elapsedRealtime();
            int i = (int) (elapsedRealtime - this.sampleStartTimeMs);
            long j = (long) i;
            this.totalElapsedTimeMs += j;
            this.totalBytesTransferred += this.sampleBytesTransferred;
            if (i > 0) {
                this.slidingPercentile.addSample((int) Math.sqrt((double) this.sampleBytesTransferred), (float) ((this.sampleBytesTransferred * 8000) / j));
                if (this.totalElapsedTimeMs >= AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS || this.totalBytesTransferred >= PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED) {
                    this.bitrateEstimate = (long) this.slidingPercentile.getPercentile(true);
                }
            }
            notifyBandwidthSample(i, this.sampleBytesTransferred, this.bitrateEstimate);
            dataSource = this.streamCount - 1;
            this.streamCount = dataSource;
            if (dataSource > null) {
                this.sampleStartTimeMs = elapsedRealtime;
            }
            this.sampleBytesTransferred = null;
        }
    }

    private void notifyBandwidthSample(int i, long j, long j2) {
        this.eventDispatcher.dispatch(new -$$Lambda$DefaultBandwidthMeter$0dWpVoCfeEm8PONlag-OKGMu96M(i, j, j2));
    }
}
