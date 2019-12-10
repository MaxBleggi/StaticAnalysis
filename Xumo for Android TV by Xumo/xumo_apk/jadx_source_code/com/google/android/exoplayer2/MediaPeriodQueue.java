package com.google.android.exoplayer2;

import android.util.Pair;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Timeline.Period;
import com.google.android.exoplayer2.Timeline.Window;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSource.MediaPeriodId;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.util.Assertions;

final class MediaPeriodQueue {
    private static final int MAXIMUM_BUFFER_AHEAD_PERIODS = 100;
    private int length;
    @Nullable
    private MediaPeriodHolder loading;
    private long nextWindowSequenceNumber;
    @Nullable
    private Object oldFrontPeriodUid;
    private long oldFrontPeriodWindowSequenceNumber;
    private final Period period = new Period();
    @Nullable
    private MediaPeriodHolder playing;
    @Nullable
    private MediaPeriodHolder reading;
    private int repeatMode;
    private boolean shuffleModeEnabled;
    private Timeline timeline = Timeline.EMPTY;
    private final Window window = new Window();

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public boolean updateRepeatMode(int i) {
        this.repeatMode = i;
        return updateForPlaybackModeChange();
    }

    public boolean updateShuffleModeEnabled(boolean z) {
        this.shuffleModeEnabled = z;
        return updateForPlaybackModeChange();
    }

    public boolean isLoading(MediaPeriod mediaPeriod) {
        return (this.loading == null || this.loading.mediaPeriod != mediaPeriod) ? null : true;
    }

    public void reevaluateBuffer(long j) {
        if (this.loading != null) {
            this.loading.reevaluateBuffer(j);
        }
    }

    public boolean shouldLoadNextMediaPeriod() {
        if (this.loading != null) {
            if (this.loading.info.isFinal || !this.loading.isFullyBuffered() || this.loading.info.durationUs == C.TIME_UNSET || this.length >= 100) {
                return false;
            }
        }
        return true;
    }

    @Nullable
    public MediaPeriodInfo getNextMediaPeriodInfo(long j, PlaybackInfo playbackInfo) {
        if (this.loading == null) {
            return getFirstMediaPeriodInfo(playbackInfo);
        }
        return getFollowingMediaPeriodInfo(this.loading, j);
    }

    public MediaPeriod enqueueNextMediaPeriod(RendererCapabilities[] rendererCapabilitiesArr, TrackSelector trackSelector, Allocator allocator, MediaSource mediaSource, MediaPeriodInfo mediaPeriodInfo) {
        long j;
        if (this.loading == null) {
            j = mediaPeriodInfo.startPositionUs;
        } else {
            j = this.loading.getRendererOffset() + this.loading.info.durationUs;
        }
        MediaPeriodHolder mediaPeriodHolder = new MediaPeriodHolder(rendererCapabilitiesArr, j, trackSelector, allocator, mediaSource, mediaPeriodInfo);
        if (this.loading != null) {
            Assertions.checkState(hasPlayingPeriod());
            this.loading.next = mediaPeriodHolder;
        }
        this.oldFrontPeriodUid = null;
        this.loading = mediaPeriodHolder;
        this.length++;
        return mediaPeriodHolder.mediaPeriod;
    }

    public MediaPeriodHolder getLoadingPeriod() {
        return this.loading;
    }

    public MediaPeriodHolder getPlayingPeriod() {
        return this.playing;
    }

    public MediaPeriodHolder getReadingPeriod() {
        return this.reading;
    }

    public MediaPeriodHolder getFrontPeriod() {
        return hasPlayingPeriod() ? this.playing : this.loading;
    }

    public boolean hasPlayingPeriod() {
        return this.playing != null;
    }

    public MediaPeriodHolder advanceReadingPeriod() {
        boolean z = (this.reading == null || this.reading.next == null) ? false : true;
        Assertions.checkState(z);
        this.reading = this.reading.next;
        return this.reading;
    }

    public MediaPeriodHolder advancePlayingPeriod() {
        if (this.playing != null) {
            if (this.playing == this.reading) {
                this.reading = this.playing.next;
            }
            this.playing.release();
            this.length--;
            if (this.length == 0) {
                this.loading = null;
                this.oldFrontPeriodUid = this.playing.uid;
                this.oldFrontPeriodWindowSequenceNumber = this.playing.info.id.windowSequenceNumber;
            }
            this.playing = this.playing.next;
        } else {
            this.playing = this.loading;
            this.reading = this.loading;
        }
        return this.playing;
    }

