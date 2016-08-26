package com.common.android.utils.logging;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.common.android.utils.ContextHelper.getContext;
import static com.common.android.utils.extensions.CollectionExtensions.isEmpty;

/**
 * Static logging facet for concrete device specific logger.
 * Depending on Logging level it shows only higher prioritized logs.
 * For instance: if the Logging Level is set to info, no debug messages will be shown, etc.
 */
final public class Logger {

    /**
     * Logging level.
     */
    @NonNull
    private static Level logLevel = Level.DEBUG;
    /**
     * Concrete Logger.
     */
    @Nullable
    private static final List<ILogger> loggers = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param logger - Concrete Logger.
     */
    public static void addLogger(@NonNull final ILogger logger) {
        Logger.loggers.add(logger);
    }

    /**
     * Constructor.
     *
     * @param logger - Concrete Logger.
     * @param level  - Logging level.
     */
    private static void addLogger(@NonNull final ILogger logger, @NonNull final Level level) {
        addLogger(logger);
        Logger.logLevel = level;
    }

    /**
     * Gets Logging level.
     *
     * @return Currently set logging level.
     */
    @NonNull
    public static Level getLogLevel() {
        return logLevel;
    }

    /**
     * Sets new Logging level.
     *
     * @param logLevel new logging level.
     */
    public static void setLogLevel(@NonNull final Level logLevel) {
        if (isEmpty(loggers))
            addLogger(new LogcatLogger());
        else
            Logger.logLevel = logLevel;
    }

    /**
     * Checks against logging level.
     *
     * @param level - Defined logging level.
     * @return true if logging is allowed.
     */
    private static boolean allowLogging(@NonNull final Level level) {
        if (isEmpty(loggers))
            addLogger(new LogcatLogger(), Level.VERBOSE);
        return logLevel.compareTo(level) <= 0;
    }

    /**
     * Representing Verbose-Logging level.
     *
     * @param message - Actual logging message.
     */
    public static void v(@NonNull final String tag, @Nullable final String message) {
        if (allowLogging(Level.VERBOSE))
            for (ILogger logger : loggers)
                logger.verbose(tag, "" + message);
    }

    /**
     * Representing Debug-Logging level.
     *
     * @param message - Actual logging message.
     */
    public static void d(@NonNull final String tag, @Nullable final String message) {
        if (allowLogging(Level.DEBUG))
            for (ILogger logger : loggers) logger.debug(tag, "" + message);
    }

    /**
     * Representing Information-Logging level.
     *
     * @param message - Actual logging message.
     */
    public static void i(@NonNull final String tag, @Nullable final String message) {
        if (allowLogging(Level.INFO))
            for (ILogger logger : loggers)
                logger.information(tag, "" + message);
    }

    /**
     * Representing Warning-Logging level.
     *
     * @param message - Actual logging message.
     */
    public static void w(@NonNull final String tag, @Nullable final String message) {
        if (allowLogging(Level.WARNING))
            for (ILogger logger : loggers)
                logger.warning(tag, "" + message);
    }

    /**
     * Representing Error-Logging level.
     *
     * @param message - Actual logging message.
     */
    public static void e(@NonNull final String tag, @Nullable final String message) {
        if (allowLogging(Level.ERROR))
            for (ILogger logger : loggers)
                logger.error(tag, "" + message);
    }

    /**
     * Representing Error logging of throwable.
     *
     * @param throwable - Actual throwable.
     */
    public static void printStackTrace(@Nullable final Throwable throwable) {
        if (throwable == null) return;
        if (allowLogging(Level.ERROR)) throwable.printStackTrace();
    }

    public static void v(@Nullable final String message) {
        v(getTag(), message);
    }

    public static void d(@Nullable final String message) {
        d(getTag(), message);
    }

    public static void i(@Nullable final String message) {
        i(getTag(), message);
    }

    public static void w(@Nullable final String message) {
        w(getTag(), message);
    }

    public static void e(@Nullable final String message) {
        e(getTag(), message);
    }

    /**
     * Gets current application tag.
     *
     * @return current set prefix tag.
     */
    @NonNull
    public static String getTag() {
        return getContext().getClass().getSimpleName();
    }

    public static void toast(@Nullable final String message) {
        if (allowLogging(Level.INFO))
            for (ILogger logger : loggers)
                logger.toast(message);
    }

    /**
     * Represents the logging levels.
     */
    public enum Level {
        VERBOSE("V"), DEBUG("D"), INFO("I"), WARNING("W"), ERROR("E"), SILENT("");
        @NonNull
        public final String TAG;

        Level(@NonNull final String tag) {
            TAG = tag;
        }
    }
}