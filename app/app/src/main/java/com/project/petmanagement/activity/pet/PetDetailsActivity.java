package com.project.petmanagement.activity.pet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.project.petmanagement.R;
import com.project.petmanagement.adapters.PetDetailsViewPager2Adapter;
import com.project.petmanagement.models.entity.Pet;
import com.project.petmanagement.payloads.responses.PetResponse;
import com.project.petmanagement.services.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetDetailsActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ImageView btnBack, btnUpdatePet;
    private TextView namePet, breed;
    private ImageView imagePet;
    private Pet pet;
    private long idPet;
    private FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);
        findViewById();
        idPet = getIntent().getLongExtra("idPet", 0);
        PetDetailsViewPager2Adapter adapter = new PetDetailsViewPager2Adapter(PetDetailsActivity.this);
        adapter.setData(idPet);
        adapter.setBtnAdd(btnAdd);
        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Thông tin");
                    break;
                default:
                    tab.setText("Hoạt động");
                    break;
            }
        }).attach();
        getPet();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnUpdatePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetDetailsActivity.this, UpdatePetActivity.class);
                intent.putExtra("idPet", idPet);
                startActivity(intent);
            }
        });
    }

    private void getPet() {
        APIService.apiService.getPetDetail(idPet).enqueue(new Callback<PetResponse>() {
            @Override
            public void onResponse(Call<PetResponse> call, Response<PetResponse> response) {
                if (response.isSuccessful()) {
                    PetResponse petResponse = response.body();
                    if (petResponse != null && petResponse.getData() != null) {
                        pet = petResponse.getData();
                        namePet.setText(pet.getFullName());
                        breed.setText(pet.getBreed().getName());
                        if (pet.getImage() != null) {
                            Glide.with(PetDetailsActivity.this).load(pet.getImage()).error(R.drawable.no_image).into(imagePet);
                        } else {
                            imagePet.setImageDrawable(ContextCompat.getDrawable(PetDetailsActivity.this, R.drawable.gray_pet_image));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PetResponse> call, Throwable t) {

            }
        });
    }

    private void findViewById() {
        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager_2);
        btnBack = findViewById(R.id.btn_back);
        btnUpdatePet = findViewById(R.id.btn_update_pet);
        namePet = findViewById(R.id.name_pet);
        breed = findViewById(R.id.breed);
        imagePet = findViewById(R.id.image_pet);
        btnAdd = findViewById(R.id.btn_add);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPet();
    }
}