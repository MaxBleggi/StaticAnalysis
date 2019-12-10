package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.common.util.JsonUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.cast.zzcv;
import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;
import com.google.android.libraries.cast.companionlibrary.utils.CustomData.PLAYSUBTYPE;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Class(creator = "MediaInfoCreator")
@Reserved({1})
public class MediaInfo extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Creator<MediaInfo> CREATOR = new zzaj();
    public static final int STREAM_TYPE_BUFFERED = 1;
    public static final int STREAM_TYPE_INVALID = -1;
    public static final int STREAM_TYPE_LIVE = 2;
    public static final int STREAM_TYPE_NONE = 0;
    public static final long UNKNOWN_DURATION = -1;
    @Field(getter = "getStreamType", id = 3)
    private int streamType;
    @Field(getter = "getContentType", id = 4)
    private String zzdd;
    @Field(getter = "getMetadata", id = 5)
    private MediaMetadata zzde;
    @Field(getter = "getStreamDuration", id = 6)
    private long zzdf;
    @Field(getter = "getMediaTracks", id = 7)
    private List<MediaTrack> zzdg;
    @Field(getter = "getTextTrackStyle", id = 8)
    private TextTrackStyle zzdh;
    @Field(getter = "getAdBreaks", id = 10)
    private List<AdBreakInfo> zzdi;
    @Field(getter = "getAdBreakClips", id = 11)
    private List<AdBreakClipInfo> zzdj;
    @Field(getter = "getEntity", id = 12)
    private String zzdk;
    @Field(id = 9)
    private String zzj;
    @Field(getter = "getContentId", id = 2)
    private final String zzk;
    private JSONObject zzp;

    @VisibleForTesting
    public static class Builder {
        private final MediaInfo zzdl;

        public Builder(String str) throws IllegalArgumentException {
            this.zzdl = new MediaInfo(str);
        }

        public Builder(String str, String str2) throws IllegalArgumentException {
            this.zzdl = new MediaInfo(str, str2);
        }

        public Builder setStreamType(int i) throws IllegalArgumentException {
            this.zzdl.setStreamType(i);
            return this;
        }

        public Builder setContentType(String str) {
            this.zzdl.setContentType(str);
            return this;
        }

        public Builder setMetadata(MediaMetadata mediaMetadata) {
            this.zzdl.zza(mediaMetadata);
            return this;
        }

        public Builder setStreamDuration(long j) throws IllegalArgumentException {
            this.zzdl.zza(j);
            return this;
        }

        public Builder setCustomData(JSONObject jSONObject) {
            this.zzdl.setCustomData(jSONObject);
            return this;
        }

        public Builder setMediaTracks(List<MediaTrack> list) {
            this.zzdl.zza((List) list);
            return this;
        }

        public Builder setTextTrackStyle(TextTrackStyle textTrackStyle) {
            this.zzdl.setTextTrackStyle(textTrackStyle);
            return this;
        }

        public Builder setEntity(String str) {
            this.zzdl.zzd(str);
            return this;
        }

        public Builder setAdBreaks(List<AdBreakInfo> list) {
            this.zzdl.zzb(list);
            return this;
        }

        public Builder setAdBreakClips(List<AdBreakClipInfo> list) {
            this.zzdl.zzc(list);
            return this;
        }

        public MediaInfo build() {
            return this.zzdl;
        }
    }

    @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor
    MediaInfo(@com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 2) @androidx.annotation.NonNull java.lang.String r1, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 3) int r2, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 4) java.lang.String r3, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 5) com.google.android.gms.cast.MediaMetadata r4, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 6) long r5, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 7) java.util.List<com.google.android.gms.cast.MediaTrack> r7, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 8) com.google.android.gms.cast.TextTrackStyle r8, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 9) java.lang.String r9, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 10) java.util.List<com.google.android.gms.cast.AdBreakInfo> r10, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 11) java.util.List<com.google.android.gms.cast.AdBreakClipInfo> r11, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 12) java.lang.String r12) {
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
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r0 = this;
        r0.<init>();
        r0.zzk = r1;
        r0.streamType = r2;
        r0.zzdd = r3;
        r0.zzde = r4;
        r0.zzdf = r5;
        r0.zzdg = r7;
        r0.zzdh = r8;
        r0.zzj = r9;
        r1 = r0.zzj;
        r2 = 0;
        if (r1 == 0) goto L_0x0027;
    L_0x0018:
        r1 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x0022 }
        r3 = r0.zzj;	 Catch:{ JSONException -> 0x0022 }
        r1.<init>(r3);	 Catch:{ JSONException -> 0x0022 }
        r0.zzp = r1;	 Catch:{ JSONException -> 0x0022 }
        goto L_0x0029;
    L_0x0022:
        r0.zzp = r2;
        r0.zzj = r2;
        goto L_0x0029;
    L_0x0027:
        r0.zzp = r2;
    L_0x0029:
        r0.zzdi = r10;
        r0.zzdj = r11;
        r0.zzdk = r12;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.MediaInfo.<init>(java.lang.String, int, java.lang.String, com.google.android.gms.cast.MediaMetadata, long, java.util.List, com.google.android.gms.cast.TextTrackStyle, java.lang.String, java.util.List, java.util.List, java.lang.String):void");
    }

    MediaInfo(String str) throws IllegalArgumentException {
        this(str, -1, null, null, -1, null, null, null, null, null, null);
        if (str == null) {
            throw new IllegalArgumentException("contentID cannot be null");
        }
    }

    MediaInfo(String str, String str2) throws IllegalArgumentException {
        this(str, -1, null, null, -1, null, null, null, null, null, str2);
        if (str == null) {
            throw new IllegalArgumentException("contentID cannot be null");
        }
    }

    MediaInfo(JSONObject jSONObject) throws JSONException {
        this(jSONObject.getString("contentId"), -1, null, null, -1, null, null, null, null, null, null);
        String string = jSONObject.getString("streamType");
        int i = 0;
        if ("NONE".equals(string)) {
            this.streamType = 0;
        } else if ("BUFFERED".equals(string)) {
            this.streamType = 1;
        } else if (PLAYSUBTYPE.LIVE.equals(string)) {
            this.streamType = 2;
        } else {
            this.streamType = -1;
        }
        this.zzdd = jSONObject.optString(JsonKey.CONTENT_TYPE, null);
        if (jSONObject.has(TtmlNode.TAG_METADATA)) {
            JSONObject jSONObject2 = jSONObject.getJSONObject(TtmlNode.TAG_METADATA);
            this.zzde = new MediaMetadata(jSONObject2.getInt("metadataType"));
            this.zzde.zze(jSONObject2);
        }
        this.zzdf = -1;
        if (jSONObject.has("duration") && !jSONObject.isNull("duration")) {
            double optDouble = jSONObject.optDouble("duration", 0.0d);
            if (!(Double.isNaN(optDouble) || Double.isInfinite(optDouble))) {
                this.zzdf = (long) (optDouble * 1000.0d);
            }
        }
        if (jSONObject.has("tracks")) {
            this.zzdg = new ArrayList();
            JSONArray jSONArray = jSONObject.getJSONArray("tracks");
            while (i < jSONArray.length()) {
                this.zzdg.add(new MediaTrack(jSONArray.getJSONObject(i)));
                i++;
            }
        } else {
            this.zzdg = null;
        }
        if (jSONObject.has("textTrackStyle")) {
            jSONObject2 = jSONObject.getJSONObject("textTrackStyle");
            TextTrackStyle textTrackStyle = new TextTrackStyle();
            textTrackStyle.zze(jSONObject2);
            this.zzdh = textTrackStyle;
        } else {
            this.zzdh = null;
        }
        zzd(jSONObject);
        this.zzp = jSONObject.optJSONObject(VideoCastManager.EXTRA_CUSTOM_DATA);
        if (jSONObject.has("entity")) {
            this.zzdk = jSONObject.getString("entity");
        }
    }

    public String getContentId() {
        return this.zzk;
    }

    final void setStreamType(int i) throws IllegalArgumentException {
        if (i < -1 || i > 2) {
            throw new IllegalArgumentException("invalid stream type");
        }
        this.streamType = i;
    }

    public int getStreamType() {
        return this.streamType;
    }

    final void setContentType(String str) {
        this.zzdd = str;
    }

    public String getContentType() {
        return this.zzdd;
    }

    final void zza(MediaMetadata mediaMetadata) {
        this.zzde = mediaMetadata;
    }

    public MediaMetadata getMetadata() {
        return this.zzde;
    }

    final void zza(long j) throws IllegalArgumentException {
        if (j < 0) {
            if (j != -1) {
                throw new IllegalArgumentException("Invalid stream duration");
            }
        }
        this.zzdf = j;
    }

    public long getStreamDuration() {
        return this.zzdf;
    }

    final void zza(List<MediaTrack> list) {
        this.zzdg = list;
    }

    public List<MediaTrack> getMediaTracks() {
        return this.zzdg;
    }

    public void setTextTrackStyle(TextTrackStyle textTrackStyle) {
        this.zzdh = textTrackStyle;
    }

    public TextTrackStyle getTextTrackStyle() {
        return this.zzdh;
    }

    final void setCustomData(JSONObject jSONObject) {
        this.zzp = jSONObject;
    }

    public JSONObject getCustomData() {
        return this.zzp;
    }

    public List<AdBreakInfo> getAdBreaks() {
        return this.zzdi == null ? null : Collections.unmodifiableList(this.zzdi);
    }

    public List<AdBreakClipInfo> getAdBreakClips() {
        return this.zzdj == null ? null : Collections.unmodifiableList(this.zzdj);
    }

    @VisibleForTesting
    public final void zzb(List<AdBreakInfo> list) {
        this.zzdi = list;
    }

    @VisibleForTesting
    final void zzc(List<AdBreakClipInfo> list) {
        this.zzdj = list;
    }

    final void zzd(JSONObject jSONObject) throws JSONException {
        int i = 0;
        if (jSONObject.has("breaks")) {
            JSONArray jSONArray = jSONObject.getJSONArray("breaks");
            this.zzdi = new ArrayList(jSONArray.length());
            int i2 = 0;
            while (i2 < jSONArray.length()) {
                AdBreakInfo zzb = AdBreakInfo.zzb(jSONArray.getJSONObject(i2));
                if (zzb == null) {
                    this.zzdi.clear();
                    break;
                } else {
                    this.zzdi.add(zzb);
                    i2++;
                }
            }
        }
        if (jSONObject.has("breakClips")) {
            jSONObject = jSONObject.getJSONArray("breakClips");
            this.zzdj = new ArrayList(jSONObject.length());
            while (i < jSONObject.length()) {
                AdBreakClipInfo zza = AdBreakClipInfo.zza(jSONObject.getJSONObject(i));
                if (zza != null) {
                    this.zzdj.add(zza);
                    i++;
                } else {
                    this.zzdj.clear();
                    return;
                }
            }
        }
    }

    public String getEntity() {
        return this.zzdk;
    }

    @VisibleForTesting
    public final void zzd(String str) {
        this.zzdk = str;
    }

    public final org.json.JSONObject toJson() {
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
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r6 = this;
        r0 = new org.json.JSONObject;
        r0.<init>();
        r1 = "contentId";	 Catch:{ JSONException -> 0x00f6 }
        r2 = r6.zzk;	 Catch:{ JSONException -> 0x00f6 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x00f6 }
        r1 = r6.streamType;	 Catch:{ JSONException -> 0x00f6 }
        switch(r1) {
            case 1: goto L_0x0017;
            case 2: goto L_0x0014;
            default: goto L_0x0011;
        };	 Catch:{ JSONException -> 0x00f6 }
    L_0x0011:
        r1 = "NONE";	 Catch:{ JSONException -> 0x00f6 }
        goto L_0x0019;	 Catch:{ JSONException -> 0x00f6 }
    L_0x0014:
        r1 = "LIVE";	 Catch:{ JSONException -> 0x00f6 }
        goto L_0x0019;	 Catch:{ JSONException -> 0x00f6 }
    L_0x0017:
        r1 = "BUFFERED";	 Catch:{ JSONException -> 0x00f6 }
    L_0x0019:
        r2 = "streamType";	 Catch:{ JSONException -> 0x00f6 }
        r0.put(r2, r1);	 Catch:{ JSONException -> 0x00f6 }
        r1 = r6.zzdd;	 Catch:{ JSONException -> 0x00f6 }
        if (r1 == 0) goto L_0x0029;	 Catch:{ JSONException -> 0x00f6 }
    L_0x0022:
        r1 = "contentType";	 Catch:{ JSONException -> 0x00f6 }
        r2 = r6.zzdd;	 Catch:{ JSONException -> 0x00f6 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x00f6 }
    L_0x0029:
        r1 = r6.zzde;	 Catch:{ JSONException -> 0x00f6 }
        if (r1 == 0) goto L_0x0038;	 Catch:{ JSONException -> 0x00f6 }
    L_0x002d:
        r1 = "metadata";	 Catch:{ JSONException -> 0x00f6 }
        r2 = r6.zzde;	 Catch:{ JSONException -> 0x00f6 }
        r2 = r2.toJson();	 Catch:{ JSONException -> 0x00f6 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x00f6 }
    L_0x0038:
        r1 = r6.zzdf;	 Catch:{ JSONException -> 0x00f6 }
        r3 = -1;	 Catch:{ JSONException -> 0x00f6 }
        r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1));	 Catch:{ JSONException -> 0x00f6 }
        if (r5 > 0) goto L_0x0048;	 Catch:{ JSONException -> 0x00f6 }
    L_0x0040:
        r1 = "duration";	 Catch:{ JSONException -> 0x00f6 }
        r2 = org.json.JSONObject.NULL;	 Catch:{ JSONException -> 0x00f6 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x00f6 }
        goto L_0x0059;	 Catch:{ JSONException -> 0x00f6 }
    L_0x0048:
        r1 = "duration";	 Catch:{ JSONException -> 0x00f6 }
        r2 = r6.zzdf;	 Catch:{ JSONException -> 0x00f6 }
        r2 = (double) r2;
        r4 = 4652007308841189376; // 0x408f400000000000 float:0.0 double:1000.0;
        java.lang.Double.isNaN(r2);
        r2 = r2 / r4;
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x00f6 }
    L_0x0059:
        r1 = r6.zzdg;	 Catch:{ JSONException -> 0x00f6 }
        if (r1 == 0) goto L_0x0081;	 Catch:{ JSONException -> 0x00f6 }
    L_0x005d:
        r1 = new org.json.JSONArray;	 Catch:{ JSONException -> 0x00f6 }
        r1.<init>();	 Catch:{ JSONException -> 0x00f6 }
        r2 = r6.zzdg;	 Catch:{ JSONException -> 0x00f6 }
        r2 = r2.iterator();	 Catch:{ JSONException -> 0x00f6 }
    L_0x0068:
        r3 = r2.hasNext();	 Catch:{ JSONException -> 0x00f6 }
        if (r3 == 0) goto L_0x007c;	 Catch:{ JSONException -> 0x00f6 }
    L_0x006e:
        r3 = r2.next();	 Catch:{ JSONException -> 0x00f6 }
        r3 = (com.google.android.gms.cast.MediaTrack) r3;	 Catch:{ JSONException -> 0x00f6 }
        r3 = r3.toJson();	 Catch:{ JSONException -> 0x00f6 }
        r1.put(r3);	 Catch:{ JSONException -> 0x00f6 }
        goto L_0x0068;	 Catch:{ JSONException -> 0x00f6 }
    L_0x007c:
        r2 = "tracks";	 Catch:{ JSONException -> 0x00f6 }
        r0.put(r2, r1);	 Catch:{ JSONException -> 0x00f6 }
    L_0x0081:
        r1 = r6.zzdh;	 Catch:{ JSONException -> 0x00f6 }
        if (r1 == 0) goto L_0x0090;	 Catch:{ JSONException -> 0x00f6 }
    L_0x0085:
        r1 = "textTrackStyle";	 Catch:{ JSONException -> 0x00f6 }
        r2 = r6.zzdh;	 Catch:{ JSONException -> 0x00f6 }
        r2 = r2.toJson();	 Catch:{ JSONException -> 0x00f6 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x00f6 }
    L_0x0090:
        r1 = r6.zzp;	 Catch:{ JSONException -> 0x00f6 }
        if (r1 == 0) goto L_0x009b;	 Catch:{ JSONException -> 0x00f6 }
    L_0x0094:
        r1 = "customData";	 Catch:{ JSONException -> 0x00f6 }
        r2 = r6.zzp;	 Catch:{ JSONException -> 0x00f6 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x00f6 }
    L_0x009b:
        r1 = r6.zzdk;	 Catch:{ JSONException -> 0x00f6 }
        if (r1 == 0) goto L_0x00a6;	 Catch:{ JSONException -> 0x00f6 }
    L_0x009f:
        r1 = "entity";	 Catch:{ JSONException -> 0x00f6 }
        r2 = r6.zzdk;	 Catch:{ JSONException -> 0x00f6 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x00f6 }
    L_0x00a6:
        r1 = r6.zzdi;	 Catch:{ JSONException -> 0x00f6 }
        if (r1 == 0) goto L_0x00ce;	 Catch:{ JSONException -> 0x00f6 }
    L_0x00aa:
        r1 = new org.json.JSONArray;	 Catch:{ JSONException -> 0x00f6 }
        r1.<init>();	 Catch:{ JSONException -> 0x00f6 }
        r2 = r6.zzdi;	 Catch:{ JSONException -> 0x00f6 }
        r2 = r2.iterator();	 Catch:{ JSONException -> 0x00f6 }
    L_0x00b5:
        r3 = r2.hasNext();	 Catch:{ JSONException -> 0x00f6 }
        if (r3 == 0) goto L_0x00c9;	 Catch:{ JSONException -> 0x00f6 }
    L_0x00bb:
        r3 = r2.next();	 Catch:{ JSONException -> 0x00f6 }
        r3 = (com.google.android.gms.cast.AdBreakInfo) r3;	 Catch:{ JSONException -> 0x00f6 }
        r3 = r3.toJson();	 Catch:{ JSONException -> 0x00f6 }
        r1.put(r3);	 Catch:{ JSONException -> 0x00f6 }
        goto L_0x00b5;	 Catch:{ JSONException -> 0x00f6 }
    L_0x00c9:
        r2 = "breaks";	 Catch:{ JSONException -> 0x00f6 }
        r0.put(r2, r1);	 Catch:{ JSONException -> 0x00f6 }
    L_0x00ce:
        r1 = r6.zzdj;	 Catch:{ JSONException -> 0x00f6 }
        if (r1 == 0) goto L_0x00f6;	 Catch:{ JSONException -> 0x00f6 }
    L_0x00d2:
        r1 = new org.json.JSONArray;	 Catch:{ JSONException -> 0x00f6 }
        r1.<init>();	 Catch:{ JSONException -> 0x00f6 }
        r2 = r6.zzdj;	 Catch:{ JSONException -> 0x00f6 }
        r2 = r2.iterator();	 Catch:{ JSONException -> 0x00f6 }
    L_0x00dd:
        r3 = r2.hasNext();	 Catch:{ JSONException -> 0x00f6 }
        if (r3 == 0) goto L_0x00f1;	 Catch:{ JSONException -> 0x00f6 }
    L_0x00e3:
        r3 = r2.next();	 Catch:{ JSONException -> 0x00f6 }
        r3 = (com.google.android.gms.cast.AdBreakClipInfo) r3;	 Catch:{ JSONException -> 0x00f6 }
        r3 = r3.toJson();	 Catch:{ JSONException -> 0x00f6 }
        r1.put(r3);	 Catch:{ JSONException -> 0x00f6 }
        goto L_0x00dd;	 Catch:{ JSONException -> 0x00f6 }
    L_0x00f1:
        r2 = "breakClips";	 Catch:{ JSONException -> 0x00f6 }
        r0.put(r2, r1);	 Catch:{ JSONException -> 0x00f6 }
    L_0x00f6:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.MediaInfo.toJson():org.json.JSONObject");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaInfo)) {
            return false;
        }
        MediaInfo mediaInfo = (MediaInfo) obj;
        if ((this.zzp == null ? 1 : null) != (mediaInfo.zzp == null ? 1 : null)) {
            return false;
        }
        return (this.zzp == null || mediaInfo.zzp == null || JsonUtils.areJsonValuesEquivalent(this.zzp, mediaInfo.zzp)) && zzcv.zza(this.zzk, mediaInfo.zzk) && this.streamType == mediaInfo.streamType && zzcv.zza(this.zzdd, mediaInfo.zzdd) && zzcv.zza(this.zzde, mediaInfo.zzde) && this.zzdf == mediaInfo.zzdf && zzcv.zza(this.zzdg, mediaInfo.zzdg) && zzcv.zza(this.zzdh, mediaInfo.zzdh) && zzcv.zza(this.zzdi, mediaInfo.zzdi) && zzcv.zza(this.zzdj, mediaInfo.zzdj) && zzcv.zza(this.zzdk, mediaInfo.zzdk) != null;
    }

    public int hashCode() {
        return Objects.hashCode(this.zzk, Integer.valueOf(this.streamType), this.zzdd, this.zzde, Long.valueOf(this.zzdf), String.valueOf(this.zzp), this.zzdg, this.zzdh, this.zzdi, this.zzdj, this.zzdk);
    }

    public void writeToParcel(Parcel parcel, int i) {
        this.zzj = this.zzp == null ? null : this.zzp.toString();
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, getContentId(), false);
        SafeParcelWriter.writeInt(parcel, 3, getStreamType());
        SafeParcelWriter.writeString(parcel, 4, getContentType(), false);
        SafeParcelWriter.writeParcelable(parcel, 5, getMetadata(), i, false);
        SafeParcelWriter.writeLong(parcel, 6, getStreamDuration());
        SafeParcelWriter.writeTypedList(parcel, 7, getMediaTracks(), false);
        SafeParcelWriter.writeParcelable(parcel, 8, getTextTrackStyle(), i, false);
        SafeParcelWriter.writeString(parcel, 9, this.zzj, false);
        SafeParcelWriter.writeTypedList(parcel, 10, getAdBreaks(), false);
        SafeParcelWriter.writeTypedList(parcel, 11, getAdBreakClips(), false);
        SafeParcelWriter.writeString(parcel, 12, getEntity(), false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
