package com.google.android.gms.cast.framework.media.widget;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import com.google.android.gms.cast.AdBreakClipInfo;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.MediaStatus;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.R;
import com.google.android.gms.cast.framework.Session;
import com.google.android.gms.cast.framework.SessionManager;
import com.google.android.gms.cast.framework.SessionManagerListener;
import com.google.android.gms.cast.framework.media.ImageHints;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.cast.framework.media.RemoteMediaClient.Listener;
import com.google.android.gms.cast.framework.media.uicontroller.UIMediaController;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.internal.cast.zzan;
import com.google.android.gms.internal.cast.zzau;
import com.google.android.gms.internal.cast.zzx;
import java.util.Timer;
import java.util.TimerTask;

public class ExpandedControllerActivity extends AppCompatActivity implements ControlButtonsContainer {
    private SessionManager zzhj;
    private final SessionManagerListener<CastSession> zznd = new zzb();
    private final Listener zzrf = new zza();
    private SeekBar zzrq;
    @DrawableRes
    private int zzsm;
    @ColorRes
    private int zzsn;
    @DrawableRes
    private int zzso;
    @DrawableRes
    private int zzsp;
    @DrawableRes
    private int zzsq;
    @DrawableRes
    private int zzsr;
    @DrawableRes
    private int zzss;
    @DrawableRes
    private int zzst;
    @DrawableRes
    private int zzsu;
    @DrawableRes
    private int zzsv;
    @DrawableRes
    private int zzsw;
    @DrawableRes
    private int zzsx;
    @DrawableRes
    private int zzsy;
    private int zzsz;
    private TextView zzta;
    private ImageView zztb;
    private ImageView zztc;
    private zzan zztd;
    private int[] zzte;
    private ImageView[] zztf = new ImageView[4];
    private View zztg;
    private ImageView zzth;
    private TextView zzti;
    private TextView zztj;
    private TextView zztk;
    private TextView zztl;
    private zzx zztm;
    private UIMediaController zztn;
    private boolean zzto;
    private boolean zztp;
    private Timer zztq;

    private class zza implements Listener {
        private final /* synthetic */ ExpandedControllerActivity zztr;

        private zza(ExpandedControllerActivity expandedControllerActivity) {
            this.zztr = expandedControllerActivity;
        }

        public final void onPreloadStatusUpdated() {
        }

        public final void onQueueStatusUpdated() {
        }

        public final void onStatusUpdated() {
            RemoteMediaClient zzd = this.zztr.getRemoteMediaClient();
            if (zzd != null) {
                if (zzd.hasMediaSession()) {
                    this.zztr.zzto = false;
                    this.zztr.zzcv();
                    this.zztr.zzcw();
                    return;
                }
            }
            if (!this.zztr.zzto) {
                this.zztr.finish();
            }
        }

        public final void onMetadataUpdated() {
            this.zztr.zzcu();
        }

        public final void onSendingRemoteMediaRequest() {
            this.zztr.zzta.setText(this.zztr.getResources().getString(R.string.cast_expanded_controller_loading));
        }

        public final void onAdBreakStatusUpdated() {
            this.zztr.zzcw();
        }
    }

    private class zzb implements SessionManagerListener<CastSession> {
        private final /* synthetic */ ExpandedControllerActivity zztr;

        private zzb(ExpandedControllerActivity expandedControllerActivity) {
            this.zztr = expandedControllerActivity;
        }

        public final /* bridge */ /* synthetic */ void onSessionEnding(Session session) {
        }

        public final /* bridge */ /* synthetic */ void onSessionResumeFailed(Session session, int i) {
        }

        public final /* bridge */ /* synthetic */ void onSessionResumed(Session session, boolean z) {
        }

        public final /* bridge */ /* synthetic */ void onSessionResuming(Session session, String str) {
        }

        public final /* bridge */ /* synthetic */ void onSessionStartFailed(Session session, int i) {
        }

        public final /* bridge */ /* synthetic */ void onSessionStarted(Session session, String str) {
        }

        public final /* bridge */ /* synthetic */ void onSessionStarting(Session session) {
        }

