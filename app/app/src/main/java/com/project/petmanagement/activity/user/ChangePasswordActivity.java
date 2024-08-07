package com.project.petmanagement.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.project.petmanagement.R;
import com.project.petmanagement.payloads.requests.ChangePasswordRequest;
import com.project.petmanagement.payloads.responses.Response;
import com.project.petmanagement.services.APIService;

import retrofit2.Call;
import retrofit2.Callback;

public class ChangePasswordActivity extends AppCompatActivity {
    private TextInputEditText inputOldPassword, inputNewPassword, inputRetypeNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ImageView returnArrow = findViewById(R.id.return_arrow);
        returnArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        inputOldPassword = findViewById(R.id.input_old_password);
        inputNewPassword = findViewById(R.id.input_new_password);
        inputRetypeNewPassword = findViewById(R.id.input_retype_new_password);
        Button confirmBtn = findViewById(R.id.btn_confirm);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = String.valueOf(inputOldPassword.getText());
                String newPassword = String.valueOf(inputNewPassword.getText());
                String retypeNewPassword = String.valueOf(inputRetypeNewPassword.getText());
                if (isValid(oldPassword, newPassword, retypeNewPassword)) {
                    ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
                    changePasswordRequest.setOldPassword(oldPassword);
                    changePasswordRequest.setNewPassword(newPassword);
                    changePasswordRequest.setRetypeNewPassword(retypeNewPassword);
                    APIService.apiService.changePassword(changePasswordRequest).enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                            if(response.isSuccessful()){
                                Toast.makeText(ChangePasswordActivity.this, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<Response> call, Throwable t) {
                            Toast.makeText(ChangePasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private boolean isValid(String oldPassword, String newPassword, String retypeNewPassword) {
        if (inputOldPassword.length() == 0) {
            inputOldPassword.setError("Mật khẩu cũ trống.");
            return false;
        }
        if (inputNewPassword.length() == 0) {
            inputNewPassword.setError("Mật khẩu mới trống");
            return false;
        } else if (inputNewPassword.length() < 8) {
            inputNewPassword.setError("Mật khẩu mới phải có ít nhất 08 kí tự");
            return false;
        }
        if (inputRetypeNewPassword.length() == 0) {
            inputRetypeNewPassword.setError("Mật khẩu nhập lại trống");
            return false;
        }
        if (oldPassword.equals(newPassword)) {
            inputNewPassword.setError("Mật khẩu mới trùng với mật khẩu cũ");
            return false;
        }
        if (!newPassword.equals(retypeNewPassword)) {
            inputRetypeNewPassword.setError("Mật khẩu nhập lại sai");
            return false;
        }
        return true;
    }
}