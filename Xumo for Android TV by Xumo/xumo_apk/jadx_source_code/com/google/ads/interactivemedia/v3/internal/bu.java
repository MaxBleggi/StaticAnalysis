package com.google.ads.interactivemedia.v3.internal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/* compiled from: IMASDK */
public interface bu {

    /* compiled from: IMASDK */
    public static final class b {
        public final String a;
        public final byte[] b;

        public b(String str, byte[] bArr) {
            this.a = (String) fe.a((Object) str);
            this.b = (byte[]) fe.a((Object) bArr);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof b)) {
                return false;
            }
            boolean z = true;
            if (obj == this) {
                return true;
            }
            b bVar = (b) obj;
            if (!this.a.equals(bVar.a) || Arrays.equals(this.b, bVar.b) == null) {
                z = false;
            }
            return z;
        }

        public int hashCode() {
            return this.a.hashCode() + (Arrays.hashCode(this.b) * 31);
        }
    }

    /* compiled from: IMASDK */
    public static final class a implements bu {
        private final Map<UUID, b> a = new HashMap();

        public void a(UUID uuid, b bVar) {
            this.a.put(uuid, bVar);
        }

        public boolean equals(Object obj) {
            if (obj != null) {
                if (getClass() == obj.getClass()) {
                    a aVar = (a) obj;
                    if (this.a.size() != aVar.a.size()) {
                        return false;
                    }
                    for (UUID uuid : this.a.keySet()) {
                        if (!ft.a(this.a.get(uuid), aVar.a.get(uuid))) {
                            return false;
                        }
                    }
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return this.a.hashCode();
        }
    }

    /* compiled from: IMASDK */
    public static final class c implements bu {
        private b a;

        public c(b bVar) {
            this.a = bVar;
        }

        public boolean equals(Object obj) {
            if (obj != null) {
                if (getClass() == obj.getClass()) {
                    return ft.a(this.a, ((c) obj).a);
                }
            }
            return null;
        }

        public int hashCode() {
            return this.a.hashCode();
        }
    }
}
