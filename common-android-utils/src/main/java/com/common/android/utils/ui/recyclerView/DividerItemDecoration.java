package com.common.android.utils.ui.recyclerView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    int mOrientation = -1;
    @Nullable
    private Drawable mDivider;
    private boolean showFirstDivider = false;
    private boolean showLastDivider = false;

    public DividerItemDecoration(@NotNull final Context context, @NotNull final AttributeSet attrs) {
        final TypedArray a = context
                .obtainStyledAttributes(attrs, new int[]{android.R.attr.listDivider});
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    public DividerItemDecoration(@NotNull final Context context, @NotNull final AttributeSet attrs, final boolean showFirstDivider,
                                 final boolean showLastDivider) {
        this(context, attrs);
        this.showFirstDivider = showFirstDivider;
        this.showLastDivider = showLastDivider;
    }

    public DividerItemDecoration(@NotNull final Context context, final int resId) {
        mDivider = ContextCompat.getDrawable(context, resId);
    }

    public DividerItemDecoration(@NotNull final Context context, final int resId, final boolean showFirstDivider,
                                 final boolean showLastDivider) {
        this(context, resId);
        this.showFirstDivider = showFirstDivider;
        this.showLastDivider = showLastDivider;
    }

    public DividerItemDecoration(@NotNull final Context context) {
        final int[] attrs = {android.R.attr.listDivider};
        final TypedArray ta = context.obtainStyledAttributes(attrs);
        mDivider = ta.getDrawable(0);
        ta.recycle();
    }

    public DividerItemDecoration(final Drawable divider) {
        mDivider = divider;
    }

    public DividerItemDecoration(final Drawable divider, final boolean showFirstDivider,
                                 final boolean showLastDivider) {
        this(divider);
        this.showFirstDivider = showFirstDivider;
        this.showLastDivider = showLastDivider;
    }

    @Override
    public void getItemOffsets(@NotNull final Rect outRect, @NotNull final View view, @NotNull final RecyclerView parent,
                               @NotNull final RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mDivider == null) {
            return;
        }

        final int position = parent.getChildAdapterPosition(view);
        if (position == RecyclerView.NO_POSITION || (position == 0 && !showFirstDivider)) {
            return;
        }

        if (mOrientation == -1)
            getOrientation(parent);

        if (mOrientation == LinearLayoutManager.VERTICAL) {
            outRect.top = mDivider.getIntrinsicHeight();
            if (showLastDivider && position == (state.getItemCount() - 1)) {
                outRect.bottom = outRect.top;
            }
        } else {
            outRect.left = mDivider.getIntrinsicWidth();
            if (showLastDivider && position == (state.getItemCount() - 1)) {
                outRect.right = outRect.left;
            }
        }
    }

    @Override
    public void onDrawOver(final Canvas c, @NotNull final RecyclerView parent, @NotNull final RecyclerView.State state) {
        if (mDivider == null) {
            super.onDrawOver(c, parent, state);
            return;
        }

        // Initialization needed to avoid compiler warning
        int left = 0;
        int right = 0;
        int top = 0;
        int bottom = 0;
        final int size;
        final int orientation = mOrientation != -1 ? mOrientation : getOrientation(parent);
        final int childCount = parent.getChildCount();

        if (orientation == LinearLayoutManager.VERTICAL) {
            size = mDivider.getIntrinsicHeight();
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
        } else { //horizontal
            size = mDivider.getIntrinsicWidth();
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
        }

        for (int i = showFirstDivider ? 0 : 1; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            if (orientation == LinearLayoutManager.VERTICAL) {
                top = child.getTop() - params.topMargin - size;
                bottom = top + size;
            } else { //horizontal
                left = child.getLeft() - params.leftMargin;
                right = left + size;
            }
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }

        // show last divider
        if (showLastDivider && childCount > 0) {
            final View child = parent.getChildAt(childCount - 1);
            if (parent.getChildAdapterPosition(child) == (state.getItemCount() - 1)) {
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                if (orientation == LinearLayoutManager.VERTICAL) {
                    top = child.getBottom() + params.bottomMargin;
                    bottom = top + size;
                } else { // horizontal
                    left = child.getRight() + params.rightMargin;
                    right = left + size;
                }
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

    private int getOrientation(@NotNull final RecyclerView parent) {
        if (mOrientation == -1) {
            if (parent.getLayoutManager() instanceof LinearLayoutManager) {
                final LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
                mOrientation = layoutManager.getOrientation();
            } else {
                throw new IllegalStateException(
                        "DividerItemDecoration can only be used with a LinearLayoutManager.");
            }
        }
        return mOrientation;
    }

    public DividerItemDecoration showFirstDivider(final boolean showFirstDivider) {
        this.showFirstDivider = showFirstDivider;
        return this;
    }

    public DividerItemDecoration showLastDivider(final boolean showLastDivider) {
        this.showLastDivider = showLastDivider;
        return this;
    }
}