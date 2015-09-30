package com.common.android.utils.ui.recyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Viewholder class that has a reference to the cell data element
 * Created by Santi on 10/04/2015.
 */
public abstract class ItemViewHolder<T> extends RecyclerView.ViewHolder {
    protected T item;
    protected int position;
    protected OnItemSelectedListener selectedListener;
    @Nullable
    protected ItemRecyclerAdapter.OnItemClickListener<T> listener;

    public ItemViewHolder(@NotNull final View itemView, final OnItemSelectedListener listener) {
        super(itemView);
        this.selectedListener = listener;
    }

    public ItemViewHolder(@NotNull final View itemView) {
        super(itemView);
    }

    public void bindItem(final T item, @Nullable final ItemRecyclerAdapter.OnItemClickListener<T> listener) {
        this.item = item;
        this.position = getAdapterPosition();
        this.listener = listener;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (selectedListener != null) {
                    selectedListener.onItemSelected(getAdapterPosition());
                }
                if (listener != null) {
                    listener.onItemClick(item, itemView, position);
                }
            }
        });
    }

    public interface OnItemSelectedListener {
        void onItemSelected(int position);
    }

}
