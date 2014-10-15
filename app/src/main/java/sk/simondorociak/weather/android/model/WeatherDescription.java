package sk.simondorociak.weather.android.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public class WeatherDescription {

    @JsonProperty("value")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
