package com.project.petmanagement.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.petmanagement.R;
import com.project.petmanagement.activity.medical.MedicalDocumentsActivity;
import com.project.petmanagement.activity.healthStatistic.HealthStatisticActivity;
import com.project.petmanagement.models.entity.Pet;
import com.project.petmanagement.payloads.responses.PetResponse;
import com.project.petmanagement.services.APIService;
import com.project.petmanagement.utils.FormatDateUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PetInfoFragment extends Fragment {
    private long idPet;
    private ImageView gender;
    private TextView age;
    private TextView seeMoreStatic, seeMoreMedical;
    private final FloatingActionButton btnAdd;
    private Button deletePetBtn;
    private Pet pet;

    public PetInfoFragment(long idPet, FloatingActionButton btnAdd) {
        this.idPet = idPet;
        this.btnAdd = btnAdd;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pet_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewById(view);
        getPet();
        seeMoreStatic.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), HealthStatisticActivity.class);
            intent.putExtra("petId", idPet);
            startActivity(intent);
        });
        seeMoreMedical.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), MedicalDocumentsActivity.class);
            intent.putExtra("petId", idPet);
            startActivity(intent);
        });
        deletePetBtn.setOnClickListener(v -> {
            deletePet();
        });
    }

    private void getPet() {
        APIService.apiService.getPetDetail(idPet).enqueue(new Callback<PetResponse>() {
            @Override
            public void onResponse(Call<PetResponse> call, Response<PetResponse> response) {
                if (response.isSuccessful()) {
                    PetResponse petResponse = response.body();
                    if (petResponse != null) {
                        pet = petResponse.getData();
                        if (pet.getGender() == 0) {
                            gender.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.baseline_female_24));
                        } else {
                            gender.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.baseline_male_24));
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            age.setText(FormatDateUtils.calculate(pet.getDateOfBirth()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PetResponse> call, Throwable t) {
            }
        });
    }

    private void deletePet(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireContext());
        alertDialog.setTitle("Xóa thú cưng")
                .setMessage("Bạn có chắc chắn muốn xóa thú cưng này?")
                .setPositiveButton("Có", (dialog, which) -> {
                        APIService.apiService.deletePet(idPet).enqueue(new Callback<PetResponse>() {
                            @Override
                            public void onResponse(Call<PetResponse> call, Response<PetResponse> response) {
                                if (response.isSuccessful()) {
                                    if (response.body() != null && response.body().getData() != null) {
                                        Toast.makeText(requireContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                                        ((Activity) requireContext()).finish();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<PetResponse> call, Throwable t) {

                            }
                        });
                })
                .setNegativeButton("Không", (dialog, which) -> dialog.cancel())
                .show();
    }

    private void findViewById(View view) {
        seeMoreStatic = view.findViewById(R.id.see_more_static);
        seeMoreMedical = view.findViewById(R.id.see_more_medical);
        gender = view.findViewById(R.id.gender);
        age = view.findViewById(R.id.age);
        deletePetBtn = view.findViewById(R.id.delete_pet_btn);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPet();
        btnAdd.setVisibility(View.GONE);
    }

}