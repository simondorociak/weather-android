package sk.simondorociak.weather.android.client.request;

import android.net.Uri;

import com.android.volley.Response;

import sk.simondorociak.weather.android.client.Params;
import sk.simondorociak.weather.android.client.response.WeatherResponse;
import sk.simondorociak.weather.android.commons.Endpoints;

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public class WeatherRequest extends JsonRequest<WeatherResponse> {

    /**
     *
     * @param today true if forecast should be only for today, false otherwise
     * @param lat current latitude of device
     * @param lon current longitude of device
     * @param listener listener called when this request will succeed
     * @param errorListener listener called when this request will fail
     */
    public WeatherRequest(final boolean today, final double lat, final double lon,
                          final Response.Listener<WeatherResponse> listener,
                          final Response.ErrorListener errorListener) {
        super(Method.GET, getURL(Endpoints.WEATHER_URL, today, lat, lon), listener, errorListener, WeatherResponse.class);
    }

    /**
     *
     * @param url url of request
     * @param today true if forecast should be only for today, false otherwise
     * @param lat current latitude of device
     * @param lon current longitude of device
     * @return formatted URL as String
     */
    private static String getURL(final String url, final boolean today, final double lat, final double lon) {
        Uri.Builder builder = Uri.parse(url).buildUpon();
        builder.appendQueryParameter(Params.GPS_LOCATION, String.format(Params.GPS_LOCATION_VALUE, lat, lon));
        if (today) {
            builder.appendQueryParameter(Params.DATE, Params.DATE_TODAY);
        }
        else {
            builder.appendQueryParameter(Params.DAYS_COUNT, Params.DEFAULT_DAYS_COUNT);
        }
        return builder.toString();
    }
}
