package com.google.android.exoplayer2.mediacodec;

import android.annotation.TargetApi;
import android.graphics.Point;
import android.media.MediaCodecInfo.AudioCapabilities;
import android.media.MediaCodecInfo.CodecCapabilities;
import android.media.MediaCodecInfo.CodecProfileLevel;
import android.media.MediaCodecInfo.VideoCapabilities;
import android.util.Pair;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil.DecoderQueryException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;

@TargetApi(16)
public final class MediaCodecInfo {
    public static final int MAX_SUPPORTED_INSTANCES_UNKNOWN = -1;
    public static final String TAG = "MediaCodecInfo";
    public final boolean adaptive;
    @Nullable
    public final CodecCapabilities capabilities;
    private final boolean isVideo;
    @Nullable
    public final String mimeType;
    public final String name;
    public final boolean passthrough;
    public final boolean secure;
    public final boolean tunneling;

    public static MediaCodecInfo newPassthroughInstance(String str) {
        return new MediaCodecInfo(str, null, null, true, false, false);
    }

    public static MediaCodecInfo newInstance(String str, String str2, CodecCapabilities codecCapabilities) {
        return new MediaCodecInfo(str, str2, codecCapabilities, false, false, false);
    }

    public static MediaCodecInfo newInstance(String str, String str2, CodecCapabilities codecCapabilities, boolean z, boolean z2) {
        return new MediaCodecInfo(str, str2, codecCapabilities, false, z, z2);
    }

    private MediaCodecInfo(String str, @Nullable String str2, @Nullable CodecCapabilities codecCapabilities, boolean z, boolean z2, boolean z3) {
        this.name = (String) Assertions.checkNotNull(str);
        this.mimeType = str2;
        this.capabilities = codecCapabilities;
        this.passthrough = z;
        str = true;
        z2 = (z2 || codecCapabilities == null || !isAdaptive(codecCapabilities)) ? false : true;
        this.adaptive = z2;
        z2 = codecCapabilities != null && isTunneling(codecCapabilities);
        this.tunneling = z2;
        if (!z3) {
            if (codecCapabilities == null || isSecure(codecCapabilities) == null) {
                str = null;
            }
        }
        this.secure = str;
        this.isVideo = MimeTypes.isVideo(str2);
    }

    public String toString() {
        return this.name;
    }

    public CodecProfileLevel[] getProfileLevels() {
        if (this.capabilities != null) {
            if (this.capabilities.profileLevels != null) {
                return this.capabilities.profileLevels;
            }
        }
        return new CodecProfileLevel[0];
    }

    public int getMaxSupportedInstances() {
        if (Util.SDK_INT >= 23) {
            if (this.capabilities != null) {
                return getMaxSupportedInstancesV23(this.capabilities);
            }
        }
        return -1;
    }

