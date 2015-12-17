package com.common.android.utils.extensions;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.common.android.utils.R;
import com.common.android.utils.interfaces.ICallback;
import com.common.android.utils.interfaces.ICommand;
import com.common.android.utils.interfaces.ILogTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.common.android.utils.ContextHelper.getContext;
import static com.common.android.utils.extensions.DeviceExtensions.hideKeyboard;

/**
 * Created by Jan Rabe on 29/07/15.
 */
public class FragmentExtensions {

    public static final String TAG = FragmentExtensions.class.getSimpleName();

    private FragmentExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static void addContextMenu(@NotNull final View view, @NotNull final Fragment fragment) {
        fragment.registerForContextMenu(view);
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(@NotNull final View v, final boolean hasFocus) {
                if (hasFocus) {
                    hideKeyboard(v);
                    fragment.getActivity().openContextMenu(v);
                }
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                fragment.getActivity().openContextMenu(v);
            }
        });
    }

    public static void focusView(@NotNull final View v, @NotNull final Fragment fragment) {
        v.requestFocus();
        final InputMethodManager imm = (InputMethodManager) fragment.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static <T extends Fragment & ILogTag> void replaceWithFade(@Nullable final FragmentManager fragmentManager, @Nullable final T fragment) {
        if (fragmentManager == null || fragment == null)
            return;

        replaceWithFade(fragmentManager, fragment, fragment.tag(), new ICommand<FragmentTransaction>() {
            @Override
            public FragmentTransaction execute(@NotNull final FragmentTransaction fragmentTransaction) {
                return fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    public static <T extends Fragment & ILogTag> void replaceSlidingHorizontallyWith(@Nullable final FragmentManager fragmentManager, @Nullable final T fragment) {
        if (fragmentManager == null || fragment == null)
            return;

        replaceWithFade(fragmentManager, fragment, fragment.tag(), new ICommand<FragmentTransaction>() {
            @Override
            public FragmentTransaction execute(@NotNull final FragmentTransaction fragmentTransaction) {
                return fragmentTransaction.setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit, R.anim.slide_pop_enter, R.anim.slide_pop_exit);
            }
        });
    }

    public static <T extends Fragment & ILogTag> void replaceVerticallyWith(@Nullable final FragmentManager fragmentManager, @Nullable final T fragment) {
        if (fragmentManager == null || fragment == null)
            return;

        replaceWithFade(fragmentManager, fragment, fragment.tag(), new ICommand<FragmentTransaction>() {
            @Override
            public FragmentTransaction execute(@NotNull final FragmentTransaction fragmentTransaction) {
                return fragmentTransaction.setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_bottom, R.anim.slide_in_top, R.anim.slide_out_bottom);
            }
        });
    }

    private static void replaceWithFade(@NotNull final FragmentManager fragmentManager, @Nullable final Fragment fragment, @NotNull final String identifier, @NotNull final ICommand<FragmentTransaction> injector) {
        if (fragment == null)
            return;

        // inject e.g. animation into transaction
        injector.execute(fragmentManager.beginTransaction())
                .replace(R.id.fragment_container, fragment, identifier)
                .addToBackStack(identifier)
                .commit();

        printBackStack();
    }

    public static <T extends Fragment & ILogTag> void replaceWithNoBackStack(@Nullable final FragmentManager fragmentManager, @Nullable final T fragment) {
        if (fragmentManager == null || fragment == null)
            return;

        final String identifier = fragment.tag();
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, identifier)
                .commit();

        printBackStack();
    }

    public static <T extends Fragment & ILogTag> void replaceWithTransitOpen(@Nullable final FragmentManager fragmentManager, @NotNull final T fragment) {
        if (fragmentManager == null)
            return;

        fragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.fragment_container, fragment, fragment.tag())
                .addToBackStack(fragment.tag())
                .commit();

        printBackStack();
    }

    public static <T extends Fragment & ILogTag> void add(@NotNull final FragmentManager fm, @NotNull final T fragment) {
        Log.v(TAG, "add: " + fragment.tag());

        final FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_container, fragment, fragment.tag())
                .commit();
        printBackStack();
    }

    public static <T extends Fragment & ILogTag> void addToBackStack(@NotNull final FragmentManager fm, @NotNull final T fragment) {
        addToBackStack(fm, fragment, fragment.tag());
    }

    public static <T extends Fragment & ILogTag> void addToBackStack(@NotNull final FragmentManager fragmentManager, @NotNull final T fragment, final String identifier) {
        Log.v(TAG, "add: " + fragment.tag());

        final FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.fragment_container, fragment, identifier)
                .addToBackStack(identifier)
                .commit();
        printBackStack();
    }

    public static <T extends Fragment & ILogTag> void removeFragments(@NotNull final FragmentManager fm, @NotNull final T... fragments) {
        for (final Fragment fragment : fragments) {
            if (fragment == null)
                continue;
            fm.beginTransaction()
                    .remove(fragment)
                    .commit();
            Log.v(TAG, "remove: " + fragment.getClass().getSimpleName());
        }
        printBackStack();
    }

    public static <T extends Fragment & ILogTag> void removeFragmentsFadeOut(@NotNull final FragmentManager fm, @NotNull final T... fragments) {
        for (final Fragment fragment : fragments) {
            if (fragment == null)
                continue;
            fm.beginTransaction()
                    .remove(fragment)
                    .commit();
            Log.v(TAG, "remove: " + fragment.getClass().getSimpleName());
        }
        printBackStack();
    }

    public static void removeFragment(@NotNull final FragmentManager fm, @NotNull final Fragment fragment) {
        fm.beginTransaction().remove(fragment).commit();
        Log.v(TAG, "removed: " + fragment.getClass().getSimpleName());
    }

    public static <T> void removeFragmentFadeOut(@NotNull final FragmentManager fm, @NotNull final Fragment fragment, @Nullable final ICallback<T> onAnimationComplete) {
        fm.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .remove(fragment).commit();
        Log.v(TAG, "removed: " + fragment.getClass().getSimpleName());
        if (onAnimationComplete != null)
            onAnimationComplete.onSuccess(null);
    }

    public static void printBackStack() {
        final FragmentManager fm = getContext().getSupportFragmentManager();

        Log.v(TAG, "Current BackStack:  " + fm.getBackStackEntryCount());
        for (int entry = 0; entry < fm.getBackStackEntryCount(); entry++) {
            final FragmentManager.BackStackEntry stackEntry = fm.getBackStackEntryAt(entry);
            Log.v(TAG, "[" + stackEntry.getId() + "] " + stackEntry.getName());
        }
    }

    public static void popBackStackImmediate(@NotNull final FragmentManager fm) {
        fm.popBackStackImmediate();
        printBackStack();
    }

    public static void clearStack(@NotNull final FragmentManager fm) {
        fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        printBackStack();
    }

    @NotNull
    public static <T extends Fragment> T newInstance(@NotNull final Class<T> type, @Nullable final Bundle args) {
        final T f = ClassExtensions.newInstance(type);
        if (args != null) {
            f.setArguments(args);
        }
        return f;
    }

    public static boolean inRoot(@NotNull final FragmentManager fragmentManager) {
        return fragmentManager.getBackStackEntryCount() == 0;
    }

    public static Fragment currentFragment(@IdRes final int container) {
        return getContext().getSupportFragmentManager().findFragmentById(container);
    }
}
