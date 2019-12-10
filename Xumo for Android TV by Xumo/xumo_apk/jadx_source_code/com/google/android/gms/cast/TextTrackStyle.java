package com.google.android.gms.cast;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.view.accessibility.CaptioningManager;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.common.util.JsonUtils;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.internal.cast.zzcv;
import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;
import com.xumo.xumo.repository.UserPreferences;
import org.json.JSONException;
import org.json.JSONObject;

@Class(creator = "TextTrackStyleCreator")
@Reserved({1})
public final class TextTrackStyle extends AbstractSafeParcelable {
    public static final int COLOR_UNSPECIFIED = 0;
    public static final Creator<TextTrackStyle> CREATOR = new zzbv();
    public static final float DEFAULT_FONT_SCALE = 1.0f;
    public static final int EDGE_TYPE_DEPRESSED = 4;
    public static final int EDGE_TYPE_DROP_SHADOW = 2;
    public static final int EDGE_TYPE_NONE = 0;
    public static final int EDGE_TYPE_OUTLINE = 1;
    public static final int EDGE_TYPE_RAISED = 3;
    public static final int EDGE_TYPE_UNSPECIFIED = -1;
    public static final int FONT_FAMILY_CASUAL = 4;
    public static final int FONT_FAMILY_CURSIVE = 5;
    public static final int FONT_FAMILY_MONOSPACED_SANS_SERIF = 1;
    public static final int FONT_FAMILY_MONOSPACED_SERIF = 3;
    public static final int FONT_FAMILY_SANS_SERIF = 0;
    public static final int FONT_FAMILY_SERIF = 2;
    public static final int FONT_FAMILY_SMALL_CAPITALS = 6;
    public static final int FONT_FAMILY_UNSPECIFIED = -1;
    public static final int FONT_STYLE_BOLD = 1;
    public static final int FONT_STYLE_BOLD_ITALIC = 3;
    public static final int FONT_STYLE_ITALIC = 2;
    public static final int FONT_STYLE_NORMAL = 0;
    public static final int FONT_STYLE_UNSPECIFIED = -1;
    public static final int WINDOW_TYPE_NONE = 0;
    public static final int WINDOW_TYPE_NORMAL = 1;
    public static final int WINDOW_TYPE_ROUNDED = 2;
    public static final int WINDOW_TYPE_UNSPECIFIED = -1;
    @Field(getter = "getBackgroundColor", id = 4)
    private int backgroundColor;
    @Field(getter = "getEdgeColor", id = 6)
    private int edgeColor;
    @Field(getter = "getEdgeType", id = 5)
    private int edgeType;
    @Field(getter = "getFontScale", id = 2)
    private float fontScale;
    @Field(getter = "getFontStyle", id = 12)
    private int fontStyle;
    @Field(getter = "getForegroundColor", id = 3)
    private int foregroundColor;
    @Field(getter = "getWindowColor", id = 8)
    private int windowColor;
    @Field(getter = "getWindowType", id = 7)
    private int zzgw;
    @Field(getter = "getWindowCornerRadius", id = 9)
    private int zzgx;
    @Field(getter = "getFontFamily", id = 10)
    private String zzgy;
    @Field(getter = "getFontGenericFamily", id = 11)
    private int zzgz;
    @Field(id = 13)
    private String zzj;
    private JSONObject zzp;

