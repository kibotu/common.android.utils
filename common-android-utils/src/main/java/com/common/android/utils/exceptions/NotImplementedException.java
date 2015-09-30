package com.common.android.utils.exceptions;

/**
 * Created by jan.rabe on 14/04/15.
 */
public class NotImplementedException extends RuntimeException {

    public NotImplementedException() {
    }

    public NotImplementedException(final String detailMessage) {
        super(detailMessage);
    }

    public NotImplementedException(final String detailMessage, final Throwable throwable) {
        super(detailMessage, throwable);
    }

    public NotImplementedException(final Throwable throwable) {
        super(throwable);
    }
}
