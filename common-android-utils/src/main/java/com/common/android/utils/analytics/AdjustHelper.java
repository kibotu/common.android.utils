package common.android.utils.analytics;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.AdjustEvent;
import com.adjust.sdk.LogLevel;
import de.questico.app.BuildConfig;
import org.jetbrains.annotations.NotNull;

import static com.adviqo.app.misc.ConfigService.App;
import static com.adviqo.app.misc.ConfigService.AppConstants.ADJUST_TOKEN;

/**
 * Created by Jan Rabe on 27/07/15.
 */
public class AdjustHelper {

    private static final String TAG = AdjustHelper.class.getSimpleName();

    private static boolean hasBeenInit;

    public static void initAdjust(@NotNull final Activity context) {
        if (hasBeenInit)
            return;

        final String environment = BuildConfig.DEBUG ? AdjustConfig.ENVIRONMENT_SANDBOX : AdjustConfig.ENVIRONMENT_PRODUCTION;
        final AdjustConfig config = new AdjustConfig(context, App.getValue(ADJUST_TOKEN), environment);
        Adjust.onCreate(config);
        config.setLogLevel(BuildConfig.DEBUG ? LogLevel.VERBOSE : LogLevel.ASSERT);

        // get deep link
        final Intent intent = context.getIntent();
        final Uri data = intent.getData();
        if (data != null)
            Adjust.appWillOpenUrl(data);

        hasBeenInit = true;
    }

    public static void trackEvent(@NotNull final String tracking) {
        Adjust.trackEvent(new AdjustEvent(tracking));
        if (BuildConfig.DEBUG)
            Log.v(TAG, tracking);
    }
}
