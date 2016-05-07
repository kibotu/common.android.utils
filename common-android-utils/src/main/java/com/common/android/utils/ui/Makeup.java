package com.common.android.utils.ui;

import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;

/**
 * Credit: <a href="https://gist.github.com/Trikke/90efd4432fc09aaadf3e">https://gist.github.com/Trikke/90efd4432fc09aaadf3e</a>
 */
public class Makeup {

    @NonNull
    private final Spannable sb;

    public Makeup(@NonNull final String input) {
        sb = new SpannableString(input);
    }

    @NonNull
    public Makeup strikethrough(final int start, final int length) {
        final StrikethroughSpan span = new StrikethroughSpan();
        sb.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    @NonNull
    public Makeup strikethrough() {
        final StrikethroughSpan span = new StrikethroughSpan();
        sb.setSpan(span, 0, getLength(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    @NonNull
    public Makeup underline(final int start, final int length) {
        final UnderlineSpan span = new UnderlineSpan();
        sb.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    @NonNull
    public Makeup underline() {
        final UnderlineSpan span = new UnderlineSpan();
        sb.setSpan(span, 0, getLength(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    @NonNull
    public Makeup boldify(final int start, final int length) {
        final StyleSpan span = new StyleSpan(Typeface.BOLD);
        sb.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    @NonNull
    public Makeup boldify() {
        final StyleSpan span = new StyleSpan(Typeface.BOLD);
        sb.setSpan(span, 0, getLength(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    @NonNull
    public Makeup italize(final int start, final int length) {
        final StyleSpan span = new StyleSpan(Typeface.ITALIC);
        sb.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    @NonNull
    public Makeup italize() {
        final StyleSpan span = new StyleSpan(Typeface.ITALIC);
        sb.setSpan(span, 0, getLength(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    @NonNull
    public Makeup colorize(final int start, final int length, @ColorInt final int color) {
        final ForegroundColorSpan span = new ForegroundColorSpan(color);
        sb.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    @NonNull
    public Makeup colorize(@ColorInt final int color) {
        final ForegroundColorSpan span = new ForegroundColorSpan(color);
        sb.setSpan(span, 0, getLength(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    @NonNull
    public Makeup mark(final int start, final int length, @ColorInt final int color) {
        final BackgroundColorSpan span = new BackgroundColorSpan(color);
        sb.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    @NonNull
    public Makeup mark(final int color) {
        final BackgroundColorSpan span = new BackgroundColorSpan(color);
        sb.setSpan(span, 0, getLength(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    @NonNull
    public Makeup proportionate(final int start, final int length, final float proportion) {
        final RelativeSizeSpan span = new RelativeSizeSpan(proportion);
        sb.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    @NonNull
    public Makeup proportionate(final float proportion) {
        final RelativeSizeSpan span = new RelativeSizeSpan(proportion);
        sb.setSpan(span, 0, getLength(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    @NonNull
    public Spannable apply() {
        return sb;
    }

    private int getLength() {
        return sb.length();
    }
}