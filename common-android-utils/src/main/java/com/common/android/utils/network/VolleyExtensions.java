package com.common.android.utils.network;

import android.support.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.common.android.utils.logging.Logger;

/**
 * Created by Jan Rabe on 26/11/15.
 */
public class VolleyExtensions {

    private static final String TAG = VolleyExtensions.class.getSimpleName();

    public static void printVolleyError(@Nullable final VolleyError volleyError) {
        String body = null;
        int statusCode = 0;
        String message = null;
        try {
            message = volleyError.getMessage();
            final NetworkResponse networkResponse = volleyError.networkResponse;
            if (networkResponse != null) {
                body = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
                statusCode = networkResponse.statusCode;
            } else
                body = volleyError.getClass().getSimpleName();
        } catch (final Exception e) {
            e.printStackTrace();
        }

        Logger.w(TAG, "[" + statusCode + "] " + message + " body: " + body);
    }
}
