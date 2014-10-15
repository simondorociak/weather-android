package sk.simondorociak.weather.android.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import sk.simondorociak.weather.R;
import sk.simondorociak.weather.android.adapter.NavigationAdapter;
import sk.simondorociak.weather.android.app.AppController;
import sk.simondorociak.weather.android.commons.Const;
import sk.simondorociak.weather.android.helpers.Updateable;
import sk.simondorociak.weather.android.model.NavigationItem;
import sk.simondorociak.weather.android.ui.fragments.ForecastFragment;
import sk.simondorociak.weather.android.ui.fragments.TodayFragment;
import sk.simondorociak.weather.android.util.DialogUtils;
import sk.simondorociak.weather.android.util.L;
import sk.simondorociak.weather.android.util.PreferenceUtils;

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public class Main extends BaseActivity {

    // constants
    private static final int LOC_MIN_TIME = 1000 * 60 * 5;
    private static final int LOC_MIN_DISTANCE = 100;
    private static final int REQUEST_GPS = 3;

    // navigation properties
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mNavigationList;

    // location properties
    private LocationManager mLocationManager;
    private String mLocationProvider;

    // currently visible fragment
    private Fragment mVisibleFragment;

    // dialogs
    private Dialog mGpsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // enable up navigation that should be replaced
        // with navigation drawer icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // set-up widgets and navigation
        initWidgets();
        initNavigation();

        // init location manager
        initLocationManager();

        // if device has turned any provider
        if (!isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                && !isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            // show GPS enabling dialog
            showGPSDialog(this);

            return;
        }

        // set-up location provider
        setUpLocationProvider();

        // after first launch show first navigation item
        if (savedInstanceState == null) {
            setNavigationItem(0, (NavigationItem) mNavigationList.getAdapter().getItem(0));
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // sync drawer toggle
        if (mDrawerToggle != null) {
            mDrawerToggle.syncState();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mDrawerToggle != null) {
            mDrawerToggle.onConfigurationChanged(newConfig);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // start GSP tracking
        if (!TextUtils.isEmpty(mLocationProvider)) {
            startLocationTracking(mLocationProvider);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // stop GPS tracking
        stopLocationTracking();

        // dismiss visible popup components
        dismissPopupComponentsAttachedIntoActivity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GPS) {

            // if user did turned on any provider
            if (!isProviderEnabled(LocationManager.GPS_PROVIDER)
                    && !isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                showGPSDialog(this);
            }
            // if user enabled at least one provider
            else {
                // set-up location provider
                setUpLocationProvider();

                // start GPS tracking
                startLocationTracking(mLocationProvider);

                // show first navigation item
                setNavigationItem(0, (NavigationItem) mNavigationList.getAdapter().getItem(0));
            }
        }
    }

    /**
     * Sets up location provider that application should use.
     */
    private void setUpLocationProvider() {
        if (isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            mLocationProvider = LocationManager.NETWORK_PROVIDER;
            L.info("Used NetworkProvider");
        }
        else if (isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mLocationProvider = LocationManager.GPS_PROVIDER;
            L.info("Used GPSProvider");
        }
    }

    /**
     * Shows GPS Enabling Dialog on the Screen.
     */
    private void showGPSDialog(final Context context) {
        if (mGpsDialog == null) {
            mGpsDialog = DialogUtils.getGPSEnablingDialog(context,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int whichButton) {
                            if (whichButton == DialogInterface.BUTTON_POSITIVE) {
                                // close dialog
                                dialogInterface.dismiss();

                                // open GPS Settings Screen
                                startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), REQUEST_GPS);
                            } else if (whichButton == DialogInterface.BUTTON_NEGATIVE) {
                                // close dialog
                                dialogInterface.dismiss();

                                // close application
                                finish();
                            }
                        }
                    });
        }
        if (!mGpsDialog.isShowing()) {
            mGpsDialog.show();
        }
    }

    /**
     * Dismisses popup components such as Dialogs if are currently attached into Activity.
     */
    private void dismissPopupComponentsAttachedIntoActivity() {
        if (mGpsDialog != null && mGpsDialog.isShowing()) {
            mGpsDialog.dismiss();
        }
    }

    /**
     * Sets-up widgets of this screen.
     */
    private void initWidgets() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer);
        mNavigationList = (ListView) findViewById(R.id.navigation_list);
        mNavigationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View row, int position, long id) {

                // add fragment due to clicked position in navigation
                setNavigationItem(position, (NavigationItem) parent.getItemAtPosition(position));
            }
        });
    }

    /**
     * Sets-up location manager.
     */
    private void initLocationManager() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * Initialises left pane navigation.
     */
    private void initNavigation() {
        // create navigation items
        List<NavigationItem> navItems = createNavigationItems();

        // set-up adapter for navigation list
        mNavigationList.setAdapter(new NavigationAdapter(AppController.getContext(), navItems));

        // set-up navigation toggle
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_navigation_drawer, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    /**
     * Creates navigation items from resources.
     * @return
     */
    private List<NavigationItem> createNavigationItems() {
        List<NavigationItem> navItems = new ArrayList<NavigationItem>();
        final String[] items = AppController.getContext().getResources().getStringArray(R.array.navigation_items);
        final TypedArray icons = AppController.getContext().getResources().obtainTypedArray(R.array.navigation_icons);
        for (int i = 0; i < items.length; i++) {
            navItems.add(new NavigationItem(items[i], icons.getResourceId(i, -1)));
        }

        // recycle typed array
        icons.recycle();

        return navItems;
    }

    /**
     * Sets up navigation item.
     * @param position position of navigation item
     * @param item currently selected navigation item
     */
    private void setNavigationItem(final int position, final NavigationItem item) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = TodayFragment.getInstance();
            break;
            case 1:
                fragment = ForecastFragment.getInstance();
            break;
        }

        if (fragment != null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();

            // update navigation selection and close drawer
            mNavigationList.setItemChecked(position, true);
            mDrawerLayout.closeDrawer(mNavigationList);

            // update action bar title
            if (item != null) {
                updateActionBarTitle(item.getTitle());
            }

            mVisibleFragment = fragment;
        }
    }

    /**
     * Updates currently visible fragment.
     * @param visibleItem currently shown navigation item
     */
    private void updateNavigationItem(final Fragment visibleItem) {
        if (visibleItem != null) {
            try {
                Updateable updateableItem = (Updateable) visibleItem;
                updateableItem.onUpdate();
            }
            catch (ClassCastException e) {
                L.info(String.format("Fragment should implement %s interface", Updateable.class.getSimpleName()));
            }
        }
    }

    /**
     * Starts location tracking.
     * @param provider location provider to use
     */
    private void startLocationTracking(final String provider) {
        mLocationManager.requestLocationUpdates(provider, LOC_MIN_TIME, LOC_MIN_DISTANCE, locationListener);
        L.info("Location tracking started");
    }

    /**
     * Stops location tracking.
     */
    private void stopLocationTracking() {
        mLocationManager.removeUpdates(locationListener);
        L.info("Location tracking stopped");
    }

    /**
     * Checks whether device has enabled specific provider.
     * @param provider specific provider to check
     * @return true if device has enabled specific provider, false otherwise
     */
    private boolean isProviderEnabled(final String provider) {
        return mLocationManager.isProviderEnabled(provider);
    }

    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        // hold drawer toggle selection
        if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(mNavigationList)) {
                mDrawerLayout.closeDrawer(mNavigationList);
            }
            else {
                mDrawerLayout.openDrawer(mNavigationList);
            }
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_settings:
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                    startActivity(new Intent(Main.this, SettingsActivity.class));
                }
                else {
                    startActivity(new Intent(Main.this, SettingsNewApiActivity.class));
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * LocationListener used for obtaining current location of device.
     */
    private final LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            L.info("onLocationChanged called");

            // update current location of device
            final double latitude = location.getLatitude();
            final double longitude = location.getLongitude();

            PreferenceUtils.putDouble(Const.PREF_LATITUDE, latitude);
            PreferenceUtils.putDouble(Const.PREF_LONGITUDE, longitude);

            // update currently visible fragment
            updateNavigationItem(mVisibleFragment);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) { }

        @Override
        public void onProviderEnabled(String s) { }

        @Override
        public void onProviderDisabled(String s) { }
    };
}
