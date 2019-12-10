package com.xumo.xumo.model;

public class Provider {
    private String mAdTag = "";
    private int mId = 0;
    private String mName = "";
    private String mPlayer = "";
    private boolean preroll = false;

    public Provider(int i, String str, String str2, String str3) {
        this.mId = i;
        this.mName = str;
        this.mAdTag = str2;
        this.mPlayer = str3;
    }

    public int getId() {
        return this.mId;
    }

    public void setId(int i) {
        this.mId = i;
    }

    public String getName() {
        return this.mName;
    }

    public void setName(String str) {
        this.mName = str;
    }

    public String getAdTag() {
        return this.mAdTag;
    }

    public void setAdTag(String str) {
        this.mAdTag = str;
    }

    public String getPlayer() {
        return this.mPlayer;
    }

    public void setPlayer(String str) {
        this.mPlayer = str;
    }

    public boolean isPreroll() {
        return this.preroll;
    }

    public void setPreroll(boolean z) {
        this.preroll = z;
    }
}
