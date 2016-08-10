package com.common.android.utils.creditcard;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.regex.Pattern;

import static android.text.TextUtils.isEmpty;

/**
 * Regex pattern matcher for credit cards based on <a href="http://www.regular-expressions.info/creditcard.html">http://www.regular-expressions.info/creditcard.html</a>
 * <p>
 * Also see:
 * <p>
 * http://www.richardsramblings.com/regex/credit-card-numbers/
 * https://gist.github.com/claudiosmweb/26d9668f21dbdc787472
 */
public enum CardType {


    VISA("VISA", CardRegex.REGX_VISA, CardRegex.REGX_VISA_TYPE),
    VISA_LONG("VISA", CardRegex.REGX_LONG_VISA, CardRegex.REGX_VISA_TYPE),
    MAESTRO("VISA", CardRegex.REGX_MAESTRO, CardRegex.REGX_VISA_TYPE),
    MASTERCARD("Master Card", CardRegex.REGX_MC, CardRegex.REGX_MC_TYPE),
    AMEX("American Express", CardRegex.REGX_AMEX, CardRegex.REGX_AMEX_TYPE),
    DISCOVER("Discover", CardRegex.REGX_DISCOVER, CardRegex.REGX_DISCOVER_TYPE),
    DINERS("DinersClub", CardRegex.REGX_DINERS_CLUB, CardRegex.REGX_DINERS_CLUB_TYPE),
    JCB("JCB", CardRegex.REGX_JCB, CardRegex.REGX_JCB_TYPE),
    INVALID("Unknown", "", "");

    /**
     * Readable name.
     */
    public final String name;

    /**
     * regex that matches the entire card number
     */
    public final Pattern fullRegex;

    /**
     * regex that will match when there is enough of the card to determine type
     */
    public final Pattern typeRegex;

    CardType(@NonNull final String name, @NonNull final String fullRegex, @NonNull final String typeRegex) {
        this.name = name;
        this.fullRegex = Pattern.compile(fullRegex);
        this.typeRegex = Pattern.compile(typeRegex);
    }

    public boolean matches(@Nullable final String number) {
        return !isEmpty(number) && fullRegex.matcher(number).matches();
    }

    public static boolean matchesAny(@Nullable final String number) {
        if (isEmpty(number))
            return false;

        for (final CardType validator : values())
            if (validator.matches(number))
                return true;

        return false;
    }
}