    public boolean removeAfter(MediaPeriodHolder mediaPeriodHolder) {
        boolean z = false;
        Assertions.checkState(mediaPeriodHolder != null);
        this.loading = mediaPeriodHolder;
        while (mediaPeriodHolder.next != null) {
            mediaPeriodHolder = mediaPeriodHolder.next;
            if (mediaPeriodHolder == this.reading) {
                this.reading = this.playing;
                z = true;
            }
            mediaPeriodHolder.release();
            this.length--;
        }
        this.loading.next = null;
        return z;
    }

    public void clear(boolean z) {
        MediaPeriodHolder frontPeriod = getFrontPeriod();
        if (frontPeriod != null) {
            this.oldFrontPeriodUid = z ? frontPeriod.uid : false;
            this.oldFrontPeriodWindowSequenceNumber = frontPeriod.info.id.windowSequenceNumber;
            frontPeriod.release();
            removeAfter(frontPeriod);
        } else if (!z) {
            this.oldFrontPeriodUid = null;
        }
        this.playing = null;
        this.loading = null;
        this.reading = null;
        this.length = false;
    }

    public boolean updateQueuedPeriods(MediaPeriodId mediaPeriodId, long j) {
        int indexOfPeriod = this.timeline.getIndexOfPeriod(mediaPeriodId.periodUid);
        mediaPeriodId = null;
        MediaPeriodHolder frontPeriod = getFrontPeriod();
        while (frontPeriod != null) {
            if (mediaPeriodId == null) {
                frontPeriod.info = getUpdatedMediaPeriodInfo(frontPeriod.info);
            } else {
                if (indexOfPeriod != -1) {
                    if (frontPeriod.uid.equals(this.timeline.getUidOfPeriod(indexOfPeriod))) {
                        MediaPeriodInfo followingMediaPeriodInfo = getFollowingMediaPeriodInfo(mediaPeriodId, j);
                        if (followingMediaPeriodInfo == null) {
                            return removeAfter(mediaPeriodId) ^ 1;
                        }
                        frontPeriod.info = getUpdatedMediaPeriodInfo(frontPeriod.info);
                        if (!canKeepMediaPeriodHolder(frontPeriod, followingMediaPeriodInfo)) {
                            return removeAfter(mediaPeriodId) ^ 1;
                        }
                    }
                }
                return removeAfter(mediaPeriodId) ^ 1;
            }
            if (frontPeriod.info.isLastInTimelinePeriod != null) {
                indexOfPeriod = this.timeline.getNextPeriodIndex(indexOfPeriod, this.period, this.window, this.repeatMode, this.shuffleModeEnabled);
            }
            MediaPeriodHolder mediaPeriodHolder = frontPeriod;
            Object obj = frontPeriod.next;
            Object obj2 = mediaPeriodHolder;
        }
        return true;
    }

    public MediaPeriodInfo getUpdatedMediaPeriodInfo(MediaPeriodInfo mediaPeriodInfo) {
        boolean isLastInPeriod = isLastInPeriod(mediaPeriodInfo.id);
        boolean isLastInTimeline = isLastInTimeline(mediaPeriodInfo.id, isLastInPeriod);
        this.timeline.getPeriodByUid(mediaPeriodInfo.id.periodUid, this.period);
        long adDurationUs = mediaPeriodInfo.id.isAd() ? this.period.getAdDurationUs(mediaPeriodInfo.id.adGroupIndex, mediaPeriodInfo.id.adIndexInAdGroup) : mediaPeriodInfo.id.endPositionUs == Long.MIN_VALUE ? this.period.getDurationUs() : mediaPeriodInfo.id.endPositionUs;
        return new MediaPeriodInfo(mediaPeriodInfo.id, mediaPeriodInfo.startPositionUs, mediaPeriodInfo.contentPositionUs, adDurationUs, isLastInPeriod, isLastInTimeline);
    }

    public MediaPeriodId resolveMediaPeriodIdForAds(Object obj, long j) {
        return resolveMediaPeriodIdForAds(obj, j, resolvePeriodIndexToWindowSequenceNumber(obj));
    }

    private MediaPeriodId resolveMediaPeriodIdForAds(Object obj, long j, long j2) {
        this.timeline.getPeriodByUid(obj, this.period);
        int adGroupIndexForPositionUs = this.period.getAdGroupIndexForPositionUs(j);
        if (adGroupIndexForPositionUs == -1) {
            j = this.period.getAdGroupIndexAfterPositionUs(j);
            if (j == -1) {
                j = Long.MIN_VALUE;
            } else {
                j = this.period.getAdGroupTimeUs(j);
            }
            return new MediaPeriodId(obj, j2, j);
        }
        return new MediaPeriodId(obj, adGroupIndexForPositionUs, this.period.getFirstAdIndexToPlay(adGroupIndexForPositionUs), j2);
    }

