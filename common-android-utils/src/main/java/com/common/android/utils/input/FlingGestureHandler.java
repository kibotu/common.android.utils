package com.common.android.utils.input;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Jan Rabe on 22/04/15.
 */
public abstract class FlingGestureHandler implements View.OnTouchListener {

    @NonNull
    private final GestureDetector gestureDetector;

    private final int minDistance;
    private final int velocityThreshold;

    public FlingGestureHandler(final Activity activity) {
        this(activity, 100, 100);
    }

    public FlingGestureHandler(final Activity activity, final int minDistance, final int velocityThreshold) {
        gestureDetector = new GestureDetector(activity, new GestureListener());
        this.minDistance = minDistance;
        this.velocityThreshold = velocityThreshold;
    }

    @Override
    public boolean onTouch(final View v, @NonNull final MotionEvent event) {
        return !gestureDetector.onTouchEvent(event);
    }

    public abstract void onRightToLeft();

    public abstract void onLeftToRight();

    public abstract void onBottomToTop();

    public abstract void onTopToBottom();

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(@NonNull final MotionEvent e1, @NonNull final MotionEvent e2, final float velocityX, final float velocityY) {
            if (e1.getX() - e2.getX() > minDistance && Math.abs(velocityX) > velocityThreshold) {
                onRightToLeft();
                return true;
            } else if (e2.getX() - e1.getX() > minDistance && Math.abs(velocityX) > velocityThreshold) {
                onLeftToRight();
                return true;
            }
            if (e1.getY() - e2.getY() > minDistance && Math.abs(velocityY) > velocityThreshold) {
                onBottomToTop();
                return true;
            } else if (e2.getY() - e1.getY() > minDistance && Math.abs(velocityY) > velocityThreshold) {
                onTopToBottom();
                return true;
            }
            return false;
        }
    }

}
