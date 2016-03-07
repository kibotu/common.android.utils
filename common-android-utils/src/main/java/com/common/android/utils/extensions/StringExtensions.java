package com.common.android.utils.extensions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by Jan Rabe on 14/10/15.
 */
public class StringExtensions {

    public StringExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    @NonNull
    public static String clamp(@Nullable final String s, final int maxLength) {
        if (TextUtils.isEmpty(s))
            return "";

        return s.substring(0, (int) MathExtensions.clamp(s.length(), 0, maxLength));
    }
}
