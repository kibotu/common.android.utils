package com.common.android.utils.extensions;

import android.support.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import static android.text.TextUtils.isEmpty;

/**
 * Created by jan.rabe on 18/12/15.
 */
public class ValidationExtensions {


    public static boolean isValidDateExpirationDate(@Nullable Date date, @NotNull Locale locale) {
        if (date == null)
            return false;

        int creditCardYear = CalendarExtensions.getYear(date, locale);
        int currentYear = Calendar.getInstance(locale).get(Calendar.YEAR);
        int creditCardMonth = CalendarExtensions.getMonth(date, locale);
        int currentMonth = Calendar.getInstance(locale).get(Calendar.MONTH);

        return creditCardYear > currentYear
                || (creditCardYear >= currentYear
                && creditCardMonth >= currentMonth);
    }

    public static boolean isValidCvv(@Nullable final String cvv) {
        return !isEmpty(cvv) && cvv.matches("^[0-9]{3,4}$");
    }

    /**
     * Regex pattern matcher for credit cards based on <a href="http://www.regular-expressions.info/creditcard.html">http://www.regular-expressions.info/creditcard.html</a>
     */
    public enum CreditCardNumberValidator {
        AdviqoVisa("^4[0-9]{17}?$"),
        Visa("^4[0-9]{12}(?:[0-9]{3})?$"),
        MasterCard("^5[1-5][0-9]{14}$"),
        AmericanExpress("^3[47][0-9]{13}$"),
        DinersClub("^3(?:0[0-5]|[68][0-9])[0-9]{11}$"),
        Discover("^6(?:011|5[0-9]{2})[0-9]{12}$"),
        JCB("^(?:2131|1800|35\\d{3})\\d{11}$");

        private final Pattern pattern;

        CreditCardNumberValidator(@NotNull final String pattern) {
            this.pattern = Pattern.compile(pattern);
        }

        public boolean matches(@Nullable final String number) {
            return !isEmpty(number) && pattern.matcher(number).matches();
        }

        public static boolean matchesAny(@Nullable final String number) {
            if (isEmpty(number))
                return false;

            for (final CreditCardNumberValidator validator : values())
                if (validator.matches(number))
                    return true;

            return false;
        }
    }
}
