package com.google.android.gms.internal.cast;

import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import androidx.mediarouter.media.MediaRouteSelector;
import androidx.mediarouter.media.MediaRouter;
import androidx.mediarouter.media.MediaRouter.Callback;
import androidx.mediarouter.media.MediaRouter.RouteInfo;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class zzw extends zzk {
    private final MediaRouter zzcb;
    private final Map<MediaRouteSelector, Set<Callback>> zzjt = new HashMap();

    public zzw(MediaRouter mediaRouter) {
        this.zzcb = mediaRouter;
    }

    public final int zzm() {
        return 12451009;
    }

    public final void zza(Bundle bundle, zzl com_google_android_gms_internal_cast_zzl) {
        bundle = MediaRouteSelector.fromBundle(bundle);
        if (!this.zzjt.containsKey(bundle)) {
            this.zzjt.put(bundle, new HashSet());
        }
        ((Set) this.zzjt.get(bundle)).add(new zzv(com_google_android_gms_internal_cast_zzl));
    }

    public final void zzan() {
        for (Set<Callback> it : this.zzjt.values()) {
            for (Callback removeCallback : it) {
                this.zzcb.removeCallback(removeCallback);
            }
        }
        this.zzjt.clear();
    }

    public final void zza(Bundle bundle, int i) {
        bundle = MediaRouteSelector.fromBundle(bundle);
        for (Callback addCallback : (Set) this.zzjt.get(bundle)) {
            this.zzcb.addCallback(bundle, addCallback, i);
        }
    }

    public final void zzd(Bundle bundle) {
        for (Callback removeCallback : (Set) this.zzjt.get(MediaRouteSelector.fromBundle(bundle))) {
            this.zzcb.removeCallback(removeCallback);
        }
    }

    public final boolean zzb(Bundle bundle, int i) {
        return this.zzcb.isRouteAvailable(MediaRouteSelector.fromBundle(bundle), i);
    }

    public final void zzk(String str) {
        for (RouteInfo routeInfo : this.zzcb.getRoutes()) {
            if (routeInfo.getId().equals(str)) {
                this.zzcb.selectRoute(routeInfo);
                return;
            }
        }
    }

    public final void zzak() {
        this.zzcb.selectRoute(this.zzcb.getDefaultRoute());
    }

    public final boolean zzal() {
        return this.zzcb.getSelectedRoute().getId().equals(this.zzcb.getDefaultRoute().getId());
    }

    public final Bundle zzl(String str) {
        for (RouteInfo routeInfo : this.zzcb.getRoutes()) {
            if (routeInfo.getId().equals(str)) {
                return routeInfo.getExtras();
            }
        }
        return null;
    }

    public final String zzam() {
        return this.zzcb.getSelectedRoute().getId();
    }

    public final void setMediaSessionCompat(MediaSessionCompat mediaSessionCompat) {
        this.zzcb.setMediaSessionCompat(mediaSessionCompat);
    }
}
