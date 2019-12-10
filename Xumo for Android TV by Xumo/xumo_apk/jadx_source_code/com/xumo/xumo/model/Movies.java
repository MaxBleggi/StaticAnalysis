package com.xumo.xumo.model;

public class Movies {
    private int mListItemIndexX;
    private boolean mLoadingNow = false;

    public int getmListItemIndexX() {
        return this.mListItemIndexX;
    }

    public void setmListItemIndexX(int i) {
        this.mListItemIndexX = i;
    }

    public boolean ismLoadingNow() {
        return this.mLoadingNow;
    }

    public void setmLoadingNow(boolean z) {
        this.mLoadingNow = z;
    }
}
