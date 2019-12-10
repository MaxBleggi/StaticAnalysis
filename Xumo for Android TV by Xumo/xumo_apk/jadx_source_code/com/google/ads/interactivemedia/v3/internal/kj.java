package com.google.ads.interactivemedia.v3.internal;

import java.nio.ByteBuffer;
import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: IMASDK */
public class kj {
    private final kh a;
    private final SecureRandom b;

    /* compiled from: IMASDK */
    public class a extends Exception {
        final /* synthetic */ kj a;

        public a(kj kjVar) {
            this.a = kjVar;
        }

        public a(kj kjVar, Throwable th) {
            this.a = kjVar;
            super(th);
        }
    }

    public kj(kh khVar, SecureRandom secureRandom) {
        this.a = khVar;
        this.b = secureRandom;
    }

    static void a(byte[] bArr) {
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = (byte) (bArr[i] ^ 68);
        }
    }

    public byte[] a(String str) throws a {
        try {
            str = this.a.a(str, false);
            if (str.length == 32) {
                byte[] bArr = new byte[16];
                ByteBuffer.wrap(str, 4, 16).get(bArr);
                a(bArr);
                return bArr;
            }
            throw new a(this);
        } catch (String str2) {
            throw new a(this, str2);
        }
    }

    public byte[] a(byte[] bArr, String str) throws a {
        if (bArr.length == 16) {
            try {
                str = this.a.a(str, false);
                if (str.length > 16) {
                    ByteBuffer allocate = ByteBuffer.allocate(str.length);
                    allocate.put(str);
                    allocate.flip();
                    byte[] bArr2 = new byte[16];
                    str = new byte[(str.length - 16)];
                    allocate.get(bArr2);
                    allocate.get(str);
                    Key secretKeySpec = new SecretKeySpec(bArr, "AES");
                    bArr = Cipher.getInstance("AES/CBC/PKCS5Padding");
                    bArr.init(2, secretKeySpec, new IvParameterSpec(bArr2));
                    return bArr.doFinal(str);
                }
                throw new a(this);
            } catch (byte[] bArr3) {
                throw new a(this, bArr3);
            } catch (byte[] bArr32) {
                throw new a(this, bArr32);
            } catch (byte[] bArr322) {
                throw new a(this, bArr322);
            } catch (byte[] bArr3222) {
                throw new a(this, bArr3222);
            } catch (byte[] bArr32222) {
                throw new a(this, bArr32222);
            } catch (byte[] bArr322222) {
                throw new a(this, bArr322222);
            } catch (byte[] bArr3222222) {
                throw new a(this, bArr3222222);
            }
        }
        throw new a(this);
    }
}
