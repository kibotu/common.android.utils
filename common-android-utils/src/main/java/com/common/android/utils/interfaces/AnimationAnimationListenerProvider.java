package com.common.android.utils.interfaces;

import android.view.animation.Animation;

/**
 * Created by jan.rabe on 07/03/16.
 */
public interface AnimationAnimationListenerProvider {

    Animation.AnimationListener getAnimationListener();

    void setAnimationListener(Animation.AnimationListener animationListener);
}
