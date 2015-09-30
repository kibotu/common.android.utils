package com.common.android.utils.analytics;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;

import java.util.Arrays;

public final class EventTracker {
    private final Tracker tracker;
    private final HitBuilders.EventBuilder builder;

    private String screenName;
    private Dimension[] dimensions;

    private EventTracker(final Tracker tracker) {
        this.tracker = tracker;
        this.builder = new HitBuilders.EventBuilder();
    }

    protected static EventTracker from(final Tracker tracker) {
        return new EventTracker(tracker);
    }

    public EventTracker screenName(final String screenName) {
        this.screenName = screenName;
        return this;
    }

    public EventTracker category(final String category) {
        builder.setCategory(category);
        return this;
    }

    public EventTracker action(final String action) {
        builder.setAction(action);
        return this;
    }

    public EventTracker label(final String label) {
        if (label != null) {
            builder.setLabel(label);
        }
        return this;
    }

    public EventTracker value(final long value) {
        builder.setValue(value);
        return this;
    }

    public EventTracker addProduct(final Product product) {
        builder.addProduct(product);
        return this;
    }

    public EventTracker productAction(final ProductAction productAction) {
        builder.setProductAction(productAction);
        return this;
    }

    public EventTracker customDimension(final Dimension dimension) {
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

    public void setNewSession() {
        builder.setNewSession();
    }
}