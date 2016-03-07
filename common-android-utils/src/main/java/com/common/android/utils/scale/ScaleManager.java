package com.common.android.utils.scale;

import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by yqritc on 2015/06/12.
 */
public class ScaleManager {

    private Size mViewSize;
    private Size mVideoSize;

    public ScaleManager(final Size viewSize, final Size videoSize) {
        mViewSize = viewSize;
        mVideoSize = videoSize;
    }

    @Nullable
    public Matrix getScaleMatrix(@NonNull final ScalableType scalableType) {
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

    @NonNull
    private Matrix getMatrix(final float sx, final float sy, final float px, final float py) {
        final Matrix matrix = new Matrix();
        matrix.setScale(sx, sy, px, py);
        return matrix;
    }

    @NonNull
    private Matrix getMatrix(final float sx, final float sy, @NonNull final PivotPoint pivotPoint) {
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

    @NonNull
    private Matrix getNoScale() {
        final float sx = mVideoSize.getWidth() / (float) mViewSize.getWidth();
        final float sy = mVideoSize.getHeight() / (float) mViewSize.getHeight();
        return getMatrix(sx, sy, PivotPoint.LEFT_TOP);
    }

    @NonNull
    private Matrix getFitScale(@NonNull final PivotPoint pivotPoint) {
        float sx = (float) mViewSize.getWidth() / mVideoSize.getWidth();
        float sy = (float) mViewSize.getHeight() / mVideoSize.getHeight();
        final float minScale = Math.min(sx, sy);
        sx = minScale / sx;
        sy = minScale / sy;
        return getMatrix(sx, sy, pivotPoint);
    }

    @NonNull
    private Matrix fitXY() {
        return getMatrix(1, 1, PivotPoint.LEFT_TOP);
    }

    @NonNull
    private Matrix fitStart() {
        return getFitScale(PivotPoint.LEFT_TOP);
    }

    @NonNull
    private Matrix fitCenter() {
        return getFitScale(PivotPoint.CENTER);
    }

    @NonNull
    private Matrix fitEnd() {
        return getFitScale(PivotPoint.RIGHT_BOTTOM);
    }

    @NonNull
    private Matrix getOriginalScale(@NonNull final PivotPoint pivotPoint) {
        final float sx = mVideoSize.getWidth() / (float) mViewSize.getWidth();
        final float sy = mVideoSize.getHeight() / (float) mViewSize.getHeight();
        return getMatrix(sx, sy, pivotPoint);
    }

    @NonNull
    private Matrix getCropScale(@NonNull final PivotPoint pivotPoint) {
        float sx = (float) mViewSize.getWidth() / mVideoSize.getWidth();
        float sy = (float) mViewSize.getHeight() / mVideoSize.getHeight();
        final float maxScale = Math.max(sx, sy);
        sx = maxScale / sx;
        sy = maxScale / sy;
        return getMatrix(sx, sy, pivotPoint);
    }

    @NonNull
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

    @NonNull
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

    @NonNull
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