package com.common.android.utils.extensions;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.widget.ImageView;

/**
 * Created by Jan Rabe on 24/09/15.
 */
public class BitmapExtensions {

    private BitmapExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static Bitmap flipHorizontally(@NonNull final Bitmap src) {
        final Matrix matrix = new Matrix();
        matrix.preScale(1.0f, -1.0f);
        return createBitmapWithMatrix(src, matrix);
    }

    public static Bitmap flipVertically(@NonNull final Bitmap src) {
        final Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);
        return createBitmapWithMatrix(src, matrix);
    }

    public static Bitmap createBitmapWithMatrix(@NonNull final Bitmap src, @NonNull final Matrix matrix) {
        final Bitmap dst = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        if (src != dst) {
//            src.recycle();
        }
        return dst;
    }

    public static Bitmap getBitmap(@NonNull final ImageView imageView) {
        return ((BitmapDrawable) imageView.getDrawable()).getBitmap();
    }
}
