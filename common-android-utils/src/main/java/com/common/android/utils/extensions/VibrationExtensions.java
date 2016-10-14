package com.common.android.utils.extensions;

import android.content.Context;
import android.media.AudioAttributes;
import android.os.Build;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;

import static com.common.android.utils.ContextHelper.getContext;
import static com.common.android.utils.extensions.MathExtensions.clamp;

/**
 * Created by <a href="https://about.me/janrabe">Jan Rabe</a>.
 */

public class VibrationExtensions {

    /**
     * A Vibrator for interacting with the vibrator hardware.
     */
    public static Vibrator getVibrator() {
        return (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
    }

    /**
     * {@link Vibrator#hasVibrator()}
     */
    public static boolean hasVibrator() {
        return getVibrator().hasVibrator();
    }

    /**
     * {@link Vibrator#vibrate(long)}
     */
    public static void vibrate(long milliseconds) {
        getVibrator().vibrate(milliseconds);
    }

    /**
     * {@link Vibrator#vibrate(long, AudioAttributes)}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void vibrate(long milliseconds, AudioAttributes attributes) {
        getVibrator().vibrate(milliseconds, attributes);
    }

    /**
     * {@link Vibrator#vibrate(long[], int)}
     */
    public static void vibrate(long[] pattern, int repeat) {
        getVibrator().vibrate(pattern, repeat);
    }


    /**
     * {@link Vibrator#vibrate(long[], int)}
     */
    public static void vibrate(long[] pattern) {
        getVibrator().vibrate(pattern, -1);
    }

    /**
     * {@link Vibrator#vibrate(long[], int, AudioAttributes)}
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void vibrate(long[] pattern, int repeat, AudioAttributes attributes) {
        getVibrator().vibrate(pattern, repeat, attributes);
    }

    /**
     * {@link Vibrator#cancel()}
     */
    public static void cancel() {
        getVibrator().cancel();
    }

    /**
     * <a href="http://stackoverflow.com/a/20821575">Pseudo Pulse-width modulation</a>.
     *
     * @param intensity Intensity value [0,1]
     * @param duration  Duration in milliseconds.
     * @return Vibration Pattern.
     */
    public static long[] createVibratorPattern(float intensity, long duration) {
        intensity = clamp(intensity, 0, 1);
        float dutyCycle = Math.abs((intensity * 2.0f) - 1.0f);
        long hWidth = (long) (dutyCycle * (duration - 1)) + 1;
        long lWidth = dutyCycle == 1.0f ? 0 : 1;

        int pulseCount = (int) (2.0f * ((float) duration / (float) (hWidth + lWidth)));
        long[] pattern = new long[pulseCount];

        for (int i = 0; i < pulseCount; i++) {
            pattern[i] = intensity < 0.5f ? (i % 2 == 0 ? hWidth : lWidth) : (i % 2 == 0 ? lWidth : hWidth);
        }

        return pattern;
    }

    public enum Vibration {
        PRESET_01(0.1f),
        PRESET_02(0.2f),
        PRESET_03(0.3f),
        PRESET_04(0.4f),
        PRESET_05(0.5f),
        PRESET_06(0.6f),
        PRESET_07(0.7f),
        PRESET_08(0.8f),
        PRESET_09(0.9f),
        PRESET_10(1f);

        private final float intensity;

        Vibration(float intensity) {
            this.intensity = intensity;
        }

        /**
         * @param duration In Millis.
         */
        public void vibrate(long duration) {
            VibrationExtensions.vibrate(createVibratorPattern(intensity, duration));
        }
    }
}
