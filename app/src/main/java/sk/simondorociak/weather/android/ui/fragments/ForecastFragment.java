package sk.simondorociak.weather.android.ui.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.List;

import sk.simondorociak.weather.R;
import sk.simondorociak.weather.android.adapter.ForecastAdapter;
import sk.simondorociak.weather.android.app.AppController;
import sk.simondorociak.weather.android.client.request.WeatherRequest;
import sk.simondorociak.weather.android.client.response.WeatherResponse;
import sk.simondorociak.weather.android.commons.Const;
import sk.simondorociak.weather.android.helpers.Updateable;
import sk.simondorociak.weather.android.model.Weather;
import sk.simondorociak.weather.android.util.L;
import sk.simondorociak.weather.android.util.PreferenceUtils;
import sk.simondorociak.weather.android.util.Utils;

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public class ForecastFragment extends Fragment implements Updateable {

    // UI widgets
    private ListView mForecastList;
    private ProgressBar mProgressBar;
    private TextView mError, mNoNetwork;

    /**
     * Gets instance of this Fragment.
     * @return
     */
    public static Fragment getInstance() {
        return new ForecastFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_forecast, container, false);
        // initialise and set-up widgets
        initWidgets(root);
        setUpWidgets();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        // refresh adapter
        ((ForecastAdapter) mForecastList.getAdapter()).refresh();

        // load current forecast
        onUpdate();
    }

    @Override
    public void onStop() {
        super.onStop();
        // cancel pending requests
        AppController.getInstance().cancelPendingRequests();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(getString(R.string.forecast_list_row_menu_title));
        getActivity().getMenuInflater().inflate(R.menu.menu_forecast_list_row, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // get selected data
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Weather weather = (Weather) mForecastList.getAdapter().getItem(info.position);
        Intent shareIntent;
        try {
            switch (item.getItemId()) {
                case R.id.action_share:
                    shareIntent = getShareIntent(weather.toString());
                    startActivity(Intent.createChooser(shareIntent, getString(R.string.share_via_text)));
                    return true;
                case R.id.action_share_email:
                    shareIntent = getShareEmailIntent(weather.toString());
                    startActivity(Intent.createChooser(shareIntent, getString(R.string.share_via_text)));
                    return true;
                case R.id.action_share_sms:
                    shareIntent = getShareSMSIntent(weather.toString());
                    startActivity(shareIntent);
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        }
        catch (ActivityNotFoundException e) {
            L.info("Unable to start Activity for sharing");
            return super.onContextItemSelected(item);
        }
    }

    /**
     * Returns Intent used for sharing.
     * @param data data as String to share
     * @return
     */
    private Intent getShareIntent(final String data) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, data);
        return intent;
    }

    /**
     * Returns Intent used for sharing via e-mail client.
     * @param data data as String to share
     * @return
     */
    private Intent getShareEmailIntent(final String data) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "", null));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Weather");
        intent.putExtra(Intent.EXTRA_TEXT, data);
        return intent;
    }

    /**
     * Returns Intent used for sharing via SMS.
     * @param data data as String to share
     * @return
     */
    private Intent getShareSMSIntent(final String data) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("sms:"));
            intent.putExtra("sms_body", data);
        }
        else {
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, data);

            // add default SMS package if available
            String defaultSMSPackage = Telephony.Sms.getDefaultSmsPackage(AppController.getContext());
            if (!TextUtils.isEmpty(defaultSMSPackage)) {
               intent.setPackage(defaultSMSPackage);
            }
        }
        return intent;
    }

    /**
     * Initialises UI widgets of this Fragment.
     * @param root root view of Fragment
     */
    private void initWidgets(final View root) {
        mForecastList = (ListView) root.findViewById(R.id.forecast_list);
        mProgressBar = (ProgressBar) root.findViewById(R.id.progress);
        mError = (TextView) root.findViewById(R.id.error_text);
        mNoNetwork = (TextView) root.findViewById(R.id.no_network_text);

        // show progress and hide forecast list
        Utils.showView(mProgressBar);
        Utils.hideView(mForecastList);
    }

    /**
     * Set-ups widgets of this Fragment.
     */
    private void setUpWidgets() {
        // set-up adapter to list
        mForecastList.setAdapter(new ForecastAdapter(AppController.getContext(),
                AppController.getInstance().getImageLoader()));

        // assign context menu for listview
        registerForContextMenu(mForecastList);
    }

    /**
     * Loads forecast from server.
     */
    private void loadForecastFromServer(final double lat, final double lon) {
        AppController.getInstance().addRequestToQueue(new WeatherRequest(false, lat, lon,
                new Response.Listener<WeatherResponse>() {
                    @Override
                    public void onResponse(WeatherResponse response) {
                        List<Weather> data = response.getWeather();
                        // if weather data are available
                        if (data.size() > 0) {

                            // update forecast list
                            ((ForecastAdapter) mForecastList.getAdapter()).update(response.getWeather());

                            // hide progress dialog and show forecast list
                            Utils.hideView(mProgressBar, mNoNetwork, mError);
                            Utils.showView(mForecastList);
                        }
                        else {
                            // show error message to user
                            Utils.hideView(mForecastList, mNoNetwork, mProgressBar);
                            Utils.showView(mError);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Utils.hideView(mForecastList, mNoNetwork, mProgressBar);
                        Utils.showView(mError);
                    }
                }));
    }

    @Override
    public void onUpdate() {
        // load current forecast
        if (Utils.isDeviceOnline(AppController.getContext())) {
            loadForecastFromServer(PreferenceUtils.getDouble(Const.PREF_LATITUDE),
                    PreferenceUtils.getDouble(Const.PREF_LONGITUDE));
        }
        // show no network layout to uer
        else {
            Utils.hideView(mForecastList, mProgressBar, mError);
            Utils.showView(mNoNetwork);
        }
    }
}
