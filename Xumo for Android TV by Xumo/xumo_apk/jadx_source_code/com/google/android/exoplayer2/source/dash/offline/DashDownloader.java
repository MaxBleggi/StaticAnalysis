package com.google.android.exoplayer2.source.dash.offline;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.offline.DownloadException;
import com.google.android.exoplayer2.offline.DownloaderConstructorHelper;
import com.google.android.exoplayer2.offline.SegmentDownloader;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.dash.DashSegmentIndex;
import com.google.android.exoplayer2.source.dash.DashUtil;
import com.google.android.exoplayer2.source.dash.DashWrappingSegmentIndex;
import com.google.android.exoplayer2.source.dash.manifest.AdaptationSet;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.manifest.Period;
import com.google.android.exoplayer2.source.dash.manifest.RangedUri;
import com.google.android.exoplayer2.source.dash.manifest.Representation;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class DashDownloader extends SegmentDownloader<DashManifest> {
    public DashDownloader(Uri uri, List<StreamKey> list, DownloaderConstructorHelper downloaderConstructorHelper) {
        super(uri, list, downloaderConstructorHelper);
    }

    protected DashManifest getManifest(DataSource dataSource, Uri uri) throws IOException {
        return DashUtil.loadManifest(dataSource, uri);
    }

    protected List<Segment> getSegments(DataSource dataSource, DashManifest dashManifest, boolean z) throws InterruptedException, IOException {
        DashManifest dashManifest2 = dashManifest;
        List arrayList = new ArrayList();
        for (int i = 0; i < dashManifest.getPeriodCount(); i++) {
            Period period = dashManifest2.getPeriod(i);
            long msToUs = C.msToUs(period.startMs);
            long periodDurationUs = dashManifest2.getPeriodDurationUs(i);
            List list = period.adaptationSets;
            int i2 = 0;
            while (i2 < list.size()) {
                int i3 = i2;
                List list2 = list;
                addSegmentsForAdaptationSet(dataSource, (AdaptationSet) list.get(i2), msToUs, periodDurationUs, z, arrayList);
                i2 = i3 + 1;
                list = list2;
            }
        }
        return arrayList;
    }

    private static void addSegmentsForAdaptationSet(DataSource dataSource, AdaptationSet adaptationSet, long j, long j2, boolean z, ArrayList<Segment> arrayList) throws IOException, InterruptedException {
        IOException e;
        AdaptationSet adaptationSet2 = adaptationSet;
        long j3 = j;
        ArrayList<Segment> arrayList2 = arrayList;
        int i = 0;
        while (i < adaptationSet2.representations.size()) {
            Representation representation = (Representation) adaptationSet2.representations.get(i);
            long j4;
            try {
                try {
                    DashSegmentIndex segmentIndex = getSegmentIndex(dataSource, adaptationSet2.type, representation);
                    if (segmentIndex != null) {
                        int segmentCount = segmentIndex.getSegmentCount(j2);
                        if (segmentCount != -1) {
                            String str = representation.baseUrl;
                            RangedUri initializationUri = representation.getInitializationUri();
                            if (initializationUri != null) {
                                addSegment(j3, str, initializationUri, arrayList2);
                            }
                            RangedUri indexUri = representation.getIndexUri();
                            if (indexUri != null) {
                                addSegment(j3, str, indexUri, arrayList2);
                            }
                            long firstSegmentNum = segmentIndex.getFirstSegmentNum();
                            long j5 = (((long) segmentCount) + firstSegmentNum) - 1;
                            while (firstSegmentNum <= j5) {
                                addSegment(j3 + segmentIndex.getTimeUs(firstSegmentNum), str, segmentIndex.getSegmentUrl(firstSegmentNum), arrayList2);
                                firstSegmentNum++;
                                adaptationSet2 = adaptationSet;
                            }
                            i++;
                            adaptationSet2 = adaptationSet;
                        } else {
                            throw new DownloadException("Unbounded segment index");
                        }
                    }
                    j4 = j2;
                    try {
                        throw new DownloadException("Missing segment index");
                    } catch (IOException e2) {
                        e = e2;
                    }
                } catch (IOException e3) {
                    e = e3;
                    j4 = j2;
                    if (z) {
                        i++;
                        adaptationSet2 = adaptationSet;
                    } else {
                        throw e;
                    }
                }
            } catch (IOException e4) {
                e = e4;
                DataSource dataSource2 = dataSource;
                j4 = j2;
                if (z) {
                    i++;
                    adaptationSet2 = adaptationSet;
                } else {
                    throw e;
                }
            }
        }
    }

    private static void addSegment(long j, String str, RangedUri rangedUri, ArrayList<Segment> arrayList) {
        arrayList.add(new Segment(j, new DataSpec(rangedUri.resolveUri(str), rangedUri.start, rangedUri.length, null)));
    }

    @Nullable
    private static DashSegmentIndex getSegmentIndex(DataSource dataSource, int i, Representation representation) throws IOException, InterruptedException {
        DashSegmentIndex index = representation.getIndex();
        if (index != null) {
            return index;
        }
        dataSource = DashUtil.loadChunkIndex(dataSource, i, representation);
        if (dataSource == null) {
            dataSource = null;
        } else {
            dataSource = new DashWrappingSegmentIndex(dataSource, representation.presentationTimeOffsetUs);
        }
        return dataSource;
    }
}
