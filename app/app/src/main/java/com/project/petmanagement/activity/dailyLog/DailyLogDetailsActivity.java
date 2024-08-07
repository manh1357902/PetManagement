package com.project.petmanagement.activity.dailyLog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.project.petmanagement.R;
import com.project.petmanagement.models.entity.DailyActivityLog;
import com.project.petmanagement.payloads.responses.DailyActivityLogResponse;
import com.project.petmanagement.services.APIService;
import com.project.petmanagement.utils.FormatDateUtils;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyLogDetailsActivity extends AppCompatActivity {
    private long logId;
    private ImageView returnArrow;
    private CircleImageView petImage;
    private TextView petName;
    private TextInputEditText logTitle;
    private TextInputEditText logDate;
    private TextInputEditText logTime;
    private AutoCompleteTextView logActivity;
    private TextInputEditText logNote;
    private ImageView images;
    private Button updateBtn;
    private Button deleteBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_log_details);
        initView();
        returnArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        logId = getIntent().getLongExtra("logId", 0);
        getDailyLog();
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UpdateDailyLogActivity.class);
                intent.putExtra("logId", logId);
                startActivity(intent);
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDailyLog();
            }
        });
    }

    private void initView() {
        petImage = findViewById(R.id.image_pet);
        petName = findViewById(R.id.name_pet);
        logTitle = findViewById(R.id.log_title);
        logDate = findViewById(R.id.log_date);
        logTime = findViewById(R.id.log_time);
        logActivity = findViewById(R.id.log_activity);
        images = findViewById(R.id.images);
        logNote = findViewById(R.id.log_note);
        updateBtn = findViewById(R.id.update_btn);
        deleteBtn = findViewById(R.id.delete_btn);
        updateBtn.setVisibility(View.VISIBLE);
        deleteBtn.setVisibility(View.VISIBLE);
        returnArrow = findViewById(R.id.return_arrow);
    }

    private void getDailyLog() {
        APIService.apiService.getDailyLogById(logId).enqueue(new Callback<DailyActivityLogResponse>() {
            @Override
            public void onResponse(Call<DailyActivityLogResponse> call, Response<DailyActivityLogResponse> response) {
                if (response.isSuccessful()) {
                    DailyActivityLogResponse dailyActivityLogResponse = response.body();
                    if (dailyActivityLogResponse != null) {
                        DailyActivityLog dailyActivityLog = dailyActivityLogResponse.getData();
                        petName.setText(dailyActivityLog.getTitle());
                        images.getLayoutParams().width = 400;
                        images.getLayoutParams().height = 400;
                        images.requestLayout();
                        Glide.with(DailyLogDetailsActivity.this)
                                .load(dailyActivityLog.getPet().getImage())
                                .error(R.drawable.default_pet_no_image)
                                .into(petImage);
                        logTitle.setText(dailyActivityLog.getDailyActivity().getName());
                        logTitle.setEnabled(false);
                        logTitle.setAlpha(1);
                        logDate.setText(FormatDateUtils.DateToString(dailyActivityLog.getDate()));
                        logDate.setEnabled(false);
                        logDate.setAlpha(1);
                        logTime.setText(dailyActivityLog.getTime());
                        logTime.setEnabled(false);
                        logTime.setAlpha(1);
                        logActivity.setText(dailyActivityLog.getDailyActivity().getName());
                        logActivity.setEnabled(false);
                        logActivity.setAlpha(1);
                        logNote.setText(dailyActivityLog.getNote());
                        logNote.setEnabled(false);
                        logNote.setAlpha(1);
                        Glide.with(DailyLogDetailsActivity.this)
                                .load(dailyActivityLog.getImage())
                                .error(R.drawable.baseline_image_24)
                                .into(images);
                        images.setEnabled(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<DailyActivityLogResponse> call, Throwable t) {
                Toast.makeText(DailyLogDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteDailyLog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DailyLogDetailsActivity.this);
        alertDialog.setTitle("Thông báo")
                .setMessage("Bạn chắc chắn muốn xóa nhật kí này")
                .setPositiveButton("Có", (dialog, which) -> {
                    APIService.apiService.deleteDailyLog(logId).enqueue(new Callback<DailyActivityLogResponse>() {
                        @Override
                        public void onResponse(Call<DailyActivityLogResponse> call, Response<DailyActivityLogResponse> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(DailyLogDetailsActivity.this, "Xóa thành công.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<DailyActivityLogResponse> call, Throwable t) {
                            Toast.makeText(DailyLogDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("Không", (dialog, which) -> dialog.cancel())
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDailyLog();
    }
}
