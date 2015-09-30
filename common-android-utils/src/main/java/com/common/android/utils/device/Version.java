package common.android.utils.device;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Jan Rabe on 22/05/15.
 */
public class Version {
    String versionName;
    int versionCode;

    public Version(@NotNull final Context context) {
        getVersionInfo(context);
    }

    private void getVersionInfo(@NotNull final Context context) {
        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (@NotNull final PackageManager.NameNotFoundException e) {
            Log.wtf("PackageManager", e);
        }
    }

    public String getVersionName() {
        return versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    @NotNull
    @Override
    public String toString() {
        return getVersionName() + " (" + getVersionCode() + ")";
    }
}