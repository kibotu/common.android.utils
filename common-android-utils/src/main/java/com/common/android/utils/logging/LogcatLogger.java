package com.common.android.utils.logging;

import android.util.Log;

public class LogcatLogger implements ILogger {

    @Override
    public void debug(final String tag, final String message) {
        Log.d(tag, message);
    }

    @Override
    public void verbose(final String tag, final String message) {
        Log.d(tag, message);
    }

    @Override
    public void information(final String tag, final String message) {
        Log.d(tag, message);
    }

    @Override
    public void warning(final String tag, final String message) {
        Log.d(tag, message);
    }

    @Override
    public void error(final String tag, final String message) {
        Log.d(tag, message);
    }

    @Override
    public void toast(final String message) {
        toast(message);
    }
}