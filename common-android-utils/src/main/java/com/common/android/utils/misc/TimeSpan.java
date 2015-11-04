package com.common.android.utils.misc;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

/**
 * Created by Jan Rabe on 26/10/15.
 */
public class TimeSpan {

    /**
     * @param timeSpan in seconds
     * @return the time as String in the format hh:mm:ss
     */
    @NotNull
    public static String toString(final long timeSpan) {
        int timeSeconds = (int) (timeSpan / 1000);
        int timeMinutes = timeSeconds / 60;
        timeSeconds = timeSeconds % 60;
        final int timeHours = timeMinutes / 60;
        timeMinutes = timeMinutes % 60;
        return MessageFormat.format("{0}{1}:{2}{3}:{4}{5}", timeHours / 10, timeHours % 10, timeMinutes / 10, timeMinutes % 10, timeSeconds / 10, timeSeconds % 10);
    }
}
