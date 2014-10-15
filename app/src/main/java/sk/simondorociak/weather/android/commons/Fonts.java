package sk.simondorociak.weather.android.commons;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.util.LruCache;

/**
 * Helper class for storing and providing fonts mostly used in application. For storing fonts
 * is used LruCache with default capacity of 5.
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public final class Fonts {

    private final static LruCache<String, Typeface> fonts = new LruCache<String, Typeface>(5);

    /**
     * Disable class instantiation.
     */
    private Fonts() { }

    /**
     * Gets font stored in cache. Loads it from assets if necessary.
     * @param context current Context
     * @param name name of font to return
     * @return
     */
    public static Typeface getFont(final Context context, final String name) {
        Typeface font = fonts.get(name);
        if (font == null) {
            font = Typeface.createFromAsset(context.getAssets(), name);
            fonts.put(name, font);
        }
        return font;
    }
}
