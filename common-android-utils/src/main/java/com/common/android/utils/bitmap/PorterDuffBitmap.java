package com.common.android.utils.bitmap;

import android.graphics.*;
import android.util.Log;
import android.view.View;
import com.common.android.utils.device.Dimension;
import org.jetbrains.annotations.NotNull;

import static com.common.android.utils.extensions.DeviceExtensions.getScreenDimension;
import static com.common.android.utils.extensions.ViewExtensions.getContentRoot;
import static com.common.android.utils.extensions.ViewExtensions.getScreenLocation;

/**
 * Created by Jan Rabe on 11/06/15.
 */
public class PorterDuffBitmap {

    public static final String TAG = PorterDuffBitmap.class.getSimpleName();
    @NotNull
    final Bitmap bitmap;
    @NotNull
    final Canvas canvas;
    int topOffset;

    public PorterDuffBitmap(@NotNull final Bitmap bitmap, final int topOffset) {
        this.bitmap = bitmap;
        canvas = new Canvas(bitmap);
        this.topOffset = topOffset;
    }

    @NotNull
    public static PorterDuffBitmap createFullscreenBitmap(final int color) {
        final Dimension dim = getScreenDimension();
        final int topOffset = dim.height - getContentRoot().getMeasuredHeight();
        Log.v(TAG, dim.toString() + " topOffset=" + topOffset + " " + getContentRoot().getMeasuredHeight());
        final PorterDuffBitmap p = new PorterDuffBitmap(Bitmap.createBitmap(dim.width, dim.height - topOffset, Bitmap.Config.ARGB_8888), topOffset);
        p.canvas.drawColor(color);
        return p;
    }

    public void addMask(@NotNull final View view, final int left, final int top, @NotNull final PorterDuff.Mode mode) {
        final int[] locations = getScreenLocation(view);
        Log.v(TAG, view.getWidth() + "x" + view.getHeight());
        drawWithPorterDuff(locations[0] + left, locations[1] + top, view.getWidth(), view.getHeight(), mode);
    }

    private void drawWithPorterDuff(final int left, final int top, final int width, final int height, @NotNull final PorterDuff.Mode mode) {
        final Paint maskPaint = new Paint();
        maskPaint.setXfermode(new PorterDuffXfermode(mode));

        final Bitmap mask = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        final Canvas c = new Canvas(mask);
        c.drawColor(Color.BLACK);

        // apply mask
        Log.v(TAG, "left=" + left + "top= " + top + "topOffset" + topOffset + " topWithOffset=" + (top - topOffset));
        canvas.drawBitmap(mask, left, top - topOffset, maskPaint);
        mask.recycle();
    }

    @NotNull
    public Bitmap getBitmap() {
        return bitmap;
    }
}
