package sk.simondorociak.weather.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import sk.simondorociak.weather.R;
import sk.simondorociak.weather.android.commons.Fonts;

/**
 * Custom implementation of TextView widget with custom defined font.
 * Font should be set via XML attribute.
 * @author Simon Dorociak <S.Dorociak@gmail.com>
 */
public class TypefaceTextView extends TextView {

    public TypefaceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TypefaceTextView);
        if (a != null) {
            String fontName = a.getString(R.styleable.TypefaceTextView_font);
            if (fontName != null && !isInEditMode()) {
                setTypeface(Fonts.getFont(getContext(), fontName));
            }
            a.recycle();
        }
    }
}
