package com.common.android.utils.misc;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Jan Rabe on 20/10/15.
 */
public class GsonProvider {

    @NonNull
    private static final Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .create();

    @NonNull
    private static final Gson gsonPretty = new GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create();

    private GsonProvider() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    @NonNull
    public static Gson getGson() {
        return gson;
    }

    @NonNull
    public static Gson getPrettyPrinting() {
        return gsonPretty;
    }
}