    @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor
    TextTrackStyle(@com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 2) float r1, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 3) int r2, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 4) int r3, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 5) int r4, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 6) int r5, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 7) int r6, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 8) int r7, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 9) int r8, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 10) java.lang.String r9, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 11) int r10, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 12) int r11, @com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param(id = 13) java.lang.String r12) {
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
        r0.fontScale = r1;
        r0.foregroundColor = r2;
        r0.backgroundColor = r3;
        r0.edgeType = r4;
        r0.edgeColor = r5;
        r0.zzgw = r6;
        r0.windowColor = r7;
        r0.zzgx = r8;
        r0.zzgy = r9;
        r0.zzgz = r10;
        r0.fontStyle = r11;
        r0.zzj = r12;
        r1 = r0.zzj;
        r2 = 0;
        if (r1 == 0) goto L_0x002f;
    L_0x0020:
        r1 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x002a }
        r3 = r0.zzj;	 Catch:{ JSONException -> 0x002a }
        r1.<init>(r3);	 Catch:{ JSONException -> 0x002a }
        r0.zzp = r1;	 Catch:{ JSONException -> 0x002a }
        return;
    L_0x002a:
        r0.zzp = r2;
        r0.zzj = r2;
        return;
    L_0x002f:
        r0.zzp = r2;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.TextTrackStyle.<init>(float, int, int, int, int, int, int, int, java.lang.String, int, int, java.lang.String):void");
    }

    public TextTrackStyle() {
        this(1.0f, 0, 0, -1, 0, -1, 0, 0, null, -1, -1, null);
    }

    public final void setFontScale(float f) {
        this.fontScale = f;
    }

    public final float getFontScale() {
        return this.fontScale;
    }

    public final void setForegroundColor(int i) {
        this.foregroundColor = i;
    }

    public final int getForegroundColor() {
        return this.foregroundColor;
    }

    public final void setBackgroundColor(int i) {
        this.backgroundColor = i;
    }

    public final int getBackgroundColor() {
        return this.backgroundColor;
    }

    public final void setEdgeType(int i) {
        if (i < 0 || i > 4) {
            throw new IllegalArgumentException("invalid edgeType");
        }
        this.edgeType = i;
    }

    public final int getEdgeType() {
        return this.edgeType;
    }

    public final void setEdgeColor(int i) {
        this.edgeColor = i;
    }

    public final int getEdgeColor() {
        return this.edgeColor;
    }

    public final void setWindowType(int i) {
        if (i < 0 || i > 2) {
            throw new IllegalArgumentException("invalid windowType");
        }
        this.zzgw = i;
    }

    public final int getWindowType() {
        return this.zzgw;
    }

    public final void setWindowColor(int i) {
        this.windowColor = i;
    }

    public final int getWindowColor() {
        return this.windowColor;
    }

    public final void setWindowCornerRadius(int i) {
        if (i >= 0) {
            this.zzgx = i;
            return;
        }
        throw new IllegalArgumentException("invalid windowCornerRadius");
    }

    public final int getWindowCornerRadius() {
        return this.zzgx;
    }

    public final void setFontFamily(String str) {
        this.zzgy = str;
    }

    public final String getFontFamily() {
        return this.zzgy;
    }

    public final void setFontGenericFamily(int i) {
        if (i < 0 || i > 6) {
            throw new IllegalArgumentException("invalid fontGenericFamily");
        }
        this.zzgz = i;
    }

    public final int getFontGenericFamily() {
        return this.zzgz;
    }

    public final void setFontStyle(int i) {
        if (i < 0 || i > 3) {
            throw new IllegalArgumentException("invalid fontStyle");
        }
        this.fontStyle = i;
    }

    public final int getFontStyle() {
        return this.fontStyle;
    }

    public final void setCustomData(JSONObject jSONObject) {
        this.zzp = jSONObject;
    }

    public final JSONObject getCustomData() {
        return this.zzp;
    }

    @TargetApi(19)
    public static TextTrackStyle fromSystemSettings(Context context) {
        TextTrackStyle textTrackStyle = new TextTrackStyle();
        if (!PlatformVersion.isAtLeastKitKat()) {
            return textTrackStyle;
        }
        CaptioningManager captioningManager = (CaptioningManager) context.getSystemService("captioning");
        textTrackStyle.setFontScale(captioningManager.getFontScale());
        context = captioningManager.getUserStyle();
        textTrackStyle.setBackgroundColor(context.backgroundColor);
        textTrackStyle.setForegroundColor(context.foregroundColor);
        switch (context.edgeType) {
            case 1:
                textTrackStyle.setEdgeType(1);
                break;
            case 2:
                textTrackStyle.setEdgeType(2);
                break;
            default:
                textTrackStyle.setEdgeType(0);
                break;
        }
        textTrackStyle.setEdgeColor(context.edgeColor);
        context = context.getTypeface();
        if (context != null) {
            if (Typeface.MONOSPACE.equals(context)) {
                textTrackStyle.setFontGenericFamily(1);
            } else if (Typeface.SANS_SERIF.equals(context) || !Typeface.SERIF.equals(context)) {
                textTrackStyle.setFontGenericFamily(0);
            } else {
                textTrackStyle.setFontGenericFamily(2);
            }
            boolean isBold = context.isBold();
            context = context.isItalic();
            if (isBold && context != null) {
                textTrackStyle.setFontStyle(3);
            } else if (isBold) {
                textTrackStyle.setFontStyle(1);
            } else if (context != null) {
                textTrackStyle.setFontStyle(2);
            } else {
                textTrackStyle.setFontStyle(0);
            }
        }
        return textTrackStyle;
    }

    public final void zze(JSONObject jSONObject) throws JSONException {
        String string;
        this.fontScale = (float) jSONObject.optDouble("fontScale", 1.0d);
        this.foregroundColor = zzh(jSONObject.optString("foregroundColor"));
        this.backgroundColor = zzh(jSONObject.optString("backgroundColor"));
        if (jSONObject.has(UserPreferences.EDGE_TYPE_KEY)) {
            string = jSONObject.getString(UserPreferences.EDGE_TYPE_KEY);
            if ("NONE".equals(string)) {
                this.edgeType = 0;
            } else if ("OUTLINE".equals(string)) {
                this.edgeType = 1;
            } else if ("DROP_SHADOW".equals(string)) {
                this.edgeType = 2;
            } else if ("RAISED".equals(string)) {
                this.edgeType = 3;
            } else if ("DEPRESSED".equals(string)) {
                this.edgeType = 4;
            }
        }
        this.edgeColor = zzh(jSONObject.optString(UserPreferences.EDGE_COLOR_KEY));
        if (jSONObject.has("windowType")) {
            string = jSONObject.getString("windowType");
            if ("NONE".equals(string)) {
                this.zzgw = 0;
            } else if ("NORMAL".equals(string)) {
                this.zzgw = 1;
            } else if ("ROUNDED_CORNERS".equals(string)) {
                this.zzgw = 2;
            }
        }
        this.windowColor = zzh(jSONObject.optString("windowColor"));
        if (this.zzgw == 2) {
            this.zzgx = jSONObject.optInt("windowRoundedCornerRadius", 0);
        }
        this.zzgy = jSONObject.optString(TtmlNode.ATTR_TTS_FONT_FAMILY, null);
        if (jSONObject.has("fontGenericFamily")) {
            string = jSONObject.getString("fontGenericFamily");
            if ("SANS_SERIF".equals(string)) {
                this.zzgz = 0;
            } else if ("MONOSPACED_SANS_SERIF".equals(string)) {
                this.zzgz = 1;
            } else if ("SERIF".equals(string)) {
                this.zzgz = 2;
            } else if ("MONOSPACED_SERIF".equals(string)) {
                this.zzgz = 3;
            } else if ("CASUAL".equals(string)) {
                this.zzgz = 4;
            } else if ("CURSIVE".equals(string)) {
                this.zzgz = 5;
            } else if ("SMALL_CAPITALS".equals(string)) {
                this.zzgz = 6;
            }
        }
        if (jSONObject.has(TtmlNode.ATTR_TTS_FONT_STYLE)) {
            string = jSONObject.getString(TtmlNode.ATTR_TTS_FONT_STYLE);
            if ("NORMAL".equals(string)) {
                this.fontStyle = 0;
            } else if ("BOLD".equals(string)) {
                this.fontStyle = 1;
            } else if ("ITALIC".equals(string)) {
                this.fontStyle = 2;
            } else if ("BOLD_ITALIC".equals(string)) {
                this.fontStyle = 3;
            }
        }
        this.zzp = jSONObject.optJSONObject(VideoCastManager.EXTRA_CUSTOM_DATA);
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
        r4 = this;
        r0 = new org.json.JSONObject;
        r0.<init>();
        r1 = "fontScale";	 Catch:{ JSONException -> 0x0117 }
        r2 = r4.fontScale;	 Catch:{ JSONException -> 0x0117 }
        r2 = (double) r2;	 Catch:{ JSONException -> 0x0117 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0117 }
        r1 = r4.foregroundColor;	 Catch:{ JSONException -> 0x0117 }
        if (r1 == 0) goto L_0x001c;	 Catch:{ JSONException -> 0x0117 }
    L_0x0011:
        r1 = "foregroundColor";	 Catch:{ JSONException -> 0x0117 }
        r2 = r4.foregroundColor;	 Catch:{ JSONException -> 0x0117 }
        r2 = zzd(r2);	 Catch:{ JSONException -> 0x0117 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0117 }
    L_0x001c:
        r1 = r4.backgroundColor;	 Catch:{ JSONException -> 0x0117 }
        if (r1 == 0) goto L_0x002b;	 Catch:{ JSONException -> 0x0117 }
    L_0x0020:
        r1 = "backgroundColor";	 Catch:{ JSONException -> 0x0117 }
        r2 = r4.backgroundColor;	 Catch:{ JSONException -> 0x0117 }
        r2 = zzd(r2);	 Catch:{ JSONException -> 0x0117 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0117 }
    L_0x002b:
        r1 = r4.edgeType;	 Catch:{ JSONException -> 0x0117 }
        switch(r1) {
            case 0: goto L_0x0051;
            case 1: goto L_0x0049;
            case 2: goto L_0x0041;
            case 3: goto L_0x0039;
            case 4: goto L_0x0031;
            default: goto L_0x0030;
        };	 Catch:{ JSONException -> 0x0117 }
    L_0x0030:
        goto L_0x0058;	 Catch:{ JSONException -> 0x0117 }
    L_0x0031:
        r1 = "edgeType";	 Catch:{ JSONException -> 0x0117 }
        r2 = "DEPRESSED";	 Catch:{ JSONException -> 0x0117 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0117 }
        goto L_0x0058;	 Catch:{ JSONException -> 0x0117 }
    L_0x0039:
        r1 = "edgeType";	 Catch:{ JSONException -> 0x0117 }
        r2 = "RAISED";	 Catch:{ JSONException -> 0x0117 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0117 }
        goto L_0x0058;	 Catch:{ JSONException -> 0x0117 }
    L_0x0041:
        r1 = "edgeType";	 Catch:{ JSONException -> 0x0117 }
        r2 = "DROP_SHADOW";	 Catch:{ JSONException -> 0x0117 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0117 }
        goto L_0x0058;	 Catch:{ JSONException -> 0x0117 }
    L_0x0049:
        r1 = "edgeType";	 Catch:{ JSONException -> 0x0117 }
        r2 = "OUTLINE";	 Catch:{ JSONException -> 0x0117 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0117 }
        goto L_0x0058;	 Catch:{ JSONException -> 0x0117 }
    L_0x0051:
        r1 = "edgeType";	 Catch:{ JSONException -> 0x0117 }
        r2 = "NONE";	 Catch:{ JSONException -> 0x0117 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0117 }
    L_0x0058:
        r1 = r4.edgeColor;	 Catch:{ JSONException -> 0x0117 }
        if (r1 == 0) goto L_0x0067;	 Catch:{ JSONException -> 0x0117 }
    L_0x005c:
        r1 = "edgeColor";	 Catch:{ JSONException -> 0x0117 }
        r2 = r4.edgeColor;	 Catch:{ JSONException -> 0x0117 }
        r2 = zzd(r2);	 Catch:{ JSONException -> 0x0117 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0117 }
    L_0x0067:
        r1 = r4.zzgw;	 Catch:{ JSONException -> 0x0117 }
        switch(r1) {
            case 0: goto L_0x007d;
            case 1: goto L_0x0075;
            case 2: goto L_0x006d;
            default: goto L_0x006c;
        };	 Catch:{ JSONException -> 0x0117 }
    L_0x006c:
        goto L_0x0084;	 Catch:{ JSONException -> 0x0117 }
    L_0x006d:
        r1 = "windowType";	 Catch:{ JSONException -> 0x0117 }
        r2 = "ROUNDED_CORNERS";	 Catch:{ JSONException -> 0x0117 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0117 }
        goto L_0x0084;	 Catch:{ JSONException -> 0x0117 }
    L_0x0075:
        r1 = "windowType";	 Catch:{ JSONException -> 0x0117 }
        r2 = "NORMAL";	 Catch:{ JSONException -> 0x0117 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0117 }
        goto L_0x0084;	 Catch:{ JSONException -> 0x0117 }
    L_0x007d:
        r1 = "windowType";	 Catch:{ JSONException -> 0x0117 }
        r2 = "NONE";	 Catch:{ JSONException -> 0x0117 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0117 }
    L_0x0084:
        r1 = r4.windowColor;	 Catch:{ JSONException -> 0x0117 }
        if (r1 == 0) goto L_0x0093;	 Catch:{ JSONException -> 0x0117 }
    L_0x0088:
        r1 = "windowColor";	 Catch:{ JSONException -> 0x0117 }
        r2 = r4.windowColor;	 Catch:{ JSONException -> 0x0117 }
        r2 = zzd(r2);	 Catch:{ JSONException -> 0x0117 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0117 }
    L_0x0093:
        r1 = r4.zzgw;	 Catch:{ JSONException -> 0x0117 }
        r2 = 2;	 Catch:{ JSONException -> 0x0117 }
        if (r1 != r2) goto L_0x009f;	 Catch:{ JSONException -> 0x0117 }
    L_0x0098:
        r1 = "windowRoundedCornerRadius";	 Catch:{ JSONException -> 0x0117 }
        r2 = r4.zzgx;	 Catch:{ JSONException -> 0x0117 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0117 }
    L_0x009f:
        r1 = r4.zzgy;	 Catch:{ JSONException -> 0x0117 }
        if (r1 == 0) goto L_0x00aa;	 Catch:{ JSONException -> 0x0117 }
    L_0x00a3:
        r1 = "fontFamily";	 Catch:{ JSONException -> 0x0117 }
        r2 = r4.zzgy;	 Catch:{ JSONException -> 0x0117 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0117 }
    L_0x00aa:
        r1 = r4.zzgz;	 Catch:{ JSONException -> 0x0117 }
        switch(r1) {
            case 0: goto L_0x00e0;
            case 1: goto L_0x00d8;
            case 2: goto L_0x00d0;
            case 3: goto L_0x00c8;
            case 4: goto L_0x00c0;
            case 5: goto L_0x00b8;
            case 6: goto L_0x00b0;
            default: goto L_0x00af;
        };	 Catch:{ JSONException -> 0x0117 }
    L_0x00af:
        goto L_0x00e7;	 Catch:{ JSONException -> 0x0117 }
    L_0x00b0:
        r1 = "fontGenericFamily";	 Catch:{ JSONException -> 0x0117 }
        r2 = "SMALL_CAPITALS";	 Catch:{ JSONException -> 0x0117 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0117 }
        goto L_0x00e7;	 Catch:{ JSONException -> 0x0117 }
    L_0x00b8:
        r1 = "fontGenericFamily";	 Catch:{ JSONException -> 0x0117 }
        r2 = "CURSIVE";	 Catch:{ JSONException -> 0x0117 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0117 }
        goto L_0x00e7;	 Catch:{ JSONException -> 0x0117 }
    L_0x00c0:
        r1 = "fontGenericFamily";	 Catch:{ JSONException -> 0x0117 }
        r2 = "CASUAL";	 Catch:{ JSONException -> 0x0117 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0117 }
        goto L_0x00e7;	 Catch:{ JSONException -> 0x0117 }
    L_0x00c8:
        r1 = "fontGenericFamily";	 Catch:{ JSONException -> 0x0117 }
        r2 = "MONOSPACED_SERIF";	 Catch:{ JSONException -> 0x0117 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0117 }
        goto L_0x00e7;	 Catch:{ JSONException -> 0x0117 }
    L_0x00d0:
        r1 = "fontGenericFamily";	 Catch:{ JSONException -> 0x0117 }
        r2 = "SERIF";	 Catch:{ JSONException -> 0x0117 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0117 }
        goto L_0x00e7;	 Catch:{ JSONException -> 0x0117 }
    L_0x00d8:
        r1 = "fontGenericFamily";	 Catch:{ JSONException -> 0x0117 }
        r2 = "MONOSPACED_SANS_SERIF";	 Catch:{ JSONException -> 0x0117 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0117 }
        goto L_0x00e7;	 Catch:{ JSONException -> 0x0117 }
    L_0x00e0:
        r1 = "fontGenericFamily";	 Catch:{ JSONException -> 0x0117 }
        r2 = "SANS_SERIF";	 Catch:{ JSONException -> 0x0117 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0117 }
    L_0x00e7:
        r1 = r4.fontStyle;	 Catch:{ JSONException -> 0x0117 }
        switch(r1) {
            case 0: goto L_0x0105;
            case 1: goto L_0x00fd;
            case 2: goto L_0x00f5;
            case 3: goto L_0x00ed;
            default: goto L_0x00ec;
        };	 Catch:{ JSONException -> 0x0117 }
    L_0x00ec:
        goto L_0x010c;	 Catch:{ JSONException -> 0x0117 }
    L_0x00ed:
        r1 = "fontStyle";	 Catch:{ JSONException -> 0x0117 }
        r2 = "BOLD_ITALIC";	 Catch:{ JSONException -> 0x0117 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0117 }
        goto L_0x010c;	 Catch:{ JSONException -> 0x0117 }
    L_0x00f5:
        r1 = "fontStyle";	 Catch:{ JSONException -> 0x0117 }
        r2 = "ITALIC";	 Catch:{ JSONException -> 0x0117 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0117 }
        goto L_0x010c;	 Catch:{ JSONException -> 0x0117 }
    L_0x00fd:
        r1 = "fontStyle";	 Catch:{ JSONException -> 0x0117 }
        r2 = "BOLD";	 Catch:{ JSONException -> 0x0117 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0117 }
        goto L_0x010c;	 Catch:{ JSONException -> 0x0117 }
    L_0x0105:
        r1 = "fontStyle";	 Catch:{ JSONException -> 0x0117 }
        r2 = "NORMAL";	 Catch:{ JSONException -> 0x0117 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0117 }
    L_0x010c:
        r1 = r4.zzp;	 Catch:{ JSONException -> 0x0117 }
        if (r1 == 0) goto L_0x0117;	 Catch:{ JSONException -> 0x0117 }
    L_0x0110:
        r1 = "customData";	 Catch:{ JSONException -> 0x0117 }
        r2 = r4.zzp;	 Catch:{ JSONException -> 0x0117 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x0117 }
    L_0x0117:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.TextTrackStyle.toJson():org.json.JSONObject");
    }

    private static String zzd(int i) {
        return String.format("#%02X%02X%02X%02X", new Object[]{Integer.valueOf(Color.red(i)), Integer.valueOf(Color.green(i)), Integer.valueOf(Color.blue(i)), Integer.valueOf(Color.alpha(i))});
    }

    private static int zzh(java.lang.String r7) {
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
        r0 = 0;
        if (r7 == 0) goto L_0x003e;
    L_0x0003:
        r1 = r7.length();
        r2 = 9;
        if (r1 != r2) goto L_0x003e;
    L_0x000b:
        r1 = r7.charAt(r0);
        r3 = 35;
        if (r1 != r3) goto L_0x003e;
    L_0x0013:
        r1 = 1;
        r3 = 3;
        r1 = r7.substring(r1, r3);	 Catch:{ NumberFormatException -> 0x003e }
        r4 = 16;	 Catch:{ NumberFormatException -> 0x003e }
        r1 = java.lang.Integer.parseInt(r1, r4);	 Catch:{ NumberFormatException -> 0x003e }
        r5 = 5;	 Catch:{ NumberFormatException -> 0x003e }
        r3 = r7.substring(r3, r5);	 Catch:{ NumberFormatException -> 0x003e }
        r3 = java.lang.Integer.parseInt(r3, r4);	 Catch:{ NumberFormatException -> 0x003e }
        r6 = 7;	 Catch:{ NumberFormatException -> 0x003e }
        r5 = r7.substring(r5, r6);	 Catch:{ NumberFormatException -> 0x003e }
        r5 = java.lang.Integer.parseInt(r5, r4);	 Catch:{ NumberFormatException -> 0x003e }
        r7 = r7.substring(r6, r2);	 Catch:{ NumberFormatException -> 0x003e }
        r7 = java.lang.Integer.parseInt(r7, r4);	 Catch:{ NumberFormatException -> 0x003e }
        r7 = android.graphics.Color.argb(r7, r1, r3, r5);	 Catch:{ NumberFormatException -> 0x003e }
        return r7;
    L_0x003e:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.TextTrackStyle.zzh(java.lang.String):int");
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TextTrackStyle)) {
            return false;
        }
        TextTrackStyle textTrackStyle = (TextTrackStyle) obj;
        if ((this.zzp == null ? 1 : null) != (textTrackStyle.zzp == null ? 1 : null)) {
            return false;
        }
        return (this.zzp == null || textTrackStyle.zzp == null || JsonUtils.areJsonValuesEquivalent(this.zzp, textTrackStyle.zzp)) && this.fontScale == textTrackStyle.fontScale && this.foregroundColor == textTrackStyle.foregroundColor && this.backgroundColor == textTrackStyle.backgroundColor && this.edgeType == textTrackStyle.edgeType && this.edgeColor == textTrackStyle.edgeColor && this.zzgw == textTrackStyle.zzgw && this.zzgx == textTrackStyle.zzgx && zzcv.zza(this.zzgy, textTrackStyle.zzgy) && this.zzgz == textTrackStyle.zzgz && this.fontStyle == textTrackStyle.fontStyle;
    }

    public final int hashCode() {
        return Objects.hashCode(Float.valueOf(this.fontScale), Integer.valueOf(this.foregroundColor), Integer.valueOf(this.backgroundColor), Integer.valueOf(this.edgeType), Integer.valueOf(this.edgeColor), Integer.valueOf(this.zzgw), Integer.valueOf(this.windowColor), Integer.valueOf(this.zzgx), this.zzgy, Integer.valueOf(this.zzgz), Integer.valueOf(this.fontStyle), String.valueOf(this.zzp));
    }

    public final void writeToParcel(Parcel parcel, int i) {
        this.zzj = this.zzp == 0 ? 0 : this.zzp.toString();
        i = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeFloat(parcel, 2, getFontScale());
        SafeParcelWriter.writeInt(parcel, 3, getForegroundColor());
        SafeParcelWriter.writeInt(parcel, 4, getBackgroundColor());
        SafeParcelWriter.writeInt(parcel, 5, getEdgeType());
        SafeParcelWriter.writeInt(parcel, 6, getEdgeColor());
        SafeParcelWriter.writeInt(parcel, 7, getWindowType());
        SafeParcelWriter.writeInt(parcel, 8, getWindowColor());
        SafeParcelWriter.writeInt(parcel, 9, getWindowCornerRadius());
        SafeParcelWriter.writeString(parcel, 10, getFontFamily(), false);
        SafeParcelWriter.writeInt(parcel, 11, getFontGenericFamily());
        SafeParcelWriter.writeInt(parcel, 12, getFontStyle());
        SafeParcelWriter.writeString(parcel, 13, this.zzj, false);
        SafeParcelWriter.finishObjectHeader(parcel, i);
    }
}
