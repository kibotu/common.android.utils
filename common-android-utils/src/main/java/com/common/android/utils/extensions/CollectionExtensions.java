package com.common.android.utils.extensions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Jan Rabe on 24/09/15.
 */
final public class CollectionExtensions {

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

    public static boolean isEmpty(@Nullable final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static int size(@Nullable final Collection<?> collection) {
        return isEmpty(collection)
                ? 0
                : collection.size();
    }

    public static boolean isEmpty(@Nullable Map<?, ?> map) {
        return map == null || map.isEmpty();
    }
}
