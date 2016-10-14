package com.common.android.utils.extensions;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;
import android.support.annotation.RequiresPermission;

import static android.Manifest.permission.DISABLE_KEYGUARD;

public class KeyGuardExtensions {

    @RequiresPermission(DISABLE_KEYGUARD)
    public static void unlockScreen(Context context) {
        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        final KeyguardManager.KeyguardLock kl = km.newKeyguardLock("MyKeyguardLock");
        kl.disableKeyguard();

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.ON_AFTER_RELEASE, "MyWakeLock");
        wakeLock.acquire();
    }
}