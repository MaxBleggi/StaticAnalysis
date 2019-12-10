package com.google.android.exoplayer2.util;

import androidx.annotation.Nullable;
import java.util.Arrays;

public final class TimedValueQueue<V> {
    private static final int INITIAL_BUFFER_SIZE = 10;
    private int first;
    private int size;
    private long[] timestamps;
    private V[] values;

    public TimedValueQueue() {
        this(10);
    }

    public TimedValueQueue(int i) {
        this.timestamps = new long[i];
        this.values = newArray(i);
    }

    public synchronized void add(long j, V v) {
        clearBufferOnTimeDiscontinuity(j);
        doubleCapacityIfFull();
        addUnchecked(j, v);
    }

    public synchronized void clear() {
        this.first = 0;
        this.size = 0;
        Arrays.fill(this.values, null);
    }

    public synchronized int size() {
        return this.size;
    }

    @Nullable
    public synchronized V pollFloor(long j) {
        return poll(j, true);
    }

    @Nullable
    public synchronized V poll(long j) {
        return poll(j, false);
    }

    @Nullable
    private V poll(long j, boolean z) {
        long j2 = Long.MAX_VALUE;
        V v = null;
        while (this.size > 0) {
            long j3 = j - this.timestamps[this.first];
            if (j3 < 0) {
                if (z) {
                    break;
                } else if ((-j3) >= j2) {
                    break;
                }
            }
            v = this.values[this.first];
            this.values[this.first] = null;
            this.first = (this.first + 1) % this.values.length;
            this.size--;
            j2 = j3;
        }
        return v;
    }

    private void clearBufferOnTimeDiscontinuity(long j) {
        if (this.size > 0) {
            if (j <= this.timestamps[((this.first + this.size) - 1) % this.values.length]) {
                clear();
            }
        }
    }

    private void doubleCapacityIfFull() {
        int length = this.values.length;
        if (this.size >= length) {
            int i = length * 2;
            Object obj = new long[i];
            Object newArray = newArray(i);
            length -= this.first;
            System.arraycopy(this.timestamps, this.first, obj, 0, length);
            System.arraycopy(this.values, this.first, newArray, 0, length);
            if (this.first > 0) {
                System.arraycopy(this.timestamps, 0, obj, length, this.first);
                System.arraycopy(this.values, 0, newArray, length, this.first);
            }
            this.timestamps = obj;
            this.values = newArray;
            this.first = 0;
        }
    }

    private void addUnchecked(long j, V v) {
        int length = (this.first + this.size) % this.values.length;
        this.timestamps[length] = j;
        this.values[length] = v;
        this.size++;
    }

    private static <V> V[] newArray(int i) {
        return (Object[]) new Object[i];
    }
}
