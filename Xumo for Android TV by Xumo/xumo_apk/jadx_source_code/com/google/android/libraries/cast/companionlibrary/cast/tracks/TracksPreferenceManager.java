package com.google.android.libraries.cast.companionlibrary.cast.tracks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.view.accessibility.CaptioningManager;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.libraries.cast.companionlibrary.R;
import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;
import com.google.android.libraries.cast.companionlibrary.utils.LogUtils;
import com.google.android.libraries.cast.companionlibrary.utils.PreferenceAccessor;
import com.google.android.libraries.cast.companionlibrary.utils.Utils;
import java.util.HashMap;
import java.util.Map;

public class TracksPreferenceManager implements OnSharedPreferenceChangeListener {
    private static final String EDGE_TYPE_DEFAULT = "EDGE_TYPE_NONE";
    private static final Map<String, Integer> EDGE_TYPE_MAPPING = new HashMap();
    private static final Map<String, Integer> FONT_FAMILY_MAPPING = new HashMap();
    private static final String FONT_FAMILY_SANS_SERIF = "FONT_FAMILY_SANS_SERIF";
    private static final Map<String, String> OPACITY_MAPPING = new HashMap();
    private static final String TAG = LogUtils.makeLogTag(TracksPreferenceManager.class);
    private boolean isInitialized = false;
    private CheckBoxPreference mCaptionAvailability;
    private ListPreference mCaptionBackgroundColorListPreference;
    private ListPreference mCaptionBackgroundOpacityListPreference;
    private ListPreference mCaptionEdgeTypeListPreference;
    private ListPreference mCaptionFontFamilyListPreference;
    private ListPreference mCaptionFontScaleListPreference;
    private ListPreference mCaptionTextColorListPreference;
    private ListPreference mCaptionTextOpacityListPreference;
    private final Context mContext;
    private final PreferenceAccessor mPreferenceAccessor;
    private final SharedPreferences mSharedPreferences;

    static {
        OPACITY_MAPPING.put("FF", "100");
        OPACITY_MAPPING.put("BF", "75");
        OPACITY_MAPPING.put("80", "50");
        OPACITY_MAPPING.put("3F", "25");
        FONT_FAMILY_MAPPING.put(FONT_FAMILY_SANS_SERIF, Integer.valueOf(0));
        FONT_FAMILY_MAPPING.put("FONT_FAMILY_SERIF", Integer.valueOf(2));
        FONT_FAMILY_MAPPING.put("FONT_FAMILY_MONOSPACED_SANS_SERIF", Integer.valueOf(1));
        EDGE_TYPE_MAPPING.put(EDGE_TYPE_DEFAULT, Integer.valueOf(0));
        EDGE_TYPE_MAPPING.put("EDGE_TYPE_OUTLINE", Integer.valueOf(1));
        EDGE_TYPE_MAPPING.put("EDGE_TYPE_DROP_SHADOW", Integer.valueOf(2));
    }

    public TracksPreferenceManager(Context context) {
        this.mContext = context;
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        this.mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
        this.mPreferenceAccessor = VideoCastManager.getInstance().getPreferenceAccessor();
    }

    public TextTrackStyle getTextTrackStyle() {
        TextTrackStyle fromSystemSettings = TextTrackStyle.fromSystemSettings(this.mContext);
        if (Utils.IS_KITKAT_OR_ABOVE) {
            return fromSystemSettings;
        }
        fromSystemSettings.setFontGenericFamily(((Integer) FONT_FAMILY_MAPPING.get(getFontFamily())).intValue());
        fromSystemSettings.setBackgroundColor(Color.parseColor(getBackgroundColor()));
        fromSystemSettings.setEdgeType(((Integer) EDGE_TYPE_MAPPING.get(getEdgeType())).intValue());
        fromSystemSettings.setFontScale(getFontScale());
        boolean isBold = Typeface.DEFAULT.isBold();
        boolean isItalic = Typeface.DEFAULT.isItalic();
        int i = 0;
        if (isBold && isItalic) {
            i = 3;
        } else if (isBold || isItalic) {
            if (isBold) {
                i = 1;
            }
        }
        fromSystemSettings.setFontStyle(i);
        fromSystemSettings.setForegroundColor(combineColorAndOpacity(getTextColor(), getTextOpacity()));
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Edge is: ");
        stringBuilder.append(getEdgeType());
        LogUtils.LOGD(str, stringBuilder.toString());
        fromSystemSettings.setBackgroundColor(combineColorAndOpacity(getBackgroundColor(), getBackgroundOpacity()));
        return fromSystemSettings;
    }

