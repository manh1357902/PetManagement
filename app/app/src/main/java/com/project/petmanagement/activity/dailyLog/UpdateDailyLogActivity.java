package com.project.petmanagement.activity.dailyLog;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.project.petmanagement.R;
import com.project.petmanagement.models.entity.DailyActivity;
import com.project.petmanagement.models.entity.DailyActivityLog;
import com.project.petmanagement.payloads.requests.DailyActivityLogRequest;
import com.project.petmanagement.payloads.responses.DailyActivityLogResponse;
import com.project.petmanagement.payloads.responses.ListDaiLyActivityResponse;
import com.project.petmanagement.services.APIService;
import com.project.petmanagement.utils.FormatDateUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateDailyLogActivity extends AppCompatActivity {
    private long petId;
    private long logId;
    private ImageView returnArrow;
    private CircleImageView petImage;
    private TextView petName;
    private TextInputEditText logTitle;
    private TextInputEditText logDate;
    private DatePickerDialog datePickerDialog;
    private TextInputEditText logTime;
    private TimePickerDialog timePickerDialog;
    private AutoCompleteTextView logActivity;
    private ArrayAdapter<String> dailyActivityAdapter;
    private Map<String, DailyActivity> dailyActivityMap;
    private TextInputEditText logNote;
    private ImageView images;
    private Bitmap imageBitmap;
    private Button saveBtn;
    private String imageUrl;
    private final static int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private final static int READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 101;
    private final static int WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 102;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_daily_log);
        initView();
        customDatePicker(logDate);
        customTimepicker(logTime);
        returnArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        images.setOnClickListener(v -> setOpenCameraDialog());
        logId = getIntent().getLongExtra("logId", 0);
        APIService.apiService.getDailyLogById(logId).enqueue(new Callback<DailyActivityLogResponse>() {
            @Override
            public void onResponse(Call<DailyActivityLogResponse> call, Response<DailyActivityLogResponse> response) {
                if (response.isSuccessful()) {
                    DailyActivityLogResponse dailyActivityLogResponse = response.body();
                    if (dailyActivityLogResponse != null) {
                        DailyActivityLog dailyActivityLog = dailyActivityLogResponse.getData();
                        petId = dailyActivityLog.getPet().getId();
                        petName.setText(dailyActivityLog.getPet().getFullName());
                        Glide.with(UpdateDailyLogActivity.this)
                                .load(dailyActivityLog.getPet().getImage())
                                .error(R.drawable.default_pet_no_image)
                                .into(petImage);
                        logTitle.setText(dailyActivityLog.getTitle());
                        logDate.setText(FormatDateUtils.DateToString(dailyActivityLog.getDate()));
                        logTime.setText(dailyActivityLog.getTime());
                        logActivity.setText(dailyActivityLog.getDailyActivity().getName());
                        logNote.setText(dailyActivityLog.getNote());
                        Glide.with(UpdateDailyLogActivity.this)
                                .load(dailyActivityLog.getImage())
                                .error(R.drawable.baseline_image_24)
                                .into(images);
                        setDailyActivityList();
                    }
                }
                System.out.println(response);
            }

            @Override
            public void onFailure(Call<DailyActivityLogResponse> call, Throwable t) {
                Toast.makeText(UpdateDailyLogActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDailyLog();
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
        dailyActivityMap = new LinkedHashMap<>();
        images = findViewById(R.id.images);
        logNote = findViewById(R.id.log_note);
        returnArrow = findViewById(R.id.return_arrow);
        saveBtn = findViewById(R.id.save_btn);
    }

    private void setDailyActivityList() {
        APIService.apiService.getAllDaiLyActivities().enqueue(new Callback<ListDaiLyActivityResponse>() {
            @Override
            public void onResponse(Call<ListDaiLyActivityResponse> call, Response<ListDaiLyActivityResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getData() != null) {
                        for (DailyActivity dailyActivity : response.body().getData()) {
                            dailyActivityMap.put(dailyActivity.getName(), dailyActivity);
                        }
                        dailyActivityAdapter = new ArrayAdapter<>(UpdateDailyLogActivity.this, R.layout.item_dropdown_list, new ArrayList<>(dailyActivityMap.keySet()));
                        logActivity.setAdapter(dailyActivityAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ListDaiLyActivityResponse> call, Throwable t) {

            }
        });
        logActivity.setOnItemClickListener((parent, view, position, id) -> {
            logActivity.setError(null);
        });
    }

    private boolean isValid() {
        if (logTitle.length() == 0) {
            logTitle.setError("Tiêu đề không được để trống");
            return false;
        }
        if (logDate.length() == 0) {
            logDate.setError("Ngày ghi nhật kí không được để trống.");
            return false;
        }
        if (logTime.length() == 0) {
            logTime.setError("Giờ ghi nhật kí không được để trống.");
            return false;
        }
        if (logActivity.length() == 0) {
            logActivity.setError("Hoạt động không được để trống");
            return false;
        }
        return true;
    }

    private void updateDailyLog() {
        if (isValid()) {
            String title = Objects.requireNonNull(logTitle.getText()).toString().trim();
            String date;
            try {
                Date formatDate = FormatDateUtils.StringToDate1(logDate.getText().toString().trim());
                date = FormatDateUtils.DateToString1(formatDate);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            String time = logTime.getText().toString().trim();
            long dailyActivityId = dailyActivityMap.get(logActivity.getText().toString()).getId();
            String note = logNote.getText().toString().trim();
            String finalDate = date;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                if (ContextCompat.checkSelfPermission(UpdateDailyLogActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    saveImage();
                } else {
                    ActivityCompat.requestPermissions(UpdateDailyLogActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
                }
            } else {
                saveImage();
            }
            DailyActivityLogRequest dailyActivityLogRequest = new DailyActivityLogRequest();
            dailyActivityLogRequest.setId(logId);
            dailyActivityLogRequest.setPetId(petId);
            dailyActivityLogRequest.setTitle(title);
            dailyActivityLogRequest.setDate(finalDate);
            dailyActivityLogRequest.setTime(time);
            dailyActivityLogRequest.setDailyActivityId(dailyActivityId);
            dailyActivityLogRequest.setImage(imageUrl);
            dailyActivityLogRequest.setNote(note);
            APIService.apiService.updateDailyLog(dailyActivityLogRequest).enqueue(new Callback<DailyActivityLogResponse>() {
                @Override
                public void onResponse(Call<DailyActivityLogResponse> call, Response<DailyActivityLogResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(UpdateDailyLogActivity.this, "Cập nhật nhật kí thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<DailyActivityLogResponse> call, Throwable t) {
                    Toast.makeText(UpdateDailyLogActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void customDatePicker(TextInputEditText editeDate) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        editeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(UpdateDailyLogActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        try {
                            Date date1 = sdf.parse(date);
                            String date2 = sdf.format(date1);
                            editeDate.setText(date2);
                            editeDate.setError(null);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private void customTimepicker(TextInputEditText editTime) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        editTime.setOnClickListener(v -> {
            timePickerDialog = new TimePickerDialog(UpdateDailyLogActivity.this, (view, hourOfDay, minute1) -> {
                String time = hourOfDay + ":" + minute1;
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                try {
                    Date date = dateFormat.parse(time);
                    String formattedTime = dateFormat.format(date);
                    editTime.setText(formattedTime);
                    editTime.setError(null);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }, hour, minute, true);
            timePickerDialog.show();
        });
    }

    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if (o.getResultCode() == Activity.RESULT_OK) {
                Bundle bundle = o.getData().getExtras();
                if (bundle != null) {
                    imageBitmap = (Bitmap) bundle.get("data");
                    images.getLayoutParams().width = 400;
                    images.getLayoutParams().height = 400;
                    images.requestLayout();
                    images.setImageBitmap(imageBitmap);
                }
            }
        }
    });
    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if (o.getResultCode() == Activity.RESULT_OK) {
                Uri uri = null;
                if (o.getData() != null) {
                    uri = o.getData().getData();
                }
                try {
                    imageBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                    images.getLayoutParams().width = 400;
                    images.getLayoutParams().height = 400;
                    images.requestLayout();
                    images.setImageBitmap(imageBitmap);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    });

    public void setOpenCameraDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(UpdateDailyLogActivity.this);
        alertDialog.setTitle("Chọn")
                .setPositiveButton("Gallery", (dialog, which) -> {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                        if (ContextCompat.checkSelfPermission(UpdateDailyLogActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(UpdateDailyLogActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
                        } else {
                            startGallery();
                        }
                    } else {
                        startGallery();
                    }

                })
                .setNegativeButton("Camera", (dialog, which) -> {
                    if (ContextCompat.checkSelfPermission(UpdateDailyLogActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(UpdateDailyLogActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
                    } else {
                        startCamera();
                    }
                })
                .setNeutralButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    public void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(intent);
    }

    public void startGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(this, "Camera permission denied.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startGallery();
            } else {
                Toast.makeText(this, "Gallery permission denied.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startGallery();
            } else {
                Toast.makeText(this, "Gallery permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveImage() {
        File dir = new File(getFilesDir(), "daily_log_image");
        if (!dir.exists()) {
            dir.mkdir();
        }
        if (imageBitmap != null) {
            File file = new File(dir, "daily_log_" + System.currentTimeMillis() + ".jpg");
            OutputStream outputStream;
            try {
                outputStream = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 20, outputStream);
            imageUrl = file.getAbsolutePath();
            try {
                outputStream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
