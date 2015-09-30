package common.android.utils.extensions;

import android.support.annotation.ColorRes;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import com.adviqo.app.MainActivity;
import com.adviqo.shared.SupportedCountry;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Jan Rabe on 24/09/15.
 */
final public class SpannableExtensions {

    private SpannableExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static Spannable colorize(@NotNull final Spannable spannable, @ColorRes final int color) {
        final Spannable result = new SpannableString(spannable);
        result.setSpan(new ForegroundColorSpan(MainActivity.currentMainActivity().getResources().getColor(color)), 0, result.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return result;
    }

    public static Spannable strikeThrough(@NotNull final Spannable spannable) {
        final Spannable result = new SpannableString(spannable);
        result.setSpan(new StrikethroughSpan(), 0, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return result;
    }

    public static Spannable localize(final float money) {
        return new SpannableString(SupportedCountry.geDefaultCountry().getCurrency().format(money));
    }
}
