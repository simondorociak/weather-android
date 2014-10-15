package sk.simondorociak.weather.android.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public class WeatherLocation {

    @JsonProperty("areaName")
    private List<Area> areas = new ArrayList<Area>();
    @JsonProperty("country")
    private List<Country> countries = new ArrayList<Country>();
    @JsonProperty("latitude")
    private String latitude;
    @JsonProperty("longitude")
    private String longitude;
    @JsonProperty("population")
    private String population;
    @JsonProperty("region")
    private List<Region> regions = new ArrayList<Region>();
    @JsonProperty("weatherUrl")
    private List<Object> weatherUrls = new ArrayList<Object>();

    /**
     * Gets area name of this location.
     * @return
     */
    public String getAreaName() {
        if (areas.size() > 0) {
            return areas.get(0).getValue();
        }
        return "";
    }

    /**
     * Get country name of this location.
     * @return
     */
    public String getCountryName() {
        if (countries.size() > 0) {
            return countries.get(0).getValue();
        }
        return "";
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    public List<Object> getWeatherUrls() {
        return weatherUrls;
    }

    public void setWeatherUrls(List<Object> weatherUrls) {
        this.weatherUrls = weatherUrls;
    }
}