    @SuppressLint({"NewApi"})
    public boolean isCaptionEnabled() {
        if (Utils.IS_KITKAT_OR_ABOVE) {
            return ((CaptioningManager) this.mContext.getSystemService("captioning")).isEnabled();
        }
        return this.mPreferenceAccessor.getBooleanFromPreference(this.mContext.getString(R.string.ccl_key_caption_enabled), false);
    }

    public void setFontFamily(String str) {
        this.mPreferenceAccessor.saveStringToPreference(this.mContext.getString(R.string.ccl_key_caption_font_family), str);
    }

    public String getFontFamily() {
        return this.mPreferenceAccessor.getStringFromPreference(this.mContext.getString(R.string.ccl_key_caption_font_family), FONT_FAMILY_SANS_SERIF);
    }

    public void setFontScale(String str) {
        this.mPreferenceAccessor.saveStringToPreference(this.mContext.getString(R.string.ccl_key_caption_font_scale), str);
    }

    public float getFontScale() {
        return Float.parseFloat(this.mPreferenceAccessor.getStringFromPreference(this.mContext.getString(R.string.ccl_key_caption_font_scale), String.valueOf(1.0f)));
    }

    public void setTextColor(String str) {
        this.mPreferenceAccessor.saveStringToPreference(this.mContext.getString(R.string.ccl_key_caption_text_color), str);
    }

    public String getTextColor() {
        return this.mPreferenceAccessor.getStringFromPreference(this.mContext.getString(R.string.ccl_key_caption_text_color), this.mContext.getString(R.string.ccl_prefs_caption_text_color_value_default));
    }

    public void setTextOpacity(String str) {
        this.mPreferenceAccessor.saveStringToPreference(this.mContext.getString(R.string.ccl_key_caption_text_opacity), str);
    }

    public String getTextOpacity() {
        return this.mPreferenceAccessor.getStringFromPreference(this.mContext.getString(R.string.ccl_key_caption_text_opacity), this.mContext.getString(R.string.ccl_prefs_caption_text_opacity_value_default));
    }

    public void setEdgeType(String str) {
        this.mPreferenceAccessor.saveStringToPreference(this.mContext.getString(R.string.ccl_key_caption_edge_type), str);
    }

    public String getEdgeType() {
        return this.mPreferenceAccessor.getStringFromPreference(this.mContext.getString(R.string.ccl_key_caption_edge_type), EDGE_TYPE_DEFAULT);
    }

    public void setBackgroundColor(Context context, String str) {
        this.mPreferenceAccessor.saveStringToPreference(context.getString(R.string.ccl_key_caption_background_color), str);
    }

    public String getBackgroundColor() {
        return this.mPreferenceAccessor.getStringFromPreference(this.mContext.getString(R.string.ccl_key_caption_background_color), this.mContext.getString(R.string.ccl_prefs_caption_background_color_value_default));
    }

    public void setBackgroundOpacity(String str) {
        this.mPreferenceAccessor.saveStringToPreference(this.mContext.getString(R.string.ccl_key_caption_background_opacity), str);
    }

    public String getBackgroundOpacity() {
        return this.mPreferenceAccessor.getStringFromPreference(this.mContext.getString(R.string.ccl_key_caption_background_opacity), this.mContext.getString(R.string.ccl_prefs_caption_background_opacity_value_default));
    }

