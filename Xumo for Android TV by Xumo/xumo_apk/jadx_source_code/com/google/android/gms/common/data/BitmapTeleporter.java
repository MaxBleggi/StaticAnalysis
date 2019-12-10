package com.google.android.gms.common.data;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.ParcelFileDescriptor.AutoCloseInputStream;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.VersionField;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.nio.Buffer;
import java.nio.ByteBuffer;

@ShowFirstParty
@KeepForSdk
@Class(creator = "BitmapTeleporterCreator")
public class BitmapTeleporter extends AbstractSafeParcelable implements ReflectedParcelable {
    @KeepForSdk
    public static final Creator<BitmapTeleporter> CREATOR = new zaa();
    @Field(id = 3)
    private final int mType;
    @VersionField(id = 1)
    private final int zale;
    @Field(id = 2)
    private ParcelFileDescriptor zalf;
    private Bitmap zalg;
    private boolean zalh;
    private File zali;

    @Constructor
    BitmapTeleporter(@Param(id = 1) int i, @Param(id = 2) ParcelFileDescriptor parcelFileDescriptor, @Param(id = 3) int i2) {
        this.zale = i;
        this.zalf = parcelFileDescriptor;
        this.mType = i2;
        this.zalg = 0;
        this.zalh = false;
    }

    @KeepForSdk
    public BitmapTeleporter(Bitmap bitmap) {
        this.zale = 1;
        this.zalf = null;
        this.mType = 0;
        this.zalg = bitmap;
        this.zalh = true;
    }

    @KeepForSdk
    public Bitmap get() {
        if (!this.zalh) {
            Closeable dataInputStream = new DataInputStream(new AutoCloseInputStream(this.zalf));
            try {
                byte[] bArr = new byte[dataInputStream.readInt()];
                int readInt = dataInputStream.readInt();
                int readInt2 = dataInputStream.readInt();
                Config valueOf = Config.valueOf(dataInputStream.readUTF());
                dataInputStream.read(bArr);
                zaa(dataInputStream);
                Buffer wrap = ByteBuffer.wrap(bArr);
                Bitmap createBitmap = Bitmap.createBitmap(readInt, readInt2, valueOf);
                createBitmap.copyPixelsFromBuffer(wrap);
                this.zalg = createBitmap;
                this.zalh = true;
            } catch (Throwable e) {
                throw new IllegalStateException("Could not read from parcel file descriptor", e);
            } catch (Throwable th) {
                zaa(dataInputStream);
            }
        }
        return this.zalg;
    }

    public void writeToParcel(Parcel parcel, int i) {
        if (this.zalf == null) {
            Bitmap bitmap = this.zalg;
            Buffer allocate = ByteBuffer.allocate(bitmap.getRowBytes() * bitmap.getHeight());
            bitmap.copyPixelsToBuffer(allocate);
            byte[] array = allocate.array();
            Closeable dataOutputStream = new DataOutputStream(new BufferedOutputStream(zabz()));
            try {
                dataOutputStream.writeInt(array.length);
                dataOutputStream.writeInt(bitmap.getWidth());
                dataOutputStream.writeInt(bitmap.getHeight());
                dataOutputStream.writeUTF(bitmap.getConfig().toString());
                dataOutputStream.write(array);
                zaa(dataOutputStream);
            } catch (Parcel parcel2) {
                throw new IllegalStateException("Could not write into unlinked file", parcel2);
            } catch (Throwable th) {
                zaa(dataOutputStream);
            }
        }
        i |= 1;
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel2);
        SafeParcelWriter.writeInt(parcel2, 1, this.zale);
        SafeParcelWriter.writeParcelable(parcel2, 2, this.zalf, i, false);
        SafeParcelWriter.writeInt(parcel2, 3, this.mType);
        SafeParcelWriter.finishObjectHeader(parcel2, beginObjectHeader);
        this.zalf = null;
    }

    @KeepForSdk
    public void release() {
        if (!this.zalh) {
            try {
                this.zalf.close();
            } catch (Throwable e) {
                Log.w("BitmapTeleporter", "Could not close PFD", e);
            }
        }
    }

    @KeepForSdk
    public void setTempDir(File file) {
        if (file != null) {
            this.zali = file;
            return;
        }
        throw new NullPointerException("Cannot set null temp directory");
    }

    private final java.io.FileOutputStream zabz() {
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
        r3 = this;
        r0 = r3.zali;
        if (r0 == 0) goto L_0x0030;
    L_0x0004:
        r0 = "teleporter";	 Catch:{ IOException -> 0x0027 }
        r1 = ".tmp";	 Catch:{ IOException -> 0x0027 }
        r2 = r3.zali;	 Catch:{ IOException -> 0x0027 }
        r0 = java.io.File.createTempFile(r0, r1, r2);	 Catch:{ IOException -> 0x0027 }
        r1 = new java.io.FileOutputStream;	 Catch:{ FileNotFoundException -> 0x001f }
        r1.<init>(r0);	 Catch:{ FileNotFoundException -> 0x001f }
        r2 = 268435456; // 0x10000000 float:2.5243549E-29 double:1.32624737E-315;	 Catch:{ FileNotFoundException -> 0x001f }
        r2 = android.os.ParcelFileDescriptor.open(r0, r2);	 Catch:{ FileNotFoundException -> 0x001f }
        r3.zalf = r2;	 Catch:{ FileNotFoundException -> 0x001f }
        r0.delete();
        return r1;
    L_0x001f:
        r0 = new java.lang.IllegalStateException;
        r1 = "Temporary file is somehow already deleted";
        r0.<init>(r1);
        throw r0;
    L_0x0027:
        r0 = move-exception;
        r1 = new java.lang.IllegalStateException;
        r2 = "Could not create temporary file";
        r1.<init>(r2, r0);
        throw r1;
    L_0x0030:
        r0 = new java.lang.IllegalStateException;
        r1 = "setTempDir() must be called before writing this object to a parcel";
        r0.<init>(r1);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.data.BitmapTeleporter.zabz():java.io.FileOutputStream");
    }

    private static void zaa(Closeable closeable) {
        try {
            closeable.close();
        } catch (Closeable closeable2) {
            Log.w("BitmapTeleporter", "Could not close stream", closeable2);
        }
    }
}
