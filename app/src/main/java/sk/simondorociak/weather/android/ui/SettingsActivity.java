package sk.simondorociak.weather.android.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;

import sk.simondorociak.weather.R;

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public class SettingsActivity extends BasePreferenceActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // enable up navigation in ActionBar
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        addPreferencesFromResource(R.xml.main_preferences);

        // show default summaries for ListPreferences
        setDefaultSummary(getString(R.string.pref_key_temperature_unit), getString(R.string.pref_key_length_unit));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    /**
     * Sets up default summary for ListPreferences with provided key(s).
     * @param keys keys of ListPreferences
     */
    private void setDefaultSummary(String... keys) {
        for (String key: keys) {
            Preference pref = findPreference(key);
            if (pref != null && pref instanceof ListPreference) {
                pref.setSummary(((ListPreference) pref).getEntry());
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        final Preference pref = findPreference(key);
        if (pref instanceof ListPreference) {
            pref.setSummary(((ListPreference) pref).getEntry());
        }
    }
}
