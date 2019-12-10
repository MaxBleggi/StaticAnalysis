package com.google.android.gms.internal.measurement;

final class zzyi {
    static String zzd(zzun com_google_android_gms_internal_measurement_zzun) {
        zzyk com_google_android_gms_internal_measurement_zzyj = new zzyj(com_google_android_gms_internal_measurement_zzun);
        com_google_android_gms_internal_measurement_zzun = new StringBuilder(com_google_android_gms_internal_measurement_zzyj.size());
        for (int i = 0; i < com_google_android_gms_internal_measurement_zzyj.size(); i++) {
            byte zzal = com_google_android_gms_internal_measurement_zzyj.zzal(i);
            if (zzal == (byte) 34) {
                com_google_android_gms_internal_measurement_zzun.append("\\\"");
            } else if (zzal == (byte) 39) {
                com_google_android_gms_internal_measurement_zzun.append("\\'");
            } else if (zzal != (byte) 92) {
                switch (zzal) {
                    case (byte) 7:
                        com_google_android_gms_internal_measurement_zzun.append("\\a");
                        break;
                    case (byte) 8:
                        com_google_android_gms_internal_measurement_zzun.append("\\b");
                        break;
                    case (byte) 9:
                        com_google_android_gms_internal_measurement_zzun.append("\\t");
                        break;
                    case (byte) 10:
                        com_google_android_gms_internal_measurement_zzun.append("\\n");
                        break;
                    case (byte) 11:
                        com_google_android_gms_internal_measurement_zzun.append("\\v");
                        break;
                    case (byte) 12:
                        com_google_android_gms_internal_measurement_zzun.append("\\f");
                        break;
                    case (byte) 13:
                        com_google_android_gms_internal_measurement_zzun.append("\\r");
                        break;
                    default:
                        if (zzal >= (byte) 32 && zzal <= (byte) 126) {
                            com_google_android_gms_internal_measurement_zzun.append((char) zzal);
                            break;
                        }
                        com_google_android_gms_internal_measurement_zzun.append('\\');
                        com_google_android_gms_internal_measurement_zzun.append((char) (((zzal >>> 6) & 3) + 48));
                        com_google_android_gms_internal_measurement_zzun.append((char) (((zzal >>> 3) & 7) + 48));
                        com_google_android_gms_internal_measurement_zzun.append((char) ((zzal & 7) + 48));
                        break;
                }
            } else {
                com_google_android_gms_internal_measurement_zzun.append("\\\\");
            }
        }
        return com_google_android_gms_internal_measurement_zzun.toString();
    }
}
