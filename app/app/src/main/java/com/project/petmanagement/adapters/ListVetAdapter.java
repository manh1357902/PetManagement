package com.project.petmanagement.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.petmanagement.R;
import com.project.petmanagement.activity.veterinarian.VetDetailsActivity;
import com.project.petmanagement.models.entity.Vet;
import com.project.petmanagement.utils.ImageUtils;

import java.util.List;

public class ListVetAdapter extends RecyclerView.Adapter<ListVetAdapter.VererinarianViewHolder> {
    private List<Vet> vetList;
    private Context context;

    public ListVetAdapter(List<Vet> VetList, Context context) {
        this.vetList = VetList;
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setVetList(List<Vet> VetList) {
        this.vetList = VetList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VererinarianViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vets, parent, false);
        return new VererinarianViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VererinarianViewHolder holder, int position) {
        final Vet vet = vetList.get(position);
        holder.avatar.setImageBitmap(ImageUtils.decodeBase64(vet.getAvatar()));
        holder.name.setText(vet.getFullName());
        holder.workAt.setText(vet.getWorkAt());
        String experience1 = vet.getExperience() + " năm";
        holder.experience.setText(experience1);
        holder.itemVet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VetDetailsActivity.class);
                intent.putExtra("vet", vet);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (vetList != null)
            return vetList.size();
        return 0;
    }

    public static class VererinarianViewHolder extends RecyclerView.ViewHolder {
        private final ImageView avatar;
        private final TextView name, workAt, experience;
        private final LinearLayout itemVet;

        public VererinarianViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.image_doctor);
            name = itemView.findViewById(R.id.name_doctor);
            workAt = itemView.findViewById(R.id.work_at);
            experience = itemView.findViewById(R.id.experience);
            itemVet = itemView.findViewById(R.id.item_veterinarian);
        }
    }
}
