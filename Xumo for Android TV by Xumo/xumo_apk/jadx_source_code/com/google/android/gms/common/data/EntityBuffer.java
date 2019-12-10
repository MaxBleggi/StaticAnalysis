package com.google.android.gms.common.data;

import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.ArrayList;

@KeepForSdk
public abstract class EntityBuffer<T> extends AbstractDataBuffer<T> {
    private boolean zamd = null;
    private ArrayList<Integer> zame;

    @KeepForSdk
    protected EntityBuffer(DataHolder dataHolder) {
        super(dataHolder);
    }

    @KeepForSdk
    protected String getChildDataMarkerColumn() {
        return null;
    }

    @KeepForSdk
    protected abstract T getEntry(int i, int i2);

    @KeepForSdk
    protected abstract String getPrimaryDataMarkerColumn();

    @KeepForSdk
    public final T get(int i) {
        zacb();
        int zah = zah(i);
        int i2 = 0;
        if (i >= 0) {
            if (i != this.zame.size()) {
                int count;
                if (i == this.zame.size() - 1) {
                    count = this.mDataHolder.getCount() - ((Integer) this.zame.get(i)).intValue();
                } else {
                    count = ((Integer) this.zame.get(i + 1)).intValue() - ((Integer) this.zame.get(i)).intValue();
                }
                if (count == 1) {
                    i = zah(i);
                    int windowIndex = this.mDataHolder.getWindowIndex(i);
                    String childDataMarkerColumn = getChildDataMarkerColumn();
                    if (childDataMarkerColumn != null && this.mDataHolder.getString(childDataMarkerColumn, i, windowIndex) == 0) {
                    }
                }
                i2 = count;
            }
        }
        return getEntry(zah, i2);
    }

    @KeepForSdk
    public int getCount() {
        zacb();
        return this.zame.size();
    }

    private final void zacb() {
        synchronized (this) {
            if (!this.zamd) {
                int count = this.mDataHolder.getCount();
                this.zame = new ArrayList();
                if (count > 0) {
                    this.zame.add(Integer.valueOf(0));
                    String primaryDataMarkerColumn = getPrimaryDataMarkerColumn();
                    Object string = this.mDataHolder.getString(primaryDataMarkerColumn, 0, this.mDataHolder.getWindowIndex(0));
                    int i = 1;
                    while (i < count) {
                        int windowIndex = this.mDataHolder.getWindowIndex(i);
                        String string2 = this.mDataHolder.getString(primaryDataMarkerColumn, i, windowIndex);
                        if (string2 != null) {
                            if (!string2.equals(string)) {
                                this.zame.add(Integer.valueOf(i));
                                string = string2;
                            }
                            i++;
                        } else {
                            StringBuilder stringBuilder = new StringBuilder(String.valueOf(primaryDataMarkerColumn).length() + 78);
                            stringBuilder.append("Missing value for markerColumn: ");
                            stringBuilder.append(primaryDataMarkerColumn);
                            stringBuilder.append(", at row: ");
                            stringBuilder.append(i);
                            stringBuilder.append(", for window: ");
                            stringBuilder.append(windowIndex);
                            throw new NullPointerException(stringBuilder.toString());
                        }
                    }
                }
                this.zamd = true;
            }
        }
    }

    private final int zah(int i) {
        if (i >= 0 && i < this.zame.size()) {
            return ((Integer) this.zame.get(i)).intValue();
        }
        StringBuilder stringBuilder = new StringBuilder(53);
        stringBuilder.append("Position ");
        stringBuilder.append(i);
        stringBuilder.append(" is out of bounds for this buffer");
        throw new IllegalArgumentException(stringBuilder.toString());
    }
}
