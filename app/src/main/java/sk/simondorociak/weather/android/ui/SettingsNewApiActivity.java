package sk.simondorociak.weather.android.ui;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import sk.simondorociak.weather.R;

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public class SettingsNewApiActivity extends BasePreferenceActivity {

    @TargetApi(11)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager()
                .beginTransaction()
                .add(android.R.id.content, new MyPreferenceFragment())
                .commit();
    }

    @TargetApi(11)
    public static class MyPreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.main_preferences);
        }
    }
}
