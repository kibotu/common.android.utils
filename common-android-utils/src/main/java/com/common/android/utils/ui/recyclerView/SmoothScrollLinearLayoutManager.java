package com.common.android.utils.ui.recyclerView;

import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import com.common.android.utils.interfaces.LogTag;

/**
 * Created by Jan Rabe on 21/08/15.
 */
public class SmoothScrollLinearLayoutManager extends LinearLayoutManager implements LogTag {

    private static final float MILLISECONDS_PER_INCH = 60;

    public SmoothScrollLinearLayoutManager(final Context context, final int orientation, final boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    @Override
    public void smoothScrollToPosition(@NonNull final RecyclerView recyclerView, final RecyclerView.State state, final int position) {

        final RecyclerView.SmoothScroller smoothScroller = new TopSnappedSmoothScroller(recyclerView.getContext()) {
            //This controls the direction in which smoothScroll looks for your view
            @Override
            public PointF computeScrollVectorForPosition(final int targetPosition) {
                return SmoothScrollLinearLayoutManager.this.computeScrollVectorForPosition(targetPosition);
            }

            //This returns the milliseconds it takes to scroll one pixel.
            @Override
            protected float calculateSpeedPerPixel(@NonNull final DisplayMetrics displayMetrics) {
                return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
            }
        };
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }

    @NonNull
    @Override
    final public String tag() {
        return getClass().getSimpleName();
    }

    private class TopSnappedSmoothScroller extends LinearSmoothScroller {

        public TopSnappedSmoothScroller(@NonNull final Context context) {
            super(context);
        }

        @Override
        public PointF computeScrollVectorForPosition(final int targetPosition) {
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