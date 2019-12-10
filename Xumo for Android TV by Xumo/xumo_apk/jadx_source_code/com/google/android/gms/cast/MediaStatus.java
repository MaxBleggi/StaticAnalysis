package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.SparseArray;
import com.google.android.gms.cast.MediaQueueItem.Builder;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.common.util.JsonUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.cast.zzcv;
import com.google.android.gms.internal.cast.zzdq;
import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Class(creator = "MediaStatusCreator")
@Reserved({1})
@VisibleForTesting
public class MediaStatus extends AbstractSafeParcelable {
    public static final long COMMAND_PAUSE = 1;
    public static final long COMMAND_SEEK = 2;
    public static final long COMMAND_SET_VOLUME = 4;
    public static final long COMMAND_SKIP_BACKWARD = 32;
    public static final long COMMAND_SKIP_FORWARD = 16;
    public static final long COMMAND_TOGGLE_MUTE = 8;
    public static final Creator<MediaStatus> CREATOR = new zzar();
    public static final int IDLE_REASON_CANCELED = 2;
    public static final int IDLE_REASON_ERROR = 4;
    public static final int IDLE_REASON_FINISHED = 1;
    public static final int IDLE_REASON_INTERRUPTED = 3;
    public static final int IDLE_REASON_NONE = 0;
    public static final int PLAYER_STATE_BUFFERING = 4;
    public static final int PLAYER_STATE_IDLE = 1;
    public static final int PLAYER_STATE_PAUSED = 3;
    public static final int PLAYER_STATE_PLAYING = 2;
    public static final int PLAYER_STATE_UNKNOWN = 0;
    public static final int REPEAT_MODE_REPEAT_ALL = 1;
    public static final int REPEAT_MODE_REPEAT_ALL_AND_SHUFFLE = 3;
    public static final int REPEAT_MODE_REPEAT_OFF = 0;
    public static final int REPEAT_MODE_REPEAT_SINGLE = 2;
    @Field(getter = "getMediaInfo", id = 2)
    @VisibleForTesting
    private MediaInfo zzdl;
    @Field(getter = "getPlaybackRate", id = 5)
    @VisibleForTesting
    private double zzdo;
    @Field(getter = "getActiveTrackIds", id = 12)
    @VisibleForTesting
    private long[] zzdp;
    @Field(getter = "getMediaSessionId", id = 3)
    @VisibleForTesting
    private long zzer;
    @Field(getter = "getCurrentItemId", id = 4)
    @VisibleForTesting
    private int zzes;
    @Field(getter = "getPlayerState", id = 6)
    @VisibleForTesting
    private int zzet;
    @Field(getter = "getIdleReason", id = 7)
    @VisibleForTesting
    private int zzeu;
    @Field(getter = "getStreamPosition", id = 8)
    @VisibleForTesting
    private long zzev;
    @Field(id = 9)
    private long zzew;
    @Field(getter = "getStreamVolume", id = 10)
    @VisibleForTesting
    private double zzex;
    @Field(getter = "isMute", id = 11)
    @VisibleForTesting
    private boolean zzey;
    @Field(getter = "getLoadingItemId", id = 13)
    @VisibleForTesting
    private int zzez;
    @Field(getter = "getPreloadedItemId", id = 14)
    @VisibleForTesting
    private int zzfa;
    @Field(id = 16)
    private int zzfb;
    @Field(id = 17)
    private final ArrayList<MediaQueueItem> zzfc;
    @Field(getter = "isPlayingAd", id = 18)
    @VisibleForTesting
    private boolean zzfd;
    @Field(getter = "getAdBreakStatus", id = 19)
    @VisibleForTesting
    private AdBreakStatus zzfe;
    @Field(getter = "getVideoInfo", id = 20)
    @VisibleForTesting
    private VideoInfo zzff;
    @VisibleForTesting
    private zzaq zzfg;
    @VisibleForTesting
    private zzan zzfh;
    private final SparseArray<Integer> zzfi;
    @Field(id = 15)
    private String zzj;
    @VisibleForTesting
    private JSONObject zzp;

