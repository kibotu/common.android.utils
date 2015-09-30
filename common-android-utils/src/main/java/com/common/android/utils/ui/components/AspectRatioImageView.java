package com.common.android.utils.ui.components;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import org.jetbrains.annotations.NotNull;

/**
 * Created by franziska.huth on 10.03.15.
 */
public class AspectRatioImageView extends ImageView {
    public AspectRatioImageView(final Context context) {
        super(context);
    }

    public AspectRatioImageView(@NotNull final Context context, final AttributeSet attrSet) {
        super(context, attrSet);

    }

    public AspectRatioImageView(@NotNull final Context context, final AttributeSet attrSet, final int defStyle) {
        super(context, attrSet, defStyle);

    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        final Drawable drawable = getDrawable();
        if (drawable != null) {
            final int height = MeasureSpec.getSize(heightMeasureSpec);
            final int diw = drawable.getIntrinsicHeight();
            if (diw > 0) {
                final int width = height * drawable.getIntrinsicWidth() / diw;
                setMeasuredDimension(width, height);
            } else
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


}
