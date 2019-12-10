package com.google.android.libraries.cast.companionlibrary.cast.player;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.libraries.cast.companionlibrary.R;
import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.NoConnectionException;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.TransientNetworkDisconnectionException;
import com.google.android.libraries.cast.companionlibrary.cast.tracks.ui.TracksChooserDialog;
import com.google.android.libraries.cast.companionlibrary.utils.LogUtils;
import com.google.android.libraries.cast.companionlibrary.utils.Utils;
import com.google.android.libraries.cast.companionlibrary.widgets.MiniController;

public class VideoCastControllerActivity extends AppCompatActivity implements VideoCastController {
    public static final String DIALOG_TAG = "dialog";
    private static final String TAG = LogUtils.makeLogTag(VideoCastControllerActivity.class);
    public static final String TASK_TAG = "task";
    private VideoCastManager mCastManager;
    private ImageButton mClosedCaptionIcon;
    private View mControllers;
    private TextView mEnd;
    private boolean mImmersive;
    private TextView mLine2;
    private OnVideoCastControllerListener mListener;
    private TextView mLiveText;
    private ProgressBar mLoading;
    private int mNextPreviousVisibilityPolicy = 2;
    private View mPageView;
    private Drawable mPauseDrawable;
    private Drawable mPlayDrawable;
    private ImageButton mPlayPause;
    private View mPlaybackControls;
    private SeekBar mSeekbar;
    private ImageButton mSkipNext;
    private ImageButton mSkipPrevious;
    private TextView mStart;
    private Drawable mStopDrawable;
    private int mStreamType;
    private Toolbar mToolbar;
    private double mVolumeIncrement;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        finish();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.cast_player_menu, menu);
        this.mCastManager.addMediaRouterButton(menu, R.id.media_route_menu_item);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            finish();
        }
        return true;
    }

    public boolean dispatchKeyEvent(@NonNull KeyEvent keyEvent) {
        if (!this.mCastManager.onDispatchVolumeKeyEvent(keyEvent, this.mVolumeIncrement)) {
            if (super.dispatchKeyEvent(keyEvent) == null) {
                return null;
            }
        }
        return true;
    }

    private void loadAndSetupViews() {
        this.mPauseDrawable = getResources().getDrawable(R.drawable.ic_pause_circle_white_80dp);
        this.mPlayDrawable = getResources().getDrawable(R.drawable.ic_play_circle_white_80dp);
        this.mStopDrawable = getResources().getDrawable(R.drawable.ic_stop_circle_white_80dp);
        this.mPageView = findViewById(R.id.pageview);
        this.mPlayPause = (ImageButton) findViewById(R.id.play_pause_toggle);
        this.mLiveText = (TextView) findViewById(R.id.live_text);
        this.mStart = (TextView) findViewById(R.id.start_text);
        this.mEnd = (TextView) findViewById(R.id.end_text);
        this.mSeekbar = (SeekBar) findViewById(R.id.seekbar);
        this.mLine2 = (TextView) findViewById(R.id.textview2);
        this.mLoading = (ProgressBar) findViewById(R.id.progressbar1);
        this.mControllers = findViewById(R.id.controllers);
        this.mClosedCaptionIcon = (ImageButton) findViewById(R.id.cc);
        this.mSkipNext = (ImageButton) findViewById(R.id.next);
        this.mSkipPrevious = (ImageButton) findViewById(R.id.previous);
        this.mPlaybackControls = findViewById(R.id.playback_controls);
        ((MiniController) findViewById(R.id.miniController1)).setCurrentVisibility(false);
        setClosedCaptionState(2);
        this.mPlayPause.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    VideoCastControllerActivity.this.mListener.onPlayPauseClicked(view);
                } catch (View view2) {
                    LogUtils.LOGE(VideoCastControllerActivity.TAG, "Failed to toggle playback due to temporary network issue", view2);
                    Utils.showToast(VideoCastControllerActivity.this, R.string.ccl_failed_no_connection_trans);
                } catch (View view22) {
                    LogUtils.LOGE(VideoCastControllerActivity.TAG, "Failed to toggle playback due to network issues", view22);
                    Utils.showToast(VideoCastControllerActivity.this, R.string.ccl_failed_no_connection);
                } catch (View view222) {
                    LogUtils.LOGE(VideoCastControllerActivity.TAG, "Failed to toggle playback due to other issues", view222);
                    Utils.showToast(VideoCastControllerActivity.this, R.string.ccl_failed_perform_action);
                }
            }
        });
        this.mSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
                try {
                    if (VideoCastControllerActivity.this.mListener != null) {
                        VideoCastControllerActivity.this.mListener.onStopTrackingTouch(seekBar);
                    }
                } catch (SeekBar seekBar2) {
                    LogUtils.LOGE(VideoCastControllerActivity.TAG, "Failed to complete seek", seekBar2);
                    VideoCastControllerActivity.this.finish();
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                try {
                    if (VideoCastControllerActivity.this.mListener != null) {
                        VideoCastControllerActivity.this.mListener.onStartTrackingTouch(seekBar);
                    }
                } catch (SeekBar seekBar2) {
                    LogUtils.LOGE(VideoCastControllerActivity.TAG, "Failed to start seek", seekBar2);
                    VideoCastControllerActivity.this.finish();
                }
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                VideoCastControllerActivity.this.mStart.setText(Utils.formatMillis(i));
                try {
                    if (VideoCastControllerActivity.this.mListener != null) {
                        VideoCastControllerActivity.this.mListener.onProgressChanged(seekBar, i, z);
                    }
                } catch (SeekBar seekBar2) {
                    LogUtils.LOGE(VideoCastControllerActivity.TAG, "Failed to set the progress result", seekBar2);
                }
            }
        });
        this.mClosedCaptionIcon.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    VideoCastControllerActivity.this.showTracksChooserDialog();
                } catch (View view2) {
                    LogUtils.LOGE(VideoCastControllerActivity.TAG, "Failed to get the media", view2);
                }
            }
        });
        this.mSkipNext.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    VideoCastControllerActivity.this.mListener.onSkipNextClicked(view);
                } catch (View view2) {
                    LogUtils.LOGE(VideoCastControllerActivity.TAG, "Failed to move to the next item in the queue", view2);
                }
            }
        });
        this.mSkipPrevious.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    VideoCastControllerActivity.this.mListener.onSkipPreviousClicked(view);
                } catch (View view2) {
                    LogUtils.LOGE(VideoCastControllerActivity.TAG, "Failed to move to the previous item in the queue", view2);
                }
            }
        });
    }

    private void showTracksChooserDialog() throws TransientNetworkDisconnectionException, NoConnectionException {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        Fragment findFragmentByTag = getSupportFragmentManager().findFragmentByTag(DIALOG_TAG);
        if (findFragmentByTag != null) {
            beginTransaction.remove(findFragmentByTag);
        }
        beginTransaction.addToBackStack(null);
        TracksChooserDialog.newInstance(this.mCastManager.getRemoteMediaInformation()).show(beginTransaction, DIALOG_TAG);
    }

    private void setUpActionBar() {
        this.mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void showLoading(boolean z) {
        this.mLoading.setVisibility(z ? false : true);
    }

    public void adjustControllersForLiveStream(boolean z) {
        int i = 0;
        int i2 = z ? 4 : 0;
        TextView textView = this.mLiveText;
        if (!z) {
            i = 4;
        }
        textView.setVisibility(i);
        this.mStart.setVisibility(i2);
        this.mEnd.setVisibility(i2);
        this.mSeekbar.setVisibility(i2);
    }

    public void setClosedCaptionState(int i) {
        switch (i) {
            case 1:
                this.mClosedCaptionIcon.setVisibility(0);
                this.mClosedCaptionIcon.setEnabled(true);
                return;
            case 2:
                this.mClosedCaptionIcon.setVisibility(0);
                this.mClosedCaptionIcon.setEnabled(false);
                return;
            case 3:
                this.mClosedCaptionIcon.setVisibility(8);
                return;
            default:
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("setClosedCaptionState(): Invalid state requested: ");
                stringBuilder.append(i);
                LogUtils.LOGE(str, stringBuilder.toString());
                return;
        }
    }

    public void onQueueItemsUpdated(int i, int i2) {
        Object obj = i2 > 0 ? 1 : null;
        i = i2 < i - 1 ? 1 : 0;
        switch (this.mNextPreviousVisibilityPolicy) {
            case 1:
                if (i != 0) {
                    this.mSkipNext.setVisibility(0);
                    this.mSkipNext.setEnabled(true);
                } else {
                    this.mSkipNext.setVisibility(4);
                }
                if (obj != null) {
                    this.mSkipPrevious.setVisibility(0);
                    this.mSkipPrevious.setEnabled(true);
                    return;
                }
                this.mSkipPrevious.setVisibility(4);
                return;
            case 2:
                if (i != 0) {
                    this.mSkipNext.setVisibility(0);
                    this.mSkipNext.setEnabled(true);
                } else {
                    this.mSkipNext.setVisibility(0);
                    this.mSkipNext.setEnabled(false);
                }
                if (obj != null) {
                    this.mSkipPrevious.setVisibility(0);
                    this.mSkipPrevious.setEnabled(true);
                    return;
                }
                this.mSkipPrevious.setVisibility(0);
                this.mSkipPrevious.setEnabled(false);
                return;
            case 3:
                this.mSkipNext.setVisibility(0);
                this.mSkipNext.setEnabled(true);
                this.mSkipPrevious.setVisibility(0);
                this.mSkipPrevious.setEnabled(true);
                return;
            case 4:
                this.mSkipNext.setVisibility(4);
                this.mSkipPrevious.setVisibility(4);
                return;
            default:
                LogUtils.LOGE(TAG, "onQueueItemsUpdated(): Invalid NextPreviousPolicy has been set");
                return;
        }
    }

    public void setPlaybackStatus(int i) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setPlaybackStatus(): state = ");
        stringBuilder.append(i);
        LogUtils.LOGD(str, stringBuilder.toString());
        switch (i) {
            case 1:
                if (this.mStreamType == 2) {
                    this.mControllers.setVisibility(0);
                    this.mLoading.setVisibility(4);
                    this.mPlaybackControls.setVisibility(0);
                    this.mPlayPause.setImageDrawable(this.mPlayDrawable);
                    this.mLine2.setText(getString(R.string.ccl_casting_to_device, new Object[]{this.mCastManager.getDeviceName()}));
                    return;
                }
                this.mPlaybackControls.setVisibility(4);
                this.mLoading.setVisibility(0);
                this.mLine2.setText(getString(R.string.ccl_loading));
                return;
            case 2:
                this.mLoading.setVisibility(4);
                this.mPlaybackControls.setVisibility(0);
                if (this.mStreamType == 2) {
                    this.mPlayPause.setImageDrawable(this.mStopDrawable);
                } else {
                    this.mPlayPause.setImageDrawable(this.mPauseDrawable);
                }
                this.mLine2.setText(getString(R.string.ccl_casting_to_device, new Object[]{this.mCastManager.getDeviceName()}));
                this.mControllers.setVisibility(0);
                return;
            case 3:
                this.mControllers.setVisibility(0);
                this.mLoading.setVisibility(4);
                this.mPlaybackControls.setVisibility(0);
                this.mPlayPause.setImageDrawable(this.mPlayDrawable);
                this.mLine2.setText(getString(R.string.ccl_casting_to_device, new Object[]{this.mCastManager.getDeviceName()}));
                return;
            case 4:
                this.mPlaybackControls.setVisibility(4);
                this.mLoading.setVisibility(0);
                this.mLine2.setText(getString(R.string.ccl_loading));
                return;
            default:
                return;
        }
    }

    public void updateSeekbar(int i, int i2) {
        this.mSeekbar.setProgress(i);
        this.mSeekbar.setMax(i2);
        this.mStart.setText(Utils.formatMillis(i));
        this.mEnd.setText(Utils.formatMillis(i2));
    }

    public void setImage(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        if (this.mPageView instanceof ImageView) {
            ((ImageView) this.mPageView).setImageBitmap(bitmap);
        } else {
            this.mPageView.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
        }
    }

    public void setTitle(String str) {
        this.mToolbar.setTitle((CharSequence) str);
    }

    public void setSubTitle(String str) {
        this.mLine2.setText(str);
    }

    public void setOnVideoCastControllerChangedListener(OnVideoCastControllerListener onVideoCastControllerListener) {
        if (onVideoCastControllerListener != null) {
            this.mListener = onVideoCastControllerListener;
        }
    }

    public void setStreamType(int i) {
        this.mStreamType = i;
    }

    public void updateControllersStatus(boolean z) {
        boolean z2 = false;
        this.mControllers.setVisibility(z ? 0 : 4);
        if (z) {
            if (this.mStreamType) {
                z2 = true;
            }
            adjustControllersForLiveStream(z2);
        }
    }

    public void closeActivity() {
        finish();
    }

    public void setNextPreviousVisibilityPolicy(int i) {
        this.mNextPreviousVisibilityPolicy = i;
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z && this.mImmersive) {
            setImmersive();
        }
    }

    @TargetApi(11)
    private void setImmersive() {
        if (VERSION.SDK_INT >= 11) {
            int systemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            if (VERSION.SDK_INT >= 14) {
                systemUiVisibility ^= 2;
            }
            if (VERSION.SDK_INT >= 16) {
                systemUiVisibility ^= 4;
            }
            if (VERSION.SDK_INT >= 18) {
                systemUiVisibility ^= 4096;
            }
            getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
            if (VERSION.SDK_INT >= 18) {
                setImmersive(true);
            }
        }
    }
}
