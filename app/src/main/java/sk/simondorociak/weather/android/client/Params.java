package sk.simondorociak.weather.android.client;

/**
 * Helper class used for holding and providing http parameter names used by Weather API.
 * @see <a href="http://www.worldweatheronline.com/free-weather-feed.aspx">World Weather Online Feed API</a>
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public final class Params {

    public static final String GPS_LOCATION = "q";
    public static final String DATE = "date";
    public static final String DAYS_COUNT = "num_of_days";
    public static final String RESULTS_COUNT = "num_of_results";

    // default values
    public static final String DATE_TODAY = "today";
    public static final String DEFAULT_DAYS_COUNT = "5";
    public static final String DEFAULT_RESULTS_COUNT = "1";
    public static final String GPS_LOCATION_VALUE = "%f,%f";

    /**
     * Disable class instantiation.
     */
    private Params() { }
}
