package com.google.ads.interactivemedia.v3.internal;

import com.google.ads.interactivemedia.v3.api.player.VideoProgressUpdate;
import com.google.ads.interactivemedia.v3.internal.jc.c;
import com.google.ads.interactivemedia.v3.internal.ji.b;
import java.util.SortedSet;

/* compiled from: IMASDK */
public class iu implements b {
    private final SortedSet<Float> a;
    private jd b;
    private String c;
    private float d = 0.0f;

    public iu(jd jdVar, SortedSet<Float> sortedSet, String str) {
        this.b = jdVar;
        this.c = str;
        this.a = sortedSet;
    }

    public void a(VideoProgressUpdate videoProgressUpdate) {
        if (videoProgressUpdate != null) {
            if (videoProgressUpdate.getDuration() >= 0.0f) {
                int isEmpty = a(videoProgressUpdate.getCurrentTime()).isEmpty() ^ 1;
                this.d = videoProgressUpdate.getCurrentTime();
                if (isEmpty != 0) {
                    this.b.b(new jc(jc.b.contentTimeUpdate, c.contentTimeUpdate, this.c, videoProgressUpdate));
                }
            }
        }
    }

    private SortedSet<Float> a(float f) {
        if (this.d < f) {
            return this.a.subSet(Float.valueOf(this.d), Float.valueOf(f));
        }
        return this.a.subSet(Float.valueOf(f), Float.valueOf(this.d));
    }
}
