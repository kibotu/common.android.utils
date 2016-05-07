/*
 * Copyright 2014 Johannes Homeier
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.common.android.utils.ui;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.Gravity;

/**
 * credits: https://gist.github.com/homj/f456dede83cb34a9e997
 */
public class DrawerIconDrawable extends Drawable {
    public static final int STATE_DRAWER = 0;
    public static final int STATE_ARROW = 1;

    private static final float BASE_DRAWABLE_SIZE = 48f;
    private static final float BASE_ICON_SIZE = 18f;
    private static final float BASE_BAR_WIDTH = BASE_ICON_SIZE;
    private static final float BASE_BAR_HEIGHT = 2f;
    private static final float BASE_BAR_SPACING = 5f;
    private static final float BASE_BAR_SHRINKAGE = BASE_BAR_WIDTH / 6f;

    private static final float FULL_ROTATION = 720f;
    private static final float TOPRECT_FIRST_ROTATION = 450f;
    private static final float TOPRECT_SECOND_ROTATION = FULL_ROTATION - TOPRECT_FIRST_ROTATION;
    private static final float MIDRECT_FIRST_ROTATION = 360f;
    private static final float MIDRECT_SECOND_ROTATION = FULL_ROTATION - MIDRECT_FIRST_ROTATION;
    private static final float BOTRECT_FIRST_ROTATION = 270f;
    private static final float BOTRECT_SECOND_ROTATION = FULL_ROTATION - BOTRECT_FIRST_ROTATION;

    private static final float LEVEL_BREAKPOINT = 0.5f;

    // level of the animation
    private float level;

    // Dimensions
    private final int width;
    private final int height;
    private int offsetX;
    private int offsetY;
    private float barWidth;
    private float barHeight;
    private float barSpacing;
    private float barShrinkage;

    // Drawing-Objects
    @NonNull
    private final Paint mPaint;
    @NonNull
    private final Rect iconRect;
    @NonNull
    private final RectF topRect;
    @NonNull
    private final RectF middleRect;
    @NonNull
    private final RectF bottomRect;

    private int gravity = Gravity.CENTER;

    private boolean breakpointReached = false;


    /**
     * Create a new DrawerIconDrawableV1 with size in pixel
     *
     * @param size the size (both width and height) this drawable should have in pixel
     */
    public DrawerIconDrawable(int size) {
        this(size, size);
    }

    /**
     * Create a new DrawerIconDrawableV1 with a specfied width  and height  in pixel
     *
     * @param width  the width this drawable should have in pixel
     * @param height the height this drawable should have in pixel
     */
    public DrawerIconDrawable(int width, int height) {
        this(width, height, 0, 0);
    }

    /**
     * Create a new DrawerIconDrawableV1 with specified width and height in pixel and also apply a {@link Gravity} to align the icon
     *
     * @param width   the width this drawable should have in pixel
     * @param height  the height this drawable should have in pixel
     * @param gravity the gravity to align the icon in this drawable
     */
    public DrawerIconDrawable(int width, int height, int gravity) {
        this(width, height, 0, 0, gravity);
    }

    /**
     * Create a new DrawerIconDrawableV1 with specified width and height in pixel and also apply a offset to the icon
     *
     * @param width   the width this drawable should have in pixel
     * @param height  the height this drawable should have in pixel
     * @param offsetX the offset the icon should have from its center in x-direction
     * @param offsetY the offset the icon should have from its center in y-direction
     */
    public DrawerIconDrawable(int width, int height, int offsetX, int offsetY) {
        this(width, height, 0, 0, Gravity.CENTER);
    }

    /**
     * Create a new DrawerIconDrawableV1 with specified width and height in pixel and also apply a offset to the icon plus a {@link Gravity} to align the icon
     *
     * @param width   the width this drawable should have in pixel
     * @param height  the height this drawable should have in pixel
     * @param offsetX the offset the icon should have from its center in x-direction
     * @param offsetY the offset the icon should have from its center in y-direction
     * @param gravity the gravity to align the icon in this drawable
     */
    public DrawerIconDrawable(int width, int height, int offsetX, int offsetY, int gravity) {
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.gravity = gravity;

        setBounds(new Rect(0, 0, width, height));

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(0xffffffff);

        iconRect = new Rect();
        topRect = new RectF();
        middleRect = new RectF();
        bottomRect = new RectF();

        setDefaultIconSize();
        setLevel(0);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.translate(iconRect.left + offsetX, iconRect.top + offsetY);
        float scaleFactor = level < LEVEL_BREAKPOINT ? level * 2 : (level - 0.5f) * 2;

        drawTopRect(canvas, scaleFactor);
        drawMiddleRect(canvas, scaleFactor);
        drawBottomRect(canvas, scaleFactor);
    }

    private void drawTopRect(@NonNull Canvas canvas, float scaleFactor) {
        canvas.save();
        offsetTopRect(barShrinkage * scaleFactor, 0, -barShrinkage * scaleFactor, 0);
        canvas.rotate(
                level < LEVEL_BREAKPOINT
                        ? level * TOPRECT_FIRST_ROTATION
                        : LEVEL_BREAKPOINT * TOPRECT_FIRST_ROTATION + (1 - level) * TOPRECT_SECOND_ROTATION
                , iconRect.width() / 2
                , iconRect.height() / 2);
        canvas.drawRect(topRect, mPaint);
        canvas.restore();
    }

