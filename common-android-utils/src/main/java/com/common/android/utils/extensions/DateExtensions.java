package com.common.android.utils.extensions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jan Rabe on 24/09/15.
 */
final public class DateExtensions {

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

    public static long getTimeZoneOffset() {
        return getTimeZoneOffset(Locale.getDefault());
    }

    public static long getTimeZoneOffset(Locale locale) {
        Calendar calendar = new GregorianCalendar(locale);
        TimeZone timeZone = calendar.getTimeZone();
        int GMTOffset = timeZone.getOffset(new Date().getTime());
        return TimeUnit.MINUTES.convert(GMTOffset, TimeUnit.MILLISECONDS);
    }

    public static boolean usesDayLightSaving() {
        return TimeZone.getDefault().inDaylightTime(new Date());
    }
}
