package com.common.android.utils.input;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jan Rabe on 03/08/15.
 */
public enum KeyEventDelegate implements IKeyListener {

    instance;

    private static final String TAG = KeyEventDelegate.class.getSimpleName();
    private final List<IKeyListener> keyListeners = new ArrayList<>();

    public static void addKeyListener(@Nullable final IKeyListener listener) {
        if (listener != null && !instance.keyListeners.contains(listener))
            instance.keyListeners.add(listener);
    }

    public static void removeKeyListener(@Nullable final IKeyListener listener) {
        if (listener != null)
            instance.keyListeners.remove(listener);
    }

    @Override
    public boolean onKeyUp(final int keyCode, final KeyEvent event) {
        boolean hasBeenHandled = false;
        for (final IKeyListener listener : keyListeners)
            if (!hasBeenHandled)
                hasBeenHandled = listener.onKeyUp(keyCode, event);
        return hasBeenHandled;
    }

    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        Log.v(TAG, "onkeydown " + keyCode + " event " + event);
        boolean hasBeenHandled = false;
        for (final IKeyListener listener : keyListeners)
            if (!hasBeenHandled)
                hasBeenHandled = listener.onKeyDown(keyCode, event);
        return hasBeenHandled;
    }
}
