package com.common.android.utils.extensions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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

    @NonNull
    public static Date[] getPastMonths(final int amount) {
        final Calendar calendar = Calendar.getInstance();
        final Date[] result = new Date[amount];
        for (int i = 0; i < amount; ++i) {
            result[i] = calendar.getTime();
            calendar.add(Calendar.MONTH, -1);
        }
        return result;
    }

    public static int getYear(@Nullable final Date date, @NonNull final Locale locale) {
        if (date == null)
            return 0;

        final Calendar cal = Calendar.getInstance(locale);
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static int getDay(@Nullable final Date date, @NonNull final Locale locale) {
        if (date == null)
            return 0;

        final Calendar cal = Calendar.getInstance(locale);
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static int getMonth(@Nullable final Date date, @NonNull final Locale locale) {
        if (date == null)
            return 0;

        final Calendar cal = Calendar.getInstance(locale);
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }
}
