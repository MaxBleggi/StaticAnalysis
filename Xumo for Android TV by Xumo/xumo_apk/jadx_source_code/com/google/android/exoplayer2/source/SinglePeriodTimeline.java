package com.google.android.exoplayer2.source;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.Timeline.Period;
import com.google.android.exoplayer2.Timeline.Window;
import com.google.android.exoplayer2.util.Assertions;

public final class SinglePeriodTimeline extends Timeline {
    private static final Object UID = new Object();
    private final boolean isDynamic;
    private final boolean isSeekable;
    private final long periodDurationUs;
    private final long presentationStartTimeMs;
    @Nullable
    private final Object tag;
    private final long windowDefaultStartPositionUs;
    private final long windowDurationUs;
    private final long windowPositionInPeriodUs;
    private final long windowStartTimeMs;

    public int getPeriodCount() {
        return 1;
    }

    public int getWindowCount() {
        return 1;
    }

    public SinglePeriodTimeline(long j, boolean z, boolean z2) {
        this(j, z, z2, null);
    }

    public SinglePeriodTimeline(long j, boolean z, boolean z2, @Nullable Object obj) {
        this(j, j, 0, 0, z, z2, obj);
    }

    public SinglePeriodTimeline(long j, long j2, long j3, long j4, boolean z, boolean z2, @Nullable Object obj) {
        this(C.TIME_UNSET, C.TIME_UNSET, j, j2, j3, j4, z, z2, obj);
    }

    public SinglePeriodTimeline(long j, long j2, long j3, long j4, long j5, long j6, boolean z, boolean z2, @Nullable Object obj) {
        this.presentationStartTimeMs = j;
        this.windowStartTimeMs = j2;
        this.periodDurationUs = j3;
        this.windowDurationUs = j4;
        this.windowPositionInPeriodUs = j5;
        this.windowDefaultStartPositionUs = j6;
        this.isSeekable = z;
        this.isDynamic = z2;
        this.tag = obj;
    }

    public Window getWindow(int i, Window window, boolean z, long j) {
        long j2;
        SinglePeriodTimeline singlePeriodTimeline = this;
        Assertions.checkIndex(i, 0, 1);
        Object obj = z ? singlePeriodTimeline.tag : null;
        long j3 = singlePeriodTimeline.windowDefaultStartPositionUs;
        if (singlePeriodTimeline.isDynamic && j != 0) {
            if (singlePeriodTimeline.windowDurationUs != C.TIME_UNSET) {
                j3 += j;
                if (j3 > singlePeriodTimeline.windowDurationUs) {
                }
            }
            j2 = C.TIME_UNSET;
            return window.set(obj, singlePeriodTimeline.presentationStartTimeMs, singlePeriodTimeline.windowStartTimeMs, singlePeriodTimeline.isSeekable, singlePeriodTimeline.isDynamic, j2, singlePeriodTimeline.windowDurationUs, 0, 0, singlePeriodTimeline.windowPositionInPeriodUs);
        }
        j2 = j3;
        return window.set(obj, singlePeriodTimeline.presentationStartTimeMs, singlePeriodTimeline.windowStartTimeMs, singlePeriodTimeline.isSeekable, singlePeriodTimeline.isDynamic, j2, singlePeriodTimeline.windowDurationUs, 0, 0, singlePeriodTimeline.windowPositionInPeriodUs);
    }

    public Period getPeriod(int i, Period period, boolean z) {
        Assertions.checkIndex(i, 0, 1);
        return period.set(null, z ? UID : 0, 0, this.periodDurationUs, -this.windowPositionInPeriodUs);
    }

    public int getIndexOfPeriod(Object obj) {
        return UID.equals(obj) != null ? null : -1;
    }

    public Object getUidOfPeriod(int i) {
        Assertions.checkIndex(i, 0, 1);
        return UID;
    }
}
