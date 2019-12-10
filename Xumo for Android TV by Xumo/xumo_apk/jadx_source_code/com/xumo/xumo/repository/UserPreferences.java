package com.xumo.xumo.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.text.TextUtils;
import com.xumo.xumo.application.XumoContext;
import com.xumo.xumo.model.Session;
import com.xumo.xumo.service.XumoWebService;
import com.xumo.xumo.util.BeaconUtil.PlayReason;
import com.xumo.xumo.util.LogUtil;
import com.xumo.xumo.util.XumoUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;

public class UserPreferences {
    private static final String APP_LAST_USED_DATE = "appLastUsedDate";
    private static final String AUTO_PLAY_KEY = "shouldNotAutoPlay";
    public static final String BACKGROUND_COLOR_KEY = "backgroundColor";
    public static final String BACKGROUND_OPACITY_KEY = "backgroundOpacity";
    public static final String DEVICE_ID = "device-id-xumo2";
    public static final String EDGE_COLOR_KEY = "edgeColor";
    public static final String EDGE_TYPE_KEY = "edgeType";
    private static final String FAVORITES_KEY = "favoriteChannelIds";
    private static final String FAVORITES_PREFERENCES_FILE = "xumo.favorites";
    private static final String FCM_PREFERENCES_FILE = "xumo.fcm";
    private static final String FCM_TOKEN_KEY = "FCM_Token_Key";
    private static final String LAST_PLAYED_CHANNEL_ID = "TheLastPlayedChannelId";
    private static final String PREFERENCES_FILE = "xumo.main";
    private static final String PUBLISHER_PROVIDED_ID = "GoogleIMA_PublisherProvidedID";
    private static final String RECENT_SEARCHES = "recentSearches";
    private static final String SUBTITLE_SWITCH_KEY = "subtitleSwitch";
    public static final String TEXT_COLOR_KEY = "textColor";
    public static final String TEXT_OPACITY_KEY = "textOpacity";
    public static final String TEXT_SIZE_KEY = "textSize";
    public static final String TEXT_STYLE_KEY = "textStyle";
    private static final String USE_DEBUG_LOGS = "useDebugLogs";
    private static final String USE_STAGING_PLAYERS = "useStagingPlayers";
    private static final String USE_STAGING_VIEW_BOOSTER = "useStagingViewBooster";
    private static final String USE_TEST_CHANNELS = "useTestChannels";
    public static Date appLaunchTimeStamp;
    public static boolean error = false;
    private static String mReceiverDeviceId;
    private static String mReceiverSessionId;
    private static UserPreferences sInstance;
    public static Session session = new Session();
    private Timer mBeaconTimer;
    private BeaconTimerListener mBeaconTimerListener;
    private BeaconTimerTask mBeaconTimerTask;
    private String mChannelPlayId = "";
    private final SharedPreferences mFavoritesSharedPreferences;
    private final SharedPreferences mFcmSharedPreferences;
    private String mPageId = PageId.GuideScreen.toString();
    private String mPageViewId = XumoUtil.generateRandomId();
    private final SharedPreferences mSharedPreferences;
    private boolean viewBoost = true;
    private String viewBoostAssetId = "";
    private String viewBoostChannelId = "";

    public interface BeaconTimerListener {
        void termination();
    }

    private class BeaconTimerTask extends TimerTask {
        private BeaconTimerTask() {
        }

        public void run() {
            if (UserPreferences.this.mBeaconTimerListener != null) {
                UserPreferences.this.mBeaconTimerListener.termination();
            }
        }
    }

    private enum PageId {
        brandScreen,
        channelsScreen,
        settingsScreen,
        GuideScreen,
        MoviesScreen
    }

    public boolean getViewBoost() {
        return this.viewBoost;
    }

    public void setViewBoost(boolean z) {
        this.viewBoost = z;
    }

    public String getViewBoostAssetId() {
        return this.viewBoostAssetId;
    }

    public void setViewBoostAssetId(String str) {
        this.viewBoostAssetId = str;
    }

    public String getViewBoostChannelId() {
        return this.viewBoostChannelId;
    }

    public void setViewBoostChannelId(String str) {
        this.viewBoostChannelId = str;
    }

    private UserPreferences() {
        Context applicationContext = XumoContext.getInstance().getApplicationContext();
        this.mSharedPreferences = applicationContext.getSharedPreferences(PREFERENCES_FILE, 0);
        this.mFcmSharedPreferences = applicationContext.getSharedPreferences(FCM_PREFERENCES_FILE, 0);
        this.mFavoritesSharedPreferences = applicationContext.getSharedPreferences(FAVORITES_PREFERENCES_FILE, 0);
        updatePreferencesFromXumo1ToXumo2();
    }

