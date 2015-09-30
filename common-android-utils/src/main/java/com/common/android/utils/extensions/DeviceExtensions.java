package common.android.utils.extensions;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import com.adviqo.app.MainActivity;
import common.android.utils.device.Dimension;
import org.jetbrains.annotations.NotNull;

import static android.os.Build.VERSION.SDK_INT;
import static common.android.utils.extensions.ViewExtensions.getContentRoot;

/**
 * Created by Jan Rabe on 24/09/15.
 */
public class DeviceExtensions {

    private DeviceExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    @NotNull
    public static Dimension getScreenDimension(@NotNull final Activity activity) {
        final WindowManager w = activity.getWindowManager();
        final Display d = w.getDefaultDisplay();
        final DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);
        // since SDK_INT = 1;
        Dimension dimension = new Dimension(metrics.widthPixels, metrics.heightPixels);
        // includes window decorations (statusbar bar/menu bar)
        if (SDK_INT >= 14 && SDK_INT < 17)
            try {
                dimension = new Dimension((Integer) Display.class.getMethod("getRawWidth").invoke(d), (Integer) Display.class.getMethod("getRawHeight").invoke(d));
            } catch (final Exception ignored) {
            }
        // includes window decorations (statusbar bar/menu bar)
        if (SDK_INT >= 17)
            try {
                final Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(d, realSize);
                dimension = new Dimension(realSize.x, realSize.y);
            } catch (final Exception ignored) {
            }
        return dimension;
    }

    public static void showKeyboard() {
        final View view = getContentRoot();
        view.requestFocus();
        final InputMethodManager imm = (InputMethodManager) MainActivity.currentMainActivity().getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void hideKeyboard() {
        hideKeyboard(MainActivity.currentMainActivity(), getContentRoot());
    }


    public static void hideKeyboard(@NotNull final Context context, @NotNull final View v) {
        final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
