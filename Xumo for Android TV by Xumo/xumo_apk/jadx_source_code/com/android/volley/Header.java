package com.android.volley;

import android.text.TextUtils;

public final class Header {
    private final String mName;
    private final String mValue;

    public Header(String str, String str2) {
        this.mName = str;
        this.mValue = str2;
    }

    public final String getName() {
        return this.mName;
    }

    public final String getValue() {
        return this.mValue;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj != null) {
            if (getClass() == obj.getClass()) {
                Header header = (Header) obj;
                if (!TextUtils.equals(this.mName, header.mName) || TextUtils.equals(this.mValue, header.mValue) == null) {
                    z = false;
                }
                return z;
            }
        }
        return false;
    }

    public int hashCode() {
        return (this.mName.hashCode() * 31) + this.mValue.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Header[name=");
        stringBuilder.append(this.mName);
        stringBuilder.append(",value=");
        stringBuilder.append(this.mValue);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
