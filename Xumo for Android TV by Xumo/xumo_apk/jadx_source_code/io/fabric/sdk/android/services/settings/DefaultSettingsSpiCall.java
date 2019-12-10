package io.fabric.sdk.android.services.settings;

import androidx.recyclerview.widget.ItemTouchHelper.Callback;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequest.HttpRequestException;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

class DefaultSettingsSpiCall extends AbstractSpiCall implements SettingsSpiCall {
    static final String BUILD_VERSION_PARAM = "build_version";
    static final String DISPLAY_VERSION_PARAM = "display_version";
    static final String HEADER_DEVICE_MODEL = "X-CRASHLYTICS-DEVICE-MODEL";
    static final String HEADER_INSTALLATION_ID = "X-CRASHLYTICS-INSTALLATION-ID";
    static final String HEADER_OS_BUILD_VERSION = "X-CRASHLYTICS-OS-BUILD-VERSION";
    static final String HEADER_OS_DISPLAY_VERSION = "X-CRASHLYTICS-OS-DISPLAY-VERSION";
    static final String ICON_HASH = "icon_hash";
    static final String INSTANCE_PARAM = "instance";
    static final String SOURCE_PARAM = "source";

    boolean requestWasSuccessful(int i) {
        if (!(i == Callback.DEFAULT_DRAG_ANIMATION_DURATION || i == 201 || i == 202)) {
            if (i != 203) {
                return false;
            }
        }
        return true;
    }

    public DefaultSettingsSpiCall(Kit kit, String str, String str2, HttpRequestFactory httpRequestFactory) {
        this(kit, str, str2, httpRequestFactory, HttpMethod.GET);
    }

    DefaultSettingsSpiCall(Kit kit, String str, String str2, HttpRequestFactory httpRequestFactory, HttpMethod httpMethod) {
        super(kit, str, str2, httpRequestFactory, httpMethod);
    }

    public JSONObject invoke(SettingsRequest settingsRequest) {
        Throwable e;
        Logger logger;
        Throwable th;
        StringBuilder stringBuilder;
        String str;
        StringBuilder stringBuilder2;
        try {
            Map queryParamsFor = getQueryParamsFor(settingsRequest);
            HttpRequest httpRequest = getHttpRequest(queryParamsFor);
            try {
                settingsRequest = applyHeadersTo(httpRequest, settingsRequest);
                try {
                    Logger logger2 = Fabric.getLogger();
                    String str2 = Fabric.TAG;
                    StringBuilder stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("Requesting settings from ");
                    stringBuilder3.append(getUrl());
                    logger2.d(str2, stringBuilder3.toString());
                    logger2 = Fabric.getLogger();
                    str2 = Fabric.TAG;
                    stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("Settings query params were: ");
                    stringBuilder3.append(queryParamsFor);
                    logger2.d(str2, stringBuilder3.toString());
                    JSONObject handleResponse = handleResponse(settingsRequest);
                    if (settingsRequest != null) {
                        Logger logger3 = Fabric.getLogger();
                        str = Fabric.TAG;
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("Settings request ID: ");
                        stringBuilder2.append(settingsRequest.header(AbstractSpiCall.HEADER_REQUEST_ID));
                        logger3.d(str, stringBuilder2.toString());
                    }
                    return handleResponse;
                } catch (HttpRequestException e2) {
                    e = e2;
                    try {
                        Fabric.getLogger().e(Fabric.TAG, "Settings request failed.", e);
                        if (settingsRequest != null) {
                            return null;
                        }
                        logger = Fabric.getLogger();
                        str = Fabric.TAG;
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("Settings request ID: ");
                        stringBuilder2.append(settingsRequest.header(AbstractSpiCall.HEADER_REQUEST_ID));
                        logger.d(str, stringBuilder2.toString());
                        return null;
                    } catch (Throwable th2) {
                        th = th2;
                        if (settingsRequest != null) {
                            logger = Fabric.getLogger();
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("Settings request ID: ");
                            stringBuilder.append(settingsRequest.header(AbstractSpiCall.HEADER_REQUEST_ID));
                            logger.d(Fabric.TAG, stringBuilder.toString());
                        }
                        throw th;
                    }
                }
            } catch (HttpRequestException e3) {
                e = e3;
                settingsRequest = httpRequest;
                Fabric.getLogger().e(Fabric.TAG, "Settings request failed.", e);
                if (settingsRequest != null) {
                    return null;
                }
                logger = Fabric.getLogger();
                str = Fabric.TAG;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Settings request ID: ");
                stringBuilder2.append(settingsRequest.header(AbstractSpiCall.HEADER_REQUEST_ID));
                logger.d(str, stringBuilder2.toString());
                return null;
            } catch (Throwable th3) {
                th = th3;
                settingsRequest = httpRequest;
                if (settingsRequest != null) {
                    logger = Fabric.getLogger();
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Settings request ID: ");
                    stringBuilder.append(settingsRequest.header(AbstractSpiCall.HEADER_REQUEST_ID));
                    logger.d(Fabric.TAG, stringBuilder.toString());
                }
                throw th;
            }
        } catch (HttpRequestException e4) {
            e = e4;
            settingsRequest = null;
            Fabric.getLogger().e(Fabric.TAG, "Settings request failed.", e);
            if (settingsRequest != null) {
                return null;
            }
            logger = Fabric.getLogger();
            str = Fabric.TAG;
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Settings request ID: ");
            stringBuilder2.append(settingsRequest.header(AbstractSpiCall.HEADER_REQUEST_ID));
            logger.d(str, stringBuilder2.toString());
            return null;
        } catch (SettingsRequest settingsRequest2) {
            th = settingsRequest2;
            settingsRequest2 = null;
            if (settingsRequest2 != null) {
                logger = Fabric.getLogger();
                stringBuilder = new StringBuilder();
                stringBuilder.append("Settings request ID: ");
                stringBuilder.append(settingsRequest2.header(AbstractSpiCall.HEADER_REQUEST_ID));
                logger.d(Fabric.TAG, stringBuilder.toString());
            }
            throw th;
        }
    }

