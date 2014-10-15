package sk.simondorociak.weather.android.util;

import android.util.Log;

/**
 * Helper util class used for application logging.
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public final class L {

    private static final String TAG_INFO = "Info";

    /**
     * Disable class instantiation.
     */
    private L() { }

    /**
     * Logs info message with default tag.
     * @param message message to log
     */
    public static void info(final String message) {
        Log.i(TAG_INFO, message);
    }

    /**
     * Logs info message with specified tag.
     * @param tag tag of message to log
     * @param message message to log
     */
    public static void info(final String tag, final String message) {
        Log.i(tag, message);
    }
}
