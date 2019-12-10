package com.google.android.exoplayer2.video.spherical;

import com.android.volley.toolbox.ImageRequest;
import com.google.android.exoplayer2.util.Assertions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Projection {
    public static final int DRAW_MODE_TRIANGLES = 0;
    public static final int DRAW_MODE_TRIANGLES_FAN = 2;
    public static final int DRAW_MODE_TRIANGLES_STRIP = 1;
    public static final int POSITION_COORDS_PER_VERTEX = 3;
    public static final int TEXTURE_COORDS_PER_VERTEX = 2;
    public final Mesh leftMesh;
    public final Mesh rightMesh;
    public final boolean singleMesh;
    public final int stereoMode;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DrawMode {
    }

    public static final class Mesh {
        private final SubMesh[] subMeshes;

        public Mesh(SubMesh... subMeshArr) {
            this.subMeshes = subMeshArr;
        }

        public int getSubMeshCount() {
            return this.subMeshes.length;
        }

        public SubMesh getSubMesh(int i) {
            return this.subMeshes[i];
        }
    }

    public static final class SubMesh {
        public static final int VIDEO_TEXTURE_ID = 0;
        public final int mode;
        public final float[] textureCoords;
        public final int textureId;
        public final float[] vertices;

        public SubMesh(int i, float[] fArr, float[] fArr2, int i2) {
            this.textureId = i;
            Assertions.checkArgument(((long) fArr.length) * 2 == ((long) fArr2.length) * 3 ? 1 : 0);
            this.vertices = fArr;
            this.textureCoords = fArr2;
            this.mode = i2;
        }

        public int getVertexCount() {
            return this.vertices.length / 3;
        }
    }

    public static Projection createEquirectangular(int i) {
        return createEquirectangular(50.0f, 36, 72, 180.0f, 360.0f, i);
    }

    public static Projection createEquirectangular(float f, int i, int i2, float f2, float f3, int i3) {
        float f4 = f;
        int i4 = i;
        int i5 = i2;
        float f5 = f2;
        float f6 = f3;
        Assertions.checkArgument(f4 > 0.0f);
        Assertions.checkArgument(i4 >= 1);
        Assertions.checkArgument(i5 >= 1);
        boolean z = f5 > 0.0f && f5 <= 180.0f;
        Assertions.checkArgument(z);
        boolean z2 = f6 > 0.0f && f6 <= 360.0f;
        Assertions.checkArgument(z2);
        f5 = (float) Math.toRadians((double) f5);
        f6 = (float) Math.toRadians((double) f6);
        float f7 = f5 / ((float) i4);
        float f8 = f6 / ((float) i5);
        int i6 = i5 + 1;
        int i7 = ((i6 * 2) + 2) * i4;
        Object obj = new float[(i7 * 3)];
        Object obj2 = new float[(i7 * 2)];
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        while (i8 < i4) {
            float f9 = f5 / ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT;
            float f10 = (((float) i8) * f7) - f9;
            int i11 = i8 + 1;
            float f11 = (((float) i11) * f7) - f9;
            int i12 = i10;
            i10 = i9;
            i9 = 0;
            while (i9 < i6) {
                int i13;
                float f12;
                int i14;
                float f13;
                int i15;
                int i16 = i12;
                i4 = 2;
                i12 = i10;
                i10 = 0;
                while (i10 < i4) {
                    float f14;
                    float f15;
                    if (i10 == 0) {
                        f14 = f10;
                        f15 = f14;
                    } else {
                        f15 = f10;
                        f14 = f11;
                    }
                    f10 = ((float) i9) * f8;
                    i13 = i11;
                    int i17 = i12 + 1;
                    f12 = f8;
                    i14 = i6;
                    double d = (double) f4;
                    f13 = f5;
                    double d2 = (double) ((f10 + 3.1415927f) - (f6 / ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT));
                    double sin = Math.sin(d2);
                    Double.isNaN(d);
                    double d3 = (double) f14;
                    i15 = i8;
                    int i18 = i9;
                    obj[i12] = -((float) ((sin * d) * Math.cos(d3)));
                    i11 = i17 + 1;
                    double sin2 = Math.sin(d3);
                    Double.isNaN(d);
                    obj[i17] = (float) (sin2 * d);
                    i8 = i11 + 1;
                    d2 = Math.cos(d2);
                    Double.isNaN(d);
                    obj[i11] = (float) ((d * d2) * Math.cos(d3));
                    int i19 = i16 + 1;
                    obj2[i16] = f10 / f6;
                    i4 = i19 + 1;
                    obj2[i19] = (((float) (i15 + i10)) * f7) / f13;
                    if (i18 == 0) {
                        if (i10 == 0) {
                            i5 = i18;
                            i19 = i2;
                            System.arraycopy(obj, i8 - 3, obj, i8, 3);
                            i8 += 3;
                            System.arraycopy(obj2, i4 - 2, obj2, i4, 2);
                            i4 += 2;
                            i16 = i4;
                            i12 = i8;
                            i10++;
                            i9 = i5;
                            f10 = f15;
                            i11 = i13;
                            f8 = f12;
                            i6 = i14;
                            f5 = f13;
                            i8 = i15;
                            i4 = 2;
                            i5 = i19;
                            f4 = f;
                        }
                    }
                    i5 = i18;
                    i19 = i2;
                    if (!(i5 == i19 && i10 == 1)) {
                        i16 = i4;
                        i12 = i8;
                        i10++;
                        i9 = i5;
                        f10 = f15;
                        i11 = i13;
                        f8 = f12;
                        i6 = i14;
                        f5 = f13;
                        i8 = i15;
                        i4 = 2;
                        i5 = i19;
                        f4 = f;
                    }
                    System.arraycopy(obj, i8 - 3, obj, i8, 3);
                    i8 += 3;
                    System.arraycopy(obj2, i4 - 2, obj2, i4, 2);
                    i4 += 2;
                    i16 = i4;
                    i12 = i8;
                    i10++;
                    i9 = i5;
                    f10 = f15;
                    i11 = i13;
                    f8 = f12;
                    i6 = i14;
                    f5 = f13;
                    i8 = i15;
                    i4 = 2;
                    i5 = i19;
                    f4 = f;
                }
                f13 = f5;
                i13 = i11;
                f12 = f8;
                i14 = i6;
                i15 = i8;
                i9++;
                i5 = i5;
                i10 = i12;
                i12 = i16;
                f10 = f10;
                f4 = f;
                i4 = i;
            }
            i9 = i10;
            i10 = i12;
            i8 = i11;
            f4 = f;
            i4 = i;
        }
        return new Projection(new Mesh(new SubMesh(0, obj, obj2, 1)), i3);
    }

    public Projection(Mesh mesh, int i) {
        this(mesh, mesh, i);
    }

    public Projection(Mesh mesh, Mesh mesh2, int i) {
        this.leftMesh = mesh;
        this.rightMesh = mesh2;
        this.stereoMode = i;
        this.singleMesh = mesh == mesh2 ? true : null;
    }
}
