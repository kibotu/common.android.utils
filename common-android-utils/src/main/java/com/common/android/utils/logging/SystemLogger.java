package com.common.android.utils.logging;

/**
 * Created by Nyaruhodo on 26.08.2016.
 */

public class SystemLogger implements ILogger {

    @Override
    public void debug(String tag, String message) {
        System.out.println(tag + " " + message);
    }

    @Override
    public void verbose(String tag, String message) {
        System.out.println(tag + " " + message);
    }

    @Override
    public void information(String tag, String message) {
        System.out.println(tag + " " + message);
    }

    @Override
    public void warning(String tag, String message) {
        System.out.println(tag + " " + message);
    }

    @Override
    public void error(String tag, String message) {
        System.err.println(tag + " " + message);
    }

    @Override
    public void toast(String message) {
        System.out.println(message);
    }
}
