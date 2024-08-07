package com.project.petmanagement.activity.pet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.petmanagement.R;
import com.project.petmanagement.adapters.ManagePetRecyclerViewAdapter;
import com.project.petmanagement.models.entity.Pet;
import com.project.petmanagement.payloads.responses.ListPetResponse;
import com.project.petmanagement.services.APIService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagePetActivity extends AppCompatActivity {
    private RecyclerView petRecyclerView;
    private Button btnAddPet;
    private ImageView btnBack;
    private ImageView btnSearch;
    private EditText search;
    private ManagePetRecyclerViewAdapter managePetRecyclerViewAdapter;
    private List<Pet> pets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_pet);
        findViewById();
        processBtn();
        getPetList();
        search.setVisibility(View.GONE);
        // Tìm pet
        search();
        petRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy <= 0) {
                    btnAddPet.setVisibility(View.VISIBLE);
                } else {
                    btnAddPet.setVisibility(View.GONE);
                }
            }
        });
    }

    private void findViewById() {
        petRecyclerView = findViewById(R.id.pet_manage_recyclerview);
        btnAddPet = findViewById(R.id.btn_add_pet);
        btnBack = findViewById(R.id.btn_back);
        btnSearch = findViewById(R.id.btn_search);
        search = findViewById(R.id.search);
    }

    private void search() {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            // Text change
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (pets != null) {
                    if (s.toString().isEmpty()) {
                        managePetRecyclerViewAdapter.setData(pets);
                        Objects.requireNonNull(petRecyclerView.getAdapter()).notifyDataSetChanged();
                    } else {
                        List<Pet> listPetSearch = new ArrayList<>();
                        for (Pet pet : pets) {
                            if (pet.getFullName().toLowerCase().contains(s.toString().toLowerCase())) {
                                listPetSearch.add(pet);
                                managePetRecyclerViewAdapter.setData(listPetSearch);
                                Objects.requireNonNull(petRecyclerView.getAdapter()).notifyDataSetChanged();
                            }
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void processBtn() {
        btnAddPet.setOnClickListener(v -> {
            Intent intent = new Intent(ManagePetActivity.this, AddNewPetActivity.class);
            startActivity(intent);
        });
        btnBack.setOnClickListener(v -> finish());
        btnSearch.setOnClickListener(v -> {
            if (search.getVisibility() == View.GONE) {
                search.setVisibility(View.VISIBLE);
            } else {
                search.setVisibility(View.GONE);
            }
        });

    }

    private void getPetList() {
        APIService.apiService.getAllPetUser().enqueue(new Callback<ListPetResponse>() {
            @Override
            public void onResponse(Call<ListPetResponse> call, Response<ListPetResponse> response) {
                if (response.isSuccessful()) {
                    ListPetResponse petResponse = response.body();
                    if (petResponse != null) {
                        pets = petResponse.getData();
                        managePetRecyclerViewAdapter = new ManagePetRecyclerViewAdapter(ManagePetActivity.this, pets);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ManagePetActivity.this, RecyclerView.VERTICAL, false);
                        petRecyclerView.setLayoutManager(linearLayoutManager);
                        petRecyclerView.setAdapter(managePetRecyclerViewAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ListPetResponse> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPetList();
    }
}
