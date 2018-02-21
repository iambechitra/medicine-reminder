package com.example.bechitra.medicinereminder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bechitra.medicinereminder.MedicineInformation;
import com.example.bechitra.medicinereminder.R;

import java.util.List;


public class MainScreenRecyclerAdapter extends RecyclerView.Adapter<MainScreenRecyclerAdapter.MedicineInformationViewHolder>{

    private Context context;
    private List<MedicineInformation> medicineInformationList;
    private RelativeLayout.LayoutParams params;

    public MainScreenRecyclerAdapter(Context context, List<MedicineInformation> medicineInformationList) {
        this.context = context;
        this.medicineInformationList = medicineInformationList;
        this.params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public MedicineInformationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.reminder_list_viewer,  null);
        view.setLayoutParams(params);
        return new MedicineInformationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MedicineInformationViewHolder holder, int position) {
        MedicineInformation medicineInformation = medicineInformationList.get(position);
        holder.medicineName.setText(medicineInformation.getNameOfMedicine());
        holder.medicineInformation.setText(medicineInformation.getDescription());
        holder.medicineTakeTime.setText(medicineInformation.getTime());
    }

    @Override
    public int getItemCount() {
        return medicineInformationList.size();
    }

    class MedicineInformationViewHolder extends RecyclerView.ViewHolder{
        TextView medicineName, medicineInformation, medicineTakeTime;

        public MedicineInformationViewHolder(View itemView) {
            super(itemView);
            medicineName = itemView.findViewById(R.id.medicineNameTextView);
            medicineInformation = itemView.findViewById(R.id.descriptionTextView);
            medicineTakeTime = itemView.findViewById(R.id.timeTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(v.getContext(), "ItemSerial Clicked! "+medicineName.getText(), Toast.LENGTH_SHORT).show();
                    medicineName.setText("ItemSerial Click");
                }
            });
        }
    }
}
