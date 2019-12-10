package com.xumo.xumo.model;

import java.util.ArrayList;

public class Channel {
    private String mCallSign = "";
    private ArrayList<Category> mCategories = null;
    private String mChannelId = "";
    private int mChannelNumber = 0;
    private String mChannelTitle = "";
    private String mDescriptionText = "";
    private int mGenreId = 0;
    private String mGenreName = "";
    private boolean mHasSchedule = false;
    private boolean mHasVod = true;
    private boolean mIsLive = false;
    private boolean mIsNew = false;

    public Channel(String str) {
        this.mChannelId = str;
    }

    public String getChannelId() {
        return this.mChannelId;
    }

    public void setChannelId(String str) {
        this.mChannelId = str;
    }

    public String getChannelTitle() {
        return this.mChannelTitle;
    }

    public void setChannelTitle(String str) {
        this.mChannelTitle = str;
    }

    public String getDescriptionText() {
        return this.mDescriptionText;
    }

    public void setDescriptionText(String str) {
        this.mDescriptionText = str;
    }

    public int getChannelNumber() {
        return this.mChannelNumber;
    }

    public void setChannelNumber(int i) {
        this.mChannelNumber = i;
    }

    public ArrayList<Category> getCategories() {
        return this.mCategories;
    }

    public void setCategories(ArrayList<Category> arrayList) {
        this.mCategories = arrayList;
    }

    public String getCallSign() {
        return this.mCallSign;
    }

    public void setCallSign(String str) {
        this.mCallSign = str;
    }

    public int getGenreId() {
        return this.mGenreId;
    }

    public void setGenreId(int i) {
        this.mGenreId = i;
    }

    public String getGenreName() {
        return this.mGenreName;
    }

    public void setGenreName(String str) {
        this.mGenreName = str;
    }

    public boolean isLive() {
        return this.mIsLive;
    }

    public void setLive(boolean z) {
        this.mIsLive = z;
    }

    public boolean isHasSchedule() {
        return this.mHasSchedule;
    }

    public void setHasSchedule(boolean z) {
        this.mHasSchedule = z;
    }

    public boolean isHasVod() {
        return this.mHasVod;
    }

    public void setHasVod(boolean z) {
        this.mHasVod = z;
    }

    public boolean isNew() {
        return this.mIsNew;
    }

    public void setNew(boolean z) {
        this.mIsNew = z;
    }
}
