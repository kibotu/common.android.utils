package com.common.android.utils.ui.components;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private OnTimeSetListener listener;

    public TimePickerFragment(final OnTimeSetListener listener) {
        super();
        this.listener = listener;
    }

    @NonNull
    @Override
    public TimePickerDialog onCreateDialog(final Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(final TimePicker view, final int hourOfDay, final int minute) {
        if (listener != null)
            listener.onTimeSet(hourOfDay, minute);
    }

    interface OnTimeSetListener {
        void onTimeSet(int hourOfDay, int minute);
    }
}