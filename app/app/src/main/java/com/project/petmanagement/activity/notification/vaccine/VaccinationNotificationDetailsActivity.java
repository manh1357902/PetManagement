package com.project.petmanagement.activity.notification.vaccine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.project.petmanagement.R;
import com.project.petmanagement.models.entity.OneTimeSchedule;
import com.project.petmanagement.models.entity.VaccinationNotification;
import com.project.petmanagement.payloads.requests.OneTimeScheduleRequest;
import com.project.petmanagement.payloads.responses.VaccineNotificationResponse;
import com.project.petmanagement.services.APIService;
import com.project.petmanagement.utils.FormatDateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VaccinationNotificationDetailsActivity extends AppCompatActivity {
    private CircleImageView imagePet;
    private TextView namePet;
    private AutoCompleteTextView vaccineView;
    private TextView quantityInject;
    private List<OneTimeScheduleRequest> oneTimeScheduleRequestList;
    private LinearLayout parentLayout;
    private VaccinationNotification vaccinationNotification;
    private TextInputEditText title, note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination_notification_details);
        ImageView returnArrow = findViewById(R.id.return_arrow);
        vaccinationNotification = (VaccinationNotification) getIntent().getSerializableExtra("vaccineNotification");
        vaccineView = findViewById(R.id.vaccine_type);
        imagePet = findViewById(R.id.image_pet);
        namePet = findViewById(R.id.name_pet);
        quantityInject = findViewById(R.id.quantity_inject);
        parentLayout = findViewById(R.id.parent_layout);
        title = findViewById(R.id.title_vaccine);
        note = findViewById(R.id.note);
        Button btnDelete = findViewById(R.id.btn_delete);
        Button btnUpdate = findViewById(R.id.btn_update);
        setUpInfoVaccineNotification();
        btnUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(VaccinationNotificationDetailsActivity.this, UpdateVaccinationNotificationActivity.class);
            intent.putExtra("vaccineNotification", vaccinationNotification);
            startActivity(intent);
        });
        btnDelete.setOnClickListener(v -> {
            AlertDialog.Builder arlertDialog = new AlertDialog.Builder(VaccinationNotificationDetailsActivity.this);
            arlertDialog.setTitle("Thông báo")
                    .setMessage("Bạn chắc chán muốn xóa lịch này")
                    .setPositiveButton("Có", (dialog, which) -> {
                        APIService.apiService.deleteVaccineNotification(vaccinationNotification.getId()).enqueue(new Callback<com.project.petmanagement.payloads.responses.Response>() {
                            @Override
                            public void onResponse(Call<com.project.petmanagement.payloads.responses.Response> call, Response<com.project.petmanagement.payloads.responses.Response> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(VaccinationNotificationDetailsActivity.this, "Xóa thông báo thành công.", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<com.project.petmanagement.payloads.responses.Response> call, Throwable t) {
                                Toast.makeText(VaccinationNotificationDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    })
                    .setNegativeButton("Không", (dialog, which) -> dialog.cancel())
                    .show();
        });
        setInfoPet();
        returnArrow.setOnClickListener(v -> finish());
        setUpOneTimeSchedule();

    }

    private void setUpInfoVaccineNotification() {
        if (vaccinationNotification != null) {
            title.setText(vaccinationNotification.getTitle());
            note.setText(vaccinationNotification.getNote());
            vaccineView.setText(vaccinationNotification.getVaccine().getName());
            quantityInject.setText(String.valueOf(vaccinationNotification.getVaccine().getVaccineDose()));
            oneTimeScheduleRequestList = new ArrayList<>();
            for (OneTimeSchedule oneTimeSchedule : vaccinationNotification.getSchedules()) {
                OneTimeScheduleRequest oneTimeScheduleRequest = new OneTimeScheduleRequest();
                oneTimeScheduleRequest.setId(oneTimeSchedule.getId());
                oneTimeScheduleRequest.setTime(oneTimeSchedule.getTime());
                oneTimeScheduleRequest.setStatus(oneTimeSchedule.getVaccinationStatus());
                String date1 = FormatDateUtils.DateToString1(oneTimeSchedule.getDate());
                oneTimeScheduleRequest.setDate(date1);
                oneTimeScheduleRequestList.add(oneTimeScheduleRequest);
            }
        }
    }

    private void setInfoPet() {
        if (vaccinationNotification.getPet() != null) {
            namePet.setText(vaccinationNotification.getPet().getFullName());
            Glide.with(VaccinationNotificationDetailsActivity.this)
                    .load(vaccinationNotification.getPet().getImage())
                    .error(R.drawable.default_pet_no_image)
                    .into(imagePet);
        }
    }

    private void setUpOneTimeSchedule() {
        if (oneTimeScheduleRequestList != null && !oneTimeScheduleRequestList.isEmpty()) {
            parentLayout.removeAllViews();
            int stt = 1;
            for (OneTimeScheduleRequest oneTimeScheduleRequest : oneTimeScheduleRequestList) {
                try {
                    Date date = FormatDateUtils.StringToDate(oneTimeScheduleRequest.getDate());
                    String strDate = FormatDateUtils.DateToString(date);
                    View childView = getLayoutInflater().inflate(R.layout.item_one_time_schedule, null, false);
                    TextView title = childView.findViewById(R.id.title);
                    String strTile = "Lịch tiêm vaccine " + stt;
                    stt += 1;
                    title.setText(strTile);
                    TextView dateInject = childView.findViewById(R.id.date_inject);
                    TextView hourInject = childView.findViewById(R.id.hour);
                    dateInject.setText(strDate);
                    hourInject.setText(oneTimeScheduleRequest.getTime());
                    parentLayout.addView(childView);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void getVaccineNotificationById() {
        APIService.apiService.getVaccineNotification(vaccinationNotification.getId()).enqueue(new Callback<VaccineNotificationResponse>() {
            @Override
            public void onResponse(Call<VaccineNotificationResponse> call, Response<VaccineNotificationResponse> response) {
                if (response.isSuccessful()) {
                    VaccineNotificationResponse vaccineNotificationResponse = response.body();
                    if (vaccineNotificationResponse != null && vaccineNotificationResponse.getData() != null) {
                        vaccinationNotification = vaccineNotificationResponse.getData();
                        oneTimeScheduleRequestList.clear();
                        for (OneTimeSchedule oneTimeSchedule : vaccinationNotification.getSchedules()) {
                            OneTimeScheduleRequest oneTimeScheduleRequest = new OneTimeScheduleRequest();
                            oneTimeScheduleRequest.setId(oneTimeSchedule.getId());
                            oneTimeScheduleRequest.setTime(oneTimeSchedule.getTime());
                            oneTimeScheduleRequest.setStatus(oneTimeSchedule.getVaccinationStatus());
                            String date1 = FormatDateUtils.DateToString1(oneTimeSchedule.getDate());
                            oneTimeScheduleRequest.setDate(date1);
                            oneTimeScheduleRequestList.add(oneTimeScheduleRequest);
                        }
                        setUpInfoVaccineNotification();
                        setUpOneTimeSchedule();
                    }
                }
            }

            @Override
            public void onFailure(Call<VaccineNotificationResponse> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getVaccineNotificationById();
    }
}