package com.google.android.libraries.cast.companionlibrary.cast.dialog.video;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.mediarouter.app.MediaRouteControllerDialog;
import com.google.android.libraries.cast.companionlibrary.R;
import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;
import com.google.android.libraries.cast.companionlibrary.cast.callbacks.VideoCastConsumerImpl;
import com.google.android.libraries.cast.companionlibrary.utils.FetchBitmapTask;
import com.google.android.libraries.cast.companionlibrary.utils.LogUtils;
import com.xumo.xumo.util.LogUtil;

public class VideoMediaRouteControllerDialog extends MediaRouteControllerDialog {
    private static final String TAG = LogUtils.makeLogTag(VideoMediaRouteControllerDialog.class);
    private VideoCastConsumerImpl mCastConsumerImpl;
    private VideoCastManager mCastManager;
    private Context mContext;
    private TextView mEmptyText;
    private FetchBitmapTask mFetchBitmap;
    private ImageView mIcon;
    private View mIconContainer;
    private Uri mIconUri;
    private ProgressBar mLoading;
    private Drawable mPauseDrawable;
    private ImageView mPausePlay;
    private Drawable mPlayDrawable;
    protected int mState;
    private Drawable mStopDrawable;
    private int mStreamType;
    private TextView mSubTitle;
    private View mTextContainer;
    private TextView mTitle;

    public VideoMediaRouteControllerDialog(Context context, int i) {
        super(context, i);
    }

    public VideoMediaRouteControllerDialog(Context context) {
        super(context, R.style.CCLCastDialog);
        try {
            this.mContext = context;
            this.mCastManager = VideoCastManager.getInstance();
            this.mState = this.mCastManager.getPlaybackStatus();
            this.mCastConsumerImpl = new VideoCastConsumerImpl() {
                public void onRemoteMediaPlayerStatusUpdated() {
                    VideoMediaRouteControllerDialog.this.mState = VideoMediaRouteControllerDialog.this.mCastManager.getPlaybackStatus();
                    VideoMediaRouteControllerDialog.this.updatePlayPauseState(VideoMediaRouteControllerDialog.this.mState);
                }

                public void onRemoteMediaPlayerMetadataUpdated() {
                    VideoMediaRouteControllerDialog.this.updateMetadata();
                }
            };
            this.mCastManager.addVideoCastConsumer(this.mCastConsumerImpl);
            this.mPauseDrawable = context.getResources().getDrawable(R.drawable.ic_media_route_controller_pause);
            this.mPlayDrawable = context.getResources().getDrawable(R.drawable.ic_media_route_controller_play);
            this.mStopDrawable = context.getResources().getDrawable(R.drawable.ic_media_route_controller_stop);
        } catch (Context context2) {
            LogUtils.LOGE(TAG, "Failed to update the content of dialog", context2);
        }
    }

    protected void onStop() {
        if (this.mCastManager != null) {
            this.mCastManager.removeVideoCastConsumer(this.mCastConsumerImpl);
            this.mCastManager = null;
        }
        if (this.mFetchBitmap != null) {
            this.mFetchBitmap.cancel(true);
            this.mFetchBitmap = null;
        }
        super.onStop();
    }

    private void hideControls(boolean z, int i) {
        int i2 = 0;
        int i3 = z ? 8 : 0;
        this.mIcon.setVisibility(i3);
        this.mIconContainer.setVisibility(i3);
        this.mTextContainer.setVisibility(i3);
        TextView textView = this.mEmptyText;
        if (i == 0) {
            i = R.string.ccl_no_media_info;
        }
        textView.setText(i);
        i = this.mEmptyText;
        if (!z) {
            i2 = 8;
        }
        i.setVisibility(i2);
        if (z) {
            this.mPausePlay.setVisibility(i3);
        }
    }

    private void updateMetadata() {
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
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r5 = this;
        r0 = 1;
        r1 = r5.mCastManager;	 Catch:{ TransientNetworkDisconnectionException -> 0x0041, TransientNetworkDisconnectionException -> 0x0041 }
        r1 = r1.getRemoteMediaInformation();	 Catch:{ TransientNetworkDisconnectionException -> 0x0041, TransientNetworkDisconnectionException -> 0x0041 }
        if (r1 != 0) goto L_0x000f;
    L_0x0009:
        r1 = com.google.android.libraries.cast.companionlibrary.R.string.ccl_no_media_info;
        r5.hideControls(r0, r1);
        return;
    L_0x000f:
        r0 = r1.getStreamType();
        r5.mStreamType = r0;
        r0 = 0;
        r5.hideControls(r0, r0);
        r0 = r1.getMetadata();
        r1 = r1.getCustomData();
        r2 = new com.google.android.libraries.cast.companionlibrary.utils.ResponseCustomData;
        r2.<init>(r1);
        r1 = r2.assetTitle;
        r2 = r2.assetThumbnailUri;
        r3 = r5.mSubTitle;
        r4 = "com.google.android.gms.cast.metadata.SUBTITLE";
        r0 = r0.getString(r4);
        r3.setText(r0);
        if (r1 == 0) goto L_0x0038;
    L_0x0037:
        goto L_0x003a;
    L_0x0038:
        r1 = "";
    L_0x003a:
        r5.setTitle(r1);
        r5.setIcon(r2);
        return;
    L_0x0041:
        r1 = com.google.android.libraries.cast.companionlibrary.R.string.ccl_failed_no_connection_short;
        r5.hideControls(r0, r1);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.cast.companionlibrary.cast.dialog.video.VideoMediaRouteControllerDialog.updateMetadata():void");
    }

