package com.xumo.xumo.service;

import android.content.Context;
import android.net.Uri.Builder;
import android.text.TextUtils;
import androidx.core.os.EnvironmentCompat;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.xumo.xumo.application.XumoApplication;
import com.xumo.xumo.application.XumoContext;
import com.xumo.xumo.model.Category;
import com.xumo.xumo.model.DeviceIdList;
import com.xumo.xumo.model.LiveAsset;
import com.xumo.xumo.model.VideoAsset;
import com.xumo.xumo.repository.UserPreferences;
import com.xumo.xumo.util.BeaconUtil;
import com.xumo.xumo.util.BeaconUtil.ImpressionBeaconEvent;
import com.xumo.xumo.util.LogUtil;
import com.xumo.xumo.util.XumoUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

public class XumoWebService {
    private static final String AUTHORIZATION_KEY = "Authorization";
    private static final int CATEGORY_DEFAULT_OFFSET = 0;
    private static final int DEFAULT_TIMEOUT_MS = 10000;
    public static final String DEVICE_ID_AUTH_PREFIX = "XumoDeviceId";
    private static final String GET_CHANNELS_LIST_URL = "channels/list/%s.json?f=genreId&sort=hybrid&geoId=%s";
    private static final String GET_CHANNEL_ASSETS_METADATA_URL = "assets/asset/%s.json?f=title&f=descriptions&f=providers&f=runtime&f=availableSince&f=cuePoints";
    private static final String GET_CHANNEL_ASSETS_URL = "categories/category/%s.json?offset=%d&limit=20&f=asset.title&f=asset.runtime&f=asset.availableSince&f=asset.ratings&f=asset.releaseYear";
    private static final String GET_CHANNEL_CATEGORIES_URL = "channels/channel/%s/categories.json";
    private static final String GET_CHANNEL_LIVE_CONTENT_URL = "channels/channel/%s/broadcast.json?hour=%d&year=%d&month=%d&day=%d";
    private static final String GET_DEVICE_SETTINGS_URL = "devices/device/settings.json";
    private static final String GET_GENRES_URL = "channels/list/%s/genres.json";
    private static final String GET_LIVE_ON_NOW_AND_NEXT_URL = "channels/list/%s/onnowandnext.json?f=asset.title&f=asset.descriptions";
    private static final String POPULAR = "popular";
    private static final String POST_ID_URL = "devices/device/id.json";
    private static final int RETRY_COUNT = 1;
    private static final String SEARCH_VIDEO_ASSETS_URL = "assets/assets.json?f=asset.title&f=asset.availableSince&f=asset.runtime";
    private static final String TEST_CHANNEL_LIST_ID = "90000";
    private static final String XUMO_FALSE = "false";
    private static final String XUMO_TRUE = "true";
    private static String authorizationValue = null;
    private static String defaultChannelListId = "10015";
    private static DeviceIdList mDeviceSettings = null;
    private static XumoWebService sInstance = null;
    private static String urlApp = "android-tv-app.xumo.com";
    private static String urlGetProviders = "config/staging-players.json";
    private static String urlGetViewBooster = "config/viewbooster-staging.json";
    private static String urlMds = null;
    private static final String urlPath2 = "v2";
    private static final String urlScheme = "https";
    private UserPreferences mUserPreferences;
    private boolean xumoMovieChannel = false;

    public interface DeviceAuthorizationListener {
        void onCompletion(String str, DeviceIdList deviceIdList);

        void onError();
    }

    public interface DeviceSettingsListener {
        void onCompletion(String str, DeviceIdList deviceIdList);

        void onError();
    }

    public interface GetLiveContentListener {
        void onCompletion(ArrayList<LiveAsset> arrayList, long j);

        void onError();
    }

    private class JsonKey {
        public static final String AD_INTERVAL = "adInterval";
        public static final String AD_INTERVAL_AUTO_PLAY = "adIntervalAutoPlay";
        public static final String AD_TAG = "adTag";
        public static final String AD_TARGET_DURATION = "adTargetDuration";
        public static final String ASSETS = "assets";
        public static final String ASSET_ID = "assetId";
        public static final String AVAILABLE_SINCE = "availableSince";
        public static final String BITRATE = "bitrate";
        public static final String CALL_SIGN = "callsign";
        public static final String CAPTIONS = "captions";
        public static final String CAPTIONS_TYPE = "type";
        public static final String CATEGORIES = "categories";
        public static final String CATEGORY_ID = "categoryId";
        public static final String CHANNEL = "channel";
        public static final String CHANNEL_ID = "channelId";
        public static final String CHANNEL_LIST_ID = "channelListId";
        public static final String CODE = "code";
        public static final String CONTENT_TYPE = "contentType";
        public static final String CUEPOINTS = "cuePoints";
        public static final String DESCRIPTION = "description";
        public static final String DESCRIPTIONS = "descriptions";
        public static final String END = "end";
        public static final String EPISODE_TITLE = "episodeTitle";
        public static final String FROM = "from";
        public static final String GENRE = "genre";
        public static final String GENRES = "genres";
        public static final String GENRE_ID = "genreId";
        public static final String GENRE_LIST = "genreList";
        public static final String GEO_ID = "geoId";
        public static final String GUID = "guid";
        public static final String HAS_SCHEDULE = "has_schedule";
        public static final String HAS_VOD = "has_vod";
        public static final String HEIGHT = "height";
        public static final String HITS = "hits";
        public static final String HYBRID_TYPE = "hybrid_type";
        public static final String ID = "id";
        public static final String IS_LIVE = "is_live";
        public static final String IS_NEW = "is_new";
        public static final String IS_XUMO_MOVIE_CHANNEL = "is_xumo_movie_channel";
        public static final String ITEM = "item";
        public static final String LANG = "lang";
        public static final String LARGE = "large";
        public static final String MAXRETRYCOUNT = "maxRetryCount";
        public static final String MEDIUM = "medium";
        public static final String NAME = "name";
        public static final String NUMBER = "number";
        public static final String PLAYER = "player";
        public static final String PRECACHEADS = "precacheAds";
        public static final String PREROLL = "preroll";
        public static final String PRODUCES = "produces";
        public static final String PROPERTIES = "properties";
        public static final String PROVIDERS = "providers";
        public static final String PROVIDER_ASSET_ID = "providerAssetId";
        public static final String RATINGS = "ratings";
        public static final String REFRESH = "refresh";
        public static final String RELEASE_YEAR = "releaseYear";
        public static final String RESULTS = "results";
        public static final String RETRYDOMAINS = "retryDomains";
        public static final String RUN_TIME = "runtime";
        public static final String SMALL = "small";
        public static final String SOURCES = "sources";
        public static final String START = "start";
        public static final String TIMESTAMPS = "timestamps";
        public static final String TINY = "tiny";
        public static final String TITLE = "title";
        public static final String TO = "to";
        public static final String TYPE = "type";
        public static final String UDK = "udk";
        public static final String URI = "uri";
        public static final String URL = "url";
        public static final String VALUE = "value";
        public static final String WIDTH = "width";

        private JsonKey() {
        }
    }

    public interface Listener {
        void onCompletion(Object obj);

        void onError();
    }

    public interface NoResponseListener {
        void onCompletion();

        void onError();
    }

    static {
        if (UserPreferences.getInstance().getUseTestChannels()) {
            authorizationValue = "XumoAndroidId id=MvHfvM8n46fqDZ4q";
            urlMds = "android-tv-qa-mds.xumo.com";
        } else {
            authorizationValue = "XumoAndroidId id=eQKn8TqYdK5Cb5ww";
            urlMds = "android-tv-mds.xumo.com";
        }
        if (UserPreferences.getInstance().getUseStagingPlayers()) {
        }
        if (UserPreferences.getInstance().getUseStagingViewBooster()) {
        }
    }

    private static String getMdsPath(String str) {
        str = new Builder().scheme(urlScheme).encodedAuthority(urlMds).appendEncodedPath(urlPath2).appendEncodedPath(str).toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("url = ");
        stringBuilder.append(str);
        LogUtil.d(stringBuilder.toString());
        return str;
    }

    private static String getAppPath(String str) {
        str = new Builder().scheme(urlScheme).encodedAuthority(urlApp).appendEncodedPath(str).toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("url = ");
        stringBuilder.append(str);
        LogUtil.d(stringBuilder.toString());
        return str;
    }

    private XumoWebService() {
        XumoContext.getInstance().getApplicationContext();
        this.mUserPreferences = UserPreferences.getInstance();
    }

    private void addXumoRequestQueue(JsonObjectRequest jsonObjectRequest) {
        if (jsonObjectRequest != null) {
            Context applicationContext = XumoContext.getInstance().getApplicationContext();
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
            ((XumoApplication) applicationContext.getApplicationContext()).getRequestQueue().add(jsonObjectRequest);
        }
    }

    public static synchronized XumoWebService getInstance() {
        XumoWebService xumoWebService;
        synchronized (XumoWebService.class) {
            if (sInstance == null) {
                sInstance = new XumoWebService();
            }
            xumoWebService = sInstance;
        }
        return xumoWebService;
    }

    public void getOnNowLives(Listener listener) {
        getOnNowLives(0, listener);
    }

    private void getOnNowLives(int i, final Listener listener) {
        getDeviceSettings(new DeviceSettingsListener() {
            public void onCompletion(String str, DeviceIdList deviceIdList) {
                XumoWebService.this.getOnNowLiveList(deviceIdList.getChannelListId(), new Listener() {
                    public void onCompletion(Object obj) {
                        listener.onCompletion(obj);
                    }

                    public void onError() {
                        listener.onError();
                    }
                });
            }

            public void onError() {
                listener.onError();
            }
        });
    }

    public void getGenres(Listener listener) {
        getGenres(0, listener);
    }

    public void getGenres(int i, final Listener listener) {
        getDeviceSettings(new DeviceSettingsListener() {
            public void onCompletion(String str, DeviceIdList deviceIdList) {
                XumoWebService.this.getGenres(deviceIdList.getChannelListId(), new Listener() {
                    public void onCompletion(Object obj) {
                        listener.onCompletion(obj);
                    }

                    public void onError() {
                        listener.onError();
                    }
                });
            }

            public void onError() {
                listener.onError();
            }
        });
    }

    public void getGenres(String str, final Listener listener) {
        addXumoRequestQueue(new JsonObjectRequest(0, String.format(getMdsPath(GET_GENRES_URL), new Object[]{str}), null, new com.android.volley.Response.Listener<JSONObject>() {
            public void onResponse(org.json.JSONObject r7) {
                /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r6 = this;
                r0 = new java.lang.StringBuilder;
                r0.<init>();
                r1 = "getGenres.response=";
                r0.append(r1);
                r0.append(r7);
                r0 = r0.toString();
                com.xumo.xumo.util.LogUtil.d(r0);
                r0 = new java.util.ArrayList;
                r0.<init>();
                r1 = "genres";	 Catch:{ JSONException -> 0x005d }
                r7 = r7.getJSONArray(r1);	 Catch:{ JSONException -> 0x005d }
                r1 = 0;	 Catch:{ JSONException -> 0x005d }
            L_0x0020:
                r2 = r7.length();	 Catch:{ JSONException -> 0x005d }
                if (r1 >= r2) goto L_0x0043;	 Catch:{ JSONException -> 0x005d }
            L_0x0026:
                r2 = r7.getJSONObject(r1);	 Catch:{ JSONException -> 0x005d }
                r3 = "genreId";	 Catch:{ JSONException -> 0x005d }
                r3 = r2.getInt(r3);	 Catch:{ JSONException -> 0x005d }
                r4 = "value";	 Catch:{ JSONException -> 0x005d }
                r2 = r2.getString(r4);	 Catch:{ JSONException -> 0x005d }
                r4 = "";	 Catch:{ JSONException -> 0x005d }
                r5 = new com.xumo.xumo.model.Genre;	 Catch:{ JSONException -> 0x005d }
                r5.<init>(r3, r2, r4);	 Catch:{ JSONException -> 0x005d }
                r0.add(r5);	 Catch:{ JSONException -> 0x005d }
                r1 = r1 + 1;
                goto L_0x0020;
            L_0x0043:
                r7 = r11;
                r7.onCompletion(r0);
                r7 = new java.lang.StringBuilder;
                r7.<init>();
                r1 = "getGenres genres=";
                r7.append(r1);
                r7.append(r0);
                r7 = r7.toString();
                com.xumo.xumo.util.LogUtil.d(r7);
                return;
            L_0x005d:
                r7 = r11;
                r7.onError();
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.xumo.xumo.service.XumoWebService.3.onResponse(org.json.JSONObject):void");
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("getGenres.onErrorResponse: ");
                stringBuilder.append(volleyError);
                LogUtil.d(stringBuilder.toString());
                listener.onError();
            }
        }));
    }

