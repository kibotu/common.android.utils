package com.common.android.utils.extensions;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import org.jetbrains.annotations.NotNull;

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

    public static void showSettings(@NotNull final Context context) {
        final Intent intent = new Intent(Settings.ACTION_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void showDevSettings(@NotNull final Context context) {
        final Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
