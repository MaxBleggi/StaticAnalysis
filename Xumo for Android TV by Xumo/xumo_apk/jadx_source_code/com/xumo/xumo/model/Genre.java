package com.xumo.xumo.model;

import java.util.ArrayList;

public class Genre {
    private String mCategoryId = "";
    private ArrayList<String> mChannelIdList;
    private int mGenreId = 0;
    private String mValue = "";

    public Genre(int i, String str, String str2) {
        this.mGenreId = i;
        this.mValue = str;
        this.mCategoryId = str2;
    }

    public int getGenreId() {
        return this.mGenreId;
    }

    public void setGenreId(int i) {
        this.mGenreId = i;
    }

    public String getValue() {
        return this.mValue;
    }

    public void setValue(String str) {
        this.mValue = str;
    }

    public String getCategoryId() {
        return this.mCategoryId;
    }

    public void setCategoryId(String str) {
        this.mCategoryId = str;
    }

    public ArrayList<String> getChannelIdList() {
        return this.mChannelIdList;
    }

    public void setChannelIdList(ArrayList<String> arrayList) {
        this.mChannelIdList = arrayList;
    }
}
