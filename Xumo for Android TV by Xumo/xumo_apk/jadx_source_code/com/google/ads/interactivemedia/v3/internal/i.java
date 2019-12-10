package com.google.ads.interactivemedia.v3.internal;

/* compiled from: IMASDK */
public class i {
    private final String a;
    private final String b;

    private i(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    public static i a(String str, String str2) {
        af.a(str, "Name is null or empty");
        af.a(str2, "Version is null or empty");
        return new i(str, str2);
    }

    public String a() {
        return this.a;
    }

    public String b() {
        return this.b;
    }
}
