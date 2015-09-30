package common.android.utils.ui.recyclerView;

import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import common.android.utils.extensions.ClassExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jan Rabe on 09/09/15.
 */
public class DataBindAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NotNull
    private final List<Pair<T, Class>> data;

    @Nullable
    private IOnItemClickListener<T> IOnItemClickListener;

    private List<DataBinder<T, ?>> binderType;

    public DataBindAdapter() {
        this.data = new ArrayList<>();
        this.binderType = new ArrayList<>();
    }

    protected <VH extends RecyclerView.ViewHolder> void addBinder(@NotNull final DataBinder<T, VH> binder) {
        binderType.add(binder);
    }

    public void setOnItemClickListener(@NotNull final IOnItemClickListener<T> IOnItemClickListener) {
        this.IOnItemClickListener = IOnItemClickListener;
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull final ViewGroup parent, final int viewType) {
        return getDataBinder(viewType).newViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        getPosition(position).bindViewHolder(viewHolder, position, IOnItemClickListener);
    }

    public void add(@NotNull final T t, @NotNull final Class clazz) {
        data.add(new Pair<>(t, clazz));
        addIfNotExists(clazz);
        notifyItemRangeChanged(0, data.size() - 1);
    }

    private void addIfNotExists(@NotNull final Class clazz) {
        for (final DataBinder<T, ?> aBinderType : binderType)
            if (ClassExtensions.equals(aBinderType.getClass(), clazz))
                return;

        final Constructor<T> constr = (Constructor<T>) clazz.getConstructors()[0];
        final DataBinder<T, ?> instance;
        try {
            instance = (DataBinder<T, ?>) constr.newInstance(this);
            binderType.add(instance);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
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
        data.clear();
    }
}