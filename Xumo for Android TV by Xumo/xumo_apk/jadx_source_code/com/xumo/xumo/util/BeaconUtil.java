package com.xumo.xumo.util;

import android.net.Uri.Builder;
import android.text.TextUtils;
import androidx.recyclerview.widget.ItemTouchHelper.Callback;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.libraries.cast.companionlibrary.cast.player.VideoCastControllerActivity;
import com.xumo.xumo.application.XumoApplication;
import com.xumo.xumo.application.XumoContext;
import com.xumo.xumo.model.OnNowLive;
import com.xumo.xumo.model.VideoAsset;
import com.xumo.xumo.repository.UserPreferences;
import com.xumo.xumo.repository.UserPreferences.BeaconTimerListener;
import java.util.Date;
import org.json.JSONObject;

public class BeaconUtil {
    private static final String TAG = "Beacon";
    private static final String TRKID = "TRKID";
    private static final String beaconPath = "beacon.json";
    private static final String impressionPath = "impression.json";
    private static String urlAuthority = "android-tv-beacons.xumo.com";
    private static final String urlPath1 = "content";
    private static final String urlPath2 = "v2";
    private static final String urlScheme = "https";

    public enum AdBeaconEvents {
        AdRequested("adRequested"),
        AdStarted("adStarted"),
        AdCompleted("adCompleted"),
        AdPercentile("adPercentile"),
        AdError("adError"),
        AdSkipped("adSkipped"),
        AdPaused("adPaused"),
        AdResumed("adResumed");
        
        private final String description;

        private AdBeaconEvents(String str) {
            this.description = str;
        }

        public String description() {
            return this.description;
        }
    }

    public static class AdBeaconState {
        private static final int AD_ID_RAND_LEN = 12;
        static final String AD_TYPE = "IMA-3.9.4";
        String adId;
        private final AdPlacement adPlacement;
        private String curAdPodId = createNewAdPodId();
        private final String podId = createNewPodId();
        private int podIndex = -1;

        public AdBeaconState(AdPlacement adPlacement) {
            this.adPlacement = adPlacement;
            updateAdPod(1);
        }

        public void updateAdPod(int i) {
            if (this.podIndex != i) {
                this.podIndex = i;
                this.curAdPodId = createNewAdPodId();
                i = new StringBuilder();
                i.append(this.adPlacement.description);
                i.append("-");
                i.append(this.podIndex);
                i.append("-");
                i.append(this.podId);
                i.append("-");
                i.append(this.curAdPodId);
                this.adId = i.toString();
            }
        }

        private static String createNewPodId() {
            return XumoUtil.getRandomNumber(12);
        }

        private static String createNewAdPodId() {
            return XumoUtil.getRandomNumber(12);
        }
    }

    public enum AdPlacement {
        PreRoll("PRE"),
        MidRoll("MID"),
        PostRoll("POS");
        
        private final String description;

        private AdPlacement(String str) {
            this.description = str;
        }

        public String description() {
            return this.description;
        }
    }

    public enum AppErrors {
        InvalidGenreErrors("InvalidGenreErrors", "7002", "Invalid genre list data");
        
        private final String description;
        private final String errorCode;
        private final String rawValue;

        private AppErrors(String str, String str2, String str3) {
            this.rawValue = str;
            this.errorCode = str2;
            this.description = str3;
        }

        public String rawValue() {
            return this.rawValue;
        }

        public String errorCode() {
            return this.errorCode;
        }

        public String description() {
            return this.description;
        }
    }

    private enum BeaconItem {
        eventType,
        eventSubType,
        playId,
        clientPlayheadPosition,
        channelId,
        categoryId,
        assetId,
        providerId,
        clientTimeWatched,
        timestamp,
        interval,
        deviceId,
        contentInterval,
        errorCode,
        sessionId,
        clientVersion,
        pageId,
        pageViewId,
        channelPlayId,
        position,
        playReason,
        playType,
        programId,
        viewedItems,
        adPlayId,
        adPlayType
    }

