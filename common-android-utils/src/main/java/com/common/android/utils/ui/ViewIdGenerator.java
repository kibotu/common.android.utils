package com.common.android.utils.ui;

import android.os.Build;
import android.util.Log;
import android.view.View;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Jan Rabe on 24/08/15.
 */
public class ViewIdGenerator {

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    private static final String TAG = ViewIdGenerator.class.getSimpleName();

    public static int generateViewId() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            return View.generateViewId();

        while (true) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                Log.v(TAG, "next generated id: " + result);
                return result;
            }
        }
    }
}
