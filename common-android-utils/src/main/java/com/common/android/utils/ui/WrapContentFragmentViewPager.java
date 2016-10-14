package com.common.android.utils.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.sbrukhanda.fragmentviewpager.FragmentViewPager;

/**
 * Special thanks to Daniel López Lacalle for his response
 * (http://stackoverflow.com/questions/8394681/android-i-am-unable-to-have-viewpager-wrap-content/20784791#20784791)
 */
public class WrapContentFragmentViewPager extends FragmentViewPager {

    public WrapContentFragmentViewPager(Context context) {
        super(context);
    }

    public WrapContentFragmentViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (getLayoutParams().height == ViewPager.LayoutParams.WRAP_CONTENT) {

            int height = 0;
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                child.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                int h = child.getMeasuredHeight();
                if (h > height) height = h;
            }

            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
