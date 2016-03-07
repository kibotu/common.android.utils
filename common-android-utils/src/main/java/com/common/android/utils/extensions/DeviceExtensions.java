package com.common.android.utils.extensions;

import android.app.Service;
import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import com.common.android.utils.device.Dimension;

import static android.os.Build.VERSION.SDK_INT;
import static com.common.android.utils.ContextHelper.getContext;
import static com.common.android.utils.extensions.ViewExtensions.getContentRoot;

/**
 * Created by Jan Rabe on 24/09/15.
 */
public class DeviceExtensions {

    private DeviceExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    @NonNull
    public static Dimension getScreenDimension() {
        final WindowManager w = getContext().getWindowManager();
        final Display d = w.getDefaultDisplay();
        final DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);
        // since SDK_INT = 1;
        Dimension dimension = new Dimension(metrics.widthPixels, metrics.heightPixels);
        // includes window decorations (statusbar bar/menu bar)
        if (SDK_INT >= 14 && SDK_INT < 17)
            try {
                dimension = new Dimension((Integer) Display.class.getMethod("getRawWidth").invoke(d), (Integer) Display.class.getMethod("getRawHeight").invoke(d));
            } catch (@NonNull final Exception ignored) {
            }
        // includes window decorations (statusbar bar/menu bar)
        if (SDK_INT >= 17)
            try {
                final Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(d, realSize);
                dimension = new Dimension(realSize.x, realSize.y);
            } catch (@NonNull final Exception ignored) {
            }
        return dimension;
    }

    public static void showKeyboard() {
        showKeyboard(getContentRoot());
    }

    public static void showKeyboard(@NonNull final View v) {
        v.requestFocus();
        final InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void hideKeyboard() {
        hideKeyboard(getContentRoot());
    }

    public static void hideKeyboard(@NonNull final View v) {
        final InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
