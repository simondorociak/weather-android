package sk.simondorociak.weather.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import sk.simondorociak.weather.R;
import sk.simondorociak.weather.android.model.Weather;
import sk.simondorociak.weather.android.util.DateUtils;
import sk.simondorociak.weather.android.util.Utils;

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public class ForecastAdapter extends BaseAdapter {

    private List<Weather> items = new ArrayList<Weather>();
    private Context context;

    // image loader for loading weather ions
    private ImageLoader imageLoader;

    public ForecastAdapter(final Context context, final ImageLoader imageLoader) {
        this.context = context;
        this.imageLoader = imageLoader;
    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return items != null ? items.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.forecast_item_row, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.icon = (NetworkImageView) convertView.findViewById(R.id.icon);
            viewHolder.day = (TextView) convertView.findViewById(R.id.day);
            viewHolder.temperature = (TextView) convertView.findViewById(R.id.temperature);
            viewHolder.condition = (TextView) convertView.findViewById(R.id.condition);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Weather item = items.get(position);
        viewHolder.icon.setImageUrl(item.getWeatherIcon(), imageLoader);
        viewHolder.day.setText(DateUtils.getDayInWeek(item.getDate()));
        viewHolder.temperature.setText(Utils.getFormattedTemperature(item, Utils.isCelsiusActivated()));
        viewHolder.condition.setText(item.getCondition());

        return convertView;
    }

    /**
     * Updates adapter with new forecast data.
     * @param newData new data to insert
     */
    public void update(final List<Weather> newData) {
        if (newData == null || newData.size() == 0) {
            return;
        }

        if (items.size() > 0) {
            items.clear();
        }

        items.addAll(newData);
        update();
    }

    /**
     * Refreshes adapter without data set changes.
     */
    public void refresh() {
        update();
    }

    /**
     * Calls request to update adapter itself.
     */
    private void update() {
        notifyDataSetChanged();
    }

    /**
     * Helper class that uses ViewHolder design pattern for view's caching.
     */
    private static class ViewHolder {

        protected NetworkImageView icon;
        protected TextView day, temperature, condition;

    }
}
