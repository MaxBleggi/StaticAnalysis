package com.google.android.exoplayer2.source.hls.playlist;

import android.net.Uri;
import android.util.Base64;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmInitData.SchemeData;
import com.google.android.exoplayer2.extractor.mp4.PsshAtomUtil;
import com.google.android.exoplayer2.source.UnrecognizedInputFormatException;
import com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist.Segment;
import com.google.android.exoplayer2.upstream.ParsingLoadable.Parser;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.libraries.cast.companionlibrary.utils.CustomData.PLAYSUBTYPE;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HlsPlaylistParser implements Parser<HlsPlaylist> {
    private static final String ATTR_CLOSED_CAPTIONS_NONE = "CLOSED-CAPTIONS=NONE";
    private static final String BOOLEAN_FALSE = "NO";
    private static final String BOOLEAN_TRUE = "YES";
    private static final String KEYFORMAT_IDENTITY = "identity";
    private static final String KEYFORMAT_PLAYREADY = "com.microsoft.playready";
    private static final String KEYFORMAT_WIDEVINE_PSSH_BINARY = "urn:uuid:edef8ba9-79d6-4ace-a3c8-27dcd51d21ed";
    private static final String KEYFORMAT_WIDEVINE_PSSH_JSON = "com.widevine";
    private static final String METHOD_AES_128 = "AES-128";
    private static final String METHOD_NONE = "NONE";
    private static final String METHOD_SAMPLE_AES = "SAMPLE-AES";
    private static final String METHOD_SAMPLE_AES_CENC = "SAMPLE-AES-CENC";
    private static final String METHOD_SAMPLE_AES_CTR = "SAMPLE-AES-CTR";
    private static final String PLAYLIST_HEADER = "#EXTM3U";
    private static final Pattern REGEX_ATTR_BYTERANGE = Pattern.compile("BYTERANGE=\"(\\d+(?:@\\d+)?)\\b\"");
    private static final Pattern REGEX_AUDIO = Pattern.compile("AUDIO=\"(.+?)\"");
    private static final Pattern REGEX_AUTOSELECT = compileBooleanAttrPattern("AUTOSELECT");
    private static final Pattern REGEX_AVERAGE_BANDWIDTH = Pattern.compile("AVERAGE-BANDWIDTH=(\\d+)\\b");
    private static final Pattern REGEX_BANDWIDTH = Pattern.compile("[^-]BANDWIDTH=(\\d+)\\b");
    private static final Pattern REGEX_BYTERANGE = Pattern.compile("#EXT-X-BYTERANGE:(\\d+(?:@\\d+)?)\\b");
    private static final Pattern REGEX_CODECS = Pattern.compile("CODECS=\"(.+?)\"");
    private static final Pattern REGEX_DEFAULT = compileBooleanAttrPattern("DEFAULT");
    private static final Pattern REGEX_FORCED = compileBooleanAttrPattern("FORCED");
    private static final Pattern REGEX_FRAME_RATE = Pattern.compile("FRAME-RATE=([\\d\\.]+)\\b");
    private static final Pattern REGEX_GROUP_ID = Pattern.compile("GROUP-ID=\"(.+?)\"");
    private static final Pattern REGEX_IMPORT = Pattern.compile("IMPORT=\"(.+?)\"");
    private static final Pattern REGEX_INSTREAM_ID = Pattern.compile("INSTREAM-ID=\"((?:CC|SERVICE)\\d+)\"");
    private static final Pattern REGEX_IV = Pattern.compile("IV=([^,.*]+)");
    private static final Pattern REGEX_KEYFORMAT = Pattern.compile("KEYFORMAT=\"(.+?)\"");
    private static final Pattern REGEX_KEYFORMATVERSIONS = Pattern.compile("KEYFORMATVERSIONS=\"(.+?)\"");
    private static final Pattern REGEX_LANGUAGE = Pattern.compile("LANGUAGE=\"(.+?)\"");
    private static final Pattern REGEX_MEDIA_DURATION = Pattern.compile("#EXTINF:([\\d\\.]+)\\b");
    private static final Pattern REGEX_MEDIA_SEQUENCE = Pattern.compile("#EXT-X-MEDIA-SEQUENCE:(\\d+)\\b");
    private static final Pattern REGEX_MEDIA_TITLE = Pattern.compile("#EXTINF:[\\d\\.]+\\b,(.+)");
    private static final Pattern REGEX_METHOD = Pattern.compile("METHOD=(NONE|AES-128|SAMPLE-AES|SAMPLE-AES-CENC|SAMPLE-AES-CTR)\\s*(?:,|$)");
    private static final Pattern REGEX_NAME = Pattern.compile("NAME=\"(.+?)\"");
    private static final Pattern REGEX_PLAYLIST_TYPE = Pattern.compile("#EXT-X-PLAYLIST-TYPE:(.+)\\b");
    private static final Pattern REGEX_RESOLUTION = Pattern.compile("RESOLUTION=(\\d+x\\d+)");
    private static final Pattern REGEX_TARGET_DURATION = Pattern.compile("#EXT-X-TARGETDURATION:(\\d+)\\b");
    private static final Pattern REGEX_TIME_OFFSET = Pattern.compile("TIME-OFFSET=(-?[\\d\\.]+)\\b");
    private static final Pattern REGEX_TYPE = Pattern.compile("TYPE=(AUDIO|VIDEO|SUBTITLES|CLOSED-CAPTIONS)");
    private static final Pattern REGEX_URI = Pattern.compile("URI=\"(.+?)\"");
    private static final Pattern REGEX_VALUE = Pattern.compile("VALUE=\"(.+?)\"");
    private static final Pattern REGEX_VARIABLE_REFERENCE = Pattern.compile("\\{\\$([a-zA-Z0-9\\-_]+)\\}");
    private static final Pattern REGEX_VERSION = Pattern.compile("#EXT-X-VERSION:(\\d+)\\b");
    private static final String TAG_BYTERANGE = "#EXT-X-BYTERANGE";
    private static final String TAG_DEFINE = "#EXT-X-DEFINE";
    private static final String TAG_DISCONTINUITY = "#EXT-X-DISCONTINUITY";
    private static final String TAG_DISCONTINUITY_SEQUENCE = "#EXT-X-DISCONTINUITY-SEQUENCE";
    private static final String TAG_ENDLIST = "#EXT-X-ENDLIST";
    private static final String TAG_GAP = "#EXT-X-GAP";
    private static final String TAG_INDEPENDENT_SEGMENTS = "#EXT-X-INDEPENDENT-SEGMENTS";
    private static final String TAG_INIT_SEGMENT = "#EXT-X-MAP";
    private static final String TAG_KEY = "#EXT-X-KEY";
    private static final String TAG_MEDIA = "#EXT-X-MEDIA";
    private static final String TAG_MEDIA_DURATION = "#EXTINF";
    private static final String TAG_MEDIA_SEQUENCE = "#EXT-X-MEDIA-SEQUENCE";
    private static final String TAG_PLAYLIST_TYPE = "#EXT-X-PLAYLIST-TYPE";
    private static final String TAG_PREFIX = "#EXT";
    private static final String TAG_PROGRAM_DATE_TIME = "#EXT-X-PROGRAM-DATE-TIME";
    private static final String TAG_START = "#EXT-X-START";
    private static final String TAG_STREAM_INF = "#EXT-X-STREAM-INF";
    private static final String TAG_TARGET_DURATION = "#EXT-X-TARGETDURATION";
    private static final String TAG_VERSION = "#EXT-X-VERSION";
    private static final String TYPE_AUDIO = "AUDIO";
    private static final String TYPE_CLOSED_CAPTIONS = "CLOSED-CAPTIONS";
    private static final String TYPE_SUBTITLES = "SUBTITLES";
    private static final String TYPE_VIDEO = "VIDEO";
    private final HlsMasterPlaylist masterPlaylist;

    private static class LineIterator {
        private final Queue<String> extraLines;
        private String next;
        private final BufferedReader reader;

        public LineIterator(Queue<String> queue, BufferedReader bufferedReader) {
            this.extraLines = queue;
            this.reader = bufferedReader;
        }

        public boolean hasNext() throws IOException {
            if (this.next != null) {
                return true;
            }
            if (this.extraLines.isEmpty()) {
                do {
                    String readLine = this.reader.readLine();
                    this.next = readLine;
                    if (readLine == null) {
                        return false;
                    }
                    this.next = this.next.trim();
                } while (this.next.isEmpty());
                return true;
            }
            this.next = (String) this.extraLines.poll();
            return true;
        }

        public String next() throws IOException {
            if (!hasNext()) {
                return null;
            }
            String str = this.next;
            this.next = null;
            return str;
        }
    }

    public HlsPlaylistParser() {
        this(HlsMasterPlaylist.EMPTY);
    }

    public HlsPlaylistParser(HlsMasterPlaylist hlsMasterPlaylist) {
        this.masterPlaylist = hlsMasterPlaylist;
    }

    public HlsPlaylist parse(Uri uri, InputStream inputStream) throws IOException {
        Closeable bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        inputStream = new ArrayDeque();
        try {
            if (checkPlaylistHeader(bufferedReader)) {
                String readLine;
                while (true) {
                    readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        readLine = readLine.trim();
                        if (!readLine.isEmpty()) {
                            if (!readLine.startsWith(TAG_STREAM_INF)) {
                                if (readLine.startsWith(TAG_TARGET_DURATION) || readLine.startsWith(TAG_MEDIA_SEQUENCE) || readLine.startsWith(TAG_MEDIA_DURATION) || readLine.startsWith(TAG_KEY) || readLine.startsWith(TAG_BYTERANGE) || readLine.equals(TAG_DISCONTINUITY) || readLine.equals(TAG_DISCONTINUITY_SEQUENCE)) {
                                    break;
                                } else if (readLine.equals(TAG_ENDLIST)) {
                                    break;
                                } else {
                                    inputStream.add(readLine);
                                }
                            } else {
                                inputStream.add(readLine);
                                uri = parseMasterPlaylist(new LineIterator(inputStream, bufferedReader), uri.toString());
                                Util.closeQuietly(bufferedReader);
                                return uri;
                            }
                        }
                    } else {
                        Util.closeQuietly(bufferedReader);
                        throw new ParserException("Failed to parse the playlist, could not identify any tags.");
                    }
                }
                inputStream.add(readLine);
                uri = parseMediaPlaylist(this.masterPlaylist, new LineIterator(inputStream, bufferedReader), uri.toString());
                return uri;
            }
            throw new UnrecognizedInputFormatException("Input does not start with the #EXTM3U header.", uri);
        } finally {
            Util.closeQuietly(bufferedReader);
        }
    }

    private static boolean checkPlaylistHeader(BufferedReader bufferedReader) throws IOException {
        int read = bufferedReader.read();
        if (read == 239) {
            if (bufferedReader.read() == 187) {
                if (bufferedReader.read() == 191) {
                    read = bufferedReader.read();
                }
            }
            return false;
        }
        char skipIgnorableWhitespace = skipIgnorableWhitespace(bufferedReader, true, read);
        int length = PLAYLIST_HEADER.length();
        char c = skipIgnorableWhitespace;
        for (read = 0; read < length; read++) {
            if (c != PLAYLIST_HEADER.charAt(read)) {
                return false;
            }
            c = bufferedReader.read();
        }
        return Util.isLinebreak(skipIgnorableWhitespace(bufferedReader, false, c));
    }

    private static int skipIgnorableWhitespace(BufferedReader bufferedReader, boolean z, int i) throws IOException {
        while (i != -1 && Character.isWhitespace(i) && (z || !Util.isLinebreak(i))) {
            i = bufferedReader.read();
        }
        return i;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist parseMasterPlaylist(com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistParser.LineIterator r41, java.lang.String r42) throws java.io.IOException {
        /*
        r0 = new java.util.HashSet;
        r0.<init>();
        r1 = new java.util.HashMap;
        r1.<init>();
        r11 = new java.util.HashMap;
        r11.<init>();
        r5 = new java.util.ArrayList;
        r5.<init>();
        r6 = new java.util.ArrayList;
        r6.<init>();
        r7 = new java.util.ArrayList;
        r7.<init>();
        r2 = new java.util.ArrayList;
        r2.<init>();
        r4 = new java.util.ArrayList;
        r4.<init>();
        r3 = 1;
        r8 = 0;
        r9 = 0;
        r10 = 0;
    L_0x002c:
        r12 = r41.hasNext();
        if (r12 == 0) goto L_0x011b;
    L_0x0032:
        r12 = r41.next();
        r14 = "#EXT";
        r14 = r12.startsWith(r14);
        if (r14 == 0) goto L_0x0041;
    L_0x003e:
        r4.add(r12);
    L_0x0041:
        r14 = "#EXT-X-DEFINE";
        r14 = r12.startsWith(r14);
        if (r14 == 0) goto L_0x0059;
    L_0x0049:
        r13 = REGEX_NAME;
        r13 = parseStringAttr(r12, r13, r11);
        r14 = REGEX_VALUE;
        r12 = parseStringAttr(r12, r14, r11);
        r11.put(r13, r12);
        goto L_0x002c;
    L_0x0059:
        r14 = "#EXT-X-INDEPENDENT-SEGMENTS";
        r14 = r12.equals(r14);
        if (r14 == 0) goto L_0x0063;
    L_0x0061:
        r10 = 1;
        goto L_0x002c;
    L_0x0063:
        r14 = "#EXT-X-MEDIA";
        r14 = r12.startsWith(r14);
        if (r14 == 0) goto L_0x006f;
    L_0x006b:
        r2.add(r12);
        goto L_0x002c;
    L_0x006f:
        r14 = "#EXT-X-STREAM-INF";
        r14 = r12.startsWith(r14);
        if (r14 == 0) goto L_0x002c;
    L_0x0077:
        r14 = "CLOSED-CAPTIONS=NONE";
        r14 = r12.contains(r14);
        r9 = r9 | r14;
        r14 = REGEX_BANDWIDTH;
        r14 = parseIntAttr(r12, r14);
        r15 = REGEX_AVERAGE_BANDWIDTH;
        r15 = parseOptionalStringAttr(r12, r15, r11);
        if (r15 == 0) goto L_0x0090;
    L_0x008c:
        r14 = java.lang.Integer.parseInt(r15);
    L_0x0090:
        r20 = r14;
        r14 = REGEX_CODECS;
        r14 = parseOptionalStringAttr(r12, r14, r11);
        r15 = REGEX_RESOLUTION;
        r15 = parseOptionalStringAttr(r12, r15, r11);
        if (r15 == 0) goto L_0x00c3;
    L_0x00a0:
        r13 = "x";
        r13 = r15.split(r13);
        r15 = r13[r8];
        r15 = java.lang.Integer.parseInt(r15);
        r13 = r13[r3];
        r13 = java.lang.Integer.parseInt(r13);
        if (r15 <= 0) goto L_0x00bb;
    L_0x00b4:
        if (r13 > 0) goto L_0x00b7;
    L_0x00b6:
        goto L_0x00bb;
    L_0x00b7:
        r26 = r13;
        r13 = r15;
        goto L_0x00be;
    L_0x00bb:
        r13 = -1;
        r26 = -1;
    L_0x00be:
        r21 = r13;
        r22 = r26;
        goto L_0x00c7;
    L_0x00c3:
        r21 = -1;
        r22 = -1;
    L_0x00c7:
        r13 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r15 = REGEX_FRAME_RATE;
        r15 = parseOptionalStringAttr(r12, r15, r11);
        if (r15 == 0) goto L_0x00d8;
    L_0x00d1:
        r13 = java.lang.Float.parseFloat(r15);
        r23 = r13;
        goto L_0x00da;
    L_0x00d8:
        r23 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
    L_0x00da:
        r13 = REGEX_AUDIO;
        r12 = parseOptionalStringAttr(r12, r13, r11);
        if (r12 == 0) goto L_0x00eb;
    L_0x00e2:
        if (r14 == 0) goto L_0x00eb;
    L_0x00e4:
        r13 = com.google.android.exoplayer2.util.Util.getCodecsOfType(r14, r3);
        r1.put(r12, r13);
    L_0x00eb:
        r12 = r41.next();
        r12 = replaceVariableReferences(r12, r11);
        r13 = r0.add(r12);
        if (r13 == 0) goto L_0x002c;
    L_0x00f9:
        r13 = r5.size();
        r15 = java.lang.Integer.toString(r13);
        r16 = 0;
        r17 = "application/x-mpegURL";
        r18 = 0;
        r24 = 0;
        r25 = 0;
        r19 = r14;
        r13 = com.google.android.exoplayer2.Format.createVideoContainerFormat(r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25);
        r14 = new com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist$HlsUrl;
        r14.<init>(r12, r13);
        r5.add(r14);
        goto L_0x002c;
    L_0x011b:
        r12 = 0;
        r13 = 0;
        r14 = 0;
    L_0x011e:
        r15 = r2.size();
        if (r12 >= r15) goto L_0x0228;
    L_0x0124:
        r15 = r2.get(r12);
        r15 = (java.lang.String) r15;
        r36 = parseSelectionFlags(r15);
        r0 = REGEX_URI;
        r0 = parseOptionalStringAttr(r15, r0, r11);
        r3 = REGEX_NAME;
        r28 = parseStringAttr(r15, r3, r11);
        r3 = REGEX_LANGUAGE;
        r37 = parseOptionalStringAttr(r15, r3, r11);
        r3 = REGEX_GROUP_ID;
        r3 = parseOptionalStringAttr(r15, r3, r11);
        r8 = REGEX_TYPE;
        r8 = parseStringAttr(r15, r8, r11);
        r38 = r2;
        r2 = r8.hashCode();
        r39 = r10;
        r10 = -959297733; // 0xffffffffc6d2473b float:-26915.615 double:NaN;
        r40 = r13;
        r13 = 2;
        if (r2 == r10) goto L_0x017b;
    L_0x015c:
        r10 = -333210994; // 0xffffffffec239a8e float:-7.911391E26 double:NaN;
        if (r2 == r10) goto L_0x0171;
    L_0x0161:
        r10 = 62628790; // 0x3bba3b6 float:1.1028458E-36 double:3.09427336E-316;
        if (r2 == r10) goto L_0x0167;
    L_0x0166:
        goto L_0x0185;
    L_0x0167:
        r2 = "AUDIO";
        r2 = r8.equals(r2);
        if (r2 == 0) goto L_0x0185;
    L_0x016f:
        r2 = 0;
        goto L_0x0186;
    L_0x0171:
        r2 = "CLOSED-CAPTIONS";
        r2 = r8.equals(r2);
        if (r2 == 0) goto L_0x0185;
    L_0x0179:
        r2 = 2;
        goto L_0x0186;
    L_0x017b:
        r2 = "SUBTITLES";
        r2 = r8.equals(r2);
        if (r2 == 0) goto L_0x0185;
    L_0x0183:
        r2 = 1;
        goto L_0x0186;
    L_0x0185:
        r2 = -1;
    L_0x0186:
        switch(r2) {
            case 0: goto L_0x01ee;
            case 1: goto L_0x01d1;
            case 2: goto L_0x018b;
            default: goto L_0x0189;
        };
    L_0x0189:
        goto L_0x021c;
    L_0x018b:
        r0 = REGEX_INSTREAM_ID;
        r0 = parseStringAttr(r15, r0, r11);
        r2 = "CC";
        r2 = r0.startsWith(r2);
        if (r2 == 0) goto L_0x01a8;
    L_0x0199:
        r2 = "application/cea-608";
        r0 = r0.substring(r13);
        r0 = java.lang.Integer.parseInt(r0);
    L_0x01a3:
        r24 = r0;
        r19 = r2;
        goto L_0x01b4;
    L_0x01a8:
        r2 = "application/cea-708";
        r3 = 7;
        r0 = r0.substring(r3);
        r0 = java.lang.Integer.parseInt(r0);
        goto L_0x01a3;
    L_0x01b4:
        if (r14 != 0) goto L_0x01bb;
    L_0x01b6:
        r14 = new java.util.ArrayList;
        r14.<init>();
    L_0x01bb:
        r18 = 0;
        r20 = 0;
        r21 = -1;
        r16 = r28;
        r17 = r28;
        r22 = r36;
        r23 = r37;
        r0 = com.google.android.exoplayer2.Format.createTextContainerFormat(r16, r17, r18, r19, r20, r21, r22, r23, r24);
        r14.add(r0);
        goto L_0x021c;
    L_0x01d1:
        r18 = "application/x-mpegURL";
        r19 = "text/vtt";
        r20 = 0;
        r21 = -1;
        r16 = r28;
        r17 = r28;
        r22 = r36;
        r23 = r37;
        r2 = com.google.android.exoplayer2.Format.createTextContainerFormat(r16, r17, r18, r19, r20, r21, r22, r23);
        r3 = new com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist$HlsUrl;
        r3.<init>(r0, r2);
        r7.add(r3);
        goto L_0x021c;
    L_0x01ee:
        r2 = r1.get(r3);
        r31 = r2;
        r31 = (java.lang.String) r31;
        if (r31 == 0) goto L_0x01ff;
    L_0x01f8:
        r2 = com.google.android.exoplayer2.util.MimeTypes.getMediaMimeType(r31);
        r30 = r2;
        goto L_0x0201;
    L_0x01ff:
        r30 = 0;
    L_0x0201:
        r29 = "application/x-mpegURL";
        r32 = -1;
        r33 = -1;
        r34 = -1;
        r35 = 0;
        r27 = r28;
        r13 = com.google.android.exoplayer2.Format.createAudioContainerFormat(r27, r28, r29, r30, r31, r32, r33, r34, r35, r36, r37);
        if (r0 != 0) goto L_0x0214;
    L_0x0213:
        goto L_0x021e;
    L_0x0214:
        r2 = new com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist$HlsUrl;
        r2.<init>(r0, r13);
        r6.add(r2);
    L_0x021c:
        r13 = r40;
    L_0x021e:
        r12 = r12 + 1;
        r2 = r38;
        r10 = r39;
        r3 = 1;
        r8 = 0;
        goto L_0x011e;
    L_0x0228:
        r39 = r10;
        r40 = r13;
        if (r9 == 0) goto L_0x0234;
    L_0x022e:
        r0 = java.util.Collections.emptyList();
        r9 = r0;
        goto L_0x0235;
    L_0x0234:
        r9 = r14;
    L_0x0235:
        r0 = new com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist;
        r2 = r0;
        r3 = r42;
        r8 = r40;
        r10 = r39;
        r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11);
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistParser.parseMasterPlaylist(com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistParser$LineIterator, java.lang.String):com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist");
    }

    private static int parseSelectionFlags(String str) {
        int i = parseOptionalBooleanAttribute(str, REGEX_DEFAULT, false) ? 1 : 0;
        if (parseOptionalBooleanAttribute(str, REGEX_FORCED, false)) {
            i |= 2;
        }
        return parseOptionalBooleanAttribute(str, REGEX_AUTOSELECT, false) != null ? i | 4 : i;
    }

    private static HlsMediaPlaylist parseMediaPlaylist(HlsMasterPlaylist hlsMasterPlaylist, LineIterator lineIterator, String str) throws IOException {
        HlsMasterPlaylist hlsMasterPlaylist2 = hlsMasterPlaylist;
        boolean z = hlsMasterPlaylist2.hasIndependentSegments;
        Map hashMap = new HashMap();
        List arrayList = new ArrayList();
        List arrayList2 = new ArrayList();
        TreeMap treeMap = new TreeMap();
        long j = C.TIME_UNSET;
        int i = 0;
        int i2 = 1;
        boolean z2 = z;
        String str2 = "";
        long j2 = C.TIME_UNSET;
        int i3 = 0;
        String str3 = null;
        long j3 = 0;
        boolean z3 = false;
        int i4 = 0;
        long j4 = 0;
        int i5 = 1;
        boolean z4 = false;
        DrmInitData drmInitData = null;
        long j5 = 0;
        long j6 = 0;
        DrmInitData drmInitData2 = null;
        boolean z5 = false;
        long j7 = -1;
        int i6 = 0;
        long j8 = 0;
        String str4 = null;
        String str5 = null;
        Segment segment = null;
        loop0:
        while (true) {
            long j9 = 0;
            while (lineIterator.hasNext()) {
                String next = lineIterator.next();
                if (next.startsWith(TAG_PREFIX)) {
                    arrayList2.add(next);
                }
                if (next.startsWith(TAG_PLAYLIST_TYPE)) {
                    next = parseStringAttr(next, REGEX_PLAYLIST_TYPE, hashMap);
                    if (PLAYSUBTYPE.VOD.equals(next)) {
                        i3 = 1;
                    } else if ("EVENT".equals(next)) {
                        i3 = 2;
                    }
                } else if (next.startsWith(TAG_START)) {
                    j = (long) (parseDoubleAttr(next, REGEX_TIME_OFFSET) * 1000000.0d);
                } else if (next.startsWith(TAG_INIT_SEGMENT)) {
                    String parseStringAttr = parseStringAttr(next, REGEX_URI, hashMap);
                    next = parseOptionalStringAttr(next, REGEX_ATTR_BYTERANGE, hashMap);
                    if (next != null) {
                        r5 = next.split("@");
                        j7 = Long.parseLong(r5[i]);
                        if (r5.length > i2) {
                            j5 = Long.parseLong(r5[i2]);
                        }
                    }
                    segment = new Segment(parseStringAttr, j5, j7);
                    j5 = 0;
                    j7 = -1;
                } else if (next.startsWith(TAG_TARGET_DURATION)) {
                    j2 = 1000000 * ((long) parseIntAttr(next, REGEX_TARGET_DURATION));
                } else if (next.startsWith(TAG_MEDIA_SEQUENCE)) {
                    j6 = parseLongAttr(next, REGEX_MEDIA_SEQUENCE);
                    j4 = j6;
                } else if (next.startsWith(TAG_VERSION)) {
                    i5 = parseIntAttr(next, REGEX_VERSION);
                } else {
                    String parseOptionalStringAttr;
                    if (next.startsWith(TAG_DEFINE)) {
                        parseOptionalStringAttr = parseOptionalStringAttr(next, REGEX_IMPORT, hashMap);
                        if (parseOptionalStringAttr != null) {
                            next = (String) hlsMasterPlaylist2.variableDefinitions.get(parseOptionalStringAttr);
                            if (next != null) {
                                hashMap.put(parseOptionalStringAttr, next);
                            }
                        } else {
                            hashMap.put(parseStringAttr(next, REGEX_NAME, hashMap), parseStringAttr(next, REGEX_VALUE, hashMap));
                        }
                    } else if (next.startsWith(TAG_MEDIA_DURATION)) {
                        r9 = (long) (parseDoubleAttr(next, REGEX_MEDIA_DURATION) * 1000000.0d);
                        str2 = parseOptionalStringAttr(next, REGEX_MEDIA_TITLE, "", hashMap);
                        j9 = r9;
                    } else if (next.startsWith(TAG_KEY)) {
                        parseOptionalStringAttr = parseStringAttr(next, REGEX_METHOD, hashMap);
                        String parseOptionalStringAttr2 = parseOptionalStringAttr(next, REGEX_KEYFORMAT, KEYFORMAT_IDENTITY, hashMap);
                        if (METHOD_NONE.equals(parseOptionalStringAttr)) {
                            treeMap.clear();
                            drmInitData2 = null;
                            str4 = null;
                            str5 = null;
                        } else {
                            String parseOptionalStringAttr3 = parseOptionalStringAttr(next, REGEX_IV, hashMap);
                            if (!KEYFORMAT_IDENTITY.equals(parseOptionalStringAttr2)) {
                                Object parsePlayReadySchemeData;
                                if (str3 == null) {
                                    if (!METHOD_SAMPLE_AES_CENC.equals(parseOptionalStringAttr)) {
                                        if (!METHOD_SAMPLE_AES_CTR.equals(parseOptionalStringAttr)) {
                                            str3 = C.CENC_TYPE_cbcs;
                                        }
                                    }
                                    str3 = C.CENC_TYPE_cenc;
                                }
                                if (KEYFORMAT_PLAYREADY.equals(parseOptionalStringAttr2)) {
                                    parsePlayReadySchemeData = parsePlayReadySchemeData(next, hashMap);
                                } else {
                                    parsePlayReadySchemeData = parseWidevineSchemeData(next, parseOptionalStringAttr2, hashMap);
                                }
                                if (parsePlayReadySchemeData != null) {
                                    treeMap.put(parseOptionalStringAttr2, parsePlayReadySchemeData);
                                    str5 = parseOptionalStringAttr3;
                                    drmInitData2 = null;
                                    str4 = null;
                                }
                            } else if (METHOD_AES_128.equals(parseOptionalStringAttr)) {
                                str4 = parseStringAttr(next, REGEX_URI, hashMap);
                                str5 = parseOptionalStringAttr3;
                            }
                            str5 = parseOptionalStringAttr3;
                            str4 = null;
                        }
                    } else if (next.startsWith(TAG_BYTERANGE)) {
                        r5 = parseStringAttr(next, REGEX_BYTERANGE, hashMap).split("@");
                        j7 = Long.parseLong(r5[i]);
                        if (r5.length > i2) {
                            j5 = Long.parseLong(r5[i2]);
                        }
                    } else if (next.startsWith(TAG_DISCONTINUITY_SEQUENCE)) {
                        i4 = Integer.parseInt(next.substring(next.indexOf(58) + i2));
                        z3 = true;
                    } else if (next.equals(TAG_DISCONTINUITY)) {
                        i6++;
                    } else if (next.startsWith(TAG_PROGRAM_DATE_TIME)) {
                        if (j3 == 0) {
                            j3 = C.msToUs(Util.parseXsDateTime(next.substring(next.indexOf(58) + i2))) - j8;
                        }
                    } else if (next.equals(TAG_GAP)) {
                        z5 = true;
                    } else if (next.equals(TAG_INDEPENDENT_SEGMENTS)) {
                        z2 = true;
                    } else if (next.equals(TAG_ENDLIST)) {
                        z4 = true;
                    } else if (!next.startsWith("#")) {
                        TreeMap treeMap2;
                        DrmInitData drmInitData3;
                        String toHexString = str4 == null ? null : str5 != null ? str5 : Long.toHexString(j6);
                        r9 = j6 + 1;
                        if (j7 == -1) {
                            j5 = 0;
                        }
                        if (drmInitData2 != null || treeMap.isEmpty()) {
                            treeMap2 = treeMap;
                            drmInitData3 = drmInitData2;
                        } else {
                            SchemeData[] schemeDataArr = (SchemeData[]) treeMap.values().toArray(new SchemeData[i]);
                            drmInitData3 = new DrmInitData(str3, schemeDataArr);
                            if (drmInitData == null) {
                                SchemeData[] schemeDataArr2 = new SchemeData[schemeDataArr.length];
                                i2 = 0;
                                while (i2 < schemeDataArr.length) {
                                    treeMap2 = treeMap;
                                    schemeDataArr2[i2] = schemeDataArr[i2].copyWithData(null);
                                    i2++;
                                    treeMap = treeMap2;
                                }
                                treeMap2 = treeMap;
                                drmInitData = new DrmInitData(str3, schemeDataArr2);
                            } else {
                                treeMap2 = treeMap;
                            }
                        }
                        arrayList.add(new Segment(replaceVariableReferences(next, hashMap), segment, str2, j9, i6, j8, drmInitData3, str4, toHexString, j5, j7, z5));
                        j8 += j9;
                        str2 = "";
                        if (j7 != -1) {
                            j5 += j7;
                        }
                        j6 = r9;
                        drmInitData2 = drmInitData3;
                        j7 = -1;
                        treeMap = treeMap2;
                        hlsMasterPlaylist2 = hlsMasterPlaylist;
                        i = 0;
                        i2 = 1;
                        z5 = false;
                    }
                    treeMap = treeMap;
                    hlsMasterPlaylist2 = hlsMasterPlaylist;
                    i = 0;
                    i2 = 1;
                }
            }
            break loop0;
        }
        return new HlsMediaPlaylist(i3, str, arrayList2, j, j3, z3, i4, j4, i5, j2, z2, z4, j3 != 0, drmInitData, arrayList);
    }

    @Nullable
    private static SchemeData parsePlayReadySchemeData(String str, Map<String, String> map) throws ParserException {
        if (!"1".equals(parseOptionalStringAttr(str, REGEX_KEYFORMATVERSIONS, "1", map))) {
            return null;
        }
        str = parseStringAttr(str, REGEX_URI, map);
        return new SchemeData(C.PLAYREADY_UUID, MimeTypes.VIDEO_MP4, PsshAtomUtil.buildPsshAtom(C.PLAYREADY_UUID, Base64.decode(str.substring(str.indexOf(44)), null)));
    }

    @Nullable
    private static SchemeData parseWidevineSchemeData(String str, String str2, Map<String, String> map) throws ParserException {
        if (KEYFORMAT_WIDEVINE_PSSH_BINARY.equals(str2)) {
            str = parseStringAttr(str, REGEX_URI, map);
            return new SchemeData(C.WIDEVINE_UUID, MimeTypes.VIDEO_MP4, Base64.decode(str.substring(str.indexOf(44)), 0));
        } else if (KEYFORMAT_WIDEVINE_PSSH_JSON.equals(str2) == null) {
            return null;
        } else {
            try {
                return new SchemeData(C.WIDEVINE_UUID, "hls", str.getBytes("UTF-8"));
            } catch (Throwable e) {
                throw new ParserException(e);
            }
        }
    }

    private static int parseIntAttr(String str, Pattern pattern) throws ParserException {
        return Integer.parseInt(parseStringAttr(str, pattern, Collections.emptyMap()));
    }

    private static long parseLongAttr(String str, Pattern pattern) throws ParserException {
        return Long.parseLong(parseStringAttr(str, pattern, Collections.emptyMap()));
    }

    private static double parseDoubleAttr(String str, Pattern pattern) throws ParserException {
        return Double.parseDouble(parseStringAttr(str, pattern, Collections.emptyMap()));
    }

    private static String parseStringAttr(String str, Pattern pattern, Map<String, String> map) throws ParserException {
        map = parseOptionalStringAttr(str, pattern, map);
        if (map != null) {
            return map;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Couldn't match ");
        stringBuilder.append(pattern.pattern());
        stringBuilder.append(" in ");
        stringBuilder.append(str);
        throw new ParserException(stringBuilder.toString());
    }

    @Nullable
    private static String parseOptionalStringAttr(String str, Pattern pattern, Map<String, String> map) {
        return parseOptionalStringAttr(str, pattern, null, map);
    }

    private static String parseOptionalStringAttr(String str, Pattern pattern, String str2, Map<String, String> map) {
        str = pattern.matcher(str);
        if (str.find() != null) {
            str2 = str.group(1);
        }
        if (map.isEmpty() == null) {
            return str2 == null ? str2 : replaceVariableReferences(str2, map);
        } else {
            return str2;
        }
    }

    private static String replaceVariableReferences(String str, Map<String, String> map) {
        str = REGEX_VARIABLE_REFERENCE.matcher(str);
        StringBuffer stringBuffer = new StringBuffer();
        while (str.find()) {
            String group = str.group(1);
            if (map.containsKey(group)) {
                str.appendReplacement(stringBuffer, Matcher.quoteReplacement((String) map.get(group)));
            }
        }
        str.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    private static boolean parseOptionalBooleanAttribute(String str, Pattern pattern, boolean z) {
        str = pattern.matcher(str);
        return str.find() != null ? str.group(1).equals(BOOLEAN_TRUE) : z;
    }

    private static Pattern compileBooleanAttrPattern(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append("=(");
        stringBuilder.append(BOOLEAN_FALSE);
        stringBuilder.append("|");
        stringBuilder.append(BOOLEAN_TRUE);
        stringBuilder.append(")");
        return Pattern.compile(stringBuilder.toString());
    }
}
