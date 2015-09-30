package common.android.utils.localization;

import android.content.Context;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import com.adviqo.app.network.ClientFactory;
import com.adviqo.app.network.localization.LocalizationClient;
import com.adviqo.shared.SupportedCountry;
import common.android.utils.network.Client;
import com.adviqo.app.misc.DialogFactory;
import de.questico.app.BuildConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static common.android.utils.extensions.ToastExtensions.toast;

/**
 * Created by franziska.huth on 02.06.15.
 */
public enum Localization {
    INSTANCE;
    private static Context context;
    @NotNull
    private static String TAG = Localization.class.getSimpleName();
    private static boolean LoggingEnabled = false;
    private final LocalizationObservable localizationObservable = new LocalizationObservable();
    ConcurrentHashMap<String, String> dictionary;

    @NotNull
    public static LocalizationObservable getLocalizationObservable() {
        return INSTANCE.localizationObservable;
    }

    public static void init(@NotNull final Context context, final String language) {
        Localization.context = context;

        final LocalizationClient client = ClientFactory.getInstance(context).getLocalizationClient();
        client.getDictionary(language, new Client.ClientListener<ConcurrentHashMap<String, String>>() {
            @Override
            public void onSuccess(final Client<ConcurrentHashMap<String, String>> client, final ConcurrentHashMap<String, String> result) {
                INSTANCE.dictionary = result;
                if (BuildConfig.DEBUG && LoggingEnabled) {
                    for (final Map.Entry<String, String> map : INSTANCE.dictionary.entrySet()) {
                        Log.v(TAG, map.getKey() + " -> " + map.getValue());
                    }
                }

                INSTANCE.localizationObservable.notifyObservers();
            }

            @Override
            public void onError(final Client<ConcurrentHashMap<String, String>> client, @NotNull final Exception error) {
                toast(error.getMessage());
                DialogFactory.createErrorDialog(Localization.context).show();
            }
        });
    }

    @NotNull
    public static String getString(@Nullable final String key) {
        if (INSTANCE.dictionary == null) {
            return "";
        }

        final String result = INSTANCE.dictionary.get(key);
        return result == null ? "" : result;

    }

    public static String getString(final int resourceId) {

        if (INSTANCE.dictionary == null)
            return replaceStrings(context.getString(resourceId));

        final String s = getString(getFieldNameOfResourceIdentifier(resourceId));
        return replaceStrings(TextUtils.isEmpty(s)
                ? context.getString(resourceId)
                : s);
    }

    private static String replaceStrings(@NotNull final String s) {
        return s.replace("%@", "%s").replace("%C", SupportedCountry.geDefaultCountry().getCurrency().getFormat());
    }

    private static String getFieldNameOfResourceIdentifier(final int resourceId) {
        try {
            for (final Field field : de.questico.app.R.string.class.getFields()) {

                if (field.getInt(null) == resourceId) {
                    return field.getName();
                }

            }
        } catch (@NotNull final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void localizeWith(@NotNull final TextView textView, @StringRes final int resourceId) {
        textView.setText(getString(resourceId));
    }
}