    public void setIcon(Uri uri) {
        String str = LogUtil.CCL;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setIcon uri=");
        stringBuilder.append(uri);
        LogUtils.d(str, stringBuilder.toString());
        if (this.mIconUri == null || !this.mIconUri.equals(uri)) {
            this.mIconUri = uri;
            if (uri == null) {
                this.mIcon.setImageBitmap(BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.album_art_placeholder));
                return;
            }
            if (this.mFetchBitmap != null) {
                this.mFetchBitmap.cancel(true);
            }
            this.mFetchBitmap = new FetchBitmapTask() {
                protected void onPostExecute(Bitmap bitmap) {
                    VideoMediaRouteControllerDialog.this.mIcon.setImageBitmap(bitmap);
                    if (this == VideoMediaRouteControllerDialog.this.mFetchBitmap) {
                        VideoMediaRouteControllerDialog.this.mFetchBitmap = null;
                    }
                }
            };
            this.mFetchBitmap.execute(this.mIconUri);
        }
    }

    private void updatePlayPauseState(int i) {
        if (this.mPausePlay != null) {
            switch (i) {
                case 1:
                    this.mPausePlay.setVisibility(4);
                    setLoadingVisibility(false);
                    if (this.mState == 1 && this.mCastManager.getIdleReason() == 1) {
                        hideControls(true, R.string.ccl_no_media_info);
                        return;
                    }
                    switch (this.mStreamType) {
                        case 1:
                            this.mPausePlay.setVisibility(4);
                            setLoadingVisibility(false);
                            return;
                        case 2:
                            if (this.mCastManager.getIdleReason() == 2) {
                                this.mPausePlay.setImageDrawable(this.mPlayDrawable);
                                adjustControlsVisibility(true);
                                return;
                            }
                            this.mPausePlay.setVisibility(4);
                            setLoadingVisibility(false);
                            return;
                        default:
                            return;
                    }
                case 2:
                    this.mPausePlay.setImageDrawable(getPauseStopDrawable());
                    adjustControlsVisibility(true);
                    return;
                case 3:
                    this.mPausePlay.setImageDrawable(this.mPlayDrawable);
                    adjustControlsVisibility(true);
                    return;
                case 4:
                    adjustControlsVisibility(false);
                    return;
                default:
                    this.mPausePlay.setVisibility(4);
                    setLoadingVisibility(false);
                    return;
            }
        }
    }

    private Drawable getPauseStopDrawable() {
        switch (this.mStreamType) {
            case 1:
                return this.mPauseDrawable;
            case 2:
                return this.mStopDrawable;
            default:
                return this.mPauseDrawable;
        }
    }

    private void setLoadingVisibility(boolean z) {
        this.mLoading.setVisibility(z ? false : true);
    }

    private void adjustControlsVisibility(boolean z) {
        this.mPausePlay.setVisibility(z ? 0 : 4);
        setLoadingVisibility(z ^ 1);
    }

    public View onCreateMediaControlView(Bundle bundle) {
        bundle = getLayoutInflater().inflate(R.layout.custom_media_route_controller_controls_dialog, null);
        loadViews(bundle);
        this.mState = this.mCastManager.getPlaybackStatus();
        updateMetadata();
        updatePlayPauseState(this.mState);
        setUpCallbacks();
        return bundle;
    }

    private void setUpCallbacks() {
        this.mPausePlay.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (VideoMediaRouteControllerDialog.this.mCastManager != null) {
                    try {
                        VideoMediaRouteControllerDialog.this.adjustControlsVisibility(false);
                        VideoMediaRouteControllerDialog.this.mCastManager.togglePlayback();
                    } catch (Throwable e) {
                        VideoMediaRouteControllerDialog.this.adjustControlsVisibility(true);
                        LogUtils.LOGE(VideoMediaRouteControllerDialog.TAG, "Failed to toggle playback", e);
                    } catch (Throwable e2) {
                        VideoMediaRouteControllerDialog.this.adjustControlsVisibility(true);
                        LogUtils.LOGE(VideoMediaRouteControllerDialog.TAG, "Failed to toggle playback due to network issues", e2);
                    }
                }
            }
        });
        this.mIcon.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                VideoMediaRouteControllerDialog.this.showTargetActivity();
            }
        });
        this.mTextContainer.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                VideoMediaRouteControllerDialog.this.showTargetActivity();
            }
        });
    }

    private void showTargetActivity() {
        if (this.mCastManager != null && this.mCastManager.getTargetActivity() != null) {
            try {
                this.mCastManager.onTargetActivityInvoked(this.mContext);
            } catch (Throwable e) {
                LogUtils.LOGE(TAG, "Failed to start the target activity due to network issues", e);
            }
            cancel();
        }
    }

    private void loadViews(View view) {
        this.mIcon = (ImageView) view.findViewById(R.id.iconView);
        this.mIconContainer = view.findViewById(R.id.iconContainer);
        this.mTextContainer = view.findViewById(R.id.textContainer);
        this.mPausePlay = (ImageView) view.findViewById(R.id.playPauseView);
        this.mTitle = (TextView) view.findViewById(R.id.titleView);
        this.mSubTitle = (TextView) view.findViewById(R.id.subTitleView);
        this.mLoading = (ProgressBar) view.findViewById(R.id.loadingView);
        this.mEmptyText = (TextView) view.findViewById(R.id.emptyView);
    }
}
