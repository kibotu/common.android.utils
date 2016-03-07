package com.common.android.utils.extensions;

import android.support.annotation.NonNull;

/**
 * Created by Jan Rabe on 26/10/15.
 */
public class RunnableExtensions {

    public RunnableExtensions() throws IllegalAccessException {
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
