package com.google.ads.interactivemedia.v3.internal;

/* compiled from: IMASDK */
final class ip extends a {
    private final String TXXX;

    ip(String str) {
        if (str != null) {
            this.TXXX = str;
            return;
        }
        throw new NullPointerException("Null TXXX");
    }

    String TXXX() {
        return this.TXXX;
    }

    public String toString() {
        String str = this.TXXX;
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 28);
        stringBuilder.append("TimedMetadataWithKeys{TXXX=");
        stringBuilder.append(str);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof a)) {
            return null;
        }
        return this.TXXX.equals(((a) obj).TXXX());
    }

    public int hashCode() {
        return this.TXXX.hashCode() ^ 1000003;
    }
}
