package com.google.android.exoplayer2.ui.spherical;

import android.annotation.TargetApi;
import android.opengl.GLES20;
import com.google.android.exoplayer2.video.spherical.Projection;
import com.google.android.exoplayer2.video.spherical.Projection.Mesh;
import com.google.android.exoplayer2.video.spherical.Projection.SubMesh;
import java.nio.FloatBuffer;

@TargetApi(15)
final class ProjectionRenderer {
    private static final String[] FRAGMENT_SHADER_CODE = new String[]{"#extension GL_OES_EGL_image_external : require", "precision mediump float;", "uniform samplerExternalOES uTexture;", "varying vec2 vTexCoords;", "void main() {", "  gl_FragColor = texture2D(uTexture, vTexCoords);", "}"};
    private static final float[] TEX_MATRIX_BOTTOM = new float[]{1.0f, 0.0f, 0.0f, 0.0f, -0.5f, 0.0f, 0.0f, 1.0f, 1.0f};
    private static final float[] TEX_MATRIX_LEFT = new float[]{0.5f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f};
    private static final float[] TEX_MATRIX_RIGHT = new float[]{0.5f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.5f, 1.0f, 1.0f};
    private static final float[] TEX_MATRIX_TOP = new float[]{1.0f, 0.0f, 0.0f, 0.0f, -0.5f, 0.0f, 0.0f, 0.5f, 1.0f};
    private static final float[] TEX_MATRIX_WHOLE = new float[]{1.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f};
    private static final String[] VERTEX_SHADER_CODE = new String[]{"uniform mat4 uMvpMatrix;", "uniform mat3 uTexMatrix;", "attribute vec4 aPosition;", "attribute vec2 aTexCoords;", "varying vec2 vTexCoords;", "void main() {", "  gl_Position = uMvpMatrix * aPosition;", "  vTexCoords = (uTexMatrix * vec3(aTexCoords, 1)).xy;", "}"};
    private MeshData leftMeshData;
    private int mvpMatrixHandle;
    private int positionHandle;
    private int program;
    private MeshData rightMeshData;
    private int stereoMode;
    private int texCoordsHandle;
    private int textureHandle;
    private int uTexMatrixHandle;

    interface EyeType {
        public static final int LEFT = 1;
        public static final int MONOCULAR = 0;
        public static final int RIGHT = 2;
    }

    private static class MeshData {
        private final int drawMode;
        private final FloatBuffer textureBuffer;
        private final FloatBuffer vertexBuffer;
        private final int vertexCount;

        public MeshData(SubMesh subMesh) {
            this.vertexCount = subMesh.getVertexCount();
            this.vertexBuffer = GlUtil.createBuffer(subMesh.vertices);
            this.textureBuffer = GlUtil.createBuffer(subMesh.textureCoords);
            switch (subMesh.mode) {
                case 1:
                    this.drawMode = 5;
                    return;
                case 2:
                    this.drawMode = 6;
                    return;
                default:
                    this.drawMode = 4;
                    return;
            }
        }
    }

    ProjectionRenderer() {
    }

    public static boolean isSupported(Projection projection) {
        Mesh mesh = projection.leftMesh;
        projection = projection.rightMesh;
        if (mesh.getSubMeshCount() == 1 && mesh.getSubMesh(0).textureId == 0 && projection.getSubMeshCount() == 1 && projection.getSubMesh(0).textureId == null) {
            return true;
        }
        return false;
    }

    public void setProjection(Projection projection) {
        if (isSupported(projection)) {
            this.stereoMode = projection.stereoMode;
            this.leftMeshData = new MeshData(projection.leftMesh.getSubMesh(0));
            if (projection.singleMesh) {
                projection = this.leftMeshData;
            } else {
                projection = new MeshData(projection.rightMesh.getSubMesh(0));
            }
            this.rightMeshData = projection;
        }
    }

    void init() {
        this.program = GlUtil.compileProgram(VERTEX_SHADER_CODE, FRAGMENT_SHADER_CODE);
        this.mvpMatrixHandle = GLES20.glGetUniformLocation(this.program, "uMvpMatrix");
        this.uTexMatrixHandle = GLES20.glGetUniformLocation(this.program, "uTexMatrix");
        this.positionHandle = GLES20.glGetAttribLocation(this.program, "aPosition");
        this.texCoordsHandle = GLES20.glGetAttribLocation(this.program, "aTexCoords");
        this.textureHandle = GLES20.glGetUniformLocation(this.program, "uTexture");
    }

    void draw(int i, float[] fArr, int i2) {
        int i3 = i2;
        MeshData meshData = i3 == 2 ? r0.rightMeshData : this.leftMeshData;
        if (meshData != null) {
            GLES20.glUseProgram(r0.program);
            GlUtil.checkGlError();
            GLES20.glEnableVertexAttribArray(r0.positionHandle);
            GLES20.glEnableVertexAttribArray(r0.texCoordsHandle);
            GlUtil.checkGlError();
            float[] fArr2 = r0.stereoMode == 1 ? i3 == 2 ? TEX_MATRIX_BOTTOM : TEX_MATRIX_TOP : r0.stereoMode == 2 ? i3 == 2 ? TEX_MATRIX_RIGHT : TEX_MATRIX_LEFT : TEX_MATRIX_WHOLE;
            GLES20.glUniformMatrix3fv(r0.uTexMatrixHandle, 1, false, fArr2, 0);
            GLES20.glUniformMatrix4fv(r0.mvpMatrixHandle, 1, false, fArr, 0);
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(36197, i);
            GLES20.glUniform1i(r0.textureHandle, 0);
            GlUtil.checkGlError();
            GLES20.glVertexAttribPointer(r0.positionHandle, 3, 5126, false, 12, meshData.vertexBuffer);
            GlUtil.checkGlError();
            GLES20.glVertexAttribPointer(r0.texCoordsHandle, 2, 5126, false, 8, meshData.textureBuffer);
            GlUtil.checkGlError();
            GLES20.glDrawArrays(meshData.drawMode, 0, meshData.vertexCount);
            GlUtil.checkGlError();
            GLES20.glDisableVertexAttribArray(r0.positionHandle);
            GLES20.glDisableVertexAttribArray(r0.texCoordsHandle);
        }
    }

    void shutdown() {
        if (this.program != 0) {
            GLES20.glDeleteProgram(this.program);
        }
    }
}
