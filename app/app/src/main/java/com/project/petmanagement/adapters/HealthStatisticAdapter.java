package com.project.petmanagement.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.project.petmanagement.R;
import com.project.petmanagement.activity.healthStatistic.UpdateHealthRecordActivity;
import com.project.petmanagement.models.entity.HealthRecord;
import com.project.petmanagement.utils.FormatDateUtils;

import java.util.List;

public class HealthStatisticAdapter extends RecyclerView.Adapter<HealthStatisticAdapter.StaticHealViewHolder> {
    private Context context;
    private List<HealthRecord> healthRecords;

    public HealthStatisticAdapter(Context context, List<HealthRecord> healthRecords) {
        this.context = context;
        this.healthRecords = healthRecords;
    }

    @NonNull
    @Override
    public StaticHealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StaticHealViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_health_record, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull StaticHealViewHolder holder, int position) {
        final HealthRecord healthRecord = healthRecords.get(position);
        holder.imageStatic.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.up));
        if (position != 0) {
            if (healthRecords.get(position - 1).getWeight() > healthRecord.getWeight()) {
                holder.imageStatic.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.down));
            } else {
                holder.imageStatic.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.up));
            }
        } else {
            holder.imageStatic.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.up));
        }
        holder.weight.setText(healthRecord.getWeight() + " kg");
        holder.checkupDate.setText(FormatDateUtils.DateToString(healthRecord.getCheckUpDate()));
        holder.itemStaticHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateHealthRecordActivity.class);
                intent.putExtra("healthRecord", healthRecord);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (healthRecords != null) {
            return healthRecords.size();
        }
        return 0;
    }

    public static class StaticHealViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageStatic;
        private final TextView weight;
        private final TextView checkupDate;
        private final RelativeLayout itemStaticHealth;

        public StaticHealViewHolder(@NonNull View itemView) {
            super(itemView);
            imageStatic = itemView.findViewById(R.id.image_static);
            weight = itemView.findViewById(R.id.static_weight);
            checkupDate = itemView.findViewById(R.id.static_checkup_date);
            itemStaticHealth = itemView.findViewById(R.id.item_static);
        }
    }
}
