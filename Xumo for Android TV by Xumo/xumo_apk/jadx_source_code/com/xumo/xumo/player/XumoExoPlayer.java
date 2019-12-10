package com.xumo.xumo.player;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.amazon.device.ads.aftv.AdBreakPattern;
import com.amazon.device.ads.aftv.AmazonFireTVAdCallback;
import com.amazon.device.ads.aftv.AmazonFireTVAdRequest;
import com.amazon.device.ads.aftv.AmazonFireTVAdResponse;
import com.google.ads.interactivemedia.v3.api.AdErrorEvent;
import com.google.ads.interactivemedia.v3.api.AdErrorEvent.AdErrorListener;
import com.google.ads.interactivemedia.v3.api.AdEvent;
import com.google.ads.interactivemedia.v3.api.AdEvent.AdEventListener;
import com.google.ads.interactivemedia.v3.api.player.ContentProgressProvider;
import com.google.ads.interactivemedia.v3.api.player.VideoProgressUpdate;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player.EventListener;
import com.google.android.exoplayer2.Player.EventListener.-CC;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.drm.FrameworkMediaDrm;
import com.google.android.exoplayer2.drm.HttpMediaDrmCallback;
import com.google.android.exoplayer2.drm.MediaDrmCallback;
import com.google.android.exoplayer2.drm.UnsupportedDrmException;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.offline.FilteringManifestParser;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSource.MediaPeriodId;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.MediaSourceEventListener.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaSourceEventListener.MediaLoadData;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.SingleSampleMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.manifest.DashManifestParser;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector.ParametersBuilder;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.DefaultTimeBar;
import com.google.android.exoplayer2.ui.PlayerControlView.VisibilityListener;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.TimeBar;
import com.google.android.exoplayer2.ui.TimeBar.OnScrubListener;
import com.google.android.exoplayer2.upstream.DataSource.Factory;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.cast.framework.media.NotificationOptions;
import com.xumo.xumo.ads.XumoImaAdsLoader;
import com.xumo.xumo.ads.XumoImaAdsLoader.Builder;
import com.xumo.xumo.application.XumoApplication;
import com.xumo.xumo.model.LiveAsset;
import com.xumo.xumo.model.OnNowLive;
import com.xumo.xumo.model.PlayerProvider;
import com.xumo.xumo.model.Provider;
import com.xumo.xumo.model.VideoAsset;
import com.xumo.xumo.model.XumoDataSync;
import com.xumo.xumo.repository.UserPreferences;
import com.xumo.xumo.service.XumoWebService.Listener;
import com.xumo.xumo.tv.R;
import com.xumo.xumo.util.AmazonUtil;
import com.xumo.xumo.util.BeaconUtil;
import com.xumo.xumo.util.BeaconUtil.AdBeaconEvents;
import com.xumo.xumo.util.BeaconUtil.AdBeaconState;
import com.xumo.xumo.util.BeaconUtil.AdPlacement;
import com.xumo.xumo.util.BeaconUtil.PlayType;
import com.xumo.xumo.util.BeaconUtil.VideoBeaconErrors;
import com.xumo.xumo.util.BeaconUtil.VideoBeaconEvents;
import com.xumo.xumo.util.LogUtil;
import com.xumo.xumo.util.OmnitureBeaconUtil;
import com.xumo.xumo.util.XumoCBSNBeaconApiHelper;
import com.xumo.xumo.util.XumoUtil;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class XumoExoPlayer implements EventListener, MediaSourceEventListener, ContentProgressProvider, AdErrorListener, AdEventListener, OnScrubListener {
    private static final String AD_EXAMPLE_SINGLE_INLINE_LINEAR = "https://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/single_ad_samples&ciu_szs=300x250&impl=s&gdfp_req=1&env=vp&output=vast&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ct%3Dlinear&correlator=";
    private static final String AD_EXAMPLE_VMAP_PREROLL_SINGLE_AD_MIDROLL_STA_POD_WITH_3_ADS_POSTROLL_SINGLE_AD = "https://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/ad_rule_samples&ciu_szs=300x250&ad_rule=1&impl=s&gdfp_req=1&env=vp&output=vmap&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ar%3Dpremidpostpod&cmsid=496&vid=short_onecue&correlator=";
    private static final String AD_EXAMPLE_XUMO_1 = "https://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/ad_rule_samples&ciu_szs=300x250&ad_rule=1&impl=s&gdfp_req=1&env=vp&output=vmap&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ar%3Dpremidpostpod&cmsid=496&vid=short_onecue&correlator=";
    private static final String ASSET_ID = "[ASSET_ID]";
    private static final String CATEGORY_ID = "[CATEGORY_ID]";
    private static final String CHANNEL_ID = "[CHANNEL_ID]";
    private static final String DESCRIPTION_URL = "[description_url]";
    private static final String IFA = "[IFA]";
    private static final String PROVIDER_ASSET_ID = "[PROVIDER_ASSET_ID]";
    private static final String PROVIDER_ID = "[PROVIDER_ID]";
    private static final String PROVIDER_NAME = "[PROVIDER_NAME]";
    private static final String RANDOM_NUMBER_8 = "[RANDOM_NUMBER_8]";
    private static final String REFERRER_URL = "[referrer_url]";
    private static final String TIMESTAMP = "[timestamp]";
    private static final String URL_SCHEME = "https:";
    private static final String VPOS = "[VPOS]";
    public static String mPlaySessionId;
    private AdBreakPattern adBreakPattern;
    private boolean[] adPlay;
    private Factory dataSourceFactory;
    private boolean firstPrepareVideo = false;
    Handler handler = new Handler();
    private boolean isForceStop = false;
    private boolean isHasAD = true;
    private boolean isPause = false;
    private String mADtag = "";
    private AdBeaconState mAdBeaconState;
    private String mAdTagUrl = "";
    private WatchdogTimer mAdWatchdog;
    private TextView mAssetDurationTextView;
    private TextView mAssetPositionTextView;
    private Fragment mAttachFragment;
    private VideoBeaconEvents mBeaconState;
    private Timer mBeaconTimer;
    private BeaconTimerTask mBeaconTimerTask;
    private WatchdogTimer mBufWatchdog;
    private int mBufferingIndicatorColor;
    private Context mContext;
    private XumoExoPlayerControllerListener mControllerListener;
    private Float[] mCuePoints;
    private AdEvent mCurrentAdEvent;
    private int mCurrentCueIndex;
    private Factory mDataSourceFactory;
    private TextView mDescriptionTextView;
    private boolean mErrorState = false;
    private PlayerView mExoPlayerView;
    private ExtractorsFactory mExtractorsFactory;
    private boolean mFallbackAfterHLSLoadFail;
    private ViewGroup mFbAdContainerView;
    private final String mFbPlacementId;
    private boolean mHasPlayedVideoOnce = false;
    private boolean mIsAdDisplayed = false;
    private boolean mIsAdDisplayedCopy = false;
    private boolean mIsAdDisplayedSaved = false;
    private boolean mIsLive = false;
    private boolean mIsSeekto = false;
    private int mKeepTotalWatchedTime;
    private long mLastAdPlayTime = System.currentTimeMillis();
    private String mLastChannelId;
    private long mLastContentPosition = 0;
    private long mLastContentTotalWatchedTime;
    private PlayType mLastPlayType;
    private Handler mMainHandler;
    private int mMoviesControllerSkipMultiple = 0;
    private ImageButton mNextVodButton;
    private NowPlayingAdListener mNowPlayingAdListener;
    private OnKeyPressListener mOnKeyPressListener;
    private OnNowLive mOnNowLive;
    private LinearLayout mParentLy;
    private ImageButton mPauseButton;
    private ImageButton mPlayButton;
    private XumoExoPlayerEventListener mPlayerEventListener;
    private PlayerProvider mPlayerProvider = new PlayerProvider();
    private boolean mPlayerReleased = false;
    private ImageButton mPreVodButton;
    private String mProviderName = "";
    private boolean mQueuedVideoAsset = false;
    private boolean mRequiredSeek = false;
    private boolean mRequiredSeekAfterAds = false;
    private boolean mShouldAutoPlay = true;
    private SimpleExoPlayer mSimpleExoPlayer;
    private ImageButton mSkipBackButton;
    private ImageButton mSkipFwdButton;
    private long mStartTime = 0;
    private long mStartTimeAfterAds = 0;
    private DefaultTimeBar mTimeBar;
    private TextView mTitleTextView;
    private TrackSelector mTrackSelector;
    private XumoTvExoPlayerControllerListener mTvControllerListener;
    private boolean mUseTestAds = false;
    private VideoAsset mVideoAsset;
    private ImageButton mVodCcButton;
    private LinearLayout mVodMoreFromLy;
    private TextView mVodSkipBackText;
    private TextView mVodSkipFwdText;
    private WakeLock mWakelock;
    private XumoImaAdsLoader mXumoImaAdsLoader;
    private FrameworkMediaDrm mediaDrm;
    private boolean playRequestSent;
    private Provider provider;
    Runnable runnable = new Runnable() {
        public void run() {
            XumoExoPlayer.this.handler.postDelayed(this, 1000);
            int access$2400 = 1 == Math.abs(XumoExoPlayer.this.mMoviesControllerSkipMultiple) ? XumoExoPlayer.this.mMoviesControllerSkipMultiple * 15000 : 2 == Math.abs(XumoExoPlayer.this.mMoviesControllerSkipMultiple) ? XumoExoPlayer.this.mMoviesControllerSkipMultiple * 15000 : 3 == Math.abs(XumoExoPlayer.this.mMoviesControllerSkipMultiple) ? XumoExoPlayer.this.mMoviesControllerSkipMultiple * 20000 : 4 == Math.abs(XumoExoPlayer.this.mMoviesControllerSkipMultiple) ? 45000 * XumoExoPlayer.this.mMoviesControllerSkipMultiple : 5 == Math.abs(XumoExoPlayer.this.mMoviesControllerSkipMultiple) ? 120000 * XumoExoPlayer.this.mMoviesControllerSkipMultiple : 0;
            XumoExoPlayer.this.setMoviePosition((long) access$2400);
        }
    };
    private int totalDurationWatchedForCurrentVideo = 0;
    private MediaSource videoSource;

    private class BeaconTimerTask extends TimerTask {
        boolean countTick;
        long lastCurrentPosition = -1;
        boolean playSuccessBeaconSent;
        boolean runForAds;

        public void setTotalDurationWatchedForCurrentVideo(int i) {
            XumoExoPlayer.this.totalDurationWatchedForCurrentVideo = i;
        }

        public int getTotalDurationWatchedForCurrentVideo() {
            return XumoExoPlayer.this.totalDurationWatchedForCurrentVideo;
        }

        BeaconTimerTask(boolean z) {
            XumoExoPlayer.this.mLastContentTotalWatchedTime = 0;
            if (!z) {
                XumoExoPlayer.this.totalDurationWatchedForCurrentVideo = XumoExoPlayer.this.getVideoTotalWatchedTime();
            }
            this.runForAds = z;
            this.countTick = null;
        }

        public void run() {
            if ((XumoExoPlayer.this.mSimpleExoPlayer != null && XumoExoPlayer.this.mSimpleExoPlayer.getPlayWhenReady()) || XumoExoPlayer.this.playingValidFbAds()) {
                if (!(XumoExoPlayer.this.isAdDisplayed() || this.playSuccessBeaconSent)) {
                    this.playSuccessBeaconSent = true;
                }
                long currentPosition = this.runForAds ? XumoExoPlayer.this.getCurrentPosition() : XumoExoPlayer.this.getContentPosition();
                if (this.lastCurrentPosition != currentPosition || XumoExoPlayer.this.playingValidFbAds()) {
                    if ((this.lastCurrentPosition < currentPosition || XumoExoPlayer.this.playingValidFbAds()) && this.countTick) {
                        XumoExoPlayer.this.totalDurationWatchedForCurrentVideo = XumoExoPlayer.this.totalDurationWatchedForCurrentVideo + 1;
                        if ("9999158".equals(XumoExoPlayer.this.mLastChannelId) && 1 == XumoExoPlayer.this.totalDurationWatchedForCurrentVideo) {
                            XumoCBSNBeaconApiHelper.sendCBSNBeacon();
                        }
                    }
                    if (!XumoExoPlayer.this.isAdDisplayed() && !this.runForAds && this.countTick && XumoExoPlayer.this.totalDurationWatchedForCurrentVideo > 0 && (XumoExoPlayer.this.totalDurationWatchedForCurrentVideo == 5 || XumoExoPlayer.this.totalDurationWatchedForCurrentVideo == 10 || XumoExoPlayer.this.totalDurationWatchedForCurrentVideo == 15 || XumoExoPlayer.this.totalDurationWatchedForCurrentVideo % 30 == 0)) {
                        XumoExoPlayer.this.saveVideoTotalWatchedTime();
                        XumoExoPlayer.this.sendContentBeacon(VideoBeaconEvents.PlayInterval, null);
                    }
                    XumoExoPlayer.this.mLastContentTotalWatchedTime = (long) XumoExoPlayer.this.totalDurationWatchedForCurrentVideo;
                    if (XumoExoPlayer.this.isAdDisplayed() && (this.lastCurrentPosition < currentPosition || XumoExoPlayer.this.playingValidFbAds())) {
                        XumoExoPlayer.this.resetAdWatchdog();
                    }
                    this.lastCurrentPosition = currentPosition;
                }
                if (XumoExoPlayer.this.mCuePoints != null && XumoExoPlayer.this.mCurrentCueIndex > 0 && XumoExoPlayer.this.mCurrentCueIndex < XumoExoPlayer.this.mCuePoints.length - 1 && XumoExoPlayer.this.mVideoAsset != null && XumoExoPlayer.this.mCuePoints[XumoExoPlayer.this.mCurrentCueIndex].floatValue() * 1000.0f <= ((float) currentPosition)) {
                    if (XumoExoPlayer.this.readyToPlayAds()) {
                        XumoExoPlayer.this.mStartTimeAfterAds = currentPosition;
                        XumoExoPlayer.this.isHasAD = XumoExoPlayer.this.readyToPlayAds();
                        new Handler(Looper.getMainLooper()).post(new -$$Lambda$XumoExoPlayer$BeaconTimerTask$jERq269lRwTq3WmcfPPxp9cAP8M());
                    }
                    XumoExoPlayer.this.mCurrentCueIndex = XumoExoPlayer.this.mCurrentCueIndex + 1;
                }
                this.countTick ^= true;
            }
        }
    }

    public interface NowPlayingAdListener {
        void onNowPlaying(boolean z);
    }

    public interface OnKeyPressListener {
        void onKeyPress(int i, KeyEvent keyEvent);
    }

    public interface XumoExoPlayerControllerListener {
        void pause();

        void play();
    }

    public interface XumoExoPlayerEventListener {
        void onPlayerError(int i);

        void onPlayerStateChanged(boolean z, int i);
    }

    public interface XumoTvExoPlayerControllerListener {
        boolean isShowNextAsset();

        boolean isShowPreAsset();

        boolean isVodCcButton();
    }

    private boolean playingValidFbAds() {
        return false;
    }

    public /* synthetic */ void onLoadingChanged(boolean z) {
        -CC.$default$onLoadingChanged(this, z);
    }

    public void onMediaPeriodCreated(int i, MediaPeriodId mediaPeriodId) {
    }

    public void onMediaPeriodReleased(int i, MediaPeriodId mediaPeriodId) {
    }

    public /* synthetic */ void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        -CC.$default$onPlaybackParametersChanged(this, playbackParameters);
    }

    public /* synthetic */ void onPositionDiscontinuity(int i) {
        -CC.$default$onPositionDiscontinuity(this, i);
    }

    public void onReadingStarted(int i, MediaPeriodId mediaPeriodId) {
    }

    public /* synthetic */ void onRepeatModeChanged(int i) {
        -CC.$default$onRepeatModeChanged(this, i);
    }

    public void onScrubMove(TimeBar timeBar, long j) {
    }

    public /* synthetic */ void onSeekProcessed() {
        -CC.$default$onSeekProcessed(this);
    }

    public /* synthetic */ void onShuffleModeEnabledChanged(boolean z) {
        -CC.$default$onShuffleModeEnabledChanged(this, z);
    }

    public /* synthetic */ void onTimelineChanged(Timeline timeline, @Nullable Object obj, int i) {
        -CC.$default$onTimelineChanged(this, timeline, obj, i);
    }

    public /* synthetic */ void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
        -CC.$default$onTracksChanged(this, trackGroupArray, trackSelectionArray);
    }

    public boolean ismIsAdDisplayedCopy() {
        return this.mIsAdDisplayedCopy;
    }

    public void clearVideoTotalWatchedTime() {
        this.mKeepTotalWatchedTime = 0;
    }

    public int getVideoTotalWatchedTime() {
        return this.mKeepTotalWatchedTime;
    }

    public void saveVideoTotalWatchedTime() {
        if (this.mBeaconTimerTask != null) {
            this.mKeepTotalWatchedTime = this.mBeaconTimerTask.getTotalDurationWatchedForCurrentVideo();
        }
    }

    public void resumeVideoTotalWatchedTime() {
        if (this.mBeaconTimerTask != null) {
            this.mBeaconTimerTask.setTotalDurationWatchedForCurrentVideo(this.mKeepTotalWatchedTime);
        }
    }

    public XumoExoPlayer(Context context) {
        this.mContext = context;
        this.mMainHandler = new Handler();
        this.mDataSourceFactory = new DefaultDataSourceFactory(this.mContext, Util.getUserAgent(this.mContext, this.mContext.getString(R.string.app_name)));
        this.mExtractorsFactory = new DefaultExtractorsFactory();
        this.mErrorState = false;
        this.mShouldAutoPlay = true;
        this.playRequestSent = false;
        this.mUseTestAds = context.getResources().getBoolean(R.bool.use_test_ads);
        this.mFbPlacementId = context.getString(R.string.facebook_video_instream_placement_id);
        this.mBufferingIndicatorColor = ContextCompat.getColor(context, R.color.xumoBlue);
        this.mAdWatchdog = new WatchdogTimer(new -$$Lambda$XumoExoPlayer$OMeXajyhQZFxDOhZn9SnGACnCJw());
        this.mBufWatchdog = new WatchdogTimer(new -$$Lambda$XumoExoPlayer$YzXgk5FGNUqWXR_D-SBvtFMFPuU(), 15000);
        this.dataSourceFactory = buildDataSourceFactory();
    }

    private Factory buildDataSourceFactory() {
        return ((XumoApplication) this.mContext).buildDataSourceFactory();
    }

    private void onPlayerError() {
        onPlayerError(ExoPlaybackException.createForRenderer(new Exception(), 0));
    }

    public void setup() {
        if (this.mSimpleExoPlayer == null) {
            DrmSessionManager buildDrmSessionManagerV18;
            this.mTrackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory());
            ((DefaultTrackSelector) this.mTrackSelector).setParameters(new ParametersBuilder().setRendererDisabled(2, true));
            ((DefaultTrackSelector) this.mTrackSelector).setParameters(new ParametersBuilder().setRendererDisabled(3, true));
            try {
                buildDrmSessionManagerV18 = buildDrmSessionManagerV18(Util.getDrmUuid("widevine"), "https://widevine-dash.ezdrm.com/proxy?pX=5FE38E", null, false);
            } catch (UnsupportedDrmException e) {
                e.printStackTrace();
                buildDrmSessionManagerV18 = null;
            }
            this.mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this.mContext, new DefaultRenderersFactory(this.mContext, 0), this.mTrackSelector, buildDrmSessionManagerV18);
            setSwitchSubtitle();
            this.mSimpleExoPlayer.addListener(this);
            this.mSimpleExoPlayer.setVolume(1.0f);
            this.mSimpleExoPlayer.setPlayWhenReady(this.mShouldAutoPlay);
        }
    }

    private List<StreamKey> getOfflineStreamKeys(Uri uri) {
        return ((XumoApplication) this.mContext).getDownloadTracker().getOfflineStreamKeys(uri);
    }

    public void setControllerListener(XumoExoPlayerControllerListener xumoExoPlayerControllerListener) {
        this.mControllerListener = xumoExoPlayerControllerListener;
    }

    public void setTvControllerListener(XumoTvExoPlayerControllerListener xumoTvExoPlayerControllerListener) {
        this.mTvControllerListener = xumoTvExoPlayerControllerListener;
    }

    public void setPlayerEventListener(XumoExoPlayerEventListener xumoExoPlayerEventListener) {
        this.mPlayerEventListener = xumoExoPlayerEventListener;
    }

    public void setAttachFragment(Fragment fragment) {
        this.mAttachFragment = fragment;
    }

    public Fragment getAttachFragment() {
        return this.mAttachFragment;
    }

    public void setPlayerView(PlayerView playerView) {
        if (playerView != null) {
            this.mExoPlayerView = playerView;
            this.mExoPlayerView.setVisibility(0);
            this.mExoPlayerView.setPlayer(null);
            this.mExoPlayerView.setPlayer(this.mSimpleExoPlayer);
            this.mParentLy = (LinearLayout) playerView.findViewById(R.id.parent_ly);
            this.mTitleTextView = (TextView) playerView.findViewById(R.id.title);
            this.mDescriptionTextView = (TextView) playerView.findViewById(R.id.description);
            this.mPlayButton = (ImageButton) playerView.findViewById(R.id.play);
            this.mPauseButton = (ImageButton) playerView.findViewById(R.id.pause);
            this.mSkipBackButton = (ImageButton) playerView.findViewById(R.id.skip_back);
            this.mSkipFwdButton = (ImageButton) playerView.findViewById(R.id.skip_fwd);
            this.mPreVodButton = (ImageButton) playerView.findViewById(R.id.pre_vod_button);
            this.mNextVodButton = (ImageButton) playerView.findViewById(R.id.next_vod_button);
            this.mVodSkipFwdText = (TextView) playerView.findViewById(R.id.skip_fwd_text);
            this.mVodSkipBackText = (TextView) playerView.findViewById(R.id.skip_back_text);
            this.mVodCcButton = (ImageButton) playerView.findViewById(R.id.vod_cc_id);
            this.mTimeBar = (DefaultTimeBar) playerView.findViewById(R.id.exo_progress);
            this.mTimeBar.addListener(this);
            this.mAssetPositionTextView = (TextView) playerView.findViewById(R.id.exo_position);
            this.mAssetDurationTextView = (TextView) playerView.findViewById(R.id.exo_duration);
            this.mVodMoreFromLy = (LinearLayout) playerView.findViewById(R.id.vod_more_from_ly);
            setSubTitle();
            this.mFbAdContainerView = (ViewGroup) playerView.findViewById(R.id.fb_ad_view);
            if (!(isAdDisplayed() == null || this.mIsAdDisplayed == null || this.mXumoImaAdsLoader == null || this.mExoPlayerView == null || playingValidFbAds() != null)) {
                playerView = this.mExoPlayerView.getOverlayFrameLayout();
                ViewGroup adUiViewGroup = this.mXumoImaAdsLoader.getAdUiViewGroup();
                if (adUiViewGroup != null && adUiViewGroup.getChildCount() > 0) {
                    View childAt = adUiViewGroup.getChildAt(0);
                    if (childAt instanceof WebView) {
                        adUiViewGroup.removeView(childAt);
                        playerView.addView(childAt);
                    }
                }
                this.mXumoImaAdsLoader.setAdUiViewGroup(playerView);
                updatePlayerController();
            }
        }
    }

    public void setSubTitle() {
        if (this.mExoPlayerView != null && this.mExoPlayerView.getSubtitleView() != null) {
            this.mExoPlayerView.getSubtitleView().setStyle(XumoUtil.getCaptionStyleCompat(this.mContext));
            this.mExoPlayerView.getSubtitleView().setFixedTextSize(2, (float) XumoUtil.getTextSize());
        }
    }

    public void setSwitchSubtitle() {
        if (UserPreferences.getInstance().getSubtitleSwitch()) {
            ((DefaultTrackSelector) this.mTrackSelector).setParameters(new ParametersBuilder().setRendererDisabled(2, false).build());
        } else {
            ((DefaultTrackSelector) this.mTrackSelector).setParameters(new ParametersBuilder().setRendererDisabled(2, true).build());
        }
    }

    public void removePlayerView() {
        if (this.mControllerListener != null) {
            this.mControllerListener = null;
        }
        if (this.mTvControllerListener != null) {
            this.mTvControllerListener = null;
        }
        if (this.mPlayerEventListener != null) {
            this.mPlayerEventListener = null;
        }
        if (this.mAttachFragment != null) {
            this.mAttachFragment = null;
        }
        if (this.mExoPlayerView != null) {
            this.mExoPlayerView.setVisibility(4);
            this.mExoPlayerView.setPlayer(null);
            this.mExoPlayerView = null;
        }
    }

    public void updatePlayerController() {
        updatePlayerController(this.mIsLive);
    }

    public void updatePlayerController(boolean z) {
        if (this.mSimpleExoPlayer != null) {
            if (this.mExoPlayerView != null) {
                this.mIsLive = z;
                if (isAdDisplayed()) {
                    if (this.mTitleTextView) {
                        this.mTitleTextView.setVisibility(4);
                    }
                    if (this.mDescriptionTextView) {
                        this.mDescriptionTextView.setVisibility(4);
                    }
                    this.mParentLy.setVisibility(8);
                    this.mPlayButton.setVisibility(4);
                    this.mPauseButton.setVisibility(4);
                    this.mSkipBackButton.setVisibility(4);
                    this.mSkipFwdButton.setVisibility(4);
                    this.mVodSkipFwdText.setVisibility(4);
                    this.mVodSkipBackText.setVisibility(4);
                    this.mPreVodButton.setVisibility(4);
                    this.mNextVodButton.setVisibility(4);
                    this.mVodCcButton.setVisibility(4);
                    this.mVodMoreFromLy.setVisibility(4);
                    this.mTimeBar.setVisibility(4);
                    this.mAssetPositionTextView.setVisibility(4);
                    this.mAssetDurationTextView.setVisibility(4);
                    if (this.mExoPlayerView) {
                        this.mExoPlayerView.hideController();
                    }
                } else {
                    if (this.mTitleTextView) {
                        this.mTitleTextView.setVisibility(0);
                    }
                    if (this.mDescriptionTextView) {
                        this.mDescriptionTextView.setVisibility(0);
                    }
                    if (this.mIsLive) {
                        this.mParentLy.setVisibility(8);
                        this.mPlayButton.setVisibility(4);
                        this.mPauseButton.setVisibility(4);
                        this.mSkipBackButton.setVisibility(4);
                        this.mSkipFwdButton.setVisibility(4);
                        this.mVodSkipFwdText.setVisibility(4);
                        this.mVodSkipBackText.setVisibility(4);
                        this.mPreVodButton.setVisibility(4);
                        this.mNextVodButton.setVisibility(4);
                        this.mVodCcButton.setVisibility(4);
                        this.mTitleTextView.setVisibility(4);
                        this.mDescriptionTextView.setVisibility(4);
                        this.mTimeBar.setVisibility(4);
                        this.mAssetPositionTextView.setVisibility(4);
                        this.mAssetDurationTextView.setVisibility(4);
                        this.mVodMoreFromLy.setVisibility(4);
                        if (this.mExoPlayerView) {
                            this.mExoPlayerView.hideController();
                        }
                    } else {
                        this.mParentLy.setVisibility(0);
                        if (this.mSimpleExoPlayer.getPlayWhenReady()) {
                            this.mPlayButton.setVisibility(4);
                            this.mPauseButton.setVisibility(0);
                        } else {
                            this.mPlayButton.setVisibility(0);
                            this.mPauseButton.setVisibility(4);
                        }
                        this.mSkipBackButton.setVisibility(0);
                        this.mSkipFwdButton.setVisibility(0);
                        this.mVodSkipFwdText.setVisibility(0);
                        this.mVodSkipBackText.setVisibility(0);
                        if (this.mTvControllerListener) {
                            if (this.mTvControllerListener.isShowPreAsset()) {
                                this.mPreVodButton.setVisibility(0);
                            } else {
                                this.mPreVodButton.setVisibility(4);
                            }
                            if (this.mTvControllerListener.isShowNextAsset()) {
                                this.mNextVodButton.setVisibility(0);
                            } else {
                                this.mNextVodButton.setVisibility(4);
                            }
                            if (this.mTvControllerListener.isVodCcButton()) {
                                this.mVodCcButton.setVisibility(0);
                            } else {
                                this.mVodCcButton.setVisibility(4);
                            }
                        }
                        this.mTitleTextView.setVisibility(0);
                        this.mDescriptionTextView.setVisibility(0);
                        this.mTimeBar.setVisibility(0);
                        this.mAssetPositionTextView.setVisibility(0);
                        this.mAssetDurationTextView.setVisibility(0);
                    }
                }
            }
        }
    }

    public void prepare(VideoAsset videoAsset) {
        prepare(videoAsset, 0);
    }

    public void prepare(final VideoAsset videoAsset, long j) {
        if (this.mSimpleExoPlayer != null) {
            if (this.mExoPlayerView != null) {
                if (videoAsset != null) {
                    Object url = videoAsset.getUrl();
                    int i = 0;
                    this.isPause = false;
                    this.isForceStop = false;
                    if (TextUtils.isEmpty(url)) {
                        LogUtil.w("videoURL is empty or null.");
                        finishPlay();
                        return;
                    }
                    if (this.mIsAdDisplayedSaved) {
                        this.mHasPlayedVideoOnce = true;
                        this.mIsAdDisplayedSaved = false;
                    }
                    this.mVideoAsset = videoAsset;
                    this.videoSource = buildMediaSource(url, videoAsset.getmSrtCaption());
                    if (this.videoSource == null) {
                        finishPlay();
                        return;
                    }
                    int i2;
                    boolean[] zArr;
                    this.mErrorState = false;
                    String channelId = videoAsset.getChannelId();
                    PlayType playType = videoAsset.getAssetType() == 1 ? PlayType.VOD : PlayType.LiveLite;
                    if (!(!TextUtils.isEmpty(channelId) && channelId.equals(this.mLastChannelId) && this.mLastPlayType == playType)) {
                        UserPreferences.getInstance().refreshChannelPlayId(channelId);
                    }
                    mPlaySessionId = null;
                    this.mLastPlayType = playType;
                    this.mLastChannelId = channelId;
                    sendContentBeacon(VideoBeaconEvents.PlayRequested, null);
                    this.firstPrepareVideo = true;
                    if (!this.playRequestSent) {
                        if (this.mBeaconTimerTask != null) {
                            this.totalDurationWatchedForCurrentVideo = 0;
                        }
                        this.mLastContentTotalWatchedTime = 0;
                        this.playRequestSent = true;
                    }
                    if (j <= 0) {
                        if (videoAsset.getAssetType() != 1) {
                            this.mStartTime = 0;
                            this.mRequiredSeek = false;
                            this.mRequiredSeekAfterAds = false;
                            this.mStartTimeAfterAds = this.mStartTime;
                            this.isHasAD = false;
                            if (videoAsset.getAssetType() == 3 || videoAsset.getAssetType() == 2 || videoAsset.getAssetType() == 1) {
                                if (this.mVideoAsset == null || videoAsset.getAssetType() != 3) {
                                    if (videoAsset.getCuePoints() == null) {
                                        this.mCuePoints = new Float[(videoAsset.getCuePoints().length + 2)];
                                        this.mCuePoints[0] = Float.valueOf(0.0f);
                                        j = null;
                                        while (j < videoAsset.getCuePoints().length) {
                                            i2 = j + 1;
                                            this.mCuePoints[i2] = Float.valueOf(videoAsset.getCuePoints()[j]);
                                            j = i2;
                                        }
                                        this.mCuePoints[videoAsset.getCuePoints().length + 1] = Float.valueOf(((float) this.mVideoAsset.getRunTime()) + 1.0f);
                                    } else {
                                        this.mCuePoints = new Float[2];
                                        this.mCuePoints[0] = Float.valueOf(0.0f);
                                        this.mCuePoints[1] = Float.valueOf(((float) this.mVideoAsset.getRunTime()) + 1.0f);
                                    }
                                    this.mCurrentCueIndex = 0;
                                    if (!(this.mCuePoints == null || this.mCuePoints.length == null)) {
                                        j = new long[this.mCuePoints.length];
                                        zArr = new boolean[this.mCuePoints.length];
                                        this.adPlay = new boolean[this.mCuePoints.length];
                                        for (i2 = 0; i2 < this.mCuePoints.length; i2++) {
                                            j[i2] = (long) (this.mCuePoints[i2].floatValue() * 1000.0f);
                                            zArr[i2] = false;
                                            this.adPlay[i2] = false;
                                        }
                                        this.mExoPlayerView.setExtraAdGroupMarkers(j, zArr);
                                    }
                                    if (this.mStartTime > 0) {
                                        while (i < this.mCuePoints.length) {
                                            j = i + 1;
                                            if (j > this.mCuePoints.length - 1 && ((float) this.mStartTime) >= this.mCuePoints[i].floatValue() * 1000.0f && ((float) this.mStartTime) < this.mCuePoints[j].floatValue() * 1000.0f) {
                                                this.mCurrentCueIndex = i;
                                                break;
                                            }
                                            i = j;
                                        }
                                    }
                                    XumoDataSync.getInstance().getPlayerProvider(new Listener() {
                                        public void onCompletion(Object obj) {
                                            if (XumoExoPlayer.this.isAppRunning()) {
                                                XumoExoPlayer.this.mPlayerProvider = (PlayerProvider) obj;
                                                XumoExoPlayer.this.getProvider(videoAsset);
                                            }
                                        }

                                        public void onError() {
                                            if (XumoExoPlayer.this.isAppRunning()) {
                                                XumoExoPlayer.this.mPlayerProvider = new PlayerProvider();
                                                XumoExoPlayer.this.getProvider(videoAsset);
                                            }
                                        }
                                    });
                                } else {
                                    finishPlay();
                                    return;
                                }
                            }
                        }
                    }
                    this.mStartTime = j;
                    this.mRequiredSeek = true;
                    this.mRequiredSeekAfterAds = true;
                    this.mStartTimeAfterAds = this.mStartTime;
                    this.isHasAD = false;
                    if (this.mVideoAsset == null) {
                    }
                    if (videoAsset.getCuePoints() == null) {
                        this.mCuePoints = new Float[2];
                        this.mCuePoints[0] = Float.valueOf(0.0f);
                        this.mCuePoints[1] = Float.valueOf(((float) this.mVideoAsset.getRunTime()) + 1.0f);
                    } else {
                        this.mCuePoints = new Float[(videoAsset.getCuePoints().length + 2)];
                        this.mCuePoints[0] = Float.valueOf(0.0f);
                        j = null;
                        while (j < videoAsset.getCuePoints().length) {
                            i2 = j + 1;
                            this.mCuePoints[i2] = Float.valueOf(videoAsset.getCuePoints()[j]);
                            j = i2;
                        }
                        this.mCuePoints[videoAsset.getCuePoints().length + 1] = Float.valueOf(((float) this.mVideoAsset.getRunTime()) + 1.0f);
                    }
                    this.mCurrentCueIndex = 0;
                    j = new long[this.mCuePoints.length];
                    zArr = new boolean[this.mCuePoints.length];
                    this.adPlay = new boolean[this.mCuePoints.length];
                    for (i2 = 0; i2 < this.mCuePoints.length; i2++) {
                        j[i2] = (long) (this.mCuePoints[i2].floatValue() * 1000.0f);
                        zArr[i2] = false;
                        this.adPlay[i2] = false;
                    }
                    this.mExoPlayerView.setExtraAdGroupMarkers(j, zArr);
                    if (this.mStartTime > 0) {
                        while (i < this.mCuePoints.length) {
                            j = i + 1;
                            if (j > this.mCuePoints.length - 1) {
                            }
                            i = j;
                        }
                    }
                    XumoDataSync.getInstance().getPlayerProvider(/* anonymous class already generated */);
                }
            }
        }
    }

    private void getProvider(final VideoAsset videoAsset) {
        if (videoAsset != null) {
            XumoDataSync.getInstance().getProvider(this.mVideoAsset != null ? this.mVideoAsset.getProviderId() : 0, new Listener() {
                public void onCompletion(Object obj) {
                    if (XumoExoPlayer.this.isAppRunning()) {
                        if (XumoExoPlayer.this.mVideoAsset != null) {
                            if (XumoExoPlayer.this.mVideoAsset == videoAsset) {
                                Provider provider = (Provider) obj;
                                if (!(provider == null || TextUtils.isEmpty(provider.getAdTag()))) {
                                    if (provider.isPreroll()) {
                                        XumoExoPlayer.this.isHasAD = true;
                                    } else {
                                        XumoExoPlayer.this.isHasAD = XumoExoPlayer.this.readyToPlayAds();
                                    }
                                    XumoExoPlayer.this.mADtag = provider.getAdTag();
                                    XumoExoPlayer.this.mProviderName = provider.getName();
                                }
                                XumoExoPlayer.this.mCurrentCueIndex = XumoExoPlayer.this.mCurrentCueIndex + 1;
                                XumoExoPlayer.this.requestNextAd(XumoExoPlayer.this.getNextAdUri());
                            }
                        }
                        LogUtil.d("getProvider video asset difference.");
                    }
                }

                public void onError() {
                    XumoExoPlayer.this.prepareNextVideo();
                }
            });
        }
    }

    private void getAmazon() {
        this.mADtag = "";
        if (this.adBreakPattern != null && this.provider != null) {
            AmazonFireTVAdRequest.builder().withAppID(AmazonUtil.APP_ID).withContext(this.mContext).withAdBreakPattern(this.adBreakPattern).withTimeOut(Long.valueOf(1000)).withCallback(new AmazonFireTVAdCallback() {
                public void onSuccess(AmazonFireTVAdResponse amazonFireTVAdResponse) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("APS: Bids received from amazon :");
                    stringBuilder.append(amazonFireTVAdResponse.getReasonString());
                    Log.e("AmazonAd", stringBuilder.toString());
                    amazonFireTVAdResponse = amazonFireTVAdResponse.getAdServerTargetingParams();
                    if (!(amazonFireTVAdResponse == null || amazonFireTVAdResponse.size() <= 0 || AmazonUtil.getAmazonAdUrl(amazonFireTVAdResponse).isEmpty())) {
                        String substring = XumoExoPlayer.this.provider.getAdTag().substring(0, XumoExoPlayer.this.provider.getAdTag().lastIndexOf(";"));
                        String substring2 = XumoExoPlayer.this.provider.getAdTag().substring(XumoExoPlayer.this.provider.getAdTag().lastIndexOf(";"));
                        XumoExoPlayer xumoExoPlayer = XumoExoPlayer.this;
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append(substring);
                        stringBuilder2.append("&");
                        stringBuilder2.append(AmazonUtil.getAmazonAdUrl(amazonFireTVAdResponse));
                        stringBuilder2.append(substring2);
                        xumoExoPlayer.mADtag = stringBuilder2.toString();
                        Log.e("AmazonAd", XumoExoPlayer.this.mADtag);
                        XumoExoPlayer.this.mProviderName = XumoExoPlayer.this.provider.getName();
                    }
                    XumoExoPlayer.this.requestNextAd(XumoExoPlayer.this.getNextAdUri());
                }

                public void onFailure(AmazonFireTVAdResponse amazonFireTVAdResponse) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("APS: No bids were returned from amazon. Reason:");
                    stringBuilder.append(amazonFireTVAdResponse.getReasonString());
                    Log.e("AmazonAd", stringBuilder.toString());
                    XumoExoPlayer.this.requestNextAd(XumoExoPlayer.this.mADtag);
                }
            }).build().executeRequest();
        }
    }

    private void prepareNextVideo() {
        if (this.mSimpleExoPlayer != null) {
            clearAds();
            this.mSimpleExoPlayer.prepare(this.videoSource);
        }
    }

    private void requestNextAd(@Nullable String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.isHasAD);
        stringBuilder.append("");
        LogUtil.d("AD_log_isHasAD", stringBuilder.toString());
        if (TextUtils.isEmpty(str)) {
            LogUtil.d("AD_log_adTag", "空");
        } else {
            LogUtil.d("AD_log_adTag", str);
        }
        if ((this.mExoPlayerView != null && this.mSimpleExoPlayer != null) || isAppRunning()) {
            if (!isAdDisplayed() || (this.mVideoAsset.getAssetType() != 1 && playingValidFbAds())) {
                clearAds();
                if (!this.isHasAD) {
                    if (!this.mQueuedVideoAsset) {
                        if (this.mCuePoints != null) {
                            if (this.mCurrentCueIndex >= this.mCuePoints.length) {
                                finishPlay();
                                return;
                            }
                        }
                        this.mSimpleExoPlayer.prepare(this.videoSource);
                        if (this.mCurrentCueIndex == 1) {
                            updateNowPlayingAdListener(false);
                        } else if (this.mVideoAsset != null) {
                            createAdBeaconStateCreated();
                            sendAdBeacon(AdBeaconEvents.AdError, getContentPosition(), VideoBeaconErrors.MediaNoError.rawValue());
                            resumeAfterAdStop();
                        }
                        return;
                    }
                }
                if (TextUtils.isEmpty(str)) {
                    prepareNextVideo();
                    if (this.mVideoAsset != null) {
                        createAdBeaconStateCreated();
                        sendAdBeacon(AdBeaconEvents.AdError, getContentPosition(), VideoBeaconErrors.MediaNoError.rawValue());
                        resumeAfterAdStop();
                    }
                } else {
                    this.mIsAdDisplayedCopy = true;
                    if (isControllerVisible()) {
                        hideController();
                    }
                    setUseController(false);
                    createAdBeaconStateCreated();
                    sendAdBeacon(AdBeaconEvents.AdRequested, 0, VideoBeaconErrors.MediaNoError.rawValue());
                    this.mAdTagUrl = str;
                    this.mXumoImaAdsLoader = new Builder(this.mContext).setAdEventListener(this).buildForAdTag(Uri.parse(this.mAdTagUrl));
                    this.mXumoImaAdsLoader.getAdsLoader().addAdErrorListener(this);
                    this.mSimpleExoPlayer.prepare(new AdsMediaSource(this.videoSource, this.mDataSourceFactory, this.mXumoImaAdsLoader, this.mExoPlayerView.getOverlayFrameLayout()));
                    if (isRequiredSeekAfterAds() != null) {
                        try {
                            this.mSimpleExoPlayer.seekTo(this.mStartTimeAfterAds);
                        } catch (String str2) {
                            StringBuilder stringBuilder2 = new StringBuilder();
                            stringBuilder2.append("Failed to seek time: ");
                            stringBuilder2.append(str2.getMessage());
                            LogUtil.e(stringBuilder2.toString());
                        }
                    }
                    resetAdWatchdog();
                }
                return;
            }
            this.mQueuedVideoAsset = true;
        }
    }

    private void clearAds() {
        if (isAppRunning() && this.mXumoImaAdsLoader != null) {
            this.mXumoImaAdsLoader.release();
        }
        this.mIsAdDisplayed = false;
        this.mIsAdDisplayedCopy = false;
        setUseController(true);
        this.mIsAdDisplayedSaved = false;
        this.mCurrentAdEvent = null;
        updateNowPlayingAdListener(false);
    }

    public void play() {
        if (this.mIsSeekto) {
            sendContentBeacon(VideoBeaconEvents.SeekEnded, null);
            this.mIsSeekto = false;
        }
        if (this.mSimpleExoPlayer != null && !playingValidFbAds()) {
            this.mShouldAutoPlay = true;
            this.mSimpleExoPlayer.setPlayWhenReady(true);
            BeaconUtil.stopKeepAliveImpressionsBeaconTimer();
            this.mMoviesControllerSkipMultiple = 0;
            this.mVodSkipFwdText.setText("");
            this.mVodSkipBackText.setText("");
            if (!(this.mIsLive || isAdDisplayed() || this.mExoPlayerView == null)) {
                this.mPlayButton.setVisibility(4);
                this.mPauseButton.setVisibility(0);
                this.handler.removeCallbacks(this.runnable);
            }
        }
    }

    public void rePlay() {
        if (this.mIsSeekto) {
            sendContentBeacon(VideoBeaconEvents.SeekEnded, null);
            this.mIsSeekto = false;
        }
        this.mShouldAutoPlay = true;
        this.mSimpleExoPlayer.setPlayWhenReady(true);
        BeaconUtil.stopKeepAliveImpressionsBeaconTimer();
        this.mMoviesControllerSkipMultiple = 0;
        this.mVodSkipFwdText.setText("");
        this.mVodSkipBackText.setText("");
        if (this.mExoPlayerView != null) {
            this.mPlayButton.setVisibility(4);
            this.mPauseButton.setVisibility(0);
            this.handler.removeCallbacks(this.runnable);
        }
    }

    public void pause() {
        if (this.mSimpleExoPlayer != null) {
            BeaconUtil.startKeepAliveImpressionsBeaconTimerIfNecessary();
            this.mShouldAutoPlay = false;
            this.mSimpleExoPlayer.setPlayWhenReady(false);
            if (!(this.mIsLive || isAdDisplayed() || this.mExoPlayerView == null)) {
                this.mPlayButton.setVisibility(0);
                this.mPauseButton.setVisibility(4);
                this.handler.removeCallbacks(this.runnable);
            }
        }
    }

    public void stop() {
        stop(false);
    }

    public void stop(boolean z) {
        this.isForceStop = true;
        if (this.playRequestSent) {
            sendContentBeacon(VideoBeaconEvents.PlayStopped, null);
            this.playRequestSent = false;
        }
        if ((z || !isAdDisplayed()) && this.mSimpleExoPlayer) {
            pause();
            clearAds();
            this.mSimpleExoPlayer.stop();
        }
    }

    public boolean getPlayWhenReady() {
        return this.mSimpleExoPlayer != null && this.mSimpleExoPlayer.getPlayWhenReady();
    }

    public void setOnNowLive(OnNowLive onNowLive) {
        this.mOnNowLive = onNowLive;
    }

    public OnNowLive getOnNowLive() {
        return this.mOnNowLive;
    }

    public void setVideoAsset(VideoAsset videoAsset) {
        this.mVideoAsset = videoAsset;
    }

    public VideoAsset getVideoAsset() {
        return this.mVideoAsset;
    }

    public void showController() {
        if (this.mExoPlayerView != null && !isAdDisplayed()) {
            this.mExoPlayerView.showController();
        }
    }

    public void hideController() {
        if (this.mExoPlayerView != null) {
            this.mExoPlayerView.hideController();
        }
    }

    public void setUseController(boolean z) {
        if (this.mExoPlayerView != null) {
            this.mExoPlayerView.setUseController(z);
        }
    }

    public void setControllerVisibilityListener(VisibilityListener visibilityListener) {
        if (this.mExoPlayerView != null) {
            this.mExoPlayerView.setControllerVisibilityListener(visibilityListener);
        }
    }

    public boolean isControllerVisible() {
        return this.mExoPlayerView != null && this.mExoPlayerView.isControllerVisible();
    }

    public long getContentPosition() {
        return this.mSimpleExoPlayer != null ? this.mSimpleExoPlayer.getContentPosition() : 0;
    }

    public long getCurrentPosition() {
        return this.mSimpleExoPlayer != null ? this.mSimpleExoPlayer.getCurrentPosition() : 0;
    }

    public Timeline getCurrentTimeline() {
        return this.mSimpleExoPlayer != null ? this.mSimpleExoPlayer.getCurrentTimeline() : null;
    }

    public void release() {
        this.mIsAdDisplayedSaved = this.mIsAdDisplayed;
        this.mIsAdDisplayed = false;
        this.mIsAdDisplayedCopy = false;
        this.mLastContentPosition = getContentPosition();
        if (this.mSimpleExoPlayer != null) {
            removePlayerView();
            invalidateWatchedBeaconTimer();
            this.mShouldAutoPlay = this.mSimpleExoPlayer.getPlayWhenReady();
            if (this.mSimpleExoPlayer != null) {
                this.mSimpleExoPlayer.stop();
                this.mSimpleExoPlayer.release();
                this.mSimpleExoPlayer = null;
            }
            if (this.mXumoImaAdsLoader != null) {
                this.mXumoImaAdsLoader.release();
            }
            this.mTrackSelector = null;
        }
        wakeLockRelease();
        this.mPlayerReleased = true;
    }

    private boolean isAppRunning() {
        return this.mContext instanceof XumoApplication ? ((XumoApplication) this.mContext).isRunning() : true;
    }

    private void wakeLockAcquire() {
        PowerManager powerManager = (PowerManager) this.mContext.getSystemService("power");
        if (this.mWakelock == null) {
            this.mWakelock = powerManager.newWakeLock(536870922, "Xumo:XumoPlayerController");
        }
        if (!this.mWakelock.isHeld()) {
            LogUtil.d("wakeLockAcquire");
            this.mWakelock.acquire();
        }
    }

    private void wakeLockRelease() {
        if (this.mWakelock != null && this.mWakelock.isHeld()) {
            LogUtil.d("wakeLockRelease");
            this.mWakelock.release();
        }
    }

    public void setTitle(String str) {
        if (this.mExoPlayerView != null && this.mTitleTextView != null) {
            this.mTitleTextView.setText(str);
            this.mTitleTextView.setTextSize(2, 15.0f);
        }
    }

    public void setMoviesTitle(String str) {
        if (this.mExoPlayerView != null && this.mTitleTextView != null) {
            this.mTitleTextView.setText(str);
            this.mTitleTextView.setTextSize(2, 30.0f);
        }
    }

    public void setDescription(String str) {
        if (this.mExoPlayerView != null && this.mDescriptionTextView != null) {
            this.mDescriptionTextView.setText(str);
        }
    }

    private boolean isAdDisplayed() {
        return this.mIsAdDisplayed;
    }

    public boolean isLive() {
        return this.mIsLive;
    }

    private MediaSource buildMediaSource(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            LogUtil.d("buildMediaSource videoURL is null or empty.");
            return null;
        }
        MediaSource createMediaSource;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("buildMediaSource videoURL=");
        stringBuilder.append(str);
        LogUtil.d(stringBuilder.toString());
        Uri parse = Uri.parse(str);
        str = Util.inferContentType(Uri.parse(str));
        switch (str) {
            case null:
                this.mFallbackAfterHLSLoadFail = false;
                createMediaSource = new DashMediaSource.Factory(this.dataSourceFactory).setManifestParser(new FilteringManifestParser(new DashManifestParser(), getOfflineStreamKeys(parse))).createMediaSource(parse);
                break;
            case 1:
                this.mFallbackAfterHLSLoadFail = false;
                createMediaSource = new SsMediaSource.Factory(this.mDataSourceFactory).createMediaSource(parse);
                break;
            case 2:
            case 3:
                if (!this.mFallbackAfterHLSLoadFail) {
                    createMediaSource = new HlsMediaSource.Factory(this.mDataSourceFactory).createMediaSource(parse);
                    if (str == 3) {
                        this.mFallbackAfterHLSLoadFail = true;
                        break;
                    }
                }
                this.mFallbackAfterHLSLoadFail = false;
                createMediaSource = new ExtractorMediaSource.Factory(this.mDataSourceFactory).setExtractorsFactory(this.mExtractorsFactory).createMediaSource(parse);
                this.mSimpleExoPlayer.prepare(createMediaSource, true, false);
                break;
                break;
            default:
                this.mFallbackAfterHLSLoadFail = false;
                createMediaSource = new ExtractorMediaSource.Factory(this.mDataSourceFactory).setExtractorsFactory(this.mExtractorsFactory).createMediaSource(parse);
                this.mSimpleExoPlayer.prepare(createMediaSource, true, false);
                break;
        }
        str = new SingleSampleMediaSource.Factory(this.mDataSourceFactory).createMediaSource(Uri.parse(str2), Format.createTextSampleFormat(null, MimeTypes.APPLICATION_SUBRIP, 1, Locale.getDefault().getLanguage()), C.TIME_UNSET);
        str2 = new MergingMediaSource(createMediaSource, str);
        if (this.mVideoAsset.ismHasCaption() != null) {
            str2.addEventListener(this.mMainHandler, this);
            return str2;
        }
        createMediaSource.addEventListener(this.mMainHandler, this);
        return createMediaSource;
    }

    public void onPlayerStateChanged(boolean z, int i) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("playWhenReady=");
        stringBuilder.append(z);
        String stringBuilder2 = stringBuilder.toString();
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(" playbackState=");
        stringBuilder3.append(i);
        LogUtil.d(stringBuilder2, stringBuilder3.toString());
        if (i == 4) {
            sendContentBeacon(VideoBeaconEvents.PlayEnded, null);
            long contentPosition = getContentPosition();
            if (contentPosition > getVideoDuration()) {
                contentPosition = getVideoDuration();
            }
            if (this.mVideoAsset != null && this.mCuePoints != null && this.mCurrentCueIndex == this.mCuePoints.length - 1 && Math.abs((this.mVideoAsset.getRunTime() * 1000) - contentPosition) <= 1000 && readyToPlayAds()) {
                this.mStartTimeAfterAds = contentPosition;
                this.isHasAD = readyToPlayAds();
                this.mCurrentCueIndex++;
                requestNextAd(getNextAdUri());
            } else if (!isAdDisplayed() && (this.mAdWatchdog == null || !this.mAdWatchdog.isRunning())) {
                finishPlay();
            }
        } else if (z && i == 3) {
            boolean isTarget = OmnitureBeaconUtil.isTarget(this.mVideoAsset);
        } else if (z && i == 2) {
            if (!this.mIsAdDisplayedCopy && this.playRequestSent) {
                saveVideoTotalWatchedTime();
            }
            if (!this.isForceStop) {
                sendContentBeacon(VideoBeaconEvents.PlayStalled, null);
            }
        }
        if ((z && (i == 3 || i == 2)) || (playingValidFbAds() && isAppRunning())) {
            wakeLockAcquire();
        } else {
            wakeLockRelease();
        }
        if (i != 2 || isAdDisplayed()) {
            stopBufWatchdog();
        } else {
            resetBufWatchdog();
        }
        if (this.mBeaconTimerTask == null || (!isAdDisplayed() && this.mBeaconTimerTask.runForAds)) {
            clearVideoTotalWatchedTime();
            startWatchedBeaconTimer(false);
        }
        if (!z && i == 3) {
            this.isPause = true;
            if (!this.isForceStop) {
                sendContentBeacon(VideoBeaconEvents.PlayPaused, null);
            }
        }
        if (z && i == 3 && this.firstPrepareVideo) {
            this.firstPrepareVideo = false;
            sendContentBeacon(VideoBeaconEvents.PlaySuccess, null);
        }
        if (z && i == 3 && this.isPause) {
            this.isPause = false;
            sendContentBeacon(VideoBeaconEvents.PlayResumed, null);
        }
    }

    @androidx.annotation.Nullable
    private java.lang.String getNextAdUri() {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r7 = this;
        r0 = r7.mUseTestAds;
        if (r0 == 0) goto L_0x0008;
    L_0x0004:
        r0 = "https://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/ad_rule_samples&ciu_szs=300x250&ad_rule=1&impl=s&gdfp_req=1&env=vp&output=vmap&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ar%3Dpremidpostpod&cmsid=496&vid=short_onecue&correlator=";
        goto L_0x010e;
    L_0x0008:
        r0 = r7.mADtag;
        r0 = android.text.TextUtils.isEmpty(r0);
        if (r0 != 0) goto L_0x010d;
    L_0x0010:
        r0 = r7.mVideoAsset;
        if (r0 == 0) goto L_0x010d;
    L_0x0014:
        r0 = r7.mADtag;
        r1 = "";
        r2 = r7.mVideoAsset;	 Catch:{ UnsupportedEncodingException -> 0x0026 }
        r2 = r2.getUrl();	 Catch:{ UnsupportedEncodingException -> 0x0026 }
        r3 = "UTF-8";	 Catch:{ UnsupportedEncodingException -> 0x0026 }
        r2 = java.net.URLEncoder.encode(r2, r3);	 Catch:{ UnsupportedEncodingException -> 0x0026 }
        r1 = r2;
        goto L_0x0040;
    L_0x0026:
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "Failed to encode URL: ";
        r2.append(r3);
        r3 = r7.mVideoAsset;
        r3 = r3.getUrl();
        r2.append(r3);
        r2 = r2.toString();
        com.xumo.xumo.util.LogUtil.w(r2);
    L_0x0040:
        r2 = com.xumo.xumo.repository.UserPreferences.getInstance();
        r2 = r2.getDeviceId();
        r3 = "XumoDeviceId ";
        r4 = "";
        r2 = r2.replace(r3, r4);
        r3 = "[CHANNEL_ID]";
        r4 = r7.mVideoAsset;
        r4 = r4.getChannelId();
        r0 = r0.replace(r3, r4);
        r3 = "[CATEGORY_ID]";
        r4 = r7.mVideoAsset;
        r4 = r4.getCategoryId();
        r0 = r0.replace(r3, r4);
        r3 = "[ASSET_ID]";
        r4 = r7.mVideoAsset;
        r4 = r4.getAssetId();
        r0 = r0.replace(r3, r4);
        r3 = "[RANDOM_NUMBER_8]";
        r4 = 8;
        r4 = com.xumo.xumo.util.XumoUtil.getRandomNumber(r4);
        r0 = r0.replace(r3, r4);
        r3 = "[PROVIDER_ASSET_ID]";
        r4 = r7.mVideoAsset;
        r4 = r4.getProviderAssetId();
        r0 = r0.replace(r3, r4);
        r3 = "[PROVIDER_ID]";
        r4 = r7.mVideoAsset;
        r4 = r4.getProviderId();
        r4 = java.lang.String.valueOf(r4);
        r0 = r0.replace(r3, r4);
        r3 = "[PROVIDER_NAME]";
        r4 = r7.mProviderName;
        r5 = " ";
        r6 = "";
        r4 = r4.replace(r5, r6);
        r0 = r0.replace(r3, r4);
        r3 = "[timestamp]";
        r4 = java.lang.System.currentTimeMillis();
        r4 = java.lang.String.valueOf(r4);
        r0 = r0.replace(r3, r4);
        r3 = "[referrer_url]";
        r0 = r0.replace(r3, r1);
        r3 = "[description_url]";
        r0 = r0.replace(r3, r1);
        r1 = "[IFA]";
        r0 = r0.replace(r1, r2);
        r1 = r7.mCurrentCueIndex;
        if (r1 != 0) goto L_0x00d9;
    L_0x00d0:
        r1 = "[VPOS]";
        r2 = "preroll";
        r0 = r0.replace(r1, r2);
        goto L_0x00fb;
    L_0x00d9:
        r1 = r7.mCuePoints;
        if (r1 == 0) goto L_0x00f3;
    L_0x00dd:
        r1 = r7.mCurrentCueIndex;
        if (r1 <= 0) goto L_0x00f3;
    L_0x00e1:
        r1 = r7.mCurrentCueIndex;
        r2 = r7.mCuePoints;
        r2 = r2.length;
        r2 = r2 + -1;
        if (r1 >= r2) goto L_0x00f3;
    L_0x00ea:
        r1 = "[VPOS]";
        r2 = "midroll";
        r0 = r0.replace(r1, r2);
        goto L_0x00fb;
    L_0x00f3:
        r1 = "[VPOS]";
        r2 = "postroll";
        r0 = r0.replace(r1, r2);
    L_0x00fb:
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "https:";
        r1.append(r2);
        r1.append(r0);
        r0 = r1.toString();
        goto L_0x010e;
    L_0x010d:
        r0 = 0;
    L_0x010e:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xumo.xumo.player.XumoExoPlayer.getNextAdUri():java.lang.String");
    }

    public void onPlayerError(ExoPlaybackException exoPlaybackException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("error=");
        stringBuilder.append(exoPlaybackException.getMessage());
        LogUtil.w(stringBuilder.toString());
        this.mErrorState = true;
        if (this.mPlayerEventListener != null) {
            this.mPlayerEventListener.onPlayerError(exoPlaybackException.type);
        }
        VideoBeaconErrors videoBeaconErrors = VideoBeaconErrors.MediaNoError;
        if (exoPlaybackException.type == 2) {
            prepare(this.mVideoAsset);
            videoBeaconErrors = VideoBeaconErrors.MediaAborted;
        } else if (exoPlaybackException.type == 1) {
            videoBeaconErrors = VideoBeaconErrors.MediaDecodeError;
        } else if (exoPlaybackException.type == null) {
            videoBeaconErrors = VideoBeaconErrors.MediaNetworkError;
        }
        this.isPause = null;
        sendContentBeacon(VideoBeaconEvents.PlayError, videoBeaconErrors);
    }

    public void onLoadStarted(int i, @Nullable MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        LogUtil.d("onLoadStarted");
        if (this.mSimpleExoPlayer != 0 && this.mSimpleExoPlayer.isCurrentWindowDynamic() == 0 && this.mRequiredSeek != 0 && isAdDisplayed() == 0) {
            this.mSimpleExoPlayer.seekTo(this.mStartTime);
            this.mStartTime = 0;
            this.mRequiredSeek = false;
        }
    }

    public void onLoadCompleted(int i, @Nullable MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        i = new StringBuilder();
        i.append("elapsedRealtimeMs=");
        i.append(loadEventInfo.elapsedRealtimeMs);
        LogUtil.d(i.toString());
        this.mFallbackAfterHLSLoadFail = false;
        if (this.mSimpleExoPlayer != 0) {
            i = new StringBuilder();
            i.append("getDuration=");
            i.append(this.mSimpleExoPlayer.getDuration());
            LogUtil.d(i.toString());
        }
    }

    public void onLoadCanceled(int i, @Nullable MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
        LogUtil.d("onLoadCanceled");
    }

    public void onLoadError(int i, @Nullable MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z) {
        i = new StringBuilder();
        i.append("onLoadError ");
        i.append(iOException.getMessage());
        LogUtil.d(i.toString());
        if (this.mFallbackAfterHLSLoadFail != 0) {
            prepare(this.mVideoAsset, this.mStartTime);
        }
    }

    public void onUpstreamDiscarded(int i, MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
        LogUtil.d("onUpstreamDiscarded");
    }

    public void onDownstreamFormatChanged(int i, @Nullable MediaPeriodId mediaPeriodId, MediaLoadData mediaLoadData) {
        LogUtil.d("onDownstreamFormatChanged");
    }

    public void onScrubStart(TimeBar timeBar, long j) {
        sendContentBeacon(VideoBeaconEvents.SeekStarted, 0);
    }

    public void onScrubStop(TimeBar timeBar, long j, boolean z) {
        timeBar = null;
        if (j == 0) {
            while (timeBar < this.mCuePoints.length) {
                if (this.adPlay[timeBar] == null) {
                    this.mCurrentCueIndex = timeBar;
                }
                timeBar++;
            }
        } else if (j == this.mSimpleExoPlayer.getDuration()) {
            this.mCurrentCueIndex = this.mCuePoints.length - 1;
        } else {
            while (timeBar < this.mCuePoints.length) {
                z = timeBar + 1;
                if (z <= this.mCuePoints.length - 1) {
                    float f = (float) j;
                    if (f >= this.mCuePoints[timeBar].floatValue() * 1000.0f && f < this.mCuePoints[z].floatValue() * 1000.0f && !this.adPlay[timeBar]) {
                        this.mCurrentCueIndex = timeBar;
                        break;
                    }
                }
                timeBar = z;
            }
        }
        sendContentBeacon(VideoBeaconEvents.SeekEnded, 0);
    }

    public void onAudioBecomeNoisy() {
        if (this.mSimpleExoPlayer != null) {
            int playbackState = this.mSimpleExoPlayer.getPlaybackState();
            if (isAdDisplayed()) {
                stop();
            } else if (!getPlayWhenReady() && (playbackState == 3 || playbackState == 2)) {
                play();
            }
        }
    }

    public VideoProgressUpdate getContentProgress() {
        if (this.mSimpleExoPlayer != null) {
            if (this.mSimpleExoPlayer.getDuration() > 0) {
                return new VideoProgressUpdate(this.mSimpleExoPlayer.getCurrentPosition(), this.mSimpleExoPlayer.getDuration());
            }
        }
        return VideoProgressUpdate.VIDEO_TIME_NOT_READY;
    }

    public void onAdError(AdErrorEvent adErrorEvent) {
        Log.e("XumoExoPlayer", adErrorEvent.getError().getMessage());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AD_log_adErrorEvent=");
        stringBuilder.append(adErrorEvent.getError().getMessage());
        LogUtil.w(stringBuilder.toString());
        stopBufWatchdog();
        sendAdBeacon(AdBeaconEvents.AdError, getContentPosition(), adErrorEvent.getError().getErrorCodeNumber());
        if (this.mCurrentAdEvent == null || this.mCurrentAdEvent.getAd() == null || this.mCurrentAdEvent.getAd().getAdPodInfo() == null || this.mCurrentAdEvent.getAd().getAdPodInfo().getAdPosition() == this.mCurrentAdEvent.getAd().getAdPodInfo().getTotalAds()) {
            resumeAfterAdStop();
        } else {
            resetAdWatchdog();
        }
    }

    public void onAdEvent(AdEvent adEvent) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("adEvent.getType():");
        stringBuilder.append(adEvent.getType());
        Log.e("XumoExoPlayer", stringBuilder.toString());
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Event: ");
        stringBuilder2.append(adEvent.getType());
        LogUtil.i(stringBuilder2.toString());
        stopBufWatchdog();
        this.mCurrentAdEvent = adEvent;
        if (!(adEvent.getAd() == null || adEvent.getAd().getAdPodInfo() == null || this.mAdBeaconState == null)) {
            this.mAdBeaconState.updateAdPod(adEvent.getAd().getAdPodInfo().getAdPosition());
        }
        switch (adEvent.getType()) {
            case LOADED:
                resetAdWatchdog();
                this.mLastAdPlayTime = System.currentTimeMillis();
                this.adPlay[this.mCurrentCueIndex - 1] = 1;
                return;
            case FIRST_QUARTILE:
                resetAdWatchdog();
                sendAdBeacon(AdBeaconEvents.AdPercentile, getContentPosition(), VideoBeaconErrors.MediaNoError.rawValue());
                return;
            case MIDPOINT:
                resetAdWatchdog();
                sendAdBeacon(AdBeaconEvents.AdPercentile, getContentPosition(), VideoBeaconErrors.MediaNoError.rawValue());
                return;
            case THIRD_QUARTILE:
                resetAdWatchdog();
                sendAdBeacon(AdBeaconEvents.AdPercentile, getContentPosition(), VideoBeaconErrors.MediaNoError.rawValue());
                return;
            case SKIPPED:
                sendAdBeacon(AdBeaconEvents.AdSkipped, getContentPosition(), VideoBeaconErrors.MediaNoError.rawValue());
                this.mIsAdDisplayed = false;
                this.mIsAdDisplayedCopy = false;
                updateNowPlayingAdListener(false);
                this.mLastAdPlayTime = System.currentTimeMillis();
                resumeAfterAdStop();
                return;
            case PAUSED:
                resetAdWatchdog();
                sendAdBeacon(AdBeaconEvents.AdPaused, 0, VideoBeaconErrors.MediaNoError.rawValue());
                return;
            case RESUMED:
                resetAdWatchdog();
                sendAdBeacon(AdBeaconEvents.AdResumed, 0, VideoBeaconErrors.MediaNoError.rawValue());
                return;
            case COMPLETED:
                sendAdBeacon(AdBeaconEvents.AdCompleted, 0, VideoBeaconErrors.MediaNoError.rawValue());
                this.mIsAdDisplayed = false;
                this.mIsAdDisplayedCopy = false;
                updateNowPlayingAdListener(false);
                if (adEvent.getAd().getAdPodInfo().getAdPosition() == adEvent.getAd().getAdPodInfo().getTotalAds()) {
                    this.mLastAdPlayTime = System.currentTimeMillis();
                    resumeAfterAdStop();
                    return;
                }
                return;
            case ALL_ADS_COMPLETED:
                resetAdWatchdog();
                this.mIsAdDisplayed = false;
                this.mIsAdDisplayedCopy = false;
                return;
            case STARTED:
                resetAdWatchdog();
                sendAdBeacon(AdBeaconEvents.AdStarted, 0, VideoBeaconErrors.MediaNoError.rawValue());
                startWatchedBeaconTimer(true);
                this.mIsAdDisplayed = true;
                this.mIsAdDisplayedCopy = true;
                updateNowPlayingAdListener(true);
                return;
            case CLICKED:
                sendAdBeacon(AdBeaconEvents.AdCompleted, getContentPosition(), VideoBeaconErrors.MediaNoError.rawValue());
                this.mIsAdDisplayed = false;
                this.mIsAdDisplayedCopy = false;
                updateNowPlayingAdListener(false);
                this.mLastAdPlayTime = System.currentTimeMillis();
                resumeAfterAdStop();
                return;
            case TAPPED:
                resetAdWatchdog();
                return;
            case CONTENT_PAUSE_REQUESTED:
                resetAdWatchdog();
                return;
            case CONTENT_RESUME_REQUESTED:
                resetAdWatchdog();
                this.mIsAdDisplayed = false;
                this.mIsAdDisplayedCopy = false;
                return;
            case AD_PROGRESS:
                this.mIsAdDisplayed = true;
                this.mIsAdDisplayedCopy = true;
                updateNowPlayingAdListener(true);
                return;
            default:
                return;
        }
    }

    private void stopAdWatchdog() {
        if (this.mAdWatchdog != null) {
            this.mAdWatchdog.stopWatchdog();
        }
    }

    private void resetAdWatchdog() {
        if (this.mAdWatchdog != null) {
            this.mAdWatchdog.resetWatchdog();
        }
    }

    private void stopBufWatchdog() {
        if (this.mBufWatchdog != null) {
            this.mBufWatchdog.stopWatchdog();
        }
    }

    private void resetBufWatchdog() {
        if (this.mBufWatchdog != null) {
            this.mBufWatchdog.resetWatchdog();
        }
    }

    private void createAdBeaconStateCreated() {
        if (this.mCuePoints != null && 2 <= this.mCuePoints.length) {
            if (this.mCurrentCueIndex == 1) {
                this.mAdBeaconState = new AdBeaconState(AdPlacement.PreRoll);
            } else if (1 >= this.mCurrentCueIndex || this.mCurrentCueIndex >= this.mCuePoints.length - 1) {
                this.mAdBeaconState = new AdBeaconState(AdPlacement.PostRoll);
            } else {
                this.mAdBeaconState = new AdBeaconState(AdPlacement.MidRoll);
            }
        }
    }

    private void resumeAfterAds() {
        StringBuilder stringBuilder;
        stopAdWatchdog();
        if (currentAdIsPostRoll()) {
            finishPlay();
        } else if (this.mQueuedVideoAsset) {
            playNextQueuedVideo();
        } else if (isRequiredSeekAfterAds() && this.mSimpleExoPlayer != null) {
            if (!isAdDisplayed()) {
                try {
                    this.mSimpleExoPlayer.seekTo(this.mStartTimeAfterAds);
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(this.mStartTimeAfterAds);
                    stringBuilder.append("");
                    LogUtil.d("AD_log_mStartTimeAfterAds=", stringBuilder.toString());
                } catch (NullPointerException e) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Failed to seek time: ");
                    stringBuilder.append(e.getMessage());
                    LogUtil.e(stringBuilder.toString());
                }
            } else if (!this.mSimpleExoPlayer.getPlayWhenReady()) {
                play();
            }
        }
    }

    private void resumeAfterAdWatchdogStop() {
        sendAdBeacon(AdBeaconEvents.AdError, getContentPosition(), VideoBeaconErrors.AdError.rawValue());
        resumeAfterAdStop();
    }

    private void resumeAfterAdStop() {
        if (isAppRunning()) {
            clearAds();
            updateNowPlayingAdListener(false);
            updatePlayerController();
            startWatchedBeaconTimer(false);
            resumeVideoTotalWatchedTime();
            resumeAfterAds();
            return;
        }
        this.mIsAdDisplayed = false;
        this.mIsAdDisplayedCopy = false;
        this.mIsAdDisplayedSaved = false;
        this.mCurrentAdEvent = null;
    }

    private boolean currentAdIsPostRoll() {
        return this.mCuePoints != null && this.mCurrentCueIndex == this.mCuePoints.length;
    }

    private void finishPlay() {
        stopAdWatchdog();
        stopBufWatchdog();
        if (this.mBeaconTimerTask != null) {
            invalidateWatchedBeaconTimer();
            this.playRequestSent = false;
        }
        if (this.mPlayerEventListener != null) {
            this.mPlayerEventListener.onPlayerStateChanged(getPlayWhenReady(), 4);
        }
    }

    private void playNextQueuedVideo() {
        stopAdWatchdog();
        stopBufWatchdog();
        if (this.mQueuedVideoAsset) {
            this.mQueuedVideoAsset = false;
            updatePlayerController();
            prepareNextVideo();
        }
    }

    private boolean isRequiredSeekAfterAds() {
        if (!(this.mRequiredSeekAfterAds || this.mVideoAsset == null || !(this.mVideoAsset instanceof LiveAsset))) {
            Calendar instance = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            instance.setTime(new Date());
            if (XumoUtil.getStartTimeDiff(instance.getTime(), (LiveAsset) this.mVideoAsset) > 0) {
                this.mRequiredSeekAfterAds = true;
            }
        }
        return this.mRequiredSeekAfterAds;
    }

    private void startWatchedBeaconTimer(boolean z) {
        invalidateWatchedBeaconTimer();
        if (this.mBeaconTimer == null) {
            this.mBeaconTimer = new Timer();
            this.mBeaconTimerTask = new BeaconTimerTask(z);
            this.mBeaconTimer.schedule(this.mBeaconTimerTask, 0, 500);
        }
    }

    private void invalidateWatchedBeaconTimer() {
        if (this.mBeaconTimer != null) {
            this.mBeaconTimer.cancel();
        }
        this.mBeaconTimer = null;
        if (this.mBeaconTimerTask != null) {
            this.mBeaconTimerTask.cancel();
        }
        this.mBeaconTimerTask = null;
        this.totalDurationWatchedForCurrentVideo = 0;
    }

    public String getPlayId() {
        if (mPlaySessionId == null) {
            mPlaySessionId = UserPreferences.getInstance().refreshPlayId();
            clearVideoTotalWatchedTime();
            if (this.mBeaconTimerTask != null) {
                this.totalDurationWatchedForCurrentVideo = 0;
            }
        }
        return mPlaySessionId;
    }

    private void sendContentBeacon(VideoBeaconEvents videoBeaconEvents, VideoBeaconErrors videoBeaconErrors) {
        this.mBeaconState = videoBeaconEvents;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mBeaconState:");
        stringBuilder.append(this.mBeaconState);
        Log.e("playWhenReady", stringBuilder.toString());
        String playId = getPlayId();
        String str = "0";
        if (!(this.mSimpleExoPlayer == null || videoBeaconEvents == VideoBeaconEvents.PlayRequested)) {
            str = String.valueOf(getContentPosition() / 1000);
        }
        String str2 = str;
        str = "0";
        if (!(this.mBeaconTimerTask == null || videoBeaconEvents == VideoBeaconEvents.PlayRequested)) {
            str = String.valueOf(this.totalDurationWatchedForCurrentVideo);
        }
        String str3 = str;
        String valueOf = (this.mBeaconTimerTask == null || 30 > this.totalDurationWatchedForCurrentVideo || videoBeaconEvents != VideoBeaconEvents.PlayInterval) ? str3 : String.valueOf(30);
        PlayType playType = null;
        if (this.mVideoAsset != null) {
            playType = this.mVideoAsset.getAssetType() == 1 ? PlayType.VOD : PlayType.LiveLite;
            if (this.mOnNowLive != null && this.mOnNowLive.getId().equals(this.mOnNowLive.getNextId())) {
                playType = PlayType.Live;
            }
        }
        BeaconUtil.sendBeaconVideo(this.mVideoAsset, videoBeaconEvents, str2, str3, valueOf, videoBeaconErrors, playId, 0, UserPreferences.getInstance().getPlayReason(), playType, null, false);
    }

    private void sendAdBeacon(AdBeaconEvents adBeaconEvents, long j, int i) {
        String valueOf = String.valueOf(j / 1000);
        String str = "0";
        if (this.mBeaconTimerTask != null && r0.mBeaconTimerTask.runForAds) {
            str = String.valueOf(r0.mLastContentTotalWatchedTime);
        }
        String str2 = str;
        PlayType playType = null;
        if (r0.mVideoAsset != null) {
            playType = r0.mVideoAsset.getAssetType() == 1 ? PlayType.VOD : PlayType.LiveLite;
            if (r0.mOnNowLive != null && r0.mOnNowLive.getId().equals(r0.mOnNowLive.getNextId())) {
                playType = PlayType.Live;
            }
        }
        AdBeaconEvents adBeaconEvents2 = adBeaconEvents;
        BeaconUtil.sendBeaconAd(r0.mVideoAsset, adBeaconEvents2, valueOf, str2, String.valueOf(30.0d), i, getPlayId(), 0, UserPreferences.getInstance().getPlayReason(), playType, null, false, r0.mAdBeaconState);
    }

    private void sendOmnitureBeacon() {
        OmnitureBeaconUtil.sendBeacon(UserPreferences.getInstance().getChannelPlayId());
    }

    private void updateNowPlayingAdListener(boolean z) {
        if (this.mNowPlayingAdListener != null) {
            this.mNowPlayingAdListener.onNowPlaying(z);
        }
    }

    private boolean readyToPlayAds() {
        if (this.mLastAdPlayTime != 0) {
            if (this.mPlayerProvider == null || XumoUtil.getTimeDiffToNowInSecs(this.mLastAdPlayTime) < this.mPlayerProvider.getAdInterval() || this.adPlay == null || this.adPlay[this.mCurrentCueIndex]) {
                return false;
            }
        }
        return true;
    }

    public void sendOnKeyPress(int i, KeyEvent keyEvent) {
        if (this.mOnKeyPressListener != null) {
            this.mOnKeyPressListener.onKeyPress(i, keyEvent);
        }
    }

    public void setOnKeyPressListener(OnKeyPressListener onKeyPressListener) {
        this.mOnKeyPressListener = onKeyPressListener;
    }

    public void setMoviePosition(long j) {
        if (this.mSimpleExoPlayer != null) {
            int i = 0;
            if (this.mSimpleExoPlayer.getCurrentPosition() + j < 0) {
                this.mSimpleExoPlayer.seekTo(0);
                while (i < this.mCuePoints.length) {
                    if (this.adPlay[i] == null) {
                        this.mCurrentCueIndex = i;
                    }
                    i++;
                }
            } else if (this.mSimpleExoPlayer.getCurrentPosition() + j > this.mSimpleExoPlayer.getDuration()) {
                this.mSimpleExoPlayer.seekTo(this.mSimpleExoPlayer.getDuration());
            } else {
                this.mSimpleExoPlayer.seekTo(this.mSimpleExoPlayer.getCurrentPosition() + j);
                if (!this.mIsAdDisplayedCopy) {
                    long currentPosition = this.mSimpleExoPlayer.getCurrentPosition() + j;
                    while (i < this.mCuePoints.length) {
                        j = i + 1;
                        if (j <= this.mCuePoints.length - 1) {
                            float f = (float) currentPosition;
                            if (f >= this.mCuePoints[i].floatValue() * 1000.0f && f < this.mCuePoints[j].floatValue() * 1000.0f && !this.adPlay[i]) {
                                this.mCurrentCueIndex = i;
                                break;
                            }
                        }
                        i = j;
                    }
                }
            }
            if (this.mIsSeekto == null) {
                sendContentBeacon(VideoBeaconEvents.SeekStarted, null);
            }
            this.mIsSeekto = true;
        }
    }

    public void setTimeBarKeyDown(int i, KeyEvent keyEvent) {
        this.mTimeBar.setKeyTimeIncrement(NotificationOptions.SKIP_STEP_TEN_SECONDS_IN_MS);
        this.mTimeBar.onKeyDown(i, keyEvent);
    }

    public void setScrubberColor(int i) {
        this.mTimeBar.setScrubberColor(i);
    }

    public long getVideoDuration() {
        return this.mSimpleExoPlayer.getDuration();
    }

    public void setVodBackOrFwd(boolean z) {
        boolean z2 = getVideoDuration() >= 3600000 ? true : true;
        this.mVodSkipFwdText.setText("");
        this.mVodSkipBackText.setText("");
        pause();
        StringBuilder stringBuilder;
        if (z) {
            if (this.mMoviesControllerSkipMultiple <= false) {
                this.mMoviesControllerSkipMultiple = 0;
            }
            this.mMoviesControllerSkipMultiple -= true;
            if (this.mMoviesControllerSkipMultiple < (-z2)) {
                this.mMoviesControllerSkipMultiple = true;
            }
            z = this.mVodSkipBackText;
            stringBuilder = new StringBuilder();
            stringBuilder.append(Math.abs(this.mMoviesControllerSkipMultiple));
            stringBuilder.append("x");
            z.setText(stringBuilder.toString());
        } else {
            if (this.mMoviesControllerSkipMultiple >= false) {
                this.mMoviesControllerSkipMultiple = 0;
            }
            this.mMoviesControllerSkipMultiple += true;
            if (this.mMoviesControllerSkipMultiple > z2) {
                this.mMoviesControllerSkipMultiple = 1;
            }
            z = this.mVodSkipFwdText;
            stringBuilder = new StringBuilder();
            stringBuilder.append(Math.abs(this.mMoviesControllerSkipMultiple));
            stringBuilder.append("x");
            z.setText(stringBuilder.toString());
        }
        this.handler.post(this.runnable);
    }

    public long getClientTimeWatched() {
        return this.mBeaconTimerTask != null ? (long) this.totalDurationWatchedForCurrentVideo : -1;
    }

    private DefaultDrmSessionManager<FrameworkMediaCrypto> buildDrmSessionManagerV18(UUID uuid, String str, String[] strArr, boolean z) throws UnsupportedDrmException {
        MediaDrmCallback httpMediaDrmCallback = new HttpMediaDrmCallback(str, ((XumoApplication) this.mContext).buildHttpDataSourceFactory());
        if (strArr != null) {
            for (str = null; str < strArr.length - 1; str += 2) {
                httpMediaDrmCallback.setKeyRequestProperty(strArr[str], strArr[str + 1]);
            }
        }
        releaseMediaDrm();
        this.mediaDrm = FrameworkMediaDrm.newInstance(uuid);
        return new DefaultDrmSessionManager(uuid, this.mediaDrm, httpMediaDrmCallback, null, z);
    }

    private void releaseMediaDrm() {
        if (this.mediaDrm != null) {
            this.mediaDrm.release();
            this.mediaDrm = null;
        }
    }
}
