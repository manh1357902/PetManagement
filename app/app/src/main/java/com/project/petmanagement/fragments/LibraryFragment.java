package com.project.petmanagement.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.project.petmanagement.R;
import com.project.petmanagement.activity.MainActivity;
import com.project.petmanagement.adapters.LibraryRecyclerViewAdapter;
import com.project.petmanagement.models.entity.Disease;
import com.project.petmanagement.models.entity.Species;
import com.project.petmanagement.payloads.responses.ListDiseaseResponse;
import com.project.petmanagement.payloads.responses.ListSpeciesResponse;
import com.project.petmanagement.services.APIService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LibraryFragment extends Fragment {

    private RecyclerView diseaseRecyclerview;
    private ArrayAdapter<String> speciesAdapter;
    private AutoCompleteTextView speciesDropdown;
    private List<Disease> diseases;
    private TextInputEditText searchInput;
    private MainActivity mActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mActivity = (MainActivity) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement MainActivity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_library, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        diseaseRecyclerview = view.findViewById(R.id.recyclerview);
        speciesDropdown = view.findViewById(R.id.species);
        searchInput = view.findViewById(R.id.search_input);
        getSpecies();
        getDiseases();
        speciesDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String speciesName = speciesAdapter.getItem(position);
                List<Disease> diseaseList = new ArrayList<>();
                if (speciesName.equals("All")) {
                    LibraryRecyclerViewAdapter recyclerViewAdapter = new LibraryRecyclerViewAdapter(getContext(), diseases);
                    diseaseRecyclerview.setAdapter(recyclerViewAdapter);
                } else {
                    if (searchInput.getText().toString().isEmpty()) {
                        for (Disease disease : diseases) {
                            if (disease.getSpecies().getName().equals(speciesName)) {
                                diseaseList.add(disease);
                            }
                        }
                    } else {
                        for (Disease disease : diseases) {
                            if (disease.getSpecies().getName().equals(speciesName) && disease.getName().toLowerCase().contains(searchInput.getText().toString().toLowerCase())) {
                                diseaseList.add(disease);
                            }
                        }
                    }
                    LibraryRecyclerViewAdapter recyclerViewAdapter = new LibraryRecyclerViewAdapter(getContext(), diseaseList);
                    diseaseRecyclerview.setAdapter(recyclerViewAdapter);
                }

            }
        });
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String key = s.toString().toLowerCase();
                List<Disease> diseaseList = new ArrayList<>();
                String speciesName = speciesDropdown.getText().toString();
                if (speciesName.isEmpty() || speciesName.equals("All")) {
                    if (diseases != null) {
                        for (Disease disease : diseases) {
                            if (disease.getName().toLowerCase().contains(key)) {
                                diseaseList.add(disease);
                            }
                        }
                        LibraryRecyclerViewAdapter recyclerViewAdapter = new LibraryRecyclerViewAdapter(mActivity, diseaseList);
                        diseaseRecyclerview.setAdapter(recyclerViewAdapter);
                    }
                } else {
                    if (key.equals("") && diseases != null) {
                        for (Disease disease : diseases) {
                            if (disease.getSpecies().getName().equals(speciesName)) {
                                diseaseList.add(disease);
                            }
                        }
                        LibraryRecyclerViewAdapter recyclerViewAdapter = new LibraryRecyclerViewAdapter(mActivity, diseaseList);
                        diseaseRecyclerview.setAdapter(recyclerViewAdapter);
                    } else if (diseases != null && !speciesDropdown.getText().toString().isEmpty()) {
                        for (Disease disease : diseases) {
                            if (disease.getSpecies().getName().equals(speciesName) && disease.getName().toLowerCase().contains(key)) {
                                diseaseList.add(disease);
                            }
                        }
                        LibraryRecyclerViewAdapter recyclerViewAdapter = new LibraryRecyclerViewAdapter(mActivity, diseaseList);
                        diseaseRecyclerview.setAdapter(recyclerViewAdapter);
                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getSpecies() {
        APIService.apiService.getSpecies().enqueue(new Callback<ListSpeciesResponse>() {
            @Override
            public void onResponse(Call<ListSpeciesResponse> call, Response<ListSpeciesResponse> response) {
                if (mActivity != null) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            List<Species> speciesList = response.body().getData();
                            List<String> species1 = new ArrayList<>();
                            species1.add("All");
                            for (Species species : speciesList) {
                                species1.add(species.getName());
                            }
                            speciesAdapter = new ArrayAdapter<>(mActivity, R.layout.item_dropdown_list, species1);
                            speciesDropdown.setAdapter(speciesAdapter);
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<ListSpeciesResponse> call, Throwable t) {

            }
        });
    }

    private void getDiseases() {
        APIService.apiService.getDiseases().enqueue(new Callback<ListDiseaseResponse>() {
            @Override
            public void onResponse(Call<ListDiseaseResponse> call, Response<ListDiseaseResponse> response) {
                if (mActivity != null) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            diseases = response.body().getData();
                            LibraryRecyclerViewAdapter recyclerViewAdapter = new LibraryRecyclerViewAdapter(getContext(), diseases);
                            RecyclerView.ItemDecoration decoration = new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL);
                            diseaseRecyclerview.setAdapter(recyclerViewAdapter);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
                            diseaseRecyclerview.setLayoutManager(linearLayoutManager);
                            diseaseRecyclerview.addItemDecoration(decoration);
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<ListDiseaseResponse> call, Throwable t) {

            }
        });
    }

}