package com.google.ads.interactivemedia.v3.internal;

import android.net.Uri;
import com.google.ads.interactivemedia.v3.api.CompanionAdSlot;
import com.google.ads.interactivemedia.v3.api.UiElement;
import com.google.ads.interactivemedia.v3.impl.data.m;
import com.google.ads.interactivemedia.v3.impl.data.n;
import com.google.android.exoplayer2.upstream.DataSchemeDataSource;
import java.lang.reflect.Type;
import java.net.MalformedURLException;

/* compiled from: IMASDK */
public class jc {
    private static final fz a = new ga().a(UiElement.class, n.GSON_TYPE_ADAPTER).a(CompanionAdSlot.class, new gm<CompanionAdSlot>() {
        public gf a(CompanionAdSlot companionAdSlot, Type type, gl glVar) {
            glVar = companionAdSlot.getWidth();
            companionAdSlot = companionAdSlot.getHeight();
            StringBuilder stringBuilder = new StringBuilder(23);
            stringBuilder.append(glVar);
            stringBuilder.append("x");
            stringBuilder.append(companionAdSlot);
            return new gk(stringBuilder.toString());
        }
    }).a(new ko()).a();
    private final b b;
    private final Object c;
    private final String d;
    private final c e;

    /* compiled from: IMASDK */
    public enum a {
        nativeUi,
        webViewUi,
        none
    }

    /* compiled from: IMASDK */
    public enum b {
        activityMonitor,
        adsManager,
        adsLoader,
        contentTimeUpdate,
        displayContainer,
        i18n,
        log,
        omid,
        videoDisplay,
        webViewLoaded
    }

    /* compiled from: IMASDK */
    public enum c {
        adBreakEnded,
        adBreakReady,
        adBreakStarted,
        adMetadata,
        adProgress,
        adsLoaded,
        allAdsCompleted,
        appStateChanged,
        click,
        complete,
        companionView,
        contentComplete,
        contentPauseRequested,
        contentResumeRequested,
        contentTimeUpdate,
        csi,
        cuepointsChanged,
        discardAdBreak,
        displayCompanions,
        destroy,
        end,
        error,
        firstquartile,
        focusSkipButton,
        fullscreen,
        getViewability,
        hide,
        impression,
        init,
        initialized,
        load,
        loaded,
        loadStream,
        log,
        midpoint,
        mute,
        omidReady,
        omidUnavailable,
        pause,
        play,
        reportVastEvent,
        resume,
        requestAds,
        requestNextAdBreak,
        requestStream,
        setPlaybackOptions,
        showVideo,
        skip,
        skippableStateChanged,
        skipShown,
        start,
        startTracking,
        stop,
        stopTracking,
        streamInitialized,
        thirdquartile,
        timedMetadata,
        timeupdate,
        unload,
        unmute,
        viewability,
        videoClicked,
        videoIconClicked,
        volumeChange,
        adRemainingTime,
        learnMore,
        preSkipButton,
        skipButton
    }

    public static jc a(String str) throws MalformedURLException, gn {
        str = Uri.parse(str);
        String substring = str.getPath().substring(1);
        if (str.getQueryParameter("sid") != null) {
            return new jc(b.valueOf(substring), c.valueOf(str.getQueryParameter("type")), str.getQueryParameter("sid"), a.a(str.getQueryParameter(DataSchemeDataSource.SCHEME_DATA), m.class));
        }
        throw new MalformedURLException("Session id must be provided in message.");
    }

    public jc(b bVar, c cVar, String str, Object obj) {
        this.b = bVar;
        this.e = cVar;
        this.d = str;
        this.c = obj;
    }

    public jc(b bVar, c cVar, String str) {
        this(bVar, cVar, str, null);
    }

    public b a() {
        return this.b;
    }

    public c b() {
        return this.e;
    }

    public Object c() {
        return this.c;
    }

    public String d() {
        return this.d;
    }

    public String e() {
        com.google.ads.interactivemedia.v3.internal.lf.a a = new com.google.ads.interactivemedia.v3.internal.lf.a().a("type", this.e).a("sid", this.d);
        if (this.c != null) {
            a.a(DataSchemeDataSource.SCHEME_DATA, this.c);
        }
        Object a2 = a.a();
        return String.format("%s('%s', %s);", new Object[]{"javascript:adsense.mobileads.afmanotify.receiveMessage", this.b, a.a(a2)});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof jc)) {
            return false;
        }
        jc jcVar = (jc) obj;
        return this.b == jcVar.b && ks.a(this.c, jcVar.c) && ks.a(this.d, jcVar.d) && this.e == jcVar.e;
    }

    public int hashCode() {
        return ks.a(this.b, this.c, this.d, this.e);
    }

    public String toString() {
        return String.format("JavaScriptMessage [command=%s, type=%s, sid=%s, data=%s]", new Object[]{this.b, this.e, this.d, this.c});
    }
}
