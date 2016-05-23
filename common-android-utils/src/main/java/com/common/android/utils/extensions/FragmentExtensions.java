package com.common.android.utils.extensions;

import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.common.android.utils.BuildConfig;
import com.common.android.utils.R;
import com.common.android.utils.interfaces.ChainableCommand;
import com.common.android.utils.interfaces.LogTag;
import com.common.android.utils.interfaces.annotations.Transit;
import com.common.android.utils.logging.Logger;
import com.common.android.utils.ui.recyclerView.Orientation;

import static com.common.android.utils.ContextHelper.getFragmentActivity;
import static com.common.android.utils.extensions.ActivityExtensions.getAppCompatActivity;

/**
 * Created by Jan Rabe on 29/07/15.
 */
final public class FragmentExtensions {

    public static final String TAG = FragmentExtensions.class.getSimpleName();

    // region setting fragment container id // TODO: 07/03/16 find a better solution to set this

    @IdRes
    public static int fragmentContainerId = R.id.fragment_container;

    @IdRes
    public static int getFragmentContainerId() {
        return fragmentContainerId;
    }

    public static void setFragmentContainerId(@IdRes final int fragmentContainerId) {
        FragmentExtensions.fragmentContainerId = fragmentContainerId;
    }

    // endregion

    // region logging

    public static boolean LOGGING_ENABLED = BuildConfig.DEBUG;

    public static boolean isLoggingEnabled() {
        return LOGGING_ENABLED;
    }

    public static void setLoggingEnabled(final boolean loggingEnabled) {
        LOGGING_ENABLED = loggingEnabled;
    }

    private FragmentExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    // endregion

    // region misc

    public static boolean isInRoot() {
        return getFragmentActivity().getSupportFragmentManager().getBackStackEntryCount() == 0;
    }

    public static Fragment currentFragment(@IdRes final int container) {
        return getAppCompatActivity().getSupportFragmentManager().findFragmentById(container);
    }

    public static Fragment currentFragment() {
        return getAppCompatActivity().getSupportFragmentManager().findFragmentById(getFragmentContainerId());
    }

    @NonNull
    public static <T extends Fragment> T newInstance(@NonNull final Class<T> type, @Nullable final Bundle args) {
        final T f = ClassExtensions.newInstance(type);
        if (args != null) {
            f.setArguments(args);
        }
        return f;
    }

    public static void printBackStack() {
        if (!LOGGING_ENABLED)
            return;

        final FragmentManager fm = getAppCompatActivity().getSupportFragmentManager();
        Log.v(TAG, "Current BackStack:  " + fm.getBackStackEntryCount());
        for (int entry = 0; entry < fm.getBackStackEntryCount(); entry++) {
            final FragmentManager.BackStackEntry stackEntry = fm.getBackStackEntryAt(entry);
            Log.v(TAG, "[" + stackEntry.getId() + "] " + stackEntry.getName());
        }
    }

    // endregion

    // region transaction type

    @NonNull
    public static <T extends Fragment & LogTag> ChainableCommand<FragmentTransaction> add(@NonNull final T fragment, @NonNull final ChainableCommand<FragmentTransaction> command) {
        if (LOGGING_ENABLED) {
            final Fragment currentFragment = currentFragment();
            final String tag = currentFragment != null ? currentFragment.getClass().getSimpleName() : "[empty]";
            Logger.v(TAG, "[add] " + tag + " with " + fragment.tag());
        }
        return t -> command.execute(t).add(getFragmentContainerId(), fragment, fragment.tag());
    }


    @NonNull
    public static <T extends Fragment & LogTag> ChainableCommand<FragmentTransaction> replace(@NonNull final T fragment, @NonNull final ChainableCommand<FragmentTransaction> command) {
        if (LOGGING_ENABLED) {
            final Fragment currentFragment = currentFragment();
            final String tag = currentFragment != null ? currentFragment.getClass().getSimpleName() : "[empty]";
            Logger.v(TAG, "[replace] " + tag + " with " + fragment.tag());
        }
        return t -> command.execute(t).replace(getFragmentContainerId(), fragment, fragment.tag());
    }

