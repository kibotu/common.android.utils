package com.common.android.utils.ui;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import com.common.android.utils.interfaces.LogTag;
import com.common.android.utils.logging.Logger;
import com.common.android.utils.misc.SynchronizedValue;
import com.sbrukhanda.fragmentviewpager.adapters.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by <a href="https://about.me/janrabe">Jan Rabe</a>.
 */

public class FragmentListPageAdapter<T> extends FragmentPagerAdapter implements LogTag {

    public interface Updateable<T> {
        void updateWith(T t);
    }

    private Object primaryItem;

    public boolean contains(T item) {
        return data.get().contains(item);
    }

    public int indexOf(T item) {
        return data.get().indexOf(item);
    }

    public interface FragmentFactory<T> {
        @NonNull
        Fragment create(FragmentListPageAdapter<T> adapter, int position);
    }

    protected final FragmentFactory<T> fragmentFactory;

    protected final SynchronizedValue<List<T>> data;

    public FragmentListPageAdapter(FragmentManager fragmentManager, FragmentFactory<T> fragmentFactory) {
        super(fragmentManager);
        this.fragmentFactory = fragmentFactory;
        data = new SynchronizedValue<>();
        data.set(new ArrayList<>());
    }

    @Override
    public Fragment instantiateFragment(int position) {
        return fragmentFactory.create(this, position);
    }

    public T get(int index) {
        return data.get().get(index);
    }

    public FragmentListPageAdapter add(T t) {
        data.get().add(t);
        return this;
    }

    public FragmentListPageAdapter update(int index, T model) {
        data.get().set(index, model);
        notifyItemChanged(index);
        return this;
    }

    public FragmentListPageAdapter clear() {
        data.get().clear();
        return this;
    }

    @Override
    public int getCount() {
        return data.get().size();
    }

    public CharSequence getPageTitle(int position) {
        return String.valueOf(position);
    }

    public FragmentListPageAdapter addAll(Collection<T> models) {
        data.get().addAll(models);
        return this;
    }

    public FragmentListPageAdapter remove(T index) {
        data.get().remove(index);
        return this;
    }

    public void update(T item) {
        int index = data.get().indexOf(item);
        if (index != -1)
            update(index, item);
    }

    public T getItem(int index) {
        return data.get().get(index);
    }

    @NonNull
    @Override
    public String tag() {
        return getClass().getSimpleName();
    }

    @Override
    public void notifyDataSetChanged() {

        for (int i = 0; i < getCount(); ++i) {
            Fragment fragment = getFragment(i);
            if (fragment instanceof Updateable)
                ((Updateable<T>) fragment).updateWith(data.get().get(i));
        }

        super.notifyDataSetChanged();
    }

    public void notifyItemChanged(int index) {

        Fragment fragment = getFragment(index);
        if (fragment instanceof Updateable)
            ((Updateable<T>) fragment).updateWith(data.get().get(index));

        super.notifyDataSetChanged();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Logger.v(tag(), "[destroyItem] position=" + position);
        super.destroyItem(container, position, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Logger.v(tag(), "[instantiateItem] position=" + position);
        return super.instantiateItem(container, position);
    }

    @Override
    public int getItemPosition(Object object) {
        return primaryItem == object
                ? super.getItemPosition(object)
                : POSITION_NONE;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        primaryItem = object;
        super.setPrimaryItem(container, position, object);
    }
}
