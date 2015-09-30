package com.common.android.utils.interfaces;

import java.util.List;

public interface IStorage<T> {

    boolean create(T t);

    List<T> read();

    boolean delete(T t);

    void clear();

    boolean contains(T t);
}