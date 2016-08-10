package com.common.android.utils.extensions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;

import static com.common.android.utils.extensions.ByteExtensions.validUTF8;

/**
 * Created by Jan Rabe on 14/10/15.
 */
final public class StringExtensions {

    private StringExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    @NonNull
    public static String clamp(@Nullable final String s, final int maxLength) {
        if (TextUtils.isEmpty(s))
            return "";

        return s.substring(0, (int) MathExtensions.clamp(s.length(), 0, maxLength));
    }

    /**
     * http://stackoverflow.com/a/1447720
     */
    public static String fixEncoding(String latin1) {
        try {
            byte[] bytes = latin1.getBytes("ISO-8859-1");
            if (!validUTF8(bytes))
                return latin1;
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // Impossible, throw unchecked
            throw new IllegalStateException("No Latin1 or UTF-8: " + e.getMessage());
        }
    }
}
