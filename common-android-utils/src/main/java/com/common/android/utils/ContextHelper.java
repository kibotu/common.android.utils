package com.common.android.utils;

import android.support.v4.app.FragmentActivity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Jan Rabe on 30/09/15.
 */
public class ContextHelper {

    @Nullable
    private static FragmentActivity context;

    public ContextHelper() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    @NotNull
    public static FragmentActivity getContext() {
        if (context == null)
            throw new IllegalStateException("Please call ContextHelper.setContext() when your activity starts.");
        return context;
    }

    public static void setContext(@NotNull final FragmentActivity context) {
        ContextHelper.context = context;
    }
}
