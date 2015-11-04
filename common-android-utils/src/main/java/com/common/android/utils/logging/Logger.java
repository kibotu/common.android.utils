package com.common.android.utils.logging;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.common.android.utils.ContextHelper.getContext;

/**
 * Static logging facet for concrete device specific logger.
 * Depending on Logging level it shows only higher prioritized logs.
 * For instance: if the Logging Level is set to info, no debug messages will be shown, etc.
 */
final public class Logger {

    /**
     * Logging level.
     */
    @NotNull
    private static Level logLevel;
    /**
     * Concrete Logger.
     */
    @Nullable
    private static ILogger logger;

    /**
     * Constructor.
     *
     * @param logger - Concrete Logger.
     * @param level  - Logging level.
     */
    private static void setLogger(@NotNull final ILogger logger, @NotNull final Level level) {
        Logger.logger = logger;
        Logger.logLevel = level;
    }

    /**
     * Gets Logging level.
     *
     * @return Currently set logging level.
     */
    @NotNull
    public static Level getLogLevel() {
        return logLevel;
    }

    /**
     * Sets new Logging level.
     *
     * @param logLevel new logging level.
     */
    public static void setLogLevel(@NotNull final Level logLevel) {
        if (logger == null)
            setLogger(new LogcatLogger(), logLevel);
        else
            Logger.logLevel = logLevel;
    }

    /**
     * Checks against logging level.
     *
     * @param level - Defined logging level.
     * @return true if logging is allowed.
     */
    private static boolean allowLogging(@NotNull final Level level) {
        if (logger == null)
            setLogger(new LogcatLogger(), Level.VERBOSE);
        return logLevel.compareTo(level) <= 0;
    }

    /**
     * Representing Verbose-Logging level.
     *
     * @param message - Actual logging message.
     */
    public static void v(@NotNull final String tag, @Nullable final String message) {
        if (allowLogging(Level.VERBOSE)) logger.verbose(tag, "" + message);
    }

    /**
     * Representing Debug-Logging level.
     *
     * @param message - Actual logging message.
     */
    public static void d(@NotNull final String tag, @Nullable final String message) {
        if (allowLogging(Level.DEBUG)) logger.debug(tag, "" + message);
    }

    /**
     * Representing Information-Logging level.
     *
     * @param message - Actual logging message.
     */
    public static void i(@NotNull final String tag, @Nullable final String message) {
        if (allowLogging(Level.INFO)) logger.information(tag, "" + message);
    }

    /**
     * Representing Warning-Logging level.
     *
     * @param message - Actual logging message.
     */
    public static void w(@NotNull final String tag, @Nullable final String message) {
        if (allowLogging(Level.WARNING)) logger.warning(tag, "" + message);
    }

    /**
     * Representing Error-Logging level.
     *
     * @param message - Actual logging message.
     */
    public static void e(@NotNull final String tag, @Nullable final String message) {
        if (allowLogging(Level.ERROR)) logger.error(tag, "" + message);
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
    @NotNull
    public static String getTag() {
        return getContext().getClass().getSimpleName();
    }

    public static void toast(@Nullable String message) {
        if (allowLogging(Level.INFO)) logger.toast(message);
    }

    /**
     * Represents the logging levels.
     */
    public enum Level {
        VERBOSE("V"), DEBUG("D"), INFO("I"), WARNING("W"), ERROR("E"), SILENT("");
        @NotNull
        public final String TAG;

        Level(@NotNull final String tag) {
            TAG = tag;
        }
    }
}