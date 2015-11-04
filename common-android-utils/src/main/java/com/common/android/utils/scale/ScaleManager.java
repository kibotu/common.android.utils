package com.common.android.utils.scale;

import android.graphics.Matrix;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by yqritc on 2015/06/12.
 */
public class ScaleManager {

    private Size mViewSize;
    private Size mVideoSize;

    public ScaleManager(Size viewSize, Size videoSize) {
        mViewSize = viewSize;
        mVideoSize = videoSize;
    }

    @Nullable
    public Matrix getScaleMatrix(@NotNull ScalableType scalableType) {
        switch (scalableType) {
            case NONE:
                return getNoScale();

            case FIT_XY:
                return fitXY();
            case FIT_CENTER:
                return fitCenter();
            case FIT_START:
                return fitStart();
            case FIT_END:
                return fitEnd();

            case LEFT_TOP:
                return getOriginalScale(PivotPoint.LEFT_TOP);
            case LEFT_CENTER:
                return getOriginalScale(PivotPoint.LEFT_CENTER);
            case LEFT_BOTTOM:
                return getOriginalScale(PivotPoint.LEFT_BOTTOM);
            case CENTER_TOP:
                return getOriginalScale(PivotPoint.CENTER_TOP);
            case CENTER:
                return getOriginalScale(PivotPoint.CENTER);
            case CENTER_BOTTOM:
                return getOriginalScale(PivotPoint.CENTER_BOTTOM);
            case RIGHT_TOP:
                return getOriginalScale(PivotPoint.RIGHT_TOP);
            case RIGHT_CENTER:
                return getOriginalScale(PivotPoint.RIGHT_CENTER);
            case RIGHT_BOTTOM:
                return getOriginalScale(PivotPoint.RIGHT_BOTTOM);

            case LEFT_TOP_CROP:
                return getCropScale(PivotPoint.LEFT_TOP);
            case LEFT_CENTER_CROP:
                return getCropScale(PivotPoint.LEFT_CENTER);
            case LEFT_BOTTOM_CROP:
                return getCropScale(PivotPoint.LEFT_BOTTOM);
            case CENTER_TOP_CROP:
                return getCropScale(PivotPoint.CENTER_TOP);
            case CENTER_CROP:
                return getCropScale(PivotPoint.CENTER);
            case CENTER_BOTTOM_CROP:
                return getCropScale(PivotPoint.CENTER_BOTTOM);
            case RIGHT_TOP_CROP:
                return getCropScale(PivotPoint.RIGHT_TOP);
            case RIGHT_CENTER_CROP:
                return getCropScale(PivotPoint.RIGHT_CENTER);
            case RIGHT_BOTTOM_CROP:
                return getCropScale(PivotPoint.RIGHT_BOTTOM);

            case START_INSIDE:
                return startInside();
            case CENTER_INSIDE:
                return centerInside();
            case END_INSIDE:
                return endInside();

            default:
                return null;
        }
    }

    @NotNull
    private Matrix getMatrix(float sx, float sy, float px, float py) {
        Matrix matrix = new Matrix();
        matrix.setScale(sx, sy, px, py);
        return matrix;
    }

    @NotNull
    private Matrix getMatrix(float sx, float sy, @NotNull PivotPoint pivotPoint) {
        switch (pivotPoint) {
            case LEFT_TOP:
                return getMatrix(sx, sy, 0, 0);
            case LEFT_CENTER:
                return getMatrix(sx, sy, 0, mViewSize.getHeight() / 2f);
            case LEFT_BOTTOM:
                return getMatrix(sx, sy, 0, mViewSize.getHeight());
            case CENTER_TOP:
                return getMatrix(sx, sy, mViewSize.getWidth() / 2f, 0);
            case CENTER:
                return getMatrix(sx, sy, mViewSize.getWidth() / 2f, mViewSize.getHeight() / 2f);
            case CENTER_BOTTOM:
                return getMatrix(sx, sy, mViewSize.getWidth() / 2f, mViewSize.getHeight());
            case RIGHT_TOP:
                return getMatrix(sx, sy, mViewSize.getWidth(), 0);
            case RIGHT_CENTER:
                return getMatrix(sx, sy, mViewSize.getWidth(), mViewSize.getHeight() / 2f);
            case RIGHT_BOTTOM:
                return getMatrix(sx, sy, mViewSize.getWidth(), mViewSize.getHeight());
            default:
                throw new IllegalArgumentException("Illegal PivotPoint");
        }
    }

    @NotNull
    private Matrix getNoScale() {
        float sx = mVideoSize.getWidth() / (float) mViewSize.getWidth();
        float sy = mVideoSize.getHeight() / (float) mViewSize.getHeight();
        return getMatrix(sx, sy, PivotPoint.LEFT_TOP);
    }

    @NotNull
    private Matrix getFitScale(@NotNull PivotPoint pivotPoint) {
        float sx = (float) mViewSize.getWidth() / mVideoSize.getWidth();
        float sy = (float) mViewSize.getHeight() / mVideoSize.getHeight();
        float minScale = Math.min(sx, sy);
        sx = minScale / sx;
        sy = minScale / sy;
        return getMatrix(sx, sy, pivotPoint);
    }

    @NotNull
    private Matrix fitXY() {
        return getMatrix(1, 1, PivotPoint.LEFT_TOP);
    }

    @NotNull
    private Matrix fitStart() {
        return getFitScale(PivotPoint.LEFT_TOP);
    }

    @NotNull
    private Matrix fitCenter() {
        return getFitScale(PivotPoint.CENTER);
    }

    @NotNull
    private Matrix fitEnd() {
        return getFitScale(PivotPoint.RIGHT_BOTTOM);
    }

    @NotNull
    private Matrix getOriginalScale(@NotNull PivotPoint pivotPoint) {
        float sx = mVideoSize.getWidth() / (float) mViewSize.getWidth();
        float sy = mVideoSize.getHeight() / (float) mViewSize.getHeight();
        return getMatrix(sx, sy, pivotPoint);
    }

    @NotNull
    private Matrix getCropScale(@NotNull PivotPoint pivotPoint) {
        float sx = (float) mViewSize.getWidth() / mVideoSize.getWidth();
        float sy = (float) mViewSize.getHeight() / mVideoSize.getHeight();
        float maxScale = Math.max(sx, sy);
        sx = maxScale / sx;
        sy = maxScale / sy;
        return getMatrix(sx, sy, pivotPoint);
    }

    @NotNull
    private Matrix startInside() {
        if (mVideoSize.getHeight() <= mViewSize.getWidth()
                && mVideoSize.getHeight() <= mViewSize.getHeight()) {
            // video is smaller than view size
            return getOriginalScale(PivotPoint.LEFT_TOP);
        } else {
            // either of width or height of the video is larger than view size
            return fitStart();
        }
    }

    @NotNull
    private Matrix centerInside() {
        if (mVideoSize.getHeight() <= mViewSize.getWidth()
                && mVideoSize.getHeight() <= mViewSize.getHeight()) {
            // video is smaller than view size
            return getOriginalScale(PivotPoint.CENTER);
        } else {
            // either of width or height of the video is larger than view size
            return fitCenter();
        }
    }

    @NotNull
    private Matrix endInside() {
        if (mVideoSize.getHeight() <= mViewSize.getWidth()
                && mVideoSize.getHeight() <= mViewSize.getHeight()) {
            // video is smaller than view size
            return getOriginalScale(PivotPoint.RIGHT_BOTTOM);
        } else {
            // either of width or height of the video is larger than view size
            return fitEnd();
        }
    }
}