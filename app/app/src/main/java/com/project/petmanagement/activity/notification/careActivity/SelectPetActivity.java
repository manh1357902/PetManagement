package com.project.petmanagement.activity.notification.careActivity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.petmanagement.R;
import com.project.petmanagement.adapters.SelectPetActivityAdapter;
import com.project.petmanagement.payloads.responses.ListPetResponse;
import com.project.petmanagement.services.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectPetActivity extends AppCompatActivity {

    private RecyclerView listPetRecyclerView;
    private SelectPetActivityAdapter selectPetActivityAdapter;
    private String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pet_to_care);
        ImageView returnArrow = findViewById(R.id.return_arrow);
        listPetRecyclerView = findViewById(R.id.pet_list_recycler_view);
        returnArrow.setOnClickListener(v -> finish());
        action = getIntent().getStringExtra("action");
        getListPet();

    }

    private void getListPet() {
        APIService.apiService.getAllPetUser().enqueue(new Callback<ListPetResponse>() {
            @Override
            public void onResponse(Call<ListPetResponse> call, Response<ListPetResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getData() != null) {
                        selectPetActivityAdapter = new SelectPetActivityAdapter(response.body().getData(), SelectPetActivity.this, action);
                        listPetRecyclerView.setAdapter(selectPetActivityAdapter);
                        listPetRecyclerView.setLayoutManager(new LinearLayoutManager(SelectPetActivity.this, LinearLayoutManager.VERTICAL, false));
                        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(SelectPetActivity.this, DividerItemDecoration.VERTICAL);
                        listPetRecyclerView.addItemDecoration(decoration);
                    }
                }
            }

            @Override
            public void onFailure(Call<ListPetResponse> call, Throwable t) {

            }
        });
    }
}