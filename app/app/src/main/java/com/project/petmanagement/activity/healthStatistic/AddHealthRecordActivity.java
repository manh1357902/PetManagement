package com.project.petmanagement.activity.healthStatistic;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.project.petmanagement.R;
import com.project.petmanagement.payloads.requests.HealRecordRequest;
import com.project.petmanagement.payloads.responses.HealRecordResponse;
import com.project.petmanagement.payloads.responses.HealthRecordErrorResponse;
import com.project.petmanagement.services.APIService;
import com.project.petmanagement.utils.DialogUtils;
import com.project.petmanagement.utils.FormatDateUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddHealthRecordActivity extends AppCompatActivity {
    private final String[] exerciseLevel = {"1", "2", "3", "4", "5"};
    private TextInputEditText checkUpdate;
    private DatePickerDialog datePickerDialog;
    private EditText editWeight;
    private TextInputEditText editSymptoms, editDiagnosis, editNote;
    private Long petId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_health_record);
        Spinner spinner = findViewById(R.id.exercise_level);
        Button btnAdd = findViewById(R.id.btn_add);
        editWeight = findViewById(R.id.weight);
        checkUpdate = findViewById(R.id.check_update);
        editSymptoms = findViewById(R.id.symptoms);
        editDiagnosis = findViewById(R.id.diagnosis);
        editNote = findViewById(R.id.note);
        ArrayAdapter<String> exerciseAdapter = new ArrayAdapter<>(this, R.layout.item_dropdown_list, Arrays.asList(exerciseLevel));
        spinner.setAdapter(exerciseAdapter);
        ImageView btnBack = findViewById(R.id.btn_back);
        petId = getIntent().getLongExtra("petId", 0);
        btnBack.setOnClickListener(v -> finish());
        customCheckUpdate();
        btnAdd.setOnClickListener(v -> {
            if (validate()) {
                String strCheckupDate = Objects.requireNonNull(checkUpdate.getText()).toString().trim();
                Date checkUpdate1 = null;
                try {
                    checkUpdate1 = FormatDateUtils.StringToDate1(strCheckupDate);
                    strCheckupDate = FormatDateUtils.DateToString1(checkUpdate1);
                    HealRecordRequest healRecordRequest = new HealRecordRequest(strCheckupDate, Double.parseDouble(editWeight.getText().toString()), Integer.parseInt(spinner.getSelectedItem().toString()), editSymptoms.getText().toString(), editDiagnosis.getText().toString(), editNote.getText().toString(), petId);
                    APIService.apiService.addHealthRecord(healRecordRequest).enqueue(new Callback<HealRecordResponse>() {
                        @Override
                        public void onResponse(Call<HealRecordResponse> call, Response<HealRecordResponse> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(AddHealthRecordActivity.this, "Thêm báo cáo thành công", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Gson gson = new Gson();
                                HealthRecordErrorResponse healthRecordErrorResponse = null;
                                try {
                                    healthRecordErrorResponse = gson.fromJson(response.errorBody().string(), HealthRecordErrorResponse.class);
                                    String message = "";
                                    if (healthRecordErrorResponse.getMessage().getCheckUpDate() != null) {
                                        message += healthRecordErrorResponse.getMessage().getCheckUpDate() + "\n";
                                    }
                                    if (healthRecordErrorResponse.getMessage().getDiagnosis() != null) {
                                        message += healthRecordErrorResponse.getMessage().getDiagnosis() + "\n";
                                    }
                                    if (healthRecordErrorResponse.getMessage().getNote() != null) {
                                        message += healthRecordErrorResponse.getMessage().getNote() + "\n";
                                    }
                                    if (healthRecordErrorResponse.getMessage().getWeight() != null) {
                                        message += healthRecordErrorResponse.getMessage().getWeight() + "\n";
                                    }
                                    if (healthRecordErrorResponse.getMessage().getSymptoms() != null) {
                                        message += healthRecordErrorResponse.getMessage().getSymptoms() + "\n";
                                    }
                                    if (healthRecordErrorResponse.getMessage().getExerciseLevel() != null) {
                                        message += healthRecordErrorResponse.getMessage().getExerciseLevel() + "\n";
                                    }
                                    if (healthRecordErrorResponse.getMessage().getPetId() != null) {
                                        message += healthRecordErrorResponse.getMessage().getPetId() + "\n";
                                    }
                                    DialogUtils.setUpDialog(AddHealthRecordActivity.this, message);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<HealRecordResponse> call, Throwable t) {
                            Toast.makeText(AddHealthRecordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }

        });

    }

    private boolean validate() {
        if (checkUpdate.length() == 0) {
            checkUpdate.setError("Ngày kiểm tra không được để trống.");
            return false;
        }
        if (editWeight.length() == 0) {
            editWeight.setError("Cân nặng không được để trống");
            return false;
        }
        return true;
    }

    private void customCheckUpdate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        checkUpdate.setOnClickListener(v -> {
            datePickerDialog = new DatePickerDialog(AddHealthRecordActivity.this, (view, year1, month1, dayOfMonth) -> {
                String date = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                try {
                    Date date1 = sdf.parse(date);
                    String date2 = sdf.format(date1);
                    Date currentDate = new Date();
                    String date3 = FormatDateUtils.DateToString1(date1);
                    Date dateChoose = FormatDateUtils.StringToDate(date3);
                    Calendar cal1 = Calendar.getInstance();
                    cal1.setTime(currentDate);
                    cal1.set(Calendar.HOUR_OF_DAY, 0);
                    cal1.set(Calendar.MINUTE, 0);
                    cal1.set(Calendar.SECOND, 0);
                    cal1.set(Calendar.MILLISECOND, 0);
                    Calendar cal2 = Calendar.getInstance();
                    cal2.setTime(dateChoose);
                    cal2.set(Calendar.HOUR_OF_DAY, 0);
                    cal2.set(Calendar.MINUTE, 0);
                    cal2.set(Calendar.SECOND, 0);
                    cal2.set(Calendar.MILLISECOND, 0);
                    if (cal1.compareTo(cal2) < 0) {
                        DialogUtils.setUpDialog(AddHealthRecordActivity.this, "Ngày bạn chọn phải nhỏ hơn hoặc bằng ngày hiện tại");
                    } else {
                        checkUpdate.setText(date2);
                    }
                    checkUpdate.setError(null);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            }, year, month, day);
            datePickerDialog.show();
        });
    }
}