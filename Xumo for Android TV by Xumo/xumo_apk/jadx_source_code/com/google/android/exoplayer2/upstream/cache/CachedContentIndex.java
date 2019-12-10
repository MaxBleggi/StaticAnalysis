package com.google.android.exoplayer2.upstream.cache;

import android.util.SparseArray;
import com.google.android.exoplayer2.upstream.cache.Cache.CacheException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.AtomicFile;
import com.google.android.exoplayer2.util.ReusableBufferedOutputStream;
import com.google.android.exoplayer2.util.Util;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

class CachedContentIndex {
    public static final String FILE_NAME = "cached_content_index.exi";
    private static final int FLAG_ENCRYPTED_INDEX = 1;
    private static final int VERSION = 2;
    private final AtomicFile atomicFile;
    private ReusableBufferedOutputStream bufferedOutputStream;
    private boolean changed;
    private final Cipher cipher;
    private final boolean encrypt;
    private final SparseArray<String> idToKey;
    private final HashMap<String, CachedContent> keyToContent;
    private final SecretKeySpec secretKeySpec;

    private boolean readFile() {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.google.android.exoplayer2.upstream.cache.CachedContentIndex.readFile():boolean
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: java.lang.NullPointerException
	at jadx.core.dex.nodes.MethodNode.initTryCatches(MethodNode.java:305)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:105)
	... 5 more
*/
        /*
        r0 = this;
        r0 = 0;
        r1 = 0;
        r2 = new java.io.BufferedInputStream;
        r3 = r9.atomicFile;
        r3 = r3.openRead();
        r2.<init>(r3);
        r3 = new java.io.DataInputStream;
        r3.<init>(r2);
        r1 = r3.readInt();
        if (r1 < 0) goto L_0x008d;
        r4 = 2;
        if (r1 <= r4) goto L_0x001d;
        goto L_0x008d;
        r5 = r3.readInt();
        r6 = 1;
        r5 = r5 & r6;
        if (r5 == 0) goto L_0x0055;
        r5 = r9.cipher;
        if (r5 != 0) goto L_0x002d;
        com.google.android.exoplayer2.util.Util.closeQuietly(r3);
        return r0;
        r5 = 16;
        r5 = new byte[r5];
        r3.readFully(r5);
        r7 = new javax.crypto.spec.IvParameterSpec;
        r7.<init>(r5);
        r5 = r9.cipher;
        r8 = r9.secretKeySpec;
        r5.init(r4, r8, r7);
        r4 = new java.io.DataInputStream;
        r5 = new javax.crypto.CipherInputStream;
        r7 = r9.cipher;
        r5.<init>(r2, r7);
        r4.<init>(r5);
        r3 = r4;
        goto L_0x005b;
        r1 = move-exception;
        r2 = new java.lang.IllegalStateException;
        r2.<init>(r1);
        throw r2;
        r2 = r9.encrypt;
        if (r2 == 0) goto L_0x005b;
        r9.changed = r6;
        r2 = r3.readInt();
        r4 = 0;
        r5 = 0;
        if (r4 >= r2) goto L_0x0072;
        r7 = com.google.android.exoplayer2.upstream.cache.CachedContent.readFromStream(r1, r3);
        r9.add(r7);
        r7 = r7.headerHashCode(r1);
        r5 = r5 + r7;
        r4 = r4 + 1;
        goto L_0x0061;
        r1 = r3.readInt();
        r2 = r3.read();
        r4 = -1;
        if (r2 != r4) goto L_0x007f;
        r2 = 1;
        goto L_0x0080;
        r2 = 0;
        if (r1 != r5) goto L_0x0089;
        if (r2 != 0) goto L_0x0085;
        goto L_0x0089;
        com.google.android.exoplayer2.util.Util.closeQuietly(r3);
        return r6;
        com.google.android.exoplayer2.util.Util.closeQuietly(r3);
        return r0;
        com.google.android.exoplayer2.util.Util.closeQuietly(r3);
        return r0;
        r0 = move-exception;
        goto L_0x0097;
        goto L_0x009e;
    L_0x0095:
        r0 = move-exception;
        r3 = r1;
        if (r3 == 0) goto L_0x009c;
        com.google.android.exoplayer2.util.Util.closeQuietly(r3);
        throw r0;
    L_0x009d:
        r3 = r1;
        if (r3 == 0) goto L_0x00a3;
        com.google.android.exoplayer2.util.Util.closeQuietly(r3);
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.CachedContentIndex.readFile():boolean");
    }

    public CachedContentIndex(File file) {
        this(file, null);
    }

    public CachedContentIndex(File file, byte[] bArr) {
        this(file, bArr, bArr != null);
    }

    public CachedContentIndex(File file, byte[] bArr, boolean z) {
        this.encrypt = z;
        boolean z2 = true;
        if (bArr != null) {
            if (!bArr.length) {
                z2 = false;
            }
            Assertions.checkArgument(z2);
            try {
                this.cipher = getCipher();
                this.secretKeySpec = new SecretKeySpec(bArr, "AES");
            } catch (File file2) {
                throw new IllegalStateException(file2);
            }
        }
        Assertions.checkState(z ^ 1);
        this.cipher = null;
        this.secretKeySpec = null;
        this.keyToContent = new HashMap();
        this.idToKey = new SparseArray();
        this.atomicFile = new AtomicFile(new File(file2, FILE_NAME));
    }

    public void load() {
        Assertions.checkState(this.changed ^ 1);
        if (!readFile()) {
            this.atomicFile.delete();
            this.keyToContent.clear();
            this.idToKey.clear();
        }
    }

    public void store() throws CacheException {
        if (this.changed) {
            writeFile();
            this.changed = false;
        }
    }

    public CachedContent getOrAdd(String str) {
        CachedContent cachedContent = (CachedContent) this.keyToContent.get(str);
        return cachedContent == null ? addNew(str) : cachedContent;
    }

    public CachedContent get(String str) {
        return (CachedContent) this.keyToContent.get(str);
    }

    public Collection<CachedContent> getAll() {
        return this.keyToContent.values();
    }

    public int assignIdForKey(String str) {
        return getOrAdd(str).id;
    }

    public String getKeyForId(int i) {
        return (String) this.idToKey.get(i);
    }

    public void maybeRemove(String str) {
        CachedContent cachedContent = (CachedContent) this.keyToContent.get(str);
        if (cachedContent != null && cachedContent.isEmpty() && !cachedContent.isLocked()) {
            this.keyToContent.remove(str);
            this.idToKey.remove(cachedContent.id);
            this.changed = true;
        }
    }

    public void removeEmpty() {
        String[] strArr = new String[this.keyToContent.size()];
        this.keyToContent.keySet().toArray(strArr);
        for (String maybeRemove : strArr) {
            maybeRemove(maybeRemove);
        }
    }

    public Set<String> getKeys() {
        return this.keyToContent.keySet();
    }

    public void applyContentMetadataMutations(String str, ContentMetadataMutations contentMetadataMutations) {
        if (getOrAdd(str).applyMetadataMutations(contentMetadataMutations) != null) {
            this.changed = true;
        }
    }

    public ContentMetadata getContentMetadata(String str) {
        str = get(str);
        return str != null ? str.getMetadata() : DefaultContentMetadata.EMPTY;
    }

    private void writeFile() throws CacheException {
        Throwable e;
        Throwable th;
        Closeable dataOutputStream;
        try {
            OutputStream startWrite = this.atomicFile.startWrite();
            if (this.bufferedOutputStream == null) {
                this.bufferedOutputStream = new ReusableBufferedOutputStream(startWrite);
            } else {
                this.bufferedOutputStream.reset(startWrite);
            }
            dataOutputStream = new DataOutputStream(this.bufferedOutputStream);
            try {
                dataOutputStream.writeInt(2);
                dataOutputStream.writeInt(this.encrypt);
                if (this.encrypt) {
                    byte[] bArr = new byte[16];
                    new Random().nextBytes(bArr);
                    dataOutputStream.write(bArr);
                    this.cipher.init(1, this.secretKeySpec, new IvParameterSpec(bArr));
                    dataOutputStream.flush();
                    startWrite = new DataOutputStream(new CipherOutputStream(this.bufferedOutputStream, this.cipher));
                }
                startWrite.writeInt(this.keyToContent.size());
                int i = 0;
                for (CachedContent cachedContent : this.keyToContent.values()) {
                    cachedContent.writeToStream(startWrite);
                    i += cachedContent.headerHashCode(2);
                }
                startWrite.writeInt(i);
                this.atomicFile.endWrite(startWrite);
                Util.closeQuietly(null);
            } catch (Throwable e2) {
                throw new IllegalStateException(e2);
            } catch (IOException e3) {
                e2 = e3;
                try {
                    throw new CacheException(e2);
                } catch (Throwable th2) {
                    e2 = th2;
                    Util.closeQuietly(dataOutputStream);
                    throw e2;
                }
            }
        } catch (Throwable e4) {
            th = e4;
            dataOutputStream = null;
            e2 = th;
            throw new CacheException(e2);
        } catch (Throwable e42) {
            th = e42;
            dataOutputStream = null;
            e2 = th;
            Util.closeQuietly(dataOutputStream);
            throw e2;
        }
    }

    private CachedContent addNew(String str) {
        CachedContent cachedContent = new CachedContent(getNewId(this.idToKey), str);
        add(cachedContent);
        this.changed = true;
        return cachedContent;
    }

    private void add(CachedContent cachedContent) {
        this.keyToContent.put(cachedContent.key, cachedContent);
        this.idToKey.put(cachedContent.id, cachedContent.key);
    }

    private static javax.crypto.Cipher getCipher() throws javax.crypto.NoSuchPaddingException, java.security.NoSuchAlgorithmException {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r0 = com.google.android.exoplayer2.util.Util.SDK_INT;
        r1 = 18;
        if (r0 != r1) goto L_0x000f;
    L_0x0006:
        r0 = "AES/CBC/PKCS5PADDING";	 Catch:{ Throwable -> 0x000f }
        r1 = "BC";	 Catch:{ Throwable -> 0x000f }
        r0 = javax.crypto.Cipher.getInstance(r0, r1);	 Catch:{ Throwable -> 0x000f }
        return r0;
    L_0x000f:
        r0 = "AES/CBC/PKCS5PADDING";
        r0 = javax.crypto.Cipher.getInstance(r0);
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.CachedContentIndex.getCipher():javax.crypto.Cipher");
    }

    public static int getNewId(SparseArray<String> sparseArray) {
        int i;
        int size = sparseArray.size();
        if (size == 0) {
            i = 0;
        } else {
            i = sparseArray.keyAt(size - 1) + 1;
        }
        if (i < 0) {
            i = 0;
            while (i < size) {
                if (i != sparseArray.keyAt(i)) {
                    break;
                }
                i++;
            }
        }
        return i;
    }
}
