package com.google.android.libraries.cast.companionlibrary.cast;

import android.content.Context;
import android.text.TextUtils;
import androidx.mediarouter.media.MediaRouter.RouteInfo;
import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.cast.Cast.CastOptions;
import com.google.android.gms.cast.Cast.CastOptions.Builder;
import com.google.android.gms.cast.Cast.Listener;
import com.google.android.gms.cast.Cast.MessageReceivedCallback;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.CastStatusCodes;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.cast.companionlibrary.cast.callbacks.DataCastConsumer;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.NoConnectionException;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.TransientNetworkDisconnectionException;
import com.google.android.libraries.cast.companionlibrary.utils.LogUtils;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class DataCastManager extends BaseCastManager implements MessageReceivedCallback {
    private static final String TAG = LogUtils.makeLogTag(DataCastManager.class);
    private static DataCastManager sInstance;
    private final Set<DataCastConsumer> mDataConsumers = new CopyOnWriteArraySet();
    private final Set<String> mNamespaceList = new HashSet();

    class CastListener extends Listener {
        CastListener() {
        }

        public void onApplicationDisconnected(int i) {
            DataCastManager.this.onApplicationDisconnected(i);
        }

        public void onApplicationStatusChanged() {
            DataCastManager.this.onApplicationStatusChanged();
        }
    }

    private DataCastManager() {
    }

    public static synchronized DataCastManager initialize(Context context, CastConfiguration castConfiguration) {
        synchronized (DataCastManager.class) {
            if (sInstance == null) {
                LogUtils.LOGD(TAG, "New instance of DataCastManager is created");
                if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(context) == 0) {
                    sInstance = new DataCastManager(context, castConfiguration);
                } else {
                    context = "Couldn't find the appropriate version of Google Play Services";
                    LogUtils.LOGE(TAG, context);
                    throw new RuntimeException(context);
                }
            }
            context = sInstance;
        }
        return context;
    }

    protected DataCastManager(Context context, CastConfiguration castConfiguration) {
        super(context, castConfiguration);
        Context<String> namespaces = castConfiguration.getNamespaces();
        if (namespaces != null) {
            for (String str : namespaces) {
                if (TextUtils.isEmpty(str)) {
                    LogUtils.LOGD(TAG, "A null or empty namespace was ignored.");
                } else {
                    this.mNamespaceList.add(str);
                }
            }
        }
    }

    public static DataCastManager getInstance() {
        if (sInstance != null) {
            return sInstance;
        }
        String str = "No DataCastManager instance was found, did you forget to initialize it?";
        LogUtils.LOGE(TAG, str);
        throw new IllegalStateException(str);
    }

    public boolean addNamespace(String str) throws TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("namespace cannot be empty");
        } else if (this.mNamespaceList.contains(str)) {
            LogUtils.LOGD(TAG, "Ignoring to add a namespace that is already added.");
            return false;
        } else {
            try {
                Cast.CastApi.setMessageReceivedCallbacks(this.mApiClient, str, this);
                this.mNamespaceList.add(str);
                return true;
            } catch (Throwable e) {
                LogUtils.LOGE(TAG, String.format("addNamespace(%s)", new Object[]{str}), e);
                return false;
            }
        }
    }

    public boolean removeNamespace(String str) throws TransientNetworkDisconnectionException, NoConnectionException {
        checkConnectivity();
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("namespace cannot be empty");
        } else if (this.mNamespaceList.contains(str)) {
            try {
                Cast.CastApi.removeMessageReceivedCallbacks(this.mApiClient, str);
                this.mNamespaceList.remove(str);
                return true;
            } catch (Throwable e) {
                LogUtils.LOGE(TAG, String.format("removeNamespace(%s)", new Object[]{str}), e);
                return false;
            }
        } else {
            LogUtils.LOGD(TAG, "Ignoring to remove a namespace that is not registered.");
            return false;
        }
    }

    public void sendDataMessage(String str, String str2) throws IllegalArgumentException, IllegalStateException, IOException {
        checkConnectivity();
        if (TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("namespace cannot be empty");
        }
        Cast.CastApi.sendMessage(this.mApiClient, str2, str).setResultCallback(new ResultCallback<Status>() {
            public void onResult(Status status) {
                if (!status.isSuccess()) {
                    DataCastManager.this.onMessageSendFailed(status);
                }
            }
        });
    }

    protected void onDeviceUnselected() {
        detachDataChannels();
    }

    protected Builder getCastOptionBuilder(CastDevice castDevice) {
        castDevice = CastOptions.builder(this.mSelectedCastDevice, new CastListener());
        if (isFeatureEnabled(1)) {
            castDevice.setVerboseLoggingEnabled(true);
        }
        return castDevice;
    }

    public void onApplicationConnected(ApplicationMetadata applicationMetadata, String str, String str2, boolean z) {
        String str3 = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onApplicationConnected() reached with sessionId: ");
        stringBuilder.append(str2);
        LogUtils.LOGD(str3, stringBuilder.toString());
        this.mPreferenceAccessor.saveStringToPreference(BaseCastManager.PREFS_KEY_SESSION_ID, str2);
        if (this.mReconnectionStatus == 2) {
            List<RouteInfo> routes = this.mMediaRouter.getRoutes();
            if (routes != null) {
                String stringFromPreference = this.mPreferenceAccessor.getStringFromPreference(BaseCastManager.PREFS_KEY_ROUTE_ID);
                Object obj = null;
                for (RouteInfo routeInfo : routes) {
                    if (stringFromPreference.equals(routeInfo.getId())) {
                        LogUtils.LOGD(TAG, "Found the correct route during reconnection attempt");
                        obj = 1;
                        this.mReconnectionStatus = 3;
                        this.mMediaRouter.selectRoute(routeInfo);
                        break;
                    }
                }
                if (obj == null) {
                    onDeviceSelected(null, null);
                    this.mReconnectionStatus = 4;
                    return;
                }
            }
        }
        try {
            attachDataChannels();
            this.mSessionId = str2;
            for (DataCastConsumer onApplicationConnected : this.mDataConsumers) {
                onApplicationConnected.onApplicationConnected(applicationMetadata, str, str2, z);
            }
        } catch (ApplicationMetadata applicationMetadata2) {
            LogUtils.LOGE(TAG, "Failed to attach namespaces", applicationMetadata2);
        }
    }

    private void attachDataChannels() throws IllegalStateException, IOException {
        checkConnectivity();
        for (String messageReceivedCallbacks : this.mNamespaceList) {
            Cast.CastApi.setMessageReceivedCallbacks(this.mApiClient, messageReceivedCallbacks, this);
        }
    }

    private void detachDataChannels() {
        if (this.mApiClient != null) {
            for (String str : this.mNamespaceList) {
                try {
                    Cast.CastApi.removeMessageReceivedCallbacks(this.mApiClient, str);
                } catch (Throwable e) {
                    String str2 = TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("detachDataChannels() Failed to remove namespace: ");
                    stringBuilder.append(str);
                    LogUtils.LOGE(str2, stringBuilder.toString(), e);
                }
            }
        }
    }

    public void onApplicationConnectionFailed(int i) {
        if (this.mReconnectionStatus != 2) {
            for (DataCastConsumer onApplicationConnectionFailed : this.mDataConsumers) {
                onApplicationConnectionFailed.onApplicationConnectionFailed(i);
            }
            onDeviceSelected(null, null);
            if (this.mMediaRouter != 0) {
                LogUtils.LOGD(TAG, "onApplicationConnectionFailed(): Setting route to default");
                this.mMediaRouter.selectRoute(this.mMediaRouter.getDefaultRoute());
            }
        } else if (i == CastStatusCodes.APPLICATION_NOT_RUNNING) {
            this.mReconnectionStatus = 4;
            onDeviceSelected(null, null);
        }
    }

    public void onApplicationDisconnected(int i) {
        for (DataCastConsumer onApplicationDisconnected : this.mDataConsumers) {
            onApplicationDisconnected.onApplicationDisconnected(i);
        }
        if (this.mMediaRouter != 0) {
            this.mMediaRouter.selectRoute(this.mMediaRouter.getDefaultRoute());
        }
        onDeviceSelected(null, null);
    }

    public void onApplicationStatusChanged() {
        if (isConnected()) {
            try {
                String applicationStatus = Cast.CastApi.getApplicationStatus(this.mApiClient);
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onApplicationStatusChanged() reached: ");
                stringBuilder.append(applicationStatus);
                LogUtils.LOGD(str, stringBuilder.toString());
                for (DataCastConsumer onApplicationStatusChanged : this.mDataConsumers) {
                    onApplicationStatusChanged.onApplicationStatusChanged(applicationStatus);
                }
            } catch (Throwable e) {
                LogUtils.LOGE(TAG, "onApplicationStatusChanged(): Failed", e);
            }
        }
    }

    public void onApplicationStopFailed(int i) {
        for (DataCastConsumer onApplicationStopFailed : this.mDataConsumers) {
            onApplicationStopFailed.onApplicationStopFailed(i);
        }
    }

    public void onConnectivityRecovered() {
        try {
            attachDataChannels();
        } catch (Throwable e) {
            LogUtils.LOGE(TAG, "onConnectivityRecovered(): Failed to reattach data channels", e);
        }
        super.onConnectivityRecovered();
    }

    public void onMessageReceived(CastDevice castDevice, String str, String str2) {
        for (DataCastConsumer onMessageReceived : this.mDataConsumers) {
            onMessageReceived.onMessageReceived(castDevice, str, str2);
        }
    }

    public void onMessageSendFailed(Status status) {
        for (DataCastConsumer onMessageSendFailed : this.mDataConsumers) {
            onMessageSendFailed.onMessageSendFailed(status);
        }
    }

    public void addDataCastConsumer(DataCastConsumer dataCastConsumer) {
        if (dataCastConsumer != null) {
            addBaseCastConsumer(dataCastConsumer);
            String str;
            StringBuilder stringBuilder;
            if (this.mDataConsumers.add(dataCastConsumer)) {
                str = TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Successfully added the new DataCastConsumer listener ");
                stringBuilder.append(dataCastConsumer);
                LogUtils.LOGD(str, stringBuilder.toString());
                return;
            }
            str = TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Adding Listener ");
            stringBuilder.append(dataCastConsumer);
            stringBuilder.append(" was already registered, skipping this step");
            LogUtils.LOGD(str, stringBuilder.toString());
        }
    }

    public void removeDataCastConsumer(DataCastConsumer dataCastConsumer) {
        if (dataCastConsumer != null) {
            removeBaseCastConsumer(dataCastConsumer);
            this.mDataConsumers.remove(dataCastConsumer);
        }
    }
}
