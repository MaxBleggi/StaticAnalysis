package com.xumo.xumo.util;

import android.net.Uri;
import com.google.android.libraries.cast.companionlibrary.utils.CustomData.PLAYSUBTYPE;
import com.xumo.xumo.repository.UserPreferences;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class XumoCBSNBeaconApiHelper {
    private static final String CBSNBeaconUrl = "https://saa.cbsi.com/b/ss/cbsicbsnewssite/0";
    private static final String brandPlatformIdAndroidTv = "cbsnews-xumotv_ott_androidtv";
    private static final String brandPlatformIdFireTv = "cbsnews-xumotv_ott_firetv";

    private static String buildCBSNBeaconURL() {
        return Uri.parse(CBSNBeaconUrl).buildUpon().appendQueryParameter("AQB", "1").appendQueryParameter("vid", UserPreferences.getInstance().getDeviceId()).appendQueryParameter("pageName", "/cbsn").appendQueryParameter("pe", "link_o").appendQueryParameter("pev2", "trackVideoStartPartner").appendQueryParameter("c.", "").appendQueryParameter("brandPlatformId", brandPlatformIdFireTv).appendQueryParameter("screenName", "/cbsn").appendQueryParameter("siteCode", "cbsnews").appendQueryParameter("siteSection", "cbs-news-digital-channel").appendQueryParameter("mediaPartnerId", "xumo_tv").appendQueryParameter("mediaDistNetwork", "can").appendQueryParameter("mediaDeviceId", UserPreferences.getInstance().getDeviceId()).appendQueryParameter("pageType", "live_streaming_player").appendQueryParameter("a.", "").appendQueryParameter("appID", "1.1.34").appendQueryParameter(JsonKey.CONTENT_TYPE, PLAYSUBTYPE.LIVE).appendQueryParameter("media.", "").appendQueryParameter(JsonKey.NAME, "9mwStzqtXKyib_egzSUPPh4DldNaEjJ2").appendQueryParameter("friendlyName", "CBSN+Live").appendQueryParameter("play", "true").appendQueryParameter(".media", "").appendQueryParameter(".a", "").appendQueryParameter(".c", "").appendQueryParameter("AQE", "1").build().toString();
    }

    public static void sendCBSNBeacon() {
        final String buildCBSNBeaconURL = buildCBSNBeaconURL();
        if (buildCBSNBeaconURL != null) {
            LogUtil.d("Beacon", buildCBSNBeaconURL);
            new Thread(new Runnable() {
                public void run() {
                    try {
                        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL(buildCBSNBeaconURL).openConnection();
                        httpsURLConnection.setRequestMethod(HttpRequest.METHOD_GET);
                        httpsURLConnection.setRequestProperty(HttpRequest.HEADER_CACHE_CONTROL, "no-cache");
                        httpsURLConnection.connect();
                        int responseCode = httpsURLConnection.getResponseCode();
                        httpsURLConnection.getContent();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Omniture Beacon responseCode:");
                        stringBuilder.append(responseCode);
                        LogUtil.d("Beacon", stringBuilder.toString());
                        httpsURLConnection.disconnect();
                    } catch (Exception e) {
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("");
                        stringBuilder2.append(e);
                        LogUtil.d(stringBuilder2.toString());
                    }
                }
            }).start();
            return;
        }
        LogUtil.d("Cannot build Omniture Beacon URL!!!");
    }
}
