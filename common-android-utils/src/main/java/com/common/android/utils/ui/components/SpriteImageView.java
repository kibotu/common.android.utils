package com.common.android.utils.ui.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;

/**
 * Created by stefan.ehrhart on 27.05.15.
 */
public class SpriteImageView extends ImageView {

    Rect mDestinationRect, mSourceRect;
    Bitmap mBitmap;

    public SpriteImageView(final Context context) {
        super(context);
        init();
    }

    public SpriteImageView(@NonNull final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpriteImageView(@NonNull final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mDestinationRect = new Rect();
        mSourceRect = new Rect();
    }

    @Override
    public void setImageBitmap(final Bitmap bm) {
        mBitmap = bm;
        super.setImageBitmap(bm);
    }

    @Override
    public void setImageDrawable(@Nullable final Drawable drawable) {
        if (drawable instanceof BitmapDrawable)
            mBitmap = ((BitmapDrawable) drawable).getBitmap();
        if (drawable instanceof GlideBitmapDrawable)
            mBitmap = ((GlideBitmapDrawable) drawable).getBitmap();
    }

    @Override
    public void onDraw(@NonNull final Canvas canvas) {
        // Get the destination rectangle.
        // Make any changes if needed -- like padding / scaling.
        getDrawingRect(mDestinationRect);

        // Draw the bitmap.
        if (mBitmap != null)
            canvas.drawBitmap(mBitmap, mSourceRect, mDestinationRect, null);
    }

    public void setCoordinates(final int left, final int top, final int width, final int height) {
        mSourceRect = new Rect(left, top, left + width, top + height);
        invalidate();
    }
}
