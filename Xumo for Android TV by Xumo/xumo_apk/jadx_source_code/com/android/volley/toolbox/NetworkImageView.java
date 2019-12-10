package com.android.volley.toolbox;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;

public class NetworkImageView extends ImageView {
    private int mDefaultImageId;
    private int mErrorImageId;
    private ImageContainer mImageContainer;
    private ImageLoader mImageLoader;
    private String mUrl;

    public NetworkImageView(Context context) {
        this(context, null);
    }

    public NetworkImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NetworkImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setImageUrl(String str, ImageLoader imageLoader) {
        this.mUrl = str;
        this.mImageLoader = imageLoader;
        loadImageIfNecessary(null);
    }

    public void setDefaultImageResId(int i) {
        this.mDefaultImageId = i;
    }

    public void setErrorImageResId(int i) {
        this.mErrorImageId = i;
    }

    void loadImageIfNecessary(final boolean z) {
        Object obj;
        int width = getWidth();
        int height = getHeight();
        ScaleType scaleType = getScaleType();
        Object obj2 = 1;
        Object obj3;
        if (getLayoutParams() != null) {
            obj3 = getLayoutParams().width == -2 ? 1 : null;
            if (getLayoutParams().height == -2) {
                obj = 1;
                if (obj3 != null || obj == null) {
                    obj2 = null;
                }
                if (width == 0 || height != 0 || r3 != null) {
                    if (!TextUtils.isEmpty(this.mUrl)) {
                        if (!(this.mImageContainer == null || this.mImageContainer.getRequestUrl() == null)) {
                            if (this.mImageContainer.getRequestUrl().equals(this.mUrl)) {
                                this.mImageContainer.cancelRequest();
                                setDefaultImageOrNull();
                            }
                        }
                        if (obj3 != null) {
                            width = 0;
                        }
                        this.mImageContainer = this.mImageLoader.get(this.mUrl, new ImageListener() {
                            public void onErrorResponse(VolleyError volleyError) {
                                if (NetworkImageView.this.mErrorImageId != null) {
                                    NetworkImageView.this.setImageResource(NetworkImageView.this.mErrorImageId);
                                }
                            }

                            public void onResponse(final ImageContainer imageContainer, boolean z) {
                                if (z && z) {
                                    NetworkImageView.this.post(new Runnable() {
                                        public void run() {
                                            AnonymousClass1.this.onResponse(imageContainer, false);
                                        }
                                    });
                                    return;
                                }
                                if (imageContainer.getBitmap()) {
                                    NetworkImageView.this.setImageBitmap(imageContainer.getBitmap());
                                } else if (NetworkImageView.this.mDefaultImageId != null) {
                                    NetworkImageView.this.setImageResource(NetworkImageView.this.mDefaultImageId);
                                }
                            }
                        }, width, obj == null ? 0 : height, scaleType);
                    }
                    if (this.mImageContainer) {
                        this.mImageContainer.cancelRequest();
                        this.mImageContainer = false;
                    }
                    setDefaultImageOrNull();
                }
                return;
            }
        }
        obj3 = null;
        obj = null;
        if (obj3 != null) {
        }
        obj2 = null;
        if (width == 0) {
        }
        if (!TextUtils.isEmpty(this.mUrl)) {
            if (this.mImageContainer) {
                this.mImageContainer.cancelRequest();
                this.mImageContainer = false;
            }
            setDefaultImageOrNull();
        } else if (this.mImageContainer.getRequestUrl().equals(this.mUrl)) {
            this.mImageContainer.cancelRequest();
            setDefaultImageOrNull();
            if (obj3 != null) {
                width = 0;
            }
            if (obj == null) {
            }
            this.mImageContainer = this.mImageLoader.get(this.mUrl, /* anonymous class already generated */, width, obj == null ? 0 : height, scaleType);
        }
    }

    private void setDefaultImageOrNull() {
        if (this.mDefaultImageId != 0) {
            setImageResource(this.mDefaultImageId);
        } else {
            setImageBitmap(null);
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        loadImageIfNecessary(true);
    }

    protected void onDetachedFromWindow() {
        if (this.mImageContainer != null) {
            this.mImageContainer.cancelRequest();
            setImageBitmap(null);
            this.mImageContainer = null;
        }
        super.onDetachedFromWindow();
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        invalidate();
    }
}
