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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.project.petmanagement.R;
import com.project.petmanagement.models.entity.CareActivity;
import com.project.petmanagement.models.entity.CareActivityInfo;
import com.project.petmanagement.models.entity.CareActivityNotification;
import com.project.petmanagement.models.entity.Pet;
import com.project.petmanagement.models.entity.RecurringSchedule;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CareActivityNotificationDetailsActivity extends AppCompatActivity {
    private Pet pet;
    private CircleImageView imagePet;
    private TextView namePet;
    private CareActivityNotificationRequest careActivityNotificationRequest;
    private RecurringScheduleRequest recurringScheduleRequest;
    private LinearLayout parentLayout1, parentLayout2, linearDayOfWeek;
    private CardView setActivityInfo, activityInfo, notifyInfo, setActivityNotification;
    private Map<Long, CareActivity> careActivityMap;
    private TextView careActivityNotifyTitle, careActivityNotifyNote, frequency, hourNotify, dayNotify, fromToEndDate;
    private Map<Long, CareActivity> careActivityMap2;
    private CareActivityNotification careActivityNotification;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_activity_notification_details);
        setActivityInfo = findViewById(R.id.set_activity_info);
        ImageView btnUpdate = findViewById(R.id.btn_update);
        ImageView btnEditActivityInfo = findViewById(R.id.btn_edit_info_activity);
        ImageView btnEditRecurringSchedule = findViewById(R.id.btn_edit_notify);
        btnEditActivityInfo.setVisibility(View.GONE);
        btnEditRecurringSchedule.setVisibility(View.GONE);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEditActivityInfo.setVisibility(View.VISIBLE);
                btnEditRecurringSchedule.setVisibility(View.VISIBLE);
            }
        });
        imagePet = findViewById(R.id.image_pet);
        namePet = findViewById(R.id.name_pet);
        parentLayout1 = findViewById(R.id.parent_layout1);
        parentLayout2 = findViewById(R.id.parent_layout2);
        activityInfo = findViewById(R.id.activity_info);
        careActivityMap = new LinkedHashMap<>();
        getCareActivity();
        careActivityNotifyTitle = findViewById(R.id.care_activity_notify_title);
        careActivityNotifyNote = findViewById(R.id.care_activity_notify_note);
        frequency = findViewById(R.id.frequency);
        hourNotify = findViewById(R.id.hour_notify);
        dayNotify = findViewById(R.id.day_of_week);
        fromToEndDate = findViewById(R.id.start_date_to_end_date);
        notifyInfo = findViewById(R.id.notify_info);
        setActivityNotification = findViewById(R.id.set_activity_notification);
        linearDayOfWeek = findViewById(R.id.linear_day_of_week);
        Button btnDelete = findViewById(R.id.btn_delete);
        careActivityNotification = (CareActivityNotification) getIntent().getSerializableExtra("careActivityNotification");
        if (careActivityNotification != null) {
            pet = careActivityNotification.getPet();
            careActivityNotificationRequest = new CareActivityNotificationRequest();
            careActivityNotificationRequest.setPetId(careActivityNotification.getPet().getId());
            careActivityNotificationRequest.setNote(careActivityNotification.getNote());
            careActivityNotificationRequest.setTitle(careActivityNotification.getTitle());
            careActivityNotificationRequest.setNotificationStatus(careActivityNotification.getNotificationStatus());
            if (careActivityNotification.getSchedule() != null) {
                RecurringSchedule recurringSchedule = careActivityNotification.getSchedule();
                recurringScheduleRequest = new RecurringScheduleRequest();
                recurringScheduleRequest.setValue(recurringSchedule.getValue());
                recurringScheduleRequest.setTime(recurringSchedule.getTime());
                recurringScheduleRequest.setId(recurringSchedule.getId());
                recurringScheduleRequest.setFrequency(recurringSchedule.getFrequency());
                if (recurringSchedule.getFrequency() == FrequencyEnum.NO_REPEAT) {
                    recurringScheduleRequest.setDate(FormatDateUtils.DateToString1(recurringSchedule.getDate()));
                } else {
                    if (recurringSchedule.getFrequency() == FrequencyEnum.WEEKLY) {
                        recurringScheduleRequest.setDaysOfWeek(recurringSchedule.getDaysOfWeek());
                    }
                    recurringScheduleRequest.setFromDate(FormatDateUtils.DateToString1(recurringSchedule.getFromDate()));
                    recurringScheduleRequest.setToDate(FormatDateUtils.DateToString1(recurringSchedule.getToDate()));
                }
                recurringScheduleRequest.setName(recurringSchedule.getName());
            }
            List<CareActivityInfoRequest> careActivityInfoRequests = new ArrayList<>();
            careActivityMap2 = new LinkedHashMap<>();
            for (CareActivityInfo careActivityInfo : careActivityNotification.getCareActivityInfoList()) {
                CareActivityInfoRequest careActivityInfoRequest = new CareActivityInfoRequest();
                careActivityInfoRequest.setCareActivityId(careActivityInfo.getCareActivity().getId());
                careActivityInfoRequest.setId(careActivityInfo.getId());
                careActivityInfoRequest.setNote(careActivityInfo.getNote());
                careActivityMap2.put(careActivityInfo.getCareActivity().getId(), careActivityInfo.getCareActivity());
                careActivityInfoRequests.add(careActivityInfoRequest);
            }
            careActivityNotificationRequest.setCareActivityInfoRequestList(careActivityInfoRequests);

        }
        btnEditActivityInfo.setOnClickListener(v -> {
            Intent intent = new Intent(CareActivityNotificationDetailsActivity.this, UpdateCareActivityInfoActivity.class);
            intent.putExtra("activityInfo", careActivityNotificationRequest);
            intent.putExtra("careActivityNotificationId", careActivityNotification.getId());
            startActivity(intent);
        });
        btnEditRecurringSchedule.setOnClickListener(v -> {
            Intent intent = new Intent(CareActivityNotificationDetailsActivity.this, UpdateRecurringScheduleActivity.class);
            intent.putExtra("recurringScheduleRequest", recurringScheduleRequest);
            startActivity(intent);
        });
        btnDelete.setOnClickListener(v -> {
            AlertDialog.Builder arlertDialog = new AlertDialog.Builder(CareActivityNotificationDetailsActivity.this);
            arlertDialog.setTitle("Thông báo")
                    .setMessage("Bạn chắc chán muốn xóa lịch này")
                    .setPositiveButton("Có", (dialog, which) -> {
                        APIService.apiService.deleteCareActivityNotification(careActivityNotification.getId()).enqueue(new Callback<com.project.petmanagement.payloads.responses.Response>() {
                            @Override
                            public void onResponse(Call<com.project.petmanagement.payloads.responses.Response> call, Response<com.project.petmanagement.payloads.responses.Response> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(CareActivityNotificationDetailsActivity.this, "Xóa thông báo thành công.", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<com.project.petmanagement.payloads.responses.Response> call, Throwable t) {
                            }
                        });
                    })
                    .setNegativeButton("Không", (dialog, which) -> dialog.cancel())
                    .show();
        });
        ImageView returnArrow = findViewById(R.id.return_arrow);
        returnArrow.setOnClickListener(v -> finish());
        setUpRecurringSchedule();
        setUpActivityCareActivityNotification();
        setInfoPet();
    }

    private void setInfoPet() {
        if (pet != null) {
            namePet.setText(pet.getFullName());
            Glide.with(CareActivityNotificationDetailsActivity.this)
                    .load(pet.getImage())
                    .error(R.drawable.default_pet_no_image)
                    .into(imagePet);
        }
    }

    private void getCareActivityNotification() {
        APIService.apiService.getCareActivityNotificationById(careActivityNotification.getId()).enqueue(new Callback<CareActivityNotificationResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<CareActivityNotificationResponse> call, Response<CareActivityNotificationResponse> response) {
                if (response.isSuccessful()) {
                    CareActivityNotificationResponse careActivityNotificationResponse = response.body();
                    if (careActivityNotificationResponse != null) {
                        careActivityNotification = careActivityNotificationResponse.getData();
                        if (careActivityNotification != null) {
                            pet = careActivityNotification.getPet();
                            careActivityNotificationRequest = new CareActivityNotificationRequest();
                            careActivityNotificationRequest.setPetId(careActivityNotification.getPet().getId());
                            careActivityNotificationRequest.setNote(careActivityNotification.getNote());
                            careActivityNotificationRequest.setTitle(careActivityNotification.getTitle());
                            careActivityNotificationRequest.setNotificationStatus(careActivityNotification.getNotificationStatus());
                            if (careActivityNotification.getSchedule() != null) {
                                RecurringSchedule recurringSchedule = careActivityNotification.getSchedule();
                                recurringScheduleRequest = new RecurringScheduleRequest();
                                recurringScheduleRequest.setValue(recurringSchedule.getValue());
                                recurringScheduleRequest.setTime(recurringSchedule.getTime());
                                recurringScheduleRequest.setId(recurringSchedule.getId());
                                recurringScheduleRequest.setFrequency(recurringSchedule.getFrequency());
                                if (recurringSchedule.getFrequency() == FrequencyEnum.NO_REPEAT) {
                                    recurringScheduleRequest.setDate(FormatDateUtils.DateToString1(recurringSchedule.getDate()));
                                } else {
                                    if (recurringSchedule.getFrequency() == FrequencyEnum.WEEKLY) {
                                        recurringScheduleRequest.setDaysOfWeek(recurringSchedule.getDaysOfWeek());
                                    }
                                    recurringScheduleRequest.setFromDate(FormatDateUtils.DateToString1(recurringSchedule.getFromDate()));
                                    recurringScheduleRequest.setToDate(FormatDateUtils.DateToString1(recurringSchedule.getToDate()));
                                }
                                recurringScheduleRequest.setName(recurringSchedule.getName());

                            }
                            List<CareActivityInfoRequest> careActivityInfoRequests = new ArrayList<>();
                            careActivityMap2 = new LinkedHashMap<>();
                            for (CareActivityInfo careActivityInfo : careActivityNotification.getCareActivityInfoList()) {
                                CareActivityInfoRequest careActivityInfoRequest = new CareActivityInfoRequest();
                                careActivityInfoRequest.setCareActivityId(careActivityInfo.getCareActivity().getId());
                                careActivityInfoRequest.setId(careActivityInfo.getId());
                                careActivityInfoRequest.setNote(careActivityInfo.getNote());
                                careActivityMap2.put(careActivityInfo.getCareActivity().getId(), careActivityInfo.getCareActivity());
                                careActivityInfoRequests.add(careActivityInfoRequest);
                            }
                            careActivityNotificationRequest.setCareActivityInfoRequestList(careActivityInfoRequests);
                            setUpActivityCareActivityNotification();
                            setUpRecurringSchedule();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CareActivityNotificationResponse> call, Throwable t) {

            }
        });
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
                if (careActivityMap.isEmpty()) {
                    careActivityType.setText(careActivityMap2.get(careActivityInfoRequest.getCareActivityId()).getName());
                } else {
                    careActivityType.setText(careActivityMap.get(careActivityInfoRequest.getCareActivityId()).getName());

                }
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

    private void getCareActivity() {
        APIService.apiService.getAllCareActivities().enqueue(new Callback<ListCareActivityResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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
        getCareActivityNotification();
    }
}