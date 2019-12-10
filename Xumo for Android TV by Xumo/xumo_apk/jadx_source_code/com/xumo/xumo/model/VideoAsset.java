package com.xumo.xumo.model;

import android.content.Context;
import com.xumo.xumo.service.XumoWebService;
import com.xumo.xumo.service.XumoWebService.NoResponseListener;
import com.xumo.xumo.tv.R;
import com.xumo.xumo.util.XumoUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoAsset implements Serializable {
    private int axisX;
    private int axisY;
    private int bitrate = 0;
    private int height = 0;
    private boolean loadMoreFlag = false;
    private Date mAssetAge;
    private String mAssetId = "";
    private int mAssetType = 1;
    private String mCategoryId = "";
    private String mCategoryTitle = "";
    private String mChannelId = "";
    private String mContentType = "";
    private float[] mCuePoints;
    private String mDescriptionText = "";
    private String mDfxpCaption = "";
    private boolean mHasCaption;
    private int mHits = 0;
    private String mProviderAssetId = "";
    private int mProviderId = 0;
    private String mProviderTitle = "";
    private String mRatings = "";
    protected Date mRealTimeStartTime;
    private int mReleaseYear = 0;
    private long mRunTime = 0;
    private String mSrtCaption = "";
    private String mTitle = "";
    private String mUrl = "";
    private String mVttCaption = "";
    private String mlang = "";
    private boolean nowPlaying = false;
    private int width = 0;

    public static class AssetType implements Serializable {
        public static final int LIVE = 2;
        public static final int PROMOTED = 3;
        public static final int VIDEO = 1;
    }

    public long getRelativeVideoStartTime() {
        return 0;
    }

    public String getThumbnailURL() {
        return null;
    }

    public boolean isLoadMoreFlag() {
        return this.loadMoreFlag;
    }

    public void setLoadMoreFlag(boolean z) {
        this.loadMoreFlag = z;
    }

    public boolean isNowPlaying() {
        return this.nowPlaying;
    }

    public void setNowPlaying(boolean z) {
        this.nowPlaying = z;
    }

    public VideoAsset(String str, String str2, String str3) {
        this.mAssetId = str;
        this.mCategoryId = str2;
        this.mChannelId = str3;
    }

    public String getChannelId() {
        return this.mChannelId;
    }

    public void setChannelId(String str) {
        this.mChannelId = str;
    }

    public String getAssetId() {
        return this.mAssetId;
    }

    public void setAssetId(String str) {
        this.mAssetId = str;
    }

    public String getCategoryId() {
        return this.mCategoryId;
    }

    public void setCategoryId(String str) {
        this.mCategoryId = str;
    }

    public String getCategoryTitle() {
        return this.mCategoryTitle;
    }

    public void setCategoryTitle(String str) {
        this.mCategoryTitle = str;
    }

    public int getProviderId() {
        return this.mProviderId;
    }

    public void setProviderId(int i) {
        this.mProviderId = i;
    }

    public String getmProviderTitle() {
        return this.mProviderTitle;
    }

    public void setmProviderTitle(String str) {
        this.mProviderTitle = str;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String str) {
        this.mTitle = removeLeadingExtraTitleChars(str);
    }

    public String getmRatings() {
        return this.mRatings;
    }

    public void setmRatings(String str) {
        this.mRatings = str;
    }

    public int getmReleaseYear() {
        return this.mReleaseYear;
    }

    public void setmReleaseYear(int i) {
        this.mReleaseYear = i;
    }

    public String getDescriptionText() {
        return this.mDescriptionText;
    }

    public void setDescriptionText(String str) {
        this.mDescriptionText = str;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public void setUrl(String str) {
        this.mUrl = str;
    }

    public String getmlang() {
        return this.mlang;
    }

    public void setmlang(String str) {
        this.mlang = str;
    }

    public int getBitrate() {
        return this.bitrate;
    }

    public void setBitrate(int i) {
        this.bitrate = i;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public long getRunTime() {
        return this.mRunTime;
    }

    public void setRunTime(long j) {
        this.mRunTime = j;
    }

    public Date getAssetAge() {
        return this.mAssetAge;
    }

    public void setAssetAge(Date date) {
        this.mAssetAge = date;
    }

    public int getAssetType() {
        return this.mAssetType;
    }

    public void setAssetType(int i) {
        this.mAssetType = i;
    }

    public String getContentType() {
        return this.mContentType;
    }

    public void setContentType(String str) {
        this.mContentType = str;
    }

    public String getProviderAssetId() {
        return this.mProviderAssetId;
    }

    public void setProviderAssetId(String str) {
        this.mProviderAssetId = str;
    }

    public int getAxisX() {
        return this.axisX;
    }

    public void setAxisX(int i) {
        this.axisX = i;
    }

    public int getAxisY() {
        return this.axisY;
    }

    public void setAxisY(int i) {
        this.axisY = i;
    }

    public int getmHits() {
        return this.mHits;
    }

    public void setmHits(int i) {
        this.mHits = i;
    }

    public void getAssetMetaData(final NoResponseListener noResponseListener) {
        XumoWebService.getInstance().getVideoMetadata(this, new NoResponseListener() {
            public void onCompletion() {
                noResponseListener.onCompletion();
            }

            public void onError() {
                noResponseListener.onError();
            }
        });
    }

    public String getRunTimeString() {
        return XumoUtil.getProperTime(this.mRunTime);
    }

    public String getVideoAssetAge(Context context) {
        Date date = new Date(0);
        if (!(this.mAssetAge == null || this.mAssetAge == date)) {
            date = new Date();
            if (date.getTime() > this.mAssetAge.getTime()) {
                double time = (double) ((date.getTime() - this.mAssetAge.getTime()) / 1000);
                if (time < 60.0d) {
                    return context.getResources().getString(R.string.video_asset_age_now);
                }
                if (time < 120.0d) {
                    return context.getResources().getString(R.string.video_asset_age_1_min_ago);
                }
                Object[] objArr;
                if (time < 3600.0d) {
                    context = context.getResources();
                    objArr = new Object[1];
                    Double.isNaN(time);
                    objArr[0] = Integer.valueOf((int) (time / 60.0d));
                    return context.getString(R.string.video_asset_age_mins_ago, objArr);
                } else if (time < 7200.0d) {
                    return context.getResources().getString(R.string.video_asset_age_1_hour_ago);
                } else {
                    if (time < 43200.0d) {
                        context = context.getResources();
                        Object[] objArr2 = new Object[1];
                        Double.isNaN(time);
                        objArr2[0] = Integer.valueOf((int) (time / 3600.0d));
                        return context.getString(R.string.video_asset_age_hours_ago, objArr2);
                    } else if (time < 86400.0d) {
                        return context.getResources().getString(R.string.video_asset_age_today);
                    } else {
                        if (time < 172800.0d) {
                            return context.getResources().getString(R.string.video_asset_age_yesterday);
                        }
                        if (time < 345600.0d) {
                            context = context.getResources();
                            objArr = new Object[1];
                            Double.isNaN(time);
                            objArr[0] = Integer.valueOf((int) (time / 86400.0d));
                            return context.getString(R.string.video_asset_age_days_ago, objArr);
                        }
                    }
                }
            }
        }
        return null;
    }

    private String removeLeadingExtraTitleChars(String str) {
        if (str != null) {
            Matcher matcher = Pattern.compile("(\\W+)([\\w\\W]+)").matcher(str);
            if (matcher.matches() && matcher.groupCount() >= 2) {
                return matcher.group(2);
            }
        }
        return str;
    }

    public void setRealTimeStartTime(Date date) {
        this.mRealTimeStartTime = date;
    }

    public float[] getCuePoints() {
        return this.mCuePoints;
    }

    public void setCuePoints(float[] fArr) {
        this.mCuePoints = fArr;
    }

    public boolean ismHasCaption() {
        return this.mHasCaption;
    }

    public void setmHasCaption(boolean z) {
        this.mHasCaption = z;
    }

    public String getmVttCaption() {
        return this.mVttCaption;
    }

    public void setmVttCaption(String str) {
        this.mVttCaption = str;
    }

    public String getmDfxpCaption() {
        return this.mDfxpCaption;
    }

    public void setmDfxpCaption(String str) {
        this.mDfxpCaption = str;
    }

    public String getmSrtCaption() {
        return this.mSrtCaption;
    }

    public void setmSrtCaption(String str) {
        this.mSrtCaption = str;
    }
}
