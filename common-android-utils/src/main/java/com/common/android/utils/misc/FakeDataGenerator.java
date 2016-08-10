package com.common.android.utils.misc;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import java.util.Random;

import static java.text.MessageFormat.format;

/**
 * Created by Nyaruhodo on 07.05.2016.
 */
public class FakeDataGenerator {

    public static String createRandomImageUrl() {
        return format("http://lorempixel.com/{0}/{1}/", new Random().nextInt(400) + 200, new Random().nextInt(200) + 400);
    }

    public static int generateRandomColor() {
        return generateRandomColor(255, 255, 255, 255);
    }

    public static ColorDrawable generateRandomColorDrawable() {
        return generateRandomColorDrawable(255, 255, 255, 255);
    }

    public static ColorDrawable generateRandomColorDrawable(int alpha, int red, int green, int blue) {
        return new ColorDrawable(generateRandomColor(alpha, red, green, blue));
    }

    public static int generateRandomColor(int alpha, int red, int green, int blue) {

        final Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);

        // mix the color
        r = (r + red) / 2;
        g = (g + green) / 2;
        b = (b + blue) / 2;

        return Color.argb(alpha, r, g, b);
    }
}
