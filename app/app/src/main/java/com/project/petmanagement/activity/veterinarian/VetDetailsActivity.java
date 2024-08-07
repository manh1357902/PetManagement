package com.project.petmanagement.activity.veterinarian;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.project.petmanagement.R;
import com.project.petmanagement.models.entity.Vet;
import com.project.petmanagement.utils.ImageUtils;

public class VetDetailsActivity extends AppCompatActivity {
    private final int REQUEST_CALL_PHONE_PERMISSION = 123;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet_details);
        ImageView btnBack = findViewById(R.id.btn_back);
        ImageView btnShare = findViewById(R.id.btn_share);
        ImageView avatar = findViewById(R.id.vet_avatar);
        RelativeLayout btnCallVeterinarian = findViewById(R.id.btn_call_veterinarian);
        TextView textNameVet = findViewById(R.id.vet_name);
        TextView textWorkAt = findViewById(R.id.work_at);
        TextView textClinicAddress = findViewById(R.id.clinic_address);
        TextView textExperience = findViewById(R.id.experience);
        TextView textDescription = findViewById(R.id.description);
        TextView textPhoneNumber = findViewById(R.id.phone_number);
        TextView textEmail = findViewById(R.id.email);
        Vet vet = (Vet) getIntent().getSerializableExtra("vet");
        if (vet != null) {
            phoneNumber = vet.getPhoneNumber();
            avatar.setImageBitmap(ImageUtils.decodeBase64(vet.getAvatar()));
            textNameVet.setText(vet.getFullName());
            textWorkAt.setText(vet.getWorkAt());
            textClinicAddress.setText(vet.getClinicAddress());
            textExperience.setText(String.valueOf(vet.getExperience()));
            textDescription.setText(vet.getDescription());
            textPhoneNumber.setText(vet.getPhoneNumber());
            textEmail.setText(vet.getEmail());
        }
        btnBack.setOnClickListener(v -> finish());
        btnCallVeterinarian.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(VetDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(VetDetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE_PERMISSION);
            } else {
                call();
            }

        });
    }

    private void call() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_PHONE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                call();
            } else {
                Toast.makeText(this, "Quyền điện thoại bị từ chối", Toast.LENGTH_SHORT).show();
            }
        }
    }
}