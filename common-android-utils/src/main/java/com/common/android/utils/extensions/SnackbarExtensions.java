package com.common.android.utils.extensions;

import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.common.android.utils.R;

import static android.text.TextUtils.isEmpty;
import static com.common.android.utils.extensions.ResourceExtensions.color;
import static com.common.android.utils.extensions.ViewExtensions.getContentRoot;

public class SnackbarExtensions {

    private static Snackbar createSnackbar(String message, @ColorRes final int bgColor, @ColorRes final int textColor) {
        final Snackbar snackbar = Snackbar.make(getContentRoot(), message, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(color(textColor));
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(color(bgColor));
        TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(color(textColor));
        textView.setMaxLines(2);
        return snackbar;
    }

    public static void showSuccessSnack(@Nullable final String message) {
        if (!isEmpty(message))
            createSnackbar(message, R.color.success, R.color.black).show();
    }

    public static void showInfoSnack(@Nullable final String message) {
        if (!isEmpty(message))
            createSnackbar(message, R.color.info, R.color.black).show();
    }

    public static void showWarningSnack(@Nullable final String message) {
        if (!isEmpty(message))
            createSnackbar(message, R.color.warning, R.color.black).show();
    }

    public static void showDangerSnack(@Nullable final String message) {
        if (!isEmpty(message))
            createSnackbar(message, R.color.danger, R.color.black).show();
    }
}