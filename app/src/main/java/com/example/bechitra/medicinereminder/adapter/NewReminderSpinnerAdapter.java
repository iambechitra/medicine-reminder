package com.example.bechitra.medicinereminder.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bechitra.medicinereminder.R;

import java.util.List;

/**
 * Created by bechitra on 2/20/18.
 */

public class NewReminderSpinnerAdapter extends BaseAdapter{
    Activity activity;
    LayoutInflater inflater;
    List<String>list;

    public NewReminderSpinnerAdapter(Activity activity, List<String> list) {
        this.activity = activity;
        this.inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;



    }

    public void updateDataSet(List<String> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(R.layout.new_reminder_spinner_row, null);
        TextView textView = view.findViewById(R.id.newReminderSpinnerRowTextView);
        textView.setText(list.get(position));
        if(list.get(position).equals("INTERVALS") || list.get(position).equals("FREQUENCY"))
            textView.setTextColor(Color.parseColor("#9E9E9E"));

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }
}
