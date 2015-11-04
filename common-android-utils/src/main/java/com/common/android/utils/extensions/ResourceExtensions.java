package com.common.android.utils.extensions;

import android.support.annotation.DimenRes;

import static com.common.android.utils.ContextHelper.getContext;

/**
 * Created by Jan Rabe on 27/10/15.
 */
public class ResourceExtensions {

    private ResourceExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static int dimension(@DimenRes final int resId) {
        return (int) getContext().getResources().getDimension(resId);
    }
}
