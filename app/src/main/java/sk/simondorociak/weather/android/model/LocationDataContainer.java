package sk.simondorociak.weather.android.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public class LocationDataContainer {

    @JsonProperty("result")
    private List<WeatherLocation> locations = new ArrayList<WeatherLocation>();

    public List<WeatherLocation> getWeatherLocations() {
        return locations;
    }

    public void setWeatherLocations(List<WeatherLocation> locations) {
        this.locations = locations;
    }
}
