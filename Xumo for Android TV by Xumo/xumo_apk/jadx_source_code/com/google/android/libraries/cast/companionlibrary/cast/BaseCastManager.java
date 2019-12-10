package com.google.android.libraries.cast.companionlibrary.cast;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import androidx.core.view.MenuItemCompat;
import androidx.mediarouter.app.MediaRouteActionProvider;
import androidx.mediarouter.app.MediaRouteButton;
import androidx.mediarouter.app.MediaRouteDialogFactory;
import androidx.mediarouter.media.MediaRouteSelector;
import androidx.mediarouter.media.MediaRouter;
import androidx.mediarouter.media.MediaRouter.RouteInfo;
import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.Cast.ApplicationConnectionResult;
import com.google.android.gms.cast.Cast.CastOptions.Builder;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.CastMediaControlIntent;
import com.google.android.gms.cast.CastStatusCodes;
import com.google.android.gms.cast.LaunchOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.cast.companionlibrary.R;
import com.google.android.libraries.cast.companionlibrary.cast.callbacks.BaseCastConsumer;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.CastException;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.NoConnectionException;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.OnFailedListener;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.TransientNetworkDisconnectionException;
import com.google.android.libraries.cast.companionlibrary.cast.reconnection.ReconnectionService;
import com.google.android.libraries.cast.companionlibrary.utils.LogUtils;
import com.google.android.libraries.cast.companionlibrary.utils.PreferenceAccessor;
import com.google.android.libraries.cast.companionlibrary.utils.Utils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public abstract class BaseCastManager implements ConnectionCallbacks, OnConnectionFailedListener, OnFailedListener {
    public static final int CLEAR_ALL = 0;
    public static final int CLEAR_MEDIA_END = 8;
    public static final int CLEAR_ROUTE = 1;
    public static final int CLEAR_SESSION = 4;
    public static final int CLEAR_WIFI = 2;
    public static final int DISCONNECT_REASON_APP_NOT_RUNNING = 2;
    public static final int DISCONNECT_REASON_CONNECTIVITY = 1;
    public static final int DISCONNECT_REASON_EXPLICIT = 3;
    public static final int DISCONNECT_REASON_OTHER = 0;
    public static final int NO_APPLICATION_ERROR = 0;
    public static final int NO_STATUS_CODE = -1;
    public static final String PREFS_KEY_APPLICATION_ID = "application-id";
    public static final String PREFS_KEY_CAST_ACTIVITY_NAME = "cast-activity-name";
    public static final String PREFS_KEY_CAST_CUSTOM_DATA_NAMESPACE = "cast-custom-data-namespace";
    public static final String PREFS_KEY_MEDIA_END = "media-end";
    public static final String PREFS_KEY_ROUTE_ID = "route-id";
    public static final String PREFS_KEY_SESSION_ID = "session-id";
    public static final String PREFS_KEY_SSID = "ssid";
    public static final int RECONNECTION_STATUS_FINALIZED = 3;
    public static final int RECONNECTION_STATUS_INACTIVE = 4;
    public static final int RECONNECTION_STATUS_IN_PROGRESS = 2;
    public static final int RECONNECTION_STATUS_STARTED = 1;
    private static final int SESSION_RECOVERY_TIMEOUT_S = 10;
    private static final String TAG = LogUtils.makeLogTag(BaseCastManager.class);
    private static final int UI_VISIBILITY_DELAY_MS = 300;
    private static final int WHAT_UI_HIDDEN = 1;
    private static final int WHAT_UI_VISIBLE = 0;
    private static String sCclVersion;
    protected GoogleApiClient mApiClient;
    protected int mApplicationErrorCode = 0;
    protected String mApplicationId;
    private final Set<BaseCastConsumer> mBaseCastConsumers = new CopyOnWriteArraySet();
    protected int mCapabilities;
    protected CastConfiguration mCastConfiguration;
    protected boolean mConnectionSuspended;
    protected Context mContext;
    private boolean mDestroyOnDisconnect = false;
    protected String mDeviceName;
    protected MediaRouteSelector mMediaRouteSelector;
    protected MediaRouter mMediaRouter;
    protected CastMediaRouterCallback mMediaRouterCallback;
    protected PreferenceAccessor mPreferenceAccessor;
    protected int mReconnectionStatus = 4;
    protected AsyncTask<Void, Integer, Boolean> mReconnectionTask;
    private RouteInfo mRouteInfo;
    protected CastDevice mSelectedCastDevice;
    protected String mSessionId;
    private Handler mUiVisibilityHandler;
    protected boolean mUiVisible;
    protected int mVisibilityCounter;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DisconnectReason {
    }

    private class UpdateUiVisibilityHandlerCallback implements Callback {
        private UpdateUiVisibilityHandlerCallback() {
        }

        public boolean handleMessage(Message message) {
            BaseCastManager.this.onUiVisibilityChanged(message.what == null ? true : null);
            return true;
        }
    }

    private static boolean isFlagSet(int i, int i2) {
        if (i != 0) {
            if ((i & i2) != i2) {
                return false;
            }
        }
        return true;
    }

    protected abstract Builder getCastOptionBuilder(CastDevice castDevice);

    protected abstract void onApplicationConnected(ApplicationMetadata applicationMetadata, String str, String str2, boolean z);

    protected abstract void onApplicationConnectionFailed(int i);

    protected abstract void onApplicationStopFailed(int i);

    protected void onDeviceUnselected() {
    }

    protected BaseCastManager() {
    }

    protected BaseCastManager(Context context, CastConfiguration castConfiguration) {
        this.mCastConfiguration = castConfiguration;
        this.mCapabilities = castConfiguration.getCapabilities();
        LogUtils.setDebug(isFeatureEnabled(1));
        sCclVersion = context.getString(R.string.ccl_version);
        this.mApplicationId = castConfiguration.getApplicationId();
        castConfiguration = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("BaseCastManager is instantiated\nVersion: ");
        stringBuilder.append(sCclVersion);
        stringBuilder.append("\nApplication ID: ");
        stringBuilder.append(this.mApplicationId);
        LogUtils.LOGD(castConfiguration, stringBuilder.toString());
        this.mContext = context.getApplicationContext();
        this.mPreferenceAccessor = new PreferenceAccessor(this.mContext);
        this.mUiVisibilityHandler = new Handler(new UpdateUiVisibilityHandlerCallback());
        this.mPreferenceAccessor.saveStringToPreference(PREFS_KEY_APPLICATION_ID, this.mApplicationId);
        this.mMediaRouter = MediaRouter.getInstance(this.mContext);
        this.mMediaRouteSelector = new MediaRouteSelector.Builder().addControlCategory(CastMediaControlIntent.categoryForCast(this.mApplicationId)).build();
        this.mMediaRouterCallback = new CastMediaRouterCallback(this);
        this.mMediaRouter.addCallback(this.mMediaRouteSelector, this.mMediaRouterCallback, 4);
    }

    private MediaRouteDialogFactory getMediaRouteDialogFactory() {
        return this.mCastConfiguration.getMediaRouteDialogFactory();
    }

    public final void onDeviceSelected(CastDevice castDevice, RouteInfo routeInfo) {
        for (BaseCastConsumer onDeviceSelected : this.mBaseCastConsumers) {
            onDeviceSelected.onDeviceSelected(castDevice, routeInfo);
        }
        if (castDevice == null) {
            disconnectDevice(this.mDestroyOnDisconnect, true, false);
        } else {
            setDevice(castDevice);
        }
    }

    public final void onCastAvailabilityChanged(boolean z) {
        for (BaseCastConsumer onCastAvailabilityChanged : this.mBaseCastConsumers) {
            onCastAvailabilityChanged.onCastAvailabilityChanged(z);
        }
    }

    public final void disconnectDevice(boolean z, boolean z2, boolean z3) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("disconnectDevice(");
        stringBuilder.append(z2);
        stringBuilder.append(",");
        stringBuilder.append(z3);
        stringBuilder.append(")");
        LogUtils.LOGD(str, stringBuilder.toString());
        if (this.mSelectedCastDevice != null) {
            StringBuilder stringBuilder2;
            int i;
            this.mSelectedCastDevice = null;
            this.mDeviceName = null;
            String str2 = "disconnectDevice() Disconnect Reason: ";
            if (this.mConnectionSuspended) {
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(str2);
                stringBuilder2.append("Connectivity lost");
                str2 = stringBuilder2.toString();
                i = 1;
            } else {
                i = this.mApplicationErrorCode;
                if (i == 0) {
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(str2);
                    stringBuilder2.append("Intentional disconnect");
                    str2 = stringBuilder2.toString();
                    i = 3;
                } else if (i != CastStatusCodes.APPLICATION_NOT_RUNNING) {
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(str2);
                    stringBuilder2.append("Other");
                    str2 = stringBuilder2.toString();
                    i = 0;
                } else {
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append(str2);
                    stringBuilder2.append("App was taken over or not available anymore");
                    str2 = stringBuilder2.toString();
                    i = 2;
                }
            }
            LogUtils.LOGD(TAG, str2);
            for (BaseCastConsumer onDisconnectionReason : this.mBaseCastConsumers) {
                onDisconnectionReason.onDisconnectionReason(i);
            }
            str2 = TAG;
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("mConnectionSuspended: ");
            stringBuilder2.append(this.mConnectionSuspended);
            LogUtils.LOGD(str2, stringBuilder2.toString());
            if (!this.mConnectionSuspended && z2) {
                clearPersistedConnectionInfo(0);
                stopReconnectionService();
            }
            try {
                if ((isConnected() || isConnecting()) && z) {
                    LogUtils.LOGD(TAG, "Calling stopApplication");
                    stopApplication();
                }
            } catch (Throwable e) {
                LogUtils.LOGE(TAG, "Failed to stop the application after disconnecting route", e);
            }
            onDeviceUnselected();
            if (this.mApiClient != null) {
                if (this.mApiClient.isConnected()) {
                    LogUtils.LOGD(TAG, "Trying to disconnect");
                    this.mApiClient.disconnect();
                }
                if (this.mMediaRouter != null && z3) {
                    LogUtils.LOGD(TAG, "disconnectDevice(): Setting route to default");
                    this.mMediaRouter.selectRoute(this.mMediaRouter.getDefaultRoute());
                }
                this.mApiClient = null;
            }
            this.mSessionId = null;
            onDisconnected(z, z2, z3);
        }
    }

    public final boolean isDeviceOnLocalNetwork() throws CastException {
        if (this.mSelectedCastDevice != null) {
            return this.mSelectedCastDevice.isOnLocalNetwork();
        }
        throw new CastException("No cast device has yet been selected");
    }

    private void setDevice(CastDevice castDevice) {
        this.mSelectedCastDevice = castDevice;
        this.mDeviceName = this.mSelectedCastDevice.getFriendlyName();
        if (this.mApiClient == null) {
            castDevice = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("acquiring a connection to Google Play services for ");
            stringBuilder.append(this.mSelectedCastDevice);
            LogUtils.LOGD(castDevice, stringBuilder.toString());
            this.mApiClient = new GoogleApiClient.Builder(this.mContext).addApi(Cast.API, getCastOptionBuilder(this.mSelectedCastDevice).build()).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
            this.mApiClient.connect();
        } else if (this.mApiClient.isConnected() == null && this.mApiClient.isConnecting() == null) {
            this.mApiClient.connect();
        }
    }

    public final void onCastDeviceDetected(RouteInfo routeInfo) {
        for (BaseCastConsumer onCastDeviceDetected : this.mBaseCastConsumers) {
            onCastDeviceDetected.onCastDeviceDetected(routeInfo);
        }
    }

    public final void onRouteRemoved(RouteInfo routeInfo) {
        for (BaseCastConsumer onRouteRemoved : this.mBaseCastConsumers) {
            onRouteRemoved.onRouteRemoved(routeInfo);
        }
    }

    public final MenuItem addMediaRouterButton(Menu menu, int i) {
        menu = menu.findItem(i);
        MediaRouteActionProvider mediaRouteActionProvider = (MediaRouteActionProvider) MenuItemCompat.getActionProvider(menu);
        mediaRouteActionProvider.setRouteSelector(this.mMediaRouteSelector);
        if (getMediaRouteDialogFactory() != null) {
            mediaRouteActionProvider.setDialogFactory(getMediaRouteDialogFactory());
        }
        return menu;
    }

    public final void addMediaRouterButton(MediaRouteButton mediaRouteButton) {
        mediaRouteButton.setRouteSelector(this.mMediaRouteSelector);
        if (getMediaRouteDialogFactory() != null) {
            mediaRouteButton.setDialogFactory(getMediaRouteDialogFactory());
        }
    }

    public final synchronized void incrementUiCounter() {
        this.mVisibilityCounter++;
        if (!this.mUiVisible) {
            this.mUiVisible = true;
            this.mUiVisibilityHandler.removeMessages(1);
            this.mUiVisibilityHandler.sendEmptyMessageDelayed(0, 300);
        }
        if (this.mVisibilityCounter == 0) {
            LogUtils.LOGD(TAG, "UI is no longer visible");
        } else {
            LogUtils.LOGD(TAG, "UI is visible");
        }
    }

    public final synchronized void decrementUiCounter() {
        int i = this.mVisibilityCounter - 1;
        this.mVisibilityCounter = i;
        if (i == 0) {
            LogUtils.LOGD(TAG, "UI is no longer visible");
            if (this.mUiVisible) {
                this.mUiVisible = false;
                this.mUiVisibilityHandler.removeMessages(0);
                this.mUiVisibilityHandler.sendEmptyMessageDelayed(1, 300);
            }
        } else {
            LogUtils.LOGD(TAG, "UI is visible");
        }
    }

    protected void onUiVisibilityChanged(boolean z) {
        if (z) {
            if (!(this.mMediaRouter == null || this.mMediaRouterCallback == null)) {
                LogUtils.LOGD(TAG, "onUiVisibilityChanged() addCallback called");
                startCastDiscovery();
                if (isFeatureEnabled(32)) {
                    reconnectSessionIfPossible();
                }
            }
        } else if (this.mMediaRouter != null) {
            LogUtils.LOGD(TAG, "onUiVisibilityChanged() removeCallback called");
            stopCastDiscovery();
        }
        for (BaseCastConsumer onUiVisibilityChanged : this.mBaseCastConsumers) {
            onUiVisibilityChanged.onUiVisibilityChanged(z);
        }
    }

    public final void startCastDiscovery() {
        this.mMediaRouter.addCallback(this.mMediaRouteSelector, this.mMediaRouterCallback, 4);
    }

    public final void stopCastDiscovery() {
        this.mMediaRouter.removeCallback(this.mMediaRouterCallback);
    }

    public static boolean checkGooglePlayServices(Activity activity) {
        return Utils.checkGooglePlayServices(activity);
    }

    public final boolean isConnected() {
        return this.mApiClient != null && this.mApiClient.isConnected();
    }

    public final boolean isConnecting() {
        return this.mApiClient != null && this.mApiClient.isConnecting();
    }

    public final void disconnect() {
        if (isConnected() || isConnecting()) {
            disconnectDevice(this.mDestroyOnDisconnect, true, true);
        }
    }

    public final String getDeviceName() {
        return this.mDeviceName;
    }

    public final void setStopOnDisconnect(boolean z) {
        this.mDestroyOnDisconnect = z;
    }

    public final MediaRouteSelector getMediaRouteSelector() {
        return this.mMediaRouteSelector;
    }

    public final RouteInfo getRouteInfo() {
        return this.mRouteInfo;
    }

    public final void setRouteInfo(RouteInfo routeInfo) {
        this.mRouteInfo = routeInfo;
    }

    public final boolean isFeatureEnabled(int i) {
        return (this.mCapabilities & i) == i;
    }

    public final void setDeviceVolume(double d) throws CastException, TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        try {
            Cast.CastApi.setVolume(this.mApiClient, d);
        } catch (Throwable e) {
            throw new CastException("Failed to set volume", e);
        } catch (double d2) {
            throw new NoConnectionException("setDeviceVolume()", d2);
        }
    }

    public final double getDeviceVolume() throws TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        try {
            return Cast.CastApi.getVolume(this.mApiClient);
        } catch (Throwable e) {
            throw new NoConnectionException("getDeviceVolume()", e);
        }
    }

    public final void adjustDeviceVolume(double d) throws CastException, TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        double deviceVolume = getDeviceVolume();
        if (deviceVolume >= 0.0d) {
            setDeviceVolume(deviceVolume + d);
        }
    }

    public final boolean isDeviceMute() throws TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        try {
            return Cast.CastApi.isMute(this.mApiClient);
        } catch (Throwable e) {
            throw new NoConnectionException("isDeviceMute()", e);
        }
    }

    public final void setDeviceMute(boolean z) throws CastException, TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        try {
            Cast.CastApi.setMute(this.mApiClient, z);
        } catch (Throwable e) {
            throw new CastException("setDeviceMute", e);
        } catch (boolean z2) {
            throw new NoConnectionException("setDeviceMute()", z2);
        }
    }

    public final int getReconnectionStatus() {
        return this.mReconnectionStatus;
    }

    public final void setReconnectionStatus(int i) {
        if (this.mReconnectionStatus != i) {
            this.mReconnectionStatus = i;
            onReconnectionStatusChanged(this.mReconnectionStatus);
        }
    }

    private void onReconnectionStatusChanged(int i) {
        for (BaseCastConsumer onReconnectionStatusChanged : this.mBaseCastConsumers) {
            onReconnectionStatusChanged.onReconnectionStatusChanged(i);
        }
    }

    protected final boolean canConsiderSessionRecovery() {
        return canConsiderSessionRecovery(null);
    }

    public final boolean canConsiderSessionRecovery(String str) {
        String stringFromPreference = this.mPreferenceAccessor.getStringFromPreference(PREFS_KEY_SESSION_ID);
        String stringFromPreference2 = this.mPreferenceAccessor.getStringFromPreference(PREFS_KEY_ROUTE_ID);
        String stringFromPreference3 = this.mPreferenceAccessor.getStringFromPreference(PREFS_KEY_SSID);
        if (stringFromPreference != null) {
            if (stringFromPreference2 != null) {
                if (str != null && (stringFromPreference3 == null || stringFromPreference3.equals(str) == null)) {
                    return false;
                }
                LogUtils.LOGD(TAG, "Found session info in the preferences, so proceed with an attempt to reconnect if possible");
                return true;
            }
        }
        return false;
    }

    private void reconnectSessionIfPossibleInternal(RouteInfo routeInfo) {
        if (!isConnected()) {
            String stringFromPreference = this.mPreferenceAccessor.getStringFromPreference(PREFS_KEY_SESSION_ID);
            String stringFromPreference2 = this.mPreferenceAccessor.getStringFromPreference(PREFS_KEY_ROUTE_ID);
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("reconnectSessionIfPossible() Retrieved from preferences: sessionId=");
            stringBuilder.append(stringFromPreference);
            stringBuilder.append(", routeId=");
            stringBuilder.append(stringFromPreference2);
            LogUtils.LOGD(str, stringBuilder.toString());
            if (stringFromPreference != null) {
                if (stringFromPreference2 != null) {
                    setReconnectionStatus(2);
                    CastDevice fromBundle = CastDevice.getFromBundle(routeInfo.getExtras());
                    if (fromBundle != null) {
                        stringFromPreference2 = TAG;
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("trying to acquire Cast Client for ");
                        stringBuilder2.append(fromBundle);
                        LogUtils.LOGD(stringFromPreference2, stringBuilder2.toString());
                        onDeviceSelected(fromBundle, routeInfo);
                    }
                }
            }
        }
    }

    public final void cancelReconnectionTask() {
        LogUtils.LOGD(TAG, "cancelling reconnection task");
        if (this.mReconnectionTask != null && !this.mReconnectionTask.isCancelled()) {
            this.mReconnectionTask.cancel(true);
        }
    }

    public final void reconnectSessionIfPossible() {
        reconnectSessionIfPossible(10);
    }

    public final void reconnectSessionIfPossible(int i) {
        reconnectSessionIfPossible(i, null);
    }

    @TargetApi(14)
    public void reconnectSessionIfPossible(final int i, String str) {
        LogUtils.LOGD(TAG, String.format("reconnectSessionIfPossible(%d, %s)", new Object[]{Integer.valueOf(i), str}));
        if (!isConnected()) {
            String stringFromPreference = this.mPreferenceAccessor.getStringFromPreference(PREFS_KEY_ROUTE_ID);
            if (canConsiderSessionRecovery(str) != null) {
                String<RouteInfo> routes = this.mMediaRouter.getRoutes();
                RouteInfo routeInfo = null;
                if (routes != null) {
                    for (RouteInfo routeInfo2 : routes) {
                        if (routeInfo2.getId().equals(stringFromPreference)) {
                            routeInfo = routeInfo2;
                            break;
                        }
                    }
                }
                if (routeInfo != null) {
                    reconnectSessionIfPossibleInternal(routeInfo);
                } else {
                    setReconnectionStatus(1);
                }
                if (this.mReconnectionTask != null && this.mReconnectionTask.isCancelled() == null) {
                    this.mReconnectionTask.cancel(true);
                }
                this.mReconnectionTask = new AsyncTask<Void, Integer, Boolean>() {
                    protected java.lang.Boolean doInBackground(java.lang.Void... r5) {
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
                        r4 = this;
                        r5 = 0;
                        r0 = 0;
                    L_0x0002:
                        r1 = r7;
                        if (r0 >= r1) goto L_0x003d;
                    L_0x0006:
                        r1 = com.google.android.libraries.cast.companionlibrary.cast.BaseCastManager.TAG;
                        r2 = new java.lang.StringBuilder;
                        r2.<init>();
                        r3 = "Reconnection: Attempt ";
                        r2.append(r3);
                        r0 = r0 + 1;
                        r2.append(r0);
                        r2 = r2.toString();
                        com.google.android.libraries.cast.companionlibrary.utils.LogUtils.LOGD(r1, r2);
                        r1 = r4.isCancelled();
                        r2 = 1;
                        if (r1 == 0) goto L_0x002c;
                    L_0x0027:
                        r5 = java.lang.Boolean.valueOf(r2);
                        return r5;
                    L_0x002c:
                        r1 = com.google.android.libraries.cast.companionlibrary.cast.BaseCastManager.this;	 Catch:{ InterruptedException -> 0x0002 }
                        r1 = r1.isConnected();	 Catch:{ InterruptedException -> 0x0002 }
                        if (r1 == 0) goto L_0x0037;	 Catch:{ InterruptedException -> 0x0002 }
                    L_0x0034:
                        r4.cancel(r2);	 Catch:{ InterruptedException -> 0x0002 }
                    L_0x0037:
                        r1 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;	 Catch:{ InterruptedException -> 0x0002 }
                        java.lang.Thread.sleep(r1);	 Catch:{ InterruptedException -> 0x0002 }
                        goto L_0x0002;
                    L_0x003d:
                        r5 = java.lang.Boolean.valueOf(r5);
                        return r5;
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.cast.companionlibrary.cast.BaseCastManager.1.doInBackground(java.lang.Void[]):java.lang.Boolean");
                    }

                    protected void onPostExecute(Boolean bool) {
                        if (bool == null || bool.booleanValue() == null) {
                            LogUtils.LOGD(BaseCastManager.TAG, "Couldn't reconnect, dropping connection");
                            BaseCastManager.this.setReconnectionStatus(4);
                            BaseCastManager.this.onDeviceSelected(null, null);
                        }
                    }
                };
                if (VERSION.SDK_INT >= 11) {
                    this.mReconnectionTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
                } else {
                    this.mReconnectionTask.execute(new Void[0]);
                }
            }
        }
    }

    public void onConnectivityRecovered() {
        for (BaseCastConsumer onConnectivityRecovered : this.mBaseCastConsumers) {
            onConnectivityRecovered.onConnectivityRecovered();
        }
    }

    public final void onConnected(Bundle bundle) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onConnected() reached with prior suspension: ");
        stringBuilder.append(this.mConnectionSuspended);
        LogUtils.LOGD(str, stringBuilder.toString());
        if (this.mConnectionSuspended) {
            this.mConnectionSuspended = false;
            if (bundle == null || bundle.getBoolean(Cast.EXTRA_APP_NO_LONGER_RUNNING) == null) {
                onConnectivityRecovered();
            } else {
                LogUtils.LOGD(TAG, "onConnected(): App no longer running, so disconnecting");
                disconnect();
            }
        } else if (isConnected() == null) {
            if (this.mReconnectionStatus == 2) {
                setReconnectionStatus(4);
            }
        } else {
            try {
                if (isFeatureEnabled(8) != null) {
                    this.mPreferenceAccessor.saveStringToPreference(PREFS_KEY_SSID, Utils.getWifiSsid(this.mContext));
                }
                Cast.CastApi.requestStatus(this.mApiClient);
                if (this.mCastConfiguration.isDisableLaunchOnConnect() == null) {
                    launchApp();
                }
                for (BaseCastConsumer onConnected : this.mBaseCastConsumers) {
                    onConnected.onConnected();
                }
            } catch (Bundle bundle2) {
                LogUtils.LOGE(TAG, "requestStatus()", bundle2);
            }
        }
    }

    protected void onDisconnected(boolean z, boolean z2, boolean z3) {
        LogUtils.LOGD(TAG, "onDisconnected() reached");
        this.mDeviceName = false;
        this.mConnectionSuspended = false;
        for (BaseCastConsumer onDisconnected : this.mBaseCastConsumers) {
            onDisconnected.onDisconnected();
        }
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onConnectionFailed() reached, error code: ");
        stringBuilder.append(connectionResult.getErrorCode());
        stringBuilder.append(", reason: ");
        stringBuilder.append(connectionResult.toString());
        LogUtils.LOGD(str, stringBuilder.toString());
        disconnectDevice(this.mDestroyOnDisconnect, false, false);
        this.mConnectionSuspended = false;
        if (this.mMediaRouter != null) {
            this.mMediaRouter.selectRoute(this.mMediaRouter.getDefaultRoute());
        }
        for (BaseCastConsumer onConnectionFailed : this.mBaseCastConsumers) {
            onConnectionFailed.onConnectionFailed(connectionResult);
        }
        connectionResult = connectionResult.getResolution();
        if (connectionResult != null) {
            try {
                connectionResult.send();
            } catch (ConnectionResult connectionResult2) {
                LogUtils.LOGE(TAG, "Failed to show recovery from the recoverable error", connectionResult2);
            }
        }
    }

    public void onConnectionSuspended(int i) {
        this.mConnectionSuspended = true;
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onConnectionSuspended() was called with cause: ");
        stringBuilder.append(i);
        LogUtils.LOGD(str, stringBuilder.toString());
        for (BaseCastConsumer onConnectionSuspended : this.mBaseCastConsumers) {
            onConnectionSuspended.onConnectionSuspended(i);
        }
    }

    public final void launchApp(String str, LaunchOptions launchOptions) throws TransientNetworkDisconnectionException, NoConnectionException {
        LogUtils.LOGD(TAG, "launchApp(applicationId, launchOptions) is called");
        if (!isConnected()) {
            if (this.mReconnectionStatus == 2) {
                setReconnectionStatus(4);
                return;
            }
            checkConnectivity();
        }
        if (this.mReconnectionStatus == 2) {
            LogUtils.LOGD(TAG, "Attempting to join a previously interrupted session...");
            launchOptions = this.mPreferenceAccessor.getStringFromPreference(PREFS_KEY_SESSION_ID);
            LogUtils.LOGD(TAG, "joinApplication() -> start");
            Cast.CastApi.joinApplication(this.mApiClient, str, launchOptions).setResultCallback(new ResultCallback<ApplicationConnectionResult>() {
                public void onResult(ApplicationConnectionResult applicationConnectionResult) {
                    if (applicationConnectionResult.getStatus().isSuccess()) {
                        LogUtils.LOGD(BaseCastManager.TAG, "joinApplication() -> success");
                        BaseCastManager.this.onApplicationConnected(applicationConnectionResult.getApplicationMetadata(), applicationConnectionResult.getApplicationStatus(), applicationConnectionResult.getSessionId(), applicationConnectionResult.getWasLaunched());
                        return;
                    }
                    LogUtils.LOGD(BaseCastManager.TAG, "joinApplication() -> failure");
                    BaseCastManager.this.clearPersistedConnectionInfo(12);
                    BaseCastManager.this.cancelReconnectionTask();
                    BaseCastManager.this.onApplicationConnectionFailed(applicationConnectionResult.getStatus().getStatusCode());
                }
            });
        } else {
            LogUtils.LOGD(TAG, "Launching app");
            Cast.CastApi.launchApplication(this.mApiClient, str, launchOptions).setResultCallback(new ResultCallback<ApplicationConnectionResult>() {
                public void onResult(ApplicationConnectionResult applicationConnectionResult) {
                    if (applicationConnectionResult.getStatus().isSuccess()) {
                        LogUtils.LOGD(BaseCastManager.TAG, "launchApplication() -> success result");
                        BaseCastManager.this.onApplicationConnected(applicationConnectionResult.getApplicationMetadata(), applicationConnectionResult.getApplicationStatus(), applicationConnectionResult.getSessionId(), applicationConnectionResult.getWasLaunched());
                        return;
                    }
                    LogUtils.LOGD(BaseCastManager.TAG, "launchApplication() -> failure result");
                    BaseCastManager.this.onApplicationConnectionFailed(applicationConnectionResult.getStatus().getStatusCode());
                }
            });
        }
    }

    private void launchApp() throws TransientNetworkDisconnectionException, NoConnectionException {
        LogUtils.LOGD(TAG, "launchApp() is called");
        launchApp(this.mCastConfiguration.getApplicationId(), this.mCastConfiguration.getLaunchOptions());
    }

    public final void stopApplication() throws TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        Cast.CastApi.stopApplication(this.mApiClient, this.mSessionId).setResultCallback(new ResultCallback<Status>() {
            public void onResult(Status status) {
                if (status.isSuccess()) {
                    LogUtils.LOGD(BaseCastManager.TAG, "stopApplication -> onResult Stopped application successfully");
                    return;
                }
                LogUtils.LOGD(BaseCastManager.TAG, "stopApplication -> onResult: stopping application failed");
                BaseCastManager.this.onApplicationStopFailed(status.getStatusCode());
            }
        });
    }

    public final void addBaseCastConsumer(BaseCastConsumer baseCastConsumer) {
        if (baseCastConsumer != null && this.mBaseCastConsumers.add(baseCastConsumer)) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Successfully added the new BaseCastConsumer listener ");
            stringBuilder.append(baseCastConsumer);
            LogUtils.LOGD(str, stringBuilder.toString());
        }
    }

    public final void removeBaseCastConsumer(BaseCastConsumer baseCastConsumer) {
        if (baseCastConsumer != null && this.mBaseCastConsumers.remove(baseCastConsumer)) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Successfully removed the existing BaseCastConsumer listener ");
            stringBuilder.append(baseCastConsumer);
            LogUtils.LOGD(str, stringBuilder.toString());
        }
    }

    public final void checkConnectivity() throws TransientNetworkDisconnectionException, NoConnectionException {
        if (!isConnected()) {
            if (this.mConnectionSuspended) {
                throw new TransientNetworkDisconnectionException();
            }
            throw new NoConnectionException();
        }
    }

    public void onFailed(int i, int i2) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onFailed() was called with statusCode: ");
        stringBuilder.append(i2);
        LogUtils.LOGD(str, stringBuilder.toString());
        for (BaseCastConsumer onFailed : this.mBaseCastConsumers) {
            onFailed.onFailed(i, i2);
        }
    }

    public static String getCclVersion() {
        return sCclVersion;
    }

    public PreferenceAccessor getPreferenceAccessor() {
        return this.mPreferenceAccessor;
    }

    public final void clearPersistedConnectionInfo(int i) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("clearPersistedConnectionInfo(): Clearing persisted data for ");
        stringBuilder.append(i);
        LogUtils.LOGD(str, stringBuilder.toString());
        if (isFlagSet(i, 4)) {
            this.mPreferenceAccessor.saveStringToPreference(PREFS_KEY_SESSION_ID, null);
        }
        if (isFlagSet(i, 1)) {
            this.mPreferenceAccessor.saveStringToPreference(PREFS_KEY_ROUTE_ID, null);
        }
        if (isFlagSet(i, 2)) {
            this.mPreferenceAccessor.saveStringToPreference(PREFS_KEY_SSID, null);
        }
        if (isFlagSet(i, 8) != 0) {
            this.mPreferenceAccessor.saveLongToPreference(PREFS_KEY_MEDIA_END, null);
        }
    }

    protected void startReconnectionService(long j) {
        if (isFeatureEnabled(8)) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("startReconnectionService() for media length lef = ");
            stringBuilder.append(j);
            LogUtils.LOGD(str, stringBuilder.toString());
            this.mPreferenceAccessor.saveLongToPreference(PREFS_KEY_MEDIA_END, Long.valueOf(SystemClock.elapsedRealtime() + j));
            j = this.mContext.getApplicationContext();
            Intent intent = new Intent(j, ReconnectionService.class);
            intent.setPackage(j.getPackageName());
            j.startService(intent);
        }
    }

    protected void stopReconnectionService() {
        if (isFeatureEnabled(8)) {
            LogUtils.LOGD(TAG, "stopReconnectionService()");
            Context applicationContext = this.mContext.getApplicationContext();
            Intent intent = new Intent(applicationContext, ReconnectionService.class);
            intent.setPackage(applicationContext.getPackageName());
            applicationContext.stopService(intent);
        }
    }

    public boolean isAnyRouteAvailable() {
        return this.mMediaRouterCallback.isRouteAvailable();
    }

    public CastConfiguration getCastConfiguration() {
        return this.mCastConfiguration;
    }

    public MediaRouter getMediaRouter() {
        return this.mMediaRouter;
    }
}