        public final /* bridge */ /* synthetic */ void onSessionSuspended(Session session, int i) {
        }

        public final /* synthetic */ void onSessionEnded(Session session, int i) {
            this.zztr.finish();
        }
    }

    public final int getButtonSlotCount() {
        return 4;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.zzhj = CastContext.getSharedInstance(this).getSessionManager();
        if (this.zzhj.getCurrentCastSession() == null) {
            finish();
        }
        this.zztn = new UIMediaController(this);
        this.zztn.setPostRemoteMediaClientListener(this.zzrf);
        setContentView((int) R.layout.cast_expanded_controller_activity);
        TypedArray obtainStyledAttributes = obtainStyledAttributes(new int[]{androidx.appcompat.R.attr.selectableItemBackgroundBorderless, androidx.appcompat.R.attr.colorControlActivated});
        this.zzsm = obtainStyledAttributes.getResourceId(0, 0);
        this.zzsn = obtainStyledAttributes.getResourceId(1, 0);
        obtainStyledAttributes.recycle();
        ColorStateList colorStateList = null;
        obtainStyledAttributes = obtainStyledAttributes(null, R.styleable.CastExpandedController, R.attr.castExpandedControllerStyle, R.style.CastExpandedController);
        this.zzsz = obtainStyledAttributes.getResourceId(R.styleable.CastExpandedController_castButtonColor, 0);
        this.zzso = obtainStyledAttributes.getResourceId(R.styleable.CastExpandedController_castSeekBarProgressDrawable, 0);
        this.zzsp = obtainStyledAttributes.getResourceId(R.styleable.CastExpandedController_castSeekBarThumbDrawable, 0);
        this.zzsq = obtainStyledAttributes.getResourceId(R.styleable.CastExpandedController_castPlayButtonDrawable, 0);
        this.zzsr = obtainStyledAttributes.getResourceId(R.styleable.CastExpandedController_castPauseButtonDrawable, 0);
        this.zzss = obtainStyledAttributes.getResourceId(R.styleable.CastExpandedController_castStopButtonDrawable, 0);
        this.zzst = obtainStyledAttributes.getResourceId(R.styleable.CastExpandedController_castSkipPreviousButtonDrawable, 0);
        this.zzsu = obtainStyledAttributes.getResourceId(R.styleable.CastExpandedController_castSkipNextButtonDrawable, 0);
        this.zzsv = obtainStyledAttributes.getResourceId(R.styleable.CastExpandedController_castRewind30ButtonDrawable, 0);
        this.zzsw = obtainStyledAttributes.getResourceId(R.styleable.CastExpandedController_castForward30ButtonDrawable, 0);
        this.zzsx = obtainStyledAttributes.getResourceId(R.styleable.CastExpandedController_castMuteToggleButtonDrawable, 0);
        this.zzsy = obtainStyledAttributes.getResourceId(R.styleable.CastExpandedController_castClosedCaptionsButtonDrawable, 0);
        int resourceId = obtainStyledAttributes.getResourceId(R.styleable.CastExpandedController_castControlButtons, 0);
        if (resourceId != 0) {
            TypedArray obtainTypedArray = getResources().obtainTypedArray(resourceId);
            Preconditions.checkArgument(obtainTypedArray.length() == 4);
            this.zzte = new int[obtainTypedArray.length()];
            for (int i = 0; i < obtainTypedArray.length(); i++) {
                this.zzte[i] = obtainTypedArray.getResourceId(i, 0);
            }
            obtainTypedArray.recycle();
        } else {
            this.zzte = new int[]{R.id.cast_button_type_empty, R.id.cast_button_type_empty, R.id.cast_button_type_empty, R.id.cast_button_type_empty};
        }
        obtainStyledAttributes.recycle();
        View findViewById = findViewById(R.id.expanded_controller_layout);
        UIMediaController uIMediaController = this.zztn;
        this.zztb = (ImageView) findViewById.findViewById(R.id.background_image_view);
        this.zztc = (ImageView) findViewById.findViewById(R.id.blurred_background_image_view);
        View findViewById2 = findViewById.findViewById(R.id.background_place_holder_image_view);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        uIMediaController.bindImageViewToImageOfCurrentItem(this.zztb, new ImageHints(4, displayMetrics.widthPixels, displayMetrics.heightPixels), findViewById2);
        this.zzta = (TextView) findViewById.findViewById(R.id.status_text);
        uIMediaController.bindViewToLoadingIndicator((ProgressBar) findViewById.findViewById(R.id.loading_indicator));
        TextView textView = (TextView) findViewById.findViewById(R.id.start_text);
        TextView textView2 = (TextView) findViewById.findViewById(R.id.end_text);
        ImageView imageView = (ImageView) findViewById.findViewById(R.id.live_stream_indicator);
        this.zzrq = (SeekBar) findViewById.findViewById(R.id.seek_bar);
        Drawable drawable = getResources().getDrawable(this.zzso);
        if (drawable != null) {
            if (this.zzso == R.drawable.cast_expanded_controller_seekbar_track) {
                colorStateList = zzct();
                LayerDrawable layerDrawable = (LayerDrawable) drawable;
                Drawable wrap = DrawableCompat.wrap(layerDrawable.findDrawableByLayerId(16908301));
                DrawableCompat.setTintList(wrap, colorStateList);
                layerDrawable.setDrawableByLayerId(16908301, wrap);
                layerDrawable.findDrawableByLayerId(16908288).setColorFilter(getResources().getColor(R.color.cast_expanded_controller_seek_bar_progress_background_tint_color), Mode.SRC_IN);
            }
            this.zzrq.setProgressDrawable(drawable);
        }
        drawable = getResources().getDrawable(this.zzsp);
        if (drawable != null) {
            if (this.zzsp == R.drawable.cast_expanded_controller_seekbar_thumb) {
                if (colorStateList == null) {
                    colorStateList = zzct();
                }
                drawable = DrawableCompat.wrap(drawable);
                DrawableCompat.setTintList(drawable, colorStateList);
            }
            this.zzrq.setThumb(drawable);
        }
        if (PlatformVersion.isAtLeastLollipop()) {
            this.zzrq.setSplitTrack(false);
        }
        SeekBar seekBar = (SeekBar) findViewById.findViewById(R.id.live_stream_seek_bar);
        uIMediaController.bindTextViewToStreamPosition(textView, true);
        uIMediaController.bindTextViewToStreamDuration(textView2, imageView);
        uIMediaController.bindSeekBar(this.zzrq);
        uIMediaController.bindViewToUIController(seekBar, new zzau(seekBar, this.zzrq));
        this.zztf[0] = (ImageView) findViewById.findViewById(R.id.button_0);
        this.zztf[1] = (ImageView) findViewById.findViewById(R.id.button_1);
        this.zztf[2] = (ImageView) findViewById.findViewById(R.id.button_2);
        this.zztf[3] = (ImageView) findViewById.findViewById(R.id.button_3);
        zza(findViewById, R.id.button_0, this.zzte[0], uIMediaController);
        zza(findViewById, R.id.button_1, this.zzte[1], uIMediaController);
        zza(findViewById, R.id.button_play_pause_toggle, R.id.cast_button_type_play_pause_toggle, uIMediaController);
        zza(findViewById, R.id.button_2, this.zzte[2], uIMediaController);
        zza(findViewById, R.id.button_3, this.zzte[3], uIMediaController);
        this.zztg = findViewById(R.id.ad_container);
        this.zzth = (ImageView) this.zztg.findViewById(R.id.ad_image_view);
        this.zztj = (TextView) this.zztg.findViewById(R.id.ad_label);
        this.zzti = (TextView) this.zztg.findViewById(R.id.ad_in_progress_label);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById.findViewById(R.id.seek_bar_controls);
        findViewById = new zzan(this);
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams.addRule(0, R.id.end_text);
        layoutParams.addRule(1, R.id.start_text);
        layoutParams.addRule(6, R.id.seek_bar);
        layoutParams.addRule(7, R.id.seek_bar);
        layoutParams.addRule(5, R.id.seek_bar);
        layoutParams.addRule(8, R.id.seek_bar);
        findViewById.setLayoutParams(layoutParams);
        if (PlatformVersion.isAtLeastJellyBeanMR1()) {
            findViewById.setPaddingRelative(this.zzrq.getPaddingStart(), this.zzrq.getPaddingTop(), this.zzrq.getPaddingEnd(), this.zzrq.getPaddingBottom());
        } else {
            findViewById.setPadding(this.zzrq.getPaddingLeft(), this.zzrq.getPaddingTop(), this.zzrq.getPaddingRight(), this.zzrq.getPaddingBottom());
        }
        findViewById.setContentDescription(getResources().getString(R.string.cast_seek_bar));
        findViewById.setBackgroundColor(0);
        relativeLayout.addView(findViewById);
        this.zztd = findViewById;
        this.zztl = (TextView) findViewById(R.id.ad_skip_text);
        this.zztk = (TextView) findViewById(R.id.ad_skip_button);
        this.zztk.setOnClickListener(new zzb(this));
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.quantum_ic_keyboard_arrow_down_white_36);
        }
        zzcv();
        zzcu();
        this.zztm = new zzx(getApplicationContext(), new ImageHints(-1, this.zzth.getWidth(), this.zzth.getHeight()));
        this.zztm.zza(new zza(this));
    }

    protected void onResume() {
        boolean z;
        CastContext.getSharedInstance(this).getSessionManager().addSessionManagerListener(this.zznd, CastSession.class);
        Session currentCastSession = CastContext.getSharedInstance(this).getSessionManager().getCurrentCastSession();
        if (currentCastSession == null || !(currentCastSession.isConnected() || currentCastSession.isConnecting())) {
            finish();
        }
        RemoteMediaClient remoteMediaClient = getRemoteMediaClient();
        if (remoteMediaClient != null) {
            if (remoteMediaClient.hasMediaSession()) {
                z = false;
                this.zzto = z;
                zzcv();
                zzcw();
                super.onResume();
            }
        }
        z = true;
        this.zzto = z;
        zzcv();
        zzcw();
        super.onResume();
    }

    protected void onPause() {
        CastContext.getSharedInstance(this).getSessionManager().removeSessionManagerListener(this.zznd, CastSession.class);
        super.onPause();
    }

    protected void onDestroy() {
        this.zztm.clear();
        if (this.zztn != null) {
            this.zztn.setPostRemoteMediaClientListener(null);
            this.zztn.dispose();
        }
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            finish();
        }
        return true;
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            z = getWindow().getDecorView().getSystemUiVisibility() ^ 2;
            if (PlatformVersion.isAtLeastJellyBean()) {
                z ^= 4;
            }
            if (PlatformVersion.isAtLeastKitKat()) {
                z ^= 4096;
            }
            getWindow().getDecorView().setSystemUiVisibility(z);
            if (PlatformVersion.isAtLeastJellyBeanMR2()) {
                setImmersive(true);
            }
        }
    }

    public TextView getStatusTextView() {
        return this.zzta;
    }

    public SeekBar getSeekBar() {
        return this.zzrq;
    }

    public final int getButtonTypeAt(int i) throws IndexOutOfBoundsException {
        return this.zzte[i];
    }

    public final ImageView getButtonImageViewAt(int i) throws IndexOutOfBoundsException {
        return this.zztf[i];
    }

    public UIMediaController getUIMediaController() {
        return this.zztn;
    }

    private final ColorStateList zzct() {
        int color = getResources().getColor(this.zzsn);
        TypedValue typedValue = new TypedValue();
        getResources().getValue(R.dimen.cast_expanded_controller_seekbar_disabled_alpha, typedValue, true);
        int argb = Color.argb((int) (typedValue.getFloat() * ((float) Color.alpha(color))), Color.red(color), Color.green(color), Color.blue(color));
        int[] iArr = new int[]{color, argb};
        r0 = new int[2][];
        r0[0] = new int[]{16842910};
        r0[1] = new int[]{-16842910};
        return new ColorStateList(r0, iArr);
    }

    private final void zza(View view, int i, int i2, UIMediaController uIMediaController) {
        ImageView imageView = (ImageView) view.findViewById(i);
        if (i2 == R.id.cast_button_type_empty) {
            imageView.setVisibility(4);
            return;
        }
        if (i2 != R.id.cast_button_type_custom) {
            if (i2 == R.id.cast_button_type_play_pause_toggle) {
                imageView.setBackgroundResource(this.zzsm);
                Drawable zzb = zze.zzb(this, this.zzsz, this.zzsr);
                Drawable zzb2 = zze.zzb(this, this.zzsz, this.zzsq);
                Drawable zzb3 = zze.zzb(this, this.zzsz, this.zzss);
                imageView.setImageDrawable(zzb2);
                uIMediaController.bindImageViewToPlayPauseToggle(imageView, zzb2, zzb, zzb3, null, false);
            } else if (i2 == R.id.cast_button_type_skip_previous) {
                imageView.setBackgroundResource(this.zzsm);
                imageView.setImageDrawable(zze.zzb(this, this.zzsz, this.zzst));
                imageView.setContentDescription(getResources().getString(R.string.cast_skip_prev));
                uIMediaController.bindViewToSkipPrev(imageView, 0);
            } else if (i2 == R.id.cast_button_type_skip_next) {
                imageView.setBackgroundResource(this.zzsm);
                imageView.setImageDrawable(zze.zzb(this, this.zzsz, this.zzsu));
                imageView.setContentDescription(getResources().getString(R.string.cast_skip_next));
                uIMediaController.bindViewToSkipNext(imageView, 0);
            } else if (i2 == R.id.cast_button_type_rewind_30_seconds) {
                imageView.setBackgroundResource(this.zzsm);
                imageView.setImageDrawable(zze.zzb(this, this.zzsz, this.zzsv));
                imageView.setContentDescription(getResources().getString(R.string.cast_rewind_30));
                uIMediaController.bindViewToRewind(imageView, 30000);
            } else if (i2 == R.id.cast_button_type_forward_30_seconds) {
                imageView.setBackgroundResource(this.zzsm);
                imageView.setImageDrawable(zze.zzb(this, this.zzsz, this.zzsw));
                imageView.setContentDescription(getResources().getString(R.string.cast_forward_30));
                uIMediaController.bindViewToForward(imageView, 30000);
            } else if (i2 == R.id.cast_button_type_mute_toggle) {
                imageView.setBackgroundResource(this.zzsm);
                imageView.setImageDrawable(zze.zzb(this, this.zzsz, this.zzsx));
                uIMediaController.bindImageViewToMuteToggle(imageView);
            } else if (i2 == R.id.cast_button_type_closed_caption) {
                imageView.setBackgroundResource(this.zzsm);
                imageView.setImageDrawable(zze.zzb(this, this.zzsz, this.zzsy));
                uIMediaController.bindViewToClosedCaption(imageView);
            }
        }
    }

    private final RemoteMediaClient getRemoteMediaClient() {
        Session currentCastSession = this.zzhj.getCurrentCastSession();
        return (currentCastSession == null || !currentCastSession.isConnected()) ? null : currentCastSession.getRemoteMediaClient();
    }

    private final void zzcu() {
        RemoteMediaClient remoteMediaClient = getRemoteMediaClient();
        if (remoteMediaClient != null && remoteMediaClient.hasMediaSession()) {
            MediaInfo mediaInfo = remoteMediaClient.getMediaInfo();
            if (mediaInfo != null) {
                MediaMetadata metadata = mediaInfo.getMetadata();
                if (metadata != null) {
                    ActionBar supportActionBar = getSupportActionBar();
                    if (supportActionBar != null) {
                        supportActionBar.setTitle(metadata.getString(MediaMetadata.KEY_TITLE));
                    }
                }
            }
        }
    }

    private final void zzcv() {
        CastSession currentCastSession = this.zzhj.getCurrentCastSession();
        if (currentCastSession != null) {
            CastDevice castDevice = currentCastSession.getCastDevice();
            if (castDevice != null) {
                if (!TextUtils.isEmpty(castDevice.getFriendlyName())) {
                    this.zzta.setText(getResources().getString(R.string.cast_casting_to_device, new Object[]{r0}));
                    return;
                }
            }
        }
        this.zzta.setText("");
    }

    private final void zzcw() {
        MediaInfo mediaInfo;
        MediaStatus mediaStatus;
        RemoteMediaClient remoteMediaClient = getRemoteMediaClient();
        Object obj = null;
        if (remoteMediaClient == null) {
            mediaInfo = null;
        } else {
            mediaInfo = remoteMediaClient.getMediaInfo();
        }
        if (remoteMediaClient == null) {
            mediaStatus = null;
        } else {
            mediaStatus = remoteMediaClient.getMediaStatus();
        }
        Object obj2 = (mediaStatus == null || !mediaStatus.isPlayingAd()) ? null : 1;
        if (obj2 != null) {
            CharSequence title;
            this.zzrq.setEnabled(false);
            if (PlatformVersion.isAtLeastJellyBeanMR1() && this.zztc.getVisibility() == 8) {
                Drawable drawable = this.zztb.getDrawable();
                if (drawable != null && (drawable instanceof BitmapDrawable)) {
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    if (bitmap != null) {
                        bitmap = zze.zza(this, bitmap, 0.25f, 7.5f);
                        if (bitmap != null) {
                            this.zztc.setImageBitmap(bitmap);
                            this.zztc.setVisibility(0);
                        }
                    }
                }
            }
            AdBreakClipInfo currentAdBreakClip = mediaStatus.getCurrentAdBreakClip();
            if (currentAdBreakClip != null) {
                title = currentAdBreakClip.getTitle();
                obj = currentAdBreakClip.getImageUrl();
            } else {
                title = null;
            }
            this.zzti.setVisibility(0);
            if (TextUtils.isEmpty(obj)) {
                this.zzth.setVisibility(8);
            } else {
                this.zztm.zza(Uri.parse(obj));
            }
            TextView textView = this.zztj;
            if (TextUtils.isEmpty(title)) {
                title = getResources().getString(R.string.cast_ad_label);
            }
            textView.setText(title);
            this.zzrq.setEnabled(false);
            this.zztg.setVisibility(0);
            zza(currentAdBreakClip, remoteMediaClient);
        } else {
            this.zzrq.setEnabled(true);
            this.zztl.setVisibility(8);
            this.zztk.setVisibility(8);
            this.zztg.setVisibility(8);
            if (PlatformVersion.isAtLeastJellyBeanMR1()) {
                this.zztc.setVisibility(8);
                this.zztc.setImageBitmap(null);
            }
        }
        if (mediaInfo != null) {
            this.zztd.zzj(this.zzrq.getMax());
            this.zztd.zzb(mediaInfo.getAdBreaks(), -1);
        }
    }

    private final void zza(AdBreakClipInfo adBreakClipInfo, RemoteMediaClient remoteMediaClient) {
        if (!this.zzto) {
            if (!remoteMediaClient.isBuffering()) {
                this.zztk.setVisibility(8);
                if (!(adBreakClipInfo == null || adBreakClipInfo.getWhenSkippableInMs() == -1)) {
                    if (!this.zztp) {
                        TimerTask com_google_android_gms_cast_framework_media_widget_zzc = new zzc(this, adBreakClipInfo, remoteMediaClient);
                        this.zztq = new Timer();
                        this.zztq.scheduleAtFixedRate(com_google_android_gms_cast_framework_media_widget_zzc, 0, 500);
                        this.zztp = true;
                    }
                    if (((float) (adBreakClipInfo.getWhenSkippableInMs() - remoteMediaClient.getApproximateAdBreakClipPositionMs())) <= null) {
                        this.zztl.setVisibility(8);
                        if (this.zztp != null) {
                            this.zztq.cancel();
                            this.zztp = false;
                        }
                        this.zztk.setVisibility(0);
                        this.zztk.setClickable(true);
                        return;
                    }
                    this.zztl.setVisibility(0);
                    this.zztl.setText(String.format(getResources().getString(R.string.cast_expanded_controller_skip_ad_text), new Object[]{Integer.valueOf(((int) adBreakClipInfo) / 1000)}));
                    this.zztk.setClickable(false);
                }
            }
        }
    }
}
