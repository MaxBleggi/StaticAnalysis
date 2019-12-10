package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View.OnKeyListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import androidx.preference.Preference.BaseSavedState;

public class SeekBarPreference extends Preference {
    private static final String TAG = "SeekBarPreference";
    boolean mAdjustable;
    private int mMax;
    int mMin;
    SeekBar mSeekBar;
    private OnSeekBarChangeListener mSeekBarChangeListener;
    private int mSeekBarIncrement;
    private OnKeyListener mSeekBarKeyListener;
    int mSeekBarValue;
    private TextView mSeekBarValueTextView;
    private boolean mShowSeekBarValue;
    boolean mTrackingTouch;

    private static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        int max;
        int min;
        int seekBarValue;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.seekBarValue = parcel.readInt();
            this.min = parcel.readInt();
            this.max = parcel.readInt();
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.seekBarValue);
            parcel.writeInt(this.min);
            parcel.writeInt(this.max);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public SeekBarPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mSeekBarChangeListener = new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (z && SeekBarPreference.this.mTrackingTouch == 0) {
                    SeekBarPreference.this.syncValueInternal(seekBar);
                }
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                SeekBarPreference.this.mTrackingTouch = true;
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                SeekBarPreference.this.mTrackingTouch = false;
                if (seekBar.getProgress() + SeekBarPreference.this.mMin != SeekBarPreference.this.mSeekBarValue) {
                    SeekBarPreference.this.syncValueInternal(seekBar);
                }
            }
        };
        this.mSeekBarKeyListener = new OnKeyListener() {
            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public boolean onKey(android.view.View r2, int r3, android.view.KeyEvent r4) {
                /*
                r1 = this;
                r2 = r4.getAction();
                r0 = 0;
                if (r2 == 0) goto L_0x0008;
            L_0x0007:
                return r0;
            L_0x0008:
                r2 = androidx.preference.SeekBarPreference.this;
                r2 = r2.mAdjustable;
                if (r2 != 0) goto L_0x0017;
            L_0x000e:
                r2 = 21;
                if (r3 == r2) goto L_0x0016;
            L_0x0012:
                r2 = 22;
                if (r3 != r2) goto L_0x0017;
            L_0x0016:
                return r0;
            L_0x0017:
                r2 = 23;
                if (r3 == r2) goto L_0x0037;
            L_0x001b:
                r2 = 66;
                if (r3 != r2) goto L_0x0020;
            L_0x001f:
                goto L_0x0037;
            L_0x0020:
                r2 = androidx.preference.SeekBarPreference.this;
                r2 = r2.mSeekBar;
                if (r2 != 0) goto L_0x002e;
            L_0x0026:
                r2 = "SeekBarPreference";
                r3 = "SeekBar view is null and hence cannot be adjusted.";
                android.util.Log.e(r2, r3);
                return r0;
            L_0x002e:
                r2 = androidx.preference.SeekBarPreference.this;
                r2 = r2.mSeekBar;
                r2 = r2.onKeyDown(r3, r4);
                return r2;
            L_0x0037:
                return r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.preference.SeekBarPreference.2.onKey(android.view.View, int, android.view.KeyEvent):boolean");
            }
        };
        context = context.obtainStyledAttributes(attributeSet, R.styleable.SeekBarPreference, i, i2);
        this.mMin = context.getInt(R.styleable.SeekBarPreference_min, 0);
        setMax(context.getInt(R.styleable.SeekBarPreference_android_max, 100));
        setSeekBarIncrement(context.getInt(R.styleable.SeekBarPreference_seekBarIncrement, 0));
        this.mAdjustable = context.getBoolean(R.styleable.SeekBarPreference_adjustable, true);
        this.mShowSeekBarValue = context.getBoolean(R.styleable.SeekBarPreference_showSeekBarValue, true);
        context.recycle();
    }

    public SeekBarPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SeekBarPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.seekBarPreferenceStyle);
    }

    public SeekBarPreference(Context context) {
        this(context, null);
    }

    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.itemView.setOnKeyListener(this.mSeekBarKeyListener);
        this.mSeekBar = (SeekBar) preferenceViewHolder.findViewById(R.id.seekbar);
        this.mSeekBarValueTextView = (TextView) preferenceViewHolder.findViewById(R.id.seekbar_value);
        if (this.mShowSeekBarValue != null) {
            this.mSeekBarValueTextView.setVisibility(0);
        } else {
            this.mSeekBarValueTextView.setVisibility(8);
            this.mSeekBarValueTextView = null;
        }
        if (this.mSeekBar == null) {
            Log.e(TAG, "SeekBar view is null in onBindViewHolder.");
            return;
        }
        this.mSeekBar.setOnSeekBarChangeListener(this.mSeekBarChangeListener);
        this.mSeekBar.setMax(this.mMax - this.mMin);
        if (this.mSeekBarIncrement != null) {
            this.mSeekBar.setKeyProgressIncrement(this.mSeekBarIncrement);
        } else {
            this.mSeekBarIncrement = this.mSeekBar.getKeyProgressIncrement();
        }
        this.mSeekBar.setProgress(this.mSeekBarValue - this.mMin);
        if (this.mSeekBarValueTextView != null) {
            this.mSeekBarValueTextView.setText(String.valueOf(this.mSeekBarValue));
        }
        this.mSeekBar.setEnabled(isEnabled());
    }

    protected void onSetInitialValue(Object obj) {
        if (obj == null) {
            obj = Integer.valueOf(null);
        }
        setValue(getPersistedInt(((Integer) obj).intValue()));
    }

    protected Object onGetDefaultValue(TypedArray typedArray, int i) {
        return Integer.valueOf(typedArray.getInt(i, 0));
    }

    public void setMin(int i) {
        if (i > this.mMax) {
            i = this.mMax;
        }
        if (i != this.mMin) {
            this.mMin = i;
            notifyChanged();
        }
    }

    public int getMin() {
        return this.mMin;
    }

    public final void setMax(int i) {
        if (i < this.mMin) {
            i = this.mMin;
        }
        if (i != this.mMax) {
            this.mMax = i;
            notifyChanged();
        }
    }

    public final int getSeekBarIncrement() {
        return this.mSeekBarIncrement;
    }

    public final void setSeekBarIncrement(int i) {
        if (i != this.mSeekBarIncrement) {
            this.mSeekBarIncrement = Math.min(this.mMax - this.mMin, Math.abs(i));
            notifyChanged();
        }
    }

    public int getMax() {
        return this.mMax;
    }

    public void setAdjustable(boolean z) {
        this.mAdjustable = z;
    }

    public boolean isAdjustable() {
        return this.mAdjustable;
    }

    public void setValue(int i) {
        setValueInternal(i, true);
    }

    private void setValueInternal(int i, boolean z) {
        if (i < this.mMin) {
            i = this.mMin;
        }
        if (i > this.mMax) {
            i = this.mMax;
        }
        if (i != this.mSeekBarValue) {
            this.mSeekBarValue = i;
            if (this.mSeekBarValueTextView != null) {
                this.mSeekBarValueTextView.setText(String.valueOf(this.mSeekBarValue));
            }
            persistInt(i);
            if (z) {
                notifyChanged();
            }
        }
    }

    public int getValue() {
        return this.mSeekBarValue;
    }

    void syncValueInternal(SeekBar seekBar) {
        int progress = this.mMin + seekBar.getProgress();
        if (progress == this.mSeekBarValue) {
            return;
        }
        if (callChangeListener(Integer.valueOf(progress))) {
            setValueInternal(progress, null);
        } else {
            seekBar.setProgress(this.mSeekBarValue - this.mMin);
        }
    }

    protected Parcelable onSaveInstanceState() {
        Parcelable onSaveInstanceState = super.onSaveInstanceState();
        if (isPersistent()) {
            return onSaveInstanceState;
        }
        Parcelable savedState = new SavedState(onSaveInstanceState);
        savedState.seekBarValue = this.mSeekBarValue;
        savedState.min = this.mMin;
        savedState.max = this.mMax;
        return savedState;
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable.getClass().equals(SavedState.class)) {
            SavedState savedState = (SavedState) parcelable;
            super.onRestoreInstanceState(savedState.getSuperState());
            this.mSeekBarValue = savedState.seekBarValue;
            this.mMin = savedState.min;
            this.mMax = savedState.max;
            notifyChanged();
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }
}
