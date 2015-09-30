package com.common.android.utils.input;

import android.view.KeyEvent;

public interface IKeyListener {

    boolean onKeyUp(final int keyCode, final KeyEvent event);

    boolean onKeyDown(final int keyCode, final KeyEvent event);
}