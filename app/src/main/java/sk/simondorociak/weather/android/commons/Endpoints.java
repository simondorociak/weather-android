package sk.simondorociak.weather.android.commons;

/**
 * Helper class used for holding and providing endpoints used by application.
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public final class Endpoints {

    // current api key
    private static final String API_KEY = "6507e602715b21f6ffcfcf309e5155d8c2de78ee";

    // base urls and params
    private static final String BASE_URL = "http://api.worldweatheronline.com/";
    private static final String BASE_PARAMS = "format=json&key=" + API_KEY;

    // service names
    private static final String WEATHER_SERVICE = "free/v1/weather.ashx?";
    private static final String SEARCH_SERVICE = "free/v1/search.ashx?";

    // full urls
    public static final String WEATHER_URL = BASE_URL + WEATHER_SERVICE + BASE_PARAMS;
    public static final String SEARCH_URL = BASE_URL + SEARCH_SERVICE + BASE_PARAMS;

    /**
     * Disable class instantiation.
     */
    private Endpoints() { }
}
