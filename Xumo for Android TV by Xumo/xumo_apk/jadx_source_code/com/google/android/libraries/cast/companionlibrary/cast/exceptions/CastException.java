package com.google.android.libraries.cast.companionlibrary.cast.exceptions;

import android.content.Context;

public class CastException extends Exception {
    private static final long serialVersionUID = 1;

    public CastException(String str, Throwable th) {
        super(str, th);
    }

    public CastException(String str) {
        super(str);
    }

    public CastException(Context context, int i) {
        super(context.getResources().getString(i));
    }

    public CastException(Context context, int i, Exception exception) {
        super(context.getResources().getString(i), exception);
    }

    public CastException(Throwable th) {
        super(th);
    }
}
