package com.common.android.utils.extensions;

public class SnackbarExtensions {

    private static Snackbar createSnackbar(String message, @ColorRes final int bgColor, @ColorRes final int textColor) {
        final Snackbar snackbar = Snackbar.make(getContentRoot(), message, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(color(textColor));
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(color(bgColor));
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
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