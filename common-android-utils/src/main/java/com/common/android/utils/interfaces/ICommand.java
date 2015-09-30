package com.common.android.utils.interfaces;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Jan Rabe on 25/09/15.
 */
public interface ICommand<T> {

    T execute(@NotNull final T t);
}
