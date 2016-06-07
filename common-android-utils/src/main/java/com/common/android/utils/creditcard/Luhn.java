package com.common.android.utils.creditcard;

import android.support.annotation.Nullable;

import static android.text.TextUtils.isEmpty;

/**
 * Luhn Class is an implementation of the Luhn algorithm that checks validity of a credit card number.
 *
 * @author <a href="http://www.chriswareham.demon.co.uk/software/Luhn.java">Chris Wareham</a>
 */
public class Luhn {

    /**
     * <p>Checks whether a string of digits is a valid credit card number according to the Luhn algorithm.</p>
     * <p>
     * <pre>
     * 1. Starting with the second to last digit and moving left, double the value of all the alternating digits. For any digits that thus become 10 or more, add their digits together.
     * For example, 1111 becomes 2121, while 8763 becomes 7733 (from (1+6)7(1+2)3).
     *
     * 2. Add all these digits together.
     * For example,
     *
     * 1111 becomes 2121,
     *  then 2+1+2+1 is 6;
     * while 8763 becomes 7733,
     *  then 7+7+3+3 is 20.
     *
     * 3. If the total ends in 0 (put another way, if the total modulus 10 is 0), then the number is valid according to the Luhn formula, else it is not valid.
     * So, 1111 is not valid (as shown above, it comes out to 6),
     * while 8763 is valid (as shown above, it comes out to 20).
     * </pre>
     *
     * @param ccNumber the credit card number to validate.
     * @return <b>true</b> if the number is valid, <b>false</b> otherwise.
     */
    public static boolean check(@Nullable final String ccNumber) {
        if (isEmpty(ccNumber))
            return false;

        int sum = 0;
        boolean alternate = false;
        for (int i = ccNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(ccNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }

    private static String getDigits(String s) {
        final StringBuilder digitsOnly = new StringBuilder();
        char c;
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            if (Character.isDigit(c))
                digitsOnly.append(c);
        }
        return digitsOnly.toString();
    }

    /**
     * Perform Luhn check
     *
     * @param cardNumber
     * @return
     */
    public static boolean c(String cardNumber) {
        String digitsOnly = getDigits(cardNumber);
        int sum = 0;
        int digit = 0;
        int addend = 0;
        boolean timesTwo = false;
        for (int i = digitsOnly.length() - 1; i >= 0; i--) {
            digit = Integer.parseInt(digitsOnly.substring(i, i + 1));
            if (timesTwo) {
                addend = digit * 2;
                if (addend > 9)
                    addend -= 9;
            } else
                addend = digit;
            sum += addend;
            timesTwo = !timesTwo;
        }
        int modulus = sum % 10;
        return modulus == 0;
    }
}
