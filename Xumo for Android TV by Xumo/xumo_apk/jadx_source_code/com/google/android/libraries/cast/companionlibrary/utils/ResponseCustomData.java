package com.google.android.libraries.cast.companionlibrary.utils;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.libraries.cast.companionlibrary.utils.CustomData.PLAYTYPE;
import com.xumo.xumo.util.LogUtil;
import org.json.JSONObject;

public class ResponseCustomData {
    private static final String ASSET_THUMBNAIL_FORMAT = "https://image.xumo.com/v1/assets/asset/%s/480x300.jpeg";
    private static final String CATEGORYNAME = "categoryName";
    private static final String KEY_ASSET_ID = "assetId";
    private static final String KEY_ASSET_TITLE = "assetTitle";
    private static final String KEY_AVAILABLE_NEXT_ASSET = "availableNextAsset";
    private static final String KEY_AVAILABLE_PREV_ASSET = "availablePrevAsset";
    private static final String KEY_CATGORY_ID = "categoryId";
    private static final String KEY_CHANNEL_ID = "channelId";
    private static final String KEY_DEVICE_ID = "deviceId";
    private static final String KEY_HAS_CAPTIONS = "hasCaptions";
    private static final String KEY_IS_AD = "isAd";
    private static final String KEY_IS_CAPTIONS_ENABLED = "isCaptionsEnabled";
    private static final String KEY_PLAYER_SUB_TYPE = "playSubType";
    private static final String KEY_PLAYER_TYPE = "playType";
    private static final String KEY_SESSION_ID = "sessionId";
    private static final String LIVEDESCRIPTION = "liveDescription";
    public String assetId;
    public Uri assetThumbnailUri;
    public String assetTitle;
    public boolean availableNextAsset;
    public boolean availablePrevAsset;
    public String categoryId;
    public String categoryName;
    public String channelId;
    public String deviceId;
    public boolean hasCaptions;
    public boolean isAd;
    public boolean isCaptionsEnabled;
    private JSONObject jsonObject;
    public String liveDescription;
    public String playSubType;
    public String playType;
    public String sessionId;

    public ResponseCustomData(JSONObject jSONObject) {
        String str;
        StringBuilder stringBuilder;
        if (jSONObject != null) {
            this.jsonObject = jSONObject;
            try {
                str = LogUtil.CCL;
                stringBuilder = new StringBuilder();
                stringBuilder.append("jsonObject=");
                stringBuilder.append(jSONObject.toString(4));
                LogUtils.d(str, stringBuilder.toString());
            } catch (JSONObject jSONObject2) {
                str = LogUtil.CCL;
                stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(jSONObject2.getMessage());
                LogUtils.d(str, stringBuilder.toString());
            }
            this.playType = getJsonString("playType");
            this.playSubType = getJsonString("playSubType");
            this.channelId = getJsonString("channelId");
            this.categoryId = getJsonString("categoryId");
            this.assetId = getJsonString("assetId");
            this.assetThumbnailUri = Uri.parse(String.format(ASSET_THUMBNAIL_FORMAT, new Object[]{this.assetId}));
            this.assetTitle = getJsonString(KEY_ASSET_TITLE);
            this.isAd = getJsonBoolean(KEY_IS_AD);
            this.availablePrevAsset = getJsonBoolean(KEY_AVAILABLE_PREV_ASSET);
            this.availableNextAsset = getJsonBoolean(KEY_AVAILABLE_NEXT_ASSET);
            this.hasCaptions = getJsonBoolean(KEY_HAS_CAPTIONS);
            this.isCaptionsEnabled = getJsonBoolean(KEY_IS_CAPTIONS_ENABLED);
            this.categoryName = getJsonString(CATEGORYNAME);
            this.liveDescription = getJsonString(LIVEDESCRIPTION);
            this.deviceId = getJsonString("deviceId");
            this.sessionId = getJsonString("sessionId");
        }
    }

    private String getJsonString(String str) {
        try {
            return this.jsonObject.getString(str);
        } catch (String str2) {
            String str3 = LogUtil.CCL;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(str2.getMessage());
            LogUtils.d(str3, stringBuilder.toString());
            return null;
        }
    }

    private boolean getJsonBoolean(String str) {
        try {
            return this.jsonObject.getBoolean(str);
        } catch (String str2) {
            String str3 = LogUtil.CCL;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(str2.getMessage());
            LogUtils.d(str3, stringBuilder.toString());
            return null;
        }
    }

    public boolean isBroadcast() {
        return TextUtils.equals(this.playType, PLAYTYPE.BROADCAST);
    }
}
