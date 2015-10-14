package com.common.android.utils.ui.recyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.common.android.utils.interfaces.ILogTag;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Jan Rabe on 09/09/15.
 */
public abstract class DataBinder<T, VH extends RecyclerView.ViewHolder> implements ILogTag {

    @NotNull
    protected final DataBindAdapter<T> dataBindAdapter;

    public DataBinder(@NotNull final DataBindAdapter<T> dataBindAdapter) {
        this.dataBindAdapter = dataBindAdapter;
    }

    @NotNull
    public abstract VH newViewHolder(@NotNull final ViewGroup parent);

    final public T get(final int position) {
        return dataBindAdapter.get(position);
    }

    public abstract void bindViewHolder(@NotNull final VH viewHolder, final int position);

    @NotNull
    @Override
    final public String tag() {
        return getClass().getSimpleName();
    }
}