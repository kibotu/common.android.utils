package com.common.android.utils.extensions;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

import static com.common.android.utils.ContextHelper.getContext;

/**
 * Created by Nyaruhodo on 21.05.2016.
 */
public class DrawableExtensions {
    @Nullable
    public static Drawable scaleImage(@Nullable Drawable image, float scaleFactor) {

        if ((image == null) || !(image instanceof BitmapDrawable)) {
            return image;
        }

        Bitmap b = ((BitmapDrawable) image).getBitmap();

        int sizeX = Math.round(image.getIntrinsicWidth() * scaleFactor);
        int sizeY = Math.round(image.getIntrinsicHeight() * scaleFactor);

        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, sizeX, sizeY, false);

        image = new BitmapDrawable(getContext().getResources(), bitmapResized);

        return image;

    }
}
