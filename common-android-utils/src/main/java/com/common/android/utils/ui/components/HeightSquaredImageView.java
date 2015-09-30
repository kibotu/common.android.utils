package com.common.android.utils.ui.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import org.jetbrains.annotations.NotNull;

/**
 * Created by conrad on 2015/03/11.
 */
public class HeightSquaredImageView extends ImageView {
    public HeightSquaredImageView(final Context context) {
        super(context);
    }

    public HeightSquaredImageView(@NotNull final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public HeightSquaredImageView(@NotNull final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, heightMeasureSpec);
    }
}
