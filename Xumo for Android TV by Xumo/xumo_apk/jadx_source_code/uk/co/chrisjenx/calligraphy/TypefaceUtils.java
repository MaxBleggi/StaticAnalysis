package uk.co.chrisjenx.calligraphy;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;

public final class TypefaceUtils {
    private static final Map<String, Typeface> sCachedFonts = new HashMap();
    private static final Map<Typeface, CalligraphyTypefaceSpan> sCachedSpans = new HashMap();

    public static Typeface load(AssetManager assetManager, String str) {
        synchronized (sCachedFonts) {
            try {
                if (sCachedFonts.containsKey(str)) {
                    Typeface typeface = (Typeface) sCachedFonts.get(str);
                    return typeface;
                }
                assetManager = Typeface.createFromAsset(assetManager, str);
                sCachedFonts.put(str, assetManager);
                return assetManager;
            } catch (AssetManager assetManager2) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Can't create asset from ");
                stringBuilder.append(str);
                stringBuilder.append(". Make sure you have passed in the correct path and file name.");
                Log.w("Calligraphy", stringBuilder.toString(), assetManager2);
                sCachedFonts.put(str, null);
                return null;
            }
        }
    }

    public static CalligraphyTypefaceSpan getSpan(Typeface typeface) {
        if (typeface == null) {
            return null;
        }
        synchronized (sCachedSpans) {
            if (sCachedSpans.containsKey(typeface)) {
                CalligraphyTypefaceSpan calligraphyTypefaceSpan = (CalligraphyTypefaceSpan) sCachedSpans.get(typeface);
                return calligraphyTypefaceSpan;
            }
            CalligraphyTypefaceSpan calligraphyTypefaceSpan2 = new CalligraphyTypefaceSpan(typeface);
            sCachedSpans.put(typeface, calligraphyTypefaceSpan2);
            return calligraphyTypefaceSpan2;
        }
    }

    public static boolean isLoaded(Typeface typeface) {
        return (typeface == null || sCachedFonts.containsValue(typeface) == null) ? null : true;
    }

    private TypefaceUtils() {
    }
}