    public void getProviders(final Listener listener) {
        addXumoRequestQueue(new JsonObjectRequest(0, getAppPath(urlGetProviders), null, new com.android.volley.Response.Listener<JSONObject>() {
            public void onResponse(org.json.JSONObject r11) {
                /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r10 = this;
                r0 = new java.lang.StringBuilder;
                r0.<init>();
                r1 = "getProviders.response=";
                r0.append(r1);
                r0.append(r11);
                r0 = r0.toString();
                com.xumo.xumo.util.LogUtil.d(r0);
                r0 = new com.xumo.xumo.model.PlayerProvider;
                r0.<init>();
                r1 = "adTargetDuration";
                r1 = r11.has(r1);
                if (r1 == 0) goto L_0x0044;
            L_0x0021:
                r1 = "adTargetDuration";	 Catch:{ JSONException -> 0x002b }
                r1 = r11.getLong(r1);	 Catch:{ JSONException -> 0x002b }
                r0.setAdTargetDuration(r1);	 Catch:{ JSONException -> 0x002b }
                goto L_0x0044;
            L_0x002b:
                r1 = move-exception;
                r2 = new java.lang.StringBuilder;
                r2.<init>();
                r3 = "Failed to parse the ad target duration from player.json: ";
                r2.append(r3);
                r1 = r1.getMessage();
                r2.append(r1);
                r1 = r2.toString();
                com.xumo.xumo.util.LogUtil.w(r1);
            L_0x0044:
                r1 = "adInterval";
                r1 = r11.has(r1);
                if (r1 == 0) goto L_0x006f;
            L_0x004c:
                r1 = "adInterval";	 Catch:{ JSONException -> 0x0056 }
                r1 = r11.getLong(r1);	 Catch:{ JSONException -> 0x0056 }
                r0.setAdInterval(r1);	 Catch:{ JSONException -> 0x0056 }
                goto L_0x006f;
            L_0x0056:
                r1 = move-exception;
                r2 = new java.lang.StringBuilder;
                r2.<init>();
                r3 = "Failed to parse the ad interval from player.json: ";
                r2.append(r3);
                r1 = r1.getMessage();
                r2.append(r1);
                r1 = r2.toString();
                com.xumo.xumo.util.LogUtil.w(r1);
            L_0x006f:
                r1 = "adIntervalAutoPlay";
                r1 = r11.has(r1);
                if (r1 == 0) goto L_0x009a;
            L_0x0077:
                r1 = "adIntervalAutoPlay";	 Catch:{ JSONException -> 0x0081 }
                r1 = r11.getLong(r1);	 Catch:{ JSONException -> 0x0081 }
                r0.setAdIntervalAutoPlay(r1);	 Catch:{ JSONException -> 0x0081 }
                goto L_0x009a;
            L_0x0081:
                r1 = move-exception;
                r2 = new java.lang.StringBuilder;
                r2.<init>();
                r3 = "Failed to parse the ad interval auto play from player.json: ";
                r2.append(r3);
                r1 = r1.getMessage();
                r2.append(r1);
                r1 = r2.toString();
                com.xumo.xumo.util.LogUtil.w(r1);
            L_0x009a:
                r1 = "precacheAds";
                r1 = r11.has(r1);
                if (r1 == 0) goto L_0x00c5;
            L_0x00a2:
                r1 = "precacheAds";	 Catch:{ JSONException -> 0x00ac }
                r1 = r11.getBoolean(r1);	 Catch:{ JSONException -> 0x00ac }
                r0.setPreCacheAds(r1);	 Catch:{ JSONException -> 0x00ac }
                goto L_0x00c5;
            L_0x00ac:
                r1 = move-exception;
                r2 = new java.lang.StringBuilder;
                r2.<init>();
                r3 = "Failed to parse the precache ads from player.json: ";
                r2.append(r3);
                r1 = r1.getMessage();
                r2.append(r1);
                r1 = r2.toString();
                com.xumo.xumo.util.LogUtil.w(r1);
            L_0x00c5:
                r1 = "retryDomains";
                r1 = r11.has(r1);
                r2 = 0;
                if (r1 == 0) goto L_0x011d;
            L_0x00ce:
                r1 = "retryDomains";	 Catch:{ JSONException -> 0x0104 }
                r1 = r11.getJSONArray(r1);	 Catch:{ JSONException -> 0x0104 }
                r3 = r1.length();	 Catch:{ JSONException -> 0x0104 }
                r4 = new java.lang.String[r3];	 Catch:{ JSONException -> 0x0104 }
                r5 = 0;
            L_0x00db:
                if (r5 >= r3) goto L_0x0100;
            L_0x00dd:
                r6 = r1.getString(r5);	 Catch:{ JSONException -> 0x00e4 }
                r4[r5] = r6;	 Catch:{ JSONException -> 0x00e4 }
                goto L_0x00fd;
            L_0x00e4:
                r6 = move-exception;
                r7 = new java.lang.StringBuilder;	 Catch:{ JSONException -> 0x0104 }
                r7.<init>();	 Catch:{ JSONException -> 0x0104 }
                r8 = "Failed to parse a retry domain from player.json: ";	 Catch:{ JSONException -> 0x0104 }
                r7.append(r8);	 Catch:{ JSONException -> 0x0104 }
                r6 = r6.getMessage();	 Catch:{ JSONException -> 0x0104 }
                r7.append(r6);	 Catch:{ JSONException -> 0x0104 }
                r6 = r7.toString();	 Catch:{ JSONException -> 0x0104 }
                com.xumo.xumo.util.LogUtil.w(r6);	 Catch:{ JSONException -> 0x0104 }
            L_0x00fd:
                r5 = r5 + 1;	 Catch:{ JSONException -> 0x0104 }
                goto L_0x00db;	 Catch:{ JSONException -> 0x0104 }
            L_0x0100:
                r0.setRetryDomains(r4);	 Catch:{ JSONException -> 0x0104 }
                goto L_0x011d;
            L_0x0104:
                r1 = move-exception;
                r3 = new java.lang.StringBuilder;
                r3.<init>();
                r4 = "Failed to parse the retry domains from player.json: ";
                r3.append(r4);
                r1 = r1.getMessage();
                r3.append(r1);
                r1 = r3.toString();
                com.xumo.xumo.util.LogUtil.w(r1);
            L_0x011d:
                r1 = "maxRetryCount";
                r1 = r11.has(r1);
                if (r1 == 0) goto L_0x0148;
            L_0x0125:
                r1 = "maxRetryCount";	 Catch:{ JSONException -> 0x012f }
                r1 = r11.getInt(r1);	 Catch:{ JSONException -> 0x012f }
                r0.setMaxRetryCount(r1);	 Catch:{ JSONException -> 0x012f }
                goto L_0x0148;
            L_0x012f:
                r1 = move-exception;
                r3 = new java.lang.StringBuilder;
                r3.<init>();
                r4 = "Failed to parse the max retry count from player.json: ";
                r3.append(r4);
                r1 = r1.getMessage();
                r3.append(r1);
                r1 = r3.toString();
                com.xumo.xumo.util.LogUtil.w(r1);
            L_0x0148:
                r1 = new java.util.ArrayList;
                r1.<init>();
                r3 = "providers";	 Catch:{ JSONException -> 0x01b3 }
                r11 = r11.getJSONArray(r3);	 Catch:{ JSONException -> 0x01b3 }
                r3 = 0;	 Catch:{ JSONException -> 0x01b3 }
            L_0x0154:
                r4 = r11.length();	 Catch:{ JSONException -> 0x01b3 }
                if (r3 >= r4) goto L_0x0196;	 Catch:{ JSONException -> 0x01b3 }
            L_0x015a:
                r4 = r11.getJSONObject(r3);	 Catch:{ JSONException -> 0x01b3 }
                r5 = "id";	 Catch:{ JSONException -> 0x01b3 }
                r5 = r4.optInt(r5, r2);	 Catch:{ JSONException -> 0x01b3 }
                r6 = "name";	 Catch:{ JSONException -> 0x01b3 }
                r7 = "";	 Catch:{ JSONException -> 0x01b3 }
                r6 = r4.optString(r6, r7);	 Catch:{ JSONException -> 0x01b3 }
                r7 = "adTag";	 Catch:{ JSONException -> 0x01b3 }
                r8 = "";	 Catch:{ JSONException -> 0x01b3 }
                r7 = r4.optString(r7, r8);	 Catch:{ JSONException -> 0x01b3 }
                r8 = "player";	 Catch:{ JSONException -> 0x01b3 }
                r9 = "";	 Catch:{ JSONException -> 0x01b3 }
                r8 = r4.optString(r8, r9);	 Catch:{ JSONException -> 0x01b3 }
                r9 = "preroll";	 Catch:{ JSONException -> 0x01b3 }
                r4 = r4.optString(r9);	 Catch:{ JSONException -> 0x01b3 }
                r9 = "true";	 Catch:{ JSONException -> 0x01b3 }
                r4 = r4.equals(r9);	 Catch:{ JSONException -> 0x01b3 }
                r9 = new com.xumo.xumo.model.Provider;	 Catch:{ JSONException -> 0x01b3 }
                r9.<init>(r5, r6, r7, r8);	 Catch:{ JSONException -> 0x01b3 }
                r9.setPreroll(r4);	 Catch:{ JSONException -> 0x01b3 }
                r1.add(r9);	 Catch:{ JSONException -> 0x01b3 }
                r3 = r3 + 1;	 Catch:{ JSONException -> 0x01b3 }
                goto L_0x0154;	 Catch:{ JSONException -> 0x01b3 }
            L_0x0196:
                r0.setProviders(r1);	 Catch:{ JSONException -> 0x01b3 }
                r11 = r8;
                r11.onCompletion(r0);
                r11 = new java.lang.StringBuilder;
                r11.<init>();
                r0 = "getProviders providers=";
                r11.append(r0);
                r11.append(r1);
                r11 = r11.toString();
                com.xumo.xumo.util.LogUtil.d(r11);
                return;
            L_0x01b3:
                r11 = r8;
                r11.onError();
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.xumo.xumo.service.XumoWebService.5.onResponse(org.json.JSONObject):void");
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("getProviders.onErrorResponse: ");
                stringBuilder.append(volleyError);
                LogUtil.d(stringBuilder.toString());
                listener.onError();
            }
        }));
    }

    public void getAllChannels(Listener listener) {
        getAllChannels(0, listener);
    }

    private void getAllChannels(int i, final Listener listener) {
        getDeviceSettings(new DeviceSettingsListener() {
            public void onCompletion(String str, DeviceIdList deviceIdList) {
                XumoWebService.this.getChannelsList(deviceIdList.getChannelListId(), deviceIdList.getGeoId(), new Listener() {
                    public void onCompletion(Object obj) {
                        listener.onCompletion(obj);
                    }

                    public void onError() {
                        listener.onError();
                    }
                });
            }

            public void onError() {
                listener.onError();
            }
        });
    }

    public void getChannelCategories(final String str, final Listener listener) {
        addXumoRequestQueue(new JsonObjectRequest(0, String.format(getMdsPath(GET_CHANNEL_CATEGORIES_URL), new Object[]{str}), null, new com.android.volley.Response.Listener<JSONObject>() {
            public void onResponse(org.json.JSONObject r7) {
                /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r6 = this;
                r0 = new java.lang.StringBuilder;
                r0.<init>();
                r1 = "getChannelCategories.onResponse response = ";
                r0.append(r1);
                r0.append(r7);
                r0 = r0.toString();
                com.xumo.xumo.util.LogUtil.d(r0);
                r0 = new java.util.ArrayList;
                r0.<init>();
                r1 = "categories";	 Catch:{ JSONException -> 0x0078 }
                r7 = r7.getJSONArray(r1);	 Catch:{ JSONException -> 0x0078 }
                r1 = 0;	 Catch:{ JSONException -> 0x0078 }
            L_0x0020:
                r2 = r7.length();	 Catch:{ JSONException -> 0x0078 }
                if (r1 >= r2) goto L_0x005e;	 Catch:{ JSONException -> 0x0078 }
            L_0x0026:
                r2 = r7.getJSONObject(r1);	 Catch:{ JSONException -> 0x0078 }
                r3 = new com.xumo.xumo.model.Category;	 Catch:{ JSONException -> 0x0078 }
                r4 = r10;	 Catch:{ JSONException -> 0x0078 }
                r5 = "categoryId";	 Catch:{ JSONException -> 0x0078 }
                r5 = r2.getString(r5);	 Catch:{ JSONException -> 0x0078 }
                r3.<init>(r4, r5);	 Catch:{ JSONException -> 0x0078 }
                r4 = "title";	 Catch:{ JSONException -> 0x0078 }
                r4 = r2.getString(r4);	 Catch:{ JSONException -> 0x0078 }
                r3.setTitle(r4);	 Catch:{ JSONException -> 0x0078 }
                r4 = "description";	 Catch:{ JSONException -> 0x0078 }
                r4 = r2.getString(r4);	 Catch:{ JSONException -> 0x0078 }
                r3.setDescriptionText(r4);	 Catch:{ JSONException -> 0x0078 }
                r4 = "hits";	 Catch:{ JSONException -> 0x0078 }
                r2 = r2.getInt(r4);	 Catch:{ JSONException -> 0x0078 }
                r3.setHits(r2);	 Catch:{ JSONException -> 0x0078 }
                r2 = r3.getHits();	 Catch:{ JSONException -> 0x0078 }
                if (r2 == 0) goto L_0x005b;	 Catch:{ JSONException -> 0x0078 }
            L_0x0058:
                r0.add(r3);	 Catch:{ JSONException -> 0x0078 }
            L_0x005b:
                r1 = r1 + 1;
                goto L_0x0020;
            L_0x005e:
                r7 = new java.lang.StringBuilder;
                r7.<init>();
                r1 = "getChannelCategories categories=";
                r7.append(r1);
                r7.append(r0);
                r7 = r7.toString();
                com.xumo.xumo.util.LogUtil.d(r7);
                r7 = r11;
                r7.onCompletion(r0);
                return;
            L_0x0078:
                r7 = r11;
                r7.onError();
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.xumo.xumo.service.XumoWebService.8.onResponse(org.json.JSONObject):void");
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("getChannelCategories.onErrorResponse: ");
                stringBuilder.append(volleyError);
                LogUtil.d(stringBuilder.toString());
                listener.onError();
            }
        }));
    }

    public void getAssetsInCategories(Category category, Listener listener) {
        getAssetsInCategories(category, 0, listener);
    }

