package com.common.android.utils.extensions;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import static com.common.android.utils.ContextHelper.getContext;

/**
 * Created by Jan Rabe on 24/09/15.
 */
public class ActivityExtensions {

    private ActivityExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static void enableImmersiveMode() {
        getContext().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            getContext().getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                @Override
                public void onSystemUiVisibilityChange(final int visibility) {
                    if (visibility == 0) {
                        getContext().getWindow().getDecorView().setSystemUiVisibility(
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                    }
                }
            });
        }
    }

}
