package com.common.android.utils.ui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

import com.common.android.utils.R;

public class HorizontalClippingImageView extends HeightSquaredImageView {

    private float mPercentage;
    private Rect mClipRect;

    public HorizontalClippingImageView(final Context context) {
        super(context);
        init(null, 0);
    }

    public HorizontalClippingImageView(@NonNull final Context context, @NonNull final AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public HorizontalClippingImageView(@NonNull final Context context, @NonNull final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(@NonNull final AttributeSet attrs, final int defStyle) {
        mClipRect = new Rect();
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.HorizontalClippingImageView, defStyle, 0);
        mPercentage = a.getFloat(R.styleable.HorizontalClippingImageView_percentage, 0);
        a.recycle();
    }

    @Override
    protected void onDraw(@NonNull final Canvas canvas) {
        canvas.getClipBounds(mClipRect);
        mClipRect.right = Math.round(mClipRect.right * getPercentage());
        canvas.clipRect(mClipRect);
        super.onDraw(canvas);
    }

    public float getPercentage() {
        return mPercentage;
    }

    public void setPercentage(final float rating) {
        mPercentage = rating;
        postInvalidate();
    }

}
