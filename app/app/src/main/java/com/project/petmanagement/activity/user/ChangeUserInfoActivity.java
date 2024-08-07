package com.project.petmanagement.activity.user;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.project.petmanagement.MyApplication;
import com.project.petmanagement.R;
import com.project.petmanagement.models.entity.User;
import com.project.petmanagement.payloads.requests.UserRequest;
import com.project.petmanagement.payloads.responses.UserResponse;
import com.project.petmanagement.services.APIService;
import com.project.petmanagement.services.StorageService;
import com.project.petmanagement.utils.DialogUtils;
import com.project.petmanagement.utils.FormatDateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeUserInfoActivity extends AppCompatActivity {
    private TextInputEditText fullName, dob, email, address;
    private  DatePickerDialog datePickerDialog;
    private final StorageService storageService = MyApplication.getStorageService();
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_info);
        ImageView arrowReturn = findViewById(R.id.btn_back);
        fullName = findViewById(R.id.full_name);
        dob = findViewById(R.id.dob);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        Button btnConfirm = findViewById(R.id.btn_confirm);
        user = storageService.getUser("user");
        if (user != null) {
            fullName.setText(user.getFullName());
            dob.setText(FormatDateUtils.DateToString(user.getDateOfBirth()));
            email.setText(user.getEmail());
            address.setText(user.getAddress());
        }
        customDob();
        arrowReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation()){
                    UserRequest userRequest = new UserRequest();
                    userRequest.setFullName(fullName.getText().toString().trim());
                    userRequest.setEmail(email.getText().toString().trim());
                    userRequest.setAvatar(address.getText().toString().trim());
                    userRequest.setAvatar(user.getAvatar());
                    try {
                        Date date1 = FormatDateUtils.StringToDate1(dob.getText().toString().trim());
                        String date2 = FormatDateUtils.DateToString1(date1);
                        userRequest.setDateOfBirth(date2);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    APIService.apiService.updateUser(userRequest).enqueue(new Callback<UserResponse>() {
                        @Override
                        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                            if(response.isSuccessful()){
                                UserResponse userResponse = response.body();
                                if (userResponse!=null){
                                    User user1 = userResponse.getData();
                                    storageService.setUser("user", user1);
                                    Toast.makeText(ChangeUserInfoActivity.this, "Cập nhập thành công", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                            Toast.makeText(ChangeUserInfoActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    public boolean isValidEmail(CharSequence target) {
        return (target != null && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    private boolean validation(){
        if(fullName.length()==0){
            fullName.setError("Tên không được để trống");
            return false;
        }
        if(dob.length()==0){
            dob.setError("Ngày sinh không được để trống");
            return false;
        }
        if(email.length()==0){
            email.setError("Email không được để trống");
            return false;
        }
        if(!isValidEmail(email.getText().toString().trim())){
            email.setError("Email không đúng định dạng");
            return false;
        }
        return true;
    }
    private void customDob() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(ChangeUserInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
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
                                DialogUtils.setUpDialog(ChangeUserInfoActivity.this, "Ngày bạn chọn không được lớn hơn ngày hiện tại");
                            } else {
                                dob.setText(date2);
                                dob.setError(null);
                            }
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
    }
}