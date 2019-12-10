package com.google.android.exoplayer2.offline;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class DownloadAction {
    @Nullable
    private static Deserializer[] defaultDeserializers;
    public final byte[] data;
    public final boolean isRemoveAction;
    public final String type;
    public final Uri uri;
    public final int version;

    public static abstract class Deserializer {
        public final String type;
        public final int version;

        public abstract DownloadAction readFromStream(int i, DataInputStream dataInputStream) throws IOException;

        public Deserializer(String str, int i) {
            this.type = str;
            this.version = i;
        }
    }

    public abstract Downloader createDownloader(DownloaderConstructorHelper downloaderConstructorHelper);

    protected abstract void writeToStream(DataOutputStream dataOutputStream) throws IOException;

    public static synchronized com.google.android.exoplayer2.offline.DownloadAction.Deserializer[] getDefaultDeserializers() {
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
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r0 = com.google.android.exoplayer2.offline.DownloadAction.class;
        monitor-enter(r0);
        r1 = defaultDeserializers;	 Catch:{ all -> 0x0055 }
        if (r1 == 0) goto L_0x000b;	 Catch:{ all -> 0x0055 }
    L_0x0007:
        r1 = defaultDeserializers;	 Catch:{ all -> 0x0055 }
        monitor-exit(r0);
        return r1;
    L_0x000b:
        r1 = 4;
        r1 = new com.google.android.exoplayer2.offline.DownloadAction.Deserializer[r1];	 Catch:{ all -> 0x0055 }
        r2 = 0;	 Catch:{ all -> 0x0055 }
        r3 = com.google.android.exoplayer2.offline.ProgressiveDownloadAction.DESERIALIZER;	 Catch:{ all -> 0x0055 }
        r1[r2] = r3;	 Catch:{ all -> 0x0055 }
        r2 = 1;
        r3 = "com.google.android.exoplayer2.source.dash.offline.DashDownloadAction";	 Catch:{ Exception -> 0x0022 }
        r3 = java.lang.Class.forName(r3);	 Catch:{ Exception -> 0x0022 }
        r4 = 2;
        r3 = getDeserializer(r3);	 Catch:{ Exception -> 0x0023 }
        r1[r2] = r3;	 Catch:{ Exception -> 0x0023 }
        goto L_0x0023;
    L_0x0022:
        r4 = 1;
    L_0x0023:
        r2 = "com.google.android.exoplayer2.source.hls.offline.HlsDownloadAction";	 Catch:{ Exception -> 0x0032 }
        r2 = java.lang.Class.forName(r2);	 Catch:{ Exception -> 0x0032 }
        r3 = r4 + 1;
        r2 = getDeserializer(r2);	 Catch:{ Exception -> 0x0033 }
        r1[r4] = r2;	 Catch:{ Exception -> 0x0033 }
        goto L_0x0033;
    L_0x0032:
        r3 = r4;
    L_0x0033:
        r2 = "com.google.android.exoplayer2.source.smoothstreaming.offline.SsDownloadAction";	 Catch:{ Exception -> 0x0042 }
        r2 = java.lang.Class.forName(r2);	 Catch:{ Exception -> 0x0042 }
        r4 = r3 + 1;
        r2 = getDeserializer(r2);	 Catch:{ Exception -> 0x0043 }
        r1[r3] = r2;	 Catch:{ Exception -> 0x0043 }
        goto L_0x0043;
    L_0x0042:
        r4 = r3;
    L_0x0043:
        r1 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r1);	 Catch:{ all -> 0x0055 }
        r1 = (java.lang.Object[]) r1;	 Catch:{ all -> 0x0055 }
        r1 = java.util.Arrays.copyOf(r1, r4);	 Catch:{ all -> 0x0055 }
        r1 = (com.google.android.exoplayer2.offline.DownloadAction.Deserializer[]) r1;	 Catch:{ all -> 0x0055 }
        defaultDeserializers = r1;	 Catch:{ all -> 0x0055 }
        r1 = defaultDeserializers;	 Catch:{ all -> 0x0055 }
        monitor-exit(r0);
        return r1;
    L_0x0055:
        r1 = move-exception;
        monitor-exit(r0);
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.offline.DownloadAction.getDefaultDeserializers():com.google.android.exoplayer2.offline.DownloadAction$Deserializer[]");
    }

    public static DownloadAction deserializeFromStream(Deserializer[] deserializerArr, InputStream inputStream) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        inputStream = dataInputStream.readUTF();
        int readInt = dataInputStream.readInt();
        for (Deserializer deserializer : deserializerArr) {
            if (inputStream.equals(deserializer.type) && deserializer.version >= readInt) {
                return deserializer.readFromStream(readInt, dataInputStream);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No deserializer found for:");
        stringBuilder.append(inputStream);
        stringBuilder.append(", ");
        stringBuilder.append(readInt);
        throw new DownloadException(stringBuilder.toString());
    }

    public static void serializeToStream(DownloadAction downloadAction, OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeUTF(downloadAction.type);
        dataOutputStream.writeInt(downloadAction.version);
        downloadAction.writeToStream(dataOutputStream);
        dataOutputStream.flush();
    }

    protected DownloadAction(String str, int i, Uri uri, boolean z, @Nullable byte[] bArr) {
        this.type = str;
        this.version = i;
        this.uri = uri;
        this.isRemoveAction = z;
        if (bArr == null) {
            bArr = Util.EMPTY_BYTE_ARRAY;
        }
        this.data = bArr;
    }

    public final byte[] toByteArray() {
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
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r1 = this;
        r0 = new java.io.ByteArrayOutputStream;
        r0.<init>();
        serializeToStream(r1, r0);	 Catch:{ IOException -> 0x000d }
        r0 = r0.toByteArray();
        return r0;
    L_0x000d:
        r0 = new java.lang.IllegalStateException;
        r0.<init>();
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.offline.DownloadAction.toByteArray():byte[]");
    }

    public boolean isSameMedia(DownloadAction downloadAction) {
        return this.uri.equals(downloadAction.uri);
    }

    public List<StreamKey> getKeys() {
        return Collections.emptyList();
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = false;
        if (obj != null) {
            if (getClass() == obj.getClass()) {
                DownloadAction downloadAction = (DownloadAction) obj;
                if (this.type.equals(downloadAction.type) && this.version == downloadAction.version && this.uri.equals(downloadAction.uri) && this.isRemoveAction == downloadAction.isRemoveAction && Arrays.equals(this.data, downloadAction.data) != null) {
                    z = true;
                }
                return z;
            }
        }
        return false;
    }

    public int hashCode() {
        return (((this.uri.hashCode() * 31) + this.isRemoveAction) * 31) + Arrays.hashCode(this.data);
    }

    private static Deserializer getDeserializer(Class<?> cls) throws NoSuchFieldException, IllegalAccessException {
        return (Deserializer) Assertions.checkNotNull(cls.getDeclaredField("DESERIALIZER").get(null));
    }
}
