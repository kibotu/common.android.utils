package com.common.android.utils.misc;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

/**
 * Created by Jan Rabe on 20/10/15.
 */
public class GsonProvider {

    @NonNull
    private static final Gson gson = new Gson();

    private GsonProvider() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    @NonNull
    public static Gson getGson() {
        return gson;
    }
}
