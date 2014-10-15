package sk.simondorociak.weather.android.helpers;

/**
 * Helper interface used for simple way how to update Fragment content.
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public interface Updateable {

    /**
     * Called when Fragment content should be updated.
     */
    public void onUpdate();
}
