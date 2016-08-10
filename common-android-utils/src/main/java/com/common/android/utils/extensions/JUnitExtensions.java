package com.common.android.utils.extensions;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jan.rabe on 10/08/16.
 */

public class JUnitExtensions {

    private JUnitExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static boolean isJUnitTest() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        List<StackTraceElement> list = Arrays.asList(stackTrace);
        for (StackTraceElement element : list) {
            if (element.getClassName().startsWith("org.junit.")) {
                return true;
            }
        }
        return false;
    }
}