    public static synchronized UserPreferences getInstance() {
        UserPreferences userPreferences;
        synchronized (UserPreferences.class) {
            if (sInstance == null) {
                sInstance = new UserPreferences();
            }
            userPreferences = sInstance;
        }
        return userPreferences;
    }

    private void updatePreferencesFromXumo1ToXumo2() {
        String string = this.mSharedPreferences.getString("device-id", null);
        if (string != null) {
            String replaceFirst = string.replaceFirst(XumoWebService.DEVICE_ID_AUTH_PREFIX, "XumoDeviceId ");
            setDeviceId(replaceFirst);
            this.mSharedPreferences.edit().remove("device-id").apply();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Maybe first launch from application update, modified the device ID for compatibility [");
            stringBuilder.append(string);
            stringBuilder.append("] to [");
            stringBuilder.append(replaceFirst);
            stringBuilder.append("]");
            LogUtil.d(stringBuilder.toString());
        }
    }

    public void clear() {
        this.mSharedPreferences.edit().clear().apply();
        this.mFavoritesSharedPreferences.edit().clear().apply();
    }

    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        this.mSharedPreferences.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        this.mSharedPreferences.unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    public long getTimeInSecondsSinceAppLaunchIfAvailable() {
        return appLaunchTimeStamp != null ? (new Date().getTime() - appLaunchTimeStamp.getTime()) / 1000 : -1;
    }

    public void startTimerForBeaconIfNecessaryWithTarget(int i, BeaconTimerListener beaconTimerListener) {
        if (this.mBeaconTimer == null) {
            LogUtil.d("Beacon", "keepAlive Start");
            this.mBeaconTimer = new Timer();
            this.mBeaconTimerTask = new BeaconTimerTask();
            this.mBeaconTimerListener = beaconTimerListener;
            long j = (long) (i * 1000);
            this.mBeaconTimer.schedule(this.mBeaconTimerTask, j, j);
        }
    }

    public void invalidateTimerForBeacon() {
        if (this.mBeaconTimer != null) {
            LogUtil.d("Beacon", "keepAlive Stop");
            this.mBeaconTimer.cancel();
            this.mBeaconTimer = null;
            this.mBeaconTimerTask = null;
            this.mBeaconTimerListener = null;
        }
    }

    public void setDeviceId(String str) {
        this.mSharedPreferences.edit().putString(DEVICE_ID, str).apply();
    }

    public String getDeviceId() {
        return this.mSharedPreferences.getString(DEVICE_ID, null);
    }

    public void removeDeviceId() {
        this.mSharedPreferences.edit().remove(DEVICE_ID).apply();
    }

    public void setSubtitleSwitch(boolean z) {
        this.mSharedPreferences.edit().putBoolean(SUBTITLE_SWITCH_KEY, z).apply();
    }

    public boolean getSubtitleSwitch() {
        return this.mSharedPreferences.getBoolean(SUBTITLE_SWITCH_KEY, false);
    }

    public void setTextColor(int i) {
        this.mSharedPreferences.edit().putInt(TEXT_COLOR_KEY, i).apply();
    }

    public int getTextColor() {
        return this.mSharedPreferences.getInt(TEXT_COLOR_KEY, 1);
    }

    public void setTextOpacity(int i) {
        this.mSharedPreferences.edit().putInt(TEXT_OPACITY_KEY, i).apply();
    }

    public int getTextOpacity() {
        return this.mSharedPreferences.getInt(TEXT_OPACITY_KEY, 4);
    }

    public void setTextStyle(int i) {
        this.mSharedPreferences.edit().putInt(TEXT_STYLE_KEY, i).apply();
    }

    public int getTextStyle() {
        return this.mSharedPreferences.getInt(TEXT_STYLE_KEY, 1);
    }

    public void setTextSize(int i) {
        this.mSharedPreferences.edit().putInt(TEXT_SIZE_KEY, i).apply();
    }

    public int getTextSize() {
        return this.mSharedPreferences.getInt(TEXT_SIZE_KEY, 2);
    }

    public void setEdgeType(int i) {
        this.mSharedPreferences.edit().putInt(EDGE_TYPE_KEY, i).apply();
    }

    public int getEdgeType() {
        return this.mSharedPreferences.getInt(EDGE_TYPE_KEY, 1);
    }

    public void setEdgeColor(int i) {
        this.mSharedPreferences.edit().putInt(EDGE_COLOR_KEY, i).apply();
    }

    public int getEdgeColor() {
        return this.mSharedPreferences.getInt(EDGE_COLOR_KEY, 6);
    }

    public void setBackgroundColor(int i) {
        this.mSharedPreferences.edit().putInt("backgroundColor", i).apply();
    }

    public int getBackgroundColor() {
        return this.mSharedPreferences.getInt("backgroundColor", 2);
    }

    public void setBackgroundOpacity(int i) {
        this.mSharedPreferences.edit().putInt(BACKGROUND_OPACITY_KEY, i).apply();
    }

    public int getBackgroundOpacity() {
        return this.mSharedPreferences.getInt(BACKGROUND_OPACITY_KEY, 4);
    }

    public void removeCaptionsAppearance() {
        this.mSharedPreferences.edit().remove(TEXT_COLOR_KEY).apply();
        this.mSharedPreferences.edit().remove(TEXT_OPACITY_KEY).apply();
        this.mSharedPreferences.edit().remove(TEXT_STYLE_KEY).apply();
        this.mSharedPreferences.edit().remove(TEXT_SIZE_KEY).apply();
        this.mSharedPreferences.edit().remove(EDGE_TYPE_KEY).apply();
        this.mSharedPreferences.edit().remove(EDGE_COLOR_KEY).apply();
        this.mSharedPreferences.edit().remove("backgroundColor").apply();
        this.mSharedPreferences.edit().remove(BACKGROUND_OPACITY_KEY).apply();
    }

    public boolean getUseDebugLogs() {
        return this.mSharedPreferences.getBoolean(USE_DEBUG_LOGS, false);
    }

    public void setUseDebugLogs(boolean z) {
        this.mSharedPreferences.edit().putBoolean(USE_DEBUG_LOGS, z).apply();
    }

    public boolean getUseTestChannels() {
        return this.mSharedPreferences.getBoolean(USE_TEST_CHANNELS, false);
    }

    public void setUseTestChannels(boolean z) {
        this.mSharedPreferences.edit().putBoolean(USE_TEST_CHANNELS, z).apply();
    }

    public boolean getUseStagingPlayers() {
        return this.mSharedPreferences.getBoolean(USE_STAGING_PLAYERS, false);
    }

    public void setUseStagingPlayers(boolean z) {
        this.mSharedPreferences.edit().putBoolean(USE_STAGING_PLAYERS, z).apply();
    }

    public boolean getUseStagingViewBooster() {
        return this.mSharedPreferences.getBoolean(USE_STAGING_VIEW_BOOSTER, false);
    }

    public void setUseStagingViewBooster(boolean z) {
        this.mSharedPreferences.edit().putBoolean(USE_STAGING_VIEW_BOOSTER, z).apply();
    }

    public long getAppLastUsedDate() {
        return this.mSharedPreferences.getLong(APP_LAST_USED_DATE, 0);
    }

    public void setAppLastUsedDate(long j) {
        this.mSharedPreferences.edit().putLong(APP_LAST_USED_DATE, j).apply();
    }

    public void disableAllDebugModes() {
        setUseDebugLogs(false);
        setUseTestChannels(false);
        setUseStagingPlayers(false);
        setUseStagingViewBooster(false);
    }

    public void setLastPlayedChannelId(String str) {
        this.mSharedPreferences.edit().putString(LAST_PLAYED_CHANNEL_ID, str).apply();
    }

    public String getLastPlayedChannelId() {
        return this.mSharedPreferences.getString(LAST_PLAYED_CHANNEL_ID, "");
    }

    public void removeLastPlayedChannelId() {
        this.mSharedPreferences.edit().remove(LAST_PLAYED_CHANNEL_ID).apply();
    }

    public void setRecentSearches(ArrayList<String> arrayList) {
        if (arrayList != null) {
            if (!arrayList.isEmpty()) {
                JSONArray jSONArray = new JSONArray();
                arrayList = arrayList.iterator();
                while (arrayList.hasNext()) {
                    jSONArray.put((String) arrayList.next());
                }
                this.mSharedPreferences.edit().putString(RECENT_SEARCHES, jSONArray.toString()).apply();
                return;
            }
        }
        this.mSharedPreferences.edit().remove(RECENT_SEARCHES).apply();
    }

    public ArrayList<String> getRecentSearches() {
        ArrayList<String> arrayList = null;
        String string = this.mSharedPreferences.getString(RECENT_SEARCHES, null);
        if (string == null) {
            return null;
        }
        try {
            JSONArray jSONArray = new JSONArray(string);
            if (jSONArray.length() > 0) {
                ArrayList<String> arrayList2 = new ArrayList();
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList2.add(jSONArray.getString(i));
                }
                arrayList = arrayList2;
            }
        } catch (JSONException e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Preferences was damaged!? ");
            stringBuilder.append(e);
            LogUtil.e(stringBuilder.toString());
            this.mSharedPreferences.edit().remove(RECENT_SEARCHES).apply();
        }
        return arrayList;
    }

