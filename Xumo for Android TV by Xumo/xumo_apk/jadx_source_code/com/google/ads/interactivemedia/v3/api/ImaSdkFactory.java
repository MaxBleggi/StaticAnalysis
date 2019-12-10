package com.google.ads.interactivemedia.v3.api;

import android.content.Context;
import android.net.Uri;
import com.google.ads.interactivemedia.v3.impl.data.TestingConfiguration;
import com.google.ads.interactivemedia.v3.internal.ic;
import com.google.ads.interactivemedia.v3.internal.ii;
import com.google.ads.interactivemedia.v3.internal.im;
import com.google.ads.interactivemedia.v3.internal.in;
import com.google.ads.interactivemedia.v3.internal.is;
import com.google.ads.interactivemedia.v3.internal.iz;
import com.google.ads.interactivemedia.v3.internal.ja;
import com.google.ads.interactivemedia.v3.internal.jk;
import com.google.ads.interactivemedia.v3.internal.jm;

/* compiled from: IMASDK */
public class ImaSdkFactory {
    private static ImaSdkFactory instance;

    private ImaSdkFactory() {
    }

    public static ImaSdkFactory getInstance() {
        if (instance == null) {
            instance = new ImaSdkFactory();
        }
        return instance;
    }

    public ImaSdkSettings createImaSdkSettings() {
        return new ja();
    }

    public AdsLoader createAdsLoader(Context context) {
        return createAdsLoader(context, createImaSdkSettings());
    }

    public AdsLoader createAdsLoader(Context context, ImaSdkSettings imaSdkSettings) {
        Uri uri = iz.b;
        if (imaSdkSettings != null && imaSdkSettings.isDebugMode()) {
            uri = iz.c;
        }
        return new ii(context, uri, imaSdkSettings);
    }

    private AdsLoader createAdsLoader(Context context, ImaSdkSettings imaSdkSettings, TestingConfiguration testingConfiguration) {
        Uri uri = iz.b;
        if (imaSdkSettings != null && imaSdkSettings.isDebugMode()) {
            uri = iz.c;
        }
        return createAdsLoader(context, uri, imaSdkSettings, testingConfiguration);
    }

    private AdsLoader createAdsLoader(Context context, Uri uri, ImaSdkSettings imaSdkSettings, TestingConfiguration testingConfiguration) {
        AdsLoader iiVar = new ii(context, uri, imaSdkSettings, testingConfiguration);
        iiVar.a();
        return iiVar;
    }

    public AdDisplayContainer createAdDisplayContainer() {
        return new ic();
    }

    public StreamDisplayContainer createStreamDisplayContainer() {
        return new jk();
    }

    public AdsRenderingSettings createAdsRenderingSettings() {
        return new im();
    }

    public AdsRequest createAdsRequest() {
        return new in();
    }

    public StreamRequest createLiveStreamRequest(String str, String str2, StreamDisplayContainer streamDisplayContainer) {
        StreamRequest jmVar = new jm();
        jmVar.a(str);
        jmVar.d(str2);
        jmVar.a(streamDisplayContainer);
        return jmVar;
    }

    public StreamRequest createVodStreamRequest(String str, String str2, String str3, StreamDisplayContainer streamDisplayContainer) {
        StreamRequest jmVar = new jm();
        jmVar.b(str);
        jmVar.c(str2);
        jmVar.d(str3);
        jmVar.a(streamDisplayContainer);
        return jmVar;
    }

    public CompanionAdSlot createCompanionAdSlot() {
        return new is();
    }
}
