package com.google.android.libraries.cast.companionlibrary.cast;

import android.app.Service;
import androidx.annotation.NonNull;
import androidx.mediarouter.app.MediaRouteDialogFactory;
import com.google.android.gms.cast.LaunchOptions;
import com.google.android.libraries.cast.companionlibrary.utils.Utils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CastConfiguration {
    public static final int CCL_DEFAULT_FORWARD_STEP_S = 30;
    public static final int FEATURE_AUTO_RECONNECT = 32;
    public static final int FEATURE_CAPTIONS_PREFERENCE = 16;
    public static final int FEATURE_DEBUGGING = 1;
    public static final int FEATURE_LOCKSCREEN = 2;
    public static final int FEATURE_NOTIFICATION = 4;
    public static final int FEATURE_WIFI_RECONNECT = 8;
    public static final int NEXT_PREV_VISIBILITY_POLICY_ALWAYS = 3;
    public static final int NEXT_PREV_VISIBILITY_POLICY_DISABLED = 2;
    public static final int NEXT_PREV_VISIBILITY_POLICY_HIDDEN = 1;
    public static final int NEXT_PREV_VISIBLILITY_HIDEN = 4;
    public static final int NOTIFICATION_ACTION_DISCONNECT = 4;
    public static final int NOTIFICATION_ACTION_FORWARD = 6;
    public static final int NOTIFICATION_ACTION_PLAY_PAUSE = 1;
    public static final int NOTIFICATION_ACTION_REWIND = 5;
    public static final int NOTIFICATION_ACTION_SKIP_NEXT = 2;
    public static final int NOTIFICATION_ACTION_SKIP_PREVIOUS = 3;
    private static final int NOTIFICATION_MAX_COMPACT_ACTIONS = 3;
    private static final int NOTIFICATION_MAX_EXPANDED_ACTIONS = 5;
    private String mApplicationId;
    private int mCapabilities;
    private boolean mCastControllerImmersive;
    private Class<? extends Service> mCustomNotificationService;
    private final boolean mDisableLaunchOnConnect;
    private int mForwardStep;
    private LaunchOptions mLaunchOptions;
    private MediaRouteDialogFactory mMediaRouteDialogFactory;
    private List<String> mNamespaces;
    private int mNextPrevVisibilityPolicy;
    private List<Integer> mNotificationActions;
    private List<Integer> mNotificationCompactActions;
    private Class<?> mTargetActivity;

    public static class Builder {
        private String mApplicationId;
        private boolean mAutoReconnectEnabled;
        private boolean mCaptionPreferenceEnabled;
        private boolean mCastControllerImmersive = true;
        private Class<? extends Service> mCustomNotificationService;
        private boolean mDebugEnabled;
        private boolean mDisableLaunchOnConnect;
        private int mForwardStep = 30;
        private Locale mLocale;
        private boolean mLockScreenEnabled;
        private MediaRouteDialogFactory mMediaRouteDialogFactory;
        private List<String> mNamespaces;
        private int mNextPrevVisibilityPolicy = 2;
        private List<Integer> mNotificationActions;
        private List<Integer> mNotificationCompactActions;
        private boolean mNotificationEnabled;
        private boolean mRelaunchIfRunning;
        private Class<?> mTargetActivity;
        private boolean mWifiReconnectEnabled;

        public Builder(String str) {
            this.mApplicationId = Utils.assertNotEmpty(str, "applicationId");
            this.mNotificationActions = new ArrayList();
            this.mNotificationCompactActions = new ArrayList();
            this.mNamespaces = new ArrayList();
        }

        public CastConfiguration build() {
            if (!this.mNotificationEnabled) {
                if (!this.mNotificationActions.isEmpty()) {
                    throw new IllegalArgumentException("Notification was not enabled but some notification actions were configured");
                }
            }
            if (this.mNotificationActions.size() > 5) {
                throw new IllegalArgumentException("You cannot add more than 5 notification actions for the expanded view");
            } else if (this.mNotificationCompactActions.size() <= 3) {
                if (this.mCustomNotificationService != null) {
                    if (!this.mNotificationEnabled) {
                        throw new IllegalArgumentException("For custom notifications, you should enable notifications first");
                    }
                }
                return new CastConfiguration();
            } else {
                throw new IllegalArgumentException("You cannot add more than 3 compact notification actions for the compact view");
            }
        }

        public Builder enableDebug() {
            this.mDebugEnabled = true;
            return this;
        }

        public Builder enableLockScreen() {
            this.mLockScreenEnabled = true;
            return this;
        }

        public Builder disableLaunchOnConnect() {
            this.mDisableLaunchOnConnect = true;
            return this;
        }

        public Builder enableNotification() {
            this.mNotificationEnabled = true;
            return this;
        }

        public Builder enableWifiReconnection() {
            this.mWifiReconnectEnabled = true;
            return this;
        }

        public Builder enableCaptionManagement() {
            this.mCaptionPreferenceEnabled = true;
            return this;
        }

        public Builder enableAutoReconnect() {
            this.mAutoReconnectEnabled = true;
            return this;
        }

        public Builder addNotificationAction(int i, boolean z) {
            if (!this.mNotificationActions.contains(Integer.valueOf(i))) {
                if (z) {
                    this.mNotificationCompactActions.add(Integer.valueOf(this.mNotificationActions.size()));
                }
                this.mNotificationActions.add(Integer.valueOf(i));
            }
            return this;
        }

        public Builder setTargetActivity(@NonNull Class<?> cls) {
            this.mTargetActivity = (Class) Utils.assertNotNull(cls, "targetActivity");
            return this;
        }

        public Builder setNextPrevVisibilityPolicy(int i) {
            this.mNextPrevVisibilityPolicy = i;
            return this;
        }

        public Builder addNamespace(@NonNull String str) {
            this.mNamespaces.add(Utils.assertNotEmpty(str, "namespace"));
            return this;
        }

        public Builder setLaunchOptions(boolean z, @NonNull Locale locale) {
            this.mLocale = (Locale) Utils.assertNotNull(locale, "locale");
            this.mRelaunchIfRunning = z;
            return this;
        }

        public Builder setCastControllerImmersive(boolean z) {
            this.mCastControllerImmersive = z;
            return this;
        }

        public Builder setForwardStep(int i) {
            this.mForwardStep = i;
            return this;
        }

        public Builder setCustomNotificationService(Class<? extends Service> cls) {
            this.mCustomNotificationService = (Class) Utils.assertNotNull(cls, "customNotificationService");
            return this;
        }

        public Builder setMediaRouteDialogFactory(MediaRouteDialogFactory mediaRouteDialogFactory) {
            this.mMediaRouteDialogFactory = mediaRouteDialogFactory;
            return this;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface NotificationAction {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PrevNextPolicy {
    }

    private CastConfiguration(Builder builder) {
        if (builder.mDebugEnabled) {
            this.mCapabilities |= 1;
        }
        if (builder.mLockScreenEnabled) {
            this.mCapabilities |= 2;
        }
        if (builder.mNotificationEnabled) {
            this.mCapabilities |= 4;
        }
        if (builder.mWifiReconnectEnabled) {
            this.mCapabilities |= 8;
        }
        if (builder.mCaptionPreferenceEnabled) {
            this.mCapabilities |= 16;
        }
        if (builder.mAutoReconnectEnabled) {
            this.mCapabilities |= 32;
        }
        this.mNotificationActions = new ArrayList(builder.mNotificationActions);
        this.mNotificationCompactActions = new ArrayList(builder.mNotificationCompactActions);
        this.mNextPrevVisibilityPolicy = builder.mNextPrevVisibilityPolicy;
        this.mApplicationId = builder.mApplicationId;
        this.mTargetActivity = builder.mTargetActivity;
        if (!builder.mNamespaces.isEmpty()) {
            this.mNamespaces = new ArrayList(builder.mNamespaces);
        }
        if (builder.mLocale != null) {
            this.mLaunchOptions = new com.google.android.gms.cast.LaunchOptions.Builder().setLocale(builder.mLocale).setRelaunchIfRunning(builder.mRelaunchIfRunning).build();
        } else {
            this.mLaunchOptions = new com.google.android.gms.cast.LaunchOptions.Builder().setRelaunchIfRunning(false).build();
        }
        this.mCastControllerImmersive = builder.mCastControllerImmersive;
        this.mForwardStep = builder.mForwardStep;
        this.mCustomNotificationService = builder.mCustomNotificationService;
        this.mMediaRouteDialogFactory = builder.mMediaRouteDialogFactory;
        this.mDisableLaunchOnConnect = builder.mDisableLaunchOnConnect;
    }

    public List<Integer> getNotificationActions() {
        return this.mNotificationActions;
    }

    public List<Integer> getNotificationCompactActions() {
        return this.mNotificationCompactActions;
    }

    public int getCapabilities() {
        return this.mCapabilities;
    }

    public int getNextPrevVisibilityPolicy() {
        return this.mNextPrevVisibilityPolicy;
    }

    public String getApplicationId() {
        return this.mApplicationId;
    }

    public Class<?> getTargetActivity() {
        return this.mTargetActivity;
    }

    public List<String> getNamespaces() {
        return this.mNamespaces;
    }

    public LaunchOptions getLaunchOptions() {
        return this.mLaunchOptions;
    }

    public boolean isCastControllerImmersive() {
        return this.mCastControllerImmersive;
    }

    public boolean isDisableLaunchOnConnect() {
        return this.mDisableLaunchOnConnect;
    }

    public int getForwardStep() {
        return this.mForwardStep;
    }

    public Class<? extends Service> getCustomNotificationService() {
        return this.mCustomNotificationService;
    }

    public MediaRouteDialogFactory getMediaRouteDialogFactory() {
        return this.mMediaRouteDialogFactory;
    }

    public void setNextPrevVisibilityPolicy(int i) {
        this.mNextPrevVisibilityPolicy = i;
    }
}
