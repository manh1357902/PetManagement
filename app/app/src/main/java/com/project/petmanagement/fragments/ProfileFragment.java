package com.project.petmanagement.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.project.petmanagement.MyApplication;
import com.project.petmanagement.R;
import com.project.petmanagement.activity.login.LoginActivity;
import com.project.petmanagement.activity.nutrition.NutritiousFoodActivity;
import com.project.petmanagement.activity.pet.ManagePetActivity;
import com.project.petmanagement.activity.shop.ShopActivity;
import com.project.petmanagement.activity.user.ChangeUserInfoActivity;
import com.project.petmanagement.activity.user.ChangePasswordActivity;
import com.project.petmanagement.activity.veterinarian.VetsActivity;
import com.project.petmanagement.models.entity.User;
import com.project.petmanagement.payloads.requests.FCMToken;
import com.project.petmanagement.payloads.requests.UserRequest;
import com.project.petmanagement.payloads.responses.Response;
import com.project.petmanagement.payloads.responses.UserResponse;
import com.project.petmanagement.services.APIService;
import com.project.petmanagement.services.StorageService;
import com.project.petmanagement.utils.FormatDateUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import retrofit2.Call;
import retrofit2.Callback;

public class ProfileFragment extends Fragment {
    private final StorageService storageService = MyApplication.getStorageService();
    private ImageView images;
    private String imageUrl;
    private Bitmap imageBitmap;
    private final static int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private final static int READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 101;
    private final static int WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 102;
    private Button btnLogout, btnLogin;
    private TextView fullName;
    private TextView phoneNumber;
    private ImageView openCamera;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if (o.getResultCode() == Activity.RESULT_OK) {
                Bundle bundle = o.getData().getExtras();
                if (bundle != null) {
                    imageBitmap = (Bitmap) bundle.get("data");
                    images.setImageBitmap(imageBitmap);
                    saveImage();
                    updateUser();
                }
            }
        }
    });
    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if (o.getResultCode() == Activity.RESULT_OK) {
                Uri uri = null;
                if (o.getData() != null) {
                    uri = o.getData().getData();
                }
                try {
                    imageBitmap = BitmapFactory.decodeStream(requireActivity().getContentResolver().openInputStream(uri));
                    images.setImageBitmap(imageBitmap);
                    saveImage();
                    updateUser();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    });
    public void updateUser(){
        User user = storageService.getUser("user");
        UserRequest userRequest = new UserRequest();
        userRequest.setAddress(user.getAddress());
        userRequest.setAvatar(imageUrl);
        userRequest.setFullName(user.getFullName());
        userRequest.setDateOfBirth(FormatDateUtils.DateToString1(user.getDateOfBirth()));
        userRequest.setEmail(user.getEmail());
        APIService.apiService.updateUser(userRequest).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, retrofit2.Response<UserResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(requireActivity(), "Cập nhập avatar thành công", Toast.LENGTH_SHORT).show();
                    storageService.setUser("user", response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnLogin = view.findViewById(R.id.logout_btn);
        CardView pet = view.findViewById(R.id.pet_card_view);
        CardView nutrition = view.findViewById(R.id.nutrition_card_view);
        CardView vet = view.findViewById(R.id.veterinarian);
        CardView shop = view.findViewById(R.id.shop);
        CardView changePassword = view.findViewById(R.id.change_password_card_view);
        CardView deleteAccount = view.findViewById(R.id.delete_account_card_view);
        ImageView updateProfile = view.findViewById(R.id.update_profile);
        btnLogout = view.findViewById(R.id.btn_logout);
        fullName = view.findViewById(R.id.full_name);
        phoneNumber = view.findViewById(R.id.phone_number);
        images = view.findViewById(R.id.avatar);
        openCamera = view.findViewById(R.id.camera);
        openCamera.setVisibility(View.GONE);
        User user = storageService.getUser("user");
        if (user != null) {
            openCamera.setVisibility(View.VISIBLE);
            fullName.setText(user.getFullName());
            String phone = "+84 " + user.getPhoneNumber().substring(1, 4) + " xxx xxx";
            phoneNumber.setText(phone);
            if(user.getAvatar()!=null){
                images.setImageURI(Uri.parse(user.getAvatar()));
            }else{
                images.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.gray_pet_image));
            }
            btnLogin.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);
        } else {
            btnLogin.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.GONE);
            openCamera.setVisibility(View.GONE);
        }
