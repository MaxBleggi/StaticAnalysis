package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.Preference.BaseSavedState;
import androidx.preference.internal.AbstractMultiSelectListPreference;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MultiSelectListPreference extends AbstractMultiSelectListPreference {
    private CharSequence[] mEntries;
    private CharSequence[] mEntryValues;
    private Set<String> mValues;

    private static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        Set<String> mValues;

        SavedState(Parcel parcel) {
            super(parcel);
            int readInt = parcel.readInt();
            this.mValues = new HashSet();
            String[] strArr = new String[readInt];
            parcel.readStringArray(strArr);
            Collections.addAll(this.mValues, strArr);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(@NonNull Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mValues.size());
            parcel.writeStringArray((String[]) this.mValues.toArray(new String[this.mValues.size()]));
        }
    }

    public MultiSelectListPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mValues = new HashSet();
        context = context.obtainStyledAttributes(attributeSet, R.styleable.MultiSelectListPreference, i, i2);
        this.mEntries = TypedArrayUtils.getTextArray(context, R.styleable.MultiSelectListPreference_entries, R.styleable.MultiSelectListPreference_android_entries);
        this.mEntryValues = TypedArrayUtils.getTextArray(context, R.styleable.MultiSelectListPreference_entryValues, R.styleable.MultiSelectListPreference_android_entryValues);
        context.recycle();
    }

    public MultiSelectListPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public MultiSelectListPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, TypedArrayUtils.getAttr(context, R.attr.dialogPreferenceStyle, 16842897));
    }

    public MultiSelectListPreference(Context context) {
        this(context, null);
    }

    public void setEntries(CharSequence[] charSequenceArr) {
        this.mEntries = charSequenceArr;
    }

    public void setEntries(@ArrayRes int i) {
        setEntries(getContext().getResources().getTextArray(i));
    }

    public CharSequence[] getEntries() {
        return this.mEntries;
    }

    public void setEntryValues(CharSequence[] charSequenceArr) {
        this.mEntryValues = charSequenceArr;
    }

    public void setEntryValues(@ArrayRes int i) {
        setEntryValues(getContext().getResources().getTextArray(i));
    }

    public CharSequence[] getEntryValues() {
        return this.mEntryValues;
    }

    public void setValues(Set<String> set) {
        this.mValues.clear();
        this.mValues.addAll(set);
        persistStringSet(set);
    }

    public Set<String> getValues() {
        return this.mValues;
    }

    public int findIndexOfValue(String str) {
        if (!(str == null || this.mEntryValues == null)) {
            for (int length = this.mEntryValues.length - 1; length >= 0; length--) {
                if (this.mEntryValues[length].equals(str)) {
                    return length;
                }
            }
        }
        return -1;
    }

    protected boolean[] getSelectedItems() {
        CharSequence[] charSequenceArr = this.mEntryValues;
        int length = charSequenceArr.length;
        Set set = this.mValues;
        boolean[] zArr = new boolean[length];
        for (int i = 0; i < length; i++) {
            zArr[i] = set.contains(charSequenceArr[i].toString());
        }
        return zArr;
    }

    protected Object onGetDefaultValue(TypedArray typedArray, int i) {
        typedArray = typedArray.getTextArray(i);
        i = new HashSet();
        for (CharSequence charSequence : typedArray) {
            i.add(charSequence.toString());
        }
        return i;
    }

    protected void onSetInitialValue(Object obj) {
        setValues(getPersistedStringSet((Set) obj));
    }

    protected Parcelable onSaveInstanceState() {
        Parcelable onSaveInstanceState = super.onSaveInstanceState();
        if (isPersistent()) {
            return onSaveInstanceState;
        }
        Parcelable savedState = new SavedState(onSaveInstanceState);
        savedState.mValues = getValues();
        return savedState;
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable != null) {
            if (parcelable.getClass().equals(SavedState.class)) {
                SavedState savedState = (SavedState) parcelable;
                super.onRestoreInstanceState(savedState.getSuperState());
                setValues(savedState.mValues);
                return;
            }
        }
        super.onRestoreInstanceState(parcelable);
    }
}
