package com.common.android.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Jan Rabe on 16/07/15.
 */
public class TestClass {

    public static void helloWorld(Context context) {
        Toast.makeText(context, "Hello world!", Toast.LENGTH_LONG).show();
    }
}
