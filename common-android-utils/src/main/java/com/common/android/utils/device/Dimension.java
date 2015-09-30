package common.android.utils.device;

import android.content.Context;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Jan Rabe on 23/09/15.
 */
public class Dimension {

    public final int width, height;

    public Dimension(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    public static String getDisplayCountry(@NotNull final Context context) {
        return context.getResources().getConfiguration().locale.getDisplayCountry();
    }

    @NotNull
    @Override
    public String toString() {
        return "Dimension{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Dimension dimension = (Dimension) o;

        if (width != dimension.width) return false;
        return height == dimension.height;
    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        return result;
    }
}
