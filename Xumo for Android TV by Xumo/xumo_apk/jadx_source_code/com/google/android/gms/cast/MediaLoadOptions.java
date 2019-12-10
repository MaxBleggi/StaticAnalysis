package com.google.android.gms.cast;

import org.json.JSONObject;

public class MediaLoadOptions {
    public static final double PLAYBACK_RATE_MAX = 2.0d;
    public static final double PLAYBACK_RATE_MIN = 0.5d;
    private boolean zzdm;
    private long zzdn;
    private double zzdo;
    private long[] zzdp;
    private String zzdq;
    private String zzdr;
    private JSONObject zzp;

    public static class Builder {
        private boolean zzdm = true;
        private long zzdn = 0;
        private double zzdo = 1.0d;
        private long[] zzdp = null;
        private String zzdq = null;
        private String zzdr = null;
        private JSONObject zzp = null;

        public Builder setAutoplay(boolean z) {
            this.zzdm = z;
            return this;
        }

        public Builder setPlayPosition(long j) {
            this.zzdn = j;
            return this;
        }

        public Builder setPlaybackRate(double d) {
            if (Double.compare(d, MediaLoadOptions.PLAYBACK_RATE_MAX) > 0 || Double.compare(d, MediaLoadOptions.PLAYBACK_RATE_MIN) < 0) {
                throw new IllegalArgumentException("playbackRate must be between PLAYBACK_RATE_MIN and PLAYBACK_RATE_MAX");
            }
            this.zzdo = d;
            return this;
        }

        public Builder setActiveTrackIds(long[] jArr) {
            this.zzdp = jArr;
            return this;
        }

        public Builder setCustomData(JSONObject jSONObject) {
            this.zzp = jSONObject;
            return this;
        }

        public Builder setCredentials(String str) {
            this.zzdq = str;
            return this;
        }

        public Builder setCredentialsType(String str) {
            this.zzdr = str;
            return this;
        }

        public MediaLoadOptions build() {
            return new MediaLoadOptions(this.zzdm, this.zzdn, this.zzdo, this.zzdp, this.zzp, this.zzdq, this.zzdr);
        }
    }

    private MediaLoadOptions(boolean z, long j, double d, long[] jArr, JSONObject jSONObject, String str, String str2) {
        this.zzdm = z;
        this.zzdn = j;
        this.zzdo = d;
        this.zzdp = jArr;
        this.zzp = jSONObject;
        this.zzdq = str;
        this.zzdr = str2;
    }

    public boolean getAutoplay() {
        return this.zzdm;
    }

    public long getPlayPosition() {
        return this.zzdn;
    }

    public double getPlaybackRate() {
        return this.zzdo;
    }

    public long[] getActiveTrackIds() {
        return this.zzdp;
    }

    public JSONObject getCustomData() {
        return this.zzp;
    }

    public String getCredentials() {
        return this.zzdq;
    }

    public String getCredentialsType() {
        return this.zzdr;
    }
}
