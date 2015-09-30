package common.android.utils.analytics;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.StringRes;
import android.util.Log;
import com.adviqo.app.ui.mainMenu.MainMenuItem;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;
import common.android.utils.device.Version;
import de.questico.app.BuildConfig;
import de.questico.app.R;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import static com.adviqo.app.Settings.USE_GOOGLE_ANALYTICS_TEST_ENVIRONMENT;

public enum AnalyticsService {

    instance;

    public static final int SESSION_LIMIT = 500;
    private static final String TAG = AnalyticsService.class.getSimpleName();
    private static Context context;
    volatile int sessionCounter;
    String currentScreen = "";
    private Tracker tracker;

    public static void init(@NotNull final Context context, final String trackingId) {
        AnalyticsService.context = context;
        final GoogleAnalytics analytics = GoogleAnalytics.getInstance(context);

        instance.tracker = analytics.newTracker(trackingId);

        analytics.getLogger().setLogLevel(BuildConfig.DEBUG
                ? Logger.LogLevel.VERBOSE
                : Logger.LogLevel.ERROR);

        analytics.setDryRun(!USE_GOOGLE_ANALYTICS_TEST_ENVIRONMENT && BuildConfig.DEBUG);

        instance.tracker.enableExceptionReporting(false);
        instance.tracker.enableAdvertisingIdCollection(true);
        instance.tracker.enableAutoActivityTracking(false);
        instance.tracker.setSessionTimeout(300);
        instance.tracker.setSampleRate(100);

        analytics.setLocalDispatchPeriod(BuildConfig.DEBUG
                ? 15
                : 300);

        final Version version = new Version(context);
        instance.tracker.setAppVersion(version.toString());
        instance.tracker.setAppName(BuildConfig.APPLICATION_ID);
        instance.tracker.setLanguage(Locale.getDefault().getDisplayLanguage());
    }

    public static void setContext(final Activity context) {
        AnalyticsService.context = context;
    }

    public static synchronized void track(@StringRes final int screenNameId, @StringRes final int categoryId, @StringRes final int actionId, final String label) {
        track(getString(screenNameId), getString(categoryId), getString(actionId), label);
    }

    public static void track(@StringRes final int screenNameId, @StringRes final int categoryId, @StringRes final int actionId, @StringRes final int labelId) {
        track(screenNameId, categoryId, actionId, getString(labelId));
    }

    public static void track(@NotNull final String screenName, @StringRes final int categoryId, @StringRes final int actionId, @StringRes final int labelId) {
        track(screenName, getString(categoryId), getString(actionId), getString(labelId));
    }

    /**
     * https://developers.google.com/analytics/devguides/collection/android/v4/
     *
     * @param screenName
     * @param category
     * @param action
     * @param label
     */
    public static void track(@NotNull final String screenName, @NotNull final String category, @NotNull final String action, @StringRes final String label) {
        ++instance.sessionCounter;
        final EventTracker tracker = AnalyticsService
                .instance
                .event()
                .screenName(screenName)
                .category(category)
                .action(action)
                .label(label);
        if (instance.sessionCounter >= SESSION_LIMIT) {
            tracker.setNewSession();
            instance.sessionCounter = 0;
        }
        tracker.track();
        Log.v(TAG, "[ " + screenName + " | " + category + " | " + action + " | " + label + " ]");
    }

    public static void track(final String screenName) {
        if (screenName.equals(instance.currentScreen))
            return;
        instance.currentScreen = screenName;
        ++instance.sessionCounter;
        final ScreenTracker tracker = AnalyticsService.instance.screen(instance.currentScreen);
        if (instance.sessionCounter >= SESSION_LIMIT) {
            tracker.setNewSession();
            instance.sessionCounter = 0;
        }
        tracker.track();
        Log.v(TAG, instance.currentScreen);
    }

    private static String getString(@StringRes final int resourceId) {
        return instance.context.getString(resourceId);
    }

    public static void reportActivityStart(@SuppressWarnings("unused") @NotNull final Activity activity) {
        AnalyticsService.track(getString(R.string.app_name));
    }

    public static void reportActivityStop(@NotNull final Activity activity) {
        GoogleAnalytics.getInstance(activity).reportActivityStop(activity);
    }

    public ScreenTracker screen(final String screenName) {
        return ScreenTracker.from(getTracker(), screenName);
    }

    public EventTracker event() {
        return EventTracker.from(getTracker());
    }

    public TimingTracker timing() {
        return TimingTracker.from(getTracker());
    }

    public Tracker getTracker() {
        return tracker;
    }

}