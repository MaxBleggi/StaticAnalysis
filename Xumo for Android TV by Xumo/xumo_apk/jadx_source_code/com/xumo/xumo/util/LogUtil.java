package com.xumo.xumo.util;

import android.util.Log;
import java.util.regex.Pattern;

public class LogUtil {
    public static final String CCL = "CCL";
    private static final int DEBUG = 3;
    private static final int ERROR = 6;
    private static final int INFO = 4;
    private static final int MAX_ENABLED_LOG_LEVEL = 2;
    private static final String TAG = "xumo";
    private static final int VERBOSE = 2;
    private static final int WARN = 5;

    private static boolean isEnable(int i) {
        return false;
    }

    private LogUtil() {
    }

    public static void v(String str) {
        if (isEnable(2)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getClassInfo());
            stringBuilder.append(" ");
            stringBuilder.append(str);
            Log.v(TAG, stringBuilder.toString());
        }
    }

    public static void v(String str, String str2) {
        if (isEnable(2)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getClassInfo());
            stringBuilder.append(" ");
            stringBuilder.append(str2);
            Log.v(str, stringBuilder.toString());
        }
    }

    public static void v(String str, Throwable th) {
        if (isEnable(2)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getClassInfo());
            stringBuilder.append(" ");
            stringBuilder.append(str);
            Log.v(TAG, stringBuilder.toString(), th);
        }
    }

    public static void v(String str, String str2, Throwable th) {
        if (isEnable(2)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getClassInfo());
            stringBuilder.append(" ");
            stringBuilder.append(str2);
            Log.v(str, stringBuilder.toString(), th);
        }
    }

    public static void d(String str) {
        if (isEnable(3)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getClassInfo());
            stringBuilder.append(" ");
            stringBuilder.append(str);
            Log.d(TAG, stringBuilder.toString());
        }
    }

    public static void d(String str, String str2) {
        if (isEnable(3)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getClassInfo());
            stringBuilder.append(" ");
            stringBuilder.append(str2);
            Log.d(str, stringBuilder.toString());
        }
    }

    public static void d(String str, Throwable th) {
        if (isEnable(3)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getClassInfo());
            stringBuilder.append(" ");
            stringBuilder.append(str);
            Log.d(TAG, stringBuilder.toString(), th);
        }
    }

    public static void d(String str, String str2, Throwable th) {
        if (isEnable(3)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getClassInfo());
            stringBuilder.append(" ");
            stringBuilder.append(str2);
            Log.d(str, stringBuilder.toString(), th);
        }
    }

    public static void i(String str) {
        if (isEnable(4)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getClassInfo());
            stringBuilder.append(" ");
            stringBuilder.append(str);
            Log.i(TAG, stringBuilder.toString());
        }
    }

    public static void i(String str, String str2) {
        if (isEnable(4)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getClassInfo());
            stringBuilder.append(" ");
            stringBuilder.append(str2);
            Log.i(str, stringBuilder.toString());
        }
    }

    public static void i(String str, Throwable th) {
        if (isEnable(4)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getClassInfo());
            stringBuilder.append(" ");
            stringBuilder.append(str);
            Log.i(TAG, stringBuilder.toString(), th);
        }
    }

    public static void i(String str, String str2, Throwable th) {
        if (isEnable(4)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getClassInfo());
            stringBuilder.append(" ");
            stringBuilder.append(str2);
            Log.i(str, stringBuilder.toString(), th);
        }
    }

    public static void w(String str) {
        if (isEnable(5)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getClassInfo());
            stringBuilder.append(" ");
            stringBuilder.append(str);
            Log.w(TAG, stringBuilder.toString());
        }
    }

    public static void w(String str, String str2) {
        if (isEnable(5)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getClassInfo());
            stringBuilder.append(" ");
            stringBuilder.append(str2);
            Log.w(str, stringBuilder.toString());
        }
    }

    public static void w(String str, Throwable th) {
        if (isEnable(5)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getClassInfo());
            stringBuilder.append(" ");
            stringBuilder.append(str);
            Log.w(TAG, stringBuilder.toString(), th);
        }
    }

    public static void w(String str, String str2, Throwable th) {
        if (isEnable(5)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getClassInfo());
            stringBuilder.append(" ");
            stringBuilder.append(str2);
            Log.w(str, stringBuilder.toString(), th);
        }
    }

    public static void e(String str) {
        if (isEnable(6)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getClassInfo());
            stringBuilder.append(" ");
            stringBuilder.append(str);
            Log.i(TAG, stringBuilder.toString());
        }
    }

    public static void e(String str, String str2) {
        if (isEnable(6)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getClassInfo());
            stringBuilder.append(" ");
            stringBuilder.append(str2);
            Log.e(str, stringBuilder.toString());
        }
    }

    public static void e(String str, Throwable th) {
        if (isEnable(6)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getClassInfo());
            stringBuilder.append(" ");
            stringBuilder.append(str);
            Log.e(TAG, stringBuilder.toString(), th);
        }
    }

    public static void e(String str, String str2, Throwable th) {
        if (isEnable(6)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getClassInfo());
            stringBuilder.append(" ");
            stringBuilder.append(str2);
            Log.e(str, stringBuilder.toString(), th);
        }
    }

    public static void wtf(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClassInfo());
        stringBuilder.append(" ");
        stringBuilder.append(str);
        Log.wtf(TAG, stringBuilder.toString());
    }

    public static void wtf(String str, String str2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClassInfo());
        stringBuilder.append(" ");
        stringBuilder.append(str2);
        Log.wtf(str, stringBuilder.toString());
    }

    public static void wtf(String str, Throwable th) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClassInfo());
        stringBuilder.append(" ");
        stringBuilder.append(str);
        Log.wtf(TAG, stringBuilder.toString(), th);
    }

    public static void wtf(String str, String str2, Throwable th) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClassInfo());
        stringBuilder.append(" ");
        stringBuilder.append(str2);
        Log.wtf(str, stringBuilder.toString(), th);
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
