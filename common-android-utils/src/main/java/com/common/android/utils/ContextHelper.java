package com.common.android.utils;

import android.support.v7.app.AppCompatActivity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Jan Rabe on 30/09/15.
 */
public class ContextHelper {

    @Nullable
    private static AppCompatActivity context;

    public ContextHelper() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    @NotNull
    public static AppCompatActivity getContext() {
        if (context == null)
            throw new IllegalStateException("Please call ContextHelper.setContext() when your activity starts.");
        return context;
    }

    public static void setContext(@NotNull final AppCompatActivity context) {
        ContextHelper.context = context;
    }
}
