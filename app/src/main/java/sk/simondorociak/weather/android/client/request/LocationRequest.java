package sk.simondorociak.weather.android.client.request;

import android.net.Uri;

import com.android.volley.Response;

import sk.simondorociak.weather.android.client.Params;
import sk.simondorociak.weather.android.client.response.SearchResponse;
import sk.simondorociak.weather.android.commons.Endpoints;

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public class LocationRequest extends JsonRequest<SearchResponse> {

    /**
     *
     * @param lat current latitude of device
     * @param lon current longitude of device
     * @param listener request success listener
     * @param errorListener request error listener
     */
    public LocationRequest(final double lat, final double lon,
                          final Response.Listener<SearchResponse> listener,
                          final Response.ErrorListener errorListener) {
        super(Method.GET, getURL(Endpoints.SEARCH_URL, lat, lon), listener, errorListener, SearchResponse.class);
    }

    /**
     *
     * @param url url of request
     * @param lat current latitude of device
     * @param lon current longitude of device
     * @return formatted URL as String
     */
    private static String getURL(final String url, final double lat, final double lon) {
        Uri.Builder builder = Uri.parse(url).buildUpon();
        builder.appendQueryParameter(Params.GPS_LOCATION, String.format(Params.GPS_LOCATION_VALUE, lat, lon));
        builder.appendQueryParameter(Params.RESULTS_COUNT, Params.DEFAULT_RESULTS_COUNT);
        return builder.toString();
    }
}
