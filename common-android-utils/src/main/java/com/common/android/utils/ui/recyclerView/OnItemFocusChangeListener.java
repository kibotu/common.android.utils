package com.common.android.utils.ui.recyclerView;

import android.support.annotation.NonNull;
import android.view.View;

public interface OnItemFocusChangeListener<T> {
    void onFocusChange(@NonNull final T item, @NonNull final View view, boolean hasFocus);
}