    private boolean shouldNotAutoPlay() {
        return this.mSharedPreferences.getBoolean(AUTO_PLAY_KEY, true);
    }

    public void setReceiverId(String str, String str2) {
        mReceiverDeviceId = str;
        mReceiverSessionId = str2;
    }

    public String getReceiverDeviceId() {
        return mReceiverDeviceId;
    }

    public String getReceiverSessionId() {
        return mReceiverSessionId;
    }

    public String getPublisherProvidedId() {
        String string = this.mSharedPreferences.getString(PUBLISHER_PROVIDED_ID, null);
        return TextUtils.isEmpty(string) ? UUID.randomUUID().toString() : string;
    }

    public String refreshPlayId() {
        return XumoUtil.generateRandomId();
    }

    public PlayReason getPlayReason() {
        if (shouldNotAutoPlay()) {
            return PlayReason.UserTriggered;
        }
        return PlayReason.AutoPlay;
    }

    public String getPageId() {
        return this.mPageId;
    }

    public void setToBrandScreen() {
        if (!this.mPageId.equals(PageId.brandScreen.toString())) {
            refreshPageViewId();
        }
        this.mPageId = PageId.brandScreen.toString();
    }

    public void setToChannelsScreen() {
        if (!this.mPageId.equals(PageId.channelsScreen.toString())) {
            refreshPageViewId();
        }
        this.mPageId = PageId.channelsScreen.toString();
    }

