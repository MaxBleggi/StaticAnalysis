package com.google.ads.interactivemedia.v3.api.player;

import com.google.ads.interactivemedia.v3.internal.ks;

/* compiled from: IMASDK */
public final class VideoProgressUpdate {
    public static final VideoProgressUpdate VIDEO_TIME_NOT_READY = new VideoProgressUpdate(-1, -1);
    private float currentTime;
    private float duration;

    public VideoProgressUpdate(long j, long j2) {
        this.currentTime = ((float) j) / 1148846080;
        this.duration = ((float) j2) / 1148846080;
    }

    public float getCurrentTime() {
        return this.currentTime;
    }

    public float getDuration() {
        return this.duration;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        VideoProgressUpdate videoProgressUpdate = (VideoProgressUpdate) obj;
        return Float.floatToIntBits(this.currentTime) == Float.floatToIntBits(videoProgressUpdate.currentTime) && Float.floatToIntBits(this.duration) == Float.floatToIntBits(videoProgressUpdate.duration);
    }

    public int hashCode() {
        return ks.a(Float.valueOf(this.currentTime), Float.valueOf(this.duration));
    }

    public String toString() {
        float f = this.currentTime;
        float f2 = this.duration;
        StringBuilder stringBuilder = new StringBuilder(75);
        stringBuilder.append("VideoProgressUpdate [currentTime=");
        stringBuilder.append(f);
        stringBuilder.append(", duration=");
        stringBuilder.append(f2);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