    private long resolvePeriodIndexToWindowSequenceNumber(Object obj) {
        int indexOfPeriod;
        int i = this.timeline.getPeriodByUid(obj, this.period).windowIndex;
        if (this.oldFrontPeriodUid != null) {
            indexOfPeriod = this.timeline.getIndexOfPeriod(this.oldFrontPeriodUid);
            if (indexOfPeriod != -1 && this.timeline.getPeriod(indexOfPeriod, this.period).windowIndex == i) {
                return this.oldFrontPeriodWindowSequenceNumber;
            }
        }
        for (MediaPeriodHolder frontPeriod = getFrontPeriod(); frontPeriod != null; frontPeriod = frontPeriod.next) {
            if (frontPeriod.uid.equals(obj)) {
                return frontPeriod.info.id.windowSequenceNumber;
            }
        }
        for (obj = getFrontPeriod(); obj != null; obj = obj.next) {
            indexOfPeriod = this.timeline.getIndexOfPeriod(obj.uid);
            if (indexOfPeriod != -1 && this.timeline.getPeriod(indexOfPeriod, this.period).windowIndex == i) {
                return obj.info.id.windowSequenceNumber;
            }
        }
        long j = this.nextWindowSequenceNumber;
        this.nextWindowSequenceNumber = 1 + j;
        return j;
    }

    private boolean canKeepMediaPeriodHolder(MediaPeriodHolder mediaPeriodHolder, MediaPeriodInfo mediaPeriodInfo) {
        mediaPeriodHolder = mediaPeriodHolder.info;
        return (mediaPeriodHolder.startPositionUs != mediaPeriodInfo.startPositionUs || mediaPeriodHolder.id.equals(mediaPeriodInfo.id) == null) ? null : true;
    }

    private boolean updateForPlaybackModeChange() {
        MediaPeriodHolder frontPeriod = getFrontPeriod();
        boolean z = true;
        if (frontPeriod == null) {
            return true;
        }
        int indexOfPeriod = this.timeline.getIndexOfPeriod(frontPeriod.uid);
        while (true) {
            indexOfPeriod = this.timeline.getNextPeriodIndex(indexOfPeriod, this.period, this.window, this.repeatMode, this.shuffleModeEnabled);
            while (frontPeriod.next != null && !frontPeriod.info.isLastInTimelinePeriod) {
                frontPeriod = frontPeriod.next;
            }
            if (indexOfPeriod == -1) {
                break;
            } else if (frontPeriod.next == null) {
                break;
            } else if (this.timeline.getIndexOfPeriod(frontPeriod.next.uid) != indexOfPeriod) {
                break;
            } else {
                frontPeriod = frontPeriod.next;
            }
        }
        boolean removeAfter = removeAfter(frontPeriod);
        frontPeriod.info = getUpdatedMediaPeriodInfo(frontPeriod.info);
        if (removeAfter) {
            if (hasPlayingPeriod()) {
                z = false;
            }
        }
        return z;
    }

    private MediaPeriodInfo getFirstMediaPeriodInfo(PlaybackInfo playbackInfo) {
        return getMediaPeriodInfo(playbackInfo.periodId, playbackInfo.contentPositionUs, playbackInfo.startPositionUs);
    }

