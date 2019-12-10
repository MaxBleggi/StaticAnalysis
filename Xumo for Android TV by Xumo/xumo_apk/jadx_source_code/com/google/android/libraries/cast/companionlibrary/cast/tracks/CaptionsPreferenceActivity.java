package com.google.android.libraries.cast.companionlibrary.cast.tracks;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.google.android.libraries.cast.companionlibrary.R;
import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;
import com.google.android.libraries.cast.companionlibrary.utils.LogUtils;
import com.google.android.libraries.cast.companionlibrary.utils.Utils;

public class CaptionsPreferenceActivity extends PreferenceActivity {
    private static final String TAG = LogUtils.makeLogTag(CaptionsPreferenceActivity.class);

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        bundle = VideoCastManager.getInstance();
        if (!bundle.isFeatureEnabled(16)) {
            LogUtils.LOGE(TAG, "Did you forget to enable FEATURE_CAPTIONS_PREFERENCE when you initialized the VideoCastManage?");
            finish();
        } else if (Utils.IS_KITKAT_OR_ABOVE) {
            startActivity(new Intent("android.settings.ACCESSIBILITY_SETTINGS"));
            finish();
        } else {
            addPreferencesFromResource(R.xml.caption_preference);
            bundle.getTracksPreferenceManager().setUpPreferences(getPreferenceScreen());
        }
    }
}
