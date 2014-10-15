package sk.simondorociak.weather.android.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockPreferenceActivity;

import sk.simondorociak.weather.R;

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public abstract class BasePreferenceActivity extends SherlockPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            initActionBar(actionBar);
        }
    }

    /**
     * Initialises ActionBar.
     * @param actionBar current ActionBar
     */
    private void initActionBar(final ActionBar actionBar) {
        // set-up custom view for ActionBar
        View view = getLayoutInflater().inflate(R.layout.ab_layout, null);
        if (view != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setCustomView(view);

            // update custom view title
            ((TextView) view.findViewById(R.id.title)).setText(getTitle());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
