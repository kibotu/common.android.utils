package common.android.utils.interfaces.annotations;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static com.google.android.gms.analytics.Logger.LogLevel.*;
import static java.lang.annotation.RetentionPolicy.SOURCE;

@IntDef({VERBOSE, INFO, WARNING, ERROR})
@Retention(SOURCE)
public @interface LogLevel {
}