package com.project.petmanagement.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.petmanagement.R;
import com.project.petmanagement.activity.dailyLog.DailyLogDetailsActivity;
import com.project.petmanagement.models.entity.DailyActivityLog;
import com.project.petmanagement.utils.FormatDateUtils;

import java.util.List;

public class DailyActivityLogAdapter extends RecyclerView.Adapter<DailyActivityLogAdapter.DailyActivityLogViewHolder> {
    private final Context context;
    private final List<DailyActivityLog> dailyActivityLogs;

    public DailyActivityLogAdapter(Context context, List<DailyActivityLog> dailyActivityLogs) {
        this.context = context;
        this.dailyActivityLogs = dailyActivityLogs;
    }

    @NonNull
    @Override
    public DailyActivityLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily_log, parent, false);
        return new DailyActivityLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyActivityLogViewHolder holder, int position) {
        final DailyActivityLog dailyActivityLog = dailyActivityLogs.get(position);
        holder.dailyLogTitle.setText(dailyActivityLog.getDailyActivity().getName());
        holder.dailyLogTime.setText(dailyActivityLog.getTime().substring(0, 5));
        holder.dailyLogDate.setText(FormatDateUtils.DateToString(dailyActivityLog.getDate()));
        holder.dailyLogItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DailyLogDetailsActivity.class);
                intent.putExtra("logId", dailyActivityLog.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (dailyActivityLogs != null) {
            return dailyActivityLogs.size();
        }
        return 0;
    }

    public static class DailyActivityLogViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout dailyLogItem;
        private final TextView dailyLogTitle;
        private final TextView dailyLogTime;
        private final TextView dailyLogDate;

        public DailyActivityLogViewHolder(@NonNull View itemView) {
            super(itemView);
            dailyLogItem = itemView.findViewById(R.id.daily_log);
            dailyLogTitle = itemView.findViewById(R.id.daily_log_title);
            dailyLogDate = itemView.findViewById(R.id.daily_log_date);
            dailyLogTime = itemView.findViewById(R.id.daily_log_time);
        }
    }
}
