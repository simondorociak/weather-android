package sk.simondorociak.weather.android.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import sk.simondorociak.weather.R;

/**
 * Helper util class used for work with Dialogs.
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public final class DialogUtils {

    /**
     * Disable class instantiation.
     */
    private DialogUtils() { }

    /**
     * Returns dialog used for enabling GPS provider(s).
     * @param context current Context
     * @param listener listener for buttons in Dialog
     * @return
     */
    public static Dialog getGPSEnablingDialog(final Context context, DialogInterface.OnClickListener listener) {
        return new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.dialog_info_title))
                .setMessage(context.getString(R.string.dialog_gps_message))
                .setPositiveButton(context.getString(R.string.dialog_button_yes), listener)
                .setNegativeButton(context.getString(R.string.dialog_button_not_now), listener)
                .setCancelable(false)
                .create();
    }
}
