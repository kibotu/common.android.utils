package com.common.android.utils.ui.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.appl.library.CoverFlowCarousel;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Jan Rabe on 05/08/15.
 */
public class CustomCarouselView extends CoverFlowCarousel {

    public CustomCarouselView(final Context context) {
        super(context);
    }

    public CustomCarouselView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomCarouselView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(@NotNull final MotionEvent event) {
        return true;
    }
}
