package com.google.android.libraries.cast.companionlibrary.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.libraries.cast.companionlibrary.R;
import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.CastException;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.NoConnectionException;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.OnFailedListener;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.TransientNetworkDisconnectionException;
import com.google.android.libraries.cast.companionlibrary.utils.FetchBitmapTask;
import com.google.android.libraries.cast.companionlibrary.utils.LogUtils;
import com.google.android.libraries.cast.companionlibrary.utils.ResponseCustomData;
import com.xumo.xumo.util.LogUtil;

public class MiniController extends RelativeLayout implements IMiniController {
    public static final int UNDEFINED_STATUS_CODE = -1;
    private boolean mAutoSetup;
    private VideoCastManager mCastManager;
    private OnMiniControllerListener mControllerListener;
    private FetchBitmapTask mFetchBitmapTask;
    private FetchBitmapTask mFetchUpcomingBitmapTask;
    private Handler mHandler;
    protected ImageView mIcon;
    private Uri mIconUri;
    private OnMiniControllerChangedListener mListener;
    protected ProgressBar mLoading;
    private View mMainContainer;
    private Drawable mPauseDrawable;
    private Drawable mPlayDrawable;
    protected ImageView mPlayPause;
    private Drawable mStopDrawable;
    private int mStreamType = 1;
    protected TextView mSubTitle;
    protected TextView mTitle;
    private View mUpcomingContainer;
    private ImageView mUpcomingIcon;
    private Uri mUpcomingIconUri;
    private MediaQueueItem mUpcomingItem;
    private View mUpcomingPlay;
    private View mUpcomingStop;
    private TextView mUpcomingTitle;

    public interface OnMiniControllerListener {
        void setVisibility(int i);
    }

    public interface OnMiniControllerChangedListener extends OnFailedListener {
        void onPlayPauseClicked(View view) throws CastException, TransientNetworkDisconnectionException, NoConnectionException;

        void onTargetActivityInvoked(Context context) throws TransientNetworkDisconnectionException, NoConnectionException;

        void onUpcomingPlayClicked(View view, MediaQueueItem mediaQueueItem);

        void onUpcomingStopClicked(View view, MediaQueueItem mediaQueueItem);
    }

    public void setProgressVisibility(boolean z) {
    }

    public MiniController(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        LayoutInflater.from(context).inflate(R.layout.mini_controller, this);
        context = getContext().obtainStyledAttributes(attributeSet, R.styleable.MiniController);
        this.mAutoSetup = context.getBoolean(R.styleable.MiniController_auto_setup, false);
        context.recycle();
        this.mPauseDrawable = getResources().getDrawable(R.drawable.ic_mini_controller_pause);
        this.mPauseDrawable.setColorFilter(getResources().getColor(17170443), Mode.SRC_IN);
        this.mPlayDrawable = getResources().getDrawable(R.drawable.ic_mini_controller_play);
        this.mPlayDrawable.setColorFilter(getResources().getColor(17170443), Mode.SRC_IN);
        this.mStopDrawable = getResources().getDrawable(R.drawable.ic_mini_controller_stop);
        this.mStopDrawable.setColorFilter(getResources().getColor(17170443), Mode.SRC_IN);
        this.mHandler = new Handler();
        this.mCastManager = VideoCastManager.getInstance();
        loadViews();
        setUpCallbacks();
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
        if (this.mControllerListener != null) {
            this.mControllerListener.setVisibility(i);
        }
    }

    public void setOnMiniControllerChangedListener(OnMiniControllerChangedListener onMiniControllerChangedListener) {
        LogUtils.d(LogUtil.CCL, "");
        if (onMiniControllerChangedListener != null) {
            this.mListener = onMiniControllerChangedListener;
        }
    }

    public void setOnMiniControllerListener(OnMiniControllerListener onMiniControllerListener) {
        LogUtils.d(LogUtil.CCL, "");
        if (onMiniControllerListener != null) {
            this.mControllerListener = onMiniControllerListener;
        }
    }

    public void removeOnMiniControllerChangedListener(OnMiniControllerChangedListener onMiniControllerChangedListener) {
        LogUtils.d(LogUtil.CCL, "");
        if (onMiniControllerChangedListener != null && this.mListener == onMiniControllerChangedListener) {
            this.mListener = null;
        }
    }

