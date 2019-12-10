package com.google.android.exoplayer2.drm;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.drm.DefaultDrmSession.ProvisioningManager;
import com.google.android.exoplayer2.drm.DrmInitData.SchemeData;
import com.google.android.exoplayer2.drm.DrmSession.DrmSessionException;
import com.google.android.exoplayer2.drm.ExoMediaDrm.OnEventListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.EventDispatcher;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@TargetApi(18)
public class DefaultDrmSessionManager<T extends ExoMediaCrypto> implements DrmSessionManager<T>, ProvisioningManager<T> {
    public static final int INITIAL_DRM_REQUEST_RETRY_COUNT = 3;
    public static final int MODE_DOWNLOAD = 2;
    public static final int MODE_PLAYBACK = 0;
    public static final int MODE_QUERY = 1;
    public static final int MODE_RELEASE = 3;
    public static final String PLAYREADY_CUSTOM_DATA_KEY = "PRCustomData";
    private static final String TAG = "DefaultDrmSessionMgr";
    private final MediaDrmCallback callback;
    private final EventDispatcher<DefaultDrmSessionEventListener> eventDispatcher;
    private final int initialDrmRequestRetryCount;
    private final ExoMediaDrm<T> mediaDrm;
    volatile MediaDrmHandler mediaDrmHandler;
    private int mode;
    private final boolean multiSession;
    private byte[] offlineLicenseKeySetId;
    private final HashMap<String, String> optionalKeyRequestParameters;
    private Looper playbackLooper;
    private final List<DefaultDrmSession<T>> provisioningSessions;
    private final List<DefaultDrmSession<T>> sessions;
    private final UUID uuid;