    JSONObject handleResponse(HttpRequest httpRequest) {
        int code = httpRequest.code();
        Logger logger = Fabric.getLogger();
        String str = Fabric.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Settings result was: ");
        stringBuilder.append(code);
        logger.d(str, stringBuilder.toString());
        if (requestWasSuccessful(code)) {
            return getJsonObjectFrom(httpRequest.body());
        }
        httpRequest = Fabric.getLogger();
        String str2 = Fabric.TAG;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Failed to retrieve settings from ");
        stringBuilder2.append(getUrl());
        httpRequest.e(str2, stringBuilder2.toString());
        return null;
    }

    private JSONObject getJsonObjectFrom(String str) {
        try {
            return new JSONObject(str);
        } catch (Throwable e) {
            Logger logger = Fabric.getLogger();
            String str2 = Fabric.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to parse settings JSON from ");
            stringBuilder.append(getUrl());
            logger.d(str2, stringBuilder.toString(), e);
            Logger logger2 = Fabric.getLogger();
            String str3 = Fabric.TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Settings response ");
            stringBuilder2.append(str);
            logger2.d(str3, stringBuilder2.toString());
            return null;
        }
    }

    private Map<String, String> getQueryParamsFor(SettingsRequest settingsRequest) {
        Map<String, String> hashMap = new HashMap();
        hashMap.put(BUILD_VERSION_PARAM, settingsRequest.buildVersion);
        hashMap.put(DISPLAY_VERSION_PARAM, settingsRequest.displayVersion);
        hashMap.put("source", Integer.toString(settingsRequest.source));
        if (settingsRequest.iconHash != null) {
            hashMap.put(ICON_HASH, settingsRequest.iconHash);
        }
        settingsRequest = settingsRequest.instanceId;
        if (!CommonUtils.isNullOrEmpty(settingsRequest)) {
            hashMap.put(INSTANCE_PARAM, settingsRequest);
        }
        return hashMap;
    }

    private HttpRequest applyHeadersTo(HttpRequest httpRequest, SettingsRequest settingsRequest) {
        applyNonNullHeader(httpRequest, AbstractSpiCall.HEADER_API_KEY, settingsRequest.apiKey);
        applyNonNullHeader(httpRequest, AbstractSpiCall.HEADER_CLIENT_TYPE, "android");
        applyNonNullHeader(httpRequest, AbstractSpiCall.HEADER_CLIENT_VERSION, this.kit.getVersion());
        applyNonNullHeader(httpRequest, "Accept", "application/json");
        applyNonNullHeader(httpRequest, HEADER_DEVICE_MODEL, settingsRequest.deviceModel);
        applyNonNullHeader(httpRequest, HEADER_OS_BUILD_VERSION, settingsRequest.osBuildVersion);
        applyNonNullHeader(httpRequest, HEADER_OS_DISPLAY_VERSION, settingsRequest.osDisplayVersion);
        applyNonNullHeader(httpRequest, HEADER_INSTALLATION_ID, settingsRequest.installationId);
        return httpRequest;
    }

    private void applyNonNullHeader(HttpRequest httpRequest, String str, String str2) {
        if (str2 != null) {
            httpRequest.header(str, str2);
        }
    }
}
