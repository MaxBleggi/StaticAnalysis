package com.crashlytics.android.core;

import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

class Sha1FileIdStrategy implements FileIdStrategy {
    Sha1FileIdStrategy() {
    }

    public String createId(File file) throws IOException {
        return getFileSHA(file.getPath());
    }

    private static String getFileSHA(String str) throws IOException {
        Closeable closeable = null;
        try {
            InputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(str));
            try {
                str = CommonUtils.sha1(bufferedInputStream);
                CommonUtils.closeQuietly(bufferedInputStream);
                return str;
            } catch (Throwable th) {
                str = th;
                closeable = bufferedInputStream;
                CommonUtils.closeQuietly(closeable);
                throw str;
            }
        } catch (Throwable th2) {
            str = th2;
            CommonUtils.closeQuietly(closeable);
            throw str;
        }
    }
}
