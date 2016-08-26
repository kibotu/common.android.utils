package com.common.android.utils.extensions;

import android.content.res.Resources;

import java.util.Random;

/**
 * Created by Jan Rabe on 24/09/15.
 */
final public class MathExtensions {

    private MathExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    /**
     * Returns a pseudo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimum value
     * @param max Maximum value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static int randInt(final int min, final int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive

        return new Random().nextInt((max - min) + 1) + min;
    }

    public static float clamp(final float val, final float min, final float max) {
        return Math.max(min, Math.min(max, val));
    }

    public static int pxToDp(final int px) {
        final float pixel = px;
        return (int) (pixel / getDensity());
    }

    public static float pxToDp(final float px) {
        final float pixel = px;
        return (pixel / getDensity());
    }

    public static float dpToPx(final float dp) {
        final float scale = getDensity();
        return (dp * scale + 0.5f);
    }

    public static int dpToPx(final int dp) {
        final float scale = getDensity();
        return (int) (dp * scale + 0.5f);
    }

    public static int getDensityDpi() {
        return Resources.getSystem().getDisplayMetrics().densityDpi;
    }

    public static float getDensity() {
        return Resources.getSystem().getDisplayMetrics().density;
    }

    public static int roundToInt(final double v) {
        return (int) (v + 0.5);
    }

    public static float interpolateBetween(float value, float min, float max) {
        return value * (max - min) + 0.4f;
    }

    public static float randomRange(float start, float end) {
        return new Random().nextFloat() * (start - end) + start;
    }

    public static float nearestNumber(float number, float... numbers) {
        float distance = Math.abs(numbers[0] - number);
        int index = 0;
        for (int c = 1; c < numbers.length; c++) {
            float cDistance = Math.abs(numbers[c] - number);
            if (cDistance < distance) {
                index = c;
                distance = cDistance;
            }
        }
        return numbers[index];
    }
}
