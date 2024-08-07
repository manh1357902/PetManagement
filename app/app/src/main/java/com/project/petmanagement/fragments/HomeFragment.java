package com.project.petmanagement.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.petmanagement.R;
import com.project.petmanagement.activity.notification.ScheduleActivity;
import com.project.petmanagement.activity.notification.careActivity.CareActivityNotificationsActivity;
import com.project.petmanagement.activity.notification.vaccine.VaccinationNotificationsActivity;
import com.project.petmanagement.activity.pet.AddNewPetActivity;
import com.project.petmanagement.adapters.DatesOfMonthRecyclerAdapter;
import com.project.petmanagement.adapters.MonthRecyclerAdapter;
import com.project.petmanagement.adapters.PetHomeAdapter;
import com.project.petmanagement.models.entity.Pet;
import com.project.petmanagement.payloads.responses.ListPetResponse;
import com.project.petmanagement.services.APIService;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    private RecyclerView petRecyclerView, monthsRecyclerView, datesOfMonthRecyclerView, vaccinationNotificationRecyclerView, careNotificationRecyclerView;
    private List<Pet> petList;
    private LinearLayout existedPet;
    private Button addPetBtn;
    private RelativeLayout noPet;
    private ImageView noVaccinationNotificationImage, noCareNotificationImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        getAllPetsByUser();
        getDatesRecyclerView();
        addPetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), AddNewPetActivity.class);
                startActivity(intent);
            }
        });
        noPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), AddNewPetActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout activityRedirect = view.findViewById(R.id.activity_redirect);
        activityRedirect.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ScheduleActivity.class);
            startActivity(intent);
        });
        LinearLayout injectActivity = view.findViewById(R.id.inject_activity);
        injectActivity.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), VaccinationNotificationsActivity.class);
            startActivity(intent);
        });
        LinearLayout careActivity = view.findViewById(R.id.care_activity);
        careActivity.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CareActivityNotificationsActivity.class);
            startActivity(intent);
        });
    }

    private void initView(View view) {
        petRecyclerView = view.findViewById(R.id.pet_list_recycler_view);
        existedPet = view.findViewById(R.id.existed_pet_list);
        addPetBtn = view.findViewById(R.id.add_pet_btn);
        noPet = view.findViewById(R.id.no_pet);
        monthsRecyclerView = view.findViewById(R.id.months_recycler_view);
        datesOfMonthRecyclerView = view.findViewById(R.id.dates_month_recycler_view);
        vaccinationNotificationRecyclerView = view.findViewById(R.id.vaccination_notification_recycler_view);
        noVaccinationNotificationImage = view.findViewById(R.id.no_vaccination_notification_image);
        careNotificationRecyclerView = view.findViewById(R.id.care_notification_recycler_view);
        noCareNotificationImage = view.findViewById(R.id.no_care_notification_image);
    }

    private List<LocalDate> getDatesOfMonth(int year, int month) {
        List<LocalDate> dates = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            YearMonth yearMonth = YearMonth.of(year, month);
            for (int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
                dates.add(LocalDate.of(year, month, day));
            }
        }
        return dates;
    }

    private void getAllPetsByUser() {
        APIService.apiService.getAllPetUser().enqueue(new Callback<ListPetResponse>() {
            @Override
            public void onResponse(Call<ListPetResponse> call, Response<ListPetResponse> response) {
                if (response.isSuccessful()) {
                    ListPetResponse listPetResponse = response.body();
                    if (listPetResponse != null) {
                        petList = listPetResponse.getData();
                        if (petList.isEmpty()) {
                            noPet.setVisibility(View.VISIBLE);
                            existedPet.setVisibility(View.GONE);
                        } else {
                            noPet.setVisibility(View.GONE);
                            existedPet.setVisibility(View.VISIBLE);
                            PetHomeAdapter petHomeAdapter = new PetHomeAdapter(requireContext(), petList);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
                            petRecyclerView.setAdapter(petHomeAdapter);
                            petRecyclerView.setLayoutManager(layoutManager);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ListPetResponse> call, Throwable t) {

            }
        });
    }

    private void getDatesRecyclerView() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate currentDate = LocalDate.now();
            int year = currentDate.getYear();
            int currentMonth = currentDate.getMonth().getValue();

            MonthRecyclerAdapter monthRecyclerAdapter = new MonthRecyclerAdapter(requireContext(), datesOfMonthRecyclerView, vaccinationNotificationRecyclerView, noVaccinationNotificationImage, careNotificationRecyclerView, noCareNotificationImage);
            LinearLayoutManager monthsRecyclerViewLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
            monthsRecyclerView.setAdapter(monthRecyclerAdapter);
            monthsRecyclerView.setLayoutManager(monthsRecyclerViewLayoutManager);
            monthsRecyclerViewLayoutManager.scrollToPositionWithOffset(currentMonth - 1, 0);
            monthRecyclerAdapter.setOnItemClickListener(new MonthRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    monthsRecyclerViewLayoutManager.scrollToPositionWithOffset(position, 0);
                }
            });

            List<LocalDate> localDateList = getDatesOfMonth(year, currentMonth);
            DatesOfMonthRecyclerAdapter datesOfMonthRecyclerAdapter = new DatesOfMonthRecyclerAdapter(requireContext(), localDateList, vaccinationNotificationRecyclerView, noVaccinationNotificationImage, careNotificationRecyclerView, noCareNotificationImage);
            LinearLayoutManager datesOfMonthRecyclerViewLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
            datesOfMonthRecyclerView.setAdapter(datesOfMonthRecyclerAdapter);
            datesOfMonthRecyclerView.setLayoutManager(datesOfMonthRecyclerViewLayoutManager);
            datesOfMonthRecyclerViewLayoutManager.scrollToPositionWithOffset(currentDate.getDayOfMonth() - 1, 0);
            datesOfMonthRecyclerAdapter.setOnItemClickListener(new DatesOfMonthRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    datesOfMonthRecyclerViewLayoutManager.scrollToPositionWithOffset(position, 0);
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllPetsByUser();
        getDatesRecyclerView();
    }
}