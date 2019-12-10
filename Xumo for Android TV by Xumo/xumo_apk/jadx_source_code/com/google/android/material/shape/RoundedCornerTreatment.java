package com.google.android.material.shape;

import com.android.volley.toolbox.ImageRequest;
import com.google.android.material.internal.Experimental;

@Experimental("The shapes API is currently experimental and subject to change")
public class RoundedCornerTreatment extends CornerTreatment {
    private final float radius;

    public RoundedCornerTreatment(float f) {
        this.radius = f;
    }

    public void getCornerPath(float f, float f2, ShapePath shapePath) {
        shapePath.reset(0.0f, this.radius * f2);
        shapePath.addArc(0.0f, 0.0f, (this.radius * ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT) * f2, (this.radius * ImageRequest.DEFAULT_IMAGE_BACKOFF_MULT) * f2, f + 180.0f, 90.0f);
    }
}
