package com.common.android.utils.ui.recyclerView;

import android.view.View;
import org.jetbrains.annotations.NotNull;

public interface IOnItemClickListener<T> {
    void onItemClick(@NotNull final T item, @NotNull final View rowView, final int position);
}