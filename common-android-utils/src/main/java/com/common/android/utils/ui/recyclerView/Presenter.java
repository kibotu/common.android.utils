package com.common.android.utils.ui.recyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.common.android.utils.interfaces.LayoutProvider;
import com.common.android.utils.interfaces.LogTag;

/**
 * Created by Jan Rabe on 09/09/15.
 */
public abstract class Presenter<T, VH extends RecyclerView.ViewHolder> implements LogTag, LayoutProvider {

    @NonNull
    protected final PresenterAdapter<T> presenterAdapter;

    public Presenter(@NonNull final PresenterAdapter<T> presenterAdapter) {
        this.presenterAdapter = presenterAdapter;
    }

    @NonNull
    protected VH onCreateViewHolder(@NonNull final ViewGroup parent) {
        return createViewHolder(getLayout(), parent);
    }

    @NonNull
    protected abstract VH createViewHolder(int layout, ViewGroup parent);

    final public T get(final int position) {
        return presenterAdapter.get(position);
    }

    public abstract void bindViewHolder(@NonNull final VH viewHolder, T item, final int position);

    @NonNull
    @Override
    public String tag() {
        return getClass().getSimpleName();
    }
}