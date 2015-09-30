package common.android.utils.notifications;

import android.content.Context;
import com.adviqo.app.MainActivity;
import com.groboot.pushapps.DeviceIDTypes;
import com.groboot.pushapps.PushManager;
import org.jetbrains.annotations.NotNull;

import static com.adviqo.app.misc.ConfigService.App;
import static com.adviqo.app.misc.ConfigService.AppConstants.GCM_PROJECT_NUMBER;
import static com.adviqo.app.misc.ConfigService.AppConstants.PUSHAPPS_APP_TOKEN;

/**
 * Created by jan.rabe on 10/04/15.
 *
 * @see <a href="https://wiki.pushapps.mobi/display/PUSHAPPS/Android+Getting+Started">PUSHAPPS Android Getting Started</a>
 */
public class NotificationService {

    public static void startListeningToPushNotifications(@NotNull final Context context) {

        final String pushappsAppToken = App.getValue(PUSHAPPS_APP_TOKEN);
        final String googleApiProjectNumber = App.getValue(GCM_PROJECT_NUMBER);

        // optional - sets the source of the device id for identification, default is DeviceIDTypes.IMEI. 
        // If you use this method, you must do it before your first call to init(Context,String,String), and
        // it is recommended to never change the id type after setting it for the first time 
        // to avoid duplicate devices registrations.
        PushManager.getInstance(context).setDeviceIDType(DeviceIDTypes.ANDROID_ID);
        // Start PushApps and register to the push notification service (GCM)
        PushManager.init(context, googleApiProjectNumber, pushappsAppToken);
        // optional - allows more than on notifications in the status bar, default is false
        PushManager.getInstance(context).setShouldStackNotifications(true);
        // optional - set a your own icon for the notification, defaults is the application icon
        // PushManager.getInstance(context.getApplicationContext()).setNotificationIcon(R.drawable.notification_icon); // no custom icon
        PushManager.getInstance(MainActivity.currentMainActivity()).register();
    }

    public static void stopListeningToPushNotifications() {
        PushManager.getInstance(MainActivity.currentMainActivity()).unregister();
    }
}
