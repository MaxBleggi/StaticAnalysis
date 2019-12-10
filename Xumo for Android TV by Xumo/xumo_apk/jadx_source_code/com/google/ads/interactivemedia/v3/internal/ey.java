package com.google.ads.interactivemedia.v3.internal;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

/* compiled from: IMASDK */
public final class ey implements fc {
    private final fb a;
    private RandomAccessFile b;
    private String c;
    private long d;
    private boolean e;

    /* compiled from: IMASDK */
    public static class a extends IOException {
        public a(IOException iOException) {
            super(iOException);
        }
    }

    public ey() {
        this(null);
    }

    public ey(fb fbVar) {
        this.a = fbVar;
    }

    public long a(eu euVar) throws a {
        try {
            long length;
            this.c = euVar.a.toString();
            this.b = new RandomAccessFile(euVar.a.getPath(), "r");
            this.b.seek(euVar.d);
            if (euVar.e == -1) {
                length = this.b.length() - euVar.d;
            } else {
                length = euVar.e;
            }
            this.d = length;
            if (this.d >= 0) {
                this.e = true;
                if (this.a != null) {
                    this.a.a();
                }
                return this.d;
            }
            throw new EOFException();
        } catch (eu euVar2) {
            throw new a(euVar2);
        }
    }

    public int a(byte[] bArr, int i, int i2) throws a {
        if (this.d == 0) {
            return -1;
        }
        try {
            bArr = this.b.read(bArr, i, (int) Math.min(this.d, (long) i2));
            if (bArr > null) {
                this.d -= (long) bArr;
                if (this.a != 0) {
                    this.a.a(bArr);
                }
            }
            return bArr;
        } catch (byte[] bArr2) {
            throw new a(bArr2);
        }
    }

    public void a() throws a {
        this.c = null;
        if (this.b != null) {
            try {
                this.b.close();
                this.b = null;
                if (this.e) {
                    this.e = false;
                    if (this.a != null) {
                        this.a.b();
                    }
                }
            } catch (IOException e) {
                throw new a(e);
            } catch (Throwable th) {
                this.b = null;
                if (this.e) {
                    this.e = false;
                    if (this.a != null) {
                        this.a.b();
                    }
                }
            }
        }
    }
}
