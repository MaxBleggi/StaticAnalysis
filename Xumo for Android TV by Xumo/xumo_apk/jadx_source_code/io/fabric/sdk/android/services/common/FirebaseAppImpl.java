package io.fabric.sdk.android.services.common;

import io.fabric.sdk.android.Fabric;
import java.lang.reflect.Method;

final class FirebaseAppImpl implements FirebaseApp {
    private static final String FIREBASE_APP_CLASS = "com.google.firebase.FirebaseApp";
    private static final String GET_INSTANCE_METHOD = "getInstance";
    private static final String IS_DATA_COLLECTION_ENABLED_METHOD = "isDataCollectionDefaultEnabled";
    private final Object firebaseAppInstance;
    private final Method isDataCollectionDefaultEnabledMethod;

    public static io.fabric.sdk.android.services.common.FirebaseApp getInstance(android.content.Context r4) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r4 = r4.getClassLoader();	 Catch:{ ClassNotFoundException -> 0x004c, NoSuchMethodException -> 0x002c, Exception -> 0x001f }
        r0 = "com.google.firebase.FirebaseApp";	 Catch:{ ClassNotFoundException -> 0x004c, NoSuchMethodException -> 0x002c, Exception -> 0x001f }
        r4 = r4.loadClass(r0);	 Catch:{ ClassNotFoundException -> 0x004c, NoSuchMethodException -> 0x002c, Exception -> 0x001f }
        r0 = "getInstance";	 Catch:{ ClassNotFoundException -> 0x004c, NoSuchMethodException -> 0x002c, Exception -> 0x001f }
        r1 = 0;	 Catch:{ ClassNotFoundException -> 0x004c, NoSuchMethodException -> 0x002c, Exception -> 0x001f }
        r2 = new java.lang.Class[r1];	 Catch:{ ClassNotFoundException -> 0x004c, NoSuchMethodException -> 0x002c, Exception -> 0x001f }
        r0 = r4.getDeclaredMethod(r0, r2);	 Catch:{ ClassNotFoundException -> 0x004c, NoSuchMethodException -> 0x002c, Exception -> 0x001f }
        r1 = new java.lang.Object[r1];	 Catch:{ ClassNotFoundException -> 0x004c, NoSuchMethodException -> 0x002c, Exception -> 0x001f }
        r0 = r0.invoke(r4, r1);	 Catch:{ ClassNotFoundException -> 0x004c, NoSuchMethodException -> 0x002c, Exception -> 0x001f }
        r1 = new io.fabric.sdk.android.services.common.FirebaseAppImpl;	 Catch:{ ClassNotFoundException -> 0x004c, NoSuchMethodException -> 0x002c, Exception -> 0x001f }
        r1.<init>(r4, r0);	 Catch:{ ClassNotFoundException -> 0x004c, NoSuchMethodException -> 0x002c, Exception -> 0x001f }
        return r1;
    L_0x001f:
        r4 = move-exception;
        r0 = io.fabric.sdk.android.Fabric.getLogger();
        r1 = "Fabric";
        r2 = "Unexpected error loading FirebaseApp instance.";
        r0.d(r1, r2, r4);
        goto L_0x0057;
    L_0x002c:
        r4 = move-exception;
        r0 = io.fabric.sdk.android.Fabric.getLogger();
        r1 = "Fabric";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "Could not find method: ";
        r2.append(r3);
        r4 = r4.getMessage();
        r2.append(r4);
        r4 = r2.toString();
        r0.d(r1, r4);
        goto L_0x0057;
    L_0x004c:
        r4 = io.fabric.sdk.android.Fabric.getLogger();
        r0 = "Fabric";
        r1 = "Could not find class: com.google.firebase.FirebaseApp";
        r4.d(r0, r1);
    L_0x0057:
        r4 = 0;
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.fabric.sdk.android.services.common.FirebaseAppImpl.getInstance(android.content.Context):io.fabric.sdk.android.services.common.FirebaseApp");
    }

    private FirebaseAppImpl(Class cls, Object obj) throws NoSuchMethodException {
        this.firebaseAppInstance = obj;
        this.isDataCollectionDefaultEnabledMethod = cls.getDeclaredMethod(IS_DATA_COLLECTION_ENABLED_METHOD, new Class[0]);
    }

    public boolean isDataCollectionDefaultEnabled() {
        try {
            return ((Boolean) this.isDataCollectionDefaultEnabledMethod.invoke(this.firebaseAppInstance, new Object[0])).booleanValue();
        } catch (Throwable e) {
            Fabric.getLogger().d(Fabric.TAG, "Cannot check isDataCollectionDefaultEnabled on FirebaseApp.", e);
            return false;
        }
    }
}
