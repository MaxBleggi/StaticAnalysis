package com.xumo.xumo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.xumo.xumo.application.XumoApplication;
import com.xumo.xumo.tv.R;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.Locale;

public class XumoImageUtil {
    private static final String BASE_IMAGE_URL = "https://image.xumo.com/v1/";
    private static final String CHANNEL_IMAGE_URL = "https://image.xumo.com/v1/channels/channel/%s/%dx%d.png?type=color_onBlack";
    private static final String CHANNEL_TITLE_URL = "https://image.xumo.com/v1/channels/channel/%s/%dx%d.png?type=%s";
    private static final int DEFAULT_ASSET_THUMBNAIL_HEIGHT = 144;
    private static final int DEFAULT_ASSET_THUMBNAIL_WIDTH = 256;
    private static final int DEFAULT_CHANNEL_IMAGE_HEIGHT = 90;
    private static final int DEFAULT_CHANNEL_IMAGE_WIDTH = 120;
    private static final int DEFAULT_CHANNEL_TITLE_HEIGHT = 204;
    private static final int DEFAULT_CHANNEL_TITLE_WIDTH = 204;
    private static final String PREFIX_CHANNEL_TITLE_IMAGE_KEY = "channelTitle_";
    private static final String PROVIDER_IMAGE_URL = "https://image.xumo.com/v1/providers/provider/%s/120x90.png?type=color_onBlack";
    private static final String VIDEO_IMAGE_URL = "https://image.xumo.com/v1/assets/asset/%s/%dx%d.png";

    public interface ResponseCallback {
        void onCompletion(Bitmap bitmap);
    }

    public static void getProviderImageURL(Context context, String str, ImageView imageView, ResponseCallback responseCallback) {
        if (!TextUtils.isEmpty(str)) {
            getImageWithURLString(context, String.format(PROVIDER_IMAGE_URL, new Object[]{str}), imageView, responseCallback);
        }
    }

    public static void getChannelImageURL(Context context, String str, ImageView imageView, ResponseCallback responseCallback, int... iArr) {
        if (!TextUtils.isEmpty(str)) {
            int i = 120;
            int i2 = 90;
            if (iArr.length >= 1 && iArr[0] > 0) {
                i = iArr[0];
            }
            if (iArr.length >= 2 && iArr[1] > 0) {
                i2 = iArr[1];
            }
            getImageWithURLString(context, String.format(CHANNEL_IMAGE_URL, new Object[]{str, Integer.valueOf(i), Integer.valueOf(i2)}), imageView, responseCallback);
        }
    }

    public static void getChannelTitltURL(Context context, String str, ImageView imageView, ResponseCallback responseCallback, int... iArr) {
        if (!TextUtils.isEmpty(str)) {
            int i = 204;
            int i2 = (iArr.length < 1 || iArr[0] <= 0) ? 204 : iArr[0];
            if (iArr.length >= 2 && iArr[1] > 0) {
                i = iArr[1];
            }
            iArr = "channelTile";
            if (i2 == i) {
                iArr = "smartCast_channelTile";
            }
            if (i2 == 1280 && i == 720) {
                iArr = "ident";
            }
            getImageWithURLString(context, String.format(CHANNEL_TITLE_URL, new Object[]{str, Integer.valueOf(i2), Integer.valueOf(i), iArr}), imageView, responseCallback);
        }
    }

    public static void getAssetThumbnailURL(Context context, String str, ImageView imageView, ResponseCallback responseCallback, int... iArr) {
        if (!TextUtils.isEmpty(str)) {
            int i = 256;
            int i2 = DEFAULT_ASSET_THUMBNAIL_HEIGHT;
            if (iArr.length >= 1 && iArr[0] > 0) {
                i = iArr[0];
            }
            if (iArr.length >= 2 && iArr[1] > 0) {
                i2 = iArr[1];
            }
            getImageWithURLString(context, String.format(Locale.US, VIDEO_IMAGE_URL, new Object[]{str, Integer.valueOf(i), Integer.valueOf(i2)}), imageView, responseCallback);
        }
    }

