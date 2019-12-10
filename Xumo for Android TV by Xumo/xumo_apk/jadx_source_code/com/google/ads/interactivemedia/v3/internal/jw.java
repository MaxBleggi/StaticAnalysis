package com.google.ads.interactivemedia.v3.internal;

import android.annotation.TargetApi;
import android.app.UiModeManager;
import android.content.Context;
import android.os.Build.VERSION;
import android.webkit.WebSettings;
import com.google.ads.interactivemedia.v3.impl.data.TestingConfiguration;

/* compiled from: IMASDK */
public class jw {
    @TargetApi(17)
    public static void a(WebSettings webSettings) {
        if (VERSION.SDK_INT >= 17) {
            webSettings.setMediaPlaybackRequiresUserGesture(false);
        }
    }

    @TargetApi(13)
    public static boolean a(Context context, TestingConfiguration testingConfiguration) {
        boolean z = true;
        if (testingConfiguration != null && testingConfiguration.forceTvMode() != null) {
            return true;
        }
        if (VERSION.SDK_INT <= 12) {
            return false;
        }
        UiModeManager uiModeManager = (UiModeManager) context.getSystemService("uimode");
        if (uiModeManager == null || uiModeManager.getCurrentModeType() != 4) {
            z = false;
        }
        return z;
    }
}
