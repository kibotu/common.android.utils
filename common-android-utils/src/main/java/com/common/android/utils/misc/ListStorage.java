package com.common.android.utils.misc;

import android.content.Context;
import android.preference.PreferenceManager;
import com.common.android.utils.interfaces.IStorage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jan Rabe on 01/09/15.
 */
public class ListStorage<T> implements IStorage<T> {

    private final Type listType = new TypeToken<ArrayList<T>>() {
    }.getType();

    @NotNull
    protected final static Gson gson = new Gson();

    @NotNull
    protected Context context;
    @NotNull
    private final String key;

    public ListStorage(@NotNull final Context context, @NotNull final String key) {
        this.context = context;
        this.key = key;
    }

    @Override
    public boolean create(final T t) {
        final List<T> list = read();
        boolean success = list.add(t);
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
        boolean success = list.remove(t);
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
