package androidx.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import androidx.core.content.ContextCompat;

public class PreferenceManager {
    public static final String KEY_HAS_SET_DEFAULT_VALUES = "_has_set_default_values";
    private static final int STORAGE_DEFAULT = 0;
    private static final int STORAGE_DEVICE_PROTECTED = 1;
    private Context mContext;
    @Nullable
    private Editor mEditor;
    private long mNextId = 0;
    private boolean mNoCommit;
    private OnDisplayPreferenceDialogListener mOnDisplayPreferenceDialogListener;
    private OnNavigateToScreenListener mOnNavigateToScreenListener;
    private OnPreferenceTreeClickListener mOnPreferenceTreeClickListener;
    private PreferenceComparisonCallback mPreferenceComparisonCallback;
    @Nullable
    private PreferenceDataStore mPreferenceDataStore;
    private PreferenceScreen mPreferenceScreen;
    @Nullable
    private SharedPreferences mSharedPreferences;
    private int mSharedPreferencesMode;
    private String mSharedPreferencesName;
    private int mStorage = 0;

    public interface OnDisplayPreferenceDialogListener {
        void onDisplayPreferenceDialog(Preference preference);
    }

    public interface OnNavigateToScreenListener {
        void onNavigateToScreen(PreferenceScreen preferenceScreen);
    }

    public interface OnPreferenceTreeClickListener {
        boolean onPreferenceTreeClick(Preference preference);
    }

    public static abstract class PreferenceComparisonCallback {
        public abstract boolean arePreferenceContentsTheSame(Preference preference, Preference preference2);

        public abstract boolean arePreferenceItemsTheSame(Preference preference, Preference preference2);
    }

