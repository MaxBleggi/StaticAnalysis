package com.google.ads.interactivemedia.v3.api;

import androidx.core.view.PointerIconCompat;

/* compiled from: IMASDK */
public final class AdError extends Exception {
    private final AdErrorCode a;
    private final AdErrorType b;

    /* compiled from: IMASDK */
    public enum AdErrorCode {
        INTERNAL_ERROR(-1),
        VAST_MALFORMED_RESPONSE(100),
        UNKNOWN_AD_RESPONSE(PointerIconCompat.TYPE_ALIAS),
        VAST_LOAD_TIMEOUT(301),
        VAST_TOO_MANY_REDIRECTS(302),
        VIDEO_PLAY_ERROR(400),
        VAST_MEDIA_LOAD_TIMEOUT(402),
        VAST_LINEAR_ASSET_MISMATCH(403),
        OVERLAY_AD_PLAYING_FAILED(500),
        OVERLAY_AD_LOADING_FAILED(502),
        VAST_NONLINEAR_ASSET_MISMATCH(503),
        COMPANION_AD_LOADING_FAILED(603),
        UNKNOWN_ERROR(900),
        VAST_EMPTY_RESPONSE(PointerIconCompat.TYPE_VERTICAL_TEXT),
        FAILED_TO_REQUEST_ADS(1005),
        VAST_ASSET_NOT_FOUND(PointerIconCompat.TYPE_CROSSHAIR),
        ADS_REQUEST_NETWORK_ERROR(PointerIconCompat.TYPE_NO_DROP),
        INVALID_ARGUMENTS(1101),
        PLAYLIST_NO_CONTENT_TRACKING(1205);
        
        private final int a;

        private AdErrorCode(int i) {
            this.a = i;
        }

        public int getErrorNumber() {
            return this.a;
        }

        static AdErrorCode a(int i) {
            for (AdErrorCode adErrorCode : values()) {
                if (adErrorCode.getErrorNumber() == i) {
                    return adErrorCode;
                }
            }
            if (1204 == i) {
                return INTERNAL_ERROR;
            }
            return UNKNOWN_ERROR;
        }

        public boolean equals(int i) {
            return this.a == i;
        }

        public String toString() {
            String name = name();
            int i = this.a;
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(name).length() + 41);
            stringBuilder.append("AdErrorCode [name: ");
            stringBuilder.append(name);
            stringBuilder.append(", number: ");
            stringBuilder.append(i);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    /* compiled from: IMASDK */
    public enum AdErrorType {
        LOAD,
        PLAY
    }

    public AdError(AdErrorType adErrorType, int i, String str) {
        this(adErrorType, AdErrorCode.a(i), str);
    }

    public AdError(AdErrorType adErrorType, AdErrorCode adErrorCode, String str) {
        super(str);
        this.b = adErrorType;
        this.a = adErrorCode;
    }

    public AdErrorType getErrorType() {
        return this.b;
    }

    public AdErrorCode getErrorCode() {
        return this.a;
    }

    public int getErrorCodeNumber() {
        return this.a.getErrorNumber();
    }

    public String getMessage() {
        return super.getMessage();
    }

    public String toString() {
        String valueOf = String.valueOf(this.b);
        String valueOf2 = String.valueOf(this.a);
        String message = getMessage();
        StringBuilder stringBuilder = new StringBuilder(((String.valueOf(valueOf).length() + 45) + String.valueOf(valueOf2).length()) + String.valueOf(message).length());
        stringBuilder.append("AdError [errorType: ");
        stringBuilder.append(valueOf);
        stringBuilder.append(", errorCode: ");
        stringBuilder.append(valueOf2);
        stringBuilder.append(", message: ");
        stringBuilder.append(message);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
