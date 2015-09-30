package com.common.android.utils.ui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import com.common.android.utils.R;
import org.jetbrains.annotations.NotNull;

public class StarsView extends RelativeLayout {

    @NotNull
    HorizontalClippingImageView[] mFullStarViews;
    private float mRating;

    //region Constructors

    public StarsView(@NotNull final Context context) {
        super(context);
        init(context, null, 0);
    }

    public StarsView(@NotNull final Context context, @NotNull final AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public StarsView(@NotNull final Context context, @NotNull final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(@NotNull final Context context, @NotNull final AttributeSet attrs, final int defStyle) {
        final LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.view_stars, this, true);

        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.StarsView, defStyle, 0);
        mRating = a.getFloat(R.styleable.StarsView_rating, 0);
        a.recycle();

        this.mFullStarViews = new HorizontalClippingImageView[]{
                (HorizontalClippingImageView) findViewById(R.id.full_star0),
                (HorizontalClippingImageView) findViewById(R.id.full_star1),
                (HorizontalClippingImageView) findViewById(R.id.full_star2),
                (HorizontalClippingImageView) findViewById(R.id.full_star3),
                (HorizontalClippingImageView) findViewById(R.id.full_star4)
        };

        setRatingForStars();
    }

    //endregion

    private void setRatingForStars() {
        for (int i = 0; i < mFullStarViews.length; i++) {
            final float percentage = Math.min(1.0f, Math.max(0.0f, mRating - i));
            mFullStarViews[i].setPercentage(percentage);
        }
    }

    public void setRating(final float rating) {
        mRating = rating;
        setRatingForStars();
    }

}
