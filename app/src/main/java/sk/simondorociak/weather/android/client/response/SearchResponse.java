package sk.simondorociak.weather.android.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import sk.simondorociak.weather.android.model.WeatherLocation;
import sk.simondorociak.weather.android.model.LocationDataContainer;

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public class SearchResponse {

    @JsonProperty("search_api")
    private LocationDataContainer data;

    /**
     * Gets current device location data object.
     * @return current device location
     */
    public WeatherLocation getWeatherLocation() {
        if (data != null && data.getWeatherLocations().size() > 0) {
            return data.getWeatherLocations().get(0);
        }
        return null;
    }

    public LocationDataContainer getData() {
        return data;
    }

    public void setData(LocationDataContainer data) {
        this.data = data;
    }
}