    public void getAssetsInCategories(final Category category, int i, final Listener listener) {
        if (category == null) {
            listener.onError();
            return;
        }
        addXumoRequestQueue(new JsonObjectRequest(0, String.format(getMdsPath(GET_CHANNEL_ASSETS_URL), new Object[]{category.getCategoryId(), Integer.valueOf(i)}), null, new com.android.volley.Response.Listener<JSONObject>() {
            public void onResponse(org.json.JSONObject r10) {
                /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r9 = this;
                r0 = new java.lang.StringBuilder;
                r0.<init>();
                r1 = "getAssetsInCategories.onResponse response = ";
                r0.append(r1);
                r0.append(r10);
                r0 = r0.toString();
                com.xumo.xumo.util.LogUtil.d(r0);
                r0 = new java.util.ArrayList;
                r0.<init>();
                r1 = "results";	 Catch:{ JSONException -> 0x00dd }
                r1 = r10.getJSONArray(r1);	 Catch:{ JSONException -> 0x00dd }
                r2 = 0;	 Catch:{ JSONException -> 0x00dd }
                r3 = 0;	 Catch:{ JSONException -> 0x00dd }
            L_0x0021:
                r4 = r1.length();	 Catch:{ JSONException -> 0x00dd }
                if (r3 >= r4) goto L_0x00c3;	 Catch:{ JSONException -> 0x00dd }
            L_0x0027:
                r4 = r1.getJSONObject(r3);	 Catch:{ JSONException -> 0x00dd }
                r5 = "id";	 Catch:{ JSONException -> 0x00dd }
                r5 = r4.getString(r5);	 Catch:{ JSONException -> 0x00dd }
                r6 = android.text.TextUtils.isEmpty(r5);	 Catch:{ JSONException -> 0x00dd }
                if (r6 == 0) goto L_0x0039;	 Catch:{ JSONException -> 0x00dd }
            L_0x0037:
                goto L_0x00bf;	 Catch:{ JSONException -> 0x00dd }
            L_0x0039:
                r6 = new com.xumo.xumo.model.VideoAsset;	 Catch:{ JSONException -> 0x00dd }
                r7 = r10;	 Catch:{ JSONException -> 0x00dd }
                r7 = r7.getCategoryId();	 Catch:{ JSONException -> 0x00dd }
                r8 = r10;	 Catch:{ JSONException -> 0x00dd }
                r8 = r8.getChannelId();	 Catch:{ JSONException -> 0x00dd }
                r6.<init>(r5, r7, r8);	 Catch:{ JSONException -> 0x00dd }
                if (r3 != 0) goto L_0x0055;	 Catch:{ JSONException -> 0x00dd }
            L_0x004c:
                r5 = "hits";	 Catch:{ JSONException -> 0x00dd }
                r5 = r10.getInt(r5);	 Catch:{ JSONException -> 0x00dd }
                r6.setmHits(r5);	 Catch:{ JSONException -> 0x00dd }
            L_0x0055:
                r5 = "episodeTitle";	 Catch:{ JSONException -> 0x00dd }
                r5 = r4.optString(r5);	 Catch:{ JSONException -> 0x00dd }
                r7 = android.text.TextUtils.isEmpty(r5);	 Catch:{ JSONException -> 0x00dd }
                if (r7 != 0) goto L_0x0065;	 Catch:{ JSONException -> 0x00dd }
            L_0x0061:
                r6.setTitle(r5);	 Catch:{ JSONException -> 0x00dd }
                goto L_0x006e;	 Catch:{ JSONException -> 0x00dd }
            L_0x0065:
                r5 = "title";	 Catch:{ JSONException -> 0x00dd }
                r5 = r4.optString(r5);	 Catch:{ JSONException -> 0x00dd }
                r6.setTitle(r5);	 Catch:{ JSONException -> 0x00dd }
            L_0x006e:
                r5 = "ratings";	 Catch:{ JSONException -> 0x00dd }
                r5 = r4.optJSONArray(r5);	 Catch:{ JSONException -> 0x00dd }
                if (r5 == 0) goto L_0x0089;	 Catch:{ JSONException -> 0x00dd }
            L_0x0076:
                r7 = r5.length();	 Catch:{ JSONException -> 0x00dd }
                if (r7 == 0) goto L_0x0089;	 Catch:{ JSONException -> 0x00dd }
            L_0x007c:
                r5 = r5.optJSONObject(r2);	 Catch:{ JSONException -> 0x00dd }
                r7 = "code";	 Catch:{ JSONException -> 0x00dd }
                r5 = r5.optString(r7);	 Catch:{ JSONException -> 0x00dd }
                r6.setmRatings(r5);	 Catch:{ JSONException -> 0x00dd }
            L_0x0089:
                r5 = "releaseYear";	 Catch:{ JSONException -> 0x00dd }
                r5 = r4.optInt(r5);	 Catch:{ JSONException -> 0x00dd }
                r6.setmReleaseYear(r5);	 Catch:{ JSONException -> 0x00dd }
                r5 = "runtime";	 Catch:{ JSONException -> 0x00dd }
                r7 = r4.optLong(r5);	 Catch:{ JSONException -> 0x00dd }
                r6.setRunTime(r7);	 Catch:{ JSONException -> 0x00dd }
                r5 = "availableSince";	 Catch:{ JSONException -> 0x00dd }
                r4 = r4.optString(r5);	 Catch:{ JSONException -> 0x00dd }
                r5 = android.text.TextUtils.isEmpty(r4);	 Catch:{ JSONException -> 0x00dd }
                if (r5 != 0) goto L_0x00bc;	 Catch:{ JSONException -> 0x00dd }
            L_0x00a7:
                r5 = new java.text.SimpleDateFormat;	 Catch:{ JSONException -> 0x00dd }
                r7 = "EEE, dd MMM yyyy HH:mm:ss Z";	 Catch:{ JSONException -> 0x00dd }
                r8 = java.util.Locale.US;	 Catch:{ JSONException -> 0x00dd }
                r5.<init>(r7, r8);	 Catch:{ JSONException -> 0x00dd }
                r4 = r5.parse(r4);	 Catch:{ ParseException -> 0x00b8 }
                r6.setAssetAge(r4);	 Catch:{ ParseException -> 0x00b8 }
                goto L_0x00bc;
            L_0x00b8:
                r4 = move-exception;
                r4.printStackTrace();	 Catch:{ JSONException -> 0x00dd }
            L_0x00bc:
                r0.add(r6);	 Catch:{ JSONException -> 0x00dd }
            L_0x00bf:
                r3 = r3 + 1;
                goto L_0x0021;
            L_0x00c3:
                r10 = new java.lang.StringBuilder;
                r10.<init>();
                r1 = "getAssetsInCategories videoAssets=";
                r10.append(r1);
                r10.append(r0);
                r10 = r10.toString();
                com.xumo.xumo.util.LogUtil.d(r10);
                r10 = r12;
                r10.onCompletion(r0);
                return;
            L_0x00dd:
                r10 = r12;
                r10.onError();
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.xumo.xumo.service.XumoWebService.10.onResponse(org.json.JSONObject):void");
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("getAssetsInCategories.onErrorResponse: ");
                stringBuilder.append(volleyError);
                LogUtil.d(stringBuilder.toString());
                listener.onError();
            }
        }));
    }