    public static class SimplePreferenceComparisonCallback extends PreferenceComparisonCallback {
        public boolean arePreferenceItemsTheSame(Preference preference, Preference preference2) {
            return preference.getId() == preference2.getId() ? true : null;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean arePreferenceContentsTheSame(androidx.preference.Preference r4, androidx.preference.Preference r5) {
            /*
            r3 = this;
            r0 = r4.getClass();
            r1 = r5.getClass();
            r2 = 0;
            if (r0 == r1) goto L_0x000c;
        L_0x000b:
            return r2;
        L_0x000c:
            if (r4 != r5) goto L_0x0015;
        L_0x000e:
            r0 = r4.wasDetached();
            if (r0 == 0) goto L_0x0015;
        L_0x0014:
            return r2;
        L_0x0015:
            r0 = r4.getTitle();
            r1 = r5.getTitle();
            r0 = android.text.TextUtils.equals(r0, r1);
            if (r0 != 0) goto L_0x0024;
        L_0x0023:
            return r2;
        L_0x0024:
            r0 = r4.getSummary();
            r1 = r5.getSummary();
            r0 = android.text.TextUtils.equals(r0, r1);
            if (r0 != 0) goto L_0x0033;
        L_0x0032:
            return r2;
        L_0x0033:
            r0 = r4.getIcon();
            r1 = r5.getIcon();
            if (r0 == r1) goto L_0x0046;
        L_0x003d:
            if (r0 == 0) goto L_0x0045;
        L_0x003f:
            r0 = r0.equals(r1);
            if (r0 != 0) goto L_0x0046;
        L_0x0045:
            return r2;
        L_0x0046:
            r0 = r4.isEnabled();
            r1 = r5.isEnabled();
            if (r0 == r1) goto L_0x0051;
        L_0x0050:
            return r2;
        L_0x0051:
            r0 = r4.isSelectable();
            r1 = r5.isSelectable();
            if (r0 == r1) goto L_0x005c;
        L_0x005b:
            return r2;
        L_0x005c:
            r0 = r4 instanceof androidx.preference.TwoStatePreference;
            if (r0 == 0) goto L_0x0071;
        L_0x0060:
            r0 = r4;
            r0 = (androidx.preference.TwoStatePreference) r0;
            r0 = r0.isChecked();
            r1 = r5;
            r1 = (androidx.preference.TwoStatePreference) r1;
            r1 = r1.isChecked();
            if (r0 == r1) goto L_0x0071;
        L_0x0070:
            return r2;
        L_0x0071:
            r0 = r4 instanceof androidx.preference.DropDownPreference;
            if (r0 == 0) goto L_0x0078;
        L_0x0075:
            if (r4 == r5) goto L_0x0078;
        L_0x0077:
            return r2;
        L_0x0078:
            r4 = 1;
            return r4;
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.preference.PreferenceManager.SimplePreferenceComparisonCallback.arePreferenceContentsTheSame(androidx.preference.Preference, androidx.preference.Preference):boolean");
        }
    }

    private static int getDefaultSharedPreferencesMode() {
        return 0;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public PreferenceManager(Context context) {
        this.mContext = context;
        setSharedPreferencesName(getDefaultSharedPreferencesName(context));
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public PreferenceScreen inflateFromResource(Context context, int i, PreferenceScreen preferenceScreen) {
        setNoCommit(true);
        PreferenceScreen preferenceScreen2 = (PreferenceScreen) new PreferenceInflater(context, this).inflate(i, (PreferenceGroup) preferenceScreen);
        preferenceScreen2.onAttachedToHierarchy(this);
        setNoCommit(0);
        return preferenceScreen2;
    }

    public PreferenceScreen createPreferenceScreen(Context context) {
        PreferenceScreen preferenceScreen = new PreferenceScreen(context, null);
        preferenceScreen.onAttachedToHierarchy(this);
        return preferenceScreen;
    }

    long getNextId() {
        long j;
        synchronized (this) {
            j = this.mNextId;
            this.mNextId = 1 + j;
        }
        return j;
    }

    public String getSharedPreferencesName() {
        return this.mSharedPreferencesName;
    }

    public void setSharedPreferencesName(String str) {
        this.mSharedPreferencesName = str;
        this.mSharedPreferences = null;
    }

    public int getSharedPreferencesMode() {
        return this.mSharedPreferencesMode;
    }

    public void setSharedPreferencesMode(int i) {
        this.mSharedPreferencesMode = i;
        this.mSharedPreferences = 0;
    }

    public void setStorageDefault() {
        if (VERSION.SDK_INT >= 24) {
            this.mStorage = 0;
            this.mSharedPreferences = null;
        }
    }

    public void setStorageDeviceProtected() {
        if (VERSION.SDK_INT >= 24) {
            this.mStorage = 1;
            this.mSharedPreferences = null;
        }
    }

    public boolean isStorageDefault() {
        boolean z = true;
        if (VERSION.SDK_INT < 24) {
            return true;
        }
        if (this.mStorage != 0) {
            z = false;
        }
        return z;
    }

    public boolean isStorageDeviceProtected() {
        boolean z = false;
        if (VERSION.SDK_INT < 24) {
            return false;
        }
        if (this.mStorage == 1) {
            z = true;
        }
        return z;
    }

    public void setPreferenceDataStore(PreferenceDataStore preferenceDataStore) {
        this.mPreferenceDataStore = preferenceDataStore;
    }

    @Nullable
    public PreferenceDataStore getPreferenceDataStore() {
        return this.mPreferenceDataStore;
    }

    public SharedPreferences getSharedPreferences() {
        if (getPreferenceDataStore() != null) {
            return null;
        }
        if (this.mSharedPreferences == null) {
            Context context;
            if (this.mStorage != 1) {
                context = this.mContext;
            } else {
                context = ContextCompat.createDeviceProtectedStorageContext(this.mContext);
            }
            this.mSharedPreferences = context.getSharedPreferences(this.mSharedPreferencesName, this.mSharedPreferencesMode);
        }
        return this.mSharedPreferences;
    }

    public static SharedPreferences getDefaultSharedPreferences(Context context) {
        return context.getSharedPreferences(getDefaultSharedPreferencesName(context), getDefaultSharedPreferencesMode());
    }

    private static String getDefaultSharedPreferencesName(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(context.getPackageName());
        stringBuilder.append("_preferences");
        return stringBuilder.toString();
    }

    public PreferenceScreen getPreferenceScreen() {
        return this.mPreferenceScreen;
    }

    public boolean setPreferences(PreferenceScreen preferenceScreen) {
        if (preferenceScreen == this.mPreferenceScreen) {
            return null;
        }
        if (this.mPreferenceScreen != null) {
            this.mPreferenceScreen.onDetached();
        }
        this.mPreferenceScreen = preferenceScreen;
        return true;
    }

    public Preference findPreference(CharSequence charSequence) {
        if (this.mPreferenceScreen == null) {
            return null;
        }
        return this.mPreferenceScreen.findPreference(charSequence);
    }

    public static void setDefaultValues(Context context, int i, boolean z) {
        setDefaultValues(context, getDefaultSharedPreferencesName(context), getDefaultSharedPreferencesMode(), i, z);
    }

    public static void setDefaultValues(Context context, String str, int i, int i2, boolean z) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_HAS_SET_DEFAULT_VALUES, 0);
        if (z || !sharedPreferences.getBoolean(KEY_HAS_SET_DEFAULT_VALUES, false)) {
            z = new PreferenceManager(context);
            z.setSharedPreferencesName(str);
            z.setSharedPreferencesMode(i);
            z.inflateFromResource(context, i2, null);
            sharedPreferences.edit().putBoolean(KEY_HAS_SET_DEFAULT_VALUES, 1).apply();
        }
    }

    Editor getEditor() {
        if (this.mPreferenceDataStore != null) {
            return null;
        }
        if (!this.mNoCommit) {
            return getSharedPreferences().edit();
        }
        if (this.mEditor == null) {
            this.mEditor = getSharedPreferences().edit();
        }
        return this.mEditor;
    }

    boolean shouldCommit() {
        return this.mNoCommit ^ 1;
    }

    private void setNoCommit(boolean z) {
        if (!(z || this.mEditor == null)) {
            this.mEditor.apply();
        }
        this.mNoCommit = z;
    }

    public Context getContext() {
        return this.mContext;
    }

    public PreferenceComparisonCallback getPreferenceComparisonCallback() {
        return this.mPreferenceComparisonCallback;
    }

    public void setPreferenceComparisonCallback(PreferenceComparisonCallback preferenceComparisonCallback) {
        this.mPreferenceComparisonCallback = preferenceComparisonCallback;
    }

    public OnDisplayPreferenceDialogListener getOnDisplayPreferenceDialogListener() {
        return this.mOnDisplayPreferenceDialogListener;
    }

    public void setOnDisplayPreferenceDialogListener(OnDisplayPreferenceDialogListener onDisplayPreferenceDialogListener) {
        this.mOnDisplayPreferenceDialogListener = onDisplayPreferenceDialogListener;
    }

    public void showDialog(Preference preference) {
        if (this.mOnDisplayPreferenceDialogListener != null) {
            this.mOnDisplayPreferenceDialogListener.onDisplayPreferenceDialog(preference);
        }
    }

    public void setOnPreferenceTreeClickListener(OnPreferenceTreeClickListener onPreferenceTreeClickListener) {
        this.mOnPreferenceTreeClickListener = onPreferenceTreeClickListener;
    }

    public OnPreferenceTreeClickListener getOnPreferenceTreeClickListener() {
        return this.mOnPreferenceTreeClickListener;
    }

    public void setOnNavigateToScreenListener(OnNavigateToScreenListener onNavigateToScreenListener) {
        this.mOnNavigateToScreenListener = onNavigateToScreenListener;
    }

    public OnNavigateToScreenListener getOnNavigateToScreenListener() {
        return this.mOnNavigateToScreenListener;
    }
}
