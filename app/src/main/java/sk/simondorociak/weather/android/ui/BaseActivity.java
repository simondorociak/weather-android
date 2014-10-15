package sk.simondorociak.weather.android.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import sk.simondorociak.weather.R;

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public abstract class BaseActivity extends SherlockFragmentActivity {

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
        actionBar.setDisplayHomeAsUpEnabled(false);

        // set-up custom view to ActionBar
        final View view = getLayoutInflater().inflate(R.layout.ab_layout, null);
        if (view != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);

            // set-up title
            ((TextView) view.findViewById(R.id.title)).setText(getTitle());

            actionBar.setCustomView(view);
        }
    }

    /**
     * Updates ActionBar title.
     * @param title title of ActionBar to update
     */
    public final void updateActionBarTitle(final String title) {
        // get action bar custom view
        View view = getSupportActionBar().getCustomView();
        if (view != null) {
            ((TextView) view.findViewById(R.id.title)).setText(title);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
        getSupportMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
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
