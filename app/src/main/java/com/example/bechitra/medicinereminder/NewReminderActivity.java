package com.example.bechitra.medicinereminder;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.RelativeLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;

public class NewReminderActivity extends AppCompatActivity {
    ExpandableLinearLayout expandableLinearLayout1,expandableLinearLayout2,expandableLinearLayout3,expandableLinearLayout4;
    RelativeLayout scrollButton4, scrollButton1, scrollButton2, scrollButton3;
    CardView cardView1, cardView2, cardView3, cardView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reminder);
        findViewById();
        setViewExpandable(expandableLinearLayout1, cardView1, scrollButton1);
        setViewExpandable(expandableLinearLayout2, cardView2, scrollButton2);
        setViewExpandable(expandableLinearLayout3, cardView3, scrollButton3);
        setViewExpandable(expandableLinearLayout4, cardView4, scrollButton4);
    }

    private void setViewExpandable(final ExpandableLinearLayout expandableLinearLayout, CardView onclickView, final RelativeLayout scrollButton) {
        expandableLinearLayout.setInRecyclerView(true);
        expandableLinearLayout.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                changeRotation(scrollButton, 0f, 180f).start();
            }

            @Override
            public void onPreClose() {
                changeRotation(scrollButton, 180f, 0f).start();
            }
        });
        onclickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLinearLayout.toggle();
            }
        });
    }

    private void findViewById() {
        expandableLinearLayout1 = findViewById(R.id.expandableLayout1);
        scrollButton1 = findViewById(R.id.scrollButton1);
        cardView1 = findViewById(R.id.cardViewer1);

        expandableLinearLayout2 = findViewById(R.id.expandableLayout2);
        scrollButton2 = findViewById(R.id.scrollButton2);
        cardView2 = findViewById(R.id.cardViewer2);

        expandableLinearLayout3 = findViewById(R.id.expandableLayout3);
        scrollButton3 = findViewById(R.id.scrollButton3);
        cardView3 = findViewById(R.id.cardViewer3);

        expandableLinearLayout4 = findViewById(R.id.expandableLayout4);
        scrollButton4 = findViewById(R.id.scrollButton4);
        cardView4 = findViewById(R.id.cardViewer4);
    }

    private ObjectAnimator changeRotation(RelativeLayout scrollButton, float from, float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(scrollButton, "Rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }
}
