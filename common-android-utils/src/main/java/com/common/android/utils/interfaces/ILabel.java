package common.android.utils.interfaces;

import android.support.annotation.StringRes;

/**
 * Created by Jan Rabe on 24/09/15.
 */
public interface ILabel {

    @StringRes
    int getLabelResourceId();

    String getLabel();
}
