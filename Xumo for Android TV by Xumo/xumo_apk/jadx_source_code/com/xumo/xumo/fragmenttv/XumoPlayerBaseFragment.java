package com.xumo.xumo.fragmenttv;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ui.PlayerView;
import com.xumo.xumo.activity.MainTvActivity;
import com.xumo.xumo.player.XumoExoPlayer;
import com.xumo.xumo.player.XumoExoPlayer.XumoExoPlayerControllerListener;
import com.xumo.xumo.player.XumoExoPlayer.XumoExoPlayerEventListener;
import com.xumo.xumo.tv.R;

public abstract class XumoPlayerBaseFragment extends BaseFragment implements XumoExoPlayerControllerListener, XumoExoPlayerEventListener {
    public static String MOVIES_CHANNEL_ID = "9999334";
    protected static final int NET_WORK_ERROR_TYPE_CHANNEL = 3;
    protected static final int NET_WORK_ERROR_TYPE_VIDEO = 4;
    protected static final int PLAYER_ERROR_TYPE_CHANNEL = 1;
    protected static final int PLAYER_ERROR_TYPE_VIDEO = 2;
    protected static final int PLAYER_SHOW_ERROR_MESSAGE = 0;
    private int counter = -1;
    protected RelativeLayout mErrorMessageLayout;
    protected TextView mErrorMessageTextView;
    protected PlayerView mPlayerView;
    private Handler mShowErrorMessageHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            if (message.what == null) {
                XumoPlayerBaseFragment.this.showPlayerErrorMessage(XumoPlayerBaseFragment.this.playerErrorType);
            }
        }
    };
    protected XumoExoPlayer mXumoExoPlayer;
    private int playerErrorType;

    public abstract void onPlayerStateChanged(boolean z, int i);

    protected abstract void resumeOnNowWhenError();

    protected abstract void resumeVideoAssetWhenError();

    protected abstract void resumeVideoAssetWhenNetworkError();

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mPlayerView = (PlayerView) view.findViewById(R.id.player_view);
        this.mXumoExoPlayer = ((MainTvActivity) getActivity()).getXumoExoPlayer();
        this.mErrorMessageLayout = (RelativeLayout) view.findViewById(R.id.player_error_message_layout);
        this.mErrorMessageLayout.setVisibility(8);
        this.mErrorMessageTextView = (TextView) view.findViewById(R.id.player_error_message);
        setupXumoExoPlayer();
    }

    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        super.onStop();
    }

    public void play() {
        if (this.mXumoExoPlayer != null) {
            this.mXumoExoPlayer.play();
        }
    }

    public void pause() {
        if (this.mXumoExoPlayer != null) {
            this.mXumoExoPlayer.pause();
        }
    }

    protected void setupXumoExoPlayer() {
        this.mXumoExoPlayer.setControllerListener(this);
        this.mXumoExoPlayer.setPlayerEventListener(this);
        this.mXumoExoPlayer.setAttachFragment(this);
        this.mXumoExoPlayer.setPlayerView(this.mPlayerView);
    }

    protected boolean isShowPlayerErrorMessage() {
        return this.mErrorMessageLayout != null && this.mErrorMessageLayout.getVisibility() == 0;
    }

    protected void showPlayerErrorMessage(int i) {
        if (this.mErrorMessageLayout != null) {
            if (this.counter < 0) {
                this.counter = 5;
            }
            if (this.mErrorMessageLayout.getVisibility() != 0) {
                this.mErrorMessageLayout.setVisibility(0);
            }
            this.playerErrorType = i;
            switch (i) {
                case 1:
                    this.mErrorMessageTextView.setText(getString(R.string.player_channel_error_message));
                    break;
                case 2:
                    if (this.mXumoExoPlayer != null && this.mXumoExoPlayer.getVideoAsset() != null && MOVIES_CHANNEL_ID.equals(this.mXumoExoPlayer.getVideoAsset().getChannelId())) {
                        this.mErrorMessageTextView.setText(R.string.player_video_error_message);
                        break;
                    }
                    this.mErrorMessageTextView.setText(getString(R.string.player_video_error_message_next, Integer.valueOf(this.counter)));
                    break;
                    break;
                default:
                    break;
            }
            if (this.counter == 0) {
                hidePlayerErrorMessage();
                switch (i) {
                    case 1:
                        resumeOnNowWhenError();
                        return;
                    case 2:
                        resumeVideoAssetWhenError();
                        return;
                    default:
                        return;
                }
            }
            this.mShowErrorMessageHandler.sendEmptyMessageDelayed(0, 1000);
            this.counter--;
        }
    }

    protected boolean isPlayerErrorMessageVisibility() {
        return this.mErrorMessageLayout != null && this.mErrorMessageLayout.getVisibility() == 0;
    }

    protected void hidePlayerErrorMessage() {
        if (this.mErrorMessageLayout != null) {
            this.mErrorMessageLayout.setVisibility(4);
        }
        clearErrorMessageHandler();
    }

    protected void showNetworkErrorMessage(int i) {
        if (this.mErrorMessageLayout != null) {
            if (this.mErrorMessageLayout.getVisibility() != 0) {
                this.mErrorMessageLayout.setVisibility(0);
            }
            this.mErrorMessageTextView.setText(getString(R.string.net_work_error_message));
            if (i == 3) {
                hidePlayerErrorMessage();
                resumeOnNowWhenError();
            } else if (i == 4) {
                hidePlayerErrorMessage();
                resumeVideoAssetWhenNetworkError();
            }
        }
    }

    private void clearErrorMessageHandler() {
        this.mShowErrorMessageHandler.removeMessages(0);
        this.counter = -1;
    }

    public void onDestroy() {
        super.onDestroy();
        clearErrorMessageHandler();
        this.mXumoExoPlayer.removePlayerView();
    }
}
