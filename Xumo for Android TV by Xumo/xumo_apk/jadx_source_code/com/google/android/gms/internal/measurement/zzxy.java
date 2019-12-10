package com.google.android.gms.internal.measurement;

import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

final class zzxy extends zzxx<FieldDescriptorType, Object> {
    zzxy(int i) {
        super(i);
    }

    public final void zzsw() {
        if (!isImmutable()) {
            Entry zzbw;
            for (int i = 0; i < zzyj(); i++) {
                zzbw = zzbw(i);
                if (((zzvq) zzbw.getKey()).zzwi()) {
                    zzbw.setValue(Collections.unmodifiableList((List) zzbw.getValue()));
                }
            }
            for (Entry zzbw2 : zzyk()) {
                if (((zzvq) zzbw2.getKey()).zzwi()) {
                    zzbw2.setValue(Collections.unmodifiableList((List) zzbw2.getValue()));
                }
            }
        }
        super.zzsw();
    }
}
