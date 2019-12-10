package com.google.android.exoplayer2.ui;

import android.content.res.Resources;
import android.text.TextUtils;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.util.Locale;

public class DefaultTrackNameProvider implements TrackNameProvider {
    private final Resources resources;

    public DefaultTrackNameProvider(Resources resources) {
        this.resources = (Resources) Assertions.checkNotNull(resources);
    }

    public String getTrackName(Format format) {
        int inferPrimaryTrackType = inferPrimaryTrackType(format);
        if (inferPrimaryTrackType == 2) {
            format = joinWithSeparator(buildResolutionString(format), buildBitrateString(format));
        } else if (inferPrimaryTrackType == 1) {
            format = joinWithSeparator(buildLabelString(format), buildAudioChannelString(format), buildBitrateString(format));
        } else {
            format = buildLabelString(format);
        }
        return format.length() == 0 ? this.resources.getString(R.string.exo_track_unknown) : format;
    }

    private String buildResolutionString(Format format) {
        int i = format.width;
        format = format.height;
        if (i != -1) {
            if (format != -1) {
                return this.resources.getString(R.string.exo_track_resolution, new Object[]{Integer.valueOf(i), Integer.valueOf(format)});
            }
        }
        return "";
    }

    private String buildBitrateString(Format format) {
        if (format.bitrate == -1) {
            return "";
        }
        return this.resources.getString(R.string.exo_track_bitrate, new Object[]{Float.valueOf(((float) format) / 1000000.0f)});
    }

    private String buildAudioChannelString(Format format) {
        format = format.channelCount;
        if (format != -1) {
            if (format >= 1) {
                switch (format) {
                    case 1:
                        return this.resources.getString(R.string.exo_track_mono);
                    case 2:
                        return this.resources.getString(R.string.exo_track_stereo);
                    case 6:
                    case 7:
                        return this.resources.getString(R.string.exo_track_surround_5_point_1);
                    case 8:
                        return this.resources.getString(R.string.exo_track_surround_7_point_1);
                    default:
                        return this.resources.getString(R.string.exo_track_surround);
                }
            }
        }
        return "";
    }

    private String buildLabelString(Format format) {
        if (!TextUtils.isEmpty(format.label)) {
            return format.label;
        }
        format = format.language;
        if (!TextUtils.isEmpty(format)) {
            if (!C.LANGUAGE_UNDETERMINED.equals(format)) {
                format = buildLanguageString(format);
                return format;
            }
        }
        format = "";
        return format;
    }

    private String buildLanguageString(String str) {
        return (Util.SDK_INT >= 21 ? Locale.forLanguageTag(str) : new Locale(str)).getDisplayLanguage();
    }

    private String joinWithSeparator(String... strArr) {
        Object obj = "";
        for (String str : strArr) {
            if (str.length() > 0) {
                if (TextUtils.isEmpty(obj)) {
                    obj = str;
                } else {
                    obj = this.resources.getString(R.string.exo_item_list, new Object[]{obj, str});
                }
            }
        }
        return obj;
    }

    private static int inferPrimaryTrackType(Format format) {
        int trackType = MimeTypes.getTrackType(format.sampleMimeType);
        if (trackType != -1) {
            return trackType;
        }
        if (MimeTypes.getVideoMediaMimeType(format.codecs) != null) {
            return 2;
        }
        if (MimeTypes.getAudioMediaMimeType(format.codecs) != null) {
            return 1;
        }
        if (format.width == -1) {
            if (format.height == -1) {
                if (format.channelCount == -1) {
                    if (format.sampleRate == -1) {
                        return -1;
                    }
                }
                return 1;
            }
        }
        return 2;
    }
}
