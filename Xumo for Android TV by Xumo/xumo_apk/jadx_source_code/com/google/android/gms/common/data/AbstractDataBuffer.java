package com.google.android.gms.common.data;

import android.os.Bundle;
import com.google.android.gms.common.annotation.KeepForSdk;
import java.util.Iterator;

public abstract class AbstractDataBuffer<T> implements DataBuffer<T> {
    protected final DataHolder mDataHolder;

    @KeepForSdk
    protected AbstractDataBuffer(DataHolder dataHolder) {
        this.mDataHolder = dataHolder;
    }

    public abstract T get(int i);

    public int getCount() {
        return this.mDataHolder == null ? 0 : this.mDataHolder.getCount();
    }

    @Deprecated
    public final void close() {
        release();
    }

    @Deprecated
    public boolean isClosed() {
        if (this.mDataHolder != null) {
            if (!this.mDataHolder.isClosed()) {
                return false;
            }
        }
        return true;
    }

    public Bundle getMetadata() {
        return this.mDataHolder.getMetadata();
    }

    public Iterator<T> iterator() {
        return new DataBufferIterator(this);
    }

    public Iterator<T> singleRefIterator() {
        return new SingleRefDataBufferIterator(this);
    }

    public void release() {
        if (this.mDataHolder != null) {
            this.mDataHolder.close();
        }
    }
}