    public void setUpPreferences(PreferenceScreen preferenceScreen) {
        this.mCaptionAvailability = (CheckBoxPreference) preferenceScreen.findPreference(this.mContext.getString(R.string.ccl_key_caption_enabled));
        this.mCaptionFontScaleListPreference = (ListPreference) preferenceScreen.findPreference(this.mContext.getString(R.string.ccl_key_caption_font_scale));
        this.mCaptionFontFamilyListPreference = (ListPreference) preferenceScreen.findPreference(this.mContext.getString(R.string.ccl_key_caption_font_family));
        this.mCaptionTextColorListPreference = (ListPreference) preferenceScreen.findPreference(this.mContext.getString(R.string.ccl_key_caption_text_color));
        this.mCaptionTextOpacityListPreference = (ListPreference) preferenceScreen.findPreference(this.mContext.getString(R.string.ccl_key_caption_text_opacity));
        this.mCaptionEdgeTypeListPreference = (ListPreference) preferenceScreen.findPreference(this.mContext.getString(R.string.ccl_key_caption_edge_type));
        this.mCaptionBackgroundColorListPreference = (ListPreference) preferenceScreen.findPreference(this.mContext.getString(R.string.ccl_key_caption_background_color));
        this.mCaptionBackgroundOpacityListPreference = (ListPreference) preferenceScreen.findPreference(this.mContext.getString(R.string.ccl_key_caption_background_opacity));
        this.isInitialized = true;
        onSharedPreferenceChanged(this.mSharedPreferences, this.mContext.getString(R.string.ccl_key_caption_enabled), false);
        onSharedPreferenceChanged(this.mSharedPreferences, this.mContext.getString(R.string.ccl_key_caption_font_family), false);
        onSharedPreferenceChanged(this.mSharedPreferences, this.mContext.getString(R.string.ccl_key_caption_font_scale), false);
        onSharedPreferenceChanged(this.mSharedPreferences, this.mContext.getString(R.string.ccl_key_caption_text_color), false);
        onSharedPreferenceChanged(this.mSharedPreferences, this.mContext.getString(R.string.ccl_key_caption_text_opacity), false);
        onSharedPreferenceChanged(this.mSharedPreferences, this.mContext.getString(R.string.ccl_key_caption_edge_type), false);
        onSharedPreferenceChanged(this.mSharedPreferences, this.mContext.getString(R.string.ccl_key_caption_background_color), false);
        onSharedPreferenceChanged(this.mSharedPreferences, this.mContext.getString(R.string.ccl_key_caption_background_opacity), false);
    }

    private void setCaptionAvailability(boolean z) {
        this.mCaptionFontScaleListPreference.setEnabled(z);
        this.mCaptionFontFamilyListPreference.setEnabled(z);
        this.mCaptionTextColorListPreference.setEnabled(z);
        this.mCaptionTextOpacityListPreference.setEnabled(z);
        this.mCaptionEdgeTypeListPreference.setEnabled(z);
        this.mCaptionBackgroundColorListPreference.setEnabled(z);
        this.mCaptionBackgroundOpacityListPreference.setEnabled(z);
    }

