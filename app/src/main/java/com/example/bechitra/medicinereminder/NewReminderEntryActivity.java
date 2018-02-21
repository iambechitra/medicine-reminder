package com.example.bechitra.medicinereminder;

import android.animation.ObjectAnimator;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bechitra.medicinereminder.Adapter.NewReminderSpinnerAdapter;
import com.example.bechitra.medicinereminder.Adapter.ReminderTimesRecyclerAdapter;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewReminderEntryActivity extends AppCompatActivity
{
    List<String> initialItems = new ArrayList<>();
    List<String> frequencyExpandedItems = new ArrayList<>();
    List<String> intervalExpandedItems = new ArrayList<>();
    List<String> finalExpandedItems = new ArrayList<>();
    List<TimeAndQuantity> timeAndQuantities = new ArrayList<>();

    private RecyclerView recyclerViewWithinRememberTimes;
    private ExpandableLinearLayout expandableLinearLayout1, /*expandableLinearLayout2,*/ expandableLinearLayout3, expandableLinearLayout4, expandableLinearLayout5;
    private RelativeLayout scrollButton4, scrollButton1, scrollButton2, scrollButton3, scrollButton5;
    private CardView cardView1, cardView2, cardView3, cardView4, cardView5;
    private ScrollView scrollView;
    private TextView dateSelectionText;
    private Spinner reminderTimesSpinner;

    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reminder_entry);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findViewById();
        initializeToSpinnerList();

        setViewExpandable(expandableLinearLayout1, cardView1, scrollButton1);
        //setViewExpandable(expandableLinearLayout2, cardView2, scrollButton2);
        setViewExpandable(expandableLinearLayout3, cardView3, scrollButton3);
        setViewExpandable(expandableLinearLayout4, cardView4, scrollButton4);
        setViewExpandable(expandableLinearLayout5, cardView5, scrollButton5);

        NewReminderSpinnerAdapter spinnerAdapter = new NewReminderSpinnerAdapter(this, initialItems);
        reminderTimesSpinner.setAdapter(spinnerAdapter);
        reminderTimesSpinner.setSelection(0);

        final int[] i = {0};
        //randomListGenerator(++i[0]);
        recyclerViewWithinRememberTimes.setHasFixedSize(true);
        recyclerViewWithinRememberTimes.setLayoutManager(new LinearLayoutManager(this));
        final ReminderTimesRecyclerAdapter adapter = new ReminderTimesRecyclerAdapter(timeAndQuantities, this);
        recyclerViewWithinRememberTimes.setAdapter(adapter);

        reminderTimesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id)
            {
                Toast.makeText(NewReminderEntryActivity.this, initialItems.get(position), Toast.LENGTH_SHORT).show();

                if (position != 0)
                {
                    randomListGenerator(++i[0]);
                    adapter.updateDataSet(timeAndQuantities);
                    adapter.notifyDataSetChanged();
                    recyclerViewWithinRememberTimes.scrollToPosition(timeAndQuantities.size());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        dateSelectionText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewReminderEntryActivity.this,
                                                                         R.style.Theme_AppCompat_Light_Dialog, dateSetListener, calendar.get(Calendar.YEAR),
                                                                         calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
                datePickerDialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                dateSelectionText.setText(Integer.toString(dayOfMonth) + "/" + Integer.toString(month + 1) + "/" + Integer.toString(year));
            }
        };
    }

    private void setViewExpandable(final ExpandableLinearLayout expandableLinearLayout, CardView onclickView, final RelativeLayout scrollButton)
    {
        expandableLinearLayout.setInRecyclerView(true);
        expandableLinearLayout.setListener(new ExpandableLayoutListenerAdapter()
        {
            @Override
            public void onPreOpen()
            {
                changeRotation(scrollButton, 0f, 180f).start();
            }

            @Override
            public void onPreClose()
            {
                changeRotation(scrollButton, 180f, 0f).start();
            }
        });
        onclickView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                expandableLinearLayout.toggle();
            }
        });
    }

    private void randomListGenerator(int index)
    {
        for (int i = 0; i < index; i++)
        {
            timeAndQuantities.add(new TimeAndQuantity("Time " + (i + 1), "Take " + (i + 1)));
        }
    }

    private void initializeToSpinnerList()
    {
        initialItems.add("FREQUENCY");
        initialItems.add("Once a day");
        initialItems.add("Twice a day");
        initialItems.add("3 times a day");
        initialItems.add("...");
        initialItems.add("INTERVALS");
        initialItems.add("Every 12 hours");
        initialItems.add("Every 8 hours");
        initialItems.add("Every 6 hours");
        initialItems.add("...");


        frequencyExpandedItems.add("Once a day");
        frequencyExpandedItems.add("Twice a day");
        frequencyExpandedItems.add("3 times a day");
        frequencyExpandedItems.add("4 times a day");
        frequencyExpandedItems.add("5 times a day");
        frequencyExpandedItems.add("6 times a day");
        frequencyExpandedItems.add("7 times a day");
        frequencyExpandedItems.add("8 times a day");
        frequencyExpandedItems.add("9 times a day");
        frequencyExpandedItems.add("10 times a day");
        frequencyExpandedItems.add("11 times a day");
        frequencyExpandedItems.add("12 times a day");

        intervalExpandedItems.add("Every 12 hours");
        intervalExpandedItems.add("Every 8 hours");
        intervalExpandedItems.add("Every 6 hours");
        intervalExpandedItems.add("Every 4 hours");
        intervalExpandedItems.add("Every 3 hours");
        intervalExpandedItems.add("Every 2 hours");
        intervalExpandedItems.add("Every hour");

        finalExpandedItems.add("FREQUENCY");
        finalExpandedItems.addAll(frequencyExpandedItems);
        finalExpandedItems.add("INTERVALS");
        finalExpandedItems.addAll(intervalExpandedItems);
    }

    private void findViewById()
    {
        scrollView = findViewById(R.id.scrollView);
        reminderTimesSpinner = findViewById(R.id.reminderTimeSelectorSpinner);
        recyclerViewWithinRememberTimes = findViewById(R.id.recyclerViewForReminderTimes);


        expandableLinearLayout1 = findViewById(R.id.expandableLayout1);
        scrollButton1 = findViewById(R.id.scrollButton1);
        cardView1 = findViewById(R.id.cardViewer1);

        //expandableLinearLayout2 = findViewById(R.id.expandableLayout2);
        scrollButton2 = findViewById(R.id.scrollButton2);
        cardView2 = findViewById(R.id.cardViewer2);

        expandableLinearLayout3 = findViewById(R.id.expandableLayout3);
        scrollButton3 = findViewById(R.id.scrollButton3);
        cardView3 = findViewById(R.id.cardViewer3);
        dateSelectionText = findViewById(R.id.startDateTextSelector);


        expandableLinearLayout4 = findViewById(R.id.expandableLayout4);
        scrollButton4 = findViewById(R.id.scrollButton4);
        cardView4 = findViewById(R.id.cardViewer4);

        expandableLinearLayout5 = findViewById(R.id.expandableLayout5);
        scrollButton5 = findViewById(R.id.scrollButton5);
        cardView5 = findViewById(R.id.cardViewer5);
    }

    private ObjectAnimator changeRotation(RelativeLayout scrollButton, float from, float to)
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(scrollButton, "Rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }
}
