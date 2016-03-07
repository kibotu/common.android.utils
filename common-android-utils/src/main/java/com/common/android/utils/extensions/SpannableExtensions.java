package com.common.android.utils.extensions;

import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;

import static com.common.android.utils.ContextHelper.getContext;

/**
 * Created by Jan Rabe on 24/09/15.
 */
final public class SpannableExtensions {

    private SpannableExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    @NonNull
    public static Spannable colorize(@NonNull final Spannable spannable, @ColorRes final int color) {
        final Spannable result = new SpannableString(spannable);
        result.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(color)), 0, result.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return result;
    }

    @NonNull
    public static Spannable strikeThrough(@NonNull final Spannable spannable) {
        final Spannable result = new SpannableString(spannable);
        result.setSpan(new StrikethroughSpan(), 0, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return result;
    }

    @NonNull
    public static Spannable bold(@NonNull final Spannable spannable) {
        final SpannableString result = new SpannableString(spannable);
        result.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, spannable.length(), 0);
        return result;
    }

    @NonNull
    public static Spannable boldItalic(@NonNull final Spannable spannable) {
        final SpannableString result = new SpannableString(spannable);
        result.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, spannable.length(), 0);
        return result;
    }
}
