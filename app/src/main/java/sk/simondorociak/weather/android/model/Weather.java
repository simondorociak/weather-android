package sk.simondorociak.weather.android.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import sk.simondorociak.weather.android.util.DateUtils;
import sk.simondorociak.weather.android.util.Utils;

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public class Weather {

    @JsonProperty("cloudcover")
    private String cloudCover;
    @JsonProperty("humidity")
    private String humidity;
    @JsonProperty("observation_time")
    private String observationTime;
    @JsonProperty("date")
    private String date;
    @JsonProperty("precipMM")
    private String precipitation;
    @JsonProperty("pressure")
    private String pressure;
    @JsonProperty("temp_C")
    private String temperatureCelsius;
    @JsonProperty("temp_F")
    private String temperatureFahrenheit;
    @JsonProperty("tempMaxC")
    private String maxTemperatureCelsius;
    @JsonProperty("tempMaxF")
    private String maxTemperatureFahrenheit;
    @JsonProperty("tempMinC")
    private String minTemperatureCelsius;
    @JsonProperty("tempMinF")
    private String minTemperatureFahrenheit;
    @JsonProperty("visibility")
    private String visibility;
    @JsonProperty("weatherCode")
    private String weatherCode;
    @JsonProperty("weatherDesc")
    private List<WeatherDescription> description = new ArrayList<WeatherDescription>();
    @JsonProperty("weatherIconUrl")
    private List<WeatherDescription> icons = new ArrayList<WeatherDescription>();
    @JsonProperty("winddir16Point")
    private String windDirectionPoint;
    @JsonProperty("winddirDegree")
    private String windDirectionDegree;
    @JsonProperty("winddirection")
    private String windDirection;
    @JsonProperty("windspeedKmph")
    private String windSpeedInKilometers;
    @JsonProperty("windspeedMiles")
    private String getWindSpeedInMiles;

    @Override
    public String toString() {
        String builder = "";
        builder += String.format("Weather for date: %s (%s) ", date, DateUtils.getDayInWeek(date));
        builder += Utils.getFormattedTemperature(this, Utils.isCelsiusActivated()) + "\n";
        builder += String.format("Condition: %s", getCondition());
        return builder;
    }

    /**
     * Returns icon associated with this weather.
     * @return
     */
    public String getWeatherIcon() {
        if (icons.size() > 0) {
            return icons.get(0).getValue();
        }
        return "";
    }

    /**
     * Gets and returns current weather conditions.
     * @return
     */
    public String getCondition() {
        if (description.size() > 0) {
            return description.get(0).getValue();
        }
        return "";
    }

    public String getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(String cloudCover) {
        this.cloudCover = cloudCover;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getObservationTime() {
        return observationTime;
    }

    public void setObservationTime(String observationTime) {
        this.observationTime = observationTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(String precipitation) {
        this.precipitation = precipitation;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getTemperatureCelsius() {
        return temperatureCelsius;
    }

    public void setTemperatureCelsius(String temperatureCelsius) {
        this.temperatureCelsius = temperatureCelsius;
    }

    public String getTemperatureFahrenheit() {
        return temperatureFahrenheit;
    }

    public void setTemperatureFahrenheit(String temperatureFahrenheit) {
        this.temperatureFahrenheit = temperatureFahrenheit;
    }

    public String getMaxTemperatureCelsius() {
        return maxTemperatureCelsius;
    }

    public void setMaxTemperatureCelsius(String maxTemperatureCelsius) {
        this.maxTemperatureCelsius = maxTemperatureCelsius;
    }

    public String getMaxTemperatureFahrenheit() {
        return maxTemperatureFahrenheit;
    }

    public void setMaxTemperatureFahrenheit(String maxTemperatureFahrenheit) {
        this.maxTemperatureFahrenheit = maxTemperatureFahrenheit;
    }

    public String getMinTemperatureCelsius() {
        return minTemperatureCelsius;
    }

    public void setMinTemperatureCelsius(String minTemperatureCelsius) {
        this.minTemperatureCelsius = minTemperatureCelsius;
    }

    public String getMinTemperatureFahrenheit() {
        return minTemperatureFahrenheit;
    }

    public void setMinTemperatureFahrenheit(String minTemperatureFahrenheit) {
        this.minTemperatureFahrenheit = minTemperatureFahrenheit;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    public List<WeatherDescription> getDescription() {
        return description;
    }

    public void setDescription(List<WeatherDescription> description) {
        this.description = description;
    }

    public List<WeatherDescription> getIcons() {
        return icons;
    }

    public void setIcons(List<WeatherDescription> icons) {
        this.icons = icons;
    }

    public String getWindDirectionPoint() {
        return windDirectionPoint;
    }

    public void setWindDirectionPoint(String windDirectionPoint) {
        this.windDirectionPoint = windDirectionPoint;
    }

    public String getWindDirectionDegree() {
        return windDirectionDegree;
    }

    public void setWindDirectionDegree(String windDirectionDegree) {
        this.windDirectionDegree = windDirectionDegree;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindSpeedInKilometers() {
        return windSpeedInKilometers;
    }

    public void setWindSpeedInKilometers(String windSpeedInKilometers) {
        this.windSpeedInKilometers = windSpeedInKilometers;
    }

    public String getGetWindSpeedInMiles() {
        return getWindSpeedInMiles;
    }

    public void setGetWindSpeedInMiles(String getWindSpeedInMiles) {
        this.getWindSpeedInMiles = getWindSpeedInMiles;
    }
}
