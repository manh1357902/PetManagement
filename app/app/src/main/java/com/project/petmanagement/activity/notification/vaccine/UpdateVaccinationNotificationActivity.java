package com.project.petmanagement.activity.notification.vaccine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.project.petmanagement.R;
import com.project.petmanagement.models.entity.OneTimeSchedule;
import com.project.petmanagement.models.entity.Pet;
import com.project.petmanagement.models.entity.VaccinationNotification;
import com.project.petmanagement.models.entity.Vaccine;
import com.project.petmanagement.payloads.requests.OneTimeScheduleRequest;
import com.project.petmanagement.payloads.requests.VaccinationNotificationRequest;
import com.project.petmanagement.payloads.responses.ListVaccineResponse;
import com.project.petmanagement.payloads.responses.VaccineNotificationResponse;
import com.project.petmanagement.services.APIService;
import com.project.petmanagement.utils.FormatDateUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateVaccinationNotificationActivity extends AppCompatActivity {
    private Pet pet;
    private CircleImageView imagePet;
    private TextView namePet;
    private ArrayAdapter<String> vaccineAdapter;
    private Map<String, Vaccine> vaccineMap;
    private AutoCompleteTextView vaccineView;
    private TextView quantityInject;
    private List<OneTimeScheduleRequest> oneTimeScheduleRequestList;
    private LinearLayout parentLayout;
    private TextInputEditText title, note;
    private VaccinationNotification vaccinationNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_vaccination_notification);
        ImageView returnArrow = findViewById(R.id.return_arrow);
        vaccineMap = new LinkedHashMap<>();
        vaccinationNotification = (VaccinationNotification) getIntent().getSerializableExtra("vaccineNotification");
        vaccineView = findViewById(R.id.vaccine_type);
        imagePet = findViewById(R.id.image_pet);
        namePet = findViewById(R.id.name_pet);
        quantityInject = findViewById(R.id.quantity_inject);
        parentLayout = findViewById(R.id.parent_layout);
        title = findViewById(R.id.title_vaccine);
        note = findViewById(R.id.note);
        ImageView btnEdit = findViewById(R.id.btn_edit);
        oneTimeScheduleRequestList = new ArrayList<>();
        if (vaccinationNotification != null) {
            title.setText(vaccinationNotification.getTitle());
            note.setText(vaccinationNotification.getNote());
            vaccineView.setText(vaccinationNotification.getVaccine().getName());
            quantityInject.setText(String.valueOf(vaccinationNotification.getVaccine().getVaccineDose()));
            pet = vaccinationNotification.getPet();
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
        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(UpdateVaccinationNotificationActivity.this, UpdateOneTimeSchedulesActivity.class);
            intent.putExtra("vaccineNotificationId", vaccinationNotification.getId());
            intent.putExtra("listOneTime", (Serializable) oneTimeScheduleRequestList);
            startActivity(intent);
        });
        setInfoPet();
        vaccineView.setOnItemClickListener((parent, view, position, id) -> {
            vaccineView.setError(null);
            String vaccineName = vaccineAdapter.getItem(position);
            Vaccine vaccine = vaccineMap.get(vaccineName);
            if (vaccine != null) {
                quantityInject.setText(String.valueOf(vaccine.getVaccineDose()));
            }
        });
        Button btnSave = findViewById(R.id.btn_update);
        returnArrow.setOnClickListener(v -> finish());
        setUpOneTimeSchedule();
        btnSave.setOnClickListener(v -> {
            if (validation()) {
                Vaccine vaccine = vaccineMap.get(vaccineView.getText().toString());
                VaccinationNotificationRequest vaccinationNotificationRequest = new VaccinationNotificationRequest(pet.getId(), title.getText().toString(), vaccine.getId(), note.getText().toString(), oneTimeScheduleRequestList);
                APIService.apiService.updateVaccinationNotification(vaccinationNotification.getId(), vaccinationNotificationRequest).enqueue(new Callback<VaccineNotificationResponse>() {
                    @Override
                    public void onResponse(Call<VaccineNotificationResponse> call, Response<VaccineNotificationResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(UpdateVaccinationNotificationActivity.this, "Cập nhập lịch thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<VaccineNotificationResponse> call, Throwable t) {

                    }
                });

            }
        });

    }

    private void setInfoPet() {
        if (pet != null) {
            namePet.setText(pet.getFullName());
            Glide.with(UpdateVaccinationNotificationActivity.this)
                    .load(pet.getImage())
                    .error(R.drawable.default_pet_no_image)
                    .into(imagePet);
            getVaccine();
        }
    }

    private boolean validation() {
        if (oneTimeScheduleRequestList == null || oneTimeScheduleRequestList.isEmpty()) {
            return false;
        }
        if (title.length() == 0) {
            title.setError("Hành động không được để trống");
            return false;
        }
        if (vaccineView.length() == 0) {
            vaccineView.setError("Loại vaccine không được để trống");
            return false;
        }
        return true;
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

    private void getVaccine() {
        APIService.apiService.getVaccineByPet(pet.getId()).enqueue(new Callback<ListVaccineResponse>() {
            @Override
            public void onResponse(Call<ListVaccineResponse> call, Response<ListVaccineResponse> response) {
                if (response.isSuccessful()) {
                    ListVaccineResponse vaccineResponse = response.body();
                    if (vaccineResponse != null && vaccineResponse.getData() != null) {
                        for (Vaccine vaccine : vaccineResponse.getData()) {
                            vaccineMap.put(vaccine.getName(), vaccine);
                        }
                        vaccineAdapter = new ArrayAdapter<>(UpdateVaccinationNotificationActivity.this, R.layout.item_dropdown_list, new ArrayList<>(vaccineMap.keySet()));
                        vaccineView.setAdapter(vaccineAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ListVaccineResponse> call, Throwable t) {
                Toast.makeText(UpdateVaccinationNotificationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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