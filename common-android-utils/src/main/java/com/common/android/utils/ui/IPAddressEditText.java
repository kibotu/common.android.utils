package com.common.android.utils.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import com.common.android.utils.R;


/**
 * Liberated from <a href="http://stackoverflow.com/a/15259987">http://stackoverflow.com/a/15259987</a>
 */
public class IPAddressEditText extends EditText {

    public enum Type {
        IpAddress, SubnetMask
    }

    Type type = Type.IpAddress;

    public IPAddressEditText(Context context) {
        super(context);
        init();
    }

    public IPAddressEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        getAttributes(context, attrs);
        init();
    }

    public IPAddressEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttributes(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public IPAddressEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        getAttributes(context, attrs);
        init();
    }

    private void getAttributes(Context context, AttributeSet attrs) {
        final TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.IPAddressEditText);

        final int ordinal = arr.getInt(R.styleable.IPAddressEditText_inputType, 0);
        if (ordinal >= 0 && ordinal < Type.values().length) {
            type = Type.values()[ordinal];
        }

        arr.recycle();
    }

    private void init() {
        if (isInEditMode())
            return;

        setInputType(InputType.TYPE_CLASS_PHONE);
        setFilters(createInputFilters());

        addTextChangedListener(createTextWatcher());
    }

    @NonNull
    private InputFilter[] createInputFilters() {
        return new InputFilter[]{(source, start, end, dest, dstart, dend) -> {
            if (end <= start)
                return null;

            String destTxt = dest.toString();
            String resultingTxt = destTxt.substring(0, dstart) + source.subSequence(start, end) + destTxt.substring(dend);

            if (!resultingTxt.matches("^\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3})?)?)?)?)?)?"))
                return "";

            String[] splits = resultingTxt.split("\\.");
            for (String split : splits)
                if (Integer.valueOf(split) > 255)
                    return "";

            return null;
        }};
    }

    @NonNull
    private TextWatcher createTextWatcher() {
        return new TextWatcher() {
            boolean deleting = false;
            int lastCount = 0;

            @Override
            public void afterTextChanged(Editable s) {
                if (deleting)
                    return;

                String working = s.toString();
                String[] split = working.split("\\.");
                String string = split[split.length - 1];

                if (string.length() == 3 || string.equalsIgnoreCase("0")
                        || (string.length() == 2 && Character.getNumericValue(string.charAt(0)) > (type == Type.IpAddress ? 1 : 2)))
                    s.append('.');
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                deleting = lastCount >= count;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Nothing happens here
            }
        };
    }
}