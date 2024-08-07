package com.project.petmanagement.activity.notification.careActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.project.petmanagement.R;
import com.project.petmanagement.models.entity.CareActivity;
import com.project.petmanagement.models.entity.Pet;
import com.project.petmanagement.models.enums.FrequencyEnum;
import com.project.petmanagement.payloads.requests.CareActivityInfoRequest;
import com.project.petmanagement.payloads.requests.CareActivityNotificationRequest;
import com.project.petmanagement.payloads.requests.RecurringScheduleRequest;
import com.project.petmanagement.payloads.responses.CareActivityNotificationResponse;
import com.project.petmanagement.payloads.responses.ListCareActivityResponse;
import com.project.petmanagement.services.APIService;
import com.project.petmanagement.utils.FormatDateUtils;

import java.text.ParseException;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCareActivityNotificationActivity extends AppCompatActivity {
    private Pet pet;
    private CircleImageView imagePet;
    private TextView namePet;
    private CareActivityNotificationRequest careActivityNotificationRequest;
    private RecurringScheduleRequest recurringScheduleRequest;
    private Button saveActivityScheduleBtn;
    private LinearLayout parentLayout1, parentLayout2, linearDayOfWeek;
    private CardView setActivityInfo, activityInfo, notifyInfo, setActivityNotification;
    private Map<Long, CareActivity> careActivityMap;
    private TextView careActivityNotifyTitle, careActivityNotifyNote, frequency, hourNotify, dayNotify, fromToEndDate;
    private ActivityResultLauncher<Intent> launcherPet = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if (o.getResultCode() == RESULT_OK) {
                Intent data = o.getData();
                if (data != null) {
                    pet = (Pet) data.getSerializableExtra("pet");
                    setInfoPet();
                }
            }
        }
    });
    private ActivityResultLauncher<Intent> launcherCareActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if (o.getResultCode() == RESULT_OK) {
                Intent data = o.getData();
                if (data != null) {
                    careActivityNotificationRequest = (CareActivityNotificationRequest) data.getSerializableExtra("careActivityNotificationRequest");
                    setButtonSave();
                }
            }
        }
    });
    private ActivityResultLauncher<Intent> launcherRecuringSchedule = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if (o.getResultCode() == RESULT_OK) {
                Intent data = o.getData();
                if (data != null) {
                    recurringScheduleRequest = (RecurringScheduleRequest) data.getSerializableExtra("recurringScheduleRequest");
                    setButtonSave();
                }
            }
        }
    });

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_care_activity_notification);
        setActivityInfo = findViewById(R.id.set_activity_info);
        Button btnSelectPet = findViewById(R.id.return_choose_pet);
        getCareActivity();
        ImageView btnEditActivityInfo = findViewById(R.id.btn_edit_info_activity);
        ImageView btnEditRecurringSchedule = findViewById(R.id.btn_edit_notify);
        saveActivityScheduleBtn = findViewById(R.id.save_activity_schedule_btn);
        imagePet = findViewById(R.id.image_pet);
        namePet = findViewById(R.id.name_pet);
        parentLayout1 = findViewById(R.id.parent_layout1);
        parentLayout2 = findViewById(R.id.parent_layout2);
        activityInfo = findViewById(R.id.activity_info);
        careActivityMap = new LinkedHashMap<>();
        careActivityNotifyTitle = findViewById(R.id.care_activity_notify_title);
        careActivityNotifyNote = findViewById(R.id.care_activity_notify_note);
        frequency = findViewById(R.id.frequency);
        hourNotify = findViewById(R.id.hour_notify);
        dayNotify = findViewById(R.id.day_of_week);
        fromToEndDate = findViewById(R.id.start_date_to_end_date);
        notifyInfo = findViewById(R.id.notify_info);
        setActivityNotification = findViewById(R.id.set_activity_notification);
        linearDayOfWeek = findViewById(R.id.linear_day_of_week);
        pet = (Pet) getIntent().getSerializableExtra("pet");
        if (pet != null) {
            setInfoPet();
        }
        btnEditActivityInfo.setOnClickListener(v -> {
            Intent intent = new Intent(AddCareActivityNotificationActivity.this, AddCareActivityInfoActivity.class);
            intent.putExtra("activityInfo", careActivityNotificationRequest);
            launcherCareActivity.launch(intent);
        });
        btnEditRecurringSchedule.setOnClickListener(v -> {
            Intent intent = new Intent(AddCareActivityNotificationActivity.this, AddRecurringScheduleActivity.class);
            intent.putExtra("recurringScheduleRequest", recurringScheduleRequest);
            launcherRecuringSchedule.launch(intent);
        });
        btnSelectPet.setOnClickListener(v -> {
            Intent intent = new Intent(AddCareActivityNotificationActivity.this, SelectPetActivity.class);
            intent.putExtra("action", "reselect");
            launcherPet.launch(intent);
        });
        setActivityInfo.setOnClickListener(v -> {
            Intent intent = new Intent(AddCareActivityNotificationActivity.this, AddCareActivityInfoActivity.class);
            launcherCareActivity.launch(intent);
        });

        setActivityNotification.setOnClickListener(v -> {
            Intent intent = new Intent(AddCareActivityNotificationActivity.this, AddRecurringScheduleActivity.class);
            launcherRecuringSchedule.launch(intent);
        });

        saveActivityScheduleBtn.setOnClickListener(v -> {
            careActivityNotificationRequest.setRecurringScheduleRequest(recurringScheduleRequest);
            careActivityNotificationRequest.setPetId(pet.getId());
            APIService.apiService.addCareActivityNotification(careActivityNotificationRequest).enqueue(new Callback<CareActivityNotificationResponse>() {
                @Override
                public void onResponse(Call<CareActivityNotificationResponse> call, Response<CareActivityNotificationResponse> response) {
                    if (response.isSuccessful()) {
                        Intent intent = new Intent(AddCareActivityNotificationActivity.this, CareActivityNotificationsActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(AddCareActivityNotificationActivity.this, "Thêm lịch thành công.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddCareActivityNotificationActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CareActivityNotificationResponse> call, Throwable t) {
                    Toast.makeText(AddCareActivityNotificationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
        setButtonSave();
        ImageView returnArrow = findViewById(R.id.return_arrow);
        returnArrow.setOnClickListener(v -> finish());
        setUpActivityCareActivityNotification();
        setUpRecurringSchedule();
    }

    private void setInfoPet() {
        if (pet != null) {
            namePet.setText(pet.getFullName());
            Glide.with(AddCareActivityNotificationActivity.this)
                    .load(pet.getImage())
                    .error(R.drawable.default_pet_no_image)
                    .into(imagePet);
        }
    }

    private void setUpActivityCareActivityNotification() {
        if (careActivityNotificationRequest != null && careActivityNotificationRequest.getCareActivityInfoRequestList() != null) {
            parentLayout1.removeAllViews();
            activityInfo.setVisibility(View.VISIBLE);
            setActivityInfo.setVisibility(View.GONE);
            int stt = 1;
            careActivityNotifyTitle.setText(careActivityNotificationRequest.getTitle());
            careActivityNotifyNote.setText(careActivityNotificationRequest.getNote());
            for (CareActivityInfoRequest careActivityInfoRequest : careActivityNotificationRequest.getCareActivityInfoRequestList()) {
                View childView = getLayoutInflater().inflate(R.layout.item_care_activity_info, null, false);
                TextView title = childView.findViewById(R.id.title);
                String strTile = "Hành động " + stt;
                stt += 1;
                title.setText(strTile);
                TextView careActivityType = childView.findViewById(R.id.activity_type);
                TextView note = childView.findViewById(R.id.note);
                careActivityType.setText(careActivityMap.get(careActivityInfoRequest.getCareActivityId()).getName());
                note.setText(careActivityInfoRequest.getNote());
                parentLayout1.addView(childView);
            }
        } else {
            activityInfo.setVisibility(View.GONE);
            setActivityInfo.setVisibility(View.VISIBLE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setUpRecurringSchedule() {
        if (recurringScheduleRequest != null) {
            parentLayout2.removeAllViews();
            notifyInfo.setVisibility(View.VISIBLE);
            setActivityNotification.setVisibility(View.GONE);
            if (recurringScheduleRequest.getFrequency() == FrequencyEnum.NO_REPEAT) {
                linearDayOfWeek.setVisibility(View.GONE);
                frequency.setText("Không lặp lại");
                try {
                    Date date = FormatDateUtils.StringToDate(recurringScheduleRequest.getDate());
                    String date1 = FormatDateUtils.DateToString(date);
                    fromToEndDate.setText(date1);
                    hourNotify.setText(recurringScheduleRequest.getTime());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            } else {
                String strFrequency;
                if (recurringScheduleRequest.getFrequency() == FrequencyEnum.DAILY) {
                    dayNotify.setVisibility(View.GONE);
                    strFrequency = "Lặp lại mỗi " + recurringScheduleRequest.getValue() + " ngày 1 lần";
                } else {
                    linearDayOfWeek.setVisibility(View.VISIBLE);
                    strFrequency = "Lặp lại mỗi " + recurringScheduleRequest.getValue() + " tuần 1 lần";
                    StringBuilder day = new StringBuilder();
                    for (DayOfWeek dayOfWeek : recurringScheduleRequest.getDaysOfWeek()) {
                        if (dayOfWeek == DayOfWeek.MONDAY) {
                            day.append("Thứ hai, ");
                        } else if (dayOfWeek == DayOfWeek.TUESDAY) {
                            day.append("Thứ ba, ");
                        } else if (dayOfWeek == DayOfWeek.WEDNESDAY) {
                            day.append("Thứ tư, ");
                        } else if (dayOfWeek == DayOfWeek.THURSDAY) {
                            day.append("Thứ năm, ");
                        } else if (dayOfWeek == DayOfWeek.FRIDAY) {
                            day.append("Thứ sáu, ");
                        } else if (dayOfWeek == DayOfWeek.SATURDAY) {
                            day.append("Thứ bảy, ");
                        } else {
                            day.append("Chủ nhật, ");
                        }
                    }
                    dayNotify.setText(day.toString().substring(0, day.toString().length() - 2));
                }
                frequency.setText(strFrequency);
                hourNotify.setText(recurringScheduleRequest.getTime());
                try {
                    Date date = FormatDateUtils.StringToDate(recurringScheduleRequest.getFromDate());
                    String date1 = FormatDateUtils.DateToString(date);
                    Date date2 = FormatDateUtils.StringToDate(recurringScheduleRequest.getToDate());
                    String date3 = FormatDateUtils.DateToString(date2);
                    String endDate = "Từ ngày " + date1 + " đến " + date3;
                    fromToEndDate.setText(endDate);
                    hourNotify.setText(recurringScheduleRequest.getTime());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            notifyInfo.setVisibility(View.GONE);
            setActivityNotification.setVisibility(View.VISIBLE);
        }
    }

    private void setButtonSave() {
        if (careActivityNotificationRequest != null && recurringScheduleRequest != null) {
            saveActivityScheduleBtn.setEnabled(true);
            saveActivityScheduleBtn.setAlpha(1);
        } else {
            saveActivityScheduleBtn.setEnabled(false);
            saveActivityScheduleBtn.setAlpha(0.4f);
        }
    }

    private void getCareActivity() {
        APIService.apiService.getAllCareActivities().enqueue(new Callback<ListCareActivityResponse>() {
            @Override
            public void onResponse(Call<ListCareActivityResponse> call, Response<ListCareActivityResponse> response) {
                if (response.isSuccessful()) {
                    ListCareActivityResponse careActivityResponse = response.body();
                    if (careActivityResponse != null && careActivityResponse.getData() != null) {
                        for (CareActivity careActivity : careActivityResponse.getData()) {
                            careActivityMap.put(careActivity.getId(), careActivity);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ListCareActivityResponse> call, Throwable t) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
        setUpActivityCareActivityNotification();
        setUpRecurringSchedule();
        setButtonSave();
    }
}