    @NonNull
    public static <T extends Fragment & LogTag> ChainableCommand<FragmentTransaction> remove(@NonNull final T fragment, @NonNull final ChainableCommand<FragmentTransaction> command) {
        if (LOGGING_ENABLED) {
            final Fragment currentFragment = currentFragment();
            final String tag = currentFragment != null ? currentFragment.getClass().getSimpleName() : "[empty]";
            Logger.v(TAG, "[remove] " + tag + " with " + fragment.tag());
        }
        return t -> command.execute(t).remove(fragment);
    }

    // endregion

    // region transaction lifecycle

    @NonNull
    public static ChainableCommand<FragmentTransaction> empty() {
        return t -> t;
    }

    public static void popBackStackImmediate() {
        if (LOGGING_ENABLED)
            Logger.v(TAG, "[popBackStackImmediate]");
        getAppCompatActivity().getSupportFragmentManager().popBackStackImmediate();
    }

    public static void popBackStack() {
        if (LOGGING_ENABLED)
            Logger.v(TAG, "[popBackStack]");
        getAppCompatActivity().getSupportFragmentManager().popBackStack();
    }

    public static void clearBackStack() {
        if (LOGGING_ENABLED)
            Logger.v(TAG, "[clearBackStack]");
        getAppCompatActivity().getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @NonNull
    public static ChainableCommand<FragmentTransaction> beginTransaction() {
        FragmentExtensions.printBackStack();
        if (LOGGING_ENABLED)
            Logger.v(TAG, "[beginTransaction]");
        return t -> getAppCompatActivity().getSupportFragmentManager().beginTransaction();
    }

    @NonNull
    public static ChainableCommand<FragmentTransaction> addToBackStack(@NonNull final String tag, @NonNull final ChainableCommand<FragmentTransaction> command) {
        if (LOGGING_ENABLED)
            Logger.v(TAG, "[addToBackStack]");
        return t -> command.execute(t).addToBackStack(tag);
    }

    @NonNull
    public static void commit(@NonNull final ChainableCommand<FragmentTransaction> command, @android.support.annotation.Nullable final Runnable callback) {
        commit(command);
        if (callback != null)
            callback.run();
    }

    @NonNull
    public static void commit(@NonNull final ChainableCommand<FragmentTransaction> command) {
        if (LOGGING_ENABLED)
            Logger.v(TAG, "[commit]");
        command.execute(null).commit(); // intended 'null' so we can decorate in arbitrary order
        printBackStack();
    }

    // endregion

    // region transition 

    @NonNull
    public static ChainableCommand<FragmentTransaction> setTransition(@Transit final int transition, @NonNull final ChainableCommand<FragmentTransaction> command) {
        return t -> command.execute(t).setTransition(transition);
    }

    @NonNull
    public static ChainableCommand<FragmentTransaction> setTransitionOpen(@NonNull final ChainableCommand<FragmentTransaction> command) {
        return setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN, command);
    }

