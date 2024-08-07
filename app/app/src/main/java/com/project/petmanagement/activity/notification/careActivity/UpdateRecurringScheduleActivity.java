package com.project.petmanagement.activity.notification.careActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.project.petmanagement.R;
import com.project.petmanagement.adapters.DayOfWeekAdapter;
import com.project.petmanagement.models.enums.FrequencyEnum;
import com.project.petmanagement.payloads.requests.RecurringScheduleRequest;
import com.project.petmanagement.payloads.responses.Response;
import com.project.petmanagement.services.APIService;
import com.project.petmanagement.utils.DialogUtils;
import com.project.petmanagement.utils.FormatDateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class UpdateRecurringScheduleActivity extends AppCompatActivity implements DayOfWeekInterface {
    private Dialog dialogCustom;
    private TextView frequency;
    private Integer valueFrequency;
    private LinearLayout noRepeatLayout, dayRepeatLayout, weekRepeatLayout;
    private TextInputEditText dateNoRepeat, hourNoRepeat, startDateDayRepeat, endDateDayRepeat, startDateWeekRepeat, endDateWeekRepeat;
    private TextView chooseHourDayRepeat, chooseHourWeekRepeat, hourDayRepeat, hourWeekRepeat;
    private TimePickerDialog timePickerDialog;
    private RecyclerView dayOfWeekRecyclerView;
    private Long recurringScheduleRequestId;
    private DatePickerDialog datePickerDialog;
    private List<DayOfWeek> dayOfWeeks;

    private void clearText() {
        dateNoRepeat.setText("");
        hourNoRepeat.setText("");
        startDateDayRepeat.setText("");
        endDateDayRepeat.setText("");
        hourDayRepeat.setText("00:00");
        hourWeekRepeat.setText("00:00");
        startDateWeekRepeat.setText("");
        endDateWeekRepeat.setText("");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_care_notification);
        LinearLayout changeFrequency = findViewById(R.id.change_frequency);
        dayOfWeekRecyclerView = findViewById(R.id.recyclerview_date);
        frequency = findViewById(R.id.frequency);
        noRepeatLayout = findViewById(R.id.no_repeat);
        dayRepeatLayout = findViewById(R.id.repeat_day);
        weekRepeatLayout = findViewById(R.id.repeat_week);
        Button saveBtn = findViewById(R.id.save_btn);
        dateNoRepeat = findViewById(R.id.date_no_repeat);
        hourNoRepeat = findViewById(R.id.hour_no_repeat);
        startDateDayRepeat = findViewById(R.id.start_date_repeat_day);
        endDateDayRepeat = findViewById(R.id.end_date_repeat_day);
        hourDayRepeat = findViewById(R.id.hour_repeat_day);
        chooseHourDayRepeat = findViewById(R.id.choose_hour_repeat_day);
        hourWeekRepeat = findViewById(R.id.hour_repeat_week);
        startDateWeekRepeat = findViewById(R.id.start_date_repeat_week);
        endDateWeekRepeat = findViewById(R.id.end_date_repeat_week);
        chooseHourWeekRepeat = findViewById(R.id.choose_hour_repeat_week);
        DayOfWeekAdapter dayOfWeekAdapter = new DayOfWeekAdapter(this, this);
        dayOfWeekRecyclerView.setAdapter(dayOfWeekAdapter);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        dayOfWeekRecyclerView.setLayoutManager(layoutManager);
        RecurringScheduleRequest recurringScheduleRequest1 = (RecurringScheduleRequest) getIntent().getSerializableExtra("recurringScheduleRequest");
        if (recurringScheduleRequest1 != null) {
            recurringScheduleRequestId = recurringScheduleRequest1.getId();
            if (recurringScheduleRequest1.getFrequency() == FrequencyEnum.NO_REPEAT) {
                noRepeatLayout.setVisibility(View.VISIBLE);
                dayRepeatLayout.setVisibility(View.GONE);
                weekRepeatLayout.setVisibility(View.GONE);
                clearText();
                frequency.setText("Không lặp lại");
                try {
                    Date date = FormatDateUtils.StringToDate(recurringScheduleRequest1.getDate());
                    String date1 = FormatDateUtils.DateToString(date);
                    valueFrequency = recurringScheduleRequest1.getValue();
                    hourNoRepeat.setText(recurringScheduleRequest1.getTime());
                    dateNoRepeat.setText(date1);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            } else if (recurringScheduleRequest1.getFrequency() == FrequencyEnum.DAILY) {
                noRepeatLayout.setVisibility(View.GONE);
                dayRepeatLayout.setVisibility(View.VISIBLE);
                weekRepeatLayout.setVisibility(View.GONE);
                clearText();
                String strFrequency = "Lặp lại mỗi " + recurringScheduleRequest1.getValue() + " ngày 1 lần";
                frequency.setText(strFrequency);
                valueFrequency = recurringScheduleRequest1.getValue();
                hourDayRepeat.setText(recurringScheduleRequest1.getTime());
                try {
                    Date date = FormatDateUtils.StringToDate(recurringScheduleRequest1.getFromDate());
                    String date1 = FormatDateUtils.DateToString(date);
                    startDateDayRepeat.setText(date1);
                    Date date2 = FormatDateUtils.StringToDate(recurringScheduleRequest1.getToDate());
                    String date3 = FormatDateUtils.DateToString(date2);
                    endDateDayRepeat.setText(date3);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            } else {
                noRepeatLayout.setVisibility(View.GONE);
                dayRepeatLayout.setVisibility(View.GONE);
                weekRepeatLayout.setVisibility(View.VISIBLE);
                clearText();
                dayOfWeekAdapter.setDayOfWeeksChoose(recurringScheduleRequest1.getDaysOfWeek());
                valueFrequency = recurringScheduleRequest1.getValue();
                String strFrequency = "Lặp lại mỗi " + recurringScheduleRequest1.getValue() + " tuần 1 lần";
                frequency.setText(strFrequency);
                hourWeekRepeat.setText(recurringScheduleRequest1.getTime());
                try {
                    Date date = FormatDateUtils.StringToDate(recurringScheduleRequest1.getFromDate());
                    String date1 = FormatDateUtils.DateToString(date);
                    startDateWeekRepeat.setText(date1);
                    Date date2 = FormatDateUtils.StringToDate(recurringScheduleRequest1.getToDate());
                    String date3 = FormatDateUtils.DateToString(date2);
                    endDateWeekRepeat.setText(date3);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        //day
        customDayFromDate();
        customDayEndDate();
        //week
        customWeekFromDate();
        customWeekEndDate();
        customTimepickerEditText();
        customDate(dateNoRepeat);
        customTimepickerTextView();
        customTimepickerTextView1();
        saveBtn.setOnClickListener(v -> {
            if (noRepeatLayout.getVisibility() == View.VISIBLE) {
                if (validationNoRepeat()) {
                    try {
                        RecurringScheduleRequest recurringScheduleRequest = new RecurringScheduleRequest();
                        recurringScheduleRequest.setFrequency(FrequencyEnum.NO_REPEAT);
                        Date date1 = FormatDateUtils.StringToDate1(dateNoRepeat.getText().toString());
                        String date2 = FormatDateUtils.DateToString1(date1);
                        recurringScheduleRequest.setId(recurringScheduleRequestId);
                        recurringScheduleRequest.setDate(date2);
                        recurringScheduleRequest.setValue(0);
                        recurringScheduleRequest.setName(frequency.getText().toString());
                        recurringScheduleRequest.setTime(hourNoRepeat.getText().toString());
                        APIService.apiService.updateRecurringSchedule(recurringScheduleRequest).enqueue(new Callback<Response>() {
                            @Override
                            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(UpdateRecurringScheduleActivity.this, "Cập thời gian nhập thành công", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<Response> call, Throwable t) {

                            }
                        });
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
            if (dayRepeatLayout.getVisibility() == View.VISIBLE) {
                if (validationRepeatDay()) {
                    try {
                        RecurringScheduleRequest recurringScheduleRequest = new RecurringScheduleRequest();
                        recurringScheduleRequest.setFrequency(FrequencyEnum.DAILY);
                        Date date1 = FormatDateUtils.StringToDate1(startDateDayRepeat.getText().toString());
                        String strStartDate = FormatDateUtils.DateToString1(date1);
                        recurringScheduleRequest.setFromDate(strStartDate);
                        recurringScheduleRequest.setId(recurringScheduleRequestId);
                        Date date3 = FormatDateUtils.StringToDate1(endDateDayRepeat.getText().toString());
                        String strEndDate = FormatDateUtils.DateToString1(date3);
                        recurringScheduleRequest.setTime(hourDayRepeat.getText().toString());
                        recurringScheduleRequest.setToDate(strEndDate);
                        recurringScheduleRequest.setValue(valueFrequency);
                        recurringScheduleRequest.setName(frequency.getText().toString());
                        APIService.apiService.updateRecurringSchedule(recurringScheduleRequest).enqueue(new Callback<Response>() {
                            @Override
                            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(UpdateRecurringScheduleActivity.this, "Cập thời gian nhập thành công", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<Response> call, Throwable t) {

                            }
                        });
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else if (weekRepeatLayout.getVisibility() == View.VISIBLE) {
                if (validationRepeatWeek()) {
                    try {
                        RecurringScheduleRequest recurringScheduleRequest = new RecurringScheduleRequest();
                        recurringScheduleRequest.setFrequency(FrequencyEnum.WEEKLY);
                        Date date1 = FormatDateUtils.StringToDate1(startDateWeekRepeat.getText().toString());
                        String strStartDate = FormatDateUtils.DateToString1(date1);
                        recurringScheduleRequest.setFromDate(strStartDate);
                        recurringScheduleRequest.setId(recurringScheduleRequestId);
                        Date date3 = FormatDateUtils.StringToDate1(endDateWeekRepeat.getText().toString());
                        String strEndDate = FormatDateUtils.DateToString1(date3);
                        recurringScheduleRequest.setTime(hourWeekRepeat.getText().toString());
                        recurringScheduleRequest.setToDate(strEndDate);
                        recurringScheduleRequest.setValue(valueFrequency);
                        recurringScheduleRequest.setDaysOfWeek(dayOfWeeks);
                        recurringScheduleRequest.setName(frequency.getText().toString());
                        APIService.apiService.updateRecurringSchedule(recurringScheduleRequest).enqueue(new Callback<Response>() {
                            @Override
                            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(UpdateRecurringScheduleActivity.this, "Cập thời gian nhập thành công", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<Response> call, Throwable t) {

                            }
                        });
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        final int[] checkedItem = {-1};
        changeFrequency.setOnClickListener(v -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(UpdateRecurringScheduleActivity.this);
            final String[] listItems = new String[]{"Không lặp lại", "Lặp theo ngày", "Lặp theo tuần"};
            alertDialog.setSingleChoiceItems(listItems, checkedItem[0], (dialog, which) -> {
                checkedItem[0] = which;
                if (which == 0) {
                    frequency.setText(listItems[which]);
                    noRepeatLayout.setVisibility(View.VISIBLE);
                    dayRepeatLayout.setVisibility(View.GONE);
                    weekRepeatLayout.setVisibility(View.GONE);
                    valueFrequency = 0;
                    clearText();
                    dialog.dismiss();
                } else if (which == 1) {
                    openAddDialog(Gravity.CENTER, "Lặp theo ngày");
                    dialog.dismiss();
                } else {
                    openAddDialog(Gravity.CENTER, "Lặp theo tuần");
                    dialog.dismiss();
                }

            });
            alertDialog.setNegativeButton("Cancel", (dialog, which) -> {
            });
            AlertDialog customAlertDialog = alertDialog.create();
            customAlertDialog.show();
        });
        ImageView returnArrow = findViewById(R.id.return_arrow);
        returnArrow.setOnClickListener(v -> finish());
    }

    private void openAddDialog(int gravity, String typeFrequency) {
        dialogCustom = new Dialog(this);
        dialogCustom.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogCustom.setContentView(R.layout.item_add_frequency_value);
        Window window = dialogCustom.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = Gravity.CENTER;
        window.setAttributes(windowAttribute);
        if (Gravity.BOTTOM == gravity) {
            dialogCustom.setCancelable(false);
        } else {
            dialogCustom.setCancelable(true);
        }
        TextInputEditText editValueFrequency = dialogCustom.findViewById(R.id.value_frequency);
        Button btnCancel = dialogCustom.findViewById(R.id.btn_cancel);
        Button btnAccept = dialogCustom.findViewById(R.id.btn_accept);
        btnCancel.setOnClickListener(v -> {
            dialogCustom.dismiss();
        });
        btnAccept.setOnClickListener(v -> {
            if (validation(editValueFrequency)) {
                String str = null;
                if (typeFrequency.equals("Lặp theo ngày")) {
                    str = "Lặp lại mỗi " + editValueFrequency.getText().toString() + " ngày 1 lần";
                    frequency.setText(str);
                    valueFrequency = Integer.parseInt(editValueFrequency.getText().toString());
                    noRepeatLayout.setVisibility(View.GONE);
                    weekRepeatLayout.setVisibility(View.GONE);
                    dayOfWeekRecyclerView.setVisibility(View.GONE);
                    dayRepeatLayout.setVisibility(View.VISIBLE);
                } else {
                    str = "Lặp lại  " + editValueFrequency.getText().toString() + " tuần 1 lần";
                    frequency.setText(str);
                    valueFrequency = Integer.parseInt(editValueFrequency.getText().toString());
                    noRepeatLayout.setVisibility(View.GONE);
                    dayRepeatLayout.setVisibility(View.GONE);
                    weekRepeatLayout.setVisibility(View.VISIBLE);
                }
                clearText();
                dialogCustom.dismiss();
            }
        });
        dialogCustom.show();
    }

    private boolean validationRepeatWeek() {
        if (startDateWeekRepeat.length() == 0) {
            DialogUtils.setUpDialog(UpdateRecurringScheduleActivity.this, "Ngày bắt đầu không được để trống");
            return false;
        }
        if (endDateWeekRepeat.length() == 0) {
            DialogUtils.setUpDialog(UpdateRecurringScheduleActivity.this, "Ngày kết thúc không được để trống");
            return false;
        }
        return true;
    }

    private boolean validationRepeatDay() {
        if (startDateDayRepeat.length() == 0) {
            DialogUtils.setUpDialog(UpdateRecurringScheduleActivity.this, "Ngày bắt đầu không được để trống");
            return false;
        }
        if (endDateDayRepeat.length() == 0) {
            DialogUtils.setUpDialog(UpdateRecurringScheduleActivity.this, "Ngày kết thúc không được để trống");
            return false;
        }
        return true;
    }

    private boolean validationNoRepeat() {
        if (dateNoRepeat.length() == 0) {
            DialogUtils.setUpDialog(UpdateRecurringScheduleActivity.this, "Ngày không được để trống");
            return false;
        }
        if (hourNoRepeat.length() == 0) {
            DialogUtils.setUpDialog(UpdateRecurringScheduleActivity.this, "Giờ không được để trống");
            return false;
        }
        return true;
    }

    private boolean validation(TextInputEditText editText) {
        if (editText.length() == 0) {
            editText.setError("Giá trị lặp không được để trống.");
            return false;
        }
        if (Integer.parseInt(editText.getText().toString()) <= 0) {
            editText.setError("Giá trị lặp phải lớn hơn 0.");
            return false;
        }
        return true;
    }

    private void customDate(TextInputEditText editDate) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        editDate.setOnClickListener(v -> {
            datePickerDialog = new DatePickerDialog(UpdateRecurringScheduleActivity.this, (view, year1, month1, dayOfMonth) -> {
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
                        DialogUtils.setUpDialog(UpdateRecurringScheduleActivity.this, "Ngày bạn chọn phải lớn hơn hoặc bằng ngày hiện tại");
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

    private void customTimepickerTextView() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        chooseHourDayRepeat.setOnClickListener(v -> {
            timePickerDialog = new TimePickerDialog(UpdateRecurringScheduleActivity.this, (view, hourOfDay, minute1) -> {
                String time = hourOfDay + ":" + minute1;
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                try {
                    Date date = dateFormat.parse(time);
                    String formattedTime = dateFormat.format(date);
                    hourDayRepeat.setText(formattedTime);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }, hour, minute, true);
            timePickerDialog.show();
        });
    }

    private void customTimepickerTextView1() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        chooseHourWeekRepeat.setOnClickListener(v -> {
            timePickerDialog = new TimePickerDialog(UpdateRecurringScheduleActivity.this, (view, hourOfDay, minute1) -> {
                String time = hourOfDay + ":" + minute1;
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                try {
                    Date date = dateFormat.parse(time);
                    String formattedTime = dateFormat.format(date);
                    hourWeekRepeat.setText(formattedTime);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }, hour, minute, true);
            timePickerDialog.show();
        });
    }

    private void customTimepickerEditText() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        hourNoRepeat.setOnClickListener(v -> {
            timePickerDialog = new TimePickerDialog(UpdateRecurringScheduleActivity.this, (view, hourOfDay, minute1) -> {
                String time = hourOfDay + ":" + minute1;
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                try {
                    Date date = dateFormat.parse(time);
                    String formattedTime = dateFormat.format(date);
                    hourNoRepeat.setText(formattedTime);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }, hour, minute, true);
            timePickerDialog.show();
        });
    }

    private void customDayFromDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        startDateDayRepeat.setOnClickListener(v -> {
            datePickerDialog = new DatePickerDialog(UpdateRecurringScheduleActivity.this, (view, year1, month1, dayOfMonth) -> {
                String date = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                try {
                    Date startDate1 = FormatDateUtils.StringToDate1(date);
                    boolean check = true;
                    if (endDateDayRepeat.length() != 0) {
                        Date endDate1 = FormatDateUtils.StringToDate1(endDateDayRepeat.getText().toString());
                        if (startDate1.compareTo(endDate1) > 0) {
                            check = false;
                        }
                    }
                    if (check) {
                        String startDate2 = FormatDateUtils.DateToString(startDate1);
                        startDateDayRepeat.setText(startDate2);
                    } else {
                        DialogUtils.setUpDialog(UpdateRecurringScheduleActivity.this, "Ngày bắt đầu không được lớn hơn ngày kết thúc");
                    }
                    startDateDayRepeat.setError(null);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }, year, month, day);
            datePickerDialog.show();
        });
    }

    private void customDayEndDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        endDateDayRepeat.setOnClickListener(v -> {
            datePickerDialog = new DatePickerDialog(UpdateRecurringScheduleActivity.this, (view, year1, month1, dayOfMonth) -> {
                String date = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                try {
                    Date endDate1 = FormatDateUtils.StringToDate1(date);
                    boolean check = true;
                    if (startDateDayRepeat.length() != 0) {
                        Date startDate1 = FormatDateUtils.StringToDate1(startDateDayRepeat.getText().toString());
                        if (endDate1.compareTo(startDate1) < 0) {
                            check = false;
                        }
                    }
                    if (check) {
                        String endDate2 = FormatDateUtils.DateToString(endDate1);
                        endDateDayRepeat.setText(endDate2);
                    } else {
                        DialogUtils.setUpDialog(UpdateRecurringScheduleActivity.this, "Ngày bắt đầu không được lớn hơn ngày kết thúc");
                    }
                    endDateDayRepeat.setError(null);

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }, year, month, day);
            datePickerDialog.show();
        });
    }

    private void customWeekFromDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        startDateWeekRepeat.setOnClickListener(v -> {
            datePickerDialog = new DatePickerDialog(UpdateRecurringScheduleActivity.this, (view, year1, month1, dayOfMonth) -> {
                String date = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                try {
                    Date startDate1 = FormatDateUtils.StringToDate1(date);
                    boolean check = true;
                    if (endDateWeekRepeat.length() != 0) {
                        Date endDate1 = FormatDateUtils.StringToDate1(endDateWeekRepeat.getText().toString());
                        if (startDate1.compareTo(endDate1) > 0) {
                            check = false;
                        }
                    }
                    if (check) {
                        String startDate2 = FormatDateUtils.DateToString(startDate1);
                        startDateWeekRepeat.setText(startDate2);
                    } else {
                        DialogUtils.setUpDialog(UpdateRecurringScheduleActivity.this, "Ngày bắt đầu không được lớn hơn ngày kết thúc");
                    }
                    startDateWeekRepeat.setError(null);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }, year, month, day);
            datePickerDialog.show();
        });
    }

    private void customWeekEndDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        endDateWeekRepeat.setOnClickListener(v -> {
            datePickerDialog = new DatePickerDialog(UpdateRecurringScheduleActivity.this, (view, year1, month1, dayOfMonth) -> {
                String date = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                try {
                    Date endDate1 = FormatDateUtils.StringToDate1(date);
                    boolean check = true;
                    if (startDateWeekRepeat.length() != 0) {
                        Date startDate1 = FormatDateUtils.StringToDate1(startDateWeekRepeat.getText().toString());
                        if (endDate1.compareTo(startDate1) < 0) {
                            check = false;
                        }
                    }
                    if (check) {
                        String endDate2 = FormatDateUtils.DateToString(endDate1);
                        endDateWeekRepeat.setText(endDate2);
                    } else {
                        DialogUtils.setUpDialog(UpdateRecurringScheduleActivity.this, "Ngày bắt đầu không được lớn hơn ngày kết thúc");
                    }
                    endDateWeekRepeat.setError(null);

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }, year, month, day);
            datePickerDialog.show();
        });
    }

    @Override
    public void getDayOfWeek(List<DayOfWeek> dayOfWeeks) {
        this.dayOfWeeks = dayOfWeeks;
    }


}