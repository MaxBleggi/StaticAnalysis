package com.google.android.exoplayer2.source.dash.manifest;

import android.net.Uri;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.offline.FilterableManifest;
import com.google.android.exoplayer2.offline.StreamKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DashManifest implements FilterableManifest<DashManifest> {
    public final long availabilityStartTimeMs;
    public final long durationMs;
    public final boolean dynamic;
    public final Uri location;
    public final long minBufferTimeMs;
    public final long minUpdatePeriodMs;
    private final List<Period> periods;
    public final long publishTimeMs;
    public final long suggestedPresentationDelayMs;
    public final long timeShiftBufferDepthMs;
    public final UtcTimingElement utcTiming;

    public DashManifest(long j, long j2, long j3, boolean z, long j4, long j5, long j6, long j7, UtcTimingElement utcTimingElement, Uri uri, List<Period> list) {
        this.availabilityStartTimeMs = j;
        this.durationMs = j2;
        this.minBufferTimeMs = j3;
        this.dynamic = z;
        this.minUpdatePeriodMs = j4;
        this.timeShiftBufferDepthMs = j5;
        this.suggestedPresentationDelayMs = j6;
        this.publishTimeMs = j7;
        this.utcTiming = utcTimingElement;
        this.location = uri;
        r0.periods = list == null ? Collections.emptyList() : list;
    }

    public final int getPeriodCount() {
        return this.periods.size();
    }

    public final Period getPeriod(int i) {
        return (Period) this.periods.get(i);
    }

    public final long getPeriodDurationMs(int i) {
        if (i == this.periods.size() - 1) {
            return this.durationMs == C.TIME_UNSET ? C.TIME_UNSET : this.durationMs - ((Period) this.periods.get(i)).startMs;
        } else {
            return ((Period) this.periods.get(i + 1)).startMs - ((Period) this.periods.get(i)).startMs;
        }
    }

    public final long getPeriodDurationUs(int i) {
        return C.msToUs(getPeriodDurationMs(i));
    }

    public final DashManifest copy(List<StreamKey> list) {
        long j;
        DashManifest dashManifest = this;
        LinkedList linkedList = new LinkedList(list);
        Collections.sort(linkedList);
        linkedList.add(new StreamKey(-1, -1, -1));
        ArrayList arrayList = new ArrayList();
        long j2 = 0;
        int i = 0;
        while (true) {
            int periodCount = getPeriodCount();
            j = C.TIME_UNSET;
            if (i >= periodCount) {
                break;
            }
            if (((StreamKey) linkedList.peek()).periodIndex != i) {
                long periodDurationMs = getPeriodDurationMs(i);
                if (periodDurationMs != C.TIME_UNSET) {
                    j2 += periodDurationMs;
                }
            } else {
                Period period = getPeriod(i);
                arrayList.add(new Period(period.id, period.startMs - j2, copyAdaptationSets(period.adaptationSets, linkedList), period.eventStreams));
            }
            i++;
        }
        if (dashManifest.durationMs != C.TIME_UNSET) {
            j = dashManifest.durationMs - j2;
        }
        return new DashManifest(dashManifest.availabilityStartTimeMs, j, dashManifest.minBufferTimeMs, dashManifest.dynamic, dashManifest.minUpdatePeriodMs, dashManifest.timeShiftBufferDepthMs, dashManifest.suggestedPresentationDelayMs, dashManifest.publishTimeMs, dashManifest.utcTiming, dashManifest.location, arrayList);
    }

    private static ArrayList<AdaptationSet> copyAdaptationSets(List<AdaptationSet> list, LinkedList<StreamKey> linkedList) {
        StreamKey streamKey = (StreamKey) linkedList.poll();
        int i = streamKey.periodIndex;
        ArrayList<AdaptationSet> arrayList = new ArrayList();
        do {
            int i2 = streamKey.groupIndex;
            AdaptationSet adaptationSet = (AdaptationSet) list.get(i2);
            List list2 = adaptationSet.representations;
            List arrayList2 = new ArrayList();
            do {
                arrayList2.add((Representation) list2.get(streamKey.trackIndex));
                streamKey = (StreamKey) linkedList.poll();
                if (streamKey.periodIndex != i) {
                    break;
                }
            } while (streamKey.groupIndex == i2);
            arrayList.add(new AdaptationSet(adaptationSet.id, adaptationSet.type, arrayList2, adaptationSet.accessibilityDescriptors, adaptationSet.supplementalProperties));
        } while (streamKey.periodIndex == i);
        linkedList.addFirst(streamKey);
        return arrayList;
    }
}