    public void removeOnMiniControllerListener(OnMiniControllerListener onMiniControllerListener) {
        LogUtils.d(LogUtil.CCL, "");
        if (onMiniControllerListener != null && this.mControllerListener == onMiniControllerListener) {
            this.mControllerListener = null;
        }
    }

    public void setStreamType(int i) {
        this.mStreamType = i;
    }

    public void setProgress(int i, int i2) {
        if (this.mStreamType != 2) {
        }
    }

    public void setUpcomingVisibility(boolean z) {
        this.mUpcomingContainer.setVisibility(z ? 0 : 8);
        setProgressVisibility(z ^ 1);
    }

    public void setUpcomingItem(MediaQueueItem mediaQueueItem) {
        this.mUpcomingItem = mediaQueueItem;
        if (mediaQueueItem != null) {
            mediaQueueItem = mediaQueueItem.getMedia();
            if (mediaQueueItem != null) {
                ResponseCustomData responseCustomData = new ResponseCustomData(mediaQueueItem.getCustomData());
                mediaQueueItem = responseCustomData.assetTitle;
                Uri uri = responseCustomData.assetThumbnailUri;
                if (mediaQueueItem == null) {
                    mediaQueueItem = "";
                }
                setUpcomingTitle(mediaQueueItem);
                setUpcomingIcon(uri);
                return;
            }
            return;
        }
        setUpcomingTitle("");
        setUpcomingIcon((Uri) null);
    }

    public void setCurrentVisibility(boolean z) {
        this.mMainContainer.setVisibility(z ? false : true);
    }

    public void setPlayPauseVisibility(boolean z) {
        this.mPlayPause.setVisibility(z ? false : true);
    }