    public enum DeepLinkType {
        Channel("channelId", ""),
        Category("channelId", "categoryId"),
        LiveChannel("channelId", ""),
        Asset("assetId", "");
        
        private final String actionValueName;
        private final String optionValueName;

        private DeepLinkType(String str, String str2) {
            this.actionValueName = str;
            this.optionValueName = str2;
        }

        public String actionValueName() {
            return this.actionValueName;
        }

        public String optionValueName() {
            return this.optionValueName;
        }
    }

    public enum ImpressionBeaconEvent {
        AppStart("appStart"),
        KeepAlive("keepAlive"),
        AppError("appError"),
        AppReport("appReport"),
        AdError("adError"),
        FavoriteChannelsViewed("favoriteChannelsView"),
        FavoriteChannelClicked("favoriteChannelClicked"),
        Favorited("favoriteClicked"),
        FeaturedChannelsViewed("featuredChannelsView"),
        FeaturedChannelClicked("featuredChannelClicked"),
        LiveChannelsViewed("liveChannelsView"),
        LiveChannelClicked("liveChannelClicked"),
        QuickChannelClicked("pigClicked"),
        GenreClicked("genreClicked"),
        MenuClicked("menuClicked"),
        AppForeGrounded("appForeGrounded"),
        AppBackGrounded("appBackGrounded"),
        pushOpen("pushOpen"),
        PageView("pageView");
        
        private final String rawValue;

        private ImpressionBeaconEvent(String str) {
            this.rawValue = str;
        }

        public String rawValue() {
            return this.rawValue;
        }
    }

    public enum NotificationType {
        Banner("banner"),
        Dialog(VideoCastControllerActivity.DIALOG_TAG);
        
        private final String rawValue;

        private NotificationType(String str) {
            this.rawValue = str;
        }

        public String rawValue() {
            return this.rawValue;
        }
    }

    public enum PlayReason {
        UserTriggered(0),
        AutoPlay(1),
        ContinousPlay(2);
        
        private final int id;

        private PlayReason(int i) {
            this.id = i;
        }

        public int rawValue() {
            return this.id;
        }
    }

    public enum PlayType {
        VOD("vod"),
        Live("live"),
        LiveLite("livelite");
        
        private final String description;

        private PlayType(String str) {
            this.description = str;
        }

        public String description() {
            return this.description;
        }
    }

    public enum VideoBeaconErrors {
        MediaNoError(0),
        MediaAborted(1),
        MediaNetworkError(2),
        MediaDecodeError(3),
        AdError(8002),
        MediaNotSupported(4);
        
        private final int id;

        private VideoBeaconErrors(int i) {
            this.id = i;
        }

        public int rawValue() {
            return this.id;
        }
    }

    public enum VideoBeaconEventType {
        content("content"),
        ad("ad");
        
        private final String rawValue;

        private VideoBeaconEventType(String str) {
            this.rawValue = str;
        }

        public String rawValue() {
            return this.rawValue;
        }
    }

    public enum VideoBeaconEvents {
        PlayRequested("playRequested"),
        PlaySuccess("playSuccess"),
        PlayStopped("playStopped"),
        PlayError("playError"),
        PlayStalled("playStalled"),
        PlayPaused("playPaused"),
        PlayResumed("playResumed"),
        PlayEnded("playEnded"),
        SeekStarted("seekStarted"),
        SeekEnded("seekEnded"),
        PlayInterval("playInterval"),
        AdError("adError"),
        CastStarted("castStarted");
        
        private final String description;

        private VideoBeaconEvents(String str) {
            this.description = str;
        }

        public String description() {
            return this.description;
        }
    }

    private static Builder getBeaconPath() {
        return new Builder().scheme(urlScheme).encodedAuthority(urlAuthority).appendPath("content").appendPath(urlPath2).appendPath(beaconPath);
    }

