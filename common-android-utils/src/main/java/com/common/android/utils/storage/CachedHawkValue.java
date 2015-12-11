package com.common.android.utils.storage;

import com.orhanobut.hawk.Hawk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by jan.rabe on 10/12/15.
 */
public class CachedHawkValue<T> {

    private final String key;
    @Nullable
    private T cachedValue;
    private boolean isDirty;

    public CachedHawkValue(@NotNull final String key) {
        this.key = key;
        isDirty = true;
    }

    public CachedHawkValue(@NotNull final String key, @NotNull final T value) {
        Hawk.put(key, value);
        this.key = key;
        isDirty = false;
        cachedValue = value;
    }

    @Nullable
    public T get() {
        if (isDirty || cachedValue == null) {
            cachedValue = Hawk.get(key);
            isDirty = false;
        }
        return cachedValue;
    }

    public void set(@Nullable final T value) {
        Hawk.put(key, value);
        cachedValue = value;
    }

    public void invalidate() {
        isDirty = true;
    }
}
