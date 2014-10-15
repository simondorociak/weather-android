package sk.simondorociak.weather.android.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public class WeatherDataContainer {

    @JsonProperty("current_condition")
    private List<Weather> currentCondition = new ArrayList<Weather>();
    @JsonProperty("request")
    private List<WeatherRequestInfo> info = new ArrayList<WeatherRequestInfo>();
    @JsonProperty("weather")
    private List<Weather> weather = new ArrayList<Weather>();

    public List<Weather> getCurrentCondition() {
        return currentCondition;
    }

    public void setCurrentCondition(List<Weather> currentCondition) {
        this.currentCondition = currentCondition;
    }

    public List<WeatherRequestInfo> getInfo() {
        return info;
    }

    public void setInfo(List<WeatherRequestInfo> info) {
        this.info = info;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }
}
