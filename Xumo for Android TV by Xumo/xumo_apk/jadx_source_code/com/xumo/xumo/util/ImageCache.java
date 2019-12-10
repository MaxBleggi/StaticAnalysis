package com.xumo.xumo.util;

import android.graphics.Bitmap;
import androidx.collection.ArrayMap;
import androidx.recyclerview.widget.ItemTouchHelper.Callback;
import java.util.Map.Entry;

public class ImageCache {
    private static ImageCache sInstance;
    private ArrayMap<String, Bitmap> mBitmapHashMap = new ArrayMap();

    public static synchronized ImageCache getInstance() {
        ImageCache imageCache;
        synchronized (ImageCache.class) {
            if (sInstance == null) {
                sInstance = new ImageCache();
            }
            imageCache = sInstance;
        }
        return imageCache;
    }

    public synchronized void put(String str, Bitmap bitmap) {
        if (!this.mBitmapHashMap.containsKey(str)) {
            Bitmap bitmap2;
            if (bitmap == null) {
                bitmap2 = (Bitmap) this.mBitmapHashMap.remove(str);
                if (bitmap2 != null) {
                    bitmap2.recycle();
                }
            } else if (!bitmap.isRecycled()) {
                bitmap2 = (Bitmap) this.mBitmapHashMap.put(str, bitmap);
                if (bitmap2 != null) {
                    bitmap2.recycle();
                }
            }
            bitmap = new StringBuilder();
            bitmap.append("size=");
            bitmap.append(this.mBitmapHashMap.size());
            LogUtil.d("memory", bitmap.toString());
        }
    }

    public synchronized Bitmap get(String str) {
        Bitmap bitmap;
        bitmap = null;
        if (this.mBitmapHashMap.containsKey(str)) {
            bitmap = (Bitmap) this.mBitmapHashMap.get(str);
        }
        return bitmap;
    }

    public synchronized void clear() {
        LogUtil.d("memory", "");
        for (Entry entry : this.mBitmapHashMap.entrySet()) {
            if (entry.getValue() != null) {
                Bitmap bitmap = (Bitmap) entry.getValue();
                if (!bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            }
        }
        for (int i = 0; i < this.mBitmapHashMap.size(); i++) {
            this.mBitmapHashMap.removeAt(i);
        }
        this.mBitmapHashMap.clear();
    }

    public synchronized void cleanup() {
        if (this.mBitmapHashMap != null && this.mBitmapHashMap.size() > Callback.DEFAULT_SWIPE_ANIMATION_DURATION) {
            for (int i = 125; i < this.mBitmapHashMap.size(); i++) {
                ((Bitmap) this.mBitmapHashMap.valueAt(i)).recycle();
                this.mBitmapHashMap.removeAt(i);
            }
        }
    }
}
