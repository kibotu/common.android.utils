package com.common.android.utils.interfaces;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by Jan Rabe on 22/09/15.
 */
public interface ScreenNameProvider {
    @NonNull
    String getScreenName(@NonNull final Context context);
}
