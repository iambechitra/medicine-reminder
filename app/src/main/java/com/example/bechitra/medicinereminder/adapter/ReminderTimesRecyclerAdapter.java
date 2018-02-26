package com.example.bechitra.medicinereminder.adapter;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.bechitra.medicinereminder.R;
import com.example.bechitra.medicinereminder.TimeAndQuantity;

import java.util.Calendar;
import java.util.List;


class TimeAndQuantityMedicine extends RecyclerView.ViewHolder {
    TextView timeText, quantityText;

    public TimeAndQuantityMedicine(View itemView) {
        super(itemView);
        timeText = itemView.findViewById(R.id.timeSelectionTextView);
        quantityText = itemView.findViewById(R.id.quantitySelectionTextView);
    }
}

public class ReminderTimesRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<TimeAndQuantity> timeAndQuantities;
    Context context;

    public ReminderTimesRecyclerAdapter(List<TimeAndQuantity> timeAndQuantities, Context context) {
        this.timeAndQuantities = timeAndQuantities;
        this.context = context;
    }

    public void updateDataSet(List<TimeAndQuantity> timeAndQuantities) {
        this.timeAndQuantities = timeAndQuantities;

    }

    public List<TimeAndQuantity> getDataSetFromReminderTime() {
        return timeAndQuantities;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.time_and_quantity_for_medicine_for_cardview, parent, false);
        return new TimeAndQuantityMedicine(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        TimeAndQuantityMedicine tqm = (TimeAndQuantityMedicine)holder;
        TimeAndQuantity timeAndQuantity = timeAndQuantities.get(position);
        tqm.timeText.setText(timeAndQuantity.getTIME());
        tqm.quantityText.setText(timeAndQuantity.getQUANTITY());

        tqm.timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        onItemChanged(position, hourOfDay, minute);
                    }
                }, Calendar.HOUR_OF_DAY, Calendar.MINUTE, false);
                timePickerDialog.show();
            }
        });
    }

    private void onItemChanged(int position, int hour, int minute) {
        String TIME = "";
        if(isPostMeridiem(hour)) {
            int accHour = hour - 12;
            if(accHour != 0)
                TIME = Integer.toString(accHour)+"."+Integer.toString(minute)+"PM";
            else
                TIME = "12."+Integer.toString(minute)+"PM";
        } else {
            if (hour != 0)
                TIME = Integer.toString(hour) + "." + Integer.toString(minute) + "AM";
            else
                TIME = "12." + Integer.toString(minute) + "AM";
        }
        timeAndQuantities.get(position).setTIME(TIME);
        notifyItemChanged(position);
    }

    private boolean isPostMeridiem(int hour) {
        if (hour >=12)
            return true;
        return false;
    }

    @Override
    public int getItemCount() {
        return timeAndQuantities.size();
    }
}
