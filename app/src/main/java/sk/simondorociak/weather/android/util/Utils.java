package sk.simondorociak.weather.android.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import sk.simondorociak.weather.R;
import sk.simondorociak.weather.android.app.AppController;
import sk.simondorociak.weather.android.model.Weather;

/**
 * Helper util class that does not match to any category.
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public final class Utils {

    private static final int UNIT_KM_TO_M = 1000;

    /**
     * Disable class instantiation.
     */
    private Utils() { }

    /**
     * Checks whether device is online or not.
     * @param context current Context
     * @return true if device is online, false otherwise
     */
    public static boolean isDeviceOnline(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * Formats provided temperature and returns it.
     * @param weather current weather data
     * @param celsius true if celsius unit is activated, false otherwise
     * @return
     */
    public static String getFormattedTemperature(final Weather weather, boolean celsius) {
        final Context context = AppController.getContext();
        if (celsius) {
            return context.getString(R.string.weather_temperature_celsius, weather.getMaxTemperatureCelsius());
        }
        else {
            return context.getString(R.string.weather_temperature_fahrenheit, weather.getMaxTemperatureFahrenheit());
        }
    }

    /**
     * Formats provided wind speed and returns it.
     * @param weather current weather information data
     * @param meters true if m unit is activated, false otherwise
     * @return formatted wind speed as String
     */
    public static String getFormattedWindSpeed(final Weather weather, boolean meters) {
        final Context context = AppController.getContext();
        if (meters) {
            try {
                double value = Double.parseDouble(weather.getWindSpeedInKilometers()) * UNIT_KM_TO_M;
                return context.getString(R.string.weather_wind_speed_m, formatDouble(value));
            }
            catch (NumberFormatException e) {
                L.info("Unable to parse weather wind speed into double");
                return String.valueOf(0);
            }
        }
        else {
            return context.getString(R.string.weather_wind_speed_miles, weather.getGetWindSpeedInMiles());
        }
    }

    /**
     * Formats double with minimal necessary precision.
     * @param d double value to format
     * @return formatted double as String
     */
    private static String formatDouble(final double d) {
        if (d == (long) d) {
            return String.format("%d", (long) d);
        }
        else {
            return String.format("%s", d);
        }
    }

    /**
     * Checks whether celsius unit is activated or not.
     * @return true if celsius unit is activated, false otherwise
     */
    public static boolean isCelsiusActivated() {
        final Context context = AppController.getContext();
        String key = context.getString(R.string.pref_key_temperature_unit);
        String defValue = context.getString(R.string.id_pref_celsius_value);
        String selectedUnit = PreferenceUtils.getString(key, defValue);
        return selectedUnit.equals(defValue);
    }

    /**
     * Checks whether km unit is activated or not.
     * @return true if km unit is activated, false otherwise
     */
    public static boolean isKilometersActivated() {
        final Context context = AppController.getContext();
        String key = context.getString(R.string.pref_key_length_unit);
        String defValue = context.getString(R.string.id_pref_meters_value);
        String selectedUnit = PreferenceUtils.getString(key, defValue);
        return selectedUnit.equals(defValue);
    }

    /**
     * Shows provided view(s).
     * @param views view(s) to show
     */
    public static void showView(final View... views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Hides provided view(s)
     * @param views view(s) to hide
     */
    public static void hideView(final View... views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }
}
