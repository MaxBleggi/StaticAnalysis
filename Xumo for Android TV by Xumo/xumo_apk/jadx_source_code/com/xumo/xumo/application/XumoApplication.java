package com.xumo.xumo.application;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import androidx.collection.LruCache;
import androidx.core.app.NotificationManagerCompat;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.google.android.exoplayer2.offline.DownloadAction.Deserializer;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.offline.DownloaderConstructorHelper;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource.Factory;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.NoOpCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.Util;
import com.xumo.xumo.model.Viewbooster;
import com.xumo.xumo.repository.UserPreferences;
import com.xumo.xumo.service.XumoWebService;
import com.xumo.xumo.service.XumoWebService.Listener;
import com.xumo.xumo.util.BeaconUtil;
import com.xumo.xumo.util.BeaconUtil.ImpressionBeaconEvent;
import com.xumo.xumo.util.DownloadTracker;
import com.xumo.xumo.util.LogUtil;
import com.xumo.xumo.util.XumoUtil;
import io.fabric.sdk.android.Fabric;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Date;

public class XumoApplication extends MultiDexApplication implements ActivityLifecycleCallbacks {
    private static final int APP_STATUS_BACKGROUND = 1;
    private static final int APP_STATUS_FOREGROUND = 3;
    public static final int APP_STATUS_RETURNED_TO_FOREGROUND = 2;
    private static final String DOWNLOAD_ACTION_FILE = "actions";
    private static final String DOWNLOAD_CONTENT_DIRECTORY = "downloads";
    private static final String DOWNLOAD_TRACKER_ACTION_FILE = "tracked_actions";
    private static final int MAX_SIMULTANEOUS_DOWNLOADS = 2;
    private Cache downloadCache;
    private File downloadDirectory;
    private DownloadManager downloadManager;
    private DownloadTracker downloadTracker;
    private int mAppStatus = 1;
    private ImageLoader mImageLoader;
    private XumoLruCache mLruCache;
    private RequestQueue mRequestQueue;
    private int mRunning = 0;
    private OnSharedPreferenceChangeListener mWaitingForDeviceId;

    private class XumoLruCache implements ImageCache {
        private LruCache<String, Bitmap> mMemoryCache;

        XumoLruCache() {
            this.mMemoryCache = new LruCache<String, Bitmap>(((int) (Runtime.getRuntime().maxMemory() / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID)) / 8, XumoApplication.this) {
                protected int sizeOf(String str, Bitmap bitmap) {
                    return bitmap.getByteCount() / 1024;
                }
            };
        }

        public Bitmap getBitmap(String str) {
            return (Bitmap) this.mMemoryCache.get(str);
        }

        public void putBitmap(String str, Bitmap bitmap) {
            Bitmap bitmap2 = (Bitmap) this.mMemoryCache.put(str, bitmap);
            if (bitmap2 != null && bitmap2.isRecycled() == null) {
                bitmap2.recycle();
            }
        }

        public void destroy() {
            if (this.mMemoryCache != null) {
                this.mMemoryCache.evictAll();
            }
        }
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public void onActivityDestroyed(Activity activity) {
    }

    public void onActivityPaused(Activity activity) {
    }

