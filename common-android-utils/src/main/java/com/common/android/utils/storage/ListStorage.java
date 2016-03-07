package com.common.android.utils.storage;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import com.common.android.utils.interfaces.StorageProvider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jan Rabe on 01/09/15.
 * Use Hawk instead.
 */
@Deprecated
public class ListStorage<T> implements StorageProvider<T> {

    private final Type listType = new TypeToken<ArrayList<T>>() {
    }.getType();

    @NonNull
    protected final static Gson gson = new Gson();

    @NonNull
    protected Context context;
    @NonNull
    private final String key;

    public ListStorage(@NonNull final Context context, @NonNull final String key) {
        this.context = context;
        this.key = key;
    }

    @Override
    public boolean create(final T t) {
        final List<T> list = read();
        final boolean success = list.add(t);
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(key, gson.toJson(list))
                .apply();
        return success;
    }

    @Override
    public List<T> read() {
        return gson.fromJson(PreferenceManager.getDefaultSharedPreferences(context).getString(key, createNewEmptyJson()), listType);
    }

    private String createNewEmptyJson() {
        return gson.toJson(new ArrayList<T>());
    }

    @Override
    public boolean delete(final T t) {
        final List<T> list = read();
        final boolean success = list.remove(t);
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(key, gson.toJson(list))
                .apply();
        return success;
    }

    @Override
    public void clear() {
        final List<T> list = read();
        list.clear();
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(key, createNewEmptyJson())
                .apply();
    }

    @Override
    public boolean contains(final T t) {
        return read().contains(t);
    }
}
