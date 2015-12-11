package com.common.android.utils.extensions;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static com.common.android.utils.ContextHelper.getContext;

/**
 * Created by Jan Rabe on 27/10/15.
 */
public class ResourceExtensions {

    private ResourceExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static int dimension(@DimenRes final int resId) {
        return (int) getContext().getResources().getDimension(resId);
    }

    public static int color(@ColorRes final int color) {
        return SDK_INT >= Build.VERSION_CODES.M
                ? ContextCompat.getColor(getContext(), color)
                : getResources().getColor(color);
    }

    public static Drawable drawable(@DrawableRes int drawable) {
        if (SDK_INT >= LOLLIPOP) {
            return getResources().getDrawable(drawable, getContext().getTheme());
        } else {
            return getResources().getDrawable(drawable);
        }
    }

    private static Resources getResources() {
        return getContext().getResources();
    }
}
