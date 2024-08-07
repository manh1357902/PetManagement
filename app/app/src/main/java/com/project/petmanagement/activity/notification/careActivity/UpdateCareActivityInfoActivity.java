package com.project.petmanagement.activity.notification.careActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.project.petmanagement.R;
import com.project.petmanagement.models.entity.CareActivity;
import com.project.petmanagement.payloads.requests.CareActivityInfoRequest;
import com.project.petmanagement.payloads.requests.CareActivityNotificationRequest;
import com.project.petmanagement.payloads.responses.CareActivityNotificationResponse;
import com.project.petmanagement.payloads.responses.ListCareActivityResponse;
import com.project.petmanagement.services.APIService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateCareActivityInfoActivity extends AppCompatActivity {
    private int stt = 1;
    private LinearLayout parentLayout;
    private Button saveBtn;
    private ArrayAdapter<String> careActivityAdapter;
    private Map<String, CareActivity> careActivityMap;
    private TextInputEditText title, totalNote;
    private Long careActivityNotificationId;
    private List<CareActivityInfoRequest> careActivityInfoRequests;
    private final List<CareActivityInfoRequest> deleteCareActivityInfoRequests = new ArrayList<>();
    private CareActivityNotificationRequest careActivityNotificationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_care_info);
        ImageView returnArrow = findViewById(R.id.return_arrow);
        CardView btnAddView = findViewById(R.id.add_activity_btn);
        parentLayout = findViewById(R.id.parent_layout);
        saveBtn = findViewById(R.id.save_btn);
        title = findViewById(R.id.title);
        totalNote = findViewById(R.id.total_note);
        careActivityMap = new LinkedHashMap<>();
        careActivityNotificationRequest = (CareActivityNotificationRequest) getIntent().getSerializableExtra("activityInfo");
        if (careActivityNotificationRequest != null) {
            careActivityInfoRequests = careActivityNotificationRequest.getCareActivityInfoRequestList();
            title.setText(careActivityNotificationRequest.getTitle());
            totalNote.setText(careActivityNotificationRequest.getNote());
            careActivityNotificationId = getIntent().getLongExtra("careActivityNotificationId", 0);
            if (careActivityNotificationRequest.getCareActivityInfoRequestList() != null) {
                for (CareActivityInfoRequest careActivityInfoRequest : careActivityNotificationRequest.getCareActivityInfoRequestList()) {
                    final View childView = getLayoutInflater().inflate(R.layout.item_add_care_activity_info, null, false);
                    TextView title = childView.findViewById(R.id.title);
                    String strTile = "Hoạt động " + stt;
                    stt += 1;
                    title.setText(strTile);
                    AutoCompleteTextView activityType = childView.findViewById(R.id.activity_type);
                    TextInputEditText noteEdit = childView.findViewById(R.id.note);
                    TextView activityInfoId = childView.findViewById(R.id.activity_info_id);
                    noteEdit.setText(careActivityInfoRequest.getNote());
                    activityInfoId.setText(String.valueOf(careActivityInfoRequest.getId()));
                    APIService.apiService.getAllCareActivities().enqueue(new Callback<ListCareActivityResponse>() {
                        @Override
                        public void onResponse(Call<ListCareActivityResponse> call, Response<ListCareActivityResponse> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null && response.body().getData() != null) {
                                    for (CareActivity careActivity : response.body().getData()) {
                                        careActivityMap.put(careActivity.getName(), careActivity);
                                        if (careActivity.getId().equals(careActivityInfoRequest.getCareActivityId())) {
                                            activityType.setText(careActivity.getName());
                                        }
                                        careActivityAdapter = new ArrayAdapter<>(UpdateCareActivityInfoActivity.this, R.layout.item_dropdown_list, new ArrayList<>(careActivityMap.keySet()));
                                        activityType.setAdapter(careActivityAdapter);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ListCareActivityResponse> call, Throwable t) {

                        }
                    });
                    ImageView deleteLayout = childView.findViewById(R.id.delete_layout);
                    deleteLayout.setOnClickListener(v -> {
                        int position = parentLayout.indexOfChild(childView);
                        parentLayout.removeView(childView);
                        for (int i = position; i < parentLayout.getChildCount(); i++) {
                            TextView title1 = parentLayout.getChildAt(i).findViewById(R.id.title);
                            String strTile1 = "Hoạt động " + (i + 1);
                            title1.setText(strTile1);
                        }
                        stt = parentLayout.getChildCount() + 1;
                        if (parentLayout.getChildCount() == 0) {
                            stt = 1;
                        }
                        setUpButton();
                    });
                    parentLayout.addView(childView);
                    setUpButton();
                }

            }
        }

        btnAddView.setOnClickListener(v -> {
            addView();
        });
        returnArrow.setOnClickListener(v -> finish());

        Button saveBtn = findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(v -> {
            saveCareActivityInfo();
        });
        setUpButton();
    }

    private boolean validate(AutoCompleteTextView editTypeActivity, TextInputEditText editNote) {
        if (title.length() == 0) {
            title.setError("Title không được để trống");
            return false;
        }
        if (editTypeActivity.length() == 0) {
            editTypeActivity.setError("Loại hành động không được để trống");
            return false;
        }
        return true;
    }

    private void saveCareActivityInfo() {
        List<CareActivityInfoRequest> careActivityInfoList = new ArrayList<>();
        List<Long> activityInfoId = new ArrayList<>();
        for (int i = 0; i < parentLayout.getChildCount(); i++) {
            View childView = parentLayout.getChildAt(i);
            AutoCompleteTextView activityType = childView.findViewById(R.id.activity_type);
            TextInputEditText note = childView.findViewById(R.id.note);
            TextView id = childView.findViewById(R.id.activity_info_id);
            if (validate(activityType, note)) {
                Long careActivityId = careActivityMap.get(activityType.getText().toString()).getId();
                if (validate(activityType, note)) {
                    CareActivityInfoRequest careActivityInfoRequest = new CareActivityInfoRequest(careActivityId, note.getText().toString());
                    if (id.length() != 0) {
                        careActivityInfoRequest.setId(Long.parseLong(id.getText().toString()));
                        activityInfoId.add(Long.parseLong(id.getText().toString()));
                    }
                    careActivityInfoList.add(careActivityInfoRequest);
                }
            }
        }
        if (!careActivityInfoList.isEmpty()) {
            for (CareActivityInfoRequest careActivityInfoRequest : careActivityInfoRequests) {
                if (!activityInfoId.contains(careActivityInfoRequest.getId())) {
                    deleteCareActivityInfoRequests.add(careActivityInfoRequest);
                }
            }
            careActivityNotificationRequest.setTitle(title.getText().toString());
            careActivityNotificationRequest.setNote(totalNote.getText().toString());
            if (!deleteCareActivityInfoRequests.isEmpty()) {
                for (CareActivityInfoRequest careActivityInfoRequest : deleteCareActivityInfoRequests) {
                    APIService.apiService.deleteCareActivityInfo(careActivityInfoRequest.getId()).enqueue(new Callback<com.project.petmanagement.payloads.responses.Response>() {
                        @Override
                        public void onResponse(Call<com.project.petmanagement.payloads.responses.Response> call, Response<com.project.petmanagement.payloads.responses.Response> response) {

                        }

                        @Override
                        public void onFailure(Call<com.project.petmanagement.payloads.responses.Response> call, Throwable t) {

                        }
                    });
                }
            }

            APIService.apiService.updateCareActivityNotification(careActivityNotificationId, careActivityNotificationRequest).enqueue(new Callback<CareActivityNotificationResponse>() {
                @Override
                public void onResponse(Call<CareActivityNotificationResponse> call, Response<CareActivityNotificationResponse> response) {
                    if (response.isSuccessful()) {

                        APIService.apiService.updateCareActivityInfo(careActivityNotificationId, careActivityInfoList).enqueue(new Callback<com.project.petmanagement.payloads.responses.Response>() {
                            @Override
                            public void onResponse(Call<com.project.petmanagement.payloads.responses.Response> call, Response<com.project.petmanagement.payloads.responses.Response> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(UpdateCareActivityInfoActivity.this, "update thành công", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<com.project.petmanagement.payloads.responses.Response> call, Throwable t) {

                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<CareActivityNotificationResponse> call, Throwable t) {

                }
            });
        }
    }

    private void addView() {
        final View childView = getLayoutInflater().inflate(R.layout.item_add_care_activity_info, null, false);
        TextView title = childView.findViewById(R.id.title);
        String strTile = "Hoạt động " + stt;
        stt += 1;
        title.setText(strTile);
        AutoCompleteTextView activityType = childView.findViewById(R.id.activity_type);
        activityType.setOnItemClickListener((parent, view, position, id) -> {
            activityType.setError(null);
        });
        APIService.apiService.getAllCareActivities().enqueue(new Callback<ListCareActivityResponse>() {
            @Override
            public void onResponse(Call<ListCareActivityResponse> call, Response<ListCareActivityResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getData() != null) {
                        for (CareActivity careActivity : response.body().getData()) {
                            careActivityMap.put(careActivity.getName(), careActivity);
                        }
                        careActivityAdapter = new ArrayAdapter<>(UpdateCareActivityInfoActivity.this, R.layout.item_dropdown_list, new ArrayList<>(careActivityMap.keySet()));
                        activityType.setAdapter(careActivityAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ListCareActivityResponse> call, Throwable t) {

            }
        });
        ImageView deleteLayout = childView.findViewById(R.id.delete_layout);
        deleteLayout.setOnClickListener(v -> {
            int position = parentLayout.indexOfChild(childView);
            parentLayout.removeView(childView);
            for (int i = position; i < parentLayout.getChildCount(); i++) {
                TextView title1 = parentLayout.getChildAt(i).findViewById(R.id.title);
                String strTile1 = "Hoạt động " + (i + 1);
                title1.setText(strTile1);
            }
            stt = parentLayout.getChildCount() + 1;
            if (parentLayout.getChildCount() == 0) {
                stt = 1;
            }
            setUpButton();
        });
        parentLayout.addView(childView);
        setUpButton();
    }

    private void setUpButton() {
        if (parentLayout.getChildCount() == 0) {
            saveBtn.setEnabled(false);
            saveBtn.setAlpha(0.4f);
        } else {
            saveBtn.setEnabled(true);
            saveBtn.setAlpha(1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpButton();
    }
}