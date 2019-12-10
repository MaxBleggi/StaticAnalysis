package com.xumo.xumo.application;

import android.content.Context;

public class XumoContext {
    private static XumoContext sInstance;
    private Context mApplicationContext;

    static void onCreateApplicationContext(Context context) {
        sInstance = new XumoContext(context);
    }

    private XumoContext(Context context) {
        this.mApplicationContext = context;
    }

    public static XumoContext getInstance() {
        if (sInstance != null) {
            return sInstance;
        }
        throw new RuntimeException("XumoContext not initialized.");
    }

    public Context getApplicationContext() {
        return this.mApplicationContext;
    }
}
