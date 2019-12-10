package com.google.android.libraries.cast.companionlibrary.cast.exceptions;

import java.io.IOException;

public class NoConnectionException extends IOException {
    public NoConnectionException(Throwable th) {
        super(th);
    }

    public NoConnectionException(String str, Throwable th) {
        super(str, th);
    }
}
