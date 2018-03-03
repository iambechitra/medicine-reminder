package com.example.bechitra.medicinereminder.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bechitra.medicinereminder.R;

import java.util.StringTokenizer;

/**
 * Created by asish on 3/2/18.
 */

public class DaysIntervalDialog extends DialogFragment
{
    Button incrementButton, decrementButton;
    TextView daysIntervalText;
    String dialogText;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.no_of_days_dialog, null);

        incrementButton = view.findViewById(R.id.incrementDaysIntervalButton);
        decrementButton = view.findViewById(R.id.decrementButtonDaysIntervalButton);
        daysIntervalText = view.findViewById(R.id.daysIntervalTextInDialog);

        incrementButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int value = getDecimalValue(daysIntervalText);
                value++;
                daysIntervalText.setText(value+" days");
            }
        });

        decrementButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int value = getDecimalValue(daysIntervalText);
                if(value > 1) {
                    value--;

                    if(value == 1)
                        daysIntervalText.setText("1 day");
                    else
                        daysIntervalText.setText(value+" days");
                }
            }
        });

        builder.setView(view);
        builder.setTitle("Days interval");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dismiss();
            }
        });

        dialogText = daysIntervalText.getText().toString();
        return builder.create();
    }

    private int getDecimalValue(TextView textView) {
        int DECIMAL_VALUE = 0;

        StringTokenizer stk = new StringTokenizer(textView.getText().toString());
        String decimalString = stk.nextToken();
        DECIMAL_VALUE = Integer.parseInt(decimalString);

        return DECIMAL_VALUE;
    }

    public String getData() { return dialogText; }
}