    private void drawMiddleRect(@NonNull Canvas canvas, float scaleFactor) {
        canvas.save();
        offsetMiddleRect(0, 0, -barShrinkage * 2f / 3f * scaleFactor, 0);
        canvas.rotate(
                level < LEVEL_BREAKPOINT
                        ? level * MIDRECT_FIRST_ROTATION
                        : LEVEL_BREAKPOINT * MIDRECT_FIRST_ROTATION + (1 - level) * MIDRECT_SECOND_ROTATION
                , iconRect.width() / 2
                , iconRect.height() / 2);
        canvas.drawRect(middleRect, mPaint);
        canvas.restore();
    }

    private void drawBottomRect(@NonNull Canvas canvas, float scaleFactor) {
        canvas.save();
        offsetBottomRect(barShrinkage * scaleFactor, 0, -barShrinkage * scaleFactor,
                0);
        canvas.rotate(
                level < LEVEL_BREAKPOINT
                        ? level * BOTRECT_FIRST_ROTATION
                        : LEVEL_BREAKPOINT * BOTRECT_FIRST_ROTATION + (1 - level) * BOTRECT_SECOND_ROTATION
                , iconRect.width() / 2
                , iconRect.height() / 2);
        canvas.drawRect(bottomRect, mPaint);
        canvas.restore();
    }

    private void offsetTopRect(float offsetLeft, float offsetTop,
                               float offsetRight, float offsetBottom) {
        topRect.set(
                iconRect.width() / 2 - barWidth / 2 + offsetLeft
                , iconRect.height() / 2 - barSpacing - barHeight / 2 + offsetTop
                , iconRect.width() / 2 + barWidth / 2 + offsetRight
                , iconRect.height() / 2 - barSpacing + barHeight / 2 + offsetBottom);
    }

    private void offsetMiddleRect(float offsetLeft, float offsetTop,
                                  float offsetRight, float offsetBottom) {
        middleRect.set(
                iconRect.width() / 2 - barWidth / 2 + offsetLeft
                , iconRect.height() / 2 - barHeight / 2 + offsetTop
                , iconRect.width() / 2 + barWidth / 2 + offsetRight
                , iconRect.height() / 2 + barHeight / 2 + offsetBottom);
    }

    private void offsetBottomRect(float offsetLeft, float offsetTop,
                                  float offsetRight, float offsetBottom) {
        bottomRect.set(
                iconRect.width() / 2 - barWidth / 2 + offsetLeft
                , iconRect.height() / 2 + barSpacing - barHeight / 2 + offsetTop
                , iconRect.width() / 2 + barWidth / 2 + offsetRight
                , iconRect.height() / 2 + barSpacing + barHeight / 2 + offsetBottom);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return 0;
    }

    /**
     * set the color of the Drawable;
     *
     * @param color
     */
    public void setColor(int color) {
        mPaint.setColor(color);
        invalidateSelf();
    }

    /**
     * set the size of the icon; this size should be smaller than the size of this drawable itself to fully show the transformation!
     *
     * @param size the size of the icon in pixel
     */
    public void setIconSize(int size) {
        if (size > Math.min(width, height)) size = Math.min(width, height);

        iconRect.set(0, 0, size, size);
        Gravity.apply(gravity, iconRect.width(), iconRect.height(), getBounds(), iconRect);

        float ratio = size / BASE_ICON_SIZE;
        barWidth = BASE_BAR_WIDTH * ratio;
        barHeight = BASE_BAR_HEIGHT * ratio;
        barSpacing = BASE_BAR_SPACING * ratio;
        barShrinkage = BASE_BAR_SHRINKAGE * ratio;

        invalidateSelf();
    }

    /**
     * Apply a {@link Gravity} to align the icon in this drawable
     *
     * @param gravity the gravity to align the icon in this drawable
     */
    public void setGravity(int gravity) {
        Gravity.apply(gravity, iconRect.width(), iconRect.height(), getBounds(), iconRect);
        invalidateSelf();
    }

    /**
     * resets the icon size to its default size (as specified in the Material-Design-guidelines
     * this means, the icon will be 1/3 of the minimal size of this drawable
     */
    public void setDefaultIconSize() {
        setIconSize((int) (Math.min(width, height) * BASE_ICON_SIZE / BASE_DRAWABLE_SIZE));
    }

    /**
     * offset the icon from its center
     *
     * @param offsetX the offset the icon should have from its center in x-direction
     * @param offsetY the offset the icon should have from its center in y-direction
     */
    public void offsetIcon(int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        invalidateSelf();
    }

    /**
     * set the state of the Drawable;
     *
     * @param level
     */
    public void setState(int state) {
        switch (state) {
            case STATE_DRAWER:
                setLevel((float) STATE_DRAWER);
                break;
            case STATE_ARROW:
                setLevel((float) STATE_ARROW);
                break;
        }
    }

    /**
     * set the level of the animation; drawer indicator is fully displayed at 0;
     * arrow is fully displayed at 1
     *
     * @param level
     */
    public void setLevel(float level) {
        if (level == 1)
            breakpointReached = true;
        if (level == 0)
            breakpointReached = false;

        this.level = (breakpointReached ? LEVEL_BREAKPOINT : 0) + level / 2;
        invalidateSelf();
    }

    @Override
    public int getIntrinsicWidth() {
        return width;
    }

    @Override
    public int getIntrinsicHeight() {
        return height;
    }
}