    private void setUpCallbacks() {
        this.mPlayPause.setOnClickListener(new OnClickListener() {
            public void onClick(android.view.View r3) {
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
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r2 = this;
                r0 = com.google.android.libraries.cast.companionlibrary.widgets.MiniController.this;
                r0 = r0.mListener;
                if (r0 == 0) goto L_0x003c;
            L_0x0008:
                r0 = com.google.android.libraries.cast.companionlibrary.widgets.MiniController.this;
                r1 = 1;
                r0.setLoadingVisibility(r1);
                r0 = -1;
                r1 = com.google.android.libraries.cast.companionlibrary.widgets.MiniController.this;	 Catch:{ CastException -> 0x0031, TransientNetworkDisconnectionException -> 0x0025, NoConnectionException -> 0x0019 }
                r1 = r1.mListener;	 Catch:{ CastException -> 0x0031, TransientNetworkDisconnectionException -> 0x0025, NoConnectionException -> 0x0019 }
                r1.onPlayPauseClicked(r3);	 Catch:{ CastException -> 0x0031, TransientNetworkDisconnectionException -> 0x0025, NoConnectionException -> 0x0019 }
                goto L_0x003c;
            L_0x0019:
                r3 = com.google.android.libraries.cast.companionlibrary.widgets.MiniController.this;
                r3 = r3.mListener;
                r1 = com.google.android.libraries.cast.companionlibrary.R.string.ccl_failed_no_connection;
                r3.onFailed(r1, r0);
                goto L_0x003c;
            L_0x0025:
                r3 = com.google.android.libraries.cast.companionlibrary.widgets.MiniController.this;
                r3 = r3.mListener;
                r1 = com.google.android.libraries.cast.companionlibrary.R.string.ccl_failed_no_connection_trans;
                r3.onFailed(r1, r0);
                goto L_0x003c;
            L_0x0031:
                r3 = com.google.android.libraries.cast.companionlibrary.widgets.MiniController.this;
                r3 = r3.mListener;
                r1 = com.google.android.libraries.cast.companionlibrary.R.string.ccl_failed_perform_action;
                r3.onFailed(r1, r0);
            L_0x003c:
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.cast.companionlibrary.widgets.MiniController.1.onClick(android.view.View):void");
            }
        });
        this.mMainContainer.setOnClickListener(new OnClickListener() {
            public void onClick(android.view.View r3) {
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
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r2 = this;
                r3 = com.google.android.libraries.cast.companionlibrary.widgets.MiniController.this;
                r3 = r3.mListener;
                if (r3 == 0) goto L_0x0033;
            L_0x0008:
                r3 = com.google.android.libraries.cast.companionlibrary.widgets.MiniController.this;
                r0 = 0;
                r3.setLoadingVisibility(r0);
                r3 = "CCL";	 Catch:{ Exception -> 0x0027 }
                r0 = "";	 Catch:{ Exception -> 0x0027 }
                com.google.android.libraries.cast.companionlibrary.utils.LogUtils.d(r3, r0);	 Catch:{ Exception -> 0x0027 }
                r3 = com.google.android.libraries.cast.companionlibrary.widgets.MiniController.this;	 Catch:{ Exception -> 0x0027 }
                r3 = r3.mListener;	 Catch:{ Exception -> 0x0027 }
                r0 = com.google.android.libraries.cast.companionlibrary.widgets.MiniController.this;	 Catch:{ Exception -> 0x0027 }
                r0 = r0.mIcon;	 Catch:{ Exception -> 0x0027 }
                r0 = r0.getContext();	 Catch:{ Exception -> 0x0027 }
                r3.onTargetActivityInvoked(r0);	 Catch:{ Exception -> 0x0027 }
                goto L_0x0033;
            L_0x0027:
                r3 = com.google.android.libraries.cast.companionlibrary.widgets.MiniController.this;
                r3 = r3.mListener;
                r0 = com.google.android.libraries.cast.companionlibrary.R.string.ccl_failed_perform_action;
                r1 = -1;
                r3.onFailed(r0, r1);
            L_0x0033:
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.cast.companionlibrary.widgets.MiniController.2.onClick(android.view.View):void");
            }
        });
        this.mUpcomingPlay.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (MiniController.this.mListener != null) {
                    MiniController.this.mListener.onUpcomingPlayClicked(view, MiniController.this.mUpcomingItem);
                }
            }
        });
        this.mUpcomingStop.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (MiniController.this.mListener != null) {
                    MiniController.this.mListener.onUpcomingStopClicked(view, MiniController.this.mUpcomingItem);
                }
            }
        });
    }

    public MiniController(Context context) {
        super(context);
        loadViews();
    }

    public final void setIcon(Bitmap bitmap) {
        this.mIcon.setImageBitmap(bitmap);
    }

    private void setUpcomingIcon(Bitmap bitmap) {
        this.mUpcomingIcon.setImageBitmap(bitmap);
    }

    public void setIcon(Uri uri) {
        String str = LogUtil.CCL;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("uri=");
        stringBuilder.append(uri);
        LogUtils.d(str, stringBuilder.toString());
        if (this.mIconUri == null || !this.mIconUri.equals(uri)) {
            this.mIconUri = uri;
            if (this.mFetchBitmapTask != null) {
                this.mFetchBitmapTask.cancel(true);
            }
            this.mFetchBitmapTask = new FetchBitmapTask() {
                protected void onPostExecute(Bitmap bitmap) {
                    if (bitmap == null) {
                        bitmap = BitmapFactory.decodeResource(MiniController.this.getResources(), R.drawable.album_art_placeholder);
                    }
                    MiniController.this.setIcon(bitmap);
                    if (this == MiniController.this.mFetchBitmapTask) {
                        MiniController.this.mFetchBitmapTask = null;
                    }
                }
            };
            this.mFetchBitmapTask.execute(uri);
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mAutoSetup) {
            this.mCastManager.addMiniController(this);
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mFetchBitmapTask != null) {
            this.mFetchBitmapTask.cancel(true);
            this.mFetchBitmapTask = null;
        }
        if (this.mAutoSetup) {
            this.mCastManager.removeMiniController(this);
        }
    }

    public void setTitle(String str) {
        this.mTitle.setText(str);
    }

    public void setSubtitle(String str) {
        this.mSubTitle.setText(str);
    }

    public void setPlaybackStatus(int i, int i2) {
        StringBuilder stringBuilder;
        switch (i) {
            case 1:
                switch (this.mStreamType) {
                    case 1:
                        LogUtils.d(LogUtil.CCL, "MediaStatus.STREAM_TYPE_BUFFERED");
                        this.mPlayPause.setVisibility(4);
                        setLoadingVisibility(false);
                        return;
                    case 2:
                        LogUtils.d(LogUtil.CCL, "MediaStatus.STREAM_TYPE_LIVE");
                        if (i2 == 2) {
                            this.mPlayPause.setVisibility(0);
                            this.mPlayPause.setImageDrawable(this.mPlayDrawable);
                            setLoadingVisibility(false);
                            return;
                        }
                        this.mPlayPause.setVisibility(4);
                        setLoadingVisibility(false);
                        return;
                    default:
                        return;
                }
            case 2:
                i = this.mCastManager.getCastConfiguration().getNextPrevVisibilityPolicy();
                i2 = LogUtil.CCL;
                stringBuilder = new StringBuilder();
                stringBuilder.append("nextPrev=");
                stringBuilder.append(i);
                LogUtils.d(i2, stringBuilder.toString());
                if (i == 4) {
                    this.mPlayPause.setVisibility(4);
                    LogUtils.d(LogUtil.CCL, "mPlayPause=View.INVISIBLE");
                } else {
                    this.mPlayPause.setVisibility(0);
                    LogUtils.d(LogUtil.CCL, "mPlayPause=View.VISIBLE");
                    this.mPlayPause.setImageDrawable(getPauseStopDrawable());
                }
                setLoadingVisibility(false);
                return;
            case 3:
                this.mPlayPause.setVisibility(0);
                this.mPlayPause.setImageDrawable(this.mPlayDrawable);
                setLoadingVisibility(false);
                return;
            case 4:
                this.mPlayPause.setVisibility(4);
                setLoadingVisibility(1);
                return;
            default:
                i2 = LogUtil.CCL;
                stringBuilder = new StringBuilder();
                stringBuilder.append("default state=");
                stringBuilder.append(i);
                LogUtils.d(i2, stringBuilder.toString());
                this.mPlayPause.setVisibility(4);
                setLoadingVisibility(false);
                return;
        }
    }

    public boolean isVisible() {
        return isShown();
    }

    private void loadViews() {
        this.mIcon = (ImageView) findViewById(R.id.icon_view);
        this.mTitle = (TextView) findViewById(R.id.title_view);
        this.mSubTitle = (TextView) findViewById(R.id.subtitle_view);
        this.mPlayPause = (ImageView) findViewById(R.id.play_pause);
        this.mLoading = (ProgressBar) findViewById(R.id.loading_view);
        this.mMainContainer = findViewById(R.id.container_current);
        this.mUpcomingIcon = (ImageView) findViewById(R.id.icon_view_upcoming);
        this.mUpcomingTitle = (TextView) findViewById(R.id.title_view_upcoming);
        this.mUpcomingContainer = findViewById(R.id.container_upcoming);
        this.mUpcomingPlay = findViewById(R.id.play_upcoming);
        this.mUpcomingStop = findViewById(R.id.stop_upcoming);
    }

    private void setLoadingVisibility(boolean z) {
        this.mLoading.setVisibility(z ? false : true);
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

    private void setUpcomingIcon(Uri uri) {
        if (this.mUpcomingIconUri == null || !this.mUpcomingIconUri.equals(uri)) {
            this.mUpcomingIconUri = uri;
            if (this.mFetchUpcomingBitmapTask != null) {
                this.mFetchUpcomingBitmapTask.cancel(true);
            }
            this.mFetchUpcomingBitmapTask = new FetchBitmapTask() {
                protected void onPostExecute(Bitmap bitmap) {
                    if (bitmap == null) {
                        bitmap = BitmapFactory.decodeResource(MiniController.this.getResources(), R.drawable.album_art_placeholder);
                    }
                    MiniController.this.setUpcomingIcon(bitmap);
                    if (this == MiniController.this.mFetchUpcomingBitmapTask) {
                        MiniController.this.mFetchUpcomingBitmapTask = null;
                    }
                }
            };
            this.mFetchUpcomingBitmapTask.execute(uri);
        }
    }

    private void setUpcomingTitle(String str) {
        this.mUpcomingTitle.setText(str);
    }
}
