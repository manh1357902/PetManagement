package com.project.petmanagement.activity.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.project.petmanagement.R;

import java.util.Objects;

public class ChangeOrderInfoActivity extends AppCompatActivity {
    private TextInputEditText inputPhoneNumber, inputAddress;
    private Button btnConfirm;
    private ImageView btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_order_info);
        inputPhoneNumber = findViewById(R.id.input_phone_number);
        inputAddress = findViewById(R.id.input_address);
        btnBack = findViewById(R.id.btn_back);
        btnConfirm = findViewById(R.id.btn_confirm);
        String phoneNumber = getIntent().getStringExtra("phoneNumber");
        String address = getIntent().getStringExtra("address");
        inputAddress.setText(address);
        inputPhoneNumber.setText(phoneNumber);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    Intent intent = new Intent(ChangeOrderInfoActivity.this, PaymentActivity.class);
                    intent.putExtra("phoneNumber", Objects.requireNonNull(inputPhoneNumber.getText()).toString());
                    intent.putExtra("address", Objects.requireNonNull(inputAddress.getText()).toString());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean validate() {
        if (inputPhoneNumber.length() == 0) {
            inputPhoneNumber.setError("Số điện thoại không được để trống");
            return false;
        }
        if (inputAddress.length() == 0) {
            inputAddress.setError("Địa chỉ không được để trống");
            return false;
        }
        return true;
    }
}