    private String getCaptionSummaryForList(SharedPreferences sharedPreferences, int i, int i2, int i3, int i4) {
        Resources resources = this.mContext.getResources();
        sharedPreferences = sharedPreferences.getString(resources.getString(i), resources.getString(i2));
        i = resources.getStringArray(i3);
        i2 = resources.getStringArray(i4);
        for (i3 = 0; i3 < i2.length; i3++) {
            if (i2[i3].equals(sharedPreferences) != 0) {
                return i[i3];
            }
        }
        return "";
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
        onSharedPreferenceChanged(sharedPreferences, str, true);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str, boolean z) {
        if (!this.isInitialized) {
            return;
        }
        if (this.mContext.getString(R.string.ccl_key_caption_enabled).equals(str)) {
            this.mCaptionAvailability.setSummary(this.mCaptionAvailability.isChecked() != null ? R.string.ccl_prefs_caption_enabled : R.string.ccl_prefs_caption_disabled);
            setCaptionAvailability(this.mCaptionAvailability.isChecked());
            if (z) {
                VideoCastManager.getInstance().onTextTrackEnabledChanged(this.mCaptionAvailability.isChecked());
            }
            return;
        }
        if (this.mContext.getString(R.string.ccl_key_caption_font_scale).equals(str)) {
            this.mCaptionFontScaleListPreference.setSummary(getCaptionSummaryForList(sharedPreferences, R.string.ccl_key_caption_font_scale, R.string.ccl_prefs_caption_font_scale_value_default, R.array.ccl_prefs_caption_font_scale_names, R.array.ccl_prefs_caption_font_scale_values));
        } else if (this.mContext.getString(R.string.ccl_key_caption_font_family).equals(str)) {
            this.mCaptionFontFamilyListPreference.setSummary(getCaptionSummaryForList(sharedPreferences, R.string.ccl_key_caption_font_family, R.string.ccl_prefs_caption_font_family_value_default, R.array.ccl_prefs_caption_font_family_names, R.array.ccl_prefs_caption_font_family_values));
        } else if (this.mContext.getString(R.string.ccl_key_caption_text_color).equals(str)) {
            this.mCaptionTextColorListPreference.setSummary(getCaptionSummaryForList(sharedPreferences, R.string.ccl_key_caption_text_color, R.string.ccl_prefs_caption_text_color_value_default, R.array.ccl_prefs_caption_color_names, R.array.ccl_prefs_caption_color_values));
        } else if (this.mContext.getString(R.string.ccl_key_caption_text_opacity).equals(str)) {
            sharedPreferences = this.mPreferenceAccessor.getStringFromPreference(this.mContext.getString(R.string.ccl_key_caption_text_opacity), this.mContext.getString(R.string.ccl_prefs_caption_text_opacity_value_default));
            str = this.mCaptionTextOpacityListPreference;
            r0 = new StringBuilder();
            r0.append((String) OPACITY_MAPPING.get(sharedPreferences));
            r0.append("%%");
            str.setSummary(r0.toString());
        } else if (this.mContext.getString(R.string.ccl_key_caption_edge_type).equals(str)) {
            this.mCaptionEdgeTypeListPreference.setSummary(getCaptionSummaryForList(sharedPreferences, R.string.ccl_key_caption_edge_type, R.string.ccl_prefs_caption_edge_type_value_default, R.array.ccl_prefs_caption_edge_type_names, R.array.ccl_prefs_caption_edge_type_values));
        } else if (this.mContext.getString(R.string.ccl_key_caption_background_color).equals(str)) {
            this.mCaptionBackgroundColorListPreference.setSummary(getCaptionSummaryForList(sharedPreferences, R.string.ccl_key_caption_background_color, R.string.ccl_prefs_caption_background_color_value_default, R.array.ccl_prefs_caption_color_names, R.array.ccl_prefs_caption_color_values));
        } else if (this.mContext.getString(R.string.ccl_key_caption_background_opacity).equals(str) != null) {
            sharedPreferences = this.mPreferenceAccessor.getStringFromPreference(this.mContext.getString(R.string.ccl_key_caption_background_opacity), this.mContext.getString(R.string.ccl_prefs_caption_background_opacity_value_default));
            str = this.mCaptionBackgroundOpacityListPreference;
            r0 = new StringBuilder();
            r0.append((String) OPACITY_MAPPING.get(sharedPreferences));
            r0.append("%%");
            str.setSummary(r0.toString());
        }
        if (z) {
            VideoCastManager.getInstance().onTextTrackStyleChanged(getTextTrackStyle());
        }
    }

    private static int combineColorAndOpacity(String str, String str2) {
        str = str.replace("#", "");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("#");
        stringBuilder.append(str2);
        stringBuilder.append(str);
        return Color.parseColor(stringBuilder.toString());
    }
}
