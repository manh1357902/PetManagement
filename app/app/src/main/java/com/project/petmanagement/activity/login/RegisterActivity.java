package com.project.petmanagement.activity.login;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.project.petmanagement.MyApplication;
import com.project.petmanagement.R;
import com.project.petmanagement.activity.MainActivity;
import com.project.petmanagement.models.entity.User;
import com.project.petmanagement.payloads.requests.FCMToken;
import com.project.petmanagement.payloads.requests.RegisterRequest;
import com.project.petmanagement.payloads.responses.LoginResponse;
import com.project.petmanagement.payloads.responses.RegisterErrorResponse;
import com.project.petmanagement.services.APIService;
import com.project.petmanagement.services.StorageService;
import com.project.petmanagement.utils.DialogUtils;
import com.project.petmanagement.utils.FormatDateUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private TextView textLogin;
    private TextInputEditText fullName, dob, phoneNumber, password, rePassword, email, address;
    private Button btnSignup;
    private DatePickerDialog datePickerDialog;
    private final StorageService storageService = MyApplication.getStorageService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViewById();
        changeLogin();
        customDob();
        btnSignup.setOnClickListener(v -> signUp());
    }

    private void findViewById() {
        textLogin = findViewById(R.id.text_login);
        btnSignup = findViewById(R.id.btn_signup);
        fullName = findViewById(R.id.full_name);
        dob = findViewById(R.id.dob);
        phoneNumber = findViewById(R.id.phone_number);
        password = findViewById(R.id.password);
        rePassword = findViewById(R.id.confirm_password);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
    }

    private void signUp() {
        if (validation()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            if (!password.getText().toString().equals(rePassword.getText().toString())) {
                DialogUtils.setUpDialog(RegisterActivity.this, "Mật khẩu xác nhận không đúng.");
            } else {
                try {
                    SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    Date dob2 = sdf1.parse(dob.getText().toString());
                    String dob3 = sdf.format(dob2);
                    RegisterRequest registerRequest = new RegisterRequest(fullName.getText().toString().trim(), dob3, phoneNumber.getText().toString().trim(), email.getText().toString().trim(), address.getText().toString(), password.getText().toString());
                    APIService.apiService.signup(registerRequest).enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.code() == 200) {
                                LoginResponse loginResponse = response.body();
                                storageService.setString("token", loginResponse.getToken());
                                User user = loginResponse.getData();
                                storageService.setUser("user", user);
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                                FirebaseMessaging.getInstance().getToken()
                                        .addOnCompleteListener(task -> {
                                            String token = task.getResult();
                                            FCMToken fcmToken = new FCMToken(token);
                                            APIService.apiService.setFcmToken(fcmToken).enqueue(new Callback<com.project.petmanagement.payloads.responses.Response>() {
                                                @Override
                                                public void onResponse(Call<com.project.petmanagement.payloads.responses.Response> call1, Response<com.project.petmanagement.payloads.responses.Response> response1) {
                                                    if (response1.isSuccessful()) {
                                                        Toast.makeText(RegisterActivity.this, "set token is successful.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<com.project.petmanagement.payloads.responses.Response> call1, Throwable t) {
                                                    Toast.makeText(RegisterActivity.this, "set token is failed.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        });
                            } else if (response.code() == 400) {
                                Gson gson = new Gson();
                                RegisterErrorResponse registerErrorResponse = null;
                                try {
                                    registerErrorResponse = gson.fromJson(response.errorBody().string(), RegisterErrorResponse.class);
                                    String message = "";
                                    if (registerErrorResponse.getMessage().getEmailError() != null) {
                                        message += registerErrorResponse.getMessage().getEmailError() + "\n";
                                    }
                                    if (registerErrorResponse.getMessage().getPasswordError() != null) {
                                        message += registerErrorResponse.getMessage().getPasswordError() + "\n";
                                    }
                                    if (registerErrorResponse.getMessage().getFullNameError() != null) {
                                        message += registerErrorResponse.getMessage().getFullNameError() + "\n";
                                    }
                                    if (registerErrorResponse.getMessage().getPhoneNumberError() != null) {
                                        message += registerErrorResponse.getMessage().getPhoneNumberError() + "\n";
                                    }
                                    DialogUtils.setUpDialog(RegisterActivity.this, message);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Toast.makeText(RegisterActivity.this, "Kiểm tra lại kết nối mạng.", Toast.LENGTH_SHORT).show();
                            Log.d("dddd", t.getMessage());
                        }
                    });
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    private boolean validation() {
        if (fullName.length() == 0) {
            fullName.setError("Tên không được để trống");
            return false;
        }
        if (dob.length() == 0) {
            dob.setError("Ngày sinh không được để trống.");
            return false;
        }
        if (phoneNumber.length() == 0) {
            phoneNumber.setError("Số điện thoại không được để trống.");
            return false;
        }
        if (password.length() == 0) {
            password.setError("Mật khẩu không được để trống.");
            return false;
        }

        if (rePassword.length() == 0) {
            rePassword.setError("Xác nhận mật khẩu không dược để trống.");
            return false;
        }
        if (email.length() == 0) {
            email.setError("Email không được để trống.");
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
                datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                                DialogUtils.setUpDialog(RegisterActivity.this, "Ngày bạn chọn không được lớn hơn ngày hiện tại");
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

    private void changeLogin() {
        textLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}