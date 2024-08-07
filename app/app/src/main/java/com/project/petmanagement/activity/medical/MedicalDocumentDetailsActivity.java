package com.project.petmanagement.activity.medical;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.project.petmanagement.R;
import com.project.petmanagement.models.entity.MedicalDocument;
import com.project.petmanagement.payloads.responses.MedicalDocumentResponse;
import com.project.petmanagement.services.APIService;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicalDocumentDetailsActivity extends AppCompatActivity {
    private TextView titleDocument;
    private TextView noteDocument;
    private Dialog dialog;
    private Long medicalId;
    private MedicalDocument medicalDocument;
    private String url;
    private EditText editTitleMedical;
    private EditText editNoteMedical;
    private TextView nameFile;
    private File document;
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
        setContentView(R.layout.activity_medical_document_details);
        titleDocument = findViewById(R.id.title_document);
        noteDocument = findViewById(R.id.note);
        ImageView openFile = findViewById(R.id.file_open);
        ImageView btnBack = findViewById(R.id.btn_back);
        ImageView btnUpdate = findViewById(R.id.btn_update);
        btnBack.setOnClickListener(v -> finish());
        medicalId = getIntent().getLongExtra("medicalId", 0);
        getMedicalDocument();
        openFile.setOnClickListener(v -> {
            Intent intent = new Intent(MedicalDocumentDetailsActivity.this, ViewPDFActivity.class);
            intent.putExtra("url", url);
            startActivity(intent);
        });
        btnUpdate.setOnClickListener(v -> {
            openUpdateDialog(Gravity.CENTER);
        });

    }

    private void openUpdateDialog(int gravity) {
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
        editTitleMedical.setText(medicalDocument.getTitle());
        editNoteMedical.setText(medicalDocument.getNote());
        TextView btnAccept = dialog.findViewById(R.id.btn_accept);
        nameFile = dialog.findViewById(R.id.name_file);
        ImageView chooseFile = dialog.findViewById(R.id.choose_file);
        new RetrivePdfStream().execute(medicalDocument.getUrl());
        chooseFile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("application/pdf");
            intent = Intent.createChooser(intent, "Chọn 1 file");
            chooseFileLauncher.launch(intent);
        });
        btnAccept.setOnClickListener(v -> {
            RequestBody title = RequestBody.create(MediaType.parse("text/plain"), editTitleMedical.getText().toString());
            RequestBody note = RequestBody.create(MediaType.parse("text/plain"), editNoteMedical.getText().toString());
            RequestBody petId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(medicalDocument.getPet().getId()));
            if (document != null) {
                RequestBody requestFile = RequestBody.create(MediaType.parse("*/*"), document);
                MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", document.getName(), requestFile);
                if (validate()) {
                    APIService.apiService.updateMedicalDocument(medicalDocument.getId(), title, note, petId, filePart).enqueue(new Callback<MedicalDocumentResponse>() {
                        @Override
                        public void onResponse(Call<MedicalDocumentResponse> call, Response<MedicalDocumentResponse> response) {
                            if (response.isSuccessful()) {
                                dialog.cancel();
                                Toast.makeText(MedicalDocumentDetailsActivity.this, "Cập hồ sơ thành công", Toast.LENGTH_SHORT).show();
                                getMedicalDocument();
                            }
                        }

                        @Override
                        public void onFailure(Call<MedicalDocumentResponse> call, Throwable t) {
                            Toast.makeText(MedicalDocumentDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        dialog.show();
    }

    private void getMedicalDocument() {
        APIService.apiService.getMedicalDocumentByid(medicalId).enqueue(new Callback<MedicalDocumentResponse>() {
            @Override
            public void onResponse(Call<MedicalDocumentResponse> call, Response<MedicalDocumentResponse> response) {
                if (response.isSuccessful()) {
                    MedicalDocumentResponse medicalDocumentResponse = response.body();
                    if (medicalDocumentResponse != null && medicalDocumentResponse.getData() != null) {
                        medicalDocument = medicalDocumentResponse.getData();
                        titleDocument.setText(medicalDocument.getTitle());
                        noteDocument.setText(medicalDocument.getNote());
                        url = medicalDocument.getUrl();
                    }
                }
            }

            @Override
            public void onFailure(Call<MedicalDocumentResponse> call, Throwable t) {

            }
        });
    }

    private boolean validate() {
        if (editTitleMedical.length() == 0) {
            editTitleMedical.setError("Title không được để trống");
            return false;
        }
        return true;
    }

    class RetrivePdfStream extends AsyncTask<String, Void, File> {
        @Override
        protected File doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    File lastDocument = File.createTempFile("document", ".pdf");
                    FileOutputStream outputStream = new FileOutputStream(lastDocument);
                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, length);
                    }
                    outputStream.close();
                    inputStream.close();
                    return lastDocument;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        // Here load the pdf and dismiss the dialog box
        protected void onPostExecute(File lastDocument) {
            if (lastDocument != null) {
                document = lastDocument;
                nameFile.setText(document.getName());
            } else {
                Toast.makeText(MedicalDocumentDetailsActivity.this, "Lỗi khi tải xuống và lưu file", Toast.LENGTH_SHORT).show();
            }
        }
    }
}