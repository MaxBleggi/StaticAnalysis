package com.xumo.xumo.model;

import android.text.TextUtils;
import java.util.ArrayList;

public class VideoPlaylist {
    private String mChannelDescription = "";
    private String mChannelId = "";
    private String mChannelTitle = "";
    private int mGenreId = -1;
    private String mGenreName = "";
    private int mPlayMode = 2;
    private String mPlayingVideoAssetId = "";
    private int mPlayingVideoAssetIndex = -1;
    private ArrayList<VideoAsset> mVideoAssets = null;

    private VideoPlaylist() {
    }

    public VideoPlaylist(ArrayList<VideoAsset> arrayList) {
        setVideoAssets(arrayList);
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

    public String getChannelDescription() {
        return this.mChannelDescription;
    }

    public void setChannelDescription(String str) {
        this.mChannelDescription = str;
    }

    public ArrayList<VideoAsset> getVideoAssets() {
        return this.mVideoAssets;
    }

    public int getVideoAssetsCount() {
        return this.mVideoAssets != null ? this.mVideoAssets.size() : 0;
    }

    public void setVideoAssets(ArrayList<VideoAsset> arrayList) {
        if (arrayList != null) {
            if (arrayList.size() != 0) {
                this.mVideoAssets = arrayList;
                if (this.mPlayingVideoAssetIndex < null) {
                    this.mPlayingVideoAssetIndex = 0;
                    this.mPlayingVideoAssetId = ((VideoAsset) this.mVideoAssets.get(0)).getAssetId();
                } else if (this.mPlayingVideoAssetIndex >= this.mVideoAssets.size() || this.mPlayingVideoAssetId.equals(((VideoAsset) this.mVideoAssets.get(this.mPlayingVideoAssetIndex)).getAssetId()) == null) {
                    notifyStartVideo(this.mPlayingVideoAssetId);
                }
            }
        }
    }

    public int getPlayMode() {
        return this.mPlayMode;
    }

    public void setPlayMode(int i) {
        this.mPlayMode = i;
    }

    public int getPlayingVideoAssetIndex() {
        return this.mPlayingVideoAssetIndex;
    }

    public String getPlayingVideoAssetId() {
        return this.mPlayingVideoAssetId;
    }

    public VideoAsset getPlayingVideoAsset() {
        return (this.mVideoAssets == null || this.mPlayingVideoAssetIndex < 0) ? null : (VideoAsset) this.mVideoAssets.get(this.mPlayingVideoAssetIndex);
    }

    public VideoAsset getPrevVideoAsset() {
        if (!hasPrevVideo()) {
            return null;
        }
        int i = this.mPlayingVideoAssetIndex - 1;
        if (i < 0) {
            i = getVideoAssetsCount() - 1;
        }
        return (VideoAsset) this.mVideoAssets.get(i);
    }

    public VideoAsset getNextVideoAsset() {
        if (!hasNextVideo()) {
            return null;
        }
        int i = this.mPlayingVideoAssetIndex + 1;
        if (i >= getVideoAssetsCount()) {
            i = 0;
        }
        return (VideoAsset) this.mVideoAssets.get(i);
    }

    public boolean hasPrevVideo() {
        if (getVideoAssetsCount() != 0) {
            if (this.mPlayingVideoAssetIndex >= 0) {
                if (this.mPlayMode != 1) {
                    if (this.mPlayMode != 2) {
                        if (this.mPlayingVideoAssetIndex > 0) {
                            return true;
                        }
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean hasNextVideo() {
        if (getVideoAssetsCount() != 0) {
            if (this.mPlayingVideoAssetIndex >= 0) {
                if (this.mPlayMode != 1) {
                    if (this.mPlayMode != 2) {
                        if (this.mPlayingVideoAssetIndex < getVideoAssetsCount() - 1) {
                            return true;
                        }
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean notifyStartVideo(int i) {
        if (i >= 0 && getVideoAssetsCount() != 0) {
            if (i < getVideoAssetsCount()) {
                this.mPlayingVideoAssetIndex = i;
                this.mPlayingVideoAssetId = ((VideoAsset) this.mVideoAssets.get(this.mPlayingVideoAssetIndex)).getAssetId();
                return true;
            }
        }
        return false;
    }

    public boolean notifyStartVideo(String str) {
        int i = 0;
        if (TextUtils.isEmpty(str) || getVideoAssetsCount() == 0) {
            return false;
        }
        this.mPlayingVideoAssetIndex = 0;
        int size = this.mVideoAssets.size();
        while (i < size) {
            if (str.equals(((VideoAsset) this.mVideoAssets.get(i)).getAssetId())) {
                this.mPlayingVideoAssetIndex = i;
                break;
            }
            i++;
        }
        this.mPlayingVideoAssetId = ((VideoAsset) this.mVideoAssets.get(this.mPlayingVideoAssetIndex)).getAssetId();
        return true;
    }

    public boolean notifyPrevVideo() {
        if (!hasPrevVideo()) {
            return false;
        }
        this.mPlayingVideoAssetIndex--;
        if (this.mPlayingVideoAssetIndex < 0) {
            this.mPlayingVideoAssetIndex = getVideoAssetsCount() - 1;
        }
        this.mPlayingVideoAssetId = ((VideoAsset) this.mVideoAssets.get(this.mPlayingVideoAssetIndex)).getAssetId();
        return true;
    }

    public boolean notifyNextVideo() {
        if (!hasNextVideo()) {
            return false;
        }
        this.mPlayingVideoAssetIndex++;
        if (this.mPlayingVideoAssetIndex >= getVideoAssetsCount()) {
            this.mPlayingVideoAssetIndex = 0;
        }
        this.mPlayingVideoAssetId = ((VideoAsset) this.mVideoAssets.get(this.mPlayingVideoAssetIndex)).getAssetId();
        return true;
    }
}
