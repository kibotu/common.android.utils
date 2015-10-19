package com.common.android.utils.ui.recyclerView;

import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.common.android.utils.extensions.ClassExtensions;
import com.common.android.utils.interfaces.ILogTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jan Rabe on 09/09/15.
 */
public class DataBindAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ILogTag {

    @NotNull
    private final ArrayList<Pair<T, Class>> data;

    @Nullable
    private IOnItemClickListener<T> onItemClickListener;

    @Nullable
    private IOnItemFocusChangeListener<T> onItemFocusChangeListener;

    @Nullable
    private View.OnKeyListener onKeyListener;

    @NotNull
    private List<DataBinder<T, ?>> binderType;

    public DataBindAdapter() {
        this.data = new ArrayList<>();
        this.binderType = new ArrayList<>();
    }

    protected <VH extends RecyclerView.ViewHolder> void addBinder(@NotNull final DataBinder<T, VH> binder) {
        binderType.add(binder);
    }

    @Nullable
    public IOnItemClickListener<T> getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(@Nullable final IOnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Nullable
    public IOnItemFocusChangeListener<T> getOnItemFocusChangeListener() {
        return onItemFocusChangeListener;
    }

    public void setOnItemFocusChangeListener(@Nullable final IOnItemFocusChangeListener<T> onItemFocusChangeListener) {
        this.onItemFocusChangeListener = onItemFocusChangeListener;
    }

    @Nullable
    public View.OnKeyListener getOnKeyListener() {
        return onKeyListener;
    }

    public void setOnKeyListener(@Nullable final View.OnKeyListener onKeyListener) {
        this.onKeyListener = onKeyListener;
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull final ViewGroup parent, final int viewType) {
        return getDataBinder(viewType).newViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        getPosition(position).bindViewHolder(viewHolder, position);
    }

    public void add(final int index, @NotNull final T t, @NotNull final Class clazz) {
        data.add(index, new Pair<>(t, clazz));
        addIfNotExists(clazz);
        notifyItemInserted(index);
    }

    public void add(@NotNull final T t, @NotNull final Class clazz) {
        data.add(new Pair<>(t, clazz));
        addIfNotExists(clazz);
        notifyItemInserted(data.size() - 1);
    }

    @SuppressWarnings("unchecked")
    private void addIfNotExists(@NotNull final Class clazz) {
        for (final DataBinder<T, ?> binderType : this.binderType)
            if (ClassExtensions.equals(binderType.getClass(), clazz))
                return;

        final Constructor<T> constructor = (Constructor<T>) clazz.getConstructors()[0];
        DataBinder<T, ?> instance = null;
        try {
            instance = (DataBinder<T, ?>) constructor.newInstance(this);
            binderType.add(instance);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        if (instance == null)
            throw new IllegalArgumentException(clazz.getCanonicalName() + " has no constructor with parameter: " + getClass().getCanonicalName());
    }

    public T get(final int position) {
        return data.get(position).first;
    }

    public int getItemViewType(final int position) {
        for (int i = 0; i < binderType.size(); ++i)
            if (ClassExtensions.equals(data.get(position).second, binderType.get(i).getClass()))
                return i;

        return 0;
    }

    private DataBinder<T, ? extends RecyclerView.ViewHolder> getDataBinder(final int viewType) {
        return binderType.get(viewType);
    }


    private DataBinder getPosition(final int position) {
        return binderType.get(getItemViewType(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void clear() {
        binderType.clear();
        data.clear();
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    final public String tag() {
        return getClass().getSimpleName();
    }
}