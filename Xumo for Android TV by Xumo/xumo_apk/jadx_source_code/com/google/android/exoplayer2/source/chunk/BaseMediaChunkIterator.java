package com.google.android.exoplayer2.source.chunk;

import java.util.NoSuchElementException;

public abstract class BaseMediaChunkIterator implements MediaChunkIterator {
    private long currentIndex;
    private final long fromIndex;
    private final long toIndex;

    public BaseMediaChunkIterator(long j, long j2) {
        this.fromIndex = j;
        this.toIndex = j2;
        this.currentIndex = j - 1;
    }

    public boolean isEnded() {
        return this.currentIndex > this.toIndex;
    }

    public boolean next() {
        this.currentIndex++;
        return isEnded() ^ 1;
    }

    protected void checkInBounds() {
        if (this.currentIndex < this.fromIndex || this.currentIndex > this.toIndex) {
            throw new NoSuchElementException();
        }
    }

    protected long getCurrentIndex() {
        return this.currentIndex;
    }
}