    public boolean isFormatSupported(Format format) throws DecoderQueryException {
        boolean z = false;
        if (!isCodecSupported(format.codecs)) {
            return false;
        }
        if (this.isVideo) {
            if (format.width > 0) {
                if (format.height > 0) {
                    if (Util.SDK_INT >= 21) {
                        return isVideoSizeAndRateSupportedV21(format.width, format.height, (double) format.frameRate);
                    }
                    if (format.width * format.height <= MediaCodecUtil.maxH264DecodableFrameSize()) {
                        z = true;
                    }
                    if (!z) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("legacyFrameSize, ");
                        stringBuilder.append(format.width);
                        stringBuilder.append("x");
                        stringBuilder.append(format.height);
                        logNoSupport(stringBuilder.toString());
                    }
                    return z;
                }
            }
            return true;
        }
        if (Util.SDK_INT < 21 || ((format.sampleRate == -1 || isAudioSampleRateSupportedV21(format.sampleRate)) && (format.channelCount == -1 || isAudioChannelCountSupportedV21(format.channelCount) != null))) {
            z = true;
        }
        return z;
    }

    public boolean isCodecSupported(String str) {
        if (str != null) {
            if (this.mimeType != null) {
                String mediaMimeType = MimeTypes.getMediaMimeType(str);
                if (mediaMimeType == null) {
                    return true;
                }
                StringBuilder stringBuilder;
                if (this.mimeType.equals(mediaMimeType)) {
                    Pair codecProfileAndLevel = MediaCodecUtil.getCodecProfileAndLevel(str);
                    if (codecProfileAndLevel == null) {
                        return true;
                    }
                    for (CodecProfileLevel codecProfileLevel : getProfileLevels()) {
                        if (codecProfileLevel.profile == ((Integer) codecProfileAndLevel.first).intValue() && codecProfileLevel.level >= ((Integer) codecProfileAndLevel.second).intValue()) {
                            return true;
                        }
                    }
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("codec.profileLevel, ");
                    stringBuilder.append(str);
                    stringBuilder.append(", ");
                    stringBuilder.append(mediaMimeType);
                    logNoSupport(stringBuilder.toString());
                    return false;
                }
                stringBuilder = new StringBuilder();
                stringBuilder.append("codec.mime ");
                stringBuilder.append(str);
                stringBuilder.append(", ");
                stringBuilder.append(mediaMimeType);
                logNoSupport(stringBuilder.toString());
                return false;
            }
        }
        return true;
    }

    public boolean isSeamlessAdaptationSupported(Format format) {
        if (this.isVideo) {
            return this.adaptive;
        }
        format = MediaCodecUtil.getCodecProfileAndLevel(format.codecs);
        format = (format == null || ((Integer) format.first).intValue() != 42) ? null : true;
        return format;
    }

    public boolean isSeamlessAdaptationSupported(Format format, Format format2, boolean z) {
        boolean z2 = true;
        if (this.isVideo) {
            if (format.sampleMimeType.equals(format2.sampleMimeType) && format.rotationDegrees == format2.rotationDegrees && (this.adaptive || (format.width == format2.width && format.height == format2.height))) {
                if (z || format2.colorInfo) {
                    if (Util.areEqual(format.colorInfo, format2.colorInfo) != null) {
                    }
                }
                return z2;
            }
            z2 = false;
            return z2;
        }
        if (MimeTypes.AUDIO_AAC.equals(this.mimeType) && format.sampleMimeType.equals(format2.sampleMimeType) && format.channelCount == format2.channelCount) {
            if (format.sampleRate == format2.sampleRate) {
                format = MediaCodecUtil.getCodecProfileAndLevel(format.codecs);
                format2 = MediaCodecUtil.getCodecProfileAndLevel(format2.codecs);
                if (format != null) {
                    if (format2 != null) {
                        format = ((Integer) format.first).intValue();
                        format2 = ((Integer) format2.first).intValue();
                        if (format != 42 || format2 != 42) {
                            z2 = false;
                        }
                        return z2;
                    }
                }
                return false;
            }
        }
        return false;
    }

    @TargetApi(21)
    public boolean isVideoSizeAndRateSupportedV21(int i, int i2, double d) {
        if (this.capabilities == null) {
            logNoSupport("sizeAndRate.caps");
            return false;
        }
        VideoCapabilities videoCapabilities = this.capabilities.getVideoCapabilities();
        if (videoCapabilities == null) {
            logNoSupport("sizeAndRate.vCaps");
            return false;
        }
        if (!areSizeAndRateSupportedV21(videoCapabilities, i, i2, d)) {
            StringBuilder stringBuilder;
            if (i < i2) {
                if (areSizeAndRateSupportedV21(videoCapabilities, i2, i, d)) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("sizeAndRate.rotated, ");
                    stringBuilder.append(i);
                    stringBuilder.append("x");
                    stringBuilder.append(i2);
                    stringBuilder.append("x");
                    stringBuilder.append(d);
                    logAssumedSupport(stringBuilder.toString());
                }
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("sizeAndRate.support, ");
            stringBuilder.append(i);
            stringBuilder.append("x");
            stringBuilder.append(i2);
            stringBuilder.append("x");
            stringBuilder.append(d);
            logNoSupport(stringBuilder.toString());
            return false;
        }
        return true;
    }

    @TargetApi(21)
    public Point alignVideoSizeV21(int i, int i2) {
        if (this.capabilities == null) {
            logNoSupport("align.caps");
            return null;
        }
        VideoCapabilities videoCapabilities = this.capabilities.getVideoCapabilities();
        if (videoCapabilities == null) {
            logNoSupport("align.vCaps");
            return null;
        }
        int widthAlignment = videoCapabilities.getWidthAlignment();
        int heightAlignment = videoCapabilities.getHeightAlignment();
        return new Point(Util.ceilDivide(i, widthAlignment) * widthAlignment, Util.ceilDivide(i2, heightAlignment) * heightAlignment);
    }

    @TargetApi(21)
    public boolean isAudioSampleRateSupportedV21(int i) {
        if (this.capabilities == null) {
            logNoSupport("sampleRate.caps");
            return false;
        }
        AudioCapabilities audioCapabilities = this.capabilities.getAudioCapabilities();
        if (audioCapabilities == null) {
            logNoSupport("sampleRate.aCaps");
            return false;
        } else if (audioCapabilities.isSampleRateSupported(i)) {
            return true;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("sampleRate.support, ");
            stringBuilder.append(i);
            logNoSupport(stringBuilder.toString());
            return false;
        }
    }

    @TargetApi(21)
    public boolean isAudioChannelCountSupportedV21(int i) {
        if (this.capabilities == null) {
            logNoSupport("channelCount.caps");
            return false;
        }
        AudioCapabilities audioCapabilities = this.capabilities.getAudioCapabilities();
        if (audioCapabilities == null) {
            logNoSupport("channelCount.aCaps");
            return false;
        } else if (adjustMaxInputChannelCount(this.name, this.mimeType, audioCapabilities.getMaxInputChannelCount()) >= i) {
            return true;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("channelCount.support, ");
            stringBuilder.append(i);
            logNoSupport(stringBuilder.toString());
            return false;
        }
    }

    private void logNoSupport(String str) {
        String str2 = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("NoSupport [");
        stringBuilder.append(str);
        stringBuilder.append("] [");
        stringBuilder.append(this.name);
        stringBuilder.append(", ");
        stringBuilder.append(this.mimeType);
        stringBuilder.append("] [");
        stringBuilder.append(Util.DEVICE_DEBUG_INFO);
        stringBuilder.append("]");
        Log.d(str2, stringBuilder.toString());
    }

    private void logAssumedSupport(String str) {
        String str2 = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AssumedSupport [");
        stringBuilder.append(str);
        stringBuilder.append("] [");
        stringBuilder.append(this.name);
        stringBuilder.append(", ");
        stringBuilder.append(this.mimeType);
        stringBuilder.append("] [");
        stringBuilder.append(Util.DEVICE_DEBUG_INFO);
        stringBuilder.append("]");
        Log.d(str2, stringBuilder.toString());
    }

    private static int adjustMaxInputChannelCount(String str, String str2, int i) {
        if (i <= 1) {
            if (Util.SDK_INT < 26 || i <= 0) {
                if (!(MimeTypes.AUDIO_MPEG.equals(str2) || MimeTypes.AUDIO_AMR_NB.equals(str2) || MimeTypes.AUDIO_AMR_WB.equals(str2) || MimeTypes.AUDIO_AAC.equals(str2) || MimeTypes.AUDIO_VORBIS.equals(str2) || MimeTypes.AUDIO_OPUS.equals(str2) || MimeTypes.AUDIO_RAW.equals(str2) || MimeTypes.AUDIO_FLAC.equals(str2) || MimeTypes.AUDIO_ALAW.equals(str2) || MimeTypes.AUDIO_MLAW.equals(str2))) {
                    if (!MimeTypes.AUDIO_MSGSM.equals(str2)) {
                        str2 = MimeTypes.AUDIO_AC3.equals(str2) ? 6 : MimeTypes.AUDIO_E_AC3.equals(str2) != null ? 16 : 30;
                        String str3 = TAG;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("AssumedMaxChannelAdjustment: ");
                        stringBuilder.append(str);
                        stringBuilder.append(", [");
                        stringBuilder.append(i);
                        stringBuilder.append(" to ");
                        stringBuilder.append(str2);
                        stringBuilder.append("]");
                        Log.w(str3, stringBuilder.toString());
                        return str2;
                    }
                }
                return i;
            }
        }
        return i;
    }

    private static boolean isAdaptive(CodecCapabilities codecCapabilities) {
        return (Util.SDK_INT < 19 || isAdaptiveV19(codecCapabilities) == null) ? null : true;
    }

    @TargetApi(19)
    private static boolean isAdaptiveV19(CodecCapabilities codecCapabilities) {
        return codecCapabilities.isFeatureSupported("adaptive-playback");
    }

    private static boolean isTunneling(CodecCapabilities codecCapabilities) {
        return (Util.SDK_INT < 21 || isTunnelingV21(codecCapabilities) == null) ? null : true;
    }

    @TargetApi(21)
    private static boolean isTunnelingV21(CodecCapabilities codecCapabilities) {
        return codecCapabilities.isFeatureSupported("tunneled-playback");
    }

    private static boolean isSecure(CodecCapabilities codecCapabilities) {
        return (Util.SDK_INT < 21 || isSecureV21(codecCapabilities) == null) ? null : true;
    }

    @TargetApi(21)
    private static boolean isSecureV21(CodecCapabilities codecCapabilities) {
        return codecCapabilities.isFeatureSupported("secure-playback");
    }

    @TargetApi(21)
    private static boolean areSizeAndRateSupportedV21(VideoCapabilities videoCapabilities, int i, int i2, double d) {
        if (d != -1.0d) {
            if (d > 0.0d) {
                return videoCapabilities.areSizeAndRateSupported(i, i2, d);
            }
        }
        return videoCapabilities.isSizeSupported(i, i2);
    }

    @TargetApi(23)
    private static int getMaxSupportedInstancesV23(CodecCapabilities codecCapabilities) {
        return codecCapabilities.getMaxSupportedInstances();
    }
}