    @NonNull
    public static ChainableCommand<FragmentTransaction> setTransitionClose(@NonNull final ChainableCommand<FragmentTransaction> command) {
        return setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE, command);
    }

    @NonNull
    public static ChainableCommand<FragmentTransaction> setTransitionFade(@NonNull final ChainableCommand<FragmentTransaction> command) {
        return setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE, command);
    }

    // endregion

    // region animations

    @NonNull
    public static ChainableCommand<FragmentTransaction> setCustomAnimations(@AnimRes final int enter, @AnimRes final int exit, @AnimRes final int popEnter, @AnimRes final int popExit, @NonNull final ChainableCommand<FragmentTransaction> command) {
        return t -> command.execute(t).setCustomAnimations(enter, exit, popEnter, popExit);
    }

    @NonNull
    public static ChainableCommand<FragmentTransaction> setCustomAnimations(@AnimRes final int enter, @AnimRes final int exit, @NonNull final ChainableCommand<FragmentTransaction> command) {
        return t -> command.execute(t).setCustomAnimations(enter, exit);
    }

    @NonNull
    public static ChainableCommand<FragmentTransaction> fade(@NonNull final ChainableCommand<FragmentTransaction> command) {
        if (LOGGING_ENABLED)
            Logger.v(TAG, "[fade]");
        return setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out, command);
    }

    @NonNull
    public static ChainableCommand<FragmentTransaction> grow(@NonNull final ChainableCommand<FragmentTransaction> command) {
        if (LOGGING_ENABLED)
            Logger.v(TAG, "[grow]");
        return setCustomAnimations(R.anim.grow, R.anim.shrink, command);
    }

    @NonNull
    public static ChainableCommand<FragmentTransaction> slide(@NonNull final Orientation orientation, @NonNull final ChainableCommand<FragmentTransaction> command) {
        return orientation == Orientation.Horizontal
                ? slideHorizontally(command)
                : slideVertically(command);
    }

    @NonNull
    private static ChainableCommand<FragmentTransaction> slideVertically(@NonNull final ChainableCommand<FragmentTransaction> command) {
        if (LOGGING_ENABLED)
            Logger.v(TAG, "[slideVertically]");
        return setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_bottom, R.anim.slide_in_top, R.anim.slide_out_bottom, command);
    }

    @NonNull
    private static ChainableCommand<FragmentTransaction> slideHorizontally(@NonNull final ChainableCommand<FragmentTransaction> command) {
        if (LOGGING_ENABLED)
            Logger.v(TAG, "[slideHorizontally]");
        return setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit, R.anim.slide_pop_enter, R.anim.slide_pop_exit, command);
    }

    // endregion

    // region convenient methods for add

    public static <T extends Fragment & LogTag> void addByFading(@NonNull final T fragment) {
        commit(add(fragment, fade(beginTransaction())));
    }

    public static <T extends Fragment & LogTag> void add(@NonNull final T fragment) {
        commit(add(fragment, beginTransaction()));
    }

    public static <T extends Fragment & LogTag> void addToBackStackByFading(@NonNull final T fragment) {
        commit(addToBackStack(fragment.tag(), add(fragment, fade(beginTransaction()))));
    }

    public static <T extends Fragment & LogTag> void addBySlidingHorizontally(@NonNull final T fragment) {
        commit(add(fragment, slide(Orientation.Horizontal, beginTransaction())));
    }

    public static <T extends Fragment & LogTag> void addToBackStackBySlidingHorizontally(@NonNull final T fragment) {
        commit(addToBackStack(fragment.tag(), add(fragment, slide(Orientation.Horizontal, beginTransaction()))));
    }

    // endregion

    // region convenient methods for replace

    public static <T extends Fragment & LogTag> void replace(@NonNull final T fragment) {
        commit(replace(fragment, beginTransaction()));
    }

    public static <T extends Fragment & LogTag> void replaceBySlidingHorizontally(@NonNull final T fragment) {
        commit(replace(fragment, slide(Orientation.Horizontal, beginTransaction())));
    }

    public static <T extends Fragment & LogTag> void replaceToBackStackBySlidingHorizontally(@NonNull final T fragment) {
        commit(addToBackStack(fragment.tag(), replace(fragment, slide(Orientation.Horizontal, beginTransaction()))));
    }

    public static <T extends Fragment & LogTag> void replaceByFading(@NonNull final T fragment) {
        commit(replace(fragment, fade(beginTransaction())));
    }

    public static <T extends Fragment & LogTag> void replaceToBackStackByFading(@NonNull final T fragment) {
        commit(addToBackStack(fragment.tag(), replace(fragment, fade(beginTransaction()))));
    }

    // endregion

    // region convenient methods for remove

    public static <T extends Fragment & LogTag> void remove(@NonNull final T fragment) {
        commit(remove(fragment, beginTransaction()));
    }

    public static <T extends Fragment & LogTag> void removeByFading(@NonNull final T fragment) {
        commit(remove(fragment, fade(beginTransaction())));
    }

    public static <T extends Fragment & LogTag> void removeToBackStackByFading(@NonNull final T fragment) {
        commit(addToBackStack(fragment.tag(), remove(fragment, fade(beginTransaction()))));
    }

    public static <T extends Fragment & LogTag> void removeBySlidingHorizontally(@NonNull final T fragment) {
        commit(remove(fragment, slide(Orientation.Horizontal, beginTransaction())));
    }

    public static <T extends Fragment & LogTag> void removeToBackStackBySlidingHorizontally(@NonNull final T fragment) {
        commit(addToBackStack(fragment.tag(), remove(fragment, slide(Orientation.Horizontal, beginTransaction()))));
    }

    // endregion

}
