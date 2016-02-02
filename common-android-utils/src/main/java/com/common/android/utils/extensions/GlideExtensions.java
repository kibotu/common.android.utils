package com.common.android.utils.extensions;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorRes;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.common.android.utils.logging.Logger;
import com.common.android.utils.ui.components.SpriteImageView;
import jp.wasabeef.glide.transformations.CropTransformation;
import org.jetbrains.annotations.NotNull;

import static com.common.android.utils.ContextHelper.getContext;

/**
 * Created by Jan Rabe on 30/09/15.
 */
public class GlideExtensions {

    private static final String TAG = GlideExtensions.class.getSimpleName();

    public GlideExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static void drawImageWithCenterCrop(final String imageUrl, @NotNull final ImageView imageView, @ColorRes final int placeHolderColor) {
        final ColorDrawable drawable = new ColorDrawable(Color.GRAY);
        Glide.with(imageView.getContext()).load(imageUrl).placeholder(imageView.getContext().getResources().getColor(placeHolderColor)).fitCenter().centerCrop().into(imageView);
    }

    public static void drawImageWithFitCenter(final String imageUrl, @NotNull final ImageView imageView, @ColorRes final int placeHolderColor) {
        final ColorDrawable drawable = new ColorDrawable(Color.GRAY);
        Glide.with(imageView.getContext()).load(imageUrl).placeholder(imageView.getContext().getResources().getColor(placeHolderColor)).fitCenter().into(imageView);
    }

    @NotNull
    public static SimpleTarget<GlideBitmapDrawable> getSimpleTarget(final @NotNull SpriteImageView imageView, final int amountRows, final int amountColumns, final float density, final int columnWidth, final int rowHeight) {
        return new SimpleTarget<GlideBitmapDrawable>() {
            @Override
            public void onResourceReady(final GlideBitmapDrawable resource, final GlideAnimation<? super GlideBitmapDrawable> glideAnimation) {
                imageView.setImageDrawable(resource);
                imageView.setCoordinates((int) (amountColumns * columnWidth * density), (int) (amountRows * columnWidth * density), (int) (rowHeight * density), (int) (rowHeight * density));
            }
        };
    }

    public static void drawImageCenterCrop(@NotNull final String imageUrl, @NotNull ImageView imageView, @ColorRes int placeHolderColor) {
        Glide.with(imageView.getContext()).load(imageUrl).placeholder(getContext().getResources().getColor(placeHolderColor)).centerCrop().into(imageView);
    }

    public static void drawImageTopCrop(@NotNull String imageUrl, @NotNull ImageView imageView, @ColorRes int placeHolderColor, final int width, final int height) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .bitmapTransform(new CropTransformation(getContext(), width, height, CropTransformation.CropType.TOP))
                .placeholder(placeHolderColor)
                .into(imageView);
    }

    public static void drawImageWithFitCenterNoFading(String imageUrl, @NotNull ImageView imageView, @ColorRes int placeHolderColor) {
        Glide.with(imageView.getContext()).load(imageUrl).dontAnimate().placeholder(imageView.getContext().getResources().getColor(placeHolderColor)).fitCenter().into(imageView);
    }

    public static void topCrop(@NotNull final String url, @NotNull final ImageView image, final int width, final int height, final int imageWidth, final int imageHeight) {
        drawImageTopCrop(url, image, R.color.bluewin_black, width, height);
        Logger.v(TAG, String.format("[TopCrop | %dx%d | %dx%d | view %.2f | original %.2f | %s]", width, height, imageWidth, imageHeight, width / (float) height, imageWidth / (float) imageHeight, url));
    }

    public static void centerCrop(@NotNull final String url, @NotNull final ImageView image, final int width, final int height, final int imageWidth, final int imageHeight) {
        drawImageCenterCrop(url, image, R.color.bluewin_black);
        Logger.v(TAG, String.format("[CenterCrop | %dx%d | %dx%d | view %.2f | original %.2f | %s]", width, height, imageWidth, imageHeight, width / (float) height, imageWidth / (float) imageHeight, url));
    }
}
