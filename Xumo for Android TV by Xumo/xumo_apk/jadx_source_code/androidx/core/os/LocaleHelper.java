package androidx.core.os;

import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.Locale;

@RestrictTo({Scope.LIBRARY_GROUP})
final class LocaleHelper {
    static Locale forLanguageTag(String str) {
        String[] split;
        if (str.contains("-")) {
            split = str.split("-", -1);
            if (split.length > 2) {
                return new Locale(split[0], split[1], split[2]);
            }
            if (split.length > 1) {
                return new Locale(split[0], split[1]);
            }
            if (split.length == 1) {
                return new Locale(split[0]);
            }
        } else if (!str.contains(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR)) {
            return new Locale(str);
        } else {
            split = str.split(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR, -1);
            if (split.length > 2) {
                return new Locale(split[0], split[1], split[2]);
            }
            if (split.length > 1) {
                return new Locale(split[0], split[1]);
            }
            if (split.length == 1) {
                return new Locale(split[0]);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Can not parse language tag: [");
        stringBuilder.append(str);
        stringBuilder.append("]");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    static String toLanguageTag(Locale locale) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(locale.getLanguage());
        String country = locale.getCountry();
        if (!(country == null || country.isEmpty())) {
            stringBuilder.append("-");
            stringBuilder.append(locale.getCountry());
        }
        return stringBuilder.toString();
    }

    private LocaleHelper() {
    }
}
