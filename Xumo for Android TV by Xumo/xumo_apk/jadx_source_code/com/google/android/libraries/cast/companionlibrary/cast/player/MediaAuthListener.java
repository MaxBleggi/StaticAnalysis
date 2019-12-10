package com.google.android.libraries.cast.companionlibrary.cast.player;

import com.google.android.gms.cast.MediaInfo;
import org.json.JSONObject;

public interface MediaAuthListener {
    void onAuthFailure(String str);

    void onAuthResult(MediaAuthStatus mediaAuthStatus, MediaInfo mediaInfo, String str, int i, JSONObject jSONObject);
}
