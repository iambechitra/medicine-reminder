package com.example.bechitra.medicinereminder.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.bechitra.medicinereminder.R;
import com.example.bechitra.medicinereminder.TimeAndQuantity;
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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.time_and_quantity_for_medicine_for_cardview, parent, false);
        return new TimeAndQuantityMedicine(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TimeAndQuantityMedicine tqm = (TimeAndQuantityMedicine)holder;
        TimeAndQuantity timeAndQuantity = timeAndQuantities.get(position);
        tqm.timeText.setText(timeAndQuantity.getTIME());
        tqm.quantityText.setText(timeAndQuantity.getQUANTITY());
    }

    @Override
    public int getItemCount() {
        return timeAndQuantities.size();
    }
}
