package com.google.android.gms.cast.framework.media.widget;

import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.fragment.app.Fragment;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.framework.R;
import com.google.android.gms.cast.framework.media.ImageHints;
import com.google.android.gms.cast.framework.media.uicontroller.UIMediaController;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.cast.zzdh;

public class MiniControllerFragment extends Fragment implements ControlButtonsContainer {
    private static final zzdh zzbe = new zzdh("MiniControllerFragment");
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
    private int[] zzte;
    private ImageView[] zztf = new ImageView[3];
    private UIMediaController zztn;
    private boolean zztv;
    private int zztw;
    private int zztx;
    private TextView zzty;
    private int zztz;
    private int zzua;
    private int zzub;
    @DrawableRes
    private int zzuc;
    @DrawableRes
    private int zzud;
    @DrawableRes
    private int zzue;

    public final int getButtonSlotCount() {
        return 3;
    }

    public void onInflate(Context context, AttributeSet attributeSet, Bundle bundle) {
        super.onInflate(context, attributeSet, bundle);
        if (this.zzte == null) {
            attributeSet = context.obtainStyledAttributes(attributeSet, R.styleable.CastMiniController, R.attr.castMiniControllerStyle, R.style.CastMiniController);
            this.zztv = attributeSet.getBoolean(R.styleable.CastMiniController_castShowImageThumbnail, true);
            int i = 0;
            this.zztw = attributeSet.getResourceId(R.styleable.CastMiniController_castTitleTextAppearance, 0);
            this.zztx = attributeSet.getResourceId(R.styleable.CastMiniController_castSubtitleTextAppearance, 0);
            this.zztz = attributeSet.getResourceId(R.styleable.CastMiniController_castBackground, 0);
            this.zzua = attributeSet.getColor(R.styleable.CastMiniController_castProgressBarColor, 0);
            this.zzsz = attributeSet.getResourceId(R.styleable.CastMiniController_castButtonColor, 0);
            this.zzsq = attributeSet.getResourceId(R.styleable.CastMiniController_castPlayButtonDrawable, 0);
            this.zzsr = attributeSet.getResourceId(R.styleable.CastMiniController_castPauseButtonDrawable, 0);
            this.zzss = attributeSet.getResourceId(R.styleable.CastMiniController_castStopButtonDrawable, 0);
            this.zzuc = attributeSet.getResourceId(R.styleable.CastMiniController_castPlayButtonDrawable, 0);
            this.zzud = attributeSet.getResourceId(R.styleable.CastMiniController_castPauseButtonDrawable, 0);
            this.zzue = attributeSet.getResourceId(R.styleable.CastMiniController_castStopButtonDrawable, 0);
            this.zzst = attributeSet.getResourceId(R.styleable.CastMiniController_castSkipPreviousButtonDrawable, 0);
            this.zzsu = attributeSet.getResourceId(R.styleable.CastMiniController_castSkipNextButtonDrawable, 0);
            this.zzsv = attributeSet.getResourceId(R.styleable.CastMiniController_castRewind30ButtonDrawable, 0);
            this.zzsw = attributeSet.getResourceId(R.styleable.CastMiniController_castForward30ButtonDrawable, 0);
            this.zzsx = attributeSet.getResourceId(R.styleable.CastMiniController_castMuteToggleButtonDrawable, 0);
            this.zzsy = attributeSet.getResourceId(R.styleable.CastMiniController_castClosedCaptionsButtonDrawable, 0);
            bundle = attributeSet.getResourceId(R.styleable.CastMiniController_castControlButtons, 0);
            if (bundle != null) {
                context = context.getResources().obtainTypedArray(bundle);
                Preconditions.checkArgument(context.length() == 3 ? true : null);
                this.zzte = new int[context.length()];
                for (bundle = null; bundle < context.length(); bundle++) {
                    this.zzte[bundle] = context.getResourceId(bundle, 0);
                }
                context.recycle();
                if (this.zztv != null) {
                    this.zzte[0] = R.id.cast_button_type_empty;
                }
                this.zzub = 0;
                context = this.zzte;
                bundle = context.length;
                while (i < bundle) {
                    if (context[i] != R.id.cast_button_type_empty) {
                        this.zzub++;
                    }
                    i++;
                }
            } else {
                zzbe.w("Unable to read attribute castControlButtons.", new Object[0]);
                this.zzte = new int[]{R.id.cast_button_type_empty, R.id.cast_button_type_empty, R.id.cast_button_type_empty};
            }
            attributeSet.recycle();
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.zztn = new UIMediaController(getActivity());
        layoutInflater = layoutInflater.inflate(R.layout.cast_mini_controller, viewGroup);
        layoutInflater.setVisibility(8);
        this.zztn.bindViewVisibilityToMediaSession(layoutInflater, 8);
        RelativeLayout relativeLayout = (RelativeLayout) layoutInflater.findViewById(R.id.container_current);
        if (this.zztz != 0) {
            relativeLayout.setBackgroundResource(this.zztz);
        }
        ImageView imageView = (ImageView) layoutInflater.findViewById(R.id.icon_view);
        TextView textView = (TextView) layoutInflater.findViewById(R.id.title_view);
        if (this.zztw != 0) {
            textView.setTextAppearance(getActivity(), this.zztw);
        }
        this.zzty = (TextView) layoutInflater.findViewById(R.id.subtitle_view);
        if (this.zztx != 0) {
            this.zzty.setTextAppearance(getActivity(), this.zztx);
        }
        ProgressBar progressBar = (ProgressBar) layoutInflater.findViewById(R.id.progressBar);
        if (this.zzua != 0) {
            ((LayerDrawable) progressBar.getProgressDrawable()).setColorFilter(this.zzua, Mode.SRC_IN);
        }
        this.zztn.bindTextViewToMetadataOfCurrentItem(textView, MediaMetadata.KEY_TITLE);
        this.zztn.bindTextViewToSmartSubtitle(this.zzty);
        this.zztn.bindProgressBar(progressBar);
        this.zztn.bindViewToLaunchExpandedController(relativeLayout);
        if (this.zztv) {
            this.zztn.bindImageViewToImageOfCurrentItem(imageView, new ImageHints(2, getResources().getDimensionPixelSize(R.dimen.cast_mini_controller_icon_width), getResources().getDimensionPixelSize(R.dimen.cast_mini_controller_icon_height)), R.drawable.cast_album_art_placeholder);
        } else {
            imageView.setVisibility(8);
        }
        this.zztf[0] = (ImageView) relativeLayout.findViewById(R.id.button_0);
        this.zztf[1] = (ImageView) relativeLayout.findViewById(R.id.button_1);
        this.zztf[2] = (ImageView) relativeLayout.findViewById(R.id.button_2);
        zza(relativeLayout, R.id.button_0, 0);
        zza(relativeLayout, R.id.button_1, 1);
        zza(relativeLayout, R.id.button_2, 2);
        return layoutInflater;
    }

    public void onDestroy() {
        if (this.zztn != null) {
            this.zztn.dispose();
            this.zztn = null;
        }
        super.onDestroy();
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

    private final void zza(RelativeLayout relativeLayout, int i, int i2) {
        ImageView imageView = (ImageView) relativeLayout.findViewById(i);
        i2 = this.zzte[i2];
        if (i2 == R.id.cast_button_type_empty) {
            imageView.setVisibility(4);
            return;
        }
        if (i2 != R.id.cast_button_type_custom) {
            if (i2 == R.id.cast_button_type_play_pause_toggle) {
                i2 = this.zzsq;
                int i3 = this.zzsr;
                int i4 = this.zzss;
                if (this.zzub == 1) {
                    i2 = this.zzuc;
                    i3 = this.zzud;
                    i4 = this.zzue;
                }
                Drawable zza = zze.zza(getContext(), this.zzsz, i2);
                Drawable zza2 = zze.zza(getContext(), this.zzsz, i3);
                Drawable zza3 = zze.zza(getContext(), this.zzsz, i4);
                imageView.setImageDrawable(zza2);
                View progressBar = new ProgressBar(getContext());
                i2 = new LayoutParams(-2, -2);
                i2.addRule(8, i);
                i2.addRule(6, i);
                i2.addRule(5, i);
                i2.addRule(7, i);
                i2.addRule(15);
                progressBar.setLayoutParams(i2);
                progressBar.setVisibility(8);
                i = progressBar.getIndeterminateDrawable();
                if (!(this.zzua == 0 || i == 0)) {
                    i.setColorFilter(this.zzua, Mode.SRC_IN);
                }
                relativeLayout.addView(progressBar);
                this.zztn.bindImageViewToPlayPauseToggle(imageView, zza, zza2, zza3, progressBar, true);
            } else if (i2 == R.id.cast_button_type_skip_previous) {
                imageView.setImageDrawable(zze.zza(getContext(), this.zzsz, this.zzst));
                imageView.setContentDescription(getResources().getString(R.string.cast_skip_prev));
                this.zztn.bindViewToSkipPrev(imageView, 0);
            } else if (i2 == R.id.cast_button_type_skip_next) {
                imageView.setImageDrawable(zze.zza(getContext(), this.zzsz, this.zzsu));
                imageView.setContentDescription(getResources().getString(R.string.cast_skip_next));
                this.zztn.bindViewToSkipNext(imageView, 0);
            } else if (i2 == R.id.cast_button_type_rewind_30_seconds) {
                imageView.setImageDrawable(zze.zza(getContext(), this.zzsz, this.zzsv));
                imageView.setContentDescription(getResources().getString(R.string.cast_rewind_30));
                this.zztn.bindViewToRewind(imageView, 30000);
            } else if (i2 == R.id.cast_button_type_forward_30_seconds) {
                imageView.setImageDrawable(zze.zza(getContext(), this.zzsz, this.zzsw));
                imageView.setContentDescription(getResources().getString(R.string.cast_forward_30));
                this.zztn.bindViewToForward(imageView, 30000);
            } else if (i2 == R.id.cast_button_type_mute_toggle) {
                imageView.setImageDrawable(zze.zza(getContext(), this.zzsz, this.zzsx));
                this.zztn.bindImageViewToMuteToggle(imageView);
            } else if (i2 == R.id.cast_button_type_closed_caption) {
                imageView.setImageDrawable(zze.zza(getContext(), this.zzsz, this.zzsy));
                this.zztn.bindViewToClosedCaption(imageView);
            }
        }
    }
}
