package com.common.android.utils.interfaces.annotations;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_CLOSE;
import static android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE;
import static android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
import static android.support.v4.app.FragmentTransaction.TRANSIT_NONE;

@IntDef({TRANSIT_NONE, TRANSIT_FRAGMENT_OPEN, TRANSIT_FRAGMENT_CLOSE, TRANSIT_FRAGMENT_FADE})
@Retention(RetentionPolicy.SOURCE)
public @interface Transit {
}