package com.common.android.utils.extensions;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import static android.os.Build.VERSION_CODES.KITKAT;
import static com.common.android.utils.ContextHelper.getContext;
import static net.kibotu.android.deviceinfo.library.version.Version.isAtLeastVersion;

/**
 * Created by Jan Rabe on 24/09/15.
 */
final public class ActivityExtensions {

    private ActivityExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static void enableImmersiveMode() {
        if (!isAtLeastVersion(KITKAT))
            return;

        final Window window = getContext().getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.getDecorView().setOnSystemUiVisibilityChangeListener(visibility -> {
            if (visibility != 0)
                return;

            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        });
    }

    /**
     * This snippet hides the system bars.
     */
    public static void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        getContext().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    public static void onWindowFocusChanged(final boolean hasFocus) {
        if (hasFocus) {
            getContext().getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    /**
     * This snippet shows the system bars. It does this by removing all the flags
     * except for the ones that make the content appear under the system bars.
     */
    public static void showSystemUI() {
        getContext().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    public static void showSettings(@NonNull final Context context) {
        final Intent intent = new Intent(Settings.ACTION_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void showDevSettings(@NonNull final Context context) {
        final Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Nullable
    public static AppCompatActivity getAppCompatActivity() {
        if (getContext() instanceof AppCompatActivity)
            return (AppCompatActivity) getContext();
        else
            return null;
    }

    @Nullable
    public static ActionBar getSupportActionBar() {
        return getAppCompatActivity() != null
                ? getAppCompatActivity().getSupportActionBar()
                : null;
    }

    public static boolean isPackageInstalled(@NonNull final String packageName) {
        try {
            getContext().getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static String prependMarketUrl(@NonNull final String packageName) {
        return "market://details?id=" + packageName;
    }
}
