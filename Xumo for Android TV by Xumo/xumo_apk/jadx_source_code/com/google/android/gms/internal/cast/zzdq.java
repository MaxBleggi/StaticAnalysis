package com.google.android.gms.internal.cast;

public final class zzdq {
    public static Integer zzu(String str) {
        if (str == null) {
            return null;
        }
        Object obj = -1;
        int hashCode = str.hashCode();
        if (hashCode != -1118317585) {
            if (hashCode != -962896020) {
                if (hashCode != 1645938909) {
                    if (hashCode == 1645952171) {
                        if (str.equals("REPEAT_OFF") != null) {
                            obj = null;
                        }
                    }
                } else if (str.equals("REPEAT_ALL") != null) {
                    obj = 1;
                }
            } else if (str.equals("REPEAT_SINGLE") != null) {
                obj = 2;
            }
        } else if (str.equals("REPEAT_ALL_AND_SHUFFLE") != null) {
            obj = 3;
        }
        switch (obj) {
            case null:
                return Integer.valueOf(0);
            case 1:
                return Integer.valueOf(1);
            case 2:
                return Integer.valueOf(2);
            case 3:
                return Integer.valueOf(3);
            default:
                return null;
        }
    }

    public static String zza(Integer num) {
        if (num == null) {
            return null;
        }
        switch (num.intValue()) {
            case null:
                return "REPEAT_OFF";
            case 1:
                return "REPEAT_ALL";
            case 2:
                return "REPEAT_SINGLE";
            case 3:
                return "REPEAT_ALL_AND_SHUFFLE";
            default:
                return null;
        }
    }
}
