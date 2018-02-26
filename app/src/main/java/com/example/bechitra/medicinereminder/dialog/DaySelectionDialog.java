package com.example.bechitra.medicinereminder.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.example.bechitra.medicinereminder.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bechitra on 2/26/18.
 */

public class DaySelectionDialog extends DialogFragment{
    Map<String, Boolean> isDaySelected = new HashMap<>();
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MapInitialize();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] daysOfWeek = {"Saturday","Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        builder.setTitle("Select days to take the medication");
        builder.setMultiChoiceItems(daysOfWeek, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                isDaySelected.put(daysOfWeek[which], isChecked);
            }
        });

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return builder.create();
    }

    private void MapInitialize() {
        isDaySelected.put("Saturday", false);
        isDaySelected.put("Sunday", false);
        isDaySelected.put("Monday", false);
        isDaySelected.put("Tuesday", false);
        isDaySelected.put("Wednesday", false);
        isDaySelected.put("Thursday", false);
        isDaySelected.put("Friday", false);
    }
}
