package com.project.petmanagement.activity.healthStatistic;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.project.petmanagement.R;
import com.project.petmanagement.adapters.HealthStatisticAdapter;
import com.project.petmanagement.models.entity.HealthRecord;
import com.project.petmanagement.payloads.responses.ListHealthRecordResponse;
import com.project.petmanagement.services.APIService;
import com.project.petmanagement.utils.DialogUtils;
import com.project.petmanagement.utils.FormatDateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HealthStatisticActivity extends AppCompatActivity {
    private LineChart lineChart;
    private DatePickerDialog datePickerDialog;
    private RecyclerView statisticRecyclerView;
    private FloatingActionButton btnAdd;
    private ImageView btnBack;
    private Long petId;
    private List<HealthRecord> healthRecords;
    private TextInputEditText startDate, endDate;
    private Button btnStatics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_statistic);
        findViewById();
        petId = getIntent().getLongExtra("petId", 0);
        btnBack.setOnClickListener(v -> finish());
        statisticRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        statisticRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(HealthStatisticActivity.this, AddHealthRecordActivity.class);
            intent.putExtra("petId", petId);
            startActivity(intent);
        });
        getAllHealthRecords();
        customStartDate();
        customEndDate();
        btnStatics.setOnClickListener(v -> statisticHealthRecords());
    }

    private void findViewById() {
        lineChart = findViewById(R.id.line_chart);
        statisticRecyclerView = findViewById(R.id.statistic_recycler_view);
        btnAdd = findViewById(R.id.btn_add);
        btnBack = findViewById(R.id.btn_back);
        startDate = findViewById(R.id.start_date);
        endDate = findViewById(R.id.end_date);
        btnStatics = findViewById(R.id.btn_statics);
    }

    private void statisticHealthRecords() {
        if (validation()) {
            String strStartDate = startDate.getText().toString();
            String strEndDate = endDate.getText().toString();
            try {
                Date dateStartDate = FormatDateUtils.StringToDate1(strStartDate);
                Date dateEndDate = FormatDateUtils.StringToDate1(strEndDate);
                APIService.apiService.staticsHealthRecord(petId, FormatDateUtils.DateToString1(dateStartDate), FormatDateUtils.DateToString1(dateEndDate)).enqueue(new Callback<ListHealthRecordResponse>() {
                    @Override
                    public void onResponse(Call<ListHealthRecordResponse> call, Response<ListHealthRecordResponse> response) {
                        if (response.isSuccessful()) {
                            ListHealthRecordResponse listHealthRecordResponse = response.body();
                            if (listHealthRecordResponse != null && listHealthRecordResponse.getData() != null) {
                                healthRecords = listHealthRecordResponse.getData();
                                HealthStatisticAdapter healthStatisticAdapter = new HealthStatisticAdapter(HealthStatisticActivity.this, healthRecords);
                                statisticRecyclerView.setAdapter(healthStatisticAdapter);
                                if (healthRecords == null || healthRecords.isEmpty()) {
                                    lineChart.setVisibility(View.GONE);
                                } else {
                                    lineChart.setVisibility(View.VISIBLE);
                                }
                                customChart();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ListHealthRecordResponse> call, Throwable t) {

                    }
                });

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void getAllHealthRecords() {
        APIService.apiService.getHealthRecordByPet(petId).enqueue(new Callback<ListHealthRecordResponse>() {
            @Override
            public void onResponse(Call<ListHealthRecordResponse> call, Response<ListHealthRecordResponse> response) {
                if (response.isSuccessful()) {
                    ListHealthRecordResponse healthRecordResponse = response.body();
                    if (healthRecordResponse != null && healthRecordResponse.getData() != null) {
                        healthRecords = healthRecordResponse.getData();
                        HealthStatisticAdapter healthStatisticAdapter = new HealthStatisticAdapter(HealthStatisticActivity.this, healthRecords);
                        statisticRecyclerView.setAdapter(healthStatisticAdapter);
                        if (healthRecords == null || healthRecords.isEmpty()) {
                            lineChart.setVisibility(View.GONE);
                        } else {
                            lineChart.setVisibility(View.VISIBLE);
                        }
                        customChart();
                    }
                }
            }

            @Override
            public void onFailure(Call<ListHealthRecordResponse> call, Throwable t) {

            }
        });
    }

    private void customChart() {
        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        for (int i = 0; i < healthRecords.size(); i++) {
            HealthRecord healthRecord = healthRecords.get(i);
            float x = i;
            float y = healthRecord.getWeight().floatValue();
            entries.add(new Entry(x, y));
            String date = FormatDateUtils.DateToString(healthRecord.getCheckUpDate());
            // Add date string to labels list
            labels.add(date);
        }

        // Tạo DataSet và cấu hình nó
        LineDataSet dataSet = new LineDataSet(entries, "Cân nặng");
        dataSet.setColor(ContextCompat.getColor(HealthStatisticActivity.this, R.color.green));
        dataSet.setValueTextColor(ContextCompat.getColor(HealthStatisticActivity.this, R.color.green));
        dataSet.setLineWidth(1f);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        // Tạo LineData và thêm DataSet vào đó
        LineData lineData = new LineData(dataSet);

        // Cấu hình trục X và trục Y
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(8f);
        xAxis.setGranularity(1f); // Đơn vị giữa các giá trị trên trục X
        xAxis.setValueFormatter(new CustomXAxisValueFormatter(labels)); // Định dạng giá trị trục X
        xAxis.setDrawGridLines(false);
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setGranularity(1f); // Đơn vị giữa các giá trị trên trục Y
        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setEnabled(false);
        yAxis.setDrawGridLines(false);
        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setEnabled(true);

        // Đặt dữ liệu vào biểu đồ
        lineChart.getDescription().setText("");
        lineChart.setData(lineData);
        lineChart.setDragEnabled(true);
        lineChart.setExtraRightOffset(20f);
        lineChart.setPinchZoom(true);
        lineChart.setVisibleXRangeMaximum(5);
        // Cập nhật biểu đồ
        lineChart.invalidate();
    }

    public static class CustomXAxisValueFormatter extends ValueFormatter {

        private final List<String> labels;

        public CustomXAxisValueFormatter(List<String> labels) {
            this.labels = labels;
        }

        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            // Ensure the value is within the index range
            int index = (int) value;
            if (index >= 0 && index < labels.size()) {
                return labels.get(index);
            } else {
                return "";
            }
        }
    }

    private boolean validation() {
        if (startDate.length() == 0) {
            startDate.setError("Ngày bắt đầu không được để trống");
            return false;
        }
        if (endDate.length() == 0) {
            endDate.setError("Ngày kết thúc không được để trống");
            return false;
        }
        return true;
    }

    private void customStartDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        startDate.setOnClickListener(v -> {
            datePickerDialog = new DatePickerDialog(HealthStatisticActivity.this, (view, year1, month1, dayOfMonth) -> {
                String date = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                try {
                    Date startDate1 = FormatDateUtils.StringToDate1(date);
                    boolean check = true;
                    if (endDate.length() != 0) {
                        Date endDate1 = FormatDateUtils.StringToDate1(endDate.getText().toString());
                        if (startDate1.compareTo(endDate1) > 0) {
                            check = false;
                        }
                    }
                    if (check) {
                        String startDate2 = FormatDateUtils.DateToString(startDate1);
                        startDate.setText(startDate2);
                    } else {
                        DialogUtils.setUpDialog(HealthStatisticActivity.this, "Ngày bắt đầu không được lớn hơn ngày kết thúc");
                    }
                    startDate.setError(null);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }, year, month, day);
            datePickerDialog.show();
        });
    }

    private void customEndDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        endDate.setOnClickListener(v -> {
            datePickerDialog = new DatePickerDialog(HealthStatisticActivity.this, (view, year1, month1, dayOfMonth) -> {
                String date = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                try {
                    Date endDate1 = FormatDateUtils.StringToDate1(date);
                    boolean check = true;
                    if (startDate.length() != 0) {
                        Date startDate1 = FormatDateUtils.StringToDate1(startDate.getText().toString());
                        if (endDate1.compareTo(startDate1) < 0) {
                            check = false;
                        }
                    }
                    if (check) {
                        String endDate2 = FormatDateUtils.DateToString(endDate1);
                        endDate.setText(endDate2);
                    } else {
                        DialogUtils.setUpDialog(HealthStatisticActivity.this, "Ngày bắt đầu không được lớn hơn ngày kết thúc");
                    }
                    endDate.setError(null);

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }, year, month, day);
            datePickerDialog.show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        startDate.setText("");
        endDate.setText("");
        getAllHealthRecords();
    }
}