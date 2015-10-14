package com.common.android.utils.extensions;

import android.text.TextUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Jan Rabe on 14/10/15.
 */
public class StringExtensions {

    public StringExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    @NotNull
    public static String clamp(@Nullable final String s, final int maxLength) {
        if (TextUtils.isEmpty(s))
            return "";

        return s.substring(0, (int) MathExtensions.clamp(s.length(), 0, maxLength));
    }
}
