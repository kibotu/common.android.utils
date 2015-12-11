package com.common.android.utils.interfaces;

import android.content.Context;
import org.jetbrains.annotations.Nullable;

public interface ITitleProvider {
    @Nullable
    String getTitle(Context context);
}