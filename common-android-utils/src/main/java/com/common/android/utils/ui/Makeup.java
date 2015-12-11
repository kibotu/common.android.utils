package com.common.android.utils.ui;

import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.*;

/**
 * Credit: <a href="https://gist.github.com/Trikke/90efd4432fc09aaadf3e">https://gist.github.com/Trikke/90efd4432fc09aaadf3e</a>
 */
public class Makeup {

    private final Spannable sb;

    public Makeup(String input) {
        sb = new SpannableString(input);
    }

    public Makeup strikethrough(int start, int length) {
        final StrikethroughSpan span = new StrikethroughSpan();
        sb.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public Makeup strikethrough() {
        final StrikethroughSpan span = new StrikethroughSpan();
        sb.setSpan(span, 0, getLength(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public Makeup underline(int start, int length) {
        final UnderlineSpan span = new UnderlineSpan();
        sb.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public Makeup underline() {
        final UnderlineSpan span = new UnderlineSpan();
        sb.setSpan(span, 0, getLength(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public Makeup boldify(int start, int length) {
        final StyleSpan span = new StyleSpan(Typeface.BOLD);
        sb.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public Makeup boldify() {
        final StyleSpan span = new StyleSpan(Typeface.BOLD);
        sb.setSpan(span, 0, getLength(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public Makeup italize(int start, int length) {
        final StyleSpan span = new StyleSpan(Typeface.ITALIC);
        sb.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public Makeup italize() {
        final StyleSpan span = new StyleSpan(Typeface.ITALIC);
        sb.setSpan(span, 0, getLength(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public Makeup colorize(int start, int length, @ColorInt int color) {
        final ForegroundColorSpan span = new ForegroundColorSpan(color);
        sb.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public Makeup colorize(@ColorInt int color) {
        final ForegroundColorSpan span = new ForegroundColorSpan(color);
        sb.setSpan(span, 0, getLength(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public Makeup mark(int start, int length, @ColorInt int color) {
        final BackgroundColorSpan span = new BackgroundColorSpan(color);
        sb.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public Makeup mark(int color) {
        final BackgroundColorSpan span = new BackgroundColorSpan(color);
        sb.setSpan(span, 0, getLength(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public Makeup proportionate(int start, int length, float proportion) {
        final RelativeSizeSpan span = new RelativeSizeSpan(proportion);
        sb.setSpan(span, start, start + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public Makeup proportionate(float proportion) {
        final RelativeSizeSpan span = new RelativeSizeSpan(proportion);
        sb.setSpan(span, 0, getLength(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public Spannable apply() {
        return sb;
    }

    private int getLength() {
        return sb.length();
    }
}