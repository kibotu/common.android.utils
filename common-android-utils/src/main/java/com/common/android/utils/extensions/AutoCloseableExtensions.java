package com.common.android.utils.extensions;

import android.annotation.SuppressLint;

import com.common.android.utils.logging.Logger;

public class AutoCloseableExtensions {

    private static final String TAG = AutoCloseableExtensions.class.getSimpleName();

    /**
     * Quietly close a {@link AutoCloseable} dealing with nulls and exceptions.
     *
     * @param closeable to be closed.
     */
    @SuppressLint("NewApi")
    public static void quietClose(final AutoCloseable closeable) {
        try {
            if (null != closeable) {
                Logger.v(TAG, "[quietClose] " + closeable.getClass().getSimpleName());
                closeable.close();
            }
        } catch (final Exception ignore) {
        }
    }

    /**
     * Close a {@link AutoCloseable} dealing with nulls and exceptions.
     * This version re-throws exceptions as runtime exceptions.
     *
     * @param closeable to be closed.
     */
    @SuppressLint("NewApi")
    public static void close(final AutoCloseable closeable) {
        try {
            if (null != closeable) {
                Logger.v(TAG, "[close] " + closeable.getClass().getSimpleName());
                closeable.close();
            }
        } catch (final Exception e) {
            ThrowableExtensions.rethrowUnchecked(e);
        }
    }
}