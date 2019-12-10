package com.google.ads.interactivemedia.v3.internal;

import com.google.ads.interactivemedia.v3.api.AdErrorEvent.AdErrorListener;
import com.google.ads.interactivemedia.v3.api.player.AdProgressProvider;
import com.google.ads.interactivemedia.v3.impl.data.b;
import com.google.ads.interactivemedia.v3.impl.data.m;
import com.google.ads.interactivemedia.v3.internal.jc.c;

/* compiled from: IMASDK */
public interface jo extends AdErrorListener, AdProgressProvider {
    void a();

    void a(b bVar);

    void a(boolean z);

    boolean a(c cVar, m mVar);

    void b();

    boolean b(c cVar, m mVar);

    void c();

    void d();

    boolean e();
}
