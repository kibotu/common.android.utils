package common.android.utils.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.adjust.sdk.AdjustReferrerReceiver;
import com.groboot.pushapps.GCMBroadcastReceiver;
import org.jetbrains.annotations.NotNull;


/**
 * Created by franziska.huth on 28.04.15.
 */
public class InstallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(@NotNull final Context context, @NotNull final Intent intent) {
        // Adjust
        new AdjustReferrerReceiver().onReceive(context, intent);

        new GCMBroadcastReceiver().onReceive(context, intent);
    }
}
