package com.common.android.utils.creditcard;

/**
 * Created by jan.rabe on 07/06/16.
 * See: <a href="http://www.regular-expressions.info/creditcard.html">http://www.regular-expressions.info/creditcard.html</a>
 */
public class CardRegex {

    private CardRegex() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    static final String REGX_VISA = "^4[0-9]{15}?"; // VISA 16
    static final String REGX_MAESTRO = "^(?:5[0678]\\d\\d|6304|6390|67\\d\\d)\\d{8,15}$/?"; // MAESTRO 19
    static final String REGX_LONG_VISA = "^4[0-9]{17}?"; // VISA 18
    static final String REGX_MC = "^5[1-5][0-9]{14}$"; // MC 16
    static final String REGX_AMEX = "^3[47][0-9]{13}$"; // AMEX 15
    static final String REGX_DISCOVER = "^6(?:011|5[0-9]{2})[0-9]{12}$"; // Discover 16
    static final String REGX_DINERS_CLUB = "^3(?:0[0-5]|[68][0-9])[0-9]{11}$"; // DinersClub 14
    static final String REGX_JCB = "^35[0-9]{14}$"; // JCB 16

    static final String REGX_VISA_TYPE = "^4[0-9]{3}?"; // VISA 16
    static final String REGX_MC_TYPE = "^5[1-5][0-9]{2}$"; // MC 16
    static final String REGX_AMEX_TYPE = "^3[47][0-9]{2}$"; // AMEX 15
    static final String REGX_DISCOVER_TYPE = "^6(?:011|5[0-9]{2})$"; // Discover 16
    static final String REGX_DINERS_CLUB_TYPE = "^3(?:0[0-5]|[68][0-9])[0-9]$"; // DinersClub 14
    static final String REGX_JCB_TYPE = "^35[0-9]{2}$"; // JCB 15
}