    public void setToSettingsScreen() {
        if (!this.mPageId.equals(PageId.settingsScreen.toString())) {
            refreshPageViewId();
        }
        this.mPageId = PageId.settingsScreen.toString();
    }

    public void setToGuideScreen() {
        if (!this.mPageId.equals(PageId.GuideScreen.toString())) {
            refreshPageViewId();
        }
        this.mPageId = PageId.GuideScreen.toString();
    }

    public void setToMoviesScreen() {
        if (!this.mPageId.equals(PageId.MoviesScreen.toString())) {
            refreshPageViewId();
        }
        this.mPageId = PageId.MoviesScreen.toString();
    }

    public String getPageViewId() {
        return this.mPageViewId;
    }

    private void refreshPageViewId() {
        this.mPageViewId = XumoUtil.generateRandomId();
    }

    public String getChannelPlayId() {
        return this.mChannelPlayId;
    }

    public void refreshChannelPlayId(String str) {
        this.mChannelPlayId = XumoUtil.generateRandomId();
    }

    public void setFcmToken(String str) {
        this.mFcmSharedPreferences.edit().putString(FCM_TOKEN_KEY, str).apply();
    }

    public String getFcmToken() {
        return this.mFcmSharedPreferences.getString(FCM_TOKEN_KEY, "");
    }

    public void addChannelToFavorites(String str) {
        if (str != null) {
            if (!str.isEmpty()) {
                String string = this.mFavoritesSharedPreferences.getString(FAVORITES_KEY, "");
                if (!string.isEmpty()) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(string);
                    stringBuilder.append(",");
                    stringBuilder.append(str);
                    str = stringBuilder.toString();
                }
                this.mFavoritesSharedPreferences.edit().putString(FAVORITES_KEY, str).apply();
            }
        }
    }

    public void removeChannelFromFavorites(String str) {
        if (str != null) {
            if (!str.isEmpty()) {
                String string = this.mFavoritesSharedPreferences.getString(FAVORITES_KEY, "");
                StringBuffer stringBuffer = new StringBuffer();
                for (String str2 : string.split(",")) {
                    if (!str2.equals(str)) {
                        if (stringBuffer.length() > 0) {
                            stringBuffer.append(",");
                        }
                        stringBuffer.append(str2);
                    }
                }
                this.mFavoritesSharedPreferences.edit().putString(FAVORITES_KEY, stringBuffer.toString()).apply();
            }
        }
    }

    public boolean isFavorited(String str) {
        if (str != null) {
            if (!str.isEmpty()) {
                for (String equals : this.mFavoritesSharedPreferences.getString(FAVORITES_KEY, "").split(",")) {
                    if (equals.equals(str)) {
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    public List<String> getFavoriteChannelIds() {
        String string = this.mFavoritesSharedPreferences.getString(FAVORITES_KEY, "");
        if (string.isEmpty()) {
            return null;
        }
        List<String> asList = Arrays.asList(string.split(","));
        Collections.reverse(asList);
        return asList;
    }
}
