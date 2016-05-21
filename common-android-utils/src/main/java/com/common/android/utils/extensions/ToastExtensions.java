package com.common.android.utils.extensions;

import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.common.android.utils.ContextHelper;
import com.common.android.utils.R;

import static android.text.TextUtils.isEmpty;
import static com.common.android.utils.ContextHelper.getActivity;
import static com.common.android.utils.ContextHelper.getContext;
import static com.common.android.utils.extensions.ResourceExtensions.color;
import static com.common.android.utils.extensions.ViewExtensions.inflate;

/**
 * Created by Jan Rabe on 24/09/15.
 */
final public class ToastExtensions {

    public static boolean showToastMessages = true;

    private ToastExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static void toast(@Nullable final String message) {
        if (!showToastMessages)
            return;

        if (isEmpty(message))
            return;

        getActivity().runOnUiThread(() -> {
            final Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 100);
            toast.show();
        });
    }

    static void toast(@StringRes final int resourceId) {
        if (!showToastMessages)
            return;

        getActivity().runOnUiThread(() -> {
            final Toast toast = Toast.makeText(getContext(), getContext().getText(resourceId), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 100);
            toast.show();
        });
    }

    private static Toast createCustomToast(@Nullable String message, @ColorRes final int bgColor, @ColorRes final int textColor) {

        final ViewHolder viewHolder = new ViewHolder(R.layout.custom_toast, (ViewGroup) ViewExtensions.getContentRoot());
        viewHolder.text.setText(message);

        viewHolder.itemView.setBackgroundColor(color(bgColor));
        viewHolder.text.setTextColor(color(textColor));

        final Toast toast = new Toast(ContextHelper.getApplication());
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(viewHolder.itemView);
        return toast;
    }

    static class ViewHolder {

        @NonNull
        TextView text;

        @NonNull
        View itemView;

        public ViewHolder(@LayoutRes int layout, @Nullable ViewGroup parent) {
            itemView = inflate(layout, parent);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }

    public static void showSuccessToast(@Nullable final String message) {
        if (!isEmpty(message))
            createCustomToast(message, R.color.success, R.color.black).show();
    }

    public static void showInfoToast(@Nullable final String message) {
        if (!isEmpty(message))
            createCustomToast(message, R.color.info, R.color.black).show();
    }

    public static void showWarningToast(@Nullable final String message) {
        if (!isEmpty(message))
            createCustomToast(message, R.color.warning, R.color.black).show();
    }

    public static void showDangerToast(@Nullable final String message) {
        if (!isEmpty(message))
            createCustomToast(message, R.color.danger, R.color.black).show();
    }
}
