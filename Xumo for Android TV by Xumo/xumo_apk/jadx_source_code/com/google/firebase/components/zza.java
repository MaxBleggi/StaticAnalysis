package com.google.firebase.components;

/* compiled from: com.google.firebase:firebase-common@@16.0.2 */
abstract class zza implements ComponentContainer {
    zza() {
    }

    public <T> T get(Class<T> cls) {
        cls = getProvider(cls);
        if (cls == null) {
            return null;
        }
        return cls.get();
    }
}
