package com.xumo.xumo.model;

import java.util.ArrayList;

public class PlayerProvider {
    public static final long DEFAULT_AD_INTERVAL = 180;
    private long mAdInterval = 180;
    private long mAdIntervalAutoPlay = 0;
    private long mAdTargetDuration = 0;
    private int mMaxRetryCount = 0;
    private boolean mPreCacheAds = false;
    private ArrayList<Provider> mProviders;
    private String[] mRetryDomains = null;

    public ArrayList<Provider> getProviders() {
        return this.mProviders;
    }

    public void setProviders(ArrayList<Provider> arrayList) {
        this.mProviders = arrayList;
    }

    public long getAdTargetDuration() {
        return this.mAdTargetDuration;
    }

    public void setAdTargetDuration(long j) {
        this.mAdTargetDuration = j;
    }

    public long getAdInterval() {
        return this.mAdInterval;
    }

    public void setAdInterval(long j) {
        this.mAdInterval = j;
    }

    public long getAdIntervalAutoPlay() {
        return this.mAdIntervalAutoPlay;
    }

    public void setAdIntervalAutoPlay(long j) {
        this.mAdIntervalAutoPlay = j;
    }

    public boolean shouldPreCacheAds() {
        return this.mPreCacheAds;
    }

    public void setPreCacheAds(boolean z) {
        this.mPreCacheAds = z;
    }

    public String[] getRetryDomains() {
        return this.mRetryDomains;
    }

    public void setRetryDomains(String[] strArr) {
        this.mRetryDomains = strArr;
    }

    public int getMaxRetryCount() {
        return this.mMaxRetryCount;
    }

    public void setMaxRetryCount(int i) {
        this.mMaxRetryCount = i;
    }
}
