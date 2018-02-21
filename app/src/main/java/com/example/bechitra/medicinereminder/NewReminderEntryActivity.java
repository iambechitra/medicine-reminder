package com.example.bechitra.medicinereminder;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bechitra.medicinereminder.adapter.NewReminderSpinnerAdapter;
import com.example.bechitra.medicinereminder.adapter.ReminderTimesRecyclerAdapter;
import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewReminderEntryActivity extends AppCompatActivity
{
    private List<String> initialItemsPart1 = new ArrayList<>();
    private List<String> initialItemsPart2 = new ArrayList<>();
    private List<String> frequencyExpandedItems = new ArrayList<>();
    private List<String> intervalExpandedItems = new ArrayList<>();
    private List<String> finalExpandedItems = new ArrayList<>();
    private List<TimeAndQuantity> timeAndQuantities = new ArrayList<>();

    private boolean isFrequencyExpanded = false, isIntervalExpanded = false; /*TO check wheather the frequency or interval was expanded before or not */
    private float []ROTATION_ANGLE = new float[5];

    private RecyclerView recyclerViewWithinRememberTimes;
    private ExpandableLayout expandableLayout1, expandableLayout2, expandableLayout3, expandableLayout4, expandableLayout5;
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

        setViewExpandable(expandableLayout1, cardView1, scrollButton1, 1);
        setViewExpandable(expandableLayout2, cardView2, scrollButton2, 2);
        setViewExpandable(expandableLayout3, cardView3, scrollButton3, 3);
        setViewExpandable(expandableLayout4, cardView4, scrollButton4, 4);
        setViewExpandable(expandableLayout5, cardView5, scrollButton5, 5);

        finalExpandedItems.addAll(initialItemsPart1);
        finalExpandedItems.addAll(initialItemsPart2);
        final NewReminderSpinnerAdapter spinnerAdapter = new NewReminderSpinnerAdapter(this, finalExpandedItems);
        reminderTimesSpinner.setAdapter(spinnerAdapter);
        reminderTimesSpinner.setSelection(1);

        final int[] i = {0};
        final Handler handler = new Handler();
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
                Toast.makeText(NewReminderEntryActivity.this, finalExpandedItems.get(position), Toast.LENGTH_SHORT).show();

                if (finalExpandedItems.get(position).equals("FREQUENCY") || finalExpandedItems.get(position).equals("INTERVALS")) {

                }

                if (!finalExpandedItems.get(position).equals("FREQUENCY") &&
                        !finalExpandedItems.get(position).equals("INTERVALS") && !finalExpandedItems.get(position).equals("...")) {
                    randomListGenerator(++i[0]);
                    adapter.updateDataSet(timeAndQuantities);
                    adapter.notifyDataSetChanged();
                    recyclerViewWithinRememberTimes.scrollToPosition(timeAndQuantities.size());
                }

                /*Re Open the Spinner after refreshing the list of items*/
                if(finalExpandedItems.get(position).equals("...")) {
                    finalExpandedItems.clear();
                    if(position == 4) {
                        if(!isIntervalExpanded) {
                            finalExpandedItems.add("FREQUENCY");
                            finalExpandedItems.addAll(frequencyExpandedItems);
                            finalExpandedItems.addAll(initialItemsPart2);
                            isFrequencyExpanded = true;
                        } else
                            BindListOfAllItemsTogether();
                    } else {

                        if(!isFrequencyExpanded) {
                            finalExpandedItems.addAll(initialItemsPart1);
                            finalExpandedItems.add("INTERVALS");
                            finalExpandedItems.addAll(intervalExpandedItems);
                            isIntervalExpanded = true;
                        } else
                            BindListOfAllItemsTogether();

                    }

                    spinnerAdapter.updateDataSet(finalExpandedItems);
                    spinnerAdapter.notifyDataSetChanged();
                    new Thread(new Runnable() {
                        public void run() {
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    reminderTimesSpinner.performClick();
                                }
                            }, 100);
                        }
                    }).start();
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

    private void setViewExpandable(final ExpandableLayout expandableLayout, CardView onclickView, final RelativeLayout scrollButton, final int serial)
    {
        onclickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayout.toggle();

                Animation animation = new RotateAnimation(ROTATION_ANGLE[serial-1], getRotationAngle(scrollButton.getRotation(), serial-1), scrollButton.getPivotX(), scrollButton.getPivotY());
                animation.setDuration(500);
                animation.setRepeatCount(0);
                animation.setRepeatMode(Animation.REVERSE);
                animation.setFillAfter(true);
                scrollButton.setAnimation(animation);
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
        initialItemsPart1.add("FREQUENCY");
        initialItemsPart1.add("Once a day");
        initialItemsPart1.add("Twice a day");
        initialItemsPart1.add("3 times a day");
        initialItemsPart1.add("...");
        initialItemsPart2.add("INTERVALS");
        initialItemsPart2.add("Every 12 hours");
        initialItemsPart2.add("Every 8 hours");
        initialItemsPart2.add("Every 6 hours");
        initialItemsPart2.add("...");


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
    }

    private void BindListOfAllItemsTogether() {
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


        expandableLayout1 = findViewById(R.id.expandableLayout1);
        scrollButton1 = findViewById(R.id.scrollButton1);
        cardView1 = findViewById(R.id.cardViewer1);

        expandableLayout2 = findViewById(R.id.expandableLayout2);
        scrollButton2 = findViewById(R.id.scrollButton2);
        cardView2 = findViewById(R.id.cardViewer2);

        expandableLayout3 = findViewById(R.id.expandableLayout3);
        scrollButton3 = findViewById(R.id.scrollButton3);
        cardView3 = findViewById(R.id.cardViewer3);
        dateSelectionText = findViewById(R.id.startDateTextSelector);


        expandableLayout4 = findViewById(R.id.expandableLayout4);
        scrollButton4 = findViewById(R.id.scrollButton4);
        cardView4 = findViewById(R.id.cardViewer4);

        expandableLayout5 = findViewById(R.id.expandableLayout5);
        scrollButton5 = findViewById(R.id.scrollButton5);
        cardView5 = findViewById(R.id.cardViewer5);
    }

    private float getRotationAngle(float current, int position) {
        if(ROTATION_ANGLE[position] == 0) {
            ROTATION_ANGLE[position] = 180;
            return ROTATION_ANGLE[position];
        }

        ROTATION_ANGLE[position] = 0;
        return ROTATION_ANGLE[position];
    }
}