    public static void getImageWithURLString(Context context, final String str, ImageView imageView, final ResponseCallback responseCallback) {
        XumoApplication xumoApplication = (XumoApplication) context.getApplicationContext();
        String str2 = str;
        imageView.setTag(xumoApplication.getImageLoader().get(str2, new ImageListener() {
            public void onResponse(ImageContainer imageContainer, boolean z) {
                if (imageContainer.getBitmap()) {
                    responseCallback.onCompletion(imageContainer.getBitmap());
                }
            }

            public void onErrorResponse(VolleyError volleyError) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onErrorResponse error=");
                stringBuilder.append(volleyError.getMessage());
                stringBuilder.append(" urlString=");
                stringBuilder.append(str);
                LogUtil.d("debug", stringBuilder.toString());
                responseCallback.onCompletion(null);
            }
        }, imageView.getMeasuredWidth(), imageView.getMeasuredHeight(), ScaleType.FIT_CENTER));
    }

    public static void setProviderImage(Context context, final String str, final ImageView imageView) {
        ImageContainer imageContainer = (ImageContainer) imageView.getTag();
        if (imageContainer != null) {
            imageContainer.cancelRequest();
        }
        imageView.setImageDrawable(null);
        Bitmap bitmap = ImageCache.getInstance().get(str);
        if (bitmap == null) {
            getProviderImageURL(context, str, imageView, new ResponseCallback() {
                public void onCompletion(Bitmap bitmap) {
                    imageView.setImageBitmap(bitmap);
                    if (bitmap != null) {
                        ImageCache.getInstance().put(str, bitmap);
                    }
                }
            });
        } else {
            imageView.setImageBitmap(bitmap);
        }
    }

    public static void setChannelImage(Context context, String str, final ImageView imageView, int... iArr) {
        ImageContainer imageContainer = (ImageContainer) imageView.getTag();
        if (imageContainer != null) {
            imageContainer.cancelRequest();
        }
        imageView.setImageDrawable(null);
        int i = 120;
        int i2 = 90;
        if (iArr.length >= 1 && iArr[0] > 0) {
            i = iArr[0];
        }
        if (iArr.length >= 2 && iArr[1] > 0) {
            i2 = iArr[1];
        }
        final String imageCacheKey = getImageCacheKey(str, i, i2);
        Bitmap bitmap = ImageCache.getInstance().get(imageCacheKey);
        if (bitmap == null) {
            getChannelImageURL(context, str, imageView, new ResponseCallback() {
                public void onCompletion(Bitmap bitmap) {
                    imageView.setImageBitmap(bitmap);
                    if (bitmap != null) {
                        ImageCache.getInstance().put(imageCacheKey, bitmap);
                    }
                }
            }, iArr);
        } else {
            imageView.setImageBitmap(bitmap);
        }
    }

    public static void setAssetThumbnailImage(Context context, String str, String str2, ImageView imageView, int... iArr) {
        ImageContainer imageContainer = (ImageContainer) imageView.getTag();
        if (imageContainer != null) {
            imageContainer.cancelRequest();
        }
        if (str2 != null) {
            imageView.setImageDrawable(null);
        }
        int i = 256;
        int i2 = DEFAULT_ASSET_THUMBNAIL_HEIGHT;
        if (iArr.length >= 1 && iArr[0] > 0) {
            i = iArr[0];
        }
        if (iArr.length >= 2 && iArr[1] > 0) {
            i2 = iArr[1];
        }
        final String imageCacheKey = getImageCacheKey(str, i, i2);
        Bitmap bitmap = ImageCache.getInstance().get(imageCacheKey);
        if (bitmap == null) {
            final ImageView imageView2 = imageView;
            final String str3 = str2;
            final int[] iArr2 = iArr;
            final Context context2 = context;
            getAssetThumbnailURL(context, str, imageView, new ResponseCallback() {
                public void onCompletion(Bitmap bitmap) {
                    if (bitmap != null) {
                        imageView2.setImageBitmap(bitmap);
                        ImageCache.getInstance().put(imageCacheKey, bitmap);
                    } else if (str3 != null) {
                        bitmap = 256;
                        int i = XumoImageUtil.DEFAULT_ASSET_THUMBNAIL_HEIGHT;
                        if (iArr2.length >= 1 && iArr2[0] > 0) {
                            bitmap = iArr2[0];
                        }
                        if (iArr2.length >= 2 && iArr2[1] > 0) {
                            i = iArr2[1];
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(XumoImageUtil.PREFIX_CHANNEL_TITLE_IMAGE_KEY);
                        stringBuilder.append(str3);
                        bitmap = ImageCache.getInstance().get(XumoImageUtil.getImageCacheKey(stringBuilder.toString(), bitmap, i));
                        if (bitmap == null) {
                            XumoImageUtil.getChannelTitltURL(context2, str3, imageView2, new ResponseCallback() {
                                public void onCompletion(Bitmap bitmap) {
                                    if (bitmap != null) {
                                        imageView2.setImageBitmap(bitmap);
                                        ImageCache.getInstance().put(imageCacheKey, bitmap);
                                        return;
                                    }
                                    imageView2.setImageResource(R.drawable.no_thumbnail_image);
                                }
                            }, iArr2);
                            return;
                        }
                        imageView2.setImageBitmap(bitmap);
                        ImageCache.getInstance().put(imageCacheKey, bitmap);
                    }
                }
            }, iArr);
            return;
        }
        imageView.setImageBitmap(bitmap);
    }

    public static void setChannelTitleImage(Context context, String str, final ImageView imageView, int... iArr) {
        ImageContainer imageContainer = (ImageContainer) imageView.getTag();
        if (imageContainer != null) {
            imageContainer.cancelRequest();
        }
        imageView.setImageDrawable(null);
        int i = 204;
        int i2 = (iArr.length < 1 || iArr[0] <= 0) ? 204 : iArr[0];
        if (iArr.length >= 2 && iArr[1] > 0) {
            i = iArr[1];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PREFIX_CHANNEL_TITLE_IMAGE_KEY);
        stringBuilder.append(str);
        final String imageCacheKey = getImageCacheKey(stringBuilder.toString(), i2, i);
        Bitmap bitmap = ImageCache.getInstance().get(imageCacheKey);
        if (bitmap == null) {
            getChannelTitltURL(context, str, imageView, new ResponseCallback() {
                public void onCompletion(Bitmap bitmap) {
                    imageView.setImageBitmap(bitmap);
                    if (bitmap != null) {
                        ImageCache.getInstance().put(imageCacheKey, bitmap);
                    } else {
                        imageView.setImageResource(R.drawable.no_thumbnail_image);
                    }
                }
            }, iArr);
        } else {
            imageView.setImageBitmap(bitmap);
        }
    }

    private static String getImageCacheKey(String str, int i, int i2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        stringBuilder.append(i);
        stringBuilder.append("x");
        stringBuilder.append(i2);
        return stringBuilder.toString();
    }
}
