package com.google.android.gms.cast.framework;

import android.app.Activity;
import android.content.Context;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.StringRes;
import androidx.mediarouter.app.MediaRouteButton;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.internal.cast.zzn;
import com.google.android.gms.internal.cast.zzr;

public interface IntroductoryOverlay {

    public static class Builder {
        private final Activity zzim;
        private final View zzin;
        private int zzio;
        private String zzip;
        private OnOverlayDismissedListener zziq;
        private boolean zzir;
        private float zzis;
        private String zzit;

        public Builder(Activity activity, MenuItem menuItem) {
            this.zzim = (Activity) Preconditions.checkNotNull(activity);
            this.zzin = ((MenuItem) Preconditions.checkNotNull(menuItem)).getActionView();
        }

        public Builder(Activity activity, MediaRouteButton mediaRouteButton) {
            this.zzim = (Activity) Preconditions.checkNotNull(activity);
            this.zzin = (View) Preconditions.checkNotNull(mediaRouteButton);
        }

        public Builder setOverlayColor(@ColorRes int i) {
            this.zzio = this.zzim.getResources().getColor(i);
            return this;
        }

        public Builder setTitleText(String str) {
            this.zzip = str;
            return this;
        }

        public Builder setTitleText(@StringRes int i) {
            this.zzip = this.zzim.getResources().getString(i);
            return this;
        }

        public Builder setOnOverlayDismissedListener(OnOverlayDismissedListener onOverlayDismissedListener) {
            this.zziq = onOverlayDismissedListener;
            return this;
        }

        public Builder setSingleTime() {
            this.zzir = true;
            return this;
        }

        public Builder setFocusRadiusId(@DimenRes int i) {
            this.zzis = this.zzim.getResources().getDimension(i);
            return this;
        }

        public Builder setFocusRadius(float f) {
            this.zzis = f;
            return this;
        }

        public Builder setButtonText(String str) {
            this.zzit = str;
            return this;
        }

        public Builder setButtonText(@StringRes int i) {
            this.zzit = this.zzim.getResources().getString(i);
            return this;
        }

        public final Activity getActivity() {
            return this.zzim;
        }

        public final View zzac() {
            return this.zzin;
        }

        public final OnOverlayDismissedListener zzad() {
            return this.zziq;
        }

        public final int zzae() {
            return this.zzio;
        }

        public final boolean zzaf() {
            return this.zzir;
        }

        public final String zzag() {
            return this.zzip;
        }

        public final String zzah() {
            return this.zzit;
        }

        public final float zzai() {
            return this.zzis;
        }

        public IntroductoryOverlay build() {
            if (PlatformVersion.isAtLeastJellyBean()) {
                return new zzn(this);
            }
            return new zzr(this);
        }
    }

    public interface OnOverlayDismissedListener {
        void onOverlayDismissed();
    }

    public static class zza {
        public static void zzd(Context context) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean("googlecast-introOverlayShown", true).apply();
        }

        public static boolean zze(Context context) {
            return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("googlecast-introOverlayShown", false);
        }
    }

    void remove();

    void show();
}
