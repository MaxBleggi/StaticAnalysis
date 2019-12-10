package com.google.android.exoplayer2.source;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.source.MediaPeriod.Callback;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;

final class MergingMediaPeriod implements MediaPeriod, Callback {
    private Callback callback;
    private final ArrayList<MediaPeriod> childrenPendingPreparation = new ArrayList();
    private SequenceableLoader compositeSequenceableLoader;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private MediaPeriod[] enabledPeriods;
    public final MediaPeriod[] periods;
    private final IdentityHashMap<SampleStream, Integer> streamPeriodIndices;
    private TrackGroupArray trackGroups;

    public MergingMediaPeriod(CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory, MediaPeriod... mediaPeriodArr) {
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory;
        this.periods = mediaPeriodArr;
        this.compositeSequenceableLoader = compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(new SequenceableLoader[null]);
        this.streamPeriodIndices = new IdentityHashMap();
    }

    public void prepare(Callback callback, long j) {
        this.callback = callback;
        Collections.addAll(this.childrenPendingPreparation, this.periods);
        for (MediaPeriod prepare : this.periods) {
            prepare.prepare(this, j);
        }
    }

    public void maybeThrowPrepareError() throws IOException {
        for (MediaPeriod maybeThrowPrepareError : this.periods) {
            maybeThrowPrepareError.maybeThrowPrepareError();
        }
    }

    public TrackGroupArray getTrackGroups() {
        return this.trackGroups;
    }

    public long selectTracks(TrackSelection[] trackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j) {
        ArrayList arrayList;
        MergingMediaPeriod mergingMediaPeriod = this;
        TrackSelection[] trackSelectionArr2 = trackSelectionArr;
        SampleStream[] sampleStreamArr2 = sampleStreamArr;
        int[] iArr = new int[trackSelectionArr2.length];
        int[] iArr2 = new int[trackSelectionArr2.length];
        for (int i = 0; i < trackSelectionArr2.length; i++) {
            int i2;
            if (sampleStreamArr2[i] == null) {
                i2 = -1;
            } else {
                i2 = ((Integer) mergingMediaPeriod.streamPeriodIndices.get(sampleStreamArr2[i])).intValue();
            }
            iArr[i] = i2;
            iArr2[i] = -1;
            if (trackSelectionArr2[i] != null) {
                TrackGroup trackGroup = trackSelectionArr2[i].getTrackGroup();
                for (int i3 = 0; i3 < mergingMediaPeriod.periods.length; i3++) {
                    if (mergingMediaPeriod.periods[i3].getTrackGroups().indexOf(trackGroup) != -1) {
                        iArr2[i] = i3;
                        break;
                    }
                }
            }
        }
        mergingMediaPeriod.streamPeriodIndices.clear();
        Object obj = new SampleStream[trackSelectionArr2.length];
        SampleStream[] sampleStreamArr3 = new SampleStream[trackSelectionArr2.length];
        TrackSelection[] trackSelectionArr3 = new TrackSelection[trackSelectionArr2.length];
        ArrayList arrayList2 = new ArrayList(mergingMediaPeriod.periods.length);
        long j2 = j;
        int i4 = 0;
        while (i4 < mergingMediaPeriod.periods.length) {
            int i5 = 0;
            while (i5 < trackSelectionArr2.length) {
                TrackSelection trackSelection = null;
                sampleStreamArr3[i5] = iArr[i5] == i4 ? sampleStreamArr2[i5] : null;
                if (iArr2[i5] == i4) {
                    trackSelection = trackSelectionArr2[i5];
                }
                trackSelectionArr3[i5] = trackSelection;
                i5++;
            }
            arrayList = arrayList2;
            TrackSelection[] trackSelectionArr4 = trackSelectionArr3;
            int i6 = i4;
            long selectTracks = mergingMediaPeriod.periods[i4].selectTracks(trackSelectionArr3, zArr, sampleStreamArr3, zArr2, j2);
            if (i6 == 0) {
                j2 = selectTracks;
            } else if (selectTracks != j2) {
                throw new IllegalStateException("Children enabled at different positions.");
            }
            Object obj2 = null;
            for (i5 = 0; i5 < trackSelectionArr2.length; i5++) {
                boolean z = true;
                if (iArr2[i5] == i6) {
                    Assertions.checkState(sampleStreamArr3[i5] != null);
                    obj[i5] = sampleStreamArr3[i5];
                    mergingMediaPeriod.streamPeriodIndices.put(sampleStreamArr3[i5], Integer.valueOf(i6));
                    obj2 = 1;
                } else if (iArr[i5] == i6) {
                    if (sampleStreamArr3[i5] != null) {
                        z = false;
                    }
                    Assertions.checkState(z);
                }
            }
            if (obj2 != null) {
                arrayList.add(mergingMediaPeriod.periods[i6]);
            }
            i4 = i6 + 1;
            arrayList2 = arrayList;
            trackSelectionArr3 = trackSelectionArr4;
        }
        arrayList = arrayList2;
        System.arraycopy(obj, 0, sampleStreamArr2, 0, obj.length);
        mergingMediaPeriod.enabledPeriods = new MediaPeriod[arrayList.size()];
        arrayList.toArray(mergingMediaPeriod.enabledPeriods);
        mergingMediaPeriod.compositeSequenceableLoader = mergingMediaPeriod.compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(mergingMediaPeriod.enabledPeriods);
        return j2;
    }

