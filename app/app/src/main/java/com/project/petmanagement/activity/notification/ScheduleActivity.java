package com.project.petmanagement.activity.notification;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.project.petmanagement.R;
import com.project.petmanagement.activity.notification.careActivity.CareActivityNotificationsActivity;
import com.project.petmanagement.activity.notification.vaccine.VaccinationNotificationsActivity;

public class ScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        ImageView returnArrow = findViewById(R.id.return_arrow);
        returnArrow.setOnClickListener(v -> finish());
        LinearLayout careActivity = findViewById(R.id.care_activity);
        LinearLayout injectActivity = findViewById(R.id.inject_activity);
        careActivity.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), CareActivityNotificationsActivity.class);
            startActivity(intent);
            finish();
        });
        injectActivity.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), VaccinationNotificationsActivity.class);
            startActivity(intent);
            finish();
        });
    }
}