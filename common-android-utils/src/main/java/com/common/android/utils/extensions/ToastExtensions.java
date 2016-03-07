package com.common.android.utils.extensions;

import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import static com.common.android.utils.ContextHelper.getContext;

/**
 * Created by Jan Rabe on 24/09/15.
 */
public class ToastExtensions {

    public static boolean showToastMessages = true;

    private ToastExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static void toast(@Nullable final String message) {
        if (!showToastMessages)
            return;

        if (TextUtils.isEmpty(message))
            return;

        getContext().runOnUiThread(() -> {
            final Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 100);
            toast.show();
        });
    }

    public static void toast(@StringRes final int resourceId) {
        if (!showToastMessages)
            return;

        getContext().runOnUiThread(() -> {
            final Toast toast = Toast.makeText(getContext(), getContext().getText(resourceId), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 100);
            toast.show();
        });
    }
}
