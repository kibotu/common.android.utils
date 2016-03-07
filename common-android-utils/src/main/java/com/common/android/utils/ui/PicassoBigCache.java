package com.common.android.utils.ui;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.StatFs;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.Builder;
import com.squareup.picasso.UrlConnectionDownloader;

import java.io.File;

import static com.common.android.utils.ContextHelper.getContext;

public enum PicassoBigCache {

    INSTANCE;

    private static final String BIG_CACHE_PATH = "picasso-big-cache";
    private static final int MIN_DISK_CACHE_SIZE = 32 * 1024 * 1024;       // 32MB
    private static final int MAX_DISK_CACHE_SIZE = 512 * 1024 * 1024;      // 512MB

    private static final float MAX_AVAILABLE_SPACE_USE_FRACTION = 0.9f;
    private static final float MAX_TOTAL_SPACE_USE_FRACTION = 0.25f;

    private Picasso picassoInstance;

    @NonNull
    static Downloader createBigCacheDownloader() {
        try {
            Class.forName("com.squareup.okhttp.OkHttpClient");
            final File cacheDir = createDefaultCacheDir(BIG_CACHE_PATH);
            final long cacheSize = calculateDiskCacheSize(cacheDir);
            final OkHttpDownloader downloader = new OkHttpDownloader(cacheDir, cacheSize);
            return downloader;
        } catch (@NonNull final ClassNotFoundException e) {
            return new UrlConnectionDownloader(getContext().getApplicationContext());
        }
    }

    @Nullable
    static File createDefaultCacheDir(@NonNull final String path) {
        File cacheDir = getContext().getApplicationContext().getExternalCacheDir();
        if (cacheDir == null)
            cacheDir = getContext().getApplicationContext().getCacheDir();
        final File cache = new File(cacheDir, path);
        if (!cache.exists()) {
            cache.mkdirs();
        }
        return cache;
    }

    /**
     * Calculates bonded min max cache size. Min value is {@link #MIN_DISK_CACHE_SIZE}
     *
     * @param dir cache dir
     * @return disk space in bytes
     */

    static long calculateDiskCacheSize(@NonNull final File dir) {
        final long size = Math.min(calculateAvailableCacheSize(dir), MAX_DISK_CACHE_SIZE);
        return Math.max(size, MIN_DISK_CACHE_SIZE);
    }

    /**
     * Calculates minimum of available or total fraction of disk space
     *
     * @param dir
     * @return space in bytes
     */
    @SuppressLint("NewApi")
    static long calculateAvailableCacheSize(@NonNull final File dir) {
        long size = 0;
        try {
            final StatFs statFs = new StatFs(dir.getAbsolutePath());
            final int sdkInt = Build.VERSION.SDK_INT;
            final long totalBytes;
            final long availableBytes;
            if (sdkInt < Build.VERSION_CODES.JELLY_BEAN_MR2) {
                final int blockSize = statFs.getBlockSize();
                availableBytes = ((long) statFs.getAvailableBlocks()) * blockSize;
                totalBytes = ((long) statFs.getBlockCount()) * blockSize;
            } else {
                availableBytes = statFs.getAvailableBytes();
                totalBytes = statFs.getTotalBytes();
            }
            // Target at least 90% of available or 25% of total space
            size = (long) Math.min(availableBytes * MAX_AVAILABLE_SPACE_USE_FRACTION, totalBytes * MAX_TOTAL_SPACE_USE_FRACTION);
        } catch (@NonNull final IllegalArgumentException ignored) {
            // ignored
        }
        return size;
    }

    private void init() {
        final Builder builder = new Picasso.Builder(getContext().getApplicationContext());
        builder.downloader(createBigCacheDownloader());
        picassoInstance = builder.build();
    }

    public Picasso getPicassoBigCache() {
        if (picassoInstance == null) {
            synchronized (INSTANCE) {
                if (picassoInstance == null)
                    init();
            }
        }
        return picassoInstance;
    }

}