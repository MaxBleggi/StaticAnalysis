package com.google.ads.interactivemedia.v3.internal;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.google.ads.interactivemedia.v3.api.AdError;
import com.google.ads.interactivemedia.v3.api.AdError.AdErrorCode;
import com.google.ads.interactivemedia.v3.api.AdError.AdErrorType;
import com.google.ads.interactivemedia.v3.api.AdErrorEvent;
import com.google.ads.interactivemedia.v3.api.StreamDisplayContainer;
import com.google.ads.interactivemedia.v3.api.player.VideoProgressUpdate;
import com.google.ads.interactivemedia.v3.api.player.VideoStreamPlayer;
import com.google.ads.interactivemedia.v3.api.player.VideoStreamPlayer.VideoStreamPlayerCallback;
import com.google.ads.interactivemedia.v3.impl.data.m;
import com.google.ads.interactivemedia.v3.impl.data.o;
import com.google.ads.interactivemedia.v3.internal.jc.c;
import com.google.ads.interactivemedia.v3.internal.ji.b;
import java.util.HashMap;
import java.util.Map;

/* compiled from: IMASDK */
public class jn implements VideoStreamPlayerCallback, b, jo {
    private VideoStreamPlayer a;
    private jd b;
    private final jl c;
    private boolean d;
    private iv e;
    private il f;
    private final String g;
    private String h;

    /* compiled from: IMASDK */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[c.values().length];

        static {
            /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r0 = com.google.ads.interactivemedia.v3.internal.jc.c.values();
            r0 = r0.length;
            r0 = new int[r0];
            a = r0;
            r0 = a;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = com.google.ads.interactivemedia.v3.internal.jc.c.loadStream;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r1 = r1.ordinal();	 Catch:{ NoSuchFieldError -> 0x0014 }
            r2 = 1;	 Catch:{ NoSuchFieldError -> 0x0014 }
            r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0014 }
        L_0x0014:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.ads.interactivemedia.v3.internal.jn.1.<clinit>():void");
        }
    }

    /* compiled from: IMASDK */
    static abstract class a {
        a() {
        }

        abstract String TXXX();

        public static a create(String str) {
            return new ip(str);
        }
    }

    public jn(String str, jf jfVar, jd jdVar, jl jlVar, StreamDisplayContainer streamDisplayContainer, String str2, Context context) throws AdError {
        this(str, jfVar, jdVar, jlVar, streamDisplayContainer, str2, null, null, context);
    }

    public boolean b(c cVar, m mVar) {
        return false;
    }

    public boolean e() {
        return true;
    }

    public void onAdError(AdErrorEvent adErrorEvent) {
    }

    public jn(String str, jf jfVar, jd jdVar, jl jlVar, StreamDisplayContainer streamDisplayContainer, String str2, iv ivVar, il ilVar, Context context) throws AdError {
        this.d = false;
        this.a = streamDisplayContainer.getVideoStreamPlayer();
        if (this.a != null) {
            this.c = jlVar;
            this.g = str;
            this.b = jdVar;
            this.h = str2;
            this.d = false;
            this.e = ivVar;
            if (this.e == null) {
                this.e = new iv(this.a, jfVar.a());
            }
            this.f = ilVar;
            if (this.f == null) {
                try {
                    this.f = new il(str, jfVar, jdVar, streamDisplayContainer, context);
                } catch (String str3) {
                    Log.e("IMASDK", "Error creating ad UI: ", str3);
                    return;
                }
            }
            return;
        }
        throw new AdError(AdErrorType.LOAD, AdErrorCode.INVALID_ARGUMENTS, "Server-side ad insertion player was not provided.");
    }

    public void a(boolean z) {
        this.e.a(this.f);
        this.e.a(this);
    }

    public VideoProgressUpdate getAdProgress() {
        return this.a.getContentProgress();
    }

    public boolean a(c cVar, m mVar) {
        if (AnonymousClass1.a[cVar.ordinal()] != 1) {
            return false;
        }
        if (mVar == null || mVar.streamUrl == null) {
            this.c.a((AdErrorEvent) new id(new AdError(AdErrorType.LOAD, AdErrorCode.INTERNAL_ERROR, "Load message must contain video url.")));
        } else {
            this.d = false;
            this.e.b();
            this.a.loadUrl(a(mVar.streamUrl), mVar.subtitles);
        }
        return true;
    }

    public void a(com.google.ads.interactivemedia.v3.impl.data.b bVar) {
        this.f.a(bVar);
    }

    public void a() {
        this.f.a();
    }

    public void d() {
        Log.d("SDK_DEBUG", "Destroying StreamVideoDisplay");
        g();
        this.a = null;
        this.b = null;
        if (this.e != null) {
            this.e.c();
            this.e.b(this.f);
            this.e.b(this);
        }
        this.e = null;
        this.f.b();
        this.f = null;
    }

    public void onUserTextReceived(String str) {
        a(c.timedMetadata, a.create(str));
    }

    public void onVolumeChanged(int i) {
        a(c.volumeChange, o.builder().volumePercentage(i).build());
    }

    public void a(VideoProgressUpdate videoProgressUpdate) {
        if (!this.d) {
            a(c.start, o.builder().volumePercentage(this.a.getVolume()).build());
            this.d = true;
        }
        a(c.timeupdate, (Object) videoProgressUpdate);
    }

    public void b() {
        this.a.onAdBreakStarted();
    }

    public void c() {
        this.a.onAdBreakEnded();
    }

    public void f() {
        this.a.addCallback(this);
    }

    public void g() {
        this.a.removeCallback(this);
    }

    private void a(c cVar, Object obj) {
        this.b.b(new jc(jc.b.videoDisplay, cVar, this.g, obj));
    }

    public String a(String str) {
        if (!(str == null || this.h == null)) {
            if (this.h.length() != 0) {
                String replaceAll = this.h.trim().replaceAll("\\s+", "");
                if (replaceAll.charAt(0) == '?') {
                    replaceAll = replaceAll.substring(1);
                }
                if (replaceAll.length() == 0) {
                    return str;
                }
                Map a = jy.a(Uri.parse(str));
                Map hashMap = new HashMap();
                str = Uri.parse(str).buildUpon();
                str.clearQuery();
                String str2 = "http://www.dom.com/path?";
                replaceAll = String.valueOf(replaceAll);
                Map a2 = jy.a(Uri.parse(replaceAll.length() != 0 ? str2.concat(replaceAll) : new String(str2)));
                hashMap.putAll(a2);
                if (!a.isEmpty()) {
                    for (String str3 : a.keySet()) {
                        if (!a2.containsKey(str3)) {
                            hashMap.put(str3, (String) a.get(str3));
                        }
                    }
                }
                str.encodedQuery(jy.a(hashMap));
                return str.build().toString();
            }
        }
        return str;
    }
}
