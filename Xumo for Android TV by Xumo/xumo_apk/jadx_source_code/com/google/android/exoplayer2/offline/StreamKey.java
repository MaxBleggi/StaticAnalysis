package com.google.android.exoplayer2.offline;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class StreamKey implements Comparable<StreamKey> {
    public final int groupIndex;
    public final int periodIndex;
    public final int trackIndex;

    public StreamKey(int i, int i2) {
        this(0, i, i2);
    }

    public StreamKey(int i, int i2, int i3) {
        this.periodIndex = i;
        this.groupIndex = i2;
        this.trackIndex = i3;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.periodIndex);
        stringBuilder.append(".");
        stringBuilder.append(this.groupIndex);
        stringBuilder.append(".");
        stringBuilder.append(this.trackIndex);
        return stringBuilder.toString();
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj != null) {
            if (getClass() == obj.getClass()) {
                StreamKey streamKey = (StreamKey) obj;
                if (this.periodIndex != streamKey.periodIndex || this.groupIndex != streamKey.groupIndex || this.trackIndex != streamKey.trackIndex) {
                    z = false;
                }
                return z;
            }
        }
        return false;
    }

    public int hashCode() {
        return (((this.periodIndex * 31) + this.groupIndex) * 31) + this.trackIndex;
    }

    public int compareTo(@NonNull StreamKey streamKey) {
        int i = this.periodIndex - streamKey.periodIndex;
        if (i != 0) {
            return i;
        }
        i = this.groupIndex - streamKey.groupIndex;
        return i == 0 ? this.trackIndex - streamKey.trackIndex : i;
    }
}
