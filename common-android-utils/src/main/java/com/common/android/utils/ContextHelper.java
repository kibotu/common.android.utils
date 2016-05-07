package com.common.android.utils;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Jan Rabe on 30/09/15.
 */
public class ContextHelper {

    @Nullable
    Application application;

    @Nullable
    private static FragmentActivity context;

    public ContextHelper() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    @NonNull
    public static FragmentActivity getContext() {
        if (context == null)
            throw new IllegalStateException("Please call ContextHelper.setContext() when your activity starts.");
        return context;
    }

    public static void setContext(@NonNull final FragmentActivity context) {
        ContextHelper.context = context;
    }

    @Nullable
    public Application getApplication() {
        return application;
    }

    public void setApplication(@NonNull Application application) {
        this.application = application;
    }
}
