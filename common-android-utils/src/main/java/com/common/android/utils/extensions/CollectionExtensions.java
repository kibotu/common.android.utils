package com.common.android.utils.extensions;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Jan Rabe on 24/09/15.
 */
public class CollectionExtensions {

    private CollectionExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    @NonNull
    public static <T> Collection<T> intersect(@NonNull final Collection<? extends T> a, @NonNull final Collection<? extends T> b) {
        final Collection<T> result = new ArrayList<T>();
        for (final T t : a) {
            if (b.remove(t)) result.add(t);
        }
        return result;
    }
}