    public void getLiveVideosForChannelId(String str, Date date, GetLiveContentListener getLiveContentListener) {
        final String str2 = str;
        final Date date2 = date;
        final GetLiveContentListener getLiveContentListener2 = getLiveContentListener;
        Calendar instance = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        instance.setTime(date2);
        int i = instance.get(11);
        int i2 = instance.get(1);
        int i3 = instance.get(2) + 1;
        int i4 = instance.get(5);
        addXumoRequestQueue(new JsonObjectRequest(0, String.format(Locale.US, getMdsPath(GET_CHANNEL_LIVE_CONTENT_URL), new Object[]{str2, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4)}), null, new com.android.volley.Response.Listener<JSONObject>() {
            public void onResponse(org.json.JSONObject r14) {
                /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r13 = this;
                r0 = new java.lang.StringBuilder;
                r0.<init>();
                r1 = "getLiveVideosForChannelId.onResponse response = ";
                r0.append(r1);
                r0.append(r14);
                r0 = r0.toString();
                com.xumo.xumo.util.LogUtil.d(r0);
                r0 = new java.util.ArrayList;
                r0.<init>();
                r1 = "assets";	 Catch:{ JSONException -> 0x00ca }
                r1 = r14.getJSONArray(r1);	 Catch:{ JSONException -> 0x00ca }
                r2 = 0;	 Catch:{ JSONException -> 0x00ca }
            L_0x0020:
                r3 = r1.length();	 Catch:{ JSONException -> 0x00ca }
                if (r2 >= r3) goto L_0x0077;	 Catch:{ JSONException -> 0x00ca }
            L_0x0026:
                r3 = r1.getJSONObject(r2);	 Catch:{ JSONException -> 0x00ca }
                r4 = "id";	 Catch:{ JSONException -> 0x00ca }
                r4 = r3.getString(r4);	 Catch:{ JSONException -> 0x00ca }
                r5 = android.text.TextUtils.isEmpty(r4);	 Catch:{ JSONException -> 0x00ca }
                if (r5 == 0) goto L_0x0037;	 Catch:{ JSONException -> 0x00ca }
            L_0x0036:
                goto L_0x0074;	 Catch:{ JSONException -> 0x00ca }
            L_0x0037:
                r5 = "timestamps";	 Catch:{ JSONException -> 0x00ca }
                r3 = r3.getJSONObject(r5);	 Catch:{ JSONException -> 0x00ca }
                r5 = "start";	 Catch:{ JSONException -> 0x00ca }
                r5 = r3.getLong(r5);	 Catch:{ JSONException -> 0x00ca }
                r7 = "end";	 Catch:{ JSONException -> 0x00ca }
                r7 = r3.getLong(r7);	 Catch:{ JSONException -> 0x00ca }
                r3 = r2;	 Catch:{ JSONException -> 0x00ca }
                r9 = com.xumo.xumo.util.XumoUtil.getTimeMillisUTC(r3);	 Catch:{ JSONException -> 0x00ca }
                r11 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;	 Catch:{ JSONException -> 0x00ca }
                r9 = r9 / r11;	 Catch:{ JSONException -> 0x00ca }
                r3 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1));	 Catch:{ JSONException -> 0x00ca }
                if (r3 <= 0) goto L_0x0074;	 Catch:{ JSONException -> 0x00ca }
            L_0x0056:
                r3 = new com.xumo.xumo.model.LiveAsset;	 Catch:{ JSONException -> 0x00ca }
                r9 = r1;	 Catch:{ JSONException -> 0x00ca }
                r3.<init>(r4, r9);	 Catch:{ JSONException -> 0x00ca }
                r3.setStart(r5);	 Catch:{ JSONException -> 0x00ca }
                r3.setEnd(r7);	 Catch:{ JSONException -> 0x00ca }
                r3.setIndex(r2);	 Catch:{ JSONException -> 0x00ca }
                r4 = r1.length();	 Catch:{ JSONException -> 0x00ca }
                r3.setLength(r4);	 Catch:{ JSONException -> 0x00ca }
                r4 = 2;	 Catch:{ JSONException -> 0x00ca }
                r3.setAssetType(r4);	 Catch:{ JSONException -> 0x00ca }
                r0.add(r3);	 Catch:{ JSONException -> 0x00ca }
            L_0x0074:
                r2 = r2 + 1;	 Catch:{ JSONException -> 0x00ca }
                goto L_0x0020;	 Catch:{ JSONException -> 0x00ca }
            L_0x0077:
                r1 = "refresh";	 Catch:{ JSONException -> 0x00ca }
                r14 = r14.getJSONObject(r1);	 Catch:{ JSONException -> 0x00ca }
                r1 = "from";	 Catch:{ JSONException -> 0x00ca }
                r1 = r14.getLong(r1);	 Catch:{ JSONException -> 0x00ca }
                r3 = "to";	 Catch:{ JSONException -> 0x00ca }
                r3 = r14.getLong(r3);	 Catch:{ JSONException -> 0x00ca }
                r14 = 0;	 Catch:{ JSONException -> 0x00ca }
                r3 = r3 - r1;	 Catch:{ JSONException -> 0x00ca }
                r5 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;	 Catch:{ JSONException -> 0x00ca }
                r14 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1));	 Catch:{ JSONException -> 0x00ca }
                if (r14 <= 0) goto L_0x0093;	 Catch:{ JSONException -> 0x00ca }
            L_0x0092:
                r3 = r5;	 Catch:{ JSONException -> 0x00ca }
            L_0x0093:
                r14 = new java.util.Random;	 Catch:{ JSONException -> 0x00ca }
                r14.<init>();	 Catch:{ JSONException -> 0x00ca }
                r3 = (int) r3;	 Catch:{ JSONException -> 0x00ca }
                r14 = r14.nextInt(r3);	 Catch:{ JSONException -> 0x00ca }
                r3 = (long) r14;
                r3 = r3 + r1;
                r14 = new java.lang.StringBuilder;
                r14.<init>();
                r1 = "getLiveVideosForChannelId liveAssets=";
                r14.append(r1);
                r14.append(r0);
                r14 = r14.toString();
                r1 = new java.lang.StringBuilder;
                r1.<init>();
                r2 = " refreshTime=";
                r1.append(r2);
                r1.append(r3);
                r1 = r1.toString();
                com.xumo.xumo.util.LogUtil.d(r14, r1);
                r14 = r3;
                r14.onCompletion(r0, r3);
                return;
            L_0x00ca:
                r14 = r3;
                r14.onError();
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.xumo.xumo.service.XumoWebService.12.onResponse(org.json.JSONObject):void");
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("getLiveVideosForChannelId.onErrorResponse: ");
                stringBuilder.append(volleyError);
                LogUtil.d(stringBuilder.toString());
                getLiveContentListener2.onError();
            }
        }));
    }

    public void getVideoMetadata(final VideoAsset videoAsset, final NoResponseListener noResponseListener) {
        if (videoAsset == null) {
            noResponseListener.onError();
            return;
        }
        addXumoRequestQueue(new JsonObjectRequest(0, String.format(getMdsPath(GET_CHANNEL_ASSETS_METADATA_URL), new Object[]{videoAsset.getAssetId()}), null, new com.android.volley.Response.Listener<JSONObject>() {
            public void onResponse(org.json.JSONObject r20) {
                /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r19 = this;
                r1 = r19;
                r2 = r20;
                r0 = new java.lang.StringBuilder;
                r0.<init>();
                r3 = "getVideoMetadata.onResponse response = ";
                r0.append(r3);
                r0.append(r2);
                r0 = r0.toString();
                com.xumo.xumo.util.LogUtil.d(r0);
                r0 = r11;	 Catch:{ JSONException -> 0x02cd }
                r3 = "title";	 Catch:{ JSONException -> 0x02cd }
                r3 = r2.optString(r3);	 Catch:{ JSONException -> 0x02cd }
                r0.setTitle(r3);	 Catch:{ JSONException -> 0x02cd }
                r0 = r11;	 Catch:{ JSONException -> 0x02cd }
                r3 = "runtime";	 Catch:{ JSONException -> 0x02cd }
                r3 = r2.optLong(r3);	 Catch:{ JSONException -> 0x02cd }
                r0.setRunTime(r3);	 Catch:{ JSONException -> 0x02cd }
                r0 = "descriptions";	 Catch:{ JSONException -> 0x02cd }
                r0 = r2.optJSONObject(r0);	 Catch:{ JSONException -> 0x02cd }
                if (r0 == 0) goto L_0x0095;	 Catch:{ JSONException -> 0x02cd }
            L_0x0036:
                r3 = "tiny";	 Catch:{ JSONException -> 0x02cd }
                r3 = r0.optString(r3);	 Catch:{ JSONException -> 0x02cd }
                r3 = android.text.TextUtils.isEmpty(r3);	 Catch:{ JSONException -> 0x02cd }
                if (r3 != 0) goto L_0x004e;	 Catch:{ JSONException -> 0x02cd }
            L_0x0042:
                r3 = r11;	 Catch:{ JSONException -> 0x02cd }
                r4 = "tiny";	 Catch:{ JSONException -> 0x02cd }
                r0 = r0.getString(r4);	 Catch:{ JSONException -> 0x02cd }
                r3.setDescriptionText(r0);	 Catch:{ JSONException -> 0x02cd }
                goto L_0x0095;	 Catch:{ JSONException -> 0x02cd }
            L_0x004e:
                r3 = "small";	 Catch:{ JSONException -> 0x02cd }
                r3 = r0.optString(r3);	 Catch:{ JSONException -> 0x02cd }
                r3 = android.text.TextUtils.isEmpty(r3);	 Catch:{ JSONException -> 0x02cd }
                if (r3 != 0) goto L_0x0066;	 Catch:{ JSONException -> 0x02cd }
            L_0x005a:
                r3 = r11;	 Catch:{ JSONException -> 0x02cd }
                r4 = "small";	 Catch:{ JSONException -> 0x02cd }
                r0 = r0.getString(r4);	 Catch:{ JSONException -> 0x02cd }
                r3.setDescriptionText(r0);	 Catch:{ JSONException -> 0x02cd }
                goto L_0x0095;	 Catch:{ JSONException -> 0x02cd }
            L_0x0066:
                r3 = "medium";	 Catch:{ JSONException -> 0x02cd }
                r3 = r0.optString(r3);	 Catch:{ JSONException -> 0x02cd }
                r3 = android.text.TextUtils.isEmpty(r3);	 Catch:{ JSONException -> 0x02cd }
                if (r3 != 0) goto L_0x007e;	 Catch:{ JSONException -> 0x02cd }
            L_0x0072:
                r3 = r11;	 Catch:{ JSONException -> 0x02cd }
                r4 = "medium";	 Catch:{ JSONException -> 0x02cd }
                r0 = r0.getString(r4);	 Catch:{ JSONException -> 0x02cd }
                r3.setDescriptionText(r0);	 Catch:{ JSONException -> 0x02cd }
                goto L_0x0095;	 Catch:{ JSONException -> 0x02cd }
            L_0x007e:
                r3 = "large";	 Catch:{ JSONException -> 0x02cd }
                r3 = r0.optString(r3);	 Catch:{ JSONException -> 0x02cd }
                r3 = android.text.TextUtils.isEmpty(r3);	 Catch:{ JSONException -> 0x02cd }
                if (r3 != 0) goto L_0x0095;	 Catch:{ JSONException -> 0x02cd }
            L_0x008a:
                r3 = r11;	 Catch:{ JSONException -> 0x02cd }
                r4 = "large";	 Catch:{ JSONException -> 0x02cd }
                r0 = r0.getString(r4);	 Catch:{ JSONException -> 0x02cd }
                r3.setDescriptionText(r0);	 Catch:{ JSONException -> 0x02cd }
            L_0x0095:
                r0 = "availableSince";	 Catch:{ JSONException -> 0x02cd }
                r0 = r2.optString(r0);	 Catch:{ JSONException -> 0x02cd }
                r3 = android.text.TextUtils.isEmpty(r0);	 Catch:{ JSONException -> 0x02cd }
                if (r3 != 0) goto L_0x00b8;	 Catch:{ JSONException -> 0x02cd }
            L_0x00a1:
                r3 = new java.text.SimpleDateFormat;	 Catch:{ JSONException -> 0x02cd }
                r4 = "EEE, dd MMM yyyy HH:mm:ss Z";	 Catch:{ JSONException -> 0x02cd }
                r5 = java.util.Locale.US;	 Catch:{ JSONException -> 0x02cd }
                r3.<init>(r4, r5);	 Catch:{ JSONException -> 0x02cd }
                r4 = r11;	 Catch:{ ParseException -> 0x00b4 }
                r0 = r3.parse(r0);	 Catch:{ ParseException -> 0x00b4 }
                r4.setAssetAge(r0);	 Catch:{ ParseException -> 0x00b4 }
                goto L_0x00b8;
            L_0x00b4:
                r0 = move-exception;
                r0.printStackTrace();	 Catch:{ JSONException -> 0x02cd }
            L_0x00b8:
                r0 = "providers";	 Catch:{ JSONException -> 0x02cd }
                r0 = r2.optJSONArray(r0);	 Catch:{ JSONException -> 0x02cd }
                r3 = 0;	 Catch:{ JSONException -> 0x02cd }
                if (r0 == 0) goto L_0x026c;	 Catch:{ JSONException -> 0x02cd }
            L_0x00c1:
                r4 = r0.length();	 Catch:{ JSONException -> 0x02cd }
                if (r4 == 0) goto L_0x026c;	 Catch:{ JSONException -> 0x02cd }
            L_0x00c7:
                r0 = r0.optJSONObject(r3);	 Catch:{ JSONException -> 0x02cd }
                if (r0 == 0) goto L_0x026c;	 Catch:{ JSONException -> 0x02cd }
            L_0x00cd:
                r4 = r11;	 Catch:{ JSONException -> 0x02cd }
                r5 = "id";	 Catch:{ JSONException -> 0x02cd }
                r5 = r0.optInt(r5);	 Catch:{ JSONException -> 0x02cd }
                r4.setProviderId(r5);	 Catch:{ JSONException -> 0x02cd }
                r4 = r11;	 Catch:{ JSONException -> 0x02cd }
                r5 = "title";	 Catch:{ JSONException -> 0x02cd }
                r5 = r0.optString(r5);	 Catch:{ JSONException -> 0x02cd }
                r4.setmProviderTitle(r5);	 Catch:{ JSONException -> 0x02cd }
                r4 = r11;	 Catch:{ JSONException -> 0x02cd }
                r5 = "providerAssetId";	 Catch:{ JSONException -> 0x02cd }
                r5 = r0.optString(r5);	 Catch:{ JSONException -> 0x02cd }
                r4.setProviderAssetId(r5);	 Catch:{ JSONException -> 0x02cd }
                r4 = "";	 Catch:{ JSONException -> 0x02cd }
                r5 = "";	 Catch:{ JSONException -> 0x02cd }
                r6 = "";	 Catch:{ JSONException -> 0x02cd }
                r7 = "";	 Catch:{ JSONException -> 0x02cd }
                r8 = "";	 Catch:{ JSONException -> 0x02cd }
                r9 = "";	 Catch:{ JSONException -> 0x02cd }
                r10 = "";	 Catch:{ JSONException -> 0x02cd }
                r11 = "sources";	 Catch:{ JSONException -> 0x02cd }
                r11 = r0.optJSONArray(r11);	 Catch:{ JSONException -> 0x02cd }
                if (r11 == 0) goto L_0x0193;	 Catch:{ JSONException -> 0x02cd }
            L_0x0104:
                r14 = r5;	 Catch:{ JSONException -> 0x02cd }
                r13 = r8;	 Catch:{ JSONException -> 0x02cd }
                r15 = r9;	 Catch:{ JSONException -> 0x02cd }
                r8 = 0;	 Catch:{ JSONException -> 0x02cd }
                r9 = 0;	 Catch:{ JSONException -> 0x02cd }
                r12 = 0;	 Catch:{ JSONException -> 0x02cd }
                r5 = r4;	 Catch:{ JSONException -> 0x02cd }
                r4 = 0;	 Catch:{ JSONException -> 0x02cd }
            L_0x010c:
                r3 = r11.length();	 Catch:{ JSONException -> 0x02cd }
                if (r4 >= r3) goto L_0x018f;	 Catch:{ JSONException -> 0x02cd }
            L_0x0112:
                r3 = r11.getJSONObject(r4);	 Catch:{ JSONException -> 0x02cd }
                r16 = r7;	 Catch:{ JSONException -> 0x02cd }
                r7 = "produces";	 Catch:{ JSONException -> 0x02cd }
                r7 = r3.optString(r7);	 Catch:{ JSONException -> 0x02cd }
                r17 = android.text.TextUtils.isEmpty(r7);	 Catch:{ JSONException -> 0x02cd }
                if (r17 == 0) goto L_0x0127;	 Catch:{ JSONException -> 0x02cd }
            L_0x0124:
                r7 = r16;	 Catch:{ JSONException -> 0x02cd }
                goto L_0x018b;	 Catch:{ JSONException -> 0x02cd }
            L_0x0127:
                r9 = "type=tv";	 Catch:{ JSONException -> 0x02cd }
                r9 = r7.contains(r9);	 Catch:{ JSONException -> 0x02cd }
                if (r9 == 0) goto L_0x0137;	 Catch:{ JSONException -> 0x02cd }
            L_0x012f:
                r9 = "uri";	 Catch:{ JSONException -> 0x02cd }
                r9 = r3.getString(r9);	 Catch:{ JSONException -> 0x02cd }
                r15 = r7;	 Catch:{ JSONException -> 0x02cd }
                r14 = r9;	 Catch:{ JSONException -> 0x02cd }
            L_0x0137:
                r9 = ";";	 Catch:{ JSONException -> 0x02cd }
                r9 = r7.split(r9);	 Catch:{ JSONException -> 0x02cd }
                r12 = 0;	 Catch:{ JSONException -> 0x02cd }
                r9 = r9[r12];	 Catch:{ JSONException -> 0x02cd }
                r12 = "bitrate";	 Catch:{ JSONException -> 0x02cd }
                r12 = r3.optInt(r12);	 Catch:{ JSONException -> 0x02cd }
                r13 = "width";	 Catch:{ JSONException -> 0x02cd }
                r13 = r3.optInt(r13);	 Catch:{ JSONException -> 0x02cd }
                r18 = r7;	 Catch:{ JSONException -> 0x02cd }
                r7 = "height";	 Catch:{ JSONException -> 0x02cd }
                r7 = r3.optInt(r7);	 Catch:{ JSONException -> 0x02cd }
                if (r8 <= 0) goto L_0x0158;	 Catch:{ JSONException -> 0x02cd }
            L_0x0156:
                if (r8 > r12) goto L_0x015a;	 Catch:{ JSONException -> 0x02cd }
            L_0x0158:
                if (r8 != 0) goto L_0x0185;	 Catch:{ JSONException -> 0x02cd }
            L_0x015a:
                r8 = "application/x-mpegurl";	 Catch:{ JSONException -> 0x02cd }
                r8 = r9.equalsIgnoreCase(r8);	 Catch:{ JSONException -> 0x02cd }
                if (r8 == 0) goto L_0x0171;	 Catch:{ JSONException -> 0x02cd }
            L_0x0162:
                r5 = "uri";	 Catch:{ JSONException -> 0x02cd }
                r5 = r3.getString(r5);	 Catch:{ JSONException -> 0x02cd }
                r8 = "lang";	 Catch:{ JSONException -> 0x02cd }
                r3 = r3.getString(r8);	 Catch:{ JSONException -> 0x02cd }
            L_0x016e:
                r16 = r3;	 Catch:{ JSONException -> 0x02cd }
                goto L_0x0184;	 Catch:{ JSONException -> 0x02cd }
            L_0x0171:
                r8 = android.text.TextUtils.isEmpty(r5);	 Catch:{ JSONException -> 0x02cd }
                if (r8 == 0) goto L_0x0184;	 Catch:{ JSONException -> 0x02cd }
            L_0x0177:
                r5 = "uri";	 Catch:{ JSONException -> 0x02cd }
                r5 = r3.getString(r5);	 Catch:{ JSONException -> 0x02cd }
                r8 = "lang";	 Catch:{ JSONException -> 0x02cd }
                r3 = r3.getString(r8);	 Catch:{ JSONException -> 0x02cd }
                goto L_0x016e;	 Catch:{ JSONException -> 0x02cd }
            L_0x0184:
                r8 = r12;	 Catch:{ JSONException -> 0x02cd }
            L_0x0185:
                r12 = r7;	 Catch:{ JSONException -> 0x02cd }
                r9 = r13;	 Catch:{ JSONException -> 0x02cd }
                r7 = r16;	 Catch:{ JSONException -> 0x02cd }
                r13 = r18;	 Catch:{ JSONException -> 0x02cd }
            L_0x018b:
                r4 = r4 + 1;	 Catch:{ JSONException -> 0x02cd }
                goto L_0x010c;	 Catch:{ JSONException -> 0x02cd }
            L_0x018f:
                r16 = r7;	 Catch:{ JSONException -> 0x02cd }
                r4 = r5;	 Catch:{ JSONException -> 0x02cd }
                goto L_0x0199;	 Catch:{ JSONException -> 0x02cd }
            L_0x0193:
                r14 = r5;	 Catch:{ JSONException -> 0x02cd }
                r13 = r8;	 Catch:{ JSONException -> 0x02cd }
                r15 = r9;	 Catch:{ JSONException -> 0x02cd }
                r8 = 0;	 Catch:{ JSONException -> 0x02cd }
                r9 = 0;	 Catch:{ JSONException -> 0x02cd }
                r12 = 0;	 Catch:{ JSONException -> 0x02cd }
            L_0x0199:
                r3 = r11;	 Catch:{ JSONException -> 0x02cd }
                r3.setUrl(r4);	 Catch:{ JSONException -> 0x02cd }
                r3 = r11;	 Catch:{ JSONException -> 0x02cd }
                r3.setmlang(r7);	 Catch:{ JSONException -> 0x02cd }
                r3 = r11;	 Catch:{ JSONException -> 0x02cd }
                r3.setBitrate(r8);	 Catch:{ JSONException -> 0x02cd }
                r3 = r11;	 Catch:{ JSONException -> 0x02cd }
                r3.setWidth(r9);	 Catch:{ JSONException -> 0x02cd }
                r3 = r11;	 Catch:{ JSONException -> 0x02cd }
                r3.setHeight(r12);	 Catch:{ JSONException -> 0x02cd }
                r3 = r11;	 Catch:{ JSONException -> 0x02cd }
                r3.setContentType(r13);	 Catch:{ JSONException -> 0x02cd }
                r3 = "captions";	 Catch:{ JSONException -> 0x0250 }
                r0 = r0.optJSONArray(r3);	 Catch:{ JSONException -> 0x0250 }
                if (r0 == 0) goto L_0x021d;	 Catch:{ JSONException -> 0x0250 }
            L_0x01bf:
                r3 = r11;	 Catch:{ JSONException -> 0x0250 }
                r5 = 1;	 Catch:{ JSONException -> 0x0250 }
                r3.setmHasCaption(r5);	 Catch:{ JSONException -> 0x0250 }
                r3 = 0;	 Catch:{ JSONException -> 0x0250 }
            L_0x01c6:
                r5 = r0.length();	 Catch:{ JSONException -> 0x0250 }
                if (r3 >= r5) goto L_0x021b;	 Catch:{ JSONException -> 0x0250 }
            L_0x01cc:
                r5 = r0.getJSONObject(r3);	 Catch:{ JSONException -> 0x0250 }
                r7 = "type";	 Catch:{ JSONException -> 0x0250 }
                r7 = r5.optString(r7);	 Catch:{ JSONException -> 0x0250 }
                r8 = android.text.TextUtils.isEmpty(r7);	 Catch:{ JSONException -> 0x0250 }
                if (r8 == 0) goto L_0x01dd;	 Catch:{ JSONException -> 0x0250 }
            L_0x01dc:
                goto L_0x0218;	 Catch:{ JSONException -> 0x0250 }
            L_0x01dd:
                r8 = "text/vtt";	 Catch:{ JSONException -> 0x0250 }
                r8 = r7.contains(r8);	 Catch:{ JSONException -> 0x0250 }
                if (r8 == 0) goto L_0x01f1;	 Catch:{ JSONException -> 0x0250 }
            L_0x01e5:
                r7 = r11;	 Catch:{ JSONException -> 0x0250 }
                r8 = "url";	 Catch:{ JSONException -> 0x0250 }
                r5 = r5.getString(r8);	 Catch:{ JSONException -> 0x0250 }
                r7.setmVttCaption(r5);	 Catch:{ JSONException -> 0x0250 }
                goto L_0x0218;	 Catch:{ JSONException -> 0x0250 }
            L_0x01f1:
                r8 = "application/ttml+xml";	 Catch:{ JSONException -> 0x0250 }
                r8 = r7.contains(r8);	 Catch:{ JSONException -> 0x0250 }
                if (r8 == 0) goto L_0x0205;	 Catch:{ JSONException -> 0x0250 }
            L_0x01f9:
                r7 = r11;	 Catch:{ JSONException -> 0x0250 }
                r8 = "url";	 Catch:{ JSONException -> 0x0250 }
                r5 = r5.getString(r8);	 Catch:{ JSONException -> 0x0250 }
                r7.setmDfxpCaption(r5);	 Catch:{ JSONException -> 0x0250 }
                goto L_0x0218;	 Catch:{ JSONException -> 0x0250 }
            L_0x0205:
                r8 = "text/srt";	 Catch:{ JSONException -> 0x0250 }
                r7 = r7.contains(r8);	 Catch:{ JSONException -> 0x0250 }
                if (r7 == 0) goto L_0x0218;	 Catch:{ JSONException -> 0x0250 }
            L_0x020d:
                r7 = r11;	 Catch:{ JSONException -> 0x0250 }
                r8 = "url";	 Catch:{ JSONException -> 0x0250 }
                r5 = r5.getString(r8);	 Catch:{ JSONException -> 0x0250 }
                r7.setmSrtCaption(r5);	 Catch:{ JSONException -> 0x0250 }
            L_0x0218:
                r3 = r3 + 1;	 Catch:{ JSONException -> 0x0250 }
                goto L_0x01c6;	 Catch:{ JSONException -> 0x0250 }
            L_0x021b:
                r12 = 0;	 Catch:{ JSONException -> 0x0250 }
                goto L_0x0223;	 Catch:{ JSONException -> 0x0250 }
            L_0x021d:
                r0 = r11;	 Catch:{ JSONException -> 0x0250 }
                r12 = 0;
                r0.setmHasCaption(r12);	 Catch:{ JSONException -> 0x0251 }
            L_0x0223:
                r0 = android.text.TextUtils.isEmpty(r6);	 Catch:{ JSONException -> 0x0251 }
                if (r0 != 0) goto L_0x0234;	 Catch:{ JSONException -> 0x0251 }
            L_0x0229:
                r0 = r11;	 Catch:{ JSONException -> 0x0251 }
                r0.setUrl(r6);	 Catch:{ JSONException -> 0x0251 }
                r0 = r11;	 Catch:{ JSONException -> 0x0251 }
                r0.setContentType(r10);	 Catch:{ JSONException -> 0x0251 }
                goto L_0x026d;	 Catch:{ JSONException -> 0x0251 }
            L_0x0234:
                r0 = android.text.TextUtils.isEmpty(r14);	 Catch:{ JSONException -> 0x0251 }
                if (r0 != 0) goto L_0x0245;	 Catch:{ JSONException -> 0x0251 }
            L_0x023a:
                r0 = r11;	 Catch:{ JSONException -> 0x0251 }
                r0.setUrl(r14);	 Catch:{ JSONException -> 0x0251 }
                r0 = r11;	 Catch:{ JSONException -> 0x0251 }
                r0.setContentType(r15);	 Catch:{ JSONException -> 0x0251 }
                goto L_0x026d;	 Catch:{ JSONException -> 0x0251 }
            L_0x0245:
                r0 = r11;	 Catch:{ JSONException -> 0x0251 }
                r0.setUrl(r4);	 Catch:{ JSONException -> 0x0251 }
                r0 = r11;	 Catch:{ JSONException -> 0x0251 }
                r0.setContentType(r13);	 Catch:{ JSONException -> 0x0251 }
                goto L_0x026d;
            L_0x0250:
                r12 = 0;
            L_0x0251:
                r0 = new java.lang.StringBuilder;	 Catch:{ JSONException -> 0x02cd }
                r0.<init>();	 Catch:{ JSONException -> 0x02cd }
                r3 = r11;	 Catch:{ JSONException -> 0x02cd }
                r3 = r3.getTitle();	 Catch:{ JSONException -> 0x02cd }
                r0.append(r3);	 Catch:{ JSONException -> 0x02cd }
                r3 = ": videoAsset does not contain captions";	 Catch:{ JSONException -> 0x02cd }
                r0.append(r3);	 Catch:{ JSONException -> 0x02cd }
                r0 = r0.toString();	 Catch:{ JSONException -> 0x02cd }
                com.xumo.xumo.util.LogUtil.d(r0);	 Catch:{ JSONException -> 0x02cd }
                goto L_0x026d;
            L_0x026c:
                r12 = 0;
            L_0x026d:
                r0 = "cuePoints";	 Catch:{ JSONException -> 0x0297 }
                r0 = r2.getJSONArray(r0);	 Catch:{ JSONException -> 0x0297 }
                if (r0 == 0) goto L_0x02b1;	 Catch:{ JSONException -> 0x0297 }
            L_0x0275:
                r2 = r0.length();	 Catch:{ JSONException -> 0x0297 }
                if (r2 == 0) goto L_0x02b1;	 Catch:{ JSONException -> 0x0297 }
            L_0x027b:
                r2 = r0.length();	 Catch:{ JSONException -> 0x0297 }
                r3 = r0.length();	 Catch:{ JSONException -> 0x0297 }
                r3 = new float[r3];	 Catch:{ JSONException -> 0x0297 }
            L_0x0285:
                if (r12 >= r2) goto L_0x0291;	 Catch:{ JSONException -> 0x0297 }
            L_0x0287:
                r4 = r0.getDouble(r12);	 Catch:{ JSONException -> 0x0297 }
                r4 = (float) r4;	 Catch:{ JSONException -> 0x0297 }
                r3[r12] = r4;	 Catch:{ JSONException -> 0x0297 }
                r12 = r12 + 1;	 Catch:{ JSONException -> 0x0297 }
                goto L_0x0285;	 Catch:{ JSONException -> 0x0297 }
            L_0x0291:
                r0 = r11;	 Catch:{ JSONException -> 0x0297 }
                r0.setCuePoints(r3);	 Catch:{ JSONException -> 0x0297 }
                goto L_0x02b1;
            L_0x0297:
                r0 = new java.lang.StringBuilder;	 Catch:{ JSONException -> 0x02cd }
                r0.<init>();	 Catch:{ JSONException -> 0x02cd }
                r2 = r11;	 Catch:{ JSONException -> 0x02cd }
                r2 = r2.getTitle();	 Catch:{ JSONException -> 0x02cd }
                r0.append(r2);	 Catch:{ JSONException -> 0x02cd }
                r2 = ": videoAsset does not contain cue points";	 Catch:{ JSONException -> 0x02cd }
                r0.append(r2);	 Catch:{ JSONException -> 0x02cd }
                r0 = r0.toString();	 Catch:{ JSONException -> 0x02cd }
                com.xumo.xumo.util.LogUtil.d(r0);	 Catch:{ JSONException -> 0x02cd }
            L_0x02b1:
                r0 = new java.lang.StringBuilder;
                r0.<init>();
                r2 = "getVideoMetadata videoAsset=";
                r0.append(r2);
                r2 = r11;
                r0.append(r2);
                r0 = r0.toString();
                com.xumo.xumo.util.LogUtil.d(r0);
                r0 = r12;
                r0.onCompletion();
                return;
            L_0x02cd:
                r0 = r12;
                r0.onError();
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.xumo.xumo.service.XumoWebService.14.onResponse(org.json.JSONObject):void");
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("getVideoMetadata.onErrorResponse: ");
                stringBuilder.append(volleyError);
                LogUtil.d(stringBuilder.toString());
                noResponseListener.onError();
            }
        }));
    }

    public void searchAssets(String[] strArr, final Listener listener) {
        if (strArr != null) {
            if (strArr.length > 0) {
                String str = "";
                for (int i = 0; i < strArr.length; i++) {
                    if (Pattern.compile("[^0-9a-zA-Z_@$]").matcher(strArr[i]).find()) {
                        LogUtil.e("Invalid characters. Please check your search and try again");
                        listener.onError();
                        return;
                    }
                    if (i == 0) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("&q=keyword:");
                        stringBuilder.append(strArr[i].toLowerCase(Locale.ENGLISH));
                        str = stringBuilder.toString();
                    } else {
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append(str);
                        stringBuilder2.append("&q.op=AND&q=keyword:");
                        stringBuilder2.append(strArr[i].toLowerCase(Locale.ENGLISH));
                        str = stringBuilder2.toString();
                    }
                }
                strArr = new StringBuilder();
                strArr.append(getMdsPath(SEARCH_VIDEO_ASSETS_URL));
                strArr.append(str);
                addXumoRequestQueue(new JsonObjectRequest(0, strArr.toString(), null, new com.android.volley.Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject jSONObject) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("searchVideos.onResponse = ");
                        stringBuilder.append(jSONObject);
                        LogUtil.d(stringBuilder.toString());
                        ArrayList arrayList = new ArrayList();
                        if (jSONObject != null) {
                            try {
                                jSONObject = jSONObject.getJSONArray(JsonKey.RESULTS);
                                for (int i = 0; i < jSONObject.length(); i++) {
                                    JSONObject jSONObject2 = jSONObject.getJSONObject(i);
                                    Object optString = jSONObject2.optString("id");
                                    if (!TextUtils.isEmpty(optString)) {
                                        if (TextUtils.equals(jSONObject2.optString("type"), "Asset")) {
                                            VideoAsset videoAsset = new VideoAsset(optString, "", "");
                                            optString = jSONObject2.optString(JsonKey.EPISODE_TITLE);
                                            if (TextUtils.isEmpty(optString)) {
                                                videoAsset.setTitle(jSONObject2.optString("title", ""));
                                            } else {
                                                videoAsset.setTitle(optString);
                                            }
                                            videoAsset.setRunTime(jSONObject2.optLong(JsonKey.RUN_TIME, 0));
                                            String optString2 = jSONObject2.optString(JsonKey.AVAILABLE_SINCE);
                                            if (optString2 != null) {
                                                try {
                                                    videoAsset.setAssetAge(new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US).parse(optString2));
                                                } catch (ParseException e) {
                                                    StringBuilder stringBuilder2 = new StringBuilder();
                                                    stringBuilder2.append("invalid availableSince ");
                                                    stringBuilder2.append(e);
                                                    LogUtil.w(stringBuilder2.toString());
                                                    videoAsset.setAssetAge(new Date(0));
                                                }
                                            }
                                            videoAsset.setAssetType(1);
                                            arrayList.add(videoAsset);
                                        }
                                    }
                                }
                            } catch (JSONObject jSONObject3) {
                                StringBuilder stringBuilder3 = new StringBuilder();
                                stringBuilder3.append("searchVideos ");
                                stringBuilder3.append(jSONObject3);
                                LogUtil.e(stringBuilder3.toString());
                            }
                        }
                        listener.onCompletion(arrayList);
                    }
                }, new ErrorListener() {
                    public void onErrorResponse(VolleyError volleyError) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("searchVideos.onErrorResponse: ");
                        stringBuilder.append(volleyError);
                        LogUtil.d(stringBuilder.toString());
                        listener.onError();
                    }
                }));
                return;
            }
        }
        listener.onError();
    }

    private void getDeviceAuthorization(final DeviceAuthorizationListener deviceAuthorizationListener) {
        LogUtil.d(" - Method start.");
        if (TextUtils.isEmpty(this.mUserPreferences.getDeviceId())) {
            addXumoRequestQueue(new JsonObjectRequest(1, getMdsPath(POST_ID_URL), null, new com.android.volley.Response.Listener<JSONObject>() {
                public void onResponse(JSONObject jSONObject) {
                    StringBuilder stringBuilder;
                    String stringBuilder2;
                    String valueOf;
                    String valueOf2;
                    StringBuilder stringBuilder3;
                    DeviceIdList deviceIdList;
                    StringBuilder stringBuilder4 = new StringBuilder();
                    stringBuilder4.append("getDeviceAuthorization.onResponse response = ");
                    stringBuilder4.append(jSONObject);
                    LogUtil.d(stringBuilder4.toString());
                    try {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("XumoDeviceId ");
                        stringBuilder.append(jSONObject.getString("id"));
                        stringBuilder2 = stringBuilder.toString();
                        try {
                            valueOf = String.valueOf(jSONObject.getString(JsonKey.CHANNEL_LIST_ID));
                            try {
                                valueOf2 = String.valueOf(jSONObject.getString(JsonKey.GEO_ID));
                            } catch (JSONException e) {
                                jSONObject = e;
                                valueOf2 = null;
                                stringBuilder3 = new StringBuilder();
                                stringBuilder3.append("getDeviceAuthorization.onResponse: ");
                                stringBuilder3.append(jSONObject.getMessage());
                                LogUtil.e(stringBuilder3.toString());
                                BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppError, null, new String[]{"-1", jSONObject.getMessage()});
                                jSONObject = null;
                                if (TextUtils.isEmpty(stringBuilder2)) {
                                    LogUtil.w("getDeviceAuthorization.onResponse: device id not found.");
                                    stringBuilder = new StringBuilder();
                                    stringBuilder.append("XumoDeviceId ");
                                    stringBuilder.append(XumoUtil.generateTempDeviceId());
                                    stringBuilder2 = stringBuilder.toString();
                                    BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppError, null, new String[]{"9016", "device id not found"});
                                }
                                if (TextUtils.isEmpty(valueOf)) {
                                    LogUtil.w("getDeviceAuthorization.onResponse: channelListId not found.");
                                    valueOf = XumoWebService.defaultChannelListId;
                                    BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppError, null, new String[]{"9027", "channelListId not found"});
                                }
                                if (XumoWebService.this.mUserPreferences.getUseTestChannels()) {
                                    valueOf = XumoWebService.TEST_CHANNEL_LIST_ID;
                                }
                                if (TextUtils.isEmpty(valueOf2)) {
                                    valueOf2 = EnvironmentCompat.MEDIA_UNKNOWN;
                                }
                                if (TextUtils.isEmpty(jSONObject)) {
                                    jSONObject = EnvironmentCompat.MEDIA_UNKNOWN;
                                }
                                stringBuilder4 = new StringBuilder();
                                stringBuilder4.append("getDeviceAuthorization.onResponse deviceId=");
                                stringBuilder4.append(stringBuilder2);
                                stringBuilder4.append(", channelListId=");
                                stringBuilder4.append(valueOf);
                                LogUtil.d(stringBuilder4.toString());
                                XumoWebService.this.mUserPreferences.setDeviceId(stringBuilder2);
                                deviceIdList = new DeviceIdList();
                                deviceIdList.setChannelListId(valueOf);
                                deviceIdList.setGeoId(valueOf2);
                                deviceIdList.setUdk(jSONObject);
                                deviceAuthorizationListener.onCompletion(stringBuilder2, deviceIdList);
                            }
                        } catch (JSONException e2) {
                            jSONObject = e2;
                            valueOf = null;
                            valueOf2 = valueOf;
                            stringBuilder3 = new StringBuilder();
                            stringBuilder3.append("getDeviceAuthorization.onResponse: ");
                            stringBuilder3.append(jSONObject.getMessage());
                            LogUtil.e(stringBuilder3.toString());
                            BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppError, null, new String[]{"-1", jSONObject.getMessage()});
                            jSONObject = null;
                            if (TextUtils.isEmpty(stringBuilder2)) {
                                LogUtil.w("getDeviceAuthorization.onResponse: device id not found.");
                                stringBuilder = new StringBuilder();
                                stringBuilder.append("XumoDeviceId ");
                                stringBuilder.append(XumoUtil.generateTempDeviceId());
                                stringBuilder2 = stringBuilder.toString();
                                BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppError, null, new String[]{"9016", "device id not found"});
                            }
                            if (TextUtils.isEmpty(valueOf)) {
                                LogUtil.w("getDeviceAuthorization.onResponse: channelListId not found.");
                                valueOf = XumoWebService.defaultChannelListId;
                                BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppError, null, new String[]{"9027", "channelListId not found"});
                            }
                            if (XumoWebService.this.mUserPreferences.getUseTestChannels()) {
                                valueOf = XumoWebService.TEST_CHANNEL_LIST_ID;
                            }
                            if (TextUtils.isEmpty(valueOf2)) {
                                valueOf2 = EnvironmentCompat.MEDIA_UNKNOWN;
                            }
                            if (TextUtils.isEmpty(jSONObject)) {
                                jSONObject = EnvironmentCompat.MEDIA_UNKNOWN;
                            }
                            stringBuilder4 = new StringBuilder();
                            stringBuilder4.append("getDeviceAuthorization.onResponse deviceId=");
                            stringBuilder4.append(stringBuilder2);
                            stringBuilder4.append(", channelListId=");
                            stringBuilder4.append(valueOf);
                            LogUtil.d(stringBuilder4.toString());
                            XumoWebService.this.mUserPreferences.setDeviceId(stringBuilder2);
                            deviceIdList = new DeviceIdList();
                            deviceIdList.setChannelListId(valueOf);
                            deviceIdList.setGeoId(valueOf2);
                            deviceIdList.setUdk(jSONObject);
                            deviceAuthorizationListener.onCompletion(stringBuilder2, deviceIdList);
                        }
                        try {
                            jSONObject = String.valueOf(jSONObject.getString(JsonKey.UDK));
                        } catch (JSONException e3) {
                            jSONObject = e3;
                            stringBuilder3 = new StringBuilder();
                            stringBuilder3.append("getDeviceAuthorization.onResponse: ");
                            stringBuilder3.append(jSONObject.getMessage());
                            LogUtil.e(stringBuilder3.toString());
                            BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppError, null, new String[]{"-1", jSONObject.getMessage()});
                            jSONObject = null;
                            if (TextUtils.isEmpty(stringBuilder2)) {
                                LogUtil.w("getDeviceAuthorization.onResponse: device id not found.");
                                stringBuilder = new StringBuilder();
                                stringBuilder.append("XumoDeviceId ");
                                stringBuilder.append(XumoUtil.generateTempDeviceId());
                                stringBuilder2 = stringBuilder.toString();
                                BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppError, null, new String[]{"9016", "device id not found"});
                            }
                            if (TextUtils.isEmpty(valueOf)) {
                                LogUtil.w("getDeviceAuthorization.onResponse: channelListId not found.");
                                valueOf = XumoWebService.defaultChannelListId;
                                BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppError, null, new String[]{"9027", "channelListId not found"});
                            }
                            if (XumoWebService.this.mUserPreferences.getUseTestChannels()) {
                                valueOf = XumoWebService.TEST_CHANNEL_LIST_ID;
                            }
                            if (TextUtils.isEmpty(valueOf2)) {
                                valueOf2 = EnvironmentCompat.MEDIA_UNKNOWN;
                            }
                            if (TextUtils.isEmpty(jSONObject)) {
                                jSONObject = EnvironmentCompat.MEDIA_UNKNOWN;
                            }
                            stringBuilder4 = new StringBuilder();
                            stringBuilder4.append("getDeviceAuthorization.onResponse deviceId=");
                            stringBuilder4.append(stringBuilder2);
                            stringBuilder4.append(", channelListId=");
                            stringBuilder4.append(valueOf);
                            LogUtil.d(stringBuilder4.toString());
                            XumoWebService.this.mUserPreferences.setDeviceId(stringBuilder2);
                            deviceIdList = new DeviceIdList();
                            deviceIdList.setChannelListId(valueOf);
                            deviceIdList.setGeoId(valueOf2);
                            deviceIdList.setUdk(jSONObject);
                            deviceAuthorizationListener.onCompletion(stringBuilder2, deviceIdList);
                        }
                    } catch (JSONException e4) {
                        jSONObject = e4;
                        stringBuilder2 = null;
                        valueOf = stringBuilder2;
                        valueOf2 = valueOf;
                        stringBuilder3 = new StringBuilder();
                        stringBuilder3.append("getDeviceAuthorization.onResponse: ");
                        stringBuilder3.append(jSONObject.getMessage());
                        LogUtil.e(stringBuilder3.toString());
                        BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppError, null, new String[]{"-1", jSONObject.getMessage()});
                        jSONObject = null;
                        if (TextUtils.isEmpty(stringBuilder2)) {
                            LogUtil.w("getDeviceAuthorization.onResponse: device id not found.");
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("XumoDeviceId ");
                            stringBuilder.append(XumoUtil.generateTempDeviceId());
                            stringBuilder2 = stringBuilder.toString();
                            BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppError, null, new String[]{"9016", "device id not found"});
                        }
                        if (TextUtils.isEmpty(valueOf)) {
                            LogUtil.w("getDeviceAuthorization.onResponse: channelListId not found.");
                            valueOf = XumoWebService.defaultChannelListId;
                            BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppError, null, new String[]{"9027", "channelListId not found"});
                        }
                        if (XumoWebService.this.mUserPreferences.getUseTestChannels()) {
                            valueOf = XumoWebService.TEST_CHANNEL_LIST_ID;
                        }
                        if (TextUtils.isEmpty(valueOf2)) {
                            valueOf2 = EnvironmentCompat.MEDIA_UNKNOWN;
                        }
                        if (TextUtils.isEmpty(jSONObject)) {
                            jSONObject = EnvironmentCompat.MEDIA_UNKNOWN;
                        }
                        stringBuilder4 = new StringBuilder();
                        stringBuilder4.append("getDeviceAuthorization.onResponse deviceId=");
                        stringBuilder4.append(stringBuilder2);
                        stringBuilder4.append(", channelListId=");
                        stringBuilder4.append(valueOf);
                        LogUtil.d(stringBuilder4.toString());
                        XumoWebService.this.mUserPreferences.setDeviceId(stringBuilder2);
                        deviceIdList = new DeviceIdList();
                        deviceIdList.setChannelListId(valueOf);
                        deviceIdList.setGeoId(valueOf2);
                        deviceIdList.setUdk(jSONObject);
                        deviceAuthorizationListener.onCompletion(stringBuilder2, deviceIdList);
                    }
                    if (TextUtils.isEmpty(stringBuilder2)) {
                        LogUtil.w("getDeviceAuthorization.onResponse: device id not found.");
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("XumoDeviceId ");
                        stringBuilder.append(XumoUtil.generateTempDeviceId());
                        stringBuilder2 = stringBuilder.toString();
                        BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppError, null, new String[]{"9016", "device id not found"});
                    }
                    if (TextUtils.isEmpty(valueOf)) {
                        LogUtil.w("getDeviceAuthorization.onResponse: channelListId not found.");
                        valueOf = XumoWebService.defaultChannelListId;
                        BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppError, null, new String[]{"9027", "channelListId not found"});
                    }
                    if (XumoWebService.this.mUserPreferences.getUseTestChannels()) {
                        valueOf = XumoWebService.TEST_CHANNEL_LIST_ID;
                    }
                    if (TextUtils.isEmpty(valueOf2)) {
                        valueOf2 = EnvironmentCompat.MEDIA_UNKNOWN;
                    }
                    if (TextUtils.isEmpty(jSONObject)) {
                        jSONObject = EnvironmentCompat.MEDIA_UNKNOWN;
                    }
                    stringBuilder4 = new StringBuilder();
                    stringBuilder4.append("getDeviceAuthorization.onResponse deviceId=");
                    stringBuilder4.append(stringBuilder2);
                    stringBuilder4.append(", channelListId=");
                    stringBuilder4.append(valueOf);
                    LogUtil.d(stringBuilder4.toString());
                    XumoWebService.this.mUserPreferences.setDeviceId(stringBuilder2);
                    deviceIdList = new DeviceIdList();
                    deviceIdList.setChannelListId(valueOf);
                    deviceIdList.setGeoId(valueOf2);
                    deviceIdList.setUdk(jSONObject);
                    deviceAuthorizationListener.onCompletion(stringBuilder2, deviceIdList);
                }
            }, new ErrorListener() {
                public void onErrorResponse(VolleyError volleyError) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("getDeviceAuthorization.onErrorResponse: ");
                    stringBuilder.append(volleyError.getMessage());
                    LogUtil.e(stringBuilder.toString());
                    int i = volleyError.networkResponse != null ? volleyError.networkResponse.statusCode : -1;
                    BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppError, null, new String[]{String.valueOf(i), volleyError.getMessage()});
                    volleyError = new StringBuilder();
                    volleyError.append("XumoDeviceId ");
                    volleyError.append(XumoUtil.generateTempDeviceId());
                    volleyError = volleyError.toString();
                    String access$200 = XumoWebService.defaultChannelListId;
                    if (XumoWebService.this.mUserPreferences.getUseTestChannels()) {
                        access$200 = XumoWebService.TEST_CHANNEL_LIST_ID;
                    }
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("getDeviceAuthorization.onErrorResponse deviceId=");
                    stringBuilder2.append(volleyError);
                    stringBuilder2.append(", channelListId=");
                    stringBuilder2.append(access$200);
                    LogUtil.d(stringBuilder2.toString());
                    XumoWebService.this.mUserPreferences.setDeviceId(volleyError);
                    DeviceIdList deviceIdList = new DeviceIdList();
                    deviceIdList.setChannelListId(access$200);
                    deviceIdList.setGeoId(EnvironmentCompat.MEDIA_UNKNOWN);
                    deviceIdList.setUdk(EnvironmentCompat.MEDIA_UNKNOWN);
                    deviceAuthorizationListener.onCompletion(volleyError, deviceIdList);
                }
            }) {
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> hashMap = new HashMap();
                    hashMap.put("Authorization", XumoWebService.authorizationValue);
                    return hashMap;
                }
            });
        } else {
            deviceAuthorizationListener.onCompletion(this.mUserPreferences.getDeviceId(), mDeviceSettings);
        }
    }

    public void getDeviceSettings(DeviceSettingsListener deviceSettingsListener) {
        getDeviceSettings(0, deviceSettingsListener);
    }

    private void getDeviceSettings(final int i, final DeviceSettingsListener deviceSettingsListener) {
        LogUtil.d(" - Method start.");
        getDeviceAuthorization(new DeviceAuthorizationListener() {
            public void onCompletion(final String str, DeviceIdList deviceIdList) {
                LogUtil.d("getDeviceSettings.onCompletion");
                if (deviceIdList != null) {
                    if (XumoWebService.mDeviceSettings == null) {
                        XumoWebService.mDeviceSettings = deviceIdList;
                    }
                    deviceSettingsListener.onCompletion(str, deviceIdList);
                    return;
                }
                final String str2 = str;
                XumoWebService.this.addXumoRequestQueue(new JsonObjectRequest(0, XumoWebService.getMdsPath(XumoWebService.GET_DEVICE_SETTINGS_URL), null, new com.android.volley.Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject jSONObject) {
                        String valueOf;
                        String valueOf2;
                        StringBuilder stringBuilder;
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("getDeviceSettings.response=");
                        stringBuilder2.append(jSONObject);
                        LogUtil.d(stringBuilder2.toString());
                        try {
                            valueOf = String.valueOf(jSONObject.getString(JsonKey.CHANNEL_LIST_ID));
                            try {
                                valueOf2 = String.valueOf(jSONObject.getString(JsonKey.GEO_ID));
                            } catch (JSONException e) {
                                jSONObject = e;
                                valueOf2 = null;
                                stringBuilder = new StringBuilder();
                                stringBuilder.append("getDeviceSettings.onResponse: ");
                                stringBuilder.append(jSONObject.getMessage());
                                LogUtil.e(stringBuilder.toString());
                                BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppError, null, new String[]{"", jSONObject.getMessage()});
                                jSONObject = null;
                                if (TextUtils.isEmpty(valueOf)) {
                                    LogUtil.w("getDeviceSettings.onResponse: channelListId not found.");
                                    valueOf = XumoWebService.defaultChannelListId;
                                    BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppError, null, new String[]{"9027", "channelListId not found"});
                                }
                                if (XumoWebService.this.mUserPreferences.getUseTestChannels()) {
                                    valueOf = XumoWebService.TEST_CHANNEL_LIST_ID;
                                }
                                if (TextUtils.isEmpty(valueOf2)) {
                                    valueOf2 = EnvironmentCompat.MEDIA_UNKNOWN;
                                }
                                if (TextUtils.isEmpty(jSONObject)) {
                                    jSONObject = EnvironmentCompat.MEDIA_UNKNOWN;
                                }
                                stringBuilder2 = new StringBuilder();
                                stringBuilder2.append("getDeviceSettings.onResponse deviceId=");
                                stringBuilder2.append(str);
                                stringBuilder2.append(", channelListId=");
                                stringBuilder2.append(valueOf);
                                LogUtil.d(stringBuilder2.toString());
                                XumoWebService.mDeviceSettings = new DeviceIdList();
                                XumoWebService.mDeviceSettings.setChannelListId(valueOf);
                                XumoWebService.mDeviceSettings.setGeoId(valueOf2);
                                XumoWebService.mDeviceSettings.setUdk(jSONObject);
                                deviceSettingsListener.onCompletion(str, XumoWebService.mDeviceSettings);
                            }
                            try {
                                jSONObject = String.valueOf(jSONObject.getString(JsonKey.UDK));
                            } catch (JSONException e2) {
                                jSONObject = e2;
                                stringBuilder = new StringBuilder();
                                stringBuilder.append("getDeviceSettings.onResponse: ");
                                stringBuilder.append(jSONObject.getMessage());
                                LogUtil.e(stringBuilder.toString());
                                BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppError, null, new String[]{"", jSONObject.getMessage()});
                                jSONObject = null;
                                if (TextUtils.isEmpty(valueOf)) {
                                    LogUtil.w("getDeviceSettings.onResponse: channelListId not found.");
                                    valueOf = XumoWebService.defaultChannelListId;
                                    BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppError, null, new String[]{"9027", "channelListId not found"});
                                }
                                if (XumoWebService.this.mUserPreferences.getUseTestChannels()) {
                                    valueOf = XumoWebService.TEST_CHANNEL_LIST_ID;
                                }
                                if (TextUtils.isEmpty(valueOf2)) {
                                    valueOf2 = EnvironmentCompat.MEDIA_UNKNOWN;
                                }
                                if (TextUtils.isEmpty(jSONObject)) {
                                    jSONObject = EnvironmentCompat.MEDIA_UNKNOWN;
                                }
                                stringBuilder2 = new StringBuilder();
                                stringBuilder2.append("getDeviceSettings.onResponse deviceId=");
                                stringBuilder2.append(str);
                                stringBuilder2.append(", channelListId=");
                                stringBuilder2.append(valueOf);
                                LogUtil.d(stringBuilder2.toString());
                                XumoWebService.mDeviceSettings = new DeviceIdList();
                                XumoWebService.mDeviceSettings.setChannelListId(valueOf);
                                XumoWebService.mDeviceSettings.setGeoId(valueOf2);
                                XumoWebService.mDeviceSettings.setUdk(jSONObject);
                                deviceSettingsListener.onCompletion(str, XumoWebService.mDeviceSettings);
                            }
                        } catch (JSONException e3) {
                            jSONObject = e3;
                            valueOf = null;
                            valueOf2 = valueOf;
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("getDeviceSettings.onResponse: ");
                            stringBuilder.append(jSONObject.getMessage());
                            LogUtil.e(stringBuilder.toString());
                            BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppError, null, new String[]{"", jSONObject.getMessage()});
                            jSONObject = null;
                            if (TextUtils.isEmpty(valueOf)) {
                                LogUtil.w("getDeviceSettings.onResponse: channelListId not found.");
                                valueOf = XumoWebService.defaultChannelListId;
                                BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppError, null, new String[]{"9027", "channelListId not found"});
                            }
                            if (XumoWebService.this.mUserPreferences.getUseTestChannels()) {
                                valueOf = XumoWebService.TEST_CHANNEL_LIST_ID;
                            }
                            if (TextUtils.isEmpty(valueOf2)) {
                                valueOf2 = EnvironmentCompat.MEDIA_UNKNOWN;
                            }
                            if (TextUtils.isEmpty(jSONObject)) {
                                jSONObject = EnvironmentCompat.MEDIA_UNKNOWN;
                            }
                            stringBuilder2 = new StringBuilder();
                            stringBuilder2.append("getDeviceSettings.onResponse deviceId=");
                            stringBuilder2.append(str);
                            stringBuilder2.append(", channelListId=");
                            stringBuilder2.append(valueOf);
                            LogUtil.d(stringBuilder2.toString());
                            XumoWebService.mDeviceSettings = new DeviceIdList();
                            XumoWebService.mDeviceSettings.setChannelListId(valueOf);
                            XumoWebService.mDeviceSettings.setGeoId(valueOf2);
                            XumoWebService.mDeviceSettings.setUdk(jSONObject);
                            deviceSettingsListener.onCompletion(str, XumoWebService.mDeviceSettings);
                        }
                        if (TextUtils.isEmpty(valueOf)) {
                            LogUtil.w("getDeviceSettings.onResponse: channelListId not found.");
                            valueOf = XumoWebService.defaultChannelListId;
                            BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppError, null, new String[]{"9027", "channelListId not found"});
                        }
                        if (XumoWebService.this.mUserPreferences.getUseTestChannels()) {
                            valueOf = XumoWebService.TEST_CHANNEL_LIST_ID;
                        }
                        if (TextUtils.isEmpty(valueOf2)) {
                            valueOf2 = EnvironmentCompat.MEDIA_UNKNOWN;
                        }
                        if (TextUtils.isEmpty(jSONObject)) {
                            jSONObject = EnvironmentCompat.MEDIA_UNKNOWN;
                        }
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("getDeviceSettings.onResponse deviceId=");
                        stringBuilder2.append(str);
                        stringBuilder2.append(", channelListId=");
                        stringBuilder2.append(valueOf);
                        LogUtil.d(stringBuilder2.toString());
                        XumoWebService.mDeviceSettings = new DeviceIdList();
                        XumoWebService.mDeviceSettings.setChannelListId(valueOf);
                        XumoWebService.mDeviceSettings.setGeoId(valueOf2);
                        XumoWebService.mDeviceSettings.setUdk(jSONObject);
                        deviceSettingsListener.onCompletion(str, XumoWebService.mDeviceSettings);
                    }
                }, new ErrorListener() {
                    public void onErrorResponse(VolleyError volleyError) {
                        int i = volleyError.networkResponse != null ? volleyError.networkResponse.statusCode : -1;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("getDeviceSettings.onErrorResponse: ");
                        stringBuilder.append(i);
                        stringBuilder.append(": ");
                        stringBuilder.append(volleyError.getMessage());
                        LogUtil.d(stringBuilder.toString());
                        BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppError, null, new String[]{String.valueOf(i), volleyError.getMessage()});
                        if (i == 401) {
                            XumoWebService.this.mUserPreferences.removeDeviceId();
                            if (i < 1) {
                                new Timer().schedule(new TimerTask() {
                                    public void run() {
                                        StringBuilder stringBuilder = new StringBuilder();
                                        stringBuilder.append("getDeviceSettings recursionCount=");
                                        stringBuilder.append(i);
                                        stringBuilder.append(" retryCount=");
                                        stringBuilder.append(1);
                                        LogUtil.d(stringBuilder.toString());
                                        XumoWebService.this.getDeviceSettings(i + 1, deviceSettingsListener);
                                    }
                                }, 1000);
                                return;
                            } else {
                                deviceSettingsListener.onError();
                                return;
                            }
                        }
                        XumoWebService.mDeviceSettings = new DeviceIdList();
                        XumoWebService.mDeviceSettings.setChannelListId(XumoWebService.defaultChannelListId);
                        XumoWebService.mDeviceSettings.setGeoId(EnvironmentCompat.MEDIA_UNKNOWN);
                        XumoWebService.mDeviceSettings.setUdk(EnvironmentCompat.MEDIA_UNKNOWN);
                        deviceSettingsListener.onCompletion(str, XumoWebService.mDeviceSettings);
                    }
                }) {
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> hashMap = new HashMap();
                        hashMap.put("Authorization", str2);
                        return hashMap;
                    }
                });
            }

            public void onError() {
                LogUtil.d("getDeviceSettings.onError");
                deviceSettingsListener.onError();
            }
        });
    }

    private void getOnNowLiveList(String str, final Listener listener) {
        addXumoRequestQueue(new JsonObjectRequest(0, String.format(getMdsPath(GET_LIVE_ON_NOW_AND_NEXT_URL), new Object[]{str}), null, new com.android.volley.Response.Listener<JSONObject>() {
            public void onResponse(org.json.JSONObject r8) {
                /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r7 = this;
                r0 = new java.lang.StringBuilder;
                r0.<init>();
                r1 = "getOnNowLiveList.response=";
                r0.append(r1);
                r0.append(r8);
                r0 = r0.toString();
                com.xumo.xumo.util.LogUtil.d(r0);
                r0 = new java.util.ArrayList;
                r0.<init>();
                r1 = "results";	 Catch:{ JSONException -> 0x019b }
                r8 = r8.getJSONArray(r1);	 Catch:{ JSONException -> 0x019b }
                r1 = 0;	 Catch:{ JSONException -> 0x019b }
            L_0x0020:
                r2 = r8.length();	 Catch:{ JSONException -> 0x019b }
                if (r1 >= r2) goto L_0x0181;	 Catch:{ JSONException -> 0x019b }
            L_0x0026:
                r2 = r8.getJSONObject(r1);	 Catch:{ JSONException -> 0x019b }
                r3 = r1 % 2;	 Catch:{ JSONException -> 0x019b }
                if (r3 != 0) goto L_0x00c8;	 Catch:{ JSONException -> 0x019b }
            L_0x002e:
                r3 = new com.xumo.xumo.model.OnNowLive;	 Catch:{ JSONException -> 0x019b }
                r3.<init>();	 Catch:{ JSONException -> 0x019b }
                r4 = "id";	 Catch:{ JSONException -> 0x019b }
                r4 = r2.optString(r4);	 Catch:{ JSONException -> 0x019b }
                r3.setId(r4);	 Catch:{ JSONException -> 0x019b }
                r4 = "title";	 Catch:{ JSONException -> 0x019b }
                r4 = r2.optString(r4);	 Catch:{ JSONException -> 0x019b }
                r3.setTitle(r4);	 Catch:{ JSONException -> 0x019b }
                r4 = "channelId";	 Catch:{ JSONException -> 0x019b }
                r4 = r2.optLong(r4);	 Catch:{ JSONException -> 0x019b }
                r4 = java.lang.String.valueOf(r4);	 Catch:{ JSONException -> 0x019b }
                r3.setChannelId(r4);	 Catch:{ JSONException -> 0x019b }
                r4 = "start";	 Catch:{ JSONException -> 0x019b }
                r4 = r2.optLong(r4);	 Catch:{ JSONException -> 0x019b }
                r3.setStart(r4);	 Catch:{ JSONException -> 0x019b }
                r4 = "end";	 Catch:{ JSONException -> 0x019b }
                r4 = r2.optLong(r4);	 Catch:{ JSONException -> 0x019b }
                r3.setEnd(r4);	 Catch:{ JSONException -> 0x019b }
                r4 = "descriptions";	 Catch:{ JSONException -> 0x019b }
                r2 = r2.optJSONObject(r4);	 Catch:{ JSONException -> 0x019b }
                if (r2 == 0) goto L_0x00c3;	 Catch:{ JSONException -> 0x019b }
            L_0x006c:
                r4 = "tiny";	 Catch:{ JSONException -> 0x019b }
                r4 = r2.optString(r4);	 Catch:{ JSONException -> 0x019b }
                r4 = android.text.TextUtils.isEmpty(r4);	 Catch:{ JSONException -> 0x019b }
                if (r4 != 0) goto L_0x0082;	 Catch:{ JSONException -> 0x019b }
            L_0x0078:
                r4 = "tiny";	 Catch:{ JSONException -> 0x019b }
                r2 = r2.getString(r4);	 Catch:{ JSONException -> 0x019b }
                r3.setDescriptionText(r2);	 Catch:{ JSONException -> 0x019b }
                goto L_0x00c3;	 Catch:{ JSONException -> 0x019b }
            L_0x0082:
                r4 = "small";	 Catch:{ JSONException -> 0x019b }
                r4 = r2.optString(r4);	 Catch:{ JSONException -> 0x019b }
                r4 = android.text.TextUtils.isEmpty(r4);	 Catch:{ JSONException -> 0x019b }
                if (r4 != 0) goto L_0x0098;	 Catch:{ JSONException -> 0x019b }
            L_0x008e:
                r4 = "small";	 Catch:{ JSONException -> 0x019b }
                r2 = r2.getString(r4);	 Catch:{ JSONException -> 0x019b }
                r3.setDescriptionText(r2);	 Catch:{ JSONException -> 0x019b }
                goto L_0x00c3;	 Catch:{ JSONException -> 0x019b }
            L_0x0098:
                r4 = "medium";	 Catch:{ JSONException -> 0x019b }
                r4 = r2.optString(r4);	 Catch:{ JSONException -> 0x019b }
                r4 = android.text.TextUtils.isEmpty(r4);	 Catch:{ JSONException -> 0x019b }
                if (r4 != 0) goto L_0x00ae;	 Catch:{ JSONException -> 0x019b }
            L_0x00a4:
                r4 = "medium";	 Catch:{ JSONException -> 0x019b }
                r2 = r2.getString(r4);	 Catch:{ JSONException -> 0x019b }
                r3.setDescriptionText(r2);	 Catch:{ JSONException -> 0x019b }
                goto L_0x00c3;	 Catch:{ JSONException -> 0x019b }
            L_0x00ae:
                r4 = "large";	 Catch:{ JSONException -> 0x019b }
                r4 = r2.optString(r4);	 Catch:{ JSONException -> 0x019b }
                r4 = android.text.TextUtils.isEmpty(r4);	 Catch:{ JSONException -> 0x019b }
                if (r4 != 0) goto L_0x00c3;	 Catch:{ JSONException -> 0x019b }
            L_0x00ba:
                r4 = "large";	 Catch:{ JSONException -> 0x019b }
                r2 = r2.getString(r4);	 Catch:{ JSONException -> 0x019b }
                r3.setDescriptionText(r2);	 Catch:{ JSONException -> 0x019b }
            L_0x00c3:
                r0.add(r3);	 Catch:{ JSONException -> 0x019b }
                goto L_0x017d;	 Catch:{ JSONException -> 0x019b }
            L_0x00c8:
                r3 = r1 / 2;	 Catch:{ JSONException -> 0x019b }
                r4 = r0.get(r3);	 Catch:{ JSONException -> 0x019b }
                r4 = (com.xumo.xumo.model.OnNowLive) r4;	 Catch:{ JSONException -> 0x019b }
                r5 = "id";	 Catch:{ JSONException -> 0x019b }
                r5 = r2.optString(r5);	 Catch:{ JSONException -> 0x019b }
                r4.setNextId(r5);	 Catch:{ JSONException -> 0x019b }
                r4 = r0.get(r3);	 Catch:{ JSONException -> 0x019b }
                r4 = (com.xumo.xumo.model.OnNowLive) r4;	 Catch:{ JSONException -> 0x019b }
                r5 = "title";	 Catch:{ JSONException -> 0x019b }
                r5 = r2.optString(r5);	 Catch:{ JSONException -> 0x019b }
                r4.setNextTitle(r5);	 Catch:{ JSONException -> 0x019b }
                r4 = r0.get(r3);	 Catch:{ JSONException -> 0x019b }
                r4 = (com.xumo.xumo.model.OnNowLive) r4;	 Catch:{ JSONException -> 0x019b }
                r5 = "start";	 Catch:{ JSONException -> 0x019b }
                r5 = r2.optLong(r5);	 Catch:{ JSONException -> 0x019b }
                r4.setNextStart(r5);	 Catch:{ JSONException -> 0x019b }
                r4 = r0.get(r3);	 Catch:{ JSONException -> 0x019b }
                r4 = (com.xumo.xumo.model.OnNowLive) r4;	 Catch:{ JSONException -> 0x019b }
                r5 = "end";	 Catch:{ JSONException -> 0x019b }
                r5 = r2.optLong(r5);	 Catch:{ JSONException -> 0x019b }
                r4.setNextEnd(r5);	 Catch:{ JSONException -> 0x019b }
                r4 = "descriptions";	 Catch:{ JSONException -> 0x019b }
                r2 = r2.optJSONObject(r4);	 Catch:{ JSONException -> 0x019b }
                if (r2 == 0) goto L_0x017d;	 Catch:{ JSONException -> 0x019b }
            L_0x010e:
                r4 = "tiny";	 Catch:{ JSONException -> 0x019b }
                r4 = r2.optString(r4);	 Catch:{ JSONException -> 0x019b }
                r4 = android.text.TextUtils.isEmpty(r4);	 Catch:{ JSONException -> 0x019b }
                if (r4 != 0) goto L_0x012a;	 Catch:{ JSONException -> 0x019b }
            L_0x011a:
                r3 = r0.get(r3);	 Catch:{ JSONException -> 0x019b }
                r3 = (com.xumo.xumo.model.OnNowLive) r3;	 Catch:{ JSONException -> 0x019b }
                r4 = "tiny";	 Catch:{ JSONException -> 0x019b }
                r2 = r2.getString(r4);	 Catch:{ JSONException -> 0x019b }
                r3.setNextDescriptionText(r2);	 Catch:{ JSONException -> 0x019b }
                goto L_0x017d;	 Catch:{ JSONException -> 0x019b }
            L_0x012a:
                r4 = "small";	 Catch:{ JSONException -> 0x019b }
                r4 = r2.optString(r4);	 Catch:{ JSONException -> 0x019b }
                r4 = android.text.TextUtils.isEmpty(r4);	 Catch:{ JSONException -> 0x019b }
                if (r4 != 0) goto L_0x0146;	 Catch:{ JSONException -> 0x019b }
            L_0x0136:
                r3 = r0.get(r3);	 Catch:{ JSONException -> 0x019b }
                r3 = (com.xumo.xumo.model.OnNowLive) r3;	 Catch:{ JSONException -> 0x019b }
                r4 = "small";	 Catch:{ JSONException -> 0x019b }
                r2 = r2.getString(r4);	 Catch:{ JSONException -> 0x019b }
                r3.setNextDescriptionText(r2);	 Catch:{ JSONException -> 0x019b }
                goto L_0x017d;	 Catch:{ JSONException -> 0x019b }
            L_0x0146:
                r4 = "medium";	 Catch:{ JSONException -> 0x019b }
                r4 = r2.optString(r4);	 Catch:{ JSONException -> 0x019b }
                r4 = android.text.TextUtils.isEmpty(r4);	 Catch:{ JSONException -> 0x019b }
                if (r4 != 0) goto L_0x0162;	 Catch:{ JSONException -> 0x019b }
            L_0x0152:
                r3 = r0.get(r3);	 Catch:{ JSONException -> 0x019b }
                r3 = (com.xumo.xumo.model.OnNowLive) r3;	 Catch:{ JSONException -> 0x019b }
                r4 = "medium";	 Catch:{ JSONException -> 0x019b }
                r2 = r2.getString(r4);	 Catch:{ JSONException -> 0x019b }
                r3.setNextDescriptionText(r2);	 Catch:{ JSONException -> 0x019b }
                goto L_0x017d;	 Catch:{ JSONException -> 0x019b }
            L_0x0162:
                r4 = "large";	 Catch:{ JSONException -> 0x019b }
                r4 = r2.optString(r4);	 Catch:{ JSONException -> 0x019b }
                r4 = android.text.TextUtils.isEmpty(r4);	 Catch:{ JSONException -> 0x019b }
                if (r4 != 0) goto L_0x017d;	 Catch:{ JSONException -> 0x019b }
            L_0x016e:
                r3 = r0.get(r3);	 Catch:{ JSONException -> 0x019b }
                r3 = (com.xumo.xumo.model.OnNowLive) r3;	 Catch:{ JSONException -> 0x019b }
                r4 = "large";	 Catch:{ JSONException -> 0x019b }
                r2 = r2.getString(r4);	 Catch:{ JSONException -> 0x019b }
                r3.setNextDescriptionText(r2);	 Catch:{ JSONException -> 0x019b }
            L_0x017d:
                r1 = r1 + 1;
                goto L_0x0020;
            L_0x0181:
                r8 = r11;
                r8.onCompletion(r0);
                r8 = new java.lang.StringBuilder;
                r8.<init>();
                r1 = "getOnNowLiveList onNowLives=";
                r8.append(r1);
                r8.append(r0);
                r8 = r8.toString();
                com.xumo.xumo.util.LogUtil.d(r8);
                return;
            L_0x019b:
                r8 = r11;
                r8.onError();
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.xumo.xumo.service.XumoWebService.22.onResponse(org.json.JSONObject):void");
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("getOnNowLiveList.onErrorResponse: ");
                stringBuilder.append(volleyError);
                LogUtil.d(stringBuilder.toString());
                listener.onError();
            }
        }));
    }

    private void getChannelsList(String str, String str2, final Listener listener) {
        if (str2 == null || str2.isEmpty()) {
            str2 = EnvironmentCompat.MEDIA_UNKNOWN;
        }
        addXumoRequestQueue(new JsonObjectRequest(0, String.format(getMdsPath(GET_CHANNELS_LIST_URL), new Object[]{str, str2}), null, new com.android.volley.Response.Listener<JSONObject>() {
            public void onResponse(org.json.JSONObject r9) {
                /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r8 = this;
                r0 = new java.lang.StringBuilder;
                r0.<init>();
                r1 = "getChannelsList.response=";
                r0.append(r1);
                r0.append(r9);
                r0 = r0.toString();
                com.xumo.xumo.util.LogUtil.d(r0);
                r0 = new java.util.ArrayList;
                r0.<init>();
                r1 = "channel";	 Catch:{ JSONException -> 0x011d }
                r9 = r9.getJSONObject(r1);	 Catch:{ JSONException -> 0x011d }
                r1 = "item";	 Catch:{ JSONException -> 0x011d }
                r9 = r9.getJSONArray(r1);	 Catch:{ JSONException -> 0x011d }
                r1 = 0;	 Catch:{ JSONException -> 0x011d }
                r2 = 0;	 Catch:{ JSONException -> 0x011d }
            L_0x0027:
                r3 = r9.length();	 Catch:{ JSONException -> 0x011d }
                if (r2 >= r3) goto L_0x0103;	 Catch:{ JSONException -> 0x011d }
            L_0x002d:
                r3 = r9.getJSONObject(r2);	 Catch:{ JSONException -> 0x011d }
                r4 = "guid";	 Catch:{ JSONException -> 0x011d }
                r4 = r3.getJSONObject(r4);	 Catch:{ JSONException -> 0x011d }
                r5 = new com.xumo.xumo.model.Channel;	 Catch:{ JSONException -> 0x011d }
                r6 = "value";	 Catch:{ JSONException -> 0x011d }
                r4 = r4.getString(r6);	 Catch:{ JSONException -> 0x011d }
                r5.<init>(r4);	 Catch:{ JSONException -> 0x011d }
                r4 = "title";	 Catch:{ JSONException -> 0x011d }
                r4 = r3.optString(r4);	 Catch:{ JSONException -> 0x011d }
                r5.setChannelTitle(r4);	 Catch:{ JSONException -> 0x011d }
                r4 = "description";	 Catch:{ JSONException -> 0x011d }
                r4 = r3.optString(r4);	 Catch:{ JSONException -> 0x011d }
                r5.setDescriptionText(r4);	 Catch:{ JSONException -> 0x011d }
                r4 = "number";	 Catch:{ JSONException -> 0x011d }
                r4 = r3.optInt(r4);	 Catch:{ JSONException -> 0x011d }
                r5.setChannelNumber(r4);	 Catch:{ JSONException -> 0x011d }
                r4 = "callsign";	 Catch:{ JSONException -> 0x011d }
                r4 = r3.optString(r4);	 Catch:{ JSONException -> 0x011d }
                r5.setCallSign(r4);	 Catch:{ JSONException -> 0x011d }
                r4 = "genre";	 Catch:{ JSONException -> 0x011d }
                r4 = r3.optJSONArray(r4);	 Catch:{ JSONException -> 0x011d }
                r6 = 1;	 Catch:{ JSONException -> 0x011d }
                if (r4 == 0) goto L_0x008b;	 Catch:{ JSONException -> 0x011d }
            L_0x006f:
                r7 = r4.length();	 Catch:{ JSONException -> 0x011d }
                if (r7 != r6) goto L_0x008b;	 Catch:{ JSONException -> 0x011d }
            L_0x0075:
                r4 = r4.getJSONObject(r1);	 Catch:{ JSONException -> 0x011d }
                r7 = "genreId";	 Catch:{ JSONException -> 0x011d }
                r7 = r4.optInt(r7);	 Catch:{ JSONException -> 0x011d }
                r5.setGenreId(r7);	 Catch:{ JSONException -> 0x011d }
                r7 = "value";	 Catch:{ JSONException -> 0x011d }
                r4 = r4.optString(r7);	 Catch:{ JSONException -> 0x011d }
                r5.setGenreName(r4);	 Catch:{ JSONException -> 0x011d }
            L_0x008b:
                r4 = "properties";	 Catch:{ JSONException -> 0x011d }
                r3 = r3.optJSONObject(r4);	 Catch:{ JSONException -> 0x011d }
                if (r3 == 0) goto L_0x00f0;	 Catch:{ JSONException -> 0x011d }
            L_0x0093:
                r4 = "is_live";	 Catch:{ JSONException -> 0x011d }
                r4 = r3.optString(r4);	 Catch:{ JSONException -> 0x011d }
                r7 = "true";	 Catch:{ JSONException -> 0x011d }
                r4 = r4.equals(r7);	 Catch:{ JSONException -> 0x011d }
                r5.setLive(r4);	 Catch:{ JSONException -> 0x011d }
                r4 = "has_schedule";	 Catch:{ JSONException -> 0x011d }
                r4 = r3.optString(r4);	 Catch:{ JSONException -> 0x011d }
                r7 = "true";	 Catch:{ JSONException -> 0x011d }
                r4 = r4.equals(r7);	 Catch:{ JSONException -> 0x011d }
                r5.setHasSchedule(r4);	 Catch:{ JSONException -> 0x011d }
                r4 = "has_vod";	 Catch:{ JSONException -> 0x011d }
                r4 = r3.optString(r4);	 Catch:{ JSONException -> 0x011d }
                r7 = "true";	 Catch:{ JSONException -> 0x011d }
                r4 = r4.equals(r7);	 Catch:{ JSONException -> 0x011d }
                r5.setHasVod(r4);	 Catch:{ JSONException -> 0x011d }
                r4 = "is_new";	 Catch:{ JSONException -> 0x011d }
                r4 = r3.optString(r4);	 Catch:{ JSONException -> 0x011d }
                r7 = "true";	 Catch:{ JSONException -> 0x011d }
                r4 = r4.equals(r7);	 Catch:{ JSONException -> 0x011d }
                r5.setNew(r4);	 Catch:{ JSONException -> 0x011d }
                r4 = com.xumo.xumo.service.XumoWebService.this;	 Catch:{ JSONException -> 0x011d }
                r4 = r4.xumoMovieChannel;	 Catch:{ JSONException -> 0x011d }
                if (r4 != 0) goto L_0x00f0;	 Catch:{ JSONException -> 0x011d }
            L_0x00d7:
                r4 = "is_xumo_movie_channel";	 Catch:{ JSONException -> 0x011d }
                r3 = r3.optString(r4);	 Catch:{ JSONException -> 0x011d }
                r4 = "true";	 Catch:{ JSONException -> 0x011d }
                r3 = r3.equals(r4);	 Catch:{ JSONException -> 0x011d }
                if (r3 == 0) goto L_0x00f0;	 Catch:{ JSONException -> 0x011d }
            L_0x00e5:
                r3 = r5.getChannelId();	 Catch:{ JSONException -> 0x011d }
                com.xumo.xumo.fragmenttv.XumoPlayerBaseFragment.MOVIES_CHANNEL_ID = r3;	 Catch:{ JSONException -> 0x011d }
                r3 = com.xumo.xumo.service.XumoWebService.this;	 Catch:{ JSONException -> 0x011d }
                r3.xumoMovieChannel = r6;	 Catch:{ JSONException -> 0x011d }
            L_0x00f0:
                r3 = com.xumo.xumo.fragmenttv.XumoPlayerBaseFragment.MOVIES_CHANNEL_ID;	 Catch:{ JSONException -> 0x011d }
                r4 = r5.getChannelId();	 Catch:{ JSONException -> 0x011d }
                r3 = r3.equals(r4);	 Catch:{ JSONException -> 0x011d }
                if (r3 != 0) goto L_0x00ff;	 Catch:{ JSONException -> 0x011d }
            L_0x00fc:
                r0.add(r5);	 Catch:{ JSONException -> 0x011d }
            L_0x00ff:
                r2 = r2 + 1;
                goto L_0x0027;
            L_0x0103:
                r9 = r11;
                r9.onCompletion(r0);
                r9 = new java.lang.StringBuilder;
                r9.<init>();
                r1 = "getChannelsList channels=";
                r9.append(r1);
                r9.append(r0);
                r9 = r9.toString();
                com.xumo.xumo.util.LogUtil.d(r9);
                return;
            L_0x011d:
                r9 = r11;
                r9.onError();
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.xumo.xumo.service.XumoWebService.24.onResponse(org.json.JSONObject):void");
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("getChannelsList.onErrorResponse: ");
                stringBuilder.append(volleyError);
                LogUtil.d(stringBuilder.toString());
                listener.onError();
            }
        }));
    }

    public void getViewbooster(final Listener listener) {
        addXumoRequestQueue(new JsonObjectRequest(0, String.format(getAppPath(urlGetViewBooster), new Object[0]), null, new com.android.volley.Response.Listener<JSONObject>() {
            public void onResponse(org.json.JSONObject r3) {
                /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r2 = this;
                r0 = new java.lang.StringBuilder;
                r0.<init>();
                r1 = "getViewbooster.response=";
                r0.append(r1);
                r0.append(r3);
                r0 = r0.toString();
                com.xumo.xumo.util.LogUtil.d(r0);
                r0 = new com.xumo.xumo.model.Viewbooster;
                r0.<init>();
                r1 = "channelId";	 Catch:{ JSONException -> 0x0045 }
                r1 = r3.getString(r1);	 Catch:{ JSONException -> 0x0045 }
                r0.setChannelId(r1);	 Catch:{ JSONException -> 0x0045 }
                r1 = "assetId";	 Catch:{ JSONException -> 0x0045 }
                r3 = r3.getString(r1);	 Catch:{ JSONException -> 0x0045 }
                r0.setAssetId(r3);	 Catch:{ JSONException -> 0x0045 }
                r3 = r9;
                r3.onCompletion(r0);
                r3 = new java.lang.StringBuilder;
                r3.<init>();
                r1 = "getViewbooster viewbooster=";
                r3.append(r1);
                r3.append(r0);
                r3 = r3.toString();
                com.xumo.xumo.util.LogUtil.d(r3);
                return;
            L_0x0045:
                r3 = r9;
                r3.onError();
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.xumo.xumo.service.XumoWebService.26.onResponse(org.json.JSONObject):void");
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("getViewbooster.onErrorResponse: ");
                stringBuilder.append(volleyError);
                LogUtil.d(stringBuilder.toString());
                listener.onError();
            }
        }));
    }

    public void getProviderGenreMapping(final int i, final Listener listener) {
        addXumoRequestQueue(new JsonObjectRequest(0, "https://vizio-app.xumo.com/config/provider-genre-mapping-data.json", null, new com.android.volley.Response.Listener<JSONObject>() {
            public void onResponse(org.json.JSONObject r3) {
                /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r2 = this;
                r0 = new java.lang.StringBuilder;
                r0.<init>();
                r1 = "getViewbooster.response=";
                r0.append(r1);
                r0.append(r3);
                r0 = r0.toString();
                com.xumo.xumo.util.LogUtil.d(r0);
                r0 = new java.lang.StringBuilder;	 Catch:{ JSONException -> 0x0031 }
                r0.<init>();	 Catch:{ JSONException -> 0x0031 }
                r1 = r8;	 Catch:{ JSONException -> 0x0031 }
                r0.append(r1);	 Catch:{ JSONException -> 0x0031 }
                r1 = "";	 Catch:{ JSONException -> 0x0031 }
                r0.append(r1);	 Catch:{ JSONException -> 0x0031 }
                r0 = r0.toString();	 Catch:{ JSONException -> 0x0031 }
                r3 = r3.getString(r0);	 Catch:{ JSONException -> 0x0031 }
                r0 = r9;
                r0.onCompletion(r3);
                return;
            L_0x0031:
                r3 = r9;
                r3.onError();
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.xumo.xumo.service.XumoWebService.28.onResponse(org.json.JSONObject):void");
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("getViewbooster.onErrorResponse: ");
                stringBuilder.append(volleyError);
                LogUtil.d(stringBuilder.toString());
                listener.onError();
            }
        }));
    }
}
