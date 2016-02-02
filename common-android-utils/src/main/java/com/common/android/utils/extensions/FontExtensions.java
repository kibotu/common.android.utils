package com.common.android.utils.extensions;

import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.view.View;
import android.widget.TextView;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

import static com.common.android.utils.extensions.ViewExtensions.setColor;

/**
 * Created by jan.rabe on 17/12/15.
 */
public class FontExtensions {

    public static void setFontFor(@NotNull final Typeface font, @NotNull final TextView... views) {
        for (final TextView view : views)
            view.setTypeface(font);
    }

    public static void applyColor(@ColorRes int resourceId, @NotNull final Collection<View> views) {
        for (final View v : views)
            setColor(v, resourceId);
    }

    public static void applyColor(@ColorRes int resourceId, @NotNull final View... views) {
        for (final View v : views)
            setColor(v, resourceId);
    }
}
