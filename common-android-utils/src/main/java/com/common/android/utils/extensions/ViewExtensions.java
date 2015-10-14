package com.common.android.utils.extensions;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.RawRes;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.common.android.utils.ContextHelper;
import com.common.android.utils.R;
import com.common.android.utils.interfaces.ICommand;
import com.common.android.utils.ui.PicassoBigCache;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.os.Build.VERSION.SDK_INT;
import static com.common.android.utils.ContextHelper.getContext;
import static com.common.android.utils.extensions.BitmapExtensions.getBitmap;
import static com.common.android.utils.extensions.DeviceExtensions.hideKeyboard;
import static com.common.android.utils.extensions.MathExtensions.convertDpToPixel;
import static com.common.android.utils.extensions.MathExtensions.roundToInt;

/**
 * Created by Jan Rabe on 24/09/15.
 */
public class ViewExtensions {

    private ViewExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static boolean isVisible(@NotNull final View view) {
        return view.getVisibility() == View.INVISIBLE || view.getVisibility() == View.GONE;
    }

    public static void toggleViewsVisibilityGone(@NotNull final View... views) {
        for (final View view : views)
            view.setVisibility(view.getVisibility() == View.GONE
                    ? View.VISIBLE
                    : View.GONE);
    }

    public static void toggleViewsVisibility(@NotNull final View... views) {
        for (final View view : views)
            view.setVisibility(view.getVisibility() == View.INVISIBLE
                    ? View.VISIBLE
                    : View.INVISIBLE);
    }


    public static void loadInto(final String imageURL, final ImageView imageView) {
        PicassoBigCache
                .INSTANCE
                .getPicassoBigCache()
                .load(imageURL)
                .fit()
                .centerCrop()
                .into(imageView);
    }

    public static void loadIntoWithPlaceholder(final String imageURL, final ImageView imageView, @DrawableRes final int placeholderResId) {
        PicassoBigCache
                .INSTANCE
                .getPicassoBigCache()
                .load(imageURL)
                .placeholder(placeholderResId)
                .fit()
                .centerCrop()
                .into(imageView);
    }

    public static void loadInto(@DrawableRes final int resourceId, final ImageView imageView) {
        PicassoBigCache
                .INSTANCE
                .getPicassoBigCache()
                .load(resourceId).into(imageView);
    }

    public static void loadInto(final String imageUrl, @NotNull final ImageView imageView, @DrawableRes final int placeholder, final Callback callback) {
        PicassoBigCache
                .INSTANCE
                .getPicassoBigCache()
                .load(imageUrl).placeholder(placeholder)
                .into(imageView, callback);
    }


