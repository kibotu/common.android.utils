package com.common.android.utils.ui.components;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by conrad on 2015/03/11.
 */
public class HeightSquaredImageView extends ImageView {
    public HeightSquaredImageView(final Context context) {
        super(context);
    }

    public HeightSquaredImageView(@NonNull final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public HeightSquaredImageView(@NonNull final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, heightMeasureSpec);
    }
}