    @SuppressLint({"HandlerLeak"})
    private class MediaDrmHandler extends Handler {
        public MediaDrmHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            byte[] bArr = (byte[]) message.obj;
            for (DefaultDrmSession defaultDrmSession : DefaultDrmSessionManager.this.sessions) {
                if (defaultDrmSession.hasSessionId(bArr)) {
                    defaultDrmSession.onMediaDrmEvent(message.what);
                    return;
                }
            }
        }
    }

    public static final class MissingSchemeDataException extends Exception {
        private MissingSchemeDataException(UUID uuid) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Media does not support uuid: ");
            stringBuilder.append(uuid);
            super(stringBuilder.toString());
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    @Deprecated
    public interface EventListener extends DefaultDrmSessionEventListener {
    }

    private class MediaDrmEventListener implements OnEventListener<T> {
        private MediaDrmEventListener() {
        }

        public void onEvent(ExoMediaDrm<? extends T> exoMediaDrm, byte[] bArr, int i, int i2, byte[] bArr2) {
            if (DefaultDrmSessionManager.this.mode == null) {
                DefaultDrmSessionManager.this.mediaDrmHandler.obtainMessage(i, bArr).sendToTarget();
            }
        }
    }

    @Deprecated
    public static DefaultDrmSessionManager<FrameworkMediaCrypto> newWidevineInstance(MediaDrmCallback mediaDrmCallback, HashMap<String, String> hashMap, Handler handler, DefaultDrmSessionEventListener defaultDrmSessionEventListener) throws UnsupportedDrmException {
        mediaDrmCallback = newWidevineInstance(mediaDrmCallback, hashMap);
        if (!(handler == null || defaultDrmSessionEventListener == null)) {
            mediaDrmCallback.addListener(handler, defaultDrmSessionEventListener);
        }
        return mediaDrmCallback;
    }

    public static DefaultDrmSessionManager<FrameworkMediaCrypto> newWidevineInstance(MediaDrmCallback mediaDrmCallback, HashMap<String, String> hashMap) throws UnsupportedDrmException {
        return newFrameworkInstance(C.WIDEVINE_UUID, mediaDrmCallback, hashMap);
    }

    @Deprecated
    public static DefaultDrmSessionManager<FrameworkMediaCrypto> newPlayReadyInstance(MediaDrmCallback mediaDrmCallback, String str, Handler handler, DefaultDrmSessionEventListener defaultDrmSessionEventListener) throws UnsupportedDrmException {
        mediaDrmCallback = newPlayReadyInstance(mediaDrmCallback, str);
        if (!(handler == null || defaultDrmSessionEventListener == null)) {
            mediaDrmCallback.addListener(handler, defaultDrmSessionEventListener);
        }
        return mediaDrmCallback;
    }

    public static DefaultDrmSessionManager<FrameworkMediaCrypto> newPlayReadyInstance(MediaDrmCallback mediaDrmCallback, String str) throws UnsupportedDrmException {
        HashMap hashMap;
        if (TextUtils.isEmpty(str)) {
            hashMap = null;
        } else {
            hashMap = new HashMap();
            hashMap.put(PLAYREADY_CUSTOM_DATA_KEY, str);
        }
        return newFrameworkInstance(C.PLAYREADY_UUID, mediaDrmCallback, hashMap);
    }

    @Deprecated
    public static DefaultDrmSessionManager<FrameworkMediaCrypto> newFrameworkInstance(UUID uuid, MediaDrmCallback mediaDrmCallback, HashMap<String, String> hashMap, Handler handler, DefaultDrmSessionEventListener defaultDrmSessionEventListener) throws UnsupportedDrmException {
        uuid = newFrameworkInstance(uuid, mediaDrmCallback, hashMap);
        if (!(handler == null || defaultDrmSessionEventListener == null)) {
            uuid.addListener(handler, defaultDrmSessionEventListener);
        }
        return uuid;
    }

    public static DefaultDrmSessionManager<FrameworkMediaCrypto> newFrameworkInstance(UUID uuid, MediaDrmCallback mediaDrmCallback, HashMap<String, String> hashMap) throws UnsupportedDrmException {
        return new DefaultDrmSessionManager(uuid, FrameworkMediaDrm.newInstance(uuid), mediaDrmCallback, (HashMap) hashMap, false, 3);
    }

    @Deprecated
    public DefaultDrmSessionManager(UUID uuid, ExoMediaDrm<T> exoMediaDrm, MediaDrmCallback mediaDrmCallback, HashMap<String, String> hashMap, Handler handler, DefaultDrmSessionEventListener defaultDrmSessionEventListener) {
        this(uuid, exoMediaDrm, mediaDrmCallback, hashMap);
        if (handler != null && defaultDrmSessionEventListener != null) {
            addListener(handler, defaultDrmSessionEventListener);
        }
    }

    public DefaultDrmSessionManager(UUID uuid, ExoMediaDrm<T> exoMediaDrm, MediaDrmCallback mediaDrmCallback, HashMap<String, String> hashMap) {
        this(uuid, (ExoMediaDrm) exoMediaDrm, mediaDrmCallback, (HashMap) hashMap, false, 3);
    }

    @Deprecated
    public DefaultDrmSessionManager(UUID uuid, ExoMediaDrm<T> exoMediaDrm, MediaDrmCallback mediaDrmCallback, HashMap<String, String> hashMap, Handler handler, DefaultDrmSessionEventListener defaultDrmSessionEventListener, boolean z) {
        this(uuid, exoMediaDrm, mediaDrmCallback, hashMap, z);
        if (handler != null && defaultDrmSessionEventListener != null) {
            addListener(handler, defaultDrmSessionEventListener);
        }
    }

    public DefaultDrmSessionManager(UUID uuid, ExoMediaDrm<T> exoMediaDrm, MediaDrmCallback mediaDrmCallback, HashMap<String, String> hashMap, boolean z) {
        this(uuid, (ExoMediaDrm) exoMediaDrm, mediaDrmCallback, (HashMap) hashMap, z, 3);
    }

    @Deprecated
    public DefaultDrmSessionManager(UUID uuid, ExoMediaDrm<T> exoMediaDrm, MediaDrmCallback mediaDrmCallback, HashMap<String, String> hashMap, Handler handler, DefaultDrmSessionEventListener defaultDrmSessionEventListener, boolean z, int i) {
        this(uuid, (ExoMediaDrm) exoMediaDrm, mediaDrmCallback, (HashMap) hashMap, z, i);
        if (handler != null && defaultDrmSessionEventListener != null) {
            addListener(handler, defaultDrmSessionEventListener);
        }
    }

    public DefaultDrmSessionManager(UUID uuid, ExoMediaDrm<T> exoMediaDrm, MediaDrmCallback mediaDrmCallback, HashMap<String, String> hashMap, boolean z, int i) {
        Assertions.checkNotNull(uuid);
        Assertions.checkNotNull(exoMediaDrm);
        Assertions.checkArgument(C.COMMON_PSSH_UUID.equals(uuid) ^ 1, "Use C.CLEARKEY_UUID instead");
        this.uuid = uuid;
        this.mediaDrm = exoMediaDrm;
        this.callback = mediaDrmCallback;
        this.optionalKeyRequestParameters = hashMap;
        this.eventDispatcher = new EventDispatcher();
        this.multiSession = z;
        this.initialDrmRequestRetryCount = i;
        this.mode = null;
        this.sessions = new ArrayList();
        this.provisioningSessions = new ArrayList();
        if (z && C.WIDEVINE_UUID.equals(uuid) != null && Util.SDK_INT >= 19) {
            exoMediaDrm.setPropertyString("sessionSharing", "enable");
        }
        exoMediaDrm.setOnEventListener(new MediaDrmEventListener());
    }

    public final void addListener(Handler handler, DefaultDrmSessionEventListener defaultDrmSessionEventListener) {
        this.eventDispatcher.addListener(handler, defaultDrmSessionEventListener);
    }

    public final void removeListener(DefaultDrmSessionEventListener defaultDrmSessionEventListener) {
        this.eventDispatcher.removeListener(defaultDrmSessionEventListener);
    }

    public final String getPropertyString(String str) {
        return this.mediaDrm.getPropertyString(str);
    }

    public final void setPropertyString(String str, String str2) {
        this.mediaDrm.setPropertyString(str, str2);
    }

    public final byte[] getPropertyByteArray(String str) {
        return this.mediaDrm.getPropertyByteArray(str);
    }

    public final void setPropertyByteArray(String str, byte[] bArr) {
        this.mediaDrm.setPropertyByteArray(str, bArr);
    }

    public void setMode(int i, byte[] bArr) {
        Assertions.checkState(this.sessions.isEmpty());
        if (i == 1 || i == 3) {
            Assertions.checkNotNull(bArr);
        }
        this.mode = i;
        this.offlineLicenseKeySetId = bArr;
    }

    public boolean canAcquireSession(@NonNull DrmInitData drmInitData) {
        boolean z = true;
        if (this.offlineLicenseKeySetId != null) {
            return true;
        }
        if (getSchemeDatas(drmInitData, this.uuid, true).isEmpty()) {
            if (drmInitData.schemeDataCount != 1 || !drmInitData.get(0).matches(C.COMMON_PSSH_UUID)) {
                return false;
            }
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("DrmInitData only contains common PSSH SchemeData. Assuming support for: ");
            stringBuilder.append(this.uuid);
            Log.w(str, stringBuilder.toString());
        }
        drmInitData = drmInitData.schemeType;
        if (drmInitData != null) {
            if (!C.CENC_TYPE_cenc.equals(drmInitData)) {
                if (!(C.CENC_TYPE_cbc1.equals(drmInitData) || C.CENC_TYPE_cbcs.equals(drmInitData))) {
                    if (C.CENC_TYPE_cens.equals(drmInitData) == null) {
                        return true;
                    }
                }
                if (Util.SDK_INT < 25) {
                    z = false;
                }
                return z;
            }
        }
        return true;
    }

    public DrmSession<T> acquireSession(Looper looper, DrmInitData drmInitData) {
        boolean z;
        AnonymousClass1 anonymousClass1;
        List list;
        if (this.playbackLooper != null) {
            if (this.playbackLooper != looper) {
                z = false;
                Assertions.checkState(z);
                if (this.sessions.isEmpty()) {
                    this.playbackLooper = looper;
                    if (this.mediaDrmHandler == null) {
                        this.mediaDrmHandler = new MediaDrmHandler(looper);
                    }
                }
                anonymousClass1 = null;
                if (this.offlineLicenseKeySetId != null) {
                    drmInitData = getSchemeDatas(drmInitData, this.uuid, false);
                    if (drmInitData.isEmpty()) {
                        list = drmInitData;
                    } else {
                        looper = new MissingSchemeDataException(this.uuid);
                        this.eventDispatcher.dispatch(new -$$Lambda$DefaultDrmSessionManager$lsU4S5fVqixyNsHyDBIvI3jEzVc(looper));
                        return new ErrorStateDrmSession(new DrmSessionException(looper));
                    }
                }
                list = null;
                if (this.multiSession == null) {
                    for (DefaultDrmSession defaultDrmSession : this.sessions) {
                        if (Util.areEqual(defaultDrmSession.schemeDatas, list)) {
                            anonymousClass1 = defaultDrmSession;
                            break;
                        }
                    }
                } else if (this.sessions.isEmpty() != null) {
                    anonymousClass1 = (DefaultDrmSession) this.sessions.get(0);
                }
                if (anonymousClass1 != null) {
                    DrmInitData defaultDrmSession2 = new DefaultDrmSession(this.uuid, this.mediaDrm, this, list, this.mode, this.offlineLicenseKeySetId, this.optionalKeyRequestParameters, this.callback, looper, this.eventDispatcher, this.initialDrmRequestRetryCount);
                    this.sessions.add(defaultDrmSession2);
                } else {
                    drmInitData = anonymousClass1;
                }
                drmInitData.acquire();
                return drmInitData;
            }
        }
        z = true;
        Assertions.checkState(z);
        if (this.sessions.isEmpty()) {
            this.playbackLooper = looper;
            if (this.mediaDrmHandler == null) {
                this.mediaDrmHandler = new MediaDrmHandler(looper);
            }
        }
        anonymousClass1 = null;
        if (this.offlineLicenseKeySetId != null) {
            list = null;
        } else {
            drmInitData = getSchemeDatas(drmInitData, this.uuid, false);
            if (drmInitData.isEmpty()) {
                list = drmInitData;
            } else {
                looper = new MissingSchemeDataException(this.uuid);
                this.eventDispatcher.dispatch(new -$$Lambda$DefaultDrmSessionManager$lsU4S5fVqixyNsHyDBIvI3jEzVc(looper));
                return new ErrorStateDrmSession(new DrmSessionException(looper));
            }
        }
        if (this.multiSession == null) {
            for (DefaultDrmSession defaultDrmSession3 : this.sessions) {
                if (Util.areEqual(defaultDrmSession3.schemeDatas, list)) {
                    anonymousClass1 = defaultDrmSession3;
                    break;
                }
            }
        } else if (this.sessions.isEmpty() != null) {
            anonymousClass1 = (DefaultDrmSession) this.sessions.get(0);
        }
        if (anonymousClass1 != null) {
            drmInitData = anonymousClass1;
        } else {
            DrmInitData defaultDrmSession22 = new DefaultDrmSession(this.uuid, this.mediaDrm, this, list, this.mode, this.offlineLicenseKeySetId, this.optionalKeyRequestParameters, this.callback, looper, this.eventDispatcher, this.initialDrmRequestRetryCount);
            this.sessions.add(defaultDrmSession22);
        }
        drmInitData.acquire();
        return drmInitData;
    }

    public void releaseSession(DrmSession<T> drmSession) {
        if (!(drmSession instanceof ErrorStateDrmSession)) {
            DefaultDrmSession defaultDrmSession = (DefaultDrmSession) drmSession;
            if (defaultDrmSession.release()) {
                this.sessions.remove(defaultDrmSession);
                if (this.provisioningSessions.size() > 1 && this.provisioningSessions.get(0) == defaultDrmSession) {
                    ((DefaultDrmSession) this.provisioningSessions.get(1)).provision();
                }
                this.provisioningSessions.remove(defaultDrmSession);
            }
        }
    }

    public void provisionRequired(DefaultDrmSession<T> defaultDrmSession) {
        this.provisioningSessions.add(defaultDrmSession);
        if (this.provisioningSessions.size() == 1) {
            defaultDrmSession.provision();
        }
    }

    public void onProvisionCompleted() {
        for (DefaultDrmSession onProvisionCompleted : this.provisioningSessions) {
            onProvisionCompleted.onProvisionCompleted();
        }
        this.provisioningSessions.clear();
    }

    public void onProvisionError(Exception exception) {
        for (DefaultDrmSession onProvisionError : this.provisioningSessions) {
            onProvisionError.onProvisionError(exception);
        }
        this.provisioningSessions.clear();
    }

    private static List<SchemeData> getSchemeDatas(DrmInitData drmInitData, UUID uuid, boolean z) {
        List<SchemeData> arrayList = new ArrayList(drmInitData.schemeDataCount);
        for (int i = 0; i < drmInitData.schemeDataCount; i++) {
            Object obj;
            SchemeData schemeData = drmInitData.get(i);
            if (!schemeData.matches(uuid)) {
                if (!C.CLEARKEY_UUID.equals(uuid) || !schemeData.matches(C.COMMON_PSSH_UUID)) {
                    obj = null;
                    if (obj != null && (schemeData.data != null || z)) {
                        arrayList.add(schemeData);
                    }
                }
            }
            obj = 1;
            arrayList.add(schemeData);
        }
        return arrayList;
    }
}
