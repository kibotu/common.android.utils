package com.common.android.utils.ui.recyclerView;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Santi on 03/07/2015.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private final int space;
    @NonNull
    private final Orientation orientation;

    public SpacesItemDecoration(final int space) {
        this(space, Orientation.Horizontal);
    }

    public SpacesItemDecoration(final int space, @NonNull final Orientation orientation) {
        this.space = space;
        this.orientation = orientation;
    }

    @Override
    public void getItemOffsets(@NonNull final Rect outRect, @NonNull final View view, @NonNull final RecyclerView parent, final RecyclerView.State state) {

        final int childAdapterPosition = parent.getChildAdapterPosition(view);

        final int offset = parent.getMeasuredWidth() - (view.getMeasuredWidth());

        if (orientation == Orientation.Horizontal) {
            if (childAdapterPosition == parent.getAdapter().getItemCount() - 1)
                outRect.right = offset;
            else
                outRect.right = space;


        } else if (orientation == Orientation.Vertical) {
            outRect.bottom = space;
            if (childAdapterPosition == parent.getChildCount() - 1) {
                outRect.bottom = 0;
            }
        }
    }
}
