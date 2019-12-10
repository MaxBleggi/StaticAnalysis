package com.google.android.libraries.cast.companionlibrary.cast;

import androidx.mediarouter.media.MediaRouter;
import androidx.mediarouter.media.MediaRouter.Callback;
import androidx.mediarouter.media.MediaRouter.RouteInfo;
import com.google.android.gms.cast.CastDevice;
import com.google.android.libraries.cast.companionlibrary.utils.LogUtils;

public class CastMediaRouterCallback extends Callback {
    private static final String TAG = LogUtils.makeLogTag(CastMediaRouterCallback.class);
    private final BaseCastManager mCastManager;
    private boolean mRouteAvailable = false;

    public CastMediaRouterCallback(BaseCastManager baseCastManager) {
        this.mCastManager = baseCastManager;
    }

    public void onRouteSelected(MediaRouter mediaRouter, RouteInfo routeInfo) {
        mediaRouter = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onRouteSelected: info=");
        stringBuilder.append(routeInfo);
        LogUtils.LOGD(mediaRouter, stringBuilder.toString());
        if (this.mCastManager.getReconnectionStatus() == 3) {
            this.mCastManager.setReconnectionStatus(4);
            this.mCastManager.cancelReconnectionTask();
            return;
        }
        this.mCastManager.getPreferenceAccessor().saveStringToPreference(BaseCastManager.PREFS_KEY_ROUTE_ID, routeInfo.getId());
        mediaRouter = CastDevice.getFromBundle(routeInfo.getExtras());
        this.mCastManager.onDeviceSelected(mediaRouter, routeInfo);
        routeInfo = TAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("onRouteSelected: mSelectedDevice=");
        stringBuilder.append(mediaRouter != null ? mediaRouter.getFriendlyName() : "Null");
        LogUtils.LOGD(routeInfo, stringBuilder.toString());
    }

    public void onRouteUnselected(MediaRouter mediaRouter, RouteInfo routeInfo) {
        mediaRouter = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onRouteUnselected: route=");
        stringBuilder.append(routeInfo);
        LogUtils.LOGD(mediaRouter, stringBuilder.toString());
        this.mCastManager.onDeviceSelected(null, routeInfo);
    }

    public void onRouteAdded(MediaRouter mediaRouter, RouteInfo routeInfo) {
        if (!mediaRouter.getDefaultRoute().equals(routeInfo)) {
            notifyRouteAvailabilityChangedIfNeeded(mediaRouter);
            this.mCastManager.onCastDeviceDetected(routeInfo);
        }
        if (this.mCastManager.getReconnectionStatus() == 1) {
            if (routeInfo.getId().equals(this.mCastManager.getPreferenceAccessor().getStringFromPreference(BaseCastManager.PREFS_KEY_ROUTE_ID)) != null) {
                mediaRouter = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onRouteAdded: Attempting to recover a session with info=");
                stringBuilder.append(routeInfo);
                LogUtils.LOGD(mediaRouter, stringBuilder.toString());
                this.mCastManager.setReconnectionStatus(2);
                mediaRouter = CastDevice.getFromBundle(routeInfo.getExtras());
                String str = TAG;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("onRouteAdded: Attempting to recover a session with device: ");
                stringBuilder2.append(mediaRouter != null ? mediaRouter.getFriendlyName() : "Null");
                LogUtils.LOGD(str, stringBuilder2.toString());
                this.mCastManager.onDeviceSelected(mediaRouter, routeInfo);
            }
        }
    }

    public void onRouteRemoved(MediaRouter mediaRouter, RouteInfo routeInfo) {
        notifyRouteAvailabilityChangedIfNeeded(mediaRouter);
        this.mCastManager.onRouteRemoved(routeInfo);
    }

    public void onRouteChanged(MediaRouter mediaRouter, RouteInfo routeInfo) {
        notifyRouteAvailabilityChangedIfNeeded(mediaRouter);
    }

    private void notifyRouteAvailabilityChangedIfNeeded(MediaRouter mediaRouter) {
        mediaRouter = isRouteAvailable(mediaRouter);
        if (mediaRouter != this.mRouteAvailable) {
            this.mRouteAvailable = mediaRouter;
            this.mCastManager.onCastAvailabilityChanged(this.mRouteAvailable);
        }
    }

    private boolean isRouteAvailable(MediaRouter mediaRouter) {
        return mediaRouter.isRouteAvailable(this.mCastManager.getMediaRouteSelector(), 3);
    }

    public boolean isRouteAvailable() {
        return this.mRouteAvailable;
    }
}
