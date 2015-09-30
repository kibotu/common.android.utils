package com.common.android.utils.ui.recyclerView;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.common.android.utils.interfaces.ILogTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>An implementation of {@link android.widget.BaseAdapter} which uses the new/bind pattern for its
 * views based on Jake Warthon work https://plus.google.com/+JakeWharton/posts (Thank you so much!)</p>
 * <p/>
 * <p>The pattern new/bind consist in an abstract class with 2 methods.
 * <ul><li><code>newView</code> to implement the code to instance new list item views</li>
 * <li>bindView to implement the code of configure a concrete list item</li></ul><p>
 * <p/>
 * Created by Marcos Trujillo (╯°□°）╯︵ ┻━┻ on 27/01/14.
 */
public abstract class ItemRecyclerAdapter<T, VH extends ItemViewHolder<T>> extends RecyclerView.Adapter<VH> implements ILogTag {

    @NotNull
    protected final Context context;
    @NotNull
    protected final List<T> dataList = Collections.synchronizedList(new ArrayList<T>());
    @NotNull
    protected final Map<T, VH> viewHolder = new ConcurrentHashMap<>();
    @NotNull
    private final LayoutInflater mLayoutInflater;
    private final Handler handler;
    private Runnable notifyChangeListener;
    protected OnItemClickListener<T> listener;

    public ItemRecyclerAdapter(@NotNull final Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        handler = new Handler(Looper.getMainLooper());
        notifyChangeListener = createNotifyDataSetChangedRunnable();
    }

    final public String tag() {
        return getClass().getSimpleName();
    }

    public VH getViewHolder(T t) {
        return viewHolder.get(t);
    }

    @Nullable
    public List<T> getItems() {
        return dataList;
    }


    @Override
    public long getItemId(final int position) {
        return position << 31 + new Random().nextInt();
    }

    @NotNull
    protected LayoutInflater getLayoutInflater() {
        return mLayoutInflater;
    }

    @Override
    public abstract VH onCreateViewHolder(ViewGroup viewGroup, int viewType);

    @Override
    public final void onBindViewHolder(@NotNull final VH vh, final int viewType) {
        final T item = getItem(vh.getAdapterPosition());
        vh.bindItem(item, listener);
        onBindViewHolder(vh, viewType, item);
        if (item == null)
            Log.v(tag(), "item is empty");
        else
            viewHolder.put(item, vh);
    }

    public abstract void onBindViewHolder(VH holder, int position, T item);

    @Nullable
    public T getItem(final int position) {
        if (dataList.size() > position && position >= 0)
            return dataList.get(position);
        return null;
    }

    public int getPosition(@NotNull final T t) {
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i) == t)
                return i;
        }
        throw new IndexOutOfBoundsException(t.toString() + " not in list.");
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void set(@Nullable final List<T> objectList) {
        if (objectList == null)
            return;

        if (!dataList.isEmpty())
            dataList.clear();

        dataList.addAll(objectList);
        notifyDataSetChangedPost();
    }

    public void set(@Nullable final T object) {
        if (object == null)
            return;

        dataList.clear();
        dataList.add(object);
        notifyDataSetChangedPost();
    }

    public void add(final T object) {
        add(object, dataList.size());
    }

    public void add(final T object, final int position) {
        dataList.add(position, object);
        notifyDataSetChanged();
    }

    public void addAll(final List<T> objectList) {
        addAll(objectList, dataList.size());
    }

    public void addAll(@Nullable final List<T> objectList, final int position) {
        if (objectList == null)
            return;

        dataList.addAll(position, objectList);
        notifyDataSetChangedPost();
    }

    public void remove(final int position) {
        if (position < 0 || dataList.size() <= position)
            return;

        dataList.remove(position);
        notifyItemRemoved(position);
    }

    public void remove(@Nullable final T object) {
        if (object == null)
            return;

        final boolean removed = dataList.remove(object);
        Log.d(tag(), "removed: " + String.valueOf(removed));
        notifyDataSetChangedPost();
    }

    public void removeAll(@Nullable final List<T> objectList) {
        if (objectList == null)
            return;

        dataList.removeAll(objectList);
        notifyDataSetChangedPost();
    }

    public boolean performClick(int position) {
        if (listener != null && position >= 0 && dataList.size() > position) {
            listener.onItemClick(getItem(position), null, position);
            return true;
        }
        return false;
    }

    protected void notifyDataSetChangedPost() {
        handler.post(notifyChangeListener);
    }

    @NotNull
    private Runnable createNotifyDataSetChangedRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                ItemRecyclerAdapter.super.notifyDataSetChanged();
            }
        };
    }

    public OnItemClickListener<T> getOnItemClickListener() {
        return listener;
    }

    public void setOnItemClickListener(final OnItemClickListener<T> listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(T item, View rowView, int position);
    }
}