package com.common.android.utils.misc;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Jan Rabe on 20/10/15.
 */
public class GsonProvider {

    @NotNull
    private static final Gson gson = new Gson();

    private GsonProvider() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    @NotNull
    public static Gson getGson() {
        return gson;
    }
}
