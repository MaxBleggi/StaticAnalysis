package com.xumo.xumo.util;

import android.net.Uri;
import com.xumo.xumo.model.VideoAsset;
import com.xumo.xumo.util.BeaconUtil.PlayType;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;

public class OmnitureBeaconUtil {
    private static final String kBaseUrl = "https://som.cbsi.com/b/ss/cbsicbsnewssite,cbsicbsiall/1/5.4/REDIR";
    private static final List<String> kChannelIds = Collections.singletonList("9999158");
    private static final String kPlatformName = "app,mobile";

    private static String buildBeaconURL(String str) {
        return Uri.parse(kBaseUrl).buildUpon().appendQueryParameter("AQB", "1").appendQueryParameter("events", "D=\"event52:\" X-Playback-Session-Id").appendQueryParameter("ndh", "0").appendQueryParameter("ce", "UTF-8").appendQueryParameter("g", "http://www.cbsnews.com/news/live/?c=24").appendQueryParameter("ch", "cbs-news-digital-channel").appendQueryParameter("j", "xumo_tv").appendQueryParameter("h1", "D=\"cbsnews:\" ch").appendQueryParameter("server", "www.cbsnews.com").appendQueryParameter("c2", "D=v2").appendQueryParameter("c3", "D=v3").appendQueryParameter("c5", "D=v5").appendQueryParameter("c6", "D=v6").appendQueryParameter("c7", "D=v7").appendQueryParameter("c8", "D=v8").appendQueryParameter("c9", "D=User-Agent").appendQueryParameter("c10", "D=v10").appendQueryParameter("c11", "D=v11").appendQueryParameter("c20", "D=v20").appendQueryParameter("c22", "D=v22").appendQueryParameter("c23", "D=v23").appendQueryParameter("c30", "D=v30").appendQueryParameter("v1", "cbsnews").appendQueryParameter("v2", "us").appendQueryParameter("v3", "xumo_tv|||app,mobile").appendQueryParameter("v5", "cbsicbsnewssite").appendQueryParameter("v6", "D=h1").appendQueryParameter("v7", "D=g").appendQueryParameter("v10", "news_item_xumo_tv").appendQueryParameter("v11", "D=ch \":\" v10").appendQueryParameter("v15", "").appendQueryParameter("v20", "CBSN Live Video").appendQueryParameter("v22", "cbsn_third_party|xumo_tv").appendQueryParameter("v23", "8a37c65b-cbd4-4bcb-b0d6-fb0eac279178").appendQueryParameter("v24", "").appendQueryParameter("v30", "9mwStzqtXKyib_egzSUPPh4DldNaEjJ2").appendQueryParameter("v31", "9mwStzqtXKyib_egzSUPPh4DldNaEjJ2").appendQueryParameter("c24", "D=v24").appendQueryParameter("c25", "D=v25").appendQueryParameter("c31", "D=v31").appendQueryParameter("c32", "D=v32").appendQueryParameter("c38", "D=v38").appendQueryParameter("c59", "D=v59").appendQueryParameter("v25", "CBSN Live Video").appendQueryParameter("v32", "xumo_tv").appendQueryParameter("v38", "live video").appendQueryParameter("v59", "non-svod").appendQueryParameter("pe", "m_s").appendQueryParameter("pev1", "D=url").appendQueryParameter("pev3", "D=v10").appendQueryParameter("url", "http://www.cbsnews.com/news/live/").appendQueryParameter("vid", str).appendQueryParameter("AQE", "1").build().toString();
    }

    public static void sendBeacon(String str) {
        str = buildBeaconURL(str);
        if (str != null) {
            LogUtil.d("Beacon", str);
            new Thread(new Runnable() {
                public void run() {
                    try {
                        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) new URL(str).openConnection();
                        httpsURLConnection.setRequestMethod(HttpRequest.METHOD_GET);
                        httpsURLConnection.setRequestProperty(HttpRequest.HEADER_CACHE_CONTROL, "no-cache");
                        httpsURLConnection.connect();
                        int responseCode = httpsURLConnection.getResponseCode();
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

    public static boolean isTarget(VideoAsset videoAsset) {
        if (videoAsset != null) {
            PlayType playType = videoAsset.getAssetType() == 1 ? PlayType.VOD : PlayType.LiveLite;
            if (kChannelIds.contains(videoAsset.getChannelId()) != null && playType == PlayType.LiveLite) {
                return true;
            }
        }
        return null;
    }
}
