package com.common.android.utils.extensions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jan Rabe on 24/09/15.
 */
public class DateExtensions {

    private DateExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    /**
     * Format dd.MM.yy hh:mm e.g.: 24.09.15 12:26
     */
    @Nullable
    public static Date parseDate(final String date) {
        final DateFormat df = new SimpleDateFormat("dd.MM.yy hh:mm");
        Date result = null;
        try {
            result = df.parse(date);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    @NonNull
    public static String parseDateInHoursMinutesDay(final Date date) {
        final DateFormat hours = new SimpleDateFormat("hh:mm");
        final DateFormat day = new SimpleDateFormat("dd.MM.yy");
        return hours.format(date) + " am " + day.format(date);
    }
}