    @Nullable
    private MediaPeriodInfo getFollowingMediaPeriodInfo(MediaPeriodHolder mediaPeriodHolder, long j) {
        MediaPeriodQueue mediaPeriodQueue = this;
        MediaPeriodHolder mediaPeriodHolder2 = mediaPeriodHolder;
        MediaPeriodInfo mediaPeriodInfo = mediaPeriodHolder2.info;
        MediaPeriodInfo mediaPeriodInfo2 = null;
        int nextPeriodIndex;
        long j2;
        if (mediaPeriodInfo.isLastInTimelinePeriod) {
            nextPeriodIndex = mediaPeriodQueue.timeline.getNextPeriodIndex(mediaPeriodQueue.timeline.getIndexOfPeriod(mediaPeriodInfo.id.periodUid), mediaPeriodQueue.period, mediaPeriodQueue.window, mediaPeriodQueue.repeatMode, mediaPeriodQueue.shuffleModeEnabled);
            if (nextPeriodIndex == -1) {
                return null;
            }
            long rendererOffset;
            Object obj;
            int i = mediaPeriodQueue.timeline.getPeriod(nextPeriodIndex, mediaPeriodQueue.period, true).windowIndex;
            Object obj2 = mediaPeriodQueue.period.uid;
            long j3 = mediaPeriodInfo.id.windowSequenceNumber;
            long j4 = 0;
            if (mediaPeriodQueue.timeline.getWindow(i, mediaPeriodQueue.window).firstPeriodIndex == nextPeriodIndex) {
                rendererOffset = (mediaPeriodHolder.getRendererOffset() + mediaPeriodInfo.durationUs) - j;
                Timeline timeline = mediaPeriodQueue.timeline;
                Pair periodPosition = timeline.getPeriodPosition(mediaPeriodQueue.window, mediaPeriodQueue.period, i, C.TIME_UNSET, Math.max(0, rendererOffset));
                if (periodPosition == null) {
                    return null;
                }
                long j5;
                Object obj3 = periodPosition.first;
                long longValue = ((Long) periodPosition.second).longValue();
                if (mediaPeriodHolder2.next == null || !mediaPeriodHolder2.next.uid.equals(obj3)) {
                    j5 = mediaPeriodQueue.nextWindowSequenceNumber;
                    mediaPeriodQueue.nextWindowSequenceNumber = 1 + j5;
                } else {
                    j5 = mediaPeriodHolder2.next.info.id.windowSequenceNumber;
                }
                j4 = longValue;
                j2 = j5;
                obj = obj3;
            } else {
                obj = obj2;
                j2 = j3;
            }
            rendererOffset = j4;
            return getMediaPeriodInfo(resolveMediaPeriodIdForAds(obj, rendererOffset, j2), rendererOffset, j4);
        }
        MediaPeriodId mediaPeriodId = mediaPeriodInfo.id;
        mediaPeriodQueue.timeline.getPeriodByUid(mediaPeriodId.periodUid, mediaPeriodQueue.period);
        int adCountInAdGroup;
        if (mediaPeriodId.isAd()) {
            nextPeriodIndex = mediaPeriodId.adGroupIndex;
            adCountInAdGroup = mediaPeriodQueue.period.getAdCountInAdGroup(nextPeriodIndex);
            if (adCountInAdGroup == -1) {
                return null;
            }
            int nextAdIndexToPlay = mediaPeriodQueue.period.getNextAdIndexToPlay(nextPeriodIndex, mediaPeriodId.adIndexInAdGroup);
            if (nextAdIndexToPlay < adCountInAdGroup) {
                if (mediaPeriodQueue.period.isAdAvailable(nextPeriodIndex, nextAdIndexToPlay)) {
                    mediaPeriodInfo2 = getMediaPeriodInfoForAd(mediaPeriodId.periodUid, nextPeriodIndex, nextAdIndexToPlay, mediaPeriodInfo.contentPositionUs, mediaPeriodId.windowSequenceNumber);
                }
                return mediaPeriodInfo2;
            }
            return getMediaPeriodInfoForContent(mediaPeriodId.periodUid, mediaPeriodInfo.contentPositionUs, mediaPeriodId.windowSequenceNumber);
        } else if (mediaPeriodInfo.id.endPositionUs != Long.MIN_VALUE) {
            nextPeriodIndex = mediaPeriodQueue.period.getAdGroupIndexForPositionUs(mediaPeriodInfo.id.endPositionUs);
            if (nextPeriodIndex == -1) {
                return getMediaPeriodInfoForContent(mediaPeriodId.periodUid, mediaPeriodInfo.id.endPositionUs, mediaPeriodId.windowSequenceNumber);
            }
            adCountInAdGroup = mediaPeriodQueue.period.getFirstAdIndexToPlay(nextPeriodIndex);
            if (mediaPeriodQueue.period.isAdAvailable(nextPeriodIndex, adCountInAdGroup)) {
                mediaPeriodInfo2 = getMediaPeriodInfoForAd(mediaPeriodId.periodUid, nextPeriodIndex, adCountInAdGroup, mediaPeriodInfo.id.endPositionUs, mediaPeriodId.windowSequenceNumber);
            }
            return mediaPeriodInfo2;
        } else {
            int adGroupCount = mediaPeriodQueue.period.getAdGroupCount();
            if (adGroupCount == 0) {
                return null;
            }
            nextPeriodIndex = adGroupCount - 1;
            if (mediaPeriodQueue.period.getAdGroupTimeUs(nextPeriodIndex) == Long.MIN_VALUE) {
                if (!mediaPeriodQueue.period.hasPlayedAdGroup(nextPeriodIndex)) {
                    adCountInAdGroup = mediaPeriodQueue.period.getFirstAdIndexToPlay(nextPeriodIndex);
                    if (!mediaPeriodQueue.period.isAdAvailable(nextPeriodIndex, adCountInAdGroup)) {
                        return null;
                    }
                    j2 = mediaPeriodQueue.period.getDurationUs();
                    return getMediaPeriodInfoForAd(mediaPeriodId.periodUid, nextPeriodIndex, adCountInAdGroup, j2, mediaPeriodId.windowSequenceNumber);
                }
            }
            return null;
        }
    }

