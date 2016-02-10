package com.common.android.utils.ui.recyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.common.android.utils.interfaces.ILayout;
import com.common.android.utils.interfaces.ILogTag;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Jan Rabe on 09/09/15.
 */
public abstract class DataBinder<T, VH extends RecyclerView.ViewHolder> implements ILogTag, ILayout {

    @NotNull
    protected final DataBindAdapter<T> dataBindAdapter;

    public DataBinder(@NotNull final DataBindAdapter<T> dataBindAdapter) {
        this.dataBindAdapter = dataBindAdapter;
    }

    @NotNull
    protected VH newViewHolder(@NotNull final ViewGroup parent) {
        return newInstance(LayoutInflater.from(parent.getContext()).inflate(getLayout(), parent, false));
    }

    final public T get(final int position) {
        return dataBindAdapter.get(position);
    }

    public abstract void bindViewHolder(@NotNull final VH viewHolder, final int position);

    @NotNull
    @Override
    final public String tag() {
        return getClass().getSimpleName();
    }

    // region hack to create new VH at runtime

    private VH newInstance(@NotNull final View view) {

        VH instance = null;
        try {
            final Constructor<?>[] constructors = getGenericClass().getConstructors();
            final Constructor<VH> constructor = (Constructor<VH>) constructors[0];
            instance = constructor.newInstance(view);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (instance == null)
            throw new IllegalArgumentException(inferredClass.getClass().getCanonicalName() + " has no constructor with parameter: " + view.getClass().getCanonicalName() + " make sure your ViewHolder is static!");

        return instance;
    }

    private Class<?> inferredClass;

    private Class<?> getGenericClass() throws ClassNotFoundException {
        if (inferredClass == null) {
            Type mySuperclass = getClass().getGenericSuperclass();

            try {
            Type tType = ((ParameterizedType) mySuperclass).getActualTypeArguments()[1];
            String className = tType.toString().split(" ")[1];
            inferredClass = Class.forName(className);
            } catch (ClassCastException e) {
                throw new IllegalStateException("Subclassing is not supported, please override DataBinder#newViewHolder.");
            }
        }
        return inferredClass;
    }

    // endregion
}