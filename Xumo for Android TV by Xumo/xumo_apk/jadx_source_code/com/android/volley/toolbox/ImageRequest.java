package com.android.volley.toolbox;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.widget.ImageView.ScaleType;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Request.Priority;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyLog;

public class ImageRequest extends Request<Bitmap> {
    public static final float DEFAULT_IMAGE_BACKOFF_MULT = 2.0f;
    public static final int DEFAULT_IMAGE_MAX_RETRIES = 2;
    public static final int DEFAULT_IMAGE_TIMEOUT_MS = 1000;
    private static final Object sDecodeLock = new Object();
    private final Config mDecodeConfig;
    private Listener<Bitmap> mListener;
    private final Object mLock;
    private final int mMaxHeight;
    private final int mMaxWidth;
    private final ScaleType mScaleType;

    public ImageRequest(String str, Listener<Bitmap> listener, int i, int i2, ScaleType scaleType, Config config, ErrorListener errorListener) {
        super(0, str, errorListener);
        this.mLock = new Object();
        setRetryPolicy(new DefaultRetryPolicy(1000, 2, DEFAULT_IMAGE_BACKOFF_MULT));
        this.mListener = listener;
        this.mDecodeConfig = config;
        this.mMaxWidth = i;
        this.mMaxHeight = i2;
        this.mScaleType = scaleType;
    }

    @Deprecated
    public ImageRequest(String str, Listener<Bitmap> listener, int i, int i2, Config config, ErrorListener errorListener) {
        this(str, listener, i, i2, ScaleType.CENTER_INSIDE, config, errorListener);
    }

    public Priority getPriority() {
        return Priority.LOW;
    }

    private static int getResizedDimension(int i, int i2, int i3, int i4, ScaleType scaleType) {
        if (i == 0 && i2 == 0) {
            return i3;
        }
        if (scaleType == ScaleType.FIT_XY) {
            return i == 0 ? i3 : i;
        } else {
            if (i == 0) {
                i = (double) i2;
                i4 = (double) i4;
                Double.isNaN(i);
                Double.isNaN(i4);
                i /= i4;
                i3 = (double) i3;
                Double.isNaN(i3);
                return (int) (i3 * i);
            } else if (i2 == 0) {
                return i;
            } else {
                double d = (double) i4;
                i3 = (double) i3;
                Double.isNaN(d);
                Double.isNaN(i3);
                d /= i3;
                double d2;
                if (scaleType == ScaleType.CENTER_CROP) {
                    i3 = (double) i;
                    Double.isNaN(i3);
                    d2 = (double) i2;
                    if (i3 * d < d2) {
                        Double.isNaN(d2);
                        i = (int) (d2 / d);
                    }
                    return i;
                }
                i3 = (double) i;
                Double.isNaN(i3);
                d2 = (double) i2;
                if (i3 * d > d2) {
                    Double.isNaN(d2);
                    i = (int) (d2 / d);
                }
                return i;
            }
        }
    }

    protected Response<Bitmap> parseNetworkResponse(NetworkResponse networkResponse) {
        Response<Bitmap> doParse;
        synchronized (sDecodeLock) {
            try {
                doParse = doParse(networkResponse);
            } catch (Throwable e) {
                VolleyLog.e("Caught OOM for %d byte image, url=%s", Integer.valueOf(networkResponse.data.length), getUrl());
                return Response.error(new ParseError(e));
            }
        }
        return doParse;
    }

    private Response<Bitmap> doParse(NetworkResponse networkResponse) {
        Object decodeByteArray;
        byte[] bArr = networkResponse.data;
        Options options = new Options();
        if (this.mMaxWidth == 0 && this.mMaxHeight == 0) {
            options.inPreferredConfig = this.mDecodeConfig;
            decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
        } else {
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
            int i = options.outWidth;
            int i2 = options.outHeight;
            int resizedDimension = getResizedDimension(this.mMaxWidth, this.mMaxHeight, i, i2, this.mScaleType);
            int resizedDimension2 = getResizedDimension(this.mMaxHeight, this.mMaxWidth, i2, i, this.mScaleType);
            options.inJustDecodeBounds = false;
            options.inSampleSize = findBestSampleSize(i, i2, resizedDimension, resizedDimension2);
            decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
            if (decodeByteArray != null && (decodeByteArray.getWidth() > resizedDimension || decodeByteArray.getHeight() > resizedDimension2)) {
                Bitmap createScaledBitmap = Bitmap.createScaledBitmap(decodeByteArray, resizedDimension, resizedDimension2, true);
                decodeByteArray.recycle();
                decodeByteArray = createScaledBitmap;
            }
        }
        if (decodeByteArray == null) {
            return Response.error(new ParseError(networkResponse));
        }
        return Response.success(decodeByteArray, HttpHeaderParser.parseCacheHeaders(networkResponse));
    }

    public void cancel() {
        super.cancel();
        synchronized (this.mLock) {
            this.mListener = null;
        }
    }

    protected void deliverResponse(Bitmap bitmap) {
        synchronized (this.mLock) {
            Listener listener = this.mListener;
        }
        if (listener != null) {
            listener.onResponse(bitmap);
        }
    }

    static int findBestSampleSize(int i, int i2, int i3, int i4) {
        double d = (double) i;
        double d2 = (double) i3;
        Double.isNaN(d);
        Double.isNaN(d2);
        d /= d2;
        i = (double) i2;
        i3 = (double) i4;
        Double.isNaN(i);
        Double.isNaN(i3);
        i = Math.min(d, i / i3);
        i3 = 1065353216;
        while (true) {
            i4 = 1073741824 * i3;
            if (((double) i4) > i) {
                return (int) i3;
            }
            i3 = i4;
        }
    }
}
