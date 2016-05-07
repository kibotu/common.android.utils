package com.common.android.utils.ui.recyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.common.android.utils.interfaces.LayoutProvider;
import com.common.android.utils.interfaces.LogTag;

/**
 * Created by Jan Rabe on 09/09/15.
 */
public abstract class DataBinder<T, VH extends RecyclerView.ViewHolder> implements LogTag, LayoutProvider {

    @NonNull
    protected final DataBindAdapter<T> dataBindAdapter;

    public DataBinder(@NonNull final DataBindAdapter<T> dataBindAdapter) {
        this.dataBindAdapter = dataBindAdapter;
    }

    @NonNull
    protected VH newViewHolder(@NonNull final ViewGroup parent) {
        return createViewHolder(getLayout(), parent);
    }

    @NonNull
    protected abstract VH createViewHolder(int layout, ViewGroup parent);

    final public T get(final int position) {
        return dataBindAdapter.get(position);
    }

    public abstract void bindViewHolder(@NonNull final VH viewHolder, final int position);

    @NonNull
    @Override
    final public String tag() {
        return getClass().getSimpleName();
    }


    // endregion
}