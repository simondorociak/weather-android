package sk.simondorociak.weather.android.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Simon on 12.10.2014.
 */
public class Region {

    @JsonProperty("value")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
