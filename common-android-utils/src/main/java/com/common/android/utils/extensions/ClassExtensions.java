package com.common.android.utils.extensions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.lang.reflect.*;
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

    public static <T> T instanceOf(@NonNull final Class<T> clazz) throws Exception {
        return clazz.newInstance();
    }

    public static boolean equals(@NonNull final Class first, @NonNull final Class second) {
        return first.getCanonicalName().equals(second.getCanonicalName());
    }

    public static <T extends Enum<T>> T getRandom(@NonNull final Class<T> enumType) {
        final T[] values = values(enumType);
        return values[randInt(0, values.length - 1)];
    }

    public static <T extends Enum<T>> T[] values(@NonNull final Class<T> enumType) {
        return enumType.getEnumConstants();
    }

    public static <T extends Enum<T>> T[] valuesFromRawType(@NonNull final Class<T> rawType) {
        return rawType.getEnumConstants();
    }

    @NonNull
    public static <E extends Enum<E>> Comparator createNewAlphabeticalEnumComparator() {
        return new Comparator<E>() {
            public int compare(@NonNull final E e1, @NonNull final E e2) {
                return e1.name().compareToIgnoreCase(e2.name());
            }
        };
    }

    public static <E extends Enum<E>> E[] getSortedEnum(@NonNull final Class<E> type) {
        final E[] values = values(type);
        Arrays.sort(values, createNewAlphabeticalEnumComparator());
        return values;
    }

    /**
     * Creates a new instance of a class.
     *
     * @param clazz Class.
     * @param <T>   Should be class type.
     * @return New Instance of given class.
     * @throws IllegalArgumentException if given class has no default constructor.
     */
    @SuppressWarnings("unchecked")
    @NonNull
    public static <Instance> Instance newInstance(@NonNull final Class clazz) {
        Instance instance = null;
        final Constructor<Instance> constructor = (Constructor<Instance>) clazz.getConstructors()[0];
        try {
            instance = constructor.newInstance();
        } catch (final java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        } catch (final InvocationTargetException e) {
            e.printStackTrace();
        }

        if (instance == null)
            throw new IllegalArgumentException(clazz.getCanonicalName() + " has no default constructor!");

        return instance;
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public static <Instance, ConstructorParam> Instance newInstanceWith(@NonNull final Class clazz, @NonNull final ConstructorParam constructorParam) {
        Instance instance = null;
        try {
            final Constructor<Instance> constructor = (Constructor<Instance>) clazz.getDeclaredConstructor(constructorParam.getClass());
            instance = constructor.newInstance(constructorParam);
        } catch (final NoSuchMethodException e) {
            e.printStackTrace();
        } catch (final InstantiationException e) {
            e.printStackTrace();
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        } catch (final InvocationTargetException e) {
            e.printStackTrace();
        }

        if (instance == null)
            throw new IllegalArgumentException(clazz.getCanonicalName() + " has no constructor with parameter: " + constructorParam.getClass().getCanonicalName());

        return instance;
    }

    static void setFinalStatic(@NonNull final Field field, final Object newValue) throws Exception {
        field.setAccessible(true);

        // remove final modifier from field
        final Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }

    private static class TestgetGenericSuperclass<T> {

        // region hack to create new VH at runtime

        @Nullable
        private T newInstance(@NonNull final View view) {

            T instance = null;
            try {
                final Constructor<?>[] constructors = getGenericClass().getConstructors();
                final Constructor<T> constructor = (Constructor<T>) constructors[0];
                instance = constructor.newInstance(view);
            } catch (final IllegalAccessException e) {
                e.printStackTrace();
            } catch (final InstantiationException e) {
                e.printStackTrace();
            } catch (final InvocationTargetException e) {
                e.printStackTrace();
            } catch (final ClassNotFoundException e) {
                e.printStackTrace();
            }

            if (instance == null)
                throw new IllegalArgumentException(inferredClass.getClass().getCanonicalName() + " has no constructor with parameter: " + view.getClass().getCanonicalName() + " make sure it is static!");

            return instance;
        }

        private Class<?> inferredClass;

        private Class<?> getGenericClass() throws ClassNotFoundException {
            if (inferredClass == null) {
                final Type mySuperclass = getClass().getGenericSuperclass();

                try {
                    final Type tType = ((ParameterizedType) mySuperclass).getActualTypeArguments()[1];
                    final String className = tType.toString().split(" ")[1];
                    inferredClass = Class.forName(className);
                } catch (final ClassCastException e) {
                    throw new IllegalStateException("Subclassing is not supported.");
                }
            }
            return inferredClass;
        }
    }
}