    @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor
    MediaStatus(@com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 2) com.google.android.gms.cast.MediaInfo r6, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 3) long r7, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 4) int r9, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 5) double r10, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 6) int r12, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 7) int r13, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 8) long r14, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 9) long r16, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 10) double r18, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 11) boolean r20, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 12) long[] r21, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 13) int r22, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 14) int r23, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 15) java.lang.String r24, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 16) int r25, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 17) java.util.List<com.google.android.gms.cast.MediaQueueItem> r26, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 18) boolean r27, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 19) com.google.android.gms.cast.AdBreakStatus r28, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 20) com.google.android.gms.cast.VideoInfo r29) {
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
        r5 = this;
        r0 = r5;
        r1 = r26;
        r5.<init>();
        r2 = new java.util.ArrayList;
        r2.<init>();
        r0.zzfc = r2;
        r2 = new android.util.SparseArray;
        r2.<init>();
        r0.zzfi = r2;
        r2 = r6;
        r0.zzdl = r2;
        r2 = r7;
        r0.zzer = r2;
        r2 = r9;
        r0.zzes = r2;
        r2 = r10;
        r0.zzdo = r2;
        r2 = r12;
        r0.zzet = r2;
        r2 = r13;
        r0.zzeu = r2;
        r2 = r14;
        r0.zzev = r2;
        r2 = r16;
        r0.zzew = r2;
        r2 = r18;
        r0.zzex = r2;
        r2 = r20;
        r0.zzey = r2;
        r2 = r21;
        r0.zzdp = r2;
        r2 = r22;
        r0.zzez = r2;
        r2 = r23;
        r0.zzfa = r2;
        r2 = r24;
        r0.zzj = r2;
        r2 = r0.zzj;
        r3 = 0;
        if (r2 == 0) goto L_0x0059;
    L_0x004a:
        r2 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x0054 }
        r4 = r0.zzj;	 Catch:{ JSONException -> 0x0054 }
        r2.<init>(r4);	 Catch:{ JSONException -> 0x0054 }
        r0.zzp = r2;	 Catch:{ JSONException -> 0x0054 }
        goto L_0x005b;
    L_0x0054:
        r0.zzp = r3;
        r0.zzj = r3;
        goto L_0x005b;
    L_0x0059:
        r0.zzp = r3;
    L_0x005b:
        r2 = r25;
        r0.zzfb = r2;
        if (r1 == 0) goto L_0x0076;
    L_0x0061:
        r2 = r26.isEmpty();
        if (r2 != 0) goto L_0x0076;
    L_0x0067:
        r2 = r26.size();
        r2 = new com.google.android.gms.cast.MediaQueueItem[r2];
        r1 = r1.toArray(r2);
        r1 = (com.google.android.gms.cast.MediaQueueItem[]) r1;
        r5.zza(r1);
    L_0x0076:
        r1 = r27;
        r0.zzfd = r1;
        r1 = r28;
        r0.zzfe = r1;
        r1 = r29;
        r0.zzff = r1;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.MediaStatus.<init>(com.google.android.gms.cast.MediaInfo, long, int, double, int, int, long, long, double, boolean, long[], int, int, java.lang.String, int, java.util.List, boolean, com.google.android.gms.cast.AdBreakStatus, com.google.android.gms.cast.VideoInfo):void");
    }

    private static boolean zza(int i, int i2, int i3, int i4) {
        if (i != 1) {
            return false;
        }
        switch (i2) {
            case 1:
            case 3:
                return i3 == 0;
            case 2:
                return i4 != 2;
            default:
                return true;
        }
    }

    public MediaStatus(JSONObject jSONObject) throws JSONException {
        this(null, 0, 0, 0.0d, 0, 0, 0, 0, 0.0d, false, null, 0, 0, null, 0, null, false, null, null);
        zza(jSONObject, 0);
    }

    public final long zzj() {
        return this.zzer;
    }

    public int getPlayerState() {
        return this.zzet;
    }

    public int getIdleReason() {
        return this.zzeu;
    }

    public double getPlaybackRate() {
        return this.zzdo;
    }

    public MediaInfo getMediaInfo() {
        return this.zzdl;
    }

    public long getStreamPosition() {
        return this.zzev;
    }

    public boolean isMediaCommandSupported(long j) {
        return (j & this.zzew) != 0 ? 1 : 0;
    }

    public double getStreamVolume() {
        return this.zzex;
    }

    public boolean isMute() {
        return this.zzey;
    }

    public long[] getActiveTrackIds() {
        return this.zzdp;
    }

    public JSONObject getCustomData() {
        return this.zzp;
    }

    public int getCurrentItemId() {
        return this.zzes;
    }

    public int getLoadingItemId() {
        return this.zzez;
    }

    public int getPreloadedItemId() {
        return this.zzfa;
    }

    public int getQueueRepeatMode() {
        return this.zzfb;
    }

    public List<MediaQueueItem> getQueueItems() {
        return this.zzfc;
    }

    public int getQueueItemCount() {
        return this.zzfc.size();
    }

    public MediaQueueItem getQueueItemById(int i) {
        return getItemById(i);
    }

    public MediaQueueItem getQueueItem(int i) {
        return getItemByIndex(i);
    }

    public boolean isPlayingAd() {
        return this.zzfd;
    }

    public final void zzf(boolean z) {
        this.zzfd = z;
    }

    public AdBreakStatus getAdBreakStatus() {
        return this.zzfe;
    }

    public VideoInfo getVideoInfo() {
        return this.zzff;
    }

    public final int zza(JSONObject jSONObject, int i) throws JSONException {
        int i2;
        double d;
        long j;
        int length;
        long[] jArr;
        int i3;
        long j2 = jSONObject.getLong("mediaSessionId");
        boolean z = false;
        if (j2 != this.zzer) {
            this.zzer = j2;
            i2 = 1;
        } else {
            i2 = 0;
        }
        if (jSONObject.has("playerState")) {
            String string = jSONObject.getString("playerState");
            int i4 = 3;
            int i5 = string.equals("IDLE") ? 1 : string.equals("PLAYING") ? 2 : string.equals("PAUSED") ? 3 : string.equals("BUFFERING") ? 4 : 0;
            if (i5 != this.zzet) {
                this.zzet = i5;
                i2 |= 2;
            }
            if (i5 == 1 && jSONObject.has("idleReason")) {
                string = jSONObject.getString("idleReason");
                if (string.equals("CANCELLED")) {
                    i4 = 2;
                } else if (!string.equals("INTERRUPTED")) {
                    i4 = string.equals("FINISHED") ? 1 : string.equals("ERROR") ? 4 : 0;
                }
                if (i4 != this.zzeu) {
                    this.zzeu = i4;
                    i2 |= 2;
                }
            }
        }
        if (jSONObject.has("playbackRate")) {
            d = jSONObject.getDouble("playbackRate");
            if (this.zzdo != d) {
                this.zzdo = d;
                i2 |= 2;
            }
        }
        if (jSONObject.has("currentTime")) {
            j = (long) (jSONObject.getDouble("currentTime") * 1000.0d);
            if (j != this.zzev) {
                this.zzev = j;
                i2 |= 2;
            }
            i2 |= 128;
        }
        if (jSONObject.has("supportedMediaCommands")) {
            j = jSONObject.getLong("supportedMediaCommands");
            if (j != this.zzew) {
                this.zzew = j;
                i2 |= 2;
            }
        }
        if (jSONObject.has(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME) && (i & 1) == 0) {
            i = jSONObject.getJSONObject(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME);
            d = i.getDouble(Param.LEVEL);
            if (d != this.zzex) {
                this.zzex = d;
                i2 |= 2;
            }
            boolean z2 = i.getBoolean("muted");
            if (z2 != this.zzey) {
                this.zzey = z2;
                i2 |= 2;
            }
        }
        if (jSONObject.has("activeTrackIds") != 0) {
            i = jSONObject.getJSONArray("activeTrackIds");
            length = i.length();
            jArr = new long[length];
            for (i3 = 0; i3 < length; i3++) {
                jArr[i3] = i.getLong(i3);
            }
            if (this.zzdp != 0) {
                if (this.zzdp.length == length) {
                    i = 0;
                    while (i < length) {
                        if (this.zzdp[i] == jArr[i]) {
                            i++;
                        }
                    }
                    i = 0;
                    if (i != 0) {
                        this.zzdp = jArr;
                    }
                }
            }
            i = 1;
            if (i != 0) {
                this.zzdp = jArr;
            }
        } else if (this.zzdp != 0) {
            jArr = null;
            i = 1;
        } else {
            jArr = null;
            i = 0;
        }
        if (i != 0) {
            this.zzdp = jArr;
            i2 |= 2;
        }
        if (jSONObject.has(VideoCastManager.EXTRA_CUSTOM_DATA) != 0) {
            this.zzp = jSONObject.getJSONObject(VideoCastManager.EXTRA_CUSTOM_DATA);
            this.zzj = null;
            i2 |= 2;
        }
        if (jSONObject.has(VideoCastManager.EXTRA_MEDIA) != 0) {
            JSONObject jSONObject2 = jSONObject.getJSONObject(VideoCastManager.EXTRA_MEDIA);
            MediaInfo mediaInfo = new MediaInfo(jSONObject2);
            if (this.zzdl == null || !(this.zzdl == null || this.zzdl.equals(mediaInfo))) {
                this.zzdl = mediaInfo;
                i2 |= 2;
            }
            if (jSONObject2.has(TtmlNode.TAG_METADATA) != 0) {
                i2 |= 4;
            }
        }
        if (jSONObject.has("currentItemId") != 0) {
            i = jSONObject.getInt("currentItemId");
            if (this.zzes != i) {
                this.zzes = i;
                i2 |= 2;
            }
        }
        i = jSONObject.optInt("preloadedItemId", 0);
        if (this.zzfa != i) {
            this.zzfa = i;
            i2 |= 16;
        }
        i = jSONObject.optInt("loadingItemId", 0);
        if (this.zzez != i) {
            this.zzez = i;
            i2 |= 2;
        }
        if (zza(this.zzet, this.zzeu, this.zzez, this.zzdl == 0 ? -1 : this.zzdl.getStreamType()) == 0) {
            JSONArray jSONArray;
            SparseArray sparseArray;
            MediaQueueItem[] mediaQueueItemArr;
            int i6;
            Integer num;
            JSONObject jSONObject3;
            MediaQueueItem itemById;
            if (jSONObject.has("repeatMode") != 0) {
                i = zzdq.zzu(jSONObject.getString("repeatMode"));
                i = Integer.valueOf(i == 0 ? this.zzfb : i.intValue());
                if (this.zzfb != i.intValue()) {
                    this.zzfb = i.intValue();
                    i = 1;
                    if (jSONObject.has("items")) {
                        jSONArray = jSONObject.getJSONArray("items");
                        length = jSONArray.length();
                        sparseArray = new SparseArray();
                        for (i3 = 0; i3 < length; i3++) {
                            sparseArray.put(i3, Integer.valueOf(jSONArray.getJSONObject(i3).getInt("itemId")));
                        }
                        mediaQueueItemArr = new MediaQueueItem[length];
                        i6 = i;
                        for (i = 0; i < length; i++) {
                            num = (Integer) sparseArray.get(i);
                            jSONObject3 = jSONArray.getJSONObject(i);
                            itemById = getItemById(num.intValue());
                            if (itemById == null) {
                                i6 |= itemById.zzf(jSONObject3);
                                mediaQueueItemArr[i] = itemById;
                                if (i == getIndexById(num.intValue()).intValue()) {
                                }
                            } else if (num.intValue() == this.zzes || this.zzdl == null) {
                                mediaQueueItemArr[i] = new MediaQueueItem(jSONObject3);
                            } else {
                                mediaQueueItemArr[i] = new Builder(this.zzdl).build();
                                mediaQueueItemArr[i].zzf(jSONObject3);
                            }
                            i6 = 1;
                        }
                        i = this.zzfc.size() == length ? 1 : i6;
                        zza(mediaQueueItemArr);
                    }
                    if (i != 0) {
                        i2 |= 8;
                    }
                }
            }
            i = 0;
            if (jSONObject.has("items")) {
                jSONArray = jSONObject.getJSONArray("items");
                length = jSONArray.length();
                sparseArray = new SparseArray();
                for (i3 = 0; i3 < length; i3++) {
                    sparseArray.put(i3, Integer.valueOf(jSONArray.getJSONObject(i3).getInt("itemId")));
                }
                mediaQueueItemArr = new MediaQueueItem[length];
                i6 = i;
                while (i < length) {
                    num = (Integer) sparseArray.get(i);
                    jSONObject3 = jSONArray.getJSONObject(i);
                    itemById = getItemById(num.intValue());
                    if (itemById == null) {
                        if (num.intValue() == this.zzes) {
                        }
                        mediaQueueItemArr[i] = new MediaQueueItem(jSONObject3);
                    } else {
                        i6 |= itemById.zzf(jSONObject3);
                        mediaQueueItemArr[i] = itemById;
                        if (i == getIndexById(num.intValue()).intValue()) {
                        }
                    }
                    i6 = 1;
                }
                if (this.zzfc.size() == length) {
                }
                zza(mediaQueueItemArr);
            }
            if (i != 0) {
                i2 |= 8;
            }
        } else {
            this.zzes = 0;
            this.zzez = 0;
            this.zzfa = 0;
            if (this.zzfc.isEmpty() == 0) {
                this.zzfb = 0;
                this.zzfc.clear();
                this.zzfi.clear();
                i2 |= 8;
            }
        }
        i = AdBreakStatus.zzc(jSONObject.optJSONObject("breakStatus"));
        if ((this.zzfe == null && i != 0) || !(this.zzfe == null || this.zzfe.equals(i))) {
            if (i != 0) {
                z = true;
            }
            this.zzfd = z;
            this.zzfe = i;
            i2 |= 32;
        }
        i = VideoInfo.zzh(jSONObject.optJSONObject("videoInfo"));
        if ((this.zzff == null && i != 0) || !(this.zzff == null || this.zzff.equals(i))) {
            this.zzff = i;
            i2 |= 64;
        }
        if (!(jSONObject.has("breakInfo") == 0 || this.zzdl == 0)) {
            this.zzdl.zzd(jSONObject.getJSONObject("breakInfo"));
            i2 |= 2;
        }
        if (jSONObject.has("queueData") != 0) {
            this.zzfh = new zzan();
            this.zzfh.zze(jSONObject.getJSONObject("queueData"));
        }
        if (jSONObject.has("mediaSeekableRange") == 0) {
            return i2;
        }
        this.zzfg = zzaq.zzg(jSONObject.optJSONObject("mediaSeekableRange"));
        return i2 | 2;
    }

    public final boolean zzk() {
        return zza(this.zzet, this.zzeu, this.zzez, this.zzdl == null ? -1 : this.zzdl.getStreamType());
    }

    public MediaQueueItem getItemById(int i) {
        Integer num = (Integer) this.zzfi.get(i);
        if (num == null) {
            return 0;
        }
        return (MediaQueueItem) this.zzfc.get(num.intValue());
    }

    public MediaQueueItem getItemByIndex(int i) {
        if (i >= 0) {
            if (i < this.zzfc.size()) {
                return (MediaQueueItem) this.zzfc.get(i);
            }
        }
        return 0;
    }

    public Integer getIndexById(int i) {
        return (Integer) this.zzfi.get(i);
    }

    private final void zza(MediaQueueItem[] mediaQueueItemArr) {
        this.zzfc.clear();
        this.zzfi.clear();
        for (int i = 0; i < mediaQueueItemArr.length; i++) {
            MediaQueueItem mediaQueueItem = mediaQueueItemArr[i];
            this.zzfc.add(mediaQueueItem);
            this.zzfi.put(mediaQueueItem.getItemId(), Integer.valueOf(i));
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaStatus)) {
            return false;
        }
        MediaStatus mediaStatus = (MediaStatus) obj;
        if ((this.zzp == null ? 1 : null) == (mediaStatus.zzp == null ? 1 : null) && this.zzer == mediaStatus.zzer && this.zzes == mediaStatus.zzes && this.zzdo == mediaStatus.zzdo && this.zzet == mediaStatus.zzet && this.zzeu == mediaStatus.zzeu && this.zzev == mediaStatus.zzev && this.zzex == mediaStatus.zzex && this.zzey == mediaStatus.zzey && this.zzez == mediaStatus.zzez && this.zzfa == mediaStatus.zzfa && this.zzfb == mediaStatus.zzfb && Arrays.equals(this.zzdp, mediaStatus.zzdp) && zzcv.zza(Long.valueOf(this.zzew), Long.valueOf(mediaStatus.zzew)) && zzcv.zza(this.zzfc, mediaStatus.zzfc) && zzcv.zza(this.zzdl, mediaStatus.zzdl)) {
            Object obj2 = (this.zzp == null || mediaStatus.zzp == null || JsonUtils.areJsonValuesEquivalent(this.zzp, mediaStatus.zzp)) ? 1 : null;
            return obj2 != null && this.zzfd == mediaStatus.isPlayingAd() && zzcv.zza(this.zzfe, mediaStatus.zzfe) && zzcv.zza(this.zzff, mediaStatus.zzff) && zzcv.zza(this.zzfg, mediaStatus.zzfg) && Objects.equal(this.zzfh, mediaStatus.zzfh) != null;
        }
    }

    public int hashCode() {
        return Objects.hashCode(this.zzdl, Long.valueOf(this.zzer), Integer.valueOf(this.zzes), Double.valueOf(this.zzdo), Integer.valueOf(this.zzet), Integer.valueOf(this.zzeu), Long.valueOf(this.zzev), Long.valueOf(this.zzew), Double.valueOf(this.zzex), Boolean.valueOf(this.zzey), Integer.valueOf(Arrays.hashCode(this.zzdp)), Integer.valueOf(this.zzez), Integer.valueOf(this.zzfa), String.valueOf(this.zzp), Integer.valueOf(this.zzfb), this.zzfc, Boolean.valueOf(this.zzfd), this.zzfe, this.zzff, this.zzfg, this.zzfh);
    }

    public void writeToParcel(Parcel parcel, int i) {
        this.zzj = this.zzp == null ? null : this.zzp.toString();
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, getMediaInfo(), i, false);
        SafeParcelWriter.writeLong(parcel, 3, this.zzer);
        SafeParcelWriter.writeInt(parcel, 4, getCurrentItemId());
        SafeParcelWriter.writeDouble(parcel, 5, getPlaybackRate());
        SafeParcelWriter.writeInt(parcel, 6, getPlayerState());
        SafeParcelWriter.writeInt(parcel, 7, getIdleReason());
        SafeParcelWriter.writeLong(parcel, 8, getStreamPosition());
        SafeParcelWriter.writeLong(parcel, 9, this.zzew);
        SafeParcelWriter.writeDouble(parcel, 10, getStreamVolume());
        SafeParcelWriter.writeBoolean(parcel, 11, isMute());
        SafeParcelWriter.writeLongArray(parcel, 12, getActiveTrackIds(), false);
        SafeParcelWriter.writeInt(parcel, 13, getLoadingItemId());
        SafeParcelWriter.writeInt(parcel, 14, getPreloadedItemId());
        SafeParcelWriter.writeString(parcel, 15, this.zzj, false);
        SafeParcelWriter.writeInt(parcel, 16, this.zzfb);
        SafeParcelWriter.writeTypedList(parcel, 17, this.zzfc, false);
        SafeParcelWriter.writeBoolean(parcel, 18, isPlayingAd());
        SafeParcelWriter.writeParcelable(parcel, 19, getAdBreakStatus(), i, false);
        SafeParcelWriter.writeParcelable(parcel, 20, getVideoInfo(), i, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public AdBreakInfo getCurrentAdBreak() {
        if (this.zzfe != null) {
            if (this.zzdl != null) {
                Object breakId = this.zzfe.getBreakId();
                if (TextUtils.isEmpty(breakId)) {
                    return null;
                }
                List<AdBreakInfo> adBreaks = this.zzdl.getAdBreaks();
                if (adBreaks != null) {
                    if (!adBreaks.isEmpty()) {
                        for (AdBreakInfo adBreakInfo : adBreaks) {
                            if (breakId.equals(adBreakInfo.getId())) {
                                return adBreakInfo;
                            }
                        }
                        return null;
                    }
                }
                return null;
            }
        }
        return null;
    }

    public AdBreakClipInfo getCurrentAdBreakClip() {
        if (this.zzfe != null) {
            if (this.zzdl != null) {
                Object breakClipId = this.zzfe.getBreakClipId();
                if (TextUtils.isEmpty(breakClipId)) {
                    return null;
                }
                List<AdBreakClipInfo> adBreakClips = this.zzdl.getAdBreakClips();
                if (adBreakClips != null) {
                    if (!adBreakClips.isEmpty()) {
                        for (AdBreakClipInfo adBreakClipInfo : adBreakClips) {
                            if (breakClipId.equals(adBreakClipInfo.getId())) {
                                return adBreakClipInfo;
                            }
                        }
                        return null;
                    }
                }
                return null;
            }
        }
        return null;
    }
}