    public void discardBuffer(long j, boolean z) {
        for (MediaPeriod discardBuffer : this.enabledPeriods) {
            discardBuffer.discardBuffer(j, z);
        }
    }

    public void reevaluateBuffer(long j) {
        this.compositeSequenceableLoader.reevaluateBuffer(j);
    }

    public boolean continueLoading(long j) {
        if (this.childrenPendingPreparation.isEmpty()) {
            return this.compositeSequenceableLoader.continueLoading(j);
        }
        int size = this.childrenPendingPreparation.size();
        for (int i = 0; i < size; i++) {
            ((MediaPeriod) this.childrenPendingPreparation.get(i)).continueLoading(j);
        }
        return false;
    }

    public long getNextLoadPositionUs() {
        return this.compositeSequenceableLoader.getNextLoadPositionUs();
    }

    public long readDiscontinuity() {
        long readDiscontinuity = this.periods[0].readDiscontinuity();
        int i = 1;
        while (i < this.periods.length) {
            if (this.periods[i].readDiscontinuity() == C.TIME_UNSET) {
                i++;
            } else {
                throw new IllegalStateException("Child reported discontinuity.");
            }
        }
        if (readDiscontinuity != C.TIME_UNSET) {
            for (MediaPeriod mediaPeriod : this.enabledPeriods) {
                if (mediaPeriod != this.periods[0]) {
                    if (mediaPeriod.seekToUs(readDiscontinuity) != readDiscontinuity) {
                        throw new IllegalStateException("Unexpected child seekToUs result.");
                    }
                }
            }
        }
        return readDiscontinuity;
    }

    public long getBufferedPositionUs() {
        return this.compositeSequenceableLoader.getBufferedPositionUs();
    }

    public long seekToUs(long j) {
        j = this.enabledPeriods[0].seekToUs(j);
        int i = 1;
        while (i < this.enabledPeriods.length) {
            if (this.enabledPeriods[i].seekToUs(j) == j) {
                i++;
            } else {
                throw new IllegalStateException("Unexpected child seekToUs result.");
            }
        }
        return j;
    }

    public long getAdjustedSeekPositionUs(long j, SeekParameters seekParameters) {
        return this.enabledPeriods[0].getAdjustedSeekPositionUs(j, seekParameters);
    }

    public void onPrepared(MediaPeriod mediaPeriod) {
        this.childrenPendingPreparation.remove(mediaPeriod);
        if (this.childrenPendingPreparation.isEmpty() != null) {
            int i = 0;
            for (MediaPeriod trackGroups : this.periods) {
                i += trackGroups.getTrackGroups().length;
            }
            TrackGroup[] trackGroupArr = new TrackGroup[i];
            MediaPeriod[] mediaPeriodArr = this.periods;
            int length = mediaPeriodArr.length;
            i = 0;
            int i2 = 0;
            while (i < length) {
                TrackGroupArray trackGroups2 = mediaPeriodArr[i].getTrackGroups();
                int i3 = trackGroups2.length;
                int i4 = i2;
                i2 = 0;
                while (i2 < i3) {
                    int i5 = i4 + 1;
                    trackGroupArr[i4] = trackGroups2.get(i2);
                    i2++;
                    i4 = i5;
                }
                i++;
                i2 = i4;
            }
            this.trackGroups = new TrackGroupArray(trackGroupArr);
            this.callback.onPrepared(this);
        }
    }

    public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
        this.callback.onContinueLoadingRequested(this);
    }
}
