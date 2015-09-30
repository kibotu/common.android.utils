package common.android.utils.interfaces;

import android.view.MotionEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Jan Rabe on 28/09/15.
 */
public interface IDispatchTouchEvent {

    boolean dispatchTouchEvent(@NotNull final MotionEvent event);
}

