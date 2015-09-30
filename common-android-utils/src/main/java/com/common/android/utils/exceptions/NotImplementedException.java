package common.android.utils.exceptions;

import com.adviqo.app.Settings;
import com.dtx12.android_animations_actions.actions.BuildConfig;

import static common.android.utils.extensions.ToastExtensions.toast;

/**
 * Created by jan.rabe on 14/04/15.
 */
public class NotImplementedException extends RuntimeException {

    public NotImplementedException() {
    }

    public NotImplementedException(final String detailMessage) {
        super(detailMessage);
    }

    public NotImplementedException(final String detailMessage, final Throwable throwable) {
        super(detailMessage, throwable);
    }

    public NotImplementedException(final Throwable throwable) {
        super(throwable);
    }

    public void printStackTrace() {
        if (Settings.LOG_NOT_IMPLEMENT_EXCEPTIONS)
            return;
        super.printStackTrace();
        if (BuildConfig.DEBUG)
            toast("Not Implemented yet.");
    }
}
