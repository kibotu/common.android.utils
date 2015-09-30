package com.common.android.utils.misc;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class ListOfJson<T> implements ParameterizedType {
    private Class<?> wrapped;

    public ListOfJson(final Class<T> wrapper) {
        this.wrapped = wrapper;
    }

    @NotNull
    @Override
    public Type[] getActualTypeArguments() {
        return new Type[]{wrapped};
    }

    @NotNull
    @Override
    public Type getRawType() {
        return List.class;
    }

    @Nullable
    @Override
    public Type getOwnerType() {
        return null;
    }
}