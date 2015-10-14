package com.common.android.utils.ui.recyclerView;

import android.view.View;
import org.jetbrains.annotations.NotNull;

public interface IOnItemFocusChangeListener<T> {
    void onFocusChange(@NotNull final T item, @NotNull final View view, boolean hasFocus);
}