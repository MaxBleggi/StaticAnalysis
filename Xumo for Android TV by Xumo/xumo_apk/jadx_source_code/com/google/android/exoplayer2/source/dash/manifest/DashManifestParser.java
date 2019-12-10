package com.google.android.exoplayer2.source.dash.manifest;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Pair;
import android.util.Xml;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmInitData.SchemeData;
import com.google.android.exoplayer2.metadata.emsg.EventMessage;
import com.google.android.exoplayer2.source.dash.manifest.SegmentBase.SegmentList;
import com.google.android.exoplayer2.source.dash.manifest.SegmentBase.SegmentTemplate;
import com.google.android.exoplayer2.source.dash.manifest.SegmentBase.SegmentTimelineElement;
import com.google.android.exoplayer2.source.dash.manifest.SegmentBase.SingleSegmentBase;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.upstream.ParsingLoadable.Parser;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.UriUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.util.XmlPullParserUtil;
import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public class DashManifestParser extends DefaultHandler implements Parser<DashManifest> {
    private static final Pattern CEA_608_ACCESSIBILITY_PATTERN = Pattern.compile("CC([1-4])=.*");
    private static final Pattern CEA_708_ACCESSIBILITY_PATTERN = Pattern.compile("([1-9]|[1-5][0-9]|6[0-3])=.*");
    private static final Pattern FRAME_RATE_PATTERN = Pattern.compile("(\\d+)(?:/(\\d+))?");
    private static final String TAG = "MpdParser";
    private final String contentId;
    private final XmlPullParserFactory xmlParserFactory;

    protected static final class RepresentationInfo {
        public final String baseUrl;
        public final ArrayList<SchemeData> drmSchemeDatas;
        public final String drmSchemeType;
        public final Format format;
        public final ArrayList<Descriptor> inbandEventStreams;
        public final long revisionId;
        public final SegmentBase segmentBase;

        public RepresentationInfo(Format format, String str, SegmentBase segmentBase, String str2, ArrayList<SchemeData> arrayList, ArrayList<Descriptor> arrayList2, long j) {
            this.format = format;
            this.baseUrl = str;
            this.segmentBase = segmentBase;
            this.drmSchemeType = str2;
            this.drmSchemeDatas = arrayList;
            this.inbandEventStreams = arrayList2;
            this.revisionId = j;
        }
    }

    protected void parseAdaptationSetChild(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
    }

    public DashManifestParser() {
        this(null);
    }

    public DashManifestParser(String str) {
        this.contentId = str;
        try {
            this.xmlParserFactory = XmlPullParserFactory.newInstance();
        } catch (String str2) {
            throw new RuntimeException("Couldn't create XmlPullParserFactory instance", str2);
        }
    }

    public DashManifest parse(Uri uri, InputStream inputStream) throws IOException {
        try {
            XmlPullParser newPullParser = this.xmlParserFactory.newPullParser();
            newPullParser.setInput(inputStream, null);
            if (newPullParser.next() == 2 && "MPD".equals(newPullParser.getName()) != null) {
                return parseMediaPresentationDescription(newPullParser, uri.toString());
            }
            throw new ParserException("inputStream does not contain a valid media presentation description");
        } catch (Throwable e) {
            throw new ParserException(e);
        }
    }

    protected DashManifest parseMediaPresentationDescription(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser2 = xmlPullParser;
        long parseDateTime = parseDateTime(xmlPullParser2, "availabilityStartTime", C.TIME_UNSET);
        long parseDuration = parseDuration(xmlPullParser2, "mediaPresentationDuration", C.TIME_UNSET);
        long parseDuration2 = parseDuration(xmlPullParser2, "minBufferTime", C.TIME_UNSET);
        String attributeValue = xmlPullParser2.getAttributeValue(null, "type");
        Object obj = null;
        boolean z = attributeValue != null && "dynamic".equals(attributeValue);
        long parseDuration3 = z ? parseDuration(xmlPullParser2, "minimumUpdatePeriod", C.TIME_UNSET) : C.TIME_UNSET;
        long parseDuration4 = z ? parseDuration(xmlPullParser2, "timeShiftBufferDepth", C.TIME_UNSET) : C.TIME_UNSET;
        long parseDuration5 = z ? parseDuration(xmlPullParser2, "suggestedPresentationDelay", C.TIME_UNSET) : C.TIME_UNSET;
        long parseDateTime2 = parseDateTime(xmlPullParser2, "publishTime", C.TIME_UNSET);
        List arrayList = new ArrayList();
        String str2 = str;
        long j = z ? C.TIME_UNSET : 0;
        Object obj2 = null;
        Uri uri = null;
        UtcTimingElement utcTimingElement = null;
        while (true) {
            long j2;
            xmlPullParser.next();
            long j3 = parseDuration4;
            if (!XmlPullParserUtil.isStartTag(xmlPullParser2, "BaseURL")) {
                if (XmlPullParserUtil.isStartTag(xmlPullParser2, "UTCTiming")) {
                    j2 = parseDuration3;
                    utcTimingElement = parseUtcTiming(xmlPullParser);
                } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, HttpRequest.HEADER_LOCATION)) {
                    j2 = parseDuration3;
                    uri = Uri.parse(xmlPullParser.nextText());
                } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "Period") && r21 == null) {
                    Object obj3 = obj;
                    Pair parsePeriod = parsePeriod(xmlPullParser2, str2, j);
                    long j4 = j;
                    Period period = (Period) parsePeriod.first;
                    j2 = parseDuration3;
                    if (period.startMs != C.TIME_UNSET) {
                        long longValue = ((Long) parsePeriod.second).longValue();
                        if (longValue == C.TIME_UNSET) {
                            longValue = C.TIME_UNSET;
                        } else {
                            longValue += period.startMs;
                        }
                        arrayList.add(period);
                        j = longValue;
                        obj = obj3;
                    } else if (z) {
                        obj = obj3;
                        j = j4;
                        obj2 = 1;
                    } else {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unable to determine start of period ");
                        stringBuilder.append(arrayList.size());
                        throw new ParserException(stringBuilder.toString());
                    }
                }
                if (!XmlPullParserUtil.isEndTag(xmlPullParser2, "MPD")) {
                    break;
                }
                parseDuration4 = j3;
                parseDuration3 = j2;
            } else if (obj == null) {
                str2 = parseBaseUrl(xmlPullParser2, str2);
                j2 = parseDuration3;
                obj = 1;
                if (!XmlPullParserUtil.isEndTag(xmlPullParser2, "MPD")) {
                    break;
                }
                parseDuration4 = j3;
                parseDuration3 = j2;
            }
            j2 = parseDuration3;
            obj = obj;
            j = j;
            if (!XmlPullParserUtil.isEndTag(xmlPullParser2, "MPD")) {
                break;
            }
            parseDuration4 = j3;
            parseDuration3 = j2;
        }
        if (parseDuration == C.TIME_UNSET) {
            if (j != C.TIME_UNSET) {
                parseDuration = j;
            } else if (!z) {
                throw new ParserException("Unable to determine duration of static manifest.");
            }
        }
        if (arrayList.isEmpty()) {
            throw new ParserException("No periods found.");
        }
        return buildMediaPresentationDescription(parseDateTime, parseDuration, parseDuration2, z, j2, j3, parseDuration5, parseDateTime2, utcTimingElement, uri, arrayList);
    }

    protected DashManifest buildMediaPresentationDescription(long j, long j2, long j3, boolean z, long j4, long j5, long j6, long j7, UtcTimingElement utcTimingElement, Uri uri, List<Period> list) {
        return new DashManifest(j, j2, j3, z, j4, j5, j6, j7, utcTimingElement, uri, list);
    }

    protected UtcTimingElement parseUtcTiming(XmlPullParser xmlPullParser) {
        return buildUtcTimingElement(xmlPullParser.getAttributeValue(null, "schemeIdUri"), xmlPullParser.getAttributeValue(null, "value"));
    }

    protected UtcTimingElement buildUtcTimingElement(String str, String str2) {
        return new UtcTimingElement(str, str2);
    }

    protected Pair<Period, Long> parsePeriod(XmlPullParser xmlPullParser, String str, long j) throws XmlPullParserException, IOException {
        String attributeValue = xmlPullParser.getAttributeValue(null, "id");
        long parseDuration = parseDuration(xmlPullParser, "start", j);
        j = parseDuration(xmlPullParser, "duration", C.TIME_UNSET);
        List arrayList = new ArrayList();
        List arrayList2 = new ArrayList();
        Object obj = null;
        SegmentBase segmentBase = null;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "BaseURL")) {
                if (obj == null) {
                    str = parseBaseUrl(xmlPullParser, str);
                    obj = 1;
                }
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "AdaptationSet")) {
                arrayList.add(parseAdaptationSet(xmlPullParser, str, segmentBase));
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "EventStream")) {
                arrayList2.add(parseEventStream(xmlPullParser));
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentBase")) {
                segmentBase = parseSegmentBase(xmlPullParser, null);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentList")) {
                segmentBase = parseSegmentList(xmlPullParser, null);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentTemplate")) {
                segmentBase = parseSegmentTemplate(xmlPullParser, null);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "Period"));
        return Pair.create(buildPeriod(attributeValue, parseDuration, arrayList, arrayList2), Long.valueOf(j));
    }

    protected Period buildPeriod(String str, long j, List<AdaptationSet> list, List<EventStream> list2) {
        return new Period(str, j, list, list2);
    }

    protected AdaptationSet parseAdaptationSet(XmlPullParser xmlPullParser, String str, SegmentBase segmentBase) throws XmlPullParserException, IOException {
        List list;
        ArrayList arrayList;
        ArrayList arrayList2;
        ArrayList arrayList3;
        ArrayList arrayList4;
        int i;
        DashManifestParser dashManifestParser = this;
        XmlPullParser xmlPullParser2 = xmlPullParser;
        int parseInt = parseInt(xmlPullParser2, "id", -1);
        int parseContentType = parseContentType(xmlPullParser);
        String str2 = null;
        String attributeValue = xmlPullParser2.getAttributeValue(null, "mimeType");
        String attributeValue2 = xmlPullParser2.getAttributeValue(null, "codecs");
        int parseInt2 = parseInt(xmlPullParser2, "width", -1);
        int parseInt3 = parseInt(xmlPullParser2, "height", -1);
        float parseFrameRate = parseFrameRate(xmlPullParser2, -1.0f);
        int parseInt4 = parseInt(xmlPullParser2, "audioSamplingRate", -1);
        String attributeValue3 = xmlPullParser2.getAttributeValue(null, JsonKey.LANG);
        String attributeValue4 = xmlPullParser2.getAttributeValue(null, "label");
        ArrayList arrayList5 = new ArrayList();
        ArrayList arrayList6 = new ArrayList();
        ArrayList arrayList7 = new ArrayList();
        ArrayList arrayList8 = new ArrayList();
        ArrayList arrayList9 = new ArrayList();
        String str3 = str;
        SegmentBase segmentBase2 = segmentBase;
        int i2 = parseContentType;
        String str4 = attributeValue3;
        String str5 = null;
        Object obj = null;
        int i3 = 0;
        int i4 = -1;
        while (true) {
            String parseBaseUrl;
            String str6;
            String str7;
            XmlPullParser xmlPullParser3;
            List list2;
            xmlPullParser.next();
            int i5;
            if (XmlPullParserUtil.isStartTag(xmlPullParser2, "BaseURL")) {
                if (obj == null) {
                    parseBaseUrl = parseBaseUrl(xmlPullParser2, str3);
                    str6 = str4;
                    list = arrayList9;
                    arrayList = arrayList8;
                    arrayList2 = arrayList7;
                    arrayList3 = arrayList6;
                    arrayList4 = arrayList5;
                    str7 = str2;
                    xmlPullParser3 = xmlPullParser2;
                    obj = 1;
                }
                i5 = i2;
                str6 = str4;
                parseBaseUrl = str3;
                list = arrayList9;
                arrayList = arrayList8;
                arrayList2 = arrayList7;
                arrayList3 = arrayList6;
                arrayList4 = arrayList5;
                str7 = str2;
                xmlPullParser3 = xmlPullParser2;
                i = i5;
                if (!XmlPullParserUtil.isEndTag(xmlPullParser3, "AdaptationSet")) {
                    break;
                }
                xmlPullParser2 = xmlPullParser3;
                arrayList6 = arrayList3;
                i2 = i;
                str3 = parseBaseUrl;
                arrayList8 = arrayList;
                arrayList7 = arrayList2;
                arrayList5 = arrayList4;
                str2 = str7;
                list2 = list;
                str4 = str6;
            } else {
                if (!XmlPullParserUtil.isStartTag(xmlPullParser2, "ContentProtection")) {
                    if (XmlPullParserUtil.isStartTag(xmlPullParser2, "ContentComponent")) {
                        str6 = checkLanguageConsistency(str4, xmlPullParser2.getAttributeValue(str2, JsonKey.LANG));
                        parseBaseUrl = str3;
                        list = arrayList9;
                        arrayList = arrayList8;
                        arrayList2 = arrayList7;
                        arrayList3 = arrayList6;
                        arrayList4 = arrayList5;
                        str7 = str2;
                        xmlPullParser3 = xmlPullParser2;
                        i = checkContentTypeConsistency(i2, parseContentType(xmlPullParser));
                    } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "Role")) {
                        i3 |= parseRole(xmlPullParser);
                    } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "AudioChannelConfiguration")) {
                        i4 = parseAudioChannelConfiguration(xmlPullParser);
                    } else {
                        if (XmlPullParserUtil.isStartTag(xmlPullParser2, "Accessibility")) {
                            arrayList7.add(parseDescriptor(xmlPullParser2, "Accessibility"));
                        } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "SupplementalProperty")) {
                            arrayList8.add(parseDescriptor(xmlPullParser2, "SupplementalProperty"));
                        } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "Representation")) {
                            int i6 = i2;
                            str6 = str4;
                            parseBaseUrl = str3;
                            ArrayList arrayList10 = arrayList9;
                            arrayList = arrayList8;
                            arrayList2 = arrayList7;
                            r36 = arrayList6;
                            arrayList4 = arrayList5;
                            str7 = str2;
                            RepresentationInfo parseRepresentation = parseRepresentation(xmlPullParser, str3, attributeValue4, attributeValue, attributeValue2, parseInt2, parseInt3, parseFrameRate, i4, parseInt4, str6, i3, arrayList2, segmentBase2);
                            int checkContentTypeConsistency = checkContentTypeConsistency(i6, getContentType(parseRepresentation.format));
                            list = arrayList10;
                            list.add(parseRepresentation);
                            i = checkContentTypeConsistency;
                            arrayList3 = r36;
                            xmlPullParser3 = xmlPullParser;
                        } else {
                            SegmentBase parseSegmentBase;
                            i5 = i2;
                            str6 = str4;
                            parseBaseUrl = str3;
                            list = arrayList9;
                            arrayList = arrayList8;
                            arrayList2 = arrayList7;
                            r36 = arrayList6;
                            arrayList4 = arrayList5;
                            str7 = str2;
                            xmlPullParser3 = xmlPullParser;
                            if (XmlPullParserUtil.isStartTag(xmlPullParser3, "SegmentBase")) {
                                parseSegmentBase = parseSegmentBase(xmlPullParser3, (SingleSegmentBase) segmentBase2);
                            } else if (XmlPullParserUtil.isStartTag(xmlPullParser3, "SegmentList")) {
                                parseSegmentBase = parseSegmentList(xmlPullParser3, (SegmentList) segmentBase2);
                            } else if (XmlPullParserUtil.isStartTag(xmlPullParser3, "SegmentTemplate")) {
                                parseSegmentBase = parseSegmentTemplate(xmlPullParser3, (SegmentTemplate) segmentBase2);
                            } else {
                                if (XmlPullParserUtil.isStartTag(xmlPullParser3, "InbandEventStream")) {
                                    arrayList3 = r36;
                                    arrayList3.add(parseDescriptor(xmlPullParser3, "InbandEventStream"));
                                } else {
                                    arrayList3 = r36;
                                    if (XmlPullParserUtil.isStartTag(xmlPullParser)) {
                                        parseAdaptationSetChild(xmlPullParser);
                                    }
                                }
                                i = i5;
                            }
                            segmentBase2 = parseSegmentBase;
                            i = i5;
                            arrayList3 = r36;
                        }
                        i5 = i2;
                        str6 = str4;
                        parseBaseUrl = str3;
                        list = arrayList9;
                        arrayList = arrayList8;
                        arrayList2 = arrayList7;
                        arrayList3 = arrayList6;
                        arrayList4 = arrayList5;
                        str7 = str2;
                        xmlPullParser3 = xmlPullParser2;
                        i = i5;
                    }
                    if (!XmlPullParserUtil.isEndTag(xmlPullParser3, "AdaptationSet")) {
                        break;
                    }
                    xmlPullParser2 = xmlPullParser3;
                    arrayList6 = arrayList3;
                    i2 = i;
                    str3 = parseBaseUrl;
                    arrayList8 = arrayList;
                    arrayList7 = arrayList2;
                    arrayList5 = arrayList4;
                    str2 = str7;
                    list2 = list;
                    str4 = str6;
                } else {
                    Pair parseContentProtection = parseContentProtection(xmlPullParser);
                    if (parseContentProtection.first != null) {
                        str5 = (String) parseContentProtection.first;
                    }
                    if (parseContentProtection.second != null) {
                        arrayList5.add(parseContentProtection.second);
                    }
                }
                str6 = str4;
                parseBaseUrl = str3;
                Object obj2 = arrayList9;
                arrayList = arrayList8;
                arrayList2 = arrayList7;
                arrayList3 = arrayList6;
                arrayList4 = arrayList5;
                str7 = str2;
                xmlPullParser3 = xmlPullParser2;
            }
            i = i2;
            if (!XmlPullParserUtil.isEndTag(xmlPullParser3, "AdaptationSet")) {
                break;
            }
            xmlPullParser2 = xmlPullParser3;
            arrayList6 = arrayList3;
            i2 = i;
            str3 = parseBaseUrl;
            arrayList8 = arrayList;
            arrayList7 = arrayList2;
            arrayList5 = arrayList4;
            str2 = str7;
            list2 = list;
            str4 = str6;
        }
        List arrayList11 = new ArrayList(list.size());
        for (int i7 = 0; i7 < list.size(); i7++) {
            arrayList11.add(buildRepresentation((RepresentationInfo) list.get(i7), dashManifestParser.contentId, str5, arrayList4, arrayList3));
        }
        return buildAdaptationSet(parseInt, i, arrayList11, arrayList2, arrayList);
    }

    protected AdaptationSet buildAdaptationSet(int i, int i2, List<Representation> list, List<Descriptor> list2, List<Descriptor> list3) {
        return new AdaptationSet(i, i2, list, list2, list3);
    }

    protected int parseContentType(XmlPullParser xmlPullParser) {
        xmlPullParser = xmlPullParser.getAttributeValue(null, JsonKey.CONTENT_TYPE);
        if (TextUtils.isEmpty(xmlPullParser)) {
            return -1;
        }
        if (MimeTypes.BASE_TYPE_AUDIO.equals(xmlPullParser)) {
            return 1;
        }
        if (MimeTypes.BASE_TYPE_VIDEO.equals(xmlPullParser)) {
            return 2;
        }
        if (MimeTypes.BASE_TYPE_TEXT.equals(xmlPullParser) != null) {
            return 3;
        }
        return -1;
    }

    protected int getContentType(Format format) {
        format = format.sampleMimeType;
        if (TextUtils.isEmpty(format)) {
            return -1;
        }
        if (MimeTypes.isVideo(format)) {
            return 2;
        }
        if (MimeTypes.isAudio(format)) {
            return 1;
        }
        if (mimeTypeIsRawText(format) != null) {
            return 3;
        }
        return -1;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected android.util.Pair<java.lang.String, com.google.android.exoplayer2.drm.DrmInitData.SchemeData> parseContentProtection(org.xmlpull.v1.XmlPullParser r17) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
        r16 = this;
        r0 = r17;
        r1 = "schemeIdUri";
        r2 = 0;
        r1 = r0.getAttributeValue(r2, r1);
        r3 = 1;
        r4 = 0;
        if (r1 == 0) goto L_0x0095;
    L_0x000d:
        r1 = com.google.android.exoplayer2.util.Util.toLowerInvariant(r1);
        r5 = -1;
        r6 = r1.hashCode();
        r7 = 489446379; // 0x1d2c5beb float:2.281153E-21 double:2.418186413E-315;
        if (r6 == r7) goto L_0x003a;
    L_0x001b:
        r7 = 755418770; // 0x2d06c692 float:7.66111E-12 double:3.732264625E-315;
        if (r6 == r7) goto L_0x0030;
    L_0x0020:
        r7 = 1812765994; // 0x6c0c9d2a float:6.799672E26 double:8.956254016E-315;
        if (r6 == r7) goto L_0x0026;
    L_0x0025:
        goto L_0x0044;
    L_0x0026:
        r6 = "urn:mpeg:dash:mp4protection:2011";
        r1 = r1.equals(r6);
        if (r1 == 0) goto L_0x0044;
    L_0x002e:
        r1 = 0;
        goto L_0x0045;
    L_0x0030:
        r6 = "urn:uuid:edef8ba9-79d6-4ace-a3c8-27dcd51d21ed";
        r1 = r1.equals(r6);
        if (r1 == 0) goto L_0x0044;
    L_0x0038:
        r1 = 2;
        goto L_0x0045;
    L_0x003a:
        r6 = "urn:uuid:9a04f079-9840-4286-ab92-e65be0885f95";
        r1 = r1.equals(r6);
        if (r1 == 0) goto L_0x0044;
    L_0x0042:
        r1 = 1;
        goto L_0x0045;
    L_0x0044:
        r1 = -1;
    L_0x0045:
        switch(r1) {
            case 0: goto L_0x0050;
            case 1: goto L_0x004c;
            case 2: goto L_0x0049;
            default: goto L_0x0048;
        };
    L_0x0048:
        goto L_0x0095;
    L_0x0049:
        r1 = com.google.android.exoplayer2.C.WIDEVINE_UUID;
        goto L_0x004e;
    L_0x004c:
        r1 = com.google.android.exoplayer2.C.PLAYREADY_UUID;
    L_0x004e:
        r5 = r2;
        goto L_0x0097;
    L_0x0050:
        r1 = "value";
        r1 = r0.getAttributeValue(r2, r1);
        r5 = "default_KID";
        r5 = com.google.android.exoplayer2.util.XmlPullParserUtil.getAttributeValueIgnorePrefix(r0, r5);
        r6 = android.text.TextUtils.isEmpty(r5);
        if (r6 != 0) goto L_0x0090;
    L_0x0062:
        r6 = "00000000-0000-0000-0000-000000000000";
        r6 = r6.equals(r5);
        if (r6 != 0) goto L_0x0090;
    L_0x006a:
        r6 = "\\s+";
        r5 = r5.split(r6);
        r6 = r5.length;
        r6 = new java.util.UUID[r6];
        r7 = 0;
    L_0x0074:
        r8 = r5.length;
        if (r7 >= r8) goto L_0x0082;
    L_0x0077:
        r8 = r5[r7];
        r8 = java.util.UUID.fromString(r8);
        r6[r7] = r8;
        r7 = r7 + 1;
        goto L_0x0074;
    L_0x0082:
        r5 = com.google.android.exoplayer2.C.COMMON_PSSH_UUID;
        r5 = com.google.android.exoplayer2.extractor.mp4.PsshAtomUtil.buildPsshAtom(r5, r6, r2);
        r6 = com.google.android.exoplayer2.C.COMMON_PSSH_UUID;
        r7 = r2;
        r8 = 0;
        r15 = r6;
        r6 = r1;
        r1 = r15;
        goto L_0x009a;
    L_0x0090:
        r6 = r1;
        r1 = r2;
        r5 = r1;
        r7 = r5;
        goto L_0x0099;
    L_0x0095:
        r1 = r2;
        r5 = r1;
    L_0x0097:
        r6 = r5;
        r7 = r6;
    L_0x0099:
        r8 = 0;
    L_0x009a:
        r17.next();
        r9 = "ms:laurl";
        r9 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r9);
        if (r9 == 0) goto L_0x00b1;
    L_0x00a5:
        r7 = "licenseUrl";
        r7 = r0.getAttributeValue(r2, r7);
    L_0x00ab:
        r10 = r1;
        r13 = r5;
    L_0x00ad:
        r11 = r7;
        r14 = r8;
        goto L_0x011d;
    L_0x00b1:
        r9 = "widevine:license";
        r9 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r9);
        if (r9 == 0) goto L_0x00cd;
    L_0x00b9:
        r8 = "robustness_level";
        r8 = r0.getAttributeValue(r2, r8);
        if (r8 == 0) goto L_0x00cb;
    L_0x00c1:
        r9 = "HW";
        r8 = r8.startsWith(r9);
        if (r8 == 0) goto L_0x00cb;
    L_0x00c9:
        r8 = 1;
        goto L_0x00ab;
    L_0x00cb:
        r8 = 0;
        goto L_0x00ab;
    L_0x00cd:
        if (r5 != 0) goto L_0x00ab;
    L_0x00cf:
        r9 = "pssh";
        r9 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTagIgnorePrefix(r0, r9);
        r10 = 4;
        if (r9 == 0) goto L_0x00f8;
    L_0x00d8:
        r9 = r17.next();
        if (r9 != r10) goto L_0x00f8;
    L_0x00de:
        r1 = r17.getText();
        r1 = android.util.Base64.decode(r1, r4);
        r5 = com.google.android.exoplayer2.extractor.mp4.PsshAtomUtil.parseUuid(r1);
        if (r5 != 0) goto L_0x00f5;
    L_0x00ec:
        r1 = "MpdParser";
        r9 = "Skipping malformed cenc:pssh data";
        com.google.android.exoplayer2.util.Log.w(r1, r9);
        r13 = r2;
        goto L_0x00f6;
    L_0x00f5:
        r13 = r1;
    L_0x00f6:
        r10 = r5;
        goto L_0x00ad;
    L_0x00f8:
        r9 = com.google.android.exoplayer2.C.PLAYREADY_UUID;
        r9 = r9.equals(r1);
        if (r9 == 0) goto L_0x00ab;
    L_0x0100:
        r9 = "mspr:pro";
        r9 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r9);
        if (r9 == 0) goto L_0x00ab;
    L_0x0108:
        r9 = r17.next();
        if (r9 != r10) goto L_0x00ab;
    L_0x010e:
        r5 = com.google.android.exoplayer2.C.PLAYREADY_UUID;
        r9 = r17.getText();
        r9 = android.util.Base64.decode(r9, r4);
        r5 = com.google.android.exoplayer2.extractor.mp4.PsshAtomUtil.buildPsshAtom(r5, r9);
        goto L_0x00ab;
    L_0x011d:
        r1 = "ContentProtection";
        r1 = com.google.android.exoplayer2.util.XmlPullParserUtil.isEndTag(r0, r1);
        if (r1 == 0) goto L_0x0136;
    L_0x0125:
        if (r10 == 0) goto L_0x0130;
    L_0x0127:
        r0 = new com.google.android.exoplayer2.drm.DrmInitData$SchemeData;
        r12 = "video/mp4";
        r9 = r0;
        r9.<init>(r10, r11, r12, r13, r14);
        goto L_0x0131;
    L_0x0130:
        r0 = r2;
    L_0x0131:
        r0 = android.util.Pair.create(r6, r0);
        return r0;
    L_0x0136:
        r1 = r10;
        r7 = r11;
        r5 = r13;
        r8 = r14;
        goto L_0x009a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseContentProtection(org.xmlpull.v1.XmlPullParser):android.util.Pair<java.lang.String, com.google.android.exoplayer2.drm.DrmInitData$SchemeData>");
    }

    protected int parseRole(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        String parseString = parseString(xmlPullParser, "schemeIdUri", null);
        String parseString2 = parseString(xmlPullParser, "value", null);
        do {
            xmlPullParser.next();
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "Role"));
        return ("urn:mpeg:dash:role:2011".equals(parseString) == null || "main".equals(parseString2) == null) ? null : 1;
    }

    protected RepresentationInfo parseRepresentation(XmlPullParser xmlPullParser, String str, String str2, String str3, String str4, int i, int i2, float f, int i3, int i4, String str5, int i5, List<Descriptor> list, SegmentBase segmentBase) throws XmlPullParserException, IOException {
        String str6;
        String str7;
        String str8;
        SingleSegmentBase singleSegmentBase;
        DashManifestParser dashManifestParser = this;
        XmlPullParser xmlPullParser2 = xmlPullParser;
        String attributeValue = xmlPullParser2.getAttributeValue(null, "id");
        int parseInt = parseInt(xmlPullParser2, "bandwidth", -1);
        String parseString = parseString(xmlPullParser2, "mimeType", str3);
        String parseString2 = parseString(xmlPullParser2, "codecs", str4);
        int parseInt2 = parseInt(xmlPullParser2, "width", i);
        int parseInt3 = parseInt(xmlPullParser2, "height", i2);
        float parseFrameRate = parseFrameRate(xmlPullParser2, f);
        int parseInt4 = parseInt(xmlPullParser2, "audioSamplingRate", i4);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        Object obj = null;
        int i6 = i3;
        SingleSegmentBase singleSegmentBase2 = segmentBase;
        String str9 = null;
        String str10 = str;
        while (true) {
            xmlPullParser.next();
            str6 = parseString2;
            if (!XmlPullParserUtil.isStartTag(xmlPullParser2, "BaseURL")) {
                if (XmlPullParserUtil.isStartTag(xmlPullParser2, "AudioChannelConfiguration")) {
                    str7 = str10;
                    i6 = parseAudioChannelConfiguration(xmlPullParser);
                } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "SegmentBase")) {
                    singleSegmentBase2 = parseSegmentBase(xmlPullParser2, singleSegmentBase2);
                } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "SegmentList")) {
                    singleSegmentBase2 = parseSegmentList(xmlPullParser2, (SegmentList) singleSegmentBase2);
                } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "SegmentTemplate")) {
                    singleSegmentBase2 = parseSegmentTemplate(xmlPullParser2, (SegmentTemplate) singleSegmentBase2);
                } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "ContentProtection")) {
                    Pair parseContentProtection = parseContentProtection(xmlPullParser);
                    str7 = str10;
                    if (parseContentProtection.first != null) {
                        str9 = (String) parseContentProtection.first;
                    }
                    if (parseContentProtection.second != null) {
                        arrayList.add(parseContentProtection.second);
                    }
                } else {
                    str7 = str10;
                    if (XmlPullParserUtil.isStartTag(xmlPullParser2, "InbandEventStream")) {
                        arrayList2.add(parseDescriptor(xmlPullParser2, "InbandEventStream"));
                    } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "SupplementalProperty")) {
                        arrayList3.add(parseDescriptor(xmlPullParser2, "SupplementalProperty"));
                    }
                }
                str8 = str9;
                singleSegmentBase = singleSegmentBase2;
                if (XmlPullParserUtil.isEndTag(xmlPullParser2, "Representation")) {
                    break;
                }
                singleSegmentBase2 = singleSegmentBase;
                parseString2 = str6;
                str10 = str7;
                str9 = str8;
            } else if (obj == null) {
                str10 = parseBaseUrl(xmlPullParser2, str10);
                obj = 1;
            }
            str7 = str10;
            str8 = str9;
            singleSegmentBase = singleSegmentBase2;
            if (XmlPullParserUtil.isEndTag(xmlPullParser2, "Representation")) {
                break;
            }
            singleSegmentBase2 = singleSegmentBase;
            parseString2 = str6;
            str10 = str7;
            str9 = str8;
        }
        ArrayList arrayList4 = arrayList2;
        ArrayList arrayList5 = arrayList;
        Format buildFormat = buildFormat(attributeValue, str2, parseString, parseInt2, parseInt3, parseFrameRate, i6, parseInt4, parseInt, str5, i5, list, str6, arrayList3);
        if (singleSegmentBase == null) {
            singleSegmentBase = new SingleSegmentBase();
        }
        return new RepresentationInfo(buildFormat, str7, singleSegmentBase, str8, arrayList5, arrayList4, -1);
    }

    protected Format buildFormat(String str, String str2, String str3, int i, int i2, float f, int i3, int i4, int i5, String str4, int i6, List<Descriptor> list, String str5, List<Descriptor> list2) {
        String str6;
        String str7 = str3;
        String sampleMimeType = getSampleMimeType(str3, str5);
        if (sampleMimeType != null) {
            if (MimeTypes.AUDIO_E_AC3.equals(sampleMimeType)) {
                sampleMimeType = parseEac3SupplementalProperties(list2);
            }
            str6 = sampleMimeType;
            if (MimeTypes.isVideo(str6)) {
                return Format.createVideoContainerFormat(str, str2, str3, str6, str5, i5, i, i2, f, null, i6);
            }
            if (MimeTypes.isAudio(str6)) {
                return Format.createAudioContainerFormat(str, str2, str3, str6, str5, i5, i3, i4, null, i6, str4);
            }
            if (mimeTypeIsRawText(str6)) {
                int parseCea608AccessibilityChannel;
                int i7;
                if (MimeTypes.APPLICATION_CEA608.equals(str6)) {
                    parseCea608AccessibilityChannel = parseCea608AccessibilityChannel(list);
                } else if (MimeTypes.APPLICATION_CEA708.equals(str6)) {
                    parseCea608AccessibilityChannel = parseCea708AccessibilityChannel(list);
                } else {
                    i7 = -1;
                    return Format.createTextContainerFormat(str, str2, str3, str6, str5, i5, i6, str4, i7);
                }
                i7 = parseCea608AccessibilityChannel;
                return Format.createTextContainerFormat(str, str2, str3, str6, str5, i5, i6, str4, i7);
            }
        }
        str6 = sampleMimeType;
        return Format.createContainerFormat(str, str2, str3, str6, str5, i5, i6, str4);
    }

    protected Representation buildRepresentation(RepresentationInfo representationInfo, String str, String str2, ArrayList<SchemeData> arrayList, ArrayList<Descriptor> arrayList2) {
        Format format = representationInfo.format;
        if (representationInfo.drmSchemeType != null) {
            str2 = representationInfo.drmSchemeType;
        }
        List list = representationInfo.drmSchemeDatas;
        list.addAll(arrayList);
        if (list.isEmpty() == null) {
            filterRedundantIncompleteSchemeDatas(list);
            format = format.copyWithDrmInitData(new DrmInitData(str2, list));
        }
        Format format2 = format;
        List list2 = representationInfo.inbandEventStreams;
        list2.addAll(arrayList2);
        return Representation.newInstance(str, representationInfo.revisionId, format2, representationInfo.baseUrl, representationInfo.segmentBase, list2);
    }

    protected SingleSegmentBase parseSegmentBase(XmlPullParser xmlPullParser, SingleSegmentBase singleSegmentBase) throws XmlPullParserException, IOException {
        long parseLong;
        XmlPullParser xmlPullParser2 = xmlPullParser;
        SingleSegmentBase singleSegmentBase2 = singleSegmentBase;
        long parseLong2 = parseLong(xmlPullParser2, "timescale", singleSegmentBase2 != null ? singleSegmentBase2.timescale : 1);
        long j = 0;
        long parseLong3 = parseLong(xmlPullParser2, "presentationTimeOffset", singleSegmentBase2 != null ? singleSegmentBase2.presentationTimeOffset : 0);
        long j2 = singleSegmentBase2 != null ? singleSegmentBase2.indexStart : 0;
        if (singleSegmentBase2 != null) {
            j = singleSegmentBase2.indexLength;
        }
        String str = null;
        String attributeValue = xmlPullParser2.getAttributeValue(null, "indexRange");
        if (attributeValue != null) {
            String[] split = attributeValue.split("-");
            j = Long.parseLong(split[0]);
            parseLong = (Long.parseLong(split[1]) - j) + 1;
        } else {
            parseLong = j;
            j = j2;
        }
        if (singleSegmentBase2 != null) {
            str = singleSegmentBase2.initialization;
        }
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser2, "Initialization")) {
                str = parseInitialization(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser2, "SegmentBase"));
        return buildSingleSegmentBase(str, parseLong2, parseLong3, j, parseLong);
    }

    protected SingleSegmentBase buildSingleSegmentBase(RangedUri rangedUri, long j, long j2, long j3, long j4) {
        return new SingleSegmentBase(rangedUri, j, j2, j3, j4);
    }

    protected SegmentList parseSegmentList(XmlPullParser xmlPullParser, SegmentList segmentList) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser2 = xmlPullParser;
        SegmentList segmentList2 = segmentList;
        long j = 1;
        long parseLong = parseLong(xmlPullParser2, "timescale", segmentList2 != null ? segmentList2.timescale : 1);
        long parseLong2 = parseLong(xmlPullParser2, "presentationTimeOffset", segmentList2 != null ? segmentList2.presentationTimeOffset : 0);
        long parseLong3 = parseLong(xmlPullParser2, "duration", segmentList2 != null ? segmentList2.duration : C.TIME_UNSET);
        String str = "startNumber";
        if (segmentList2 != null) {
            j = segmentList2.startNumber;
        }
        long parseLong4 = parseLong(xmlPullParser2, str, j);
        List list = null;
        RangedUri rangedUri = null;
        List list2 = rangedUri;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser2, "Initialization")) {
                rangedUri = parseInitialization(xmlPullParser);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "SegmentTimeline")) {
                list2 = parseSegmentTimeline(xmlPullParser);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "SegmentURL")) {
                if (list == null) {
                    list = new ArrayList();
                }
                list.add(parseSegmentUrl(xmlPullParser));
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser2, "SegmentList"));
        if (segmentList2 != null) {
            if (rangedUri == null) {
                rangedUri = segmentList2.initialization;
            }
            if (list2 == null) {
                list2 = segmentList2.segmentTimeline;
            }
            if (list == null) {
                list = segmentList2.mediaSegments;
            }
        }
        return buildSegmentList(rangedUri, parseLong, parseLong2, parseLong4, parseLong3, list2, list);
    }

    protected SegmentList buildSegmentList(RangedUri rangedUri, long j, long j2, long j3, long j4, List<SegmentTimelineElement> list, List<RangedUri> list2) {
        return new SegmentList(rangedUri, j, j2, j3, j4, list, list2);
    }

    protected SegmentTemplate parseSegmentTemplate(XmlPullParser xmlPullParser, SegmentTemplate segmentTemplate) throws XmlPullParserException, IOException {
        DashManifestParser dashManifestParser = this;
        XmlPullParser xmlPullParser2 = xmlPullParser;
        SegmentTemplate segmentTemplate2 = segmentTemplate;
        long j = 1;
        long parseLong = parseLong(xmlPullParser2, "timescale", segmentTemplate2 != null ? segmentTemplate2.timescale : 1);
        long parseLong2 = parseLong(xmlPullParser2, "presentationTimeOffset", segmentTemplate2 != null ? segmentTemplate2.presentationTimeOffset : 0);
        long parseLong3 = parseLong(xmlPullParser2, "duration", segmentTemplate2 != null ? segmentTemplate2.duration : C.TIME_UNSET);
        String str = "startNumber";
        if (segmentTemplate2 != null) {
            j = segmentTemplate2.startNumber;
        }
        long parseLong4 = parseLong(xmlPullParser2, str, j);
        RangedUri rangedUri = null;
        UrlTemplate parseUrlTemplate = parseUrlTemplate(xmlPullParser2, VideoCastManager.EXTRA_MEDIA, segmentTemplate2 != null ? segmentTemplate2.mediaTemplate : null);
        UrlTemplate parseUrlTemplate2 = parseUrlTemplate(xmlPullParser2, "initialization", segmentTemplate2 != null ? segmentTemplate2.initializationTemplate : null);
        List list = null;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser2, "Initialization")) {
                rangedUri = parseInitialization(xmlPullParser);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "SegmentTimeline")) {
                list = parseSegmentTimeline(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser2, "SegmentTemplate"));
        if (segmentTemplate2 != null) {
            if (rangedUri == null) {
                rangedUri = segmentTemplate2.initialization;
            }
            if (list == null) {
                list = segmentTemplate2.segmentTimeline;
            }
        }
        return buildSegmentTemplate(rangedUri, parseLong, parseLong2, parseLong4, parseLong3, list, parseUrlTemplate2, parseUrlTemplate);
    }

    protected SegmentTemplate buildSegmentTemplate(RangedUri rangedUri, long j, long j2, long j3, long j4, List<SegmentTimelineElement> list, UrlTemplate urlTemplate, UrlTemplate urlTemplate2) {
        return new SegmentTemplate(rangedUri, j, j2, j3, j4, list, urlTemplate, urlTemplate2);
    }

    protected EventStream parseEventStream(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        String parseString = parseString(xmlPullParser, "schemeIdUri", "");
        String parseString2 = parseString(xmlPullParser, "value", "");
        long parseLong = parseLong(xmlPullParser, "timescale", 1);
        List arrayList = new ArrayList();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Event")) {
                arrayList.add(parseEvent(xmlPullParser, parseString, parseString2, parseLong, byteArrayOutputStream));
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "EventStream"));
        long[] jArr = new long[arrayList.size()];
        EventMessage[] eventMessageArr = new EventMessage[arrayList.size()];
        for (xmlPullParser = null; xmlPullParser < arrayList.size(); xmlPullParser++) {
            EventMessage eventMessage = (EventMessage) arrayList.get(xmlPullParser);
            jArr[xmlPullParser] = eventMessage.presentationTimeUs;
            eventMessageArr[xmlPullParser] = eventMessage;
        }
        return buildEventStream(parseString, parseString2, parseLong, jArr, eventMessageArr);
    }

    protected EventStream buildEventStream(String str, String str2, long j, long[] jArr, EventMessage[] eventMessageArr) {
        return new EventStream(str, str2, j, jArr, eventMessageArr);
    }

    protected EventMessage parseEvent(XmlPullParser xmlPullParser, String str, String str2, long j, ByteArrayOutputStream byteArrayOutputStream) throws IOException, XmlPullParserException {
        XmlPullParser xmlPullParser2 = xmlPullParser;
        long parseLong = parseLong(xmlPullParser2, "id", 0);
        long parseLong2 = parseLong(xmlPullParser2, "duration", C.TIME_UNSET);
        long parseLong3 = parseLong(xmlPullParser2, "presentationTime", 0);
        parseLong2 = Util.scaleLargeTimestamp(parseLong2, 1000, j);
        long scaleLargeTimestamp = Util.scaleLargeTimestamp(parseLong3, 1000000, j);
        String parseString = parseString(xmlPullParser2, "messageData", null);
        byte[] parseEventObject = parseEventObject(xmlPullParser2, byteArrayOutputStream);
        if (parseString != null) {
            parseEventObject = Util.getUtf8Bytes(parseString);
        }
        return buildEvent(str, str2, parseLong, parseLong2, parseEventObject, scaleLargeTimestamp);
    }

    protected byte[] parseEventObject(XmlPullParser xmlPullParser, ByteArrayOutputStream byteArrayOutputStream) throws XmlPullParserException, IOException {
        byteArrayOutputStream.reset();
        XmlSerializer newSerializer = Xml.newSerializer();
        newSerializer.setOutput(byteArrayOutputStream, "UTF-8");
        xmlPullParser.nextToken();
        while (!XmlPullParserUtil.isEndTag(xmlPullParser, "Event")) {
            int i = 0;
            switch (xmlPullParser.getEventType()) {
                case 0:
                    newSerializer.startDocument(null, Boolean.valueOf(false));
                    break;
                case 1:
                    newSerializer.endDocument();
                    break;
                case 2:
                    newSerializer.startTag(xmlPullParser.getNamespace(), xmlPullParser.getName());
                    while (i < xmlPullParser.getAttributeCount()) {
                        newSerializer.attribute(xmlPullParser.getAttributeNamespace(i), xmlPullParser.getAttributeName(i), xmlPullParser.getAttributeValue(i));
                        i++;
                    }
                    break;
                case 3:
                    newSerializer.endTag(xmlPullParser.getNamespace(), xmlPullParser.getName());
                    break;
                case 4:
                    newSerializer.text(xmlPullParser.getText());
                    break;
                case 5:
                    newSerializer.cdsect(xmlPullParser.getText());
                    break;
                case 6:
                    newSerializer.entityRef(xmlPullParser.getText());
                    break;
                case 7:
                    newSerializer.ignorableWhitespace(xmlPullParser.getText());
                    break;
                case 8:
                    newSerializer.processingInstruction(xmlPullParser.getText());
                    break;
                case 9:
                    newSerializer.comment(xmlPullParser.getText());
                    break;
                case 10:
                    newSerializer.docdecl(xmlPullParser.getText());
                    break;
                default:
                    break;
            }
            xmlPullParser.nextToken();
        }
        newSerializer.flush();
        return byteArrayOutputStream.toByteArray();
    }

    protected EventMessage buildEvent(String str, String str2, long j, long j2, byte[] bArr, long j3) {
        return new EventMessage(str, str2, j2, j, bArr, j3);
    }

    protected List<SegmentTimelineElement> parseSegmentTimeline(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        List<SegmentTimelineElement> arrayList = new ArrayList();
        long j = 0;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "S")) {
                j = parseLong(xmlPullParser, "t", j);
                long parseLong = parseLong(xmlPullParser, "d", C.TIME_UNSET);
                int i = 0;
                int parseInt = parseInt(xmlPullParser, "r", 0) + 1;
                while (i < parseInt) {
                    arrayList.add(buildSegmentTimelineElement(j, parseLong));
                    j += parseLong;
                    i++;
                }
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "SegmentTimeline"));
        return arrayList;
    }

    protected SegmentTimelineElement buildSegmentTimelineElement(long j, long j2) {
        return new SegmentTimelineElement(j, j2);
    }

    protected UrlTemplate parseUrlTemplate(XmlPullParser xmlPullParser, String str, UrlTemplate urlTemplate) {
        xmlPullParser = xmlPullParser.getAttributeValue(null, str);
        return xmlPullParser != null ? UrlTemplate.compile(xmlPullParser) : urlTemplate;
    }

    protected RangedUri parseInitialization(XmlPullParser xmlPullParser) {
        return parseRangedUrl(xmlPullParser, "sourceURL", "range");
    }

    protected RangedUri parseSegmentUrl(XmlPullParser xmlPullParser) {
        return parseRangedUrl(xmlPullParser, VideoCastManager.EXTRA_MEDIA, "mediaRange");
    }

    protected RangedUri parseRangedUrl(XmlPullParser xmlPullParser, String str, String str2) {
        long parseLong;
        long parseLong2;
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        xmlPullParser = xmlPullParser.getAttributeValue(null, str2);
        if (xmlPullParser != null) {
            xmlPullParser = xmlPullParser.split("-");
            parseLong = Long.parseLong(xmlPullParser[0]);
            if (xmlPullParser.length == 2) {
                parseLong2 = (Long.parseLong(xmlPullParser[1]) - parseLong) + 1;
                return buildRangedUri(attributeValue, parseLong, parseLong2);
            }
        }
        parseLong = 0;
        parseLong2 = -1;
        return buildRangedUri(attributeValue, parseLong, parseLong2);
    }

    protected RangedUri buildRangedUri(String str, long j, long j2) {
        return new RangedUri(str, j, j2);
    }

    protected int parseAudioChannelConfiguration(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        String parseString = parseString(xmlPullParser, "schemeIdUri", null);
        int i = -1;
        if ("urn:mpeg:dash:23003:3:audio_channel_configuration:2011".equals(parseString)) {
            i = parseInt(xmlPullParser, "value", -1);
        } else if ("tag:dolby.com,2014:dash:audio_channel_configuration:2011".equals(parseString)) {
            i = parseDolbyChannelConfiguration(xmlPullParser);
        }
        do {
            xmlPullParser.next();
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "AudioChannelConfiguration"));
        return i;
    }

    private static void filterRedundantIncompleteSchemeDatas(ArrayList<SchemeData> arrayList) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            SchemeData schemeData = (SchemeData) arrayList.get(size);
            if (!schemeData.hasData()) {
                for (int i = 0; i < arrayList.size(); i++) {
                    if (((SchemeData) arrayList.get(i)).canReplace(schemeData)) {
                        arrayList.remove(size);
                        break;
                    }
                }
            }
        }
    }

    private static String getSampleMimeType(String str, String str2) {
        if (MimeTypes.isAudio(str)) {
            return MimeTypes.getAudioMediaMimeType(str2);
        }
        if (MimeTypes.isVideo(str)) {
            return MimeTypes.getVideoMediaMimeType(str2);
        }
        if (mimeTypeIsRawText(str)) {
            return str;
        }
        if (!MimeTypes.APPLICATION_MP4.equals(str)) {
            if (!(MimeTypes.APPLICATION_RAWCC.equals(str) == null || str2 == null)) {
                if (str2.contains("cea708") != null) {
                    return MimeTypes.APPLICATION_CEA708;
                }
                if (!(str2.contains("eia608") == null && str2.contains("cea608") == null)) {
                    return MimeTypes.APPLICATION_CEA608;
                }
            }
            return null;
        } else if (str2 != null) {
            if (str2.startsWith("stpp") != null) {
                return MimeTypes.APPLICATION_TTML;
            }
            if (str2.startsWith("wvtt") != null) {
                return MimeTypes.APPLICATION_MP4VTT;
            }
        }
        return null;
    }

    private static boolean mimeTypeIsRawText(String str) {
        if (!(MimeTypes.isText(str) || MimeTypes.APPLICATION_TTML.equals(str) || MimeTypes.APPLICATION_MP4VTT.equals(str) || MimeTypes.APPLICATION_CEA708.equals(str))) {
            if (MimeTypes.APPLICATION_CEA608.equals(str) == null) {
                return null;
            }
        }
        return true;
    }

    private static String checkLanguageConsistency(String str, String str2) {
        if (str == null) {
            return str2;
        }
        if (str2 == null) {
            return str;
        }
        Assertions.checkState(str.equals(str2));
        return str;
    }

    private static int checkContentTypeConsistency(int i, int i2) {
        if (i == -1) {
            return i2;
        }
        if (i2 == -1) {
            return i;
        }
        Assertions.checkState(i == i2 ? 1 : 0);
        return i;
    }

    protected static Descriptor parseDescriptor(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        String parseString = parseString(xmlPullParser, "schemeIdUri", "");
        String parseString2 = parseString(xmlPullParser, "value", null);
        String parseString3 = parseString(xmlPullParser, "id", null);
        do {
            xmlPullParser.next();
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, str));
        return new Descriptor(parseString, parseString2, parseString3);
    }

    protected static int parseCea608AccessibilityChannel(List<Descriptor> list) {
        for (int i = 0; i < list.size(); i++) {
            Descriptor descriptor = (Descriptor) list.get(i);
            if ("urn:scte:dash:cc:cea-608:2015".equals(descriptor.schemeIdUri) && descriptor.value != null) {
                Matcher matcher = CEA_608_ACCESSIBILITY_PATTERN.matcher(descriptor.value);
                if (matcher.matches()) {
                    return Integer.parseInt(matcher.group(1));
                }
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to parse CEA-608 channel number from: ");
                stringBuilder.append(descriptor.value);
                Log.w(str, stringBuilder.toString());
            }
        }
        return -1;
    }

    protected static int parseCea708AccessibilityChannel(List<Descriptor> list) {
        for (int i = 0; i < list.size(); i++) {
            Descriptor descriptor = (Descriptor) list.get(i);
            if ("urn:scte:dash:cc:cea-708:2015".equals(descriptor.schemeIdUri) && descriptor.value != null) {
                Matcher matcher = CEA_708_ACCESSIBILITY_PATTERN.matcher(descriptor.value);
                if (matcher.matches()) {
                    return Integer.parseInt(matcher.group(1));
                }
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to parse CEA-708 service block number from: ");
                stringBuilder.append(descriptor.value);
                Log.w(str, stringBuilder.toString());
            }
        }
        return -1;
    }

    protected static String parseEac3SupplementalProperties(List<Descriptor> list) {
        for (int i = 0; i < list.size(); i++) {
            Descriptor descriptor = (Descriptor) list.get(i);
            if ("tag:dolby.com,2014:dash:DolbyDigitalPlusExtensionType:2014".equals(descriptor.schemeIdUri) && "ec+3".equals(descriptor.value)) {
                return MimeTypes.AUDIO_E_AC3_JOC;
            }
        }
        return MimeTypes.AUDIO_E_AC3;
    }

    protected static float parseFrameRate(XmlPullParser xmlPullParser, float f) {
        xmlPullParser = xmlPullParser.getAttributeValue(null, "frameRate");
        if (xmlPullParser == null) {
            return f;
        }
        xmlPullParser = FRAME_RATE_PATTERN.matcher(xmlPullParser);
        if (!xmlPullParser.matches()) {
            return f;
        }
        f = Integer.parseInt(xmlPullParser.group(Cue.DIMEN_UNSET));
        xmlPullParser = xmlPullParser.group(2);
        return !TextUtils.isEmpty(xmlPullParser) ? ((float) f) / ((float) Integer.parseInt(xmlPullParser)) : (float) f;
    }

    protected static long parseDuration(XmlPullParser xmlPullParser, String str, long j) {
        xmlPullParser = xmlPullParser.getAttributeValue(null, str);
        if (xmlPullParser == null) {
            return j;
        }
        return Util.parseXsDuration(xmlPullParser);
    }

    protected static long parseDateTime(XmlPullParser xmlPullParser, String str, long j) throws ParserException {
        xmlPullParser = xmlPullParser.getAttributeValue(null, str);
        if (xmlPullParser == null) {
            return j;
        }
        return Util.parseXsDateTime(xmlPullParser);
    }

    protected static String parseBaseUrl(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        xmlPullParser.next();
        return UriUtil.resolve(str, xmlPullParser.getText());
    }

    protected static int parseInt(XmlPullParser xmlPullParser, String str, int i) {
        xmlPullParser = xmlPullParser.getAttributeValue(null, str);
        return xmlPullParser == null ? i : Integer.parseInt(xmlPullParser);
    }

    protected static long parseLong(XmlPullParser xmlPullParser, String str, long j) {
        xmlPullParser = xmlPullParser.getAttributeValue(null, str);
        return xmlPullParser == null ? j : Long.parseLong(xmlPullParser);
    }

    protected static String parseString(XmlPullParser xmlPullParser, String str, String str2) {
        xmlPullParser = xmlPullParser.getAttributeValue(null, str);
        return xmlPullParser == null ? str2 : xmlPullParser;
    }

    protected static int parseDolbyChannelConfiguration(XmlPullParser xmlPullParser) {
        xmlPullParser = Util.toLowerInvariant(xmlPullParser.getAttributeValue(null, "value"));
        if (xmlPullParser == null) {
            return -1;
        }
        int hashCode = xmlPullParser.hashCode();
        if (hashCode != 1596796) {
            if (hashCode != 2937391) {
                if (hashCode != 3094035) {
                    if (hashCode == 3133436) {
                        if (xmlPullParser.equals("fa01") != null) {
                            xmlPullParser = 3;
                            switch (xmlPullParser) {
                                case null:
                                    return 1;
                                case 1:
                                    return 2;
                                case 2:
                                    return 6;
                                case 3:
                                    return 8;
                                default:
                                    return -1;
                            }
                        }
                    }
                } else if (xmlPullParser.equals("f801") != null) {
                    xmlPullParser = 2;
                    switch (xmlPullParser) {
                        case null:
                            return 1;
                        case 1:
                            return 2;
                        case 2:
                            return 6;
                        case 3:
                            return 8;
                        default:
                            return -1;
                    }
                }
            } else if (xmlPullParser.equals("a000") != null) {
                xmlPullParser = true;
                switch (xmlPullParser) {
                    case null:
                        return 1;
                    case 1:
                        return 2;
                    case 2:
                        return 6;
                    case 3:
                        return 8;
                    default:
                        return -1;
                }
            }
        } else if (xmlPullParser.equals("4000") != null) {
            xmlPullParser = null;
            switch (xmlPullParser) {
                case null:
                    return 1;
                case 1:
                    return 2;
                case 2:
                    return 6;
                case 3:
                    return 8;
                default:
                    return -1;
            }
        }
        xmlPullParser = -1;
        switch (xmlPullParser) {
            case null:
                return 1;
            case 1:
                return 2;
            case 2:
                return 6;
            case 3:
                return 8;
            default:
                return -1;
        }
    }
}
