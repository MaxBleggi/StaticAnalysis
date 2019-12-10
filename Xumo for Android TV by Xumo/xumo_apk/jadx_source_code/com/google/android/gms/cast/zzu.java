package com.google.android.gms.cast;

import androidx.mediarouter.media.MediaRouter;
import androidx.mediarouter.media.MediaRouter.Callback;
import androidx.mediarouter.media.MediaRouter.RouteInfo;

final class zzu extends Callback {
    private final /* synthetic */ CastRemoteDisplayLocalService zzch;

    zzu(CastRemoteDisplayLocalService castRemoteDisplayLocalService) {
        this.zzch = castRemoteDisplayLocalService;
    }

    public final void onRouteUnselected(MediaRouter mediaRouter, RouteInfo routeInfo) {
        this.zzch.zzb("onRouteUnselected");
        if (this.zzch.zzbx == null) {
            this.zzch.zzb("onRouteUnselected, no device was selected");
        } else if (CastDevice.getFromBundle(routeInfo.getExtras()).getDeviceId().equals(this.zzch.zzbx.getDeviceId()) == null) {
            this.zzch.zzb("onRouteUnselected, device does not match");
        } else {
            CastRemoteDisplayLocalService.stopService();
        }
    }
}
