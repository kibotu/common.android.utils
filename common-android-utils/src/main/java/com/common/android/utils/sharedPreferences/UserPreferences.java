package com.common.android.utils.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import static com.common.android.utils.ContextHelper.getContext;


/**
 * Created by Jan Rabe on 27/07/15.
 */
public enum UserPreferences {

    Persistent;

    static String USE_PUSH_NOTIFICATIONS = "USE_PUSH_NOTIFICATIONS";
    static String USE_PUSH_NOTIFICATIONS_DIALOG_HAS_BEEN_SHOWN = "USE_PUSH_NOTIFICATIONS_DIALOG_HAS_BEEN_SHOWN";

    public static synchronized boolean usePushNotifications() {
        return getContext().getPreferences(Context.MODE_PRIVATE).getBoolean(USE_PUSH_NOTIFICATIONS, true);
    }

    public static synchronized void usePushNotifications(final boolean usePushNotifications) {
        final SharedPreferences.Editor editor = getContext().getPreferences(Context.MODE_PRIVATE).edit();
        editor.putBoolean(USE_PUSH_NOTIFICATIONS, usePushNotifications);
        editor.apply();
    }

    public static synchronized boolean usePushNotificationsDialogHasBeenShown() {
        return getContext().getPreferences(Context.MODE_PRIVATE).getBoolean(USE_PUSH_NOTIFICATIONS_DIALOG_HAS_BEEN_SHOWN, false);
    }

    public static synchronized void usePushNotificationsDialogHasBeenShown(final boolean usePushNotifications) {
        final SharedPreferences.Editor editor = getContext().getPreferences(Context.MODE_PRIVATE).edit();
        editor.putBoolean(USE_PUSH_NOTIFICATIONS_DIALOG_HAS_BEEN_SHOWN, usePushNotifications);
        editor.apply();
    }

    public void clearAll() {
        getContext().getSharedPreferences("YOUR_PREFS", 0).edit().clear().commit();
    }
}
