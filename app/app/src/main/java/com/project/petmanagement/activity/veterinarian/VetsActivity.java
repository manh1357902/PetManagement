package com.project.petmanagement.activity.veterinarian;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.petmanagement.R;
import com.project.petmanagement.adapters.ListVetAdapter;
import com.project.petmanagement.models.entity.Vet;
import com.project.petmanagement.payloads.responses.ListVetResponse;
import com.project.petmanagement.services.APIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VetsActivity extends AppCompatActivity {
    private RecyclerView listVeterinarian;
    private EditText search;
    private ListVetAdapter vetAdapter;
    private List<Vet> vetList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vets);
        listVeterinarian = findViewById(R.id.list_veterinarian);
        ImageView btnBack = findViewById(R.id.btn_back);
        ImageView btnSearch = findViewById(R.id.btn_search);
        search = findViewById(R.id.search);
        search.setVisibility(View.GONE);
        getAllVet();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSearch.setOnClickListener(v -> {
            if (search.getVisibility() == View.GONE) {
                search.setVisibility(View.VISIBLE);
            } else {
                search.setVisibility(View.GONE);
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString();
                if (keyword.isEmpty()) {
                    vetAdapter.setVetList(vetList);
                    listVeterinarian.setAdapter(vetAdapter);
                }
                APIService.apiService.searchVet(keyword).enqueue(new Callback<ListVetResponse>() {
                    @Override
                    public void onResponse(Call<ListVetResponse> call, Response<ListVetResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                List<Vet> vets = response.body().getData();
                                vetAdapter.setVetList(vets);
                                listVeterinarian.setAdapter(vetAdapter);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ListVetResponse> call, Throwable t) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getAllVet() {
        APIService.apiService.getAllVet().enqueue(new Callback<ListVetResponse>() {
            @Override
            public void onResponse(Call<ListVetResponse> call, Response<ListVetResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        vetList = response.body().getData();
                        vetAdapter = new ListVetAdapter(vetList, VetsActivity.this);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(VetsActivity.this, LinearLayoutManager.VERTICAL, false);
                        listVeterinarian.setAdapter(vetAdapter);
                        listVeterinarian.setLayoutManager(layoutManager);
                    }
                }
            }

            @Override
            public void onFailure(Call<ListVetResponse> call, Throwable t) {

            }
        });
    }
}