package sk.simondorociak.weather.android.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Util class used for working with dates.
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public final class DateUtils {

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd";
    private static final String DAY_IN_WEEK_PATTERN = "EEEE";

    /**
     * Disable class instantiation.
     */
    private DateUtils() { }

    /**
     * Calculates and returns day in week in default locale.
     * @param dateString source date as String
     * @return
     */
    public static String getDayInWeek(final String dateString) {
        try {
            Date temp = new SimpleDateFormat(DEFAULT_PATTERN).parse(dateString);
            return new SimpleDateFormat(DAY_IN_WEEK_PATTERN, Locale.getDefault()).format(temp);
        }
        catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}
