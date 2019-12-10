package androidx.core.util;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AtomicFile {
    private final File mBackupName;
    private final File mBaseName;

    @androidx.annotation.NonNull
    public java.io.FileOutputStream startWrite() throws java.io.IOException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: androidx.core.util.AtomicFile.startWrite():java.io.FileOutputStream
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
        r0 = r3.mBaseName;
        r0 = r0.exists();
        if (r0 == 0) goto L_0x0042;
        r0 = r3.mBackupName;
        r0 = r0.exists();
        if (r0 != 0) goto L_0x003d;
        r0 = r3.mBaseName;
        r1 = r3.mBackupName;
        r0 = r0.renameTo(r1);
        if (r0 != 0) goto L_0x0042;
        r0 = "AtomicFile";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "Couldn't rename file ";
        r1.append(r2);
        r2 = r3.mBaseName;
        r1.append(r2);
        r2 = " to backup file ";
        r1.append(r2);
        r2 = r3.mBackupName;
        r1.append(r2);
        r1 = r1.toString();
        android.util.Log.w(r0, r1);
        goto L_0x0042;
        r0 = r3.mBaseName;
        r0.delete();
        r0 = new java.io.FileOutputStream;
        r1 = r3.mBaseName;
        r0.<init>(r1);
        goto L_0x005e;
        r0 = r3.mBaseName;
        r0 = r0.getParentFile();
        r0 = r0.mkdirs();
        if (r0 == 0) goto L_0x0078;
        r0 = new java.io.FileOutputStream;
        r1 = r3.mBaseName;
        r0.<init>(r1);
        return r0;
        r0 = new java.io.IOException;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "Couldn't create ";
        r1.append(r2);
        r2 = r3.mBaseName;
        r1.append(r2);
        r1 = r1.toString();
        r0.<init>(r1);
        throw r0;
        r0 = new java.io.IOException;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "Couldn't create directory ";
        r1.append(r2);
        r2 = r3.mBaseName;
        r1.append(r2);
        r1 = r1.toString();
        r0.<init>(r1);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.util.AtomicFile.startWrite():java.io.FileOutputStream");
    }

    public AtomicFile(@NonNull File file) {
        this.mBaseName = file;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(file.getPath());
        stringBuilder.append(".bak");
        this.mBackupName = new File(stringBuilder.toString());
    }

    @NonNull
    public File getBaseFile() {
        return this.mBaseName;
    }

    public void delete() {
        this.mBaseName.delete();
        this.mBackupName.delete();
    }

    public void finishWrite(@Nullable FileOutputStream fileOutputStream) {
        if (fileOutputStream != null) {
            sync(fileOutputStream);
            try {
                fileOutputStream.close();
                this.mBackupName.delete();
            } catch (FileOutputStream fileOutputStream2) {
                Log.w("AtomicFile", "finishWrite: Got exception:", fileOutputStream2);
            }
        }
    }

    public void failWrite(@Nullable FileOutputStream fileOutputStream) {
        if (fileOutputStream != null) {
            sync(fileOutputStream);
            try {
                fileOutputStream.close();
                this.mBaseName.delete();
                this.mBackupName.renameTo(this.mBaseName);
            } catch (FileOutputStream fileOutputStream2) {
                Log.w("AtomicFile", "failWrite: Got exception:", fileOutputStream2);
            }
        }
    }

    @NonNull
    public FileInputStream openRead() throws FileNotFoundException {
        if (this.mBackupName.exists()) {
            this.mBaseName.delete();
            this.mBackupName.renameTo(this.mBaseName);
        }
        return new FileInputStream(this.mBaseName);
    }

    @NonNull
    public byte[] readFully() throws IOException {
        FileInputStream openRead = openRead();
        try {
            byte[] bArr = new byte[openRead.available()];
            int i = 0;
            while (true) {
                int read = openRead.read(bArr, i, bArr.length - i);
                if (read <= 0) {
                    break;
                }
                i += read;
                read = openRead.available();
                if (read > bArr.length - i) {
                    Object obj = new byte[(read + i)];
                    System.arraycopy(bArr, 0, obj, 0, i);
                    bArr = obj;
                }
            }
            return bArr;
        } finally {
            openRead.close();
        }
    }

    private static boolean sync(@androidx.annotation.NonNull java.io.FileOutputStream r0) {
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
        r0 = r0.getFD();	 Catch:{ IOException -> 0x0009 }
        r0.sync();	 Catch:{ IOException -> 0x0009 }
        r0 = 1;
        return r0;
    L_0x0009:
        r0 = 0;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.util.AtomicFile.sync(java.io.FileOutputStream):boolean");
    }
}
