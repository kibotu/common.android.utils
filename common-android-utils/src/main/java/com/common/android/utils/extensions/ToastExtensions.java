package common.android.utils.extensions;

import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;
import com.adviqo.app.MainActivity;
import com.adviqo.app.Settings;
import de.questico.app.R;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Jan Rabe on 24/09/15.
 */
public class ToastExtensions {

    private ToastExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static void toast(@Nullable final String message) {
        if (!Settings.SHOW_TOAST_MESSAGES)
            return;

        if (TextUtils.isEmpty(message))
            return;

        MainActivity.currentMainActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Toast toast = Toast.makeText(MainActivity.currentMainActivity(), message, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 100);
                toast.show();
            }
        });
    }


    public static void toast(@Nullable final int resourceId) {
        if (!Settings.SHOW_TOAST_MESSAGES)
            return;

        MainActivity.currentMainActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Toast toast = Toast.makeText(MainActivity.currentMainActivity(), MainActivity.currentMainActivity().getText(resourceId), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0, 100);
                toast.show();
            }
        });
    }

    public static void toast(@NotNull final Exception error) {
        if (error.getMessage().equalsIgnoreCase("error.checkphonenumber.dedicated_anonym_phonenumber"))
            toast(R.string.kErrorPhoneNumberAlreadyTaken);
        else
            toast(error.getMessage());
    }
}
