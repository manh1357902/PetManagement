package com.project.petmanagement.adapters;


import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.project.petmanagement.R;
import com.project.petmanagement.activity.notification.careActivity.DayOfWeekInterface;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DayOfWeekAdapter extends RecyclerView.Adapter<DayOfWeekAdapter.DayViewHolder> {
    private List<String> dayOfWeekList = new ArrayList<>();
    private Map<String, DayOfWeek> dayOfWeekMap = new LinkedHashMap<>();
    private Context context;
    private DayOfWeekInterface dayOfWeekInterface;
    private List<DayOfWeek> dayOfWeeksChoose = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public DayOfWeekAdapter(Context context, DayOfWeekInterface dayOfWeekInterface) {
        this.context = context;
        this.dayOfWeekInterface = dayOfWeekInterface;
        dayOfWeekMap.put("Thứ Hai", DayOfWeek.MONDAY);
        dayOfWeekMap.put("Thứ Ba", DayOfWeek.TUESDAY);
        dayOfWeekMap.put("Thứ Tư", DayOfWeek.WEDNESDAY);
        dayOfWeekMap.put("Thứ Năm", DayOfWeek.THURSDAY);
        dayOfWeekMap.put("Thứ Sáu", DayOfWeek.FRIDAY);
        dayOfWeekMap.put("Thứ Bảy", DayOfWeek.SATURDAY);
        dayOfWeekMap.put("Chủ Nhật", DayOfWeek.SUNDAY);
        dayOfWeekList.addAll(dayOfWeekMap.keySet());
    }

    public void setDayOfWeeksChoose(List<DayOfWeek> dayOfWeeksChoose) {
        this.dayOfWeeksChoose = dayOfWeeksChoose;
        dayOfWeekInterface.getDayOfWeek(dayOfWeeksChoose);
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DayViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day_of_week, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        String day1 = dayOfWeekList.get(position);
        holder.day.setText(day1);
        final boolean[] check = {false};
        if (dayOfWeeksChoose.contains(dayOfWeekMap.get(day1))) {
            holder.layout.setBackgroundColor(ContextCompat.getColor(context, R.color.orange1));
            check[0] = true;
        }
        holder.layout.setOnClickListener(v -> {
            if (!check[0]) {
                holder.layout.setBackgroundColor(ContextCompat.getColor(context, R.color.orange1));
                dayOfWeeksChoose.add(dayOfWeekMap.get(dayOfWeekList.get(position)));
                check[0] = true;
            } else {
                holder.layout.setBackgroundColor(ContextCompat.getColor(context, R.color.pink));
                dayOfWeeksChoose.remove(dayOfWeekMap.get(dayOfWeekList.get(position)));
                check[0] = false;
            }
            dayOfWeekInterface.getDayOfWeek(dayOfWeeksChoose);
        });
    }

    @Override
    public int getItemCount() {
        if (!dayOfWeekList.isEmpty()) {
            return dayOfWeekList.size();
        }
        return 0;
    }

    static class DayViewHolder extends RecyclerView.ViewHolder {
        private final TextView day;
        private RelativeLayout layout;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.text_day);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
