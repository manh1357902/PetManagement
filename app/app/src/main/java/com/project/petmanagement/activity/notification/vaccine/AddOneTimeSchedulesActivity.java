package com.project.petmanagement.activity.notification.vaccine;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;
import com.project.petmanagement.R;
import com.project.petmanagement.payloads.requests.OneTimeScheduleRequest;
import com.project.petmanagement.utils.DialogUtils;
import com.project.petmanagement.utils.FormatDateUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddOneTimeSchedulesActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private LinearLayout parentLayout;
    private Button saveBtn;
    private int stt = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_one_time_schedules);
        ImageView returnArrow = findViewById(R.id.return_arrow);
        CardView btnAddLayout = findViewById(R.id.add_time);
        parentLayout = findViewById(R.id.container_view);
        returnArrow.setOnClickListener(v -> finish());
        saveBtn = findViewById(R.id.save_btn);
        List<OneTimeScheduleRequest> oneTimeScheduleRequests = (List<OneTimeScheduleRequest>) getIntent().getSerializableExtra("listOneTime");
        if (oneTimeScheduleRequests != null) {
            for (OneTimeScheduleRequest oneTimeScheduleRequest : oneTimeScheduleRequests) {
                final View childView = getLayoutInflater().inflate(R.layout.item_add_one_time_schedule, null, false);
                TextView title = childView.findViewById(R.id.title);
                String strTile = "Lịch tiêm vaccine " + stt;
                stt += 1;
                title.setText(strTile);
                TextInputEditText dateInject = childView.findViewById(R.id.date_inject);
                TextInputEditText hourInject = childView.findViewById(R.id.hour);
                Date date = null;
                try {
                    date = FormatDateUtils.StringToDate(oneTimeScheduleRequest.getDate());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                String strDate = FormatDateUtils.DateToString(date);
                dateInject.setText(strDate);
                hourInject.setText(oneTimeScheduleRequest.getTime());
                ImageView deleteLayout = childView.findViewById(R.id.delete_layout);
                deleteLayout.setOnClickListener(v -> {
                    int position = parentLayout.indexOfChild(childView);
                    parentLayout.removeView(childView);
                    for (int i = position; i < parentLayout.getChildCount(); i++) {
                        TextView title1 = parentLayout.getChildAt(i).findViewById(R.id.title);
                        String strTile1 = "Lịch tiêm vaccine " + (i + 1);
                        title1.setText(strTile1);
                    }
                    stt = parentLayout.getChildCount() + 1;
                    if (parentLayout.getChildCount() == 0) {
                        stt = 1;
                    }
                    setUpButton();
                });
                customDate(dateInject);
                customTimepicker(hourInject);
                parentLayout.addView(childView);
                setUpButton();
            }
        }
        saveBtn.setOnClickListener(v -> {
            saveSchedule();
        });
        setUpButton();
        btnAddLayout.setOnClickListener(v -> {
            addView();
        });
    }

    private boolean validate(TextInputEditText date, TextInputEditText hour) {
        if (date.length() == 0) {
            date.setError("Không được để trống");
            return false;
        }
        if (hour.length() == 0) {
            hour.setError("Không được để trống");
            return false;
        }
        return true;
    }

    private void saveSchedule() {
        List<OneTimeScheduleRequest> oneTimeScheduleRequests = new ArrayList<>();
        for (int i = 0; i < parentLayout.getChildCount(); i++) {
            View childView = parentLayout.getChildAt(i);
            TextInputEditText dateInject = childView.findViewById(R.id.date_inject);
            TextInputEditText hourInject = childView.findViewById(R.id.hour);
            if (validate(dateInject, hourInject)) {
                try {
                    Date date1 = FormatDateUtils.StringToDate1(dateInject.getText().toString());
                    String date2 = FormatDateUtils.DateToString1(date1);
                    OneTimeScheduleRequest oneTimeScheduleRequest = new OneTimeScheduleRequest(date2, hourInject.getText().toString());
                    oneTimeScheduleRequests.add(oneTimeScheduleRequest);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (!oneTimeScheduleRequests.isEmpty() && parentLayout.getChildCount() != 0) {
            Intent intent = new Intent(AddOneTimeSchedulesActivity.this, AddVaccinationNotificationActivity.class);
            intent.putExtra("listOneTime", (Serializable) oneTimeScheduleRequests);
            setResult(RESULT_OK, intent);
            finish();
        }
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

    private void addView() {
        final View childView = getLayoutInflater().inflate(R.layout.item_add_one_time_schedule, null, false);
        TextView title = childView.findViewById(R.id.title);
        String strTile = "Lịch tiêm vaccine " + stt;
        stt += 1;
        title.setText(strTile);
        TextInputEditText dateInject = childView.findViewById(R.id.date_inject);
        TextInputEditText hourInject = childView.findViewById(R.id.hour);
        ImageView deleteLayout = childView.findViewById(R.id.delete_layout);
        deleteLayout.setOnClickListener(v -> {
            int position = parentLayout.indexOfChild(childView);
            parentLayout.removeView(childView);
            for (int i = position; i < parentLayout.getChildCount(); i++) {
                TextView title1 = parentLayout.getChildAt(i).findViewById(R.id.title);
                String strTile1 = "Lịch tiêm vaccine " + (i + 1);
                title1.setText(strTile1);
            }
            stt = parentLayout.getChildCount() + 1;
            if (parentLayout.getChildCount() == 0) {
                stt = 1;
            }
            setUpButton();
        });
        customDate(dateInject);
        customTimepicker(hourInject);
        parentLayout.addView(childView);
        setUpButton();
    }

    private void customDate(TextInputEditText editDate) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        editDate.setOnClickListener(v -> {
            datePickerDialog = new DatePickerDialog(AddOneTimeSchedulesActivity.this, (view, year1, month1, dayOfMonth) -> {
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
                    if (cal1.compareTo(cal2) > 0) {
                        DialogUtils.setUpDialog(AddOneTimeSchedulesActivity.this, "Ngày bạn chọn phải lớn hơn hoặc bằng ngày hiện tại");
                    } else {
                        editDate.setText(date2);
                    }
                    editDate.setError(null);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            }, year, month, day);
            datePickerDialog.show();
        });
    }

    private void customTimepicker(TextInputEditText editTime) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        editTime.setOnClickListener(v -> {
            timePickerDialog = new TimePickerDialog(AddOneTimeSchedulesActivity.this, (view, hourOfDay, minute1) -> {
                String time = hourOfDay + ":" + minute1;
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                try {
                    Date date = dateFormat.parse(time);
                    String formattedTime = dateFormat.format(date);
                    editTime.setText(formattedTime);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }, hour, minute, true);
            timePickerDialog.show();
        });
    }
}