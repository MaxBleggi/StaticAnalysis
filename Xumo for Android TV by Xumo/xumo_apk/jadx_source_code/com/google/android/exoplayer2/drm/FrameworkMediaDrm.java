package com.google.android.exoplayer2.drm;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.media.DeniedByServerException;
import android.media.MediaCrypto;
import android.media.MediaCryptoException;
import android.media.MediaDrm;
import android.media.MediaDrm.KeyStatus;
import android.media.MediaDrmException;
import android.media.NotProvisionedException;
import android.media.UnsupportedSchemeException;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.drm.DrmInitData.SchemeData;
import com.google.android.exoplayer2.drm.ExoMediaDrm.KeyRequest;
import com.google.android.exoplayer2.drm.ExoMediaDrm.OnEventListener;
import com.google.android.exoplayer2.drm.ExoMediaDrm.OnKeyStatusChangeListener;
import com.google.android.exoplayer2.drm.ExoMediaDrm.ProvisionRequest;
import com.google.android.exoplayer2.extractor.mp4.PsshAtomUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@TargetApi(23)
public final class FrameworkMediaDrm implements ExoMediaDrm<FrameworkMediaCrypto> {
    private static final String CENC_SCHEME_MIME_TYPE = "cenc";
    private final MediaDrm mediaDrm;
    private final UUID uuid;

    public static FrameworkMediaDrm newInstance(UUID uuid) throws UnsupportedDrmException {
        try {
            return new FrameworkMediaDrm(uuid);
        } catch (UUID uuid2) {
            throw new UnsupportedDrmException(1, uuid2);
        } catch (UUID uuid22) {
            throw new UnsupportedDrmException(2, uuid22);
        }
    }

    private FrameworkMediaDrm(UUID uuid) throws UnsupportedSchemeException {
        Assertions.checkNotNull(uuid);
        Assertions.checkArgument(C.COMMON_PSSH_UUID.equals(uuid) ^ 1, "Use C.CLEARKEY_UUID instead");
        this.uuid = uuid;
        UUID uuid2 = (Util.SDK_INT >= 27 || !C.CLEARKEY_UUID.equals(uuid)) ? uuid : C.COMMON_PSSH_UUID;
        this.mediaDrm = new MediaDrm(uuid2);
        if (C.WIDEVINE_UUID.equals(uuid) != null && needsForceWidevineL3Workaround() != null) {
            forceWidevineL3(this.mediaDrm);
        }
    }

    public void setOnEventListener(OnEventListener<? super FrameworkMediaCrypto> onEventListener) {
        this.mediaDrm.setOnEventListener(onEventListener == null ? null : new -$$Lambda$FrameworkMediaDrm$zJ3h9UKP9ayPF2iQATh7r7bKJes(this, onEventListener));
    }

    public void setOnKeyStatusChangeListener(OnKeyStatusChangeListener<? super FrameworkMediaCrypto> onKeyStatusChangeListener) {
        if (Util.SDK_INT >= 23) {
            this.mediaDrm.setOnKeyStatusChangeListener(onKeyStatusChangeListener == null ? null : new -$$Lambda$FrameworkMediaDrm$WcqXRf-ZlBuRYiaqpRgpL0-wRvg(this, onKeyStatusChangeListener), null);
            return;
        }
        throw new UnsupportedOperationException();
    }

    public static /* synthetic */ void lambda$setOnKeyStatusChangeListener$1(FrameworkMediaDrm frameworkMediaDrm, OnKeyStatusChangeListener onKeyStatusChangeListener, MediaDrm mediaDrm, byte[] bArr, List list, boolean z) {
        mediaDrm = new ArrayList();
        for (KeyStatus keyStatus : list) {
            mediaDrm.add(new ExoMediaDrm.KeyStatus(keyStatus.getStatusCode(), keyStatus.getKeyId()));
        }
        onKeyStatusChangeListener.onKeyStatusChange(frameworkMediaDrm, bArr, mediaDrm, z);
    }

    public byte[] openSession() throws MediaDrmException {
        return this.mediaDrm.openSession();
    }

    public void closeSession(byte[] bArr) {
        this.mediaDrm.closeSession(bArr);
    }

    public KeyRequest getKeyRequest(byte[] bArr, @Nullable List<SchemeData> list, int i, @Nullable HashMap<String, String> hashMap) throws NotProvisionedException {
        byte[] adjustRequestInitData;
        String adjustRequestMimeType;
        SchemeData schemeData = null;
        if (list != null) {
            schemeData = getSchemeData(this.uuid, list);
            adjustRequestInitData = adjustRequestInitData(this.uuid, schemeData.data);
            adjustRequestMimeType = adjustRequestMimeType(this.uuid, schemeData.mimeType);
        } else {
            adjustRequestInitData = null;
            adjustRequestMimeType = adjustRequestInitData;
        }
        bArr = this.mediaDrm.getKeyRequest(bArr, adjustRequestInitData, adjustRequestMimeType, i, hashMap);
        list = adjustRequestData(this.uuid, bArr.getData());
        bArr = bArr.getDefaultUrl();
        if (!(TextUtils.isEmpty(bArr) == 0 || schemeData == null || TextUtils.isEmpty(schemeData.licenseServerUrl) != 0)) {
            bArr = schemeData.licenseServerUrl;
        }
        return new KeyRequest(list, bArr);
    }

