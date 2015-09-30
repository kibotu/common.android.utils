package common.android.utils.extensions;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Jan Rabe on 24/09/15.
 */
public class BitmapExtensions {

    private BitmapExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static Bitmap flipHorizontically(@NotNull final Bitmap src) {
        final Matrix matrix = new Matrix();
        matrix.preScale(1.0f, -1.0f);
        return createBitmapWithMatrix(src, matrix);
    }

    public static Bitmap flipVertically(@NotNull final Bitmap src) {
        final Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);
        return createBitmapWithMatrix(src, matrix);
    }

    public static Bitmap createBitmapWithMatrix(@NotNull final Bitmap src, @NotNull final Matrix matrix) {
        final Bitmap dst = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        if (src != dst) {
//            src.recycle();
        }
        return dst;
    }

    public static Bitmap getBitmap(@NotNull final ImageView imageView) {
        return ((BitmapDrawable) imageView.getDrawable()).getBitmap();
    }
}
