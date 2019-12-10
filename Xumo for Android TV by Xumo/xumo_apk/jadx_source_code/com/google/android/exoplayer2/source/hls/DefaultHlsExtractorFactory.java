package com.google.android.exoplayer2.source.hls;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor;
import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import com.google.android.exoplayer2.extractor.ts.Ac3Extractor;
import com.google.android.exoplayer2.extractor.ts.AdtsExtractor;
import com.google.android.exoplayer2.extractor.ts.DefaultTsPayloadReaderFactory;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class DefaultHlsExtractorFactory implements HlsExtractorFactory {
    public static final String AAC_FILE_EXTENSION = ".aac";
    public static final String AC3_FILE_EXTENSION = ".ac3";
    public static final String EC3_FILE_EXTENSION = ".ec3";
    public static final String M4_FILE_EXTENSION_PREFIX = ".m4";
    public static final String MP3_FILE_EXTENSION = ".mp3";
    public static final String MP4_FILE_EXTENSION = ".mp4";
    public static final String MP4_FILE_EXTENSION_PREFIX = ".mp4";
    public static final String VTT_FILE_EXTENSION = ".vtt";
    public static final String WEBVTT_FILE_EXTENSION = ".webvtt";

    public Pair<Extractor, Boolean> createExtractor(Extractor extractor, Uri uri, Format format, List<Format> list, DrmInitData drmInitData, TimestampAdjuster timestampAdjuster, Map<String, List<String>> map, ExtractorInput extractorInput) throws InterruptedException, IOException {
        if (extractor != null) {
            if ((extractor instanceof TsExtractor) == null) {
                if ((extractor instanceof FragmentedMp4Extractor) == null) {
                    if ((extractor instanceof WebvttExtractor) != null) {
                        return buildResult(new WebvttExtractor(format.language, timestampAdjuster));
                    }
                    if ((extractor instanceof AdtsExtractor) != null) {
                        return buildResult(new AdtsExtractor());
                    }
                    if ((extractor instanceof Ac3Extractor) != null) {
                        return buildResult(new Ac3Extractor());
                    }
                    if ((extractor instanceof Mp3Extractor) != null) {
                        return buildResult(new Mp3Extractor());
                    }
                    format = new StringBuilder();
                    format.append("Unexpected previousExtractor type: ");
                    format.append(extractor.getClass().getSimpleName());
                    throw new IllegalArgumentException(format.toString());
                }
            }
            return buildResult(extractor);
        }
        extractor = createExtractorByFileExtension(uri, format, list, drmInitData, timestampAdjuster);
        extractorInput.resetPeekPosition();
        if (sniffQuietly(extractor, extractorInput) != null) {
            return buildResult(extractor);
        }
        if ((extractor instanceof WebvttExtractor) == null) {
            uri = new WebvttExtractor(format.language, timestampAdjuster);
            if (sniffQuietly(uri, extractorInput) != null) {
                return buildResult(uri);
            }
        }
        if ((extractor instanceof AdtsExtractor) == null) {
            uri = new AdtsExtractor();
            if (sniffQuietly(uri, extractorInput) != null) {
                return buildResult(uri);
            }
        }
        if ((extractor instanceof Ac3Extractor) == null) {
            uri = new Ac3Extractor();
            if (sniffQuietly(uri, extractorInput) != null) {
                return buildResult(uri);
            }
        }
        if ((extractor instanceof Mp3Extractor) == null) {
            uri = new Mp3Extractor(null, 0);
            if (sniffQuietly(uri, extractorInput) != null) {
                return buildResult(uri);
            }
        }
        if ((extractor instanceof FragmentedMp4Extractor) == null) {
            List list2;
            if (list != null) {
                list2 = list;
            } else {
                list2 = Collections.emptyList();
            }
            Uri fragmentedMp4Extractor = new FragmentedMp4Extractor(0, timestampAdjuster, null, drmInitData, list2);
            if (sniffQuietly(fragmentedMp4Extractor, extractorInput) != null) {
                return buildResult(fragmentedMp4Extractor);
            }
        }
        if ((extractor instanceof TsExtractor) == null) {
            uri = createTsExtractor(format, list, timestampAdjuster);
            if (sniffQuietly(uri, extractorInput) != null) {
                return buildResult(uri);
            }
        }
        return buildResult(extractor);
    }

    private Extractor createExtractorByFileExtension(Uri uri, Format format, List<Format> list, DrmInitData drmInitData, TimestampAdjuster timestampAdjuster) {
        uri = uri.getLastPathSegment();
        if (uri == null) {
            uri = "";
        }
        if (!(MimeTypes.TEXT_VTT.equals(format.sampleMimeType) || uri.endsWith(WEBVTT_FILE_EXTENSION))) {
            if (!uri.endsWith(VTT_FILE_EXTENSION)) {
                if (uri.endsWith(AAC_FILE_EXTENSION)) {
                    return new AdtsExtractor();
                }
                if (!uri.endsWith(AC3_FILE_EXTENSION)) {
                    if (!uri.endsWith(EC3_FILE_EXTENSION)) {
                        if (uri.endsWith(MP3_FILE_EXTENSION)) {
                            return new Mp3Extractor(null, null);
                        }
                        if (!(uri.endsWith(".mp4") || uri.startsWith(M4_FILE_EXTENSION_PREFIX, uri.length() - 4))) {
                            if (uri.startsWith(".mp4", uri.length() - 5) == null) {
                                return createTsExtractor(format, list, timestampAdjuster);
                            }
                        }
                        if (list == null) {
                            list = Collections.emptyList();
                        }
                        return new FragmentedMp4Extractor(0, timestampAdjuster, null, drmInitData, list);
                    }
                }
                return new Ac3Extractor();
            }
        }
        return new WebvttExtractor(format.language, timestampAdjuster);
    }

    private static TsExtractor createTsExtractor(Format format, List<Format> list, TimestampAdjuster timestampAdjuster) {
        int i;
        if (list != null) {
            i = 48;
        } else {
            list = Collections.singletonList(Format.createTextSampleFormat(null, MimeTypes.APPLICATION_CEA608, 0, null));
            i = 16;
        }
        format = format.codecs;
        if (!TextUtils.isEmpty(format)) {
            if (!MimeTypes.AUDIO_AAC.equals(MimeTypes.getAudioMediaMimeType(format))) {
                i |= 2;
            }
            if (MimeTypes.VIDEO_H264.equals(MimeTypes.getVideoMediaMimeType(format)) == null) {
                i |= 4;
            }
        }
        return new TsExtractor(2, timestampAdjuster, new DefaultTsPayloadReaderFactory(i, list));
    }

    private static Pair<Extractor, Boolean> buildResult(Extractor extractor) {
        boolean z;
        if (!((extractor instanceof AdtsExtractor) || (extractor instanceof Ac3Extractor))) {
            if (!(extractor instanceof Mp3Extractor)) {
                z = false;
                return new Pair(extractor, Boolean.valueOf(z));
            }
        }
        z = true;
        return new Pair(extractor, Boolean.valueOf(z));
    }

    private static boolean sniffQuietly(com.google.android.exoplayer2.extractor.Extractor r0, com.google.android.exoplayer2.extractor.ExtractorInput r1) throws java.lang.InterruptedException, java.io.IOException {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r0 = r0.sniff(r1);	 Catch:{ EOFException -> 0x000d, all -> 0x0008 }
        r1.resetPeekPosition();
        goto L_0x0011;
    L_0x0008:
        r0 = move-exception;
        r1.resetPeekPosition();
        throw r0;
    L_0x000d:
        r1.resetPeekPosition();
        r0 = 0;
    L_0x0011:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.hls.DefaultHlsExtractorFactory.sniffQuietly(com.google.android.exoplayer2.extractor.Extractor, com.google.android.exoplayer2.extractor.ExtractorInput):boolean");
    }
}
