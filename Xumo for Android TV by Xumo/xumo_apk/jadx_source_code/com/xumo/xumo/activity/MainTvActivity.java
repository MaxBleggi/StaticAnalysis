package com.xumo.xumo.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.MessagingAnalytics;
import com.xumo.xumo.fragmenttv.MainFragment;
import com.xumo.xumo.model.DeepLinkKey;
import com.xumo.xumo.player.XumoExoPlayer;
import com.xumo.xumo.repository.UserPreferences;
import com.xumo.xumo.service.XumoFirebaseMessagingService;
import com.xumo.xumo.tv.R;
import com.xumo.xumo.util.BeaconUtil;
import com.xumo.xumo.util.LogUtil;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainTvActivity extends AppCompatActivity {
    private AudioManager mAudioManager;
    private BroadcastReceiver mAudioNoiseReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.media.AUDIO_BECOMING_NOISY") != null && MainTvActivity.this.mXumoExoPlayer != null) {
                MainTvActivity.this.mXumoExoPlayer.onAudioBecomeNoisy();
            }
        }
    };
    private LocalBroadcastManager mBroadcastReceiver;
    private String mDeepLinkAssetId;
    private String mDeepLinkCategoryId;
    private String mDeepLinkChannelId;
    private String mDeepLinkLiveChannelId;
    private String mDeepLinkNotificationId;
    private String mDeepLinkTrackingId;
    private boolean mIsLaunch;
    private boolean mIsPushNotificationDeepLink;
    private boolean mIsVizioDeepLink;
    private BroadcastReceiver mReceiver;
    private XumoExoPlayer mXumoExoPlayer;

    public interface PushNotificationListener {
        void onHandlePushNotificationDeepLink();
    }

    private class XumoNotificationBroadcastReceiver extends BroadcastReceiver {
        private XumoNotificationBroadcastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(XumoFirebaseMessagingService.PUSH_NOTIFICATION_ACTION) != null) {
                MainTvActivity.this.mIsLaunch = true;
                MainTvActivity.this.mIsPushNotificationDeepLink = true;
                MainTvActivity.this.mDeepLinkNotificationId = intent.getStringExtra(DeepLinkKey.NOTIFICATION_ID);
                MainTvActivity.this.mDeepLinkChannelId = intent.getStringExtra("channelId");
                MainTvActivity.this.mDeepLinkCategoryId = intent.getStringExtra("categoryId");
                MainTvActivity.this.mDeepLinkLiveChannelId = intent.getStringExtra(DeepLinkKey.LIVE_CHANNEL_ID);
                MainTvActivity.this.mDeepLinkAssetId = intent.getStringExtra("assetId");
            }
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return true;
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        return true;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.tv_activity_main);
        setRequestedOrientation(11);
        this.mXumoExoPlayer = new XumoExoPlayer(getApplicationContext());
        registerReceiver(this.mAudioNoiseReceiver, new IntentFilter("android.media.AUDIO_BECOMING_NOISY"));
        Intent intent = getIntent();
        if (intent != null) {
            handleVizioDeepLink(intent);
            handlePushNotificationDeepLink(intent);
        }
        if (bundle == null) {
            bundle = getSupportFragmentManager().beginTransaction();
            bundle.replace(R.id.fragment_container, new MainFragment(), MainFragment.TAG_MAIN);
            bundle.addToBackStack(null);
            bundle.commitAllowingStateLoss();
        }
        this.mAudioManager = (AudioManager) getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        initFirebase();
        this.mXumoExoPlayer.setup();
        handleDynamicLink();
        logFCMAppOpen();
    }

    protected void onStart() {
        super.onStart();
        UserPreferences.session.foregroundSession();
        this.mBroadcastReceiver = LocalBroadcastManager.getInstance(getApplicationContext());
        this.mReceiver = new XumoNotificationBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(XumoFirebaseMessagingService.PUSH_NOTIFICATION_ACTION);
        this.mBroadcastReceiver.registerReceiver(this.mReceiver, intentFilter);
    }

    private void logFCMAppOpen() {
        Intent intent = getIntent();
        if (intent != null && intent.getBooleanExtra("FCM_APP_OPEN", false)) {
            MessagingAnalytics.logNotificationOpen(intent);
        }
    }

    protected void onStop() {
        super.onStop();
        UserPreferences.session.backgroundSession();
        BeaconUtil.stopKeepAliveImpressionsBeaconTimer();
        this.mBroadcastReceiver.unregisterReceiver(this.mReceiver);
    }

    protected void onDestroy() {
        super.onDestroy();
        this.mXumoExoPlayer.release();
        unregisterReceiver(this.mAudioNoiseReceiver);
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() != 99) {
            this.mXumoExoPlayer.sendOnKeyPress(keyEvent.getKeyCode(), keyEvent);
            return super.dispatchKeyEvent(keyEvent);
        }
        keyEvent = new Intent("android.intent.action.MAIN");
        keyEvent.setFlags(C.ENCODING_PCM_MU_LAW);
        keyEvent.addCategory("android.intent.category.HOME");
        startActivity(keyEvent);
        return true;
    }

    public XumoExoPlayer getXumoExoPlayer() {
        return this.mXumoExoPlayer;
    }

    public boolean isVizioDeepLink() {
        return this.mIsVizioDeepLink;
    }

    public String getDeepLinkChannelId() {
        return this.mDeepLinkChannelId;
    }

    public String getDeepLinkTrackingId() {
        return this.mDeepLinkTrackingId;
    }

    public boolean isPushNotificationDeepLink() {
        return this.mIsPushNotificationDeepLink;
    }

    public String getDeepLinkNotificationId() {
        return this.mDeepLinkNotificationId;
    }

    public String getDeepLinkCategoryId() {
        return this.mDeepLinkCategoryId;
    }

    public String getDeepLinkLiveChannelId() {
        return this.mDeepLinkLiveChannelId;
    }

    public String getDeepLinkAssetId() {
        return this.mDeepLinkAssetId;
    }

    public boolean isLaunch() {
        return this.mIsLaunch;
    }

    public void clearDeepLinkInfo() {
        this.mIsVizioDeepLink = false;
        this.mDeepLinkChannelId = null;
        this.mDeepLinkTrackingId = null;
        this.mIsPushNotificationDeepLink = false;
        this.mDeepLinkCategoryId = null;
        this.mDeepLinkLiveChannelId = null;
        this.mDeepLinkAssetId = null;
        this.mIsLaunch = false;
    }

    private void handleVizioDeepLink(Intent intent) {
        this.mDeepLinkChannelId = intent.getStringExtra("channelId");
        this.mDeepLinkTrackingId = intent.getStringExtra(DeepLinkKey.TRACKING_ID);
        if (TextUtils.isEmpty(this.mDeepLinkChannelId) == null && TextUtils.isEmpty(this.mDeepLinkTrackingId) == null) {
            this.mIsVizioDeepLink = true;
        }
    }

    private void handlePushNotificationDeepLink(Intent intent) {
        String action = intent.getAction();
        if (action != null && action.equals("android.intent.action.VIEW")) {
            this.mDeepLinkNotificationId = intent.getStringExtra(DeepLinkKey.NOTIFICATION_ID);
            if (!TextUtils.isEmpty(this.mDeepLinkNotificationId)) {
                this.mDeepLinkChannelId = intent.getStringExtra("channelId");
                this.mDeepLinkCategoryId = intent.getStringExtra("categoryId");
                this.mDeepLinkLiveChannelId = intent.getStringExtra(DeepLinkKey.LIVE_CHANNEL_ID);
                this.mDeepLinkAssetId = intent.getStringExtra("assetId");
                this.mIsLaunch = null;
                this.mIsPushNotificationDeepLink = true;
            }
        }
    }

    private void handleDynamicLink() {
        FirebaseDynamicLinks.getInstance().getDynamicLink(getIntent()).addOnSuccessListener((Activity) this, new OnSuccessListener<PendingDynamicLinkData>() {
            public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                pendingDynamicLinkData = pendingDynamicLinkData != null ? pendingDynamicLinkData.getLink() : null;
                if (pendingDynamicLinkData != null) {
                    pendingDynamicLinkData = pendingDynamicLinkData.getPathSegments();
                    if (pendingDynamicLinkData != null && 2 <= pendingDynamicLinkData.size()) {
                        MainTvActivity.this.mDeepLinkChannelId = (String) pendingDynamicLinkData.get(1);
                        if (TextUtils.isEmpty(MainTvActivity.this.mDeepLinkChannelId) == null) {
                            MainTvActivity.this.mIsPushNotificationDeepLink = true;
                        }
                    }
                }
            }
        }).addOnFailureListener((Activity) this, new OnFailureListener() {
            public void onFailure(@NonNull Exception exception) {
                LogUtil.w("getDynamicLink:onFailure", (Throwable) exception);
            }
        });
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        FirebaseAnalytics.getInstance(this);
        FirebaseMessaging.getInstance().subscribeToTopic("general").addOnCompleteListener(-$$Lambda$MainTvActivity$NaTEvmDh9xJEI7gkygtNAajiYhU.INSTANCE);
    }

    private static /* synthetic */ void lambda$initFirebase$0(Task task) {
        if (task.isSuccessful() != null) {
            LogUtil.d("FirebaseMessaging: Successfully subscribed to 'develop' topic");
        } else {
            LogUtil.d("FirebaseMessaging: Failed to subscribe to 'develop' topic");
        }
    }

    static /* synthetic */ void lambda$initFirebase$1(Task task) {
        if (task.isSuccessful() != null) {
            LogUtil.d("FirebaseMessaging: Successfully subscribed to 'general' topic");
        } else {
            LogUtil.d("FirebaseMessaging: Failed to subscribe to 'general' topic");
        }
    }
}
