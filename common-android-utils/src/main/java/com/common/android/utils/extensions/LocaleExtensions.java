package com.common.android.utils.extensions;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.ContextThemeWrapper;

import java.util.Locale;

import static com.common.android.utils.ContextHelper.getActivity;
import static com.common.android.utils.extensions.ResourceExtensions.getResources;

public class LocaleExtensions {

    private static Locale locale;

    public static void setLocale(Locale locale) {
        LocaleExtensions.locale = locale;
        if (LocaleExtensions.locale != null) {
            Locale.setDefault(LocaleExtensions.locale);
        }
    }

    public static void updateConfig(@NonNull ContextThemeWrapper wrapper) {
        if (locale != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Configuration configuration = new Configuration();
            configuration.setLocale(locale);
            wrapper.applyOverrideConfiguration(configuration);
        }
    }

    public static void updateConfig(@NonNull Application app, @NonNull Configuration configuration) {
        if (locale != null && Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //Wrapping the configuration to avoid Activity endless loop
            Configuration config = new Configuration(configuration);
            config.locale = locale;
            Resources res = app.getBaseContext().getResources();
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
    }

    public static void restartInLocale(Locale locale) {
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        Resources resources = getResources();
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        getActivity().recreate();
    }

}