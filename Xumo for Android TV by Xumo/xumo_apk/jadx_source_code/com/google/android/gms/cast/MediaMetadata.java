package com.google.android.gms.cast;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.internal.cast.zzdr;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Class(creator = "MediaMetadataCreator")
@Reserved({1})
public class MediaMetadata extends AbstractSafeParcelable {
    public static final Creator<MediaMetadata> CREATOR = new zzal();
    public static final String KEY_ALBUM_ARTIST = "com.google.android.gms.cast.metadata.ALBUM_ARTIST";
    public static final String KEY_ALBUM_TITLE = "com.google.android.gms.cast.metadata.ALBUM_TITLE";
    public static final String KEY_ARTIST = "com.google.android.gms.cast.metadata.ARTIST";
    public static final String KEY_BROADCAST_DATE = "com.google.android.gms.cast.metadata.BROADCAST_DATE";
    public static final String KEY_COMPOSER = "com.google.android.gms.cast.metadata.COMPOSER";
    public static final String KEY_CREATION_DATE = "com.google.android.gms.cast.metadata.CREATION_DATE";
    public static final String KEY_DISC_NUMBER = "com.google.android.gms.cast.metadata.DISC_NUMBER";
    public static final String KEY_EPISODE_NUMBER = "com.google.android.gms.cast.metadata.EPISODE_NUMBER";
    public static final String KEY_HEIGHT = "com.google.android.gms.cast.metadata.HEIGHT";
    public static final String KEY_LOCATION_LATITUDE = "com.google.android.gms.cast.metadata.LOCATION_LATITUDE";
    public static final String KEY_LOCATION_LONGITUDE = "com.google.android.gms.cast.metadata.LOCATION_LONGITUDE";
    public static final String KEY_LOCATION_NAME = "com.google.android.gms.cast.metadata.LOCATION_NAME";
    public static final String KEY_RELEASE_DATE = "com.google.android.gms.cast.metadata.RELEASE_DATE";
    public static final String KEY_SEASON_NUMBER = "com.google.android.gms.cast.metadata.SEASON_NUMBER";
    public static final String KEY_SERIES_TITLE = "com.google.android.gms.cast.metadata.SERIES_TITLE";
    public static final String KEY_STUDIO = "com.google.android.gms.cast.metadata.STUDIO";
    public static final String KEY_SUBTITLE = "com.google.android.gms.cast.metadata.SUBTITLE";
    public static final String KEY_TITLE = "com.google.android.gms.cast.metadata.TITLE";
    public static final String KEY_TRACK_NUMBER = "com.google.android.gms.cast.metadata.TRACK_NUMBER";
    public static final String KEY_WIDTH = "com.google.android.gms.cast.metadata.WIDTH";
    public static final int MEDIA_TYPE_GENERIC = 0;
    public static final int MEDIA_TYPE_MOVIE = 1;
    public static final int MEDIA_TYPE_MUSIC_TRACK = 3;
    public static final int MEDIA_TYPE_PHOTO = 4;
    public static final int MEDIA_TYPE_TV_SHOW = 2;
    public static final int MEDIA_TYPE_USER = 100;
    private static final String[] zzds = new String[]{null, "String", "int", "double", "ISO-8601 date String"};
    private static final zza zzdt = new zza().zza(KEY_CREATION_DATE, "creationDateTime", 4).zza(KEY_RELEASE_DATE, "releaseDate", 4).zza(KEY_BROADCAST_DATE, "originalAirdate", 4).zza(KEY_TITLE, "title", 1).zza(KEY_SUBTITLE, "subtitle", 1).zza(KEY_ARTIST, "artist", 1).zza(KEY_ALBUM_ARTIST, "albumArtist", 1).zza(KEY_ALBUM_TITLE, "albumName", 1).zza(KEY_COMPOSER, "composer", 1).zza(KEY_DISC_NUMBER, "discNumber", 2).zza(KEY_TRACK_NUMBER, "trackNumber", 2).zza(KEY_SEASON_NUMBER, "season", 2).zza(KEY_EPISODE_NUMBER, "episode", 2).zza(KEY_SERIES_TITLE, "seriesTitle", 1).zza(KEY_STUDIO, "studio", 1).zza(KEY_WIDTH, "width", 2).zza(KEY_HEIGHT, "height", 2).zza(KEY_LOCATION_NAME, Param.LOCATION, 1).zza(KEY_LOCATION_LATITUDE, "latitude", 3).zza(KEY_LOCATION_LONGITUDE, "longitude", 3).zza("com.google.android.gms.cast.metadata.SECTION_DURATION", "sectionDuration", 3).zza("com.google.android.gms.cast.metadata.SECTION_START_TIME_IN_MEDIA", "sectionStartTimeInMedia", 3).zza("com.google.android.gms.cast.metadata.SECTION_START_ABSOLUTE_TIME", "sectionStartAbsoluteTime", 3).zza("com.google.android.gms.cast.metadata.SECTION_START_TIME_IN_CONTAINER", "sectionStartTimeInContainer", 3).zza("com.google.android.gms.cast.metadata.QUEUE_ITEM_ID", "queueItemId", 2);
    @Field(id = 3)
    private final Bundle zzdu;
    @Field(getter = "getMediaType", id = 4)
    private int zzdv;
    @Field(getter = "getImages", id = 2)
    private final List<WebImage> zzz;

