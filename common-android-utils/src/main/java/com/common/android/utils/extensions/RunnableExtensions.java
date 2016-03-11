package com.common.android.utils.extensions;

import android.support.annotation.NonNull;

/**
 * Created by Jan Rabe on 26/10/15.
 */
final public class RunnableExtensions {

    private RunnableExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static void runSafely(@NonNull final Runnable runnable) {
        try {
            runnable.run();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
