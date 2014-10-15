package sk.simondorociak.weather.android.model;

/**
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public class NavigationItem {

    private String title;
    private int iconId;

    /**
     * Default constructor.
     */
    public NavigationItem() { }

    public NavigationItem(final String title, final int iconId) {
        this.title = title;
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
