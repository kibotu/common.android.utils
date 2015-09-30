package com.common.android.utils.extensions;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Comparator;

import static com.common.android.utils.extensions.MathExtensions.randInt;

/**
 * Created by Jan Rabe on 24/09/15.
 */
public class ClassExtensions {

    private ClassExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static <T> T instanceOf(@NotNull final Class<T> clazz) throws Exception {
        return clazz.newInstance();
    }

    public static boolean equals(@NotNull final Class first, @NotNull final Class second) {
        return first.getCanonicalName().equals(second.getCanonicalName());
    }

    public static <T extends Enum<T>> T getRandom(@NotNull final Class<T> enumType) {
        final T[] values = values(enumType);
        return values[randInt(0, values.length - 1)];
    }

    public static <T extends Enum<T>> T[] values(@NotNull final Class<T> enumType) {
        return enumType.getEnumConstants();
    }

    public static <T extends Enum<T>> T[] valuesFromRawType(@NotNull final Class<T> rawType) {
        return rawType.getEnumConstants();
    }

    @NotNull
    public static <E extends Enum<E>> Comparator createNewAlphabeticalEnumComparator() {
        return new Comparator<E>() {
            public int compare(@NotNull final E e1, @NotNull final E e2) {
                return e1.name().compareToIgnoreCase(e2.name());
            }
        };
    }

    public static <E extends Enum<E>> E[] getSortedEnum(@NotNull final Class<E> type) {
        final E[] values = values(type);
        Arrays.sort(values, createNewAlphabeticalEnumComparator());
        return values;
    }
}