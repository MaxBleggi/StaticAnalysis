package com.google.android.exoplayer2;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.ColorInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Format implements Parcelable {
    public static final Creator<Format> CREATOR = new Creator<Format>() {
        public Format createFromParcel(Parcel parcel) {
            return new Format(parcel);
        }

        public Format[] newArray(int i) {
            return new Format[i];
        }
    };
    public static final int NO_VALUE = -1;
    public static final long OFFSET_SAMPLE_RELATIVE = Long.MAX_VALUE;
    public final int accessibilityChannel;
    public final int bitrate;
    public final int channelCount;
    @Nullable
    public final String codecs;
    @Nullable
    public final ColorInfo colorInfo;
    @Nullable
    public final String containerMimeType;
    @Nullable
    public final DrmInitData drmInitData;
    public final int encoderDelay;
    public final int encoderPadding;
    public final float frameRate;
    private int hashCode;
    public final int height;
    @Nullable
    public final String id;
    public final List<byte[]> initializationData;
    @Nullable
    public final String label;
    @Nullable
    public final String language;
    public final int maxInputSize;
    @Nullable
    public final Metadata metadata;
    public final int pcmEncoding;
    public final float pixelWidthHeightRatio;
    @Nullable
    public final byte[] projectionData;
    public final int rotationDegrees;
    @Nullable
    public final String sampleMimeType;
    public final int sampleRate;
    public final int selectionFlags;
    public final int stereoMode;
    public final long subsampleOffsetUs;
    public final int width;

    public int describeContents() {
        return 0;
    }

    @Deprecated
    public static Format createVideoContainerFormat(@Nullable String str, @Nullable String str2, String str3, String str4, int i, int i2, int i3, float f, @Nullable List<byte[]> list, int i4) {
        return createVideoContainerFormat(str, null, str2, str3, str4, i, i2, i3, f, list, i4);
    }

    public static Format createVideoContainerFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, String str4, String str5, int i, int i2, int i3, float f, @Nullable List<byte[]> list, int i4) {
        return new Format(str, str2, str3, str4, str5, i, -1, i2, i3, f, -1, -1.0f, null, -1, null, -1, -1, -1, -1, -1, i4, null, -1, Long.MAX_VALUE, list, null, null);
    }

    public static Format createVideoSampleFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, int i, int i2, int i3, int i4, float f, @Nullable List<byte[]> list, @Nullable DrmInitData drmInitData) {
        return createVideoSampleFormat(str, str2, str3, i, i2, i3, i4, f, list, -1, -1.0f, drmInitData);
    }

    public static Format createVideoSampleFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, int i, int i2, int i3, int i4, float f, @Nullable List<byte[]> list, int i5, float f2, @Nullable DrmInitData drmInitData) {
        return createVideoSampleFormat(str, str2, str3, i, i2, i3, i4, f, list, i5, f2, null, -1, null, drmInitData);
    }

    public static Format createVideoSampleFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, int i, int i2, int i3, int i4, float f, @Nullable List<byte[]> list, int i5, float f2, byte[] bArr, int i6, @Nullable ColorInfo colorInfo, @Nullable DrmInitData drmInitData) {
        return new Format(str, null, null, str2, str3, i, i2, i3, i4, f, i5, f2, bArr, i6, colorInfo, -1, -1, -1, -1, -1, 0, null, -1, Long.MAX_VALUE, list, drmInitData, null);
    }

    @Deprecated
    public static Format createAudioContainerFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, int i, int i2, int i3, @Nullable List<byte[]> list, int i4, @Nullable String str5) {
        return createAudioContainerFormat(str, null, str2, str3, str4, i, i2, i3, list, i4, str5);
    }

    public static Format createAudioContainerFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, int i, int i2, int i3, @Nullable List<byte[]> list, int i4, @Nullable String str6) {
        return new Format(str, str2, str3, str4, str5, i, -1, -1, -1, -1.0f, -1, -1.0f, null, -1, null, i2, i3, -1, -1, -1, i4, str6, -1, Long.MAX_VALUE, list, null, null);
    }

    public static Format createAudioSampleFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, int i, int i2, int i3, int i4, @Nullable List<byte[]> list, @Nullable DrmInitData drmInitData, int i5, @Nullable String str4) {
        return createAudioSampleFormat(str, str2, str3, i, i2, i3, i4, -1, list, drmInitData, i5, str4);
    }

    public static Format createAudioSampleFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, int i, int i2, int i3, int i4, int i5, @Nullable List<byte[]> list, @Nullable DrmInitData drmInitData, int i6, @Nullable String str4) {
        return createAudioSampleFormat(str, str2, str3, i, i2, i3, i4, i5, -1, -1, list, drmInitData, i6, str4, null);
    }

    public static Format createAudioSampleFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, int i, int i2, int i3, int i4, int i5, int i6, int i7, @Nullable List<byte[]> list, @Nullable DrmInitData drmInitData, int i8, @Nullable String str4, @Nullable Metadata metadata) {
        return new Format(str, null, null, str2, str3, i, i2, -1, -1, -1.0f, -1, -1.0f, null, -1, null, i3, i4, i5, i6, i7, i8, str4, -1, Long.MAX_VALUE, list, drmInitData, metadata);
    }

    @Deprecated
    public static Format createTextContainerFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, int i, int i2, @Nullable String str5) {
        return createTextContainerFormat(str, null, str2, str3, str4, i, i2, str5);
    }

    public static Format createTextContainerFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, int i, int i2, @Nullable String str6) {
        return createTextContainerFormat(str, str2, str3, str4, str5, i, i2, str6, -1);
    }

    public static Format createTextContainerFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, int i, int i2, @Nullable String str6, int i3) {
        return new Format(str, str2, str3, str4, str5, i, -1, -1, -1, -1.0f, -1, -1.0f, null, -1, null, -1, -1, -1, -1, -1, i2, str6, i3, Long.MAX_VALUE, null, null, null);
    }

    public static Format createTextSampleFormat(@Nullable String str, String str2, int i, @Nullable String str3) {
        return createTextSampleFormat(str, str2, i, str3, null);
    }

    public static Format createTextSampleFormat(@Nullable String str, String str2, int i, @Nullable String str3, @Nullable DrmInitData drmInitData) {
        return createTextSampleFormat(str, str2, null, -1, i, str3, -1, drmInitData, Long.MAX_VALUE, Collections.emptyList());
    }

    public static Format createTextSampleFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, int i, int i2, @Nullable String str4, int i3, @Nullable DrmInitData drmInitData) {
        return createTextSampleFormat(str, str2, str3, i, i2, str4, i3, drmInitData, Long.MAX_VALUE, Collections.emptyList());
    }

    public static Format createTextSampleFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, int i, int i2, @Nullable String str4, @Nullable DrmInitData drmInitData, long j) {
        return createTextSampleFormat(str, str2, str3, i, i2, str4, -1, drmInitData, j, Collections.emptyList());
    }

    public static Format createTextSampleFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, int i, int i2, @Nullable String str4, int i3, @Nullable DrmInitData drmInitData, long j, List<byte[]> list) {
        return new Format(str, null, null, str2, str3, i, -1, -1, -1, -1.0f, -1, -1.0f, null, -1, null, -1, -1, -1, -1, -1, i2, str4, i3, j, list, drmInitData, null);
    }

    public static Format createImageSampleFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, int i, int i2, @Nullable List<byte[]> list, @Nullable String str4, @Nullable DrmInitData drmInitData) {
        return new Format(str, null, null, str2, str3, i, -1, -1, -1, -1.0f, -1, -1.0f, null, -1, null, -1, -1, -1, -1, -1, i2, str4, -1, Long.MAX_VALUE, list, drmInitData, null);
    }

    @Deprecated
    public static Format createContainerFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, int i, int i2, @Nullable String str5) {
        return createContainerFormat(str, null, str2, str3, str4, i, i2, str5);
    }

    public static Format createContainerFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, int i, int i2, @Nullable String str6) {
        return new Format(str, str2, str3, str4, str5, i, -1, -1, -1, -1.0f, -1, -1.0f, null, -1, null, -1, -1, -1, -1, -1, i2, str6, -1, Long.MAX_VALUE, null, null, null);
    }

    public static Format createSampleFormat(@Nullable String str, @Nullable String str2, long j) {
        return new Format(str, null, null, str2, null, -1, -1, -1, -1, -1.0f, -1, -1.0f, null, -1, null, -1, -1, -1, -1, -1, 0, null, -1, j, null, null, null);
    }

    public static Format createSampleFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, int i, @Nullable DrmInitData drmInitData) {
        return new Format(str, null, null, str2, str3, i, -1, -1, -1, -1.0f, -1, -1.0f, null, -1, null, -1, -1, -1, -1, -1, 0, null, -1, Long.MAX_VALUE, null, drmInitData, null);
    }

    Format(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, int i, int i2, int i3, int i4, float f, int i5, float f2, @Nullable byte[] bArr, int i6, @Nullable ColorInfo colorInfo, int i7, int i8, int i9, int i10, int i11, int i12, @Nullable String str6, int i13, long j, @Nullable List<byte[]> list, @Nullable DrmInitData drmInitData, @Nullable Metadata metadata) {
        this.id = str;
        this.label = str2;
        this.containerMimeType = str3;
        this.sampleMimeType = str4;
        this.codecs = str5;
        this.bitrate = i;
        this.maxInputSize = i2;
        this.width = i3;
        this.height = i4;
        this.frameRate = f;
        int i14 = i5;
        if (i14 == -1) {
            i14 = 0;
        }
        r0.rotationDegrees = i14;
        r0.pixelWidthHeightRatio = f2 == -1.0f ? 1.0f : f2;
        r0.projectionData = bArr;
        r0.stereoMode = i6;
        r0.colorInfo = colorInfo;
        r0.channelCount = i7;
        r0.sampleRate = i8;
        r0.pcmEncoding = i9;
        i14 = i10;
        if (i14 == -1) {
            i14 = 0;
        }
        r0.encoderDelay = i14;
        i14 = i11;
        if (i14 == -1) {
            i14 = 0;
        }
        r0.encoderPadding = i14;
        r0.selectionFlags = i12;
        r0.language = str6;
        r0.accessibilityChannel = i13;
        r0.subsampleOffsetUs = j;
        r0.initializationData = list == null ? Collections.emptyList() : list;
        r0.drmInitData = drmInitData;
        r0.metadata = metadata;
    }

    Format(Parcel parcel) {
        this.id = parcel.readString();
        this.label = parcel.readString();
        this.containerMimeType = parcel.readString();
        this.sampleMimeType = parcel.readString();
        this.codecs = parcel.readString();
        this.bitrate = parcel.readInt();
        this.maxInputSize = parcel.readInt();
        this.width = parcel.readInt();
        this.height = parcel.readInt();
        this.frameRate = parcel.readFloat();
        this.rotationDegrees = parcel.readInt();
        this.pixelWidthHeightRatio = parcel.readFloat();
        this.projectionData = Util.readBoolean(parcel) ? parcel.createByteArray() : null;
        this.stereoMode = parcel.readInt();
        this.colorInfo = (ColorInfo) parcel.readParcelable(ColorInfo.class.getClassLoader());
        this.channelCount = parcel.readInt();
        this.sampleRate = parcel.readInt();
        this.pcmEncoding = parcel.readInt();
        this.encoderDelay = parcel.readInt();
        this.encoderPadding = parcel.readInt();
        this.selectionFlags = parcel.readInt();
        this.language = parcel.readString();
        this.accessibilityChannel = parcel.readInt();
        this.subsampleOffsetUs = parcel.readLong();
        int readInt = parcel.readInt();
        this.initializationData = new ArrayList(readInt);
        for (int i = 0; i < readInt; i++) {
            this.initializationData.add(parcel.createByteArray());
        }
        this.drmInitData = (DrmInitData) parcel.readParcelable(DrmInitData.class.getClassLoader());
        this.metadata = (Metadata) parcel.readParcelable(Metadata.class.getClassLoader());
    }

    public Format copyWithMaxInputSize(int i) {
        int i2 = i;
        return new Format(this.id, this.label, this.containerMimeType, this.sampleMimeType, this.codecs, this.bitrate, i2, this.width, this.height, this.frameRate, this.rotationDegrees, this.pixelWidthHeightRatio, this.projectionData, this.stereoMode, this.colorInfo, this.channelCount, this.sampleRate, this.pcmEncoding, this.encoderDelay, this.encoderPadding, this.selectionFlags, this.language, this.accessibilityChannel, this.subsampleOffsetUs, this.initializationData, this.drmInitData, this.metadata);
    }

    public Format copyWithSubsampleOffsetUs(long j) {
        return new Format(this.id, this.label, this.containerMimeType, this.sampleMimeType, this.codecs, this.bitrate, this.maxInputSize, this.width, this.height, this.frameRate, this.rotationDegrees, this.pixelWidthHeightRatio, this.projectionData, this.stereoMode, this.colorInfo, this.channelCount, this.sampleRate, this.pcmEncoding, this.encoderDelay, this.encoderPadding, this.selectionFlags, this.language, this.accessibilityChannel, j, this.initializationData, this.drmInitData, this.metadata);
    }

    public Format copyWithContainerInfo(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, int i, int i2, int i3, int i4, @Nullable String str5) {
        return new Format(str, str2, this.containerMimeType, str3, str4, i, this.maxInputSize, i2, i3, this.frameRate, this.rotationDegrees, this.pixelWidthHeightRatio, this.projectionData, this.stereoMode, this.colorInfo, this.channelCount, this.sampleRate, this.pcmEncoding, this.encoderDelay, this.encoderPadding, i4, str5, this.accessibilityChannel, this.subsampleOffsetUs, this.initializationData, this.drmInitData, this.metadata);
    }

    public Format copyWithManifestFormatInfo(Format format) {
        Format format2 = format;
        if (this == format2) {
            return r0;
        }
        String str;
        float f;
        float f2;
        Format format3;
        Format format4;
        int trackType = MimeTypes.getTrackType(r0.sampleMimeType);
        String str2 = format2.id;
        String str3 = format2.label != null ? format2.label : r0.label;
        String str4 = r0.language;
        if ((trackType == 3 || trackType == 1) && format2.language != null) {
            str4 = format2.language;
        }
        String str5 = str4;
        int i = r0.bitrate == -1 ? format2.bitrate : r0.bitrate;
        str4 = r0.codecs;
        if (str4 == null) {
            String codecsOfType = Util.getCodecsOfType(format2.codecs, trackType);
            if (Util.splitCodecs(codecsOfType).length == 1) {
                str = codecsOfType;
                f = r0.frameRate;
                f2 = (f == -1.0f || trackType != 2) ? f : format2.frameRate;
                format3 = format4;
                format4 = new Format(str2, str3, r0.containerMimeType, r0.sampleMimeType, str, i, r0.maxInputSize, r0.width, r0.height, f2, r0.rotationDegrees, r0.pixelWidthHeightRatio, r0.projectionData, r0.stereoMode, r0.colorInfo, r0.channelCount, r0.sampleRate, r0.pcmEncoding, r0.encoderDelay, r0.encoderPadding, r0.selectionFlags | format2.selectionFlags, str5, r0.accessibilityChannel, r0.subsampleOffsetUs, r0.initializationData, DrmInitData.createSessionCreationData(format2.drmInitData, r0.drmInitData), r0.metadata);
                return format3;
            }
        }
        str = str4;
        f = r0.frameRate;
        if (f == -1.0f) {
        }
        format3 = format4;
        format4 = new Format(str2, str3, r0.containerMimeType, r0.sampleMimeType, str, i, r0.maxInputSize, r0.width, r0.height, f2, r0.rotationDegrees, r0.pixelWidthHeightRatio, r0.projectionData, r0.stereoMode, r0.colorInfo, r0.channelCount, r0.sampleRate, r0.pcmEncoding, r0.encoderDelay, r0.encoderPadding, r0.selectionFlags | format2.selectionFlags, str5, r0.accessibilityChannel, r0.subsampleOffsetUs, r0.initializationData, DrmInitData.createSessionCreationData(format2.drmInitData, r0.drmInitData), r0.metadata);
        return format3;
    }

    public Format copyWithGaplessInfo(int i, int i2) {
        int i3 = i;
        int i4 = i2;
        return new Format(this.id, this.label, this.containerMimeType, this.sampleMimeType, this.codecs, this.bitrate, this.maxInputSize, this.width, this.height, this.frameRate, this.rotationDegrees, this.pixelWidthHeightRatio, this.projectionData, this.stereoMode, this.colorInfo, this.channelCount, this.sampleRate, this.pcmEncoding, i3, i4, this.selectionFlags, this.language, this.accessibilityChannel, this.subsampleOffsetUs, this.initializationData, this.drmInitData, this.metadata);
    }

    public Format copyWithDrmInitData(@Nullable DrmInitData drmInitData) {
        DrmInitData drmInitData2 = drmInitData;
        return new Format(this.id, this.label, this.containerMimeType, this.sampleMimeType, this.codecs, this.bitrate, this.maxInputSize, this.width, this.height, this.frameRate, this.rotationDegrees, this.pixelWidthHeightRatio, this.projectionData, this.stereoMode, this.colorInfo, this.channelCount, this.sampleRate, this.pcmEncoding, this.encoderDelay, this.encoderPadding, this.selectionFlags, this.language, this.accessibilityChannel, this.subsampleOffsetUs, this.initializationData, drmInitData2, this.metadata);
    }

    public Format copyWithMetadata(@Nullable Metadata metadata) {
        Metadata metadata2 = metadata;
        return new Format(this.id, this.label, this.containerMimeType, this.sampleMimeType, this.codecs, this.bitrate, this.maxInputSize, this.width, this.height, this.frameRate, this.rotationDegrees, this.pixelWidthHeightRatio, this.projectionData, this.stereoMode, this.colorInfo, this.channelCount, this.sampleRate, this.pcmEncoding, this.encoderDelay, this.encoderPadding, this.selectionFlags, this.language, this.accessibilityChannel, this.subsampleOffsetUs, this.initializationData, this.drmInitData, metadata2);
    }

    public Format copyWithRotationDegrees(int i) {
        int i2 = i;
        return new Format(this.id, this.label, this.containerMimeType, this.sampleMimeType, this.codecs, this.bitrate, this.maxInputSize, this.width, this.height, this.frameRate, i2, this.pixelWidthHeightRatio, this.projectionData, this.stereoMode, this.colorInfo, this.channelCount, this.sampleRate, this.pcmEncoding, this.encoderDelay, this.encoderPadding, this.selectionFlags, this.language, this.accessibilityChannel, this.subsampleOffsetUs, this.initializationData, this.drmInitData, this.metadata);
    }

    public int getPixelCount() {
        if (this.width == -1) {
            return -1;
        }
        if (this.height == -1) {
            return -1;
        }
        return this.height * this.width;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Format(");
        stringBuilder.append(this.id);
        stringBuilder.append(", ");
        stringBuilder.append(this.label);
        stringBuilder.append(", ");
        stringBuilder.append(this.containerMimeType);
        stringBuilder.append(", ");
        stringBuilder.append(this.sampleMimeType);
        stringBuilder.append(", ");
        stringBuilder.append(this.codecs);
        stringBuilder.append(", ");
        stringBuilder.append(this.bitrate);
        stringBuilder.append(", ");
        stringBuilder.append(this.language);
        stringBuilder.append(", [");
        stringBuilder.append(this.width);
        stringBuilder.append(", ");
        stringBuilder.append(this.height);
        stringBuilder.append(", ");
        stringBuilder.append(this.frameRate);
        stringBuilder.append("], [");
        stringBuilder.append(this.channelCount);
        stringBuilder.append(", ");
        stringBuilder.append(this.sampleRate);
        stringBuilder.append("])");
        return stringBuilder.toString();
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            int i = 0;
            int hashCode = (((((((((((((((((((((((((527 + (this.id == null ? 0 : this.id.hashCode())) * 31) + (this.containerMimeType == null ? 0 : this.containerMimeType.hashCode())) * 31) + (this.sampleMimeType == null ? 0 : this.sampleMimeType.hashCode())) * 31) + (this.codecs == null ? 0 : this.codecs.hashCode())) * 31) + this.bitrate) * 31) + this.width) * 31) + this.height) * 31) + this.channelCount) * 31) + this.sampleRate) * 31) + (this.language == null ? 0 : this.language.hashCode())) * 31) + this.accessibilityChannel) * 31) + (this.drmInitData == null ? 0 : this.drmInitData.hashCode())) * 31) + (this.metadata == null ? 0 : this.metadata.hashCode())) * 31;
            if (this.label != null) {
                i = this.label.hashCode();
            }
            this.hashCode = ((((((((((((((((((((hashCode + i) * 31) + this.maxInputSize) * 31) + ((int) this.subsampleOffsetUs)) * 31) + Float.floatToIntBits(this.frameRate)) * 31) + Float.floatToIntBits(this.pixelWidthHeightRatio)) * 31) + this.rotationDegrees) * 31) + this.stereoMode) * 31) + this.pcmEncoding) * 31) + this.encoderDelay) * 31) + this.encoderPadding) * 31) + this.selectionFlags;
        }
        return this.hashCode;
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj != null) {
            if (getClass() == obj.getClass()) {
                Format format = (Format) obj;
                if (this.hashCode != 0 && format.hashCode != 0 && this.hashCode != format.hashCode) {
                    return false;
                }
                if (this.bitrate != format.bitrate || this.maxInputSize != format.maxInputSize || this.width != format.width || this.height != format.height || Float.compare(this.frameRate, format.frameRate) != 0 || this.rotationDegrees != format.rotationDegrees || Float.compare(this.pixelWidthHeightRatio, format.pixelWidthHeightRatio) != 0 || this.stereoMode != format.stereoMode || this.channelCount != format.channelCount || this.sampleRate != format.sampleRate || this.pcmEncoding != format.pcmEncoding || this.encoderDelay != format.encoderDelay || this.encoderPadding != format.encoderPadding || this.subsampleOffsetUs != format.subsampleOffsetUs || this.selectionFlags != format.selectionFlags || !Util.areEqual(this.id, format.id) || !Util.areEqual(this.label, format.label) || !Util.areEqual(this.language, format.language) || this.accessibilityChannel != format.accessibilityChannel || !Util.areEqual(this.containerMimeType, format.containerMimeType) || !Util.areEqual(this.sampleMimeType, format.sampleMimeType) || !Util.areEqual(this.codecs, format.codecs) || !Util.areEqual(this.drmInitData, format.drmInitData) || !Util.areEqual(this.metadata, format.metadata) || !Util.areEqual(this.colorInfo, format.colorInfo) || !Arrays.equals(this.projectionData, format.projectionData) || initializationDataEquals(format) == null) {
                    z = false;
                }
                return z;
            }
        }
        return false;
    }

    public boolean initializationDataEquals(Format format) {
        if (this.initializationData.size() != format.initializationData.size()) {
            return false;
        }
        for (int i = 0; i < this.initializationData.size(); i++) {
            if (!Arrays.equals((byte[]) this.initializationData.get(i), (byte[]) format.initializationData.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static String toLogString(@Nullable Format format) {
        if (format == null) {
            return "null";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id=");
        stringBuilder.append(format.id);
        stringBuilder.append(", mimeType=");
        stringBuilder.append(format.sampleMimeType);
        if (format.bitrate != -1) {
            stringBuilder.append(", bitrate=");
            stringBuilder.append(format.bitrate);
        }
        if (format.codecs != null) {
            stringBuilder.append(", codecs=");
            stringBuilder.append(format.codecs);
        }
        if (!(format.width == -1 || format.height == -1)) {
            stringBuilder.append(", res=");
            stringBuilder.append(format.width);
            stringBuilder.append("x");
            stringBuilder.append(format.height);
        }
        if (format.frameRate != -1.0f) {
            stringBuilder.append(", fps=");
            stringBuilder.append(format.frameRate);
        }
        if (format.channelCount != -1) {
            stringBuilder.append(", channels=");
            stringBuilder.append(format.channelCount);
        }
        if (format.sampleRate != -1) {
            stringBuilder.append(", sample_rate=");
            stringBuilder.append(format.sampleRate);
        }
        if (format.language != null) {
            stringBuilder.append(", language=");
            stringBuilder.append(format.language);
        }
        if (format.label != null) {
            stringBuilder.append(", label=");
            stringBuilder.append(format.label);
        }
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.label);
        parcel.writeString(this.containerMimeType);
        parcel.writeString(this.sampleMimeType);
        parcel.writeString(this.codecs);
        parcel.writeInt(this.bitrate);
        parcel.writeInt(this.maxInputSize);
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
        parcel.writeFloat(this.frameRate);
        parcel.writeInt(this.rotationDegrees);
        parcel.writeFloat(this.pixelWidthHeightRatio);
        Util.writeBoolean(parcel, this.projectionData != null);
        if (this.projectionData != null) {
            parcel.writeByteArray(this.projectionData);
        }
        parcel.writeInt(this.stereoMode);
        parcel.writeParcelable(this.colorInfo, i);
        parcel.writeInt(this.channelCount);
        parcel.writeInt(this.sampleRate);
        parcel.writeInt(this.pcmEncoding);
        parcel.writeInt(this.encoderDelay);
        parcel.writeInt(this.encoderPadding);
        parcel.writeInt(this.selectionFlags);
        parcel.writeString(this.language);
        parcel.writeInt(this.accessibilityChannel);
        parcel.writeLong(this.subsampleOffsetUs);
        i = this.initializationData.size();
        parcel.writeInt(i);
        for (int i2 = 0; i2 < i; i2++) {
            parcel.writeByteArray((byte[]) this.initializationData.get(i2));
        }
        parcel.writeParcelable(this.drmInitData, 0);
        parcel.writeParcelable(this.metadata, 0);
    }
}
