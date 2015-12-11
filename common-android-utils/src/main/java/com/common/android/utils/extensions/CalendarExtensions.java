package com.common.android.utils.extensions;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    public static Date[] getPastMonths(final int amount) {
        final Calendar calendar = Calendar.getInstance(Locale.GERMANY);
        final Date[] result = new Date[amount];
        for (int i = 0; i < amount; ++i) {
            result[i] = calendar.getTime();
            calendar.roll(Calendar.MONTH, false);
        }
        return result;
    }
}
