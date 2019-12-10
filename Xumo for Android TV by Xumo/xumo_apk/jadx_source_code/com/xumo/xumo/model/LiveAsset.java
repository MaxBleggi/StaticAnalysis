package com.xumo.xumo.model;

import com.xumo.xumo.util.XumoUtil;

public class LiveAsset extends VideoAsset {
    private int index = 0;
    private int length = 0;
    private long mEnd = 0;
    private long mStart = 0;

    public LiveAsset(String str, String str2) {
        super(str, "", str2);
    }

    public long getStart() {
        return this.mStart;
    }

    public long getEnd() {
        return this.mEnd;
    }

    public void setStart(long j) {
        this.mStart = j;
    }

    public void setEnd(long j) {
        this.mEnd = j;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int i) {
        this.index = i;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int i) {
        this.length = i;
    }

    public String getLiveTimeString() {
        return XumoUtil.getLiveTimeString(this.mStart, this.mEnd);
    }
}
