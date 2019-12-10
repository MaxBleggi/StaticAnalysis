package com.google.android.exoplayer2.source;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.source.MediaPeriod.Callback;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

public final class ClippingMediaPeriod implements MediaPeriod, Callback {
    private Callback callback;
    long endUs;
    public final MediaPeriod mediaPeriod;
    private long pendingInitialDiscontinuityPositionUs;
    private ClippingSampleStream[] sampleStreams = new ClippingSampleStream[null];
    long startUs;

    private final class ClippingSampleStream implements SampleStream {
        public final SampleStream childStream;
        private boolean sentEos;

        public ClippingSampleStream(SampleStream sampleStream) {
            this.childStream = sampleStream;
        }

        public void clearSentEos() {
            this.sentEos = false;
        }

        public boolean isReady() {
            return !ClippingMediaPeriod.this.isPendingInitialDiscontinuity() && this.childStream.isReady();
        }

        public void maybeThrowError() throws IOException {
            this.childStream.maybeThrowError();
        }

        public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, boolean z) {
            if (ClippingMediaPeriod.this.isPendingInitialDiscontinuity()) {
                return -3;
            }
            if (this.sentEos) {
                decoderInputBuffer.setFlags(4);
                return -4;
            }
            z = this.childStream.readData(formatHolder, decoderInputBuffer, z);
            if (z) {
                decoderInputBuffer = formatHolder.format;
                if (decoderInputBuffer.encoderDelay || decoderInputBuffer.encoderPadding) {
                    z = false;
                    int i = ClippingMediaPeriod.this.startUs != 0 ? 0 : decoderInputBuffer.encoderDelay;
                    if (ClippingMediaPeriod.this.endUs == Long.MIN_VALUE) {
                        z = decoderInputBuffer.encoderPadding;
                    }
                    formatHolder.format = decoderInputBuffer.copyWithGaplessInfo(i, z);
                }
                return -5;
            } else if (ClippingMediaPeriod.this.endUs == Long.MIN_VALUE || ((!z || decoderInputBuffer.timeUs < ClippingMediaPeriod.this.endUs) && (!z || ClippingMediaPeriod.this.getBufferedPositionUs() != Long.MIN_VALUE))) {
                return z;
            } else {
                decoderInputBuffer.clear();
                decoderInputBuffer.setFlags(4);
                this.sentEos = true;
                return -4;
            }
        }

