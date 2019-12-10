package com.google.android.exoplayer2.audio;

import android.annotation.TargetApi;
import android.media.AudioTimestamp;
import android.media.AudioTrack;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.Util;

final class AudioTimestampPoller {
    private static final int ERROR_POLL_INTERVAL_US = 500000;
    private static final int FAST_POLL_INTERVAL_US = 5000;
    private static final int INITIALIZING_DURATION_US = 500000;
    private static final int SLOW_POLL_INTERVAL_US = 10000000;
    private static final int STATE_ERROR = 4;
    private static final int STATE_INITIALIZING = 0;
    private static final int STATE_NO_TIMESTAMP = 3;
    private static final int STATE_TIMESTAMP = 1;
    private static final int STATE_TIMESTAMP_ADVANCING = 2;
    @Nullable
    private final AudioTimestampV19 audioTimestamp;
    private long initialTimestampPositionFrames;
    private long initializeSystemTimeUs;
    private long lastTimestampSampleTimeUs;
    private long sampleIntervalUs;
    private int state;

    @TargetApi(19)
    private static final class AudioTimestampV19 {
        private final AudioTimestamp audioTimestamp = new AudioTimestamp();
        private final AudioTrack audioTrack;
        private long lastTimestampPositionFrames;
        private long lastTimestampRawPositionFrames;
        private long rawTimestampFramePositionWrapCount;

        public AudioTimestampV19(AudioTrack audioTrack) {
            this.audioTrack = audioTrack;
        }

        public boolean maybeUpdateTimestamp() {
            boolean timestamp = this.audioTrack.getTimestamp(this.audioTimestamp);
            if (timestamp) {
                long j = this.audioTimestamp.framePosition;
                if (this.lastTimestampRawPositionFrames > j) {
                    this.rawTimestampFramePositionWrapCount++;
                }
                this.lastTimestampRawPositionFrames = j;
                this.lastTimestampPositionFrames = j + (this.rawTimestampFramePositionWrapCount << 32);
            }
            return timestamp;
        }

        public long getTimestampSystemTimeUs() {
            return this.audioTimestamp.nanoTime / 1000;
        }

        public long getTimestampPositionFrames() {
            return this.lastTimestampPositionFrames;
        }
    }

    public AudioTimestampPoller(AudioTrack audioTrack) {
        if (Util.SDK_INT >= 19) {
            this.audioTimestamp = new AudioTimestampV19(audioTrack);
            reset();
            return;
        }
        this.audioTimestamp = null;
        updateState(3);
    }

    public boolean maybePollTimestamp(long j) {
        if (this.audioTimestamp != null) {
            if (j - this.lastTimestampSampleTimeUs >= this.sampleIntervalUs) {
                this.lastTimestampSampleTimeUs = j;
                boolean maybeUpdateTimestamp = this.audioTimestamp.maybeUpdateTimestamp();
                switch (this.state) {
                    case 0:
                        if (!maybeUpdateTimestamp) {
                            if (j - this.initializeSystemTimeUs > 500000) {
                                updateState(3);
                                break;
                            }
                        } else if (this.audioTimestamp.getTimestampSystemTimeUs() < this.initializeSystemTimeUs) {
                            maybeUpdateTimestamp = false;
                            break;
                        } else {
                            this.initialTimestampPositionFrames = this.audioTimestamp.getTimestampPositionFrames();
                            updateState(1);
                            break;
                        }
                        break;
                    case 1:
                        if (maybeUpdateTimestamp) {
                            if (this.audioTimestamp.getTimestampPositionFrames() > this.initialTimestampPositionFrames) {
                                updateState(2);
                                break;
                            }
                        }
                        reset();
                        break;
                        break;
                    case 2:
                        if (!maybeUpdateTimestamp) {
                            reset();
                            break;
                        }
                        break;
                    case 3:
                        if (maybeUpdateTimestamp) {
                            reset();
                            break;
                        }
                        break;
                    case 4:
                        break;
                    default:
                        throw new IllegalStateException();
                }
                return maybeUpdateTimestamp;
            }
        }
        return false;
    }

    public void rejectTimestamp() {
        updateState(4);
    }

    public void acceptTimestamp() {
        if (this.state == 4) {
            reset();
        }
    }

    public boolean hasTimestamp() {
        if (this.state != 1) {
            return this.state == 2;
        } else {
            return true;
        }
    }

    public boolean isTimestampAdvancing() {
        return this.state == 2;
    }

    public void reset() {
        if (this.audioTimestamp != null) {
            updateState(0);
        }
    }

    public long getTimestampSystemTimeUs() {
        return this.audioTimestamp != null ? this.audioTimestamp.getTimestampSystemTimeUs() : C.TIME_UNSET;
    }

    public long getTimestampPositionFrames() {
        return this.audioTimestamp != null ? this.audioTimestamp.getTimestampPositionFrames() : -1;
    }

    private void updateState(int i) {
        this.state = i;
        switch (i) {
            case 0:
                this.lastTimestampSampleTimeUs = 0;
                this.initialTimestampPositionFrames = -1;
                this.initializeSystemTimeUs = System.nanoTime() / 1000;
                this.sampleIntervalUs = 5000;
                return;
            case 1:
                this.sampleIntervalUs = 5000;
                return;
            case 2:
            case 3:
                this.sampleIntervalUs = 10000000;
                return;
            case 4:
                this.sampleIntervalUs = 500000;
                return;
            default:
                throw new IllegalStateException();
        }
    }
}
