package com.project.petmanagement.activity.nutrition;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.project.petmanagement.R;
import com.project.petmanagement.adapters.NutritionsRecyclerViewAdapter;
import com.project.petmanagement.models.entity.FoodType;
import com.project.petmanagement.models.entity.NutritiousFood;
import com.project.petmanagement.models.entity.Species;
import com.project.petmanagement.payloads.responses.ListFoodTypeResponse;
import com.project.petmanagement.payloads.responses.ListNutritiousFoodResponse;
import com.project.petmanagement.payloads.responses.ListSpeciesResponse;
import com.project.petmanagement.services.APIService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NutritiousFoodActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NutritionsRecyclerViewAdapter nutritiousAdapter;
    private List<NutritiousFood> nutritiousFoodList;
    private AutoCompleteTextView foodTypeView;
    private AutoCompleteTextView speciesView;
    private ArrayAdapter<String> foodTypeAdapter;
    private ArrayAdapter<String> speciesArrayAdapter;
    private TextInputEditText search;
    private ImageView btnBack;
    private Map<String, FoodType> foodTypes;
    private Map<String, Species> speciesMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutritious_food);
        findViewById();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        foodTypes = new LinkedHashMap<>();
        speciesMap = new LinkedHashMap<>();
        getAllSpecies();
        getALlFoodType();
        getAllNutritiousFood();
        speciesView.setOnItemClickListener((parent, view, position, id) -> {
            String speciesName = speciesArrayAdapter.getItem(position);
            if ((speciesName.equals("All") || speciesName.isEmpty())) {
                if (foodTypeView.length() == 0 || foodTypeView.getText().toString().equals("All")) {
                    if (search.getText().toString().isEmpty()) {
                        nutritiousAdapter.setNutritiousFoods(nutritiousFoodList);
                        recyclerView.setAdapter(nutritiousAdapter);
                    } else {
                        search(null, null, search.getText().toString());
                    }
                } else {
                    search(null, foodTypes.get(foodTypeView.getText().toString()).getId(), search.getText().toString());
                }
            } else {
                if (foodTypeView.length() == 0 || foodTypeView.getText().toString().equals("All")) {
                    search(speciesMap.get(speciesName).getId(), null, search.getText().toString());
                } else if (!foodTypeView.getText().toString().equals("All")) {
                    search(speciesMap.get(speciesName).getId(), foodTypes.get(foodTypeView.getText().toString()).getId(), search.getText().toString());
                }
            }
        });
        foodTypeView.setOnItemClickListener((parent, view, position, id) -> {
            String foodTypeName = foodTypeAdapter.getItem(position);
            if ((foodTypeName.equals("All") || foodTypeName.isEmpty())) {
                if (speciesView.length() == 0 || speciesView.getText().toString().equals("All")) {
                    if (search.getText().toString().isEmpty()) {
                        nutritiousAdapter.setNutritiousFoods(nutritiousFoodList);
                        recyclerView.setAdapter(nutritiousAdapter);
                    } else {
                        search(null, null, search.getText().toString());

                    }
                } else {
                    search(speciesMap.get(speciesView.getText().toString()).getId(), null, search.getText().toString());
                }
            } else {
                if (speciesView.length() == 0 || speciesView.getText().toString().equals("All")) {
                    search(null, foodTypes.get(foodTypeName).getId(), search.getText().toString());
                } else {
                    search(speciesMap.get(speciesView.getText().toString()).getId(), foodTypes.get(foodTypeName).getId(), search.getText().toString());
                }
            }


        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString();
                if ((foodTypeView.length() == 0 || foodTypeView.getText().toString().equals("All")) && (speciesView.length() == 0 || speciesView.getText().toString().equals("All"))) {
                    if (keyword.isEmpty()) {
                        nutritiousAdapter.setNutritiousFoods(nutritiousFoodList);
                        recyclerView.setAdapter(nutritiousAdapter);
                    } else {
                        search(null, null, keyword);
                    }
                } else if ((foodTypeView.length() != 0 && !foodTypeView.getText().toString().equals("All")) && (speciesView.length() != 0 && !speciesView.getText().toString().equals("All"))) {
                    search(speciesMap.get(speciesView.getText().toString()).getId(), foodTypes.get(foodTypeView.getText().toString()).getId(), keyword);
                } else if ((foodTypeView.length() == 0 || foodTypeView.getText().toString().equals("All") && (speciesView.length() != 0 && !speciesView.getText().toString().equals("All")))) {
                    search(speciesMap.get(speciesView.getText().toString()).getId(), null, keyword);
                } else if ((foodTypeView.length() != 0 && !foodTypeView.getText().toString().equals("All")) && (speciesView.length() == 0 || speciesView.getText().toString().equals("All"))) {
                    search(null, foodTypes.get(foodTypeView.getText().toString()).getId(), keyword);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }


        });
        btnBack.setOnClickListener(v -> finish());
    }

    private void search(Long speciesId, Long foodTypeId, String keyword) {
        APIService.apiService.searchNutritiousFood(speciesId, foodTypeId, keyword).enqueue(new Callback<ListNutritiousFoodResponse>() {
            @Override
            public void onResponse(Call<ListNutritiousFoodResponse> call, Response<ListNutritiousFoodResponse> response) {
                if (response.isSuccessful()) {
                    ListNutritiousFoodResponse listNutritiousFoodResponse = response.body();
                    if (listNutritiousFoodResponse != null && listNutritiousFoodResponse.getData() != null) {
                        List<NutritiousFood> nutritiousFoods = listNutritiousFoodResponse.getData();
                        nutritiousAdapter.setNutritiousFoods(nutritiousFoods);
                        recyclerView.setAdapter(nutritiousAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ListNutritiousFoodResponse> call, Throwable t) {

            }
        });
    }

    private void findViewById() {
        recyclerView = findViewById(R.id.recyclerview);
        foodTypeView = findViewById(R.id.foot_type);
        speciesView = findViewById(R.id.species);
        btnBack = findViewById(R.id.btn_back);
        search = findViewById(R.id.search);
    }

    private void getAllSpecies() {
        APIService.apiService.getSpecies().enqueue(new Callback<ListSpeciesResponse>() {
            @Override
            public void onResponse(Call<ListSpeciesResponse> call, Response<ListSpeciesResponse> response) {
                if (response.isSuccessful()) {
                    ListSpeciesResponse listSpeciesResponse = response.body();
                    if (listSpeciesResponse != null && listSpeciesResponse.getData() != null) {
                        List<String> speciesName = new ArrayList<>();
                        speciesName.add("All");
                        for (Species species : listSpeciesResponse.getData()) {
                            speciesMap.put(species.getName(), species);
                        }
                        speciesName.addAll(speciesMap.keySet());
                        speciesArrayAdapter = new ArrayAdapter<>(NutritiousFoodActivity.this, R.layout.item_dropdown_list, speciesName);
                        speciesView.setAdapter(speciesArrayAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ListSpeciesResponse> call, Throwable t) {

            }
        });
    }

    private void getALlFoodType() {
        APIService.apiService.getAllFoodTypes().enqueue(new Callback<ListFoodTypeResponse>() {
            @Override
            public void onResponse(Call<ListFoodTypeResponse> call, Response<ListFoodTypeResponse> response) {
                if (response.isSuccessful()) {
                    ListFoodTypeResponse foodTypeResponse = response.body();
                    if (foodTypeResponse != null && foodTypeResponse.getData() != null) {
                        for (FoodType x : foodTypeResponse.getData()) {
                            foodTypes.put(x.getName(), x);
                        }
                        ArrayList<String> listItem = new ArrayList<>();
                        listItem.add("All");
                        listItem.addAll(foodTypes.keySet());
                        foodTypeAdapter = new ArrayAdapter<>(NutritiousFoodActivity.this, R.layout.item_dropdown_list, listItem);
                        foodTypeView.setAdapter(foodTypeAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ListFoodTypeResponse> call, Throwable t) {
                Toast.makeText(NutritiousFoodActivity.this, "Api failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAllNutritiousFood() {
        APIService.apiService.getAllNutritious().enqueue(new Callback<ListNutritiousFoodResponse>() {
            @Override
            public void onResponse(Call<ListNutritiousFoodResponse> call, Response<ListNutritiousFoodResponse> response) {
                if (response.isSuccessful()) {
                    ListNutritiousFoodResponse listNutritiousFoodResponse = response.body();
                    if (listNutritiousFoodResponse != null && listNutritiousFoodResponse.getData() != null) {
                        nutritiousFoodList = listNutritiousFoodResponse.getData();
                        nutritiousAdapter = new NutritionsRecyclerViewAdapter(NutritiousFoodActivity.this, nutritiousFoodList);
                        recyclerView.setAdapter(nutritiousAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ListNutritiousFoodResponse> call, Throwable t) {
                Toast.makeText(NutritiousFoodActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}