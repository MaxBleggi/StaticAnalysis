package com.google.ads.interactivemedia.v3.internal;

import com.google.ads.interactivemedia.v3.api.player.VideoAdPlayer.VideoAdPlayerCallback;
import com.google.ads.interactivemedia.v3.api.player.VideoProgressUpdate;
import com.google.ads.interactivemedia.v3.api.player.VolumeProvider;
import com.google.ads.interactivemedia.v3.impl.data.o;
import com.google.ads.interactivemedia.v3.internal.jc.c;
import com.google.ads.interactivemedia.v3.internal.ji.b;

/* compiled from: IMASDK */
public class if implements VideoAdPlayerCallback, b {
    private final jd a;
    private final String b;
    private final ih c;
    private final VolumeProvider d;
    private boolean e = false;

    public if(jd jdVar, String str, ih ihVar, VolumeProvider volumeProvider) {
        this.a = jdVar;
        this.b = str;
        this.c = ihVar;
        this.d = volumeProvider;
    }

    public void onPlay() {
        this.e = false;
    }

    public void onPause() {
        this.c.c();
        a(c.pause);
    }

    public void onLoaded() {
        a(c.loaded);
    }

    public void onResume() {
        this.c.b();
        a(c.play);
    }

    public void onEnded() {
        a(c.end);
    }

    public void onError() {
        a(c.error);
    }

    public void onVolumeChanged(int i) {
        a(c.volumeChange, o.builder().volumePercentage(i).build());
    }

    public void a(VideoProgressUpdate videoProgressUpdate) {
        if (videoProgressUpdate != null && videoProgressUpdate.getDuration() > 0.0f) {
            b(videoProgressUpdate);
            a(c.timeupdate, videoProgressUpdate);
        }
    }

    void b(VideoProgressUpdate videoProgressUpdate) {
        if (!this.e && videoProgressUpdate.getCurrentTime() > 0.0f) {
            a(c.start, o.builder().volumePercentage(this.d.getVolume()).build());
            this.e = true;
        }
    }

    void a(c cVar) {
        a(cVar, null);
    }

    void a(c cVar, Object obj) {
        this.a.b(new jc(jc.b.videoDisplay, cVar, this.b, obj));
    }
}