    private static Builder getImpressionPath() {
        return new Builder().scheme(urlScheme).encodedAuthority(urlAuthority).appendPath("content").appendPath(urlPath2).appendPath(impressionPath);
    }

    private static String buildBeaconURL(VideoAsset videoAsset, VideoBeaconEvents videoBeaconEvents, String str, String str2, String str3, VideoBeaconErrors videoBeaconErrors, String str4, int i, PlayReason playReason, PlayType playType, String str5, boolean z) {
        return buildBeaconURL(videoAsset, VideoBeaconEventType.content, videoBeaconEvents.description(), str, str2, str3, videoBeaconErrors != null ? videoBeaconErrors.rawValue() : 0, str4, i, playReason, playType, str5, z, null);
    }

    private static String buildBeaconURL(VideoAsset videoAsset, AdBeaconEvents adBeaconEvents, String str, String str2, String str3, int i, String str4, int i2, PlayReason playReason, PlayType playType, String str5, boolean z, AdBeaconState adBeaconState) {
        return buildBeaconURL(videoAsset, VideoBeaconEventType.ad, adBeaconEvents.description(), str, str2, str3, i, str4, i2, playReason, playType, str5, z, adBeaconState);
    }

    private static String buildBeaconURL(VideoAsset videoAsset, VideoBeaconEventType videoBeaconEventType, String str, String str2, String str3, String str4, int i, String str5, int i2, PlayReason playReason, PlayType playType, String str6, boolean z, AdBeaconState adBeaconState) {
        String str7 = str;
        String str8 = str6;
        AdBeaconState adBeaconState2 = adBeaconState;
        if (videoAsset == null) {
            return null;
        }
        StringBuilder stringBuilder;
        String pageId;
        CharSequence pageViewId;
        String str9;
        String str10;
        StringBuilder stringBuilder2;
        Builder beaconPath = getBeaconPath();
        beaconPath.appendQueryParameter(BeaconItem.eventType.toString(), videoBeaconEventType.toString());
        beaconPath.appendQueryParameter(BeaconItem.eventSubType.toString(), str);
        if (getDeviceId() != null) {
            if (!getDeviceId().isEmpty()) {
                beaconPath.appendQueryParameter(BeaconItem.deviceId.toString(), getDeviceId());
                beaconPath.appendQueryParameter(BeaconItem.sessionId.toString(), UserPreferences.session.sessionId);
                stringBuilder = new StringBuilder();
                stringBuilder.append("******sessionId******:");
                stringBuilder.append(UserPreferences.session.sessionId);
                LogUtil.w(stringBuilder.toString());
                pageId = UserPreferences.getInstance().getPageId();
                if (pageId.getBytes().length > 0) {
                    beaconPath.appendQueryParameter(BeaconItem.pageId.toString(), pageId);
                }
                pageViewId = UserPreferences.getInstance().getPageViewId();
                if (pageViewId.getBytes().length > 0) {
                    beaconPath.appendQueryParameter(BeaconItem.pageViewId.toString(), pageViewId);
                }
                str9 = "0";
                if (!(videoAsset.getChannelId() == null || videoAsset.getChannelId().isEmpty())) {
                    str9 = videoAsset.getChannelId();
                }
                beaconPath.appendQueryParameter(BeaconItem.channelId.toString(), str9);
                if (!TextUtils.isEmpty(pageViewId)) {
                    if (!z) {
                        beaconPath.appendQueryParameter(BeaconItem.channelPlayId.toString(), UserPreferences.getInstance().getChannelPlayId());
                    } else if (str8 != null) {
                        pageId = BeaconItem.channelPlayId.toString();
                        StringBuilder stringBuilder3 = new StringBuilder();
                        stringBuilder3.append(UserPreferences.getInstance().getChannelPlayId());
                        stringBuilder3.append(TRKID);
                        stringBuilder3.append(str8);
                        beaconPath.appendQueryParameter(pageId, stringBuilder3.toString());
                    }
                }
                beaconPath.appendQueryParameter(BeaconItem.programId.toString(), "0");
                str8 = "0";
                if (videoAsset.getCategoryId() != null && videoAsset.getCategoryId().getBytes().length > 0) {
                    str8 = videoAsset.getCategoryId();
                }
                beaconPath.appendQueryParameter(BeaconItem.categoryId.toString(), str8);
                beaconPath.appendQueryParameter(BeaconItem.providerId.toString(), String.valueOf(videoAsset.getProviderId()));
                beaconPath.appendQueryParameter(BeaconItem.assetId.toString(), videoAsset.getAssetId());
                str10 = str5;
                beaconPath.appendQueryParameter(BeaconItem.playId.toString(), str5);
                beaconPath.appendQueryParameter(BeaconItem.position.toString(), String.valueOf(i2));
                beaconPath.appendQueryParameter(BeaconItem.playReason.toString(), String.valueOf(playReason.rawValue()));
                beaconPath.appendQueryParameter(BeaconItem.playType.toString(), playType.description());
                str10 = str2;
                beaconPath.appendQueryParameter(BeaconItem.clientPlayheadPosition.toString(), str2);
                str10 = str3;
                beaconPath.appendQueryParameter(BeaconItem.clientTimeWatched.toString(), str3);
                beaconPath.appendQueryParameter(BeaconItem.timestamp.toString(), currentTimeInMilliseconds());
                if (TextUtils.equals(str, VideoBeaconEvents.PlayInterval.description())) {
                    str8 = str4;
                    beaconPath.appendQueryParameter(BeaconItem.contentInterval.toString(), str4);
                }
                if (i != 0) {
                    beaconPath.appendQueryParameter(BeaconItem.errorCode.toString(), String.valueOf(i));
                }
                str7 = BeaconItem.clientVersion.toString();
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("1.1.");
                stringBuilder2.append(String.valueOf(34));
                beaconPath.appendQueryParameter(str7, stringBuilder2.toString());
                if (videoBeaconEventType == VideoBeaconEventType.ad && adBeaconState2 != null) {
                    beaconPath.appendQueryParameter(BeaconItem.adPlayId.toString(), adBeaconState2.adId);
                    beaconPath.appendQueryParameter(BeaconItem.adPlayType.toString(), "IMA-3.9.4");
                }
                return beaconPath.build().toString();
            }
        }
        beaconPath.appendQueryParameter(BeaconItem.deviceId.toString(), "undefined");
        beaconPath.appendQueryParameter(BeaconItem.sessionId.toString(), UserPreferences.session.sessionId);
        stringBuilder = new StringBuilder();
        stringBuilder.append("******sessionId******:");
        stringBuilder.append(UserPreferences.session.sessionId);
        LogUtil.w(stringBuilder.toString());
        pageId = UserPreferences.getInstance().getPageId();
        if (pageId.getBytes().length > 0) {
            beaconPath.appendQueryParameter(BeaconItem.pageId.toString(), pageId);
        }
        pageViewId = UserPreferences.getInstance().getPageViewId();
        if (pageViewId.getBytes().length > 0) {
            beaconPath.appendQueryParameter(BeaconItem.pageViewId.toString(), pageViewId);
        }
        str9 = "0";
        str9 = videoAsset.getChannelId();
        beaconPath.appendQueryParameter(BeaconItem.channelId.toString(), str9);
        if (TextUtils.isEmpty(pageViewId)) {
            if (!z) {
                beaconPath.appendQueryParameter(BeaconItem.channelPlayId.toString(), UserPreferences.getInstance().getChannelPlayId());
            } else if (str8 != null) {
                pageId = BeaconItem.channelPlayId.toString();
                StringBuilder stringBuilder32 = new StringBuilder();
                stringBuilder32.append(UserPreferences.getInstance().getChannelPlayId());
                stringBuilder32.append(TRKID);
                stringBuilder32.append(str8);
                beaconPath.appendQueryParameter(pageId, stringBuilder32.toString());
            }
        }
        beaconPath.appendQueryParameter(BeaconItem.programId.toString(), "0");
        str8 = "0";
        str8 = videoAsset.getCategoryId();
        beaconPath.appendQueryParameter(BeaconItem.categoryId.toString(), str8);
        beaconPath.appendQueryParameter(BeaconItem.providerId.toString(), String.valueOf(videoAsset.getProviderId()));
        beaconPath.appendQueryParameter(BeaconItem.assetId.toString(), videoAsset.getAssetId());
        str10 = str5;
        beaconPath.appendQueryParameter(BeaconItem.playId.toString(), str5);
        beaconPath.appendQueryParameter(BeaconItem.position.toString(), String.valueOf(i2));
        beaconPath.appendQueryParameter(BeaconItem.playReason.toString(), String.valueOf(playReason.rawValue()));
        beaconPath.appendQueryParameter(BeaconItem.playType.toString(), playType.description());
        str10 = str2;
        beaconPath.appendQueryParameter(BeaconItem.clientPlayheadPosition.toString(), str2);
        str10 = str3;
        beaconPath.appendQueryParameter(BeaconItem.clientTimeWatched.toString(), str3);
        beaconPath.appendQueryParameter(BeaconItem.timestamp.toString(), currentTimeInMilliseconds());
        if (TextUtils.equals(str, VideoBeaconEvents.PlayInterval.description())) {
            str8 = str4;
            beaconPath.appendQueryParameter(BeaconItem.contentInterval.toString(), str4);
        }
        if (i != 0) {
            beaconPath.appendQueryParameter(BeaconItem.errorCode.toString(), String.valueOf(i));
        }
        str7 = BeaconItem.clientVersion.toString();
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("1.1.");
        stringBuilder2.append(String.valueOf(34));
        beaconPath.appendQueryParameter(str7, stringBuilder2.toString());
        beaconPath.appendQueryParameter(BeaconItem.adPlayId.toString(), adBeaconState2.adId);
        beaconPath.appendQueryParameter(BeaconItem.adPlayType.toString(), "IMA-3.9.4");
        return beaconPath.build().toString();
    }

