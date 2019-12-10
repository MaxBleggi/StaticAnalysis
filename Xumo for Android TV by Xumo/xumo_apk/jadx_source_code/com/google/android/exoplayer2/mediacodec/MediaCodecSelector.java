package com.google.android.exoplayer2.mediacodec;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil.DecoderQueryException;
import java.util.Collections;
import java.util.List;

public interface MediaCodecSelector {
    public static final MediaCodecSelector DEFAULT = new MediaCodecSelector() {
        public List<MediaCodecInfo> getDecoderInfos(String str, boolean z) throws DecoderQueryException {
            str = MediaCodecUtil.getDecoderInfos(str, z);
            if (str.isEmpty()) {
                return Collections.emptyList();
            }
            return Collections.singletonList(str.get(false));
        }

        @Nullable
        public MediaCodecInfo getPassthroughDecoderInfo() throws DecoderQueryException {
            return MediaCodecUtil.getPassthroughDecoderInfo();
        }
    };
    public static final MediaCodecSelector DEFAULT_WITH_FALLBACK = new MediaCodecSelector() {
        public List<MediaCodecInfo> getDecoderInfos(String str, boolean z) throws DecoderQueryException {
            return MediaCodecUtil.getDecoderInfos(str, z);
        }

        @Nullable
        public MediaCodecInfo getPassthroughDecoderInfo() throws DecoderQueryException {
            return MediaCodecUtil.getPassthroughDecoderInfo();
        }
    };

    List<MediaCodecInfo> getDecoderInfos(String str, boolean z) throws DecoderQueryException;

    @Nullable
    MediaCodecInfo getPassthroughDecoderInfo() throws DecoderQueryException;
}
