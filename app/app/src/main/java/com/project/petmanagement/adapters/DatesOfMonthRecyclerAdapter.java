package com.project.petmanagement.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.petmanagement.R;
import com.project.petmanagement.models.entity.CareActivityNotification;
import com.project.petmanagement.models.entity.VaccinationNotification;
import com.project.petmanagement.payloads.responses.ListCareActivityNotificationResponse;
import com.project.petmanagement.payloads.responses.ListVaccineNotification;
import com.project.petmanagement.services.APIService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatesOfMonthRecyclerAdapter extends RecyclerView.Adapter<DatesOfMonthRecyclerAdapter.DatesOfMonthViewHolder> {
    private final Context context;
    private final List<LocalDate> datesOfMonth;
    private int indexRow = -1;
    private final RecyclerView vaccinationNotificationRecyclerView, careNotificationRecyclerView;
    private final ImageView noVaccinationNotificationImage, noCareNotificationImage;
    private boolean check = false;

    public DatesOfMonthRecyclerAdapter(Context context, List<LocalDate> datesOfMonth, RecyclerView vaccinationNotificationRecyclerView, ImageView noVaccinationNotificationImage, RecyclerView careNotificationRecyclerView, ImageView noCareNotificationImage) {
        this.context = context;
        this.datesOfMonth = datesOfMonth;
        this.vaccinationNotificationRecyclerView = vaccinationNotificationRecyclerView;
        this.noVaccinationNotificationImage = noVaccinationNotificationImage;
        this.careNotificationRecyclerView = careNotificationRecyclerView;
        this.noCareNotificationImage = noCareNotificationImage;
    }

    @NonNull
    @Override
    public DatesOfMonthViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date_of_month, parent, false);
        return new DatesOfMonthViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DatesOfMonthViewHolder holder, @SuppressLint("RecyclerView") int position) {
        LocalDate localDate = datesOfMonth.get(position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.dateOfMonth.setText(covertDayOfWeek(localDate.getDayOfWeek()) + "\n" + localDate.getDayOfMonth());
            if ((localDate.equals(LocalDate.now()) || position == 0) && !check) {
                holder.dateOfMonth.setBackgroundColor(Color.parseColor("#EFF7EA"));
                holder.dateOfMonth.setTextColor(Color.parseColor("#FF61B93C"));
                indexRow = position;
                check = true;
            }
        }
        holder.dateOfMonth.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                indexRow = holder.getBindingAdapterPosition();
                if (listener != null) {
                    listener.onItemClick(position);
                }
                APIService.apiService.getVaccinationNotificationByDate(localDate.toString()).enqueue(new Callback<ListVaccineNotification>() {
                    @Override
                    public void onResponse(Call<ListVaccineNotification> call, Response<ListVaccineNotification> response) {
                        if (response.isSuccessful()) {
                            ListVaccineNotification listVaccineNotification = response.body();
                            if (listVaccineNotification != null) {
                                List<VaccinationNotification> vaccinationNotificationList = listVaccineNotification.getData();
                                if (!vaccinationNotificationList.isEmpty()) {
                                    ListScheduleVaccineAdapter listScheduleVaccineAdapter = new ListScheduleVaccineAdapter(vaccinationNotificationList, context);
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                                    vaccinationNotificationRecyclerView.setAdapter(listScheduleVaccineAdapter);
                                    vaccinationNotificationRecyclerView.setLayoutManager(layoutManager);
                                    vaccinationNotificationRecyclerView.setVisibility(View.VISIBLE);
                                    noVaccinationNotificationImage.setVisibility(View.GONE);
                                } else {
                                    vaccinationNotificationRecyclerView.setVisibility(View.GONE);
                                    noVaccinationNotificationImage.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ListVaccineNotification> call, Throwable t) {

                    }
                });

                APIService.apiService.getCareActivityNotificationByDate(localDate.toString()).enqueue(new Callback<ListCareActivityNotificationResponse>() {
                    @Override
                    public void onResponse(Call<ListCareActivityNotificationResponse> call, Response<ListCareActivityNotificationResponse> response) {
                        if (response.isSuccessful()) {
                            ListCareActivityNotificationResponse listCareActivityNotificationResponse = response.body();
                            if (listCareActivityNotificationResponse != null) {
                                List<CareActivityNotification> careActivityNotificationList = listCareActivityNotificationResponse.getData();
                                if (!careActivityNotificationList.isEmpty()) {
                                    CareActivityNotificationAdapter careActivityNotificationAdapter = new CareActivityNotificationAdapter(careActivityNotificationList, context);
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                                    careNotificationRecyclerView.setAdapter(careActivityNotificationAdapter);
                                    careNotificationRecyclerView.setLayoutManager(layoutManager);
                                    careNotificationRecyclerView.setVisibility(View.VISIBLE);
                                    noCareNotificationImage.setVisibility(View.GONE);
                                } else {
                                    careNotificationRecyclerView.setVisibility(View.GONE);
                                    noCareNotificationImage.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ListCareActivityNotificationResponse> call, Throwable t) {

                    }
                });

                notifyDataSetChanged();
            }
        });
        if (indexRow == position) {
            holder.dateOfMonth.setBackgroundColor(Color.parseColor("#EFF7EA"));
            holder.dateOfMonth.setTextColor(Color.parseColor("#FF61B93C"));
        } else {
            holder.dateOfMonth.setBackgroundColor(Color.parseColor("#D8DAE7"));
            holder.dateOfMonth.setTextColor(Color.BLACK);
            holder.dateOfMonth.setAlpha(0.6F);
        }
    }

    @Override
    public int getItemCount() {
        if (datesOfMonth != null) {
            return datesOfMonth.size();
        }
        return 0;
    }

    public static class DatesOfMonthViewHolder extends RecyclerView.ViewHolder {
        private final Button dateOfMonth;

        public DatesOfMonthViewHolder(@NonNull View itemView) {
            super(itemView);
            dateOfMonth = itemView.findViewById(R.id.date_of_month_item);
        }
    }

    private String covertDayOfWeek(DayOfWeek dayOfWeek) {
        String day = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            switch (dayOfWeek) {
                case MONDAY:
                    day = "Thứ 2";
                    break;
                case TUESDAY:
                    day = "Thứ 3";
                    break;
                case WEDNESDAY:
                    day = "Thứ 4";
                    break;
                case THURSDAY:
                    day = "Thứ 5";
                    break;
                case FRIDAY:
                    day = "Thứ 6";
                    break;
                case SATURDAY:
                    day = "Thứ 7";
                    break;
                case SUNDAY:
                    day = "CN";
                    break;
            }
        }
        return day;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private DatesOfMonthRecyclerAdapter.OnItemClickListener listener;

    public void setOnItemClickListener(DatesOfMonthRecyclerAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
