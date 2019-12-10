package com.crashlytics.android.core;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.settings.SettingsData;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.atomic.AtomicBoolean;

class CrashlyticsUncaughtExceptionHandler implements UncaughtExceptionHandler {
    private final CrashListener crashListener;
    private final UncaughtExceptionHandler defaultHandler;
    private final boolean firebaseCrashlyticsClientFlag;
    private final AtomicBoolean isHandlingException = new AtomicBoolean(null);
    private final SettingsDataProvider settingsDataProvider;

    interface CrashListener {
        void onUncaughtException(SettingsDataProvider settingsDataProvider, Thread thread, Throwable th, boolean z);
    }

    interface SettingsDataProvider {
        SettingsData getSettingsData();
    }

    public CrashlyticsUncaughtExceptionHandler(CrashListener crashListener, SettingsDataProvider settingsDataProvider, boolean z, UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.crashListener = crashListener;
        this.settingsDataProvider = settingsDataProvider;
        this.firebaseCrashlyticsClientFlag = z;
        this.defaultHandler = uncaughtExceptionHandler;
    }

    public void uncaughtException(Thread thread, Throwable th) {
        this.isHandlingException.set(true);
        try {
            this.crashListener.onUncaughtException(this.settingsDataProvider, thread, th, this.firebaseCrashlyticsClientFlag);
        } catch (Throwable e) {
            Fabric.getLogger().e(CrashlyticsCore.TAG, "An error occurred in the uncaught exception handler", e);
        } catch (Throwable th2) {
            Fabric.getLogger().d(CrashlyticsCore.TAG, "Crashlytics completed exception processing. Invoking default exception handler.");
            this.defaultHandler.uncaughtException(thread, th);
            this.isHandlingException.set(false);
        }
        Fabric.getLogger().d(CrashlyticsCore.TAG, "Crashlytics completed exception processing. Invoking default exception handler.");
        this.defaultHandler.uncaughtException(thread, th);
        this.isHandlingException.set(false);
    }

    boolean isHandlingException() {
        return this.isHandlingException.get();
    }
}
