package com.common.android.utils.extensions;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.*;
import android.support.v4.content.ContextCompat;

import static android.os.Build.VERSION_CODES.LOLLIPOP_MR1;
import static com.common.android.utils.ContextHelper.getContext;
import static net.kibotu.android.deviceinfo.library.version.Version.isAtLeastVersion;

/**
 * Created by Jan Rabe on 27/10/15.
 */
final public class ResourceExtensions {

    private ResourceExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static int dimension(@DimenRes final int resId) {
        return (int) getResources().getDimension(resId);
    }

    public static int color(@ColorRes final int color) {
        return isAtLeastVersion(Build.VERSION_CODES.M)
                ? ContextCompat.getColor(getContext(), color)
                : getResources().getColor(color);
    }

    @Nullable
    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    public static Drawable drawable(@DrawableRes final int drawable) {
        return isAtLeastVersion(LOLLIPOP_MR1)
                ? getResources().getDrawable(drawable, getContext().getTheme())
                : getResources().getDrawable(drawable);
    }

    public static Resources getResources() {
        return getContext().getResources();
    }

    public static Configuration configuration() {
        return getResources().getConfiguration();
    }

    /**
     * Returns drawable resource id by name.
     *
     * @param drawable The name of the desired resource.
     * @return The associated resource identifier.  Returns 0 if no such
     * resource was found.  (0 is not a valid resource ID.)
     */
    @DrawableRes
    public static int getDrawableIdByName( @NonNull final String drawable) {
        return getContext().getResources().getIdentifier(drawable, "drawable", getContext().getPackageName());
    }

    @Nullable
    public static Drawable getDrawableByName(@NonNull final Context context, @NonNull final String drawable) {
        return ContextCompat.getDrawable(context, context.getResources().getIdentifier(drawable, "drawable", context.getPackageName()));
    }

}
