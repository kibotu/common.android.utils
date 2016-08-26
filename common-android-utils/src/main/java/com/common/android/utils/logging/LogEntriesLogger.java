package com.common.android.utils.logging;

import android.content.Context;

import com.logentries.logger.AndroidLogger;

import java.io.IOException;

import static com.common.android.utils.ContextHelper.getApplication;

/**
 * Created by Nyaruhodo on 26.08.2016.
 */

public class LogEntriesLogger implements ILogger {

    private AndroidLogger logger;

    public static synchronized LogEntriesLogger with(String token) {
        return with(getApplication(), true, true, false, null, 0, token, true);
    }

    public static synchronized LogEntriesLogger with(Context context, boolean useHttpPost, boolean useSsl, boolean isUsingDataHub,
                                                     String dataHubAddr, int dataHubPort, String token, boolean logHostName) {
        final LogEntriesLogger logger = new LogEntriesLogger();
        try {
            logger.logger = AndroidLogger.createInstance(context, useHttpPost, useSsl, isUsingDataHub, dataHubAddr, dataHubPort, token, logHostName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logger;
    }

    @Override
    public void debug(String tag, String message) {
        if (logger == null)
            return;

        logger.log(tag + " " + message);
    }

    @Override
    public void verbose(String tag, String message) {
        if (logger == null)
            return;

        logger.log(tag + " " + message);
    }

    @Override
    public void information(String tag, String message) {
        if (logger == null)
            return;

        logger.log(tag + " " + message);
    }

    @Override
    public void warning(String tag, String message) {
        if (logger == null)
            return;

        logger.log(tag + " " + message);
    }

    @Override
    public void error(String tag, String message) {
        if (logger == null)
            return;

        logger.log(tag + " " + message);
    }

    @Override
    public void toast(String message) {
        if (logger == null)
            return;

        logger.log(message);
    }
}
