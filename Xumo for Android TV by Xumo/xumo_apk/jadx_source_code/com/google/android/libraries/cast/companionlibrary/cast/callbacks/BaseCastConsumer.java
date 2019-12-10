package com.google.android.libraries.cast.companionlibrary.cast.callbacks;

import androidx.mediarouter.media.MediaRouter.RouteInfo;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.OnFailedListener;

public interface BaseCastConsumer extends OnFailedListener {
    void onCastAvailabilityChanged(boolean z);

    void onCastDeviceDetected(RouteInfo routeInfo);

    void onConnected();

    void onConnectionFailed(ConnectionResult connectionResult);

    void onConnectionSuspended(int i);

    void onConnectivityRecovered();

    void onDeviceSelected(CastDevice castDevice, RouteInfo routeInfo);

    void onDisconnected();

    void onDisconnectionReason(int i);

    void onReconnectionStatusChanged(int i);

    void onRouteRemoved(RouteInfo routeInfo);

    void onUiVisibilityChanged(boolean z);
}
