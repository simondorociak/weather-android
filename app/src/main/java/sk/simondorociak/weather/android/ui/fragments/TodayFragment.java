package sk.simondorociak.weather.android.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;

import sk.simondorociak.weather.R;
import sk.simondorociak.weather.android.app.AppController;
import sk.simondorociak.weather.android.client.request.LocationRequest;
import sk.simondorociak.weather.android.client.request.WeatherRequest;
import sk.simondorociak.weather.android.client.response.SearchResponse;
import sk.simondorociak.weather.android.client.response.WeatherResponse;
import sk.simondorociak.weather.android.commons.Const;
import sk.simondorociak.weather.android.helpers.Updateable;
import sk.simondorociak.weather.android.model.Weather;
import sk.simondorociak.weather.android.model.WeatherLocation;
import sk.simondorociak.weather.android.util.L;
import sk.simondorociak.weather.android.util.PreferenceUtils;
import sk.simondorociak.weather.android.util.Utils;

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public class TodayFragment extends Fragment implements Updateable {

    // UI widgets
    private LinearLayout mWeatherLayout;
    private NetworkImageView mWeatherIcon;
    private TextView mCity, mTemperature, mCondition, mHumidity, mPrecipitation;
    private TextView mPressure, mWindSpeed, mDirection, mError, mNoNetwork;
    private ProgressBar mProgressBar;

    // current weather data
    private Weather mTodayWeather;
    private WeatherLocation mWeatherLocation;

    /**
     * Returns instance of this Fragment.
     * @return instance of this Fragment
     */
    public static Fragment getInstance() {
        return new TodayFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_today, container, false);
        initWidgets(root);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        // load weather data
        onUpdate();

        // refresh content if weather data is available
        if (mTodayWeather != null) {
            showWeatherData(mTodayWeather);
        }

        if (mWeatherLocation != null) {
            showWeatherLocation(mWeatherLocation);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // cancel pending requests
        AppController.getInstance().cancelPendingRequests();
    }

    /**
     * Initialises UI widgets of this Fragment.
     * @param root root view of this Fragment
     */
    private void initWidgets(final View root) {
        mWeatherLayout = (LinearLayout) root.findViewById(R.id.weather_container);
        mWeatherIcon = (NetworkImageView) root.findViewById(R.id.icon);
        mCity = (TextView) root.findViewById(R.id.city);
        mTemperature = (TextView) root.findViewById(R.id.temperature);
        mCondition = (TextView) root.findViewById(R.id.condition);
        mHumidity = (TextView) root.findViewById(R.id.humidity);
        mPrecipitation = (TextView) root.findViewById(R.id.precipitation);
        mPressure = (TextView) root.findViewById(R.id.pressure);
        mWindSpeed = (TextView) root.findViewById(R.id.wind_speed);
        mDirection = (TextView) root.findViewById(R.id.direction);
        mProgressBar = (ProgressBar) root.findViewById(R.id.progress);
        mError = (TextView) root.findViewById(R.id.error_text);
        mNoNetwork = (TextView) root.findViewById(R.id.no_network_text);

        // hide fragment content and show progress
        Utils.hideView(mWeatherLayout);
        Utils.showView(mProgressBar);
    }

    /**
     * Loads today's forecast from server.
     * @param lat current latitude of device
     * @param lon current longitude of device
     */
    private void loadTodayWeatherFromServer(final double lat, final double lon) {
        AppController.getInstance().addRequestToQueue(new WeatherRequest(true, lat, lon,
                new Response.Listener<WeatherResponse>() {

                    @Override
                    public void onResponse(WeatherResponse response) {
                        mTodayWeather = response.getTodayWeather();

                        // show weather data on this screen
                        if (mTodayWeather != null) {
                            showWeatherData(mTodayWeather);

                            // show fragment content and hide progress bar
                            Utils.hideView(mProgressBar, mNoNetwork, mError);
                            Utils.showView(mWeatherLayout);
                        }
                        else {
                            // show error message to user
                            Utils.hideView(mWeatherLayout, mNoNetwork, mProgressBar);
                            Utils.showView(mError);
                        }
                    }},
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // show error message to user
                        Utils.hideView(mWeatherLayout, mProgressBar, mNoNetwork);
                        Utils.showView(mError);
                    }}));
    }

    /**
     * Loads weather location from server.
     * @param lat current latitude of device
     * @param lon current longitude of device
     */
    private void loadWeatherLocationFromServer(final double lat, final double lon) {
        AppController.getInstance().addRequestToQueue(new LocationRequest(lat, lon,
                new Response.Listener<SearchResponse>() {
                    @Override
                    public void onResponse(SearchResponse response) {
                        mWeatherLocation = response.getWeatherLocation();
                        // if weather location i available
                        if (mWeatherLocation != null) {
                            showWeatherLocation(mWeatherLocation);
                        }
                        else {
                            Utils.hideView(mCity);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        L.info("Unable to obtain weather location from back-end");
                        Utils.hideView(mCity);
                    }
                }));
    }

    /**
     * Shows current weather data obtained from server.
     * @param weather current weather obtained from server
     */
    private void showWeatherData(final Weather weather) {
        mWeatherIcon.setImageUrl(weather.getWeatherIcon(), AppController.getInstance().getImageLoader());
        mTemperature.setText(Utils.getFormattedTemperature(weather, Utils.isCelsiusActivated()));
        mCondition.setText(weather.getCondition());
        mHumidity.setText(getString(R.string.weather_humidity, weather.getHumidity()));
        mPrecipitation.setText(getString(R.string.weather_precipitation, weather.getPrecipitation()));
        mPressure.setText(getString(R.string.weather_pressure, weather.getPressure()));
        mWindSpeed.setText(Utils.getFormattedWindSpeed(weather, Utils.isKilometersActivated()));
        mDirection.setText(weather.getWindDirection());
    }

    /**
     * Shows current weather location data such as city.
     * @param location current weather location data
     */
    private void showWeatherLocation(final WeatherLocation location) {
       mCity.setText(getString(R.string.weather_location, location.getAreaName(), location.getCountryName()));
        Utils.showView(mCity);
    }

    @Override
    public void onUpdate() {
        // assign current device location
        final double latitude = PreferenceUtils.getDouble(Const.PREF_LATITUDE);
        final double longitude = PreferenceUtils.getDouble(Const.PREF_LONGITUDE);

        // load weather data from server if device is online
        if (Utils.isDeviceOnline(AppController.getContext())) {
            loadTodayWeatherFromServer(latitude, longitude);
            loadWeatherLocationFromServer(latitude, longitude);
        }
        // show no network layout to user
        else {
            Utils.hideView(mWeatherLayout, mProgressBar, mError);
            Utils.showView(mNoNetwork);
        }
    }
}