    private static class zza {
        private final Map<String, String> zzdw = new HashMap();
        private final Map<String, String> zzdx = new HashMap();
        private final Map<String, Integer> zzdy = new HashMap();

        public final zza zza(String str, String str2, int i) {
            this.zzdw.put(str, str2);
            this.zzdx.put(str2, str);
            this.zzdy.put(str, Integer.valueOf(i));
            return this;
        }

        public final String zze(String str) {
            return (String) this.zzdw.get(str);
        }

        public final String zzf(String str) {
            return (String) this.zzdx.get(str);
        }

        public final int zzg(String str) {
            Integer num = (Integer) this.zzdy.get(str);
            return num != null ? num.intValue() : null;
        }
    }

    @Constructor
    MediaMetadata(@SafeParcelable.Param(id = 2) List<WebImage> list, @SafeParcelable.Param(id = 3) Bundle bundle, @SafeParcelable.Param(id = 4) int i) {
        this.zzz = list;
        this.zzdu = bundle;
        this.zzdv = i;
    }

    public final org.json.JSONObject toJson() {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.google.android.gms.cast.MediaMetadata.toJson():org.json.JSONObject
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: java.lang.NullPointerException
	at jadx.core.dex.nodes.MethodNode.initTryCatches(MethodNode.java:305)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:105)
	... 7 more
*/
        /*
        r0 = this;
        r0 = new org.json.JSONObject;
        r0.<init>();
        r1 = "metadataType";
        r2 = r11.zzdv;
        r0.put(r1, r2);
    L_0x000c:
        r1 = r11.zzz;
        r1 = com.google.android.gms.internal.cast.zzdr.zzd(r1);
        if (r1 == 0) goto L_0x0021;
        r2 = r1.length();
        if (r2 == 0) goto L_0x0021;
        r2 = "images";
        r0.put(r2, r1);
        goto L_0x0021;
        r1 = new java.util.ArrayList;
        r1.<init>();
        r2 = r11.zzdv;
        switch(r2) {
            case 0: goto L_0x007e;
            case 1: goto L_0x006e;
            case 2: goto L_0x005c;
            case 3: goto L_0x0044;
            case 4: goto L_0x002c;
            default: goto L_0x002b;
        };
        goto L_0x008d;
        r3 = "com.google.android.gms.cast.metadata.TITLE";
        r4 = "com.google.android.gms.cast.metadata.ARTIST";
        r5 = "com.google.android.gms.cast.metadata.LOCATION_NAME";
        r6 = "com.google.android.gms.cast.metadata.LOCATION_LATITUDE";
        r7 = "com.google.android.gms.cast.metadata.LOCATION_LONGITUDE";
        r8 = "com.google.android.gms.cast.metadata.WIDTH";
        r9 = "com.google.android.gms.cast.metadata.HEIGHT";
        r10 = "com.google.android.gms.cast.metadata.CREATION_DATE";
        r2 = new java.lang.String[]{r3, r4, r5, r6, r7, r8, r9, r10};
        java.util.Collections.addAll(r1, r2);
        goto L_0x008d;
        r3 = "com.google.android.gms.cast.metadata.TITLE";
        r4 = "com.google.android.gms.cast.metadata.ARTIST";
        r5 = "com.google.android.gms.cast.metadata.ALBUM_TITLE";
        r6 = "com.google.android.gms.cast.metadata.ALBUM_ARTIST";
        r7 = "com.google.android.gms.cast.metadata.COMPOSER";
        r8 = "com.google.android.gms.cast.metadata.TRACK_NUMBER";
        r9 = "com.google.android.gms.cast.metadata.DISC_NUMBER";
        r10 = "com.google.android.gms.cast.metadata.RELEASE_DATE";
        r2 = new java.lang.String[]{r3, r4, r5, r6, r7, r8, r9, r10};
        java.util.Collections.addAll(r1, r2);
        goto L_0x008d;
        r2 = "com.google.android.gms.cast.metadata.TITLE";
        r3 = "com.google.android.gms.cast.metadata.SERIES_TITLE";
        r4 = "com.google.android.gms.cast.metadata.SEASON_NUMBER";
        r5 = "com.google.android.gms.cast.metadata.EPISODE_NUMBER";
        r6 = "com.google.android.gms.cast.metadata.BROADCAST_DATE";
        r2 = new java.lang.String[]{r2, r3, r4, r5, r6};
        java.util.Collections.addAll(r1, r2);
        goto L_0x008d;
        r2 = "com.google.android.gms.cast.metadata.TITLE";
        r3 = "com.google.android.gms.cast.metadata.STUDIO";
        r4 = "com.google.android.gms.cast.metadata.SUBTITLE";
        r5 = "com.google.android.gms.cast.metadata.RELEASE_DATE";
        r2 = new java.lang.String[]{r2, r3, r4, r5};
        java.util.Collections.addAll(r1, r2);
        goto L_0x008d;
        r2 = "com.google.android.gms.cast.metadata.TITLE";
        r3 = "com.google.android.gms.cast.metadata.ARTIST";
        r4 = "com.google.android.gms.cast.metadata.SUBTITLE";
        r5 = "com.google.android.gms.cast.metadata.RELEASE_DATE";
        r2 = new java.lang.String[]{r2, r3, r4, r5};
        java.util.Collections.addAll(r1, r2);
        r2 = "com.google.android.gms.cast.metadata.SECTION_DURATION";
        r3 = "com.google.android.gms.cast.metadata.SECTION_START_TIME_IN_MEDIA";
        r4 = "com.google.android.gms.cast.metadata.SECTION_START_ABSOLUTE_TIME";
        r5 = "com.google.android.gms.cast.metadata.SECTION_START_TIME_IN_CONTAINER";
        r6 = "com.google.android.gms.cast.metadata.QUEUE_ITEM_ID";
        r2 = new java.lang.String[]{r2, r3, r4, r5, r6};
        java.util.Collections.addAll(r1, r2);
        r1 = (java.util.ArrayList) r1;
        r2 = r1.size();
        r3 = 0;
        if (r3 >= r2) goto L_0x00f1;
        r4 = r1.get(r3);
        r3 = r3 + 1;
        r4 = (java.lang.String) r4;
        r5 = r11.zzdu;
        r5 = r5.containsKey(r4);
        if (r5 == 0) goto L_0x00a5;
        r5 = zzdt;
        r5 = r5.zzg(r4);
        switch(r5) {
            case 1: goto L_0x00e1;
            case 2: goto L_0x00d1;
            case 3: goto L_0x00c1;
            case 4: goto L_0x00e1;
            default: goto L_0x00c0;
        };
        goto L_0x00a5;
        r5 = zzdt;
        r5 = r5.zze(r4);
        r6 = r11.zzdu;
        r6 = r6.getDouble(r4);
        r0.put(r5, r6);
        goto L_0x00a5;
        r5 = zzdt;
        r5 = r5.zze(r4);
        r6 = r11.zzdu;
        r4 = r6.getInt(r4);
        r0.put(r5, r4);
        goto L_0x00a5;
        r5 = zzdt;
        r5 = r5.zze(r4);
        r6 = r11.zzdu;
        r4 = r6.getString(r4);
        r0.put(r5, r4);
        goto L_0x00a5;
        r1 = r11.zzdu;
        r1 = r1.keySet();
        r1 = r1.iterator();
        r2 = r1.hasNext();
        if (r2 == 0) goto L_0x012d;
        r2 = r1.next();
        r2 = (java.lang.String) r2;
        r3 = "com.google.";
        r3 = r2.startsWith(r3);
        if (r3 != 0) goto L_0x00fb;
        r3 = r11.zzdu;
        r3 = r3.get(r2);
        r4 = r3 instanceof java.lang.String;
        if (r4 == 0) goto L_0x011d;
        r0.put(r2, r3);
        goto L_0x00fb;
        r4 = r3 instanceof java.lang.Integer;
        if (r4 == 0) goto L_0x0125;
        r0.put(r2, r3);
        goto L_0x00fb;
        r4 = r3 instanceof java.lang.Double;
        if (r4 == 0) goto L_0x00fb;
        r0.put(r2, r3);
        goto L_0x00fb;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.MediaMetadata.toJson():org.json.JSONObject");
    }

    public MediaMetadata() {
        this(0);
    }

    public MediaMetadata(int i) {
        this(new ArrayList(), new Bundle(), i);
    }

    public int getMediaType() {
        return this.zzdv;
    }

    public void clear() {
        this.zzdu.clear();
        this.zzz.clear();
    }

    public boolean containsKey(String str) {
        return this.zzdu.containsKey(str);
    }

    public Set<String> keySet() {
        return this.zzdu.keySet();
    }

    public void putString(String str, String str2) {
        zza(str, 1);
        this.zzdu.putString(str, str2);
    }

    public String getString(String str) {
        zza(str, 1);
        return this.zzdu.getString(str);
    }

    public void putInt(String str, int i) {
        zza(str, 2);
        this.zzdu.putInt(str, i);
    }

    public int getInt(String str) {
        zza(str, 2);
        return this.zzdu.getInt(str);
    }

    public void putDouble(String str, double d) {
        zza(str, 3);
        this.zzdu.putDouble(str, d);
    }

    public double getDouble(String str) {
        zza(str, 3);
        return this.zzdu.getDouble(str);
    }

    public void putDate(String str, Calendar calendar) {
        zza(str, 4);
        this.zzdu.putString(str, zzdr.zza(calendar));
    }

    public Calendar getDate(String str) {
        zza(str, 4);
        str = this.zzdu.getString(str);
        return str != null ? zzdr.zzv(str) : null;
    }

    public String getDateAsString(String str) {
        zza(str, 4);
        return this.zzdu.getString(str);
    }

    private static void zza(String str, int i) throws IllegalArgumentException {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("null and empty keys are not allowed");
        }
        int zzg = zzdt.zzg(str);
        if (zzg == i) {
            return;
        }
        if (zzg != 0) {
            i = zzds[i];
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 21) + String.valueOf(i).length());
            stringBuilder.append("Value for ");
            stringBuilder.append(str);
            stringBuilder.append(" must be a ");
            stringBuilder.append(i);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public final void zze(org.json.JSONObject r11) {
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
        r10 = this;
        r10.clear();
        r0 = 0;
        r10.zzdv = r0;
        r0 = "metadataType";	 Catch:{ JSONException -> 0x000e }
        r0 = r11.getInt(r0);	 Catch:{ JSONException -> 0x000e }
        r10.zzdv = r0;	 Catch:{ JSONException -> 0x000e }
    L_0x000e:
        r0 = "images";
        r0 = r11.optJSONArray(r0);
        if (r0 == 0) goto L_0x001b;
    L_0x0016:
        r1 = r10.zzz;
        com.google.android.gms.internal.cast.zzdr.zza(r1, r0);
    L_0x001b:
        r0 = new java.util.ArrayList;
        r0.<init>();
        r1 = r10.zzdv;
        switch(r1) {
            case 0: goto L_0x0078;
            case 1: goto L_0x0068;
            case 2: goto L_0x0056;
            case 3: goto L_0x003e;
            case 4: goto L_0x0026;
            default: goto L_0x0025;
        };
    L_0x0025:
        goto L_0x0087;
    L_0x0026:
        r2 = "com.google.android.gms.cast.metadata.TITLE";
        r3 = "com.google.android.gms.cast.metadata.ARTIST";
        r4 = "com.google.android.gms.cast.metadata.LOCATION_NAME";
        r5 = "com.google.android.gms.cast.metadata.LOCATION_LATITUDE";
        r6 = "com.google.android.gms.cast.metadata.LOCATION_LONGITUDE";
        r7 = "com.google.android.gms.cast.metadata.WIDTH";
        r8 = "com.google.android.gms.cast.metadata.HEIGHT";
        r9 = "com.google.android.gms.cast.metadata.CREATION_DATE";
        r1 = new java.lang.String[]{r2, r3, r4, r5, r6, r7, r8, r9};
        java.util.Collections.addAll(r0, r1);
        goto L_0x0087;
    L_0x003e:
        r2 = "com.google.android.gms.cast.metadata.TITLE";
        r3 = "com.google.android.gms.cast.metadata.ALBUM_TITLE";
        r4 = "com.google.android.gms.cast.metadata.ARTIST";
        r5 = "com.google.android.gms.cast.metadata.ALBUM_ARTIST";
        r6 = "com.google.android.gms.cast.metadata.COMPOSER";
        r7 = "com.google.android.gms.cast.metadata.TRACK_NUMBER";
        r8 = "com.google.android.gms.cast.metadata.DISC_NUMBER";
        r9 = "com.google.android.gms.cast.metadata.RELEASE_DATE";
        r1 = new java.lang.String[]{r2, r3, r4, r5, r6, r7, r8, r9};
        java.util.Collections.addAll(r0, r1);
        goto L_0x0087;
    L_0x0056:
        r1 = "com.google.android.gms.cast.metadata.TITLE";
        r2 = "com.google.android.gms.cast.metadata.SERIES_TITLE";
        r3 = "com.google.android.gms.cast.metadata.SEASON_NUMBER";
        r4 = "com.google.android.gms.cast.metadata.EPISODE_NUMBER";
        r5 = "com.google.android.gms.cast.metadata.BROADCAST_DATE";
        r1 = new java.lang.String[]{r1, r2, r3, r4, r5};
        java.util.Collections.addAll(r0, r1);
        goto L_0x0087;
    L_0x0068:
        r1 = "com.google.android.gms.cast.metadata.TITLE";
        r2 = "com.google.android.gms.cast.metadata.STUDIO";
        r3 = "com.google.android.gms.cast.metadata.SUBTITLE";
        r4 = "com.google.android.gms.cast.metadata.RELEASE_DATE";
        r1 = new java.lang.String[]{r1, r2, r3, r4};
        java.util.Collections.addAll(r0, r1);
        goto L_0x0087;
    L_0x0078:
        r1 = "com.google.android.gms.cast.metadata.TITLE";
        r2 = "com.google.android.gms.cast.metadata.ARTIST";
        r3 = "com.google.android.gms.cast.metadata.SUBTITLE";
        r4 = "com.google.android.gms.cast.metadata.RELEASE_DATE";
        r1 = new java.lang.String[]{r1, r2, r3, r4};
        java.util.Collections.addAll(r0, r1);
    L_0x0087:
        r1 = "com.google.android.gms.cast.metadata.SECTION_DURATION";
        r2 = "com.google.android.gms.cast.metadata.SECTION_START_TIME_IN_MEDIA";
        r3 = "com.google.android.gms.cast.metadata.SECTION_START_ABSOLUTE_TIME";
        r4 = "com.google.android.gms.cast.metadata.SECTION_START_TIME_IN_CONTAINER";
        r5 = "com.google.android.gms.cast.metadata.QUEUE_ITEM_ID";
        r1 = new java.lang.String[]{r1, r2, r3, r4, r5};
        java.util.Collections.addAll(r0, r1);
        r1 = new java.util.HashSet;
        r1.<init>(r0);
        r0 = r11.keys();	 Catch:{ JSONException -> 0x015a }
    L_0x00a1:
        r2 = r0.hasNext();	 Catch:{ JSONException -> 0x015a }
        if (r2 == 0) goto L_0x0159;	 Catch:{ JSONException -> 0x015a }
    L_0x00a7:
        r2 = r0.next();	 Catch:{ JSONException -> 0x015a }
        r2 = (java.lang.String) r2;	 Catch:{ JSONException -> 0x015a }
        r3 = "metadataType";	 Catch:{ JSONException -> 0x015a }
        r3 = r3.equals(r2);	 Catch:{ JSONException -> 0x015a }
        if (r3 != 0) goto L_0x00a1;	 Catch:{ JSONException -> 0x015a }
    L_0x00b5:
        r3 = zzdt;	 Catch:{ JSONException -> 0x015a }
        r3 = r3.zzf(r2);	 Catch:{ JSONException -> 0x015a }
        if (r3 == 0) goto L_0x0126;	 Catch:{ JSONException -> 0x015a }
    L_0x00bd:
        r4 = r1.contains(r3);	 Catch:{ JSONException -> 0x015a }
        if (r4 == 0) goto L_0x00a1;
    L_0x00c3:
        r2 = r11.get(r2);	 Catch:{ JSONException -> 0x00a1 }
        if (r2 != 0) goto L_0x00ca;	 Catch:{ JSONException -> 0x00a1 }
    L_0x00c9:
        goto L_0x00a1;	 Catch:{ JSONException -> 0x00a1 }
    L_0x00ca:
        r4 = zzdt;	 Catch:{ JSONException -> 0x00a1 }
        r4 = r4.zzg(r3);	 Catch:{ JSONException -> 0x00a1 }
        switch(r4) {
            case 1: goto L_0x0119;
            case 2: goto L_0x0109;
            case 3: goto L_0x00e9;
            case 4: goto L_0x00d4;
            default: goto L_0x00d3;
        };	 Catch:{ JSONException -> 0x00a1 }
    L_0x00d3:
        goto L_0x00a1;	 Catch:{ JSONException -> 0x00a1 }
    L_0x00d4:
        r4 = r2 instanceof java.lang.String;	 Catch:{ JSONException -> 0x00a1 }
        if (r4 == 0) goto L_0x00a1;	 Catch:{ JSONException -> 0x00a1 }
    L_0x00d8:
        r4 = r2;	 Catch:{ JSONException -> 0x00a1 }
        r4 = (java.lang.String) r4;	 Catch:{ JSONException -> 0x00a1 }
        r4 = com.google.android.gms.internal.cast.zzdr.zzv(r4);	 Catch:{ JSONException -> 0x00a1 }
        if (r4 == 0) goto L_0x00a1;	 Catch:{ JSONException -> 0x00a1 }
    L_0x00e1:
        r4 = r10.zzdu;	 Catch:{ JSONException -> 0x00a1 }
        r2 = (java.lang.String) r2;	 Catch:{ JSONException -> 0x00a1 }
        r4.putString(r3, r2);	 Catch:{ JSONException -> 0x00a1 }
        goto L_0x00a1;	 Catch:{ JSONException -> 0x00a1 }
    L_0x00e9:
        r4 = r2 instanceof java.lang.Double;	 Catch:{ JSONException -> 0x00a1 }
        if (r4 == 0) goto L_0x00f9;	 Catch:{ JSONException -> 0x00a1 }
    L_0x00ed:
        r4 = r10.zzdu;	 Catch:{ JSONException -> 0x00a1 }
        r5 = r2;	 Catch:{ JSONException -> 0x00a1 }
        r5 = (java.lang.Double) r5;	 Catch:{ JSONException -> 0x00a1 }
        r5 = r5.doubleValue();	 Catch:{ JSONException -> 0x00a1 }
        r4.putDouble(r3, r5);	 Catch:{ JSONException -> 0x00a1 }
    L_0x00f9:
        r4 = r2 instanceof java.lang.Integer;	 Catch:{ JSONException -> 0x00a1 }
        if (r4 == 0) goto L_0x00a1;	 Catch:{ JSONException -> 0x00a1 }
    L_0x00fd:
        r4 = r10.zzdu;	 Catch:{ JSONException -> 0x00a1 }
        r2 = (java.lang.Integer) r2;	 Catch:{ JSONException -> 0x00a1 }
        r5 = r2.doubleValue();	 Catch:{ JSONException -> 0x00a1 }
        r4.putDouble(r3, r5);	 Catch:{ JSONException -> 0x00a1 }
        goto L_0x00a1;	 Catch:{ JSONException -> 0x00a1 }
    L_0x0109:
        r4 = r2 instanceof java.lang.Integer;	 Catch:{ JSONException -> 0x00a1 }
        if (r4 == 0) goto L_0x00a1;	 Catch:{ JSONException -> 0x00a1 }
    L_0x010d:
        r4 = r10.zzdu;	 Catch:{ JSONException -> 0x00a1 }
        r2 = (java.lang.Integer) r2;	 Catch:{ JSONException -> 0x00a1 }
        r2 = r2.intValue();	 Catch:{ JSONException -> 0x00a1 }
        r4.putInt(r3, r2);	 Catch:{ JSONException -> 0x00a1 }
        goto L_0x00a1;	 Catch:{ JSONException -> 0x00a1 }
    L_0x0119:
        r4 = r2 instanceof java.lang.String;	 Catch:{ JSONException -> 0x00a1 }
        if (r4 == 0) goto L_0x00a1;	 Catch:{ JSONException -> 0x00a1 }
    L_0x011d:
        r4 = r10.zzdu;	 Catch:{ JSONException -> 0x00a1 }
        r2 = (java.lang.String) r2;	 Catch:{ JSONException -> 0x00a1 }
        r4.putString(r3, r2);	 Catch:{ JSONException -> 0x00a1 }
        goto L_0x00a1;
    L_0x0126:
        r3 = r11.get(r2);	 Catch:{ JSONException -> 0x015a }
        r4 = r3 instanceof java.lang.String;	 Catch:{ JSONException -> 0x015a }
        if (r4 == 0) goto L_0x0137;	 Catch:{ JSONException -> 0x015a }
    L_0x012e:
        r4 = r10.zzdu;	 Catch:{ JSONException -> 0x015a }
        r3 = (java.lang.String) r3;	 Catch:{ JSONException -> 0x015a }
        r4.putString(r2, r3);	 Catch:{ JSONException -> 0x015a }
        goto L_0x00a1;	 Catch:{ JSONException -> 0x015a }
    L_0x0137:
        r4 = r3 instanceof java.lang.Integer;	 Catch:{ JSONException -> 0x015a }
        if (r4 == 0) goto L_0x0148;	 Catch:{ JSONException -> 0x015a }
    L_0x013b:
        r4 = r10.zzdu;	 Catch:{ JSONException -> 0x015a }
        r3 = (java.lang.Integer) r3;	 Catch:{ JSONException -> 0x015a }
        r3 = r3.intValue();	 Catch:{ JSONException -> 0x015a }
        r4.putInt(r2, r3);	 Catch:{ JSONException -> 0x015a }
        goto L_0x00a1;	 Catch:{ JSONException -> 0x015a }
    L_0x0148:
        r4 = r3 instanceof java.lang.Double;	 Catch:{ JSONException -> 0x015a }
        if (r4 == 0) goto L_0x00a1;	 Catch:{ JSONException -> 0x015a }
    L_0x014c:
        r4 = r10.zzdu;	 Catch:{ JSONException -> 0x015a }
        r3 = (java.lang.Double) r3;	 Catch:{ JSONException -> 0x015a }
        r5 = r3.doubleValue();	 Catch:{ JSONException -> 0x015a }
        r4.putDouble(r2, r5);	 Catch:{ JSONException -> 0x015a }
        goto L_0x00a1;
    L_0x0159:
        return;
    L_0x015a:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.MediaMetadata.zze(org.json.JSONObject):void");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaMetadata)) {
            return false;
        }
        MediaMetadata mediaMetadata = (MediaMetadata) obj;
        return zza(this.zzdu, mediaMetadata.zzdu) && this.zzz.equals(mediaMetadata.zzz) != null;
    }

    public int hashCode() {
        int i = 17;
        for (String str : this.zzdu.keySet()) {
            i = (i * 31) + this.zzdu.get(str).hashCode();
        }
        return (i * 31) + this.zzz.hashCode();
    }

    public void writeToParcel(Parcel parcel, int i) {
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 2, getImages(), false);
        SafeParcelWriter.writeBundle(parcel, 3, this.zzdu, false);
        SafeParcelWriter.writeInt(parcel, 4, getMediaType());
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }

    public List<WebImage> getImages() {
        return this.zzz;
    }

    public boolean hasImages() {
        return (this.zzz == null || this.zzz.isEmpty()) ? false : true;
    }

    public void clearImages() {
        this.zzz.clear();
    }

    public void addImage(WebImage webImage) {
        this.zzz.add(webImage);
    }

    private final boolean zza(Bundle bundle, Bundle bundle2) {
        if (bundle.size() != bundle2.size()) {
            return false;
        }
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            Object obj2 = bundle2.get(str);
            if ((obj instanceof Bundle) && (obj2 instanceof Bundle) && !zza((Bundle) obj, (Bundle) obj2)) {
                return false;
            }
            if (obj == null) {
                if (obj2 != null || !bundle2.containsKey(str)) {
                    return false;
                }
            } else if (!obj.equals(obj2)) {
                return false;
            }
        }
        return true;
    }
}
