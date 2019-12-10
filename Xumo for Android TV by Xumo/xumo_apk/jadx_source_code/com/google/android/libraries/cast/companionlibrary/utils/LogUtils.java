package com.google.android.libraries.cast.companionlibrary.utils;

import android.util.Log;
import com.google.android.libraries.cast.companionlibrary.cast.BaseCastManager;
import java.util.regex.Pattern;

public class LogUtils {
    private static final String LOG_PREFIX = "ccl_";
    private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();
    private static final int MAX_LOG_TAG_LENGTH = 23;
    private static boolean sDebug = false;

    private LogUtils() {
    }

    public static String makeLogTag(String str) {
        if (str.length() > 23 - LOG_PREFIX_LENGTH) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(LOG_PREFIX);
            stringBuilder.append(str.substring(0, (23 - LOG_PREFIX_LENGTH) - 1));
            return stringBuilder.toString();
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(LOG_PREFIX);
        stringBuilder.append(str);
        return stringBuilder.toString();
    }

    public static String makeLogTag(Class<?> cls) {
        return makeLogTag(cls.getSimpleName());
    }

    public static void LOGD(String str, String str2) {
        if (sDebug || Log.isLoggable(str, 3)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getVersionPrefix());
            stringBuilder.append(str2);
            Log.d(str, stringBuilder.toString());
        }
    }

    public static void LOGD(String str, String str2, Throwable th) {
        if (sDebug || Log.isLoggable(str, 3)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getVersionPrefix());
            stringBuilder.append(str2);
            Log.d(str, stringBuilder.toString(), th);
        }
    }

    public static void LOGV(String str, String str2) {
        if (sDebug && Log.isLoggable(str, 2)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getVersionPrefix());
            stringBuilder.append(str2);
            Log.v(str, stringBuilder.toString());
        }
    }

    public static void LOGV(String str, String str2, Throwable th) {
        if (sDebug && Log.isLoggable(str, 2)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getVersionPrefix());
            stringBuilder.append(str2);
            Log.v(str, stringBuilder.toString(), th);
        }
    }

    public static void LOGI(String str, String str2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getVersionPrefix());
        stringBuilder.append(str2);
        Log.i(str, stringBuilder.toString());
    }

    public static void LOGI(String str, String str2, Throwable th) {
        Log.i(str, str2, th);
    }

    public static void LOGW(String str, String str2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getVersionPrefix());
        stringBuilder.append(str2);
        Log.w(str, stringBuilder.toString());
    }

    public static void LOGW(String str, String str2, Throwable th) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getVersionPrefix());
        stringBuilder.append(str2);
        Log.w(str, stringBuilder.toString(), th);
    }

    public static void LOGE(String str, String str2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getVersionPrefix());
        stringBuilder.append(str2);
        Log.e(str, stringBuilder.toString());
    }

    public static void LOGE(String str, String str2, Throwable th) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getVersionPrefix());
        stringBuilder.append(str2);
        Log.e(str, stringBuilder.toString(), th);
    }

    public static String getVersionPrefix() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[v");
        stringBuilder.append(BaseCastManager.getCclVersion());
        stringBuilder.append("] ");
        return stringBuilder.toString();
    }

    public static void setDebug(boolean z) {
        sDebug = z;
    }

    public static void d(String str, String str2) {
        if (sDebug || Log.isLoggable(str, 3)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getVersionPrefix());
            stringBuilder.append(getClassInfo());
            stringBuilder.append(str2);
            Log.d(str, stringBuilder.toString());
        }
    }

    public static void d(String str, String str2, Throwable th) {
        if (sDebug || Log.isLoggable(str, 3)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getVersionPrefix());
            stringBuilder.append(getClassInfo());
            stringBuilder.append(str2);
            Log.d(str, stringBuilder.toString(), th);
        }
    }

    private static String getClassInfo() {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[4];
        String[] split = Pattern.compile("&quot;[\\.]+&quot;").split(stackTraceElement.getClassName());
        String str = split[split.length - 1];
        String methodName = stackTraceElement.getMethodName();
        int lineNumber = stackTraceElement.getLineNumber();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append("#");
        stringBuilder.append(methodName);
        stringBuilder.append("(");
        stringBuilder.append(lineNumber);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
