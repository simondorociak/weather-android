package sk.simondorociak.weather.android.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import sk.simondorociak.weather.android.model.Weather;
import sk.simondorociak.weather.android.model.WeatherDataContainer;

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public class WeatherResponse {

    @JsonProperty("data")
    private WeatherDataContainer data;

    /**
     * Returns weather forecast(s).
     * @return
     */
    public List<Weather> getWeather() {
        return data != null ? data.getWeather() : null;
    }

    /**
     * Returns today forecast.
     * @return
     */
    public Weather getTodayWeather() {
        // if all required weather data is available
        if (data != null && data.getCurrentCondition().size() > 0 && data.getWeather().size() > 0)  {
            Weather weather = data.getWeather().get(0);

            // add extra weather information into weather object
            // because weather API is a bit "chopped" ...
            Weather weatherInfo = data.getCurrentCondition().get(0);
            weather.setHumidity(weatherInfo.getHumidity());
            weather.setPrecipitation(weatherInfo.getPrecipitation());
            weather.setPressure(weatherInfo.getPressure());

            return weather;
        }
        return null;
    }
}