    private static String buildImpressionURL(ImpressionBeaconEvent impressionBeaconEvent, AppErrors appErrors, String[] strArr, OnNowLive onNowLive) {
        if (impressionBeaconEvent == null) {
            return null;
        }
        StringBuilder stringBuilder;
        Builder impressionPath = getImpressionPath();
        impressionPath.appendQueryParameter(BeaconItem.eventType.toString(), impressionBeaconEvent.rawValue());
        String beaconItem = BeaconItem.clientVersion.toString();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("1.1.");
        stringBuilder2.append(String.valueOf(34));
        impressionPath.appendQueryParameter(beaconItem, stringBuilder2.toString());
        impressionPath.appendQueryParameter(BeaconItem.timestamp.toString(), currentTimeInMilliseconds());
        if (getDeviceId() != null) {
            if (!getDeviceId().isEmpty()) {
                impressionPath.appendQueryParameter(BeaconItem.deviceId.toString(), getDeviceId());
                impressionPath.appendQueryParameter(BeaconItem.sessionId.toString(), UserPreferences.session.sessionId);
                stringBuilder = new StringBuilder();
                stringBuilder.append("******sessionId******:");
                stringBuilder.append(UserPreferences.session.sessionId);
                LogUtil.w(stringBuilder.toString());
                beaconItem = UserPreferences.getInstance().getPageId();
                if (beaconItem.getBytes().length > 0) {
                    impressionPath.appendQueryParameter(BeaconItem.pageId.toString(), beaconItem);
                }
                beaconItem = UserPreferences.getInstance().getPageViewId();
                if (beaconItem.getBytes().length > 0) {
                    impressionPath.appendQueryParameter(BeaconItem.pageViewId.toString(), beaconItem);
                }
                if (impressionBeaconEvent == ImpressionBeaconEvent.KeepAlive) {
                    impressionPath.appendQueryParameter(BeaconItem.interval.toString(), String.valueOf(UserPreferences.getInstance().getTimeInSecondsSinceAppLaunchIfAvailable()));
                } else if (impressionBeaconEvent != ImpressionBeaconEvent.AppError) {
                    impressionPath.appendQueryParameter(BeaconItem.channelId.toString(), "0");
                    if (appErrors == null) {
                        impressionPath.appendQueryParameter(BeaconItem.viewedItems.toString(), String.format("%s,%s", new Object[]{appErrors.errorCode(), appErrors.description()}));
                    } else if (strArr != null && strArr.length > null) {
                        impressionPath.appendQueryParameter(BeaconItem.viewedItems.toString(), XumoUtil.joinStrings(",", strArr));
                    }
                } else if (impressionBeaconEvent != ImpressionBeaconEvent.PageView && onNowLive != null) {
                    impressionPath.appendQueryParameter(BeaconItem.channelId.toString(), onNowLive.getChannelId());
                } else if (impressionBeaconEvent != ImpressionBeaconEvent.AppStart) {
                    if (!(impressionBeaconEvent == ImpressionBeaconEvent.LiveChannelsViewed || impressionBeaconEvent == ImpressionBeaconEvent.FeaturedChannelsViewed || impressionBeaconEvent == ImpressionBeaconEvent.FavoriteChannelsViewed || impressionBeaconEvent == ImpressionBeaconEvent.MenuClicked || onNowLive == null)) {
                        impressionPath.appendQueryParameter(BeaconItem.categoryId.toString(), String.valueOf(onNowLive.getGenreId()));
                        impressionPath.appendQueryParameter(BeaconItem.channelId.toString(), onNowLive.getChannelId());
                    }
                    if (strArr != null) {
                        impressionPath.appendQueryParameter(BeaconItem.viewedItems.toString(), XumoUtil.joinStrings(",", strArr));
                    }
                }
                return impressionPath.build().toString();
            }
        }
        impressionPath.appendQueryParameter(BeaconItem.deviceId.toString(), "undefined");
        impressionPath.appendQueryParameter(BeaconItem.sessionId.toString(), UserPreferences.session.sessionId);
        stringBuilder = new StringBuilder();
        stringBuilder.append("******sessionId******:");
        stringBuilder.append(UserPreferences.session.sessionId);
        LogUtil.w(stringBuilder.toString());
        beaconItem = UserPreferences.getInstance().getPageId();
        if (beaconItem.getBytes().length > 0) {
            impressionPath.appendQueryParameter(BeaconItem.pageId.toString(), beaconItem);
        }
        beaconItem = UserPreferences.getInstance().getPageViewId();
        if (beaconItem.getBytes().length > 0) {
            impressionPath.appendQueryParameter(BeaconItem.pageViewId.toString(), beaconItem);
        }
        if (impressionBeaconEvent == ImpressionBeaconEvent.KeepAlive) {
            impressionPath.appendQueryParameter(BeaconItem.interval.toString(), String.valueOf(UserPreferences.getInstance().getTimeInSecondsSinceAppLaunchIfAvailable()));
        } else if (impressionBeaconEvent != ImpressionBeaconEvent.AppError) {
            if (impressionBeaconEvent != ImpressionBeaconEvent.PageView) {
            }
            if (impressionBeaconEvent != ImpressionBeaconEvent.AppStart) {
                impressionPath.appendQueryParameter(BeaconItem.categoryId.toString(), String.valueOf(onNowLive.getGenreId()));
                impressionPath.appendQueryParameter(BeaconItem.channelId.toString(), onNowLive.getChannelId());
                if (strArr != null) {
                    impressionPath.appendQueryParameter(BeaconItem.viewedItems.toString(), XumoUtil.joinStrings(",", strArr));
                }
            }
        } else {
            impressionPath.appendQueryParameter(BeaconItem.channelId.toString(), "0");
            if (appErrors == null) {
                impressionPath.appendQueryParameter(BeaconItem.viewedItems.toString(), XumoUtil.joinStrings(",", strArr));
            } else {
                impressionPath.appendQueryParameter(BeaconItem.viewedItems.toString(), String.format("%s,%s", new Object[]{appErrors.errorCode(), appErrors.description()}));
            }
        }
        return impressionPath.build().toString();
    }

