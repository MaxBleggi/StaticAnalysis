package com.google.android.exoplayer2.ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.android.volley.toolbox.ImageRequest;
import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Player.EventListener;
import com.google.android.exoplayer2.Player.EventListener.-CC;
import com.google.android.exoplayer2.Player.TextComponent;
import com.google.android.exoplayer2.Player.VideoComponent;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.Metadata.Entry;
import com.google.android.exoplayer2.metadata.id3.ApicFrame;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout.AspectRatioListener;
import com.google.android.exoplayer2.ui.PlayerControlView.VisibilityListener;
import com.google.android.exoplayer2.ui.spherical.SingleTapListener;
import com.google.android.exoplayer2.ui.spherical.SphericalSurfaceView;
import com.google.android.exoplayer2.ui.spherical.SphericalSurfaceView.SurfaceListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ErrorMessageProvider;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoListener;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public class PlayerView extends FrameLayout {
    public static final int SHOW_BUFFERING_ALWAYS = 2;
    public static final int SHOW_BUFFERING_NEVER = 0;
    public static final int SHOW_BUFFERING_WHEN_PLAYING = 1;
    private static final int SURFACE_TYPE_MONO360_VIEW = 3;
    private static final int SURFACE_TYPE_NONE = 0;
    private static final int SURFACE_TYPE_SURFACE_VIEW = 1;
    private static final int SURFACE_TYPE_TEXTURE_VIEW = 2;
    private final ImageView artworkView;
    @Nullable
    private final View bufferingView;
    private final ComponentListener componentListener;
    private final AspectRatioFrameLayout contentFrame;
    private final PlayerControlView controller;
    private boolean controllerAutoShow;
    private boolean controllerHideDuringAds;
    private boolean controllerHideOnTouch;
    private int controllerShowTimeoutMs;
    @Nullable
    private CharSequence customErrorMessage;
    @Nullable
    private Drawable defaultArtwork;
    @Nullable
    private ErrorMessageProvider<? super ExoPlaybackException> errorMessageProvider;
    @Nullable
    private final TextView errorMessageView;
    private boolean keepContentOnPlayerReset;
    private final FrameLayout overlayFrameLayout;
    private Player player;
    private int showBuffering;
    private final View shutterView;
    private final SubtitleView subtitleView;
    private final View surfaceView;
    private int textureViewRotation;
    private boolean useArtwork;
    private boolean useController;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ShowBuffering {
    }

    private final class ComponentListener implements EventListener, TextOutput, VideoListener, OnLayoutChangeListener, SurfaceListener, SingleTapListener {
        public /* synthetic */ void onLoadingChanged(boolean z) {
            -CC.$default$onLoadingChanged(this, z);
        }

        public /* synthetic */ void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            -CC.$default$onPlaybackParametersChanged(this, playbackParameters);
        }

        public /* synthetic */ void onPlayerError(ExoPlaybackException exoPlaybackException) {
            -CC.$default$onPlayerError(this, exoPlaybackException);
        }

        public /* synthetic */ void onRepeatModeChanged(int i) {
            -CC.$default$onRepeatModeChanged(this, i);
        }

        public /* synthetic */ void onSeekProcessed() {
            -CC.$default$onSeekProcessed(this);
        }

        public /* synthetic */ void onShuffleModeEnabledChanged(boolean z) {
            -CC.$default$onShuffleModeEnabledChanged(this, z);
        }

        public /* synthetic */ void onSurfaceSizeChanged(int i, int i2) {
            VideoListener.-CC.$default$onSurfaceSizeChanged(this, i, i2);
        }

        public /* synthetic */ void onTimelineChanged(Timeline timeline, @Nullable Object obj, int i) {
            -CC.$default$onTimelineChanged(this, timeline, obj, i);
        }

        private ComponentListener() {
        }

        public void onCues(List<Cue> list) {
            if (PlayerView.this.subtitleView != null) {
                PlayerView.this.subtitleView.onCues(list);
            }
        }

        public void onVideoSizeChanged(int i, int i2, int i3, float f) {
            if (PlayerView.this.contentFrame != null) {
                if (i2 != 0) {
                    if (i != 0) {
                        i = (((float) i) * f) / ((float) i2);
                        if ((PlayerView.this.surfaceView instanceof TextureView) != 0) {
                            if (i3 == 90 || i3 == 270) {
                                i = 1.0f / i;
                            }
                            if (PlayerView.this.textureViewRotation != 0) {
                                PlayerView.this.surfaceView.removeOnLayoutChangeListener(this);
                            }
                            PlayerView.this.textureViewRotation = i3;
                            if (PlayerView.this.textureViewRotation != 0) {
                                PlayerView.this.surfaceView.addOnLayoutChangeListener(this);
                            }
                            PlayerView.applyTextureViewRotation((TextureView) PlayerView.this.surfaceView, PlayerView.this.textureViewRotation);
                        } else if ((PlayerView.this.surfaceView instanceof SphericalSurfaceView) != 0) {
                            i = 0;
                        }
                        PlayerView.this.contentFrame.setAspectRatio(i);
                    }
                }
                i = 1065353216;
                if ((PlayerView.this.surfaceView instanceof TextureView) != 0) {
                    i = 1.0f / i;
                    if (PlayerView.this.textureViewRotation != 0) {
                        PlayerView.this.surfaceView.removeOnLayoutChangeListener(this);
                    }
                    PlayerView.this.textureViewRotation = i3;
                    if (PlayerView.this.textureViewRotation != 0) {
                        PlayerView.this.surfaceView.addOnLayoutChangeListener(this);
                    }
                    PlayerView.applyTextureViewRotation((TextureView) PlayerView.this.surfaceView, PlayerView.this.textureViewRotation);
                } else if ((PlayerView.this.surfaceView instanceof SphericalSurfaceView) != 0) {
                    i = 0;
                }
                PlayerView.this.contentFrame.setAspectRatio(i);
            }
        }

        public void onRenderedFirstFrame() {
            if (PlayerView.this.shutterView != null) {
                PlayerView.this.shutterView.setVisibility(4);
            }
        }

        public void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
            PlayerView.this.updateForCurrentTrackSelections(null);
        }

        public void onPlayerStateChanged(boolean z, int i) {
            PlayerView.this.updateBuffering();
            PlayerView.this.updateErrorMessage();
            if (PlayerView.this.isPlayingAd() && PlayerView.this.controllerHideDuringAds) {
                PlayerView.this.hideController();
            } else {
                PlayerView.this.maybeShowController(0);
            }
        }

        public void onPositionDiscontinuity(int i) {
            if (PlayerView.this.isPlayingAd() != 0 && PlayerView.this.controllerHideDuringAds != 0) {
                PlayerView.this.hideController();
            }
        }

        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            PlayerView.applyTextureViewRotation((TextureView) view, PlayerView.this.textureViewRotation);
        }

        public void surfaceChanged(@Nullable Surface surface) {
            if (PlayerView.this.player != null) {
                VideoComponent videoComponent = PlayerView.this.player.getVideoComponent();
                if (videoComponent != null) {
                    videoComponent.setVideoSurface(surface);
                }
            }
        }

        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return PlayerView.this.toggleControllerVisibility();
        }
    }

    @SuppressLint({"InlinedApi"})
    private boolean isDpadKey(int i) {
        if (!(i == 19 || i == 270 || i == 22 || i == 271 || i == 20 || i == 269 || i == 21 || i == 268)) {
            if (i != 23) {
                return false;
            }
        }
        return true;
    }

    public PlayerView(Context context) {
        this(context, null);
    }

    public PlayerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PlayerView(Context context, AttributeSet attributeSet, int i) {
        ViewGroup viewGroup = this;
        Context context2 = context;
        AttributeSet attributeSet2 = attributeSet;
        super(context, attributeSet, i);
        if (isInEditMode()) {
            viewGroup.contentFrame = null;
            viewGroup.shutterView = null;
            viewGroup.surfaceView = null;
            viewGroup.artworkView = null;
            viewGroup.subtitleView = null;
            viewGroup.bufferingView = null;
            viewGroup.errorMessageView = null;
            viewGroup.controller = null;
            viewGroup.componentListener = null;
            viewGroup.overlayFrameLayout = null;
            View imageView = new ImageView(context2);
            if (Util.SDK_INT >= 23) {
                configureEditModeLogoV23(getResources(), imageView);
            } else {
                configureEditModeLogo(getResources(), imageView);
            }
            addView(imageView);
            return;
        }
        int color;
        Object obj;
        int resourceId;
        Object obj2;
        int i2;
        boolean z;
        boolean z2;
        Object obj3;
        boolean z3;
        int i3;
        boolean z4;
        View sphericalSurfaceView;
        boolean z5;
        int i4 = R.layout.exo_player_view;
        int i5 = 5000;
        int i6 = true;
        boolean z6 = false;
        int[] iArr;
        if (attributeSet2 != null) {
            Theme theme = context.getTheme();
            iArr = R.styleable.PlayerView;
            TypedArray obtainStyledAttributes = theme.obtainStyledAttributes(attributeSet2, iArr, 0, 0);
            try {
                iArr = obtainStyledAttributes.hasValue(R.styleable.PlayerView_shutter_background_color);
                color = obtainStyledAttributes.getColor(R.styleable.PlayerView_shutter_background_color, 0);
                i4 = obtainStyledAttributes.getResourceId(R.styleable.PlayerView_player_layout_id, i4);
                obj = obtainStyledAttributes.getBoolean(R.styleable.PlayerView_use_artwork, true);
                resourceId = obtainStyledAttributes.getResourceId(R.styleable.PlayerView_default_artwork, 0);
                Object obj4 = obtainStyledAttributes.getBoolean(R.styleable.PlayerView_use_controller, true);
                obj2 = obtainStyledAttributes.getInt(R.styleable.PlayerView_surface_type, 1);
                i2 = obtainStyledAttributes.getInt(R.styleable.PlayerView_resize_mode, 0);
                i5 = obtainStyledAttributes.getInt(R.styleable.PlayerView_show_timeout, 5000);
                boolean z7 = obtainStyledAttributes.getBoolean(R.styleable.PlayerView_hide_on_touch, true);
                z6 = obtainStyledAttributes.getBoolean(R.styleable.PlayerView_auto_show, true);
                int i7 = i4;
                i6 = obtainStyledAttributes.getInteger(R.styleable.PlayerView_show_buffering, 0);
                boolean z8 = z7;
                viewGroup.keepContentOnPlayerReset = obtainStyledAttributes.getBoolean(R.styleable.PlayerView_keep_content_on_player_reset, viewGroup.keepContentOnPlayerReset);
                z = obtainStyledAttributes.getBoolean(R.styleable.PlayerView_hide_during_ads, true);
                z2 = z6;
                obj3 = obj4;
                z3 = z;
                i3 = i5;
                i4 = i7;
                z4 = z8;
            } finally {
                obtainStyledAttributes.recycle();
            }
        } else {
            z4 = true;
            i6 = 0;
            i3 = 5000;
            z2 = true;
            iArr = null;
            color = 0;
            obj = 1;
            resourceId = 0;
            z3 = true;
            obj2 = 1;
            i2 = 0;
            obj3 = 1;
        }
        LayoutInflater.from(context).inflate(i4, viewGroup);
        viewGroup.componentListener = new ComponentListener();
        setDescendantFocusability(262144);
        viewGroup.contentFrame = (AspectRatioFrameLayout) findViewById(R.id.exo_content_frame);
        if (viewGroup.contentFrame != null) {
            setResizeModeRaw(viewGroup.contentFrame, i2);
        }
        viewGroup.shutterView = findViewById(R.id.exo_shutter);
        if (!(viewGroup.shutterView == null || r9 == null)) {
            viewGroup.shutterView.setBackgroundColor(color);
        }
        if (viewGroup.contentFrame == null || obj2 == null) {
            viewGroup.surfaceView = null;
        } else {
            LayoutParams layoutParams = new LayoutParams(-1, -1);
            switch (obj2) {
                case 2:
                    viewGroup.surfaceView = new TextureView(context2);
                    break;
                case 3:
                    Assertions.checkState(Util.SDK_INT >= 15);
                    sphericalSurfaceView = new SphericalSurfaceView(context2);
                    sphericalSurfaceView.setSurfaceListener(viewGroup.componentListener);
                    sphericalSurfaceView.setSingleTapListener(viewGroup.componentListener);
                    viewGroup.surfaceView = sphericalSurfaceView;
                    break;
                default:
                    viewGroup.surfaceView = new SurfaceView(context2);
                    break;
            }
            viewGroup.surfaceView.setLayoutParams(layoutParams);
            viewGroup.contentFrame.addView(viewGroup.surfaceView, 0);
        }
        viewGroup.overlayFrameLayout = (FrameLayout) findViewById(R.id.exo_overlay);
        viewGroup.artworkView = (ImageView) findViewById(R.id.exo_artwork);
        z = (obj == null || viewGroup.artworkView == null) ? false : true;
        viewGroup.useArtwork = z;
        if (resourceId != 0) {
            viewGroup.defaultArtwork = ContextCompat.getDrawable(getContext(), resourceId);
        }
        viewGroup.subtitleView = (SubtitleView) findViewById(R.id.exo_subtitles);
        if (viewGroup.subtitleView != null) {
            viewGroup.subtitleView.setUserDefaultStyle();
            viewGroup.subtitleView.setUserDefaultTextSize();
        }
        viewGroup.bufferingView = findViewById(R.id.exo_buffering);
        if (viewGroup.bufferingView != null) {
            viewGroup.bufferingView.setVisibility(8);
        }
        viewGroup.showBuffering = i6;
        viewGroup.errorMessageView = (TextView) findViewById(R.id.exo_error_message);
        if (viewGroup.errorMessageView != null) {
            viewGroup.errorMessageView.setVisibility(8);
        }
        PlayerControlView playerControlView = (PlayerControlView) findViewById(R.id.exo_controller);
        sphericalSurfaceView = findViewById(R.id.exo_controller_placeholder);
        if (playerControlView != null) {
            viewGroup.controller = playerControlView;
            z5 = false;
        } else if (sphericalSurfaceView != null) {
            z5 = false;
            viewGroup.controller = new PlayerControlView(context2, null, 0, attributeSet2);
            viewGroup.controller.setLayoutParams(sphericalSurfaceView.getLayoutParams());
            ViewGroup viewGroup2 = (ViewGroup) sphericalSurfaceView.getParent();
            int indexOfChild = viewGroup2.indexOfChild(sphericalSurfaceView);
            viewGroup2.removeView(sphericalSurfaceView);
            viewGroup2.addView(viewGroup.controller, indexOfChild);
        } else {
            z5 = false;
            viewGroup.controller = null;
        }
        if (viewGroup.controller == null) {
            i3 = 0;
        }
        viewGroup.controllerShowTimeoutMs = i3;
        viewGroup.controllerHideOnTouch = z4;
        viewGroup.controllerAutoShow = z2;
        viewGroup.controllerHideDuringAds = z3;
        if (!(obj3 == null || viewGroup.controller == null)) {
            z5 = true;
        }
        viewGroup.useController = z5;
        hideController();
    }

    public static void switchTargetView(@NonNull Player player, @Nullable PlayerView playerView, @Nullable PlayerView playerView2) {
        if (playerView != playerView2) {
            if (playerView2 != null) {
                playerView2.setPlayer(player);
            }
            if (playerView != null) {
                playerView.setPlayer(null);
            }
        }
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(@Nullable Player player) {
        boolean z;
        VideoComponent videoComponent;
        TextComponent textComponent;
        Assertions.checkState(Looper.myLooper() == Looper.getMainLooper());
        if (player != null) {
            if (player.getApplicationLooper() != Looper.getMainLooper()) {
                z = false;
                Assertions.checkArgument(z);
                if (this.player == player) {
                    if (this.player != null) {
                        this.player.removeListener(this.componentListener);
                        videoComponent = this.player.getVideoComponent();
                        if (videoComponent != null) {
                            videoComponent.removeVideoListener(this.componentListener);
                            if (this.surfaceView instanceof TextureView) {
                                videoComponent.clearVideoTextureView((TextureView) this.surfaceView);
                            } else if (this.surfaceView instanceof SphericalSurfaceView) {
                                ((SphericalSurfaceView) this.surfaceView).setVideoComponent(null);
                            } else if (this.surfaceView instanceof SurfaceView) {
                                videoComponent.clearVideoSurfaceView((SurfaceView) this.surfaceView);
                            }
                        }
                        textComponent = this.player.getTextComponent();
                        if (textComponent != null) {
                            textComponent.removeTextOutput(this.componentListener);
                        }
                    }
                    this.player = player;
                    if (this.useController) {
                        this.controller.setPlayer(player);
                    }
                    if (this.subtitleView != null) {
                        this.subtitleView.setCues(null);
                    }
                    updateBuffering();
                    updateErrorMessage();
                    updateForCurrentTrackSelections(true);
                    if (player == null) {
                        videoComponent = player.getVideoComponent();
                        if (videoComponent != null) {
                            if (this.surfaceView instanceof TextureView) {
                                videoComponent.setVideoTextureView((TextureView) this.surfaceView);
                            } else if (this.surfaceView instanceof SphericalSurfaceView) {
                                ((SphericalSurfaceView) this.surfaceView).setVideoComponent(videoComponent);
                            } else if (this.surfaceView instanceof SurfaceView) {
                                videoComponent.setVideoSurfaceView((SurfaceView) this.surfaceView);
                            }
                            videoComponent.addVideoListener(this.componentListener);
                        }
                        textComponent = player.getTextComponent();
                        if (textComponent != null) {
                            textComponent.addTextOutput(this.componentListener);
                        }
                        player.addListener(this.componentListener);
                        maybeShowController(false);
                    } else {
                        hideController();
                    }
                }
            }
        }
        z = true;
        Assertions.checkArgument(z);
        if (this.player == player) {
            if (this.player != null) {
                this.player.removeListener(this.componentListener);
                videoComponent = this.player.getVideoComponent();
                if (videoComponent != null) {
                    videoComponent.removeVideoListener(this.componentListener);
                    if (this.surfaceView instanceof TextureView) {
                        videoComponent.clearVideoTextureView((TextureView) this.surfaceView);
                    } else if (this.surfaceView instanceof SphericalSurfaceView) {
                        ((SphericalSurfaceView) this.surfaceView).setVideoComponent(null);
                    } else if (this.surfaceView instanceof SurfaceView) {
                        videoComponent.clearVideoSurfaceView((SurfaceView) this.surfaceView);
                    }
                }
                textComponent = this.player.getTextComponent();
                if (textComponent != null) {
                    textComponent.removeTextOutput(this.componentListener);
                }
            }
            this.player = player;
            if (this.useController) {
                this.controller.setPlayer(player);
            }
            if (this.subtitleView != null) {
                this.subtitleView.setCues(null);
            }
            updateBuffering();
            updateErrorMessage();
            updateForCurrentTrackSelections(true);
            if (player == null) {
                hideController();
            } else {
                videoComponent = player.getVideoComponent();
                if (videoComponent != null) {
                    if (this.surfaceView instanceof TextureView) {
                        videoComponent.setVideoTextureView((TextureView) this.surfaceView);
                    } else if (this.surfaceView instanceof SphericalSurfaceView) {
                        ((SphericalSurfaceView) this.surfaceView).setVideoComponent(videoComponent);
                    } else if (this.surfaceView instanceof SurfaceView) {
                        videoComponent.setVideoSurfaceView((SurfaceView) this.surfaceView);
                    }
                    videoComponent.addVideoListener(this.componentListener);
                }
                textComponent = player.getTextComponent();
                if (textComponent != null) {
                    textComponent.addTextOutput(this.componentListener);
                }
                player.addListener(this.componentListener);
                maybeShowController(false);
            }
        }
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
        if (this.surfaceView instanceof SurfaceView) {
            this.surfaceView.setVisibility(i);
        }
    }

    public void setResizeMode(int i) {
        Assertions.checkState(this.contentFrame != null);
        this.contentFrame.setResizeMode(i);
    }

    public int getResizeMode() {
        Assertions.checkState(this.contentFrame != null);
        return this.contentFrame.getResizeMode();
    }

    public boolean getUseArtwork() {
        return this.useArtwork;
    }

    public void setUseArtwork(boolean z) {
        boolean z2;
        if (z) {
            if (this.artworkView == null) {
                z2 = false;
                Assertions.checkState(z2);
                if (this.useArtwork != z) {
                    this.useArtwork = z;
                    updateForCurrentTrackSelections(false);
                }
            }
        }
        z2 = true;
        Assertions.checkState(z2);
        if (this.useArtwork != z) {
            this.useArtwork = z;
            updateForCurrentTrackSelections(false);
        }
    }

    @Nullable
    public Drawable getDefaultArtwork() {
        return this.defaultArtwork;
    }

    @Deprecated
    public void setDefaultArtwork(@Nullable Bitmap bitmap) {
        Drawable drawable;
        if (bitmap == null) {
            drawable = null;
        } else {
            drawable = new BitmapDrawable(getResources(), bitmap);
        }
        setDefaultArtwork(drawable);
    }

    public void setDefaultArtwork(@Nullable Drawable drawable) {
        if (this.defaultArtwork != drawable) {
            this.defaultArtwork = drawable;
            updateForCurrentTrackSelections(null);
        }
    }

    public boolean getUseController() {
        return this.useController;
    }

    public void setUseController(boolean z) {
        boolean z2;
        if (z) {
            if (this.controller == null) {
                z2 = false;
                Assertions.checkState(z2);
                if (this.useController == z) {
                    this.useController = z;
                    if (z) {
                        this.controller.setPlayer(this.player);
                    } else if (this.controller) {
                        this.controller.hide();
                        this.controller.setPlayer(null);
                    }
                }
            }
        }
        z2 = true;
        Assertions.checkState(z2);
        if (this.useController == z) {
            this.useController = z;
            if (z) {
                this.controller.setPlayer(this.player);
            } else if (this.controller) {
                this.controller.hide();
                this.controller.setPlayer(null);
            }
        }
    }

    public void setShutterBackgroundColor(int i) {
        if (this.shutterView != null) {
            this.shutterView.setBackgroundColor(i);
        }
    }

    public void setKeepContentOnPlayerReset(boolean z) {
        if (this.keepContentOnPlayerReset != z) {
            this.keepContentOnPlayerReset = z;
            updateForCurrentTrackSelections(false);
        }
    }

    @Deprecated
    public void setShowBuffering(boolean z) {
        setShowBuffering((int) z);
    }

    public void setShowBuffering(int i) {
        if (this.showBuffering != i) {
            this.showBuffering = i;
            updateBuffering();
        }
    }

    public void setErrorMessageProvider(@Nullable ErrorMessageProvider<? super ExoPlaybackException> errorMessageProvider) {
        if (this.errorMessageProvider != errorMessageProvider) {
            this.errorMessageProvider = errorMessageProvider;
            updateErrorMessage();
        }
    }

    public void setCustomErrorMessage(@Nullable CharSequence charSequence) {
        Assertions.checkState(this.errorMessageView != null);
        this.customErrorMessage = charSequence;
        updateErrorMessage();
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (this.player == null || !this.player.isPlayingAd()) {
            boolean z = false;
            Object obj = (isDpadKey(keyEvent.getKeyCode()) && this.useController && !this.controller.isVisible()) ? 1 : null;
            if (!(obj == null && !dispatchMediaKeyEvent(keyEvent) && super.dispatchKeyEvent(keyEvent) == null)) {
                z = true;
            }
            if (z) {
                maybeShowController(true);
            }
            return z;
        }
        this.overlayFrameLayout.requestFocus();
        return super.dispatchKeyEvent(keyEvent);
    }

    public boolean dispatchMediaKeyEvent(KeyEvent keyEvent) {
        return (!this.useController || this.controller.dispatchMediaKeyEvent(keyEvent) == null) ? null : true;
    }

    public boolean isControllerVisible() {
        return this.controller != null && this.controller.isVisible();
    }

    public void showController() {
        showController(shouldShowControllerIndefinitely());
    }

    public void hideController() {
        if (this.controller != null) {
            this.controller.hide();
        }
    }

    public int getControllerShowTimeoutMs() {
        return this.controllerShowTimeoutMs;
    }

    public void setControllerShowTimeoutMs(int i) {
        Assertions.checkState(this.controller != null);
        this.controllerShowTimeoutMs = i;
        if (this.controller.isVisible() != 0) {
            showController();
        }
    }

    public boolean getControllerHideOnTouch() {
        return this.controllerHideOnTouch;
    }

    public void setControllerHideOnTouch(boolean z) {
        Assertions.checkState(this.controller != null);
        this.controllerHideOnTouch = z;
    }

    public boolean getControllerAutoShow() {
        return this.controllerAutoShow;
    }

    public void setControllerAutoShow(boolean z) {
        this.controllerAutoShow = z;
    }

    public void setControllerHideDuringAds(boolean z) {
        this.controllerHideDuringAds = z;
    }

    public void setControllerVisibilityListener(VisibilityListener visibilityListener) {
        Assertions.checkState(this.controller != null);
        this.controller.setVisibilityListener(visibilityListener);
    }

    public void setPlaybackPreparer(@Nullable PlaybackPreparer playbackPreparer) {
        Assertions.checkState(this.controller != null);
        this.controller.setPlaybackPreparer(playbackPreparer);
    }

    public void setControlDispatcher(@Nullable ControlDispatcher controlDispatcher) {
        Assertions.checkState(this.controller != null);
        this.controller.setControlDispatcher(controlDispatcher);
    }

    public void setRewindIncrementMs(int i) {
        Assertions.checkState(this.controller != null);
        this.controller.setRewindIncrementMs(i);
    }

    public void setFastForwardIncrementMs(int i) {
        Assertions.checkState(this.controller != null);
        this.controller.setFastForwardIncrementMs(i);
    }

    public void setRepeatToggleModes(int i) {
        Assertions.checkState(this.controller != null);
        this.controller.setRepeatToggleModes(i);
    }

    public void setShowShuffleButton(boolean z) {
        Assertions.checkState(this.controller != null);
        this.controller.setShowShuffleButton(z);
    }

    public void setShowMultiWindowTimeBar(boolean z) {
        Assertions.checkState(this.controller != null);
        this.controller.setShowMultiWindowTimeBar(z);
    }

    public void setExtraAdGroupMarkers(@Nullable long[] jArr, @Nullable boolean[] zArr) {
        Assertions.checkState(this.controller != null);
        this.controller.setExtraAdGroupMarkers(jArr, zArr);
    }

    public void setAspectRatioListener(AspectRatioListener aspectRatioListener) {
        Assertions.checkState(this.contentFrame != null);
        this.contentFrame.setAspectRatioListener(aspectRatioListener);
    }

    public View getVideoSurfaceView() {
        return this.surfaceView;
    }

    public FrameLayout getOverlayFrameLayout() {
        return this.overlayFrameLayout;
    }

    public SubtitleView getSubtitleView() {
        return this.subtitleView;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() != null) {
            return null;
        }
        return toggleControllerVisibility();
    }

    public boolean onTrackballEvent(MotionEvent motionEvent) {
        if (this.useController != null) {
            if (this.player != null) {
                maybeShowController(true);
                return true;
            }
        }
        return null;
    }

    public void onResume() {
        if (this.surfaceView instanceof SphericalSurfaceView) {
            ((SphericalSurfaceView) this.surfaceView).onResume();
        }
    }

    public void onPause() {
        if (this.surfaceView instanceof SphericalSurfaceView) {
            ((SphericalSurfaceView) this.surfaceView).onPause();
        }
    }

    private boolean toggleControllerVisibility() {
        if (this.useController) {
            if (this.player != null) {
                if (!this.controller.isVisible()) {
                    maybeShowController(true);
                } else if (this.controllerHideOnTouch) {
                    this.controller.hide();
                }
                return true;
            }
        }
        return false;
    }

    private void maybeShowController(boolean z) {
        if (!(isPlayingAd() && this.controllerHideDuringAds) && this.useController) {
            Object obj = (!this.controller.isVisible() || this.controller.getShowTimeoutMs() > 0) ? null : 1;
            boolean shouldShowControllerIndefinitely = shouldShowControllerIndefinitely();
            if (z || obj != null || shouldShowControllerIndefinitely) {
                showController(shouldShowControllerIndefinitely);
            }
        }
    }

    private boolean shouldShowControllerIndefinitely() {
        boolean z = true;
        if (this.player == null) {
            return true;
        }
        int playbackState = this.player.getPlaybackState();
        if (this.controllerAutoShow) {
            if (!(playbackState == 1 || playbackState == 4)) {
                if (!this.player.getPlayWhenReady()) {
                }
            }
            return z;
        }
        z = false;
        return z;
    }

    private void showController(boolean z) {
        if (this.useController) {
            this.controller.setShowTimeoutMs(z ? false : this.controllerShowTimeoutMs);
            this.controller.show();
        }
    }

    private boolean isPlayingAd() {
        return this.player != null && this.player.isPlayingAd() && this.player.getPlayWhenReady();
    }

    private void updateForCurrentTrackSelections(boolean z) {
        if (this.player != null) {
            if (!this.player.getCurrentTrackGroups().isEmpty()) {
                if (z && !this.keepContentOnPlayerReset) {
                    closeShutter();
                }
                z = this.player.getCurrentTrackSelections();
                int i = 0;
                while (i < z.length) {
                    if (this.player.getRendererType(i) != 2 || z.get(i) == null) {
                        i++;
                    } else {
                        hideArtwork();
                        return;
                    }
                }
                closeShutter();
                if (this.useArtwork) {
                    for (i = 0; i < z.length; i++) {
                        TrackSelection trackSelection = z.get(i);
                        if (trackSelection != null) {
                            int i2 = 0;
                            while (i2 < trackSelection.length()) {
                                Metadata metadata = trackSelection.getFormat(i2).metadata;
                                if (metadata == null || !setArtworkFromMetadata(metadata)) {
                                    i2++;
                                } else {
                                    return;
                                }
                            }
                            continue;
                        }
                    }
                    if (setDrawableArtwork(this.defaultArtwork)) {
                        return;
                    }
                }
                hideArtwork();
                return;
            }
        }
        if (!this.keepContentOnPlayerReset) {
            hideArtwork();
            closeShutter();
        }
    }

    private boolean setArtworkFromMetadata(Metadata metadata) {
        for (int i = 0; i < metadata.length(); i++) {
            Entry entry = metadata.get(i);
            if (entry instanceof ApicFrame) {
                metadata = ((ApicFrame) entry).pictureData;
                return setDrawableArtwork(new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(metadata, 0, metadata.length)));
            }
        }
        return false;
    }

    private boolean setDrawableArtwork(@Nullable Drawable drawable) {
        if (drawable != null) {
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            if (intrinsicWidth > 0 && intrinsicHeight > 0) {
                if (this.contentFrame != null) {
                    this.contentFrame.setAspectRatio(((float) intrinsicWidth) / ((float) intrinsicHeight));
                }
                this.artworkView.setImageDrawable(drawable);
                this.artworkView.setVisibility(0);
                return true;
            }
        }
        return false;
    }

    private void hideArtwork() {
        if (this.artworkView != null) {
            this.artworkView.setImageResource(17170445);
            this.artworkView.setVisibility(4);
        }
    }

    private void closeShutter() {
        if (this.shutterView != null) {
            this.shutterView.setVisibility(0);
        }
    }

    private void updateBuffering() {
        if (this.bufferingView != null) {
            View view;
            Object obj = 1;
            int i = 0;
            if (this.player != null && this.player.getPlaybackState() == 2) {
                if (this.showBuffering != 2) {
                    if (this.showBuffering == 1 && this.player.getPlayWhenReady()) {
                    }
                }
                view = this.bufferingView;
                if (obj != null) {
                    i = 8;
                }
                view.setVisibility(i);
            }
            obj = null;
            view = this.bufferingView;
            if (obj != null) {
                i = 8;
            }
            view.setVisibility(i);
        }
    }

    private void updateErrorMessage() {
        if (this.errorMessageView != null) {
            if (this.customErrorMessage != null) {
                this.errorMessageView.setText(this.customErrorMessage);
                this.errorMessageView.setVisibility(0);
                return;
            }
            Throwable th = null;
            if (!(this.player == null || this.player.getPlaybackState() != 1 || this.errorMessageProvider == null)) {
                th = this.player.getPlaybackError();
            }
            if (th != null) {
                this.errorMessageView.setText((CharSequence) this.errorMessageProvider.getErrorMessage(th).second);
                this.errorMessageView.setVisibility(0);
            } else {
                this.errorMessageView.setVisibility(8);
            }
        }
    }

    @TargetApi(23)
    private static void configureEditModeLogoV23(Resources resources, ImageView imageView) {
        imageView.setImageDrawable(resources.getDrawable(R.drawable.exo_edit_mode_logo, null));
        imageView.setBackgroundColor(resources.getColor(R.color.exo_edit_mode_background_color, null));
    }

    private static void configureEditModeLogo(Resources resources, ImageView imageView) {
        imageView.setImageDrawable(resources.getDrawable(R.drawable.exo_edit_mode_logo));
        imageView.setBackgroundColor(resources.getColor(R.color.exo_edit_mode_background_color));
    }

    private static void setResizeModeRaw(AspectRatioFrameLayout aspectRatioFrameLayout, int i) {
        aspectRatioFrameLayout.setResizeMode(i);
    }

    private static void applyTextureViewRotation(TextureView textureView, int i) {
        float width = (float) textureView.getWidth();
        float height = (float) textureView.getHeight();
        if (!(width == 0.0f || height == 0.0f)) {
            if (i != 0) {
                Matrix matrix = new Matrix();
                float f = width / ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT;
                float f2 = height / ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT;
                matrix.postRotate((float) i, f, f2);
                i = new RectF(0.0f, 0.0f, width, height);
                RectF rectF = new RectF();
                matrix.mapRect(rectF, i);
                matrix.postScale(width / rectF.width(), height / rectF.height(), f, f2);
                textureView.setTransform(matrix);
                return;
            }
        }
        textureView.setTransform(0);
    }
}
