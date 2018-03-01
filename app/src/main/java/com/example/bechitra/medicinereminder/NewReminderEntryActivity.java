package com.example.bechitra.medicinereminder;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bechitra.medicinereminder.adapter.NewReminderSpinnerAdapter;
import com.example.bechitra.medicinereminder.adapter.ReminderTimesRecyclerAdapter;
import com.example.bechitra.medicinereminder.dialog.BirthControllDialog;
import com.example.bechitra.medicinereminder.dialog.DaySelectionDialog;
import com.example.bechitra.medicinereminder.dialog.MedicationDoseSeletionDialog;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewReminderEntryActivity extends AppCompatActivity
{
    private Map<String, Integer> frequencyMap = new HashMap<>();
    private Map<String, Integer> intervalMap = new HashMap<>();
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
    private TextView dateSelectionText, medicationDoseSetText;
    private Spinner reminderTimesSpinner;
    private RadioButton specificDaysOfWeek, everyDay, daysInterval, birthControlCycle, beforeFood, withFood, afterFood, noFoodInstruction;
    private CheckBox continiousDuration, numberOfDaysDuration;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reminder_entry);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findViewById();
        initializeToSpinnerList();
        SetMapFrequencyAndInterval();

        SetViewExpandable(expandableLayout1, cardView1, scrollButton1, 1);
        SetViewExpandable(expandableLayout2, cardView2, scrollButton2, 2);
        SetViewExpandable(expandableLayout3, cardView3, scrollButton3, 3);
        SetViewExpandable(expandableLayout4, cardView4, scrollButton4, 4);
        SetViewExpandable(expandableLayout5, cardView5, scrollButton5, 5);

        finalExpandedItems.addAll(initialItemsPart1);
        finalExpandedItems.addAll(initialItemsPart2);
        final NewReminderSpinnerAdapter spinnerAdapter = new NewReminderSpinnerAdapter(this, finalExpandedItems);
        reminderTimesSpinner.setAdapter(spinnerAdapter);
        reminderTimesSpinner.setSelection(1);

        final Handler handler = new Handler();
        recyclerViewWithinRememberTimes.setHasFixedSize(true);
        recyclerViewWithinRememberTimes.setLayoutManager(new LinearLayoutManager(this));
        final ReminderTimesRecyclerAdapter adapter = new ReminderTimesRecyclerAdapter(timeAndQuantities, this);
        recyclerViewWithinRememberTimes.setAdapter(adapter);

        reminderTimesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id)
            {
                if (finalExpandedItems.get(position).equals("FREQUENCY") || finalExpandedItems.get(position).equals("INTERVALS")) {
                    if(finalExpandedItems.get(position).equals("INTERVALS")) {
                        reminderTimesSpinner.setSelection(position+1);
                    } else
                        reminderTimesSpinner.setSelection(1);

                }

                if (!finalExpandedItems.get(position).equals("FREQUENCY") &&
                        !finalExpandedItems.get(position).equals("INTERVALS") && !finalExpandedItems.get(position).equals("...")) {

                    if(finalExpandedItems.get(position).contains("Every"))
                        randomListGenerator(intervalMap.get(finalExpandedItems.get(position)));
                    else
                        randomListGenerator(frequencyMap.get(finalExpandedItems.get(position)));

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
                    reminderTimesSpinner.setSelection(1);

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

        onClickListnerForDaysItem();
        onClickListnerForFoodInstruction();
        onCheckedListnerForDuration();

        birthControlCycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BirthControllDialog dialog = new BirthControllDialog();
                dialog.show(getFragmentManager(), "H");
            }
        });

        medicationDoseSetText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MedicationDoseSeletionDialog dialog = new MedicationDoseSeletionDialog();
                dialog.show(getFragmentManager(), "TAG");
            }
        });

    }

    private void SetViewExpandable(final ExpandableLayout expandableLayout, CardView onclickView, final RelativeLayout scrollButton, final int serial)
    {
        onclickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayout.toggle();

                Animation animation = new RotateAnimation(ROTATION_ANGLE[serial-1], getRotationAngle(serial-1), scrollButton.getPivotX(), scrollButton.getPivotY());
                animation.setDuration(500);
                animation.setRepeatCount(0);
                animation.setRepeatMode(Animation.REVERSE);
                animation.setFillAfter(true);
                scrollButton.setAnimation(animation);
            }
        });
    }


    /* This function set OnclickListner for Days items on "Schedule" sub View;
     * Specifically set OnlcickListner for EveryDay, specific days of week, Days Interval, Birth control Cycle radioButton */
    private void onClickListnerForDaysItem() {
        specificDaysOfWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaySelectionDialog daySelectionDialog = new DaySelectionDialog();
                daySelectionDialog.show(getFragmentManager(), "Select Days");
                onItemChecked((RadioButton)v, true);
            }
        });

        everyDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemChecked((RadioButton) v, true);
            }
        });

        daysInterval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemChecked((RadioButton)v, true);
            }
        });

        birthControlCycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemChecked((RadioButton)v, true);
            }
        });
    }

    private void onClickListnerForFoodInstruction() {
        beforeFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFoodInstructionItemChecked((RadioButton)v, true);
            }
        });

        afterFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFoodInstructionItemChecked((RadioButton)v, true);
            }
        });

        withFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFoodInstructionItemChecked((RadioButton)v, true);
            }
        });

        noFoodInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFoodInstructionItemChecked((RadioButton)v, true);
            }
        });
    }

    private void randomListGenerator(int index)
    {
        String[] timeSet = getRandomTime(index);
        timeAndQuantities.clear();
        for (int i = 0; i < index; i++)
            timeAndQuantities.add(new TimeAndQuantity(timeSet[i], "Take " + (i + 1)));
    }

    private String[] getRandomTime(int times) {
        int [] time = new int[times];
        String[] timeSet = new String[times];
        int interval = 24/times;
        int INIT_TIME = 8;
        for(int i = 0; i < times; i++) {
            time[i] = INIT_TIME;
            INIT_TIME += interval;
        }

        for(int i = 0 ; i < time.length; i++) {
            if(time[i] >= 12 && time[i] < 24) {
                if(time[i] == 12)
                    timeSet[i] = Integer.toString(time[i])+".00PM";
                else {
                    int temp = (time[i] - 12);
                    timeSet[i] = Integer.toString(temp) + ".00PM";
                }
            } else {
                if(time[i] == 24) {
                    timeSet[i] = "12.00PM";
                } else if(time[i] > 24) {
                    int temp = (time[i] -24);
                    timeSet[i] = Integer.toString(temp)+".00AM";
                } else
                    timeSet[i] = Integer.toString(time[i])+".00AM";
            }

        }
        return timeSet;
    }

    private void SetMapFrequencyAndInterval() {
        frequencyMap.put("Once a day", 1);
        frequencyMap.put("Twice a day", 2);
        frequencyMap.put("3 times a day", 3);
        frequencyMap.put("4 times a day", 4);
        frequencyMap.put("5 times a day", 5);
        frequencyMap.put("6 times a day", 6);
        frequencyMap.put("7 times a day", 7);
        frequencyMap.put("8 times a day", 8);
        frequencyMap.put("9 times a day", 9);
        frequencyMap.put("10 times a day", 10);
        frequencyMap.put("11 times a day", 11);
        frequencyMap.put("12 times a day", 12);

        intervalMap.put("Every 12 hours", 2);
        intervalMap.put("Every 8 hours", 3);
        intervalMap.put("Every 6 hours", 4);
        intervalMap.put("Every 4 hours", 6);
        intervalMap.put("Every 3 hours", 8);
        intervalMap.put("Every 2 hours", 12);
        intervalMap.put("Every hour", 24);
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

    /* Binding all the value together for Spinner within Reminder Times subView */
    private void BindListOfAllItemsTogether() {
        finalExpandedItems.add("FREQUENCY");
        finalExpandedItems.addAll(frequencyExpandedItems);
        finalExpandedItems.add("INTERVALS");
        finalExpandedItems.addAll(intervalExpandedItems);
    }

    private void onCheckedListnerForDuration() {
        continiousDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continiousDuration.setChecked(true);
                numberOfDaysDuration.setChecked(false);
            }
        });

        numberOfDaysDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfDaysDuration.setChecked(true);
                continiousDuration.setChecked(false);
            }
        });
    }


    /* Initialize all the view with its ID */
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

        specificDaysOfWeek = findViewById(R.id.specificDaysOfWeekRadioButton);
        everyDay = findViewById(R.id.everyDayRadioButton);
        daysInterval = findViewById(R.id.daysIntervalRadioButton);
        birthControlCycle = findViewById(R.id.birthControlRadioButton);

        beforeFood = findViewById(R.id.beforeFoodRadioButton);
        afterFood = findViewById(R.id.afterFoodRadioButton);
        withFood = findViewById(R.id.withFoodRadioButton);
        noFoodInstruction = findViewById(R.id.noFoodInstructionRadioButton);

        continiousDuration = findViewById(R.id.continuousChecked);
        numberOfDaysDuration = findViewById(R.id.noOfDaysChecked);

        medicationDoseSetText = findViewById(R.id.medicationDoseSetText);
    }

    private float getRotationAngle(int position) {
        if(ROTATION_ANGLE[position] == 0) {
            ROTATION_ANGLE[position] = 180;
            return ROTATION_ANGLE[position];
        }

        ROTATION_ANGLE[position] = 0;
        return ROTATION_ANGLE[position];
    }

    private void onFoodInstructionItemChecked(RadioButton radioButton, boolean isChecked) {
        if(radioButton.getId() == R.id.withFoodRadioButton) {
            afterFood.setChecked(false);
            beforeFood.setChecked(false);
            noFoodInstruction.setChecked(false);
            withFood.setChecked(true);
        } else if(radioButton.getId() == R.id.afterFoodRadioButton) {
            withFood.setChecked(false);
            beforeFood.setChecked(false);
            noFoodInstruction.setChecked(false);
            afterFood.setChecked(true);
        } else if(radioButton.getId() == R.id.beforeFoodRadioButton) {
            withFood.setChecked(false);
            afterFood.setChecked(false);
            noFoodInstruction.setChecked(false);
            beforeFood.setChecked(true);
        } else if(radioButton.getId() == R.id.noFoodInstructionRadioButton) {
            withFood.setChecked(false);
            afterFood.setChecked(false);
            beforeFood.setChecked(false);
            noFoodInstruction.setChecked(true);
        }
    }

    private void onItemChecked(RadioButton buttonView, boolean isChecked) {
        if(buttonView.getId() == R.id.everyDayRadioButton) {
            specificDaysOfWeek.setChecked(false);
            daysInterval.setChecked(false);
            birthControlCycle.setChecked(false);
            everyDay.setChecked(true);
        } else if(buttonView.getId() == R.id.specificDaysOfWeekRadioButton) {
            everyDay.setChecked(false);
            daysInterval.setChecked(false);
            birthControlCycle.setChecked(false);
            specificDaysOfWeek.setChecked(true);
        } else if(buttonView.getId() == R.id.daysIntervalRadioButton) {
            everyDay.setChecked(false);
            specificDaysOfWeek.setChecked(false);
            birthControlCycle.setChecked(false);
            daysInterval.setChecked(true);
        } else if(buttonView.getId() == R.id.birthControlRadioButton) {
            everyDay.setChecked(false);
            specificDaysOfWeek.setChecked(false);
            daysInterval.setChecked(false);
            birthControlCycle.setChecked(true);
        }
    }
}