    private MediaPeriodInfo getMediaPeriodInfo(MediaPeriodId mediaPeriodId, long j, long j2) {
        this.timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period);
        if (!mediaPeriodId.isAd()) {
            return getMediaPeriodInfoForContent(mediaPeriodId.periodUid, j2, mediaPeriodId.windowSequenceNumber);
        } else if (this.period.isAdAvailable(mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup) == null) {
            return null;
        } else {
            return getMediaPeriodInfoForAd(mediaPeriodId.periodUid, mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup, j, mediaPeriodId.windowSequenceNumber);
        }
    }

    private MediaPeriodInfo getMediaPeriodInfoForAd(Object obj, int i, int i2, long j, long j2) {
        MediaPeriodId mediaPeriodId = new MediaPeriodId(obj, i, i2, j2);
        boolean isLastInPeriod = isLastInPeriod(mediaPeriodId);
        boolean isLastInTimeline = isLastInTimeline(mediaPeriodId, isLastInPeriod);
        return new MediaPeriodInfo(mediaPeriodId, i2 == this.period.getFirstAdIndexToPlay(i) ? r0.period.getAdResumePositionUs() : 0, j, this.timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period).getAdDurationUs(mediaPeriodId.adGroupIndex, mediaPeriodId.adIndexInAdGroup), isLastInPeriod, isLastInTimeline);
    }

    private MediaPeriodInfo getMediaPeriodInfoForContent(Object obj, long j, long j2) {
        long j3;
        int adGroupIndexAfterPositionUs = this.period.getAdGroupIndexAfterPositionUs(j);
        if (adGroupIndexAfterPositionUs == -1) {
            j3 = Long.MIN_VALUE;
        } else {
            j3 = r0.period.getAdGroupTimeUs(adGroupIndexAfterPositionUs);
        }
        MediaPeriodId mediaPeriodId = new MediaPeriodId(obj, j2, j3);
        r0.timeline.getPeriodByUid(mediaPeriodId.periodUid, r0.period);
        boolean isLastInPeriod = isLastInPeriod(mediaPeriodId);
        return new MediaPeriodInfo(mediaPeriodId, j, C.TIME_UNSET, j3 == Long.MIN_VALUE ? r0.period.getDurationUs() : j3, isLastInPeriod, isLastInTimeline(mediaPeriodId, isLastInPeriod));
    }

    private boolean isLastInPeriod(MediaPeriodId mediaPeriodId) {
        int adGroupCount = this.timeline.getPeriodByUid(mediaPeriodId.periodUid, this.period).getAdGroupCount();
        boolean z = true;
        if (adGroupCount == 0) {
            return true;
        }
        adGroupCount--;
        boolean isAd = mediaPeriodId.isAd();
        if (this.period.getAdGroupTimeUs(adGroupCount) != Long.MIN_VALUE) {
            if (isAd || mediaPeriodId.endPositionUs != Long.MIN_VALUE) {
                z = false;
            }
            return z;
        }
        int adCountInAdGroup = this.period.getAdCountInAdGroup(adGroupCount);
        if (adCountInAdGroup == -1) {
            return false;
        }
        mediaPeriodId = (isAd && mediaPeriodId.adGroupIndex == adGroupCount && mediaPeriodId.adIndexInAdGroup == adCountInAdGroup - 1) ? true : null;
        if (mediaPeriodId == null) {
            if (isAd || this.period.getFirstAdIndexToPlay(adGroupCount) != adCountInAdGroup) {
                z = false;
            }
        }
        return z;
    }

    private boolean isLastInTimeline(MediaPeriodId mediaPeriodId, boolean z) {
        int indexOfPeriod = this.timeline.getIndexOfPeriod(mediaPeriodId.periodUid);
        return (this.timeline.getWindow(this.timeline.getPeriod(indexOfPeriod, this.period).windowIndex, this.window).isDynamic == null && this.timeline.isLastPeriod(indexOfPeriod, this.period, this.window, this.repeatMode, this.shuffleModeEnabled) != null && z) ? true : null;
    }
}
