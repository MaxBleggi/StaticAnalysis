package com.xumo.xumo.model;

import java.util.ArrayList;

public class Category {
    private int hits = 0;
    private String mCategoryId = "";
    private String mChannelId = "";
    private String mDescriptionText = "";
    private String mTitle = "";
    private ArrayList<VideoAsset> mVideoAssets = new ArrayList();

    public Category(String str, String str2) {
        this.mChannelId = str;
        this.mCategoryId = str2;
    }

    public String getChannelId() {
        return this.mChannelId;
    }

    public void setChannelId(String str) {
        this.mChannelId = str;
    }

    public String getCategoryId() {
        return this.mCategoryId;
    }

    public void setCategoryId(String str) {
        this.mCategoryId = str;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String str) {
        this.mTitle = str;
    }

    public String getDescriptionText() {
        return this.mDescriptionText;
    }

    public void setDescriptionText(String str) {
        this.mDescriptionText = str;
    }

    public int getHits() {
        return this.hits;
    }

    public void setHits(int i) {
        this.hits = i;
    }

    public ArrayList<VideoAsset> getVideoAssets() {
        return this.mVideoAssets;
    }

    public void setVideoAssets(ArrayList<VideoAsset> arrayList) {
        this.mVideoAssets = arrayList;
    }
}
