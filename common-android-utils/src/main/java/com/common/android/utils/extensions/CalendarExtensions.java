package com.common.android.utils.extensions;

import java.util.Calendar;

/**
 * Created by Jan Rabe on 01/10/15.
 */
public class CalendarExtensions {

    public CalendarExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static int getDayFromTimestamp(final long timestamp) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        return cal.get(Calendar.DAY_OF_MONTH);
    }
}
