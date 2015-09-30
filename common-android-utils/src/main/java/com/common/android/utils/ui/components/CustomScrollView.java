package common.android.utils.ui.components;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Jan Rabe on 16/07/15.
 */
public class CustomScrollView extends ScrollView {

    private static final String TAG = CustomScrollView.class.getSimpleName();
    private float startX;
    private float startY;

    public CustomScrollView(@NotNull final Context context) {
        super(context);
    }

    public CustomScrollView(@NotNull final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(@NotNull final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomScrollView(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    protected boolean isScrollingVertically(@NotNull final MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                if (Math.abs(startY - event.getY()) >= getResources().getDisplayMetrics().densityDpi * 10)
                    return true;
                break;
        }

        return false;
    }

    protected boolean isScrollingHorizontally(@NotNull final MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                break;

            case MotionEvent.ACTION_MOVE:
                if (Math.abs(startX - event.getX()) >= getResources().getDisplayMetrics().density * 10)
                    return true;
                break;
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(@NotNull final MotionEvent event) {
//        if (isScrollingHorizontally(event))
//            return true;

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(@NotNull final MotionEvent event) {
//        if (isScrollingHorizontally(event))
//            return true;

        return super.onInterceptTouchEvent(event);
    }
}
