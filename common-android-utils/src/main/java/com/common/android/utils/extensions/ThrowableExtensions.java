package com.common.android.utils.extensions;

/**
 * Created by <a href="https://about.me/janrabe">Jan Rabe</a>.
 */

public class ThrowableExtensions {

    /**
     * Rethrow an {@link Throwable} preserving the stack trace but making it unchecked.
     *
     * @param ex to be rethrown and unchecked.
     */
    public static void rethrowUnchecked(final Throwable ex) {
        ThrowableExtensions.rethrow(ex);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Throwable> void rethrow(final Throwable t) throws T {
        throw (T) t;
    }
}