//        btnLogin.setOnClickListener(v -> {
//            Intent intent = new Intent(getContext(), LoginActivity.class);
//            startActivity(intent);
//        });
        nutrition.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), NutritiousFoodActivity.class);
            startActivity(intent);
        });
        vet.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), VetsActivity.class);
            startActivity(intent);
        });
        shop.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ShopActivity.class);
            startActivity(intent);
        });
        pet.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ManagePetActivity.class);
            startActivity(intent);
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChangeUserInfoActivity.class);
                startActivity(intent);
            }
        });
        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireContext());
                alertDialog.setTitle("Thông báo")
                        .setMessage("Bạn có chắc chắn muốn xóa tài khoản")
                        .setPositiveButton("Có", (dialog, which) -> {
                            APIService.apiService.deleteUser().enqueue(new Callback<Response>() {
                                @Override
                                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                                    if (response.isSuccessful()) {
                                        storageService.remove("user");
                                        storageService.remove("token");
                                        Intent intent = new Intent(requireContext(), LoginActivity.class);
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onFailure(Call<Response> call, Throwable t) {
                                    Toast.makeText(requireContext(), "Xóa tài khoản thất bại.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        })
                        .setNegativeButton("Không", (dialog, which) -> dialog.cancel())
                        .show();
            }
        });
        openCamera.setOnClickListener(v -> setOpenCameraDialog());
        btnLogout.setOnClickListener(v -> {
            storageService.remove("user");
            storageService.remove("token");
            FCMToken fcmToken = new FCMToken("");
            APIService.apiService.setFcmToken(fcmToken).enqueue(new Callback<com.project.petmanagement.payloads.responses.Response>() {
                @Override
                public void onResponse(retrofit2.Call<com.project.petmanagement.payloads.responses.Response> call1, retrofit2.Response<Response> response1) {
                    if (response1.isSuccessful()) {

                    }
                }

                @Override
                public void onFailure(Call<Response> call1, Throwable t) {
                    Toast.makeText(requireActivity(), "set token is failed.", Toast.LENGTH_SHORT).show();
                }
            });
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            startActivity(intent);
        });
    }
    public void setOpenCameraDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireActivity());
        alertDialog.setTitle("Chọn")
                .setPositiveButton("Gallery", (dialog, which) -> {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
                        } else {
                            startGallery();
                        }
                    } else {
                        startGallery();
                    }

                })
                .setNegativeButton("Camera", (dialog, which) -> {
                    if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
                    } else {
                        startCamera();
                    }
                })
                .setNeutralButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }
    public void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(intent);
    }

    public void startGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                Toast.makeText(requireActivity(), "Camera permission denied.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startGallery();
            } else {
                Toast.makeText(requireActivity(), "Gallery permission denied.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startGallery();
            } else {
                Toast.makeText(requireActivity(), "Gallery permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveImage() {
        File dir = new File(requireActivity().getFilesDir(), "user_image");
        if (!dir.exists()) {
            dir.mkdir();
        }
        if (imageBitmap != null) {
            File file = new File(dir, "user_" + System.currentTimeMillis() + ".jpg");
            OutputStream outputStream;
            try {
                outputStream = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 20, outputStream);
            imageUrl = file.getAbsolutePath();
            try {
                outputStream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        User user = storageService.getUser("user");
        if (user != null) {
            openCamera.setVisibility(View.VISIBLE);
            fullName.setText(user.getFullName());
            String phone = "+84 " + user.getPhoneNumber().substring(1, 4) + " xxx xxx";
            phoneNumber.setText(phone);
            if(user.getAvatar()!=null){
                images.setImageURI(Uri.parse(user.getAvatar()));
            }else{
                images.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.gray_pet_image));
            }
            btnLogin.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);
        } else {
            btnLogin.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.GONE);
            openCamera.setVisibility(View.GONE);
        }
    }
}