package sk.simondorociak.weather.android.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public class WeatherRequestInfo {

    @JsonProperty("query")
    private String query;
    @JsonProperty("type")
    private String type;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
