package com.common.android.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import net.kibotu.android.deviceinfo.library.Device;

/**
 * Created by Jan Rabe on 30/09/15.
 */
public class ContextHelper {

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
        Device.setContext(context);
        ContextHelper.context = context;
    }
}
