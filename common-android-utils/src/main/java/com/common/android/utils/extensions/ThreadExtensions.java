package com.common.android.utils.extensions;

import android.support.annotation.NonNull;
import com.common.android.utils.logging.Logger;

/**
 * Created by Jan Rabe on 22/10/15.
 */
public class ThreadExtensions {

    private static final String TAG = ThreadExtensions.class.getSimpleName();

    private ThreadExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static void setThreadPriority(final int priority) {
        Logger.v(TAG, "From thread priority: " + android.os.Process.getThreadPriority(android.os.Process.myTid()));
        android.os.Process.setThreadPriority(priority);
        Logger.v(TAG, "To thread priority: " + android.os.Process.getThreadPriority(android.os.Process.myTid()));
    }

    public static void safeSleep(final int millisecond) {
        try {
            Thread.sleep(millisecond);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void startNewThread(@NonNull final Runnable runnable) {
        new Thread(runnable).start();
    }
}