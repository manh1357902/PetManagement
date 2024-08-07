package com.project.petmanagement.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.project.petmanagement.R;
import com.project.petmanagement.activity.notification.careActivity.CareActivityNotificationDetailsActivity;
import com.project.petmanagement.models.entity.CareActivityNotification;
import com.project.petmanagement.models.enums.FrequencyEnum;
import com.project.petmanagement.payloads.requests.CareActivityNotificationRequest;
import com.project.petmanagement.payloads.responses.CareActivityNotificationResponse;
import com.project.petmanagement.services.APIService;
import com.project.petmanagement.utils.FormatDateUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CareActivityNotificationAdapter extends RecyclerView.Adapter<CareActivityNotificationAdapter.CareActivityNotificationViewHolder> {
    private List<CareActivityNotification> careActivityNotifications;
    private Context context;

    public CareActivityNotificationAdapter(List<CareActivityNotification> careActivityNotifications, Context context) {
        this.careActivityNotifications = careActivityNotifications;
        this.context = context;
    }

    @NonNull
    @Override
    public CareActivityNotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_care_activity_notification, parent, false);
        return new CareActivityNotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CareActivityNotificationViewHolder holder, int position) {
        final CareActivityNotification careActivityNotification = careActivityNotifications.get(position);
        holder.title.setText(careActivityNotification.getTitle());
        if (careActivityNotification.getNotificationStatus()) {
            holder.status.setChecked(true);
        } else {
            holder.status.setChecked(false);
        }
        if (careActivityNotification.getSchedule().getFrequency() == FrequencyEnum.NO_REPEAT) {
            holder.frequency.setText("Không lặp lại");
            String date1 = FormatDateUtils.DateToString1(careActivityNotification.getSchedule().getDate());
            holder.date.setText(date1);
        } else {
            if (careActivityNotification.getSchedule().getFrequency() == FrequencyEnum.DAILY)
                holder.frequency.setText("Lặp lại mỗi " + careActivityNotification.getSchedule().getValue() + " ngày 1 lần");
            else
                holder.frequency.setText("Lặp lại mỗi " + careActivityNotification.getSchedule().getValue() + " tuần 1 lần");
            String fromDate = FormatDateUtils.DateToString1(careActivityNotification.getSchedule().getFromDate());
            String toDate = FormatDateUtils.DateToString1(careActivityNotification.getSchedule().getToDate());
            String date = "Từ ngày " + fromDate + " đến ngày " + toDate;
            holder.date.setText(date);
        }
        holder.relativeLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, CareActivityNotificationDetailsActivity.class);
            intent.putExtra("careActivityNotification", careActivityNotification);
            context.startActivity(intent);
        });
        holder.status.setOnClickListener(v -> {
            CareActivityNotificationRequest careActivityNotificationRequest = new CareActivityNotificationRequest();
            careActivityNotificationRequest.setTitle(careActivityNotification.getTitle());
            careActivityNotificationRequest.setNote(careActivityNotification.getNote());
            careActivityNotificationRequest.setNotificationStatus(holder.status.isChecked());
            careActivityNotificationRequest.setPetId(careActivityNotification.getPet().getId());
            APIService.apiService.updateCareActivityNotification(careActivityNotification.getId(), careActivityNotificationRequest).enqueue(new Callback<CareActivityNotificationResponse>() {
                @Override
                public void onResponse(Call<CareActivityNotificationResponse> call, Response<CareActivityNotificationResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Cập nhập thành công", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CareActivityNotificationResponse> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        if (careActivityNotifications != null) {
            return careActivityNotifications.size();
        }
        return 0;
    }

    static class CareActivityNotificationViewHolder extends RecyclerView.ViewHolder {
        private final TextView title, frequency, date;
        private final SwitchCompat status;
        private final RelativeLayout relativeLayout;

        public CareActivityNotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            frequency = itemView.findViewById(R.id.frequency);
            date = itemView.findViewById(R.id.date);
            status = itemView.findViewById(R.id.status);
            relativeLayout = itemView.findViewById(R.id.layout);
        }
    }
}
