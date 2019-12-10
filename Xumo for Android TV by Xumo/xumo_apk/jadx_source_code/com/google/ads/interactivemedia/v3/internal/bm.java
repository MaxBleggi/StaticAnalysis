package com.google.ads.interactivemedia.v3.internal;

import java.nio.ByteBuffer;

/* compiled from: IMASDK */
public final class bm {
    public final ax a = new ax();
    public ByteBuffer b;
    public int c;
    public int d;
    public long e;
    private final int f;

    public bm(int i) {
        this.f = i;
    }

    public void a(int i) throws IllegalStateException {
        if (this.b == null) {
            this.b = b(i);
            return;
        }
        int capacity = this.b.capacity();
        int position = this.b.position();
        i += position;
        if (capacity < i) {
            i = b(i);
            if (position > 0) {
                this.b.position(0);
                this.b.limit(position);
                i.put(this.b);
            }
            this.b = i;
        }
    }

    public boolean a() {
        return (this.d & 2) != 0;
    }

    public boolean b() {
        return (this.d & 134217728) != 0;
    }

    public boolean c() {
        return (this.d & 1) != 0;
    }

    public void d() {
        if (this.b != null) {
            this.b.clear();
        }
    }

    private ByteBuffer b(int i) {
        if (this.f == 1) {
            return ByteBuffer.allocate(i);
        }
        if (this.f == 2) {
            return ByteBuffer.allocateDirect(i);
        }
        int capacity = this.b == null ? 0 : this.b.capacity();
        StringBuilder stringBuilder = new StringBuilder(44);
        stringBuilder.append("Buffer too small (");
        stringBuilder.append(capacity);
        stringBuilder.append(" < ");
        stringBuilder.append(i);
        stringBuilder.append(")");
        throw new IllegalStateException(stringBuilder.toString());
    }
}
