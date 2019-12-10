package com.google.ads.interactivemedia.v3.internal;

/* compiled from: IMASDK */
public abstract class kp {

    /* compiled from: IMASDK */
    static abstract class a extends kp {
        a() {
        }
    }

    /* compiled from: IMASDK */
    static abstract class b extends a {
        private final String a;

        b(String str) {
            this.a = (String) kv.a(str);
        }

        public final String toString() {
            return this.a;
        }
    }

    /* compiled from: IMASDK */
    static final class c extends b {
        static final int a = Integer.numberOfLeadingZeros(" 　\r   　 \u000b　   　 \t     \f 　 　　 \n 　".length() - 1);
        static final c b = new c();

        c() {
            super("CharMatcher.whitespace()");
        }

        public boolean a(char c) {
            return " 　\r   　 \u000b　   　 \t     \f 　 　　 \n 　".charAt((1682554634 * c) >>> a) == c;
        }
    }

    public static kp a() {
        return c.b;
    }

    public abstract boolean a(char c);

    protected kp() {
    }

    public boolean a(CharSequence charSequence) {
        for (int length = charSequence.length() - 1; length >= 0; length--) {
            if (!a(charSequence.charAt(length))) {
                return null;
            }
        }
        return true;
    }

    public String toString() {
        return super.toString();
    }
}
