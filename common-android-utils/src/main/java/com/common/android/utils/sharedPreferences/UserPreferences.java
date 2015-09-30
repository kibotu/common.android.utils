package common.android.utils.sharedPreferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import org.jetbrains.annotations.NotNull;

import static com.adviqo.app.misc.ConfigService.AppConstants.USE_PUSH_NOTIFICATIONS;
import static com.adviqo.app.misc.ConfigService.AppConstants.USE_PUSH_NOTIFICATIONS_DIALOG_HAS_BEEN_SHOWN;

/**
 * Created by Jan Rabe on 27/07/15.
 */
public enum UserPreferences {

    Persistent;

    public static synchronized boolean usePushNotifications(@NotNull final Activity context) {
        return context.getPreferences(Context.MODE_PRIVATE).getBoolean(USE_PUSH_NOTIFICATIONS, true);
    }

    public static synchronized void usePushNotifications(@NotNull final Activity context, final boolean usePushNotifications) {
        final SharedPreferences.Editor editor = context.getPreferences(Context.MODE_PRIVATE).edit();
        editor.putBoolean(USE_PUSH_NOTIFICATIONS, usePushNotifications);
        editor.apply();
    }

    public static synchronized boolean usePushNotificationsDialogHasBeenShown(@NotNull final Activity context) {
        return context.getPreferences(Context.MODE_PRIVATE).getBoolean(USE_PUSH_NOTIFICATIONS_DIALOG_HAS_BEEN_SHOWN, false);
    }

    public static synchronized void usePushNotificationsDialogHasBeenShown(@NotNull final Activity context, final boolean usePushNotifications) {
        final SharedPreferences.Editor editor = context.getPreferences(Context.MODE_PRIVATE).edit();
        editor.putBoolean(USE_PUSH_NOTIFICATIONS_DIALOG_HAS_BEEN_SHOWN, usePushNotifications);
        editor.apply();
    }

    public void clearAll(@NotNull final Context context) {
        context.getSharedPreferences("YOUR_PREFS", 0).edit().clear().commit();
    }
}
