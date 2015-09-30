package common.android.utils.extensions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Jan Rabe on 24/09/15.
 */
public class CollectionExtensions {

    private CollectionExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    @NotNull
    public static <T> Collection<T> intersect(@NotNull final Collection<? extends T> a, @NotNull final Collection<? extends T> b) {
        final Collection<T> result = new ArrayList<T>();
        for (final T t : a) {
            if (b.remove(t)) result.add(t);
        }
        return result;
    }
}
