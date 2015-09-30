package com.common.android.utils.interfaces;

import android.content.Context;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Jan Rabe on 22/09/15.
 */
public interface IScreenProvider {
    String getScreenName(@NotNull final Context context);
}
