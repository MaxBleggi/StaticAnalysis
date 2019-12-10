package com.google.android.gms.internal.cast;

import androidx.mediarouter.media.MediaRouter;
import androidx.mediarouter.media.MediaRouter.Callback;
import androidx.mediarouter.media.MediaRouter.RouteInfo;
import com.google.android.gms.common.internal.Preconditions;

public final class zzv extends Callback {
    private static final zzdh zzbe = new zzdh("MediaRouterCallback");
    private final zzl zzjs;

    public zzv(zzl com_google_android_gms_internal_cast_zzl) {
        this.zzjs = (zzl) Preconditions.checkNotNull(com_google_android_gms_internal_cast_zzl);
    }

    public final void onRouteSelected(MediaRouter mediaRouter, RouteInfo routeInfo) {
        try {
            this.zzjs.zzd(routeInfo.getId(), routeInfo.getExtras());
        } catch (MediaRouter mediaRouter2) {
            zzbe.zza(mediaRouter2, "Unable to call %s on %s.", "onRouteSelected", zzl.class.getSimpleName());
        }
    }

    public final void onRouteUnselected(MediaRouter mediaRouter, RouteInfo routeInfo, int i) {
        try {
            this.zzjs.zza(routeInfo.getId(), routeInfo.getExtras(), i);
        } catch (MediaRouter mediaRouter2) {
            zzbe.zza(mediaRouter2, "Unable to call %s on %s.", "onRouteUnselected", zzl.class.getSimpleName());
        }
    }

    public final void onRouteAdded(MediaRouter mediaRouter, RouteInfo routeInfo) {
        try {
            this.zzjs.zza(routeInfo.getId(), routeInfo.getExtras());
        } catch (MediaRouter mediaRouter2) {
            zzbe.zza(mediaRouter2, "Unable to call %s on %s.", "onRouteAdded", zzl.class.getSimpleName());
        }
    }

    public final void onRouteChanged(MediaRouter mediaRouter, RouteInfo routeInfo) {
        try {
            this.zzjs.zzb(routeInfo.getId(), routeInfo.getExtras());
        } catch (MediaRouter mediaRouter2) {
            zzbe.zza(mediaRouter2, "Unable to call %s on %s.", "onRouteChanged", zzl.class.getSimpleName());
        }
    }

    public final void onRouteRemoved(MediaRouter mediaRouter, RouteInfo routeInfo) {
        try {
            this.zzjs.zzc(routeInfo.getId(), routeInfo.getExtras());
        } catch (MediaRouter mediaRouter2) {
            zzbe.zza(mediaRouter2, "Unable to call %s on %s.", "onRouteRemoved", zzl.class.getSimpleName());
        }
    }
}
