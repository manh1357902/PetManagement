package com.project.petmanagement.activity.notification.vaccine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.petmanagement.R;
import com.project.petmanagement.activity.MainActivity;
import com.project.petmanagement.adapters.ListScheduleVaccineAdapter;
import com.project.petmanagement.models.entity.VaccinationNotification;
import com.project.petmanagement.payloads.responses.ListVaccineNotification;
import com.project.petmanagement.services.APIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VaccinationNotificationsActivity extends AppCompatActivity {
    private RecyclerView scheduleRecyclerView;
    private ListScheduleVaccineAdapter listScheduleVaccineAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination_notifications);
        ImageView returnArrow = findViewById(R.id.return_arrow);
        scheduleRecyclerView = findViewById(R.id.list_schedule_recyclerview);
        returnArrow.setOnClickListener(v -> {
            Intent intent = new Intent(VaccinationNotificationsActivity.this, MainActivity.class);
            intent.putExtra("fragmentIndex", String.valueOf(0));
            startActivity(intent);
            finish();
        });
        Button addInjectionScheduleBtn = findViewById(R.id.add_injection_schedule_btn);
        addInjectionScheduleBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SelectPetActivity.class);
            startActivity(intent);
        });
        getListVaccineNotification();
        scheduleRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy <= 0) {
                    addInjectionScheduleBtn.setVisibility(View.VISIBLE);
                } else {
                    addInjectionScheduleBtn.setVisibility(View.GONE);
                }
            }
        });
    }

    private void getListVaccineNotification() {
        APIService.apiService.getVaccineNotificationByUser().enqueue(new Callback<ListVaccineNotification>() {
            @Override
            public void onResponse(Call<ListVaccineNotification> call, Response<ListVaccineNotification> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getData() != null) {
                        List<VaccinationNotification> vaccineNotificationList = response.body().getData();
                        listScheduleVaccineAdapter = new ListScheduleVaccineAdapter(vaccineNotificationList, VaccinationNotificationsActivity.this);
                        scheduleRecyclerView.setAdapter(listScheduleVaccineAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(VaccinationNotificationsActivity.this, LinearLayoutManager.VERTICAL, false);
                        scheduleRecyclerView.setLayoutManager(layoutManager);
                    }
                }
            }

            @Override
            public void onFailure(Call<ListVaccineNotification> call, Throwable t) {
                Toast.makeText(VaccinationNotificationsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getListVaccineNotification();
    }
}