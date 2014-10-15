package sk.simondorociak.weather.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sk.simondorociak.weather.R;
import sk.simondorociak.weather.android.model.NavigationItem;

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public class NavigationAdapter extends BaseAdapter {

    private Context context;
    private List<NavigationItem> items;

    /**
     *
     * @param context current Context
     * @param items navigation items to add
     */
    public NavigationAdapter(final Context context, final List<NavigationItem> items) {
        this.context = context;
        this.items = items;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.navigation_item_row, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.icon);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final NavigationItem item = items.get(position);
        viewHolder.icon.setImageResource(item.getIconId());
        viewHolder.title.setText(item.getTitle());

        return convertView;
    }

    private static class ViewHolder {

        protected ImageView icon;
        protected TextView title;
    }
}
