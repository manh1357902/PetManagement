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

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MonthRecyclerAdapter extends RecyclerView.Adapter<MonthRecyclerAdapter.MonthViewHolder> {
    private final Context context;
    private int indexRow = -1;
    private boolean check = false;
    private final RecyclerView datesOfMonthRecyclerView, vaccinationNotificationRecyclerView, careNotificationRecyclerView;
    private final ImageView noVaccinationNotificationImage, noCareNotificationImage;
    private final List<String> months = Arrays.asList("Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12");

    public MonthRecyclerAdapter(Context context, RecyclerView datesOfMonthRecyclerView, RecyclerView vaccinationNotificationRecyclerView, ImageView noVaccinationNotificationImage, RecyclerView careNotificationRecyclerView, ImageView noCareNotificationImage) {
        this.context = context;
        this.datesOfMonthRecyclerView = datesOfMonthRecyclerView;
        this.vaccinationNotificationRecyclerView = vaccinationNotificationRecyclerView;
        this.noVaccinationNotificationImage = noVaccinationNotificationImage;
        this.careNotificationRecyclerView = careNotificationRecyclerView;
        this.noCareNotificationImage = noCareNotificationImage;
    }

    @NonNull
    @Override
    public MonthViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_month, parent, false);
        return new MonthViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String month = months.get(position);
        holder.month.setText(month);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate currentDate = LocalDate.now();
            int year = currentDate.getYear();
            int currentMonth = currentDate.getMonth().getValue();
            if (position + 1 == currentMonth && !check) {
                holder.month.setBackgroundColor(Color.parseColor("#FFF5EB"));
                holder.month.setTextColor(Color.parseColor("#EDA33D"));
                indexRow = position;
                check = true;
            }

            holder.month.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View view) {
                    indexRow = holder.getBindingAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(position);
                    }
                    setDatesOfMonthRecyclerView(year, position + 1);
                    notifyDataSetChanged();
                }
            });
        }

        if (indexRow == position) {
            holder.month.setBackgroundColor(Color.parseColor("#FFF5EB"));
            holder.month.setTextColor(Color.parseColor("#EDA33D"));
        } else {
            holder.month.setBackgroundColor(Color.parseColor("#D8DAE7"));
            holder.month.setTextColor(Color.BLACK);
            holder.month.setAlpha(0.6F);
        }
    }

    @Override
    public int getItemCount() {
        return 12;
    }

    public static class MonthViewHolder extends RecyclerView.ViewHolder {
        private final Button month;

        public MonthViewHolder(@NonNull View itemView) {
            super(itemView);
            month = itemView.findViewById(R.id.month);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private void setDatesOfMonthRecyclerView(int year, int month) {
        List<LocalDate> localDateList = getDatesOfMonth(year, month);
        DatesOfMonthRecyclerAdapter datesOfMonthRecyclerAdapter = new DatesOfMonthRecyclerAdapter(context, localDateList, vaccinationNotificationRecyclerView, noVaccinationNotificationImage, careNotificationRecyclerView, noCareNotificationImage);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        datesOfMonthRecyclerView.setAdapter(datesOfMonthRecyclerAdapter);
        datesOfMonthRecyclerView.setLayoutManager(layoutManager);
        layoutManager.scrollToPositionWithOffset(0, 0);

        datesOfMonthRecyclerAdapter.setOnItemClickListener(new DatesOfMonthRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                layoutManager.scrollToPositionWithOffset(position, 0);
            }
        });
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
}