    public void onActivityResumed(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public int getmAppStatus() {
        return this.mAppStatus;
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    public void onCreate() {
        super.onCreate();
        replaceFont();
        XumoContext.onCreateApplicationContext(getApplicationContext());
        this.mRequestQueue = Volley.newRequestQueue(this);
        this.mLruCache = new XumoLruCache();
        this.mImageLoader = new ImageLoader(this.mRequestQueue, this.mLruCache);
        UserPreferences.appLaunchTimeStamp = new Date();
        sendAppStartBeacon();
        sendNotificationStateBeacon();
        registerActivityLifecycleCallbacks(this);
        Fabric.with(this, new Crashlytics());
    }

    public void onTerminate() {
        this.mLruCache.destroy();
        super.onTerminate();
    }

    public void onActivityStarted(Activity activity) {
        activity = this.mRunning + 1;
        this.mRunning = activity;
        if (activity == 1) {
            this.mAppStatus = 2;
        } else if (this.mRunning > 1) {
            this.mAppStatus = 3;
        }
    }

    public void onActivityStopped(Activity activity) {
        activity = this.mRunning - 1;
        this.mRunning = activity;
        if (activity == null) {
            this.mAppStatus = 1;
        }
    }

    public RequestQueue getRequestQueue() {
        return this.mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return this.mImageLoader;
    }

    private void sendAppStartBeacon() {
        if (TextUtils.isEmpty(UserPreferences.getInstance().getDeviceId())) {
            getViewBooster();
            UserPreferences.getInstance().setAppLastUsedDate(System.currentTimeMillis());
            BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppReport, null, new String[]{"deviceID request", "reason=empty"});
            LogUtil.d("Beacon", "Maybe, app launch is the first time. Pending sendBeacon[AppStart] until DeviceAuthorization is taken");
            this.mWaitingForDeviceId = new OnSharedPreferenceChangeListener() {
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
                    if (TextUtils.equals(str, UserPreferences.DEVICE_ID) != null) {
                        LogUtil.d("Beacon", "Got DeviceAuthorization, Beacon[AppStart] fired.");
                        BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppStart, null, null);
                        BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppReport, null, new String[]{"deviceID received"});
                        UserPreferences.getInstance().unregisterOnSharedPreferenceChangeListener(XumoApplication.this.mWaitingForDeviceId);
                        XumoApplication.this.mWaitingForDeviceId = null;
                    }
                }
            };
            UserPreferences.getInstance().registerOnSharedPreferenceChangeListener(this.mWaitingForDeviceId);
            return;
        }
        BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppStart, null, null);
        UserPreferences.getInstance().setViewBoost(false);
        if (checkDateLastUsed()) {
            getViewBooster();
        }
        UserPreferences.getInstance().setAppLastUsedDate(System.currentTimeMillis());
    }

    private boolean checkDateLastUsed() {
        long j = UserPreferences.getInstance().getUseStagingViewBooster() ? 3600 : 691200;
        long appLastUsedDate = UserPreferences.getInstance().getAppLastUsedDate();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("appLastUsedDate = ");
        stringBuilder.append(appLastUsedDate);
        LogUtil.d(stringBuilder.toString());
        if (appLastUsedDate >= 0) {
            appLastUsedDate = Math.abs(XumoUtil.getTimeDiffToNowInSecs(appLastUsedDate));
            stringBuilder = new StringBuilder();
            stringBuilder.append("timeDiffInSecs = ");
            stringBuilder.append(appLastUsedDate);
            LogUtil.d(stringBuilder.toString());
            if (appLastUsedDate >= j) {
                return true;
            }
        }
        return false;
    }

    private void getViewBooster() {
        XumoWebService.getInstance().getViewbooster(new Listener() {
            public void onCompletion(Object obj) {
                Viewbooster viewbooster = (Viewbooster) obj;
                if (!(viewbooster == null || TextUtils.isEmpty(viewbooster.getChannelId()))) {
                    if (!TextUtils.isEmpty(viewbooster.getAssetId())) {
                        UserPreferences.getInstance().setViewBoost(true);
                        UserPreferences.getInstance().setViewBoostChannelId(viewbooster.getChannelId());
                        UserPreferences.getInstance().setViewBoostAssetId(viewbooster.getAssetId());
                        return;
                    }
                }
                UserPreferences.getInstance().setViewBoost(false);
            }

            public void onError() {
                UserPreferences.getInstance().setViewBoost(false);
            }
        });
    }

    private void sendNotificationStateBeacon() {
        Boolean valueOf = Boolean.valueOf(NotificationManagerCompat.from(getApplicationContext()).areNotificationsEnabled());
        String[] strArr = new String[1];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("notification=");
        stringBuilder.append(valueOf.toString());
        strArr[0] = stringBuilder.toString();
        BeaconUtil.sendBeaconImpression(ImpressionBeaconEvent.AppReport, null, strArr);
    }

    public boolean isRunning() {
        return this.mRunning != 0;
    }

    private void replaceFont() {
        Typeface createFromAsset = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Regular.ttf");
        try {
            Field declaredField = Typeface.class.getDeclaredField("MONOSPACE");
            declaredField.setAccessible(true);
            declaredField.set(null, createFromAsset);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
    }

    public Factory buildHttpDataSourceFactory() {
        return new DefaultHttpDataSourceFactory(Util.getUserAgent(this, "ExoPlayerDemo"));
    }

    public DataSource.Factory buildDataSourceFactory() {
        return buildReadOnlyCacheDataSource(new DefaultDataSourceFactory((Context) this, buildHttpDataSourceFactory()), getDownloadCache());
    }

    private static CacheDataSourceFactory buildReadOnlyCacheDataSource(DefaultDataSourceFactory defaultDataSourceFactory, Cache cache) {
        return new CacheDataSourceFactory(cache, defaultDataSourceFactory, new FileDataSourceFactory(), null, 2, null);
    }

    private synchronized Cache getDownloadCache() {
        if (this.downloadCache == null) {
            this.downloadCache = new SimpleCache(new File(getDownloadDirectory(), DOWNLOAD_CONTENT_DIRECTORY), new NoOpCacheEvictor());
        }
        return this.downloadCache;
    }

    private File getDownloadDirectory() {
        if (this.downloadDirectory == null) {
            this.downloadDirectory = getExternalFilesDir(null);
            if (this.downloadDirectory == null) {
                this.downloadDirectory = getFilesDir();
            }
        }
        return this.downloadDirectory;
    }

    public DownloadTracker getDownloadTracker() {
        initDownloadManager();
        return this.downloadTracker;
    }

    private synchronized void initDownloadManager() {
        if (this.downloadManager == null) {
            this.downloadManager = new DownloadManager(new DownloaderConstructorHelper(getDownloadCache(), buildHttpDataSourceFactory()), 2, 5, new File(getDownloadDirectory(), DOWNLOAD_ACTION_FILE), new Deserializer[0]);
            this.downloadTracker = new DownloadTracker(this, buildDataSourceFactory(), new File(getDownloadDirectory(), DOWNLOAD_TRACKER_ACTION_FILE), new Deserializer[0]);
            this.downloadManager.addListener(this.downloadTracker);
        }
    }

    public DownloadManager getDownloadManager() {
        initDownloadManager();
        return this.downloadManager;
    }
}