    public static void setOneTimeGlobalLayoutListener(@NotNull final View v, @NotNull final ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (SDK_INT >= 16)
                    v.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                else
                    v.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                onGlobalLayoutListener.onGlobalLayout();

            }
        });
    }

    public static void flipHorizontically(@NotNull final ImageView imageView) {
        imageView.setImageBitmap(BitmapExtensions.flipHorizontically(getBitmap(imageView)));
    }

    public static void setLayoutHeight(@NotNull final View v, final int height) {
        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
        params.height = height;
        v.setLayoutParams(params);
    }

    public static void setTextSwitcherAnimation(@NotNull final TextSwitcher v, @NotNull final Typeface typeface) {
        v.setFactory(new ViewSwitcher.ViewFactory() {

            @NotNull
            @Override
            public View makeView() {
                final LayoutInflater inflater = LayoutInflater.from(getContext());
                final TextView textView = (TextView) inflater.inflate(R.layout.item_title, null);
                textView.setTypeface(typeface);
                return textView;
            }
        });
        final Animation in = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_top);
        final Animation out = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_bottom);
        v.setInAnimation(in);
        v.setOutAnimation(out);
    }

    public static void getLocationOnScreen(@Nullable final View view, @Nullable final Rect src, @Nullable Rect dst) {
        if (view == null || src == null)
            return;

        if (dst == null)
            dst = new Rect();

        final int[] location = new int[2];
        view.getLocationOnScreen(location);
        final int offsetX = location[0];
        final int offsetY = location[1];

        dst.set(src.left + offsetX, src.top + offsetY, src.right + offsetX,
                src.bottom + offsetY);
    }


    public static void setFont(@NotNull final Typeface font, @NotNull final ArrayList<View> views) {

        final List<TextView> textViews = new ArrayList<>();
        for (final View view : views) {
            if (view instanceof TextView)
                textViews.add((TextView) view);
        }

        for (final TextView v : textViews)
            v.setTypeface(font);
    }

    public static void showView(@Nullable final View v, final boolean isShown) {
        if (v == null)
            return;

        v.setVisibility(isShown
                ? View.VISIBLE
                : View.GONE);
    }

    public static void showViews(@NotNull final View... views) {
        for (final View view : views)
            view.setVisibility(View.VISIBLE);
    }

    public static void showViews(@NotNull final List<View> views) {
        for (final View view : views)
            view.setVisibility(View.VISIBLE);
    }

    public static void hideViews(@NotNull final View... views) {
        for (final View view : views)
            view.setVisibility(View.INVISIBLE);
    }

    public static void hideViews(@NotNull final List<View> views) {
        for (final View view : views)
            view.setVisibility(View.INVISIBLE);
    }

    public static void hideViewsCompletely(@NotNull final View... views) {
        for (final View view : views)
            view.setVisibility(View.GONE);
    }

    public static void hideViewsCompletely(@NotNull final List<View> views) {
        for (final View view : views)
            view.setVisibility(View.GONE);
    }

    @NotNull
    public static int[] getScreenLocation(@NotNull final View view) {
        final int[] locations = new int[2];
        view.getLocationOnScreen(locations);
        return locations;
    }

    public static void moveViewRelatively(@NotNull final View view, final int left, final int top) {
        final int[] location = getScreenLocation(view);
        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(left + location[0], top + location[1], 0, 0);
        final ViewGroup.LayoutParams p = view.getLayoutParams();
        params.width = p.width;
        params.height = p.height;
        view.setLayoutParams(params);
    }

    public static void moveView(@NotNull final View view, final int left, final int top) {
        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(left, top, 0, 0);
        final ViewGroup.LayoutParams p = view.getLayoutParams();
        params.width = p.width;
        params.height = p.height;
        view.setLayoutParams(params);
    }

    public static void setFont(@NotNull final Typeface font, @NotNull final TextView... views) {
        for (final TextView v : views)
            v.setTypeface(font);
    }

    public static void addMargin(@NotNull final View view, final int left, final int top, final int right, final int bottom) {
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(left, top, right, bottom);
        view.setLayoutParams(layoutParams);
    }

    @NotNull
    public static Drawable getScaledDrawable(@DrawableRes final int resourceId, final int scaleInDp) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        final DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        options.inScreenDensity = metrics.densityDpi;
        options.inTargetDensity = metrics.densityDpi;
        options.inDensity = DisplayMetrics.DENSITY_DEFAULT;
        final int px = roundToInt(convertDpToPixel(scaleInDp));
        final Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), resourceId, options);
        final BitmapDrawable drawable = new BitmapDrawable(getContext().getResources(), Bitmap.createScaledBitmap(bitmap, px, px, true));
        Log.v("Scaling", "Scaling image [" + scaleInDp + "dp to " + px + "px] => [" + bitmap.getWidth() + "x" + bitmap.getHeight() + " px] to [" + drawable.getIntrinsicWidth() + "x" + drawable.getIntrinsicHeight() + " px]");
        bitmap.recycle();
        return drawable;
    }

    public static View getContentRoot() {
        return getContext()
                .getWindow()
                .getDecorView()
                .findViewById(android.R.id.content);
    }

    public static void addHtmlContentToTextView(@RawRes final int resourceId, @NotNull final TextView view) {
        final InputStream inputStream = getContext().getResources().openRawResource(resourceId);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        final StringBuffer body = new StringBuffer();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                body.append(line);
            }
            reader.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        view.setText(Html.fromHtml(body.toString()));
        view.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static Rect getLocationOnScreen(@NotNull final View textView) {
        final Rect rect = new Rect();
        final int[] location = new int[2];

        textView.getLocationOnScreen(location);

        rect.left = location[0];
        rect.top = location[1];
        rect.right = location[0] + textView.getWidth();
        rect.bottom = location[1] + textView.getHeight();

        return rect;
    }

    public static void hideOnLostFocus(@NotNull final MotionEvent event, @Nullable final View... views) {

        if (views == null)
            return;

        boolean hit = false;

        for (final View view : views)
            hit |= getLocationOnScreen(view).contains((int) event.getX(), (int) event.getY());

        if (event.getAction() == MotionEvent.ACTION_DOWN && !hit)
            hideKeyboard(views[0]);
    }

    public static void sendEmail(final String address, final String subject, final Spanned body, final int requestCode, final String popupTitle) {
        final Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", address, null));
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT, body);
        try {
            getContext().startActivityForResult(Intent.createChooser(i, popupTitle), requestCode);
        } catch (@NotNull final ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void showMarket() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        final String packageName = getContext().getApplicationContext().getPackageName();
        intent.setData(Uri.parse("market://details?id=" + packageName));
        getContext().startActivity(intent);
    }

    public static void setViewBackgroundWithoutResettingPadding(@NotNull final View v, @DrawableRes final int backgroundResId) {
        final int paddingBottom = v.getPaddingBottom(), paddingLeft = v.getPaddingLeft();
        final int paddingRight = v.getPaddingRight(), paddingTop = v.getPaddingTop();
        v.setBackgroundResource(backgroundResId);
        v.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    public static void setViewBackgroundColorWithoutResettingPadding(@NotNull final View v, @ColorRes final int color) {
        final int paddingBottom = v.getPaddingBottom(), paddingLeft = v.getPaddingLeft();
        final int paddingRight = v.getPaddingRight(), paddingTop = v.getPaddingTop();
        v.setBackgroundColor(v.getResources().getColor(color));
        v.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }


    public static void addFadeOutToText(@Nullable final TextView textView, final int galleryHeight, @ColorRes final int fromColor, @ColorRes final int toColor) {
        if (textView == null) {
            return;
        }

        final Resources resources = textView.getContext().getResources();
        final Rect bounds = new Rect();
        final Paint textPaint = textView.getPaint();
        final CharSequence text = textView.getText();
        textPaint.getTextBounds(text.toString(), 0, text.length(), bounds);
        final int viewWidth = textView.getWidth();
        final int startHeight = textView.getLineCount() > 2 ? galleryHeight / 2 : 0;
        final int availableWidth = (int) (viewWidth - textView.getPaddingLeft() - textView.getPaddingRight() - textPaint.measureText(text.toString()));
        if (availableWidth < 0) {
            final Shader textShader = new LinearGradient(3 * viewWidth / 4, startHeight, viewWidth, textView.getPaint().getTextSize(),
                    new int[]{resources.getColor(fromColor), resources.getColor(toColor)},
                    null, Shader.TileMode.CLAMP);
            textView.getPaint().setShader(textShader);
        }
    }

    public static void setLayoutSize(@NotNull final View v, final int width, final int height) {
        final ViewGroup.LayoutParams params = v.getLayoutParams();
        params.width = width;
        params.height = height;
        v.post(new Runnable() {
            @Override
            public void run() {
                v.setLayoutParams(params);
            }
        });
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
    }

    public static void focusView(@NotNull final View v, @NotNull final Fragment fragment) {
        v.requestFocus();
        final InputMethodManager imm = (InputMethodManager) fragment.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static View getContentRoot(final Activity context) {
        return context
                .getWindow()
                .getDecorView()
                .findViewById(android.R.id.content);
    }

    public static void addLinearLayoutMargin(@NotNull final View view, final int left, final int top, final int right, final int bottom) {
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(left, top, right, bottom);
        view.setLayoutParams(layoutParams);
    }

    public static void setRelativeLayoutHeight(@NotNull final View v, final int height) {
        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
        params.height = height;
        v.setLayoutParams(params);
    }

    public static void setBackground(@NotNull final View view, @DrawableRes final int background) {
        Picasso.with(ContextHelper.getContext()).load(background).into(new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, final Picasso.LoadedFrom from) {
                if (Build.VERSION.SDK_INT >= 16)
                    view.setBackground(new BitmapDrawable(ContextHelper.getContext().getResources(), bitmap));
                else
                    view.setBackgroundDrawable(new BitmapDrawable(ContextHelper.getContext().getResources(), bitmap));

            }

            @Override
            public void onBitmapFailed(final Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(final Drawable placeHolderDrawable) {

            }
        });
    }

    @Nullable
    public static <T extends View> T setColor(@Nullable final T view, @ColorRes final int color) {
        if (view == null)
            return view;
        view.setBackgroundColor(getContext().getResources().getColor(color));
        return view;
    }

    public static <T extends View> void applyToAllViews(@NotNull final HashMap<Integer, T> views, @NotNull final ICommand<T> command) {
        for (int i = 0; i < views.size(); ++i)
            command.execute(views.get(i));
    }
}
