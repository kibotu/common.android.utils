package com.common.android.utils.analytics;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.Arrays;

public final class TimingTracker {
    private final Tracker tracker;
    private final HitBuilders.TimingBuilder builder;

    private String screenName;
    private Dimension[] dimensions;

    public TimingTracker(final Tracker tracker) {
        this.tracker = tracker;
        this.builder = new HitBuilders.TimingBuilder();
    }

    public static TimingTracker from(final Tracker tracker) {
        return new TimingTracker(tracker);
    }

    public TimingTracker screenName(final String screenName) {
        this.screenName = screenName;
        return this;
    }

    public TimingTracker category(final String category) {
        builder.setCategory(category);
        return this;
    }

    public TimingTracker variable(final String variable) {
        builder.setVariable(variable);
        return this;
    }

    public TimingTracker label(final String label) {
        if (label != null) {
            builder.setLabel(label);
        }
        return this;
    }

    public TimingTracker value(final long timeInMillis) {
        builder.setValue(timeInMillis);
        return this;
    }

    public TimingTracker customDimension(final Dimension dimension) {
        if (dimensions == null) {
            dimensions = new Dimension[1];
            dimensions[0] = dimension;
        } else {
            dimensions = Arrays.copyOf(dimensions, dimensions.length + 1);
            dimensions[dimensions.length - 1] = dimension;
        }
        return this;
    }

    public void track() {
        if (dimensions != null && dimensions.length > 0) {
            for (Dimension dimension : dimensions) {
                builder.setCustomDimension(dimension.index, dimension.value);
            }
        }
        tracker.setScreenName(screenName);
        tracker.send(builder.build());
        tracker.setScreenName(null);
    }
}