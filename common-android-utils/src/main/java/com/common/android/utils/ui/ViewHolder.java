package com.common.android.utils.ui;

import android.view.View;
import butterknife.ButterKnife;
import com.common.android.utils.interfaces.ICustomFont;
import com.common.android.utils.interfaces.ILocalized;
import com.common.android.utils.interfaces.ILogTag;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Jan Rabe on 17/09/15.
 */
public abstract class ViewHolder<T extends View> implements ILocalized, ILogTag, ICustomFont {

    @NotNull
    public final T itemView;

    public ViewHolder(@NotNull final T t) {
        itemView = t;
        ButterKnife.bind(this, t);
    }

    @NotNull
    @Override
    final public String tag() {
        return getClass().getSimpleName();
    }
}
