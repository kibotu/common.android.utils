package com.common.android.utils.ui;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.common.android.utils.interfaces.LogTag;

import butterknife.ButterKnife;

import static com.common.android.utils.extensions.ViewExtensions.inflate;

/**
 * Created by Jan Rabe on 17/09/15.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder implements LogTag {

    public BaseViewHolder(@NonNull final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public BaseViewHolder(@LayoutRes final int layout, @Nullable final ViewGroup parent) {
        this(inflate(layout, parent));
    }

    @NonNull
    @Override
    final public String tag() {
        return getClass().getSimpleName();
    }
}