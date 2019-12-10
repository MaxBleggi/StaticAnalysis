package com.xumo.xumo.fragmenttv;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xumo.xumo.BuildConfig;
import com.xumo.xumo.model.LiveAsset;
import com.xumo.xumo.model.OnNowLive;
import com.xumo.xumo.model.VideoAsset;
import com.xumo.xumo.player.XumoExoPlayer;
import com.xumo.xumo.repository.UserPreferences;
import com.xumo.xumo.tv.R;
import com.xumo.xumo.util.XumoUtil;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class DebugFragment extends BaseFragment implements OnSharedPreferenceChangeListener {
    public static final String TAG_DEBUG = "com.xumo.xumo.fragmenttv.DebugFragment";
    private final int UPDATE_TIME = 0;
    @SuppressLint({"HandlerLeak"})
    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            if (message.what == null) {
                DebugFragment.this.handler.removeMessages(0);
                DebugFragment.this.initData();
                DebugFragment.this.handler.sendEmptyMessageDelayed(0, 1000);
            }
        }
    };
    private TextView mDebugAspectRatio;
    private TextView mDebugAssetAvailableSince;
    private TextView mDebugAssetId;
    private TextView mDebugAssetIndex;
    private TextView mDebugAssetTitle;
    private TextView mDebugAssetType;
    private TextView mDebugAvailableLangs;
    private TextView mDebugBitRate;
    private TextView mDebugCaptions;
    private TextView mDebugCaptionsStyle;
    private TextView mDebugCategoryId;
    private TextView mDebugChannelId;
    private TextView mDebugChannelPlayId;
    private TextView mDebugClientId;
    private TextView mDebugClientTimeWatched;
    private TextView mDebugCuePoints;
    private TextView mDebugCurrentLang;
    private TextView mDebugKeyCode;
    private TextView mDebugPlayerId;
    private TextView mDebugPlayerType;
    private TextView mDebugPlayhead;
    private TextView mDebugProgramId;
    private TextView mDebugProgramRating;
    private TextView mDebugProgramSchedule;
    private TextView mDebugProgramSubrating;
    private TextView mDebugProgramTitle;
    private TextView mDebugProvider;
    private TextView mDebugScreenResolution;
    private TextView mDebugSessionId;
    private TextView mDebugSourceType;
    private TextView mDebugSourceUrl;
    private TextView mDebugSystemTime;
    private TextView mDebugUa;
    private TextView mDebugUptime;
    private TextView mDebugVersion;
    private XumoExoPlayer xumoExoPlayer;

    public void setXumoExoPlayer(XumoExoPlayer xumoExoPlayer) {
        this.xumoExoPlayer = xumoExoPlayer;
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        super.onCreateView(layoutInflater, viewGroup, bundle);
        return layoutInflater.inflate(R.layout.fragment_debug, viewGroup, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        initView(view);
    }

    public void onResume() {
        super.onResume();
        this.handler.sendEmptyMessage(0);
    }

    public void onStop() {
        super.onStop();
        this.handler.removeMessages(0);
    }

    private void initView(View view) {
        this.mDebugVersion = (TextView) view.findViewById(R.id.debug_version);
        this.mDebugScreenResolution = (TextView) view.findViewById(R.id.debug_screen_resolution);
        this.mDebugUa = (TextView) view.findViewById(R.id.debug_ua);
        this.mDebugClientId = (TextView) view.findViewById(R.id.debug_client_id);
        this.mDebugSystemTime = (TextView) view.findViewById(R.id.debug_system_time);
        this.mDebugUptime = (TextView) view.findViewById(R.id.debug_uptime);
        this.mDebugKeyCode = (TextView) view.findViewById(R.id.debug_key_code);
        this.mDebugSessionId = (TextView) view.findViewById(R.id.debug_session_id);
        this.mDebugChannelId = (TextView) view.findViewById(R.id.debug_channel_id);
        this.mDebugChannelPlayId = (TextView) view.findViewById(R.id.debug_channel_play_id);
        this.mDebugCategoryId = (TextView) view.findViewById(R.id.debug_category_id);
        this.mDebugProgramId = (TextView) view.findViewById(R.id.debug_program_id);
        this.mDebugProgramTitle = (TextView) view.findViewById(R.id.debug_program_title);
        this.mDebugProgramSchedule = (TextView) view.findViewById(R.id.debug_program_schedule);
        this.mDebugProgramRating = (TextView) view.findViewById(R.id.debug_program_rating);
        this.mDebugProgramSubrating = (TextView) view.findViewById(R.id.debug_program_subrating);
        this.mDebugPlayerType = (TextView) view.findViewById(R.id.debug_player_type);
        this.mDebugPlayerId = (TextView) view.findViewById(R.id.debug_player_id);
        this.mDebugAssetId = (TextView) view.findViewById(R.id.debug_asset_id);
        this.mDebugAssetTitle = (TextView) view.findViewById(R.id.debug_asset_title);
        this.mDebugAssetType = (TextView) view.findViewById(R.id.debug_asset_type);
        this.mDebugAssetIndex = (TextView) view.findViewById(R.id.debug_asset_index);
        this.mDebugAssetAvailableSince = (TextView) view.findViewById(R.id.debug_asset_availableSince);
        this.mDebugProvider = (TextView) view.findViewById(R.id.debug_provider);
        this.mDebugSourceUrl = (TextView) view.findViewById(R.id.debug_source_url);
        this.mDebugSourceType = (TextView) view.findViewById(R.id.debug_source_type);
        this.mDebugAvailableLangs = (TextView) view.findViewById(R.id.debug_available_langs);
        this.mDebugCurrentLang = (TextView) view.findViewById(R.id.debug_current_lang);
        this.mDebugPlayhead = (TextView) view.findViewById(R.id.debug_playhead);
        this.mDebugCuePoints = (TextView) view.findViewById(R.id.debug_cuePoints);
        this.mDebugClientTimeWatched = (TextView) view.findViewById(R.id.debug_client_time_watched);
        this.mDebugCaptions = (TextView) view.findViewById(R.id.debug_captions);
        this.mDebugCaptionsStyle = (TextView) view.findViewById(R.id.debug_captions_style);
        this.mDebugAspectRatio = (TextView) view.findViewById(R.id.debug_aspectRatio);
        this.mDebugBitRate = (TextView) view.findViewById(R.id.debug_bitRate);
    }

    @SuppressLint({"SetTextI18n"})
    public void initData() {
        if (this.xumoExoPlayer != null) {
            if (this.xumoExoPlayer.getVideoAsset() != null) {
                String replace;
                StringBuilder stringBuilder;
                StringBuilder stringBuilder2;
                int i;
                int i2;
                LiveAsset liveAsset;
                TextView textView;
                StringBuilder stringBuilder3;
                String stringBuilder4;
                DecimalFormat decimalFormat;
                double currentPosition;
                Object valueOf;
                StringBuilder stringBuilder5;
                VideoAsset videoAsset = this.xumoExoPlayer.getVideoAsset();
                TextView textView2 = this.mDebugVersion;
                StringBuilder stringBuilder6 = new StringBuilder();
                stringBuilder6.append("version: ");
                Object[] objArr = new Object[1];
                int i3 = 0;
                objArr[0] = BuildConfig.VERSION_NAME;
                stringBuilder6.append(getString(R.string.settings_version_name, objArr));
                stringBuilder6.append(".");
                stringBuilder6.append(34);
                textView2.setText(stringBuilder6.toString());
                WindowManager windowManager = (WindowManager) getContext().getSystemService("window");
                TextView textView3 = this.mDebugScreenResolution;
                StringBuilder stringBuilder7 = new StringBuilder();
                stringBuilder7.append("screen resolution: ");
                stringBuilder7.append(windowManager.getDefaultDisplay().getWidth());
                stringBuilder7.append(" x ");
                stringBuilder7.append(windowManager.getDefaultDisplay().getHeight());
                textView3.setText(stringBuilder7.toString());
                textView2 = this.mDebugUa;
                stringBuilder6 = new StringBuilder();
                stringBuilder6.append("about device: ");
                stringBuilder6.append(Build.MODEL);
                stringBuilder6.append(" Android ");
                stringBuilder6.append(VERSION.RELEASE);
                textView2.setText(stringBuilder6.toString());
                textView2 = this.mDebugClientId;
                stringBuilder6 = new StringBuilder();
                stringBuilder6.append("client id: ");
                if (UserPreferences.getInstance().getDeviceId() != null) {
                    if (!UserPreferences.getInstance().getDeviceId().isEmpty()) {
                        replace = UserPreferences.getInstance().getDeviceId().replace("XumoDeviceId ", "");
                        stringBuilder6.append(replace);
                        textView2.setText(stringBuilder6.toString());
                        textView2 = this.mDebugSystemTime;
                        stringBuilder6 = new StringBuilder();
                        stringBuilder6.append("system time: ");
                        stringBuilder6.append(new Date());
                        stringBuilder6.append(" (");
                        stringBuilder6.append(System.currentTimeMillis() / 1000);
                        stringBuilder6.append(")");
                        textView2.setText(stringBuilder6.toString());
                        textView2 = this.mDebugUptime;
                        stringBuilder6 = new StringBuilder();
                        stringBuilder6.append("uptime: ");
                        stringBuilder6.append(XumoUtil.getProperTime((System.currentTimeMillis() - XumoUtil.getStartTime()) / 1000));
                        textView2.setText(stringBuilder6.toString());
                        textView2 = this.mDebugSessionId;
                        stringBuilder6 = new StringBuilder();
                        stringBuilder6.append("session id: ");
                        stringBuilder6.append(UserPreferences.session.sessionId.isEmpty() ? "N/A" : UserPreferences.session.sessionId);
                        textView2.setText(stringBuilder6.toString());
                        textView2 = this.mDebugChannelId;
                        stringBuilder6 = new StringBuilder();
                        stringBuilder6.append("channel id: ");
                        stringBuilder6.append(videoAsset.getChannelId().isEmpty() ? "N/A" : videoAsset.getChannelId());
                        textView2.setText(stringBuilder6.toString());
                        textView2 = this.mDebugChannelPlayId;
                        stringBuilder6 = new StringBuilder();
                        stringBuilder6.append("channel play id: ");
                        stringBuilder6.append(UserPreferences.getInstance().getChannelPlayId().isEmpty() ? "N/A" : UserPreferences.getInstance().getChannelPlayId());
                        textView2.setText(stringBuilder6.toString());
                        textView2 = this.mDebugCategoryId;
                        stringBuilder6 = new StringBuilder();
                        stringBuilder6.append("category id: ");
                        stringBuilder6.append(videoAsset.getCategoryId().isEmpty() ? "N/A" : videoAsset.getCategoryId());
                        textView2.setText(stringBuilder6.toString());
                        if (this.xumoExoPlayer.isLive() || OnNowPlayerFragment.mCurrentChannelId.isEmpty() || OnNowPlayerFragment.mOnNowLives.size() <= 0) {
                            this.mDebugProgramId.setText("program id: N/A");
                            this.mDebugProgramTitle.setText("program title: N/A");
                            this.mDebugProgramSchedule.setText("program startTime: N/A\nprogram endTime: N/A");
                            this.mDebugProgramRating.setText("program rating: N/A");
                            this.mDebugProgramSubrating.setText("program subrating: N/A");
                        } else {
                            OnNowLive onNowLive = null;
                            Iterator it = OnNowPlayerFragment.mOnNowLives.iterator();
                            while (it.hasNext()) {
                                OnNowLive onNowLive2 = (OnNowLive) it.next();
                                if (OnNowPlayerFragment.mCurrentChannelId.equals(onNowLive2.getChannelId())) {
                                    onNowLive = onNowLive2;
                                }
                            }
                            if (onNowLive != null) {
                                TextView textView4 = this.mDebugProgramId;
                                stringBuilder = new StringBuilder();
                                stringBuilder.append("program id: ");
                                stringBuilder.append(onNowLive.getId().isEmpty() ? "N/A" : onNowLive.getId());
                                textView4.setText(stringBuilder.toString());
                                textView4 = this.mDebugProgramTitle;
                                stringBuilder = new StringBuilder();
                                stringBuilder.append("program title: ");
                                stringBuilder.append(onNowLive.getTitle().isEmpty() ? "N/A" : onNowLive.getTitle());
                                textView4.setText(stringBuilder.toString());
                                if (onNowLive.getStart() == 0 && onNowLive.getEnd() == 0) {
                                    this.mDebugProgramSchedule.setText("program schedule: N/A");
                                } else {
                                    textView4 = this.mDebugProgramSchedule;
                                    stringBuilder = new StringBuilder();
                                    stringBuilder.append("program schedule: ");
                                    stringBuilder.append(new SimpleDateFormat("HH:mm").format(new Date(onNowLive.getStart())));
                                    stringBuilder.append(" (");
                                    stringBuilder.append(onNowLive.getStart() / 1000);
                                    stringBuilder.append(") - ");
                                    stringBuilder.append(new SimpleDateFormat("HH:mm").format(new Date(onNowLive.getEnd())));
                                    stringBuilder.append(" (");
                                    stringBuilder.append(onNowLive.getEnd() / 1000);
                                    stringBuilder.append(")");
                                    textView4.setText(stringBuilder.toString());
                                }
                            }
                            textView2 = this.mDebugProgramRating;
                            stringBuilder2 = new StringBuilder();
                            stringBuilder2.append("program rating: ");
                            stringBuilder2.append(videoAsset.getmRatings().isEmpty() ? "N/A" : videoAsset.getmRatings());
                            textView2.setText(stringBuilder2.toString());
                            this.mDebugProgramSubrating.setText("program subrating: N/A");
                        }
                        if (this.xumoExoPlayer.isLive()) {
                            this.mDebugPlayerType.setVisibility(8);
                        } else {
                            this.mDebugPlayerType.setText("player type: XumoExoPlayer");
                            this.mDebugPlayerType.setVisibility(0);
                        }
                        textView2 = this.mDebugPlayerId;
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("play id: ");
                        stringBuilder2.append(this.xumoExoPlayer.getPlayId().isEmpty() ? "N/A" : this.xumoExoPlayer.getPlayId());
                        textView2.setText(stringBuilder2.toString());
                        textView2 = this.mDebugAssetId;
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("asset id: ");
                        stringBuilder2.append(videoAsset.getAssetId().isEmpty() ? "N/A" : videoAsset.getAssetId());
                        textView2.setText(stringBuilder2.toString());
                        textView2 = this.mDebugAssetTitle;
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("asset title: ");
                        stringBuilder2.append(videoAsset.getTitle().isEmpty() ? "N/A" : videoAsset.getTitle());
                        textView2.setText(stringBuilder2.toString());
                        if (this.xumoExoPlayer.ismIsAdDisplayedCopy()) {
                            if (videoAsset.getAssetType() != 1) {
                                if (videoAsset.getAssetType() == 2) {
                                    if (videoAsset.getAssetType() != 3) {
                                        this.mDebugAssetType.setText("asset type: filler");
                                    } else {
                                        this.mDebugAssetType.setText("asset type: N/A");
                                    }
                                }
                            }
                            this.mDebugAssetType.setText("asset type: content");
                        } else {
                            this.mDebugAssetType.setText("asset type: ad");
                        }
                        if (videoAsset instanceof LiveAsset) {
                            i = 0;
                            i2 = 0;
                        } else {
                            liveAsset = (LiveAsset) videoAsset;
                            i2 = liveAsset.getIndex();
                            i = liveAsset.getLength() - 1;
                        }
                        textView = this.mDebugAssetIndex;
                        stringBuilder3 = new StringBuilder();
                        stringBuilder3.append("asset index: ");
                        stringBuilder3.append(i2);
                        stringBuilder3.append(" / ");
                        stringBuilder3.append(i);
                        textView.setText(stringBuilder3.toString());
                        textView2 = this.mDebugAssetAvailableSince;
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("asset availableSince: ");
                        stringBuilder2.append(videoAsset.getAssetAge() != null ? "N/A" : videoAsset.getAssetAge());
                        textView2.setText(stringBuilder2.toString());
                        if (this.xumoExoPlayer.isLive()) {
                            textView2 = this.mDebugProvider;
                            stringBuilder2 = new StringBuilder();
                            stringBuilder2.append("provider id: ");
                            stringBuilder2.append(videoAsset.getProviderId() != 0 ? "N/A" : Integer.valueOf(videoAsset.getProviderId()));
                            stringBuilder2.append("\nprovider name: ");
                            stringBuilder2.append(videoAsset.getmProviderTitle().isEmpty() ? "N/A" : videoAsset.getmProviderTitle());
                            textView2.setText(stringBuilder2.toString());
                        } else {
                            textView2 = this.mDebugProvider;
                            stringBuilder2 = new StringBuilder();
                            stringBuilder2.append("provider: ");
                            stringBuilder = new StringBuilder();
                            stringBuilder.append(videoAsset.getmProviderTitle());
                            stringBuilder.append(String.valueOf(videoAsset.getProviderId()));
                            if (stringBuilder.toString().isEmpty()) {
                                stringBuilder = new StringBuilder();
                                stringBuilder.append(videoAsset.getmProviderTitle());
                                stringBuilder.append(" (");
                                stringBuilder.append(String.valueOf(videoAsset.getProviderId()));
                                stringBuilder.append(")");
                                stringBuilder4 = stringBuilder.toString();
                            } else {
                                stringBuilder4 = "N/A";
                            }
                            stringBuilder2.append(stringBuilder4);
                            textView2.setText(stringBuilder2.toString());
                        }
                        textView2 = this.mDebugSourceUrl;
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("source url: ");
                        stringBuilder2.append(videoAsset.getUrl().isEmpty() ? "N/A" : videoAsset.getUrl());
                        textView2.setText(stringBuilder2.toString());
                        if (videoAsset.getContentType().isEmpty()) {
                            this.mDebugSourceType.setText("source type: N/A");
                        } else if (videoAsset.getContentType().contains(";")) {
                            textView2 = this.mDebugSourceType;
                            stringBuilder2 = new StringBuilder();
                            stringBuilder2.append("source type: ");
                            stringBuilder2.append(videoAsset.getContentType());
                            textView2.setText(stringBuilder2.toString());
                        } else {
                            textView2 = this.mDebugSourceType;
                            stringBuilder2 = new StringBuilder();
                            stringBuilder2.append("source type: ");
                            stringBuilder2.append(videoAsset.getContentType().substring(0, videoAsset.getContentType().lastIndexOf(";")));
                            textView2.setText(stringBuilder2.toString());
                        }
                        textView2 = this.mDebugAvailableLangs;
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("available langs: ");
                        stringBuilder2.append(videoAsset.getmlang().isEmpty() ? "N/A" : videoAsset.getmlang());
                        textView2.setText(stringBuilder2.toString());
                        this.mDebugCurrentLang.setText("current lang: en");
                        textView2 = this.mDebugPlayhead;
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("playhead: ");
                        decimalFormat = new DecimalFormat("#.000");
                        currentPosition = (double) this.xumoExoPlayer.getCurrentPosition();
                        Double.isNaN(currentPosition);
                        stringBuilder2.append(decimalFormat.format(currentPosition * 0.001d));
                        stringBuilder2.append(" / ");
                        if (this.xumoExoPlayer.getVideoDuration() >= 0) {
                            valueOf = Integer.valueOf(0);
                        } else {
                            decimalFormat = new DecimalFormat("#.000");
                            currentPosition = (double) this.xumoExoPlayer.getVideoDuration();
                            Double.isNaN(currentPosition);
                            valueOf = decimalFormat.format(currentPosition * 0.001d);
                        }
                        stringBuilder2.append(valueOf);
                        textView2.setText(stringBuilder2.toString());
                        if (videoAsset.getCuePoints() != null || videoAsset.getCuePoints().length <= 0) {
                            this.mDebugCuePoints.setText("cuePoints: N/A");
                        } else {
                            StringBuffer stringBuffer = new StringBuffer();
                            float[] cuePoints = videoAsset.getCuePoints();
                            int length = cuePoints.length;
                            while (i3 < length) {
                                float f = cuePoints[i3];
                                StringBuilder stringBuilder8 = new StringBuilder();
                                stringBuilder8.append((int) f);
                                stringBuilder8.append(",");
                                stringBuffer.append(stringBuilder8.toString());
                                i3++;
                            }
                            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                            TextView textView5 = this.mDebugCuePoints;
                            stringBuilder2 = new StringBuilder();
                            stringBuilder2.append("cuePoints: ");
                            stringBuilder2.append(stringBuffer);
                            textView5.setText(stringBuilder2.toString());
                        }
                        textView2 = this.mDebugClientTimeWatched;
                        stringBuilder5 = new StringBuilder();
                        stringBuilder5.append("client time watched: ");
                        stringBuilder5.append(this.xumoExoPlayer.getClientTimeWatched() < 0 ? Long.valueOf(this.xumoExoPlayer.getClientTimeWatched()) : "N/A");
                        textView2.setText(stringBuilder5.toString());
                        textView2 = this.mDebugCaptions;
                        stringBuilder6 = new StringBuilder();
                        stringBuilder6.append("captions: ");
                        stringBuilder6.append(videoAsset.ismHasCaption() ? "out of band" : "N/A");
                        textView2.setText(stringBuilder6.toString());
                        setCaptionsStyle();
                        if (videoAsset.getWidth() != 0 || videoAsset.getHeight() == 0) {
                            this.mDebugAspectRatio.setText("aspectRatio: N/A");
                        } else {
                            textView2 = this.mDebugAspectRatio;
                            stringBuilder6 = new StringBuilder();
                            stringBuilder6.append("aspectRatio: ");
                            stringBuilder6.append(videoAsset.getWidth());
                            stringBuilder6.append(" / ");
                            stringBuilder6.append(videoAsset.getHeight());
                            textView2.setText(stringBuilder6.toString());
                        }
                        textView2 = this.mDebugBitRate;
                        stringBuilder6 = new StringBuilder();
                        stringBuilder6.append("bitRate: ");
                        stringBuilder6.append(videoAsset.getBitrate() != 0 ? "N/A" : Integer.valueOf(videoAsset.getBitrate()));
                        textView2.setText(stringBuilder6.toString());
                    }
                }
                replace = "N/A";
                stringBuilder6.append(replace);
                textView2.setText(stringBuilder6.toString());
                textView2 = this.mDebugSystemTime;
                stringBuilder6 = new StringBuilder();
                stringBuilder6.append("system time: ");
                stringBuilder6.append(new Date());
                stringBuilder6.append(" (");
                stringBuilder6.append(System.currentTimeMillis() / 1000);
                stringBuilder6.append(")");
                textView2.setText(stringBuilder6.toString());
                textView2 = this.mDebugUptime;
                stringBuilder6 = new StringBuilder();
                stringBuilder6.append("uptime: ");
                stringBuilder6.append(XumoUtil.getProperTime((System.currentTimeMillis() - XumoUtil.getStartTime()) / 1000));
                textView2.setText(stringBuilder6.toString());
                textView2 = this.mDebugSessionId;
                stringBuilder6 = new StringBuilder();
                stringBuilder6.append("session id: ");
                if (UserPreferences.session.sessionId.isEmpty()) {
                }
                stringBuilder6.append(UserPreferences.session.sessionId.isEmpty() ? "N/A" : UserPreferences.session.sessionId);
                textView2.setText(stringBuilder6.toString());
                textView2 = this.mDebugChannelId;
                stringBuilder6 = new StringBuilder();
                stringBuilder6.append("channel id: ");
                if (videoAsset.getChannelId().isEmpty()) {
                }
                stringBuilder6.append(videoAsset.getChannelId().isEmpty() ? "N/A" : videoAsset.getChannelId());
                textView2.setText(stringBuilder6.toString());
                textView2 = this.mDebugChannelPlayId;
                stringBuilder6 = new StringBuilder();
                stringBuilder6.append("channel play id: ");
                if (UserPreferences.getInstance().getChannelPlayId().isEmpty()) {
                }
                stringBuilder6.append(UserPreferences.getInstance().getChannelPlayId().isEmpty() ? "N/A" : UserPreferences.getInstance().getChannelPlayId());
                textView2.setText(stringBuilder6.toString());
                textView2 = this.mDebugCategoryId;
                stringBuilder6 = new StringBuilder();
                stringBuilder6.append("category id: ");
                if (videoAsset.getCategoryId().isEmpty()) {
                }
                stringBuilder6.append(videoAsset.getCategoryId().isEmpty() ? "N/A" : videoAsset.getCategoryId());
                textView2.setText(stringBuilder6.toString());
                if (this.xumoExoPlayer.isLive()) {
                }
                this.mDebugProgramId.setText("program id: N/A");
                this.mDebugProgramTitle.setText("program title: N/A");
                this.mDebugProgramSchedule.setText("program startTime: N/A\nprogram endTime: N/A");
                this.mDebugProgramRating.setText("program rating: N/A");
                this.mDebugProgramSubrating.setText("program subrating: N/A");
                if (this.xumoExoPlayer.isLive()) {
                    this.mDebugPlayerType.setVisibility(8);
                } else {
                    this.mDebugPlayerType.setText("player type: XumoExoPlayer");
                    this.mDebugPlayerType.setVisibility(0);
                }
                textView2 = this.mDebugPlayerId;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("play id: ");
                if (this.xumoExoPlayer.getPlayId().isEmpty()) {
                }
                stringBuilder2.append(this.xumoExoPlayer.getPlayId().isEmpty() ? "N/A" : this.xumoExoPlayer.getPlayId());
                textView2.setText(stringBuilder2.toString());
                textView2 = this.mDebugAssetId;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("asset id: ");
                if (videoAsset.getAssetId().isEmpty()) {
                }
                stringBuilder2.append(videoAsset.getAssetId().isEmpty() ? "N/A" : videoAsset.getAssetId());
                textView2.setText(stringBuilder2.toString());
                textView2 = this.mDebugAssetTitle;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("asset title: ");
                if (videoAsset.getTitle().isEmpty()) {
                }
                stringBuilder2.append(videoAsset.getTitle().isEmpty() ? "N/A" : videoAsset.getTitle());
                textView2.setText(stringBuilder2.toString());
                if (this.xumoExoPlayer.ismIsAdDisplayedCopy()) {
                    if (videoAsset.getAssetType() != 1) {
                        if (videoAsset.getAssetType() == 2) {
                            if (videoAsset.getAssetType() != 3) {
                                this.mDebugAssetType.setText("asset type: N/A");
                            } else {
                                this.mDebugAssetType.setText("asset type: filler");
                            }
                        }
                    }
                    this.mDebugAssetType.setText("asset type: content");
                } else {
                    this.mDebugAssetType.setText("asset type: ad");
                }
                if (videoAsset instanceof LiveAsset) {
                    i = 0;
                    i2 = 0;
                } else {
                    liveAsset = (LiveAsset) videoAsset;
                    i2 = liveAsset.getIndex();
                    i = liveAsset.getLength() - 1;
                }
                textView = this.mDebugAssetIndex;
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append("asset index: ");
                stringBuilder3.append(i2);
                stringBuilder3.append(" / ");
                stringBuilder3.append(i);
                textView.setText(stringBuilder3.toString());
                textView2 = this.mDebugAssetAvailableSince;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("asset availableSince: ");
                if (videoAsset.getAssetAge() != null) {
                }
                stringBuilder2.append(videoAsset.getAssetAge() != null ? "N/A" : videoAsset.getAssetAge());
                textView2.setText(stringBuilder2.toString());
                if (this.xumoExoPlayer.isLive()) {
                    textView2 = this.mDebugProvider;
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("provider id: ");
                    if (videoAsset.getProviderId() != 0) {
                    }
                    stringBuilder2.append(videoAsset.getProviderId() != 0 ? "N/A" : Integer.valueOf(videoAsset.getProviderId()));
                    stringBuilder2.append("\nprovider name: ");
                    if (videoAsset.getmProviderTitle().isEmpty()) {
                    }
                    stringBuilder2.append(videoAsset.getmProviderTitle().isEmpty() ? "N/A" : videoAsset.getmProviderTitle());
                    textView2.setText(stringBuilder2.toString());
                } else {
                    textView2 = this.mDebugProvider;
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("provider: ");
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(videoAsset.getmProviderTitle());
                    stringBuilder.append(String.valueOf(videoAsset.getProviderId()));
                    if (stringBuilder.toString().isEmpty()) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(videoAsset.getmProviderTitle());
                        stringBuilder.append(" (");
                        stringBuilder.append(String.valueOf(videoAsset.getProviderId()));
                        stringBuilder.append(")");
                        stringBuilder4 = stringBuilder.toString();
                    } else {
                        stringBuilder4 = "N/A";
                    }
                    stringBuilder2.append(stringBuilder4);
                    textView2.setText(stringBuilder2.toString());
                }
                textView2 = this.mDebugSourceUrl;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("source url: ");
                if (videoAsset.getUrl().isEmpty()) {
                }
                stringBuilder2.append(videoAsset.getUrl().isEmpty() ? "N/A" : videoAsset.getUrl());
                textView2.setText(stringBuilder2.toString());
                if (videoAsset.getContentType().isEmpty()) {
                    this.mDebugSourceType.setText("source type: N/A");
                } else if (videoAsset.getContentType().contains(";")) {
                    textView2 = this.mDebugSourceType;
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("source type: ");
                    stringBuilder2.append(videoAsset.getContentType());
                    textView2.setText(stringBuilder2.toString());
                } else {
                    textView2 = this.mDebugSourceType;
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("source type: ");
                    stringBuilder2.append(videoAsset.getContentType().substring(0, videoAsset.getContentType().lastIndexOf(";")));
                    textView2.setText(stringBuilder2.toString());
                }
                textView2 = this.mDebugAvailableLangs;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("available langs: ");
                if (videoAsset.getmlang().isEmpty()) {
                }
                stringBuilder2.append(videoAsset.getmlang().isEmpty() ? "N/A" : videoAsset.getmlang());
                textView2.setText(stringBuilder2.toString());
                this.mDebugCurrentLang.setText("current lang: en");
                textView2 = this.mDebugPlayhead;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("playhead: ");
                decimalFormat = new DecimalFormat("#.000");
                currentPosition = (double) this.xumoExoPlayer.getCurrentPosition();
                Double.isNaN(currentPosition);
                stringBuilder2.append(decimalFormat.format(currentPosition * 0.001d));
                stringBuilder2.append(" / ");
                if (this.xumoExoPlayer.getVideoDuration() >= 0) {
                    decimalFormat = new DecimalFormat("#.000");
                    currentPosition = (double) this.xumoExoPlayer.getVideoDuration();
                    Double.isNaN(currentPosition);
                    valueOf = decimalFormat.format(currentPosition * 0.001d);
                } else {
                    valueOf = Integer.valueOf(0);
                }
                stringBuilder2.append(valueOf);
                textView2.setText(stringBuilder2.toString());
                if (videoAsset.getCuePoints() != null) {
                }
                this.mDebugCuePoints.setText("cuePoints: N/A");
                textView2 = this.mDebugClientTimeWatched;
                stringBuilder5 = new StringBuilder();
                stringBuilder5.append("client time watched: ");
                if (this.xumoExoPlayer.getClientTimeWatched() < 0) {
                }
                stringBuilder5.append(this.xumoExoPlayer.getClientTimeWatched() < 0 ? Long.valueOf(this.xumoExoPlayer.getClientTimeWatched()) : "N/A");
                textView2.setText(stringBuilder5.toString());
                textView2 = this.mDebugCaptions;
                stringBuilder6 = new StringBuilder();
                stringBuilder6.append("captions: ");
                if (videoAsset.ismHasCaption()) {
                }
                stringBuilder6.append(videoAsset.ismHasCaption() ? "out of band" : "N/A");
                textView2.setText(stringBuilder6.toString());
                setCaptionsStyle();
                if (videoAsset.getWidth() != 0) {
                }
                this.mDebugAspectRatio.setText("aspectRatio: N/A");
                textView2 = this.mDebugBitRate;
                stringBuilder6 = new StringBuilder();
                stringBuilder6.append("bitRate: ");
                if (videoAsset.getBitrate() != 0) {
                }
                stringBuilder6.append(videoAsset.getBitrate() != 0 ? "N/A" : Integer.valueOf(videoAsset.getBitrate()));
                textView2.setText(stringBuilder6.toString());
            }
        }
    }

    @SuppressLint({"SetTextI18n"})
    public void setKeyCode(int i) {
        TextView textView = this.mDebugKeyCode;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("key code: ");
        stringBuilder.append(i);
        textView.setText(stringBuilder.toString());
    }

    @SuppressLint({"SetTextI18n"})
    private void setCaptionsStyle() {
        UserPreferences.getInstance().registerOnSharedPreferenceChangeListener(this);
        String str = "textColor: N/A , ";
        switch (UserPreferences.getInstance().getTextColor()) {
            case 1:
                str = "textColor: WHITE , ";
                break;
            case 2:
                str = "textColor: BLACK , ";
                break;
            case 3:
                str = "textColor: YELLOW , ";
                break;
            case 4:
                str = "textColor: GREEN , ";
                break;
            case 5:
                str = "textColor: RED , ";
                break;
            case 6:
                str = "textColor: BLUE , ";
                break;
            default:
                break;
        }
        String str2 = "textOpacity: N/A , ";
        switch (UserPreferences.getInstance().getTextOpacity()) {
            case 1:
                str2 = "textOpacity: LOW , ";
                break;
            case 2:
                str2 = "textOpacity: MEDIUM , ";
                break;
            case 3:
                str2 = "textOpacity: HIGH , ";
                break;
            case 4:
                str2 = "textOpacity: SOLID , ";
                break;
            default:
                break;
        }
        String str3 = "textStyle: N/A , ";
        switch (UserPreferences.getInstance().getTextStyle()) {
            case 1:
                str3 = "textStyle: ONE , ";
                break;
            case 2:
                str3 = "textStyle: TWO , ";
                break;
            case 3:
                str3 = "textStyle: THREE , ";
                break;
            default:
                break;
        }
        String str4 = "textSize: N/A , ";
        switch (UserPreferences.getInstance().getTextSize()) {
            case 1:
                str4 = "textSize: SMALL , ";
                break;
            case 2:
                str4 = "textSize: MEDIUM , ";
                break;
            case 3:
                str4 = "textSize: LARGE , ";
                break;
            default:
                break;
        }
        String str5 = "edgeType: N/A , ";
        switch (UserPreferences.getInstance().getEdgeType()) {
            case 1:
                str5 = "edgeType: NONE , ";
                break;
            case 2:
                str5 = "edgeType: OUTLINE , ";
                break;
            case 3:
                str5 = "edgeType: DROP SHADOW , ";
                break;
            default:
                break;
        }
        String str6 = "edgeColor: N/A , ";
        switch (UserPreferences.getInstance().getEdgeColor()) {
            case 1:
                str6 = "edgeColor: WHITE , ";
                break;
            case 2:
                str6 = "edgeColor: BLACK , ";
                break;
            case 3:
                str6 = "edgeColor: YELLOW , ";
                break;
            case 4:
                str6 = "edgeColor: GREEN , ";
                break;
            case 5:
                str6 = "edgeColor: RED , ";
                break;
            case 6:
                str6 = "edgeColor: BLUE , ";
                break;
            default:
                break;
        }
        String str7 = "backgroundColor: N/A , ";
        switch (UserPreferences.getInstance().getBackgroundColor()) {
            case 1:
                str7 = "backgroundColor: WHITE , ";
                break;
            case 2:
                str7 = "backgroundColor: BLACK , ";
                break;
            case 3:
                str7 = "backgroundColor: YELLOW , ";
                break;
            case 4:
                str7 = "backgroundColor: GREEN , ";
                break;
            case 5:
                str7 = "backgroundColor: RED , ";
                break;
            case 6:
                str7 = "backgroundColor: BLUE , ";
                break;
            default:
                break;
        }
        String str8 = "backgroundOpacity: N/A";
        switch (UserPreferences.getInstance().getBackgroundOpacity()) {
            case 1:
                str8 = "backgroundOpacity: NONE";
                break;
            case 2:
                str8 = "backgroundOpacity: LOW";
                break;
            case 3:
                str8 = "backgroundOpacity: MEDIUM";
                break;
            case 4:
                str8 = "backgroundOpacity: HIGH";
                break;
            case 5:
                str8 = "backgroundOpacity: SOLID";
                break;
            default:
                break;
        }
        TextView textView = this.mDebugCaptionsStyle;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("captions style: ");
        stringBuilder.append(str);
        stringBuilder.append(str2);
        stringBuilder.append(str3);
        stringBuilder.append(str4);
        stringBuilder.append(str5);
        stringBuilder.append(str6);
        stringBuilder.append(str7);
        stringBuilder.append(str8);
        textView.setText(stringBuilder.toString());
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
        if (str.equals(UserPreferences.TEXT_COLOR_KEY) != null || str.equals(UserPreferences.TEXT_OPACITY_KEY) != null || str.equals(UserPreferences.TEXT_STYLE_KEY) != null || str.equals(UserPreferences.TEXT_SIZE_KEY) != null || str.equals(UserPreferences.EDGE_TYPE_KEY) != null || str.equals(UserPreferences.EDGE_COLOR_KEY) != null || str.equals("backgroundColor") != null || str.equals(UserPreferences.BACKGROUND_OPACITY_KEY) != null) {
            setCaptionsStyle();
        }
    }
}
