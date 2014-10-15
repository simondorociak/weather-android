package sk.simondorociak.weather.android.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import sk.simondorociak.weather.android.app.AppController;

/**
 * Helper util class used for work with SharedPreferences.
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public final class PreferenceUtils {

    /**
     * Disable class instantiation.
     */
    private PreferenceUtils() { }

    /**
     * Returns string preference value of specified preference key.
     * @param key key of preference
     * @param defValue default value to return if preference is not found
     * @return
     */
    public static String getString(final String key, final String defValue) {
        final SharedPreferences prefs = getDefaultSharedPreferences();
        if (prefs != null) {
            return prefs.getString(key, defValue);
        }
        return defValue;
    }

    /**
     *
     * @param key key of preference
     * @param value value of preference
     */
    public static void putDouble(final String key, double value) {
        final SharedPreferences prefs = getDefaultSharedPreferences();
        if (prefs != null) {
            prefs.edit().putLong(key, Double.doubleToRawLongBits(value)).apply();
        }
    }

    /**
     *
     * @param key key of preference
     * @return
     */
    public static double getDouble(final String key) {
        final SharedPreferences prefs = getDefaultSharedPreferences();
        if (prefs != null) {
            return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(-1d)));
        }
        return -1d;
    }

    /**
     * Returns DefaultSharedPreferences for application.
     * @return
     */
    private static SharedPreferences getDefaultSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(AppController.getContext());
    }
}
