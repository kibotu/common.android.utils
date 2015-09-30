package com.common.android.utils.ui.recyclerView;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import com.common.android.utils.interfaces.ILogTag;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Jan Rabe on 21/08/15.
 */
public class SmoothScrollLinearLayoutManager extends LinearLayoutManager implements ILogTag {

    private static final float MILLISECONDS_PER_INCH = 60;

    public SmoothScrollLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, final int position) {

        RecyclerView.SmoothScroller smoothScroller = new TopSnappedSmoothScroller(recyclerView.getContext()) {
            //This controls the direction in which smoothScroll looks for your view
            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                return SmoothScrollLinearLayoutManager.this.computeScrollVectorForPosition(targetPosition);
            }

            //This returns the milliseconds it takes to scroll one pixel.
            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
            }
        };
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }

    @NotNull
    @Override
    final public String tag() {
        return getClass().getSimpleName();
    }

    private class TopSnappedSmoothScroller extends LinearSmoothScroller {

        public TopSnappedSmoothScroller(Context context) {
            super(context);
        }

        @Override
        public PointF computeScrollVectorForPosition(int targetPosition) {
            return SmoothScrollLinearLayoutManager.this.computeScrollVectorForPosition(targetPosition);
        }

        @Override
        protected int getVerticalSnapPreference() {
            return SNAP_TO_START;
        }

        @Override
        protected int getHorizontalSnapPreference() {
            return SNAP_TO_START;
        }

    }
}