    public byte[] provideKeyResponse(byte[] bArr, byte[] bArr2) throws NotProvisionedException, DeniedByServerException {
        if (C.CLEARKEY_UUID.equals(this.uuid)) {
            bArr2 = ClearKeyUtil.adjustResponseData(bArr2);
        }
        return this.mediaDrm.provideKeyResponse(bArr, bArr2);
    }

    public ProvisionRequest getProvisionRequest() {
        MediaDrm.ProvisionRequest provisionRequest = this.mediaDrm.getProvisionRequest();
        return new ProvisionRequest(provisionRequest.getData(), provisionRequest.getDefaultUrl());
    }

    public void provideProvisionResponse(byte[] bArr) throws DeniedByServerException {
        this.mediaDrm.provideProvisionResponse(bArr);
    }

    public Map<String, String> queryKeyStatus(byte[] bArr) {
        return this.mediaDrm.queryKeyStatus(bArr);
    }

    public void release() {
        this.mediaDrm.release();
    }

    public void restoreKeys(byte[] bArr, byte[] bArr2) {
        this.mediaDrm.restoreKeys(bArr, bArr2);
    }

    public String getPropertyString(String str) {
        return this.mediaDrm.getPropertyString(str);
    }

    public byte[] getPropertyByteArray(String str) {
        return this.mediaDrm.getPropertyByteArray(str);
    }

    public void setPropertyString(String str, String str2) {
        this.mediaDrm.setPropertyString(str, str2);
    }

    public void setPropertyByteArray(String str, byte[] bArr) {
        this.mediaDrm.setPropertyByteArray(str, bArr);
    }

    public FrameworkMediaCrypto createMediaCrypto(byte[] bArr) throws MediaCryptoException {
        boolean z = Util.SDK_INT < 21 && C.WIDEVINE_UUID.equals(this.uuid) && "L3".equals(getPropertyString("securityLevel"));
        return new FrameworkMediaCrypto(new MediaCrypto(this.uuid, bArr), z);
    }

    private static SchemeData getSchemeData(UUID uuid, List<SchemeData> list) {
        if (C.WIDEVINE_UUID.equals(uuid) == null) {
            return (SchemeData) list.get(0);
        }
        int i;
        if (Util.SDK_INT >= 28 && list.size() > 1) {
            SchemeData schemeData;
            Object obj;
            SchemeData schemeData2 = (SchemeData) list.get(0);
            i = 0;
            for (int i2 = 0; i2 < list.size(); i2++) {
                schemeData = (SchemeData) list.get(i2);
                if (schemeData.requiresSecureDecryption != schemeData2.requiresSecureDecryption || !Util.areEqual(schemeData.mimeType, schemeData2.mimeType) || !Util.areEqual(schemeData.licenseServerUrl, schemeData2.licenseServerUrl) || !PsshAtomUtil.isPsshAtom(schemeData.data)) {
                    obj = null;
                    break;
                }
                i += schemeData.data.length;
            }
            obj = 1;
            if (obj != null) {
                obj = new byte[i];
                i = 0;
                for (int i3 = 0; i3 < list.size(); i3++) {
                    schemeData = (SchemeData) list.get(i3);
                    int length = schemeData.data.length;
                    System.arraycopy(schemeData.data, 0, obj, i, length);
                    i += length;
                }
                return schemeData2.copyWithData(obj);
            }
        }
        for (uuid = null; uuid < list.size(); uuid++) {
            SchemeData schemeData3 = (SchemeData) list.get(uuid);
            i = PsshAtomUtil.parseVersion(schemeData3.data);
            if (Util.SDK_INT < 23 && i == 0) {
                return schemeData3;
            }
            if (Util.SDK_INT >= 23 && i == 1) {
                return schemeData3;
            }
        }
        return (SchemeData) list.get(0);
    }

    private static byte[] adjustRequestInitData(UUID uuid, byte[] bArr) {
        if ((Util.SDK_INT < 21 && C.WIDEVINE_UUID.equals(uuid)) || (C.PLAYREADY_UUID.equals(uuid) && "Amazon".equals(Util.MANUFACTURER) && ("AFTB".equals(Util.MODEL) || "AFTS".equals(Util.MODEL) || "AFTM".equals(Util.MODEL)))) {
            uuid = PsshAtomUtil.parseSchemeSpecificData(bArr, uuid);
            if (uuid != null) {
                return uuid;
            }
        }
        return bArr;
    }

    private static String adjustRequestMimeType(UUID uuid, String str) {
        return (Util.SDK_INT >= 26 || C.CLEARKEY_UUID.equals(uuid) == null || (MimeTypes.VIDEO_MP4.equals(str) == null && MimeTypes.AUDIO_MP4.equals(str) == null)) ? str : "cenc";
    }

    private static byte[] adjustRequestData(UUID uuid, byte[] bArr) {
        return C.CLEARKEY_UUID.equals(uuid) != null ? ClearKeyUtil.adjustRequestData(bArr) : bArr;
    }

    @SuppressLint({"WrongConstant"})
    private static void forceWidevineL3(MediaDrm mediaDrm) {
        mediaDrm.setPropertyString("securityLevel", "L3");
    }

    private static boolean needsForceWidevineL3Workaround() {
        return "ASUS_Z00AD".equals(Util.MODEL);
    }
}