        public int skipData(long j) {
            if (ClippingMediaPeriod.this.isPendingInitialDiscontinuity()) {
                return -3;
            }
            return this.childStream.skipData(j);
        }
    }

    public ClippingMediaPeriod(MediaPeriod mediaPeriod, boolean z, long j, long j2) {
        this.mediaPeriod = mediaPeriod;
        this.pendingInitialDiscontinuityPositionUs = z ? j : 1;
        this.startUs = j;
        this.endUs = j2;
    }

    public void updateClipping(long j, long j2) {
        this.startUs = j;
        this.endUs = j2;
    }

    public void prepare(Callback callback, long j) {
        this.callback = callback;
        this.mediaPeriod.prepare(this, j);
    }

    public void maybeThrowPrepareError() throws IOException {
        this.mediaPeriod.maybeThrowPrepareError();
    }

    public TrackGroupArray getTrackGroups() {
        return this.mediaPeriod.getTrackGroups();
    }

    public long selectTracks(TrackSelection[] trackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j) {
        long j2;
        boolean z;
        SampleStream[] sampleStreamArr2 = sampleStreamArr;
        this.sampleStreams = new ClippingSampleStream[sampleStreamArr2.length];
        SampleStream[] sampleStreamArr3 = new SampleStream[sampleStreamArr2.length];
        int i = 0;
        int i2 = 0;
        while (true) {
            SampleStream sampleStream = null;
            if (i2 >= sampleStreamArr2.length) {
                break;
            }
            r0.sampleStreams[i2] = (ClippingSampleStream) sampleStreamArr2[i2];
            if (r0.sampleStreams[i2] != null) {
                sampleStream = r0.sampleStreams[i2].childStream;
            }
            sampleStreamArr3[i2] = sampleStream;
            i2++;
        }
        long selectTracks = r0.mediaPeriod.selectTracks(trackSelectionArr, zArr, sampleStreamArr3, zArr2, j);
        if (isPendingInitialDiscontinuity() && j == r0.startUs) {
            TrackSelection[] trackSelectionArr2 = trackSelectionArr;
            if (shouldKeepInitialDiscontinuity(r0.startUs, trackSelectionArr)) {
                j2 = selectTracks;
                r0.pendingInitialDiscontinuityPositionUs = j2;
                if (selectTracks != j) {
                    if (selectTracks >= r0.startUs) {
                        if (r0.endUs != Long.MIN_VALUE) {
                            if (selectTracks <= r0.endUs) {
                            }
                        }
                    }
                    z = false;
                    Assertions.checkState(z);
                    while (i < sampleStreamArr2.length) {
                        if (sampleStreamArr3[i] != null) {
                            r0.sampleStreams[i] = null;
                        } else if (sampleStreamArr2[i] != null || r0.sampleStreams[i].childStream != sampleStreamArr3[i]) {
                            r0.sampleStreams[i] = new ClippingSampleStream(sampleStreamArr3[i]);
                        }
                        sampleStreamArr2[i] = r0.sampleStreams[i];
                        i++;
                    }
                    return selectTracks;
                }
                z = true;
                Assertions.checkState(z);
                while (i < sampleStreamArr2.length) {
                    if (sampleStreamArr3[i] != null) {
                        if (sampleStreamArr2[i] != null) {
                        }
                        r0.sampleStreams[i] = new ClippingSampleStream(sampleStreamArr3[i]);
                    } else {
                        r0.sampleStreams[i] = null;
                    }
                    sampleStreamArr2[i] = r0.sampleStreams[i];
                    i++;
                }
                return selectTracks;
            }
        }
        j2 = C.TIME_UNSET;
        r0.pendingInitialDiscontinuityPositionUs = j2;
        if (selectTracks != j) {
            if (selectTracks >= r0.startUs) {
                if (r0.endUs != Long.MIN_VALUE) {
                    if (selectTracks <= r0.endUs) {
                    }
                }
            }
            z = false;
            Assertions.checkState(z);
            while (i < sampleStreamArr2.length) {
                if (sampleStreamArr3[i] != null) {
                    r0.sampleStreams[i] = null;
                } else {
                    if (sampleStreamArr2[i] != null) {
                    }
                    r0.sampleStreams[i] = new ClippingSampleStream(sampleStreamArr3[i]);
                }
                sampleStreamArr2[i] = r0.sampleStreams[i];
                i++;
            }
            return selectTracks;
        }
        z = true;
        Assertions.checkState(z);
        while (i < sampleStreamArr2.length) {
            if (sampleStreamArr3[i] != null) {
                if (sampleStreamArr2[i] != null) {
                }
                r0.sampleStreams[i] = new ClippingSampleStream(sampleStreamArr3[i]);
            } else {
                r0.sampleStreams[i] = null;
            }
            sampleStreamArr2[i] = r0.sampleStreams[i];
            i++;
        }
        return selectTracks;
    }

    public void discardBuffer(long j, boolean z) {
        this.mediaPeriod.discardBuffer(j, z);
    }

    public void reevaluateBuffer(long j) {
        this.mediaPeriod.reevaluateBuffer(j);
    }

    public long readDiscontinuity() {
        long j;
        if (isPendingInitialDiscontinuity()) {
            j = this.pendingInitialDiscontinuityPositionUs;
            this.pendingInitialDiscontinuityPositionUs = C.TIME_UNSET;
            long readDiscontinuity = readDiscontinuity();
            if (readDiscontinuity != C.TIME_UNSET) {
                j = readDiscontinuity;
            }
            return j;
        }
        j = this.mediaPeriod.readDiscontinuity();
        if (j == C.TIME_UNSET) {
            return C.TIME_UNSET;
        }
        boolean z = false;
        Assertions.checkState(j >= this.startUs);
        if (this.endUs == Long.MIN_VALUE || j <= this.endUs) {
            z = true;
        }
        Assertions.checkState(z);
        return j;
    }

    public long getBufferedPositionUs() {
        long bufferedPositionUs = this.mediaPeriod.getBufferedPositionUs();
        if (bufferedPositionUs != Long.MIN_VALUE) {
            if (this.endUs == Long.MIN_VALUE || bufferedPositionUs < this.endUs) {
                return bufferedPositionUs;
            }
        }
        return Long.MIN_VALUE;
    }

    public long seekToUs(long j) {
        this.pendingInitialDiscontinuityPositionUs = C.TIME_UNSET;
        boolean z = false;
        for (ClippingSampleStream clippingSampleStream : this.sampleStreams) {
            if (clippingSampleStream != null) {
                clippingSampleStream.clearSentEos();
            }
        }
        long seekToUs = this.mediaPeriod.seekToUs(j);
        if (seekToUs == j || (seekToUs >= this.startUs && (this.endUs == Long.MIN_VALUE || seekToUs <= this.endUs))) {
            z = true;
        }
        Assertions.checkState(z);
        return seekToUs;
    }

    public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
        if (j == this.startUs) {
            return this.startUs;
        }
        return this.mediaPeriod.getAdjustedSeekPositionUs(j, clipSeekParameters(j, seekParameters));
    }

    public long getNextLoadPositionUs() {
        long nextLoadPositionUs = this.mediaPeriod.getNextLoadPositionUs();
        if (nextLoadPositionUs != Long.MIN_VALUE) {
            if (this.endUs == Long.MIN_VALUE || nextLoadPositionUs < this.endUs) {
                return nextLoadPositionUs;
            }
        }
        return Long.MIN_VALUE;
    }

    public boolean continueLoading(long j) {
        return this.mediaPeriod.continueLoading(j);
    }

    public void onPrepared(MediaPeriod mediaPeriod) {
        this.callback.onPrepared(this);
    }

    public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
        this.callback.onContinueLoadingRequested(this);
    }

    boolean isPendingInitialDiscontinuity() {
        return this.pendingInitialDiscontinuityPositionUs != C.TIME_UNSET;
    }

    private SeekParameters clipSeekParameters(long j, SeekParameters seekParameters) {
        long constrainValue = Util.constrainValue(seekParameters.toleranceBeforeUs, 0, j - this.startUs);
        j = Util.constrainValue(seekParameters.toleranceAfterUs, 0, this.endUs == Long.MIN_VALUE ? Long.MAX_VALUE : this.endUs - j);
        if (constrainValue == seekParameters.toleranceBeforeUs && j == seekParameters.toleranceAfterUs) {
            return seekParameters;
        }
        return new SeekParameters(constrainValue, j);
    }

    private static boolean shouldKeepInitialDiscontinuity(long j, TrackSelection[] trackSelectionArr) {
        if (j != 0) {
            for (TrackSelection trackSelection : trackSelectionArr) {
                if (trackSelection != null && !MimeTypes.isAudio(trackSelection.getSelectedFormat().sampleMimeType)) {
                    return 1;
                }
            }
        }
        return false;
    }
}
