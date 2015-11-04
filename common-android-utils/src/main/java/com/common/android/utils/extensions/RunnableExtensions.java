package com.common.android.utils.extensions;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Jan Rabe on 26/10/15.
 */
public class RunnableExtensions {

    public RunnableExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static void runSafely(@NotNull final Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
