package com.google.android.exoplayer2.video.spherical;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.spherical.Projection.Mesh;
import com.google.android.exoplayer2.video.spherical.Projection.SubMesh;
import com.google.android.gms.cast.MediaLoadOptions;
import java.util.ArrayList;
import java.util.zip.Inflater;

public final class ProjectionDecoder {
    private static final int MAX_COORDINATE_COUNT = 10000;
    private static final int MAX_TRIANGLE_INDICES = 128000;
    private static final int MAX_VERTEX_COUNT = 32000;
    private static final int TYPE_DFL8 = Util.getIntegerCodeForString("dfl8");
    private static final int TYPE_MESH = Util.getIntegerCodeForString("mesh");
    private static final int TYPE_MSHP = Util.getIntegerCodeForString("mshp");
    private static final int TYPE_PROJ = Util.getIntegerCodeForString("proj");
    private static final int TYPE_RAW = Util.getIntegerCodeForString("raw ");
    private static final int TYPE_YTMP = Util.getIntegerCodeForString("ytmp");

    private static int decodeZigZag(int i) {
        return (-(i & 1)) ^ (i >> 1);
    }

    private ProjectionDecoder() {
    }

    @androidx.annotation.Nullable
    public static com.google.android.exoplayer2.video.spherical.Projection decode(byte[] r3, int r4) {
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
        r0 = new com.google.android.exoplayer2.util.ParsableByteArray;
        r0.<init>(r3);
        r3 = 0;
        r1 = isProj(r0);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0016 }
        if (r1 == 0) goto L_0x0011;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0016 }
    L_0x000c:
        r0 = parseProj(r0);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0016 }
        goto L_0x0017;	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0016 }
    L_0x0011:
        r0 = parseMshp(r0);	 Catch:{ ArrayIndexOutOfBoundsException -> 0x0016 }
        goto L_0x0017;
    L_0x0016:
        r0 = r3;
    L_0x0017:
        if (r0 != 0) goto L_0x001a;
    L_0x0019:
        return r3;
    L_0x001a:
        r1 = r0.size();
        r2 = 0;
        switch(r1) {
            case 1: goto L_0x0036;
            case 2: goto L_0x0023;
            default: goto L_0x0022;
        };
    L_0x0022:
        return r3;
    L_0x0023:
        r3 = new com.google.android.exoplayer2.video.spherical.Projection;
        r1 = r0.get(r2);
        r1 = (com.google.android.exoplayer2.video.spherical.Projection.Mesh) r1;
        r2 = 1;
        r0 = r0.get(r2);
        r0 = (com.google.android.exoplayer2.video.spherical.Projection.Mesh) r0;
        r3.<init>(r1, r0, r4);
        return r3;
    L_0x0036:
        r3 = new com.google.android.exoplayer2.video.spherical.Projection;
        r0 = r0.get(r2);
        r0 = (com.google.android.exoplayer2.video.spherical.Projection.Mesh) r0;
        r3.<init>(r0, r4);
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.video.spherical.ProjectionDecoder.decode(byte[], int):com.google.android.exoplayer2.video.spherical.Projection");
    }

    private static boolean isProj(ParsableByteArray parsableByteArray) {
        parsableByteArray.skipBytes(4);
        int readInt = parsableByteArray.readInt();
        parsableByteArray.setPosition(0);
        if (readInt == TYPE_PROJ) {
            return true;
        }
        return false;
    }

    @Nullable
    private static ArrayList<Mesh> parseProj(ParsableByteArray parsableByteArray) {
        parsableByteArray.skipBytes(8);
        int position = parsableByteArray.getPosition();
        int limit = parsableByteArray.limit();
        while (position < limit) {
            int readInt = parsableByteArray.readInt() + position;
            if (readInt > position) {
                if (readInt <= limit) {
                    position = parsableByteArray.readInt();
                    if (position != TYPE_YTMP) {
                        if (position != TYPE_MSHP) {
                            parsableByteArray.setPosition(readInt);
                            position = readInt;
                        }
                    }
                    parsableByteArray.setLimit(readInt);
                    return parseMshp(parsableByteArray);
                }
            }
            return null;
        }
        return null;
    }

    @Nullable
    private static ArrayList<Mesh> parseMshp(ParsableByteArray parsableByteArray) {
        if (parsableByteArray.readUnsignedByte() != 0) {
            return null;
        }
        parsableByteArray.skipBytes(7);
        int readInt = parsableByteArray.readInt();
        if (readInt == TYPE_DFL8) {
            ParsableByteArray parsableByteArray2 = new ParsableByteArray();
            Inflater inflater = new Inflater(true);
            try {
                if (Util.inflate(parsableByteArray, parsableByteArray2, inflater) == null) {
                    return null;
                }
                inflater.end();
                parsableByteArray = parsableByteArray2;
            } finally {
                inflater.end();
            }
        } else if (readInt != TYPE_RAW) {
            return null;
        }
        return parseRawMshpData(parsableByteArray);
    }

    @Nullable
    private static ArrayList<Mesh> parseRawMshpData(ParsableByteArray parsableByteArray) {
        ArrayList<Mesh> arrayList = new ArrayList();
        int position = parsableByteArray.getPosition();
        int limit = parsableByteArray.limit();
        while (position < limit) {
            int readInt = parsableByteArray.readInt() + position;
            if (readInt > position) {
                if (readInt <= limit) {
                    if (parsableByteArray.readInt() == TYPE_MESH) {
                        Mesh parseMesh = parseMesh(parsableByteArray);
                        if (parseMesh == null) {
                            return null;
                        }
                        arrayList.add(parseMesh);
                    }
                    parsableByteArray.setPosition(readInt);
                    position = readInt;
                }
            }
            return null;
        }
        return arrayList;
    }

    @Nullable
    private static Mesh parseMesh(ParsableByteArray parsableByteArray) {
        int readInt = parsableByteArray.readInt();
        if (readInt > 10000) {
            return null;
        }
        int i;
        float[] fArr = new float[readInt];
        for (i = 0; i < readInt; i++) {
            fArr[i] = parsableByteArray.readFloat();
        }
        i = parsableByteArray.readInt();
        if (i > MAX_VERTEX_COUNT) {
            return null;
        }
        double d = MediaLoadOptions.PLAYBACK_RATE_MAX;
        double log = Math.log(MediaLoadOptions.PLAYBACK_RATE_MAX);
        double d2 = (double) readInt;
        Double.isNaN(d2);
        int ceil = (int) Math.ceil(Math.log(d2 * MediaLoadOptions.PLAYBACK_RATE_MAX) / log);
        ParsableBitArray parsableBitArray = new ParsableBitArray(parsableByteArray.data);
        int i2 = 8;
        parsableBitArray.setPosition(parsableByteArray.getPosition() * 8);
        float[] fArr2 = new float[(i * 5)];
        int i3 = 5;
        int[] iArr = new int[5];
        int i4 = 0;
        int i5 = 0;
        while (i4 < i) {
            int i6 = 0;
            while (i6 < i3) {
                i3 = iArr[i6] + decodeZigZag(parsableBitArray.readBits(ceil));
                if (i3 < readInt) {
                    if (i3 >= 0) {
                        int i7 = i5 + 1;
                        fArr2[i5] = fArr[i3];
                        iArr[i6] = i3;
                        i6++;
                        i5 = i7;
                        i3 = 5;
                    }
                }
                return null;
            }
            i4++;
            i3 = 5;
        }
        parsableBitArray.setPosition((parsableBitArray.getPosition() + 7) & -8);
        readInt = 32;
        int readBits = parsableBitArray.readBits(32);
        SubMesh[] subMeshArr = new SubMesh[readBits];
        ceil = 0;
        while (ceil < readBits) {
            i3 = parsableBitArray.readBits(i2);
            int readBits2 = parsableBitArray.readBits(i2);
            i4 = parsableBitArray.readBits(readInt);
            if (i4 > MAX_TRIANGLE_INDICES) {
                return null;
            }
            int i8 = i3;
            double d3 = (double) i;
            Double.isNaN(d3);
            readInt = (int) Math.ceil(Math.log(d3 * d) / log);
            float[] fArr3 = new float[(i4 * 3)];
            float[] fArr4 = new float[(i4 * 2)];
            int i9 = 0;
            int i10 = 0;
            while (i9 < i4) {
                i10 += decodeZigZag(parsableBitArray.readBits(readInt));
                if (i10 >= 0) {
                    if (i10 < i) {
                        i5 = i9 * 3;
                        i7 = i10 * 5;
                        fArr3[i5] = fArr2[i7];
                        fArr3[i5 + 1] = fArr2[i7 + 1];
                        fArr3[i5 + 2] = fArr2[i7 + 2];
                        i5 = i9 * 2;
                        fArr4[i5] = fArr2[i7 + 3];
                        fArr4[i5 + 1] = fArr2[i7 + 4];
                        i9++;
                    }
                }
                return null;
            }
            subMeshArr[ceil] = new SubMesh(i8, fArr3, fArr4, readBits2);
            ceil++;
            readInt = 32;
            d = MediaLoadOptions.PLAYBACK_RATE_MAX;
            i2 = 8;
        }
        return new Mesh(subMeshArr);
    }
}