    public static void sendBeaconVideo(VideoAsset videoAsset, VideoBeaconEvents videoBeaconEvents, String str, String str2, String str3, VideoBeaconErrors videoBeaconErrors, String str4, int i, PlayReason playReason, PlayType playType, String str5, boolean z) {
        String str6 = str3 == null ? "0" : str3;
        String str7 = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Video Beacon event = ");
        VideoBeaconEvents videoBeaconEvents2 = videoBeaconEvents;
        stringBuilder.append(videoBeaconEvents);
        LogUtil.d(str7, stringBuilder.toString());
        str7 = buildBeaconURL(videoAsset, videoBeaconEvents2, str, str2, str6, videoBeaconErrors, str4, i, playReason, playType, str5, z);
        if (str7 != null) {
            LogUtil.d(TAG, str7);
            ((XumoApplication) XumoContext.getInstance().getApplicationContext().getApplicationContext()).getRequestQueue().add(new JsonObjectRequest(null, str7, null, new Listener<JSONObject>() {
                public void onResponse(JSONObject jSONObject) {
                    String str = BeaconUtil.TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Content Beacon response: ");
                    stringBuilder.append(jSONObject);
                    LogUtil.d(str, stringBuilder.toString());
                }
            }, new ErrorListener() {
                public void onErrorResponse(VolleyError volleyError) {
                }
            }) {
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse networkResponse) {
                    if (networkResponse == null) {
                        LogUtil.i(BeaconUtil.TAG, "Video Beacon response=null.");
                    } else if (networkResponse.statusCode < Callback.DEFAULT_DRAG_ANIMATION_DURATION || networkResponse.statusCode >= 300) {
                        String str = BeaconUtil.TAG;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Video Beacon statusCode=");
                        stringBuilder.append(networkResponse.statusCode);
                        LogUtil.i(str, stringBuilder.toString());
                    }
                    return super.parseNetworkResponse(networkResponse);
                }
            });
            return;
        }
        LogUtil.d(TAG, "Cannot build Content Beacon URL!!!");
    }

    public static void sendBeaconImpression(ImpressionBeaconEvent impressionBeaconEvent, AppErrors appErrors, String[] strArr) {
        sendBeaconImpression(impressionBeaconEvent, appErrors, strArr, null);
    }

    public static void sendBeaconImpression(ImpressionBeaconEvent impressionBeaconEvent, AppErrors appErrors, String[] strArr, OnNowLive onNowLive) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Impression Beacon event = ");
        stringBuilder.append(impressionBeaconEvent);
        LogUtil.d(str, stringBuilder.toString());
        String buildImpressionURL = buildImpressionURL(impressionBeaconEvent, appErrors, strArr, onNowLive);
        if (buildImpressionURL != null) {
            LogUtil.d(TAG, buildImpressionURL);
            ((XumoApplication) XumoContext.getInstance().getApplicationContext().getApplicationContext()).getRequestQueue().add(new JsonObjectRequest(0, buildImpressionURL, null, new Listener<JSONObject>() {
                public void onResponse(JSONObject jSONObject) {
                    String str = BeaconUtil.TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Impression Beacon response: ");
                    stringBuilder.append(jSONObject);
                    LogUtil.d(str, stringBuilder.toString());
                }
            }, new ErrorListener() {
                public void onErrorResponse(VolleyError volleyError) {
                }
            }) {
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse networkResponse) {
                    String str;
                    StringBuilder stringBuilder;
                    if (networkResponse == null) {
                        LogUtil.i(BeaconUtil.TAG, "Impression Beacon response=null.");
                    } else if (networkResponse.statusCode < Callback.DEFAULT_DRAG_ANIMATION_DURATION || networkResponse.statusCode >= 300) {
                        str = BeaconUtil.TAG;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Impression Beacon Status:");
                        stringBuilder.append(networkResponse.statusCode);
                        LogUtil.i(str, stringBuilder.toString());
                    }
                    str = BeaconUtil.TAG;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Impression Beacon send response: ");
                    stringBuilder.append(networkResponse.statusCode);
                    LogUtil.i(str, stringBuilder.toString());
                    return super.parseNetworkResponse(networkResponse);
                }
            });
            return;
        }
        LogUtil.i(TAG, "Cannot build Impression Beacon URL!!!");
    }

    public static void sendBeaconAd(VideoAsset videoAsset, AdBeaconEvents adBeaconEvents, String str, String str2, String str3, int i, String str4, int i2, PlayReason playReason, PlayType playType, String str5, boolean z, AdBeaconState adBeaconState) {
        String str6 = TextUtils.isEmpty(str3) ? "0" : str3;
        String str7 = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Ad Beacon event = ");
        AdBeaconEvents adBeaconEvents2 = adBeaconEvents;
        stringBuilder.append(adBeaconEvents);
        LogUtil.d(str7, stringBuilder.toString());
        str7 = buildBeaconURL(videoAsset, adBeaconEvents2, str, str2, str6, i, str4, i2, playReason, playType, str5, z, adBeaconState);
        if (str7 != null) {
            LogUtil.d(TAG, str7);
            ((XumoApplication) XumoContext.getInstance().getApplicationContext().getApplicationContext()).getRequestQueue().add(new JsonObjectRequest(null, str7, null, new Listener<JSONObject>() {
                public void onResponse(JSONObject jSONObject) {
                    String str = BeaconUtil.TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Ad Video Beacon response: ");
                    stringBuilder.append(jSONObject);
                    LogUtil.d(str, stringBuilder.toString());
                }
            }, new ErrorListener() {
                public void onErrorResponse(VolleyError volleyError) {
                }
            }) {
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse networkResponse) {
                    if (networkResponse == null) {
                        LogUtil.i(BeaconUtil.TAG, "Ad Video Beacon response=null.");
                    } else if (networkResponse.statusCode < Callback.DEFAULT_DRAG_ANIMATION_DURATION || networkResponse.statusCode >= 300) {
                        String str = BeaconUtil.TAG;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Ad Video Beacon Status:");
                        stringBuilder.append(networkResponse.statusCode);
                        LogUtil.i(str, stringBuilder.toString());
                    }
                    return super.parseNetworkResponse(networkResponse);
                }
            });
            return;
        }
        LogUtil.d(TAG, "Cannot build Content Beacon URL!!!");
    }

    public static void startKeepAliveImpressionsBeaconTimerIfNecessary() {
        UserPreferences.getInstance().startTimerForBeaconIfNecessaryWithTarget(30, new BeaconTimerListener() {
            public void termination() {
                BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.KeepAlive, null, null);
            }
        });
    }

    public static void stopKeepAliveImpressionsBeaconTimer() {
        UserPreferences.getInstance().invalidateTimerForBeacon();
    }

    private static String getDeviceId() {
        String str = "";
        String deviceId = UserPreferences.getInstance().getDeviceId();
        if (TextUtils.isEmpty(deviceId)) {
            return str;
        }
        int length = deviceId.length();
        if ("XumoDeviceId ".length() < length && deviceId.startsWith("XumoDeviceId ", 0)) {
            str = deviceId.substring("XumoDeviceId ".length(), length);
        }
        return str;
    }

    private static String currentTimeInMilliseconds() {
        return String.valueOf(new Date().getTime());
    }
}
