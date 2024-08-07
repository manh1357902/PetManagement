package com.project.petmanagement.activity.medical;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.petmanagement.R;
import com.project.petmanagement.adapters.MedicalAdapter;
import com.project.petmanagement.models.entity.MedicalDocument;
import com.project.petmanagement.payloads.responses.ListMedicalResponse;
import com.project.petmanagement.payloads.responses.MedicalDocumentResponse;
import com.project.petmanagement.services.APIService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicalDocumentsActivity extends AppCompatActivity {
    private Dialog dialog;
    private LinearLayout empty;
    private RecyclerView medicalRecyclerview;
    private TextView nameFile;
    private File document;
    private EditText editTitleMedical;
    private EditText editNoteMedical;
    private List<MedicalDocument> medicalDocuments;
    private Long idPet;
    private MedicalAdapter medicalAdapter;
    private final ActivityResultLauncher<Intent> chooseFileLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if (o.getResultCode() == Activity.RESULT_OK) {
                Intent data = o.getData();
                if (data != null) {
                    try {
                        Uri uri = data.getData();
                        if (uri != null) {
                            FileInputStream inputStream = (FileInputStream) getContentResolver().openInputStream(uri);
                            if (inputStream != null) {
                                document = File.createTempFile("document", ".pdf");
                                nameFile.setText(document.getName());
                                FileOutputStream fos = new FileOutputStream(document);
                                byte[] buffer = new byte[1024];
                                int length;
                                while ((length = inputStream.read(buffer)) > 0) {
                                    fos.write(buffer, 0, length);
                                }
                                fos.flush();
                                fos.close();
                                inputStream.close();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace(); // Xử lý ngoại lệ một cách thích hợp
                    }
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_documents);
        ImageView btnAdd = findViewById(R.id.add);
        ImageView btnBack = findViewById(R.id.btn_back);
        empty = findViewById(R.id.linear_empty);
        medicalRecyclerview = findViewById(R.id.document_recycler_view);
        idPet = getIntent().getLongExtra("petId", 0);
        getListDocument();
        btnBack.setOnClickListener(v -> finish());
        btnAdd.setOnClickListener(v -> openAddDialog(Gravity.CENTER));
    }

    private void checkEmpty() {
        if (medicalDocuments == null || medicalDocuments.size() == 0) {
            empty.setVisibility(View.VISIBLE);
            medicalRecyclerview.setVisibility(View.GONE);
        } else {
            empty.setVisibility(View.GONE);
            medicalRecyclerview.setVisibility(View.VISIBLE);
        }
    }

    private void openAddDialog(int gravity) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_dialog);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = Gravity.CENTER;
        window.setAttributes(windowAttribute);
        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(false);
        } else {
            dialog.setCancelable(true);
        }
        editTitleMedical = dialog.findViewById(R.id.name_medical);
        editNoteMedical = dialog.findViewById(R.id.note);
        TextView btnAccept = dialog.findViewById(R.id.btn_accept);
        nameFile = dialog.findViewById(R.id.name_file);
        ImageView chooseFile = dialog.findViewById(R.id.choose_file);
        chooseFile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("application/pdf");
            intent = Intent.createChooser(intent, "Chọn 1 file");
            chooseFileLauncher.launch(intent);
        });
        btnAccept.setOnClickListener(v -> {
            RequestBody title = RequestBody.create(MediaType.parse("text/plain"), editTitleMedical.getText().toString());
            RequestBody note = RequestBody.create(MediaType.parse("text/plain"), editNoteMedical.getText().toString());
            RequestBody petId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(idPet));
            if (document != null) {
                RequestBody requestFile = RequestBody.create(MediaType.parse("*/*"), document);
                MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", document.getName(), requestFile);
                if (validate()) {
                    APIService.apiService.addMedicalDocument(title, note, petId, filePart).enqueue(new Callback<MedicalDocumentResponse>() {
                        @Override
                        public void onResponse(Call<MedicalDocumentResponse> call, Response<MedicalDocumentResponse> response) {
                            if (response.isSuccessful()) {
                                dialog.cancel();
                                Toast.makeText(MedicalDocumentsActivity.this, "Thêm hồ sơ thành công", Toast.LENGTH_SHORT).show();
                                getListDocument();
                            }
                        }

                        @Override
                        public void onFailure(Call<MedicalDocumentResponse> call, Throwable t) {
                            Toast.makeText(MedicalDocumentsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        dialog.show();
    }

    private void getListDocument() {
        if (idPet != null) {
            APIService.apiService.getMedicalDocumentByPet(idPet).enqueue(new Callback<ListMedicalResponse>() {
                @Override
                public void onResponse(Call<ListMedicalResponse> call, Response<ListMedicalResponse> response) {
                    if (response.isSuccessful()) {
                        ListMedicalResponse medicalResponse = response.body();
                        if (medicalResponse != null && medicalResponse.getData() != null) {
                            medicalDocuments = medicalResponse.getData();
                            medicalAdapter = new MedicalAdapter(MedicalDocumentsActivity.this, medicalDocuments);
                            medicalRecyclerview.setAdapter(medicalAdapter);
                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MedicalDocumentsActivity.this, 3);
                            medicalRecyclerview.setLayoutManager(layoutManager);
                            checkEmpty();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ListMedicalResponse> call, Throwable t) {
                    Toast.makeText(MedicalDocumentsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean validate() {
        if (editTitleMedical.length() == 0) {
            editTitleMedical.setError("Title không được để trống");
            return false;
        }
        return true;
    }
}